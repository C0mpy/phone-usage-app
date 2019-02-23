package receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;

import dao.JSONDataAccess;
import dao.database.PhoneUsageContract;
import dao.database.PhoneUsageDbHelper;
import model.Metadata;
import model.Survey;
import model.SurveyResult;
import model.UserResult;

public class NetworkStateReceiver extends BroadcastReceiver {

    private Context context;

    @Override
    public void onReceive(final Context context, final Intent intent) {

        this.context = context;

        if (hasInternetConnection()) {
            Metadata metadata = JSONDataAccess.readMetadata(context);
            if (!metadata.getSurveyFetchedFromServer()) {
                fetchSurveyFromServer();
            }

            if (!metadata.getSurveyResultsSentToServer()) {
                sendResultsToServer();
            }
        }
    }

    private boolean hasInternetConnection() {
        final ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo ni = connectivityManager.getActiveNetworkInfo();

        if (ni != null && ni.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    private void fetchSurveyFromServer() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Metadata metadata = JSONDataAccess.readMetadata(context);
                    Survey activeSurvey = getActiveSurvey();
                    if (activeSurvey != null) {
                        JSONDataAccess.writeSurvey(activeSurvey, context);
                        metadata.setSurveyFetchedFromServer(true);
                        JSONDataAccess.writeMetadata(metadata, context);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private Survey getActiveSurvey() {
        try {
            URL url = new URL("https://phone-usage-server.herokuapp.com/active-survey");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("accept", "application/json");
            con.setRequestMethod("GET");
            if (con.getResponseCode() == 200 || con.getResponseCode() == 204) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer content = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();

                Gson gson = new Gson();
                Survey survey = gson.fromJson(content.toString(), Survey.class);
                return survey;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void sendResultsToServer() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Metadata metadata = JSONDataAccess.readMetadata(context);
                SurveyResult surveyResult = JSONDataAccess.readSurveyResult(context);
                String surveyResultId = sendSurveyResult(surveyResult);
                sendUserResult(surveyResultId, metadata);
                metadata.setSurveyResultsSentToServer(true);
                JSONDataAccess.writeMetadata(metadata, context);
            }
        }).start();
    }

    private String sendSurveyResult(SurveyResult surveyResult) {
        try {
            System.setProperty("http.keepAlive", "false");
            URL url = new URL("https://phone-usage-server.herokuapp.com/survey_results");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestMethod("POST");
            con.setDoOutput(true);

            Gson gson = new Gson();
            String surveyResultJsonString = gson.toJson(surveyResult);
            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
            wr.write(surveyResultJsonString);
            wr.close();

            if (con.getResponseCode() == 200 || con.getResponseCode() == 204) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer content = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                HashMap<String, Object> resultMap = gson.fromJson(content.toString(), HashMap.class);
                return String.valueOf(resultMap.get("survey_result_id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("sendResultsToServer", "Failed to send SURVEY RESULTS to Server!");
        }
        return null;
    }

    public void sendUserResult(String surveyResultId, Metadata metadata) {
        try {
            System.setProperty("http.keepAlive", "false");
            URL url = new URL("https://phone-usage-server.herokuapp.com/user_results");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestMethod("POST");
            con.setDoOutput(true);

            String phoneUsage = sumPhoneUsage();

            UserResult userResult = new UserResult();
            userResult.setSurvey_result_id(surveyResultId);
            userResult.setUser_uuid(metadata.getUuid());
            userResult.setTime_spent_on_phone(phoneUsage);
            userResult.setPeriod_start(String.valueOf(metadata.getLastSurveyTakenTime().getTime()));
            userResult.setPeriod_end(String.valueOf(System.currentTimeMillis()));

            Gson gson = new Gson();
            String surveyResultJsonString = gson.toJson(userResult);
            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
            wr.write(surveyResultJsonString);
            wr.close();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("sendUserResult", "Failed to send USER RESULTS to Server!");
        }
    }

    private String sumPhoneUsage() {
        PhoneUsageDbHelper phoneUsageDbHelper = new PhoneUsageDbHelper(context);
        SQLiteDatabase db = phoneUsageDbHelper.getWritableDatabase();
        String sum = "";

        db.beginTransaction();
        Cursor cursor = db.rawQuery(PhoneUsageContract.SQL_SUM_PHONE_USAGE, null);
        if (cursor.moveToFirst()) {
            sum = cursor.getString(0);
        }
        cursor.close();
        db.setTransactionSuccessful();
        db.endTransaction();

        return sum;
    }

}
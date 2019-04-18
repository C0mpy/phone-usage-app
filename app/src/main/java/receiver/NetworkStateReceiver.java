package receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import com.google.gson.Gson;
import dao.JSONDataAccess;
import dao.database.DatabaseHelper;
import dao.database.metadata.MetadataDbHelper;
import dao.database.phone_usage.PhoneUsageDbHelper;
import dao.database.survey.SurveyDbHelper;
import dao.database.survey_result.SurveyResultDbHelper;
import dto.SurveyDTO;
import dto.UserResultDTO;
import mapper.SurveyMapper;
import mapper.SurveyResultMapper;
import mapper.UserResultMapper;
import model.Metadata;
import model.PhoneUsage;
import model.Survey;
import model.SurveyResult;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

public class NetworkStateReceiver extends BroadcastReceiver {

    private Context context;
    private MetadataDbHelper metadataDbHelper;
    private DatabaseHelper databaseHelper;
    private SurveyResultDbHelper surveyResultDbHelper;
    private PhoneUsageDbHelper phoneUsageDbHelper;
    private SurveyDbHelper surveyDbHelper;
    private Gson gson;

    public static final String SERVER_PATH = "https://phone-usage-server.herokuapp.com/";
//    public static final String SERVER_PATH = "http://10.0.2.2:3000/";

    public NetworkStateReceiver(Context context) {
        this.context = context;
        metadataDbHelper = MetadataDbHelper.getInstance(context);
        databaseHelper = DatabaseHelper.getInstance(context);
        surveyResultDbHelper = SurveyResultDbHelper.getInstance(context);
        phoneUsageDbHelper = PhoneUsageDbHelper.getInstance(context);
        surveyDbHelper = SurveyDbHelper.getInstance(context);
        gson = new Gson();
    }

    @Override
    public void onReceive(final Context context, final Intent intent) {
        if (hasInternetConnection()) {
            Metadata metadata = metadataDbHelper.findOne();
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
            try {
                String command = "ping -c 1 google.com";
                return (Runtime.getRuntime().exec(command).waitFor() == 0);
            } catch (Exception e) {
                return false;
            }
        } else {
            return false;
        }
    }

    private void fetchSurveyFromServer() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Metadata metadata = metadataDbHelper.findOne();
                Survey activeSurvey = getActiveSurvey();
                if (activeSurvey != null) {
                    surveyDbHelper.removeAll();
                    surveyDbHelper.save(activeSurvey);
                    metadata.setSurveyFetchedFromServer(true);
                    metadataDbHelper.save(metadata);
                }
            }
        }).start();
    }

    private Survey getActiveSurvey() {
        StringBuffer content = sendRequestToServer("active-survey", null);
        SurveyDTO dto = gson.fromJson(content.toString(), SurveyDTO.class);
        return SurveyMapper.toModel(dto);
    }

    private void sendResultsToServer() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Metadata metadata = metadataDbHelper.findOne();
                SurveyResult surveyResult = surveyResultDbHelper.findOne();
                String surveyResultId = sendSurveyResult(surveyResult);
                sendUserResult(surveyResultId, metadata);
                surveyResultDbHelper.removeAll();
                metadata.setSurveyResultsSentToServer(true);
                metadataDbHelper.save(metadata);
            }
        }).start();
    }

    private String sendSurveyResult(SurveyResult surveyResult) {
        String surveyResultJsonString = gson.toJson(SurveyResultMapper.mapToDTO(surveyResult));
        StringBuffer content = sendRequestToServer("survey_results", surveyResultJsonString);
        HashMap<String, Object> resultMap = gson.fromJson(content.toString(), HashMap.class);
        return String.valueOf(resultMap.get("survey_result_id"));

    }

    private void sendUserResult(String surveyResultId, Metadata metadata) {
        List<PhoneUsage> phoneUsage = phoneUsageDbHelper.getPhoneUsage();
        UserResultDTO userResultDTO = UserResultMapper.mapToDto(surveyResultId, metadata, phoneUsage);
        String userResultJsonString = gson.toJson(userResultDTO);

        sendRequestToServer("user_results", userResultJsonString);
        phoneUsageDbHelper.removeAll();

    }

    private StringBuffer sendRequestToServer(String httpPath, String jsonData) {
        try {
            System.setProperty("http.keepAlive", "false");
            URL url = new URL(SERVER_PATH + httpPath);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("Accept", "application/json");

            if (jsonData == null) {
                con.setRequestMethod("GET");
            } else {
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                con.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
                wr.write(jsonData);
                wr.close();
            }

            if (con.getResponseCode() == 200 || con.getResponseCode() == 204) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer content = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                return content;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("sendRequestToServer", "Failed to send data to: " + httpPath);
        }
        return null;
    }

}
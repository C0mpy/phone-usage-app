package receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dao.database.DatabaseHelper;
import dao.database.metadata.MetadataDbHelper;
import dao.database.phone_usage.PhoneUsageDbHelper;
import dao.database.survey.SurveyDbHelper;
import dao.database.survey_result.SurveyResultDbHelper;
import dto.SurveyDTO;
import mapper.SurveyMapper;
import model.Survey;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
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
            fetchActiveSurveysFromServer();

//            if (!metadata.getSurveyResultsSentToServer()) {
//                sendResultsToServer();
//            }
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

    private void fetchActiveSurveysFromServer() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Survey> activeSurveys = getActiveSurveys();
                surveyDbHelper.removeAll();
                surveyDbHelper.save(activeSurveys);
            }
        }).start();
    }

    private List<Survey> getActiveSurveys() {
        StringBuffer content = sendRequestToServer("active-surveys", null);
        List<SurveyDTO> surveyDTOS = gson.fromJson(content.toString(), new TypeToken<List<SurveyDTO>>(){}.getType());
        return SurveyMapper.toModelList(surveyDTOS);
    }
//
//    private void sendResultsToServer() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Metadata metadata = metadataDbHelper.findOne();
//                SurveyResult surveyResult = surveyResultDbHelper.findOne();
//                String surveyResultId = sendSurveyResult(surveyResult);
//                sendUserResult(surveyResultId, metadata);
//                surveyResultDbHelper.removeAll();
//                metadata.setSurveyResultsSentToServer(true);
//                metadataDbHelper.save(metadata);
//            }
//        }).start();
//    }
//
//    private String sendSurveyResult(SurveyResult surveyResult) {
//        String surveyResultJsonString = gson.toJson(SurveyResultMapper.mapToDTO(surveyResult));
//        StringBuffer content = sendRequestToServer("survey_results", surveyResultJsonString);
//        HashMap<String, Object> resultMap = gson.fromJson(content.toString(), HashMap.class);
//        return String.valueOf(resultMap.get("survey_result_id"));
//
//    }
//
//    private void sendUserResult(String surveyResultId, Metadata metadata) {
//        List<Interval> phoneUsage = phoneUsageDbHelper.getPhoneUsage();
//        UserResultDTO userResultDTO = UserResultMapper.mapToDto(surveyResultId, metadata, phoneUsage);
//        String userResultJsonString = gson.toJson(userResultDTO);
//
//        sendRequestToServer("user_results", userResultJsonString);
//        phoneUsageDbHelper.removeAll();
//
//    }

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
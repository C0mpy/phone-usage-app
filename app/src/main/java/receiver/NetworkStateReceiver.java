package receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dao.database.metadata.MetadataDbHelper;
import dao.database.survey.SurveyDbHelper;
import dao.database.survey_result.SurveyResultDbHelper;
import dto.SurveyDTO;
import mapper.SurveyMapper;
import mapper.SurveyResultMapper;
import model.Metadata;
import model.Survey;
import model.SurveyResult;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class NetworkStateReceiver extends BroadcastReceiver {

    private Context context;
    private MetadataDbHelper metadataDbHelper;
    private SurveyResultDbHelper surveyResultDbHelper;
    private SurveyDbHelper surveyDbHelper;
    private Gson gson;

    public static final String SERVER_PATH = "https://phone-usage-server.herokuapp.com/";
//    public static final String SERVER_PATH = "http://10.0.2.2:3000/";

    public NetworkStateReceiver(Context context) {
        this.context = context;
        metadataDbHelper = MetadataDbHelper.getInstance(context);
        surveyDbHelper = SurveyDbHelper.getInstance(context);
        surveyResultDbHelper = SurveyResultDbHelper.getInstance(context);
        gson = new Gson();
    }

    @Override
    public void onReceive(final Context context, final Intent intent) {
        if (hasInternetConnection()) {
            fetchActiveSurveysFromServer();

            Metadata metadata = metadataDbHelper.findOne();
            if (metadata.getExperimentIsRunning()) {
                sendSurveyResultWithIntervals();
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

    private void sendSurveyResultWithIntervals() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SurveyResult surveyResult = surveyResultDbHelper.findOne();
                String surveyResultJsonString = gson.toJson(SurveyResultMapper.mapToDTO(surveyResult));
                sendRequestToServer("survey_results", surveyResultJsonString);
            }
        }).start();
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
package receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import dao.JSONDataAccess;
import dao.database.DatabaseHelper;
import dao.database.interval.IntervalDbHelper;
import dao.database.survey_result.SurveyResultDbHelper;
import model.Interval;
import model.SurveyResult;

import java.util.List;

public class ScreenOffReceiver extends BroadcastReceiver {

    private IntervalDbHelper intervalDbHelper;
    private SurveyResultDbHelper surveyResultDbHelper;

    public ScreenOffReceiver(Context context) {
        intervalDbHelper = IntervalDbHelper.getInstance(context);
        surveyResultDbHelper = SurveyResultDbHelper.getInstance(context);
    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        Interval intervalData = JSONDataAccess.readCurrentInterval(context);
        if(intervalData == null || intervalData.getStartTime() == 0) {
            intervalData = new Interval();
            intervalData.setStartTime(System.currentTimeMillis());
        }
        intervalData.setEndTime(System.currentTimeMillis());
        JSONDataAccess.writeCurrentInterval(intervalData, context);

        new Thread(new Runnable() {
            public void run() {
                Interval interval = JSONDataAccess.readCurrentInterval(context);
                List<String> surveyResultIds = surveyResultDbHelper.findAllIds();
                for (String surveyResultId: surveyResultIds) {
                    interval.setSurveyResultId(surveyResultId);
                    intervalDbHelper.save(interval);
                }
            }
        }).start();
        Log.d("ScreenOffReceiver", "Screen is OFF!");
    }

}

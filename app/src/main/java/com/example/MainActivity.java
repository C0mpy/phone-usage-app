package com.example;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import dao.JSONDataAccess;
import dao.database.DatabaseHelper;
import dao.database.interval.IntervalDbHelper;
import dao.database.metadata.MetadataDbHelper;
import dao.database.survey.SurveyDbHelper;
import dao.database.survey_result.SurveyResultDbHelper;
import model.Metadata;
import model.Survey;
import phone_usage_app.sw63.phoneusageapp.R;

import java.util.HashMap;

public class MainActivity extends Activity {

    Context context;

    HashMap<Integer, SeekBar> questionSeekbar = new HashMap<>();
    LinearLayout questionLinearLayout;
    Button finishButton;

    MetadataDbHelper metadataDbHelper;
    SurveyDbHelper surveyDbHelper;
    IntervalDbHelper intervalDbHelper;
    SurveyResultDbHelper surveyResultDbHelper;

    Metadata metadata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        JSONDataAccess.initCurrentInterval(context);

        resetDatabaseIfNeeded();

    }

    private void fetchDbHelpers() {
        metadataDbHelper = MetadataDbHelper.getInstance(context);
        surveyDbHelper = SurveyDbHelper.getInstance(context);
        intervalDbHelper = IntervalDbHelper.getInstance(context);
        surveyResultDbHelper = SurveyResultDbHelper.getInstance(context);
    }

    private void fetchDbData() {
        metadata = metadataDbHelper.findOne();
    }

    private void moveToNextActivity() {
        Survey activeSurvey = JSONDataAccess.readActiveSurvey(context);
        if (activeSurvey != null && activeSurvey.getEndTime() > System.currentTimeMillis()) {
            Intent surveyStatusActivity = new Intent(context, SurveyStatusActivity.class);
            startActivity(surveyStatusActivity);
        } else {
            Intent surveyPickActivity = new Intent(context, SurveyPickActivity.class);
            startActivity(surveyPickActivity);
        }
    }

    private void resetDatabaseIfNeeded() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        context.deleteDatabase(DatabaseHelper.DATABASE_NAME);
                        JSONDataAccess.writeActiveSurvey(null, context);
                    case DialogInterface.BUTTON_NEGATIVE:
                        fetchDbHelpers();
                        fetchDbData();
                        moveToNextActivity();
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        AlertDialog alertDialog = builder
                                        .setMessage("Should we reset the database ?")
                                        .setPositiveButton("Yes", dialogClickListener)
                                        .setNegativeButton("No", dialogClickListener)
                                        .create();
        alertDialog.show();
    }

}

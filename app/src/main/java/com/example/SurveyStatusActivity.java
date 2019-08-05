package com.example;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
import dao.database.interval.IntervalDbHelper;
import dao.database.metadata.MetadataDbHelper;
import dao.database.survey_result.SurveyResultDbHelper;
import model.Interval;
import model.Metadata;
import model.SurveyResult;
import phone_usage_app.sw63.phoneusageapp.R;
import util.Util;

import java.util.List;

public class SurveyStatusActivity extends Activity {

    Context context;

    MetadataDbHelper metadataDbHelper;
    IntervalDbHelper intervalDbHelper;
    SurveyResultDbHelper surveyResultDbHelper;

    TextView textView;

    Metadata metadata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.survey_status_activity);
        context = getApplicationContext();
        fetchDbHelpers();
        fetchDbData();

        textView = findViewById(R.id.surveyStatusText);
        SurveyResult activeSurveyResult = surveyResultDbHelper.findOne();
        long timeSpentForExperiment = calculateTimeSpentforExperiment(activeSurveyResult.getSurveyId());
        textView.setText("Since the start of this experimet at: " + Util.millisToDate(metadata.getExperimentStartTime()) +
                         " time spent on phone is: " + Util.millisToMinutes(timeSpentForExperiment) + " minutes");
    }

    private void fetchDbHelpers() {
        metadataDbHelper = MetadataDbHelper.getInstance(context);
        intervalDbHelper = IntervalDbHelper.getInstance(context);
        surveyResultDbHelper = SurveyResultDbHelper.getInstance(context);
    }

    private void fetchDbData() {
        metadata = metadataDbHelper.findOne();
    }

    private long calculateTimeSpentforExperiment(String surveyResultId) {
        List<Interval> intervalList = intervalDbHelper.findWhereSurveyResultId(surveyResultId);
        long summ = 0;
        for (Interval interval : intervalList) {
            summ += interval.getEndTime() - interval.getStartTime();
        }
        return summ;
    }


}

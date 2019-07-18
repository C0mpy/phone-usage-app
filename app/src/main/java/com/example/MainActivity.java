package com.example;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import dao.JSONDataAccess;
import dao.database.DatabaseHelper;
import dao.database.metadata.MetadataDbHelper;
import dao.database.interval.IntervalDbHelper;
import dao.database.survey.SurveyDbHelper;
import dao.database.survey_result.SurveyResultDbHelper;
import model.Metadata;
import model.Question;
import model.QuestionResponse;
import model.Survey;
import model.SurveyResult;
import phone_usage_app.sw63.phoneusageapp.R;
import service.StartReceiversService;

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
        JSONDataAccess.initPhoneUsage(context);
//        context.deleteDatabase(DatabaseHelper.DATABASE_NAME);
        fetchDbHelpers();
        fetchDbData();

        moveToNextActivity();


        questionLinearLayout = findViewById(R.id.questions_linear_layout);
//        addListeners();
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
        if (!this.metadata.getExperimentIsRunning()) {
            Intent timeOnPhoneActivity = new Intent(context, SurveyPickActivity.class);
            startActivity(timeOnPhoneActivity);
        } else {
//            Intent surveyPickActivity = new Intent(context, activityClass);
//            context.startActivity(surveyPickActivity);
        }
    }

//    private void addListeners() {
//        finishButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SurveyResult surveyResult = createSurveyResult(survey);
//                saveSurveyResultAndUpdateMetadata(surveyResult);
//
//                Toast.makeText(
//                      context,
//                      "Thanks for taking the survey! Next survey expected at: survey.getIntervals[0].getEndTime()",
//                      Toast.LENGTH_LONG).show();
//
//                registerReceivers();
//            }
//        });
//    }

    private SurveyResult createSurveyResult(Survey survey) {
        SurveyResult surveyResult = new SurveyResult();
        surveyResult.setSurveyId(survey.getForeignId());

        for (Question q : survey.getQuestions()) {
            SeekBar seekBar = questionSeekbar.get(("textView" + q.getContent()).hashCode());

            QuestionResponse questionResponse = new QuestionResponse();
            questionResponse.setQuestionId(q.getForeignId());
            questionResponse.setResponse(Integer.toString(seekBar.getProgress()));
            surveyResult.getQuestionResponses().add(questionResponse);
        }
        return surveyResult;
    }

    private void saveSurveyResultAndUpdateMetadata(SurveyResult surveyResult) {
        surveyResultDbHelper.save(surveyResult);
    }

    private void registerReceivers() {
        startService(new Intent(context, StartReceiversService.class));
        finish();
    }

}

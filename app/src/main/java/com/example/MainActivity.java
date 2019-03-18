package com.example;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import dao.database.DatabaseHelper;
import dao.database.metadata.MetadataDbHelper;
import dao.database.phone_usage.PhoneUsageDbHelper;
import dao.database.survey.SurveyDbHelper;
import dao.database.survey_result.SurveyResultDbHelper;
import model.Metadata;
import model.Question;
import model.QuestionResponse;
import model.Survey;
import model.SurveyResult;
import phone_usage_app.sw63.phoneusageapp.R;
import receiver.SurveyAlarm;
import service.StartReceiversService;
import util.Util;

public class MainActivity extends Activity {

    Context context;

    HashMap<Integer, SeekBar> questionSeekbar = new HashMap<>();
    LinearLayout questionLinearLayout;
    Button finishButton;

    MetadataDbHelper metadataDbHelper;
    SurveyDbHelper surveyDbHelper;
    PhoneUsageDbHelper phoneUsageDbHelper;

    Metadata metadata;
    Survey survey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();
        context.deleteDatabase(DatabaseHelper.DATABASE_NAME);

        questionLinearLayout = (LinearLayout)findViewById(R.id.questions_linear_layout);

        metadataDbHelper = MetadataDbHelper.getInstance(context);
        metadata = metadataDbHelper.findOne();
        surveyDbHelper = SurveyDbHelper.getInstance(context);
        survey = surveyDbHelper.findOne();
        phoneUsageDbHelper = PhoneUsageDbHelper.getInstance(context);

        displaySurvey();
    }

    @Override
    protected void onStart() {
        super.onStart();
        addListeners();
    }

    private void addListeners() {
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SurveyResult surveyResult = createSurveyResult(survey);
                saveSurveyResultAndUpdateMetadata(surveyResult, context);

                Toast.makeText(context,
                        "Thanks for taking the survey! Next survey expected in: "
                                + metadata.getTimeToNextSurveyInHours() / 24 + " days!",
                        Toast.LENGTH_LONG).show();

                registerReceivers();
            }
        });
    }

    private void displaySurvey() {

        TextView surveyTitle = (TextView)findViewById(R.id.surveyTitle);
        surveyTitle.setText(survey.getTitle());

        TextView surveyDescription = (TextView)findViewById(R.id.surveyDescription);
        surveyDescription.setText(survey.getDescription());

        for(Question q : survey.getQuestions()) {

            TextView textView = new TextView(context);
            textView.setId(("textView" + q.getContent()).hashCode());
            textView.setText(q.getContent());
            textView.setTextColor(Color.parseColor("#f7f0aa"));
            textView.setTextSize(Util.convertToDp(10, context));

            SeekBar seekBar = new SeekBar(context);
            seekBar.setId(("seekBar" + q.getContent()).hashCode());
            seekBar.setMax(5);

            questionSeekbar.put(textView.getId(), seekBar);

            LinearLayout linearLayout = new LinearLayout(context);
            linearLayout.setPadding(10, 0, 10 ,50);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setGravity(Gravity.CENTER);
            linearLayout.addView(textView);
            linearLayout.addView(seekBar);

            questionLinearLayout.addView(linearLayout);
        }

        finishButton = new Button(context);
        finishButton.setId("finishButton".hashCode());
        finishButton.setText("FINISH");
        finishButton.setTextSize(Util.convertToDp(15, context));

        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT
        );
        params.setMargins(0, 0, 0, 50);
        finishButton.setLayoutParams(params);

        questionLinearLayout.addView(finishButton);
    }

    private SurveyResult createSurveyResult(Survey survey) {
        SurveyResult surveyResult = new SurveyResult();
        surveyResult.setSurveyId(survey.getForeignId());

        for(Question q : survey.getQuestions()) {
            SeekBar seekBar = questionSeekbar.get(("textView" + q.getContent()).hashCode());

            QuestionResponse questionResponse = new QuestionResponse();
            questionResponse.setQuestion(q);
            questionResponse.setResponse(Integer.toString(seekBar.getProgress()));
            surveyResult.getQuestionResponses().add(questionResponse);
        }
        return surveyResult;
    }

    private void saveSurveyResultAndUpdateMetadata(SurveyResult surveyResult, Context context) {
        SurveyResultDbHelper surveyResultDbHelper = SurveyResultDbHelper.getInstance(context);
        surveyResultDbHelper.save(surveyResult);

        metadata.setSurveyFetchedFromServer(false);
        metadata.setSurveyResultsSentToServer(false);
        metadata.setLastSurveyTakenTime(System.currentTimeMillis());
        metadataDbHelper.save(metadata);
    }

    private void registerReceivers() {
        Intent startIntent = new Intent(context, StartReceiversService.class);
        startService(startIntent);
        SurveyAlarm.start(context);
        finish();
    }


}

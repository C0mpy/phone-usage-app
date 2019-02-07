package com.example;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import dao.JSONDataAccess;
import model.Metadata;
import model.Question;
import model.QuestionResponse;
import model.Survey;
import model.SurveyResult;
import phone_usage_app.sw63.phoneusageapp.R;
import receiver.NetworkStateReceiver;
import receiver.ScreenOffReceiver;
import receiver.ScreenOnReceiver;
import service.StartReceiversService;
import util.Util;

public class MainActivity extends Activity {

    HashMap<Integer, Integer> questionSeekbar = new HashMap<>();
    Context context;
    LinearLayout questionLinearLayout;
    Metadata metadata;
    Survey survey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questionLinearLayout = (LinearLayout)findViewById(R.id.questions_linear_layout);
        context = getApplicationContext();

        initMetadata();
        initSurvey();
        initPhoneUsage();

        displaySurvey();
    }

    private void initMetadata() {
        Metadata metadata = new Metadata();
        metadata.setUuid(UUID.randomUUID().toString());
        metadata.setLastSurveyTakenTime(new Date());
        metadata.setSurveyFetchedFromServer(false);
        metadata.setTimeToNextSurveyInHours(24 * 7);
        metadata.setSurveyResultsSentToServer(false);

        this.metadata = JSONDataAccess.initMetadata(metadata, context);
    }

    private void initSurvey() {
        Question question1 = new Question();
        question1.setId("-1");
        question1.setText("How would you rate your quality of sleep?");

        Question question2 = new Question();
        question2.setId("-2");
        question2.setText("How would you rate your attention span?");

        List<Question> questions = new ArrayList<>();
        questions.add(question1);
        questions.add(question2);

        Survey survey = new Survey();
        survey.setId("INITIAL");
        survey.setQuestions(questions);

        this.survey = JSONDataAccess.initSurvey(survey, context);
    }

    private void initPhoneUsage() {
        JSONDataAccess.initPhoneUsage(context);
    }

    private void displaySurvey() {

        for(Question q : survey.getQuestions()) {

            TextView textView = new TextView(context);
            textView.setId(("textView" + q.getText()).hashCode());
            textView.setText(q.getText());
            textView.setTextColor(Color.parseColor("#f7f0aa"));
            textView.setTextSize(Util.convertToDp(10, context));

            SeekBar seekBar = new SeekBar(context);
            seekBar.setId(("seekBar" + q.getText()).hashCode());
            seekBar.setMax(5);

            questionSeekbar.put(textView.getId(), seekBar.getId());

            LinearLayout linearLayout = new LinearLayout(context);
            linearLayout.setPadding(10, 0, 10 ,50);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setGravity(Gravity.CENTER);
            linearLayout.addView(textView);
            linearLayout.addView(seekBar);

            questionLinearLayout.addView(linearLayout);
        }

        Button finishButton = new Button(context);
        finishButton.setId("finishButton".hashCode());
        finishButton.setText("FINISH");
        finishButton.setTextSize(Util.convertToDp(10, context));

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

        questionLinearLayout.addView(finishButton);
    }

    private SurveyResult createSurveyResult(Survey survey) {
        SurveyResult surveyResult = new SurveyResult();
        surveyResult.setSurveyId(survey.getId());

        for(Question q : survey.getQuestions()) {
            int seekBarId = ("seekBar" + q.getText()).hashCode();
            SeekBar seekBar = (SeekBar) findViewById(seekBarId);

            QuestionResponse questionResponse = new QuestionResponse();
            questionResponse.setQuestion(q);
            questionResponse.setResponse(Integer.toString(seekBar.getProgress()));
            surveyResult.getQuestionResults().add(questionResponse);
        }
        return surveyResult;
    }

    private void saveSurveyResultAndUpdateMetadata(SurveyResult surveyResult, Context context) {
        JSONDataAccess.writeSurveyResult(surveyResult, context);

        metadata.setSurveyFetchedFromServer(false);
        metadata.setSurveyResultsSentToServer(false);
        metadata.setLastSurveyTakenTime(new Date());
        JSONDataAccess.writeMetadata(metadata, context);
    }

    private void registerReceivers() {
        Intent startIntent = new Intent(context, StartReceiversService.class);
        startService(startIntent);
        //       SurveyAlarm.start(context);
//        finish();
    }


}

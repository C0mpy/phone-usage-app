package com.example;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
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
import model.Survey;
import phone_usage_app.sw63.phoneusageapp.R;
import util.Util;

public class MainActivity extends Activity {

    HashMap<Integer, Integer> questionSeekbar = new HashMap<>();
    Context context;
    LinearLayout questionLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questionLinearLayout = (LinearLayout)findViewById(R.id.questions_linear_layout);
        context = getApplicationContext();

        initMetadata();
        initSurvey();

        displaySurvey();
    }

    private void initMetadata() {
        Metadata metadata = new Metadata();
        metadata.setUuid(UUID.randomUUID().toString());
        metadata.setLastSurveyTakenTime(new Date());
        metadata.setResultsSentToServer(false);

        JSONDataAccess.initMetadata(metadata, context);
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

        JSONDataAccess.initSurvey(survey, context);
    }

    private void displaySurvey() {

        Survey survey = JSONDataAccess.readSurvey(context);

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
                for(int textViewId : questionSeekbar.keySet()) {
                    TextView textView = (TextView) findViewById(textViewId);
                    SeekBar seekBar = (SeekBar) findViewById(questionSeekbar.get(textViewId));


                }
            }
        });
        questionLinearLayout.addView(finishButton);
    }

}

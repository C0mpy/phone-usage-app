package com.example;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import dao.database.metadata.MetadataDbHelper;
import dao.database.survey.SurveyDbHelper;
import model.Metadata;
import model.Survey;
import phone_usage_app.sw63.phoneusageapp.R;
import util.Util;

import java.util.ArrayList;
import java.util.List;

public class SurveyPickActivity extends Activity {

    Context context;

    LinearLayout surveyPickLinearLayout;

    MetadataDbHelper metadataDbHelper;
    SurveyDbHelper surveyDbHelper;

    List<Survey> surveys = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();

        setContentView(R.layout.survey_pick_activity);
        surveyPickLinearLayout = findViewById(R.id.survey_pick_linear_layout);

        fetchDbHelpers();
        fetchDbData();
        displaySurveys();
    }

    private void fetchDbHelpers() {
        metadataDbHelper = MetadataDbHelper.getInstance(context);
        surveyDbHelper = SurveyDbHelper.getInstance(context);
    }

    private void fetchDbData() {
        surveys = surveyDbHelper.findAll();
    }

    private void displaySurveys() {
        for (final Survey survey : this.surveys) {
            Button button = new Button(context);
            button.setId(("button" + survey.getTitle()).hashCode());
            button.setText(survey.getTitle());
            button.setTextColor(Color.parseColor("#f7f0aa"));
            button.setTextSize(Util.convertToDp(10, context));
            button.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v) {
                    Intent intent = new Intent(context, SurveyRespondActivity.class);
                    intent.putExtra("survey", survey);
                    startActivity(intent);
                }
            });

            this.surveyPickLinearLayout.addView(button);
        }

    }

}

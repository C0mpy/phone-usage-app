package com.example;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import phone_usage_app.sw63.phoneusageapp.R;

public class SurveyRespondActivity extends Activity {

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();

        setContentView(R.layout.survey_respond_activity);

    }
}

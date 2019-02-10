package dao;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.Metadata;
import model.PhoneUsage;
import model.Survey;
import model.SurveyResult;

import static android.content.Context.MODE_PRIVATE;

public class JSONDataAccess {

    private static Gson gson = new Gson();

    public static Survey readSurvey(Context context) {
        return gson.fromJson(getJsonString("survey", context), Survey.class);
    }

    public static Survey initSurvey(Survey survey, Context context) {
        SharedPreferences prefs = context.getSharedPreferences("survey", MODE_PRIVATE);
        if(!prefs.contains("survey")){
            writeSurvey(survey, context);
            return survey;
        }
        else {
            return readSurvey(context);
        }
    }

    public static void writeSurvey(Survey survey, Context context) {
        writeJsonObject("survey", survey, context);
    }

    public static void writeSurveyResult(SurveyResult surveyResult, Context context) {
        writeJsonObject("surveyResult", surveyResult, context);
    }

    public static Metadata readMetadata(Context context) {
        return gson.fromJson(getJsonString("metadata", context), Metadata.class);
    }

    public static Metadata initMetadata(Metadata metadata, Context context) {
        SharedPreferences prefs = context.getSharedPreferences("metadata", MODE_PRIVATE);
        if(!prefs.contains("metadata")){
            writeJsonObject("metadata", metadata, context);
            return metadata;
        }
        else {
            return readMetadata(context);
        }
    }

    public static void writeMetadata(Metadata metadata, Context context) {
        writeJsonObject("metadata", metadata, context);
    }

    public static void initPhoneUsage(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("phoneUsage", MODE_PRIVATE);
        PhoneUsage phoneUsage = new PhoneUsage();
        phoneUsage.setStartTime(System.currentTimeMillis());
        writeCurrentPhoneUsageData(phoneUsage, context);
    }

    public static void writeCurrentPhoneUsageData(PhoneUsage phoneUsageData, Context context) {
        writeJsonObject("phoneUsage", phoneUsageData, context);
        PhoneUsage pu = readCurrentPhoneUsageData(context);
        System.out.println("we");
    }

    public static PhoneUsage readCurrentPhoneUsageData(Context context) {
        return gson.fromJson(getJsonString("phoneUsage", context), PhoneUsage.class);
    }

    private static String getJsonString(String dataName, Context context) {
        SharedPreferences prefs = context.getSharedPreferences(dataName, MODE_PRIVATE);
        return prefs.getString(dataName, null);
    }

    private static void writeJsonObject(String dataName, Object data, Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(dataName, MODE_PRIVATE).edit();
        editor.putString(dataName, gson.toJson(data));
        editor.commit();
    }
}

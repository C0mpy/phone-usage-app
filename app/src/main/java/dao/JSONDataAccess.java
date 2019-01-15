package dao;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.Metadata;
import model.Question;
import model.Survey;

import static android.content.Context.MODE_PRIVATE;

public class JSONDataAccess {

    private static Gson gson = new Gson();

    public static Survey readSurvey(Context context) {
        return gson.fromJson(getJsonString("survey", context), Survey.class);
    }

    public static void initSurvey(Survey survey, Context context) {
        SharedPreferences prefs = context.getSharedPreferences("survey", MODE_PRIVATE);
        if(!prefs.contains("survey")){
            writeJsonObject("survey", survey, context);
        }
    }

    public static void writeSurvey(Survey survey, Context context) {
        writeJsonObject("survey", survey, context);
    }

    public static Metadata readMetadata(Context context) {
        return gson.fromJson(getJsonString("metadata", context), Metadata.class);
    }

    public static void initMetadata(Metadata metadata, Context context) {
        SharedPreferences prefs = context.getSharedPreferences("metadata", MODE_PRIVATE);
        if(!prefs.contains("metadata")){
            writeJsonObject("metadata", metadata, context);
        }
    }

    public static void writeMetadata(Metadata metadata, Context context) {
        writeJsonObject("metadata", metadata, context);
    }

    private static String getJsonString(String dataName, Context context) {
        SharedPreferences prefs = context.getSharedPreferences(dataName, MODE_PRIVATE);
        return prefs.getString(dataName, null);
    }

    private static void writeJsonObject(String dataName, Object data, Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(dataName, MODE_PRIVATE).edit();
        editor.putString(dataName, gson.toJson(data));
        editor.apply();
    }
}

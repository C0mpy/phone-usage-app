package dao.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.UUID;

import dao.database.metadata.MetadataContract;
import dao.database.phone_usage.PhoneUsageContract;
import dao.database.question.QuestionContract;
import dao.database.question_response.QuestionResponseContract;
import dao.database.survey.SurveyContract;
import dao.database.survey_result.SurveyResultContract;

public class DatabaseHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "phone_usage.db";

    private static DatabaseHelper mInstance = null;

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized DatabaseHelper getInstance(Context ctx) {
        if (mInstance == null) {
            mInstance = new DatabaseHelper(ctx.getApplicationContext());
        }
        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.beginTransaction();

        db.execSQL(MetadataContract.CREATE_TABLE);
        db.execSQL(QuestionContract.CREATE_TABLE);
        db.execSQL(SurveyContract.CREATE_TABLE);
        db.execSQL(QuestionResponseContract.CREATE_TABLE);
        db.execSQL(SurveyResultContract.CREATE_TABLE);
        db.execSQL(PhoneUsageContract.CREATE_TABLE);

        ContentValues metadataCV = new ContentValues();
        metadataCV.put(MetadataContract.MetadataEntry.COLUMN_UUID, UUID.randomUUID().toString());
        metadataCV.put(MetadataContract.MetadataEntry.COLUMN_LAST_SURVEY_TAKEN_TIME, System.currentTimeMillis());
        metadataCV.put(MetadataContract.MetadataEntry.COLUMN_SURVEY_FETCHED_FROM_SERVER, false);
        metadataCV.put(MetadataContract.MetadataEntry.COLUMN_SURVEY_RESULTS_SENT_TO_SERVER, false);
        metadataCV.put(MetadataContract.MetadataEntry.COLUMN_TIME_TO_NEXT_SURVEY_IN_HOURS, 24 * 7);
        db.insert(MetadataContract.MetadataEntry.TABLE_NAME, null, metadataCV);


        ContentValues surveyCV = new ContentValues();
        surveyCV.put(SurveyContract.SurveyEntry.COLUMN_FOREIGN_ID, "1");
        surveyCV.put(SurveyContract.SurveyEntry.COLUMN_TITLE, "This is the inital Survey deployed to mobile devices.");
        surveyCV.put(SurveyContract.SurveyEntry.COLUMN_DESCRIPTION, "Please answer questions on a scale 1 - 5");
        long surveyId = db.insert(SurveyContract.SurveyEntry.TABLE_NAME, null, surveyCV);

        ContentValues question1CV = new ContentValues();
        question1CV.put(QuestionContract.QuestionEntry.COLUMN_FOREIGN_ID, "1");
        question1CV.put(QuestionContract.QuestionEntry.COLUMN_CONTENT, "How would you rate your quality of sleep?");
        question1CV.put(QuestionContract.QuestionEntry.COLUMN_SURVEY_ID, surveyId);
        db.insert(QuestionContract.QuestionEntry.TABLE_NAME, null, question1CV);

        ContentValues question2CV = new ContentValues();
        question2CV.put(QuestionContract.QuestionEntry.COLUMN_FOREIGN_ID, "2");
        question2CV.put(QuestionContract.QuestionEntry.COLUMN_CONTENT, "How would you rate your attention span?");
        question2CV.put(QuestionContract.QuestionEntry.COLUMN_SURVEY_ID, surveyId);
        db.insert(QuestionContract.QuestionEntry.TABLE_NAME, null, question2CV);

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

}

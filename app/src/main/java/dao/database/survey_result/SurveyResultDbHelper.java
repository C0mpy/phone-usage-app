package dao.database.survey_result;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import dao.database.DatabaseHelper;
import dao.database.question_response.QuestionResponseDbHelper;
import model.QuestionResponse;
import model.SurveyResult;

public class SurveyResultDbHelper {

    private static SQLiteDatabase db;
    private static Context context;
    private static DatabaseHelper databaseHelper;
    private static SurveyResultDbHelper sInstance;

    private SurveyResultDbHelper(Context _context) {
        context = _context;
        databaseHelper = DatabaseHelper.getInstance(context);
        db = databaseHelper.getWritableDatabase();
    }

    public static synchronized SurveyResultDbHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new SurveyResultDbHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    public Integer count() {
        Integer result = null;
        db.beginTransaction();
        Cursor cursor = db.rawQuery(SurveyResultContract.COUNT_ENTRIES, null);
        if (cursor.moveToFirst()) {
            String str = cursor.getString(0);
            result = Integer.valueOf(str);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        return result;
    }

    public SurveyResult save(SurveyResult surveyResult) {
        db.beginTransaction();

        ContentValues values = new ContentValues();
        values.put(SurveyResultContract.SurveyResultEntry.COLUMN_SURVEY_ID, surveyResult.getSurveyId());

        String id = String.valueOf(db.insert(SurveyResultContract.SurveyResultEntry.TABLE_NAME, null, values));
        surveyResult.setId(id);

        QuestionResponseDbHelper questionResponseDbHelper = QuestionResponseDbHelper.getInstance(context);
        questionResponseDbHelper.saveAll(surveyResult.getQuestionResponses(), String.valueOf(surveyResult.getId()));

        db.setTransactionSuccessful();
        db.endTransaction();
        return surveyResult;
    }

    public List<SurveyResult> findAll() {
        List<SurveyResult> result = new ArrayList<>();
        db.beginTransaction();
        Cursor cursor = db.rawQuery(SurveyResultContract.FIND, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String id = cursor.getString(cursor.getColumnIndexOrThrow(BaseColumns._ID));
                String surveyId = cursor.getString(cursor.getColumnIndexOrThrow(SurveyResultContract.SurveyResultEntry.COLUMN_SURVEY_ID));

                QuestionResponseDbHelper questionResponseDbHelper = QuestionResponseDbHelper.getInstance(context);
                List<QuestionResponse> questionResponses = questionResponseDbHelper.findWhereSurveyResultId(id);

                result.add(new SurveyResult(id, surveyId, questionResponses));
                cursor.moveToNext();
            }
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        return result;
    }

    public SurveyResult findOne() {
        int count = count();
        if(count == 0) {
            Log.w("SurveyResDbHelper.init", "There is no SurveyResult Entity in DB");
        } else if (count > 1) {
            Log.w("SurveyResDbHelper.init", "There is more than one SurveyResult Entity in DB");
        } else {
            return findAll().get(0);
        }
        return null;
    }

}

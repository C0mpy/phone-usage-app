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
import dao.database.interval.IntervalDbHelper;
import dao.database.question.QuestionContract;
import dao.database.question_response.QuestionResponseDbHelper;
import model.Interval;
import model.QuestionResponse;
import model.Survey;
import model.SurveyResult;

public class SurveyResultDbHelper {

    private static SQLiteDatabase db;
    private static Context context;
    private static DatabaseHelper databaseHelper;
    private static QuestionResponseDbHelper questionResponseDbHelper;
    private static IntervalDbHelper intervalDbHelper;
    private static SurveyResultDbHelper sInstance;

    private SurveyResultDbHelper(Context _context) {
        context = _context;
        databaseHelper = DatabaseHelper.getInstance(context);
        db = databaseHelper.getWritableDatabase();
        db.enableWriteAheadLogging();
        questionResponseDbHelper = QuestionResponseDbHelper.getInstance(context);

    }

    public static synchronized SurveyResultDbHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new SurveyResultDbHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    public SurveyResult save(SurveyResult surveyResult) {
        ContentValues values = new ContentValues();
        values.put(SurveyResultContract.SurveyResultEntry.COLUMN_SURVEY_ID, surveyResult.getSurveyId());

        String id = String.valueOf(db.insert(SurveyResultContract.SurveyResultEntry.TABLE_NAME, null, values));
        surveyResult.setId(id);

        questionResponseDbHelper.saveAll(surveyResult.getQuestionResponses(), String.valueOf(surveyResult.getId()));
        return surveyResult;
    }

    public SurveyResult findOne() {
        int count = count();
        if(count == 0) {
            Log.w("SurveyResDbHelper.init", "There is no SurveyResult Entity in DB");
        } else if (count > 1) {
            Log.e("SurveyResDbHelper.init", "There is more than one SurveyResult Entity in DB");
            List<SurveyResult> surveyResults = findAll();
            return surveyResults.get(surveyResults.size() - 1);
        } else {
            return findAll().get(0);
        }
        return null;
    }

    private Integer count() {
        Integer result = null;
        Cursor cursor = db.rawQuery(SurveyResultContract.COUNT_ENTRIES, null);
        if (cursor.moveToFirst()) {
            result = cursor.getInt(0);
        }
        return result;
    }

    private List<SurveyResult> findAll() {
        List<SurveyResult> result = new ArrayList<>();
        Cursor cursor = db.rawQuery(SurveyResultContract.FIND, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String id = cursor.getString(cursor.getColumnIndexOrThrow(BaseColumns._ID));
                String surveyId = cursor.getString(cursor.getColumnIndexOrThrow(SurveyResultContract.SurveyResultEntry.COLUMN_SURVEY_ID));
                String uuid = cursor.getString(cursor.getColumnIndexOrThrow(SurveyResultContract.SurveyResultEntry.COLUMN_UUID));
                List<QuestionResponse> questionResponses = questionResponseDbHelper.findWhereSurveyResultId(id);
                List<Interval> intervals = intervalDbHelper.findWhereSurveyResultId(id);
                result.add(new SurveyResult(id, surveyId, uuid, intervals, questionResponses));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return result;
    }

    public void removeAll() {
        Cursor cursor = db.rawQuery(SurveyResultContract.SQL_DELETE_ENTRIES, null);
        cursor.moveToNext();
        cursor.close();
    }

}

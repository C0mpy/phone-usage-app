package dao.database.question_response;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import dao.database.DatabaseHelper;
import dao.database.question.QuestionDbHelper;
import model.Question;
import model.QuestionResponse;

public class QuestionResponseDbHelper {

    private static SQLiteDatabase db;
    private static Context context;
    private static DatabaseHelper databaseHelper;
    private static QuestionResponseDbHelper sInstance;

    private QuestionResponseDbHelper(Context _context) {
        context = _context;
        databaseHelper = DatabaseHelper.getInstance(context);
        db = databaseHelper.getWritableDatabase();
    }

    public static synchronized QuestionResponseDbHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new QuestionResponseDbHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    public void save(QuestionResponse questionResponse, String surveyResultId) {
        db.beginTransaction();

        ContentValues values = new ContentValues();
        values.put(QuestionResponseContract.QuestionResponseEntry.COLUMN_RESPONSE, questionResponse.getResponse());
        values.put(QuestionResponseContract.QuestionResponseEntry.COLUMN_QUESTION_ID, questionResponse.getQuestion().getForeignId());
        values.put(QuestionResponseContract.QuestionResponseEntry.COLUMN_SURVEY_RESULT_ID, surveyResultId);

        db.insert(QuestionResponseContract.QuestionResponseEntry.TABLE_NAME, null, values);

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void saveAll(List<QuestionResponse> questionResponseList, String surveyResultId) {
        for(QuestionResponse qr : questionResponseList) {
            save(qr, surveyResultId);
        }
    }

    public List<QuestionResponse> findWhereSurveyResultId(String surveyResultId) {
        List<QuestionResponse> result = new ArrayList<>();
        db.beginTransaction();

        Cursor cursor = db.rawQuery(QuestionResponseContract.FIND, new String[]{surveyResultId});
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String response = cursor.getString(cursor.getColumnIndexOrThrow(QuestionResponseContract.QuestionResponseEntry.COLUMN_RESPONSE));
                String questionId = cursor.getString(cursor.getColumnIndexOrThrow(QuestionResponseContract.QuestionResponseEntry.COLUMN_QUESTION_ID));

                QuestionDbHelper questionDbHelper = QuestionDbHelper.getInstance(context);
                Question question = questionDbHelper.find(questionId);

                result.add(new QuestionResponse(question, response));
                cursor.moveToNext();
            }
        }
        db.setTransactionSuccessful();
        db.endTransaction();

        return result;
    }

}

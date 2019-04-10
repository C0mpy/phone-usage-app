package dao.database.question_response;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import dao.database.DatabaseHelper;
import dao.database.question.QuestionDbHelper;
import mapper.QuestionResponseMapper;
import model.Question;
import model.QuestionResponse;

public class QuestionResponseDbHelper {

    private static SQLiteDatabase db;
    private static Context context;
    private static DatabaseHelper databaseHelper;
    private static QuestionDbHelper questionDbHelper;
    private static QuestionResponseDbHelper sInstance;

    private QuestionResponseDbHelper(Context _context) {
        context = _context;
        databaseHelper = DatabaseHelper.getInstance(context);
        questionDbHelper = QuestionDbHelper.getInstance(context);
        db = databaseHelper.getWritableDatabase();
        db.enableWriteAheadLogging();
    }

    public static synchronized QuestionResponseDbHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new QuestionResponseDbHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    public void saveAll(List<QuestionResponse> questionResponseList, String surveyResultId) {
        for(QuestionResponse qr : questionResponseList) {
            save(qr, surveyResultId);
        }
    }

    private void save(QuestionResponse questionResponse, String surveyResultId) {
        ContentValues contentValues = QuestionResponseMapper.mapToContentValues(questionResponse, surveyResultId);
        db.insert(QuestionResponseContract.QuestionResponseEntry.TABLE_NAME, null, contentValues);
    }

    public List<QuestionResponse> findWhereSurveyResultId(String surveyResultId) {
        List<QuestionResponse> result = new ArrayList<>();
        Cursor cursor = db.rawQuery(QuestionResponseContract.FIND, new String[]{surveyResultId});
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String response = cursor.getString(cursor.getColumnIndexOrThrow(QuestionResponseContract.QuestionResponseEntry.COLUMN_RESPONSE));
                String questionId = cursor.getString(cursor.getColumnIndexOrThrow(QuestionResponseContract.QuestionResponseEntry.COLUMN_QUESTION_ID));

                result.add(new QuestionResponse(questionId, response));
                cursor.moveToNext();
            }
            cursor.close();
        }
        return result;
    }

}

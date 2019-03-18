package dao.database.question;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import dao.database.DatabaseHelper;
import model.Question;

public class QuestionDbHelper {

    private static SQLiteDatabase db;
    private static Context context;
    private static DatabaseHelper databaseHelper;
    private static QuestionDbHelper sInstance;

    private QuestionDbHelper(Context _context) {
        context = _context;
        databaseHelper = DatabaseHelper.getInstance(context);
        db = databaseHelper.getWritableDatabase();
    }

    public static synchronized QuestionDbHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new QuestionDbHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    public void save(Question question, String surveyId) {
        db.beginTransaction();

        ContentValues values = new ContentValues();
        values.put(QuestionContract.QuestionEntry.COLUMN_FOREIGN_ID, question.getForeignId());
        values.put(QuestionContract.QuestionEntry.COLUMN_CONTENT, question.getContent());
        values.put(QuestionContract.QuestionEntry.COLUMN_SURVEY_ID, surveyId);

        db.insert(QuestionContract.QuestionEntry.TABLE_NAME, null, values);
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public Question find(String id) {
        Question question = null;

        db.beginTransaction();
        Cursor cursor = db.rawQuery(QuestionContract.FIND, new String[]{id});
        if (cursor.moveToFirst()) {
            String foreignId = cursor.getString(cursor.getColumnIndexOrThrow(QuestionContract.QuestionEntry.COLUMN_FOREIGN_ID));
            String content = cursor.getString(cursor.getColumnIndexOrThrow(QuestionContract.QuestionEntry.COLUMN_CONTENT));
            question = new Question(foreignId, content);
        }
        db.setTransactionSuccessful();
        db.endTransaction();

        return question;
    }

    public List<Question> findWhereSurveyId(String surveyId) {
        List<Question> result = new ArrayList<>();
        db.beginTransaction();
        Cursor cursor = db.rawQuery(QuestionContract.FIND_WHERE_SURVEY_ID, new String[]{surveyId});
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String foreignId = cursor.getString(cursor.getColumnIndexOrThrow(QuestionContract.QuestionEntry.COLUMN_FOREIGN_ID));
                String content = cursor.getString(cursor.getColumnIndexOrThrow(QuestionContract.QuestionEntry.COLUMN_CONTENT));
                result.add(new Question(foreignId, content));
                cursor.moveToNext();
            }
        }
        db.setTransactionSuccessful();
        db.endTransaction();

        return result;
    }

    public void saveAll(List<Question> questions, String surveyId) {
        for(Question q : questions) {
            save(q, surveyId);
        }
    }

    public void removeAll() {
        db.beginTransaction();

        Cursor cursor = db.rawQuery(QuestionContract.SQL_DELETE_ENTRIES, null);
        cursor.moveToNext();
        cursor.close();

        db.setTransactionSuccessful();
        db.endTransaction();
    }


}

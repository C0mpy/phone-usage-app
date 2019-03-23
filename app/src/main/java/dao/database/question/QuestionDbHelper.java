package dao.database.question;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import dao.database.DatabaseHelper;
import mapper.QuestionMapper;
import model.Question;

import java.util.ArrayList;
import java.util.List;

public class QuestionDbHelper {

    private static SQLiteDatabase db;
    private static Context context;
    private static DatabaseHelper databaseHelper;
    private static QuestionDbHelper sInstance;

    private QuestionDbHelper(Context context) {
        this.context = context;
        databaseHelper = DatabaseHelper.getInstance(context);
        db = databaseHelper.getWritableDatabase();
    }

    public static synchronized QuestionDbHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new QuestionDbHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    public Question find(String id) {
        Question question = null;
        Cursor cursor = db.rawQuery(QuestionContract.FIND, new String[] { id });
        if (cursor.moveToFirst()) {
            question = QuestionMapper.mapToModel(cursor);
            cursor.close();
        }
        return question;
    }

    public List<Question> findWhereSurveyId(String surveyId) {
        List<Question> result = new ArrayList<>();
        Cursor cursor = db.rawQuery(QuestionContract.FIND_WHERE_SURVEY_ID, new String[] { surveyId });
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                result.add(QuestionMapper.mapToModel(cursor));
                cursor.moveToNext();
            }
        }
        return result;
    }

    public void saveAll(List<Question> questions, String surveyId) {
        for (Question q : questions) {
            save(q, surveyId);
        }
    }

    private void save(Question question, String surveyId) {
        db.insert(QuestionContract.QuestionEntry.TABLE_NAME, null, QuestionMapper.mapToContentValues(question, surveyId));
    }

    public void removeAll() {
        Cursor cursor = db.rawQuery(QuestionContract.SQL_DELETE_ENTRIES, null);
        cursor.moveToNext();
        cursor.close();
    }

}

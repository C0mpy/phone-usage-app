package dao.database.survey;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import dao.database.DatabaseHelper;
import dao.database.question.QuestionDbHelper;
import model.Question;
import model.Survey;

public class SurveyDbHelper {

    private static SQLiteDatabase db;
    private static Context context;
    private static DatabaseHelper databaseHelper;
    private static SurveyDbHelper sInstance;

    private SurveyDbHelper(Context _context) {
        context = _context;
        databaseHelper = DatabaseHelper.getInstance(context);
        db = databaseHelper.getWritableDatabase();
    }

    public static synchronized SurveyDbHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new SurveyDbHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    public Integer count() {
        Integer result = null;
        db.beginTransaction();
        Cursor cursor = db.rawQuery(SurveyContract.COUNT_ENTRIES, null);
        if (cursor.moveToFirst()) {
            String str = cursor.getString(0);
            Integer inte = cursor.getInt(0);
            result = Integer.valueOf(str);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        return result;
    }

    public Survey save(Survey survey) {
        QuestionDbHelper questionDbHelper = QuestionDbHelper.getInstance(context);
        questionDbHelper.saveAll(survey.getQuestions(), survey.getForeignId());

        db.beginTransaction();

        ContentValues values = new ContentValues();
        values.put(SurveyContract.SurveyEntry.COLUMN_FOREIGN_ID, survey.getForeignId());
        values.put(SurveyContract.SurveyEntry.COLUMN_TITLE, survey.getTitle());
        values.put(SurveyContract.SurveyEntry.COLUMN_DESCRIPTION, survey.getDescription());

        db.insert(SurveyContract.SurveyEntry.TABLE_NAME, null, values);

        db.setTransactionSuccessful();
        db.endTransaction();
        return survey;
    }

    public void removeAll() {
        QuestionDbHelper questionDbHelper = QuestionDbHelper.getInstance(context);
        questionDbHelper.removeAll();

        db.beginTransaction();

        Cursor cursor = db.rawQuery(SurveyContract.SQL_DELETE_ENTRIES, null);
        cursor.moveToNext();
        cursor.close();

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public List<Survey> findAll() {
        List<Survey> result = new ArrayList<>();
        db.beginTransaction();
        Cursor cursor = db.rawQuery(SurveyContract.FIND, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String foreignId = cursor.getString(cursor.getColumnIndexOrThrow(SurveyContract.SurveyEntry.COLUMN_FOREIGN_ID));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(SurveyContract.SurveyEntry.COLUMN_TITLE));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(SurveyContract.SurveyEntry.COLUMN_DESCRIPTION));

                QuestionDbHelper questionDbHelper = QuestionDbHelper.getInstance(context);
                List<Question> questions = questionDbHelper.findWhereSurveyId(foreignId);

                result.add(new Survey(foreignId, title, description, questions));
                cursor.moveToNext();
            }
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        return result;
    }

    public Survey findOne() {
        int count = count();
        if(count == 0) {
            Log.w("SurveyDbHelper.init", "There is no Survey Entity in DB");
        } else if (count > 1) {
            Log.w("SurveyDbHelper.init", "There is more than one Survey Entity in DB");
        } else {
            return findAll().get(0);
        }
        return null;
    }

}

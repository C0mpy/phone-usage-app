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
import mapper.SurveyMapper;
import model.Interval;
import model.Question;
import model.Survey;

public class SurveyDbHelper {

    private static SQLiteDatabase db;
    private static Context context;
    private static DatabaseHelper databaseHelper;
    private static QuestionDbHelper questionDbHelper;
    private static SurveyDbHelper sInstance;

    private SurveyDbHelper(Context _context) {
        context = _context;
        databaseHelper = DatabaseHelper.getInstance(context);
        db = databaseHelper.getWritableDatabase();
        questionDbHelper = QuestionDbHelper.getInstance(context);
        db.enableWriteAheadLogging();
    }

    public static synchronized SurveyDbHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new SurveyDbHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    public Survey save(Survey survey) {
        questionDbHelper.saveAll(survey.getQuestions(), survey.getForeignId());
        db.insert(SurveyContract.SurveyEntry.TABLE_NAME, null, SurveyMapper.toContentValues(survey));
        return survey;
    }

    public List<Survey> save(List<Survey> surveys) {
        for (Survey survey: surveys) {
            save(survey);
        }
        return surveys;
    }

    public void removeAll() {
        questionDbHelper.removeAll();
        Cursor cursor = db.rawQuery(SurveyContract.SQL_DELETE_ENTRIES, null);
        cursor.moveToNext();
        cursor.close();
    }

    public Survey findOne() {
        int count = count();
        if (count == 0) {
            Log.w("SurveyDbHelper.init", "There is no Survey Entity in DB");
        } else if (count > 1) {
            Log.w("SurveyDbHelper.init", "There is more than one Survey Entity in DB");
        }
        return findAll().get(0);
    }

    private Integer count() {
        Integer result = null;
        Cursor cursor = db.rawQuery(SurveyContract.COUNT_ENTRIES, null);
        if (cursor.moveToFirst()) {
            result = cursor.getInt(0);
        }
        cursor.close();
        return result;
    }

    public List<Survey> findAll() {
        List<Survey> result = new ArrayList<>();
        Cursor cursor = db.rawQuery(SurveyContract.FIND, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Survey survey = SurveyMapper.mapToModel(cursor);
                survey.setQuestions(questionDbHelper.findWhereSurveyId(survey.getForeignId()));
                result.add(survey);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return result;
    }

}

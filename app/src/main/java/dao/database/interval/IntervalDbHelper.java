package dao.database.interval;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import dao.database.DatabaseHelper;
import dao.database.metadata.MetadataDbHelper;
import mapper.IntervalMapper;
import model.Interval;

import java.util.ArrayList;
import java.util.List;

public class IntervalDbHelper {

    private static SQLiteDatabase db;
    private static Context context;
    private static DatabaseHelper databaseHelper;
    private static IntervalDbHelper sInstance;

    private IntervalDbHelper(Context _context) {
        context = _context;
        databaseHelper = DatabaseHelper.getInstance(context);
        db = databaseHelper.getWritableDatabase();
        db.enableWriteAheadLogging();
    }

    public static synchronized IntervalDbHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new IntervalDbHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    public void save(Interval interval) {
        db.insert(IntervalContract.IntervalEntry.TABLE_NAME, null, IntervalMapper.toContentValues(interval));
    }

    public void removeAll() {
        Cursor cursor = db.rawQuery(IntervalContract.SQL_DELETE_ENTRIES, null);
        cursor.moveToNext();
        cursor.close();
    }

    public List<Interval> findAll() {
        List<Interval> result = new ArrayList<>();
        Cursor cursor = db.rawQuery(IntervalContract.SQL_GET_PHONE_USAGE, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                result.add(IntervalMapper.mapToModel(cursor));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return result;
    }

    public List<Interval> findWhereSurveyResultId(String surveyResultId) {
        List<Interval> result = new ArrayList<>();
        Cursor cursor = db.rawQuery(IntervalContract.FIND_WHERE_SURVEY_RESULT_ID, new String[] { surveyResultId });
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                result.add(IntervalMapper.mapToModel(cursor));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return result;
    }

}

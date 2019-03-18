package dao.database.metadata;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import dao.database.DatabaseHelper;
import model.Metadata;

public class MetadataDbHelper {

    private static SQLiteDatabase db;
    private static Context context;
    private static DatabaseHelper databaseHelper;
    private static MetadataDbHelper sInstance;

    private MetadataDbHelper(Context _context) {
        context = _context;
        databaseHelper = DatabaseHelper.getInstance(context);
        db = databaseHelper.getWritableDatabase();
    }

    public static synchronized MetadataDbHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new MetadataDbHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    public Metadata save(Metadata metadata) {
        db.beginTransaction();
        ContentValues values = new ContentValues();
        values.put(MetadataContract.MetadataEntry.COLUMN_UUID, metadata.getUuid());
        values.put(MetadataContract.MetadataEntry.COLUMN_LAST_SURVEY_TAKEN_TIME, metadata.getLastSurveyTakenTime());
        values.put(MetadataContract.MetadataEntry.COLUMN_SURVEY_FETCHED_FROM_SERVER, metadata.getSurveyFetchedFromServer());
        values.put(MetadataContract.MetadataEntry.COLUMN_SURVEY_RESULTS_SENT_TO_SERVER, metadata.getSurveyResultsSentToServer());
        values.put(MetadataContract.MetadataEntry.COLUMN_TIME_TO_NEXT_SURVEY_IN_HOURS, metadata.getTimeToNextSurveyInHours());

        if(count() != 0) {
            removeAll();
        }
        db.insert(MetadataContract.MetadataEntry.TABLE_NAME, null, values);

        db.setTransactionSuccessful();
        db.endTransaction();
        return metadata;
    }

    public Integer count() {
        Integer result = null;
        db.beginTransaction();
        Cursor cursor = db.rawQuery(MetadataContract.COUNT_ENTRIES, null);
        if (cursor.moveToFirst()) {
            String str = cursor.getString(0);
            result = Integer.valueOf(str);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        return result;
    }

    public void removeAll() {
        db.beginTransaction();

        Cursor cursor = db.rawQuery(MetadataContract.SQL_DELETE_ENTRIES, null);
        cursor.moveToNext();
        cursor.close();

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public Metadata findOne() {
        List<Metadata> metadataList = findAll();
        if(metadataList.size() == 0) {
            Log.w("SurveyDbHelper.init", "There is no Metadata Entity in DB");
        } else if (metadataList.size() > 1) {
            Log.w("SurveyDbHelper.init", "There is more than one Metadata Entity in DB");
        } else {
            return metadataList.get(0);
        }
        return null;
    }

    public List<Metadata> findAll() {
        List<Metadata> result = new ArrayList<>();
        db.beginTransaction();
        Cursor cursor = db.rawQuery(MetadataContract.FIND, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String uuid = cursor.getString(cursor.getColumnIndexOrThrow(MetadataContract.MetadataEntry.COLUMN_UUID));
                Long lastSurveyTakenTime = cursor.getLong(cursor.getColumnIndexOrThrow(MetadataContract.MetadataEntry.COLUMN_LAST_SURVEY_TAKEN_TIME));
                Boolean surveyFetchedFromServer = cursor.getInt(cursor.getColumnIndexOrThrow(MetadataContract.MetadataEntry.COLUMN_SURVEY_FETCHED_FROM_SERVER)) == 1 ? true : false;
                Boolean surveyResultsSentToServer = cursor.getInt(cursor.getColumnIndexOrThrow(MetadataContract.MetadataEntry.COLUMN_SURVEY_RESULTS_SENT_TO_SERVER)) == 1 ? true : false;
                Integer timeToNextSurveyInHours = cursor.getInt(cursor.getColumnIndexOrThrow(MetadataContract.MetadataEntry.COLUMN_TIME_TO_NEXT_SURVEY_IN_HOURS));
                result.add(new Metadata(uuid, lastSurveyTakenTime, surveyFetchedFromServer, surveyResultsSentToServer, timeToNextSurveyInHours));
                cursor.moveToNext();
            }
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        return result;
    }

}

package dao.database.metadata;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import dao.database.DatabaseHelper;
import mapper.MetadataMapper;
import model.Metadata;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MetadataDbHelper {

    private static SQLiteDatabase db;
    private static Context context;
    private static DatabaseHelper databaseHelper;
    private static MetadataDbHelper sInstance;

    private MetadataDbHelper(Context _context) {
        context = _context;
        databaseHelper = DatabaseHelper.getInstance(context);
        db = databaseHelper.getWritableDatabase();
        db.enableWriteAheadLogging();
    }

    public static synchronized MetadataDbHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new MetadataDbHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    public Metadata save(Metadata metadata) {
        if (count() != 0) {
            removeAll();
        }
        db.insert(MetadataContract.MetadataEntry.TABLE_NAME, null, MetadataMapper.toContentValues(metadata));
        return metadata;
    }

    private Integer count() {
        Integer result = null;
        Cursor cursor = db.rawQuery(MetadataContract.COUNT_ENTRIES, null);
        if (cursor.moveToFirst()) {
            result = cursor.getInt(0);
        }
        return result;
    }

    private void removeAll() {
        Cursor cursor = db.rawQuery(MetadataContract.SQL_DELETE_ENTRIES, null);
        cursor.moveToNext();
        cursor.close();
    }

    public Metadata findOne() {
        List<Metadata> metadataList = findAll();
        if (metadataList.size() == 0) {
            Log.w("SurveyDbHelper.init", "There is no Metadata Entity in DB");
            return save(new Metadata(UUID.randomUUID().toString(), false, null, false));
        } else if (metadataList.size() > 1) {
            Log.w("SurveyDbHelper.init", "There is more than one Metadata Entity in DB");
            return save(metadataList.get(0));
        }

        return metadataList.get(0);
    }

    private List<Metadata> findAll() {
        List<Metadata> result = new ArrayList<>();
        Cursor cursor = db.rawQuery(MetadataContract.FIND, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                result.add(MetadataMapper.toMetadata(cursor));
                cursor.moveToNext();
            }
        }
        return result;
    }

}

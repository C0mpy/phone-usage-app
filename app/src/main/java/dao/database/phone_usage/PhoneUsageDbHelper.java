package dao.database.phone_usage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import dao.database.metadata.MetadataContract;
import dao.database.DatabaseHelper;
import mapper.PhoneUsageMapper;
import model.PhoneUsage;

public class PhoneUsageDbHelper {

    private static SQLiteDatabase db;
    private static Context context;
    private static DatabaseHelper databaseHelper;
    private static PhoneUsageDbHelper sInstance;

    private PhoneUsageDbHelper(Context context) {
        this.context = context;
        databaseHelper = DatabaseHelper.getInstance(context);
        db = databaseHelper.getWritableDatabase();
    }

    public static synchronized PhoneUsageDbHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new PhoneUsageDbHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    public void save(PhoneUsage phoneUsage) {
        db.insert(PhoneUsageContract.PhoneUsageEntry.TABLE_NAME, null, PhoneUsageMapper.toContentValues(phoneUsage));
    }

    public void removeAll() {
        Cursor cursor = db.rawQuery(PhoneUsageContract.SQL_DELETE_ENTRIES, null);
        cursor.close();
    }

    public String sumPhoneUsage() {
        String sum = "";
        Cursor cursor = db.rawQuery(PhoneUsageContract.SQL_SUM_PHONE_USAGE, null);
        if (cursor.moveToFirst()) {
            sum = cursor.getString(0);
        }
        cursor.close();
        return sum;
    }

}

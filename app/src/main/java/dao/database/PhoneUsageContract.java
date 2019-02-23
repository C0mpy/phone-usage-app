package dao.database;

import android.provider.BaseColumns;

import model.PhoneUsage;

public class PhoneUsageContract {

    public static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + PhoneUsageEntry.TABLE_NAME + " (" +
                    PhoneUsageEntry._ID + " INTEGER PRIMARY KEY," +
                    PhoneUsageEntry.COLUMN_NAME_START_TIME + " TEXT," +
                    PhoneUsageEntry.COLUMN_NAME_END_TIME + " TEXT)";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + PhoneUsageEntry.TABLE_NAME;

    public static final String SQL_SUM_PHONE_USAGE =
            "SELECT SUM(" +
                    PhoneUsageEntry.COLUMN_NAME_END_TIME +
                    " - " +
                    PhoneUsageEntry.COLUMN_NAME_START_TIME +
                    ") FROM " +
                    PhoneUsageContract.PhoneUsageEntry.TABLE_NAME + ";";

    public static class PhoneUsageEntry implements BaseColumns {
        public static final String TABLE_NAME = "phone_usage";
        public static final String COLUMN_NAME_START_TIME = "start_time";
        public static final String COLUMN_NAME_END_TIME = "end_time";
    }
}

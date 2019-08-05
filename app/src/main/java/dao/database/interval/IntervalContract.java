package dao.database.interval;

import android.provider.BaseColumns;

public class IntervalContract {

    public static final String CREATE_TABLE =
          "CREATE TABLE " + IntervalEntry.TABLE_NAME + " (" +
          BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
          IntervalEntry.COLUMN_NAME_START_TIME + " TEXT," +
          IntervalEntry.COLUMN_NAME_END_TIME + " TEXT," +
          IntervalEntry.COLUMN_SURVEY_RESULT_ID + " TEXT)";


    public static final String SQL_DELETE_ENTRIES =
          "DELETE FROM " + IntervalEntry.TABLE_NAME;

    public static final String SQL_GET_PHONE_USAGE =
          "SELECT * FROM " + IntervalEntry.TABLE_NAME + ";";

    public static final String FIND_WHERE_SURVEY_RESULT_ID =
          "SELECT * FROM " + IntervalEntry.TABLE_NAME +
          " WHERE " + IntervalEntry.COLUMN_SURVEY_RESULT_ID + " = ?";

    public static class IntervalEntry implements BaseColumns {

        public static final String TABLE_NAME = "interval";

        public static final String COLUMN_NAME_START_TIME = "start_time";
        public static final String COLUMN_NAME_END_TIME = "end_time";
        public static final String COLUMN_SURVEY_RESULT_ID = "survey_result_id";
    }
}

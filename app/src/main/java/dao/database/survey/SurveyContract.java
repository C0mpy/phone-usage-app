package dao.database.survey;

import android.provider.BaseColumns;

public class SurveyContract {

    public static final String CREATE_TABLE =
            "CREATE TABLE " + SurveyEntry.TABLE_NAME + " (" +
                    BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    SurveyEntry.COLUMN_FOREIGN_ID + " TEXT," +
                    SurveyEntry.COLUMN_TITLE + " TEXT," +
                    SurveyEntry.COLUMN_DESCRIPTION + " TEXT," +
                    SurveyEntry.COLUMN_START_TIME + " INTEGER, " +
                    SurveyEntry.COLUMN_END_TIME + " INTEGER)";

    public static final String SQL_DELETE_ENTRIES =
            "DELETE FROM " + SurveyContract.SurveyEntry.TABLE_NAME;

    public static final String COUNT_ENTRIES =
            "SELECT COUNT(*) FROM " + SurveyEntry.TABLE_NAME;

    public static final String FIND =
            "SELECT * FROM " + SurveyEntry.TABLE_NAME;

    public static class SurveyEntry implements BaseColumns {

        public static final String TABLE_NAME = "survey";

        // id of the corresponding object on the server
        public static final String COLUMN_FOREIGN_ID = "foreign_id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_START_TIME = "start_time";
        public static final String COLUMN_END_TIME = "end_time";
    }
}

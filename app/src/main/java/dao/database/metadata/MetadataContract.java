package dao.database.metadata;

import android.provider.BaseColumns;

public class MetadataContract {

    public static final String CREATE_TABLE =
            "CREATE TABLE " + MetadataEntry.TABLE_NAME + " (" +
                    BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    MetadataEntry.COLUMN_UUID + " TEXT," +
                    MetadataEntry.COLUMN_LAST_SURVEY_TAKEN_TIME + " INTEGER," +
                    MetadataEntry.COLUMN_SURVEY_FETCHED_FROM_SERVER + " INTEGER," +
                    MetadataEntry.COLUMN_SURVEY_RESULTS_SENT_TO_SERVER + " INTEGER," +
                    MetadataEntry.COLUMN_TIME_TO_NEXT_SURVEY_IN_HOURS + " INTEGER)";

    public static final String COUNT_ENTRIES =
            "SELECT COUNT(*) FROM " + MetadataEntry.TABLE_NAME;

    public static final String SQL_DELETE_ENTRIES =
            "DELETE FROM " + MetadataEntry.TABLE_NAME;

    public static final String FIND =
            "SELECT * FROM " + MetadataEntry.TABLE_NAME;

    public static class MetadataEntry implements BaseColumns {

        public static final String TABLE_NAME = "metadata";

        public static final String COLUMN_UUID = "uuid";
        public static final String COLUMN_LAST_SURVEY_TAKEN_TIME = "last_survey_taken_time";
        public static final String COLUMN_SURVEY_FETCHED_FROM_SERVER = "survey_fetched_from_server";
        public static final String COLUMN_SURVEY_RESULTS_SENT_TO_SERVER = "survey_results_sent_to_server";
        public static final String COLUMN_TIME_TO_NEXT_SURVEY_IN_HOURS = "time_to_next_survey_in_hours";
    }

}

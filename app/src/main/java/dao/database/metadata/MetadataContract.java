package dao.database.metadata;

import android.provider.BaseColumns;

public class MetadataContract {

    public static final String CREATE_TABLE =
            "CREATE TABLE " + MetadataEntry.TABLE_NAME + " (" +
                    BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    MetadataEntry.COLUMN_UUID + " TEXT," +
                    MetadataEntry.COLUMN_EXPERIMENT_IS_RUNNING + " INTEGER," +
                    MetadataEntry.COLUMN_EXPERIMENT_END_TIME + " INTEGER," +
                    MetadataEntry.COLUMN_EXPERIMENT_START_TIME + " INTEGER," +
                    MetadataEntry.COLUMN_SURVEY_RESULTS_SENT_TO_SERVER + " INTEGER)";

    public static final String COUNT_ENTRIES =
            "SELECT COUNT(*) FROM " + MetadataEntry.TABLE_NAME;

    public static final String SQL_DELETE_ENTRIES =
            "DELETE FROM " + MetadataEntry.TABLE_NAME;

    public static final String FIND =
            "SELECT * FROM " + MetadataEntry.TABLE_NAME;

    public static class MetadataEntry implements BaseColumns {

        public static final String TABLE_NAME = "metadata";

        public static final String COLUMN_UUID = "uuid";
        public static final String COLUMN_EXPERIMENT_IS_RUNNING = "experiment_is_running";
        public static final String COLUMN_EXPERIMENT_END_TIME = "experiment_end_time";
        public static final String COLUMN_EXPERIMENT_START_TIME = "experiment_start_time";
        public static final String COLUMN_SURVEY_RESULTS_SENT_TO_SERVER = "survey_results_sent_to_server";
    }

}

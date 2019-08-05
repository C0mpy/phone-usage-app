package dao.database.survey_result;

import android.provider.BaseColumns;

public class SurveyResultContract {

    public static final String CREATE_TABLE =
            "CREATE TABLE " + SurveyResultEntry.TABLE_NAME + " (" +
                    BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    SurveyResultEntry.COLUMN_SURVEY_ID + " TEXT," +
                    SurveyResultEntry.COLUMN_UUID + " TEXT)";

    public static final String COUNT_ENTRIES =
            "SELECT COUNT(*) FROM " + SurveyResultEntry.TABLE_NAME;

    public static final String FIND =
            "SELECT * FROM " + SurveyResultEntry.TABLE_NAME;

    public static final String FIND_IDS =
          "SELECT " + BaseColumns._ID + " FROM " + SurveyResultEntry.TABLE_NAME;

    public static final String SQL_DELETE_ENTRIES =
          "DELETE FROM " + SurveyResultEntry.TABLE_NAME;

    public static class SurveyResultEntry implements BaseColumns {

        public static final String TABLE_NAME = "survey_result";

        // id of the corresponding object on the server
        public static final String COLUMN_SURVEY_ID = "survey_id";
        public static final String COLUMN_UUID = "uuid";

    }
}

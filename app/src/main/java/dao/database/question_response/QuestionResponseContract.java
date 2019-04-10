package dao.database.question_response;

import android.provider.BaseColumns;

public class QuestionResponseContract {

    public static final String CREATE_TABLE =
            "CREATE TABLE " + QuestionResponseEntry.TABLE_NAME + " (" +
                    BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    QuestionResponseEntry.COLUMN_QUESTION_ID + " TEXT, " +
                    QuestionResponseEntry.COLUMN_SURVEY_RESULT_ID + " TEXT, " +
                    QuestionResponseEntry.COLUMN_RESPONSE + " TEXT)";

    public static final String FIND =
            "SELECT * FROM " + QuestionResponseEntry.TABLE_NAME +
            " WHERE survey_result_id = ?";

    public static class QuestionResponseEntry implements BaseColumns {

        public static final String TABLE_NAME = "question_response";

        public static final String COLUMN_QUESTION_ID = "question_id";
        public static final String COLUMN_SURVEY_RESULT_ID = "survey_result_id";
        public static final String COLUMN_RESPONSE = "response";
    }
}

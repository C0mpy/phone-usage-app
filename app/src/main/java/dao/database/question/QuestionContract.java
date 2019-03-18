package dao.database.question;

import android.provider.BaseColumns;

public class QuestionContract {

    public static final String CREATE_TABLE =
            "CREATE TABLE " + QuestionEntry.TABLE_NAME + " (" +
                    BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    QuestionEntry.COLUMN_FOREIGN_ID + " TEXT," +
                    QuestionEntry.COLUMN_SURVEY_ID + " TEXT," +
                    QuestionEntry.COLUMN_CONTENT + " TEXT)";

    public static final String FIND =
            "SELECT * FROM " + QuestionEntry.TABLE_NAME +
            " WHERE " + QuestionEntry.COLUMN_FOREIGN_ID + " = ?";

    public static final String FIND_WHERE_SURVEY_ID =
            "SELECT * FROM " + QuestionEntry.TABLE_NAME +
                    " WHERE " + QuestionEntry.COLUMN_SURVEY_ID + " = ?";

    public static final String SQL_DELETE_ENTRIES =
            "DELETE FROM " + QuestionEntry.TABLE_NAME;


    public static class QuestionEntry implements BaseColumns {
        public static final String TABLE_NAME = "question";

        // id of the corresponding object on the server
        public static final String COLUMN_FOREIGN_ID = "foreign_id";
        public static final String COLUMN_CONTENT = "content";
        public static final String COLUMN_SURVEY_ID = "survey_id";
    }
}

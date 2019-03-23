package mapper;

import android.content.ContentValues;
import android.database.Cursor;
import dao.database.question.QuestionContract;
import dto.QuestionDTO;
import model.Question;

public class QuestionMapper {

    public static Question mapToModel(QuestionDTO dto) {
        Question model = new Question();
        model.setForeignId(dto.getId());
        model.setContent(dto.getContent());
        return model;
    }

    public static QuestionDTO mapToDto(Question model) {
        QuestionDTO dto = new QuestionDTO();
        dto.setId(model.getForeignId());
        dto.setContent(model.getContent());
        return dto;
    }

    public static ContentValues mapToContentValues(Question question, String surveyId) {
        ContentValues values = new ContentValues();
        values.put(QuestionContract.QuestionEntry.COLUMN_FOREIGN_ID, question.getForeignId());
        values.put(QuestionContract.QuestionEntry.COLUMN_CONTENT, question.getContent());
        values.put(QuestionContract.QuestionEntry.COLUMN_SURVEY_ID, surveyId);
        return values;
    }

    public static Question mapToModel(Cursor cursor) {
        String foreignId = cursor.getString(cursor.getColumnIndexOrThrow(QuestionContract.QuestionEntry.COLUMN_FOREIGN_ID));
        String content = cursor.getString(cursor.getColumnIndexOrThrow(QuestionContract.QuestionEntry.COLUMN_CONTENT));
        return new Question(foreignId, content);
    }
}

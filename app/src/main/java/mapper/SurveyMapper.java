package mapper;

import android.content.ContentValues;
import dao.database.survey.SurveyContract;
import dto.SurveyDTO;
import model.Survey;

public class SurveyMapper {

    public static ContentValues toContentValues(Survey survey) {
        ContentValues values = new ContentValues();
        values.put(SurveyContract.SurveyEntry.COLUMN_FOREIGN_ID, survey.getForeignId());
        values.put(SurveyContract.SurveyEntry.COLUMN_TITLE, survey.getTitle());
        values.put(SurveyContract.SurveyEntry.COLUMN_DESCRIPTION, survey.getDescription());
        return values;
    }

    public static Survey toModel(SurveyDTO dto) {
        Survey survey = new Survey();
        survey.setForeignId(dto.getId());
        survey.setTitle(dto.getTitle());
        survey.setDescription(dto.getDescription());
        survey.setQuestions(QuestionMapper.mapToModelList(dto.getQuestions()));
        return survey;
    }
}

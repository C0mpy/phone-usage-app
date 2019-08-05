package mapper;

import android.content.ContentValues;
import dao.database.survey.SurveyContract;
import dto.SurveyDTO;
import model.Survey;

import java.util.ArrayList;
import java.util.List;

public class SurveyMapper {

    public static ContentValues toContentValues(Survey survey) {
        ContentValues values = new ContentValues();
        values.put(SurveyContract.SurveyEntry.COLUMN_FOREIGN_ID, survey.getForeignId());
        values.put(SurveyContract.SurveyEntry.COLUMN_TITLE, survey.getTitle());
        values.put(SurveyContract.SurveyEntry.COLUMN_DESCRIPTION, survey.getDescription());
        values.put(SurveyContract.SurveyEntry.COLUMN_START_TIME, survey.getStartTime());
        values.put(SurveyContract.SurveyEntry.COLUMN_END_TIME, survey.getEndTime());
        return values;
    }

    public static Survey toModel(SurveyDTO dto) {
        Survey survey = new Survey();
        survey.setForeignId(dto.getId());
        survey.setTitle(dto.getTitle());
        survey.setDescription(dto.getDescription());
        survey.setStartTime(dto.getStartTime());
        survey.setEndTime(dto.getEndTime());
        survey.setQuestions(QuestionMapper.mapToModelList(dto.getQuestions()));
        return survey;
    }

    public static List<Survey> toModelList(List<SurveyDTO> dtos) {
        List<Survey> modelList = new ArrayList<>();
        for (SurveyDTO dto : dtos) {
            modelList.add(toModel(dto));
        }
        return modelList;
    }
}

package mapper;

import dto.SurveyResultDTO;
import model.SurveyResult;

public class SurveyResultMapper {

    public static SurveyResult mapToModel(SurveyResultDTO dto) {
        SurveyResult model = new SurveyResult();
        model.setId(dto.getId());
        model.setSurveyId(dto.getSurvey_id());
        model.setQuestionResponses(QuestionResponseMapper.mapToModelList(dto.getQuestion_responses()));
        return model;
    }

    public static SurveyResultDTO mapToDTO(SurveyResult model) {
        SurveyResultDTO dto = new SurveyResultDTO();
        dto.setSurvey_id(model.getSurveyId());
        dto.setQuestion_responses(QuestionResponseMapper.mapToDtoList(model.getQuestionResponses()));
        return dto;
    }
}

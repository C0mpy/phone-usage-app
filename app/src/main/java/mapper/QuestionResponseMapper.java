package mapper;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import dao.database.question_response.QuestionResponseContract;
import dto.QuestionResponseDTO;
import model.QuestionResponse;

public class QuestionResponseMapper {

    public static List<QuestionResponse> mapToModelList(List<QuestionResponseDTO> dtoList) {
        List<QuestionResponse> modelList = new ArrayList<>();
        for (QuestionResponseDTO dto : dtoList) {
            modelList.add(map(dto));
        }
        return modelList;
    }

    private static QuestionResponse map(QuestionResponseDTO dto) {
        QuestionResponse model = new QuestionResponse();
        model.setResponse(dto.getResponse());
        model.setQuestionId(dto.getQuestion_id());
        return model;
    }

    public static List<QuestionResponseDTO> mapToDtoList(List<QuestionResponse> modelList) {
        List<QuestionResponseDTO> dtoList = new ArrayList<>();
        for (QuestionResponse model : modelList) {
            dtoList.add(map(model));
        }
        return dtoList;
    }

    private static QuestionResponseDTO map(QuestionResponse model) {
        QuestionResponseDTO dto = new QuestionResponseDTO();
        dto.setResponse(model.getResponse());
        dto.setQuestion_id(model.getQuestionId());
        return dto;
    }

    public static ContentValues mapToContentValues(QuestionResponse questionResponse, String surveyResultId) {
        ContentValues values = new ContentValues();
        values.put(QuestionResponseContract.QuestionResponseEntry.COLUMN_RESPONSE, questionResponse.getResponse());
        values.put(QuestionResponseContract.QuestionResponseEntry.COLUMN_QUESTION_ID, questionResponse.getQuestionId());
        values.put(QuestionResponseContract.QuestionResponseEntry.COLUMN_SURVEY_RESULT_ID, surveyResultId);
        return values;
    }
}

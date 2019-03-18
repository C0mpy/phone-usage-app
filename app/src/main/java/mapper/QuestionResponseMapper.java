package mapper;

import java.util.ArrayList;
import java.util.List;

import dto.QuestionResponseDTO;
import model.Question;
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
        model.setQuestion(QuestionMapper.mapToModel(dto.getQuestion()));
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
        dto.setQuestion(QuestionMapper.mapToDto(model.getQuestion()));
        return dto;
    }
}

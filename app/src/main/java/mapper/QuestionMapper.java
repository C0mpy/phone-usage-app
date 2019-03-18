package mapper;

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

}

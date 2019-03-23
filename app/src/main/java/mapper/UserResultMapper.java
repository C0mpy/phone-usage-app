package mapper;

import dto.UserResultDTO;
import model.Metadata;

public class UserResultMapper {

    public static UserResultDTO mapToDto(String surveyResultId, Metadata metadata, String phoneUsage) {
        return new UserResultDTO(
              surveyResultId, metadata.getUuid(), phoneUsage, String.valueOf(metadata.getLastSurveyTakenTime()),
              String.valueOf(System.currentTimeMillis()));
    }

}

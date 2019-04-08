package mapper;

import java.util.List;

import dto.PhoneUsageDTO;
import dto.UserResultDTO;
import model.Metadata;
import model.PhoneUsage;

public class UserResultMapper {

    public static UserResultDTO mapToDto(String surveyResultId, Metadata metadata, List<PhoneUsage> phoneUsage) {
        List<PhoneUsageDTO> phoneUsageDTOList = PhoneUsageMapper.mapToDtoList(phoneUsage);

        return new UserResultDTO(
              surveyResultId, metadata.getUuid(), phoneUsageDTOList, String.valueOf(metadata.getLastSurveyTakenTime()),
              String.valueOf(System.currentTimeMillis()));
    }

}

package mapper;

import dto.PhoneUsageDTO;
import dto.UserResultDTO;
import model.Metadata;
import model.PhoneUsage;
import util.Util;

import java.util.List;

public class UserResultMapper {

    public static UserResultDTO mapToDto(String surveyResultId, Metadata metadata, List<PhoneUsage> phoneUsage) {
        List<PhoneUsageDTO> phoneUsageDTOList = PhoneUsageMapper.mapToDtoList(phoneUsage);

        return new UserResultDTO(surveyResultId, metadata.getUuid(), phoneUsageDTOList, String.valueOf(
              metadata.getLastSurveyTakenTime() - Util.hoursToMillis(metadata.getTimeToNextSurveyInHours())),
                                 String.valueOf(metadata.getLastSurveyTakenTime()));
    }

}

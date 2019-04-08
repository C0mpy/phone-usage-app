package dto;

import java.util.List;

public class UserResultDTO {

    private String survey_result_id;
    private String user_uuid;
    private List<PhoneUsageDTO> phone_usage;
    private String period_start;
    private String period_end;

    public UserResultDTO(
            String survey_result_id, String user_uuid, List<PhoneUsageDTO> phone_usage, String period_start, String period_end) {
        this.survey_result_id = survey_result_id;
        this.user_uuid = user_uuid;
        this.phone_usage = phone_usage;
        this.period_start = period_start;
        this.period_end = period_end;
    }

    public String getSurvey_result_id() {
        return survey_result_id;
    }

    public void setSurvey_result_id(String survey_result_id) {
        this.survey_result_id = survey_result_id;
    }

    public String getUser_uuid() {
        return user_uuid;
    }

    public void setUser_uuid(String user_uuid) {
        this.user_uuid = user_uuid;
    }

    public List<PhoneUsageDTO> getPhone_usage() {
        return phone_usage;
    }

    public void setPhone_usage(List<PhoneUsageDTO> phone_usage) {
        this.phone_usage = phone_usage;
    }

    public String getPeriod_start() {
        return period_start;
    }

    public void setPeriod_start(String period_start) {
        this.period_start = period_start;
    }

    public String getPeriod_end() {
        return period_end;
    }

    public void setPeriod_end(String period_end) {
        this.period_end = period_end;
    }
}

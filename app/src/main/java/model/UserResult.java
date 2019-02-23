package model;

public class UserResult {

    private String survey_result_id;
    private String user_uuid;
    private String time_spent_on_phone;
    private String period_start;
    private String period_end;

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

    public String getTime_spent_on_phone() {
        return time_spent_on_phone;
    }

    public void setTime_spent_on_phone(String time_spent_on_phone) {
        this.time_spent_on_phone = time_spent_on_phone;
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

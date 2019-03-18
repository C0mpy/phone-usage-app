package model;

public class Metadata {

    private String uuid;
    private Long lastSurveyTakenTime;
    private Boolean surveyFetchedFromServer;
    private Boolean surveyResultsSentToServer;
    private Integer timeToNextSurveyInHours;

    public Metadata(String uuid, Long lastSurveyTakenTime, Boolean surveyFetchedFromServer, Boolean surveyResultsSentToServer, Integer timeToNextSurveyInHours) {
        this.uuid = uuid;
        this.lastSurveyTakenTime = lastSurveyTakenTime;
        this.surveyFetchedFromServer = surveyFetchedFromServer;
        this.surveyResultsSentToServer = surveyResultsSentToServer;
        this.timeToNextSurveyInHours = timeToNextSurveyInHours;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Long getLastSurveyTakenTime() {
        return lastSurveyTakenTime;
    }

    public void setLastSurveyTakenTime(Long lastSurveyTakenTime) {
        this.lastSurveyTakenTime = lastSurveyTakenTime;
    }

    public Boolean getSurveyFetchedFromServer() {
        return surveyFetchedFromServer;
    }

    public void setSurveyFetchedFromServer(Boolean surveyFetchedFromServer) {
        this.surveyFetchedFromServer = surveyFetchedFromServer;
    }

    public Integer getTimeToNextSurveyInHours() {
        return timeToNextSurveyInHours;
    }

    public void setTimeToNextSurveyInHours(Integer timeToNextSurveyInHours) {
        this.timeToNextSurveyInHours = timeToNextSurveyInHours;
    }

    public Boolean getSurveyResultsSentToServer() {
        return surveyResultsSentToServer;
    }

    public void setSurveyResultsSentToServer(Boolean surveyResultsSentToServer) {
        this.surveyResultsSentToServer = surveyResultsSentToServer;
    }
}

package model;

import java.util.Date;

public class Metadata {

    private String uuid;

    private Date lastSurveyTakenTime;
    private Boolean surveyFetchedFromServer;
    private Integer timeToNextSurveyInHours;

    private Boolean surveyResultsSentToServer;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Date getLastSurveyTakenTime() {
        return lastSurveyTakenTime;
    }

    public void setLastSurveyTakenTime(Date lastSurveyTakenTime) {
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

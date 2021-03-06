package model;

public class Metadata {

    private String uuid;
    private Boolean experimentIsRunning;
    private Long experimentStartTime;
    private Long experimentEndTime;
    private Boolean surveyResultsSentToServer;

    public Metadata(
          String uuid, Boolean experimentIsRunning, Long experimentStartTime, Long experimentEndTime, Boolean surveyResultsSentToServer) {
        this.uuid = uuid;
        this.experimentIsRunning = experimentIsRunning;
        this.experimentStartTime = experimentStartTime;
        this.experimentEndTime = experimentEndTime;
        this.surveyResultsSentToServer = surveyResultsSentToServer;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Boolean getExperimentIsRunning() {
        return experimentIsRunning;
    }

    public void setExperimentIsRunning(Boolean experimentIsRunning) {
        this.experimentIsRunning = experimentIsRunning;
    }

    public Long getExperimentStartTime() {
        return experimentStartTime;
    }

    public void setExperimentStartTime(Long experimentStartTime) {
        this.experimentStartTime = experimentStartTime;
    }

    public Long getExperimentEndTime() {
        return experimentEndTime;
    }

    public void setExperimentEndTime(Long experimentEndTime) {
        this.experimentEndTime = experimentEndTime;
    }

    public Boolean getSurveyResultsSentToServer() {
        return surveyResultsSentToServer;
    }

    public void setSurveyResultsSentToServer(Boolean surveyResultsSentToServer) {
        this.surveyResultsSentToServer = surveyResultsSentToServer;
    }
}

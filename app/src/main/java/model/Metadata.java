package model;

import java.util.Date;

public class Metadata {

    private String uuid;
    private Date lastSurveyTakenTime;
    private Boolean resultsSentToServer;

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

    public Boolean getResultsSentToServer() {
        return resultsSentToServer;
    }

    public void setResultsSentToServer(Boolean resultsSentToServer) {
        this.resultsSentToServer = resultsSentToServer;
    }
}

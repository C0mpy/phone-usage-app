package model;

import java.io.Serializable;

public class Interval implements Serializable {

    private long startTime;
    private long endTime;
    private String surveyId;

    public Interval() {
    }

    public Interval(long startTime, long endTime, String surveyId) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.surveyId = surveyId;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(String surveyId) {
        this.surveyId = surveyId;
    }
}
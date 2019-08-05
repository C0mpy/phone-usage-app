package model;

import java.io.Serializable;

public class Interval implements Serializable {

    private long startTime;
    private long endTime;
    private String surveyResultId;

    public Interval() {
    }

    public Interval(long startTime, long endTime, String surveyResultId) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.surveyResultId = surveyResultId;
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

    public String getSurveyResultId() {
        return surveyResultId;
    }

    public void setSurveyResultId(String surveyResultId) {
        this.surveyResultId = surveyResultId;
    }
}
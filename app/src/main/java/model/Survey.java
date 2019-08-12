package model;

import java.io.Serializable;
import java.util.List;

public class Survey implements Serializable {

    // SurveyId on server
    private String foreignId;
    private String title;
    private String description;
    private long startTime;
    private long endTime;
    private List<Question> questions;

    public Survey() {}

    public Survey(String foreignId, String title, String description, long startTime, long endTime) {
        this.foreignId = foreignId;
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Survey(String foreignId, String title, String description, long startTime, long endTime, List<Question> questions) {
        this.foreignId = foreignId;
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.questions = questions;
    }

    public String getForeignId() {
        return foreignId;
    }

    public void setForeignId(String foreignId) {
        this.foreignId = foreignId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}

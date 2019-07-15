package model;

import java.io.Serializable;
import java.util.List;

public class Survey implements Serializable {

    // SurveyId on server
    private String foreignId;
    private String title;
    private String description;
    private List<Question> questions;

    public Survey() {}

    public Survey(String foreignId, String title, String description, List<Question> questions) {
        this.foreignId = foreignId;
        this.title = title;
        this.description = description;
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

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}

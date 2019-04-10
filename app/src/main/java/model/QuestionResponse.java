package model;

public class QuestionResponse {

    private String foreignId;
    private String questionId;
    private String response;

    public QuestionResponse() {}

    public QuestionResponse(String questionId, String response) {
        this.questionId = questionId;
        this.response = response;
    }

    public QuestionResponse(String foreignId, String questionId, String response) {
        this.foreignId = foreignId;
        this.questionId = questionId;
        this.response = response;
    }

    public String getForeignId() {
        return foreignId;
    }

    public void setForeignId(String foreignId) {
        this.foreignId = foreignId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String question) {
        this.questionId = question;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
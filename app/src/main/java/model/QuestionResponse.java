package model;

public class QuestionResponse {

    private String foreignId;
    private Question question;
    private String response;

    public QuestionResponse() {}

    public QuestionResponse(Question question, String response) {
        this.question = question;
        this.response = response;
    }

    public QuestionResponse(String foreignId, Question question, String response) {
        this.foreignId = foreignId;
        this.question = question;
        this.response = response;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
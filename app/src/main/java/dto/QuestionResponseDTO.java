package dto;

public class QuestionResponseDTO {

    private String id;
    private QuestionDTO question;
    private String response;

    public QuestionResponseDTO() {}

    public QuestionResponseDTO(String id, QuestionDTO question, String response) {
        this.id = id;
        this.question = question;
        this.response = response;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public QuestionDTO getQuestion() {
        return question;
    }

    public void setQuestion(QuestionDTO question) {
        this.question = question;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}

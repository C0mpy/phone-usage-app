package dto;

public class QuestionResponseDTO {

    private String id;
    private String question_id;
    private String response;

    public QuestionResponseDTO() {}

    public QuestionResponseDTO(String id, String questionId, String response) {
        this.id = id;
        this.question_id = questionId;
        this.response = response;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(String questionId) {
        this.question_id = questionId;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}

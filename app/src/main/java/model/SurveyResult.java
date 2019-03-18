package model;

import java.util.ArrayList;
import java.util.List;

public class SurveyResult {

    private String id;
    private String surveyId;
    private List<QuestionResponse> questionResponses = new ArrayList<>();

    public SurveyResult() {}

    public SurveyResult(String id, String surveyId, List<QuestionResponse> questionResponses) {
        this.id = id;
        this.surveyId = surveyId;
        this.questionResponses = questionResponses;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(String surveyId) {
        this.surveyId = surveyId;
    }

    public List<QuestionResponse> getQuestionResponses() {
        return questionResponses;
    }

    public void setQuestionResponses(List<QuestionResponse> questionResponses) {
        this.questionResponses = questionResponses;
    }
}

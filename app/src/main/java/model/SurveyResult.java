package model;

import java.util.ArrayList;
import java.util.List;

public class SurveyResult {

    String surveyId;
    List<QuestionResponse> questionResponses = new ArrayList<>();

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

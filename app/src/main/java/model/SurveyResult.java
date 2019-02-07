package model;

import java.util.ArrayList;
import java.util.List;

public class SurveyResult {

    String surveyId;
    String userUUID;
    List<QuestionResponse> questionResults = new ArrayList<>();

    public String getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(String surveyId) {
        this.surveyId = surveyId;
    }

    public String getUserUUID() {
        return userUUID;
    }

    public void setUserUUID(String userUUID) {
        this.userUUID = userUUID;
    }

    public List<QuestionResponse> getQuestionResults() {
        return questionResults;
    }

    public void setQuestionResults(List<QuestionResponse> questionResults) {
        this.questionResults = questionResults;
    }
}

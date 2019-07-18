package model;

import java.util.ArrayList;
import java.util.List;

public class SurveyResult {

    private String id;
    private String surveyId;
    private String uuid;
    private List<Interval> intervals;
    private List<QuestionResponse> questionResponses = new ArrayList<>();

    public SurveyResult() {
    }

    public SurveyResult(
          String id, String surveyId, String uuid, List<Interval> intervals, List<QuestionResponse> questionResponses) {
        this.id = id;
        this.surveyId = surveyId;
        this.uuid = uuid;
        this.intervals = intervals;
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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public List<Interval> getIntervals() {
        return intervals;
    }

    public void setIntervals(List<Interval> intervals) {
        this.intervals = intervals;
    }

    public List<QuestionResponse> getQuestionResponses() {
        return questionResponses;
    }

    public void setQuestionResponses(List<QuestionResponse> questionResponses) {
        this.questionResponses = questionResponses;
    }
}

package dto;

import java.util.ArrayList;
import java.util.List;

public class SurveyResultDTO {

    private String id;
    private String survey_id;
    private String uuid;
    private List<IntervalDTO> intervals = new ArrayList<>();
    private List<QuestionResponseDTO> question_responses = new ArrayList<>();

    public SurveyResultDTO() {
    }

    public SurveyResultDTO(
          String id, String survey_id, String uuid, List<IntervalDTO> intervals, List<QuestionResponseDTO> question_responses) {
        this.id = id;
        this.survey_id = survey_id;
        this.uuid = uuid;
        this.intervals = intervals;
        this.question_responses = question_responses;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSurvey_id() {
        return survey_id;
    }

    public void setSurvey_id(String survey_id) {
        this.survey_id = survey_id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public List<IntervalDTO> getIntervals() {
        return intervals;
    }

    public void setIntervals(List<IntervalDTO> intervals) {
        this.intervals = intervals;
    }

    public List<QuestionResponseDTO> getQuestion_responses() {
        return question_responses;
    }

    public void setQuestion_responses(List<QuestionResponseDTO> question_responses) {
        this.question_responses = question_responses;
    }
}

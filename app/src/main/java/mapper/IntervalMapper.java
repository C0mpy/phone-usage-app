package mapper;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import dao.database.interval.IntervalContract;
import dto.PhoneUsageDTO;
import model.Interval;

public class IntervalMapper {

    public static ContentValues toContentValues(Interval interval) {
        ContentValues values = new ContentValues();
        values.put(IntervalContract.IntervalEntry.COLUMN_NAME_START_TIME, interval.getStartTime());
        values.put(IntervalContract.IntervalEntry.COLUMN_NAME_END_TIME, interval.getEndTime());
        values.put(IntervalContract.IntervalEntry.COLUMN_SURVEY_RESULT_ID, interval.getSurveyId());
        return values;
    }

    public static Interval mapToModel(Cursor cursor) {
        long startTime = cursor.getLong(cursor.getColumnIndexOrThrow(IntervalContract.IntervalEntry.COLUMN_NAME_START_TIME));
        long endTime = cursor.getLong(cursor.getColumnIndexOrThrow(IntervalContract.IntervalEntry.COLUMN_NAME_END_TIME));
        String surveyResultId = cursor.getString(cursor.getColumnIndexOrThrow(IntervalContract.IntervalEntry.COLUMN_SURVEY_RESULT_ID));
        return new Interval(startTime, endTime, surveyResultId);
    }

    public static List<PhoneUsageDTO> mapToDtoList(List<Interval> modelList) {
        List<PhoneUsageDTO> result = new ArrayList<>();
        for (Interval interval : modelList) {
            result.add(mapToDto(interval));
        }
        return result;
    }

    public static PhoneUsageDTO mapToDto(Interval model) {
        return new PhoneUsageDTO(String.valueOf(model.getStartTime()),
                String.valueOf(model.getEndTime()));

    }
}

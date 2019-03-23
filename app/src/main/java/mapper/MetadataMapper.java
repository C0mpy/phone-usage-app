package mapper;

import android.content.ContentValues;
import android.database.Cursor;

import dao.database.metadata.MetadataContract;
import model.Metadata;

public class MetadataMapper {

    public static final Metadata toMetadata(Cursor cursor) {
        String uuid = cursor.getString(cursor.getColumnIndexOrThrow(MetadataContract.MetadataEntry.COLUMN_UUID));
        Long lastSurveyTakenTime =
              cursor.getLong(cursor.getColumnIndexOrThrow(MetadataContract.MetadataEntry.COLUMN_LAST_SURVEY_TAKEN_TIME));
        Boolean surveyFetchedFromServer =
              cursor.getInt(cursor.getColumnIndexOrThrow(MetadataContract.MetadataEntry.COLUMN_SURVEY_FETCHED_FROM_SERVER)) == 1
                    ? true
                    : false;
        Boolean surveyResultsSentToServer =
              cursor.getInt(cursor.getColumnIndexOrThrow(MetadataContract.MetadataEntry.COLUMN_SURVEY_RESULTS_SENT_TO_SERVER)) == 1
                    ? true
                    : false;
        Integer timeToNextSurveyInHours =
              cursor.getInt(cursor.getColumnIndexOrThrow(MetadataContract.MetadataEntry.COLUMN_TIME_TO_NEXT_SURVEY_IN_HOURS));
        return new Metadata(uuid, lastSurveyTakenTime, surveyFetchedFromServer, surveyResultsSentToServer, timeToNextSurveyInHours);
    }

    public static ContentValues toContentValues(Metadata metadata) {
        ContentValues values = new ContentValues();
        values.put(MetadataContract.MetadataEntry.COLUMN_UUID, metadata.getUuid());
        values.put(MetadataContract.MetadataEntry.COLUMN_LAST_SURVEY_TAKEN_TIME, metadata.getLastSurveyTakenTime());
        values.put(MetadataContract.MetadataEntry.COLUMN_SURVEY_FETCHED_FROM_SERVER, metadata.getSurveyFetchedFromServer());
        values.put(MetadataContract.MetadataEntry.COLUMN_SURVEY_RESULTS_SENT_TO_SERVER, metadata.getSurveyResultsSentToServer());
        values.put(MetadataContract.MetadataEntry.COLUMN_TIME_TO_NEXT_SURVEY_IN_HOURS, metadata.getTimeToNextSurveyInHours());
        return values;
    }
}

package mapper;

import android.content.ContentValues;
import android.database.Cursor;

import dao.database.metadata.MetadataContract;
import model.Metadata;

public class MetadataMapper {

    public static final Metadata toMetadata(Cursor cursor) {
        String uuid = cursor.getString(cursor.getColumnIndexOrThrow(MetadataContract.MetadataEntry.COLUMN_UUID));
        Boolean experimentIsRunning =
              cursor.getInt(cursor.getColumnIndexOrThrow(MetadataContract.MetadataEntry.COLUMN_EXPERIMENT_IS_RUNNING)) == 1
                    ? true
                    : false;
        Long experimentStartTime =
              cursor.getLong(cursor.getColumnIndexOrThrow(MetadataContract.MetadataEntry.COLUMN_EXPERIMENT_START_TIME));
        Long experimentEndTime =
              cursor.getLong(cursor.getColumnIndexOrThrow(MetadataContract.MetadataEntry.COLUMN_EXPERIMENT_END_TIME));
        Boolean surveyResultsSentToServer =
              cursor.getInt(cursor.getColumnIndexOrThrow(MetadataContract.MetadataEntry.COLUMN_SURVEY_RESULTS_SENT_TO_SERVER)) == 1
                    ? true
                    : false;
        return new Metadata(uuid, experimentIsRunning, experimentStartTime, experimentEndTime, surveyResultsSentToServer);
    }

    public static ContentValues toContentValues(Metadata metadata) {
        ContentValues values = new ContentValues();
        values.put(MetadataContract.MetadataEntry.COLUMN_UUID, metadata.getUuid());
        values.put(MetadataContract.MetadataEntry.COLUMN_EXPERIMENT_IS_RUNNING, metadata.getExperimentIsRunning());
        values.put(MetadataContract.MetadataEntry.COLUMN_EXPERIMENT_START_TIME, metadata.getExperimentStartTime());
        values.put(MetadataContract.MetadataEntry.COLUMN_EXPERIMENT_END_TIME, metadata.getExperimentEndTime());
        values.put(MetadataContract.MetadataEntry.COLUMN_SURVEY_RESULTS_SENT_TO_SERVER, metadata.getSurveyResultsSentToServer());
        return values;
    }
}

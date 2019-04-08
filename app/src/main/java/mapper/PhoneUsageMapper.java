package mapper;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

import dao.database.metadata.MetadataContract;
import dao.database.phone_usage.PhoneUsageContract;
import dto.PhoneUsageDTO;
import model.Metadata;
import model.PhoneUsage;

public class PhoneUsageMapper {

    public static ContentValues toContentValues(PhoneUsage phoneUsage) {
        ContentValues values = new ContentValues();
        values.put(PhoneUsageContract.PhoneUsageEntry.COLUMN_NAME_START_TIME, phoneUsage.getStartTime());
        values.put(PhoneUsageContract.PhoneUsageEntry.COLUMN_NAME_END_TIME, phoneUsage.getEndTime());
        return values;
    }

    public static PhoneUsage mapToModel(Cursor cursor) {
        long startTime = cursor.getLong(cursor.getColumnIndexOrThrow(PhoneUsageContract.PhoneUsageEntry.COLUMN_NAME_START_TIME));
        long endTime = cursor.getLong(cursor.getColumnIndexOrThrow(PhoneUsageContract.PhoneUsageEntry.COLUMN_NAME_END_TIME));
        return new PhoneUsage(startTime, endTime);
    }

    public static List<PhoneUsageDTO> mapToDtoList(List<PhoneUsage> modelList) {
        List<PhoneUsageDTO> result = new ArrayList<>();
        for (PhoneUsage phoneUsage : modelList) {
            result.add(mapToDto(phoneUsage));
        }
        return result;
    }

    public static PhoneUsageDTO mapToDto(PhoneUsage model) {
        return new PhoneUsageDTO(String.valueOf(model.getStartTime()),
                String.valueOf(model.getEndTime()));

    }
}

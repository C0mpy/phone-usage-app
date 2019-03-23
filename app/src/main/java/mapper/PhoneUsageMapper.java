package mapper;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.ContactsContract;
import dao.database.metadata.MetadataContract;
import dao.database.phone_usage.PhoneUsageContract;
import model.Metadata;
import model.PhoneUsage;

public class PhoneUsageMapper {

    public static ContentValues toContentValues(PhoneUsage phoneUsage) {
        ContentValues values = new ContentValues();
        values.put(PhoneUsageContract.PhoneUsageEntry.COLUMN_NAME_START_TIME, phoneUsage.getStartTime());
        values.put(PhoneUsageContract.PhoneUsageEntry.COLUMN_NAME_END_TIME, phoneUsage.getEndTime());
        return values;
    }
}

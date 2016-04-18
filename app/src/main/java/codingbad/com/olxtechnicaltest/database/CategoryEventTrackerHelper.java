package codingbad.com.olxtechnicaltest.database;

import com.google.inject.Inject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import codingbad.com.olxtechnicaltest.database.CategoryEventTrackerContract.CategoryEvent;

/**
 * Created by Ayelen Chavez on 4/18/16.
 *
 * Helper for my simple Data Base management
 */
public class CategoryEventTrackerHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "CategoryEvents.db";

    private static final String TEXT_TYPE = " TEXT";

    private static final String NUMBER_TYPE = " INTEGER";

    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + CategoryEvent.TABLE_NAME + " (" +
                    CategoryEvent._ID + " INTEGER PRIMARY KEY," +
                    CategoryEvent.COLUMN_NAME_CATEGORY_ID + TEXT_TYPE + COMMA_SEP +
                    CategoryEvent.COLUMN_NAME_INITIAL + TEXT_TYPE + COMMA_SEP +
                    CategoryEvent.COLUMN_NAME_SEARCH_CRITERIA + TEXT_TYPE + COMMA_SEP +
                    CategoryEvent.COLUMN_NAME_CLICKS + NUMBER_TYPE +
                    " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + CategoryEvent.TABLE_NAME;

    @Inject
    public CategoryEventTrackerHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}


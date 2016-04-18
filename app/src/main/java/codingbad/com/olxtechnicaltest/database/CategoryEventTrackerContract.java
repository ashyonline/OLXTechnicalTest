package codingbad.com.olxtechnicaltest.database;

import android.provider.BaseColumns;

/**
 * Created by Ayelen Chavez on 4/18/16.
 *
 * Basic Database contract
 */
public final class CategoryEventTrackerContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public CategoryEventTrackerContract() {
    }

    /* Inner class that defines the table contents */
    public static abstract class CategoryEvent implements BaseColumns {

        public static final String TABLE_NAME = "category_clicked";

        public static final String COLUMN_NAME_CATEGORY_ID = "categoryid";

        public static final String COLUMN_NAME_CLICKS = "clickscount";

        public static final String COLUMN_NAME_SEARCH_CRITERIA = "searchcriteria";

        public static final String COLUMN_NAME_INITIAL = "initial";
    }
}

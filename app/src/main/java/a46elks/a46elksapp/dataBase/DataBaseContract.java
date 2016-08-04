package a46elks.a46elksapp.dataBase;

import android.provider.BaseColumns;

/**
 * Created by Alexander on 2016-08-01.
 */
public final class DataBaseContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public DataBaseContract() {}

    /* Inner class that defines the table contents */
    public static abstract class ContactsEntry implements BaseColumns {
        public static final String TABLE_NAME = "contacts";
        public static final String COLUMN_NAME_NULLABLE = null;
        public static final String COLUMN_NAME_CONTACT_ID = "contact_id";
        public static final String COLUMN_NAME_FIRST_NAME = "first_name";
        public static final String COLUMN_NAME_LAST_NAME = "last_name";
        public static final String COLUMN_NAME_MOBILE_NUMBER = "mobile_number";
        public static final String COLUMN_NAME_CONTAINING_GROUPS = "containing_groups";
        //...
    }

    public static abstract class GroupsEntry implements BaseColumns {
        public static final String TABLE_NAME = "groups";
        public static final String COLUMN_NAME_NULLABLE = null;
        public static final String COLUMN_NAME_GROUP_ID = "group_id";
        public static final String COLUMN_NAME_GROUP_NAME = "group_name";

        //...
    }

    public static abstract class ContainingEntry implements BaseColumns {
        public static final String TABLE_NAME = "containing";
        public static final String COLUMN_NAME_GROUP_ID = "group_id";
        public static final String COLUMN_NAME_CONTACT_ID = "contact_id";
        public static final String COLUMN_NAME_NULLABLE = null;
        //...
    }


    public static abstract class HistoryEntry implements BaseColumns {
        public static final String TABLE_NAME = "history";
        public static final String COLUMN_NAME_CONTACT_ID = "contact_id";
        public static final String COLUMN_NAME_FIRST_NAME = "first_name";
        public static final String COLUMN_NAME_LAST_NAME = "last_name";
        public static final String COLUMN_NAME_MOBILE_NUMBER = "mobile_number";
        public static final String COLUMN_NAME_CONTAINING_GROUPS = "containing_groups";
        //...
    }

    public static abstract class MessagesEntry implements BaseColumns {
        public static final String TABLE_NAME = "messages";
        public static final String COLUMN_NAME_CONTACT_ID = "contact_id";
        public static final String COLUMN_NAME_FIRST_NAME = "first_name";
        public static final String COLUMN_NAME_LAST_NAME = "last_name";
        public static final String COLUMN_NAME_MOBILE_NUMBER = "mobile_number";
        public static final String COLUMN_NAME_CONTAINING_GROUPS = "containing_groups";
        //...
    }
}

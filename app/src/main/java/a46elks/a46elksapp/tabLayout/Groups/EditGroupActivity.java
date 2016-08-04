package a46elks.a46elksapp.tabLayout.Groups;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import a46elks.a46elksapp.R;
import a46elks.a46elksapp.SessionManager;
import a46elks.a46elksapp.tabLayout.Adapters.CustomListViewAdapter;
import a46elks.a46elksapp.tabLayout.Contacts.Contact;
import a46elks.a46elksapp.tabLayout.FragmentCommunicator;

public class EditGroupActivity extends AppCompatActivity {

    private CustomListViewAdapter contactsListViewAdapter;
    private ListView contactsListView;
    private ArrayList<Contact> contactsList;
    private ArrayList<Contact> chosenContactsList;
    private SessionManager sessionManager;
    private EditText groupName;
    private static final String LISTVIEWADAPTER_ACTION = "CONTACTS";
    private FragmentCommunicator fragmentCommunicator;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_group);
        Bundle extras = getIntent().getExtras();
        groupName = (EditText) findViewById(R.id.editText_edit_group_name);
        groupName.setText(extras.getString("GROUP_NAME"));
        contactsListView = (ListView) findViewById(R.id.listView_edit_group);
        sessionManager = new SessionManager(getApplicationContext());
        contactsList = sessionManager.getContacts();
        chosenContactsList = extras.getParcelableArrayList("CONTAINING_CONTACTS");
        contactsListViewAdapter = new CustomListViewAdapter(this, LISTVIEWADAPTER_ACTION, contactsList);
        contactsListView.setAdapter(contactsListViewAdapter);

        for (Contact contact : this.chosenContactsList){
            View child = getViewByPosition(contactsList.indexOf(contact), contactsListView);
            child.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            TextView firstName = (TextView) child.findViewById(R.id.text_contact_first_name);
            TextView lastName = (TextView) child.findViewById(R.id.text_contact_last_name);
            TextView mobileNumber = (TextView) child.findViewById(R.id.text_contact_mobile_number);
            firstName.setTextColor(getResources().getColor(R.color.colorTextPrimary));
            lastName.setTextColor(getResources().getColor(R.color.colorTextPrimary));
            mobileNumber.setTextColor(getResources().getColor(R.color.colorTextPrimary));
            child.refreshDrawableState();
        }

        findViewById(R.id.action_edit_contact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                attemptCreate();

            }
        });

        findViewById(R.id.action_edit_contact_go_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(Activity.RESULT_CANCELED, intent);
                finish();
            }
        });


        contactsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView groupName = (TextView) view.findViewById(R.id.text_group_name);
                TextView groupSize = (TextView) view.findViewById(R.id.text_group_size);

                if (!chosenContactsList.contains(contactsList.get(position))){

                    view.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    groupName.setTextColor(getResources().getColor(R.color.colorTextPrimary));
                    groupSize.setTextColor(getResources().getColor(R.color.colorTextPrimary));
                    chosenContactsList.add(contactsList.get(position));
                } else {
                    groupName.setTextColor(getResources().getColor(R.color.colorTextSecondary));
                    groupSize.setTextColor(getResources().getColor(R.color.colorTextSecondary));
                    view.setBackgroundColor(getResources().getColor(R.color.colorTextPrimary));
                    chosenContactsList.remove(contactsList.get(position));
                }
            }
        });

    }

    private void attemptCreate() {

        // Reset errors.

        groupName.setError(null);
        //lastName.setError(null);

        // Store values at the time of the login attempt.
        String group = groupName.getText().toString();

        boolean cancel = false;
        View focusView = null;


        // Check for a valid email address.
        if (TextUtils.isEmpty(group)) {
            groupName.setError(getString(R.string.error_field_required));
            focusView = groupName;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            Intent intent = new Intent();

            intent.putExtra("GROUP_NAME", groupName.getText().toString());
            intent.putParcelableArrayListExtra("CONTAINING_CONTACTS", chosenContactsList);

            setResult(RESULT_OK, intent);
            finish();

        }
    }

    public View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(Activity.RESULT_CANCELED, intent);
        finish();
    }
}

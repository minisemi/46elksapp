package a46elks.a46elksapp.tabLayout.Groups;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

import a46elks.a46elksapp.R;
import a46elks.a46elksapp.SessionManager;
import a46elks.a46elksapp.tabLayout.Adapters.CustomListViewAdapter;
import a46elks.a46elksapp.tabLayout.Contacts.Contact;
import a46elks.a46elksapp.tabLayout.FragmentCommunicator;

public class CreateGroupActivity extends AppCompatActivity {

    private CustomListViewAdapter contactsListViewAdapter;
    private ListView contactsListView;
    private List<Contact> contactsList;
    private SessionManager sessionManager;
    private EditText groupName, senderName;
    private static final String LISTVIEWADAPTER_ACTION = "CONTACTS";
    private FragmentCommunicator fragmentCommunicator;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
        groupName = (EditText) findViewById(R.id.editText_group_name);
        senderName = (EditText) findViewById(R.id.editText_sender_name);
        contactsListView = (ListView) findViewById(R.id.listView_add_contacts);
        sessionManager = new SessionManager(getApplicationContext());
        contactsList = sessionManager.getContacts();
        contactsListViewAdapter = new CustomListViewAdapter(this, LISTVIEWADAPTER_ACTION, contactsList);
        contactsListView.setAdapter(contactsListViewAdapter);

        findViewById(R.id.action_create_contact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                attemptCreate();

            }
        });

        findViewById(R.id.action_create_contact_go_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(Activity.RESULT_CANCELED, intent);
                finish();
            }
        });

    }

    private void attemptCreate() {

        // Reset errors.

        groupName.setError(null);
        //lastName.setError(null);
        senderName.setError(null);

        // Store values at the time of the login attempt.
        String group = groupName.getText().toString();
        String sender = senderName.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(sender)) {
            senderName.setError(getString(R.string.error_field_required));
            focusView = senderName;
            cancel = true;
        }

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
            /*intent.putExtra("CONTACT_FIRST_NAME", firstName.getText().toString());
            intent.putExtra("CONTACT_LAST_NAME", lastName.getText().toString());
            intent.putExtra("CONTACT_MOBILE_NUMBER", mobileNumber.getText().toString());*/
            setResult(RESULT_OK, intent);
            finish();

        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(Activity.RESULT_CANCELED, intent);
        finish();
    }
}

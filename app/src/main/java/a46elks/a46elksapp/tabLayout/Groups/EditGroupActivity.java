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
    private ArrayList<Contact> contactsList, addedContacts, removedContacts, chosenContactsList;
    private SessionManager sessionManager;
    private EditText groupName;
    private static final String LISTVIEWADAPTER_ACTION = "CONTACTS";
    private FragmentCommunicator fragmentCommunicator;
    private Bundle extras;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_group);
        extras = getIntent().getExtras();
        groupName = (EditText) findViewById(R.id.editText_edit_group_name);
        groupName.setText(extras.getString("GROUP_NAME"));
        contactsListView = (ListView) findViewById(R.id.listView_edit_group);
        sessionManager = new SessionManager();
        addedContacts = new ArrayList<>();
        removedContacts = new ArrayList<>();
        contactsList = sessionManager.getContacts();
        chosenContactsList = extras.getParcelableArrayList("CONTAINING_CONTACTS");
        contactsListViewAdapter = new CustomListViewAdapter(this, LISTVIEWADAPTER_ACTION, contactsList);
        contactsListView.setAdapter(contactsListViewAdapter);

        for (Contact contact : this.chosenContactsList){
            View child = getViewByPosition(contactsList.indexOf(contact)+1, contactsListView);
            child.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            TextView firstName = (TextView) child.findViewById(R.id.text_contact_first_name);
            TextView lastName = (TextView) child.findViewById(R.id.text_contact_last_name);
            TextView mobileNumber = (TextView) child.findViewById(R.id.text_contact_mobile_number);
            firstName.setTextColor(getResources().getColor(R.color.colorTextPrimary));
            lastName.setTextColor(getResources().getColor(R.color.colorTextPrimary));
            mobileNumber.setTextColor(getResources().getColor(R.color.colorTextPrimary));
            child.refreshDrawableState();
        }

        findViewById(R.id.action_edit_group).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                attemptCreate();

            }
        });

        findViewById(R.id.action_delete_group).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("POSITION", extras.getInt("POSITION"));
                //intent.putExtra("CONTACT_OLD_INFO", jsonObject.toString());
                setResult(RESULT_FIRST_USER, intent);
                finish();
            }
        });

        findViewById(R.id.action_edit_group_go_back).setOnClickListener(new View.OnClickListener() {
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

                TextView firstName = (TextView) view.findViewById(R.id.text_contact_first_name);
                TextView lastName = (TextView) view.findViewById(R.id.text_contact_last_name);
                TextView mobileNumber = (TextView) view.findViewById(R.id.text_contact_mobile_number);
                Contact contact = contactsList.get(position);

                if (!chosenContactsList.contains(contact)){

                    if (!removedContacts.contains(contact)){
                        addedContacts.add(contact);
                    } else {
                        removedContacts.remove(contact);
                    }

                    view.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    firstName.setTextColor(getResources().getColor(R.color.colorTextPrimary));
                    lastName.setTextColor(getResources().getColor(R.color.colorTextPrimary));
                    mobileNumber.setTextColor(getResources().getColor(R.color.colorTextPrimary));
                    chosenContactsList.add(contact);
                } else {

                    if (!addedContacts.contains(contact)){
                        removedContacts.add(contact);
                    }else {
                        addedContacts.remove(contact);
                    }
                    firstName.setTextColor(getResources().getColor(R.color.colorTextSecondary));
                    lastName.setTextColor(getResources().getColor(R.color.colorTextSecondary));
                    mobileNumber.setTextColor(getResources().getColor(R.color.colorTextSecondary));
                    view.setBackgroundColor(getResources().getColor(R.color.colorTextPrimary));
                    chosenContactsList.remove(contact);
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

            intent.putExtra("GROUP_NAME", groupName.getText());
            intent.putParcelableArrayListExtra("ADDED_CONTACTS", addedContacts);
            intent.putParcelableArrayListExtra("REMOVED_CONTACTS", removedContacts);

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

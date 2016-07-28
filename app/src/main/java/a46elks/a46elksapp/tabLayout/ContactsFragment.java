package a46elks.a46elksapp.tabLayout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import a46elks.a46elksapp.R;
import a46elks.a46elksapp.SessionManager;

/**
 * Created by Alexander on 2016-06-29.
 */
public class ContactsFragment extends Fragment{

    public static final String ARG_PAGE = "ARG_PAGE";
    public static final String LISTVIEWADAPTER_ACTION = "CONTACTS";
    private final int REQUEST_CODE_ADD_CONTACT = 0, REQUEST_CODE_EDIT_CONTACT = 1;
    private List<String> contactsList;
    private SessionManager sessionManager;
    private Context context = getContext();
    private JsonParser jsonParser;
    private CustomListViewAdapter contactsListViewAdapter;

    private int mPage;

    public static ContactsFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        ContactsFragment fragment = new ContactsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static ContactsFragment newInstance() {
        ContactsFragment fragment = new ContactsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        jsonParser = new JsonParser();
        sessionManager = new SessionManager(getActivity().getApplicationContext());
       // mPage = getArguments().getInt(ARG_PAGE);

    }

    //OnActivityCreated?
    //setRetainInstance(true)?

    /*@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String[] values = new String[] { "Jonas", "Fredrik" };

        CustomListViewAdapter adapter = new CustomListViewAdapter(getActivity(), values);
        setListAdapter(adapter);
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_contacts, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.action_add_contact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, AddContactActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD_CONTACT);
            }
        });



        ListView contactsListView = (ListView) view.findViewById(R.id.listView_contacts);
        contactsList = new ArrayList<>();
        contactsListViewAdapter = new CustomListViewAdapter(getContext(), LISTVIEWADAPTER_ACTION, contactsList);
        contactsListView.setAdapter(contactsListViewAdapter);

         contactsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                 TextView firstName = (TextView) view.findViewById(R.id.text_contact_first_name);
                 TextView lastName = (TextView) view.findViewById(R.id.text_contact_last_name);
                 TextView mobileNumber = (TextView) view.findViewById(R.id.text_contact_mobile_number);
                 Intent intent = new Intent();
                 intent.putExtra("CONTACT_FIRST_NAME", firstName.getText().toString());
                 intent.putExtra("CONTACT_LAST_NAME", lastName.getText().toString());
                 intent.putExtra("CONTACT_MOBILE_NUMBER", mobileNumber.getText().toString());
                 startActivityForResult(intent, REQUEST_CODE_EDIT_CONTACT);
             }
         });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle extras = data.getExtras();


        switch (requestCode){

            // Add contact
            case REQUEST_CODE_ADD_CONTACT:
                if (resultCode != Activity.RESULT_CANCELED){
                    String firstName = extras.getString("CONTACT_FIRST_NAME");
                    String lastName = extras.getString("CONTACT_LAST_NAME");
                    String mobileNumber = extras.getString("CONTACT_MOBILE_NUMBER");
                    JsonObject contact = new JsonObject();
                    contact.addProperty("CONTACT_FIRST_NAME", firstName);
                    contact.addProperty("CONTACT_LAST_NAME", lastName);
                    contact.addProperty("CONTACT_MOBILE_NUMBER", mobileNumber);
                    contact.addProperty("CONTACT_CONTAINING_GROUPS", "");
                    sessionManager.createContact(contact.toString());

                    contactsList.add(contact.toString());
                    Collections.sort(contactsList);
                    contactsListViewAdapter.notifyDataSetChanged();
                }
                break;

            case REQUEST_CODE_EDIT_CONTACT:

                switch (resultCode){

                    // Edit contact
                    case Activity.RESULT_OK:
                        String firstName = extras.getString("CONTACT_FIRST_NAME");
                        String lastName = extras.getString("CONTACT_LAST_NAME");
                        String mobileNumber = extras.getString("CONTACT_MOBILE_NUMBER");
                        String containingGroups = extras.getString("CONTACT_CONTAINING_GROUPS");
                        String oldContactInfo = extras.getString("CONTACT_OLD_INFO");
                        JsonObject contact = new JsonObject();
                        contact.addProperty("CONTACT_FIRST_NAME", firstName);
                        contact.addProperty("CONTACT_LAST_NAME", lastName);
                        contact.addProperty("CONTACT_MOBILE_NUMBER", mobileNumber);
                        contact.addProperty("CONTACT_CONTAINING_GROUPS", containingGroups);
                        sessionManager.updateContact(oldContactInfo, contact.toString());

                        contactsList.add(contact.toString());
                        Collections.sort(contactsList);
                        contactsListViewAdapter.notifyDataSetChanged();

                        break;

                    // Delete contact
                    case Activity.RESULT_FIRST_USER:
                        sessionManager.deleteContact(extras.getString("CONTACT_OLD_INFO"));
                        contactsList.remove(extras.getString("CONTACT_OLD_INFO"));
                        contactsListViewAdapter.notifyDataSetChanged();

                        break;
                }

                break;
        }
    }

}



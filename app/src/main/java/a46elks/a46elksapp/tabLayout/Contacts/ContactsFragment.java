package a46elks.a46elksapp.tabLayout.Contacts;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

import a46elks.a46elksapp.R;
import a46elks.a46elksapp.SessionManager;
import a46elks.a46elksapp.tabLayout.Adapters.CustomListViewAdapter;
import a46elks.a46elksapp.tabLayout.FragmentCommunicator;

/**
 * Created by Alexander on 2016-06-29.
 */
public class ContactsFragment extends Fragment{

    public static final String ARG_PAGE = "ARG_PAGE";
    private static final String LISTVIEWADAPTER_ACTION = "CONTACTS";
    private final int REQUEST_CODE_ADD_CONTACT = 0, REQUEST_CODE_EDIT_CONTACT = 1;
    private ArrayList<Contact> contactsList, contactsToSend;
    private SessionManager sessionManager;
    private Context context;
    private JsonParser jsonParser;
    private CustomListViewAdapter contactsListViewAdapter;
    private ListView contactsListView;
    private FragmentCommunicator fragmentCommunicator;
    private TextView contactsText;
    private Boolean chooseMode;
    private Button addContact;

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
        chooseMode = false;
        contactsToSend = new ArrayList<>();


        // mPage = getArguments().getInt(ARG_PAGE);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity();
        sessionManager = fragmentCommunicator.getSessionManager();
        contactsList = sessionManager.getContacts();

        // SET ATOMICINTEGER FOR CONTACTS DEPENDING ON HIGHEST ID IN LIST
        //Collections.sort(contactsList);
        //ontactsListViewAdapter.notifyDataSetChanged();
        //contactsList = new ArrayList<>();
        contactsListViewAdapter = new CustomListViewAdapter(context, LISTVIEWADAPTER_ACTION, contactsList);
        contactsListView.setAdapter(contactsListViewAdapter);
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
        contactsListView = (ListView) view.findViewById(R.id.listView_contacts);


        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        contactsText = (TextView) view.findViewById(R.id.text_contacts);
        addContact = (Button) view.findViewById(R.id.action_add_contact);

        addContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (chooseMode){
                    chooseMode = false;
                    contactsText.setText(getResources().getString(R.string.text_contacts));
                    addContact.setText(getResources().getString(R.string.action_add_contact));
                    contactsListViewAdapter.notifyDataSetChanged();

                    fragmentCommunicator.finishChooseContacts(contactsToSend);

                } else {

                Intent intent = new Intent(context, CreateContactActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD_CONTACT);
                }
            }
        });


         contactsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                 TextView firstName = (TextView) view.findViewById(R.id.text_contact_first_name);
                 TextView lastName = (TextView) view.findViewById(R.id.text_contact_last_name);
                 TextView mobileNumber = (TextView) view.findViewById(R.id.text_contact_mobile_number);

                 if (chooseMode){

                     if (!contactsToSend.contains(contactsList.get(position))){

                     view.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                         firstName.setTextColor(getResources().getColor(R.color.colorTextPrimary));
                         lastName.setTextColor(getResources().getColor(R.color.colorTextPrimary));
                         mobileNumber.setTextColor(getResources().getColor(R.color.colorTextPrimary));
                     contactsToSend.add(contactsList.get(position));
                     } else {
                         firstName.setTextColor(getResources().getColor(R.color.colorTextSecondary));
                         lastName.setTextColor(getResources().getColor(R.color.colorTextSecondary));
                         mobileNumber.setTextColor(getResources().getColor(R.color.colorTextSecondary));
                         view.setBackgroundColor(getResources().getColor(R.color.colorTextPrimary));
                         contactsToSend.remove(contactsList.get(position));
                     }

                 } else {

                 Intent intent = new Intent(context, EditContactActivity.class);
                 intent.putExtra("CONTACT_FIRST_NAME", firstName.getText().toString());
                 intent.putExtra("CONTACT_LAST_NAME", lastName.getText().toString());
                 intent.putExtra("CONTACT_MOBILE_NUMBER", mobileNumber.getText().toString());
                 // REMOVE TEXTVIEW CONTACT_ID FROM XML
                 intent.putExtra("POSITION", position);
                 startActivityForResult(intent, REQUEST_CODE_EDIT_CONTACT);
                 }
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
                    Contact contact = new Contact(firstName, lastName, mobileNumber);
                    sessionManager.createContact(contact);
                    //JsonObject contact = new JsonObject();
                    /*contact.addProperty("CONTACT_FIRST_NAME", firstName);
                    contact.addProperty("CONTACT_LAST_NAME", lastName);
                    contact.addProperty("CONTACT_MOBILE_NUMBER", mobileNumber);
                    contact.add("CONTACT_CONTAINING_GROUPS", new JsonArray());*/
                    //sessionManager.createContact(contact.toString());

                    contactsList.add(contact);
                    //Collections.sort(contactsList);
                    contactsListViewAdapter.notifyDataSetChanged();
                }
                break;

            case REQUEST_CODE_EDIT_CONTACT:
                int position;

                switch (resultCode){

                    // Edit contact
                    case Activity.RESULT_OK:
                        position = extras.getInt("POSITION");
                        //might need to use "set" instead of "get"
                        contactsList.get(position).setFirstName(extras.getString("CONTACT_FIRST_NAME"));
                        contactsList.get(position).setLastName(extras.getString("CONTACT_LAST_NAME"));
                        contactsList.get(position).setMobileNumber(extras.getString("CONTACT_MOBILE_NUMBER"));
                        sessionManager.updateContact(contactsList.get(position));
                        contactsListViewAdapter.notifyDataSetChanged();
                        //String containingGroups = extras.getString("CONTACT_CONTAINING_GROUPS");
                        //String oldContactInfo = extras.getString("CONTACT_OLD_INFO");
                        /*JsonObject contact = new JsonObject();
                        contact.addProperty("CONTACT_FIRST_NAME", firstName);
                        contact.addProperty("CONTACT_LAST_NAME", lastName);
                        contact.addProperty("CONTACT_MOBILE_NUMBER", mobileNumber);
                        contact.addProperty("CONTACT_CONTAINING_GROUPS", containingGroups);*/

                        /*sessionManager.updateContact(contact);

                        contactsList.add(contact);
                        Collections.sort(contactsList);
                        contactsListViewAdapter.notifyDataSetChanged();*/

                        break;

                    // Delete contact
                    case Activity.RESULT_FIRST_USER:
                        position = extras.getInt("POSITION");
                        sessionManager.deleteContact(contactsList.get(position).getContactID());
                        contactsList.remove(position);
                        contactsListViewAdapter.notifyDataSetChanged();

                        break;
                }

                break;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            fragmentCommunicator = (FragmentCommunicator) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnEventStartedListener");
        }
    }

    public void chooseContacts (ArrayList<Contact> contactsToSend){

        this.contactsToSend = contactsToSend;
        contactsText.setText(getResources().getString(R.string.text_choose_contacts));
        addContact.setText(getResources().getString(R.string.action_choose_contacts));
        chooseMode = true;
        for (Contact contact : this.contactsToSend){
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

        //contactsListView.performItemClick(contactsListView, 0, 1);


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

}



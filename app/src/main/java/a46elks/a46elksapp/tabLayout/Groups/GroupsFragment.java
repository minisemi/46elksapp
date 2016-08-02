package a46elks.a46elksapp.tabLayout.Groups;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import a46elks.a46elksapp.R;
import a46elks.a46elksapp.SessionManager;
import a46elks.a46elksapp.tabLayout.Adapters.CustomListViewAdapter;
import a46elks.a46elksapp.tabLayout.Contacts.Contact;
import a46elks.a46elksapp.tabLayout.Contacts.CreateContactActivity;
import a46elks.a46elksapp.tabLayout.Contacts.EditContactActivity;
import a46elks.a46elksapp.tabLayout.FragmentCommunicator;

/**
 * Created by Alexander on 2016-06-29.
 */
public class GroupsFragment extends android.support.v4.app.Fragment{

    public static final String ARG_PAGE = "ARG_PAGE";
    public Context context;
    private SessionManager sessionManager;
    private FragmentCommunicator fragmentCommunicator;
    private CustomListViewAdapter groupsListViewAdapter;
    private ListView groupsListView;
    private List<Contact> groupsList;
    private static final String LISTVIEWADAPTER_ACTION = "GROUPS";


    private final int REQUEST_CODE_ADD_GROUP = 0, REQUEST_CODE_EDIT_GROUP = 1;

    public static GroupsFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        GroupsFragment fragment = new GroupsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // mPage = getArguments().getInt(ARG_PAGE);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity();
        sessionManager = fragmentCommunicator.getSessionManager();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_groups, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        view.findViewById(R.id.action_add_group).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, CreateGroupActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD_GROUP);
            }
        });

        groupsListView = (ListView) view.findViewById(R.id.listView_groups);

        groupsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView firstName = (TextView) view.findViewById(R.id.text_contact_first_name);
                TextView lastName = (TextView) view.findViewById(R.id.text_contact_last_name);
                TextView mobileNumber = (TextView) view.findViewById(R.id.text_contact_mobile_number);
                Intent intent = new Intent(context, EditContactActivity.class);
                intent.putExtra("CONTACT_FIRST_NAME", firstName.getText().toString());
                intent.putExtra("CONTACT_LAST_NAME", lastName.getText().toString());
                intent.putExtra("CONTACT_MOBILE_NUMBER", mobileNumber.getText().toString());
                // REMOVE TEXTVIEW CONTACT_ID FROM XML
                intent.putExtra("POSITION", position);
                startActivityForResult(intent, REQUEST_CODE_EDIT_GROUP);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle extras = data.getExtras();


        switch (requestCode){

            // Add contact
            case REQUEST_CODE_ADD_GROUP:
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

                    groupsList.add(contact);
                    //Collections.sort(contactsList);
                    groupsListViewAdapter.notifyDataSetChanged();
                }
                break;

            case REQUEST_CODE_EDIT_GROUP:
                int position;

                switch (resultCode){

                    // Edit contact
                    case Activity.RESULT_OK:
                        position = extras.getInt("POSITION");
                        //might need to use "set" instead of "get"
                        groupsList.get(position).setFirstName(extras.getString("CONTACT_FIRST_NAME"));
                        groupsList.get(position).setLastName(extras.getString("CONTACT_LAST_NAME"));
                        groupsList.get(position).setMobileNumber(extras.getString("CONTACT_MOBILE_NUMBER"));
                        sessionManager.updateContact(groupsList.get(position));
                        groupsListViewAdapter.notifyDataSetChanged();
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
                        sessionManager.deleteContact(groupsList.get(position).getContactID());
                        groupsList.remove(position);
                        groupsListViewAdapter.notifyDataSetChanged();

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
}

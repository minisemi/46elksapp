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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
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
    private ArrayList<Group> groupsList, groupsToSend;
    private static final String LISTVIEWADAPTER_ACTION = "GROUPS";
    private Boolean chooseMode;
    private TextView groupsText;
    private Button addGroup;


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
        chooseMode = false;
        groupsToSend = new ArrayList<>();
        // mPage = getArguments().getInt(ARG_PAGE);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity();
        sessionManager = fragmentCommunicator.getSessionManager();
        groupsList = sessionManager.getGroups();
        groupsListViewAdapter = new CustomListViewAdapter(context, LISTVIEWADAPTER_ACTION, groupsList);
        groupsListView.setAdapter(groupsListViewAdapter);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_groups, container, false);
        groupsListView = (ListView) view.findViewById(R.id.listView_groups);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addGroup = (Button) view.findViewById(R.id.action_add_group);
        groupsText = (TextView) view.findViewById(R.id.text_group_name);

        view.findViewById(R.id.action_add_group).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (chooseMode){
                    chooseMode = false;
                    groupsText.setText(getResources().getString(R.string.text_groups));
                    addGroup.setText(getResources().getString(R.string.action_add_group));
                    groupsListViewAdapter.notifyDataSetChanged();

                    fragmentCommunicator.finishChooseGroups(groupsToSend);

                } else {

                    Intent intent = new Intent(context, CreateGroupActivity.class);
                    startActivityForResult(intent, REQUEST_CODE_ADD_GROUP);
                }

            }
        });


        groupsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView groupName = (TextView) view.findViewById(R.id.text_group_name);
                TextView groupSize = (TextView) view.findViewById(R.id.text_group_size);

                if (chooseMode){

                    if (!groupsToSend.contains(groupsList.get(position))){

                        view.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        groupName.setTextColor(getResources().getColor(R.color.colorTextPrimary));
                        groupSize.setTextColor(getResources().getColor(R.color.colorTextPrimary));
                        groupsToSend.add(groupsList.get(position));
                    } else {
                        groupName.setTextColor(getResources().getColor(R.color.colorTextSecondary));
                        groupSize.setTextColor(getResources().getColor(R.color.colorTextSecondary));
                        view.setBackgroundColor(getResources().getColor(R.color.colorTextPrimary));
                        groupsToSend.remove(groupsList.get(position));
                    }

                } else {

                    Intent intent = new Intent(context, EditGroupActivity.class);
                    intent.putExtra("GROUP_NAME", groupName.getText().toString());
                    intent.putExtra("POSITION", position);
                    intent.putParcelableArrayListExtra("CONTAINING_CONTACTS", groupsList.get(position).getContainingContacts());
                    startActivityForResult(intent, REQUEST_CODE_EDIT_GROUP);
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
            case REQUEST_CODE_ADD_GROUP:
                if (resultCode != Activity.RESULT_CANCELED){
                    String groupName = extras.getString("GROUP_NAME");
                    ArrayList<Contact> containingContacts = extras.getParcelableArrayList("CONTAINING_CONTACTS");
                    Group group = new Group(groupName, containingContacts);
                    sessionManager.createGroup(group);
                    //JsonObject contact = new JsonObject();
                    /*contact.addProperty("CONTACT_FIRST_NAME", firstName);
                    contact.addProperty("CONTACT_LAST_NAME", lastName);
                    contact.addProperty("CONTACT_MOBILE_NUMBER", mobileNumber);
                    contact.add("CONTACT_CONTAINING_GROUPS", new JsonArray());*/
                    //sessionManager.createContact(contact.toString());

                    groupsList.add(group);
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
                        groupsList.get(position).setName(extras.getString("CONTACT_FIRST_NAME"));
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
}

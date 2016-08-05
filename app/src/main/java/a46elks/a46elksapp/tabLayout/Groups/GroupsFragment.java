package a46elks.a46elksapp.tabLayout.Groups;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import a46elks.a46elksapp.R;
import a46elks.a46elksapp.SessionManager;
import a46elks.a46elksapp.tabLayout.Adapters.CustomListViewAdapter;
import a46elks.a46elksapp.tabLayout.Contacts.Contact;
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
        setRetainInstance(true);


        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addGroup = (Button) view.findViewById(R.id.action_add_group);
        groupsText = (TextView) view.findViewById(R.id.text_groups);

        view.findViewById(R.id.action_add_group).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (chooseMode){
                    chooseMode = false;
                    groupsText.setText(getResources().getString(R.string.text_groups));
                    addGroup.setText(getResources().getString(R.string.action_add_group));
                    groupsListViewAdapter.notifyDataSetChanged();

                    fragmentCommunicator.finishChoose("groups", groupsToSend);

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

            // Add group
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

                    // Edit group
                    case Activity.RESULT_OK:
                        position = extras.getInt("POSITION");
                        Group group = groupsList.get(position);
                        //might need to use "set" instead of "get"
                        group.setName(extras.getString("GROUP_NAME"));
                        ArrayList<Contact> addedContacts = extras.getParcelableArrayList("ADDED_CONTACTS");
                        for (Contact contact : addedContacts){
                            group.addContainingContact(contact);
                            sessionManager.addContactToGroup(group, contact);
                        }
                        ArrayList<Contact> removedContacts = extras.getParcelableArrayList("REMOVED_CONTACTS");
                        for (Contact contact : removedContacts){
                            group.removeContainingContact(contact);
                            sessionManager.removeContactFromGroup(group,contact);
                        }

                        groupsListViewAdapter.notifyDataSetChanged();

                        break;

                    // Delete group
                    case Activity.RESULT_FIRST_USER:
                        position = extras.getInt("POSITION");
                        sessionManager.deleteGroup(groupsList.get(position));
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

    public void chooseContacts (ArrayList<Group> groupsToSend){

        this.groupsToSend = groupsToSend;
        groupsText.setText(getResources().getString(R.string.text_choose_groups));
        addGroup.setText(getResources().getString(R.string.action_choose_groups));
        chooseMode = true;
        for (Group group : this.groupsToSend){
            View child = getViewByPosition(groupsList.indexOf(group), groupsListView);
            child.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            TextView groupName = (TextView) child.findViewById(R.id.text_group_name);
            TextView groupSize = (TextView) child.findViewById(R.id.text_group_size);
            groupName.setTextColor(getResources().getColor(R.color.colorTextPrimary));
            groupSize.setTextColor(getResources().getColor(R.color.colorTextPrimary));
            child.refreshDrawableState();
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
}

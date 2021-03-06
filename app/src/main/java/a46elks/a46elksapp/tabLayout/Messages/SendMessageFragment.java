package a46elks.a46elksapp.tabLayout.Messages;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import a46elks.a46elksapp.R;
import a46elks.a46elksapp.SessionManager;
import a46elks.a46elksapp.tabLayout.Contacts.Contact;
import a46elks.a46elksapp.tabLayout.Adapters.CustomExpandableListAdapter;
import a46elks.a46elksapp.tabLayout.FragmentCommunicator;
import a46elks.a46elksapp.tabLayout.Groups.Group;

/**
 * Created by Alexander on 2016-06-29.
 */
public class SendMessageFragment extends android.support.v4.app.Fragment{

    public static final String ARG_PAGE = "ARG_PAGE";
    public static final String LISTVIEWADAPTER_ACTION = "RECEIVER";
    private List<String> listDataHeader;
    private HashMap<String, ArrayList<Contact>> listDataChild;
    private SendMessageFragment sendMessageFragment = this;
    private EditText editTextMessage;
    private FragmentCommunicator fragmentCommunicator;
    private SessionManager sessionManager;
    private ArrayList<Contact> contactList;
    private ArrayList<Group> groupList;
    private CustomExpandableListAdapter adapter;
    private ExpandableListView expandableListView;

    private int mPage;

    public static SendMessageFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        SendMessageFragment fragment = new SendMessageFragment();
        fragment.setArguments(args);
        return fragment;
    }
    public static SendMessageFragment newInstance() {
        SendMessageFragment fragment = new SendMessageFragment();
        return fragment;
    }

    // Container Activity must implement this interface
   /* public interface OnEventStartedListener {
        public void onSmsSent(String message, String senderName, HashMap<String, List<Contact>> listDataChild);

    }*/

    // Might need to use Activity instead of context in this method
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contactList = new ArrayList<>();
        groupList = new ArrayList<>();
       // mPage = getArguments().getInt(ARG_PAGE);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sessionManager = fragmentCommunicator.getSessionManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_send_message, container, false);
        setRetainInstance(true);

        //   setRetainInstance(true);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
       super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.action_edit_message).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), EditMessageActivity.class);
                startActivity(intent);
                //startwaitforresponsintent..
            }
        });

        view.findViewById(R.id.action_add_message).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), CreateMessageActivity.class);
                startActivity(intent);
                //startwaitforresponsintent..
            }
        });

        view.findViewById(R.id.action_add_message).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), CreateMessageActivity.class);
                startActivity(intent);
                //startwaitforresponsintent..
            }
        });

        view.findViewById(R.id.action_add_receiver_contact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragmentCommunicator.chooseContacts(contactList);

            }
        });

        view.findViewById(R.id.action_add_receiver_group).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragmentCommunicator.chooseGroups(groupList);

            }
        });

        editTextMessage = (EditText) view.findViewById(R.id.editText_send_message);

        view.findViewById(R.id.action_send_message).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Send the event to the host activity
                fragmentCommunicator.onSmsSent(editTextMessage.getText().toString(), "a46elks", listDataChild);

            }
        });

        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();
        //listDataHeader.add("Co-Workers");
        //listDataHeader.add("Visitors");

        List<Contact> coWorkers = new ArrayList<>();
        //Contact alexander = new Contact("Alexander", "+46707142760");
        //coWorkers.add(alexander);
        List<Contact> visitors = new ArrayList<>();
       /* Contact martin = new Contact("Martin", "+46700000000");
        visitors.add(martin);

        for (int i = 1; i < 50; i++) {
            visitors.add(martin);
        }*/


        //listDataChild.put(listDataHeader.get(0), coWorkers); // Header, Child data
        //listDataChild.put(listDataHeader.get(1), visitors);



        expandableListView = (ExpandableListView)  view.findViewById(R.id.expand_receivers);

        adapter = new CustomExpandableListAdapter(getActivity(), listDataHeader, listDataChild);
        expandableListView.setAdapter(adapter);
    }

    public void populateReceivers (String content, ArrayList receiverList){

        List<String> headers = new ArrayList<>();
        HashMap<String, ArrayList<Contact>> receiverContacts = new HashMap<>();
        switch (content){
            case "contacts":
                ArrayList<Contact> contactList = receiverList;
                headers.add("Contacts");
                receiverContacts.put(headers.get(0), contactList);
                this.contactList = contactList;
                break;

            case "groups":
                ArrayList<Group> groupList = receiverList;
                this.groupList = groupList;
                for (Group group : groupList){
                    headers.add(group.getName());
                    receiverContacts.put(group.getName(), group.getContainingContacts());
                }

                break;
        }


        for (String header : headers){


        if (listDataHeader.contains(header) && receiverContacts.get(header).size()==0){
            listDataHeader.remove("Contacts");
            adapter.notifyDataSetChanged();
            return;
        }

        if (listDataHeader.contains(header) && receiverContacts.get(header).size()>0){
            listDataChild.put(header, receiverContacts.get(header));
            adapter.notifyDataSetChanged();
            expandableListView.expandGroup(listDataHeader.indexOf(header));
            return;
        }

        if (!listDataHeader.contains(header) && receiverContacts.get(header).size()>0){

            listDataHeader.add(header);
            listDataChild.put(header, receiverContacts.get(header));
            expandableListView.expandGroup(listDataHeader.indexOf(header));
            adapter.notifyDataSetChanged();
        }
        }


    }




}

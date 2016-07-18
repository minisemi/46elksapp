package a46elks.a46elksapp.tabLayout;

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

/**
 * Created by Alexander on 2016-06-29.
 */
public class SendMessageFragment extends android.support.v4.app.Fragment{

    public static final String ARG_PAGE = "ARG_PAGE";
    public static final String LISTVIEWADAPTER_ACTION = "RECEIVER";
    private List<String> listDataHeader;
    private HashMap<String, List<Contact>> listDataChild;
    private SendMessageFragment sendMessageFragment = this;
    private EditText editTextMessage;
    private FragmentCommunicator fragmentCommunicator;

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
       // mPage = getArguments().getInt(ARG_PAGE);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_send_message, container, false);
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
        listDataHeader.add("Co-Workers");
        listDataHeader.add("Visitors");

        List<Contact> coWorkers = new ArrayList<>();
        Contact alexander = new Contact("Alexander", "+46707142760");
        coWorkers.add(alexander);
        List<Contact> visitors = new ArrayList<>();
        Contact martin = new Contact("Martin", "+46700000000");
        Contact martin2 = new Contact("Martin", "+46700000000");
        Contact martin3 = new Contact("Martin", "+46700000000");
        Contact martin4 = new Contact("Martin", "+46700000000");
        Contact martin5 = new Contact("Martin", "+46700000000");
        Contact martin6 = new Contact("Martin", "+46700000000");
        Contact martin7 = new Contact("Martin", "+46700000000");
        Contact martin8 = new Contact("Martin", "+46700000000");
        Contact martin9 = new Contact("Martin", "+46700000000");
        visitors.add(martin);
        visitors.add(martin2);
        visitors.add(martin3);
        visitors.add(martin4);
        visitors.add(martin5);
        visitors.add(martin6);
        visitors.add(martin7);
        visitors.add(martin8);
        visitors.add(martin9);
        for (int i = 0; i < 100; i++) {
            visitors.add(martin9);
        }

        listDataChild.put(listDataHeader.get(0), coWorkers); // Header, Child data
        listDataChild.put(listDataHeader.get(1), visitors);

        ExpandableListView expandableListView = (ExpandableListView)  view.findViewById(R.id.expand_receivers);

        CustomExpandableListAdapter adapter = new CustomExpandableListAdapter(getActivity(), listDataHeader, listDataChild);
        expandableListView.setAdapter(adapter);
    }

    public void makeSnackBar (){
        Snackbar.make(getView(),"Message Sent", Snackbar.LENGTH_SHORT).show();

    }
}

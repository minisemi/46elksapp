package a46elks.a46elksapp.tabLayout;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import a46elks.a46elksapp.R;
import a46elks.a46elksapp.introductionGuide.CreateMessageFragment;
import a46elks.a46elksapp.serverConnection.HttpAsyncTask;

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

    private int mPage;

    public static SendMessageFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        SendMessageFragment fragment = new SendMessageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_send_message, container, false);
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

                for (List<Contact> children : listDataChild.values()){

                    for (Contact receiver : children) {
                        final String receiverNumber = receiver.getNumber();
                        HttpAsyncTask smsAsyncTask = new HttpAsyncTask(sendMessageFragment, editTextMessage.getText().toString(), "a46Elks", receiverNumber);
                        smsAsyncTask.execute();
                    }

                }

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
        visitors.add(martin);

        listDataChild.put(listDataHeader.get(0), coWorkers); // Header, Child data
        listDataChild.put(listDataHeader.get(1), visitors);

        ExpandableListView expandableListView = (ExpandableListView)  view.findViewById(R.id.expand_receivers);

        mExpandableListAdapter adapter = new mExpandableListAdapter(getActivity(), listDataHeader, listDataChild);
        expandableListView.setAdapter(adapter);
    }

    public void makeSnackBar (){
        Snackbar.make(getView(),"Message Sent", Snackbar.LENGTH_SHORT).show();

    }
}

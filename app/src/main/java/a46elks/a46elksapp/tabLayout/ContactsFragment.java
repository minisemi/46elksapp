package a46elks.a46elksapp.tabLayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import a46elks.a46elksapp.R;

/**
 * Created by Alexander on 2016-06-29.
 */
public class ContactsFragment extends Fragment{

    public static final String ARG_PAGE = "ARG_PAGE";
    public static final String LISTVIEWADAPTER_ACTION = "ADDRESS_BOOK";

    private int mPage;

    public static ContactsFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        ContactsFragment fragment = new ContactsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);

    }

    //OnActivityCreated?
    //setRetainInstance(true)?

    /*@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String[] values = new String[] { "Jonas", "Fredrik" };

        ContactsAdapter adapter = new ContactsAdapter(getActivity(), values);
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

                Snackbar.make(v,"Contact added", Snackbar.LENGTH_SHORT).show();
                //startwaitforresponsintent..
            }
        });


        String[] values = new String[] { "Jonas", "Fredrik" };

        ListView contactsList = (ListView) view.findViewById(R.id.expand_receivers);
        ContactsAdapter adapter = new ContactsAdapter(getActivity(), LISTVIEWADAPTER_ACTION, values);
        //setListAdapter(adapter);
        contactsList.setAdapter(adapter);
    }
}



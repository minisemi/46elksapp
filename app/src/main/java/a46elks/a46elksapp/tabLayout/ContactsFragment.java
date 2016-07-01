package a46elks.a46elksapp.tabLayout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import a46elks.a46elksapp.R;

/**
 * Created by Alexander on 2016-06-29.
 */
public class ContactsFragment extends android.support.v4.app.Fragment{

    public static final String ARG_PAGE = "ARG_PAGE";

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_contacts, container, false);

        return view;
    }

}

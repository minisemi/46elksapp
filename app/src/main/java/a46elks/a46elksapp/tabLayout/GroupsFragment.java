package a46elks.a46elksapp.tabLayout;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import a46elks.a46elksapp.R;

/**
 * Created by Alexander on 2016-06-29.
 */
public class GroupsFragment extends android.support.v4.app.Fragment{

    public static final String ARG_PAGE = "ARG_PAGE";
    public Context context = getActivity();

    private int mPage;

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
        mPage = getArguments().getInt(ARG_PAGE);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_groups, container, false);

        return view;
    }

}

package a46elks.a46elksapp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Alexander on 2016-06-29.
 */
public class fragment1 extends android.support.v4.app.Fragment{

    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;

    public static fragment1 newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        fragment1 fragment = new fragment1();
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

        View view = null;
        TextView textView;
        switch (getArguments().getInt(ARG_PAGE))
        {
            case 1:
                view = inflater.inflate(R.layout.fragment1, container, false);
                textView = (TextView) view;
                break;

            case 2:
                view = inflater.inflate(R.layout.fragment2, container, false);
                textView = (TextView) view;
                break;

            case 3:
                view = inflater.inflate(R.layout.fragment3, container, false);
                textView = (TextView) view;
                break;
        }
        //textView.setText("Fragment #" + mPage);
        return view;
    }

}

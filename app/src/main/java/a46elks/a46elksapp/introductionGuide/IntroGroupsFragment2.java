package a46elks.a46elksapp.introductionGuide;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import a46elks.a46elksapp.R;

/**
 * Created by Alexander on 2016-06-29.
 */
public class IntroGroupsFragment2 extends android.support.v4.app.Fragment{

    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;

    public static IntroGroupsFragment2 newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        IntroGroupsFragment2 fragment = new IntroGroupsFragment2();
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


        View view = inflater.inflate(R.layout.fragment_intro_groups2, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.action_intro_go_back1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            IntroTabLayoutActivity.viewPager.setCurrentItem(0, true);
            }
        });

        view.findViewById(R.id.action_populate_group).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            IntroTabLayoutActivity.viewPager.setCurrentItem(2, true);
            }
        });
    }
}





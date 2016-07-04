package a46elks.a46elksapp.introductionGuide;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import a46elks.a46elksapp.R;
import a46elks.a46elksapp.tabLayout.TabLayoutActivity;

/**
 * Created by Alexander on 2016-06-29.
 */
public class CreateMessageFragment extends android.support.v4.app.Fragment{

    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;

    public static CreateMessageFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        CreateMessageFragment fragment = new CreateMessageFragment();
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

        View view = inflater.inflate(R.layout.fragment_create_message, container, false);

        view.findViewById(R.id.action_intro_go_back2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntroTabLayoutActivity.viewPager.setCurrentItem(1, true);
            }
        });

        view.findViewById(R.id.action_intro_create_message).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TabLayoutActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

}





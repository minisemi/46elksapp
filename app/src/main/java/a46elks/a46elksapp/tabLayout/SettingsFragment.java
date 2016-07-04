package a46elks.a46elksapp.tabLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import a46elks.a46elksapp.LoginActivity;
import a46elks.a46elksapp.R;
import a46elks.a46elksapp.introductionGuide.CreateMessageFragment;

/**
 * Created by Alexander on 2016-06-29.
 */
public class SettingsFragment extends android.support.v4.app.Fragment{

    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;

    public static SettingsFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        SettingsFragment fragment = new SettingsFragment();
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

        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        view.findViewById(R.id.action_log_out).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                //startwaitforresponsintent..
            }
        });

        return view;
    }

}

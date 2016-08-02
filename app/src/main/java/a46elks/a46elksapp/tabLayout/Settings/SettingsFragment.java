package a46elks.a46elksapp.tabLayout.Settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import a46elks.a46elksapp.LoginActivity;
import a46elks.a46elksapp.R;
import a46elks.a46elksapp.SessionManager;
import a46elks.a46elksapp.tabLayout.FragmentCommunicator;

/**
 * Created by Alexander on 2016-06-29.
 */
public class SettingsFragment extends Fragment{

    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;
    private SessionManager sessionManager;
    private FragmentCommunicator fragmentCommunicator;

    public static SettingsFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        SettingsFragment fragment = new SettingsFragment();
        fragment.setArguments(args);
        return fragment;
    }
    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

      //  mPage = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sessionManager = fragmentCommunicator.getSessionManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        view.findViewById(R.id.action_log_out).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sessionManager.logoutUser();

                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                //startwaitforresponsintent..
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /*WebView wv = new WebView(this);
        setContentView(wv);
        wv.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onPermissionRequest(PermissionRequest request) {
                request.grant(request.getResources());
            }
        });
        wv.getSettings().setJavaScriptEnabled(true);
        wv.getSettings().setAllowContentAccess(true);
        wv.getSettings().setAllowUniversalAccessFromFileURLs(true);
        wv.getSettings().setDomStorageEnabled(true);
        wv.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);*/
    }

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
}

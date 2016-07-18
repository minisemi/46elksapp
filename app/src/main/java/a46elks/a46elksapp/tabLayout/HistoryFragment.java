package a46elks.a46elksapp.tabLayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import a46elks.a46elksapp.R;

/**
 * Created by Alexander on 2016-06-29.
 */
public class HistoryFragment extends Fragment{

    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;
    private List<String> historyList;
    private ListView historyListView;
    private final String LISTVIEWADAPTER_ACTION = "HISTORY";
    private CustomListViewAdapter historyListViewAdapter;

    public static HistoryFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        HistoryFragment fragment = new HistoryFragment();
        fragment.setArguments(args);
        return fragment;
    }
    public static HistoryFragment newInstance() {
        HistoryFragment fragment = new HistoryFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // mPage = getArguments().getInt(ARG_PAGE);
        historyList = new ArrayList();
        historyList.add("hej");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_history, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        historyListView = (ListView) view.findViewById(R.id.listView_history);
        historyListViewAdapter = new CustomListViewAdapter(getContext(), LISTVIEWADAPTER_ACTION, historyList);
        historyListView.setAdapter(historyListViewAdapter);

    }


    public void addEvent (){
         historyList.add("lol");
         historyListViewAdapter.notifyDataSetChanged();


    }

}

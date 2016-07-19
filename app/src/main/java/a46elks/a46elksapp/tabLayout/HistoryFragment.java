package a46elks.a46elksapp.tabLayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

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
    private List<ProgressBar> activeProgressBars;
    private View childView;
    private ProgressBar progressBar;

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
        activeProgressBars = new ArrayList<>();
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


    public void addEvent (String eventInfo, int eventID, int numberOfSMS){
         //TextView historyEvent = (TextView) historyListView.getChildAt(eventID).findViewById(R.id.text_history_event);
        //HistoryEvent historyEvent = new HistoryEvent();
         historyList.add(eventInfo);
         historyListViewAdapter.notifyDataSetChanged();
        //childView = getViewByPosition(eventID, historyListView);
        //progressBar = (ProgressBar) childView.findViewById(R.id.progressBar_history);
        //progressBar.getId();
        //progressBar.setMax(numberOfSMS);
        //activeProgressBars.add(progressBar);
        //childView.refreshDrawableState();
        //historyListViewAdapter.notifyDataSetChanged();
        //historyListViewAdapter.notifyDataSetChanged();

        // historyListViewAdapter.
        /*int i = historyListView.getChildCount();
        View child = historyListView.getChildAt(eventID);
        ProgressBar progressBar = (ProgressBar) child.findViewById(R.id.progressBar_history);
         progressBar.setMax(numberOfSMS);
         historyListViewAdapter.notifyDataSetChanged();
         //historyEvent.setText(eventInfo);*/

    }

    public void updateProgress (int eventID){


        View view = getViewByPosition(eventID, historyListView);
        ProgressBar progress = (ProgressBar) view.findViewById(R.id.progressBar_history);
        //int k = progress.getMax();

        progress.setProgress(progress.getProgress()+1);
        view.refreshDrawableState();
        //historyListViewAdapter.notifyDataSetChanged();
    }

    public void setProgressMax (int eventID, int numberOfSMS){
        View view = getViewByPosition(eventID, historyListView);
        ProgressBar progress = (ProgressBar) view.findViewById(R.id.progressBar_history);

        progress.setMax(numberOfSMS);
        view.refreshDrawableState();
    }

    public View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }

}

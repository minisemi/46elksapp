package a46elks.a46elksapp.tabLayout.History;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import a46elks.a46elksapp.R;
import a46elks.a46elksapp.tabLayout.Adapters.CustomListViewAdapter;

/**
 * Created by Alexander on 2016-06-29.
 */
public class HistoryFragment extends Fragment{

    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;
    private List<HistoryListItem> historyList;
    private ListView historyListView;
    private final String LISTVIEWADAPTER_ACTION = "HISTORY";
    private CustomListViewAdapter historyListViewAdapter;
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_history, container, false);
        setRetainInstance(true);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        historyList = new ArrayList();

        historyListView = (ListView) view.findViewById(R.id.listView_history);
        historyListViewAdapter = new CustomListViewAdapter(getContext(), LISTVIEWADAPTER_ACTION, historyList);
        historyListView.setAdapter(historyListViewAdapter);


    }


    public void addEvent (String eventInfo, int eventID, int numberOfSMS){
         //TextView historyEvent = (TextView) historyListView.getChildAt(eventID).findViewById(R.id.text_history_event);
        //HistoryListItem historyEvent = new HistoryListItem();
        // historyList.add(eventInfo);
         //historyListViewAdapter.notifyDataSetChanged();

        HistoryListItem historyListItem = new HistoryListItem(eventID, eventInfo, numberOfSMS);
        historyList.add(historyListItem);
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

    public void updateProgress (int eventID, int numberOfSMS){
        historyList.get(eventID).updateProgress();
        historyListViewAdapter.notifyDataSetChanged();



        /*View view = getViewByPosition(eventID, historyListView);
        ProgressBar progress = (ProgressBar) view.findViewById(R.id.progressBar_history);

        if (progress.getMax()!=numberOfSMS){
            progress.setMax(numberOfSMS);
        }
        //int k = progress.getMax();

        progress.setProgress(progress.getProgress()+1);
        view.refreshDrawableState();*/
        //historyListViewAdapter.notifyDataSetChanged();
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

package a46elks.a46elksapp.tabLayout;

import android.app.Activity;
import android.widget.ProgressBar;
import android.widget.TextView;

import a46elks.a46elksapp.R;

/**
 * Created by Alexander on 2016-07-19.
 */
public class HistoryListItem  {

    private String eventInfo;
    private int eventID, max, progress = 0;

    public HistoryListItem (int eventID, String eventInfo, int max){
        this.eventID = eventID;
        this.max = max;
        this.eventInfo = eventInfo;

    }

    public int getEventID () {return eventID;}
    public int getProgressMax () { return max;}
    public void updateProgress () { progress++;}
    public int getProgress () { return progress;}
    public String getEventInfo () { return eventInfo;}

}

package a46elks.a46elksapp.tabLayout;

import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

import java.util.HashMap;
import java.util.List;

import a46elks.a46elksapp.R;
import a46elks.a46elksapp.SessionManager;
import a46elks.a46elksapp.serverConnection.HttpAsyncTask;

public class TabLayoutActivity extends AppCompatActivity implements FragmentCommunicator {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    public static String POSITION = "POSITION";
    final int PAGE_COUNT = 5;
    private int[] tabIcons = {
            R.drawable.ic_contacts,
            R.drawable.ic_groups,
            R.drawable.ic_send_message,
            R.drawable.ic_history,
            R.drawable.ic_user_page
    };
    private int[] selectedTabIcons = {
            R.drawable.ic_contacts_selected,
            R.drawable.ic_groups_selected,
            R.drawable.ic_send_message_selected,
            R.drawable.ic_history_selected,
            R.drawable.ic_user_page_selected
    };
    private TabLayout.Tab lastSelectedTab;
    private SampleFragmentPagerAdapter sampleFragmentPagerAdapter;
    private final int
            CONTACTS_POSITION = 0,
            GROUPS_POSITION = 1,
            SEND_MESSAGE_POSITION = 2,
            HISTORY_POSITION = 3,
            USER_PAGE_POSITION = 4;
    private HistoryFragment historyFragment;
    private int eventID = 0;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_layout);
        sessionManager = new SessionManager(this);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        sampleFragmentPagerAdapter = new SampleFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(sampleFragmentPagerAdapter);

        viewPager.setOffscreenPageLimit(4);

        // Give the TabLayout the ViewPager
        tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);


        for (int i = 0; i<PAGE_COUNT; i++){ tabLayout.getTabAt(i).setIcon(selectedTabIcons[i]);}
        //tabLayout.getTabAt(2).setText(getPageTitle(2));
        viewPager.setCurrentItem(2);
        lastSelectedTab = tabLayout.getTabAt(2);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewPager.setCurrentItem(tab.getPosition(), true);
                tab.setIcon(null);
                tab.setText(getPageTitle(tab.getPosition()));
                fadeLastSelectedTab();
                lastSelectedTab = tab;
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {            }
        });

        historyFragment = (HistoryFragment) sampleFragmentPagerAdapter.getItem(HISTORY_POSITION);

    }

    // Switches between icons and text because text is larger
    protected void fadeLastSelectedTab (){
        lastSelectedTab.setText(null);
        lastSelectedTab.setIcon(tabIcons[lastSelectedTab.getPosition()]);
    }

    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        //return tabTitles[position];

        Drawable image = ContextCompat.getDrawable(TabLayoutActivity.this, selectedTabIcons[position]);
        image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
        SpannableString sb = new SpannableString(" ");
        ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sb;
    }

    public void onSmsSent(String message, String senderName, HashMap<String, List<Contact>> listDataChild) {
        // The user selected the headline of an article from the HeadlinesFragment
        // Do something here to display that article

        int numberOfSMS = 0;

        for (List<Contact> children : listDataChild.values()){
            numberOfSMS = numberOfSMS + children.size();
        }

        if (historyFragment != null) {
            // If historyfragment is available, we're in two-pane layout...

            // Call a method in the HistoryFragment to update its content
            historyFragment.addEvent(numberOfSMS+" SMS sent!", eventID, numberOfSMS);
        } else {
            /*// Otherwise, we're in the one-pane layout and must swap frags...

            // Create fragment and give it an argument for the selected article
            HistoryFragment newFragment = new HistoryFragment();
            Bundle args = new Bundle();
            args.putInt(HistoryFragment.ARG_POSITION, position);
            newFragment.setArguments(args);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.fragment_container, newFragment);
            transaction.addToBackStack(null);

            // Commit the transaction
            transaction.commit();*/
        }

            viewPager.setCurrentItem(3, true);
            String apiUsername = sessionManager.getUserDetails().get("KEY_API_USERNAME");
            String apiPassword = sessionManager.getUserDetails().get("KEY_API_PASSWORD");
            //int connectionID = 0;
            //historyFragment.setProgressMax(eventID, listDataChild.size());

            for (List<Contact> children : listDataChild.values()) {

                for (Contact receiver : children) {

                    final String receiverNumber = receiver.getNumber();
                    HttpAsyncTask smsAsyncTask = new HttpAsyncTask(eventID, apiUsername, apiPassword,
                            historyFragment, message, senderName, numberOfSMS);
                    smsAsyncTask.execute(receiverNumber);
                }

            }
            // Vill ha serverns tid (CET?) som id
            eventID++;

    }

    @Override
    public void onBackPressed() {this.moveTaskToBack(true);}

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(POSITION, tabLayout.getSelectedTabPosition());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        viewPager.setCurrentItem(savedInstanceState.getInt(POSITION));
    }
}

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

import a46elks.a46elksapp.R;

public class TabLayoutActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    public static String POSITION = "POSITION";
    final int PAGE_COUNT = 5;
    private int[] tabIcons = {
            R.drawable.ic_send_message,
            R.drawable.ic_groups,
            R.drawable.ic_contacts,
            R.drawable.ic_history,
            R.drawable.ic_user_page
    };
    private int[] selectedTabIcons = {
            R.drawable.ic_send_message_selected,
            R.drawable.ic_groups_selected,
            R.drawable.ic_contacts_selected,
            R.drawable.ic_history_selected,
            R.drawable.ic_user_page_selected
    };

    private TabLayout.Tab lastSelectedTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_layout);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new SampleFragmentPagerAdapter(getSupportFragmentManager(),
                TabLayoutActivity.this));

        // Give the TabLayout the ViewPager
        tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        for (int i = 1; i<PAGE_COUNT; i++){ tabLayout.getTabAt(i).setIcon(selectedTabIcons[i]);}
        tabLayout.getTabAt(0).setText(getPageTitle(0));
        lastSelectedTab = tabLayout.getTabAt(0);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                tab.setIcon(null);
                tab.setText(getPageTitle(tab.getPosition()));
                fadeLastSelectedTab();
                lastSelectedTab = tab;
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

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

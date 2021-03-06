package a46elks.a46elksapp.introductionGuide;

import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import a46elks.a46elksapp.R;
import a46elks.a46elksapp.SessionManager;

public class IntroTabLayoutActivity extends AppCompatActivity {

    public static ViewPager viewPager;
    public static String POSITION = "POSITION";
    final int PAGE_COUNT = 3;
    private SessionManager sessionManager;

    private TabLayout.Tab lastSelectedTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_tab_layout);
        // TODO: IMPLEMENT INTERFACE TO PASS ON PREF_NAME
        sessionManager = new SessionManager();

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        viewPager = (ViewPager) findViewById(R.id.viewpager_intro);
        viewPager.setAdapter(new IntroSampleFragmentPagerAdapter(getSupportFragmentManager()));


    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(POSITION, viewPager.getCurrentItem());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        viewPager.setCurrentItem(savedInstanceState.getInt(POSITION));
    }

    @Override
    public void onBackPressed() {

        switch (viewPager.getCurrentItem()) {

            case 0: this.moveTaskToBack(true);
                    break;

            case 1: case 2: viewPager.setCurrentItem(viewPager.getCurrentItem()-1, true);
                   break;

            default: break;
        }

    }
}

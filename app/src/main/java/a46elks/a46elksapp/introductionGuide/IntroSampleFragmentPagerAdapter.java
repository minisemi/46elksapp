package a46elks.a46elksapp.introductionGuide;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

import a46elks.a46elksapp.R;
import a46elks.a46elksapp.introductionGuide.CreateMessageFragment;
import a46elks.a46elksapp.introductionGuide.IntroGroupsFragment1;
import a46elks.a46elksapp.introductionGuide.IntroGroupsFragment2;

/**
 * Created by Alexander on 2016-06-29.
 */
public class IntroSampleFragmentPagerAdapter extends FragmentPagerAdapter{

    final int PAGE_COUNT = 3;

    public IntroSampleFragmentPagerAdapter(android.support.v4.app.FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        //behöver förmodligen inte skicka med position till fragments

        switch (position +1){

            case 1:
                return IntroGroupsFragment1.newInstance(position + 1);

            case 2:
                return IntroGroupsFragment2.newInstance(position + 1);

            case 3:
                return CreateMessageFragment.newInstance(position + 1);
        }
        return null;
    }
}

package a46elks.a46elksapp.tabLayout;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

import a46elks.a46elksapp.R;

/**
 * Created by Alexander on 2016-06-29.
 */
public class SampleFragmentPagerAdapter extends FragmentPagerAdapter{

    final int PAGE_COUNT = 5;
    private String tabTitles[] = new String[] { "Tab1", "Tab2", "Tab3" };
    private Context context;
    private int[] imageResId = {
            R.drawable.ic_send_message,
            R.drawable.ic_groups,
            R.drawable.ic_contacts,
            R.drawable.ic_history,
            R.drawable.ic_user_page
    };

    public SampleFragmentPagerAdapter(android.support.v4.app.FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {

        switch (position +1){

            case 1:

                return SendMessageFragment.newInstance(position + 1);

            case 2:
                return GroupsFragment.newInstance(position + 1);

            case 3:
                return ContactsFragment.newInstance(position + 1);

            case 4:
                return HistoryFragment.newInstance(position + 1);

            case 5:
                return SettingsFragment.newInstance(position + 1);
        }
        return null;
    }

   /* @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        //return tabTitles[position];

        Drawable image = ContextCompat.getDrawable(context, imageResId[position]);
        image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
        SpannableString sb = new SpannableString(" ");
        ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sb;
    }*/
}

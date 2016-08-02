package a46elks.a46elksapp.tabLayout.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import a46elks.a46elksapp.tabLayout.Contacts.ContactsFragment;
import a46elks.a46elksapp.tabLayout.Groups.GroupsFragment;
import a46elks.a46elksapp.tabLayout.History.HistoryFragment;
import a46elks.a46elksapp.tabLayout.Messages.SendMessageFragment;
import a46elks.a46elksapp.tabLayout.Settings.SettingsFragment;

/**
 * Created by Alexander on 2016-06-29.
 */
public class SampleFragmentPagerAdapter extends FragmentPagerAdapter{

    final int PAGE_COUNT = 5;
    private Fragment [] tabFragments = new Fragment [PAGE_COUNT];

    public SampleFragmentPagerAdapter(android.support.v4.app.FragmentManager fm) {
        super(fm);
        tabFragments[0] = new ContactsFragment();
        tabFragments[1] = new GroupsFragment();
        tabFragments[2] = new SendMessageFragment();
        tabFragments[3] = new HistoryFragment();
        tabFragments[4] = new SettingsFragment();
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }


    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        //behöver förmodligen inte skicka med position till fragments
        // kan behöva sätta en lästa med fragments och skapa dem i pageradapter om communicator interface inte fungerar

        /*switch (position +1){

            case 1:

                return ContactsFragment.newInstance(position + 1);

            case 2:
                return GroupsFragment.newInstance(position + 1);

            case 3:
                return SendMessageFragment.newInstance(position + 1);

            case 4:
                return HistoryFragment.newInstance(position + 1);

            case 5:
                return SettingsFragment.newInstance(position + 1);
        }*/
        return tabFragments[position];
    }

}

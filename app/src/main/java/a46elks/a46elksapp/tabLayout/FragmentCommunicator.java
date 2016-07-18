package a46elks.a46elksapp.tabLayout;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Alexander on 2016-07-18.
 */
public interface FragmentCommunicator {

    public void onSmsSent(String message, String senderName, HashMap<String, List<Contact>> listDataChild);
}

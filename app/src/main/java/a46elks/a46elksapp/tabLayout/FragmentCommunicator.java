package a46elks.a46elksapp.tabLayout;

import java.util.HashMap;
import java.util.List;

import a46elks.a46elksapp.SessionManager;
import a46elks.a46elksapp.tabLayout.Contacts.Contact;

/**
 * Created by Alexander on 2016-07-18.
 */
public interface FragmentCommunicator {

    public void onSmsSent(String message, String senderName, HashMap<String, List<Contact>> listDataChild);

    public void chooseContacts(List<Contact> contactList);

    public void finishChooseContacts(List<Contact> contactList);

    public SessionManager getSessionManager ();
}

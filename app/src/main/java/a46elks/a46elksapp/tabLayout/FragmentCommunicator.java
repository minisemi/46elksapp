package a46elks.a46elksapp.tabLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import a46elks.a46elksapp.SessionManager;
import a46elks.a46elksapp.tabLayout.Contacts.Contact;
import a46elks.a46elksapp.tabLayout.Groups.Group;

/**
 * Created by Alexander on 2016-07-18.
 */
public interface FragmentCommunicator {

    public void onSmsSent(String message, String senderName, HashMap<String, ArrayList<Contact>> listDataChild);

    public void chooseContacts(ArrayList<Contact> contactList);

    public void finishChooseContacts(ArrayList<Contact> contactList);

    public void chooseGroups(ArrayList<Group> groupList);

    public void finishChooseGroups(ArrayList<Group> groupList);

    public SessionManager getSessionManager ();
}

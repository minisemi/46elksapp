package a46elks.a46elksapp.tabLayout.Groups;

/**
 * Created by Alexander on 2016-08-02.
 */

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicInteger;

import a46elks.a46elksapp.tabLayout.Contacts.Contact;

/**
 * Created by Alexander on 2016-07-11.
 */
public class Group {

    //  might need to put this in sessionmanager
    private static AtomicInteger sequenceNumber = new AtomicInteger();

    private String name;
    private int groupID;
    private ArrayList<Contact> containingContacts;

    public Group(String name, ArrayList<Contact> containingContacts) {
        this.containingContacts = containingContacts;
        this.name = name;
        groupID = sequenceNumber.getAndIncrement();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroupID() {
        return Integer.toString(groupID);
    }

    public ArrayList<Contact> getContainingContacts() {
        return containingContacts;
    }

    public void addContainingContact(Contact contact) {
        containingContacts.add(contact);
    }

    public void removeContainingContact(Contact contact) {
        containingContacts.remove(contact);
    }

    public void setAtomicInteger(Integer sequenceNumber) {
        this.sequenceNumber.set(sequenceNumber);
    }

    public String getSize (){return Integer.toString(containingContacts.size());}


}


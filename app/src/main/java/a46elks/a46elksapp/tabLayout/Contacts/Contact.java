package a46elks.a46elksapp.tabLayout.Contacts;


import java.util.HashSet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Alexander on 2016-07-11.
 */
public class Contact {

    //  might need to put this in sessionmanager
    private static AtomicInteger sequenceNumber = new AtomicInteger();

    private String mobileNumber, firstName, lastName;
    private int contactID;
    private HashSet<String> containingGroups;

    public Contact (String firstName, String lastName, String mobileNumber){
        containingGroups = new HashSet<>();
        this.mobileNumber = mobileNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        contactID = sequenceNumber.getAndIncrement();
    }

    public String getMobileNumber(){return mobileNumber;}
    public void setMobileNumber(String mobileNumber){this.mobileNumber = mobileNumber;}
    public String getFirstName(){return firstName;}
    public void setFirstName(String firstName){this.firstName = firstName;}
    public String getLastName(){return lastName;}
    public void setLastName(String lastName){this.lastName = lastName;}
    public String getContactID(){return Integer.toString(contactID);}
    public HashSet<String> getContainingGroups(){return containingGroups;}
    public String getContactJson () {
        String jsonString = null;
        //TODO: PARSE CONTACT JSON INCLUDING NAME, NUMBER
        return jsonString;
    }
    public void addContainingGroup (String group){ containingGroups.add(group);}
    public void removeContainingGroup (String group){ containingGroups.remove(group);}
    public void setContactID (Integer contactID){this.contactID = contactID;}
    public void setAtomicInteger (Integer sequenceNumber){this.sequenceNumber.set(sequenceNumber);}
}

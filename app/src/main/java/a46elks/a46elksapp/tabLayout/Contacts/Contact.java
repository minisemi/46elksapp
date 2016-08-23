package a46elks.a46elksapp.tabLayout.Contacts;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashSet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Alexander on 2016-07-11.
 */
public class Contact implements Parcelable {

    //  might need to put this in sessionmanager
    private static AtomicInteger sequenceNumber = new AtomicInteger();

    private String mobileNumber, firstName, lastName;
    private int contactID;
    private HashSet<String> containingGroups;
    private Boolean contactSelected = false, groupSelected = false;

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
    public void setContactsSelected (Boolean contactSelected){this.contactSelected = contactSelected;}
    public void setGroupsSelected (Boolean groupSelected){this.groupSelected = groupSelected;}
    public Boolean getContactsSelected (){return contactSelected;}
    public Boolean getGroupsSelected (){return groupSelected;}

    @Override
    public void writeToParcel(Parcel pc, int flags) {
        pc.writeInt(contactID);
        pc.writeString(firstName);
        pc.writeString(lastName);
        pc.writeString(mobileNumber);

    }

    /** Static field used to regenerate object, individually or as arrays */
    public static final Parcelable.Creator<Contact> CREATOR = new Parcelable.Creator<Contact>() {
        public Contact createFromParcel(Parcel pc) {
            return new Contact(pc);
        }
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };

    /**Ctor from Parcel, reads back fields IN THE ORDER they were written */
    public Contact(Parcel pc){
        contactID         = pc.readInt();
        firstName      = pc.readString();
        lastName      = pc.readString();
        mobileNumber      = pc.readString();
    }

    /** Used to give additional hints on how to process the received parcel.*/
    @Override
    public int describeContents() {
// ignore for now
        return 0;
    }
}

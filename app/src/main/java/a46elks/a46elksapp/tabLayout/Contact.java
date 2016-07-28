package a46elks.a46elksapp.tabLayout;

import com.google.gson.JsonObject;

import java.util.HashSet;
import java.util.List;

/**
 * Created by Alexander on 2016-07-11.
 */
public class Contact {

    private String mobileNumber, firstName, lastName;
    private int contactID;
    private HashSet<String> containingGroups;

    public Contact (String firstName, String lastName, String mobileNumber){
        containingGroups = new HashSet<>();
        this.mobileNumber = mobileNumber;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getMobileNumber(){return mobileNumber;}
    public String getFirstName(){return firstName;}
    public String getLastName(){return lastName;}
    public int getContactID(){return contactID;}
    public String getContactJson () {
        String jsonString = null;
        //TODO: PARSE CONTACT JSON INCLUDING NAME, NUMBER
        return jsonString;
    }
    public void addContainingGroup (String group){ containingGroups.add(group);}
    public void removeContainingGroup (String group){ containingGroups.remove(group);}
}

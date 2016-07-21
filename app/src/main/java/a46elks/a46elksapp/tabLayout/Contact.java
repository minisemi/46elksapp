package a46elks.a46elksapp.tabLayout;

/**
 * Created by Alexander on 2016-07-11.
 */
public class Contact {

    private String number, receiverName;
    private int contactID;

    public Contact (String receiverName, String number){
        this.number = number;
        this.receiverName = receiverName;
    }

    public String getNumber(){return number;}
    public String getReceiverName(){return receiverName;}
    public int getContactID(){return contactID;}

}

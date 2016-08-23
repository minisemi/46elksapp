package a46elks.a46elksapp.tabLayout.Adapters;

/**
 * Created by Alexander on 2016-07-05.
 */
import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.JsonParser;

import java.util.List;

import a46elks.a46elksapp.R;
import a46elks.a46elksapp.tabLayout.Contacts.Contact;
import a46elks.a46elksapp.tabLayout.Groups.Group;
import a46elks.a46elksapp.tabLayout.History.HistoryListItem;

public class CustomListViewAdapter extends ArrayAdapter {
    private final Context context;
    private List<HistoryListItem> valuesHistory;
    private List<Contact> valuesContacts;
    private List<Group> valuesGroups;
    private final String LISTVIEWADAPTER_ACTION;
    private JsonParser jsonParser;

    // Might wanna create a specific adapter for history, handling "HistoryEvents" instead of Strings

    public CustomListViewAdapter(Context context, String LISTVIEWADAPTER_ACTION, List values) {
        super(context, -1, values);
        this.context = context;
        switch (LISTVIEWADAPTER_ACTION) {
            case "CONTACTS_CONTACTS":
                this.valuesContacts = values;
                break;

            case "GROUPS_CONTACTS":
                this.valuesContacts = values;
                break;

            case "GROUPS":
                this.valuesGroups = values;
                break;

            case "HISTORY":
                this.valuesHistory = values;
                break;
        }
        this.LISTVIEWADAPTER_ACTION = LISTVIEWADAPTER_ACTION;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = null;
        TextView textView;
        TextView firstName;
        TextView lastName;
        TextView mobileNumber;

        switch (LISTVIEWADAPTER_ACTION){

            case "CONTACTS_CONTACTS": //populateAddressBook();
                //flytta till metoden
                rowView = inflater.inflate(R.layout.list_item_contacts, parent, false);
                Contact contact = valuesContacts.get(position);
                 firstName = (TextView) rowView.findViewById(R.id.text_contact_first_name);
                 lastName = (TextView) rowView.findViewById(R.id.text_contact_last_name);
                 mobileNumber = (TextView) rowView.findViewById(R.id.text_contact_mobile_number);
                firstName.setText(contact.getFirstName());
                lastName.setText(contact.getLastName());
                mobileNumber.setText(contact.getMobileNumber());

                if (contact.getContactsSelected()){
                    rowView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                    firstName.setTextColor(ContextCompat.getColor(getContext(),R.color.colorTextPrimary));
                    lastName.setTextColor(ContextCompat.getColor(getContext(),R.color.colorTextPrimary));
                    mobileNumber.setTextColor(ContextCompat.getColor(getContext(),R.color.colorTextPrimary));

                } else {
                    firstName.setTextColor(ContextCompat.getColor(getContext(),R.color.colorTextSecondary));
                    lastName.setTextColor(ContextCompat.getColor(getContext(),R.color.colorTextSecondary));
                    mobileNumber.setTextColor(ContextCompat.getColor(getContext(),R.color.colorTextSecondary));
                    rowView.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.colorTextPrimary));
                }
                //contactID.setText(Integer.toString(valuesContacts.get(position).getContactID()));
                //containingGroups.setText(valuesContacts.get(position).getContainingGroups());
                /*JsonObject contact = (JsonObject) jsonParser.parse(valuesContacts.get(position));
                firstName.setText(contact.get("CONTACT_FIRST_NAME").toString());
                lastName.setText(contact.get("CONTACT_LAST_NAME").toString());
                mobileNumber.setText(contact.get("CONTACT_MOBILE_NUMBER").toString());
                containingGroups.setText(contact.get("CONTACT_CONTAINING_GROUPS").toString());*/

                //textView.setText(valuesHistory.get(position));
                return rowView;

            case "GROUPS_CONTACTS": //populateAddressBook();
                //flytta till metoden
                Contact groupContact = valuesContacts.get(position);
                rowView = inflater.inflate(R.layout.list_item_contacts, parent, false);
                 firstName = (TextView) rowView.findViewById(R.id.text_contact_first_name);
                 lastName = (TextView) rowView.findViewById(R.id.text_contact_last_name);
                 mobileNumber = (TextView) rowView.findViewById(R.id.text_contact_mobile_number);
                firstName.setText(groupContact.getFirstName());
                lastName.setText(groupContact.getLastName());
                mobileNumber.setText(groupContact.getMobileNumber());

                if (groupContact.getGroupsSelected()){
                    rowView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                    firstName.setTextColor(ContextCompat.getColor(getContext(),R.color.colorTextPrimary));
                    lastName.setTextColor(ContextCompat.getColor(getContext(),R.color.colorTextPrimary));
                    mobileNumber.setTextColor(ContextCompat.getColor(getContext(),R.color.colorTextPrimary));

                } else {
                    firstName.setTextColor(ContextCompat.getColor(getContext(),R.color.colorTextSecondary));
                    lastName.setTextColor(ContextCompat.getColor(getContext(),R.color.colorTextSecondary));
                    mobileNumber.setTextColor(ContextCompat.getColor(getContext(),R.color.colorTextSecondary));
                    rowView.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.colorTextPrimary));
                }
                //contactID.setText(Integer.toString(valuesContacts.get(position).getContactID()));
                //containingGroups.setText(valuesContacts.get(position).getContainingGroups());
                /*JsonObject contact = (JsonObject) jsonParser.parse(valuesContacts.get(position));
                firstName.setText(contact.get("CONTACT_FIRST_NAME").toString());
                lastName.setText(contact.get("CONTACT_LAST_NAME").toString());
                mobileNumber.setText(contact.get("CONTACT_MOBILE_NUMBER").toString());
                containingGroups.setText(contact.get("CONTACT_CONTAINING_GROUPS").toString());*/

                //textView.setText(valuesHistory.get(position));
                return rowView;

            case "GROUPS":
                rowView = inflater.inflate(R.layout.list_item_groups, parent, false);
                TextView groupName = (TextView) rowView.findViewById(R.id.text_group_name);
                TextView groupSize = (TextView) rowView.findViewById(R.id.text_group_size);
                groupName.setText(valuesGroups.get(position).getName());
                groupSize.setText("Consists of " + valuesGroups.get(position).getSize() + " contacts");
                break;


            case "HISTORY":
                rowView = inflater.inflate(R.layout.list_item_history, parent, false);
                textView = (TextView) rowView.findViewById(R.id.text_history_event);
                textView.setText(valuesHistory.get(position).getEventInfo());
                ProgressBar progressBar = (ProgressBar) rowView.findViewById(R.id.progressBar_history);
                progressBar.setMax(valuesHistory.get(position).getProgressMax());
                progressBar.setProgress(valuesHistory.get(position).getProgress());
                return rowView;

            case "SETTINGS":

            default:
                rowView = convertView;
                break;

        }

        //ImageView imageView = (ImageView) rowView.findViewById(R.id.listView_contact_picture);
        //textView.setText(valuesHistory[position]);

        return rowView;
    }


}

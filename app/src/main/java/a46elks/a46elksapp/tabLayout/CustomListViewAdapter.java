package a46elks.a46elksapp.tabLayout;

/**
 * Created by Alexander on 2016-07-05.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.List;

import a46elks.a46elksapp.R;

public class CustomListViewAdapter extends ArrayAdapter {
    private final Context context;
    private List<HistoryListItem> valuesHistory;
    private List<String> valuesContacts;
    private final String LISTVIEWADAPTER_ACTION;
    private JsonParser jsonParser;

    // Might wanna create a specific adapter for history, handling "HistoryEvents" instead of Strings

    public CustomListViewAdapter(Context context, String LISTVIEWADAPTER_ACTION, List values) {
        super(context, -1, values);
        this.context = context;
        switch (LISTVIEWADAPTER_ACTION) {
            case "CONTACTS":
                this.valuesHistory = values;
                break;

            case "HISTORY":
                this.valuesContacts = values;
                jsonParser = new JsonParser();
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

        switch (LISTVIEWADAPTER_ACTION){

            case "RECEIVER": populateReceivers();
                            break;


            case "CONTACTS": //populateAddressBook();
                //flytta till metoden
                rowView = inflater.inflate(R.layout.list_item_contacts, parent, false);
                TextView firstName = (TextView) rowView.findViewById(R.id.text_contact_first_name);
                TextView lastName = (TextView) rowView.findViewById(R.id.text_contact_last_name);
                TextView mobileNumber = (TextView) rowView.findViewById(R.id.text_contact_mobile_number);
                TextView containingGroups = (TextView) rowView.findViewById(R.id.text_containing_groups);
                JsonObject contact = (JsonObject) jsonParser.parse(valuesContacts.get(position));
                firstName.setText(contact.get("CONTACT_FIRST_NAME").toString());
                lastName.setText(contact.get("CONTACT_LAST_NAME").toString());
                mobileNumber.setText(contact.get("CONTACT_MOBILE_NUMBER").toString());
                containingGroups.setText(contact.get("CONTACT_CONTAINING_GROUPS").toString());

                //textView.setText(valuesHistory.get(position));
                return rowView;

            case "SETTINGS":

            case "HISTORY":
                rowView = inflater.inflate(R.layout.list_item_history, parent, false);
                textView = (TextView) rowView.findViewById(R.id.text_history_event);
                textView.setText(valuesHistory.get(position).getEventInfo());
                ProgressBar progressBar = (ProgressBar) rowView.findViewById(R.id.progressBar_history);
                progressBar.setMax(valuesHistory.get(position).getProgressMax());
                progressBar.setProgress(valuesHistory.get(position).getProgress());
                return rowView;

            default:
                rowView = convertView;
                break;

        }

        //ImageView imageView = (ImageView) rowView.findViewById(R.id.listView_contact_picture);
        //textView.setText(valuesHistory[position]);

        return rowView;
    }

    protected void populateReceivers (){

    }

    protected void populateAddressBook (){

    }

}

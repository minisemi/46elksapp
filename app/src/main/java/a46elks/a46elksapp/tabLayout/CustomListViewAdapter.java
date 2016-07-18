package a46elks.a46elksapp.tabLayout;

/**
 * Created by Alexander on 2016-07-05.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import a46elks.a46elksapp.R;

public class CustomListViewAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final List<String> values;
    private final String LISTVIEWADAPTER_ACTION;

    public CustomListViewAdapter(Context context, String LISTVIEWADAPTER_ACTION, List<String> values) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
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


            case "ADDRESS_BOOK": populateAddressBook();
                //flytta till metoden
                rowView = inflater.inflate(R.layout.list_item_contacts, parent, false);
                textView = (TextView) rowView.findViewById(R.id.text_contact_description);
                textView.setText(values.get(position));
                return rowView;

            case "SETTINGS":

            case "HISTORY":
                rowView = inflater.inflate(R.layout.list_item_history, parent, false);
                textView = (TextView) rowView.findViewById(R.id.text_history_event                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           );
                textView.setText(values.get(position));
                return rowView;
        }

        //ImageView imageView = (ImageView) rowView.findViewById(R.id.listView_contact_picture);
        //textView.setText(values[position]);

        return rowView;
    }

    protected void populateReceivers (){

    }

    protected void populateAddressBook (){

    }
}

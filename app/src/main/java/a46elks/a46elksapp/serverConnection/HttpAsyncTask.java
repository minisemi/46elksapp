package a46elks.a46elksapp.serverConnection;

import android.content.Context;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

import java.util.ArrayList;
import java.util.HashMap;

import a46elks.a46elksapp.tabLayout.SendMessageFragment;

/**
 * Created by Alexander on 2016-07-07.
 */
public class HttpAsyncTask extends AsyncTask{

    Context context;
    private JsonParser parser = new JsonParser();
    private JsonObject object;
    private JsonArray data;
    private SendMessageFragment sendMessageFragment;
    private HashMap expandableListRows;

    public HttpAsyncTask(SendMessageFragment sendMessageFragment, HashMap expandableListRows){
        this.context = context;
        this.expandableListRows = expandableListRows;
        this.sendMessageFragment = sendMessageFragment;
    }

    @Override
    protected Object doInBackground(Object[] params) {

        try {
            System.out.println("Sending SMS");

            HttpResponse<String> response = Unirest.post("https://api.46elks.com/a1/SMS")
                    .basicAuth("<API Username>","<API Password>")
                    .field("to", "+46723175800")
                    .field("from", "+46766862078")
                    .field("message", "Hi! This is åäö a message from me! Have a nice day!")
                    .asString();

            /*HttpResponse<JsonNode> jsonResponse = Unirest.post("https://api.46elks.com/a1/SMS")
                    .basicAuth("<API Username>","<API Password>")
                    .field("to", "+46723175800")
                    .field("from", "+46766862078")
                    .field("message", "Hi! This is åäö a message from me! Have a nice day!")
                    .asJson();*/

            System.out.println(response.getBody());


        }

        catch (Exception e){
            System.out.println(e);
        }

        /*Request addRequest = new Request(context,"add",addList, replicate).mapRequest();
        connection = new Connection(addRequest, context);
        t = new Thread(connection);
        t.start();
        do {
            jsonString = connection.getJson();
        } while (jsonString == null);
        object = (JsonObject) parser.parse(jsonString);*/


        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);

        sendMessageFragment.makeSnackBar();

        /*object = (JsonObject) parser.parse(result);
        data = (JsonArray) object.get("data");
        markerList.clear();

        for (JsonElement e : data) {
            JsonObject o = e.getAsJsonObject();
            double lat = o.get("lat").getAsDouble();
            double lon = o.get("lon").getAsDouble();
            String event = o.get("event").getAsString();
            markerList.add(String.valueOf(lat) + ";" + String.valueOf(lon) + ";" + event + ";:local");
            mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lon)).title("Pågående ärende:").snippet(event));
        }*/

    }
}

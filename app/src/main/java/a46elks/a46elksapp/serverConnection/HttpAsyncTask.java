package a46elks.a46elksapp.serverConnection;

import android.content.Context;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.util.Base64;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
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

                String line = null;
            try {
                // Construct POST data
                String data = URLEncoder.encode("from", "UTF-8") + "=" + URLEncoder.encode("Alexander", "UTF-8");
                data += "&" + URLEncoder.encode("to", "UTF-8") + "=" + URLEncoder.encode("+46707142760", "UTF-8");
                data += "&" + URLEncoder.encode("message", "UTF-8") + "=" + URLEncoder.encode("TEST SMS", "UTF-8");

                // Make HTTP POST request
                URL url = new URL("https://api.46elks.com/a1/SMS");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                String username = "u1d80044b7d60dcc0aa2f3dfcac7cfc96";
                String password = "3EF1A94C6A16733E1AAF6589DA3B3DE3";
                //String base64string = DatatypeConverter.printBase64Binary((username + ":" + password).getBytes("UTF-8"));
                String base64string = android.util.Base64.encodeToString((username + ":" + password).getBytes("UTF-8"), Base64.DEFAULT);
                String basicAuth = "Basic dTFkODAwNDRiN2Q2MGRjYzBhYTJmM2RmY2FjN2NmYzk2OjNFRjFBOTRDNkExNjczM0UxQUFGNjU4OURBM0IzREUz";
                conn.setRequestProperty("Authorization", basicAuth);
                conn.setRequestProperty("Content-Length", Integer.toString(data.getBytes("UTF-8").length));
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(data);
                wr.flush();

                // Handle response data here (currently, just print out response)
                BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                while ((line = rd.readLine()) != null) {
                    System.out.println(line);
                }
                wr.close();
                rd.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        /*try {
            System.out.println("Sending SMS");


            HttpResponse<String> response = Unirest.post("https://api.46elks.com/a1/SMS")
                    .basicAuth("<API Username>","<API Password>")
                    .field("to", "+46723175800")
                    .field("from", "+46766862078")
                    .field("message", "Hi! This is åäö a message from me! Have a nice day!")
                    .asString();

            HttpResponse<JsonNode> jsonResponse = Unirest.post("https://api.46elks.com/a1/SMS")
                    .basicAuth("<API Username>","<API Password>")
                    .field("to", "+46723175800")
                    .field("from", "+46766862078")
                    .field("message", "Hi! This is åäö a message from me! Have a nice day!")
                    .asJson();

            System.out.println(response.getBody());


        }

        catch (Exception e){
            System.out.println(e);
        }*/

        /*Request addRequest = new Request(context,"add",addList, replicate).mapRequest();
        connection = new Connection(addRequest, context);
        t = new Thread(connection);
        t.start();
        do {
            jsonString = connection.getJson();
        } while (jsonString == null);
        object = (JsonObject) parser.parse(jsonString);*/


        return line;
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

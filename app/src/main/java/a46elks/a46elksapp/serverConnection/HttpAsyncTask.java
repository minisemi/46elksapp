package a46elks.a46elksapp.serverConnection;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

import a46elks.a46elksapp.tabLayout.HistoryFragment;
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
    private String senderName, receiverNumber;
    private String message;
    private int eventID, connectionID, numberOfSMS;
    private HistoryFragment historyFragment;

    public HttpAsyncTask(int eventID, HistoryFragment historyFragment, String message, String senderName, int numberOfSMS){
        this.numberOfSMS = numberOfSMS;
        this.historyFragment = historyFragment;
        this.eventID = eventID;
        this.senderName = senderName;
        this.message = message;
        this.context = context;
        this.expandableListRows = expandableListRows;
        this.sendMessageFragment = sendMessageFragment;
    }

    @Override
    protected Object doInBackground(Object[] params) {

        String receiverNumber = params[0].toString();
        String line = null;

            try {

                // Construct POST data
                String data = URLEncoder.encode("from", "UTF-8") + "=" + URLEncoder.encode(senderName, "UTF-8");
                data += "&" + URLEncoder.encode("to", "UTF-8") + "=" + URLEncoder.encode(receiverNumber, "UTF-8");
                data += "&" + URLEncoder.encode("message", "UTF-8") + "=" + URLEncoder.encode(message, "UTF-8");

                // Make HTTP POST request
                URL url = new URL("https://api.46elks.com/a1/SMS");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                String username = "u1d80044b7d60dcc0aa2f3dfcac7cfc96";
                String password = "3EF1A94C6A16733E1AAF6589DA3B3DE3";
                //String base64string1 = DatatypeConverter.printBase64Binary((username + ":" + password).getBytes("UTF-8"));
                //String base64string2 = android.util.Base64.encodeToString((username + ":" + password).getBytes("UTF-8"), Base64.DEFAULT);
                byte[] encodedBytes = org.apache.commons.codec.binary.Base64.encodeBase64((username + ":" + password).getBytes("UTF-8"));
                String base64string = new String(encodedBytes);
                String basicAuth = "Basic " + base64string;
                conn.setRequestProperty("Authorization", basicAuth);
                conn.setRequestProperty("Content-Length", Integer.toString(data.getBytes("UTF-8").length));
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(data);
                wr.flush();
                wr.close();

                // Handle response data here (currently, just print out response)
                try {

                BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                while ((line = rd.readLine()) != null) {
                    System.out.println(line);
                }

                    //kolla om error 200 (dvs att allt gick bra) och ingen ingen timeout, annars försök igen lr säg till användare natt det inte gick
                rd.close();
                } catch(NullPointerException n){
                    System.out.println("No errors received from server");
                }

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
        historyFragment.updateProgress(eventID, numberOfSMS);
        super.onPostExecute(o);

       // sendMessageFragment.makeSnackBar();

    }
}

package a46elks.a46elksapp.introductionGuide;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

import a46elks.a46elksapp.R;
import a46elks.a46elksapp.tabLayout.TabLayoutActivity;

public class AccountCreationActivity extends AppCompatActivity {

    private Context context = this;
    private HashMap<String, String> accountCredentials;
    private EditText name, mobilenumber, email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_creation);
        accountCredentials = new HashMap<>();
        name = (EditText) findViewById(R.id.editText_name_or_company);
        mobilenumber = (EditText) findViewById(R.id.editText_mobile_number);
        email = (EditText) findViewById(R.id.editText_email);
        password = (EditText) findViewById(R.id.editText_password);


        findViewById(R.id.action_create_account).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                accountCredentials.put("KEY_NAME", name.getText().toString());
                accountCredentials.put("KEY_MOBILENUMBER", mobilenumber.getText().toString());
                accountCredentials.put("KEY_EMAIL", email.getText().toString());
                accountCredentials.put("KEY_PASSWORD", password.getText().toString());
                AccountCreationTask accountCreationTask = new AccountCreationTask(accountCredentials);
                accountCreationTask.execute();

                Intent intent = new Intent(context, IntroTabLayoutActivity.class);
                startActivity(intent);


            }
        });

    }
}

/**
 * Represents an asynchronous login/registration task used to authenticate
 * the user.
 */
public class AccountCreationTask extends AsyncTask<Void, Void, Boolean> {

    private final String email, name, password, mobilenumber, currency;
    private JsonParser jsonParser;


    AccountCreationTask(HashMap<String, String> accountCredentials) {
        name = accountCredentials.get("KEY_NAME");
        email = accountCredentials.get("KEY_EMAIL");
        password = accountCredentials.get("KEY_PASSWORD");
        mobilenumber = accountCredentials.get("KEY_MOBILENUMBER");
        currency = "SEK";
        jsonParser = new JsonParser();
    }

    @Override
    protected Boolean doInBackground(Void... params) {

        String line = null;

        try {
            // Construct POST data
            String data = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8");
            data += "&" + URLEncoder.encode("mobilenumber", "UTF-8") + "=" + URLEncoder.encode(mobilenumber, "UTF-8");
            data += "&" + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8");
            data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
            // TODO: CREATE CURRENCY OPTION FOR SIGNUP
            data += "&" + URLEncoder.encode("currency", "UTF-8") + "=" + URLEncoder.encode(currency, "UTF-8");

            // Make HTTP POST request
            URL url = new URL("https://www.46elks.com/api/signup");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();
            wr.close();

            try {

                // BufferedReader sd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                String receivedJson = null;
                while ((line = rd.readLine()) != null) {
                    System.out.println(line);
                    receivedJson = line;
                }

                    /*String line2;

                    while ((line2 = sd.readLine()) != null) {
                        System.out.println(line2);
                    }*/

                // PARSA FÖLJANDE INPUT: {"id":"u1d80044b7d60dcc0aa2f3dfcac7cfc96","secret":"3EF1A94C6A16733E1AAF6589DA3B3DE3"}
                // OCH SPARA TILL apiUsername och apiPassword
                JsonObject serverResponse = (JsonObject) jsonParser.parse(receivedJson);
                sessionManagemer.createLoginSession(serverResponse.get("id").getAsString(), serverResponse.get("secret").getAsString());



                //kolla om error 200 (dvs att allt gick bra) och ingen ingen timeout, annars försök igen lr säg till användare natt det inte gick
                rd.close();
                //sd.close();
            } catch(NullPointerException n){
                n.printStackTrace();
                System.out.println("Incorrect user details");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    protected void onPostExecute(final Boolean success) {
        mAuthTask = null;
        showProgress(false);

        if (success) {
            Intent intent = new Intent(context, TabLayoutActivity.class);
            startActivity(intent);
            //finish();
        } else {

            findViewById(R.id.text_invalid_user_credentials).setVisibility(View.VISIBLE);
            mLoginFormView.requestFocus();
        }
    }

    @Override
    protected void onCancelled() {
        mAuthTask = null;
        showProgress(false);
    }
}

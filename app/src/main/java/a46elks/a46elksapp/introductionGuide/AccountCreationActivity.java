package a46elks.a46elksapp.introductionGuide;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import a46elks.a46elksapp.LoginActivity;
import a46elks.a46elksapp.R;
import a46elks.a46elksapp.SessionManager;
import a46elks.a46elksapp.tabLayout.TabLayoutActivity;

import static android.Manifest.permission.READ_CONTACTS;

public class AccountCreationActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private Context context = this;
    private HashMap<String, String> accountCredentials;
    private EditText editName, editMobilenumber, editPassword;
    private SessionManager sessionManager;
    private AccountCreationTask mAuthTask;
    private View accountCreationForm, progressView;
    private AutoCompleteTextView editEmail;
    private static final int REQUEST_READ_CONTACTS = 0;
    private AccountCreationActivity accountCreationActivity = this;
    private String errorFailed, error;
    private TextView errorMessage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_creation);
        editName = (EditText) findViewById(R.id.editText_name_or_company);
        editName.setText("Alexander");
        editMobilenumber = (EditText) findViewById(R.id.editText_mobile_number);
        editMobilenumber.setText("+46707142760");
        editEmail = (AutoCompleteTextView) findViewById(R.id.editText_email);
        editEmail.setText("alexander+0002@46elks.com");
        editPassword = (EditText) findViewById(R.id.editText_password);
        editPassword.setText("admin");
        sessionManager = new SessionManager(context);
        errorMessage = (TextView) findViewById(R.id.text_error_message);
        errorFailed = getResources().getString(R.string.error_connection_failed);
        accountCreationForm = findViewById(R.id.account_creation_form);
        progressView = findViewById(R.id.login_progress);

        editPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });


        findViewById(R.id.action_create_account).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });

        findViewById(R.id.action_go_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuthTask = null;
                showProgress(false);
                finish();

            }
        });

    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        if (errorMessage.getVisibility()==View.VISIBLE){
            errorMessage.setVisibility(View.INVISIBLE);
        }
        editEmail.setError(null);
        editPassword.setError(null);
        editName.setError(null);
        editMobilenumber.setError(null);

        // Store values at the time of the login attempt.
        String name = editName.getText().toString();
        String mobileNumber = editMobilenumber.getText().toString();
        String email = editEmail.getText().toString().toLowerCase();
        String password = editPassword.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            editPassword.setError(getString(R.string.error_field_required));
            focusView = editPassword;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            editEmail.setError(getString(R.string.error_field_required));
            focusView = editEmail;
            cancel = true;
        } else if (!isEmailValid(email)) {
            editEmail.setError(getString(R.string.error_invalid_email));
            focusView = editEmail;
            cancel = true;
        }

        if (TextUtils.isEmpty(mobileNumber)){
            editMobilenumber.setError(getString(R.string.error_field_required));
            focusView = editMobilenumber;
            cancel = true;
        }

        if (TextUtils.isEmpty(name)){
            editName.setError(getString(R.string.error_field_required));
            focusView = editName;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            //showProgress(true);
            mAuthTask = new AccountCreationTask(name, mobileNumber, email, password);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(editEmail, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(AccountCreationActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        editEmail.setAdapter(adapter);
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            accountCreationForm.setVisibility(show ? View.GONE : View.VISIBLE);
            accountCreationForm.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    accountCreationForm.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            progressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            accountCreationForm.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        mAuthTask = null;
        showProgress(false);
        super.onBackPressed();
    }


    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class AccountCreationTask extends AsyncTask<Void, Void, Boolean> {

        private final String email, name, password, mobileNumber, currency;
        private JsonParser jsonParser;
        private JsonObject serverResponse;
        private EditText editText = null;
        private AutoCompleteTextView autoCompleteTextView;


        AccountCreationTask(String name, String mobileNumber, String email, String password) {
            this.name = name;
            this.email = email;
            this.password = password;
            this.mobileNumber = mobileNumber;
            currency = "SEK";
            jsonParser = new JsonParser();
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            String line = null;


            try {
                // Construct POST data
                String data = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8");
                data += "&" + URLEncoder.encode("mobilenumber", "UTF-8") + "=" + URLEncoder.encode(mobileNumber, "UTF-8");
                data += "&" + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8");
                data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
                // TODO: CREATE CURRENCY AND PHONECOUNTRY OPTION FOR SIGNUP
                data += "&" + URLEncoder.encode("currency", "UTF-8") + "=" + URLEncoder.encode(currency, "UTF-8");
                System.out.println("data: "+data+"}");
                System.out.println("mejl: "+email);
                System.out.println("pw: "+password+"}");

                // Make HTTP POST request
                URL url = new URL("https://www.46elks.com/api/signup");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true);
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(data);
                wr.flush();
                wr.close();

                if (!(conn.getResponseCode() >= 400)) {

                    BufferedReader sd = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    String receivedJson = null;
                    while ((line = sd.readLine()) != null) {
                        System.out.println(line);
                        receivedJson = line;
                    }
                    sd.close();


                    if (TextUtils.equals(receivedJson, "{\"success\":\"Account created\"}")){
                        String newData = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8");
                        newData += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
                        System.out.println("newData: "+newData+"}");
                        System.out.println("email: "+email);
                        System.out.println("password: "+password+"}");

                        URL newUrl = new URL("https://dashboard.46elks.com/api/login.php");
                        HttpURLConnection newConn = (HttpURLConnection) newUrl.openConnection();
                        newConn.setDoOutput(true);
                        newConn.setDoInput(true);
                        OutputStreamWriter newWr = new OutputStreamWriter(newConn.getOutputStream());
                        newWr.write(newData);
                        newWr.flush();
                        newWr.close();

                        if (!(newConn.getResponseCode() >= 400)) {

                            BufferedReader newSd = new BufferedReader(new InputStreamReader(newConn.getInputStream()));

                            String newReceivedJson = null;
                            String newLine;
                            while ((newLine = newSd.readLine()) != null) {
                                System.out.println(newLine);
                                newReceivedJson = newLine;
                            }

                            if (TextUtils.equals(newReceivedJson, "username or password incorrect")){
                                newSd.close();
                                mAuthTask = null;
                                showProgress(false);
                                finish();
                                return false;

                            } else{
                                serverResponse = (JsonObject) jsonParser.parse(newReceivedJson);
                                newSd.close();
                                return true;
                            }


                        } else {
                            // inputStream = conn.getErrorStream();

                            // If no connection was made
                            mAuthTask = null;
                            showProgress(false);
                            finish();
                            return false;
                        }

                    } else{
                        serverResponse = (JsonObject) jsonParser.parse(receivedJson);
                        error = serverResponse.get("error").toString();

                        switch (error){

                            case "\"Currency is not SEK or EUR\"":
                                break;
                            case "\"Missing parameter.\"":
                                break;
                            case "\"The phone number is not valid.\"":
                                editText = editMobilenumber;
                                break;
                            case "\"Email address is not valid.\"":
                                autoCompleteTextView = editEmail;
                                break;
                            case "\"Temporary email address is not allowed.\"":
                                autoCompleteTextView = editEmail;
                                break;
                            case "\"Unable to access the mailserver for that email.\"":
                                autoCompleteTextView = editEmail;
                                break;
                            case "\"Phone number already registered.\"":
                                editText = editMobilenumber;
                                break;
                            case "\"Email already registered.\"":
                                autoCompleteTextView = editEmail;
                                break;
                            case "\"Mobile number does not belong to any country.\"":
                                editText = editMobilenumber;
                                break;
                            case "\"Mobile number not valid.\"":
                                editText = editMobilenumber;
                                break;
                            case "\"Mobile number does not seem valid, you may still try but it might not work.\"":
                                editText = editMobilenumber;
                                break;
                            case "\"API user creation failed, unknown error.\"":
                                break;
                            case "\"Verification phone call failed.\"":
                                break;

                        }

                        System.out.println("Error detected:" + error);
                        return false;
                    }

                } else {
                    // inputStream = conn.getErrorStream();

                    // If no connection was made

                    error = errorFailed;
                    return false;
                }

            } catch (Exception e) {
                e.printStackTrace();
                error = errorFailed;
                System.out.println("Connection to server failed");
                return false;
            }

        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                sessionManager.createLoginSession(serverResponse.get("id").getAsString(), serverResponse.get("secret").getAsString());
                Intent intent = new Intent(context, IntroTabLayoutActivity.class);
                startActivity(intent);
                //finish();
            } else {

                if (!(editText == null)) {
                    editText.setError(error);
                    editText.requestFocus();

                } else if (!(autoCompleteTextView == null)){
                    autoCompleteTextView.setError(error);
                    autoCompleteTextView.requestFocus();

                } else {
                    errorMessage.setText(error);
                    errorMessage.setVisibility(View.VISIBLE);
                    accountCreationForm.requestFocus();
                }
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }


}

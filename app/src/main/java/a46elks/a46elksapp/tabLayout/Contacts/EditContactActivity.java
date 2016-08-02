package a46elks.a46elksapp.tabLayout.Contacts;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.google.gson.JsonObject;

import a46elks.a46elksapp.R;

public class EditContactActivity extends AppCompatActivity {

    private EditText firstName;
    private EditText lastName;
    private EditText mobileNumber;
    private JsonObject jsonObject;
    private Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        jsonObject = new JsonObject();
        extras = getIntent().getExtras();
        jsonObject.addProperty("CONTACT_FIRST_NAME", extras.getString("CONTACT_FIRST_NAME"));
        jsonObject.addProperty("CONTACT_LAST_NAME", extras.getString("CONTACT_LAST_NAME"));
        jsonObject.addProperty("CONTACT_MOBILE_NUMBER", extras.getString("CONTACT_MOBILE_NUMBER"));
        jsonObject.addProperty("CONTACT_CONTAINING_GROUPS", extras.getString("CONTACT_CONTAINING_GROUPS"));
        firstName = (EditText) findViewById(R.id.editText_edit_contact_first_name);
        lastName = (EditText) findViewById(R.id.editText_edit_contact_last_name);
        mobileNumber = (EditText) findViewById(R.id.editText_edit_contact_number);
        firstName.setText(extras.getString("CONTACT_FIRST_NAME"));
        lastName.setText(extras.getString("CONTACT_LAST_NAME"));
        mobileNumber.setText(extras.getString("CONTACT_MOBILE_NUMBER"));
        //TODO: FIX CHECK SO THAT MOBILE NUMBER IS ENTERED HERE AND ON ADD CONTACT

        findViewById(R.id.action_edit_contact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptCreate();

            }
        });

        findViewById(R.id.action_delete_contact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("POSITION", extras.getString("POSITION"));
                //intent.putExtra("CONTACT_OLD_INFO", jsonObject.toString());
                setResult(RESULT_FIRST_USER, intent);
                finish();
            }
        });

        findViewById(R.id.action_edit_contact_go_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(Activity.RESULT_CANCELED, intent);
                finish();
            }
        });
    }

    private void attemptCreate() {

        // Reset errors.

        firstName.setError(null);
        //lastName.setError(null);
        mobileNumber.setError(null);

        // Store values at the time of the login attempt.
        String name = firstName.getText().toString();
        String number = mobileNumber.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(number)) {
            mobileNumber.setError(getString(R.string.error_field_required));
            focusView = mobileNumber;
            cancel = true;
        } else if (!isNumberValid(number)) {
            mobileNumber.setError(getString(R.string.error_invalid_number));
            focusView = mobileNumber;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(name)) {
            firstName.setError(getString(R.string.error_field_required));
            focusView = firstName;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            Intent intent = new Intent();
            intent.putExtra("CONTACT_FIRST_NAME", firstName.getText().toString());
            intent.putExtra("CONTACT_LAST_NAME", lastName.getText().toString());
            intent.putExtra("CONTACT_MOBILE_NUMBER", mobileNumber.getText().toString());
            intent.putExtra("POSITION", extras.getString("POSITION"));
            //intent.putExtra("CONTACT_OLD_INFO", jsonObject.toString());
            setResult(RESULT_OK, intent);
            finish();

        }
    }

    public Boolean isNumberValid (String number){
        return (number.length()==10);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(Activity.RESULT_CANCELED, intent);
        finish();
    }
}

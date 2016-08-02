package a46elks.a46elksapp.tabLayout.Contacts;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import a46elks.a46elksapp.R;

public class CreateContactActivity extends AppCompatActivity {

    private EditText firstName;
    private EditText lastName;
    private EditText mobileNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contact);
        firstName = (EditText) findViewById(R.id.editText_create_contact_first_name);
        lastName = (EditText) findViewById(R.id.editText_create_contact_last_name);
        mobileNumber = (EditText) findViewById(R.id.editText_contact_number);

        findViewById(R.id.action_create_contact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                attemptCreate();

            }
        });

        findViewById(R.id.action_create_contact_go_back).setOnClickListener(new View.OnClickListener() {
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

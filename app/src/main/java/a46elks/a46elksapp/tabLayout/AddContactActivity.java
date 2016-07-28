package a46elks.a46elksapp.tabLayout;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import a46elks.a46elksapp.R;

public class AddContactActivity extends AppCompatActivity {

    private EditText firstName;
    private EditText lastName;
    private EditText mobileNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        firstName = (EditText) findViewById(R.id.editText_create_contact_first_name);
        lastName = (EditText) findViewById(R.id.editText_create_contact_last_name);
        mobileNumber = (EditText) findViewById(R.id.editText_mobile_number);

        findViewById(R.id.action_create_contact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("CONTACT_FIRST_NAME", firstName.getText().toString());
                intent.putExtra("CONTACT_LAST_NAME", lastName.getText().toString());
                intent.putExtra("CONTACT_MOBILE_NUMBER", mobileNumber.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(Activity.RESULT_CANCELED, intent);
        finish();
    }
}

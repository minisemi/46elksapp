package a46elks.a46elksapp.tabLayout;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
        firstName = (EditText) findViewById(R.id.editText_create_contact_first_name);
        lastName = (EditText) findViewById(R.id.editText_create_contact_last_name);
        mobileNumber = (EditText) findViewById(R.id.editText_mobile_number);
        firstName.setText(extras.getString("CONTACT_FIRST_NAME"));
        lastName.setText(extras.getString("CONTACT_LAST_NAME"));
        mobileNumber.setText(extras.getString("CONTACT_MOBILE_NUMBER"));
        //TODO: FIX CHECK SO THAT MOBILE NUMBER IS ENTERED HERE AND ON ADD CONTACT

        findViewById(R.id.action_edit_contact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("CONTACT_FIRST_NAME", firstName.getText().toString());
                intent.putExtra("CONTACT_LAST_NAME", lastName.getText().toString());
                intent.putExtra("CONTACT_MOBILE_NUMBER", mobileNumber.getText().toString());
                intent.putExtra("CONTACT_CONTAINING_GROUPS", extras.getString("CONTACT_CONTAINING_GROUPS"));
                intent.putExtra("CONTACT_OLD_INFO", jsonObject.toString());
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        findViewById(R.id.action_delete_contact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("CONTACT_OLD_INFO", jsonObject.toString());
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(Activity.RESULT_CANCELED, intent);
        finish();
    }
}
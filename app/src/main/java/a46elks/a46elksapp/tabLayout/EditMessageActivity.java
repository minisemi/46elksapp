package a46elks.a46elksapp.tabLayout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import a46elks.a46elksapp.R;

public class EditMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_message);

        findViewById(R.id.action_edit_message).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditMessageActivity.this, TabLayoutActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.action_edit_message_go_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditMessageActivity.this, TabLayoutActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.action_delete_message).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditMessageActivity.this, TabLayoutActivity.class);
                startActivity(intent);
            }
        });


    }
}

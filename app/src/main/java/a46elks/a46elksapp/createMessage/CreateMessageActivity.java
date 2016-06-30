package a46elks.a46elksapp.createMessage;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import a46elks.a46elksapp.R;
import a46elks.a46elksapp.groups.GroupsActivity;
import a46elks.a46elksapp.sendMessage.SendMessageActivity;

public class CreateMessageActivity extends AppCompatActivity {

    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_message);

        Button createMessageButton;

        createMessageButton =  (Button) (findViewById(R.id.action_create_message));
        createMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SendMessageActivity.class);
                startActivity(intent);


            }
        });
    }
}

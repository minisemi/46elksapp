package a46elks.a46elksapp.introductionGuide;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import a46elks.a46elksapp.R;

public class GroupsActivity1 extends AppCompatActivity {

    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups1);

        Button createGroupButton;

        createGroupButton =  (Button) (findViewById(R.id.action_create_group));
        createGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CreateMessageActivity.class);
                startActivity(intent);
            }
        });

        ListView importContactsList = (ListView) findViewById(R.id.listView_importContacts);




    }


}

package hr.fer.amigosi.guildbuild;

import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * @author Filip Kerman
 * @version v1.0 30.12.2017
 *
 */
public class MessagesActivity extends AppCompatActivity {

    private Button btnSendMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        btnSendMessage = (Button) findViewById(R.id.btnSendMessage);

        btnSendMessage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent sendMessage = new Intent(MessagesActivity.this, SendMessageActivity.class);
                startActivity(sendMessage);
            }
        });

        //TODO
    }
}

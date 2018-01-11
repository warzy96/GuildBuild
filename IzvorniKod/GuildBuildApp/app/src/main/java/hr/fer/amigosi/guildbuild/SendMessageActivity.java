package hr.fer.amigosi.guildbuild;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class SendMessageActivity extends AppCompatActivity {

    private EditText etRecipient;
    private EditText etTitle;
    private EditText etMessage;
    private Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);

        etRecipient = (EditText) findViewById(R.id.etRecipient);
        etTitle = (EditText) findViewById(R.id.etTitle);
        etMessage = (EditText) findViewById(R.id.etMessage);

        etRecipient.setHint("Recipient");
        etRecipient.setHintTextColor(getResources().getColor(R.color.colorPrimaryDark));

        etTitle.setHint("Add a subject");
        etTitle.setHintTextColor(getResources().getColor(R.color.colorPrimaryDark));

        etMessage.setHint("Add a message");
        etMessage.setHintTextColor(getResources().getColor(R.color.colorPrimaryDark));


        //TODO
    }
}

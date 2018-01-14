package hr.fer.amigosi.guildbuild;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import hr.fer.amigosi.guildbuild.DAO.PorukaDAO;
import hr.fer.amigosi.guildbuild.entities.PorukaEntity;

public class SendMessageActivity extends AppCompatActivity {

    private PorukaEntity porukaEntity;
    private EditText etRecipient;
    private EditText etTitle;
    private EditText etMessage;
    private Button btnSend;
    private String nicknameStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);

        Intent intent = getIntent();
        nicknameStr = intent.getStringExtra(MainActivity.EXTRA_MESSAGE1);
        etRecipient = (EditText) findViewById(R.id.etRecipient);
        etTitle = (EditText) findViewById(R.id.etTitle);
        etMessage = (EditText) findViewById(R.id.etMessage);
        btnSend = (Button) findViewById(R.id.btnSendMsg2);
        etRecipient.setHint("Recipient");
        etRecipient.setHintTextColor(getResources().getColor(R.color.colorPrimaryDark));
        etRecipient.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

        etTitle.setHint("Add a subject");
        etTitle.setHintTextColor(getResources().getColor(R.color.colorPrimaryDark));
        etTitle.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

        etMessage.setHint("Add a message");
        etMessage.setHintTextColor(getResources().getColor(R.color.colorPrimaryDark));
        etMessage.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

        btnSend.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                sendMessage sendMessage = new sendMessage();
                sendMessage.execute();
            }
        });

    }

    public class sendMessage extends AsyncTask<String,String,String>{

        String tmp = "";
        Boolean isSuccess = false;
        @Override
        protected String doInBackground(String... strings) {
            String etRecipientt = etRecipient.getText().toString();
            String etTitlee= etTitle.getText().toString();
            String etMessagee = etMessage.getText().toString();



            if(etRecipientt.trim().isEmpty() || etTitlee.trim().isEmpty() || etMessagee.trim().isEmpty()){
                tmp = "Please fill all the text fields";
            }
            else if(etRecipientt.trim().length()>20 || etTitlee.trim().length()>30
                    || etMessagee.trim().length()>500){
                tmp = "Please insert smaller data!";
            }

            else{
                try{
                    Connection connection = DatabaseConnection.getConnection();
                    if(connection == null){
                        tmp = "Check Your Internet Access!";
                    }
                    else
                    {
                        String query1 = "select * from korisnik where nadimak = '" + etRecipientt + "'";
                        String query2 = "insert into poruka values ('"
                                + etRecipientt + "', '"
                                + nicknameStr + "', '"
                                + etMessagee + "', '"
                                + etTitlee + "', "
                                + "null)";
                        Statement stmt1 = connection.createStatement();
                        Statement stmt2 = connection.createStatement();
                        ResultSet rs1 = stmt1.executeQuery(query1);
                        if(rs1.next()){
                            stmt2.executeUpdate(query2);
                            tmp = "Message sent!";
                            isSuccess=true;
                        }
                        else
                        {
                            isSuccess=false;
                            tmp = "Recipient does not exist!";
                        }


                    }

                }catch (Exception ex){
                    isSuccess = false;
                    tmp = ex.getMessage();
                }
            }
            return tmp;
        }

        @Override
        protected void onPostExecute(String s) {
            if(isSuccess){
                Toast.makeText(SendMessageActivity.this, s, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(SendMessageActivity.this, MessagesActivity.class);
                startActivity(intent);
            }else{
                Toast.makeText(SendMessageActivity.this, s, Toast.LENGTH_LONG);
            }
        }
    }
}

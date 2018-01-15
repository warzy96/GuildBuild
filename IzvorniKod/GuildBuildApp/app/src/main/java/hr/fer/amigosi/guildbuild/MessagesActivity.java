package hr.fer.amigosi.guildbuild;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.sql.SQLException;
import java.util.List;

import hr.fer.amigosi.guildbuild.DAO.PorukaDAO;
import hr.fer.amigosi.guildbuild.entities.PorukaEntity;

/**
 * @author Filip Kerman
 * @version v1.0 30.12.2017
 *
 */
public class MessagesActivity extends AppCompatActivity {

    private Button btnSendMessage;
    private String nickNameStr;
    private TextView nickname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        Intent intent = getIntent();
        nickNameStr = intent.getStringExtra(MainActivity.EXTRA_MESSAGE1);
        btnSendMessage = (Button) findViewById(R.id.btnSendMessage);
        btnSendMessage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent sendMessage = new Intent(MessagesActivity.this, SendMessageActivity.class);
                sendMessage.putExtra(MainActivity.EXTRA_MESSAGE1, nickNameStr);
                startActivity(sendMessage);
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        new GetReceivedMessages().execute();
    }

    private class GetReceivedMessages extends AsyncTask<String, String, List<PorukaEntity>>{

        @Override
        protected List<PorukaEntity> doInBackground(String... strings) {
            try {
                PorukaDAO porukaDAO = new PorukaDAO();
                List<PorukaEntity> porukaEntities = porukaDAO.getAllReceivedMessages(nickNameStr);
                return porukaEntities;
            }
            catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            finally {
                try {
                    PorukaDAO.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        protected void onPostExecute(List<PorukaEntity> porukaEntities) {
            LinearLayout layout = findViewById(R.id.linMsgLayout);
            layout.removeAllViews();

            for(PorukaEntity poruka : porukaEntities){
                TextView titleTextView = new TextView(getApplicationContext());
                TextView senderTextView = new TextView(getApplicationContext());
                TextView messageTextView = new TextView(getApplicationContext());


                titleTextView.setText(poruka.getNaslov());
                titleTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                titleTextView.setTextColor(Color.WHITE);
                titleTextView.setTextSize(25);

                messageTextView.setText(poruka.getTekst());
                messageTextView.setTextSize(10);
                messageTextView.setTextColor(Color.WHITE);

                senderTextView.setText("From: " +poruka.getPosiljatelj());
                senderTextView.setTextSize(20);
                senderTextView.setTextColor(Color.WHITE);


                layout.addView(titleTextView);
                layout.addView(senderTextView);
                layout.addView(messageTextView);
            }
        }
    }
}

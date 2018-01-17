package hr.fer.amigosi.guildbuild;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.List;

import hr.fer.amigosi.guildbuild.DAO.PrisustvoDAO;

public class MyEventsActivity extends AppCompatActivity {
    private String nickName;
    private LinearLayout eventsList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_events);

        String userWatching;
        Intent intent = getIntent();
        nickName = intent.getStringExtra(MainActivity.EXTRA_MESSAGE1);
        userWatching = intent.getStringExtra(GuildMembersActivity.NADIMAK_KORISNIKA_KOJI_POKRECE_ACTIVITY);
        eventsList = (LinearLayout) findViewById(R.id.eventsList);

        if(!userWatching.equals(nickName)) {
            TextView myEventsTxt = findViewById(R.id.myEventsTxt);
            myEventsTxt.setText(nickName + "'s events");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        PopulateWithEvents populateWithEvents = new PopulateWithEvents();
        populateWithEvents.execute();
    }

    private class PopulateWithEvents extends AsyncTask<Void,Void,List<String>>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            eventsList.removeAllViews();
        }

        @Override
        protected List<String> doInBackground(Void... voids) {
            try {
                PrisustvoDAO prisustvoDAO = new PrisustvoDAO();
                List<String> result = prisustvoDAO.getAllAttendedEventsForUser(nickName);
                return result;
            } catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                try {
                    PrisustvoDAO.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<String> eventNames) {
            if(eventNames.isEmpty() || eventNames==null) {
                Toast.makeText(MyEventsActivity.this, "No events to show", Toast.LENGTH_SHORT).show();
                finish();
            }
            for(String name : eventNames) {
                TextView textView = new TextView(MyEventsActivity.this);
                textView.setText(name);
                textView.setTextSize(30);
                textView.setTextColor(Color.WHITE);
                textView.setFocusable(false);

                eventsList.addView(textView);
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}

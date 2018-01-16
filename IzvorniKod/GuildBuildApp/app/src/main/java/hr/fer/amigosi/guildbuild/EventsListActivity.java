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
import java.util.ArrayList;
import java.util.List;

import hr.fer.amigosi.guildbuild.DAO.DogadajDAO;
import hr.fer.amigosi.guildbuild.entities.DogadajEntity;

public class EventsListActivity extends AppCompatActivity {

    public static final String SIFRA_DOGADAJA = "sifra_dogadaja";

    private int sifraKorisnikovogCeha;
    private int sifraTrazenogCeha;
    private String nadimak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_list);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new PopulateViewWithEvents().execute();
    }

    private class PopulateViewWithEvents extends AsyncTask<Void, Void, List<DogadajEntity>> {
        @Override
        protected void onPreExecute() {
            Intent intent = getIntent();
            sifraKorisnikovogCeha = intent.getIntExtra(MainActivity.EXTRA_MESSAGE2, 0);
            sifraTrazenogCeha = intent.getIntExtra(GuildDetailsActivity.EXTRA_MESSAGE3,0);
            nadimak = intent.getStringExtra(MainActivity.EXTRA_MESSAGE1);
        }

        @Override
        protected List<DogadajEntity> doInBackground(Void... voids) {
            List<DogadajEntity> dogadaji = new ArrayList<>();
            try {
                DogadajDAO dogadajDAO = new DogadajDAO();
                if(sifraTrazenogCeha==sifraKorisnikovogCeha) {
                    dogadaji = dogadajDAO.getAllEventsForGuild(sifraTrazenogCeha);
                }else{
                    dogadaji = dogadajDAO.getVisibleEventsForGuild(sifraTrazenogCeha);
                }
            }
            catch(Exception e) {

            }
            finally {
                try {
                    DogadajDAO.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return dogadaji;
        }

        @Override
        protected void onPostExecute(List<DogadajEntity> dogadajEntities) {
            if(dogadajEntities.isEmpty()) {
                Toast.makeText(EventsListActivity.this, "No events to show",
                        Toast.LENGTH_SHORT).show();
                finish();
            }
            else {
                LinearLayout linearLayout = findViewById(R.id.EventLayout);
                linearLayout.removeAllViews();
                for(DogadajEntity dogadajEntity : dogadajEntities) {
                    TextView eventName = new TextView(EventsListActivity.this);
                    if(dogadajEntity.isIspunjenost()){
                        eventName.setTextColor(Color.GREEN);
                    }else{
                        eventName.setTextColor(Color.RED);
                    }
                    eventName.setText(dogadajEntity.getNazivDogadaja());
                    eventName.setTextSize(35);

                    eventName.setOnClickListener(View -> {
                        Intent intent = new Intent(EventsListActivity.this, GoalsListActivity.class);
                        intent.putExtra(EventsListActivity.SIFRA_DOGADAJA,dogadajEntity.getSifraDogadaja());
                        intent.putExtra(MainActivity.EXTRA_MESSAGE1, nadimak);
                        startActivity(intent);
                    });

                    linearLayout.addView(eventName);
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}

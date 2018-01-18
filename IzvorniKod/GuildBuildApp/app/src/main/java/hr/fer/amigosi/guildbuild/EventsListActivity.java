package hr.fer.amigosi.guildbuild;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import hr.fer.amigosi.guildbuild.DAO.DogadajDAO;
import hr.fer.amigosi.guildbuild.DAO.PrisustvoDAO;
import hr.fer.amigosi.guildbuild.DAO.UserDAO;
import hr.fer.amigosi.guildbuild.entities.DogadajEntity;
import hr.fer.amigosi.guildbuild.entities.KorisnikEntity;

public class EventsListActivity extends AppCompatActivity {

    public static final String SIFRA_DOGADAJA = "sifra_dogadaja";

    private Integer sifraKorisnikovogCeha;
    private Integer sifraTrazenogCeha;
    private String nadimak;
    private Boolean notInGuild=false;

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
                    dogadaji = dogadajDAO.getAllEventsForGuild(sifraTrazenogCeha.toString());
                }else{
                    dogadaji = dogadajDAO.getVisibleEventsForGuild(sifraTrazenogCeha.toString());
                    notInGuild=true;
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
                    LinearLayout eventLayout = new LinearLayout(EventsListActivity.this);
                    TextView eventName = new TextView(EventsListActivity.this);

                    if(dogadajEntity.isIspunjenost()){
                        eventName.setTextColor(Color.GREEN);
                    }else{
                        eventName.setTextColor(Color.RED);
                    }
                    eventName.setText(dogadajEntity.getNazivDogadaja());
                    eventName.setTextSize(35);

                    eventLayout.setOrientation(LinearLayout.VERTICAL);
                    eventLayout.addView(eventName);

                    eventName.setOnClickListener(View -> {
                        Intent intent = new Intent(EventsListActivity.this, GoalsListActivity.class);
                        intent.putExtra(EventsListActivity.SIFRA_DOGADAJA,dogadajEntity.getSifraDogadaja());
                        intent.putExtra(MainActivity.EXTRA_MESSAGE1, nadimak);
                        startActivity(intent);
                    });

                    if(!notInGuild) {

                        Button joinButton = new Button(new ContextThemeWrapper(EventsListActivity.this, R.style.button_style), null, R.style.button_style);
                        Button visibleButton = new Button(new ContextThemeWrapper(EventsListActivity.this, R.style.button_style), null, R.style.button_style);

                        joinButton.setText("Join");
                        joinButton.setTextSize(25);
                        joinButton.setTextColor(Color.WHITE);

                        visibleButton.setText("Set visible");
                        visibleButton.setTextSize(25);
                        visibleButton.setTextColor(Color.WHITE);


                        eventLayout.addView(joinButton);
                        eventLayout.addView(visibleButton);

                        visibleButton.setOnClickListener(View -> {
                            SetVisible setVisible = new SetVisible();
                            setVisible.execute(dogadajEntity.getSifraDogadaja());
                        });

                        joinButton.setOnClickListener(View -> {
                            JoinToEvent joinToEvent = new JoinToEvent();
                            joinToEvent.execute(dogadajEntity.getSifraDogadaja());
                        });

                    }
                    linearLayout.addView(eventLayout);
                }
            }
        }
    }

    private class JoinToEvent extends AsyncTask<Integer,Void,String>{



        @Override
        protected String doInBackground(Integer... integers) {

            try{
                PrisustvoDAO prisustvoDAO= new PrisustvoDAO();
                if(!prisustvoDAO.checkEventJoining(integers[0].intValue(),nadimak)) {
                    prisustvoDAO.insertPrisustvo(integers[0].intValue(), nadimak);
                }else{
                    return "You already joined this event!";
                }
            }catch (Exception e){
                e.printStackTrace();
            }

            try{
                PrisustvoDAO.close();
            }catch (Exception e){
                e.printStackTrace();
            }
            return "Joining successfull";
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(EventsListActivity.this, s,
                    Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private class SetVisible extends AsyncTask<Integer,Void,String>{



        @Override
        protected String doInBackground(Integer... integers) {

            try{
                UserDAO userDao = new UserDAO();
                KorisnikEntity korisnikEntity = userDao.getUser(nadimak);
                /*if(korisnikEntity.getRang().equals(RangConstants.member)){
                    return "No authorities";
                }else{
                    DogadajDAO dogadajDAO = new DogadajDAO();
                    DogadajEntity dogadajEntity = dogadajDAO.getEvent(integers[0].intValue());
                    dogadajEntity.setVidljivost(true);
                    dogadajDAO.updateEvent(dogadajEntity);
                    return "Event now visible";
                }*/
            }catch (Exception e){
                e.printStackTrace();
            }

            try{
                PrisustvoDAO.close();
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(EventsListActivity.this, s,
                    Toast.LENGTH_SHORT).show();
            finish();
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}

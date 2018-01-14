package hr.fer.amigosi.guildbuild;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import hr.fer.amigosi.guildbuild.DAO.CehDAO;
import hr.fer.amigosi.guildbuild.entities.CehEntity;
import hr.fer.amigosi.guildbuild.entities.KorisnikEntity;

public class GuildMembersActivity extends AppCompatActivity {

    private TextView imeCeha;
    private LinearLayout layout1;
    private int sifraCeha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guild_members);

        imeCeha = (TextView) findViewById(R.id.GuildNameText2);
        layout1 = (LinearLayout) findViewById(R.id.guildMembers1);

        Intent pastIntent = getIntent();
        imeCeha.setText(pastIntent.getStringExtra(GuildDetailsActivity.EXTRA_MESSAGE4));
        imeCeha.setTextSize(35);
        imeCeha.setTextColor(Color.WHITE);
        sifraCeha = pastIntent.getIntExtra(GuildDetailsActivity.EXTRA_MESSAGE3,0);

        PopulateGuildMembers populateGuildMembers = new PopulateGuildMembers();
        populateGuildMembers.execute("");

    }

    private class PopulateGuildMembers extends AsyncTask<String,String, List<KorisnikEntity>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            layout1.removeAllViews();
        }

        @Override
        protected List<KorisnikEntity> doInBackground(String... strings) {
            try {
                CehDAO cehDAO = new CehDAO();
                List<KorisnikEntity> korisnikEntityList = cehDAO.getGuildMembers(sifraCeha);
                return korisnikEntityList;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<KorisnikEntity> korisnikEntityList) {
            for(KorisnikEntity korisnikEntity : korisnikEntityList) {
                TextView textView = new TextView(GuildMembersActivity.this);
                textView.setText(korisnikEntity.getNadimak()+ ", " + korisnikEntity.getRang());
                textView.setTextSize(35);
                textView.setTextColor(Color.WHITE);
                /*textView.setFocusable(false);
                textView.setClickable(true);
                textView.setOnClickListener(view -> {
                    Intent guildDetails = new Intent(GuildListActivity.this, GuildDetailsActivity.class);
                    guildDetails.putExtra(MainActivity.EXTRA_MESSAGE1, nickname);
                    guildDetails.putExtra(MainActivity.EXTRA_MESSAGE2, sifraCeha);
                    guildDetails.putExtra(GuildDetailsActivity.EXTRA_MESSAGE3, cehEntity.getSifraCeha());
                    startActivity(guildDetails);
                });*/
                layout1.addView(textView);
            }
        }
    }
}

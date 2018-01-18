package hr.fer.amigosi.guildbuild;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.List;

import hr.fer.amigosi.guildbuild.DAO.CehDAO;
import hr.fer.amigosi.guildbuild.DAO.UserDAO;
import hr.fer.amigosi.guildbuild.entities.CehEntity;
import hr.fer.amigosi.guildbuild.entities.KorisnikEntity;

public class GuildMembersActivity extends AppCompatActivity {

    public static final String NADIMAK_KORISNIKA_KOJI_POKRECE_ACTIVITY = "nadimakKorisnika";
    private TextView imeCeha;
    private LinearLayout layout1;
    private Integer sifraCeha;
    private Integer sifraTrazenogCeha;
    private String nadimak;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guild_members);

        imeCeha = (TextView) findViewById(R.id.GuildNameText2);
        layout1 = (LinearLayout) findViewById(R.id.guildMembers1);
        Intent pastIntent = getIntent();
        imeCeha.setText(pastIntent.getStringExtra(GuildDetailsActivity.EXTRA_MESSAGE4));
        imeCeha.setTextColor(Color.WHITE);

        Button giveUpLeadershipButton = findViewById(R.id.giveUpLeadershipButton);
        Button promoteDemoteButton = findViewById(R.id.promote_demoteMembersButton);
        giveUpLeadershipButton.setOnClickListener(view -> {
            Intent intent = new Intent(GuildMembersActivity.this, ConcedeActivity.class);
            intent.putExtra(MainActivity.EXTRA_MESSAGE1, nadimak);
            startActivity(intent);
        });
        promoteDemoteButton.setOnClickListener(view -> {
            Intent intent = new Intent(GuildMembersActivity.this, Promote_demoteActivity.class);
            intent.putExtra(MainActivity.EXTRA_MESSAGE1, nadimak);
            intent.putExtra(MainActivity.EXTRA_MESSAGE2, sifraCeha);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent pastIntent = getIntent();
        sifraTrazenogCeha = pastIntent.getIntExtra(GuildDetailsActivity.EXTRA_MESSAGE3,0);
        sifraCeha = pastIntent.getIntExtra(MainActivity.EXTRA_MESSAGE2, 0);
        nadimak = pastIntent.getStringExtra(MainActivity.EXTRA_MESSAGE1);
        Button giveUpLeadershipButton = findViewById(R.id.giveUpLeadershipButton);
        Button promoteDemoteButton = findViewById(R.id.promote_demoteMembersButton);
        if(sifraCeha != sifraTrazenogCeha) {
            giveUpLeadershipButton.setVisibility(View.GONE);
            promoteDemoteButton.setVisibility(View.GONE);
        }
        new PopulateGuildMembers().execute("");
    }

    @Override
    public void onBackPressed() {
        finish();
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
                List<KorisnikEntity> korisnikEntityList = cehDAO.getGuildMembers(sifraTrazenogCeha.toString());
                return korisnikEntityList;
            } catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                try {
                    CehDAO.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<KorisnikEntity> korisnikEntityList) {
            Button giveUpLeadershipButton = findViewById(R.id.giveUpLeadershipButton);
            Button promoteDemoteButton = findViewById(R.id.promote_demoteMembersButton);

            if(korisnikEntityList == null) {
                Toast.makeText(GuildMembersActivity.this, "Something went wrong... Try again", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }
            //Ako je korisnik sam u Cehu (leader) gumbi nemaju smisla
            if(korisnikEntityList.size() == 1) {
                giveUpLeadershipButton.setVisibility(View.GONE);
                promoteDemoteButton.setVisibility(View.GONE);
            }
            for(KorisnikEntity korisnikEntity : korisnikEntityList) {
                if(korisnikEntity.getNadimak().equals(nadimak)) {
                    if(korisnikEntity.getRang().equals(RangConstants.member)) {
                        giveUpLeadershipButton.setVisibility(View.GONE);
                        promoteDemoteButton.setVisibility(View.GONE);
                    }
                    if (korisnikEntity.getRang().equals(RangConstants.coordinator)){
                        giveUpLeadershipButton.setVisibility(View.GONE);
                    }
                }
                TextView textView = new TextView(GuildMembersActivity.this);
                textView.setText(korisnikEntity.getNadimak()+ ", " + korisnikEntity.getRang());
                textView.setTextSize(35);
                textView.setTextColor(Color.WHITE);
                textView.setOnClickListener(view -> {
                    Intent intent = new Intent(GuildMembersActivity.this, UserProfileActivity.class);
                    intent.putExtra(MainActivity.EXTRA_MESSAGE1, korisnikEntity.getNadimak());
                    intent.putExtra(NADIMAK_KORISNIKA_KOJI_POKRECE_ACTIVITY, nadimak);
                    startActivity(intent);
                });
                layout1.addView(textView);
            }
        }
    }
}

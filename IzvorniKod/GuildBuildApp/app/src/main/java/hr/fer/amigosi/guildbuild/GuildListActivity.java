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

import hr.fer.amigosi.guildbuild.DAO.CehDAO;
import hr.fer.amigosi.guildbuild.entities.CehEntity;

/**
 *  @author Filip Kerman
 *  @version v1.0 30.12.2017
 */
public class GuildListActivity extends AppCompatActivity {
    public static final String IS_FROM_GUILD_LIST_ACTIVITY = "fromGuildList";
    private LinearLayout guildList;
    private String nickname;
    private String sifraCeha;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guild_list);

        guildList = findViewById(R.id.guildList);
        Intent pastIntent = getIntent();
        nickname = pastIntent.getStringExtra(MainActivity.EXTRA_MESSAGE1);
        sifraCeha = pastIntent.getStringExtra(MainActivity.EXTRA_MESSAGE2);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new PopulateGuildList().execute();
    }

    private class PopulateGuildList extends AsyncTask<Void,Void, List<CehEntity>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            guildList.removeAllViews();
        }

        @Override
        protected List<CehEntity> doInBackground(Void... voids) {
            try {
                CehDAO cehDAO = new CehDAO();
                List<CehEntity> cehEntityList = cehDAO.getAllGuilds();
                return cehEntityList;
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
        protected void onPostExecute(List<CehEntity> cehEntityList) {
            if(cehEntityList.isEmpty()) {
                Toast.makeText(GuildListActivity.this, "No guilds to show", Toast.LENGTH_SHORT).show();
                finish();
            }
            for(CehEntity cehEntity : cehEntityList) {
                TextView textView = new TextView(GuildListActivity.this);
                textView.setText(cehEntity.getNaziv());
                textView.setTextSize(35);
                textView.setTextColor(Color.WHITE);
                textView.setFocusable(false);
                textView.setClickable(true);
                textView.setOnClickListener(view -> {
                    Intent guildDetails = new Intent(GuildListActivity.this, GuildDetailsActivity.class);
                    guildDetails.putExtra(MainActivity.EXTRA_MESSAGE1, nickname);
                    guildDetails.putExtra(MainActivity.EXTRA_MESSAGE2, sifraCeha);
                    guildDetails.putExtra(GuildDetailsActivity.EXTRA_MESSAGE3, cehEntity.getSifraCeha());
                    guildDetails.putExtra(IS_FROM_GUILD_LIST_ACTIVITY, true);
                    startActivity(guildDetails);
                });
                guildList.addView(textView);
            }
        }
    }
}

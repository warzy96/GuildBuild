package hr.fer.amigosi.guildbuild;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.EventLog;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

import hr.fer.amigosi.guildbuild.DAO.CiljDAO;
import hr.fer.amigosi.guildbuild.DAO.PodciljDAO;
import hr.fer.amigosi.guildbuild.entities.CiljEntity;
import hr.fer.amigosi.guildbuild.entities.PodciljEntity;

public class GoalsListActivity extends AppCompatActivity {

    public static final String SIFRA_CILJA = "sifra_cilja";

    private int sifraDogadaja;
    private String nadimak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goals_list);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new PopulateViewWithGoals().execute();
    }

    private class PopulateViewWithGoals extends AsyncTask<Void, Void, List<CiljEntity>> {
        @Override
        protected void onPreExecute() {
            Intent intent = getIntent();
            sifraDogadaja = intent.getIntExtra(EventsListActivity.SIFRA_DOGADAJA,0);
            nadimak = intent.getStringExtra(MainActivity.EXTRA_MESSAGE1);
        }

        @Override
        protected List<CiljEntity> doInBackground(Void... voids) {
            List<CiljEntity> ciljevi = new ArrayList<>();
            try {
                CiljDAO ciljDAO = new CiljDAO();
                ciljevi = ciljDAO.getAllGoalsForEvent(sifraDogadaja);
            }
            catch(Exception e) {

            }
            finally {
                try {
                    CiljDAO.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return ciljevi;
        }

        @Override
        protected void onPostExecute(List<CiljEntity> ciljEntities) {
            if(ciljEntities.isEmpty()) {
                Toast.makeText(GoalsListActivity.this, "No goals to show",
                        Toast.LENGTH_SHORT).show();
                finish();
            }
            else {
                LinearLayout linearLayout = findViewById(R.id.GoalLayout);
                linearLayout.removeAllViews();
                for(CiljEntity ciljEntity : ciljEntities) {
                    TextView goalName = new TextView(GoalsListActivity.this);
                    if(ciljEntity.isIspunjenost()){
                        goalName.setTextColor(Color.GREEN);
                    }else{
                        goalName.setTextColor(Color.RED);
                    }
                    goalName.setText(ciljEntity.getNazivCilja());
                    goalName.setTextSize(35);

                    goalName.setOnClickListener(View -> {
                        Intent intent = new Intent(GoalsListActivity.this, SubgoalsListActivity.class);
                        intent.putExtra(GoalsListActivity.SIFRA_CILJA,ciljEntity.getSifraCilja());
                        intent.putExtra(EventsListActivity.SIFRA_DOGADAJA, sifraDogadaja);
                        intent.putExtra(MainActivity.EXTRA_MESSAGE1,nadimak);
                        startActivity(intent);
                    });

                    linearLayout.addView(goalName);
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

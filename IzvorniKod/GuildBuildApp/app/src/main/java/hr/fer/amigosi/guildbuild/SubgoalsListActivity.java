package hr.fer.amigosi.guildbuild;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.constraint.solver.Goal;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import hr.fer.amigosi.guildbuild.DAO.PodciljDAO;
import hr.fer.amigosi.guildbuild.DAO.UserDAO;
import hr.fer.amigosi.guildbuild.entities.ObrazacEntity;
import hr.fer.amigosi.guildbuild.entities.PodciljEntity;

public class SubgoalsListActivity extends AppCompatActivity {

    private int sifraDogadaja;
    private int sifraCilja;
    private String nadimak;
    private int sifraTrazenogCeha;
    private String sifreCehova;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subgoals_list);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        new PopulateViewWithSubgoals().execute();
    }

    private class PopulateViewWithSubgoals extends AsyncTask<Void, Void, List<PodciljEntity>> {
        @Override
        protected void onPreExecute() {
            Intent intent = getIntent();
            sifraDogadaja = intent.getIntExtra(EventsListActivity.SIFRA_DOGADAJA, 0);
            sifraCilja = intent.getIntExtra(GoalsListActivity.SIFRA_CILJA,0);
            nadimak = intent.getStringExtra(MainActivity.EXTRA_MESSAGE1);
            sifraTrazenogCeha=intent.getIntExtra(GuildDetailsActivity.EXTRA_MESSAGE3,0);
        }

        @Override
        protected List<PodciljEntity> doInBackground(Void... voids) {
            List<PodciljEntity> podciljevi = new ArrayList<>();
            try {
                PodciljDAO podciljDAO = new PodciljDAO();
                podciljevi = podciljDAO.getAllSubgoalsForGoal(sifraCilja);
                UserDAO userDAO = new UserDAO();
                sifreCehova = userDAO.getSifreCehova(nadimak);
            }
            catch(Exception e) {

            }
            finally {
                try {
                    PodciljDAO.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return podciljevi;
        }

        @Override
        protected void onPostExecute(List<PodciljEntity> podciljEntities) {
            if(podciljEntities.isEmpty()) {
                Toast.makeText(SubgoalsListActivity.this, "No subgoals to show",
                        Toast.LENGTH_SHORT).show();
                finish();
            }
            else {
                LinearLayout linearLayout = findViewById(R.id.SubgoalLayout);
                linearLayout.removeAllViews();
                for(PodciljEntity podciljEntity : podciljEntities) {
                    TextView subgoalName = new TextView(SubgoalsListActivity.this);
                    if(podciljEntity.isIspunjenost()) {
                        subgoalName.setTextColor(Color.GREEN);
                    }else{
                        subgoalName.setTextColor(Color.RED);

                    }
                    subgoalName.setText(podciljEntity.getNazivPodcilja());
                    subgoalName.setTextSize(35);

                    if(sifreCehova!=null) {
                        if (sifreCehova.contains(String.valueOf(sifraTrazenogCeha))) {

                            subgoalName.setOnClickListener(View -> {
                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SubgoalsListActivity.this);
                                alertDialog.setTitle("Subgoal finished?");
                                alertDialog.setCancelable(false);
                                alertDialog.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                });
                                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        new FinishSubgoal().execute(podciljEntity.getSifraPodcilja());
                                    }
                                });
                                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                });
                                alertDialog.show();
                            });
                        }
                    }
                    linearLayout.addView(subgoalName);
                }
            }
        }
    }

    private class FinishSubgoal extends AsyncTask<Integer,Void,String>{

        Boolean isSuccess=false;

        @Override
        protected String doInBackground(Integer... integers) {
            try{
                PodciljDAO podciljDAO = new PodciljDAO();
                PodciljEntity podciljEntity = podciljDAO.getSubgoal(integers[0].intValue());
                podciljEntity.setIspunjenost(true);
                podciljDAO.updateSubgoal(podciljEntity);
                isSuccess=true;
                podciljDAO.updateGoalsAndEvents();
            }catch (Exception e){
                e.printStackTrace();
            }

            try{
                PodciljDAO.close();
            }catch (Exception e){
                e.printStackTrace();
            }
            return "Subgoal Finished!";
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(SubgoalsListActivity.this, s, Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}

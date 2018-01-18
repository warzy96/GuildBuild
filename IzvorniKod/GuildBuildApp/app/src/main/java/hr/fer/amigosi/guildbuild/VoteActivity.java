package hr.fer.amigosi.guildbuild;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import hr.fer.amigosi.guildbuild.DAO.RangDAO;
import hr.fer.amigosi.guildbuild.DAO.UserDAO;
import hr.fer.amigosi.guildbuild.DAO.VoteDAO;
import hr.fer.amigosi.guildbuild.entities.KorisnikEntity;
import hr.fer.amigosi.guildbuild.entities.VoteEntity;

public class VoteActivity extends AppCompatActivity {
    private String nickname;
    private Integer sifraCeha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);

        Intent intent = getIntent();
        nickname = intent.getStringExtra(MainActivity.EXTRA_MESSAGE1);
        sifraCeha = intent.getIntExtra(MainActivity.EXTRA_MESSAGE2, 0);
    }


    @Override
    protected void onResume() {
        super.onResume();
        DisplayVotingOptions displayVotingOptions = new DisplayVotingOptions();
        displayVotingOptions.execute();
    }

    private class DisplayVotingOptions extends AsyncTask<String, String, List<VoteEntity>>{
        Boolean jeGlasao = false;

        String tmp = "";
        @Override
        protected List<VoteEntity> doInBackground(String... strings) {

            try{
                VoteDAO voteDAO = new VoteDAO();
                VoteEntity voteEntity = voteDAO.getVoter(nickname);
                jeGlasao = voteEntity.isGlasao();
                List<VoteEntity> voteEntities = voteDAO.loadAllCoordinatorsForGivenGuild(sifraCeha.toString());
                return voteEntities;

            }catch (Exception ex){
                ex.printStackTrace();
                return null;
            }finally {
                try {
                    VoteDAO.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }

        @Override
        protected void onPostExecute(List<VoteEntity> voteEntities) {
            if(voteEntities == null){
                Toast.makeText(getApplicationContext(), "No voting options!", Toast.LENGTH_LONG).show();

            }
            else{
                LinearLayout voteLayout = findViewById(R.id.voteLayout);
                LinearLayout gornjiLayout = findViewById(R.id.gornjiLayout);
                voteLayout.removeAllViews();

                if(jeGlasao){
                    TextView waitingVotes = new TextView(getApplicationContext());
                    waitingVotes.setText("Waiting for other coordinators to vote...");
                    waitingVotes.setTextSize(30);
                    waitingVotes.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    waitingVotes.setTextColor(Color.WHITE);
                    voteLayout.addView(waitingVotes);
                }
                else{

                    for(VoteEntity voteEntity : voteEntities) {
                        if((voteEntity.getNadimak().equals(nickname)) || (voteEntity.getBrojGlasova() == -1))
                            continue;

                        TextView nicknameView = new TextView(getApplicationContext());
                        Button btnVoteFor = new Button(new ContextThemeWrapper(getApplicationContext(), R.style.button_style), null, R.style.button_style);

                        nicknameView.setText(voteEntity.getNadimak());
                        nicknameView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        nicknameView.setTextColor(Color.WHITE);
                        nicknameView.setTextSize(25);

                        btnVoteFor.setText("Vote");
                        btnVoteFor.setTextSize(20);
                        btnVoteFor.setTextColor(Color.WHITE);

                        btnVoteFor.setOnClickListener(view -> {
                            VoteFor voteFor = new VoteFor();
                            voteFor.execute(voteEntity);
                        });
                        LinearLayout horizontalView = new LinearLayout(VoteActivity.this);
                        horizontalView.setOrientation(LinearLayout.VERTICAL);
                        horizontalView.setMinimumWidth(gornjiLayout.getWidth());

                        horizontalView.addView(nicknameView);
                        horizontalView.addView(btnVoteFor);

                        voteLayout.addView(horizontalView);
                    }
                }
            }
        }
    }

    private class VoteFor extends AsyncTask<VoteEntity,String,String>{
        Boolean sviGlasali = false;
        Boolean oneLeader= false;
        @Override
        protected String doInBackground(VoteEntity... voteEntities) {
            VoteEntity voteEntity = voteEntities[0];
            try{
                VoteDAO voteDAO = new VoteDAO();
                voteDAO.incrementBrGlasova(voteEntity);
                voteDAO.markIsGlasao(nickname);
                sviGlasali = voteDAO.isFinished(sifraCeha.toString());

                if(sviGlasali){
                    List<VoteEntity> maxVoteEntities = voteDAO.maxVotes(sifraCeha.toString());
                    if(maxVoteEntities.size() > 1){
                        oneLeader=false;
                    }else{
                        oneLeader=true;
                        RangDAO rangDAO = new RangDAO();
                        String nadimakLeadera = maxVoteEntities.get(0).getNadimak();
                        if(nadimakLeadera != null){
                            rangDAO.setLeader(nadimakLeadera, sifraCeha.toString());
                            voteDAO.votingFinished(sifraCeha.toString());
                        }

                    }
                }
                return "Voted successfully";

            }catch(Exception e){
                e.printStackTrace();
            }
            finally {
                try {
                    VoteDAO.close();
                    UserDAO.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return "Voting unsuccessful";
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            if(sviGlasali && oneLeader){

                Intent intent = new Intent(VoteActivity.this, HomeActivity.class);
                intent.putExtra(MainActivity.EXTRA_MESSAGE1, nickname);
                intent.putExtra(MainActivity.EXTRA_MESSAGE2, sifraCeha);
                startActivity(intent);
            }
            else{
                DisplayVotingOptions displayVotingOptions = new DisplayVotingOptions();
                displayVotingOptions.execute();
            }
        }
    }

    public void UserProfile(View view){
        Intent intent = new Intent(VoteActivity.this, UserProfileActivity.class);
        intent.putExtra(MainActivity.EXTRA_MESSAGE1, nickname);
        intent.putExtra(MainActivity.EXTRA_MESSAGE2, sifraCeha);
        startActivity(intent);
    }


}

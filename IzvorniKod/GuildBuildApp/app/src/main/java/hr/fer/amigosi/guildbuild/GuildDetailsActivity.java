package hr.fer.amigosi.guildbuild;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import hr.fer.amigosi.guildbuild.DAO.CehDAO;
import hr.fer.amigosi.guildbuild.DAO.UserDAO;
import hr.fer.amigosi.guildbuild.entities.CehEntity;
import hr.fer.amigosi.guildbuild.entities.KorisnikEntity;

public class GuildDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE3 = "sifraTrazenogCeha";
    public static final String EXTRA_MESSAGE4 = "imeCeha";


    private TextView guildName;
    private TextView guildDesc;
    private Button btnApply;
    private Button btnSeeMembers;
    private Button btnSeeEvents;
    private Button btnLeaveGuild;

    private int sifraKorisnikovogCeha;
    private int sifraTrazenogCeha;
    private String nadimak;
    private String imeCeha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guild_details);

        guildName = (TextView) findViewById(R.id.GuildNameText);
        guildDesc = (TextView) findViewById(R.id.GuildOpis);
        btnApply = (Button) findViewById(R.id.btnApply);
        btnSeeMembers = (Button) findViewById(R.id.btnSeeGuildMem);
        btnSeeEvents = (Button) findViewById(R.id.btnSeeEvents);
        btnLeaveGuild = (Button) findViewById(R.id.btnLeaveGuild);

        if(sifraKorisnikovogCeha==sifraTrazenogCeha){
            btnApply.setVisibility(View.GONE);
        }else{
            btnLeaveGuild.setVisibility(View.GONE);
        }



        btnApply.setOnClickListener(view -> {
            if(sifraKorisnikovogCeha!=0){
                Toast.makeText(GuildDetailsActivity.this,
                        "You are already in guild!", Toast.LENGTH_SHORT).show();
            }else{
                Intent form = new Intent(GuildDetailsActivity.this,FormApplicationActivity.class);
                form.putExtra(MainActivity.EXTRA_MESSAGE1,nadimak);
                form.putExtra(GuildDetailsActivity.EXTRA_MESSAGE3,sifraTrazenogCeha);
                form.putExtra(GuildDetailsActivity.EXTRA_MESSAGE4,imeCeha);
                startActivity(form);
            }
        });

        btnSeeMembers.setOnClickListener(view -> {
            CheckMembers checkMembers = new CheckMembers();
            checkMembers.execute("");
        });

        btnLeaveGuild.setOnClickListener(view ->{
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("Are you sure you want to leave this guild?");
            alertDialog.setCancelable(false);
            alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    new LeaveGuild().execute();
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

        new IsRequestsButtonVisible().execute();

        Button requestsButton = findViewById(R.id.JoinRequestsButton);
        requestsButton.setOnClickListener(View -> {
            Intent intent = new Intent(GuildDetailsActivity.this, UserRequestsForGuild.class);
            intent.putExtra(MainActivity.EXTRA_MESSAGE2, sifraKorisnikovogCeha);
            startActivity(intent);
        });

        new IsNewEventButtonVisible().execute();
        Button newEventButton = findViewById(R.id.btnAddEvent);
        newEventButton.setOnClickListener(View -> {
            Intent intent = new Intent(GuildDetailsActivity.this, AddEventActivity.class);
            intent.putExtra(MainActivity.EXTRA_MESSAGE2, sifraKorisnikovogCeha);
            startActivity(intent);
        });

        new IsNewGoalButtonVisible().execute();
        Button newGoalButton = findViewById(R.id.btnAddGoal);
        newGoalButton.setOnClickListener(View -> {
            Intent intent = new Intent(GuildDetailsActivity.this, AddGoalActivity.class);
            intent.putExtra(MainActivity.EXTRA_MESSAGE2, sifraKorisnikovogCeha);
            startActivity(intent);
        });

        new IsNewSubgoalButtonVisible().execute();
        Button newSubgoalButton = findViewById(R.id.btnAddSubgoal);
        newSubgoalButton.setOnClickListener(View -> {
            Intent intent = new Intent(GuildDetailsActivity.this, AddSubgoalActivity.class);
            intent.putExtra(MainActivity.EXTRA_MESSAGE2, sifraKorisnikovogCeha);
            startActivity(intent);
        });
    }

    public class FillNameAndDesc extends AsyncTask<String,String,CehEntity>{
        @Override
        protected CehEntity doInBackground(String... strings) {
            try{
                CehDAO cehDAO = new CehDAO();
                CehEntity ceh = cehDAO.getGuild(sifraTrazenogCeha);
                imeCeha = ceh.getNaziv();
                return ceh;
            }catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(CehEntity cehEntity) {

            //TODO: Throws NullPointerException zbog CehEntity.getNaziv() [cehEntity je null]
            //Samo kad se s GuildListActivity dolazi na GuildDetailsActivity
            guildName.setText(cehEntity.getNaziv());
            //Overwrites xml text size
            //guildName.setTextSize(35);
            guildName.setTextColor(Color.WHITE);
            guildDesc.setText(cehEntity.getOpis());
            //Overwrites xml text size
            //guildDesc.setTextSize(35);
            guildDesc.setTextColor(Color.WHITE);
        }
    }

    private class CheckMembers extends AsyncTask<String, String, Boolean> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Boolean doInBackground(String... strings) {
            try {
                CehDAO cehDAO = new CehDAO();
                boolean guildMembers = cehDAO.checkIfMemExists(sifraTrazenogCeha);
                return guildMembers;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return false;
        }

        @Override
        protected void onPostExecute(Boolean guildMembersExists) {
            if(!guildMembersExists) {
                Toast.makeText(GuildDetailsActivity.this, "No members in this guild.", Toast.LENGTH_SHORT).show();
            }else{
                Intent guildMembers = new Intent(GuildDetailsActivity.this,GuildMembersActivity.class);
                guildMembers.putExtra(GuildDetailsActivity.EXTRA_MESSAGE3,sifraTrazenogCeha);
                guildMembers.putExtra(GuildDetailsActivity.EXTRA_MESSAGE4,imeCeha);
                guildMembers.putExtra(MainActivity.EXTRA_MESSAGE1, nadimak);
                startActivity(guildMembers);
            }
        }
    }

    private class LeaveGuild extends AsyncTask<Void, Void, String> {
        private String message;
        private boolean success = false;
        @Override
        protected String doInBackground(Void... v) {
            try {
                if(DatabaseConnection.getConnection() == null) {
                    message = "Check Your Internet Access!";
                }
                UserDAO userDAO = new UserDAO();
                KorisnikEntity korisnikEntity = userDAO.getUser(nadimak);
                korisnikEntity.setSifraCeha(0);
                korisnikEntity.setRang(null);
                korisnikEntity.setStatusPrijave(false);
                userDAO.updateUser(korisnikEntity);
                message = "You left the guild.";
                success = true;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(GuildDetailsActivity.this,
                    message, Toast.LENGTH_SHORT).show();
            if(success) {
                Intent returnToHomeScreen =
                        new Intent(GuildDetailsActivity.this, HomeActivity.class);
                returnToHomeScreen.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(returnToHomeScreen);
            }

        }
    }

    private class IsRequestsButtonVisible extends AsyncTask<Void, Void, KorisnikEntity> {
        String result = "";
        boolean success = false;
        @Override
        protected KorisnikEntity doInBackground(Void... voids) {
            KorisnikEntity korisnikEntity = new KorisnikEntity();
            try {
                UserDAO userDAO = new UserDAO();
                korisnikEntity = userDAO.getUser(nadimak);
                success = true;
            }
            catch(Exception e) {
                result = "Check your internet connection";
            }
            return korisnikEntity;
        }

        @Override
        protected void onPostExecute(KorisnikEntity korisnikEntity) {
            if(!success) {
                Toast.makeText(GuildDetailsActivity.this, result, Toast.LENGTH_SHORT).show();
            }
            else {
                if(!korisnikEntity.getRang().equals(RangConstants.leader)
                        && !korisnikEntity.getRang().equals(RangConstants.coordinator)) {
                    Button requestsButton = findViewById(R.id.JoinRequestsButton);
                    requestsButton.setVisibility(View.GONE);
                }
            }

        }
    }

    private class IsNewEventButtonVisible extends AsyncTask<Void, Void, KorisnikEntity> {
        String result = "";
        boolean success = false;
        @Override
        protected KorisnikEntity doInBackground(Void... voids) {
            KorisnikEntity korisnikEntity = new KorisnikEntity();
            try {
                UserDAO userDAO = new UserDAO();
                korisnikEntity = userDAO.getUser(nadimak);
                success = true;
            }
            catch(Exception e) {
                result = "Check your internet connection";
            }
            return korisnikEntity;
        }

        @Override
        protected void onPostExecute(KorisnikEntity korisnikEntity) {
            if(!success) {
                Toast.makeText(GuildDetailsActivity.this, result, Toast.LENGTH_SHORT).show();
            }
            else {
                if(!korisnikEntity.getRang().equals(RangConstants.leader)
                        && !korisnikEntity.getRang().equals(RangConstants.coordinator)) {
                    Button newEventButton = findViewById(R.id.btnAddEvent);
                    newEventButton.setVisibility(View.GONE);
                }
            }

        }
    }

    private class IsNewGoalButtonVisible extends AsyncTask<Void, Void, KorisnikEntity> {
        String result = "";
        boolean success = false;
        @Override
        protected KorisnikEntity doInBackground(Void... voids) {
            KorisnikEntity korisnikEntity = new KorisnikEntity();
            try {
                UserDAO userDAO = new UserDAO();
                korisnikEntity = userDAO.getUser(nadimak);
                success = true;
            }
            catch(Exception e) {
                result = "Check your internet connection";
            }
            return korisnikEntity;
        }

        @Override
        protected void onPostExecute(KorisnikEntity korisnikEntity) {
            if(!success) {
                Toast.makeText(GuildDetailsActivity.this, result, Toast.LENGTH_SHORT).show();
            }
            else {
                if(!korisnikEntity.getRang().equals(RangConstants.leader)) {
                    Button newEventButton = findViewById(R.id.btnAddGoal);
                    newEventButton.setVisibility(View.GONE);
                }
            }

        }
    }

    private class IsNewSubgoalButtonVisible extends AsyncTask<Void, Void, KorisnikEntity> {
        String result = "";
        boolean success = false;
        @Override
        protected KorisnikEntity doInBackground(Void... voids) {
            KorisnikEntity korisnikEntity = new KorisnikEntity();
            try {
                UserDAO userDAO = new UserDAO();
                korisnikEntity = userDAO.getUser(nadimak);
                success = true;
            }
            catch(Exception e) {
                result = "Check your internet connection";
            }
            return korisnikEntity;
        }

        @Override
        protected void onPostExecute(KorisnikEntity korisnikEntity) {
            if(!success) {
                Toast.makeText(GuildDetailsActivity.this, result, Toast.LENGTH_SHORT).show();
            }
            else {
                if(!korisnikEntity.getRang().equals(RangConstants.coordinator)) {
                    Button newEventButton = findViewById(R.id.btnAddSubgoal);
                    newEventButton.setVisibility(View.GONE);
                }
            }

        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent pastIntent = getIntent();
        nadimak = pastIntent.getStringExtra(MainActivity.EXTRA_MESSAGE1);
        sifraKorisnikovogCeha = pastIntent.getIntExtra(MainActivity.EXTRA_MESSAGE2,0);
        sifraTrazenogCeha = pastIntent.getIntExtra(GuildDetailsActivity.EXTRA_MESSAGE3,0);

        FillNameAndDesc fillNameAndDesc = new FillNameAndDesc();
        fillNameAndDesc.execute("");
    }
}

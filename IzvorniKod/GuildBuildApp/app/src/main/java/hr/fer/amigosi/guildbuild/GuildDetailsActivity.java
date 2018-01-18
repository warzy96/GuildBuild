package hr.fer.amigosi.guildbuild;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import hr.fer.amigosi.guildbuild.DAO.CehDAO;
import hr.fer.amigosi.guildbuild.DAO.IgraDAO;
import hr.fer.amigosi.guildbuild.DAO.UserDAO;
import hr.fer.amigosi.guildbuild.DAO.VoteDAO;
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

    private String sifraKorisnikovogCeha;
    private Integer sifraTrazenogCeha;
    private String nadimak;
    private String imeCeha;
    private TreeMap<Integer, Integer> pozicijaSifraCeha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guild_details);
        LinearLayout everythingLayout = findViewById(R.id.hideGuildDetails);
        everythingLayout.setVisibility(View.GONE);
        Spinner chooseGuildSpinner = findViewById(R.id.chooseGuildSpinner);
        TextView textView = findViewById(R.id.chooseGuildTextView);

        textView.setVisibility(View.GONE);
        chooseGuildSpinner.setVisibility(View.GONE);
        ProgressBar progressBar = findViewById(R.id.progressbar);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        progressBar.setVisibility(View.VISIBLE);
        pozicijaSifraCeha = new TreeMap<>();

        guildName = (TextView) findViewById(R.id.GuildNameText);
        guildDesc = (TextView) findViewById(R.id.GuildOpis);
        btnApply = (Button) findViewById(R.id.btnApply);
        btnSeeMembers = (Button) findViewById(R.id.btnSeeGuildMem);
        btnSeeEvents = (Button) findViewById(R.id.btnSeeEvents);
        btnLeaveGuild = (Button) findViewById(R.id.btnLeaveGuild);

        Intent pastIntent = getIntent();
        nadimak = pastIntent.getStringExtra(MainActivity.EXTRA_MESSAGE1);
        sifraKorisnikovogCeha = pastIntent.getStringExtra(MainActivity.EXTRA_MESSAGE2);

        btnApply.setOnClickListener(view -> {

                Intent form = new Intent(GuildDetailsActivity.this,FormApplicationActivity.class);
                form.putExtra(MainActivity.EXTRA_MESSAGE1,nadimak);
                form.putExtra(GuildDetailsActivity.EXTRA_MESSAGE3,sifraTrazenogCeha);
                form.putExtra(GuildDetailsActivity.EXTRA_MESSAGE4,imeCeha);
                startActivity(form);
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


        Button requestsButton = findViewById(R.id.JoinRequestsButton);
        requestsButton.setOnClickListener(View -> {
            Intent intent = new Intent(GuildDetailsActivity.this, UserRequestsForGuild.class);
            intent.putExtra(MainActivity.EXTRA_MESSAGE2, sifraTrazenogCeha);
            startActivity(intent);
        });

        Button newEventButton = findViewById(R.id.btnAddEvent);
        newEventButton.setOnClickListener(View -> {
            Intent intent = new Intent(GuildDetailsActivity.this, AddEventActivity.class);
            intent.putExtra(MainActivity.EXTRA_MESSAGE2, sifraTrazenogCeha);
            startActivity(intent);
        });

        Button newGoalButton = findViewById(R.id.btnAddGoal);
        newGoalButton.setOnClickListener(View -> {
            Intent intent = new Intent(GuildDetailsActivity.this, AddGoalActivity.class);
            intent.putExtra(MainActivity.EXTRA_MESSAGE2, sifraTrazenogCeha);
            startActivity(intent);
        });

        Button newSubgoalButton = findViewById(R.id.btnAddSubgoal);
        newSubgoalButton.setOnClickListener(View -> {
            Intent intent = new Intent(GuildDetailsActivity.this, AddSubgoalActivity.class);
            intent.putExtra(MainActivity.EXTRA_MESSAGE2, sifraTrazenogCeha);
            startActivity(intent);
        });

        btnSeeEvents.setOnClickListener(View -> {
            Intent intent = new Intent(GuildDetailsActivity.this, EventsListActivity.class);
            intent.putExtra(MainActivity.EXTRA_MESSAGE1,nadimak);
            intent.putExtra(MainActivity.EXTRA_MESSAGE2, sifraKorisnikovogCeha);
            intent.putExtra(GuildDetailsActivity.EXTRA_MESSAGE3,sifraTrazenogCeha);
            startActivity(intent);
        });



    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent pastIntent = getIntent();
        TextView textView = findViewById(R.id.chooseGuildTextView);
        nadimak = pastIntent.getStringExtra(MainActivity.EXTRA_MESSAGE1);
        sifraKorisnikovogCeha = pastIntent.getStringExtra(MainActivity.EXTRA_MESSAGE2);
        Spinner chooseGuildSpinner = findViewById(R.id.chooseGuildSpinner);

        if(pastIntent.getBooleanExtra(GuildListActivity.IS_FROM_GUILD_LIST_ACTIVITY, false)) {
            chooseGuildSpinner.setVisibility(View.GONE);
            textView.setVisibility(View.GONE);
            sifraTrazenogCeha = pastIntent.getIntExtra(GuildDetailsActivity.EXTRA_MESSAGE3, 0);

            new FillNameAndDesc().execute("");

            Boolean isInCeh = false;
            for(String temp : sifraKorisnikovogCeha.split(",")) {
                if(temp.equals(sifraTrazenogCeha.toString())) {
                    new IsRequestsButtonVisible().execute();
                    new IsNewEventButtonVisible().execute();
                    new IsNewGoalButtonVisible().execute();
                    new IsNewSubgoalButtonVisible().execute();
                    new IsApplyButtonVisible().execute();
                    isInCeh = true;
                }
            }
            if(!isInCeh) {
                Button newEventButton = findViewById(R.id.btnAddEvent);
                Button newSubgoalButton = findViewById(R.id.btnAddSubgoal);
                Button newGoalButton = findViewById(R.id.btnAddGoal);
                Button userRequests = findViewById(R.id.JoinRequestsButton);
                userRequests.setVisibility(View.GONE);
                newEventButton.setVisibility(View.GONE);
                newSubgoalButton.setVisibility(View.GONE);
                newGoalButton.setVisibility(View.GONE);
                btnLeaveGuild.setVisibility(View.GONE);
                LinearLayout everythingLayout = findViewById(R.id.hideGuildDetails);
                ProgressBar progressBar = findViewById(R.id.progressbar);
                progressBar.setVisibility(View.GONE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                everythingLayout.setVisibility(View.VISIBLE);
            }

        }
        else {
            if(sifraKorisnikovogCeha != null) {
                new PopulateSpinnerWithGuildNames().execute();
            }
        }

        chooseGuildSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                sifraTrazenogCeha = pozicijaSifraCeha.get(adapterView.getSelectedItemPosition());
                new FillNameAndDesc().execute("");
                new IsRequestsButtonVisible().execute();
                new IsNewEventButtonVisible().execute();
                new IsNewGoalButtonVisible().execute();
                new IsNewSubgoalButtonVisible().execute();

                if(sifraKorisnikovogCeha.contains(sifraTrazenogCeha.toString())){
                    btnApply.setVisibility(View.GONE);
                }else{
                    btnLeaveGuild.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(GuildDetailsActivity.this, "You have to choose a guild!"
                        , Toast.LENGTH_SHORT).show();
            }
        });

    }

    private class PopulateSpinnerWithGuildNames extends AsyncTask<Void, Void, List<String>> {

        @Override
        protected ArrayList<String> doInBackground(Void... voids) {
            ArrayList<String> guildNames = new ArrayList<>();
            try {
                int i = 0;
                CehDAO cehDAO = new CehDAO();
                for(String sifCeh : sifraKorisnikovogCeha.split(",")) {
                    String nazivCeha = cehDAO.getGuild(Integer.parseInt(sifCeh)).getNaziv();
                    guildNames.add(nazivCeha);
                    pozicijaSifraCeha.put(i, Integer.parseInt(sifCeh));
                    i++;
                }
                CehDAO.close();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return guildNames;
        }

        @Override
        protected void onPostExecute(List<String> strings) {
            Spinner chooseGuildSpinner = findViewById(R.id.chooseGuildSpinner);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(GuildDetailsActivity.this,
                    android.R.layout.simple_spinner_item, strings);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            chooseGuildSpinner.setAdapter(adapter);
            TextView textView = findViewById(R.id.chooseGuildTextView);
            textView.setVisibility(View.VISIBLE);
            chooseGuildSpinner.setVisibility(View.VISIBLE);
        }
    }

    public class FillNameAndDesc extends AsyncTask<String,String,CehEntity>{
        @Override
        protected CehEntity doInBackground(String... strings) {
            try{
                CehDAO cehDAO = new CehDAO();
                CehEntity ceh = cehDAO.getGuild(sifraTrazenogCeha);
                imeCeha = ceh.getNaziv();
                CehDAO.close();
                return ceh;
            }catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(CehEntity cehEntity) {

            guildName.setText(cehEntity.getNaziv());
            guildName.setTextColor(Color.WHITE);
            guildDesc.setText(cehEntity.getOpis());
            guildDesc.setTextColor(Color.WHITE);
        }
    }

    private class CheckMembers extends AsyncTask<String, String, Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {
            try {
                CehDAO cehDAO = new CehDAO();
                boolean guildMembers = cehDAO.checkIfMemExists(sifraTrazenogCeha.toString());
                CehDAO.close();
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
                guildMembers.putExtra(MainActivity.EXTRA_MESSAGE2, sifraKorisnikovogCeha);
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


                if(korisnikEntity.getRang().equals(RangConstants.leader)){
                    VoteDAO voteDAO = new VoteDAO();
                    voteDAO.insertAllCoordinatorsFromGuildIntoVote(korisnikEntity.getSifraCeha());

                }

                korisnikEntity.setSifraCeha(null);
                korisnikEntity.setRang(null);
                korisnikEntity.setStatusPrijave(false);
                userDAO.updateUser(korisnikEntity);
                message = "You left the guild.";
                success = true;
                UserDAO.close();
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

    private class IsApplyButtonVisible extends AsyncTask<Void, Void, KorisnikEntity> {
        boolean success = false;
        @Override
        protected KorisnikEntity doInBackground(Void... voids) {
            try {
                CehDAO cehDAO = new CehDAO();
                CehEntity cehEntity = cehDAO.getGuild(sifraTrazenogCeha);
                List<CehEntity> cehEntities = cehDAO.getGuildsForGame(cehEntity.getSifraIgre());
                for(CehEntity cehEntity1 : cehEntities) {
                    if(cehEntity1.getSifraCeha() == cehEntity.getSifraCeha()) {
                        success = true;
                        UserDAO.close();
                        return null;
                    }
                }
                success = false;
                UserDAO.close();
            }
            catch(Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(KorisnikEntity korisnikEntity) {
            if(success) {
                btnApply.setVisibility(View.GONE);
            }
            else {
                btnApply.setVisibility(View.VISIBLE);
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
                korisnikEntity = userDAO.getUserWithRank(nadimak, sifraTrazenogCeha.toString());
                success = true;
                UserDAO.close();
            }
            catch(Exception e) {
                result = e.getMessage();
                e.printStackTrace();
            }
            return korisnikEntity;
        }

        @Override
        protected void onPostExecute(KorisnikEntity korisnikEntity) {
            if(!success) {
                Toast.makeText(GuildDetailsActivity.this, result, Toast.LENGTH_SHORT).show();
            }
            else {
                Button requestsButton = findViewById(R.id.JoinRequestsButton);

                if(sifraKorisnikovogCeha.contains(sifraTrazenogCeha.toString())) {
                    if (!korisnikEntity.getRang().equals(RangConstants.leader)
                            && !korisnikEntity.getRang().equals(RangConstants.coordinator)) {
                        requestsButton.setVisibility(View.GONE);
                    }
                }else{
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
                korisnikEntity = userDAO.getUserWithRank(nadimak, sifraTrazenogCeha.toString());
                success = true;
                UserDAO.close();
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
                Button newEventButton = findViewById(R.id.btnAddEvent);

                if(sifraKorisnikovogCeha.contains(sifraTrazenogCeha.toString())) {
                    if (!korisnikEntity.getRang().equals(RangConstants.leader)
                            && !korisnikEntity.getRang().equals(RangConstants.coordinator)) {
                        newEventButton.setVisibility(View.GONE);
                    }
                }else{
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
                korisnikEntity = userDAO.getUserWithRank(nadimak, sifraTrazenogCeha.toString());
                success = true;
                UserDAO.close();
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
                Button newGoalButton = findViewById(R.id.btnAddGoal);

                if(sifraKorisnikovogCeha.contains(sifraTrazenogCeha.toString())) {
                    if (!korisnikEntity.getRang().equals(RangConstants.leader)
                            && !korisnikEntity.getRang().equals(RangConstants.coordinator)) {
                        newGoalButton.setVisibility(View.GONE);
                    }
                }else{
                    newGoalButton.setVisibility(View.GONE);
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
                korisnikEntity = userDAO.getUserWithRank(nadimak, sifraTrazenogCeha.toString());
                success = true;
                UserDAO.close();
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
                Button newSubgoalButton = findViewById(R.id.btnAddSubgoal);

                if(sifraKorisnikovogCeha.contains(sifraTrazenogCeha.toString())) {
                    if (!korisnikEntity.getRang().equals(RangConstants.leader)
                            && !korisnikEntity.getRang().equals(RangConstants.coordinator)) {
                        newSubgoalButton.setVisibility(View.GONE);
                    }
                }else{
                    newSubgoalButton.setVisibility(View.GONE);
                }
                TextView textView1 = findViewById(R.id.chooseGuildTextView);
                Spinner chooseGuildSpinner = findViewById(R.id.chooseGuildSpinner);
                ProgressBar progressBar = findViewById(R.id.progressbar);
                progressBar.setVisibility(View.GONE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                LinearLayout everythingLayout = findViewById(R.id.hideGuildDetails);
                Intent pastIntent = getIntent();
                if(!pastIntent.getBooleanExtra(GuildListActivity.IS_FROM_GUILD_LIST_ACTIVITY, false)) {
                    textView1.setVisibility(View.VISIBLE);
                    chooseGuildSpinner.setVisibility(View.VISIBLE);
                }
                everythingLayout.setVisibility(View.VISIBLE);
            }

        }
    }


}

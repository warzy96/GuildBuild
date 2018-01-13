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

        Intent pastIntent = getIntent();
        nadimak = pastIntent.getStringExtra(MainActivity.EXTRA_MESSAGE1);
        sifraKorisnikovogCeha = pastIntent.getIntExtra(MainActivity.EXTRA_MESSAGE2,0);
        sifraTrazenogCeha = pastIntent.getIntExtra(GuildDetailsActivity.EXTRA_MESSAGE3,0);

        if(sifraKorisnikovogCeha==sifraTrazenogCeha){
            btnApply.setVisibility(View.GONE);
        }else{
            btnLeaveGuild.setVisibility(View.GONE);
        }

        FillNameAndDesc fillNameAndDesc = new FillNameAndDesc();
        fillNameAndDesc.execute("");

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
            guildName.setText(cehEntity.getNaziv());
            guildName.setTextSize(35);
            guildName.setTextColor(Color.WHITE);
            guildDesc.setText(cehEntity.getOpis());
            guildDesc.setTextSize(35);
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



    @Override
    public void onBackPressed() {
        finish();
    }
}

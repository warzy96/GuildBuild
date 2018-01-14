package hr.fer.amigosi.guildbuild;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import hr.fer.amigosi.guildbuild.AdministratorAddGameActivity;
import hr.fer.amigosi.guildbuild.AdministratorProfileActivity;
import hr.fer.amigosi.guildbuild.DAO.CehDAO;
import hr.fer.amigosi.guildbuild.DAO.UserDAO;
import hr.fer.amigosi.guildbuild.DatabaseConnection;
import hr.fer.amigosi.guildbuild.R;
import hr.fer.amigosi.guildbuild.entities.KorisnikEntity;

/**
 * Created by Goran Lapat on 10.1.2018..
 */

public class ConcedeActivity extends AppCompatActivity {

    //private int sifraCeha;
    KorisnikEntity korisnik;
    private String nickname;
    Connection con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concede_ownership);

        Intent intent = getIntent();
        nickname = intent.getStringExtra(MainActivity.EXTRA_MESSAGE1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new ShowMemberList().execute("");
    }

    public class UpdateRanks extends AsyncTask<String, Void, String> {
        boolean success = false;
        @Override
        protected String doInBackground(String... strings) {
            String result = "";
            String newLeaderNickname = strings[0];
            try {
                UserDAO userDAO = new UserDAO();
                userDAO.updateUserRank(nickname, RangConstants.coordinator);
                userDAO.updateUserRank(newLeaderNickname, RangConstants.leader);
                result = "Successful";
                success = true;
            }
            catch (Exception e) {
                result = e.getMessage();
            }
            return result;
        }
    }

    public class ShowMemberList extends AsyncTask<String, String, List<KorisnikEntity>> {
        String z = "";
        Boolean isSuccess = false;

        protected void onPostExecute(List<KorisnikEntity> members) {
            LinearLayout possibleLeaders = findViewById(R.id.membersForLeadersLayout);
            possibleLeaders.removeAllViews();
            if (!isSuccess) {
                 Toast.makeText(ConcedeActivity.this, z, Toast.LENGTH_LONG).show();
            }
            else {
                for(KorisnikEntity korisnik : members) {
                    TextView textView = new TextView(ConcedeActivity.this);
                    textView.setText(korisnik.getNadimak());
                    textView.setTextSize(35);
                    textView.setTextColor(Color.WHITE);
                    textView.setClickable(true);
                    textView.setOnClickListener(view -> {
                       AlertDialog.Builder alertDialog = new AlertDialog.Builder(ConcedeActivity.this);
                       alertDialog.setTitle("Are you sure you want to give the 'Leader' title to this user?");
                       alertDialog.setCancelable(false);
                       alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int i) {
                               new UpdateRanks().execute(textView.getText().toString());
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
                    possibleLeaders.addView(textView);
                }
            }
        }

        @Override
        protected List<KorisnikEntity> doInBackground(String... params) {
            List<KorisnikEntity> members = new ArrayList<>();
            try {
                con = DatabaseConnection.getConnection();        // Connect to database
                if (con == null) {
                    z = "Check Your Internet Access!";
                } else {
                    CehDAO ceh = new CehDAO();
                    UserDAO userDAO = new UserDAO();
                    korisnik = userDAO.getUser(nickname);
                    members = ceh.getGuildMembers(korisnik.getSifraCeha());
                    isSuccess = true;
                }
            } catch (Exception ex) {
                isSuccess = false;
                z = ex.getMessage();
            }
           return members;
        }
    }
}

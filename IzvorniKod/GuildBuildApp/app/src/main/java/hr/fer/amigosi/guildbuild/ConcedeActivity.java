package hr.fer.amigosi.guildbuild;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
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

    private ListView ltMemberList;
    private Button btnConcede;
    //private int sifraCeha;
    KorisnikEntity korisnik;
    private String nickname;
    Connection con;

    //TODO: Napravi listu bez adaptera (textView), concede button uopce ne treba
    //Svaki TV moze imati onClickListener koji otvara Dialog u kojem se na positiveButton
    //prepusta vodstvo... trenutni voda -> koordinator, izabrani korisnik -> voda

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concede_ownership);

        Intent intent = getIntent();
        nickname = intent.getStringExtra(MainActivity.EXTRA_MESSAGE1);

        ltMemberList = (ListView) findViewById(R.id.memberList);
        btnConcede = (Button) findViewById(R.id.btnConcede);

        btnConcede.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    UserDAO userDAO = new UserDAO();
                    userDAO.updateUserRank(korisnik.getNadimak(), korisnik.getRang());
                } catch (Exception e) {
                    e.printStackTrace();
                    System.err.print("Neuspjelo" + e.getMessage());
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        new ShowMemberList().execute("");
    }

    public class ShowMemberList extends AsyncTask<String, String, List<KorisnikEntity>> {
        String z = "";
        Boolean isSuccess = false;

        protected void onPostExecute(List<KorisnikEntity> members) {
            ArrayAdapter<KorisnikEntity> adapter = new ArrayAdapter<KorisnikEntity>(ConcedeActivity.this,
                    android.R.layout.simple_list_item_1, members);
            ltMemberList.setAdapter(adapter);
            if (!isSuccess) {
                Toast.makeText(ConcedeActivity.this, z, Toast.LENGTH_LONG).show();
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

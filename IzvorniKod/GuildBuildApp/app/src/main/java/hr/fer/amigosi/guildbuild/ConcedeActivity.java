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
import hr.fer.amigosi.guildbuild.DatabaseConnection;
import hr.fer.amigosi.guildbuild.R;
import hr.fer.amigosi.guildbuild.entities.KorisnikEntity;

/**
 * Created by Goran Lapat on 10.1.2018..
 */

public class ConcedeActivity extends AppCompatActivity {

    private ListView ltMemberList;
    private Button btnConcede;
    private List<KorisnikEntity> members = new ArrayList<>();
    //private int sifraCeha;
    KorisnikEntity korisnik;

    Connection con;

    //public ConcedeActivity() throws Exception{
      //  con = DatabaseConnection.getConnection();
    //}

    public void updateRang(KorisnikEntity korisnik) throws SQLException {
        String query = "UPDATE korisnik SET rang = '"
                + korisnik.getRang()
                + " WHERE korisnik.nadimak = '" + korisnik.getNadimak() + "';"
                + " AND korisnik.sifCeha = " + korisnik.getSifraCeha();;
        try {
            Statement statement = con.createStatement();
            statement.executeUpdate(query);
        }
        catch(SQLException e) {
            throw e;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concede_ownership);

        ltMemberList = (ListView) findViewById(R.id.memberList);
        btnConcede = (Button) findViewById(R.id.btnConcede);

        btnConcede.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    updateRang(korisnik);
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.err.print("Neuspjelo" + e.getMessage());
                }
            }
        });


    }

    public class ShowMemberList extends AsyncTask<String, String, List<KorisnikEntity>> {
        String z = "";
        Boolean isSuccess = false;

        @Override
        protected void onPreExecute() {

        }


        protected void onPostExecute(String r) {
            //Toast.makeText(MainActivity.this, r, Toast.LENGTH_SHORT).show();
            if (isSuccess) {
                Toast.makeText(ConcedeActivity.this, r, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ConcedeActivity.this, ConcedeActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(ConcedeActivity.this, r, Toast.LENGTH_LONG).show();

            }
        }

        @Override
        protected List<KorisnikEntity> doInBackground(String... params) {
            try {
                con = DatabaseConnection.getConnection();        // Connect to database
                if (con == null) {
                    System.err.print("Check Your Internet Access!");
                } else {
                    String query = "SELECT * FROM ceh WHERE ceh.sifCeh =" + korisnik.getSifraCeha();
                    Statement stmt = con.createStatement();
                    int tmp = stmt.executeUpdate(query);

                    CehDAO ceh = new CehDAO();
                    members = ceh.getGuildMembers(korisnik.getSifraCeha());
                    ArrayAdapter<KorisnikEntity> adapter = new ArrayAdapter<KorisnikEntity>(ConcedeActivity.this,
                                                                        android.R.layout.simple_list_item_1, members);
                    ltMemberList.setAdapter(adapter);
                    isSuccess = true;
                }
            } catch (Exception ex) {
                isSuccess = false;
                System.err.print(ex.getMessage());
            }
           return members;
    }
}
}

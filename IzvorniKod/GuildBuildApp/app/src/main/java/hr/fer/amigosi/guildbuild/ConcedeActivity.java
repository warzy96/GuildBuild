package hr.fer.amigosi.guildbuild;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import hr.fer.amigosi.guildbuild.AdministratorAddGameActivity;
import hr.fer.amigosi.guildbuild.AdministratorProfileActivity;
import hr.fer.amigosi.guildbuild.DatabaseConnection;
import hr.fer.amigosi.guildbuild.R;

/**
 * Created by Goran Lapat on 10.1.2018..
 */

public class ConcedeActivity extends AppCompatActivity {

    private ListView ltMemberList;
    private Button btnConcede;
    private List<String> members = new ArrayList<>();

    Connection con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concede_ownership);

        ltMemberList = (ListView) findViewById(R.id.memberList);
        btnConcede = (Button) findViewById(R.id.btnConcede);

        btnConcede.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    public class CheckGame extends AsyncTask<String, String, String> {
        String z = "";
        Boolean isSuccess = false;

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String r) {
            //Toast.makeText(MainActivity.this, r, Toast.LENGTH_SHORT).show();
            if (isSuccess) {
                Toast.makeText(ConcedeActivity.this, r, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ConcedeActivity.this, AdministratorProfileActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(ConcedeActivity.this, r, Toast.LENGTH_LONG).show();

            }
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                con = DatabaseConnection.getConnection();        // Connect to database
                if (con == null) {
                    z = "Check Your Internet Access!";
                } else {


                    String query = "select nadimak from korisnik";
                    Statement stmt = con.createStatement();
                    //int tmp = stmt.executeUpdate(query);
                    z = "Conciding successfull!";
                    isSuccess = true;
                }
            } catch (Exception ex) {
                isSuccess = false;
                z = ex.getMessage();
            }
           return "hello";
    }
}
}

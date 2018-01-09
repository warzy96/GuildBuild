package hr.fer.amigosi.guildbuild;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class AdministratorProfileActivity extends AppCompatActivity {

    private Button btnViewUserReq;
    private Button btnDeleteUser;
    private Button btnDeleteGuild;
    private Button btnAddGame;
    private Button btnAddClass;
    private Button btnDeleteClass;

    Connection con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrator_profile);

        btnViewUserReq = (Button) findViewById(R.id.AdminAddUserButton);
        btnDeleteUser = (Button) findViewById(R.id.AdminDeleteProfileButton);
        btnDeleteGuild = (Button) findViewById(R.id.AdminDeleteGuildButton);
        btnAddGame = (Button) findViewById(R.id.AdminAddGameButton);
        btnAddClass = (Button) findViewById(R.id.AdminAddClassButton);
        btnDeleteClass = (Button) findViewById(R.id.AdminDelClassButton);

        btnViewUserReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdministratorProfileActivity.this, AdministratorViewUserReqActivity.class);
                startActivity(intent);
            }
        });

        btnDeleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdministratorProfileActivity.this, AdministratorDelAccActivity.class);
                startActivity(intent);
            }
        });

        btnDeleteGuild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdministratorProfileActivity.this, AdministratorDelGuildActivity.class);
                startActivity(intent);
            }
        });


        btnAddGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdministratorProfileActivity.this, AdministratorAddGameActivity.class);
                startActivity(intent);
            }
        });

        btnAddClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckIfGamesExists checkIfGamesExists = new CheckIfGamesExists();
                checkIfGamesExists.execute("");
            }
        });

        btnDeleteClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckIfClassesExists checkIfClassesExists = new CheckIfClassesExists();
                checkIfClassesExists.execute("");
            }
        });
    }

    public class CheckIfGamesExists extends AsyncTask<String, String, String> {
        String z = "";
        Boolean isSuccess = false;

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String r) {
            //Toast.makeText(MainActivity.this, r, Toast.LENGTH_SHORT).show();
            if (isSuccess) {
                Intent intent = new Intent(AdministratorProfileActivity.this, AdministratorAddClassActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(AdministratorProfileActivity.this, r, Toast.LENGTH_LONG).show();

            }
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                con = DatabaseConnection.getConnection();        // Connect to database
                if (con == null) {
                    z = "Check Your Internet Access!";
                } else {
                    String query = "select * from igra";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if (rs.next()) {
                        isSuccess = true;
                        con.close();
                    } else {
                        z = "No games to add classes to!";
                        isSuccess = false;
                    }
                }
            } catch (Exception ex) {
                isSuccess = false;
                z = ex.getMessage();
            }
            return z;
        }
    }

    public class CheckIfClassesExists extends AsyncTask<String, String, String> {
        String z = "";
        Boolean isSuccess = false;

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String r) {
            //Toast.makeText(MainActivity.this, r, Toast.LENGTH_SHORT).show();
            if (isSuccess) {
                Intent intent = new Intent(AdministratorProfileActivity.this, AdministratorDelClassActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(AdministratorProfileActivity.this, r, Toast.LENGTH_LONG).show();

            }
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                con = DatabaseConnection.getConnection();        // Connect to database
                if (con == null) {
                    z = "Check Your Internet Access!";
                } else {
                    String query = "select * from klasa";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if (rs.next()) {
                        isSuccess = true;
                        con.close();
                    } else {
                        z = "No classes to delete!";
                        isSuccess = false;
                    }
                }
            } catch (Exception ex) {
                isSuccess = false;
                z = ex.getMessage();
            }
            return z;
        }
    }
}

package hr.fer.amigosi.guildbuild.entities;

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

    Connection con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concede_ownership);

        ltMemberList = (ListView) findViewById(R.id.memberList);
        btnConcede = (Button) findViewById(R.id.btnConcede);

        btnConcede.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                AdministratorAddGameActivity.CheckGame checkGame = new AdministratorAddGameActivity.CheckGame();// this is the Asynctask, which is used to process in background to reduce load on app process
                checkGame.execute("");
            }
        });


    }

    public class CheckGame extends AsyncTask<String,String,String>
    {
        String z = "";
        Boolean isSuccess = false;

        @Override
        protected void onPreExecute()
        {

        }

        @Override
        protected void onPostExecute(String r)
        {
            //Toast.makeText(MainActivity.this, r, Toast.LENGTH_SHORT).show();
            if(isSuccess)
            {
                Toast.makeText(AdministratorAddGameActivity.this , r , Toast.LENGTH_LONG).show();
                Intent intent = new Intent(AdministratorAddGameActivity.this, AdministratorProfileActivity.class);
                startActivity(intent);
            }else{
                Toast.makeText(AdministratorAddGameActivity.this , r , Toast.LENGTH_LONG).show();

            }
        }
        @Override
        protected String doInBackground(String... params)
        {
            String name = etGameName.getText().toString();
            if(name.trim().equals(""))
                z = "Please enter game name";
            else if(name.trim().length()>11){
                z = "Game name too long!";
            }
            else
            {
                try
                {
                    con = DatabaseConnection.getConnection();        // Connect to database
                    if (con == null)
                    {
                        z = "Check Your Internet Access!";
                    }
                    else
                    {
                        String query = "insert into igra values (null,'"+name+"')";
                        Statement stmt = con.createStatement();
                        int tmp = stmt.executeUpdate(query);
                        z="Adding successfull!";
                        isSuccess=true;
                    }
                }
                catch (Exception ex)
                {
                    isSuccess = false;
                    z = ex.getMessage();
                }
            }
            return z;
        }
    }
}

package hr.fer.amigosi.guildbuild;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class AdministratorAddGameActivity extends AppCompatActivity {

    private EditText etGameName;
    private Button btnAdd;

    Connection con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrator_add_game);

        etGameName = (EditText) findViewById(R.id.gameToAdd);
        btnAdd = (Button) findViewById(R.id.btnAddGame);

        btnAdd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                CheckGame checkGame = new CheckGame();// this is the Asynctask, which is used to process in background to reduce load on app process
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

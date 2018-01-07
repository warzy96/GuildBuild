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

public class AdministratorDelGuildActivity extends AppCompatActivity {

    private EditText etGuildName;
    private Button btnDelete;

    Connection con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrator_del_guild);

        etGuildName = (EditText) findViewById(R.id.guildNameForDeleteText);
        btnDelete = (Button) findViewById(R.id.btnDeleteGuild);

        btnDelete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                CheckGuild checkGuild = new CheckGuild();// this is the Asynctask, which is used to process in background to reduce load on app process
                checkGuild.execute("");
            }
        });


    }

    public class CheckGuild extends AsyncTask<String,String,String>
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
                Toast.makeText(AdministratorDelGuildActivity.this , r , Toast.LENGTH_LONG).show();
                Intent intent = new Intent(AdministratorDelGuildActivity.this, AdministratorProfileActivity.class);
                startActivity(intent);
            }else{
                Toast.makeText(AdministratorDelGuildActivity.this , r , Toast.LENGTH_LONG).show();

            }
        }
        @Override
        protected String doInBackground(String... params)
        {
            String name = etGuildName.getText().toString();
            if(name.trim().equals(""))
                z = "Please enter guild name";
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
                        String query = "select * from ceh where naziv = '" + name + "'";
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(query);
                        if(rs.next())
                        {
                            String delquery = "delete from ceh where naziv = '"+name+"'";
                            stmt.executeUpdate(delquery);
                            z = "Delete successfull";
                            isSuccess=true;
                            con.close();
                        }
                        else
                        {
                            z = "Guild not found!";
                            isSuccess = false;
                        }
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

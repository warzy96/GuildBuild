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

public class AdministratorDelAccActivity extends AppCompatActivity {

    private EditText etNickname;
    private Button btnDelete;

    Connection con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrator_del_acc);

        etNickname = (EditText) findViewById(R.id.nicknameForDeleteText);
        btnDelete = (Button) findViewById(R.id.btnDeleteUser);

        btnDelete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                CheckUser checkUser = new CheckUser();// this is the Asynctask, which is used to process in background to reduce load on app process
                checkUser.execute("");
            }
        });

    }

    public class CheckUser extends AsyncTask<String,String,String>
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
                Toast.makeText(AdministratorDelAccActivity.this , r , Toast.LENGTH_LONG).show();
                Intent intent = new Intent(AdministratorDelAccActivity.this, AdministratorProfileActivity.class);
                startActivity(intent);
            }else{
                Toast.makeText(AdministratorDelAccActivity.this , r , Toast.LENGTH_LONG).show();

            }
        }
        @Override
        protected String doInBackground(String... params)
        {
            String nick = etNickname.getText().toString();
            if(nick.trim().equals(""))
                z = "Please enter nickname";
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
                        String query = "select * from korisnik where nadimak = '" + nick + "' and statusR";
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(query);
                        if(rs.next())
                        {
                            String delquery = "delete from korisnik where nadimak = '"+nick+"'";
                            stmt.executeUpdate(delquery);
                            z = "Delete successfull";
                            isSuccess=true;
                            con.close();
                        }
                        else
                        {
                            z = "Registered user not found!";
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

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

import static hr.fer.amigosi.guildbuild.MainActivity.EXTRA_MESSAGE1;
/**
 *  @author Filip Kerman
 *  @version v1.0 29.12.2017
 */

public class RegisterActivity extends AppCompatActivity {
    private EditText etEmailRegister;
    private EditText etPasswordRegister;
    private EditText etNicknameRegister;
    private Button btnRegister2;

    Connection con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etNicknameRegister = (EditText) findViewById(R.id.nicknameTextRegister);
        etEmailRegister = (EditText) findViewById(R.id.emailTextRegister);
        etPasswordRegister = (EditText) findViewById(R.id.passwordTextRegister);
        btnRegister2 = (Button) findViewById(R.id.btnRegister2);

        etNicknameRegister.setHint("Nickname");
        etEmailRegister.setHint("Email");
        etPasswordRegister.setHint("Password");
        etEmailRegister.setHintTextColor(getResources().getColor(R.color.colorPrimaryDark));
        etPasswordRegister.setHintTextColor(getResources().getColor(R.color.colorPrimaryDark));
        etNicknameRegister.setHintTextColor(getResources().getColor(R.color.colorPrimaryDark));

        btnRegister2.setOnClickListener(v -> {
            CheckRegister checkRegister = new CheckRegister();
            checkRegister.execute("");
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    public class CheckRegister extends AsyncTask<String,String,String>{
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
                Toast.makeText(RegisterActivity.this , r , Toast.LENGTH_LONG).show();
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }else{
                Toast.makeText(RegisterActivity.this , r , Toast.LENGTH_LONG).show();

            }
        }
        @Override
        protected String doInBackground(String... params)
        {
            String mail = etEmailRegister.getText().toString();
            String pw = etPasswordRegister.getText().toString();
            String nick = etNicknameRegister.getText().toString();
            if(mail.trim().equals("") || pw.trim().equals("") || nick.trim().equals("")) {
                z = "Please enter Username and Password";
            }
            else if(nick.trim().length()>20 || mail.trim().length()>100 || pw.trim().length()>16){
                z = "Please insert smaller data!";
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
                        String query1 = "select * from korisnik where email= '" + mail + "'";
                        String query2 = "select * from korisnik where nadimak= '" + nick +"'";
                        Statement stmt1 = con.createStatement();
                        Statement stmt2 = con.createStatement();
                        ResultSet rs1 = stmt1.executeQuery(query1);
                        ResultSet rs2 = stmt2.executeQuery(query2);
                        if(rs1.next())
                        {
                            z = "E-mail already exists";
                            isSuccess=false;
                        }
                        else if(rs2.next())
                        {
                            z = "Nickname already exists!";
                            isSuccess = false;
                        }else{
                            String insertQuery = "insert into korisnik values('"+mail+"','"+nick+"','"+pw
                                    +"', false, null,false,null,false)";
                            stmt1.executeUpdate(insertQuery);
                            isSuccess=true;
                            z="Register successfull, wait for admin accept";
                        }
                        con.close();
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


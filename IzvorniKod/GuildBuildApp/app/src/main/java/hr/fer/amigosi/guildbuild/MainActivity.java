package hr.fer.amigosi.guildbuild;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *  @author Filip Kerman
 *  @version v1.0 29.12.2017
 */

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE1 ="aaa";
    public static final String EXTRA_MESSAGE2 ="bbb";


    private EditText etEmail;
    private EditText etPassword;
    private Button btnRegister;
    private Button btnLogin;

    private String email, password;

    Connection con;
    //String un,pass,connectionString;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etEmail = (EditText) findViewById(R.id.emailTextLogin);
        etPassword = (EditText) findViewById(R.id.passwordTextLogin);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegister = (Button) findViewById(R.id.btnRegister);

        etEmail.setHint("Email");
        etPassword.setHint("Password");
        etEmail.setHintTextColor(getResources().getColor(R.color.colorPrimaryDark));
        etPassword.setHintTextColor(getResources().getColor(R.color.colorPrimaryDark));

        // Declaring Server ip, username, database name and password
        //connectionString = "jdbc:mysql://sql11.freemysqlhosting.net:3306/sql11214344?useSSL=false";
        //un = "sql11214344";
        //pass = "lZZVM8IVi7";

//        btnRegister.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        btnLogin.setOnClickListener(v -> {
//            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
//            startActivity(intent);
//            Toast toast = Toast.makeText(MainActivity.this, "Logged in!", Toast.LENGTH_LONG);
//            toast.show();
//        });

        btnLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                CheckLogin checkLogin = new CheckLogin();// this is the Asynctask, which is used to process in background to reduce load on app process
                checkLogin.execute("");
            }
        });
    }

    public void userProfile(View view){
        Intent intent = new Intent(this, UserProfileActivity.class);
        EditText email = (EditText) findViewById(R.id.emailTextLogin);
        EditText password = (EditText) findViewById(R.id.passwordTextLogin);
        String message1 = email.getText().toString();
        String message2 = password.getText().toString();
        intent.putExtra(EXTRA_MESSAGE1,message1);
        intent.putExtra(EXTRA_MESSAGE2,message2);
        startActivity(intent);
    }

    public void Register(View view){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void Home(View view){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        Toast toast = Toast.makeText(MainActivity.this, "Logged in!", Toast.LENGTH_LONG);
        toast.show();
    }

    public class CheckLogin extends AsyncTask<String,String,String>
    {
        String z = "";
        Boolean isSuccess = false;
        Boolean isAdmin = false;

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
                if(isAdmin){
                    Toast.makeText(MainActivity.this , "Hello Admin" , Toast.LENGTH_LONG).show();
                    //finish()
                    Intent intent = new Intent(MainActivity.this, AdministratorProfileActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(MainActivity.this , "Login Successfull" , Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
            }else{
                Toast.makeText(MainActivity.this , r , Toast.LENGTH_LONG).show();

            }
        }
        @Override
        protected String doInBackground(String... params)
        {
            String etEmaill = etEmail.getText().toString();
            String passwordd = etPassword.getText().toString();
            if(etEmaill.trim().equals("")|| passwordd.trim().equals(""))
                z = "Please enter Username and Password";
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
                        String query = "select * from korisnik where email= '" + etEmaill.toString() + "' and lozinka = '"+ passwordd.toString() +"' and statusR ";
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(query);
                        if(rs.next())
                        {
                            isAdmin = rs.getBoolean("isAdmin");
                            z = "Login successful";
                            isSuccess=true;
                            con.close();
                        }
                        else
                        {
                            z = "Invalid Credentials!";
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



    /*public Connection connectionclass(String connectionURL, String username, String password)
    {
        //StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        //StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(connectionURL, username, password);
        }
        catch (SQLException se)
        {
            Log.e("error here 1 : ", se.getMessage());
        }
        catch (ClassNotFoundException e)
        {
            Log.e("error here 2 : ", e.getMessage());
        }
        catch (Exception e)
        {
            Log.e("error here 3 : ", e.getMessage());
        }
        return connection;
    }*/

}

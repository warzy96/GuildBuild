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

import hr.fer.amigosi.guildbuild.DAO.UserDAO;
import hr.fer.amigosi.guildbuild.DAO.VoteDAO;
import hr.fer.amigosi.guildbuild.entities.KorisnikEntity;

/**
 *  @author Filip Kerman
 *  @version v1.0 29.12.2017
 */

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE1 ="nadimak";
    public static final String EXTRA_MESSAGE2 ="sifraCeha";

    private KorisnikEntity korisnikEntity;

    private EditText etEmail;
    private EditText etPassword;
    private Button btnLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etEmail = (EditText) findViewById(R.id.emailTextLogin);
        etPassword = (EditText) findViewById(R.id.passwordTextLogin);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        etEmail.setHint("Email");
        etPassword.setHint("Password");
        etEmail.setHintTextColor(getResources().getColor(R.color.colorPrimaryDark));
        etPassword.setHintTextColor(getResources().getColor(R.color.colorPrimaryDark));


        btnLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                CheckLogin checkLogin = new CheckLogin();
                checkLogin.execute("");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        etEmail.setText("");
        etPassword.setText("");
        etEmail.clearFocus();
        etPassword.clearFocus();
    }

    public void Register(View view){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public class CheckLogin extends AsyncTask<String,String,String>
    {
        String z = "";
        Boolean isSuccess = false;
        Boolean isVoteActivity = true;
        Boolean isKoordinator = false;
        String nadimak;
        Integer sifraCeha;

        @Override
        protected void onPostExecute(String r)
        {
            if(isSuccess)
            {
                if(korisnikEntity.isAdmin()){
                    Toast.makeText(MainActivity.this , "Hello Admin" , Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.this, AdministratorProfileActivity.class);
                    startActivity(intent);
                }

                else{
                        if(isVoteActivity && isKoordinator){
                            Intent intenterino = new Intent(MainActivity.this, VoteActivity.class);
                            intenterino.putExtra(EXTRA_MESSAGE1, nadimak);
                            intenterino.putExtra(EXTRA_MESSAGE2, sifraCeha);
                            startActivity(intenterino);
                        }else {
                        Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        intent.putExtra(EXTRA_MESSAGE1, korisnikEntity.getNadimak());
                        intent.putExtra(EXTRA_MESSAGE2, sifraCeha);
                        startActivity(intent);
                 }
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
            if(etEmaill.trim().isEmpty()|| passwordd.trim().isEmpty())
                z = "Please enter Username and Password";
            else
            {
                try
                {

                        UserDAO userDAO = new UserDAO();
                        korisnikEntity = userDAO.getUser(etEmaill, passwordd);
                        if(korisnikEntity != null) {
                            z = "Login successful";
                            isSuccess = true;
                            VoteDAO voteDAO = new VoteDAO();
                            if(!korisnikEntity.isAdmin()) {
                                isVoteActivity = voteDAO.checkIfGuildHasToVote(korisnikEntity.getSifraCeha());
                                Integer guildToVote = voteDAO.getGuildToVote(korisnikEntity.getSifraCeha());
                                korisnikEntity = userDAO.getUserWithRank(korisnikEntity.getNadimak(), guildToVote.toString());
                                isKoordinator = korisnikEntity.getRang().equals(RangConstants.coordinator);
                                sifraCeha = guildToVote;
                            }
                            nadimak = korisnikEntity.getNadimak();
                        }
                        else
                        {
                            z = "Invalid Credentials!";
                            isSuccess = false;
                        }
                        UserDAO.close();
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

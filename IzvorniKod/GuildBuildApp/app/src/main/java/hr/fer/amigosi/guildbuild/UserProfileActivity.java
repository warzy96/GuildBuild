package hr.fer.amigosi.guildbuild;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import hr.fer.amigosi.guildbuild.DAO.UserDAO;
import hr.fer.amigosi.guildbuild.entities.KorisnikEntity;

/**
 *  @author Filip Kerman
 *  @version v1.0 30.12.2017
 */

public class UserProfileActivity extends AppCompatActivity {
    TextView nickTextView;
    TextView aboutMeTv;
    String nickNameStr;

    Connection con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        nickNameStr = intent.getStringExtra("Nickname");

        nickTextView = (TextView) findViewById(R.id.Nick);
        nickTextView.setText(nickNameStr);

        aboutMeTv = (TextView) findViewById(R.id.aboutMe);

        ImageView mIcon = findViewById(R.id.ivProfile);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.mrpresident);                  //kasnije promijenit
        RoundedBitmapDrawable mDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
        mDrawable.setCircular(true);
        mIcon.setImageDrawable(mDrawable);

        CheckClass checkClass = new CheckClass();// this is the Asynctask, which is used to process in background to reduce load on app process
        checkClass.execute("");


        Button deleteBtn= (Button) findViewById(R.id.DeleteProfile);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View view) {
                                             DeleteProfile deleteProfile= new DeleteProfile();
                                             deleteProfile.execute();
                                         }
                                     }
        );
    }

    public void Messages(View view){
        Intent intent = new Intent(this, MessagesActivity.class);
        intent.putExtra(MainActivity.EXTRA_MESSAGE1, nickname);
        startActivity(intent);
    }

    public void EditProfile(View view){
        Intent intent = new Intent(this, EditProfileActivity.class);
        intent.putExtra("Nickname", nickNameStr);
        startActivity(intent);
    }


    public class CheckClass extends AsyncTask<String,String,String>
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

        }

        @Override
        protected String doInBackground(String... params)
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
                    String query = "select * from korisnik where korisnik.nadimak = '"+nickNameStr+"'";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if(rs.next()){
                        String opis = rs.getString("opis");
                        aboutMeTv.setText(opis);
                    }else {

                    }
                    con.close();
                }
            }
            catch (Exception ex)
            {
                isSuccess = false;
                z = ex.getMessage();
            }

            return z;
        }
    }

    private class DeleteProfile extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... korisnikEntities) {
            try {
                UserDAO userDAO = new UserDAO();
                userDAO.deleteUser(nickNameStr);
                return "Profile deleted successfully";
            } catch (Exception e) {
                e.printStackTrace();
                return "Deleting profile unsuccessful";
            }
        }
        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
            Intent intent= new Intent(UserProfileActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }
}

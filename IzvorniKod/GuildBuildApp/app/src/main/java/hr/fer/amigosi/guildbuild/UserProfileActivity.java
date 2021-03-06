package hr.fer.amigosi.guildbuild;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import hr.fer.amigosi.guildbuild.DAO.LikDAO;
import hr.fer.amigosi.guildbuild.DAO.UserDAO;
import hr.fer.amigosi.guildbuild.entities.CehEntity;
import hr.fer.amigosi.guildbuild.entities.KorisnikEntity;
import hr.fer.amigosi.guildbuild.entities.LikEntity;

/**
 *  @author Filip Kerman
 *  @version v1.0 30.12.2017
 */

public class UserProfileActivity extends AppCompatActivity {
    public static final String USER_DESCRIPTION = "UserDescription";
    private TextView nickTextView;
    private TextView aboutMeTv;
    private String nickNameStr;
    //private LinearLayout characterList;
    private Button myCharsBtn;
    private Button myEventsBtn;
    private String userWatchingNickname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        Intent intent = getIntent();
        nickNameStr = intent.getStringExtra(MainActivity.EXTRA_MESSAGE1);
        userWatchingNickname = intent.getStringExtra(GuildMembersActivity.NADIMAK_KORISNIKA_KOJI_POKRECE_ACTIVITY);


        nickTextView = (TextView) findViewById(R.id.Nick);
        nickTextView.setText(nickNameStr);

        aboutMeTv = (TextView) findViewById(R.id.aboutMe);
        //characterList = findViewById(R.id.characterList);
        myCharsBtn=(Button) findViewById(R.id.myCharactersBtn);
        myEventsBtn=(Button) findViewById(R.id.myEventsButton);

        if(!nickNameStr.equals(userWatchingNickname)) {
            Button editProfile = findViewById(R.id.EditProfile);
            ImageButton imageButton = findViewById(R.id.imageButton);

            myCharsBtn.setText(nickNameStr + "'s characters");
            myEventsBtn.setText(nickNameStr + "'s events");
            editProfile.setVisibility(View.GONE);
            imageButton.setVisibility(View.GONE);
        }


        myCharsBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent myCharsIntent = new Intent(UserProfileActivity.this, MyCharactersActivity.class);
                myCharsIntent.putExtra(MainActivity.EXTRA_MESSAGE1, nickNameStr);
                myCharsIntent.putExtra(GuildMembersActivity.NADIMAK_KORISNIKA_KOJI_POKRECE_ACTIVITY, userWatchingNickname);
                startActivity(myCharsIntent);
            }
        });

        myEventsBtn.setOnClickListener(View -> {
            Intent myEventsIntent = new Intent(UserProfileActivity.this, MyEventsActivity.class);
            myEventsIntent.putExtra(MainActivity.EXTRA_MESSAGE1, nickNameStr);
            myEventsIntent.putExtra(GuildMembersActivity.NADIMAK_KORISNIKA_KOJI_POKRECE_ACTIVITY, userWatchingNickname);
            startActivity(myEventsIntent);
        });

        ImageView mIcon = findViewById(R.id.ivProfile);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.mrpresident);                  //kasnije promijenit
        RoundedBitmapDrawable mDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
        mDrawable.setCircular(true);
        mIcon.setImageDrawable(mDrawable);



    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        nickNameStr = intent.getStringExtra(MainActivity.EXTRA_MESSAGE1);
        userWatchingNickname = intent.getStringExtra(GuildMembersActivity.NADIMAK_KORISNIKA_KOJI_POKRECE_ACTIVITY);
        nickTextView.setText(nickNameStr);
        new CheckClass().execute("");
    }

    public void Messages(View view){
        Intent intent = new Intent(this, MessagesActivity.class);
        intent.putExtra(MainActivity.EXTRA_MESSAGE1, nickNameStr);
        startActivity(intent);
    }

    public void EditProfile(View view){
        Intent intent = new Intent(this, EditProfileActivity.class);
        intent.putExtra(MainActivity.EXTRA_MESSAGE1, nickNameStr);
        intent.putExtra(USER_DESCRIPTION, aboutMeTv.getText());
        startActivity(intent);
    }


    public class CheckClass extends AsyncTask<String,String,String>
    {
        String z = "";
        Boolean isSuccess = false;
        String opis;
        @Override
        protected void onPreExecute()
        {

        }

        @Override
        protected void onPostExecute(String r)
        {
            if(opis != null)
                aboutMeTv.setText(opis);
        }

        @Override
        protected String doInBackground(String... params)
        {

            try
            {
                if (DatabaseConnection.getConnection() == null)
                {
                    z = "Check Your Internet Access!";
                }
                else
                {
                    UserDAO userDAO = new UserDAO();
                    KorisnikEntity korisnikEntity = userDAO.getUser(nickNameStr);
                    if(korisnikEntity != null) {
                        opis = korisnikEntity.getOpisKorisnika();
                    }
                }
            }
            catch (Exception ex)
            {
                isSuccess = false;
                z = ex.getMessage();
            }
            finally {
                try {
                    UserDAO.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            return z;
        }
    }

}

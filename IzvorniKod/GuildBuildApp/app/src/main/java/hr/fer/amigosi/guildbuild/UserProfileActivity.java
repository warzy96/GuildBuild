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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        nickNameStr = intent.getStringExtra(MainActivity.EXTRA_MESSAGE1);

        nickTextView = (TextView) findViewById(R.id.Nick);
        nickTextView.setText(nickNameStr);

        aboutMeTv = (TextView) findViewById(R.id.aboutMe);
        //characterList = findViewById(R.id.characterList);
        myCharsBtn=(Button) findViewById(R.id.myCharactersBtn);

        myCharsBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent myCharsIntent = new Intent(UserProfileActivity.this, MyCharactersActivity.class);
                myCharsIntent.putExtra(MainActivity.EXTRA_MESSAGE1, nickNameStr);
                startActivity(myCharsIntent);
            }
        });


        ImageView mIcon = findViewById(R.id.ivProfile);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.mrpresident);                  //kasnije promijenit
        RoundedBitmapDrawable mDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
        mDrawable.setCircular(true);
        mIcon.setImageDrawable(mDrawable);

        CheckClass checkClass = new CheckClass();// this is the Asynctask, which is used to process in background to reduce load on app process
        checkClass.execute("");

    }

    @Override
    protected void onResume() {
        super.onResume();
        nickTextView.setText(nickNameStr);
        new CheckClass().execute("");
        //new PopulateCharacterList().execute();
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

            return z;
        }
    }

//    private class PopulateCharacterList extends AsyncTask<Void,Void, List<LikEntity>> {
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            characterList.removeAllViews();
//        }
//
//        @Override
//        protected List<LikEntity> doInBackground(Void... voids) {
//            try {
//                LikDAO likDAO = new LikDAO();
//                List<LikEntity> likEntityList = likDAO.getAllCharactersForUser(nickNameStr);
//                return likEntityList;
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(List<LikEntity> likEntityList) {
//            if(likEntityList.isEmpty()) {
//                Toast.makeText(UserProfileActivity.this, "No characters to show", Toast.LENGTH_SHORT).show();
//                finish();
//            }
//            for(LikEntity likEntity : likEntityList) {
//                TextView textView = new TextView(UserProfileActivity.this);
//                textView.setText(likEntity.getNadimak() + "  " +likEntity.getLevel());      //popravit
//                textView.setTextSize(20);
//                textView.setTextColor(Color.WHITE);
//                textView.setFocusable(false);
//                textView.setClickable(true);
//                characterList.addView(textView);
//            }
//        }
//    }
}

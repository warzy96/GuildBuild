package hr.fer.amigosi.guildbuild;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import hr.fer.amigosi.guildbuild.DAO.ObrazacDAO;
import hr.fer.amigosi.guildbuild.DAO.RangDAO;
import hr.fer.amigosi.guildbuild.DAO.UserDAO;
import hr.fer.amigosi.guildbuild.DAO.VoteDAO;
import hr.fer.amigosi.guildbuild.entities.KorisnikEntity;
import hr.fer.amigosi.guildbuild.entities.VoteEntity;


public class EditProfileActivity extends AppCompatActivity {
    private Button changeAvatarBtn;
    private Button aboutMeBtn;
    private Button addNewCharacterBtn;
    private Button deleteProfileButton;
    private EditText descriptionEditText;
    private String aboutMeText="";
    private String nickname;
    private Integer sifraCeha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Intent pastIntent = getIntent();
        nickname = pastIntent.getStringExtra(MainActivity.EXTRA_MESSAGE1);
        sifraCeha = pastIntent.getIntExtra(MainActivity.EXTRA_MESSAGE2, 0);
        aboutMeText = pastIntent.getStringExtra(UserProfileActivity.USER_DESCRIPTION);

        changeAvatarBtn=(Button) findViewById(R.id.ChangeAvatarButton);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        addNewCharacterBtn=(Button) findViewById(R.id.AddNewCharButton);
        deleteProfileButton = findViewById(R.id.deleteProfile);

        descriptionEditText.setHint(aboutMeText);
        descriptionEditText.setText(aboutMeText);
        descriptionEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    v.setHint(v.getText());
                    v.setFocusable(false);
                    aboutMeText = v.getText().toString();
                    new AboutMe().execute("");
                }
                return false;
            }
        });

        deleteProfileButton.setClickable(true);
        deleteProfileButton.setOnClickListener(view -> {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("Are you sure you want delete your profile?");
            alertDialog.setCancelable(false);
            alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    new DeleteUserProfile().execute();
                }
            });
            alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            alertDialog.show();
        });

        changeAvatarBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                //TODO
            }
        });

        addNewCharacterBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent= new Intent(EditProfileActivity.this, AddNewCharacterActivity.class);
                intent.putExtra(MainActivity.EXTRA_MESSAGE1, nickname);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private class DeleteUserProfile extends AsyncTask<Void, Void, String> {
        private String message;
        private boolean success = false;
        @Override
        protected String doInBackground(Void... v) {
            try {
                if(DatabaseConnection.getConnection() == null) {
                    message = "Check Your Internet Access!";
                }
                UserDAO userDAO = new UserDAO();
                RangDAO rangDAO = new RangDAO();
                ObrazacDAO obrazacDAO = new ObrazacDAO();
                List<Integer> gdjeJeVoda = rangDAO.isUserLeader(nickname);
                if(!gdjeJeVoda.isEmpty()) {
                    for(Integer sif : gdjeJeVoda) {
                        VoteDAO voteDAO = new VoteDAO();
                        voteDAO.insertAllCoordinatorsFromGuildIntoVote(sif.toString());
                    }
                }
                obrazacDAO.deleteForm(nickname);
                userDAO.deleteUser(nickname);
                rangDAO.deleteUser(nickname);
                message = "Delete successful";
                success = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                try {
                    UserDAO.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(EditProfileActivity.this,
                    message, Toast.LENGTH_SHORT).show();
            if(success) {
                Intent returnToMainScreen =
                        new Intent(EditProfileActivity.this, MainActivity.class);
                returnToMainScreen.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(returnToMainScreen);
            }

        }
    }

    public class AboutMe extends AsyncTask<String,String,String>{
        private String message = "";
        private boolean success = false;

        @Override
        protected void onPostExecute(String r)
        {
            if(success)
            {
                Toast.makeText(EditProfileActivity.this , r , Toast.LENGTH_LONG).show();
            }
            Toast.makeText(EditProfileActivity.this , r , Toast.LENGTH_LONG).show();

        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                if (DatabaseConnection.getConnection() == null)
                {
                    message = "Check Your Internet Access!";
                }
                else
                {
                    UserDAO userDAO = new UserDAO();
                    userDAO.updateUsersDescription(nickname, aboutMeText);
                    message="Description updated successfully!";
                    success=true;
                }
            } catch (Exception e) {
                success = false;
                message = e.getMessage();
            }
            finally {
                try {
                    UserDAO.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            return message;
        }
    }
}

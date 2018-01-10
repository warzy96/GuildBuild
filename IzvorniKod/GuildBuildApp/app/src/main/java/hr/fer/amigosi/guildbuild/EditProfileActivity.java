package hr.fer.amigosi.guildbuild;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import hr.fer.amigosi.guildbuild.DAO.UserDAO;


public class EditProfileActivity extends AppCompatActivity {
    private Button changeAvatarBtn;
    private Button aboutMeBtn;
    private Button addNewCharacterBtn;
    private Button deleteProfileButton;
    private String aboutMeText="";
    private String nickname;
    private int sifraCeha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Intent pastIntent = getIntent();
        nickname = pastIntent.getStringExtra(MainActivity.EXTRA_MESSAGE1);
        sifraCeha = pastIntent.getIntExtra(MainActivity.EXTRA_MESSAGE2, 0);

        changeAvatarBtn=(Button) findViewById(R.id.ChangeAvatarButton);
        aboutMeBtn=(Button) findViewById(R.id.AboutMeButton);
        addNewCharacterBtn=(Button) findViewById(R.id.AddNewCharButton);
        deleteProfileButton = findViewById(R.id.deleteProfile);

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
        });

        Intent intent = getIntent();
        nickNameStr = intent.getStringExtra("Nickname");

        changeAvatarBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                //TODO
            }
        });

        aboutMeBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditProfileActivity.this);
                builder.setTitle("Write something about yourself:");

                // Set up the input
                final EditText input = new EditText(EditProfileActivity.this);
                // Set type of input
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                input.setLines(3);
                builder.setView(input);

                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        aboutMeText = input.getText().toString();
                        AboutMe aboutMe = new AboutMe();
                        aboutMe.execute("");
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });

        addNewCharacterBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent= new Intent(EditProfileActivity.this, AddNewCharacterActivity.class);
                intent.putExtra("Nickname", nickNameStr);
                startActivity(intent);
            }
        });
    }

    private class DeleteUserProfile extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... v) {
            try {
                UserDAO userDAO = new UserDAO();
                userDAO.deleteUser(nickname);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(EditProfileActivity.this,
                    "Delete successful", Toast.LENGTH_SHORT);
            Intent returnToMainScreen =
                    new Intent(EditProfileActivity.this, MainActivity.class);
            startActivity(returnToMainScreen);
            EditProfileActivity.this.finish();
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
                    message="Operation successfull!";
                    success=true;
                }
            } catch (Exception e) {
                success = false;
                message = e.getMessage();
            }

            return message;
        }
    }
}

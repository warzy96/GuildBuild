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


public class EditProfileActivity extends AppCompatActivity {
    private Button changeAvatarBtn;
    private Button aboutMeBtn;
    private Button addNewCharacterBtn;
    private String aboutMeText="";
    private String nickNameStr;

    Connection con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        changeAvatarBtn=(Button) findViewById(R.id.ChangeAvatarButton);
        aboutMeBtn=(Button) findViewById(R.id.AboutMeButton);
        addNewCharacterBtn=(Button) findViewById(R.id.AddNewCharButton);

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
                con=DatabaseConnection.getConnection();
                if (con == null)
                {
                    message = "Check Your Internet Access!";
                }
                else
                {
                    String query = "update korisnik set opis = '" + aboutMeText +
                            "' where korisnik.nadimak= '" + nickNameStr + "'";
                    Statement stmt = con.createStatement();
                    int tmp = stmt.executeUpdate(query);
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

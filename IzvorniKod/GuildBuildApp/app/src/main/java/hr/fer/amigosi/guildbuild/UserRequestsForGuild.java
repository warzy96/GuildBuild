package hr.fer.amigosi.guildbuild;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import hr.fer.amigosi.guildbuild.DAO.ObrazacDAO;
import hr.fer.amigosi.guildbuild.DAO.RangDAO;
import hr.fer.amigosi.guildbuild.DAO.UserDAO;
import hr.fer.amigosi.guildbuild.entities.ObrazacEntity;

public class UserRequestsForGuild extends AppCompatActivity {
    private Integer sifraCeha;
    private LinearLayout layoutToRemove;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_requests_for_guild);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new PopulateViewWithObrasci().execute();
    }

    private class AcceptUserForm extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String userNickname = strings[0];
            try {
                UserDAO userDAO = new UserDAO();
                userDAO.updateUserGuild(userNickname, sifraCeha.toString());
                RangDAO rangDAO = new RangDAO();
                rangDAO.updateUserRank(userNickname, RangConstants.member, sifraCeha.toString());
                ObrazacDAO obrazacDAO = new ObrazacDAO();
                obrazacDAO.deleteForm(userNickname, sifraCeha);
                return "User added successfully";
            }
            catch (Exception e) {
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(UserRequestsForGuild.this, s, Toast.LENGTH_SHORT).show();
            LinearLayout linearLayout = findViewById(R.id.ObrasciLayout);
            linearLayout.removeView(layoutToRemove);
        }
    }

    private class DeleteUserForm extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String userNickname = strings[0];
            try {
                ObrazacDAO obrazacDAO = new ObrazacDAO();
                obrazacDAO.deleteForm(userNickname, sifraCeha);
                return "Users application removed successfully";
            }
            catch (Exception e) {
                return e.getMessage();
            }
            finally {
                try {
                    ObrazacDAO.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(UserRequestsForGuild.this, s, Toast.LENGTH_SHORT).show();
            LinearLayout linearLayout = findViewById(R.id.ObrasciLayout);
            linearLayout.removeView(layoutToRemove);
        }
    }

    private class PopulateViewWithObrasci extends AsyncTask<Void, Void, List<ObrazacEntity>> {
        @Override
        protected void onPreExecute() {
            Intent intent = getIntent();
            sifraCeha = intent.getIntExtra(MainActivity.EXTRA_MESSAGE2, 0);
        }

        @Override
        protected List<ObrazacEntity> doInBackground(Void... voids) {
            List<ObrazacEntity> obrasci = new ArrayList<>();
            try {
                ObrazacDAO obrazacDAO = new ObrazacDAO();
                obrasci = obrazacDAO.getAllFormsForGuild(sifraCeha.toString());
            }
            catch(Exception e) {

            }
            finally {
                try {
                    ObrazacDAO.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return obrasci;
        }

        @Override
        protected void onPostExecute(List<ObrazacEntity> obrazacEntities) {
            if(obrazacEntities.isEmpty()) {
                Toast.makeText(UserRequestsForGuild.this, "No user requests to show",
                        Toast.LENGTH_SHORT).show();
                finish();
            }
            else {
                LinearLayout linearLayout = findViewById(R.id.ObrasciLayout);
                linearLayout.removeAllViews();
                for(ObrazacEntity obrazacEntity : obrazacEntities) {
                    LinearLayout userLayout = new LinearLayout(UserRequestsForGuild.this);
                    TextView nicknameTextView = new TextView(UserRequestsForGuild.this);
                    nicknameTextView.setTextColor(Color.WHITE);
                    nicknameTextView.setText(obrazacEntity.getNadimakKorisnika());
                    nicknameTextView.setTextSize(35);
                    nicknameTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                    TextView opisTextView = new TextView(UserRequestsForGuild.this);
                    opisTextView.setTextSize(20);
                    opisTextView.setText(obrazacEntity.getPoruka());
                    opisTextView.setTextColor(Color.WHITE);

                    userLayout.setOrientation(LinearLayout.VERTICAL);
                    userLayout.addView(nicknameTextView);
                    userLayout.addView(opisTextView);
                    userLayout.setOnClickListener(View -> {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(UserRequestsForGuild.this);
                        alertDialog.setTitle("Do you want to accept this user to your guild?");
                        alertDialog.setCancelable(false);
                        alertDialog.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                layoutToRemove = userLayout;
                                new AcceptUserForm().execute(nicknameTextView.getText().toString());
                            }
                        });
                        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                layoutToRemove = userLayout;
                                new DeleteUserForm().execute(nicknameTextView.getText().toString());
                            }
                        });
                        alertDialog.show();
                    });

                    linearLayout.addView(userLayout);
                }
            }
        }
    }
}

package hr.fer.amigosi.guildbuild;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import hr.fer.amigosi.guildbuild.DAO.UserDAO;
import hr.fer.amigosi.guildbuild.entities.KorisnikEntity;

public class AdministratorDelAccActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrator_del_acc);

        GetRegisteredUsers getRegisteredUsers = new GetRegisteredUsers();
        getRegisteredUsers.execute();

    }

    private class GetRegisteredUsers extends AsyncTask<String, String, List<KorisnikEntity>> {

        @Override
        protected List<KorisnikEntity> doInBackground(String... strings) {
            try {
                UserDAO userDAO = new UserDAO();
                List<KorisnikEntity> userEntities = userDAO.loadAllRegisteredUsers();
                return userEntities;
            }
            catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            finally {
                try {
                    UserDAO.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        protected void onPostExecute(List<KorisnikEntity> korisnikEntities) {
            LinearLayout layout = findViewById(R.id.linLayout1);
            layout.removeAllViews();
            for(KorisnikEntity korisnik : korisnikEntities) {
                TextView emailTextView = new TextView(getApplicationContext());
                Button removeButton = new Button(new ContextThemeWrapper(getApplicationContext(),R.style.button_style), null, R.style.button_style);

                emailTextView.setText(korisnik.getEmail());
                emailTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                emailTextView.setTextColor(Color.WHITE);
                emailTextView.setTextSize(25);
                removeButton.setText("Remove");
                removeButton.setTextSize(25);
                removeButton.setTextColor(Color.WHITE);

                removeButton.setOnClickListener(view -> {
                    RemoveUser removeUser = new RemoveUser();
                    removeUser.execute(korisnik);
                });

                LinearLayout mainLayout = new LinearLayout(getApplicationContext());
                mainLayout.setOrientation(LinearLayout.VERTICAL);
                mainLayout.setMinimumWidth(layout.getWidth());
                LinearLayout removeButtonLayout = new LinearLayout(getApplicationContext());
                removeButtonLayout.setOrientation(LinearLayout.VERTICAL);
                TextView v = new TextView(getApplicationContext());
                v.setText(" ");
                v.setTextSize(3);

                removeButtonLayout.addView(removeButton);
                mainLayout.addView(emailTextView);
                mainLayout.addView(v);
                mainLayout.addView(removeButtonLayout);
                layout.addView(mainLayout);
            }
        }
    }


    private class RemoveUser extends AsyncTask<KorisnikEntity, String, String> {
        @Override
        protected String doInBackground(KorisnikEntity... korisnikEntities) {
            KorisnikEntity korisnikEntity = korisnikEntities[0];
            try {
                UserDAO userDAO = new UserDAO();
                userDAO.deleteUser(korisnikEntity.getNadimak());
                return "Removed successfully";
            } catch (Exception e) {
                e.printStackTrace();
                return "Removing user unsuccessful";
            }
            finally {
                try {
                    UserDAO.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
            GetRegisteredUsers getRegisteredUsers = new GetRegisteredUsers();
            getRegisteredUsers.execute();
        }
    }
}

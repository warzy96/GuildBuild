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

import hr.fer.amigosi.guildbuild.DAO.CehDAO;
import hr.fer.amigosi.guildbuild.DAO.UserDAO;
import hr.fer.amigosi.guildbuild.entities.CehEntity;

public class AdministratorDelGuildActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrator_del_guild);

        GetGuilds getGuilds = new GetGuilds();
        getGuilds.execute();


    }

    private class GetGuilds extends AsyncTask<String, String, List<CehEntity>> {

        @Override
        protected List<CehEntity> doInBackground(String... strings) {
            try {
                CehDAO cehDAO = new CehDAO();
                List<CehEntity> cehEntities = cehDAO.getAllGuilds();
                return cehEntities;
            }
            catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<CehEntity> cehEntities) {
            LinearLayout layout = findViewById(R.id.linLayout2);
            layout.removeAllViews();
            for(CehEntity ceh : cehEntities) {
                TextView cehName = new TextView(getApplicationContext());
                Button removeButton = new Button(new ContextThemeWrapper(getApplicationContext(),R.style.button_style), null, R.style.button_style);

                cehName.setText(ceh.getNaziv());
                cehName.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                cehName.setTextColor(Color.WHITE);
                cehName.setTextSize(25);
                removeButton.setText("Remove");
                removeButton.setTextSize(25);
                removeButton.setTextColor(Color.WHITE);

                removeButton.setOnClickListener(view -> {
                    RemoveCeh removeCeh = new RemoveCeh();
                    removeCeh.execute(ceh);
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
                mainLayout.addView(cehName);
                mainLayout.addView(v);
                mainLayout.addView(removeButtonLayout);
                layout.addView(mainLayout);
            }
        }
    }


    private class RemoveCeh extends AsyncTask<CehEntity, String, String> {
        @Override
        protected String doInBackground(CehEntity... cehEntities) {
            CehEntity cehEntity = cehEntities[0];
            try {
                CehDAO cehDAO = new CehDAO();
                cehDAO.deleteGuild(cehEntity.getSifraCeha());
                return "Removed successfully";
            } catch (Exception e) {
                e.printStackTrace();
                return "Removing user unsuccessful";
            }
            finally {
                try {
                    CehDAO.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
            GetGuilds getGuilds = new GetGuilds();
            getGuilds.execute();
        }
    }
}

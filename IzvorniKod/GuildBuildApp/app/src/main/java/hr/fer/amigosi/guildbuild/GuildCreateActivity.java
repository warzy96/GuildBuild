package hr.fer.amigosi.guildbuild;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hr.fer.amigosi.guildbuild.DAO.CehDAO;
import hr.fer.amigosi.guildbuild.DAO.IgraDAO;
import hr.fer.amigosi.guildbuild.DAO.UserDAO;
import hr.fer.amigosi.guildbuild.entities.CehEntity;
import hr.fer.amigosi.guildbuild.entities.IgraEntity;

/**
 * Created by Matija on 4.1.2018..
 */

public class GuildCreateActivity extends AppCompatActivity{
    private List<IgraEntity> igre;
    private HashMap<String, Integer> igreMap;
    private Spinner gamesSpinner;
    private EditText guildDescription;
    private Object key;
    private String nickname;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guild_create);

        Intent intent = getIntent();
        nickname = intent.getStringExtra(MainActivity.EXTRA_MESSAGE1);

        gamesSpinner = findViewById(R.id.gamesSpinnerCreate);
        new populateSpinner().execute("");

        EditText createGuildEditText = findViewById(R.id.guildNameCreate);
        guildDescription = findViewById(R.id.guildDescriptionEditText);

        Button createButton = findViewById(R.id.createGuildButton);
        createButton.setClickable(true);
        createButton.setOnClickListener(view -> {
            key = gamesSpinner.getSelectedItem();
            AddGuild addGuild = new AddGuild();
            addGuild.execute(createGuildEditText.getText().toString());
        });
    }


    private class populateSpinner extends AsyncTask<String,String,List<String>> {
        @Override
        protected void onPreExecute()
        {

        }

        @Override
        protected void onPostExecute(List<String> igre)
        {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(GuildCreateActivity.this,
                    android.R.layout.simple_spinner_item,igre);

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            gamesSpinner.setAdapter(adapter);
        }

        @Override
        protected List<String> doInBackground(String... params)
        {
            igreMap = new HashMap<>();
            String z="";
            try
            {
                if (DatabaseConnection.getConnection() == null)
                {
                }
                else
                {
                    IgraDAO igraDAO = new IgraDAO();
                    igre = igraDAO.getAllGames();
                    for(IgraEntity igraEntity : igre) {
                        igreMap.put(igraEntity.getNazivIgre(), igraEntity.getSifraIgre());
                    }
                }
            }
            catch (Exception ex)
            {
                z = ex.getMessage();
            }
            return new ArrayList<String>(igreMap.keySet());
        }
    }

    private class AddGuild extends AsyncTask<String, Void, String> {
        private String result = "";
        private boolean success = false;
        @Override
        protected String doInBackground(String... strings) {
            try {
                CehDAO cehDAO = new CehDAO();
                cehDAO.insertGuild(strings[0], igreMap.get(key), guildDescription.getText().toString());
                UserDAO userDAO = new UserDAO();
                int guildNumber = cehDAO.getGuildNumber(strings[0], igreMap.get(key));
                if(guildNumber != 0) {
                    userDAO.updateUserGuild(nickname, guildNumber);
                    userDAO.updateUserRank(nickname, RangConstants.leader);
                    result = "Guild created successfully!";
                    success = true;
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
                result = e.getMessage();
            }
            catch (ClassNotFoundException ex) {
                ex.printStackTrace();
                result = ex.getMessage();
            }
            catch (Exception e) {
                e.printStackTrace();
                result = e.getMessage();
            }
            finally {
                try {
                    UserDAO.close();
                    CehDAO.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(GuildCreateActivity.this, result, Toast.LENGTH_SHORT).show();
            if(success)
                finish();
        }
    }
}

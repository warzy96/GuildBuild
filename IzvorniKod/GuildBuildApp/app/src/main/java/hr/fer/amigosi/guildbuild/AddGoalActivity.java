package hr.fer.amigosi.guildbuild;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import java.util.List;

import hr.fer.amigosi.guildbuild.DAO.CiljDAO;
import hr.fer.amigosi.guildbuild.DAO.DogadajDAO;
import hr.fer.amigosi.guildbuild.DAO.PodciljDAO;
import hr.fer.amigosi.guildbuild.entities.CiljEntity;
import hr.fer.amigosi.guildbuild.entities.DogadajEntity;
import hr.fer.amigosi.guildbuild.entities.PodciljEntity;

public class AddGoalActivity extends AppCompatActivity {

    private Spinner spinner;
    private EditText goalName;
    private Button btnAddGoal;

    private Integer sifraKorisnikovogCeha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goal);

        spinner = (Spinner) findViewById(R.id.spinnerEvents);
        btnAddGoal = (Button) findViewById(R.id.btnAddGoal1);
        goalName = (EditText) findViewById(R.id.goalToAdd);

        Intent pastIntent = getIntent();
        sifraKorisnikovogCeha = pastIntent.getIntExtra(MainActivity.EXTRA_MESSAGE2, 0);

        btnAddGoal.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                CheckGoal checkGoal = new CheckGoal();// this is the Asynctask, which is used to process in background to reduce load on app process
                checkGoal.execute("");
            }
        });
    }

    public class CheckGoal extends AsyncTask<String,String,String>
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
            //Toast.makeText(MainActivity.this, r, Toast.LENGTH_SHORT).show();
            if(isSuccess)
            {
                Toast.makeText(AddGoalActivity.this , r , Toast.LENGTH_LONG).show();
                finish();
            }else{
                Toast.makeText(AddGoalActivity.this , r , Toast.LENGTH_LONG).show();

            }
        }
        @Override
        protected String doInBackground(String... params)
        {
            String goalNameStr = goalName.getText().toString();
            String eventName = spinner.getSelectedItem().toString();
            if(goalNameStr.trim().equals(""))
                z = "Please enter goal name";
            else if(goalNameStr.trim().length()>11){
                z = "Goal name too long!";
            }
            else
            {
                try
                {
                    DogadajDAO dogadajDAO = new DogadajDAO();
                    DogadajEntity dogadajEntity = dogadajDAO.getEvent(eventName,sifraKorisnikovogCeha);
                    CiljDAO ciljDAO = new CiljDAO();
                    CiljEntity ciljEntity = new CiljEntity(dogadajEntity.getSifraDogadaja(),0,goalNameStr,false);
                    ciljDAO.insertGoal(ciljEntity);
                    isSuccess=true;
                    z="Adding successfull";
                }
                catch (Exception ex)
                {
                    isSuccess = false;
                    z = ex.getMessage();
                }
                finally {
                    try {
                        DogadajDAO.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            return z;
        }
    }

    private class FillSpinnerWithEvents extends AsyncTask<String,String,List<String>> {
        @Override
        protected void onPreExecute()
        {

        }

        @Override
        protected void onPostExecute(List<String> dogadaji)
        {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddGoalActivity.this,
                    android.R.layout.simple_spinner_item,dogadaji);

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
        }
        @Override
        protected List<String> doInBackground(String... params)
        {
            String z="";
            List<DogadajEntity> result = new ArrayList<>();
            List<String> imena = new ArrayList<>();
            try
            {
                DogadajDAO dogadajDAO = new DogadajDAO();
                result = dogadajDAO.getAllEventsForGuild(sifraKorisnikovogCeha);
                for(DogadajEntity dogadajEntity : result){
                    imena.add(dogadajEntity.getNazivDogadaja());
                }
            }
            catch (Exception ex)
            {
                z = ex.getMessage();
            }
            return imena;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        FillSpinnerWithEvents fillSpinnerWithEvents = new FillSpinnerWithEvents();
        fillSpinnerWithEvents.execute();
    }
}

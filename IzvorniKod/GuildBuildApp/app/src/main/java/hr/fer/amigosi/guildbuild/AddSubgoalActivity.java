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

import java.util.ArrayList;
import java.util.List;

import hr.fer.amigosi.guildbuild.DAO.CiljDAO;
import hr.fer.amigosi.guildbuild.DAO.DogadajDAO;
import hr.fer.amigosi.guildbuild.DAO.PodciljDAO;
import hr.fer.amigosi.guildbuild.entities.CiljEntity;
import hr.fer.amigosi.guildbuild.entities.DogadajEntity;
import hr.fer.amigosi.guildbuild.entities.PodciljEntity;

public class AddSubgoalActivity extends AppCompatActivity {

    private Spinner spinner;
    private EditText subgoalName;
    private Button btnAddSubgoal;

    private int sifraKorisnikovogCeha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subgoal);

        spinner = (Spinner) findViewById(R.id.spinnerGoals);
        btnAddSubgoal = (Button) findViewById(R.id.btnAddSGoal1);
        subgoalName = (EditText) findViewById(R.id.subgoalToAdd);

        Intent pastIntent = getIntent();
        sifraKorisnikovogCeha = pastIntent.getIntExtra(MainActivity.EXTRA_MESSAGE2,0);

        btnAddSubgoal.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                CheckSubgoal checkSubgoal = new CheckSubgoal();// this is the Asynctask, which is used to process in background to reduce load on app process
                checkSubgoal.execute("");
            }
        });
    }

    public class CheckSubgoal extends AsyncTask<String,String,String>
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
                Toast.makeText(AddSubgoalActivity.this , r , Toast.LENGTH_LONG).show();
                finish();
            }else{
                Toast.makeText(AddSubgoalActivity.this , r , Toast.LENGTH_LONG).show();

            }
        }
        @Override
        protected String doInBackground(String... params)
        {
            String subgoalNameStr = subgoalName.getText().toString();
            String goalName = spinner.getSelectedItem().toString();
            if(subgoalNameStr.trim().equals(""))
                z = "Please enter subgoal name";
            else if(subgoalNameStr.trim().length()>11){
                z = "Subgoal name too long!";
            }
            else
            {
                try
                {
                    CiljDAO ciljDAO = new CiljDAO();
                    CiljEntity ciljEntity = ciljDAO.getGoal(goalName);
                    PodciljDAO podciljDAO = new PodciljDAO();
                    PodciljEntity podciljEntity = new PodciljEntity(ciljEntity.getSifraCilja(), 0, subgoalNameStr,false);
                    podciljDAO.insertSubgoal(podciljEntity);
                    isSuccess=true;
                    z="Adding successfull";
                }
                catch (Exception ex)
                {
                    isSuccess = false;
                    z = ex.getMessage();
                }
            }
            return z;
        }
    }

    private class FillSpinnerWithGoals extends AsyncTask<String,String,List<String>> {
        @Override
        protected void onPreExecute()
        {

        }

        @Override
        protected void onPostExecute(List<String> ciljevi)
        {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddSubgoalActivity.this,
                    android.R.layout.simple_spinner_item,ciljevi);

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
        }
        @Override
        protected List<String> doInBackground(String... params)
        {
            String z="";
            List<String> result = new ArrayList<>();
            try
            {
                CiljDAO ciljDAO = new CiljDAO();
                result = ciljDAO.getAllGoalsForGuild(sifraKorisnikovogCeha);
            }
            catch (Exception ex)
            {
                z = ex.getMessage();
            }
            return result;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        FillSpinnerWithGoals fillSpinnerWithGoals = new FillSpinnerWithGoals();
        fillSpinnerWithGoals.execute();
    }
}

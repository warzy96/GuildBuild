package hr.fer.amigosi.guildbuild;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Statement;

import hr.fer.amigosi.guildbuild.DAO.DogadajDAO;
import hr.fer.amigosi.guildbuild.entities.DogadajEntity;

public class AddEventActivity extends AppCompatActivity {

    private EditText etEventName;
    private Button btnAddEvent;

    private int sifraKorisnikovogCeha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        etEventName = (EditText) findViewById(R.id.eventToAdd);
        btnAddEvent = (Button) findViewById(R.id.btnAddEvent1);

        Intent pastIntent = getIntent();
        sifraKorisnikovogCeha = pastIntent.getIntExtra(MainActivity.EXTRA_MESSAGE2,0);

        btnAddEvent.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                CheckEvent checkEvent = new CheckEvent();// this is the Asynctask, which is used to process in background to reduce load on app process
                checkEvent.execute("");
            }
        });
    }

    public class CheckEvent extends AsyncTask<String,String,String>
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
                Toast.makeText(AddEventActivity.this , r , Toast.LENGTH_LONG).show();
                finish();
            }else{
                Toast.makeText(AddEventActivity.this , r , Toast.LENGTH_LONG).show();

            }
        }
        @Override
        protected String doInBackground(String... params)
        {
            String name = etEventName.getText().toString();
            if(name.trim().equals(""))
                z = "Please enter event name";
            else if(name.trim().length()>11){
                z = "Event name too long!";
            }
            else
            {
                try
                {
                    DogadajDAO dogadajDAO = new DogadajDAO();
                    DogadajEntity dogadajEntity = new DogadajEntity(0,name,sifraKorisnikovogCeha,false,false);
                    dogadajDAO.insertEvent(dogadajEntity);
                    z="Adding successfull!";
                    isSuccess=true;
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
}

package hr.fer.amigosi.guildbuild;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import hr.fer.amigosi.guildbuild.DAO.ObrazacDAO;
import hr.fer.amigosi.guildbuild.entities.ObrazacEntity;

public class FormApplicationActivity extends AppCompatActivity {

    private EditText formDescription;
    private Button btnSendForm;
    private String nadimak;
    private int sifraTrazenogCeha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_application);

        formDescription = (EditText) findViewById(R.id.formDescription);
        btnSendForm = (Button) findViewById(R.id.btnSendForm);

        Intent pastIntent = getIntent();
        nadimak=pastIntent.getStringExtra(MainActivity.EXTRA_MESSAGE1);
        sifraTrazenogCeha=pastIntent.getIntExtra(GuildDetailsActivity.EXTRA_MESSAGE3,0);

        btnSendForm.setOnClickListener(view ->{
            SendForm sendForm = new SendForm();
            sendForm.execute("");
        });
    }

    public class SendForm extends AsyncTask<String,String,String>{
        boolean isSuccess=false;
        String z="";
        @Override
        protected String doInBackground(String... params)
        {
            String poruka;
            poruka=formDescription.getText().toString();
            if(poruka.isEmpty()){
                z= "Please write something in form!";
            }
            else
            {
                try
                {
                    ObrazacDAO obrazacDAO = new ObrazacDAO();
                    ObrazacEntity obrazacEntity = new ObrazacEntity();
                    obrazacEntity.setNadimakKorisnika(nadimak);
                    obrazacEntity.setPoruka(poruka);
                    obrazacEntity.setSifraCeha(sifraTrazenogCeha);
                    obrazacDAO.insertForm(obrazacEntity);
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

        @Override
        protected void onPostExecute(String s) {
            if(isSuccess){
                Intent form = new Intent(FormApplicationActivity.this,GuildDetailsActivity.class);
                form.putExtra(MainActivity.EXTRA_MESSAGE1,nadimak);
                form.putExtra(GuildDetailsActivity.EXTRA_MESSAGE3,sifraTrazenogCeha);
                startActivity(form);
            }else{
                Toast.makeText(FormApplicationActivity.this , s  , Toast.LENGTH_LONG).show();
            }
        }
    }
}

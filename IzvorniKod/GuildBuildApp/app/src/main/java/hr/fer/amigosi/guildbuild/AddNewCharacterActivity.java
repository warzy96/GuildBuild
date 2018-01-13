package hr.fer.amigosi.guildbuild;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mysql.jdbc.StringUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import hr.fer.amigosi.guildbuild.DAO.LikDAO;
import hr.fer.amigosi.guildbuild.entities.LikEntity;

public class AddNewCharacterActivity extends AppCompatActivity {
    private String nickNameStr;
    private EditText characterName;
    private EditText characterLevel;
    private EditText characterCraftSkills;
    private Spinner characterGameSpinner;
    private Spinner characterClassSpinner;
    private Button addCharacterBtn;
    private HashMap<String, Integer> klase;

    Connection con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_character);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        nickNameStr = intent.getStringExtra("Nickname");

        characterName=(EditText) findViewById(R.id.characterName);
        characterLevel=(EditText) findViewById(R.id.characterLevel);
        characterCraftSkills=(EditText) findViewById(R.id.characterCraftSkills);
        characterGameSpinner=(Spinner) findViewById(R.id.charGameSpinner);
        characterClassSpinner= (Spinner) findViewById(R.id.charClassSpinner);
        addCharacterBtn = (Button) findViewById(R.id.addCharacterBtn);

        characterGameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                FillSpinnerWithClasses fillSpinnerWithClasses = new FillSpinnerWithClasses();
                fillSpinnerWithClasses.execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        FillSpinnerWithGames fillSpinnerWithGames = new FillSpinnerWithGames();
        fillSpinnerWithGames.execute();


        FillSpinnerWithClasses fillSpinnerWithClasses = new FillSpinnerWithClasses();
        fillSpinnerWithClasses.execute();

        addCharacterBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                AddCharacter addCharacter=new AddCharacter();
                addCharacter.execute();
            }
        });

    }

    private class FillSpinnerWithGames extends AsyncTask<String,String,List<String>>{
        @Override
        protected void onPreExecute()
        {

        }

        @Override
        protected void onPostExecute(List<String> igre)
        {
            ArrayAdapter <String> adapter = new ArrayAdapter<String>(AddNewCharacterActivity.this,
                    android.R.layout.simple_spinner_item,igre);

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            characterGameSpinner.setAdapter(adapter);
        }
        @Override
        protected List<String> doInBackground(String... params)
        {
            String z="";
            List<String> lista = new ArrayList<>();
            try
            {
                con = DatabaseConnection.getConnection();        // Connect to database
                if (con == null)
                {
                }
                else
                {
                    String query = "select * from igra";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    while(rs.next())
                    {
                        String tmp = rs.getString("naziv");
                        lista.add(tmp);
                    }
                    con.close();

                }
            }
            catch (Exception ex)
            {
                z = ex.getMessage();
            }
            return lista;
        }
    }

    private class FillSpinnerWithClasses extends AsyncTask<String,String,List<String>> {
        @Override
        protected void onPreExecute()
        {

        }

        @Override
        protected void onPostExecute(List<String> klase)
        {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddNewCharacterActivity.this,
                    android.R.layout.simple_spinner_item, klase);

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            characterClassSpinner.setAdapter(adapter);
        }

        @Override
        protected List<String> doInBackground(String... params)
        {
            String z="";
            klase = new HashMap<>();
            try
            {
                con = DatabaseConnection.getConnection();        // Connect to database
                if (con == null)
                {
                }
                else
                {
                    String query = "select * from klasa join igra on klasa.sifIgre = igra.sifIgre " +
                                    "where igra.naziv = '" + characterGameSpinner.getSelectedItem().toString()+"'";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    while(rs.next())
                    {
                        int sifra= rs.getInt("sifKlase");
                        String naziv = rs.getString("naziv");
                        klase.put(naziv, sifra);
                    }
                    con.close();

                }
            }
            catch (Exception ex)
            {
                z = ex.getMessage();
            }
            return new ArrayList<>(klase.keySet());
        }
    }

    public class AddCharacter extends AsyncTask<String,String,String>
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
                Toast.makeText(AddNewCharacterActivity.this , r , Toast.LENGTH_LONG).show();
                Intent intent = new Intent(AddNewCharacterActivity.this, EditProfileActivity.class);
                startActivity(intent);
            }else{
                Toast.makeText(AddNewCharacterActivity.this , r , Toast.LENGTH_LONG).show();

            }
        }
        @Override
        protected String doInBackground(String... params)
        {
            String charNameStr = characterName.getText().toString().trim();
            String charLevelStr = characterLevel.getText().toString().trim();
            String charCraftSkillStr = characterCraftSkills.getText().toString().trim();
            String charClassStr = characterClassSpinner.getSelectedItem().toString().trim();

            if(charNameStr.equals(""))
                z = "Please enter character's name.";
            else if(charLevelStr.trim().equals("")){
                z = "Please enter character's level.";
            }
            else if(!StringUtils.isStrictlyNumeric(charLevelStr)){
                z="Level must be an integer!";
            }
            else if(charCraftSkillStr.equals("")){
                z="Please enter character's crafting skills.";
            }
            else if(charClassStr.equals("")){
                z="Please pick character's class.";
            }
            else
            {
                try
                {
                    //Lik nema ime u bazi??
                    LikDAO likDao= new LikDAO();
                    LikEntity lik = new LikEntity(Integer.parseInt(charLevelStr), klase.get(charClassStr), charCraftSkillStr, nickNameStr);

                    likDao.insertCharacter(lik);
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

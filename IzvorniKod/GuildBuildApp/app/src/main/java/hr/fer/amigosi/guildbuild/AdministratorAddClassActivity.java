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

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Result;

import hr.fer.amigosi.guildbuild.entities.IgraEntity;

public class AdministratorAddClassActivity extends AppCompatActivity {

    private Spinner spinner;
    private Button btnAddClass;
    private EditText etClassName;

    Connection con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrator_add_class);

        spinner = (Spinner) findViewById(R.id.spinner);
        btnAddClass = (Button) findViewById(R.id.btnAddClass);
        etClassName = (EditText) findViewById(R.id.classToAdd);

        FillSpinnerWithGames fillSpinnerWithGames = new FillSpinnerWithGames();
        fillSpinnerWithGames.execute();


        btnAddClass.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                CheckClass checkClass = new CheckClass();// this is the Asynctask, which is used to process in background to reduce load on app process
                checkClass.execute("");
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
            ArrayAdapter <String> adapter = new ArrayAdapter<String>(AdministratorAddClassActivity.this,
                    android.R.layout.simple_spinner_item,igre);

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
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

    public class CheckClass extends AsyncTask<String,String,String>
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
                Toast.makeText(AdministratorAddClassActivity.this , r , Toast.LENGTH_LONG).show();
                Intent intent = new Intent(AdministratorAddClassActivity.this, AdministratorProfileActivity.class);
                startActivity(intent);
            }else{
                Toast.makeText(AdministratorAddClassActivity.this , r , Toast.LENGTH_LONG).show();

            }
        }
        @Override
        protected String doInBackground(String... params)
        {
            String className = etClassName.getText().toString();
            String gameName = spinner.getSelectedItem().toString();
            if(className.trim().equals(""))
                z = "Please enter class name";
            else if(className.trim().length()>11){
                z = "Class name too long!";
            }
            else
            {
                try
                {
                    con = DatabaseConnection.getConnection();        // Connect to database
                    if (con == null)
                    {
                        z = "Check Your Internet Access!";
                    }
                    else
                    {
                        String query = "select * from igra join klasa on igra.sifIgre=klasa.sifIgre where igra.naziv = '"+gameName+"' and klasa.naziv = '"+className+"'";
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(query);
                        if(rs.next()){
                            z="That class already exists for game you selected!";
                            isSuccess=false;
                        }else {
                            z = "Adding successfull!";
                            isSuccess = true;
                            query = "select * from igra where naziv = '"+gameName + "'";
                            rs = stmt.executeQuery(query);
                            rs.next();
                            int sifIgre = rs.getInt("sifIgre");
                            query = "insert into klasa values (null,'"+className+"',"+sifIgre+")";
                            stmt.executeUpdate(query);
                        }
                        con.close();
                    }
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

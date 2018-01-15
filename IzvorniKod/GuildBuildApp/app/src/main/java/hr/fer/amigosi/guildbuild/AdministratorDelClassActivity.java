package hr.fer.amigosi.guildbuild;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AdministratorDelClassActivity extends AppCompatActivity {

    //TODO: Kad se brise klasa, obrisi i sve likove za tu klasu

    private Spinner gameSpinner;
    private Spinner classSpinner;
    private Button btnDeleteClass;

    Connection con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrator_del_class);

        gameSpinner = (Spinner) findViewById(R.id.spinnerGame);
        classSpinner = (Spinner) findViewById(R.id.spinnerClass);
        btnDeleteClass = (Button) findViewById(R.id.btnDelClass);

        FillSpinnerWithGames fillSpinnerWithGames = new FillSpinnerWithGames();
        fillSpinnerWithGames.execute();

        gameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                FillSpinnerWithClasses fillSpinnerWithClasses = new FillSpinnerWithClasses();
                fillSpinnerWithClasses.execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        classSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnDeleteClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteClass deleteClass = new DeleteClass();
                deleteClass.execute("");
            }
        });

    }

    private class FillSpinnerWithGames extends AsyncTask<String,String,List<String>> {
        @Override
        protected void onPreExecute()
        {

        }

        @Override
        protected void onPostExecute(List<String> igre)
        {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(AdministratorDelClassActivity.this,
                    android.R.layout.simple_spinner_item,igre);

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            gameSpinner.setAdapter(adapter);
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
                    String query = "select distinct igra.naziv from igra join klasa on igra.sifIgre=klasa.sifIgre";
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
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(AdministratorDelClassActivity.this,
                    android.R.layout.simple_spinner_item,klase);

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            classSpinner.setAdapter(adapter);
        }
        @Override
        protected List<String> doInBackground(String... params)
        {
            String z="";
            String gameName = gameSpinner.getSelectedItem().toString();
            List<String> lista = new ArrayList<>();
            try
            {
                con = DatabaseConnection.getConnection();        // Connect to database
                if (con == null)
                {
                }
                else
                {
                    String query = "select * from klasa join igra on igra.sifIgre=klasa.sifIgre where igra.naziv = '"+gameName+"'";
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

    public class DeleteClass extends AsyncTask<String,String ,String>{
        String z="";
        Boolean isSuccess;

        @Override
        protected void onPostExecute(String r) {
            if (isSuccess) {
                Toast.makeText(AdministratorDelClassActivity.this, r, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(AdministratorDelClassActivity.this, AdministratorProfileActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(AdministratorDelClassActivity.this, r, Toast.LENGTH_LONG).show();

            }
        }

        @Override
        protected String doInBackground(String... strings) {
            String gameName=gameSpinner.getSelectedItem().toString();
            String className=classSpinner.getSelectedItem().toString();

            if(className.isEmpty()){
                z="Select class!";
            }else {
                try {
                    con = DatabaseConnection.getConnection();        // Connect to database
                    if (con == null) {
                        z = "Check Your Internet Access!";
                    } else {
                        String query = "select * from igra where naziv = '" + gameName + "'";
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(query);
                        rs.next();
                        int sifIgre = rs.getInt("sifIgre");
                        query = "delete from klasa where naziv ='" + className + "' and sifIgre=" + sifIgre;
                        stmt.executeUpdate(query);
                        isSuccess = true;
                        z = "Delete successfull!";
                    }
                } catch (Exception ex) {
                    isSuccess = false;
                    z = ex.getMessage();
                }
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return z;
        }
    }
}

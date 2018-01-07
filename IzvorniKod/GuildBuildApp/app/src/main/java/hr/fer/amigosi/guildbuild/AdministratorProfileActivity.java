package hr.fer.amigosi.guildbuild;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class AdministratorProfileActivity extends AppCompatActivity {

    private Button btnViewUserReq;
    private Button btnDeleteUser;
    private Button btnDeleteGuild;
    private Button btnAddGame;

    Connection con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrator_profile);

        btnViewUserReq = (Button) findViewById(R.id.AdminAddUserButton);
        btnDeleteUser = (Button) findViewById(R.id.AdminDeleteProfileButton);
        btnDeleteGuild = (Button) findViewById(R.id.AdminDeleteGuildButton);
        btnAddGame = (Button) findViewById(R.id.AdminAddGameButton);

        btnViewUserReq.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(AdministratorProfileActivity.this, AdministratorViewUserReqActivity.class);
                startActivity(intent);
            }
        });

        btnDeleteUser.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(AdministratorProfileActivity.this, AdministratorDelAccActivity.class);
                startActivity(intent);
            }
        });

        btnDeleteGuild.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(AdministratorProfileActivity.this, AdministratorDelGuildActivity.class);
                startActivity(intent);
            }
        });


        btnAddGame.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(AdministratorProfileActivity.this, AdministratorAddGameActivity.class);
                startActivity(intent);
            }
        });
    }


}

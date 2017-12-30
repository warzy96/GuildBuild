package hr.fer.amigosi.guildbuild;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

/**
 *  @author Filip Kerman
 *  @version v1.0 30.12.2017
 */
public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


    }

    public void UserProfile(View view){
        Intent intent = new Intent(this, UserProfileActivity.class);
        startActivity(intent);
    }

    public void GuildList(View view){
        Intent intent = new Intent(this, GuildListActivity.class);
        startActivity(intent);
    }
}

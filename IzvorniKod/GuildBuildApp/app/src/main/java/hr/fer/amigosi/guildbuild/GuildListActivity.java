package hr.fer.amigosi.guildbuild;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 *  @author Filip Kerman
 *  @version v1.0 30.12.2017
 */
public class GuildListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guild_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}

package hr.fer.amigosi.guildbuild;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 *  @author Filip Kerman
 *  @version v1.0 30.12.2017
 */
public class HomeActivity extends AppCompatActivity {
    private static String nickname;
    private static String sifraCeha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Intent intent = getIntent();
        nickname = intent.getStringExtra(MainActivity.EXTRA_MESSAGE1);
        sifraCeha = intent.getStringExtra(MainActivity.EXTRA_MESSAGE2);

        TextView textView = findViewById(R.id.Nickname);
        textView.setText(nickname);

        Button myGuildButton = findViewById(R.id.MyGuild);
        Button createNewGuildButton = findViewById(R.id.CreateNewGuildButton);
        Button guildListButton = findViewById(R.id.GuildList);


        //Ako korisnik nije u cehu -> sakrij gumb za pregled njegovog ceha
        //Ako korisnik je u cehu -> sakrij gumb za stvaranje novog ceha
        if(sifraCeha == null) {
            myGuildButton.setVisibility(View.GONE);
        }

        createNewGuildButton.setOnClickListener(view -> {
            Intent newGuild = new Intent(HomeActivity.this, GuildCreateActivity.class);
            newGuild.putExtra(MainActivity.EXTRA_MESSAGE1, nickname);
            startActivity(newGuild);
        });

        myGuildButton.setClickable(true);
        myGuildButton.setOnClickListener(view -> {
            Intent myGuild = new Intent(HomeActivity.this, GuildDetailsActivity.class);
            myGuild.putExtra(MainActivity.EXTRA_MESSAGE1, nickname);
            myGuild.putExtra(MainActivity.EXTRA_MESSAGE2, sifraCeha);
            myGuild.putExtra(GuildDetailsActivity.EXTRA_MESSAGE3,sifraCeha);
            startActivity(myGuild);
        });

        guildListButton.setClickable(true);
        guildListButton.setOnClickListener(view -> {
            Intent guildListIntent = new Intent(HomeActivity.this, GuildListActivity.class);
            guildListIntent.putExtra(MainActivity.EXTRA_MESSAGE1, nickname);
            guildListIntent.putExtra(MainActivity.EXTRA_MESSAGE2, sifraCeha);
            startActivity(guildListIntent);
        });
    }

    public void UserProfile(View view){
        Intent intent = new Intent(this, UserProfileActivity.class);
        intent.putExtra(MainActivity.EXTRA_MESSAGE1, nickname);
        intent.putExtra(GuildMembersActivity.NADIMAK_KORISNIKA_KOJI_POKRECE_ACTIVITY, nickname);
        intent.putExtra(MainActivity.EXTRA_MESSAGE2, sifraCeha);
        startActivity(intent);
    }

    public void GuildList(View view){
        Intent intent = new Intent(this, GuildListActivity.class);
        intent.putExtra(MainActivity.EXTRA_MESSAGE1, nickname);
        startActivity(intent);
    }
}

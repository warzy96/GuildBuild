package hr.fer.amigosi.guildbuild;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 *  @author Filip Kerman
 *  @version v1.0 30.12.2017
 */
public class HomeActivity extends AppCompatActivity {
    private static String nickname;
    private static int sifraCeha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Intent intent = getIntent();
        nickname = intent.getStringExtra(MainActivity.EXTRA_MESSAGE1);
        sifraCeha = intent.getIntExtra(MainActivity.EXTRA_MESSAGE2, 0);

        TextView textView = findViewById(R.id.Nickname);
        textView.setText(nickname);

        Button myGuildButton = findViewById(R.id.MyGuild);
        Button listOfGuildsButton = findViewById(R.id.GuildList);

        //Ako korisnik nije u cehu -> sakrij gumb za pregled njegovog ceha
        if(sifraCeha == 0) {
            myGuildButton.setVisibility(View.INVISIBLE);
        }

        myGuildButton.setOnClickListener(view -> {
            Intent myGuild = new Intent(HomeActivity.this, MyGuildActivity.class);
            startActivity(myGuild);
        });
    }

    public void UserProfile(View view){
        Intent intent = new Intent(this, UserProfileActivity.class);
        intent.putExtra(MainActivity.EXTRA_MESSAGE1, nickname);
        intent.putExtra(MainActivity.EXTRA_MESSAGE2, sifraCeha);
        startActivity(intent);
    }

    public void GuildList(View view){
        Intent intent = new Intent(this, GuildListActivity.class);
        intent.putExtra(MainActivity.EXTRA_MESSAGE1, nickname);
        startActivity(intent);
    }
}

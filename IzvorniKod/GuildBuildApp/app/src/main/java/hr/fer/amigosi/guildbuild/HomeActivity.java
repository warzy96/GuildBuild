package hr.fer.amigosi.guildbuild;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import static hr.fer.amigosi.guildbuild.MainActivity.EXTRA_MESSAGE1;

/**
 *  @author Filip Kerman
 *  @version v1.0 30.12.2017
 */
public class HomeActivity extends AppCompatActivity {
    private String nickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent = getIntent();
        nickname = intent.getStringExtra(EXTRA_MESSAGE1);

        TextView textView = (TextView) findViewById(R.id.Nickname);
        textView.setText(nickname);

    }

    public void UserProfile(View view){
        Intent intent = new Intent(this, UserProfileActivity.class);
        intent.putExtra("Nickname", nickname);
        startActivity(intent);
    }

    public void GuildList(View view){
        Intent intent = new Intent(this, GuildListActivity.class);
        startActivity(intent);
    }
}

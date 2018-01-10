package hr.fer.amigosi.guildbuild;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
/**
 *  @author Filip Kerman
 *  @version v1.0 30.12.2017
 */

public class UserProfileActivity extends AppCompatActivity {
    private static String nickname;
    private static int sifraCeha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent pastIntent = getIntent();
        nickname = pastIntent.getStringExtra(MainActivity.EXTRA_MESSAGE1);
        sifraCeha = pastIntent.getIntExtra(MainActivity.EXTRA_MESSAGE2, 0);
    }

    public void Messages(View view){
        Intent intent = new Intent(this, MessagesActivity.class);
        intent.putExtra(MainActivity.EXTRA_MESSAGE1, nickname);
        startActivity(intent);
    }

    public void EditProfile(View view){
        Intent intent = new Intent(this, EditProfileActivity.class);
        intent.putExtra(MainActivity.EXTRA_MESSAGE1, nickname);
        startActivity(intent);
    }
}

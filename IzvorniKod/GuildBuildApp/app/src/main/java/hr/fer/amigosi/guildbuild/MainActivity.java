package hr.fer.amigosi.guildbuild;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE1 ="aaa";
    public static final String EXTRA_MESSAGE2 ="bbb";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginscreen);

    }

    public void userProfile(View view){
        Intent intent = new Intent(this, UserProfileActivity.class);
        EditText email = (EditText) findViewById(R.id.editText6);
        EditText password = (EditText) findViewById(R.id.editText7);
        String message1 = email.getText().toString();
        String message2 = password.getText().toString();
        intent.putExtra(EXTRA_MESSAGE1,message1);
        intent.putExtra(EXTRA_MESSAGE2,message2);
        startActivity(intent);
    }
}

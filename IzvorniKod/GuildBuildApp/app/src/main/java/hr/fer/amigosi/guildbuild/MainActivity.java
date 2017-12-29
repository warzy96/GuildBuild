package hr.fer.amigosi.guildbuild;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE1 ="aaa";
    public static final String EXTRA_MESSAGE2 ="bbb";

    private EditText etEmail;
    private EditText etPassword;
    private Button btnRegister;
    private Button btnLogin;

    private String email, password;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etEmail = (EditText) findViewById(R.id.emailText);
        etPassword = (EditText) findViewById(R.id.passwordText);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegister = (Button) findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
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

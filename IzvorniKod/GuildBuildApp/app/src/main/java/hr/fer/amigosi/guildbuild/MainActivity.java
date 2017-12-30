package hr.fer.amigosi.guildbuild;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
/**
 *  @author Filip Kerman
 *  @version v1.0 29.12.2017
 */

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE1 ="aaa";
    public static final String EXTRA_MESSAGE2 ="bbb";

//    private EditText etEmail;
//    private EditText etPassword;
//    private Button btnRegister;
//    private Button btnLogin;
//
//    private String email, password;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        etEmail = (EditText) findViewById(R.id.emailTextLogin);
//        etPassword = (EditText) findViewById(R.id.passwordTextLogin);
//        btnLogin = (Button) findViewById(R.id.btnLogin);
//        btnRegister = (Button) findViewById(R.id.btnRegister);
//
//        etEmail.setHint("Email");
//        etPassword.setHint("Password");
//        etEmail.setHintTextColor(getResources().getColor(R.color.colorPrimaryDark));
//        etPassword.setHintTextColor(getResources().getColor(R.color.colorPrimaryDark));

//        btnRegister.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        btnLogin.setOnClickListener(v -> {
//            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
//            startActivity(intent);
//            Toast toast = Toast.makeText(MainActivity.this, "Logged in!", Toast.LENGTH_LONG);
//            toast.show();
//        });
    }

    public void userProfile(View view){
        Intent intent = new Intent(this, UserProfileActivity.class);
        EditText email = (EditText) findViewById(R.id.emailTextLogin);
        EditText password = (EditText) findViewById(R.id.passwordTextLogin);
        String message1 = email.getText().toString();
        String message2 = password.getText().toString();
        intent.putExtra(EXTRA_MESSAGE1,message1);
        intent.putExtra(EXTRA_MESSAGE2,message2);
        startActivity(intent);
    }

    public void Register(View view){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void Home(View view){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        Toast toast = Toast.makeText(MainActivity.this, "Logged in!", Toast.LENGTH_LONG);
        toast.show();
    }

}

package hr.fer.amigosi.guildbuild;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static hr.fer.amigosi.guildbuild.MainActivity.EXTRA_MESSAGE1;
/**
 *  @author Filip Kerman
 *  @version v1.0 29.12.2017
 */

public class RegisterActivity extends AppCompatActivity {
//    private EditText etEmailRegister;
//    private EditText etPasswordRegister;
//    private EditText etNicknameRegister;
//    private Button btnRegister2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        etNicknameRegister = (EditText) findViewById(R.id.nicknameTextRegister);
//        etEmailRegister = (EditText) findViewById(R.id.emailTextRegister);
//        etPasswordRegister = (EditText) findViewById(R.id.passwordTextRegister);
//        btnRegister2 = (Button) findViewById(R.id.btnRegister2);
//
//        etNicknameRegister.setHint("Nickname");
//        etEmailRegister.setHint("Email");
//        etPasswordRegister.setHint("Password");
//        etEmailRegister.setHintTextColor(getResources().getColor(R.color.colorPrimaryDark));
//        etPasswordRegister.setHintTextColor(getResources().getColor(R.color.colorPrimaryDark));
//        etNicknameRegister.setHintTextColor(getResources().getColor(R.color.colorPrimaryDark));

//        btnRegister2.setOnClickListener(v -> {
//            Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
//            Toast toast = Toast.makeText(RegisterActivity.this, "Registration complete!", Toast.LENGTH_LONG);
//            toast.show();
//        });
    }

    public void Home(View view){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        Toast toast = Toast.makeText(RegisterActivity.this, "Registration complete!", Toast.LENGTH_LONG);
        toast.show();
    }
}


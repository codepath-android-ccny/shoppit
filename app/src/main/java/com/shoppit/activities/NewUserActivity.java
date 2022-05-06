package com.shoppit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shoppit.R;

public class NewUserActivity extends AppCompatActivity {
    public static final String TAG ="NewUserActivity";
    private EditText etName;
    private EditText etEmail;
    private EditText etNewusername;
    private EditText etNewpassword;
    private Button btnNewSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newuser);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etNewusername = findViewById(R.id.etNewusername);
        etNewpassword = findViewById(R.id.etNewpassword);
        btnNewSignup = findViewById(R.id.btnNewSignup);

        btnNewSignup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.i(TAG, "Clicked the login button");
                String name = etName.getText().toString();
                String email = etEmail.getText().toString();
                String username = etNewusername.getText().toString();
                String password = etNewpassword.getText().toString();
                loginUser(name,email,username,password);

            }
        });
    }

    private void loginUser(String name, String email, String username, String password) {
        Log.i(TAG, "Attempting to create new user:" + username);

        ParseUser user = new ParseUser();
        user.put("Name", "name");
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(password);

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.e(TAG, "Issue with creating new user", e);
                    return;
                }
                goLoginActivity();
                Toast.makeText(NewUserActivity.this,"New User Created", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void goLoginActivity() {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        finish();
    }

}


package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    String username, password, emailAddress;
    EditText nameText, passwordText, emailText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        nameText = findViewById(R.id.username_text);
        passwordText = findViewById(R.id.password_text);
        emailText = findViewById(R.id.email_text);
    }


    public void login(View view) {

        // todo error handling
        username = nameText.getText().toString();
        password = passwordText.getText().toString();
        emailAddress = emailText.getText().toString();
    }
}

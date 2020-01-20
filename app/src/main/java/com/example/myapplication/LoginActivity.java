package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LoginActivity extends AppCompatActivity {

    EditText passwordText, emailText;
    private FirebaseAuth firebaseAuth;
    private Pattern emailChecker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null) {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();
        }
        emailChecker = Pattern.compile(".+@.+\\..+");

        assignViewsFromLayout();
    }

    private void assignViewsFromLayout() {
        passwordText = findViewById(R.id.password_text);
        emailText = findViewById(R.id.email_text);

        passwordText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                passwordText.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_blue_light), PorterDuff.Mode.SRC_ATOP);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        emailText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                emailText.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_blue_light), PorterDuff.Mode.SRC_ATOP);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

    }


    public void login(View view) {

        // todo error handling
        final String emailAddress = emailText.getText().toString();
        final String password = passwordText.getText().toString();
        final LoginActivity thisActivity = this;

        firebaseAuth.signInWithEmailAndPassword(emailAddress, password).addOnCompleteListener(this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();

                            Toast.makeText(LoginActivity.this, "Login successful!",
                                    Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(thisActivity, MainActivity.class);
                            startActivity(i);
                        } else {

                            // display error in
                        }
                    }
                });
    }


    /**
     * check whether input fields have valid values
     * @param view
     */
    public void checkInput(View view){
        boolean good = true;

        if(passwordText.length() <6){
            passwordText.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
            good = false;
        }
        Matcher matcher = emailChecker.matcher(emailText.getText().toString());
        if(!matcher.matches()){
            emailText.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
            good = false;
        }

        if (good){
            login(view);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        // todo sign out?
    }

    public void register(View view) {
        Intent i = new Intent(this, RegisterActivity.class);
        startActivityForResult(i, 1); //todo - pick better request code
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == resultCode) {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();
        }
    }
}

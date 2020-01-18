package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity {

    EditText passwordText, emailText;
    private FirebaseAuth firebaseAuth;


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

        assignViewsFromLayout();
    }

    private void assignViewsFromLayout() {
        passwordText = findViewById(R.id.password_text);
        emailText = findViewById(R.id.email_text);
    }

    @Override
    public void onStart() {
        super.onStart();

        //todo sign in?
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

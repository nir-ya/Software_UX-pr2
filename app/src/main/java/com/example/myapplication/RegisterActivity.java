package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class RegisterActivity extends AppCompatActivity {

    EditText nameText, passwordText, emailText;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();

        assignViewsFromLayout();
    }

    private void assignViewsFromLayout() {
        nameText = findViewById(R.id.username_text);
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
        final String username = nameText.getText().toString();
        final String emailAddress = emailText.getText().toString();
        final String password = passwordText.getText().toString();

        firebaseAuth.signInWithEmailAndPassword(emailAddress, password).addOnCompleteListener(this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            Toast.makeText(RegisterActivity.this, "user logged in",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else {

                            register(username, emailAddress, password);
                        }
                    }
                });
    }


    private void register(String username, String emailAddress, String password) {
        firebaseAuth.createUserWithEmailAndPassword(emailAddress, password).addOnCompleteListener(this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(RegisterActivity.this, "user signed up",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else {

                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                });
    }


    @Override
    protected void onStop() {
        super.onStop();

        // todo sign out?
    }
}

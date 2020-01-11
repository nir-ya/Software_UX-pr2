package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

    private static final String TAG = "shevach";
    //    String username, password, emailAddress;
    EditText nameText, passwordText, emailText;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        nameText = findViewById(R.id.username_text);
        passwordText = findViewById(R.id.password_text);
        emailText = findViewById(R.id.email_text);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    public void login(View view) {

        // todo error handling
        String username = nameText.getText().toString();
        String password = passwordText.getText().toString();
        String emailAddress = emailText.getText().toString();

        // todo delete
        String testMessage =
                "username: " + username + "\n email: " + emailAddress + "\n password: " + password;
        Toast.makeText(this, testMessage, Toast.LENGTH_SHORT).show();

        mAuth.createUserWithEmailAndPassword(emailAddress, password).addOnCompleteListener(this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }

                });
    }

    private void updateUI(FirebaseUser user) {
        if (user == null) {
            Toast.makeText(this, "no user", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "user logged in", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        mAuth.signOut();
    }
}

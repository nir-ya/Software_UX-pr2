package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;



import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegisterActivity extends AppCompatActivity {

    EditText nameText, passwordText, emailText;
    private FirebaseAuth firebaseAuth;
    private Pattern emailChecker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();

        assignViewsFromLayout();
        emailChecker = Pattern.compile(".+@.+\\..+");
    }

    private void assignViewsFromLayout() {
        nameText = findViewById(R.id.username_text);
        passwordText = findViewById(R.id.password_text);
        emailText = findViewById(R.id.email_text);

        nameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                nameText.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_blue_light), PorterDuff.Mode.SRC_ATOP);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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

    /**
     * function is responsible for registering new user
     * @param view
     */
    public void register(View view) {
        // todo - maybe find a way to do this with one firebase call
        final String username = nameText.getText().toString();
        final String emailAddress = emailText.getText().toString();
        final String password = passwordText.getText().toString();
        firebaseAuth.createUserWithEmailAndPassword(emailAddress, password).addOnCompleteListener(this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            // create update to add display name to user
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(username)
                                    .build();
                            user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(RegisterActivity.this, "Register successful!",
                                                Toast.LENGTH_SHORT).show();
                                        setResult(1);
                                        finish();
                                    } else {
                                        //todo - maybe do something
                                    }
                                }
                            });


                        } else {
                            Toast.makeText(RegisterActivity.this, "Problem while Registering",
                                    Toast.LENGTH_SHORT).show();

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
        if(nameText.length() <3){
            nameText.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
            good = false;
        }

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
            register(view);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

    }
}

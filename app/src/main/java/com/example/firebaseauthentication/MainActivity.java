package com.example.firebaseauthentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    EditText inputEmail, inputPassword;
    Button signUpBtn, haveAccBtn;
    String emailValidation = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputEmail = findViewById(R.id.sigup_email);
        inputPassword = findViewById(R.id.signup_password);
        signUpBtn = findViewById(R.id.signup_btn);
        haveAccBtn = findViewById(R.id.haveAcc_btn);

        mAuth = FirebaseAuth.getInstance();

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUpUser();
            }
        });

        haveAccBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendUserToLogin();
            }
        });

    }

    private void signUpUser() {
        //get the email and password into strings
        String email = inputEmail.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();

        if(!email.matches(emailValidation)){
            inputEmail.setError("Please enter a valid email address");
            inputEmail.requestFocus();
            return;
        }
        else if(email.isEmpty()){
            inputEmail.setError("This field cannot be blank");
            inputEmail.requestFocus();
            return;
        }
        else if(password.isEmpty()){
            inputPassword.setError("This field cannot be blank");
            inputPassword.requestFocus();
            return;
        }
        else if(password.length() < 6){
            inputPassword.setError("Password must be more than 6 characters long");
            inputPassword.requestFocus();
            return;
        }
        else{
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(MainActivity.this, "User has been signed up!", Toast.LENGTH_LONG).show();
                        sendUserToLogin();
                    }
                    else{
                        Toast.makeText(MainActivity.this, "User sign up failed, try again.", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }

    private void sendUserToLogin() {
        Intent intent = new Intent(MainActivity.this, Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
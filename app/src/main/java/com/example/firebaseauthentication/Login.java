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


public class Login extends AppCompatActivity {

    EditText loginEmail, loginPassword;
    Button loginBtn;
    String emailValidation = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginEmail = findViewById(R.id.login_email);
        loginPassword = findViewById(R.id.login_password);
        loginBtn = findViewById(R.id.loginBtn);

        mAuth = FirebaseAuth.getInstance();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });
    }

    private void loginUser() {
        String email = loginEmail.getText().toString().trim();
        String password = loginPassword.getText().toString().trim();

        if(!email.matches(emailValidation)){
            loginEmail.setError("Please enter a valid email address");
            loginEmail.requestFocus();
            return;
        }
        else if(email.isEmpty()){
            loginEmail.setError("This field cannot be blank");
            loginEmail.requestFocus();
            return;
        }
        else if(password.isEmpty()){
            loginPassword.setError("This field cannot be blank");
            loginPassword.requestFocus();
            return;
        }
        else if(password.length() < 6){
            loginPassword.setError("Password must be more than 6 characters long");
            loginPassword.requestFocus();
            return;
        }
        else{
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(Login.this, "User logged in!", Toast.LENGTH_LONG).show();
                        sendToProfile();
                    }
                    else{
                        Toast.makeText(Login.this, "User log in failed. Please check your credentials", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    private void sendToProfile() {
        Intent intent = new Intent(Login.this, Profile.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
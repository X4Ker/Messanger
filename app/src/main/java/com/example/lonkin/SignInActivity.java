package com.example.lonkin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private EditText ETemail;
    private EditText ETpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in

                } else {
                    // User is signed out
                    Toast.makeText(SignInActivity.this, "Aвторизация провалена", Toast.LENGTH_SHORT).show();
                }

            }
        };

        ETemail = findViewById(R.id.et_email);
        ETpassword = findViewById(R.id.et_password);

        findViewById(R.id.btn_sign_in).setOnClickListener(this);
        findViewById(R.id.btn_registration).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_sign_in){
            signin(ETemail.getText().toString(),ETpassword.getText().toString());
        } else if (view.getId() == R.id.btn_registration){
            registration(ETemail.getText().toString(),ETpassword.getText().toString());
        }
    }

    public void signin(String email , String password)
    {
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(SignInActivity.this, "Aвторизация успешна", Toast.LENGTH_SHORT).show();
                    Intent intent_main = new Intent(SignInActivity.this, MainActivity.class);
                    startActivity(intent_main);
                }else
                    Toast.makeText(SignInActivity.this, "Aвторизация провалена", Toast.LENGTH_SHORT).show();

            }
        });
    }
    public void registration (String email , String password){
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(SignInActivity.this, "Регистрация успешна", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(SignInActivity.this, "Регистрация провалена", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

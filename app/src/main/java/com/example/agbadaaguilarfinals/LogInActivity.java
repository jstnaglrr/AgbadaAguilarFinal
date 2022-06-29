package com.example.agbadaaguilarfinals;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogInActivity extends AppCompatActivity {

    private Button SignUp;
    private Button mLogin;

    private EditText mEmail;
    private EditText mPass;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        initComponents();

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LogInActivity.this, SignUpActivity.class));
            }
        });

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();

            }
        });

    }

    private void login(){

        String email = mEmail.getText().toString().trim();
        String pass = mPass.getText().toString();

        if(TextUtils.isEmpty(email)){
            return;
        }

        if(TextUtils.isEmpty(pass)){
            return;
        }

        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), task.getException().toString(), Toast.LENGTH_SHORT).show();
                }else{

                    if(!mAuth.getCurrentUser().isEmailVerified()){
                        Toast.makeText(LogInActivity.this, "Please Verify your Account", Toast.LENGTH_SHORT).show();
                    }else{
                        startActivity(new Intent(LogInActivity.this, MainActivity.class));
                    }

                }
            }
        });

    }


    private void initComponents(){

        SignUp = (Button) findViewById(R.id.btnSignUp);
        mLogin = (Button) findViewById(R.id.btnLogIn);

        mEmail = (EditText) findViewById(R.id.editEmail);
        mPass = (EditText) findViewById(R.id.editPassword);

        mAuth = FirebaseAuth.getInstance();

    }
}
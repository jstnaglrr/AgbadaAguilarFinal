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
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {

    private Button reg;

    private EditText mEmail;
    private EditText mPassword;
    private EditText mConfirm;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initComponents();

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                registerUser();

            }
        });

    }

    private void initComponents(){

        mEmail = (EditText) findViewById(R.id.editEmail);
        mPassword = (EditText) findViewById(R.id.editPassword);
        mConfirm = (EditText) findViewById(R.id.editConfirmPassword);

        reg = (Button) findViewById(R.id.btnRegister);

        mAuth = FirebaseAuth.getInstance();

    }

    private void registerUser(){

        String email = mEmail.getText().toString().trim().toLowerCase();
        String password = mPassword.getText().toString();
        String confirmPassword = mConfirm.getText().toString();

        if(!email.contains("@")){
            mEmail.setError("Invalid Email!");
            return;
        }

        if(TextUtils.isEmpty(email)){
            mEmail.setError("Required Field");
            return;
        }

        if(TextUtils.isEmpty(password)){
            mPassword.setError("Required Field");
            return;
        }

        if(!password.equals(confirmPassword)){
            mConfirm.setError("Password should be equal");
        }

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){

                    Toast.makeText(getApplicationContext(), task.getException().toString(), Toast.LENGTH_SHORT).show();

                }else{

                    Toast.makeText(getApplicationContext(), "Succesfull!", Toast.LENGTH_SHORT).show();

                    FirebaseUser user = mAuth.getCurrentUser();

                    user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (!task.isSuccessful()){
                                Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }else{
                                startActivity(new Intent(SignUpActivity.this, LogInActivity.class));
                            }
                        }
                    });

                }
            }
        });

    }

}
package com.example.e_learning;

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
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    private EditText passwordEmail;
    private Button resetPassword;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        passwordEmail = (EditText) findViewById(R.id.Forg_pswd_email);
        resetPassword = (Button) findViewById(R.id.forg_pswd_btn);

        mAuth = FirebaseAuth.getInstance();



        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String useremail = passwordEmail.getText().toString().trim();

                if (useremail.equals("")){
                    Toast.makeText(ForgotPassword.this, "Please enter your registered email ID", Toast.LENGTH_SHORT).show();
                }else {
                    mAuth.sendPasswordResetEmail(useremail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(ForgotPassword.this, "Password resent link is sent to your registered email Id", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(ForgotPassword.this, LogInActivity.class));
                            }else {
                                Toast.makeText(ForgotPassword.this, "Error in sending password reset email", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });
    }
}
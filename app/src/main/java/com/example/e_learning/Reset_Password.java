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

public class Reset_Password extends AppCompatActivity {

    private EditText passwordEmail;
    private Button resetPassword;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset__password);

        passwordEmail = (EditText) findViewById(R.id.Res_pswd_email);
        resetPassword = (Button) findViewById(R.id.res_pswd_btn);

        mAuth = FirebaseAuth.getInstance();

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String useremail = passwordEmail.getText().toString().trim();

                if (useremail.equals("")){
                    Toast.makeText(Reset_Password.this, "Please enter your registered email id", Toast.LENGTH_SHORT).show();
                }else {
                    mAuth.sendPasswordResetEmail(useremail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(Reset_Password.this, "Password reset link is sent to your registered email id", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(Reset_Password.this, MyProfile.class));
                            }else {
                                Toast.makeText(Reset_Password.this, "Error in sending password reset email!!", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
            }
        });
    }
}
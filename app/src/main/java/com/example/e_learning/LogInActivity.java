
package com.example.e_learning;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogInActivity extends AppCompatActivity {

    private EditText loginEmail;
    private EditText loginPassword;
    private Button loginBtn;
    private TextView ForgotBtn;

    private FirebaseAuth mAuth;
    private ProgressBar loginProgressBar;
    private TextView LogIn_tv_Sign_up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        mAuth = FirebaseAuth.getInstance();

        loginEmail = (EditText) findViewById(R.id.Log_in_Email);
        loginPassword = (EditText) findViewById(R.id.Log_In_Pswd);
        loginBtn = (Button) findViewById(R.id.Log_In_Btn);
        ForgotBtn = (TextView) findViewById(R.id.Log_In_FrgtPswd);
        loginProgressBar = (ProgressBar) findViewById(R.id.Log_In_ProgressBar);
        LogIn_tv_Sign_up = (TextView)findViewById(R.id.Log_in_tv_Signup);

            ForgotBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent ForgIntent = new Intent(LogInActivity.this, ForgotPassword.class);
                    startActivity(ForgIntent);
                }
            });

            LogIn_tv_Sign_up.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent LogInIntent = new Intent(LogInActivity.this, SignUpActivity.class);
                    startActivity(LogInIntent);
                }
            });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String loginemail = loginEmail.getText().toString();
                String loginpass = loginPassword.getText().toString();

                if (!TextUtils.isEmpty(loginemail) && !TextUtils.isEmpty(loginpass)){
                    if(isValid(loginemail)){
                        if(isValidPassword(loginpass)){
                            loginProgressBar.setVisibility(View.VISIBLE);

                            mAuth.signInWithEmailAndPassword(loginemail, loginpass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()){
                                        sendToMain();

                                    }else {

                                        String errorMessage = task.getException().getMessage();
                                        Toast.makeText(LogInActivity.this, "Error :" +errorMessage, Toast.LENGTH_LONG).show();
                                    }

                                    loginProgressBar.setVisibility(View.INVISIBLE);
                                }
                            });
                        }

                    }

                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null){

            sendToMain();

        }
    }

    private void sendToMain() {

        Intent mainIntent = new Intent(LogInActivity.this, MainActivity.class);
        startActivity(mainIntent);
        finish();
    }

    public static boolean isValidPassword(String password) {

        // Regex to check valid password.
        String regex = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{8,20}$";

        // Compile the ReGex
        Pattern p = Pattern.compile(regex);

        // If the password is empty
        // return false
        if (password == null) {
            return false;
        }

        // Pattern class contains matcher() method
        // to find matching between given password
        // and regular expression.
        Matcher m = p.matcher(password);

        // Return if the password
        // matched the ReGex
        return m.matches();
    }


        public static boolean isValid(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }
}
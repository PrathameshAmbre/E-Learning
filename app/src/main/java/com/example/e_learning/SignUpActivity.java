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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    private EditText S_Up_Name;
    private EditText S_Up_Email;
    private EditText S_Up_Password;
    private EditText S_Up_ConPassword;
    private TextView S_Up_gender;
    private RadioButton S_Up_Male;
    private RadioButton S_Up_Female;
    private Button S_Up_RegisterBtn;
    private ProgressBar S_Up_Progressbar;
    private TextView S_Up_Log_in_tv;
    private RadioGroup RG;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        S_Up_Name = (EditText) findViewById(R.id.Sign_Up_Name);
        S_Up_Email = (EditText) findViewById(R.id.Sign_Up_email);
        S_Up_Password = (EditText)findViewById(R.id.Sign_Up_Password);
        S_Up_ConPassword = (EditText)findViewById(R.id.Sign_Up_ConPswd);
        S_Up_gender = (TextView) findViewById(R.id.tv_Reg_Gender);
        RG = (RadioGroup) findViewById(R.id.radioGroup_signup);
        S_Up_Male = (RadioButton) findViewById(R.id.rb_Male_sign_up);
        S_Up_Female = (RadioButton) findViewById(R.id.rb_Female_sign_up);
        S_Up_RegisterBtn = (Button) findViewById(R.id.Sign_Up_Btn);
        S_Up_Progressbar = (ProgressBar)findViewById(R.id.Sign_Up_progressBar);
        S_Up_Log_in_tv = (TextView)findViewById(R.id.Sign_Up_tv_login);

        S_Up_Log_in_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signupIntent = new Intent(SignUpActivity.this, LogInActivity.class);
                startActivity(signupIntent);
            }
        });

        S_Up_RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (RG.getCheckedRadioButtonId() == -1){
                    Toast.makeText(SignUpActivity.this, "Select Gender", Toast.LENGTH_SHORT).show();
                }else {
                    RadioButton rb = (RadioButton) RG.findViewById(RG.getCheckedRadioButtonId());

                    String name = S_Up_Name.getText().toString();
                    String email = S_Up_Email.getText().toString();
                    String password = S_Up_Password.getText().toString();
                    String conpassword = S_Up_ConPassword.getText().toString();
                    String gender = rb.getText().toString();

                    if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(conpassword)){

                        if (password.equals(conpassword)){

                            if(isValidPassword(password)){
                                if(isValid(email)){
                                    S_Up_Progressbar.setVisibility(View.VISIBLE);
                                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {

                                            if (task.isSuccessful()){
                                                FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                                                String uid = current_user.getUid();

                                                mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

                                                HashMap<String, String> userMap = new HashMap<>();
                                                userMap.put("name", name);
                                                userMap.put("email", email);
                                                userMap.put("gender", gender);

                                                mDatabase.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {

                                                        if(task.isSuccessful()){
                                                            sendToMain();
                                                        }

                                                    }
                                                });
                                            } else {
                                                String errorMessage = task.getException().getMessage();
                                                Toast.makeText(SignUpActivity.this, "Error :"+ errorMessage, Toast.LENGTH_LONG).show();
                                            }

                                            S_Up_Progressbar.setVisibility(View.INVISIBLE);
                                        }
                                    });
                                }else{
                                    Toast.makeText(SignUpActivity.this, "Email is not Valid", Toast.LENGTH_LONG).show();
                                }
                            }else{
                                Toast.makeText(SignUpActivity.this, "Password is not valid", Toast.LENGTH_LONG).show();
                            }
                        }else {
                            Toast.makeText(SignUpActivity.this, "Password and confirm password does not match", Toast.LENGTH_LONG).show();
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

        Intent mainIntent = new Intent(SignUpActivity.this, LogInActivity.class);
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
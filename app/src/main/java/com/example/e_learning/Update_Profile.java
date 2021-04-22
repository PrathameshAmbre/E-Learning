package com.example.e_learning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.google.firebase.auth.FirebaseAuth;

public class Update_Profile extends AppCompatActivity {


    private EditText name;
    private EditText username;
    private RadioButton male;
    private RadioButton female;
    private Button updPro;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update__profile);
        
        mAuth = FirebaseAuth.getInstance();

        name = (EditText) findViewById(R.id.name_up);
        username = (EditText) findViewById(R.id.username_up);
        male = (RadioButton) findViewById(R.id.tv_Male_up);
        female = (RadioButton) findViewById(R.id.tv_Female_up);
        updPro = (Button) findViewById(R.id.btn_updpro_up);

        updPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent updIntent = new Intent(Update_Profile.this, MyProfile.class);
                startActivity(updIntent);
            }
        });
    }
}
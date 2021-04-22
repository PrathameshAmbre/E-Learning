package com.example.e_learning;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyProfile extends AppCompatActivity {

    private TextView abt_tv_mp;
    private EditText name_abt;
    private EditText username_abt;
    private EditText email_abt;
    private EditText pass_abt;
    private TextView gender_abt;
    private RadioButton male_abt;
    private RadioButton female_abt;
    private Button res_pwd;
    private Button upd_pro;
    String email;



    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private static final String USERS = "users";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        Intent intent = getIntent();
        email = intent.getStringExtra(email);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");


        abt_tv_mp = (TextView) findViewById(R.id.tvmp);
        name_abt = (EditText) findViewById(R.id.name_mp);
        username_abt = (EditText) findViewById(R.id.username_mp);
        email_abt = (EditText) findViewById(R.id.mp_emailid);
        pass_abt = (EditText) findViewById(R.id.mp_pswd);
        gender_abt = (TextView) findViewById(R.id.tv_gender_mp);
        male_abt = (RadioButton) findViewById(R.id.rb_male_mp);
        female_abt = (RadioButton) findViewById(R.id.rb_female_mp);
        res_pwd = (Button) findViewById(R.id.Pswd_res_mp);
        upd_pro = (Button) findViewById(R.id.btn_updpro_up);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    if (ds.child(email).getValue().equals(email)){
                        name_abt.setText(ds.child("name").getValue(String.class));
                        email_abt.setText(email);
                        gender_abt.setText(ds.child("gender").getValue(String.class));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        res_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reset_intent = new Intent(MyProfile.this,Reset_Password.class);
                startActivity(reset_intent);
            }
        });
        upd_pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent updpro_intent = new Intent(MyProfile.this, Update_Profile.class);
                startActivity(updpro_intent);
            }
        });

    }
}
package com.example.vehiclecontroller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ImageView mailimg,psswdimg,login;
    EditText email,psswd;
    String str_mail,str_psswd;
    LottieAnimationView lottieAnimationView;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    boolean mailcondition=false;
    boolean passcondition=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        if(firebaseUser!=null){
            Intent intent = new Intent(MainActivity.this, MainActivity3.class);
            startActivity(intent);
            finish();
        }
        mailimg=findViewById(R.id.imageView12);
        psswdimg=findViewById(R.id.imageView13);
        email = findViewById(R.id.editTextTextEmailAddress4);
        psswd = findViewById(R.id.editTextTextPassword2);
        login = findViewById(R.id.imageView11);
        email.setVisibility(View.INVISIBLE);
        psswd.setVisibility(View.INVISIBLE);
        login.setVisibility(View.INVISIBLE);
        lottieAnimationView = findViewById(R.id.tire_loading);
        lottieAnimationView.setVisibility(View.GONE);

        mailimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mailimg.setImageResource(R.drawable.maildone);
                email.setVisibility(View.VISIBLE);
                email.requestFocus();
                mailcondition=true;
                if(mailcondition && passcondition){
                    login.setVisibility(View.VISIBLE);
                }
            }
        });

        psswdimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                psswdimg.setImageResource(R.drawable.psswddone);
                psswd.setVisibility(View.VISIBLE);
                psswd.requestFocus();
                passcondition=true;
                if(mailcondition && passcondition){
                    login.setVisibility(View.VISIBLE);
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lottieAnimationView.setVisibility(View.VISIBLE);
                lottieAnimationView.playAnimation();
                str_mail=email.getText().toString();
                str_psswd=psswd.getText().toString();

                if(str_mail.contains(" ")){
                    email.setError("Mail id cannot have white spaces");
                    lottieAnimationView.setVisibility(View.GONE);
                }

                else if(str_psswd.contains(" ")){
                    psswd.setError("Password cannot have whitespaces");
                    lottieAnimationView.setVisibility(View.GONE);
                }

                else if(str_psswd.length()<8){
                    psswd.setError("Password length cannot be less than 8");
                    lottieAnimationView.setVisibility(View.GONE);
                }

                else if(str_mail.contains("@")){
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
                    Query checkusername = reference.orderByChild("Mail id").equalTo(str_mail);
                    checkusername.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot alpha: snapshot.getChildren()){
                                String extraction = alpha.getKey();
                                if(snapshot.exists()){
                                    String psswdfromDB=snapshot.child(extraction).child("Password").getValue(String.class);
                                    if(psswdfromDB.equals(str_psswd)){
                                        String mailfromdb = snapshot.child(extraction).child("Mail id").getValue(String.class);
                                        String useridfromdb= snapshot.child(extraction).child("Uid").getValue(String.class);
                                        firebaseAuth.signInWithEmailAndPassword(mailfromdb,psswdfromDB).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if(task.isSuccessful()){
                                                    Intent intent = new Intent(MainActivity.this, MainActivity3.class);
                                                    startActivity(intent);
                                                    finish();

                                                }
                                                else{
                                                    lottieAnimationView.setVisibility(View.GONE);
                                                    Toast.makeText(MainActivity.this, "We have encountered an error", Toast.LENGTH_SHORT).show();
                                                }

                                            }
                                        });
                                    }
                                    else{
                                        lottieAnimationView.setVisibility(View.GONE);
                                        psswd.setError("Incorrect Password");
                                        psswd.requestFocus();
                                    }
                                }
                                else{
                                    lottieAnimationView.setVisibility(View.GONE);
                                    email.setError("No such mail Id found in our database");
                                    psswd.requestFocus();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
    }
}
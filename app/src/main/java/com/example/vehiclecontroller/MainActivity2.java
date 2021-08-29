package com.example.vehiclecontroller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MainActivity2 extends AppCompatActivity {

    TextView textView9;
    Button button2;
    EditText name,mail,pass;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main2);
        textView9=findViewById(R.id.textView9);
        name=findViewById(R.id.editTextTextPersonName);
        mail = findViewById(R.id.editTextTextEmailAddress3);
        pass = findViewById(R.id.editTextTextPassword);
        button2=findViewById(R.id.button2);
        auth=FirebaseAuth.getInstance();

        textView9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_name = name.getText().toString();
                String str_mail = mail.getText().toString();
                String str_pass = pass.getText().toString();

                if(str_name.length()==0){
                    name.setError("Name field cannot be empty");
                }
                else if(str_mail.length()==0){
                    mail.setError("Please enter your valid E mail address");
                }

                else if(str_pass.length()==0){
                    pass.setError("Please enter a password");
                }

                else if(str_mail.contains(" ")){
                    mail.setError("Mail id cannot have whitespaces");
                }

                else if(str_pass.contains(" ")){
                    pass.setError("Password cannot have whitespace");
                }

                else if(str_pass.length()<8){
                    pass.setError("Password must be greater than 8 character");
                }

                else{
                    auth.createUserWithEmailAndPassword(str_mail,str_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                HashMap<String, Object> m = new HashMap<String, Object>();
                                FirebaseUser fuser = auth.getCurrentUser();
                                String fuid = fuser.getUid();
                                m.put("Name",str_name);
                                m.put("Mail id",str_mail);
                                m.put("Password",str_pass);
                                FirebaseDatabase.getInstance().getReference("Users").child(fuid).setValue(m);
                                Intent intent = new Intent(MainActivity2.this,MainActivity3.class);
                                startActivity(intent);
                                finish();
                            }
                            else{
                                Toast.makeText(MainActivity2.this, "Please enter a new email", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }
}
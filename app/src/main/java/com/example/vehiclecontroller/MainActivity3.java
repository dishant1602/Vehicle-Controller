package com.example.vehiclecontroller;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MainActivity3 extends AppCompatActivity {

    TextView textView11;
    LottieAnimationView onoff;
    ImageView imageView4;
    boolean isselected=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main3);
        imageView4=findViewById(R.id.imageView4);
        textView11=findViewById(R.id.textView11);
        onoff=findViewById(R.id.ofgreen);
        MediaPlayer car_on = MediaPlayer.create(MainActivity3.this,R.raw.car_on);
        MediaPlayer car_off = MediaPlayer.create(MainActivity3.this,R.raw.car_off);
        HashMap<String, Object> m = new HashMap<String, Object>();
        m.put("Engine","On");
        FirebaseDatabase.getInstance().getReference("Vehicle Control").child("Status").setValue(m);

        onoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isselected){
                    if(car_off.isPlaying()){
                        car_off.pause();
                    }
                    car_on.seekTo(0);
                    car_on.start();
                    imageView4.setImageResource(R.drawable.armdisarmon);
                    onoff.setMinAndMaxProgress(0.5f,1.0f);
                    onoff.playAnimation();
                    isselected=false;
                    textView11.setText("Vehicle is 'ON'");
                }
                else{
                    if(car_on.isPlaying()){
                        car_on.pause();
                    }
                    car_off.seekTo(0);
                    car_off.start();
                    imageView4.setImageResource(R.drawable.armdisarm);
                    onoff.setMinAndMaxProgress(0.0f,0.5f);
                    onoff.playAnimation();
                    isselected=true;
                    textView11.setText("Vehicle is 'OFF'");
                }
            }
        });

        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isselected){
                    if(car_off.isPlaying()){
                        car_off.pause();
                    }
                    car_on.seekTo(0);
                    car_on.start();
                    HashMap<String, Object> m = new HashMap<String, Object>();
                    m.put("Engine","On");
                    FirebaseDatabase.getInstance().getReference("Vehicle Control").child("Status").setValue(m);
                    imageView4.setImageResource(R.drawable.armdisarmon);
                    onoff.setMinAndMaxProgress(0.5f,1.0f);
                    onoff.playAnimation();
                    isselected=false;
                    textView11.setText("Vehicle is 'ON'");
                }
                else{
                    if(car_on.isPlaying()){
                        car_on.pause();
                    }
                    car_off.seekTo(0);
                    car_off.start();
                    HashMap<String, Object> m = new HashMap<String, Object>();
                    m.put("Engine","Off");
                    FirebaseDatabase.getInstance().getReference("Vehicle Control").child("Status").setValue(m);
                    imageView4.setImageResource(R.drawable.armdisarm);
                    onoff.setMinAndMaxProgress(0.0f,0.5f);
                    onoff.playAnimation();
                    isselected=true;
                    textView11.setText("Vehicle is 'OFF'");
                }
            }
        });
    }
}
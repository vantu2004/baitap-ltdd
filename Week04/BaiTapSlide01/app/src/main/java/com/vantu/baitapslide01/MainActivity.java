package com.vantu.baitapslide01;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Integer> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setBackGround();
        randomBackground(randomIndex());
        randomBackgroundSwitch();
    }

    private int randomIndex(){
        Random random = new Random();
        return random.nextInt(4);
    }

    private void setBackGround(){
        arrayList = new ArrayList<>();
        arrayList.add(R.drawable.bottom_btn2);
        arrayList.add(R.drawable.bottom_btn4);
        arrayList.add(R.drawable.bottom_btn1);
        arrayList.add(R.drawable.bottom_btn3);
    }

    private void randomBackground(int index){
        ConstraintLayout constraintLayout = findViewById(R.id.main);
        constraintLayout.setBackgroundResource(arrayList.get(index));
    }

    private void randomBackgroundSwitch(){
        ConstraintLayout constraintLayout = findViewById(R.id.main);

        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch sw = (Switch) findViewById(R.id.switch1);
        sw.setOnCheckedChangeListener((buttonView, isChecked) -> {
            int index = randomIndex();
            if(isChecked){ //isChecked = true
                constraintLayout.setBackgroundResource(arrayList.get(index));
            }else{
                constraintLayout.setBackgroundResource(arrayList.get(index));
            }
        });
    }
}
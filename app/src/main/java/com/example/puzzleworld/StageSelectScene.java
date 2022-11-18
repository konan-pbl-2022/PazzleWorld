package com.example.puzzleworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.lang.reflect.Member;

public class StageSelectScene extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage_select_scene);

        Button nextButton1 = (Button)findViewById(R.id.button2);
        nextButton1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(StageSelectScene.this, MemberSelectScene.class);
                startActivity(intent);
            }
        });

        Button nextButton2 = (Button)findViewById(R.id.button4);
        nextButton2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(StageSelectScene.this, GachaScene.class);
                startActivity(intent);
            }
        });

        Button nextButton3 = (Button)findViewById(R.id.button5);
        nextButton3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(StageSelectScene.this, GameScene.class);
                startActivity(intent);
            }
        });


    }
}
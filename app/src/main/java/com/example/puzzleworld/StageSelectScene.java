package com.example.puzzleworld;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.reflect.Member;

public class StageSelectScene extends AppCompatActivity {

    @Override
    public void onBackPressed() {} //戻るボタンの無効化

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage_select_scene);

        //ガチャ石表示
        TextView textView = (TextView)findViewById(R.id.textView);
        textView.setText("×" + PlayerStatus.GachaStone);

                System.out.println(PlayerStatus.GachaStone);

        //編成ボタン
        Button nextButton1 = (Button)findViewById(R.id.button2);
        nextButton1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(StageSelectScene.this, MemberSelectScene.class);
                startActivity(intent);
            }
        });


        //ガチャボタン
        Button nextButton2 = (Button)findViewById(R.id.button4);
        nextButton2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(StageSelectScene.this, GachaScene.class);
                startActivity(intent);
            }
        });


        //ステージ1ボタン
        Button nextButton3 = (Button)findViewById(R.id.button5);
        nextButton3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                PlayerStatus.SelectStage = 1;
                Intent intent = new Intent(StageSelectScene.this, GameScene.class);
                startActivity(intent);
            }
        });


        //ステージ2ボタン
        Button nextButton4 = (Button)findViewById(R.id.button6);
        //表示
        if(PlayerStatus.CanPlayStage >= 2){
            nextButton4.setVisibility(View.VISIBLE);
        }else{
            nextButton4.setVisibility(View.INVISIBLE);
        }
        //画面切り替え
        nextButton4.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                PlayerStatus.SelectStage = 2;
                Intent intent = new Intent(StageSelectScene.this, GameScene.class);
                startActivity(intent);
            }
        });

        //ステージ3ボタン
        Button nextButton5 = (Button)findViewById(R.id.button7);
        //表示
        if(PlayerStatus.CanPlayStage >= 3){
            nextButton5.setVisibility(View.VISIBLE);
        }else{
            nextButton5.setVisibility(View.INVISIBLE);
        }
        //画面切り替え
        nextButton5.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                PlayerStatus.SelectStage = 3;
                Intent intent = new Intent(StageSelectScene.this, GameScene.class);
                startActivity(intent);
            }
        });

        //ステージ3ボタン
        Button nextButton6 = (Button)findViewById(R.id.button8);
        //表示
        if(PlayerStatus.CanPlayStage >= 4){
            nextButton6.setVisibility(View.VISIBLE);
        }else{
            nextButton6.setVisibility(View.INVISIBLE);
        }
        //画面切り替え
        nextButton6.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                PlayerStatus.SelectStage = 4;
                Intent intent = new Intent(StageSelectScene.this, GameScene.class);
                startActivity(intent);
            }
        });

    }
}
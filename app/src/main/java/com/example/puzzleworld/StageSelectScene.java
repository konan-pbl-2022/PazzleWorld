package com.example.puzzleworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.reflect.Member;

public class StageSelectScene extends AppCompatActivity {

    @Override
    public void onBackPressed() {} //戻るボタンの無効化

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage_select_scene);

        //ガチャ石表示
        TextView textView = (TextView)findViewById(R.id.textView);
        textView.setText(String.valueOf(PlayerStatus.GachaStone));

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



    }
}
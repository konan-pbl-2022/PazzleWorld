package com.example.puzzleworld;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.text.BreakIterator;
import java.util.Random;

public class GachaScene extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gacha_scene);



        /*排出率表示のボタン*/
//        ToggleButton emi = (ToggleButton) findViewById(R.id.emiButton);
//        emi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(isChecked){
//
//                }
//            }
//        } {
//            @Override
//            public void onClick(View view) {
//                TextView emiRateView = (TextView)findViewById(R.id.emiRateView);
//                emiRateView.setText("\"SSR\" ----> 2.5% × 4種 \\n\"SR\" ----> 5% × 4種\\n\"R\" ----> 7.5% × 4種\\n \"N\" ----> 12.5% × 4種\\n\"");
//            }
//        });

//        /*ガチャるボタンの処理*/
//        Button gachaButton = (Button)findViewById(R.id.button1);
//        gachaButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                TextView check = (TextView)findViewById(R.id.checkText);
//                check.setText("石を１つ使ってガチャを引きますか？");
//                Button ok = (Button)findViewById(R.id.okButton);
//                ok.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
////                        Intent intent = new Intent(GachaScene.this./*GachaResult*/.class);
////                            startActivity(intent);
////                    }
////                });
//                        Button back = (Button)findViewById(R.id.backButton);
//                        back.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                /*戻したい*/
//                            }
//                        });
//                    }
//                });

        /*ステージ画面へ*/
        Button toStage = (Button)findViewById(R.id.ToStage);
        toStage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GachaScene.this,StageSelectScene.class);
                startActivity(intent);
            }
        });

        /*パーティ編成画面へ*/
        Button toHensei = (Button)findViewById(R.id.ToHensei);
        toHensei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GachaScene.this,MemberSelectScene.class);
                startActivity(intent);
            }
        });

        /*ガチャボタンの処理*/
        Button protoButton = (Button)findViewById(R.id.protoButton);
        protoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*結果表示*/
                TextView ResultView = (TextView)findViewById(R.id.ResultView);
                //double rarity = Math.random(); /*浮動小数点ver*/
                //double type = Math.random(); /*浮動小数点ver*/
                Random rand1 = new Random();
                int rarity = rand1.nextInt(99) + 1; /*レアリティ*/
                Random rand2 = new Random();
                int type = rand2.nextInt(99) + 1; /*種別*/

                /*ガチャの排出率調整と処理*/
                if(rarity < 10) { //10%の確率で"SSR"
                    if(type < 25) {
                        ResultView.setText("SSR1");
                    }else if(type < 50){
                        ResultView.setText("SSR2");
                    }else if(type < 75){
                        ResultView.setText("SSR3");
                    }else{
                        ResultView.setText("SSR4");
                    }
                }else if(rarity < 30){ //20%の確率で"SR"
                    if(type < 25) {
                        ResultView.setText("SR1");
                    }else if(type < 50){
                        ResultView.setText("SR2");
                    }else if(type < 75){
                        ResultView.setText("SR3");
                    }else{
                        ResultView.setText("SR4");
                    }
                }else if(rarity < 60){ //30%の確率で"R"
                    if(type < 25) {
                        ResultView.setText("R1");
                    }else if(type < 50){
                        ResultView.setText("R2");
                    }else if(type < 75){
                        ResultView.setText("R3");
                    }else{
                        ResultView.setText("R4");
                    }
                }else{ //それ以外(40%)は"N"
                    if(type < 25) {
                        ResultView.setText("N1");
                    }else if(type < 50){
                        ResultView.setText("N2");
                    }else if(type < 75){
                        ResultView.setText("N3");
                    }else{
                        ResultView.setText("N4");
                    }
                }
            }
        });
    }
}
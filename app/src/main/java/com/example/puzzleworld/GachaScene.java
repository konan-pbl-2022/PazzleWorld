package com.example.puzzleworld;

import static com.example.puzzleworld.PlayerStatus.GachaStone;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Random;

public class GachaScene extends AppCompatActivity {

    ShareData d;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gacha_scene);

        d = (ShareData)getApplication();

        /*ガチャ石の表示*/
        TextView stone = (TextView)findViewById(R.id.textView);
        stone.setText("×"+PlayerStatus.GachaStone);


        /*排出率表示のボタン*/
        Switch emi = (Switch) findViewById(R.id.emiButton);
        TextView emiRateView = (TextView)findViewById(R.id.emiRateView);
        emi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    emiRateView.setVisibility(View.VISIBLE);
                    TextView emiRateView = (TextView)findViewById(R.id.emiRateView);
                    emiRateView.setText("'SSR' ----> 1.5% × 4種 \n 'SR' ----> 3.5% × 4種\n'R' ----> 5% × 4種 \n'N' ----> 15% × 4種");
                }else {
                    emiRateView.setVisibility(View.INVISIBLE);
                }
            }
        });

        /*確認の初期値*/
        TextView check = (TextView)findViewById(R.id.checkText);
        Button ok = (Button)findViewById(R.id.okButton);
        Button cancel = (Button)findViewById(R.id.cancelButton);
        check.setVisibility(View.INVISIBLE);
        ok.setVisibility(View.INVISIBLE);
        cancel.setVisibility(View.INVISIBLE);
        TextView ResultView = (TextView)findViewById(R.id.ResultView);
        ImageView resultImage = (ImageView)findViewById(R.id.resultImage);

        /*ガチャるボタンの処理*/
        Button gachaButton = (Button)findViewById(R.id.gachaButton);
        gachaButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                TextView check = (TextView)findViewById(R.id.checkText);
                Button ok = (Button)findViewById(R.id.okButton);
                Button cancel = (Button)findViewById(R.id.cancelButton);

                /*確認の表示とガチャボタンの非表示*/
                check.setVisibility(View.VISIBLE);
                ok.setVisibility(View.VISIBLE);
                cancel.setVisibility(View.VISIBLE);
                check.setText("石を１つ使ってガチャを引きますか？");
                gachaButton.setVisibility(View.INVISIBLE);
                ResultView.setVisibility(View.INVISIBLE);
                resultImage.setVisibility(View.INVISIBLE);

                /*okを押すと*/
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        /*表示の初期値*/
                        check.setVisibility(View.INVISIBLE);
                        ok.setVisibility(View.INVISIBLE);
                        cancel.setVisibility(View.INVISIBLE);
                        gachaButton.setVisibility(View.VISIBLE);
                        ResultView.setVisibility(View.VISIBLE);
                        resultImage.setVisibility(View.VISIBLE);

                        /*ガチャのシステム*/
                        if(GachaStone > 0){
                            PlayerStatus.GachaStone -= 1;
                            /*ガチャ石の表示の修正*/
                            stone.setText("ガチャ石："+String.valueOf(GachaStone));

                            /*結果表示*/
                            Random rand1 = new Random();
                            int rarity = rand1.nextInt(99) + 1; /*レアリティ*/
                            Random rand2 = new Random();
                            int type = rand2.nextInt(99) + 1; /*種別*/

                            /*ガチャの排出率調整と処理*/
                            if(rarity < 6) { //6%の確率で"SSR"
                                if(type < 25) {
                                    ResultView.setText("SSR");
                                    d.chara[12].possession = true;
                                    int chara_count12 = 0;
                                    chara_count12 += 1;
                                    if(chara_count12 >= 2){
                                        d.chara[12].number += 1;
                                    }
                                    resultImage.setImageDrawable(d.chara[12].image);
                                }else if(type < 50){
                                    ResultView.setText("SSR");
                                    d.chara[13].possession = true;
                                    int chara_count13 = 0;
                                    chara_count13 += 1;
                                    if(chara_count13 >= 2){
                                        d.chara[13].number += 1;
                                    }
                                    resultImage.setImageDrawable(d.chara[13].image);
                                }else if(type < 75){
                                    ResultView.setText("SSR");
                                    d.chara[14].possession = true;
                                    int chara_count14 = 0;
                                    chara_count14 += 1;
                                    if(chara_count14 >= 2){
                                        d.chara[14].number += 1;
                                    }
                                    resultImage.setImageDrawable(d.chara[14].image);
                                }else{
                                    ResultView.setText("SSR");
                                    d.chara[15].possession = true;
                                    int chara_count15 = 0;
                                    chara_count15 += 1;
                                    if(chara_count15 >= 2){
                                        d.chara[15].number += 1;
                                    }
                                    resultImage.setImageDrawable(d.chara[15].image);
                                }

                            }else if(rarity < 20){ //14%の確率で"SR"
                                if(type < 25) {
                                    ResultView.setText("SR");
                                    d.chara[8].possession = true;
                                    int chara_count8 = 0;
                                    chara_count8 += 1;
                                    if(chara_count8 >= 2){
                                        d.chara[8].number += 1;
                                    }
                                    resultImage.setImageDrawable(d.chara[8].image);
                                }else if(type < 50){
                                    ResultView.setText("SR");
                                    d.chara[9].possession = true;
                                    int chara_count9 = 0;
                                    chara_count9 += 1;
                                    if(chara_count9 >= 2){
                                        d.chara[9].number += 1;
                                    }
                                    resultImage.setImageDrawable(d.chara[9].image);
                                }else if(type < 75){
                                    ResultView.setText("SR");
                                    d.chara[10].possession = true;
                                    int chara_count10 = 0;
                                    chara_count10 += 1;
                                    if(chara_count10 >= 2){
                                        d.chara[10].number += 1;
                                    }
                                    resultImage.setImageDrawable(d.chara[10].image);
                                }else{
                                    ResultView.setText("SR");
                                    d.chara[11].possession = true;
                                    int chara_count11 = 0;
                                    chara_count11 += 1;
                                    if(chara_count11 >= 2){
                                        d.chara[11].number += 1;
                                    }
                                    resultImage.setImageDrawable(d.chara[11].image);
                                }

                            }else if(rarity < 40){ //20%の確率で"R"
                                if(type < 25) {
                                    ResultView.setText("R");
                                    d.chara[4].possession = true;
                                    int chara_count4 = 0;
                                    chara_count4 += 1;
                                    if(chara_count4 >= 2){
                                        d.chara[4].number += 1;
                                    }
                                    resultImage.setImageDrawable(d.chara[4].image);
                                }else if(type < 50){
                                    ResultView.setText("R");
                                    d.chara[5].possession = true;
                                    int chara_count5 = 0;
                                    chara_count5 += 1;
                                    if(chara_count5 >= 2){
                                        d.chara[5].number += 1;
                                    }
                                    resultImage.setImageDrawable(d.chara[5].image);
                                }else if(type < 75){
                                    ResultView.setText("R");
                                    d.chara[6].possession = true;
                                    int chara_count6 = 0;
                                    chara_count6 += 1;
                                    if(chara_count6 >= 2){
                                        d.chara[6].number += 1;
                                    }
                                    resultImage.setImageDrawable(d.chara[6].image);
                                }else{
                                    ResultView.setText("R");
                                    d.chara[7].possession = true;
                                    int chara_count7 = 0;
                                    chara_count7 += 1;
                                    if(chara_count7 >= 2){
                                        d.chara[7].number += 1;
                                    }
                                    resultImage.setImageDrawable(d.chara[7].image);
                                }
                            }else{ //それ以外(60%)は"N"
                                if(type < 25) {
                                    ResultView.setText("N");
                                    d.chara[0].possession = true;
                                    int chara_count0 = 0;
                                    chara_count0 += 1;
                                    if(chara_count0 >= 2){
                                        d.chara[0].number += 1;
                                    }
                                    resultImage.setImageDrawable(d.chara[0].image);
                                }else if(type < 50){
                                    ResultView.setText("N");
                                    d.chara[1].possession = true;
                                    int chara_count1 = 0;
                                    chara_count1 += 1;
                                    if(chara_count1 >= 2){
                                        d.chara[1].number += 1;
                                    }
                                    resultImage.setImageDrawable(d.chara[1].image);
                                }else if(type < 75){
                                    ResultView.setText("N");
                                    d.chara[2].possession = true;
                                    int chara_count2 = 0;
                                    chara_count2 += 1;
                                    if(chara_count2 >= 2){
                                        d.chara[2].number += 1;
                                    }
                                    resultImage.setImageDrawable(d.chara[2].image);
                                }else{
                                    ResultView.setText("N");
                                    d.chara[3].possession = true;
                                    int chara_count3 = 0;
                                    chara_count3 += 1;
                                    if(chara_count3 >= 2){
                                        d.chara[3].number += 1;
                                    }
                                    resultImage.setImageDrawable(d.chara[3].image);
                                }
                            }
                        }else{
                            resultImage.setVisibility(View.INVISIBLE);
                            TextView ResultView = (TextView)findViewById(R.id.ResultView);
                            ResultView.setText("ガチャ石が足りません");
                        }
                    }
                });

                /*cancelを押すと*/
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        check.setVisibility(View.INVISIBLE);
                        ok.setVisibility(View.INVISIBLE);
                        cancel.setVisibility(View.INVISIBLE);
                        gachaButton.setVisibility(View.VISIBLE);
                        ResultView.setVisibility(View.VISIBLE);
                        resultImage.setVisibility(View.VISIBLE);
                    }
                });
            }
        });

//        /*ガチャボタンの処理(旧作)*/
//        Button pull = (Button)findViewById(R.id.gachaButton);
//        pull.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(GachaStone > 0){
//                    PlayerStatus.GachaStone -= 1;
//                    /*ガチャ石の表示の修正*/
//                    stone.setText("ガチャ石："+String.valueOf(GachaStone));
//
//                    /*結果表示*/
//                    TextView ResultView = (TextView)findViewById(R.id.ResultView);
//                    Random rand1 = new Random();
//                    int rarity = rand1.nextInt(99) + 1; /*レアリティ*/
//                    Random rand2 = new Random();
//                    int type = rand2.nextInt(99) + 1; /*種別*/
//
//                    /*ガチャの排出率調整と処理*/
//                    if(rarity < 10) { //10%の確率で"SSR"
//                        if(type < 25) {
//                            ResultView.setText("SSR1");
//                        }else if(type < 50){
//                            ResultView.setText("SSR2");
//                        }else if(type < 75){
//                            ResultView.setText("SSR3");
//                        }else{
//                            ResultView.setText("SSR4");
//                        }
//
//                    }else if(rarity < 30){ //20%の確率で"SR"
//                        if(type < 25) {
//                            ResultView.setText("SR1");
//                        }else if(type < 50){
//                            ResultView.setText("SR2");
//                        }else if(type < 75){
//                            ResultView.setText("SR3");
//                        }else{
//                            ResultView.setText("SR4");
//                        }
//
//                    }else if(rarity < 60){ //30%の確率で"R"
//                        if(type < 25) {
//                            ResultView.setText("R1");
//                        }else if(type < 50){
//                            ResultView.setText("R2");
//                        }else if(type < 75){
//                            ResultView.setText("R3");
//                        }else{
//                            ResultView.setText("R4");
//                        }
//                    }else{ //それ以外(40%)は"N"
//                        if(type < 25) {
//                            ResultView.setText("N1");
//                        }else if(type < 50){
//                            ResultView.setText("N2");
//                        }else if(type < 75){
//                            ResultView.setText("N3");
//                        }else{
//                            ResultView.setText("N4");
//                        }
//                    }
//                }else{
//                    TextView ResultView = (TextView)findViewById(R.id.ResultView);
//                    ResultView.setText("ガチャ石が足りません");
//                }
//            }
//        });

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
    }
}
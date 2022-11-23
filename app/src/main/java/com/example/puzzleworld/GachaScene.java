package com.example.puzzleworld;

import static com.example.puzzleworld.PlayerStatus.GachaStone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Random;

public class GachaScene extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gacha_scene);

        /*ガチャ石の表示*/
        TextView stone = (TextView)findViewById(R.id.stoneView);
        stone.setText("ガチャ石："+String.valueOf(GachaStone));


        /*排出率表示のボタン*/
        Switch emi = (Switch) findViewById(R.id.emiButton);
        TextView emiRateView = (TextView)findViewById(R.id.emiRateView);
        emi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    emiRateView.setVisibility(View.VISIBLE);
                    TextView emiRateView = (TextView)findViewById(R.id.emiRateView);
                    emiRateView.setText("'SSR' ----> 2.5% × 4種 \n 'SR' ----> 5% × 4種\n'R' ----> 7.5% × 4種 \n'N' ----> 12.5% × 4種");
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
                        }else{
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
                    }
                });
            }
        });

//        /*ガチャボタンの処理*/
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
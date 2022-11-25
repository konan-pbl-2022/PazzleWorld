package com.example.puzzleworld;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ResultScene extends AppCompatActivity {
    private System system;
    ShareData d;
    private int ID[] = {R.id.Status001,R.id.Status002,R.id.Status003};
    private int leftLV[] = new int[3];
    private int levelUpCount = 0;

    @Override
    public void onBackPressed() {} //戻るボタンの無効化

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        d =(ShareData)getApplication();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_scene);
        ImageView stoneclear = (ImageView)findViewById(R.id.imageView6);
        ImageView stonehatuclear = (ImageView)findViewById(R.id.imageView7);
        ImageView stonelvbona = (ImageView)findViewById(R.id.imageView8);

        stoneclear.setVisibility(View.INVISIBLE);
        stonehatuclear.setVisibility(View.INVISIBLE);
        stonelvbona.setVisibility(View.INVISIBLE);

        TextView resultText = (TextView)findViewById(R.id.textView3);

        ImageView CharaImg1 = findViewById(R.id.Chara1);
        Drawable drawable1 = d.mainChara[0].getImage();
        CharaImg1.setImageDrawable(drawable1);
        ImageView CharaImg2 = findViewById(R.id.Chara2);
        Drawable drawable2 = d.mainChara[1].getImage();
        CharaImg2.setImageDrawable(drawable2);
        ImageView CharaImg3 = findViewById(R.id.Chara3);
        Drawable drawable3 = d.mainChara[2].getImage();
        CharaImg3.setImageDrawable(drawable3);
        TextView scoreLabel = findViewById(R.id.scoreLabel);
        TextView scoreLabel2 = findViewById(R.id.scoreLabel2);
        TextView scoreLabel3 = findViewById(R.id.scoreLabel3);
        scoreLabel.setText("");
        scoreLabel2.setText("");
        scoreLabel3.setText("");

        if(PlayerStatus.GameClear == true) {
            resultText.setText("Stage Clear");


            stoneclear.setVisibility(View.VISIBLE);

            if(PlayerStatus.CanPlayStage == PlayerStatus.SelectStage) {

                scoreLabel.setText("初クリアボーナス              x 1");
                stoneclear.setVisibility(View.VISIBLE);
                stonehatuclear.setVisibility(View.VISIBLE);

                scoreLabel2.setText("クリアボーナス              x 1");
                PlayerStatus.GachaStone += 2;
                PlayerStatus.CanPlayStage += 1;

            }else{
                scoreLabel.setText("");
                scoreLabel2.setText("クリアボーナス              x 1");
                stoneclear.setVisibility(View.VISIBLE);

                PlayerStatus.GachaStone += 1;
            }

            for(int i=0;i<3;i++) {
                leftLV[i] = d.mainChara[i].getLevel();
                if(PlayerStatus.SelectStage == 1) d.mainChara[i].EXP += 7;
                if(PlayerStatus.SelectStage == 2) d.mainChara[i].EXP += 16;
                if(PlayerStatus.SelectStage == 3) d.mainChara[i].EXP += 23;
                if(PlayerStatus.SelectStage == 4) d.mainChara[i].EXP += 57;
            }

            for(int i=0;i<3;i++){
                while(d.mainChara[i].getEXP() > d.mainChara[i].getReqEXP()){
                    if(d.mainChara[i].getLevel() == d.mainChara[i].getMaxLevel()) break;
                    levelUpCount += 1;
                    d.mainChara[i].level += 1;
                    d.mainChara[i].attack += 1;
                    d.mainChara[i].hp += 2;
                    d.mainChara[i].recovery += 1;
                    d.mainChara[i].ReqEXP += ((int)(d.mainChara[i].getReqEXP()*1.25));
                }
            }

            TextView status001 = findViewById(ID[0]);
            if(leftLV[0] == d.mainChara[0].getLevel()){
                status001.setText(d.mainChara[0].getEXP() + " / " + d.mainChara[0].getReqEXP());
            }else{
                status001.setText(d.mainChara[0].getEXP() + " / " + d.mainChara[0].getReqEXP()
                        +"\n    Level UP" + leftLV[0] + " --> " + d.mainChara[0].getLevel());
            }
            TextView status002 = findViewById(ID[1]);
            if(leftLV[1] == d.mainChara[1].getLevel()){
                status002.setText(d.mainChara[1].getEXP() + " / " + d.mainChara[1].getReqEXP());
            }else{
                status002.setText(d.mainChara[1].getEXP() + " / " + d.mainChara[1].getReqEXP()
                        +"\n    Level UP" + leftLV[1] + " --> " + d.mainChara[1].getLevel());
            }
            TextView status003 = findViewById(ID[2]);
            if(leftLV[2] == d.mainChara[2].getLevel()){
                status003.setText(d.mainChara[2].getEXP() + " / " + d.mainChara[2].getReqEXP());
            }else{
                status003.setText(d.mainChara[2].getEXP() + " / " + d.mainChara[2].getReqEXP()
                        +"\n    Level UP" + leftLV[2] + " --> " + d.mainChara[2].getLevel());
            }

            if(levelUpCount > 0) {
                scoreLabel3.setText("LvUPボーナス              x "+levelUpCount);
                stonelvbona.setVisibility(View.VISIBLE);
            }
            PlayerStatus.GachaStone += levelUpCount;
        }
        if(PlayerStatus.GameClear == false) {
            scoreLabel.setText("");
            scoreLabel2.setText("");
            resultText.setText("Game Over");

            TextView status001 = findViewById(ID[0]);
            status001.setText("何も得られませんでした...");
            TextView status002 = findViewById(ID[1]);
            status002.setText("何も得られませんでした...");
            TextView status003 = findViewById(ID[2]);
            status003.setText("何も得られませんでした...");
        }

        SharedPreferences sharedPreferences = getSharedPreferences("GAME_DATA", MODE_PRIVATE);
        int highScore = sharedPreferences.getInt("HIGH_SCORE", 0);
    }

    public void tryAgain(View view){
        startActivity(new Intent(getApplicationContext(), StageSelectScene.class));
    }
}
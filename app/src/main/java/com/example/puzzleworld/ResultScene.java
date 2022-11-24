package com.example.puzzleworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class ResultScene extends AppCompatActivity {

    @Override
    public void onBackPressed() {} //戻るボタンの無効化

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_scene);

        TextView resultText = (TextView)findViewById(R.id.textView3);
        if(PlayerStatus.GameClear == true) {
            resultText.setText("Stage Clear");

            TextView scoreLabel = findViewById(R.id.scoreLabel);
            TextView scoreLabel2 = findViewById(R.id.scoreLabel2);
            if(PlayerStatus.CanPlayStage == PlayerStatus.SelectStage) {

                scoreLabel.setText("初クリアボーナス  5 魔晶石");
                scoreLabel2.setText("クリアボーナス  1 魔晶石");
                PlayerStatus.GachaStone += 5;
                PlayerStatus.CanPlayStage += 1;

                PlayerStatus.GachaStone += 1;
            }else{
                scoreLabel.setText("");
                scoreLabel2.setText("クリアボーナス  1 魔晶石");
                PlayerStatus.GachaStone += 1;
            }

        }
        if(PlayerStatus.GameClear == false) {
            TextView scoreLabel = findViewById(R.id.scoreLabel);
            scoreLabel.setText("");
            TextView scoreLabel2 = findViewById(R.id.scoreLabel2);
            scoreLabel2.setText("");
            resultText.setText("Game Over");
        }



        SharedPreferences sharedPreferences = getSharedPreferences("GAME_DATA", MODE_PRIVATE);
        int highScore = sharedPreferences.getInt("HIGH_SCORE", 0);




    }

        public void tryAgain(View view){
            startActivity(new Intent(getApplicationContext(), StageSelectScene.class));
        }
}


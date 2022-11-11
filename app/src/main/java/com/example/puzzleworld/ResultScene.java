package com.example.puzzleworld;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ResultScene extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_scene);
    }

    public void draw(Graphics g){
        fillRect.draw(g);
        resultAnimation.update();

        g.setColor(color.WHITE);
        g.setFont(statusFont);
        int result =;
        g.drawstring("獲得報酬 : ");
        Object endScene;
        endScene;
        game.popScene();
        game.pushScene(mainScene);
    }

}
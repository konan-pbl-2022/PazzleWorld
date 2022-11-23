package com.example.puzzleworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.transition.Scene;
import android.view.View;
import android.widget.Button;

public class Title_Scene extends AppCompatActivity {

    @Override
    public void onBackPressed() {} //戻るボタンの無効化

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title__scene);

        Button nextButton = (Button)findViewById(R.id.button);
        nextButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(Title_Scene.this, StageSelectScene.class);
                startActivity(intent);
            }
        });
    }
}
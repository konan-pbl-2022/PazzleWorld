package com.example.puzzleworld;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MemberDisplayScene extends AppCompatActivity {
    ShareData d;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_display_scene);

        d = (ShareData) getApplication();
        TextView[] textView = new TextView[6];

        Intent intent = getIntent();
        int n = intent.getIntExtra("key1", 0);
        int h = intent.getIntExtra("key2", 0);

        ImageView imageView = new ImageView(this);
        imageView = (ImageView) findViewById(R.id.imageView);

        for (int i = 0; i < 6; i++) {
            textView[i] = new TextView(this);
            textView[i] = (TextView) findViewById(assignId("text", i));
        }

        if (h == 0) {
            imageView.setImageDrawable(d.mainChara[n].getImage());
            textView[0].setText("属性：" + d.mainChara[n].getAttribute());
            textView[1].setText("Lv." + d.mainChara[n].getLevel());
            textView[2].setText("攻撃力：" + d.mainChara[n].getAttack());
            textView[3].setText("HP      ：" + d.mainChara[n].getHp());
            textView[4].setText("回復力：" + d.mainChara[n].getRecovery());
            textView[5].setText("限界突破：" + d.mainChara[n].getNumber());
        } else {
            imageView.setImageDrawable(d.chara[n].getImage());
            textView[0].setText("属性：" + d.chara[n].getAttribute());
            textView[1].setText("Lv." + d.chara[n].getLevel());
            textView[2].setText("攻撃力：" + d.chara[n].getAttack());
            textView[3].setText("HP      ：" + d.chara[n].getHp());
            textView[4].setText("回復力：" + d.chara[n].getRecovery());
            textView[5].setText("限界突破：" + d.chara[n].getNumber());
        }



        Button backButton = new Button(this);
        backButton = (Button) findViewById(R.id.button);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MemberDisplayScene.this, MemberSelectScene.class);
                startActivity(intent);
            }
        });


    }

    int assignId(String s, int i) {
        Resources res = getResources();
        @SuppressLint("DiscouragedApi") int Id = res.getIdentifier(s + (i + 1), "id", getPackageName());
        return Id;
    }
}
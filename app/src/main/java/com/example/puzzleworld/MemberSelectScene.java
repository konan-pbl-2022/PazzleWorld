

package com.example.puzzleworld;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MemberSelectScene extends AppCompatActivity {

    int clicked = 0;
    int charaNum = 16;
    int mainCharaNum = 3;
    public  Character[] chara = new Character[charaNum];
    public Character[] mainChara = new Character[mainCharaNum];

    TextView[] textStatus = new TextView[3];
    TextView[] textLevel = new TextView[mainCharaNum];

    @SuppressLint({"UseCompatLoadingForDrawables", "MissingInflatedId"})
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_select_scene);

        for(int i = 0; i < charaNum; i++){
            chara[i] = new Character();
        }
        for(int i = 0; i < mainCharaNum; i++){
            mainChara[i] = new Character();
        }

//        charaの初期化（MainActivity)に書くべき
        chara[0].setStatus(1,1,1,1,1,0,false);
        chara[1].setStatus(1,2,2,2,2,0,false);
        chara[2].setStatus(1,3,3,3, 3,0,false);
        chara[3].setStatus(1,4,4,4, 4,0,false);
        chara[4].setStatus(1,1,1,1, 1,0,false);
        chara[5].setStatus(1,1,1,1, 1,0,false);
        chara[6].setStatus(1,1,1,1, 1,0,false);
        chara[7].setStatus(1,1,1,1, 1,0,false);
        chara[8].setStatus(1,1,1,1, 1,0,false);
        chara[9].setStatus(1,1,1,1, 1,0,false);
        chara[10].setStatus(1,1,1,1, 1,0,false);
        chara[11].setStatus(1,1,1,1, 1,0,false);
        chara[12].setStatus(1,1,1,1, 1,0,false);
        chara[13].setStatus(1,1,1,1, 1,0,false);
        chara[14].setStatus(1,1,1,1, 1,0,false);
        chara[15].setStatus(1,1,1,1, 1,0,false);
        chara[0].setPossession(true);
        chara[1].setPossession(true);
        chara[2].setPossession(true);
        chara[3].setPossession(true);


        chara[0].image = getResources().getDrawable(R.drawable.n1);
        chara[1].image = getResources().getDrawable(R.drawable.n2);
        chara[2].image = getResources().getDrawable(R.drawable.n3);
        chara[3].image = getResources().getDrawable(R.drawable.n4);
        chara[4].image = getResources().getDrawable(R.drawable.r1);
        chara[5].image = getResources().getDrawable(R.drawable.r2);
        chara[6].image = getResources().getDrawable(R.drawable.r3);
        chara[7].image = getResources().getDrawable(R.drawable.r4);
        chara[8].image = getResources().getDrawable(R.drawable.sr1);
        chara[9].image = getResources().getDrawable(R.drawable.sr2);
        chara[10].image = getResources().getDrawable(R.drawable.sr3);
        chara[11].image = getResources().getDrawable(R.drawable.sr4);
        chara[12].image = getResources().getDrawable(R.drawable.ssr1);
        chara[13].image = getResources().getDrawable(R.drawable.ssr2);
        chara[14].image = getResources().getDrawable(R.drawable.ssr3);
        chara[15].image = getResources().getDrawable(R.drawable.ssr4);


        Button[] transitionButton = new Button[3];
        ImageButton[] membersButton = new ImageButton[charaNum];
        final ImageButton[] mainCharaButton = new ImageButton[mainCharaNum];

//       　Button,Textの設定
        for (int i = 0; i < mainCharaNum; i++) {
            mainChara[i] = chara[i];
            textStatus[i] = (TextView) findViewById(assignId("text", i));
            textLevel[i] = (TextView) findViewById(assignId("level", i));
            setText_Level(i);
            mainCharaButton[i] = (ImageButton) findViewById(assignId("mainChara", i));
            mainCharaButton[i].setImageDrawable(mainChara[i].getImage());
            mainCharaButton[i].setBackgroundResource(R.drawable.frame_nothing);
        }
        for (int i = 0; i < charaNum; i++) {
            membersButton[i] = (ImageButton) findViewById(assignId("member", i));
            if(chara[i].getPossession()) {
                membersButton[i].setImageDrawable(chara[i].getImage());
            }else {
                membersButton[i].setImageResource(R.drawable.mark_question);
            }
        }
        setText_Status();

//上三体のButtonを押したときの処理
        mainCharaButton[0].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                buttonChangeColor(mainCharaButton, mainCharaNum);
                clicked = mCClickedJudge(mainCharaButton[0], clicked, 1);
            }
        });
        mainCharaButton[1].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                buttonChangeColor(mainCharaButton, mainCharaNum);
                clicked = mCClickedJudge(mainCharaButton[1], clicked,2);
            }
        });
        mainCharaButton[2].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                buttonChangeColor(mainCharaButton, mainCharaNum);
                clicked = mCClickedJudge(mainCharaButton[2], clicked,3);
            }
        });

//下16体を押したときの処理
        membersButton[0].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                membersButtonClicked(mainCharaButton, 0);
            }
        });
        membersButton[1].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                membersButtonClicked(mainCharaButton, 1);
            }
        });
        membersButton[2].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                membersButtonClicked(mainCharaButton, 2);
            }
        });
        membersButton[3].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                membersButtonClicked(mainCharaButton, 3);
            }
        });
        membersButton[4].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                membersButtonClicked(mainCharaButton, 4);
            }
        });
        membersButton[5].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                membersButtonClicked(mainCharaButton, 5);
            }
        });
        membersButton[6].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                membersButtonClicked(mainCharaButton, 6);
            }
        });
        membersButton[7].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                membersButtonClicked(mainCharaButton, 7);
            }
        });
        membersButton[8].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                membersButtonClicked(mainCharaButton, 8);
            }
        });
        membersButton[9].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                membersButtonClicked(mainCharaButton, 9);
            }
        });
        membersButton[10].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                membersButtonClicked(mainCharaButton, 10);
            }
        });
        membersButton[11].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                membersButtonClicked(mainCharaButton, 11);
            }
        });
        membersButton[12].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                membersButtonClicked(mainCharaButton, 12);
            }
        });
        membersButton[13].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                membersButtonClicked(mainCharaButton, 13);
            }
        });
        membersButton[14].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                membersButtonClicked(mainCharaButton, 14);
            }
        });
        membersButton[15].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                membersButtonClicked(mainCharaButton, 15);
            }
        });


//        画面遷移
        for (int i = 0; i < 3; i++) {
            transitionButton[i] = (Button) findViewById(assignId("button", i));
        }
        transitionButton[0].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MemberSelectScene.this,Title_Scene.class);
                startActivity(intent);
            }
        });
        transitionButton[1].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MemberSelectScene.this,MemberSelectScene.class);
                startActivity(intent);
            }
        });
        transitionButton[2].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MemberSelectScene.this,StageSelectScene.class);
                startActivity(intent);
            }
        });
    }

    //    リソースをIdとして割り当てる
    int assignId(String s, int i) {
        Resources res = getResources();
        int Id = res.getIdentifier(s + (i + 1), "id", getPackageName());
        return Id;
    }


    void buttonChangeColor(ImageButton[] buttons, int n) {
        for(int i = 0; i < n; i++) {
            buttons[i].setBackgroundResource(R.drawable.frame_nothing);
        }
    }

    int mCClickedJudge(ImageButton mCButton, int c, int n){
        if(c == n) {
            return 0;
        }else{
            mCButton.setBackgroundResource(R.drawable.frame_border);
            return n;
        }
    }

    //    交換処理
    void membersButtonClicked(ImageButton[] mCButton, int n) {
        if(chara[n].getPossession() && duplication(n)) {
            switch (clicked) {
                case 0:
                    break;
                case 1:
                    mainChara[0] = chara[n];
                    mCButton[0].setImageDrawable(mainChara[0].getImage());
                    setText_Status();
                    setText_Level(0);
                    break;
                case 2:
                    mainChara[1] = chara[n];
                    mCButton[1].setImageDrawable(mainChara[1].getImage());
                    setText_Status();
                    setText_Level(1);
                    break;
                case 3:
                    mainChara[2] = chara[n];
                    mCButton[2].setImageDrawable(mainChara[2].getImage());
                    setText_Status();
                    setText_Level(2);
                    break;
            }
        }
    }

    //    メインキャラの重複を無効にする処理
    boolean duplication(int n) {
        for(int i = 0; i < mainCharaNum; i++) {
            if(mainChara[i] == chara[n]) {
                return false;
            }
        }
        return true;
    }

    String cal_Attack() {
        int sum = 0;
        for(int i = 0; i < mainCharaNum; i++) {
            sum += mainChara[i].getAttack();
        }
        String val_str = String.valueOf(sum);
        return val_str;
    }
    String cal_Hp() {
        int sum = 0;
        for(int i = 0; i < mainCharaNum; i++) {
            sum += mainChara[i].getHp();
        }
        String val_str = String.valueOf(sum);
        return val_str;
    }
    String cal_Recovery() {
        int sum = 0;
        for(int i = 0; i < mainCharaNum; i++) {
            sum += mainChara[i].getAttack();
        }
        String val_str = String.valueOf(sum);
        return val_str;
    }
    void setText_Status(){
        textStatus[0].setText(cal_Attack());
        textStatus[1].setText(cal_Hp());
        textStatus[2].setText(cal_Recovery());
    }
    void setText_Level(int n) {
        String str = String.valueOf(mainChara[n].getLevel());
        textLevel[n].setText("Lv." + str);
    }


}




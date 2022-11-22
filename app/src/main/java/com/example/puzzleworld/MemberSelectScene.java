

package com.example.puzzleworld;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MemberSelectScene extends AppCompatActivity {

    ShareData d;

    int clicked = 0;

    TextView[] textStatus = new TextView[3];
    TextView[] textLevel = new TextView[3];

    @SuppressLint({"UseCompatLoadingForDrawables", "MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_select_scene);

        d = (ShareData) getApplication();

        Button[] transitionButton = new Button[3];
        ImageButton[] membersButton = new ImageButton[d.charaNum];
        final ImageButton[] mainCharaButton = new ImageButton[d.mainCharaNum];

//       　Button,Textの設定
        for (int i = 0; i < d.mainCharaNum; i++) {
            textStatus[i] = (TextView) findViewById(assignId("text", i));
            textLevel[i] = (TextView) findViewById(assignId("level", i));
            setText_Level(i);
            mainCharaButton[i] = (ImageButton) findViewById(assignId("mainChara", i));
            mainCharaButton[i].setImageDrawable(d.mainChara[i].getImage());
            mainCharaButton[i].setBackgroundResource(R.drawable.frame_nothing);
        }
        for (int i = 0; i < d.charaNum; i++) {
            membersButton[i] = (ImageButton) findViewById(assignId("member", i));
            if(d.chara[i].getPossession()) {
                membersButton[i].setImageDrawable(d.chara[i].getImage());
                membersButton[i].setBackgroundResource(R.drawable.frame_nothing);
            }else {
                membersButton[i].setImageResource(R.drawable.mark_question);
            }
        }
        setText_Status();

        TextView isi = new TextView(this);
        isi = (TextView) findViewById(R.id.isi);
        isi.setText("聖晶石：" + PlayerStatus.GachaStone);

//上三体のButtonを押したときの処理
        mainCharaButton[0].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                buttonChangeColor(mainCharaButton, d.mainCharaNum);
                clicked = mCClickedJudge(mainCharaButton[0], clicked, 1);
            }
        });
        mainCharaButton[1].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                buttonChangeColor(mainCharaButton, d.mainCharaNum);
                clicked = mCClickedJudge(mainCharaButton[1], clicked,2);
            }
        });
        mainCharaButton[2].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                buttonChangeColor(mainCharaButton, d.mainCharaNum);
                clicked = mCClickedJudge(mainCharaButton[2], clicked,3);
            }
        });
//        長押し処理
        mainCharaButton[0].setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                transition_DisplayScene(0, 0);
                return true;
            }
        });
        mainCharaButton[1].setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                transition_DisplayScene(1, 0);
                return true;
            }
        });
        mainCharaButton[2].setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                transition_DisplayScene(2, 0);
                return true;
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

        //長押ししたときの処理
        membersButton[0].setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                transition_DisplayScene(0, 1);
                return true;
            }
        });
        membersButton[1].setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                transition_DisplayScene(1, 1);
                return true;
            }
        });
        membersButton[2].setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                transition_DisplayScene(2, 1);
                return true;
            }
        });
        membersButton[3].setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                transition_DisplayScene(3, 1);
                return true;
            }
        });
        membersButton[4].setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                transition_DisplayScene(4, 1);
                return true;
            }
        });
        membersButton[5].setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                transition_DisplayScene(5, 1);
                return true;
            }
        });
        membersButton[6].setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                transition_DisplayScene(6, 1);
                return true;
            }
        });
        membersButton[7].setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                transition_DisplayScene(7, 1);
                return true;
            }
        });
        membersButton[8].setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                transition_DisplayScene(8, 1);
                return true;
            }
        });
        membersButton[9].setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                transition_DisplayScene(9, 1);
                return true;
            }
        });
        membersButton[10].setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                transition_DisplayScene(10, 1);
                return true;
            }
        });
        membersButton[11].setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                transition_DisplayScene(11, 1);
                return true;
            }
        });
        membersButton[12].setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                transition_DisplayScene(12, 1);
                return true;
            }
        });
        membersButton[13].setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                transition_DisplayScene(13, 1);
                return true;
            }
        });
        membersButton[14].setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                transition_DisplayScene(14, 1);
                return true;
            }
        });
        membersButton[15].setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                transition_DisplayScene(15, 1);
                return true;
            }
        });



//        画面遷移
        for (int i = 0; i < 3; i++) {
            transitionButton[i] = (Button) findViewById(assignId("button", i));
        }
        transitionButton[0].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MemberSelectScene.this,MemberSelectScene.class);
                startActivity(intent);
            }
        });
        transitionButton[1].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MemberSelectScene.this,StageSelectScene.class);
                startActivity(intent);
            }
        });
        transitionButton[2].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MemberSelectScene.this,GachaScene.class);
                startActivity(intent);
            }
        });
    }

    void transition_DisplayScene(int n, int h) {
        if(d.chara[n].getPossession()) {
            Intent intent = new Intent(MemberSelectScene.this, MemberDisplayScene.class);
            intent.putExtra("key1", n);
            intent.putExtra("key2", h);
            startActivity(intent);
        }
    }

//    リソースをIdとして割り当てる
    int assignId(String s, int i) {
        Resources res = getResources();
        @SuppressLint("DiscouragedApi") int Id = res.getIdentifier(s + (i + 1), "id", getPackageName());
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
        if(d.chara[n].getPossession() && duplication(n)) {
            switch (clicked) {
                case 0:
                    break;
                case 1:
                    d.mainChara[0] = d.chara[n];
                    mCButton[0].setImageDrawable(d.mainChara[0].getImage());
                    setText_Status();
                    setText_Level(0);
                    break;
                case 2:
                    d.mainChara[1] = d.chara[n];
                    mCButton[1].setImageDrawable(d.mainChara[1].getImage());
                    setText_Status();
                    setText_Level(1);
                    break;
                case 3:
                    d.mainChara[2] = d.chara[n];
                    mCButton[2].setImageDrawable(d.mainChara[2].getImage());
                    setText_Status();
                    setText_Level(2);
                    break;
            }
        }
    }



//    メインキャラの重複を無効にする処理
    boolean duplication(int n) {
        for(int i = 0; i < d.mainCharaNum; i++) {
            if(d.mainChara[i] == d.chara[n]) {
                return false;
            }
        }
        return true;
    }

    String cal_Attack() {
        int sum = 0;
        for(int i = 0; i < d.mainCharaNum; i++) {
            sum += d.mainChara[i].getAttack();
        }
        return String.valueOf(sum);
    }
    String cal_Hp() {
        int sum = 0;
        for(int i = 0; i < d.mainCharaNum; i++) {
            sum += d.mainChara[i].getHp();
        }
        return String.valueOf(sum);
    }
    String cal_Recovery() {
        int sum = 0;
        for(int i = 0; i < d.mainCharaNum; i++) {
            sum += d.mainChara[i].getAttack();
        }
        return String.valueOf(sum);
    }
    void setText_Status(){
        textStatus[0].setText(cal_Attack());
        textStatus[1].setText(cal_Hp());
        textStatus[2].setText(cal_Recovery());
    }
    @SuppressLint("SetTextI18n")
    void setText_Level(int n) {
        String str = String.valueOf(d.mainChara[n].getLevel());
        textLevel[n].setText("Lv." + str);
    }
}




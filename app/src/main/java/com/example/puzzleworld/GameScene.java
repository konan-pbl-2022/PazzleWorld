package com.example.puzzleworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GameScene extends AppCompatActivity implements View.OnTouchListener, View.OnDragListener{

    private System system;
    private Handler handler = new Handler();

    private Timer timer = new Timer();

    private GridLayout gridLayout;
    private View mDragView;

    private int vertical_num = 6;//縦のドロップ数
    private int horizontal_num = 6;//横のドロップ数

    Random rand = new Random();//ランダム;

    private int Mode = 0; //"1_Drag&Drop","2_Check&Count","3_NewSet","4_Action"//モード
    int NextMode = 0;

    int Rid[][] = {{R.id.Cir0,R.id.Cir1,R.id.Cir2,R.id.Cir3,R.id.Cir4,R.id.Cir5},
            {R.id.Cir6,R.id.Cir7,R.id.Cir8,R.id.Cir9,R.id.Cir10,R.id.Cir11},
            {R.id.Cir12,R.id.Cir13,R.id.Cir14,R.id.Cir15,R.id.Cir16,R.id.Cir17},
            {R.id.Cir18,R.id.Cir19,R.id.Cir20,R.id.Cir21,R.id.Cir22,R.id.Cir23},
            {R.id.Cir24,R.id.Cir25,R.id.Cir26,R.id.Cir27,R.id.Cir28,R.id.Cir29},
            {R.id.Cir30,R.id.Cir31,R.id.Cir32,R.id.Cir33,R.id.Cir34,R.id.Cir35}};

    //ドラッグ＆ドロップ後のステータスの入れ替えが難しかったため、Mapを採用
    Map<Integer,Integer> map = new HashMap<>();

    int DropDesign[] = {R.drawable.circle,R.drawable.circle2,R.drawable.circle3,
            R.drawable.circle4,R.drawable.circle5,R.drawable.circle6};
    int DeleteCount[] = {0,0,0,0,0};
    int DeleteSum = 0;

    ImageView circle[][] = new ImageView[vertical_num][horizontal_num];//ドロップの画像
    int ObjStatus[][] = new int[vertical_num][horizontal_num];//ドロップデザインセット
    int mapchecker[][] = new int[vertical_num][horizontal_num];

    private int leftId;
    private boolean firstchecked = false;
    float TestTimer = 0;
    float CircleSize = 0.02f;

    //1,水 2,草 3,火 4,岩
    int Chara1Status[] = {3,10,2};
    int Chara2Status[] = {2,35,12};
    int Chara3Status[] = {2,16,5};


    int MaxHp,CurrentHp;
    int CharaAttack[] = new int [3];
    int enemyAttackPoint = 15;
    int playerHealPoint = 0;
    int playerAttackPoint = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_scene);

        gridLayout = findViewById(R.id.GridLayout);

        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            View v = gridLayout.getChildAt(i);
            v.setOnTouchListener((View.OnTouchListener) this);
            v.setOnDragListener((View.OnDragListener) this);
        }

        Button StageSelectButton = (Button) findViewById(R.id.stselebutton);
        StageSelectButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(GameScene.this, StageSelectScene.class);
                startActivity(intent);
            }
        });

        Button ResultButton = (Button) findViewById(R.id.Resultbutton);
        ResultButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(GameScene.this, ResultScene.class);
                startActivity(intent);
            }
        });

        MaxHp = Chara1Status[1] + Chara2Status[1] + Chara3Status[1];
        CurrentHp = MaxHp;
        TextView HpText = (TextView) findViewById(R.id.HpText);
        HpText.setText(MaxHp + "/" + CurrentHp + " ");

        TextView AText1 = (TextView) findViewById(R.id.AttackText1);
        AText1.setText("");
        TextView AText2 = (TextView) findViewById(R.id.AttackText2);
        AText2.setText("");
        TextView AText3 = (TextView) findViewById(R.id.AttackText3);
        AText3.setText("");

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        //時間を管理するときに使えばイイ！
                        if (TestTimer < 6) TestTimer += 0.1;
                        else TestTimer = 0;

                        if (Mode == 0) FirstSetting(); //初期設定
                        //Mode == 1 ドラッグ＆ドロップ時間。　終わり次第Mode = 2へ
                        if (Mode == 2) CheckAndCount();
                        if (Mode == 3 && TestTimer > 0.3) DeleteDrop();
                        if(Mode == 4) DamageCalc();
                        if(Mode == 5) FillEmpty();//空白を新たなドロップが埋める
                        if(Mode == 6 && TestTimer > 0.5) UpdateSize();//徐々に現れる新ドロップ
                        if(Mode == 7) LastCheck();//攻撃、ダメージ処理
                    }
                });
            }
        }, 0, 7);
    }

    @Override
    public void onBackPressed() {} //戻るボタンの無効化

    private void FirstSetting() {
        for(int i=0;i<vertical_num;i++) {
            for(int j=0;j<horizontal_num;j++) {
                circle[i][j] = findViewById(Rid[i][j]);
                circle[i][j].setScaleX(0.95f);
                circle[i][j].setScaleY(0.95f);
                int num = rand.nextInt(5);//ドロップの属性をここでランダムで決める
                ObjStatus[i][j] = num;
                map.put(Rid[i][j],num);
                DropSet(i,j,num);
            }
        }
        CheckTester(0);
        Mode = 1;
    }

    // 押した時の動作
    public boolean onTouch(View v, MotionEvent motionEvent) {
        if(Mode == 1){
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                mDragView = v;

                // Viewをドラッグ状態にする。
                    v.startDrag(null, new View.DragShadowBuilder(v), v, 0);
                v.setAlpha(0);
            }

        }
        return true;
    }

    //ドラッグ＆ドロップ操作
    public boolean onDrag(View v, DragEvent event) {
        if(Mode == 1) {

            switch (event.getAction()) {
                // 手を放し、ドラッグが終了した時の処理
                case DragEvent.ACTION_DRAG_ENDED:
                        mDragView.setAlpha(1);
                        firstchecked = false;
                        Mode = 2;
                    break;
                // ドラッグ中他のViewの上に乗る時、Viewの位置を入れ替える
                case DragEvent.ACTION_DRAG_LOCATION:
                        swap(v, mDragView);
                        break;
            }
        }
        return true;
    }

    private void swap(View v1, View v2) {
        int a=0,b=0,c=0,d=0,temp = 0,temp2=0;
        // 同じViewなら入れ替える必要なし
        if (v1 == v2) return;
        if(!firstchecked)
        {
            leftId = v2.getId();
            firstchecked = true;
        }
        // レイアウトパラメータを抜き出して、入れ替えを行う
        GridLayout.LayoutParams p1, p2;
        p1 = (GridLayout.LayoutParams) v1.getLayoutParams();
        p2 = (GridLayout.LayoutParams) v2.getLayoutParams();
        gridLayout.removeView(v1);
        gridLayout.removeView(v2);
        gridLayout.addView(v1, p2);
        gridLayout.addView(v2, p1);

            for (int i = 0; i < vertical_num; i++) {
                for (int j = 0; j < horizontal_num; j++) {
                    if (leftId == Rid[i][j]) {
                        temp = leftId;
                        a=i;
                        b=j;
                    }
                    if (v1.getId() == Rid[i][j]) {
                        temp2 = Rid[i][j];
                        c=i;
                        d=j;
                    }
                }
            }
        Rid[a][b] = temp2;
        Rid[c][d] = temp;
    }

    private void CheckAndCount() {
        for(int i=0;i<vertical_num;i++) {
            for (int j = 0; j < horizontal_num; j++) {
                for (Integer key : map.keySet()) {
                    if(Rid[i][j] == key) ObjStatus[i][j] = map.get(key);
                }
            }
        }
        for(int i=0;i<vertical_num;i++) {
            for (int j = 0; j < horizontal_num; j++) {
                //判定用を設定
                mapchecker[i][j] = 9;
            }
        }
        for(int i=0;i<vertical_num;i++) {
            for (int j = 0; j < horizontal_num; j++) {
                if(j <= 3 && ObjStatus[i][j]==ObjStatus[i][j+1] && ObjStatus[i][j]==ObjStatus[i][j+2]){
                    mapchecker[i][j]=ObjStatus[i][j];
                    mapchecker[i][j+1]=ObjStatus[i][j];
                    mapchecker[i][j+2]=ObjStatus[i][j];
                }
                if(i <= 3 && ObjStatus[i][j]==ObjStatus[i+1][j] && ObjStatus[i+2][j]==ObjStatus[i][j]){
                    mapchecker[i][j]=ObjStatus[i][j];
                    mapchecker[i+1][j]=ObjStatus[i][j];
                    mapchecker[i+2][j]=ObjStatus[i][j];
                }
            }
        }
        //そろっているドロップの数を全属性数える
        for(int del=0;del<5;del++) {
            for (int i = 0; i < vertical_num; i++) {
                for (int j = 0; j < horizontal_num; j++) {
                    if(del == mapchecker[i][j]) {
                        DeleteCount[del] += 1;//数をカウント
                        DeleteSum++;
                        ObjStatus[i][j] = 5;
                    }
                }
            }
            system.out.println(del + ":" + DeleteCount[del]);
        }

        CheckTester(0);
        TestTimer = 0;
        Mode = 3;
    }

    public void DeleteDrop(){
        for (int i = 0; i < vertical_num; i++) {
            for (int j = 0; j < horizontal_num; j++) {
                if(ObjStatus[i][j] == 5) {
                    map.remove(Rid[i][j]);
                    map.put(Rid[i][j],5);
                    ObjStatus[i][j] = 6;
                    DropSet(i, j, map.get(Rid[i][j]));
                    TestTimer = 0;
                    return;
                }
            }
        }
        Mode = 4;
    }

    public void DamageCalc(){
        system.out.println(Mode);
        playerHealPoint = DeleteCount[0] * ((int)(Chara1Status[1] + Chara2Status[1] + Chara3Status[1])/30);
        for(int i=1;i<5;i++) {
            if(Chara1Status[0] == i) CharaAttack[0] += (int)(Chara1Status[2] * DeleteCount[i]) /3;
            if(Chara2Status[0] == i) CharaAttack[1] += (int)(Chara2Status[2] * DeleteCount[i]) /3;
            if(Chara3Status[0] == i) CharaAttack[2] += (int)(Chara3Status[2] * DeleteCount[i]) /3;
        }

        TextView AttackText1 = (TextView) findViewById(R.id.AttackText1);
        AttackText1.setText(CharaAttack[0]+"");
        TextView AttackText2 = (TextView) findViewById(R.id.AttackText2);
        AttackText2.setText(CharaAttack[1]+"");
        TextView AttackText3 = (TextView) findViewById(R.id.AttackText3);
        AttackText3.setText(CharaAttack[2]+"");

        playerAttackPoint = CharaAttack[0] + CharaAttack[1] + CharaAttack[2];

        TextView AText1 = (TextView) findViewById(R.id.EnemyATKPText);
        AText1.setText("EnemyAttack = " +enemyAttackPoint);
        TextView AText2 = (TextView) findViewById(R.id.PlayerATKPText);
        AText2.setText("playerAttack = " +playerAttackPoint);
        TextView AText3 = (TextView) findViewById(R.id.PlayerHealPText);
        AText3.setText("PlayerHeal = " +playerHealPoint);

        for (int i = 0; i < vertical_num; i++) {
            for (int j = 0; j < horizontal_num; j++) {
                if(ObjStatus[i][j] == 6) {
                    Mode = 5;//そろった場所が一か所でも残ってる場合
                    return;
                }
            }
        }
        Mode = 7; //全てのチェックが終了
    }

    private void FillEmpty() {
        system.out.println(Mode);
        CircleSize = 0.02f;
        for (int i = 0; i < vertical_num; i++) {
            for (int j = 0; j < horizontal_num; j++) {
                if(ObjStatus[i][j] == 6) {
                    circle[i][j] = findViewById(Rid[i][j]);
                    circle[i][j].setScaleX(CircleSize);
                    circle[i][j].setScaleY(CircleSize);
                    int num = rand.nextInt(5);//ドロップの属性をここでランダムで決める
                    ObjStatus[i][j] = num;
                    map.remove(Rid[i][j]);
                    map.put(Rid[i][j],num);
                    DropSet(i,j,num);
                }
            }
        }
        TestTimer = 0;
        Mode = 6;
    }

    private void UpdateSize(){
        system.out.println(Mode);
        if(CircleSize < 0.95f) {
            CircleSize += 0.075f;
            for (int i = 0; i < vertical_num; i++) {
                for (int j = 0; j < horizontal_num; j++) {
                    if(mapchecker[i][j] != 9) {
                        circle[i][j].setScaleX(CircleSize);
                        circle[i][j].setScaleY(CircleSize);
                    }
                }
            }
            TestTimer = 0;
            return;
        }else{
            CircleSize = 0.95f;
            Mode = 2; // 全て空白になるまで繰り返す
        }
    }

    private void LastCheck(){
        CurrentHp -= enemyAttackPoint;
        TextView HpText = (TextView) findViewById(R.id.HpText);
        HpText.setText(MaxHp + "/" + CurrentHp + " ");
        for(int i=0;i<3;i++) CharaAttack[i] = 0;
        Mode = 1;
    }

    //ドロップに与えられた属性によって表示する画像を変更する
    public void DropSet(int i, int j, int Num) {
        ImageView Img = findViewById(Rid[i][j]);
        Drawable drawable = getResources().getDrawable(DropDesign[Num]);
        Img.setImageDrawable(drawable);
    }

    //引数0でObjStatus,1でImageViewのId,2でmapChecker
    private void CheckTester(int num){
        for(int i=0;i<vertical_num;i++) {
            system.out.print(i + " : ");
            for (int j = 0; j < horizontal_num; j++) {
                if(num == 0) system.out.print(ObjStatus[i][j]+ " ");
                if(num == 1) system.out.print(Rid[i][j]+ " ");
                if(num == 2){
                    if(mapchecker[i][j] == 9) system.out.print("-");
                    else system.out.print(mapchecker[i][j]);
                }
            }
            system.out.print("\n");
        }
    }
}
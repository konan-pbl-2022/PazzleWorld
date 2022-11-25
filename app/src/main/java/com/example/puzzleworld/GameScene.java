package com.example.puzzleworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GameScene extends AppCompatActivity implements View.OnTouchListener, View.OnDragListener{

    ShareData d;
    private System system;
    private Handler handler = new Handler();

    private Timer timer = new Timer();
    private Timer timer2 = new Timer();

    private GridLayout gridLayout;
    private View mDragView;

    private int vertical_num = 6;//縦のドロップ数
    private int horizontal_num = 6;//横のドロップ数

    Random rand = new Random();//ランダム;

    private int Mode = 0; //"1_Drag&Drop","2_Check&Count","3_NewSet","4_Action"//モード

    int Rid[][] = {{R.id.Cir0,R.id.Cir1,R.id.Cir2,R.id.Cir3,R.id.Cir4,R.id.Cir5},
            {R.id.Cir6,R.id.Cir7,R.id.Cir8,R.id.Cir9,R.id.Cir10,R.id.Cir11},
            {R.id.Cir12,R.id.Cir13,R.id.Cir14,R.id.Cir15,R.id.Cir16,R.id.Cir17},
            {R.id.Cir18,R.id.Cir19,R.id.Cir20,R.id.Cir21,R.id.Cir22,R.id.Cir23},
            {R.id.Cir24,R.id.Cir25,R.id.Cir26,R.id.Cir27,R.id.Cir28,R.id.Cir29},
            {R.id.Cir30,R.id.Cir31,R.id.Cir32,R.id.Cir33,R.id.Cir34,R.id.Cir35}};

    //ドラッグ＆ドロップ後のステータスの入れ替えが難しかったため、Mapを採用
    Map<Integer,Integer> map = new HashMap<>();

    int EnemyDesign[] = {R.drawable.enemy1,R.drawable.enemy2,R.drawable.enemy3,R.drawable.enemy4,
                        R.drawable.enemy5,R.drawable.enemy6};

    int DropDesign[] = {R.drawable.circle,R.drawable.circle5,R.drawable.circle2,
                        R.drawable.circle4,R.drawable.circle3,R.drawable.circle6};
    int DeleteCount[] = {0,0,0,0,0};
    int DeleteSum = 0;

    ImageView circle[][] = new ImageView[vertical_num][horizontal_num];//ドロップの画像
    int ObjStatus[][] = new int[vertical_num][horizontal_num];//ドロップデザインセット
    int mapchecker[][] = new int[vertical_num][horizontal_num];

    float BlackWindowAlpha = 1;
    private int leftId;
    private boolean firstchecked = false;
    static float TestTimer = 0;
    float CircleSize = 0.02f;
    static float realtime;

    boolean dragNow = false;
    float DragTimer = 0;
    float DefaultDragTimer = 0;

    int HpBarSize = 205;
    int EnemyHpBarSize = 225;
    int DragTimerSize = 550;
//岩水火草
    int EnemyStatus[][] = {/*s1*/{1,94,7},{1,159,11},
                           /*s2*/{2,184,13},{3,249,17},
                           /*s3*/{4,360,21},{2,515,29},
                           /*s4*/{3,635,32},{1,726,37},{2,873,46},
                           /*s5*/{1,918,48},{4,944,52},{3,873,55},
                           /*s6*/{2,953,59},{3,999,61},{4,1048,64}};
    /////外部から変数を受け取るエリア
    int Stage = 1;
    int CharaStatus[][] = new int[3][4];//0属性,1攻撃,2体力,3回復
    ///////////////////////
    int MaxPhase[] = {0,2,2,2,3};
    int CurrentPhase = 1;

    int MaxHp,CurrentHp;
    int EnemyDefaultHp,EnemyCurrentHp;
    int NowEnemyType;
    int CharaAttack[] = new int [3];
    int enemyAttackPoint = 0;
    int playerHealPoint = 0;
    int playerAttackPoint = 0;

    int NextMode = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_scene);
        d =(ShareData)getApplication();

        ImageView BlackWindow = findViewById(R.id.BlackWindow);
        gridLayout = findViewById(R.id.GridLayout);

        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            View v = gridLayout.getChildAt(i);
            v.setOnTouchListener((View.OnTouchListener) this);
            v.setOnDragListener((View.OnDragListener) this);
        }

        Button StageSelectButton = (Button) findViewById(R.id.stselebutton);
        StageSelectButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                timer.cancel();
                Intent intent = new Intent(GameScene.this, StageSelectScene.class);
                startActivity(intent);
            }
        });

        DefaultDragTimer = 6;
        DragTimer = DefaultDragTimer;

        Stage = PlayerStatus.SelectStage;

        ImageView CharaImg1 = findViewById(R.id.Chara1);
        Drawable drawable1 = d.mainChara[0].getImage();
        CharaImg1.setImageDrawable(drawable1);
        ImageView CharaImg2 = findViewById(R.id.Chara2);
        Drawable drawable2 = d.mainChara[1].getImage();
        CharaImg2.setImageDrawable(drawable2);
        ImageView CharaImg3 = findViewById(R.id.Chara3);
        Drawable drawable3 = d.mainChara[2].getImage();
        CharaImg3.setImageDrawable(drawable3);

        ResetPlayerUI();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        handler.removeCallbacks(this);
                        //黒画面透明度
                        if(Mode == 8) {
                            BlackWindow.setAlpha(BlackWindowAlpha);
                            BlackWindowAlpha += 0.005;
                        }else{
                            BlackWindow.setAlpha(BlackWindowAlpha);
                            if(BlackWindowAlpha < 0)BlackWindowAlpha = 0;
                            else BlackWindowAlpha -= 0.005;
                        }

                        TestTimer += 0.1;

                        if (Mode == 0) FirstSetting(); //初期設定
                        //Mode == 1 ドラッグ＆ドロップ時間。　終わり次第Mode = 2へ
                        if(Mode == 1) {
                            for(int i=0;i<3;i++) CharaAttack[i] = 0;
                            if(dragNow){
                                //時間を管理するときに使えばイイ！
                                if(realtime != getNowDate()){
                                    DragTimer -= 0.1;
                                    if(TestTimer > 0.7) DragTimerBar();
                                    realtime = getNowDate();
                                }
                            }
                        }
                        if(Mode == 2) CheckAndCount();
                        if(Mode == 3 && TestTimer > 0.3) DeleteDrop();
                        if(Mode == 4) DamageCalc();
                        if(Mode == 5) FillEmpty();//空白を新たなドロップが埋める
                        if(Mode == 6 && TestTimer > 0.5) UpdateSize();//徐々に現れる新ドロップ
                        if(Mode == 7) PlayerAttackTurn();//攻撃、ダメージ処理
                        if(Mode == 8) GameOver();
                        if(Mode == 9) NextEnemySpawn();
                        if(Mode == 10) EnemyAttackTurn();
                        if(Mode == 11) NextMove();//次のターンへの以降

                        if(Mode == 99) SystemWaitTime();//次の
                    }
                });
            }
        }, 0, 7);
    }

    public float getNowDate(){
        SimpleDateFormat df = new SimpleDateFormat("ss.S", Locale.JAPAN);
        String time = df.format(System.currentTimeMillis());
        float num = Float.parseFloat(time);
        system.out.println(num);
        return num;
    }

    @Override
    public void onBackPressed() {} //戻るボタンの無効化

    private void FirstSetting() {
        for(int i=0;i<3;i++){
            CharaStatus[i][0] = d.mainChara[i].getAttribute();//属性
            CharaStatus[i][1] = d.mainChara[i].getAttack();//攻撃
            CharaStatus[i][2] = d.mainChara[i].getHp();//体力
            CharaStatus[i][3] = d.mainChara[i].getHp();//回復
            typeBox(i,CharaStatus[i][0]);
        }
        MaxHp = CharaStatus[0][2] + CharaStatus[1][2] + CharaStatus[2][2];
        CurrentHp = MaxHp;
        TextView HpText = (TextView) findViewById(R.id.HpText);
        HpText.setText(CurrentHp + "/" + MaxHp + " ");

        for(int i=0;i<vertical_num;i++) {
            for(int j=0;j<horizontal_num;j++) {
                circle[i][j] = findViewById(Rid[i][j]);
                circle[i][j].setScaleX(0.95f);
                circle[i][j].setScaleY(0.95f);
                int num = rand.nextInt(5);//ドロップの属性をここでランダムで決める
                ObjStatus[i][j] = num;
                map.put(Rid[i][j],num);
                DropSet(i,j,num);
                SetHPBar("Player");
                EnemyBox();
                SetHPBar("Enemy");
                DragTimerBar();
                ImageView dropCover = findViewById(R.id.dropCover);
                dropCover.setVisibility(View.INVISIBLE);
                typeBox(3,NowEnemyType);
                TextView PhaseText = (TextView) findViewById(R.id.phaseText);
                PhaseText.setText("Phase " + CurrentPhase);
            }
        }
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
            return true;
        }
        else  return true;
    }

    //ドラッグ＆ドロップ操作
    public boolean onDrag(View v, DragEvent event) {
        if(Mode == 1) {
            switch (event.getAction()) {

                // 手を放し、ドラッグが終了した時の処理
                case DragEvent.ACTION_DRAG_ENDED:
                        mDragView.setAlpha(1);
                        firstchecked = false;
                        dragNow = false;
                        Mode = 2;
                    break;
                // ドラッグ中他のViewの上に乗る時、Viewの位置を入れ替える
                case DragEvent.ACTION_DRAG_LOCATION:
                    if(DragTimer < 0){
                        mDragView.setAlpha(1);
                        firstchecked = false;
                        dragNow = false;
                        Mode = 2;
                    }else{
                        if(!dragNow)realtime = getNowDate();
                        dragNow = true;
                        swap(v, mDragView);
                        break;
                    }
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
        float Hipoint = 0.25f;
        float point = 0.20f;
        float Lowpoint = 0.15f;
        //岩水火草
        playerHealPoint = DeleteCount[0] * (int)((CharaStatus[0][1] + CharaStatus[1][1] + CharaStatus[2][1])*0.02);
        DeleteCount[0] = 0;
        if(MaxHp < CurrentHp + playerHealPoint) CurrentHp = MaxHp;
        else CurrentHp += playerHealPoint;
        SetHPBar("Player");
        TextView AText3 = (TextView) findViewById(R.id.PlayerHealPText);
        AText3.setText("+" +playerHealPoint);

        for(int j=0;j<3;j++){
            if(CharaStatus[j][0] == 1) {
                if(NowEnemyType == 2)CharaAttack[j] += (int)(CharaStatus[j][1] * DeleteCount[1] * Hipoint);//有利
                else if(NowEnemyType == 4)CharaAttack[j] += (int)(CharaStatus[j][1] * DeleteCount[1] * Lowpoint);//不利
                else CharaAttack[j] += (int)(CharaStatus[j][1] * DeleteCount[1] * point);
            }
            if(CharaStatus[j][0] == 2) {
                if(NowEnemyType == 3)CharaAttack[j] += (int)(CharaStatus[j][1] * DeleteCount[2] * Hipoint);//有利
                else if(NowEnemyType == 1)CharaAttack[j] += (int)(CharaStatus[j][1] * DeleteCount[2] * Lowpoint);//不利
                else CharaAttack[j] += (int)(CharaStatus[j][1] * DeleteCount[2] * point);
            }
            if(CharaStatus[j][0] == 3) {
                if(NowEnemyType == 4)CharaAttack[j] += (int)(CharaStatus[j][1] * DeleteCount[3] * Hipoint);//有利
                else if(NowEnemyType == 2)CharaAttack[j] += (int)(CharaStatus[j][1] * DeleteCount[3] * Lowpoint);//不利
                else CharaAttack[j] += (int)(CharaStatus[j][1] * DeleteCount[3] * point);
            }
            if(CharaStatus[j][0] == 4) {
                if(NowEnemyType == 1)CharaAttack[j] += (int)(CharaStatus[j][1] * DeleteCount[4] * Hipoint);//有利
                else if(NowEnemyType == 3)CharaAttack[j] += (int)(CharaStatus[j][1] * DeleteCount[4] * Lowpoint);//不利
                else CharaAttack[j] += (int)(CharaStatus[j][1] * DeleteCount[4] * point);
            }
        }
         for(int i=1;i<5;i++) DeleteCount[i] = 0;

        TextView AttackText1 = (TextView) findViewById(R.id.AttackText1);
        TextView AttackText2 = (TextView) findViewById(R.id.AttackText2);
        TextView AttackText3 = (TextView) findViewById(R.id.AttackText3);
        AttackText1.setText(CharaAttack[0]+"");
        AttackText2.setText(CharaAttack[1]+"");
        AttackText3.setText(CharaAttack[2]+"");

        playerAttackPoint = CharaAttack[0] + CharaAttack[1] + CharaAttack[2];

        TextView HpText = (TextView) findViewById(R.id.HpText);
        HpText.setText(CurrentHp+ "/" + MaxHp  + " ");

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

    private void PlayerAttackTurn(){
        ImageView dropCover = findViewById(R.id.dropCover);
        dropCover.setVisibility(View.VISIBLE);
        ImageView DamageEffect2 = findViewById(R.id.DamageEffect2);
        DamageEffect2.setVisibility(View.VISIBLE);

        TextView HealText = (TextView) findViewById(R.id.PlayerHealPText);
        HealText.setText("");
        EnemyCurrentHp -= playerAttackPoint;
        SetHPBar("Enemy");
        TextView PlayerATKText = (TextView) findViewById(R.id.PlayerPText);
        PlayerATKText.setText("" + playerAttackPoint);


        //2秒待ち専用モード
        if (EnemyCurrentHp <= 0) {//敵死亡
            ImageView EnemyImg = findViewById(R.id.EnemyImg);
            Drawable drawable = getResources().getDrawable(R.drawable.circle6);
            EnemyImg.setImageDrawable(drawable);
            TestTimer = 0;
            NextMode = 9;
        } else {
            TestTimer = 0;
            NextMode = 10;
        }
        Mode = 99;//2秒待ち専用モード
    }

    private void EnemyAttackTurn(){
        ResetPlayerUI();
        ImageView DamageEffect1 = findViewById(R.id.DamageEffect1);
        ImageView DamageEffect2 = findViewById(R.id.DamageEffect2);
        DamageEffect1.setVisibility(View.VISIBLE);
        DamageEffect2.setVisibility(View.INVISIBLE);

        system.out.println(EnemyDefaultHp +" / " + EnemyCurrentHp);
        CurrentHp -= enemyAttackPoint;
        SetHPBar("Player");
        TextView HpText = (TextView) findViewById(R.id.HpText);
        HpText.setText(CurrentHp + "/" + MaxHp  + " ");
        TextView EnAttackText = (TextView) findViewById(R.id.EnemyATKPText);
        EnAttackText.setText("" +enemyAttackPoint);

        if(CurrentHp <= 0) Mode = 8;//GameOver
        else {
            TestTimer = 0;
            NextMode = 11;
            Mode = 99;//2秒待ち専用モード
        }
    }

    private void GameOver(){
        PlayerStatus.GameClear = false;
        PlayerStatus.LastPhase = CurrentPhase-1;
        if(BlackWindowAlpha > 0.99){
            timer.cancel();
            Intent intent = new Intent(GameScene.this, ResultScene.class);
            startActivity(intent);
            Mode = -1;
        }
    }

    private void NextEnemySpawn(){

        ResetPlayerUI();
        ImageView DamageEffect2 = findViewById(R.id.DamageEffect2);
        DamageEffect2.setVisibility(View.INVISIBLE);
        TextView EnAttackText = (TextView) findViewById(R.id.EnemyATKPText);
        EnAttackText.setText("");
        PlayerStatus.LastPhase = CurrentPhase;
        if(MaxPhase[Stage] == CurrentPhase) {
            timer.cancel();
            PlayerStatus.GameClear = true;
            Intent intent = new Intent(GameScene.this, ResultScene.class);
            startActivity(intent);
        }
        else {
            CurrentPhase += 1;
            TextView PlayerATKText = (TextView) findViewById(R.id.PlayerPText);
            PlayerATKText.setText("Phase "+ CurrentPhase);
            TextView PhaseText = (TextView) findViewById(R.id.phaseText);
            PhaseText.setText("Phase " + CurrentPhase);
            EnemyBox();//次の敵のステータス
            typeBox(3,NowEnemyType);
        }
        TestTimer = 0;
        Mode = 11;
    }

    private void NextMove(){
        if(TestTimer > 30){
            TextView EnAttackText = (TextView) findViewById(R.id.EnemyATKPText);
            EnAttackText.setText("");
            ResetPlayerUI();
            SetHPBar("Enemy");
            DragTimer = 6;
            DragTimerBar();
            ImageView dropCover = findViewById(R.id.dropCover);
            dropCover.setVisibility(View.INVISIBLE);
            ImageView DamageEffect1 = findViewById(R.id.DamageEffect1);
            DamageEffect1.setVisibility(View.INVISIBLE);
            Mode = 1;
        }
    }

    //ドロップに与えられた属性によって表示する画像を変更する
    public void DropSet(int i, int j, int Num) {
        ImageView Img = findViewById(Rid[i][j]);
        Drawable drawable = getResources().getDrawable(DropDesign[Num]);
        Img.setImageDrawable(drawable);
    }

    //次のモード移行を待っている間
    private void SystemWaitTime(){
        if(TestTimer > 20) Mode = NextMode;
    }

    private void ResetPlayerUI(){
        playerHealPoint = 0;
        playerAttackPoint = 0;
        ImageView DamageEffect1 = findViewById(R.id.DamageEffect1);
        DamageEffect1.setVisibility(View.INVISIBLE);
        ImageView DamageEffect2 = findViewById(R.id.DamageEffect2);
        DamageEffect2.setVisibility(View.INVISIBLE);
        TextView ATKText1 = (TextView) findViewById(R.id.AttackText1);
        ATKText1.setText("");
        TextView ATKText2 = (TextView) findViewById(R.id.AttackText2);
        ATKText2.setText("");
        TextView ATKText3 = (TextView) findViewById(R.id.AttackText3);
        ATKText3.setText("");
        TextView HealText = (TextView) findViewById(R.id.PlayerHealPText);
        HealText.setText("");
        TextView PlayerATKText = (TextView) findViewById(R.id.PlayerPText);
        PlayerATKText.setText("");
        TextView EnAttackText = (TextView) findViewById(R.id.EnemyATKPText);
        EnAttackText.setText("");
    }

    /*
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
    }*/

    private void SetHPBar(String name){
        ImageView Imgw;
        Drawable drawable;
        if(name == "Player") Imgw = findViewById(R.id.HpBar);
        else Imgw = findViewById(R.id.EnemyHpBar);

        Drawable original = this.getResources().getDrawable(R.drawable.hp_bar);
        Bitmap bitmap = ((BitmapDrawable) original).getBitmap();
        if(name == "Player"){
            HpBarSize = 205 * CurrentHp / MaxHp; //DefaultSize : MaxHp = NewSize : CurrentHpから求める
            if(HpBarSize > 205) HpBarSize = 205;
            if(HpBarSize < 5) HpBarSize = 5;
            drawable = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, HpBarSize, 6, true));
        }else{
            EnemyHpBarSize = 225 * EnemyCurrentHp / EnemyDefaultHp; //DefaultSize : MaxHp = NewSize : CurrentHpから求める
            if(EnemyHpBarSize > 225) EnemyHpBarSize = 225;
            if(EnemyHpBarSize < 5) EnemyHpBarSize = 5;
            drawable = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, EnemyHpBarSize, 6, true));
        }
        Imgw.setImageDrawable(drawable);
    }

    private void DragTimerBar(){
        ImageView Imgw3 = findViewById(R.id.TimerBar);
        Drawable original3 = this.getResources().getDrawable(R.drawable.timer_bar);
        Bitmap bitmap3 = ((BitmapDrawable) original3).getBitmap();
        DragTimerSize = (int)(550 * DragTimer / DefaultDragTimer); //DefaultSize : MaxHp = NewSize : CurrentHpから求める
        if(DragTimerSize > 550) DragTimerSize = 550;
        if(DragTimerSize < 5) DragTimerSize = 5;
        Drawable drawableA = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap3, DragTimerSize, 6, true));
        Imgw3.setImageDrawable(drawableA);
        TestTimer = 0;
    }

    private void EnemyBox(){
        ImageView EnemyImg = findViewById(R.id.EnemyImg);
        Drawable drawable = null;
        int EnemyNum=0;
        if(Stage == 1){
            if(CurrentPhase == 1){
                EnemyNum = 0;
                drawable = getResources().getDrawable(R.drawable.enemy3);
            }
            if(CurrentPhase == 2){
                EnemyNum = 1;
                drawable = getResources().getDrawable(R.drawable.enemy1);
            }
        }
        if(Stage == 2){
            if(CurrentPhase == 1){
                EnemyNum = 2;
                drawable = getResources().getDrawable(R.drawable.enemy6);
            }
            if(CurrentPhase == 2){
                EnemyNum = 3;
                drawable = getResources().getDrawable(R.drawable.enemy2);
            }
        }
        if(Stage == 3){
            if(CurrentPhase == 1){
                EnemyNum = 4;
                drawable = getResources().getDrawable(R.drawable.enemy5);
            }
            if(CurrentPhase == 2){
                EnemyNum = 5;
                drawable = getResources().getDrawable(R.drawable.enemy4);
            }
        }
        if(Stage == 4){
            if(CurrentPhase == 1){
                EnemyNum = 6;
                drawable = getResources().getDrawable(R.drawable.enemy2);
            }
            if(CurrentPhase == 2){
                EnemyNum = 7;
                drawable = getResources().getDrawable(R.drawable.enemy1);
            }
            if(CurrentPhase == 3){
                EnemyNum = 8;
                drawable = getResources().getDrawable(R.drawable.enemy4);
            }
        }
        if(Stage == 5){
            if(CurrentPhase == 1){
                EnemyNum = 9;
                drawable = getResources().getDrawable(R.drawable.enemy1);
            }
            if(CurrentPhase == 2){
                EnemyNum = 10;
                drawable = getResources().getDrawable(R.drawable.enemy5);
            }
            if(CurrentPhase == 3){
                EnemyNum = 11;
                drawable = getResources().getDrawable(R.drawable.enemy7);
            }
        }
        if(Stage == 6){
            if(CurrentPhase == 1){
                EnemyNum = 12;
                drawable = getResources().getDrawable(R.drawable.enemy4);
            }
            if(CurrentPhase == 2){
                EnemyNum = 13;
                drawable = getResources().getDrawable(R.drawable.enemy8);
            }
            if(CurrentPhase == 3){
                EnemyNum = 14;
                drawable = getResources().getDrawable(R.drawable.enemy2);
            }
        }
        EnemyImg.setImageDrawable(drawable);
        NowEnemyType = EnemyStatus[EnemyNum][0];
        EnemyDefaultHp = EnemyStatus[EnemyNum][1];
        enemyAttackPoint = EnemyStatus[EnemyNum][2];
        EnemyCurrentHp = EnemyDefaultHp;
    }

    private void typeBox(int i,int Num){

        if(i==0) {
            ImageView TypeImg1 = findViewById(R.id.type1);
            Drawable typeDrawable1 = getResources().getDrawable(DropDesign[Num]);
            TypeImg1.setImageDrawable(typeDrawable1);
        }
        if(i==1){
            ImageView TypeImg2 = findViewById(R.id.type2);
            Drawable typeDrawable2 = getResources().getDrawable(DropDesign[Num]);
            TypeImg2.setImageDrawable(typeDrawable2);
        }
        if(i==2){
            ImageView TypeImg3 = findViewById(R.id.type3);
            Drawable typeDrawable3 = getResources().getDrawable(DropDesign[Num]);
            TypeImg3.setImageDrawable(typeDrawable3);
        }
        if(i==3) {
            ImageView TypeImg4 = findViewById(R.id.EnemyType);
            Drawable typeDrawable4 = getResources().getDrawable(DropDesign[Num]);
            TypeImg4.setImageDrawable(typeDrawable4);
        }


    }
}
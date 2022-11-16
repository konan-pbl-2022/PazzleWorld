package com.example.puzzleworld;

import static java.sql.Types.NULL;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;

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

    //画像のID
    int Rid[][] = {{R.id.Cir0,R.id.Cir1,R.id.Cir2,R.id.Cir3,R.id.Cir4,R.id.Cir5},
            {R.id.Cir6,R.id.Cir7,R.id.Cir8,R.id.Cir9,R.id.Cir10,R.id.Cir11},
            {R.id.Cir12,R.id.Cir13,R.id.Cir14,R.id.Cir15,R.id.Cir16,R.id.Cir17},
            {R.id.Cir18,R.id.Cir19,R.id.Cir20,R.id.Cir21,R.id.Cir22,R.id.Cir23},
            {R.id.Cir24,R.id.Cir25,R.id.Cir26,R.id.Cir27,R.id.Cir28,R.id.Cir29},
            {R.id.Cir30,R.id.Cir31,R.id.Cir32,R.id.Cir33,R.id.Cir34,R.id.Cir35}};

    //ドラッグ＆ドロップ後のステータスの入れ替えが難しかったため、Mapを採用
    Map<Integer,Integer> map = new HashMap<>();

    int DropDesign[] = {R.drawable.circle,R.drawable.circle2,R.drawable.circle3,
            R.drawable.circle4,R.drawable.circle5};

    ImageView circle[][] = new ImageView[vertical_num][horizontal_num];//ドロップの画像
    int ObjStatus[][] = new int[vertical_num][horizontal_num];//ドロップデザインセット
    int mapchecker[][] = new int[vertical_num][horizontal_num];

    char MovePos[][] = new char[vertical_num][horizontal_num];

    private int leftId;
    private int leftleftId;
    private int leftleftId2;
    private boolean firstchecked = false;
    private int L_count = 0;

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


        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        if(Mode == 0)//初期設定
                        {
                            for(int i=0;i<vertical_num;i++) {
                                for(int j=0;j<horizontal_num;j++) {
                                    circle[i][j] = findViewById(Rid[i][j]);
                                    circle[i][j].setScaleX(0.90f);
                                    circle[i][j].setScaleY(0.90f);
                                    int num = rand.nextInt(5);//ドロップの属性をここでランダムで決める
                                    ObjStatus[i][j] = num;
                                    DropSet(i,j,num);
                                }
                            }
                            CheckTester(0);
                            Mode = 1;
                        }
                        if(Mode == 2) CheckAndCount();
                    }
                });
            }
        }, 0, 60);

    }

    // 押した時の動作
    public boolean onTouch(View v, MotionEvent motionEvent) {
        if(Mode == 1){
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                mDragView = v;

                // Viewをドラッグ状態にする。
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    v.startDragAndDrop(null, new View.DragShadowBuilder(v), v, 0);
                }
                v.setAlpha(0);
            }

        }
        return true;
    }

    public boolean onDrag(View v, DragEvent event) {
        if(Mode == 1) {
            if(!firstchecked)
            {
                leftleftId = mDragView.getId();
                leftId = mDragView.getId();
                firstchecked = true;
            }
            switch (event.getAction()) {
                // 手を放し、ドラッグが終了した時の処理
                // ドラッグしているViewを表示させる。

                case DragEvent.ACTION_DRAG_ENDED:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        getMainExecutor().execute(() -> mDragView.setAlpha(1));

                        for(int i=0;i<vertical_num;i++) {
                            for (int j = 0; j < horizontal_num; j++) {

                                if (leftId == Rid[i][j]) {
                                    ObjStatus[i][j] = map.get(mDragView.getId());
                                }
                            }
                        }


                        firstchecked = false;
                        Mode = 2;
                    }
                    break;
                // ドラッグ中他のViewの上に乗る時の処理
                // Viewの位置を入れ替えるx
                case DragEvent.ACTION_DRAG_LOCATION:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {

                        getMainExecutor().execute(() -> swap(v, mDragView));

                    }
                    break;
            }
        }
        return true;
    }

    private void swap(View v1, View v2) {

        int v1Num = 0, leftNum = 0,leftNum2 = 0,leftNum3 = 0;
        // 同じViewなら入れ替える必要なし
        if (v1 == v2) return;

        // レイアウトパラメータを抜き出して、入れ替えを行う
        GridLayout.LayoutParams p1, p2;
        p1 = (GridLayout.LayoutParams) v1.getLayoutParams();
        p2 = (GridLayout.LayoutParams) v2.getLayoutParams();
        gridLayout.removeView(v1);
        gridLayout.removeView(v2);
        gridLayout.addView(v1, p2);
        gridLayout.addView(v2, p1);

        for (Integer key : map.keySet()) {
            if (leftId == key) leftNum = map.get(key);
            if (leftleftId == key) leftNum2 = map.get(key);
            if (leftleftId2 == key) leftNum3 = map.get(key);

            if (v1.getId() == key) v1Num = map.get(key);
        }

            for (int i = 0; i < vertical_num; i++) {
                for (int j = 0; j < horizontal_num; j++) {

                    if (v1.getId() == Rid[i][j]) {
                        ObjStatus[i][j] = leftNum;
                    }
                    if (leftId == Rid[i][j]) {
                        ObjStatus[i][j] = v1Num;
                    }
                }
            }


            leftId = v1.getId();
            if(L_count%2!=0) {
                leftleftId = leftId;//back用　2回に1回
            }else{
                leftleftId2 = leftId;
            }
        L_count+=1;
    }





    private void CheckAndCount() {

       /* for(int i=0;i<vertical_num;i++) {
            for (int j = 0; j < horizontal_num; j++) {
                for (Integer key : map.keySet())
                {
                    if(Rid[i][j] == key)
                    {
//                        system.out.println(key + " " + ObjStatus[i][j]);
                        ObjStatus[i][j] = map.get(key);
                        //最後更新
//                        system.out.println(key + " o  " + ObjStatus[i][j]);
                        break;
                    }

                }
            }
        }*/

        for(int i=0;i<vertical_num;i++) {
            for (int j = 0; j < horizontal_num; j++) {
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

        CheckTester(0);

        for(int i=0;i<vertical_num;i++) {
            system.out.print(i + " : ");
            for (int j = 0; j < horizontal_num; j++) {
                if(mapchecker[i][j] == 9) system.out.print("-");
                else system.out.print(mapchecker[i][j]);
            }
            system.out.print("\n");
        }


        Mode = 3;
    }

    //ドロップに与えられた属性によって表示する画像を変更する
    private void DropSet(int i, int j, int Num) {

        map.put(Rid[i][j],Num);
        ImageView Img = findViewById(Rid[i][j]);
        Drawable drawable = getResources().getDrawable(DropDesign[Num]);
        Img.setImageDrawable(drawable);
    }

    private void CheckTester(int num)
    {
        for(int i=0;i<vertical_num;i++) {
            system.out.print(i + " : ");
            for (int j = 0; j < horizontal_num; j++) {
                if(num == 0) system.out.print(ObjStatus[i][j]+ " ");
                if(num == 1) system.out.print(Rid[i][j]+ " ");
            }
            system.out.print("\n");
        }
    }
}
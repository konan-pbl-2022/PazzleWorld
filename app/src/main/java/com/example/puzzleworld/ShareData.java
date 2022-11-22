package com.example.puzzleworld;

import android.annotation.SuppressLint;
import android.app.Application;

public class ShareData extends Application {
    public int isi = 0;
    public int charaNum = 16;
    public int mainCharaNum = 3;
    public  Character[] chara = new Character[charaNum];
    public Character[] mainChara = new Character[mainCharaNum];

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onCreate() {
        super.onCreate();

        for(int i = 0; i < charaNum; i++){
            chara[i] = new Character();
        }
        for(int i = 0; i < mainCharaNum; i++){
            mainChara[i] = new Character();
        }

        chara[0].setStatus(1,1,1,1,1,0,true);
        chara[1].setStatus(1,2,2,2,2,0,true);
        chara[2].setStatus(1,3,3,3, 3,0,true);
        chara[3].setStatus(1,4,4,4, 4,0,true);
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

        for (int i = 0; i < mainCharaNum; i++) {
            mainChara[i] = chara[i];
        }
    }

}

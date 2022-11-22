package com.example.puzzleworld;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;

import androidx.appcompat.app.AppCompatActivity;


public class Character extends AppCompatActivity {
    //  ステータス
    int attribute;
    int attack;
    int hp;
    int recovery;
    int level;
    //  所持数
    int number;
    //  所持
    boolean possession;
    //　画像
    Drawable image;

    //　コンストラクタ
    public void setStatus(int atb, int a, int h, int r, int l, int n, boolean b) {
        attribute = atb;
        attack = a;
        hp = h;
        recovery = r;
        level = l;
        number = n;
        possession = b;
    }
    // セッター
    void setPossession(boolean b) {
        possession = b;
    }
    @SuppressLint("UseCompatLoadingForDrawables")
    //画像のセッターの方法不明
//    void setImage(String s){
//        int imageId = getResources().getIdentifier(s, "drawable", getPackageName());
//        image = getResources().getDrawable(imageId);
//    }

    //　ゲッター
    int getAttribute() {
        return attribute;
    }
    int getAttack() {
        return attack;
    }
    int getHp() {
        return hp;
    }
    int getRecovery() {
        return recovery;
    }
    int getLevel() {
        return level;
    }
    int getNumber() {
        return number;
    }
    boolean getPossession() {
        return possession;
    }
    Drawable getImage() {
        return image;
    }

    //　add
    int addAttack(int n) {
        return attack + n;
    }
    int addHp(int n) {
        return hp + n;
    }
    int addRecovery(int n) {
        return recovery + n;
    }
    int addLevel(int n) {
        return level + n;
    }
    int addNumber(int n) {
        return number + n;
    }
}




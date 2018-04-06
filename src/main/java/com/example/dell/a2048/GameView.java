package com.example.dell.a2048;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import android.support.v7.app.AlertDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class GameView extends GridLayout {
    private Card[][] cardmap=new Card[4][4];
    private List<Point> emptyPoints=new ArrayList<Point>();
    public GameView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        //初始化游戏
        InitGameView();
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        InitGameView();
    }

    public GameView(Context context) {
        super(context);
        InitGameView();
    }
    private void InitGameView(){
        //设置为4x4个方格
        setColumnCount(4);
        //设置背景颜色
        setBackgroundColor(0xffeee4da);

        //判定滑动方向
        setOnTouchListener(new OnTouchListener() {
            private float startx,starty,offsetx,offsety;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        startx=event.getX();
                        starty=event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        offsetx=event.getX()-startx;
                        offsety=event.getY()-starty;
                        if(Math.abs(offsetx)>Math.abs(offsety)){
                            if(offsetx<-5){
                                swipeLeft();
                            }else if(offsetx>5){
                                swipeRight();
                            }
                        }else{
                            if(offsety<-5){
                                swipeUp();
                            }else if(offsetx>3){
                                swipeDown();
                            }
                        }
                        break;
                }
                return true;
            }
        });
    }
    //适应不同大小的屏幕
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int cardWidth=(Math.min(h, w))/4;
        addCards(cardWidth,cardWidth);
        startGame();
    }
    //在4x4的方格上添加满卡片
    public void addCards(int cardwidth,int cardheight){
        Card c;
        for (int a = 0; a < 4; a++) {
            for (int b = 0; b < 4; b++) {
                c=new Card(getContext());
                c.setNum(0);
                addView(c, cardwidth, cardheight);
                cardmap[b][a]=c;
            }
        }
    }
    //游戏开始时每个卡片默认值设为0，并随机添加两张带数字的卡片
    private void startGame(){
        MainActivity.getMainActivity().clearScore();
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                cardmap[x][y].setNum(0);
            }
        }
        addRandomNum();
        addRandomNum();
    }

    private void addRandomNum() {
        emptyPoints.clear();
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if(cardmap[x][y].getNum()<=0){
                    emptyPoints.add(new Point(x,y));
                }
            }
        }
        Point p=emptyPoints.remove((int)(Math.random()*emptyPoints.size()));
        //2和4出现的概率控制在1：9
        cardmap[p.x][p.y].setNum(Math.random()>0.1?2:4);

//        Random random;
//        int next = random.nextInt(4);
//        Card [][] cards = new Card[next][next];
//        while (cards[next][next].getNum() != 0)
//        {
//            next = random.nextInt(4);
//           this.cards =  Card[next][next];
//        }
//        cards[next][next].setNum(Math.random() > 0.8 ? 4 : 2);
    }

    private void swipeLeft(){
        boolean merge = false;
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                for (int x1 = x+1; x1 <4; x1++) {
                    if(cardmap[x1][y].getNum()>0){
                        if(cardmap[x][y].getNum()<=0){
                            cardmap[x][y].setNum(cardmap[x1][y].getNum());
                            cardmap[x1][y].setNum(0);
                            merge=true;
                            x--;
                        }else if(cardmap[x][y].equal(cardmap[x1][y])){
                            cardmap[x][y].setNum(cardmap[x][y].getNum()*2);
                            cardmap[x1][y].setNum(0);
                            MainActivity.getMainActivity().addScore(cardmap[x][y].getNum());
                            merge=true;
                        }
                        break;
                    }
                }
            }
        }
        if(merge){
            addRandomNum();
            checkComplete();
        }
    }
    private void swipeDown(){

        boolean merge = false;

        for (int x = 0; x < 4; x++) {
            for (int y = 3; y >=0; y--) {

                for (int y1 = y-1; y1 >=0; y1--) {
                    if (cardmap[x][y1].getNum()>0) {

                        if (cardmap[x][y].getNum()<=0) {
                            cardmap[x][y].setNum(cardmap[x][y1].getNum());
                            cardmap[x][y1].setNum(0);

                            y++;
                            merge = true;
                        }else if (cardmap[x][y].equal(cardmap[x][y1])) {
                            cardmap[x][y].setNum(cardmap[x][y].getNum()*2);
                            cardmap[x][y1].setNum(0);
                            MainActivity.getMainActivity().addScore(cardmap[x][y].getNum());
                            merge = true;
                        }

                        break;
                    }
                }
            }
        }

        if (merge) {
            addRandomNum();
            checkComplete();
        }
    }
    private void swipeUp(){

        boolean merge = false;

        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {

                for (int y1 = y+1; y1 < 4; y1++) {
                    if (cardmap[x][y1].getNum()>0) {

                        if (cardmap[x][y].getNum()<=0) {
                            cardmap[x][y].setNum(cardmap[x][y1].getNum());
                            cardmap[x][y1].setNum(0);

                            y--;

                            merge = true;
                        }else if (cardmap[x][y].equal(cardmap[x][y1])) {
                            cardmap[x][y].setNum(cardmap[x][y].getNum()*2);
                            cardmap[x][y1].setNum(0);
                            MainActivity.getMainActivity().addScore(cardmap[x][y].getNum());
                            merge = true;
                        }

                        break;

                    }
                }
            }
        }

        if (merge) {
            addRandomNum();
            checkComplete();
        }
    }
    private void swipeRight(){
        boolean merge = false;
        for (int y = 0; y < 4; y++) {
            for (int x = 3; x >=0; x--) {
                for (int x1 = x-1; x1 >=0; x1--) {
                    if(cardmap[x1][y].getNum()>0){
                        if(cardmap[x][y].getNum()<=0){
                            cardmap[x][y].setNum(cardmap[x1][y].getNum());
                            cardmap[x1][y].setNum(0);
                            x++;
                            merge=true;
                        }else if(cardmap[x][y].equal(cardmap[x1][y])){
                            cardmap[x][y].setNum(cardmap[x][y].getNum()*2);
                            cardmap[x1][y].setNum(0);
                            MainActivity.getMainActivity().addScore(cardmap[x][y].getNum());
                            merge=true;
                        }
                        break;
                    }
                }
            }
        }
        if(merge){
            addRandomNum();
            checkComplete();
        }
    }
    //如果有空卡片或者相邻的值相同卡片则游戏还能进行
    public void checkComplete(){
        boolean complete=true;
        ALL:
        for (int y = 0; y <4; y++) {
            for (int x = 0; x <4; x++) {
                if(cardmap[x][y].getNum()==0||
                        x>0&&cardmap[x][y].equal(cardmap[x-1][y])||
                        x<3&&cardmap[x][y].equal(cardmap[x+1][y])||
                        y>0&&cardmap[x][y].equal(cardmap[x][y-1])||
                        y<3&&cardmap[x][y].equal(cardmap[x][y+1])){
                    complete=false;
                    break ALL;
                }
            }
        }
        //游戏结束弹出alert提示窗口
        if(complete){
            new AlertDialog.Builder(getContext()).setTitle("").setMessage("游戏结束").setPositiveButton("重来",new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    startGame();
                }
            }).show();
        }

    }

}

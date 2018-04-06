package com.example.dell.a2048;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

public class Card extends FrameLayout {
    private TextView label;
    private int num=0;

    public Card(Context context) {
        super(context);
        LayoutParams lp = null;

        View background = new View(getContext());
        //参数-1表示layoutparams填充满整个父容器
        lp = new LayoutParams(-1, -1);
        //设置卡片之间有10像素的间隔
        lp.setMargins(10, 10, 0, 0);
        background.setBackgroundColor(0x33ffffff);
        addView(background, lp);

        label = new TextView(getContext());
        label.setTextSize(40);
        label.setGravity(Gravity.CENTER);
        addView(label, lp);
        setNum(0);
    }

    public int getNum(){
        return num;
    }

    public void setNum(int num){
        this.num=num;
        if(num<=0){
            label.setText("");
        }else{
            label.setText(num+"");
        }
        switch (num) {
            case 0:
                label.setBackgroundColor(0x00000000);
                break;
            case 2:
                label.setBackgroundColor(0xffeee4da);
                break;
            case 4:
                label.setBackgroundColor(0xffede0c8);
                break;
            case 8:
                label.setBackgroundColor(0xfff2b179);
                break;
            case 16:
                label.setBackgroundColor(0xfff59563);
                break;
            case 32:
                label.setBackgroundColor(0xfff67c5f);
                break;
            case 64:
                label.setBackgroundColor(0xfff65e3b);
                break;
            case 128:
                label.setBackgroundColor(0xffedcf72);
                break;
            case 256:
                label.setBackgroundColor(0xffedcc61);
                break;
            case 512:
                label.setBackgroundColor(0xffedc850);
                break;
            case 1024:
                label.setBackgroundColor(0xffedc53f);
                break;
            case 2048:
                label.setBackgroundColor(0xffedc22e);
                break;
            default:
                label.setBackgroundColor(0xff3c3a32);
                break;
        }
    }

    public boolean equal(Card card){
        return getNum()==card.getNum();
    }

}

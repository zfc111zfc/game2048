package com.example.dell.a2048;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tvscore;
    private int score=0;
    public MainActivity(){
        mainActivity=this;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvscore = (TextView) findViewById(R.id.tvScore);
    }
    public void clearScore(){
        score=0;
        showScore();
    }
    public void showScore(){
        tvscore.setText(score+"");
    }
    public void addScore(int s){
        score+=s;
        showScore();
    }

    public static MainActivity mainActivity=null;
    public static MainActivity getMainActivity() {
        return mainActivity;
    }
}

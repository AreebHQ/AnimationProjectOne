package com.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Locale;

public class GameTwo extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 9000;
    MyStrtDrggngLstnr mStrtDrg;
    MyEndDrgLstnr mEndDrg;
    ImageView packImage;
    Button btn1;
    ArrayList<String> moves = new ArrayList<>();
    ArrayList <ObjectAnimator> move = new ArrayList<>();

    int GameCount = 0;
    int correct = 0;
    int wrong = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_two);
        mStrtDrg=new MyStrtDrggngLstnr();
        mEndDrg=new MyEndDrgLstnr();
        SharedPreferences gameData = getSharedPreferences("gameData", Context.MODE_PRIVATE);
        findViewById(R.id.btn_left).setOnLongClickListener(mStrtDrg);
        findViewById(R.id.btn_dwn).setOnLongClickListener(mStrtDrg);
        findViewById(R.id.btn_right).setOnLongClickListener(mStrtDrg);
        findViewById(R.id.btn_up).setOnLongClickListener(mStrtDrg);

        findViewById(R.id.btn_input1).setOnDragListener(mEndDrg);
        findViewById(R.id.btn_input2).setOnDragListener(mEndDrg);
        findViewById(R.id.btn_input3).setOnDragListener(mEndDrg);
        findViewById(R.id.btn_input4).setOnDragListener(mEndDrg);
        findViewById(R.id.btn_input5).setOnDragListener(mEndDrg);
        findViewById(R.id.btn_input6).setOnDragListener(mEndDrg);
        btn1 = findViewById(R.id.btn1);

        packImage= (ImageView) findViewById(R.id.pack);

        // getting game number of games played
        GameCount = gameData.getInt("gameTwoCount",0);

        //if 3 games have been played - set it to 0 before start
        if (GameCount == 3)
        {
            GameCount = 0;
        }

        ObjectAnimator animator = ObjectAnimator.ofFloat(packImage, "rotation", 0, 360);
        animator.setDuration(500);
        animator.start();

    }

    public void checkMoves(View view) {

        GameCount++;
        SharedPreferences gameData = getSharedPreferences("gameData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = gameData.edit();
        editor.putInt("gameTwoCount", GameCount);
        editor.commit();

        // Setting required movement for the animation
        ObjectAnimator scalePackman  = ObjectAnimator.ofFloat(packImage, "scaleX", 1.2f);
        scalePackman.setDuration(700);
        //to move right
        ObjectAnimator moveRight  = ObjectAnimator.ofFloat(packImage, "translationX", 450);
        moveRight.setDuration(1500);
        //to move right secind time
        ObjectAnimator moveRight2  = ObjectAnimator.ofFloat(packImage, "translationX", 900);
        moveRight2.setDuration(1500);
        // to move down
        ObjectAnimator moveDown =  ObjectAnimator.ofFloat(packImage, "translationY", 230);
        moveDown.setDuration(1500);
        // to move down2
        ObjectAnimator moveDown2 =  ObjectAnimator.ofFloat(packImage, "translationY", 500);
        moveDown2.setDuration(1500);
        // to move down2
        ObjectAnimator moveDown3 =  ObjectAnimator.ofFloat(packImage, "translationY", 800);
        moveDown3.setDuration(1500);
        // to move left
        ObjectAnimator moveleft =  ObjectAnimator.ofFloat(packImage, "translationX", 190);
        moveleft.setDuration(1500);
        // hide when finished
        ObjectAnimator hidePackman  = ObjectAnimator.ofFloat(packImage, "alpha", 1.0f, 0);
        hidePackman.setDuration(300);

        String solution[] = {"down","right","down","left","down","right"};

        // setting animation object
        AnimatorSet animSet = new AnimatorSet();
        // setting check for right moves since there are two right moves
        boolean firstMove = true;
        int down = 0;

        boolean win = false;
        // getting moves from moves list to ObjectAnimator list
        for (int i=0; i<moves.size();i++) {
            if  (!moves.get(i).equals(solution[i]))
            {
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setMessage( moves.get(i).toUpperCase(Locale.ROOT) +" is wrong move! GAME OVER! \n Click Ok to move to next Game.");
                int finalGameCount = GameCount;
                alert.setPositiveButton("Ok", (v, a) -> {
                    if (finalGameCount == 3) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class)); }
                    else {
                        startActivity(new Intent(getApplicationContext(), GameTwo.class));
                    }
                });
                alert.create().show();
                break;
            }
            // show dialog box for game over if move is UP
            if (moves.get(i).equals("up")) {
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setMessage("UP is wrong move! GAME OVER! \n Click Ok to move to next Game.");
                int finalGameCount = GameCount;
                alert.setPositiveButton("Ok", (v, a) -> {
                    if (finalGameCount == 3) {
                        startActivity(new Intent(getApplicationContext(), MainActivity.class)); }
                    else {
                        startActivity(new Intent(getApplicationContext(), GameTwo.class));
                    }
                });
                alert.create().show();
                break;

            }

            // add move to move array list
            if (moves.get(i).equals("down")) {
               if (down == 0){
                move.add(moveDown);
                }
               else if (down == 1)
               {
                   move.add(moveDown2);
               } else {
                   move.add(moveDown3);
               }
                down++;
            }
            // add move to move array list
            if (moves.get(i).equals("right")) {
                if (firstMove) {
                    move.add(moveRight);
                    firstMove = false;
                } else {
                    move.add(moveRight2);
                }
            }
            // show dialog box for game over if move is left
            if (moves.get(i).equals("left")) {
                move.add(moveleft);
            }
        }

        // Playing animation reading the moves for move array
        for(int i=0;i<move.size();i++)
        {
            if (i ==0 )
            {
                animSet.play(move.get(i));
                animSet.start();
                continue;
            }
            animSet.play(move.get(i)).after(move.get(i-1));
            animSet.start();
            win = true;
        }

        if (moves.size() == 6){
            correct++;
            editor.putInt("gameTwoCorrect", correct);
            editor.commit();
            // setting alert box to move to next activity and waiting time to finish the animation
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    alert.setMessage("Congratulations YOU WIN!!! \n Click Ok to move to next Game.");
                    int finalGameCount = GameCount;
                    alert.setPositiveButton("Ok", (v, a) -> {
                        if (finalGameCount == 3) {
                            startActivity(new Intent(getApplicationContext(), MainActivity.class)); }
                        else {
                            startActivity(new Intent(getApplicationContext(), GameTwo.class));
                        }
                        finish();
                    });
                    alert.create().show();
                }
            }, SPLASH_TIME_OUT); }
        else {
            wrong++;
            editor.putInt("gameTwoWrong", wrong);
            editor.commit();
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage("Incomplete Instruction -  GAME OVER! \n Click Ok to move to next Game.");
            int finalGameCount = GameCount;
            alert.setPositiveButton("Ok", (v, a) -> {
                if (finalGameCount == 3) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class)); }
                else {
                    startActivity(new Intent(getApplicationContext(), GameTwo.class));
                }
                finish();
            });
            alert.create().show();
        }

    }


    private class MyStrtDrggngLstnr implements View.OnLongClickListener{

        @Override
        public boolean onLongClick(View v) {
            WithDragShadow shadow= new WithDragShadow(v);
            ClipData data= ClipData.newPlainText("","");;
            v.startDrag(data,shadow,v,0);
            return false;
        }
    }

    private class MyEndDrgLstnr implements View.OnDragListener{
        @Override
        public boolean onDrag(View v, DragEvent event) {

            if(event.getAction()==DragEvent.ACTION_DROP){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    v.setBackground(((Button)event.getLocalState()).getBackground());
                    Log.d("Backgorund on darg", " " + ((Button) event.getLocalState()).getTransitionName());
                    moves.add(((Button) event.getLocalState()).getTransitionName());
                }

            }
            return true;
        }
    }

    private class WithDragShadow extends View.DragShadowBuilder{

        public WithDragShadow(View v){ super(v);  }

        @Override public void onDrawShadow(Canvas canvas) { super.onDrawShadow(canvas);}

        @Override
        public void onProvideShadowMetrics(Point outShadowSize, Point outShadowTouchPoint) {
            super.onProvideShadowMetrics(outShadowSize, outShadowTouchPoint);
        }
    }

}


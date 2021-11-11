package com.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.ClipData;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class gameone extends AppCompatActivity {

    MyStrtDrggngLstnr mStrtDrg;
    MyEndDrgLstnr mEndDrg;
    ImageView packImage;
    Button btn1, btn2, btn3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameone);
        mStrtDrg=new MyStrtDrggngLstnr();
        mEndDrg=new MyEndDrgLstnr();

        findViewById(R.id.btn_bck).setOnLongClickListener(mStrtDrg);
        findViewById(R.id.btn_dwn).setOnLongClickListener(mStrtDrg);
        findViewById(R.id.btn_fwd).setOnLongClickListener(mStrtDrg);
        findViewById(R.id.btn_up).setOnLongClickListener(mStrtDrg);

        findViewById(R.id.btn_input1).setOnDragListener(mEndDrg);
        findViewById(R.id.btn_input2).setOnDragListener(mEndDrg);
        findViewById(R.id.btn_input3).setOnDragListener(mEndDrg);
        findViewById(R.id.btn_input4).setOnDragListener(mEndDrg);



        btn1 = findViewById(R.id.btn1);

        packImage= (ImageView) findViewById(R.id.pack);

        ObjectAnimator animator = ObjectAnimator.ofFloat(packImage, "rotation", 0, 360);
        animator.setDuration(500);
        animator.start();


        // ObjectAnimator rotatePackman = ObjectAnimator.ofFloat(packImage, "rotationX", 180);
        //  rotatePackman.setDuration(300);

        ObjectAnimator scalePackman  = ObjectAnimator.ofFloat(packImage, "scaleX", 1.2f);
        scalePackman.setDuration(700);

        ObjectAnimator moveRight  = ObjectAnimator.ofFloat(packImage, "translationX", 400);
        moveRight.setDuration(1500);

        ObjectAnimator moveRight2  = ObjectAnimator.ofFloat(packImage, "translationX", 800);
        moveRight2.setDuration(1500);

        ObjectAnimator moveDown =  ObjectAnimator.ofFloat(packImage, "translationY", 700);
        moveDown.setDuration(1500);

        ObjectAnimator hidePackman  = ObjectAnimator.ofFloat(packImage, "alpha", 1.0f, 0);
        hidePackman.setDuration(300);



        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AnimatorSet animSet = new AnimatorSet();
                animSet.play(moveRight).after(scalePackman);
                animSet.play(moveDown).after(moveRight);
                animSet.play(moveRight2).after(moveDown);

                animSet.play(hidePackman).after(moveRight2);
                animSet.start();
            }
        });

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
package com.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class gameone extends AppCompatActivity {

    ImageView packImage;
    Button btn1, btn2, btn3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameone);

        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
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

        ObjectAnimator moveRight2  = ObjectAnimator.ofFloat(packImage, "translationX", 900);
        moveRight2.setDuration(1500);

        ObjectAnimator moveDown =  ObjectAnimator.ofFloat(packImage, "translationY", 1300);
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
}
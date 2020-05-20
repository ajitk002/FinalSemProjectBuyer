package com.example.finalsemprojectbuyer;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class new_center extends AppCompatActivity
{
    ImageView bgapp, clover;
    LinearLayout textsplash, texthome, menus;
    Animation frombottom;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_center);
        frombottom = AnimationUtils.loadAnimation(this, R.anim.frombottom);


        bgapp       = findViewById(R.id.bgapp);
        clover      = findViewById(R.id.clover);
        textsplash  = findViewById(R.id.textsplash);
        texthome    = findViewById(R.id.texthome);
        menus       = findViewById(R.id.menus);

        bgapp.animate().translationY(-1900).setDuration(800).setStartDelay(300);
        clover.animate().alpha(0).setDuration(800).setStartDelay(600);
        textsplash.animate().translationY(140).alpha(0).setDuration(800).setStartDelay(300);

        texthome.startAnimation(frombottom);
        menus.startAnimation(frombottom);

    }
}

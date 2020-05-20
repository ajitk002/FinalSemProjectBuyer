package com.example.finalsemprojectbuyer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalsemprojectbuyer.Chat.ChatHomeActivity;
import com.example.finalsemprojectbuyer.blogapp.Blog_MainActivity;

public class Center extends AppCompatActivity
{
    LinearLayout home, sales, profile;
    ImageView bgapp, clover;
    LinearLayout textsplash, texthome, menus, btn_blog;
    Animation frombottom;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_center);
        home = findViewById(R.id.btn_home);
        sales = findViewById(R.id.btn_sales);
        profile = findViewById(R.id.btn_profile);
        bgapp = findViewById(R.id.bgapp);
        clover = findViewById(R.id.clover);
        textsplash = findViewById(R.id.textsplash);
        texthome = findViewById(R.id.texthome);
        menus = findViewById(R.id.menus);
        btn_blog = findViewById(R.id.btn_blog);

        btn_blog.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent mainIntent = new Intent(getApplicationContext(), Blog_MainActivity.class);
                startActivity(mainIntent);
            }
        });
        frombottom = AnimationUtils.loadAnimation(this, R.anim.frombottom);

        bgapp.animate().translationY(-1900).setDuration(800).setStartDelay(300);
        bgapp.animate().translationY(-1500).setDuration(800).setStartDelay(300);
        clover.animate().alpha(0).setDuration(800).setStartDelay(600);
        textsplash.animate().translationY(140).alpha(0).setDuration(800).setStartDelay(300);

        texthome.startAnimation(frombottom);
        menus.startAnimation(frombottom);

        home.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(getApplicationContext(), ChatHomeActivity.class));
            }
        });

        profile.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
            }
        });

        sales.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(getApplicationContext(), SalesActivity.class));
            }
        });
    }
}

package com.example.finalsemprojectbuyer.Chat;

import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.finalsemprojectbuyer.Data.UserProfile;
import com.example.finalsemprojectbuyer.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatHomeActivity extends AppCompatActivity
{
    CircleImageView profileImage;
    TextView userName;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onResume()
    {
        super.onResume();
        status("online");
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        status("offline");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_activty);
        Toolbar toolbar;
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        profileImage = findViewById(R.id.profile_image);
        userName = findViewById(R.id.username);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        databaseReference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                UserProfile user_ = dataSnapshot.getValue(UserProfile.class);
                userName.setText(user_.getUser_name());
                UserProfile userProfile;
                firebaseAuth = FirebaseAuth.getInstance();
                FirebaseStorage firebaseStorage;
                firebaseStorage = FirebaseStorage.getInstance();
                StorageReference storageReference = firebaseStorage.getReference();
                storageReference
                        .child(firebaseAuth.getUid())
                        .child("Images")
                        .child("Profile Pic")
                        .getDownloadUrl()
                        .addOnSuccessListener(new OnSuccessListener<Uri>()
                        {
                            @Override
                            public void onSuccess(Uri uri)
                            {
                                Picasso.get().load(uri).into(profileImage);
                            }
                        });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });
        TabLayout tabLayout = findViewById(R.id.CA_tablayout);
        ViewPager viewPager = findViewById(R.id.CA_viewpager);
        ViewPageAdapter viewPageAdapter = new ViewPageAdapter(getSupportFragmentManager());
        viewPageAdapter.addFragment(new ChatFragment(), "chats");
        viewPageAdapter.addFragment(new UserFragment(), "User");
        viewPager.setAdapter(viewPageAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu)
//    {
//        getMenuInflater().inflate(R.menu.chat_menu, menu);
//        return true;
//    }

    public void status(String status)
    {
        databaseReference = FirebaseDatabase.getInstance().getReference("user").child(FirebaseAuth.getInstance().getUid());
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("status", status);
        databaseReference.updateChildren(hashMap);
    }

    class ViewPageAdapter extends FragmentPagerAdapter
    {
        private ArrayList<Fragment> fragments;
        private ArrayList<String> titles;

        ViewPageAdapter(FragmentManager fragmentManager)
        {
            super(fragmentManager);
            this.fragments = new ArrayList<>();
            this.titles = new ArrayList<>();

        }

        @NonNull
        @Override
        public Fragment getItem(int position)
        {
            return fragments.get(position);
        }

        @Override
        public int getCount()
        {
            return fragments.size();
        }

        public void addFragment(Fragment fragment, String title)
        {
            fragments.add(fragment);
            titles.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position)
        {
            return titles.get(position);
        }
    }
}

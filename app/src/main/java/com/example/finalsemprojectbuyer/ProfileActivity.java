package com.example.finalsemprojectbuyer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalsemprojectbuyer.Data.UserProfile;
import com.example.finalsemprojectbuyer.LoginActivitys.LoginActivity;
import com.example.finalsemprojectbuyer.LoginActivitys.UpdatePassWord;
import com.example.finalsemprojectbuyer.LoginActivitys.UpdateUserProfile;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity
{

    private Button btnLogout, btnUpdateProfile, changePassWord;
    private TextView tvName, tvAge, tvEmail;
    private CircleImageView ivProfilePic;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    FirebaseStorage firebaseStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile2);

        btnLogout           = findViewById(R.id.btnNFlogout);
        btnUpdateProfile    = findViewById(R.id.btnNFupdatePrpfile);
        changePassWord      = findViewById(R.id.btnNFchangePassword);
        tvName              = findViewById(R.id.tvNFname);
        tvAge               = findViewById(R.id.tvNFage);
        tvEmail             = findViewById(R.id.tvNFemail);
        ivProfilePic        = findViewById(R.id.ivNFprofilePic);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class ));
            }
        });

        btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), UpdateUserProfile.class));
            }
        });

        changePassWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), UpdatePassWord.class));
            }
        });
        getAndSetUpData();
        firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference() ;
        storageReference.child(firebaseAuth.getUid()).child("Images").child("Profile Pic").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(ivProfilePic);
            }
        });


    }
    private void getAndSetUpData()
    {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = firebaseDatabase.getInstance();
        Log.e("hello",firebaseAuth.getUid());
        DatabaseReference databaseReference = firebaseDatabase.getReference("user").child(firebaseAuth.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e("hello_hii",firebaseAuth.getUid());
                Log.e("hello_hii","jai ho");

                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                tvAge.setText(userProfile.getUser_phone_number());
                tvName.setText(userProfile.getUser_name());
                tvEmail.setText(userProfile.getUser_email_id());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Check Your Internet Connection", Toast.LENGTH_SHORT).show();

            }
        });
    }
}

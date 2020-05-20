package com.example.finalsemprojectbuyer.LoginActivitys;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalsemprojectbuyer.Data.UserProfile;
import com.example.finalsemprojectbuyer.parameter.ParameterUserProfile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finalsemprojectbuyer.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.regex.Pattern;

public class UpdateUserProfile extends AppCompatActivity
{
    private static int PICK_IMAGE = 123;
    boolean userphoneNuberErrorFlag, userNameErrorFlag2, userNameErrorFlag;
    String profile_pic_url;
    private EditText et_user_phone_number, et_user_name, xet_user_name2;
    private Button btn_update_user_profile;
    private ImageView ivProfilePic;
    private TextView userPhoneNumberError, userNameError2, userNameError;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    private FirebaseStorage firebaseStorage;
    private Uri imagepath;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_profile);

        setUpViews();
        setUpViewsData();

        ivProfilePic.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                getImage();
            }
        });
        btn_update_user_profile.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (validate())
                {
                    sendUserData();
                }
            }
        });
//        et_user_name2.addTextChangedListener(new TextWatcher()
//        {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
//            {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
//            {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable)
//            {
//                if (et_user_name2.getText().toString().isEmpty())
//                {
//                    userNameError2.setTextColor(Color.parseColor("#DD2C00"));
//                    userNameError2.setText("Please enter your name");
//                    userNameErrorFlag2 = false;
//                } else
//                {
//                    if (et_user_name2.getText().toString().length() >= 4)
//                    {
//                        userNameError2.setTextColor(Color.parseColor("#00C853"));
//                        userNameError2.setText("looks good");
//                        userNameErrorFlag2 = true;
//                    }
//
//                    Pattern userNamePattern = Pattern.compile("^[A-Za-z ]{4,10}");
//                    if (!userNamePattern.matcher(et_user_name2.getText().toString().trim()).matches())
//                    {
//                        userNameErrorFlag2 = false;
//                        userNameError2.setTextColor(Color.parseColor("#DD2C00"));
//                        userNameError2.setText("invalid name");
//                    }
//                }
//            }
//        });
        et_user_name.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                if (et_user_name.getText().toString().isEmpty())
                {
                    userNameError.setTextColor(Color.parseColor("#DD2C00"));
                    userNameError.setText("Please enter your name");
                    userNameErrorFlag = false;
                } else
                {
                    if (et_user_name.getText().toString().length() >= 4)
                    {
                        userNameError.setTextColor(Color.parseColor("#00C853"));
                        userNameError.setText("looks good");
                        userNameErrorFlag = true;
                    }

                    Pattern userNamePattern = Pattern.compile("^[A-Za-z ]{4,10}");
                    if (!userNamePattern.matcher(et_user_name.getText().toString().trim()).matches())
                    {
                        userNameErrorFlag = false;
                        userNameError.setTextColor(Color.parseColor("#DD2C00"));
                        userNameError.setText("invalid name");
                    }
                }
            }
        });
        et_user_phone_number.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                if (et_user_phone_number.getText().toString().isEmpty())
                {
                    userPhoneNumberError.setTextColor(Color.parseColor("#DD2C00"));
                    userPhoneNumberError.setText("Pleas enter your phone number");
                    userphoneNuberErrorFlag = false;
                } else
                {
                    userPhoneNumberError.setText("looks good");
                    userPhoneNumberError.setTextColor(Color.parseColor("#00C853"));
                    userphoneNuberErrorFlag = true;
                    Pattern userNamePattern = Pattern.compile("^\\d{10}");
                    if (!userNamePattern.matcher(et_user_phone_number.getText().toString().trim()).matches())
                    {
                        userPhoneNumberError.setTextColor(Color.parseColor("#DD2C00"));
                        userPhoneNumberError.setText("Invalid Phone Number");
                        userphoneNuberErrorFlag = false;
                    }
                }
            }
        });

    }

    private boolean validate()
    {
        boolean result = true;
        if (!et_user_phone_number.getText().toString().trim().isEmpty())
        {
            if (!userphoneNuberErrorFlag)
            {
                result = false;
                userPhoneNumberError.setTextColor(Color.parseColor("#DD2C00"));
                userPhoneNumberError.setText("Please enter valid number");
            } else
            {
                userPhoneNumberError.setTextColor(Color.parseColor("#00C853"));
                userPhoneNumberError.setText("Looks good");
                result = true;
            }
        } else
        {
            userPhoneNumberError.setTextColor(Color.parseColor("#DD2C00"));
            userPhoneNumberError.setText("Field cant be empty");
            result = false;
        }

//        if (!et_user_name2.getText().toString().trim().isEmpty())
//        {
//            if (!userNameErrorFlag2)
//            {
//                result = false;
//                userNameError2.setTextColor(Color.parseColor("#DD2C00"));
//                userNameError2.setText("Please enter valid number");
//            } else
//            {
//                userNameError2.setTextColor(Color.parseColor("#00C853"));
//                userNameError2.setText("Looks good");
//                result = true;
//            }
//        } else
//        {
//            userNameError2.setTextColor(Color.parseColor("#DD2C00"));
//            userNameError2.setText("Field cant be empty");
//            result = false;
//        }
        return result;
    }

    private void sendUserData()
    {

        String new_name = et_user_name.getText().toString().trim();
        String new_number = et_user_phone_number.getText().toString().trim();

        ParameterUserProfile upp = new ParameterUserProfile();
        upp.user_name = new_name;
        upp.user_email_id = email;
        upp.user_profile_Pic_Location = profile_pic_url;
        upp.user_password = "";
        upp.user_phone_number = new_number;
        upp.user_ID = firebaseAuth.getUid();
        upp.status = "hello";

        if (imagepath != null)
        {
            sendUserImage();
        }
        else
        {

        }
        Toast.makeText(getApplicationContext(), "hello bro 0 ", Toast.LENGTH_SHORT).show();
        final UserProfile userProfile = new UserProfile(upp);
        DatabaseReference databaseReference = firebaseDatabase.getReference("user").child(firebaseAuth.getUid());
        databaseReference.setValue(userProfile).addOnCompleteListener(new OnCompleteListener<Void>()
        {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                Toast.makeText(getApplicationContext(), userProfile.getUser_phone_number(), Toast.LENGTH_SHORT).show();
                if (task.isSuccessful())
                {
                    Toast.makeText(getApplicationContext(), "hello bro 1 ", Toast.LENGTH_SHORT).show();
                    Toast.makeText(UpdateUserProfile.this, "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

    private void sendUserImage()
    {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference("user").child(firebaseAuth.getUid());
        final StorageReference imageReference = storageReference.child(firebaseAuth.getUid()).child("Images").child("Profile Pic");  //User id/Images/Profile Pic.jpg
        if (imagepath != null)
        {
            imageReference
                    .putFile(imagepath)
                    .addOnFailureListener(new OnFailureListener()
                    {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {
                            Toast.makeText(UpdateUserProfile.this, "Upload failed!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task)
                        {
                            Toast.makeText(UpdateUserProfile.this, "Upload successful!", Toast.LENGTH_SHORT).show();
                            if (task.isSuccessful())
                            {
                                imageReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>()
                                {
                                    @Override
                                    public void onComplete(@NonNull Task<Uri> task)
                                    {
                                        if (task.isSuccessful())
                                        {
                                            Uri uri = task.getResult();
                                            profile_pic_url = uri.toString();
                                        } else
                                        {
                                            Toast.makeText(getApplicationContext(), "something went wrong" + task.getException().toString(), Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            } else
                            {
                                Toast.makeText(getApplicationContext(), "something went wrong" + task.getException().toString(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        } else
        {

        }
    }

    private void setUpViewsData()
    {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("user").child(firebaseAuth.getUid());
        databaseReference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                com.example.finalsemprojectbuyer.Data.UserProfile userProfile = dataSnapshot.getValue(com.example.finalsemprojectbuyer.Data.UserProfile.class);
                et_user_phone_number.setText(userProfile.getUser_phone_number());
                et_user_name.setText(userProfile.getUser_name());
                email = userProfile.getUser_email_id();
                Picasso.get().load(userProfile.user_profile_pic_url).into(ivProfilePic);
                profile_pic_url = userProfile.getUser_profile_pic_url();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                Toast.makeText(UpdateUserProfile.this, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });
//        firebaseStorage = FirebaseStorage.getInstance();
//        storageReference = firebaseStorage.getReference() ;
//        storageReference
//                .child(firebaseAuth.getUid()).child("Images")
//                .child("Profile Pic")
//                .getDownloadUrl()
//                .addOnSuccessListener(new OnSuccessListener<Uri>()
//                {
//                    @Override
//                    public void onSuccess(Uri uri) {
//                Picasso.get().load(uri).into(ivProfilePic);
//            }
//        });
    }

    private void getImage()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE);
    }

    private void setUpViews()
    {
        et_user_phone_number = findViewById(R.id.etUpdatePhoneNumber6);
        et_user_name = findViewById(R.id.etUpdateName6);
//        et_user_name2 = findViewById(R.id.etUpdateName2);
        btn_update_user_profile = findViewById(R.id.btnUpdate6);
        ivProfilePic = findViewById(R.id.ivProfilePic6);

        userPhoneNumberError = findViewById(R.id.etPhoneNumberError);
//        userNameError2 = findViewById(R.id.etSirNameError);
        userNameError = findViewById(R.id.etNameError);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123 && resultCode == RESULT_OK && data.getData() != null)
        {
            imagepath = data.getData();
            try
            {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagepath);
                ivProfilePic.setImageBitmap(bitmap);
            } catch (IOException e)
            {
                e.printStackTrace();
            }

        }
    }

}

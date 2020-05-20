package com.example.finalsemprojectbuyer.LoginActivitys;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finalsemprojectbuyer.Data.UserProfile;
import com.example.finalsemprojectbuyer.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;
import java.util.regex.Pattern;

import static com.theartofdev.edmodo.cropper.CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE;

public class Reg extends AppCompatActivity
{
    private static int
            PICK_IMAGE = 123;

    private ProgressDialog
            progressDialog;

    private UserProfile
            userProfile;

    private EditText
            et_confirmPassword, et_userName,
            et_userPassword, et_userEmail, et_user_phone_number;

    private TextView
            userNameError, userPasswordError,
            confirmPasswordError, userEmailError, userAgeError;

    private Boolean
            userNameErrorFlag = false, userNameErrorFlag2 = false, userPasswordErrorFlag = false,
            confirmPasswordErrorFlag = false, userEmailErrorFlag = false, userAgeErrorFlag = false;

    private Button
            userRegistration;

    private TextView
            gotoLogin;

    private FirebaseAuth
            firebaseAuth;

    private ImageView
            userImage;

    private String
            user_profile_pic_path;

    private FirebaseStorage
            firebaseStorage;

    private Uri
            imagepath;

    private StorageReference
            storageReference;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        Toast.makeText(this, "reg bro 1", Toast.LENGTH_SHORT).show();
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null)
        {
            Toast.makeText(this, "reg bro", Toast.LENGTH_SHORT).show();
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imagepath = result.getUri();
            userImage.setImageURI(imagepath);
//            imageUri = data.getData();
//            iv_product_picture.setImageURI(imageUri);

//            imagepath = data.getData();

//            try
//            {
////                Reg.this.stream = new FileInputStream(new File(data.getData().getLastPathSegment()));
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagepath);
//                userImage.setImageBitmap(bitmap);
//            } catch (IOException e)
//            {
//                e.printStackTrace();
//            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setUpUiViews();

        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        userRegistration.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                signUp();
            }
        });
        gotoLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                login();
            }
        });
        userImage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                CropImage
                        .activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setMinCropResultSize(512, 512)
                        .setAspectRatio(1, 1)
                        .start(Reg.this);
//                Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE);

            }
        });

        et_userName.addTextChangedListener(new TextWatcher()
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
                if (et_userName.getText().toString().isEmpty())
                {
                    userNameError.setTextColor(Color.parseColor("#DD2C00"));
                    userNameError.setText("Please enter your name");
                    userNameErrorFlag = false;
                } else
                {
                    if (et_userName.getText().toString().length() >= 4)
                    {
                        userNameError.setTextColor(Color.parseColor("#00C853"));
                        userNameError.setText("looks good");
                        userNameErrorFlag = true;
                    }

                    Pattern userNamePattern = Pattern.compile("^[A-Za-z ]{3,15}");
                    if (!userNamePattern.matcher(et_userName.getText().toString().trim()).matches())
                    {
                        userNameErrorFlag = false;
                        userNameError.setTextColor(Color.parseColor("#DD2C00"));
                        userNameError.setText("invalid name");
                    }
                }
            }
        });
//        et_userName2.addTextChangedListener(new TextWatcher()
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
//                if (et_userName2.getText().toString().isEmpty())
//                {
//                    userNameError2.setTextColor(Color.parseColor("#DD2C00"));
//                    userNameError2.setText("Please enter your name");
//                    userNameErrorFlag2 = false;
//                } else
//                {
//                    if (et_userName2.getText().toString().length() >= 4)
//                    {
//                        userNameError2.setTextColor(Color.parseColor("#00C853"));
//                        userNameError2.setText("looks good");
//                        userNameErrorFlag2 = true;
//                    }
//
//                    Pattern userNamePattern = Pattern.compile("^[A-Za-z ]{4,10}");
//                    if (!userNamePattern.matcher(et_userName2.getText().toString().trim()).matches())
//                    {
//                        userNameErrorFlag2 = false;
//                        userNameError2.setTextColor(Color.parseColor("#DD2C00"));
//                        userNameError2.setText("invalid name");
//                    }
//                }
//            }
//        });
        et_userPassword.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void afterTextChanged(Editable editable)
            {
                if (et_userPassword.getText().toString().isEmpty())
                {
                    userPasswordError.setTextColor(Color.parseColor("#DD2C00"));
                    userPasswordError.setText("Pleas enter your password");
                    userPasswordErrorFlag = false;
                    return;
                }

                Pattern userNamePattern = Pattern.compile("^.*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$");
                if (!userNamePattern.matcher(et_userPassword.getText().toString().trim()).matches())
                {
                    userPasswordErrorFlag = false;
                    userPasswordError.setTextColor(Color.parseColor("#DD2C00"));
                    userPasswordError.setText("Pass word must contain \nUpper case letter(A-Z),\nLower case letter(a-z), \nSpecial character(@#$%^&+=), \nAnd digit(0-9)");
                } else
                {
                    userPasswordError.setTextColor(Color.parseColor("#00C853"));
                    userPasswordErrorFlag = true;
                    userPasswordError.setText("looks good");
                }
            }
        });
        et_userEmail.addTextChangedListener(new TextWatcher()
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
                if (et_userEmail.getText().toString().isEmpty())
                {
                    userEmailError.setTextColor(Color.parseColor("#DD2C00"));
                    userEmailError.setText("Pleas enter your email id");
                    userEmailErrorFlag = false;
                } else
                {
                    userEmailError.setText("looks good");
                    userEmailError.setTextColor(Color.parseColor("#00C853"));
                    userEmailErrorFlag = true;
                    if (!Patterns.EMAIL_ADDRESS.matcher(et_userEmail.getText().toString().trim()).matches())
                    {
                        userEmailError.setTextColor(Color.parseColor("#DD2C00"));
                        userEmailError.setText("Invalid email id");
                        userEmailErrorFlag = false;
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
                    userAgeError.setTextColor(Color.parseColor("#DD2C00"));
                    userAgeError.setText("Pleas enter your phone number");
                    userAgeErrorFlag = false;
                } else
                {
                    userAgeError.setText("looks good");
                    userAgeError.setTextColor(Color.parseColor("#00C853"));
                    userAgeErrorFlag = true;
                    Pattern userNamePattern = Pattern.compile("^\\d{10}");
                    if (!userNamePattern.matcher(et_user_phone_number.getText().toString().trim()).matches())
                    {
                        userAgeError.setTextColor(Color.parseColor("#DD2C00"));
                        userAgeError.setText("Invalid Phone Number");
                        userAgeErrorFlag = false;
                    }
                }
            }
        });
    }

    private void login()
    {
        finish();
        Intent intent = new Intent(Reg.this, LoginActivity.class);
        startActivity(intent);
    }

    private void signUp()
    {
        firebaseAuth = FirebaseAuth.getInstance();
        if (validate())
        {
            String User_email = et_userEmail.getText().toString();
            String password = et_userPassword.getText().toString().trim();
            //creating user account
            firebaseAuth.createUserWithEmailAndPassword(User_email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>()
            {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task)
                {
                    if (task.isSuccessful())
                    {
                        progressDialog.setMessage("Getting registered");
                        progressDialog.show();
                        Log.e("hello", firebaseAuth.getUid());
                        sendUserData(firebaseAuth.getUid());
                        sendEmailVerification();
                        firebaseAuth.signOut();
                        Toast.makeText(Reg.this, "Registration Successful ", Toast.LENGTH_SHORT).show();
//                        finish();
//                        startActivity(new Intent(Reg.this, LoginActivity.class));
                    } else
                    {
                        Toast.makeText(Reg.this, " Registration Failed ", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }

    private void setUpUiViews()
    {
        et_userName = findViewById(R.id.etUserName);
        userNameError = findViewById(R.id.etUserNameError);

//        et_userName2 = findViewById(R.id.etUserName2);
//        userNameError2 = findViewById(R.id.etUserNameError2);

        et_userPassword = findViewById(R.id.etUserPassword);
        userPasswordError = findViewById(R.id.etUserPasswordError);

        et_confirmPassword = findViewById(R.id.etConfirmPassWord);
        confirmPasswordError = findViewById(R.id.etConfirmPassWordError);

        et_userEmail = findViewById(R.id.etEmail);
        userEmailError = findViewById(R.id.etEmailError);

        et_user_phone_number = findViewById(R.id.etAge);
        userAgeError = findViewById(R.id.etPhoneNumberError);

        userImage = findViewById(R.id.ivProfile2);

        userRegistration = findViewById(R.id.btnRegister2);
        gotoLogin = findViewById(R.id.gotologin2);
    }

    private boolean validate()
    {
        boolean result = true;
        if (!userNameErrorFlag)
        {
            userNameError.setTextColor(Color.parseColor("#DD2C00"));
            result = false;
            userNameError.setText("Please verify your data");
        }
//        if (!userNameErrorFlag2)
//        {
//            userNameError2.setTextColor(Color.parseColor("#DD2C00"));
//            result = false;
//            userNameError2.setText("Please verify your data");
//        }
        if (!userPasswordErrorFlag)
        {
            userPasswordError.setTextColor(Color.parseColor("#DD2C00"));
            result = false;
            userPasswordError.setText("Please verify your data");
        }

        if (!userEmailErrorFlag)
        {
            userEmailError.setTextColor(Color.parseColor("#DD2C00"));
            result = false;
            userEmailError.setText("Please verify your data");
        }
        if (!userAgeErrorFlag)
        {
            userAgeError.setTextColor(Color.parseColor("#DD2C00"));
            result = false;
            userAgeError.setText("Please verify your data");
        }
        if (imagepath == null)
        {
            Toast.makeText(this, "Please Pic profile image", Toast.LENGTH_SHORT).show();
            result = false;
        }
        if (!et_userPassword.getText().toString().trim().equals(et_confirmPassword.getText().toString().trim()))
        {
            confirmPasswordError.setTextColor(Color.parseColor("#DD2C00"));
            confirmPasswordError.setText("password doesn't match");
            result = false;
        }
        return result;
    }

    private void sendEmailVerification()
    {
//            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
//            if(firebaseUser != null)
//            {
//                firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
//                @Override
//                public void onComplete(@NonNull Task<Void> task) {
//                        if(task.isSuccessful())
//                        {
//                            Toast.makeText(Reg.this,"Successfully Registered, verification mail has been sent",Toast.LENGTH_SHORT).show();
//                            firebaseAuth.signOut();
//                            finish();
//                            startActivity(new Intent(Reg.this, MainActivity.class));
//                        }
//                        else
//                        {
//                            Toast.makeText(Reg.this,"Registration Failed, Email has not sent",Toast.LENGTH_SHORT).show();
//
//                        }
//                    }
//                });
//            }
    }

    private void sendUserData(final String uid)
    {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = firebaseDatabase.getReference("user").child(firebaseAuth.getUid());
        final String UID = firebaseAuth.getUid();

        //profile pic upload to firebase storage
        final StorageReference imageReference = storageReference.child(firebaseAuth.getUid()).child("Images").child("Profile Pic");  //User id/Images/Profile Pic.jpg
        imageReference
                .putFile(imagepath)
                .addOnFailureListener(new OnFailureListener()
                {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        Toast.makeText(Reg.this, "Upload failed!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>()
                {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task)
                    {
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
                                        user_profile_pic_path = uri.toString();
                                        userProfile =
                                                new UserProfile(
                                                        et_userName.getText().toString(),
                                                        UID,
                                                        et_user_phone_number.getText().toString(),
                                                        et_userEmail.getText().toString(),
                                                        user_profile_pic_path,
                                                        "offline");

                                        Log.e("hello", user_profile_pic_path);

                                        //After getting profile pic file path we are now add user details to realtime database
                                        myRef.setValue(userProfile)

                                                .addOnSuccessListener(
                                                        new OnSuccessListener<Void>()
                                                        {
                                                            @Override
                                                            public void onSuccess(Void aVoid)
                                                            {
                                                                finish();
                                                                progressDialog.dismiss();
                                                                startActivity(new Intent(Reg.this, LoginActivity.class));
                                                                Toast.makeText(Reg.this, "file upload is successful", Toast.LENGTH_SHORT).show();
                                                            }
                                                        })

                                                .addOnFailureListener(
                                                        new OnFailureListener()
                                                        {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e)
                                                            {
                                                                progressDialog.dismiss();
                                                                Toast.makeText(Reg.this, "file upload is unsuccessful" + e.toString(), Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(getApplicationContext(), "something went wrong" + task.getException().toString(), Toast.LENGTH_LONG).show();
                        }
                        Toast.makeText(Reg.this, "Upload successful!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}

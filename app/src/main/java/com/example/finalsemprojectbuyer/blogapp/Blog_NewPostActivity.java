package com.example.finalsemprojectbuyer.blogapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.finalsemprojectbuyer.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Blog_NewPostActivity extends AppCompatActivity
{

    Uri imagepath;
    private Toolbar newPostToolbar;
    private ImageView tv_new_post_image;
    private EditText et_new_post_desc;
    private Button btn_new_post_upload;
    public Uri post_image_uri;
    private ProgressBar new_post_progress_bar;

    private StorageReference storageReference;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    private String current_user_id, post_image_path, path2;

    private Bitmap compressedImageFile;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blog_activity_new_post);
        setUpViews();
        storageReference = FirebaseStorage.getInstance().getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        current_user_id = firebaseAuth.getCurrentUser().getUid();

        setSupportActionBar(newPostToolbar);
        getSupportActionBar().setTitle("Add New Post");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tv_new_post_image.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setMinCropResultSize(512, 512)
                        .setAspectRatio(1, 1)
                        .start(Blog_NewPostActivity.this);

            }
        });

        btn_new_post_upload.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(Blog_NewPostActivity.this, "get it", Toast.LENGTH_SHORT).show();
                final String desc = et_new_post_desc.getText().toString();
                if (!et_new_post_desc.getText().toString().isEmpty() && (post_image_uri != null))
                {
                    Toast.makeText(Blog_NewPostActivity.this, "get it again", Toast.LENGTH_SHORT).show();
                    btn_new_post_upload.setEnabled(false);
                    new_post_progress_bar.setVisibility(View.VISIBLE);
                    final String randomName = UUID.randomUUID().toString();
                    // PHOTO UPLOAD
//                    File newImageFile = new File(post_image_uri.getPath());
//                    try
//                    {
//                        compressedImageFile = new Compressor(Blog_NewPostActivity.this)
//                                .setMaxHeight(720)
//                                .setMaxWidth(720)
//                                .setQuality(50)
//                                .compressToBitmap(newImageFile);
//
//                    } catch (IOException e)
//                    {
//                        e.printStackTrace();
//                    }
//
//                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                    compressedImageFile.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//                    byte[] imageData = baos.toByteArray();

                    // upload pic and data
                    final StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("post_images");
                    storageReference.putFile(post_image_uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task)
                        {
                            if (task.isSuccessful())
                            {
                                storageReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>()
                                {
                                    @Override
                                    public void onComplete(@NonNull Task<Uri> task)
                                    {
                                        //upload data
                                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-mm-yyyy");
                                        String formatedDate = simpleDateFormat.format(Calendar.getInstance().getTime());

                                        post_image_path = task.getResult().toString();
                                        Map<String, Object> postMap = new HashMap<>();
                                        postMap.put("image_url", post_image_path);
                                        postMap.put("post_id", randomName);
                                        postMap.put("desc", desc);
                                        postMap.put("user_id", current_user_id);
                                        postMap.put("timestamp", formatedDate);
                                        final DocumentReference docref = FirebaseFirestore.getInstance().collection("posts").document(randomName);
                                        docref.set(postMap).addOnCompleteListener(new OnCompleteListener<Void>()
                                        {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task)
                                            {
                                                if (task.isSuccessful())
                                                {
                                                    Toast.makeText(Blog_NewPostActivity.this, "Post was added", Toast.LENGTH_LONG).show();
                                                    finish();
                                                    new_post_progress_bar.setVisibility(View.INVISIBLE);
                                                }
                                            }
                                        });
                                    }
                                });
                            }
                        }
                    });
//                    final StorageReference storageReference1 = storageReference.child("post_images");
//                    storageReference1.putBytes(imageData).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>()
//                    {
//                        @Override
//                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task)
//                        {
//                            if (task.isSuccessful())
//                            {
//                                storageReference1.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>()
//                                {
//                                    @Override
//                                    public void onComplete(@NonNull Task<Uri> task)
//                                    {
//                                        post_image_path = task.getResult().toString();
//                                    }
//                                });
//                                File newThumbFile = new File(post_image_uri.getPath());
//                                try
//                                {
//                                    compressedImageFile = new Compressor(Blog_NewPostActivity.this)
//                                            .setMaxHeight(100)
//                                            .setMaxWidth(100)
//                                            .setQuality(1)
//                                            .compressToBitmap(newThumbFile);
//
//                                } catch (IOException e)
//                                {
//                                    e.printStackTrace();
//                                }
//
//                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                                compressedImageFile.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//                                byte[] thumbData = baos.toByteArray();
//                                final StorageReference storageReference2 = storageReference.child("post_images/thumbs");
//                                storageReference2.putBytes(thumbData).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>()
//                                {
//                                    @Override
//                                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task)
//                                    {
//                                        storageReference2.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>()
//                                        {
//                                            @Override
//                                            public void onComplete(@NonNull Task<Uri> task)
//                                            {
//                                                path2 = task.getResult().toString();
//                                            }
//                                        });
//                                        Map<String, Object> postMap = new HashMap<>();
//                                        postMap.put("image_url", post_image_path);
//                                        postMap.put("post_id", randomName);
//                                        postMap.put("image_thumb", path2);
//                                        postMap.put("desc", desc);
//                                        postMap.put("user_id", current_user_id);
//                                        postMap.put("timestamp", FieldValue.serverTimestamp());
//                                        final DocumentReference docref = FirebaseFirestore.getInstance().collection("Posts").document(randomName);
//                                        docref.set(postMap).addOnCompleteListener(new OnCompleteListener<Void>()
//                                        {
//                                            @Override
//                                            public void onComplete(@NonNull Task<Void> task)
//                                            {
//                                                if (task.isSuccessful())
//                                                {
//                                                    Toast.makeText(Blog_NewPostActivity.this, "Post was added", Toast.LENGTH_LONG).show();
//                                                    finish();
//                                                    new_post_progress_bar.setVisibility(View.INVISIBLE);
//                                                }
//                                            }
//                                        });
//                                    }
//                                });
//                            }
//                        }
//                    });
                }
            }
        });
    }

    private void setUpViews()
    {
        tv_new_post_image = findViewById(R.id.new_post_image);
        et_new_post_desc = findViewById(R.id.new_post_desc);
        newPostToolbar = findViewById(R.id.new_post_toolbar);
        btn_new_post_upload = findViewById(R.id.post_btn);
        new_post_progress_bar = findViewById(R.id.new_post_progress);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null)
        {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if(result.getUri()!=null)
                Toast.makeText(this, "god save me", Toast.LENGTH_SHORT).show();
            post_image_uri = result.getUri();
            imagepath = result.getUri();
            tv_new_post_image.setImageURI(post_image_uri);
//            imageUri = data.getData();
        }
//        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
//        {
//            CropImage.ActivityResult result = CropImage.getActivityResult(data);
//            if (resultCode == RESULT_OK)
//            {
//                Toast.makeText(this, "image get it"+result.toString(), Toast.LENGTH_SHORT).show();
//                post_image_uri = result.getUri();
//                tv_new_post_image.setImageURI(post_image_uri);
//                imagepath = result.getUri();
//                try
//                {
//                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagepath);
//                } catch (IOException e)
//                {
//                    e.printStackTrace();
//                }
//
//            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE)
//            {
//
//                Exception error = result.getError();
//
//            }
//        }

    }

}

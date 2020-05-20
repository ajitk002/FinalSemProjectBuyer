package com.example.finalsemprojectbuyer.blogapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalsemprojectbuyer.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Blog_BlogRecyclerAdapter extends RecyclerView.Adapter<Blog_BlogRecyclerAdapter.ViewHolder>
{

    public Context context;
    private List<Blog_BlogPost> blog_list;
    private Boolean delete;

    private ref_listnere listener;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    public Blog_BlogRecyclerAdapter(List<Blog_BlogPost> blog_list, boolean delete, ref_listnere refresh)
    {
        this.blog_list = blog_list;
        this.delete = delete;
        this.listener = refresh;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.blog_blog_list_item, parent, false);
        context = parent.getContext();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position)
    {
        holder.setIsRecyclable(false);
        Blog_BlogPost current_post = blog_list.get(position);
        final String post_id = current_post.getPost_id();
        final String currentUserId = firebaseAuth.getCurrentUser().getUid();
        if (delete)
        {
            holder.delete.setVisibility(View.VISIBLE);
        } else
        {
            holder.delete.setVisibility(View.GONE);
        }

        //delete the current post
        holder.delete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                FirebaseFirestore.getInstance()
                        .collection("posts")
                        .document(
                                blog_list
                                        .get(position)
                                        .getPost_id()
                        )
                        .delete().addOnCompleteListener(
                        new OnCompleteListener<Void>()
                        {
                            @Override
                            public void onComplete(@NonNull Task<Void> task)
                            {
                                listener.isOrderToRefresh(true);
                                Toast.makeText(context, "doc deleted successful", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        //document data
        holder.post_description.setText(current_post.getDesc());
//        Picasso.get().load(current_post.getImage_url()).into(holder.blogUserImage);
        String image_url = current_post.getImage_url();
        holder.setBlogImage(image_url);
        holder.blogDate.setText(current_post.getTimestamp());
        //number of comments to this post
        firebaseFirestore.collection("posts").document(post_id).collection("Comments").addSnapshotListener(new EventListener<QuerySnapshot>()
        {
            @SuppressLint("SetTextI18n")
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e)
            {
                if (!queryDocumentSnapshots.isEmpty())
                {
                    int count = queryDocumentSnapshots.size();
                    holder.blog_comment_count.setText(count + " Comments");
                } else
                {
                    holder.blog_comment_count.setText(0 + " Comments");
                }
            }
        });
        //number of like to this post
        firebaseFirestore.collection("posts").document(post_id).collection("Likes").addSnapshotListener(new EventListener<QuerySnapshot>()
        {
            @SuppressLint("SetTextI18n")
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e)
            {
                if (!queryDocumentSnapshots.isEmpty())
                {
                    int count = queryDocumentSnapshots.size();
                    holder.blogLikeCount.setText(count + " Likes");
                } else
                {
                    holder.blogLikeCount.setText(0 + " Likes");
                }
            }
        });

        //if the current user liked this post then fill heart with red els fill with gray
        firebaseFirestore.collection("posts").document(post_id).collection("Likes").document(currentUserId)
                .addSnapshotListener(
                        new EventListener<DocumentSnapshot>()
                        {
                            @Override
                            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e)
                            {
                                if (documentSnapshot.exists())
                                {
                                    holder.blogLikeBtn.setImageDrawable(context.getDrawable(R.mipmap.blog_action_like_accent));
                                } else
                                {
                                    holder.blogLikeBtn.setImageDrawable(context.getDrawable(R.mipmap.blog_action_like_gray));
                                }

                            }
                        });

        //Likes Feature
        holder.blogLikeBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                firebaseFirestore
                        .collection("posts")
                        .document(post_id)
                        .collection("Likes")
                        .document(currentUserId)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>()
                        {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task)
                            {
                                if (!task.getResult().exists())
                                {
                                    Map<String, Object> likesMap = new HashMap<>();
                                    likesMap.put("timestamp", FieldValue.serverTimestamp());
                                    firebaseFirestore
                                            .collection("posts")
                                            .document(post_id)
                                            .collection("Likes")
                                            .document(currentUserId)
                                            .set(likesMap);
                                } else
                                {
                                    firebaseFirestore
                                            .collection("posts")
                                            .document(post_id)
                                            .collection("Likes")
                                            .document(currentUserId)
                                            .delete();
                                }

                            }
                        });
            }
        });

        holder.blogCommentBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent commentIntent = new Intent(context, Blog_CommentsActivity.class);
                commentIntent.putExtra("blog_post_id", post_id);
                context.startActivity(commentIntent);
            }
        });

        //User Data will be retrieved here...
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("user").child(current_post.getUser_id());
        databaseReference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                //setUpUserData
                Blog_UserProfile userProfile = dataSnapshot.getValue(Blog_UserProfile.class);
                assert userProfile != null;
                holder.blogUserName.setText(userProfile.getUser_name());
                Picasso.get().load(userProfile.user_profile_pic_url).into(holder.blogUserImage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                Toast.makeText(context, "Check your Int", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount()
    {
        return blog_list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ConstraintLayout blog_post_layout;
        private View mView;
        private Button delete;
        private ImageView blogImageView, blogLikeBtn, blogCommentBtn;
        private TextView blogDate, blogUserName, blogLikeCount, post_description, blog_comment_count;
        private CircleImageView blogUserImage;

        public ViewHolder(View itemView)
        {
            super(itemView);
            mView = itemView;
            blogDate = mView.findViewById(R.id.blog_date);
            blog_comment_count = mView.findViewById(R.id.blog_comment_count);
            blogUserImage = mView.findViewById(R.id.blog_user_image);
            blogUserName = mView.findViewById(R.id.blog_user_name);
            blogLikeCount = mView.findViewById(R.id.blog_like_count);
            blog_post_layout = mView.findViewById(R.id.blog_post_layout);
            delete = mView.findViewById(R.id.button);
            post_description = mView.findViewById(R.id.blog_desc);
            blogLikeBtn = mView.findViewById(R.id.blog_like_btn);
            blogCommentBtn = mView.findViewById(R.id.blog_comment_icon);
            blogImageView = mView.findViewById(R.id.blog_image);
        }

        public void setBlogImage(String downloadUri)
        {
            blogImageView = mView.findViewById(R.id.blog_image);
            Picasso.get().load(downloadUri).into(blogImageView);
        }

    }
}

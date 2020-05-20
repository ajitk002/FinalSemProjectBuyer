package com.example.finalsemprojectbuyer.blogapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalsemprojectbuyer.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Blog_CommentsRecyclerAdapter extends RecyclerView.Adapter<Blog_CommentsRecyclerAdapter.ViewHolder>
{

    public List<Blog_Comments> commentsList;
    public Context context;

    public Blog_CommentsRecyclerAdapter(List<Blog_Comments> commentsList)
    {
        this.commentsList = commentsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.blog_comment_list_item, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position)
    {

        holder.setIsRecyclable(false);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        Blog_Comments blog_comments = commentsList.get(position);
        DatabaseReference databaseReference = firebaseDatabase.getReference("user").child(blog_comments.getUser_id());
        databaseReference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                Blog_UserProfile userProfile = dataSnapshot.getValue(Blog_UserProfile.class);
                holder.comment_username.setText(userProfile.getUser_name());
                Picasso.get().load(userProfile.user_profile_pic_url).into(holder.comment_image);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                Toast.makeText(context, "Check your Int", Toast.LENGTH_SHORT).show();
            }
        });
        holder.setComment_message(blog_comments.getMessage());

    }


    @Override
    public int getItemCount()
    {

        if (commentsList != null)
        {
            return commentsList.size();
        }
        else
        {
            return 0;
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        CircleImageView comment_image;
        private View mView;
        private TextView comment_message,comment_username;

        public ViewHolder(View itemView)
        {
            super(itemView);
            mView = itemView;
            comment_message = mView.findViewById(R.id.comment_message);
            comment_username = mView.findViewById(R.id.comment_username);
            comment_image = mView.findViewById(R.id.comment_image);
        }

        public void setComment_message(String message)
        {
            comment_message.setText(message);
        }

    }

}

package com.example.finalsemprojectbuyer.Chat;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalsemprojectbuyer.Data.UserProfile;
import com.example.finalsemprojectbuyer.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.Viewholder>
{
    private Context context;
    private List<UserProfile> user;
    private Boolean isChat;

    public UserAdapter(Context context, List<UserProfile> user, boolean isChat)
    {
        this.context = context;
        this.user = user;
        this.isChat = isChat;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.user_item, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Viewholder holder, int position)
    {
        final UserProfile new_user = user.get(position);
        holder.user_name.setText(new_user.getUser_name());
        Log.e("user_sujith", "sujith");
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseStorage firebaseStorage;
        firebaseStorage = FirebaseStorage.getInstance();
        if (isChat)
        {
            if (new_user.getStatus().trim().equals("online"))
            {
                holder.online_on.setVisibility(View.VISIBLE);
                holder.online_off.setVisibility(View.GONE);
            } else
            {
                holder.online_on.setVisibility(View.GONE);
                holder.online_off.setVisibility(View.VISIBLE);
            }
        } else
        {
            holder.online_on.setVisibility(View.GONE);
            holder.online_off.setVisibility(View.GONE);
        }

//            StorageReference storageReference = firebaseStorage.getReferenceFromUrl(new_user.getUser_profile_pic_url()) ;
//            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                @Override
//                public void onSuccess(Uri uri) {
//                    Picasso.get().load(uri).into(holder.CIV);
//                }
//            });


        firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference();
        storageReference.child(new_user.getUser_id()).child("Images").child("Profile Pic").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>()
        {
            @Override
            public void onSuccess(Uri uri)
            {
                Picasso.get().load(uri).into(holder.CIV);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(context, MessageActivity.class);
                Log.e("6666", new_user.getUser_id());
                intent.putExtra("User ID", new_user.getUser_id());
                context.startActivity(intent);
            }
        });
//        Picasso.get().load(new_user.getProfile_pic_url()).into(holder.CIV);

    }

    @Override
    public int getItemCount()
    {

        Log.e("user", user.size() + "");
        return user.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder
    {
        public TextView user_name;
        public CircleImageView CIV, online_on, online_off;

        public Viewholder(@NonNull View itemView)
        {
            super(itemView);
            Log.e("user_sujith_ganesh", "sujith");
            user_name = itemView.findViewById(R.id.UI_userName);
            CIV = itemView.findViewById(R.id.UI_profile_pic);
            online_on = itemView.findViewById(R.id.online_on);
            online_off = itemView.findViewById(R.id.online_off);

        }
    }
}

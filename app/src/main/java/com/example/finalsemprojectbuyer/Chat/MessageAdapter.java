package com.example.finalsemprojectbuyer.Chat;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalsemprojectbuyer.Data.IndividualMessage;
import com.example.finalsemprojectbuyer.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.Viewholder>
{
    public static final int msg_type_left = 0;
    public static final int msg_type_right = 1;
    FirebaseUser firebaseUser;
    private String imageUrl;
    private Context context;
    private List<IndividualMessage> mChatClass;

    public MessageAdapter(Context context, List<IndividualMessage> mChatClass, String imageUrl)
    {
        this.context = context;
        this.mChatClass = mChatClass;
        this.imageUrl = imageUrl;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        if (viewType == msg_type_right)
        {
            View view = LayoutInflater.from(context).inflate(R.layout.chat_item_right, parent, false);
            return new Viewholder(view);
        } else
        {
            View view = LayoutInflater.from(context).inflate(R.layout.chat_item_left, parent, false);
            return new Viewholder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final Viewholder holder, int position)
    {
        IndividualMessage chatClass = mChatClass.get(position);
        holder.showMessage.setText(chatClass.getMessage());
        Picasso.get().load(imageUrl).into(holder.CIV);
        if (position == mChatClass.size() - 1)
        {
            if (chatClass.getisSeen())
            {
                holder.seen.setText("Seen");
            } else
            {
                holder.seen.setText("Delivered");
            }
        } else
        {
            holder.seen.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount()
    {

        Log.e("mChatClass", mChatClass.size() + "");
        return mChatClass.size();
    }

    @Override
    public int getItemViewType(int position)
    {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (mChatClass.get(position).getSender().equals(firebaseUser.getUid()))
        {
            return msg_type_right;
        } else
        {
            return msg_type_left;
        }
    }

    public class Viewholder extends RecyclerView.ViewHolder
    {
        public TextView showMessage, seen;
        public CircleImageView CIV;

        public Viewholder(@NonNull View itemView)
        {
            super(itemView);
            showMessage = itemView.findViewById(R.id.show_message);
            CIV = itemView.findViewById(R.id.cil_civ);
            seen = itemView.findViewById(R.id.txt_seen);
        }
    }
}

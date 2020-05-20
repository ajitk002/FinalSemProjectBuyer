package com.example.finalsemprojectbuyer.AfterSale;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.finalsemprojectbuyer.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder>
{
    private List<Uri>
            image_list;
    Context context;
    public ImageAdapter(List<Uri> image_list)
    {
        this.image_list = image_list;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_list_adapter_file, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position)
    {

//        Uri uri = image_list.get(position);
//        Picasso.get().load(uri).into(holder.ivImage);
        Uri uri = image_list.get(position);
//        Picasso.get().load(uri).into(holder.ivImage);
//        holder.ivImage.setImageURI(uri);
//        Log.e("bro", uri.toString());
        Glide.with(context).load(uri).listener(new RequestListener<Drawable>()
        {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource)
            {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("glide",e.getMessage());
                return false;
            }
            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource)
            {
                Toast.makeText(context, "SUCCESS", Toast.LENGTH_SHORT).show();
                return false;
            }
        }).into(holder.ivImage);
        holder.ivImage.setImageURI(uri);
        Log.e("bro", uri.toString());
    }

    @Override
    public int getItemCount()
    {
        return image_list.size();
    }
    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView)
    {
        super.onAttachedToRecyclerView(recyclerView);
        context = recyclerView.getContext();
    }

    class ImageViewHolder extends RecyclerView.ViewHolder
    {
        ImageView ivImage;

        ImageViewHolder(@NonNull View itemView)
        {
            super(itemView);
            ivImage = itemView.findViewById(R.id.image_list_image);
        }
    }
}

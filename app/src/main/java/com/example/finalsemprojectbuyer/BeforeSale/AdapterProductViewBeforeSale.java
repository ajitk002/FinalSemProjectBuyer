package com.example.finalsemprojectbuyer.BeforeSale;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalsemprojectbuyer.Data.ProductDataBeforeSale;
import com.example.finalsemprojectbuyer.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterProductViewBeforeSale extends RecyclerView.Adapter<AdapterProductViewBeforeSale.ImageViewHolder>{
    private static final String TAG = "Recycler View adapter";
    List<ProductDataBeforeSale> products;
    private Context context;

    public AdapterProductViewBeforeSale(List<ProductDataBeforeSale> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_product_item,parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        context = recyclerView.getContext();
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        final ProductDataBeforeSale productData = products.get(position);
        holder.tvTitle.setText(productData.getProduct_description());
        holder.description.setText(productData.getProduct_pick_up_key());
        Picasso.get().load(productData.getProduct_picture_url()).into(holder.ivImage);
        holder.parentlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductViewBeforeSale.class);
                intent.putExtra("myClass",productData);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    class ImageViewHolder extends RecyclerView.ViewHolder{
        ImageView ivImage;
        TextView tvTitle,description;
        LinearLayout parentlayout;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.ivRVimage);
            tvTitle = itemView.findViewById(R.id.tvRVproductTitle);
            description = itemView.findViewById(R.id.tvDescription);
            parentlayout = itemView.findViewById(R.id.layoutAV2);
        }
    }
}

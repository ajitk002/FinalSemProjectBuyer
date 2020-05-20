package com.example.finalsemprojectbuyer.AfterSale;

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

import com.example.finalsemprojectbuyer.Data.ProductDataAfterSale;
import com.example.finalsemprojectbuyer.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterProductAfterSale extends RecyclerView.Adapter<AdapterProductAfterSale.ImageViewHolder>
{

    private static final String TAG = "Recycler View adapter";
    List<ProductDataAfterSale> products;
    private Context context;

    public AdapterProductAfterSale(List<ProductDataAfterSale> products)
    {
        this.products = products;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_product_item, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position)
    {
        final ProductDataAfterSale productData = products.get(position);
        holder.tvTitle.setText(productData.getProduct_name());
        holder.description.setText(productData.getProduct_description());
        Picasso.get().load(productData.getProduct_picture_url()).into(holder.ivImage);
        holder.price.setText(productData.getProduct_price().concat("Rs"));
        holder.parentLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(context, ProductViewAfterSale.class);
                intent.putExtra("myClass", productData);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView)
    {
        super.onAttachedToRecyclerView(recyclerView);
        context = recyclerView.getContext();
    }

    @Override
    public int getItemCount()
    {
        return products.size();
    }

    class ImageViewHolder extends RecyclerView.ViewHolder
    {
        ImageView ivImage;
        TextView tvTitle, description, price;
        LinearLayout parentLayout;

        public ImageViewHolder(@NonNull View itemView)
        {
            super(itemView);
            ivImage = itemView.findViewById(R.id.ivRVimage);
            tvTitle = itemView.findViewById(R.id.tvRVproductTitle);
            description = itemView.findViewById(R.id.tvDescription);
            price = itemView.findViewById(R.id.tv_product_price);
            parentLayout = itemView.findViewById(R.id.layoutAV2);
        }
    }
}

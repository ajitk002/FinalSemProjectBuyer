package com.example.finalsemprojectbuyer.AfterSale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.finalsemprojectbuyer.Data.ImageListImageData;
import com.example.finalsemprojectbuyer.Data.ProductDataAfterSale;
import com.example.finalsemprojectbuyer.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Product_view_more extends AppCompatActivity
{
    private RecyclerView rv_image_list;
    private List<Uri>
            image_list = new ArrayList<>();
    private TextView
            tv_product_name, tv_product_upi_id, tv_product_ID, tv_product_condition,
            tv_product_original_date_of_purchase, tv_product_company, tv_product_categories,
            tv_product_description, tv_product_address, tv_product_payment_mode,
            tv_product_location_longitude, tv_product_location_latitude, tv_product_price,
            tv_product_pick_up_date, tv_product_date_of_purchase, tv_product_date_of_sale;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view_more);
        Bundle extras = getIntent().getExtras();
        setUpViews();
        if (extras.getSerializable("myClass") != null)
        {
            final ProductDataAfterSale pd = (ProductDataAfterSale) extras.getSerializable("myClass");
            tv_product_address.setText(pd.getProduct_address() == null ? "" : pd.getProduct_address());
            tv_product_categories.setText(pd.getProduct_categories() == null ? "" : pd.getProduct_categories());
            tv_product_company.setText(pd.getProduct_company() == null ? "" : pd.getProduct_categories());
            tv_product_condition.setText(pd.getProduct_condition() == null ? "" : pd.getProduct_condition());
            tv_product_date_of_purchase.setText(pd.getProduct_date_of_purchase() == null ? "" : pd.getProduct_date_of_purchase());
            tv_product_date_of_sale.setText(pd.getProduct_date_of_sale() == null ? "" : pd.getProduct_date_of_sale());
            tv_product_description.setText(pd.getProduct_description() == null ? "" : pd.getProduct_description());
            tv_product_ID.setText(pd.getProduct_ID() == null ? "" : pd.getProduct_ID());
            tv_product_location_latitude.setText(pd.getProduct_location_latitude() == null ? "" : pd.getProduct_location_latitude());
            tv_product_location_longitude.setText(pd.getProduct_location_longitude() == null ? "" : pd.getProduct_location_longitude());
            tv_product_name.setText(pd.getProduct_name() == null ? "" : pd.getProduct_name());
            tv_product_original_date_of_purchase.setText(pd.getProduct_usage_duration() == null ? "" : pd.getProduct_usage_duration());
            tv_product_payment_mode.setText(pd.getProduct_payment_mode() == null ? "" : pd.getProduct_payment_mode());
            tv_product_pick_up_date.setText(pd.getProduct_pick_up_date() == null ? "" : pd.getProduct_pick_up_date());
            tv_product_price.setText(pd.getProduct_price() == null ? "" : pd.getProduct_price());
            tv_product_upi_id.setText(pd.getProduct_upi_id() == null ? "" : pd.getProduct_upi_id());
            tv_product_name.setText(pd.getProduct_pick_up_key() == null ? "" : pd.getProduct_pick_up_key());
            loadImages(pd);
        }


    }
    private void setUpViews()
    {
        rv_image_list = findViewById(R.id.rv_image_list);
        tv_product_name = findViewById(R.id.tv_productName);
        tv_product_name = findViewById(R.id.tv_productName);
        tv_product_upi_id = findViewById(R.id.tv_product_upi_id);
        tv_product_ID = findViewById(R.id.tv_product_ID);
        tv_product_condition = findViewById(R.id.tv_product_condition);
        tv_product_original_date_of_purchase = findViewById(R.id.tv_product_original_date_of_purchase);
        tv_product_company = findViewById(R.id.tv_product_company);
        tv_product_categories = findViewById(R.id.tv_product_categories);
        tv_product_description = findViewById(R.id.tv_product_description);
        tv_product_address = findViewById(R.id.tv_product_address);
        tv_product_payment_mode = findViewById(R.id.tv_product_payment_mode);
        tv_product_location_longitude = findViewById(R.id.tv_product_location_longitude);
        tv_product_location_latitude = findViewById(R.id.tv_product_location_latitude);
        tv_product_price = findViewById(R.id.tv_product_price);
        tv_product_pick_up_date = findViewById(R.id.tv_product_pick_up_date);
        tv_product_date_of_purchase = findViewById(R.id.tv_product_date_of_purchase);
        tv_product_date_of_sale = findViewById(R.id.tv_product_date_of_sale);
    }
    private void loadImages(ProductDataAfterSale pd)
    {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance(

        ).getReference().child("Product images").child(pd.getProduct_ID());
        databaseReference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    image_list.add(Uri.parse(snapshot.getValue(ImageListImageData.class).getPath()));
                }
                rv_image_list.setVisibility(View.VISIBLE);
                rv_image_list.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
                rv_image_list.setAdapter(new ImageAdapter(image_list));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });
    }
}

package com.example.finalsemprojectbuyer.BeforeSale;

import android.app.DatePickerDialog;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.finalsemprojectbuyer.AfterSale.ImageAdapter;
import com.example.finalsemprojectbuyer.Data.ImageListImageData;
import com.example.finalsemprojectbuyer.Data.ProductDataAfterSale;
import com.example.finalsemprojectbuyer.Data.ProductDataBeforeSale;
import com.example.finalsemprojectbuyer.Data.UserProfile;
import com.example.finalsemprojectbuyer.DatePickerFragment;
import com.example.finalsemprojectbuyer.R;
import com.example.finalsemprojectbuyer.parameter.ParameterProductDataAfterSale;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductViewBeforeSale extends AppCompatActivity implements DatePickerDialog.OnDateSetListener
{
    ImageView ivDPVproductImage;
    boolean isPickupDateSelected = false;
    Button btnBuy;
    String product_date_of_purchase;
    String BuyingProductID;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String name;
    private RequestQueue mRequeue;
    private TextView
            tv_product_name, tv_product_details, tv_pick_up_date, tv_product_categories, tv_product_company,
            tv_date_of_original_purchase, tv_product_condition, tv_product_price;
    private List<Uri> image_list = new ArrayList<>();
    private RecyclerView rv_image_list;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        final Bundle extras = getIntent().getExtras();
        setContentView(R.layout.activity_detailed_product_view);
        setUpViews();

        final ProductDataBeforeSale pd = (ProductDataBeforeSale) extras.getSerializable("myClass");
        tv_product_name.setText(pd.getProduct_name());
        tv_product_details.setText(pd.getProduct_description());
        tv_product_categories.setText(pd.getProduct_categories());
        tv_product_company.setText(pd.getProduct_company());
        tv_date_of_original_purchase.setText(pd.getProduct_usage_duration());
        tv_product_condition.setText(pd.getProduct_condition());
        tv_product_price.setText(pd.getProduct_price());
        Picasso.get().load(pd.getProduct_picture_url()).into(ivDPVproductImage);
        BuyingProductID = pd.getProduct_ID();
        assert pd != null;
        loadImages(pd);
        mRequeue = Volley.newRequestQueue(getApplicationContext());
        tv_pick_up_date.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                DialogFragment date_picker = new DatePickerFragment();
                date_picker.show(getSupportFragmentManager(), "date picker");
            }
        });

        btnBuy.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (isPickupDateSelected)
                {
                    final ProductDataBeforeSale productData = (ProductDataBeforeSale) extras.getSerializable("myClass");
                    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                    DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
                    String formatedDate = df.format(Calendar.getInstance().getTime());

                    Date date = new Date();
                    ParameterProductDataAfterSale spdp = new ParameterProductDataAfterSale();
                    spdp.product_ID = productData.getProduct_ID();
                    spdp.Seller_ID = productData.getProduct_seller_ID();
                    spdp.Product_Name = productData.getProduct_name();
                    spdp.product_condition = productData.getProduct_condition();
//                    spdp.date_of_purchase = productData.;
                    spdp.product_company = productData.getProduct_company();
                    spdp.product_category = productData.getProduct_categories();
                    spdp.product_details = productData.getProduct_description();
                    spdp.product_name = productData.getProduct_name();
                    spdp.product_address = productData.getProduct_address();
                    spdp.payment_type = productData.getProduct_payment_mode();
                    spdp.longitude = productData.getProduct_location_longitude();
                    spdp.latitude = productData.getProduct_location_latitude();
                    spdp.product_price = productData.getProduct_price();
                    spdp.product_key = productData.getProduct_pick_up_key();
                    spdp.product_upi_id = productData.getProduct_upi_id();
                    spdp.product_picture_url = productData.getProduct_picture_url();
                    spdp.date_of_sale = productData.getProduct_date_of_sale();
                    spdp.original_date_of_Purchase = productData.getProduct_usage_duration();
                    spdp.date_of_Purchase = formatedDate;
                    spdp.pick_up_date = product_date_of_purchase;
                    spdp.buyer_ID = firebaseAuth.getUid();
                    spdp.product_address_pin = productData.getProduct_address_pin();

                    final ProductDataAfterSale soldProductData = new ProductDataAfterSale(spdp);

                    //removing from unsold products
                    DocumentReference documentReference = db.collection("products").document(BuyingProductID);
                    documentReference.delete().addOnCompleteListener(new OnCompleteListener<Void>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {
                            Toast.makeText(ProductViewBeforeSale.this, "Task Success", Toast.LENGTH_SHORT).show();
                            FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
                            StorageReference storageReference = firebaseStorage.getReferenceFromUrl(pd.getProduct_picture_url());

                            //Moving to sold product
                            DocumentReference documentReference = db.collection("soldProducts").document(productData.getProduct_ID());
                            documentReference.set(soldProductData).addOnSuccessListener(new OnSuccessListener<Void>()
                            {
                                @Override
                                public void onSuccess(Void aVoid)
                                {
                                    Toast.makeText(getApplicationContext(), "You have successfully purchased the products", Toast.LENGTH_SHORT).show();
                                    //
                                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("user").child( FirebaseAuth.getInstance().getUid());
                                    databaseReference.addValueEventListener(new ValueEventListener()
                                    {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                                        {
                                            UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                                            name = userProfile.getUser_name();
                                            sendNotification(soldProductData.getProduct_ID(), name, soldProductData.getProduct_name());
                                            Toast.makeText(ProductViewBeforeSale.this, "getting name"+userProfile.getUser_name(), Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError)
                                        {

                                        }
                                    });
                                    //
                                }

                            });

                        }
                    });
                }
            }
        });
    }

    private void sendNotification(String product_id, @NonNull String buyer_name, String product_name)
    {
        String url = "https://fcm.googleapis.com/fcm/send";
        JSONObject json = new JSONObject();
        try
        {
            json.put("to", "/topics/" + product_id);
            JSONObject notificationObj = new JSONObject();
            notificationObj.put("title", product_name);
            notificationObj.put("body", buyer_name + " has purchased your product");

            JSONObject extraData = new JSONObject();
            extraData.put("product_id", product_id);
            extraData.put("mType", "S");

            json.put("notification", notificationObj);
            json.put("data", extraData);

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url,
                    json,
                    new Response.Listener<JSONObject>()
                    {
                        @Override
                        public void onResponse(JSONObject response)
                        {
                            Toast.makeText(getApplicationContext(), "onResponse", Toast.LENGTH_SHORT).show();
                            Log.d("MUR", "onResponse: ");
                        }
                    }, new Response.ErrorListener()
            {
                @Override
                public void onErrorResponse(VolleyError error)
                {
                    Toast.makeText(getApplicationContext(), "onFailure", Toast.LENGTH_SHORT).show();
                    Log.d("MUR", "onError: " + error.networkResponse);
                }
            }
            )
            {

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError
                {
                    Map<String, String> header = new HashMap<>();
                    header.put("content-type", "application/json");
                    header.put("authorization", "key=AAAA1ZtthRU:APA91bHgP2WP6ebUnvwM9oVAayzyYEdf3m-cEyUUxmOb6-sYZdsB5c7sU3d6HVENadgwVUNEzD4ImAWhLUKkBxT12BIk7co88jeIvBa3t5rn_hipftdDfzuBlEbB8pDg380UwKSSosZX");
                    return header;
                }
            };
            mRequeue.add(request);
        } catch (JSONException e)

        {
            e.printStackTrace();
        }
    }

    private void setUpViews()
    {
        tv_pick_up_date = findViewById(R.id.tv_Pick_up_date);
        tv_product_name = findViewById(R.id.tvDPVname);
        tv_product_details = findViewById(R.id.tvDPVproductDetails);
        tv_product_categories = findViewById(R.id.tvDPVcategories);
        rv_image_list = findViewById(R.id.rv_image_list);
        tv_product_company = findViewById(R.id.tvDPVcompany);
        tv_date_of_original_purchase = findViewById(R.id.tvDPVnameDateOfPurchase);
        tv_product_condition = findViewById(R.id.tvDPVproductCondition);
        tv_product_price = findViewById(R.id.tvDPVprice);
        ivDPVproductImage = findViewById(R.id.ivDPVproductImage);
        btnBuy = findViewById(R.id.btnDPVBuy);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2)
    {
        isPickupDateSelected = true;
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, i);
        c.set(Calendar.MONTH, i1);
        c.set(Calendar.DAY_OF_MONTH, i2);
        product_date_of_purchase = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        tv_pick_up_date.setText(product_date_of_purchase);
    }

    private void loadImages(ProductDataBeforeSale pd)
    {
        rv_image_list.setVisibility(View.VISIBLE);
        Toast.makeText(this, "hai", Toast.LENGTH_SHORT).show();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Product images").child(pd.getProduct_ID());
        databaseReference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
//                    ImageListImageData imageListImageData = (ImageListImageData) snapshot.getChildren();
                    image_list.add(
                            Uri.parse(snapshot.getValue(ImageListImageData.class).getPath()));
//                    image_list.add(Uri.parse(imageListImageData.getPath()));
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

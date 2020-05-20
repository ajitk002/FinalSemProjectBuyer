package com.example.finalsemprojectbuyer.BeforeSale;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finalsemprojectbuyer.Data.ProductDataBeforeSale;
import com.example.finalsemprojectbuyer.R;
import com.example.finalsemprojectbuyer.parameter.ParameterProductDataBeforeSale;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class NewProductForSale extends AppCompatActivity
{
    FirebaseAuth firebaseAuth;

    private Button btn_sell_product;
    private ImageView iv_product_picture;
    private TextView tv_pick_location;
    Spinner spinner;

    private EditText
            et_product_name,
            et_product_description,
            et_product_categories,
            et_product_company,
            et_date_of_purchase,
            et_product_price;
//            et_product_condition;

    Uri imageUri;
    int PLACE_PICKER_REQUEST = 1;

    private String
            product_ID,
            product_seller_ID,
            product_name,
            product_description,
            product_categories,
            product_company,
            product_original_date_of_purchase,
            product_date_of_sale,
            product_condition,
            product_price;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product_for_sale);
        setUpUIViews();

        tv_pick_location.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                pickLocation();

            }
        });

        iv_product_picture.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startGalleryForImagePicking();
            }
        });

        btn_sell_product.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                sellProduct();

            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                product_condition = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(getApplicationContext(),product_condition,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });

    }
    public void sellProduct()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
        SimpleDateFormat salesDate = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        firebaseAuth                = FirebaseAuth.getInstance();
        product_ID                  = sdf.format(new Date());
        product_seller_ID = firebaseAuth.getUid();
        product_name                = et_product_name.getText().toString().trim();
        product_description         = et_product_description.getText().toString().trim();
        product_categories          = et_product_categories.getText().toString().trim();
        product_company             = et_product_company.getText().toString().trim();
        product_original_date_of_purchase = et_date_of_purchase.getText().toString().trim();
        product_date_of_sale        = salesDate.format(new Date());
        product_price               = et_product_price.getText().toString().trim();
        //

        //
        if(validate())
        {
            String mineType = MimeTypeMap.getSingleton().getExtensionFromMimeType(getContentResolver().getType(imageUri));
            uploadFile(mineType);
        }
    }
    public boolean validate()
    {
        if(product_ID.isEmpty())
            return false;
        if(product_seller_ID.isEmpty())
            return false;
        if(product_name.isEmpty())
            return false;
        if(product_description.isEmpty())
            return false;
        if(product_categories.isEmpty())
            return false;
        if(product_company.isEmpty())
            return false;
        if(product_original_date_of_purchase.isEmpty())
            return false;
        if(product_condition.isEmpty())
            return false;
        if(product_price.isEmpty())
            return false;
        return true;
    }
    private void uploadFile(String mineType)
    {
        final StorageReference ref = FirebaseStorage.getInstance().getReference("Product Pic").child(product_ID+"."+mineType);
        ref.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>()
        {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task)
            {
                if(task.isSuccessful())
                {
                    ref.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if(task.isSuccessful()) {
                                Uri uri = task.getResult();
                                String Picloc = uri.toString();
                                upLoadImageDataToFireStore(product_ID, product_seller_ID, product_name, product_description, product_categories, product_company, product_original_date_of_purchase, product_date_of_sale, product_condition, Picloc, product_price);
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"something went wrong"+task.getException().toString(),Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }else
                {
                    Toast.makeText(getApplicationContext(),"something went wrong"+task.getException().toString(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private void upLoadImageDataToFireStore(String productID, String sellerID, String productName, String description, String categories, String company, String dateOfPurchase, String dateOfSale, String p_con, String picLoc, String price)
    {
        final DocumentReference docref = FirebaseFirestore.getInstance().collection("products").document(productID);
        Random random = new Random();
        Toast.makeText(getApplicationContext(),product_condition+"noting",Toast.LENGTH_SHORT).show();
        ParameterProductDataBeforeSale pdp = new ParameterProductDataBeforeSale();
        pdp.product_ID = product_ID;
        pdp.product_seller_ID = product_seller_ID;
        pdp.product_name = product_name;
        pdp.product_condition = product_condition;
        pdp.product_original_date_of_purchase = product_original_date_of_purchase;
        pdp.product_company = product_company;
        pdp.product_categories = product_categories;
        pdp.product_description = product_description;
        pdp.product_address = "pending";
        pdp.product_payment_mode = "pending";
        pdp.product_location_longitude = "pending";
        pdp.product_location_latitude = "pending";
        pdp.product_price = product_price;
        pdp.product_pick_up_key = String.format("%40d%n", random.nextInt(1000));
        pdp.product_date_of_sale = dateOfSale;
        pdp.product_picture_url = picLoc;

        ProductDataBeforeSale pd = new ProductDataBeforeSale(pdp);
        docref.set(pd).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(getApplicationContext(),"Upload Complete",Toast.LENGTH_LONG).show();
                }else
                {
                    Toast.makeText(getApplicationContext(),"Something Went Wrong",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void pickLocation()
    {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try
        {
            startActivityForResult(builder.build(NewProductForSale.this), PLACE_PICKER_REQUEST);
        }
        catch (GooglePlayServicesRepairableException e)
        {
            e.printStackTrace();
        }
        catch (GooglePlayServicesNotAvailableException e)
        {
            e.printStackTrace();
        }
    }
    public void setUpUIViews()
    {
        spinner         =   findViewById(R.id.spinner_product_condition);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.product_condition, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        iv_product_picture      =   findViewById(R.id.iv_product_picture);
        et_product_name         =   findViewById(R.id.et_product_name);
        et_product_description  =   findViewById(R.id.et_product_description);
        et_product_categories   =   findViewById(R.id.et_product_categories);
        et_product_company      =   findViewById(R.id.et_product_company);
        et_date_of_purchase     =   findViewById(R.id.et_date_of_purchase);
        et_product_price        =   findViewById(R.id.et_product_price);
        tv_pick_location        =   findViewById(R.id.tv_pick_location);
        btn_sell_product        =   findViewById(R.id.btn_sell_product);
    }
    private void startGalleryForImagePicking()
    {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, 0);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0 && resultCode == RESULT_OK && data!=null)
        {
            imageUri = data.getData();
            iv_product_picture.setImageURI(imageUri);
        }
        if(requestCode == PLACE_PICKER_REQUEST && resultCode == RESULT_OK )
        {
            Log.e("map","not key error");
            Place place = PlacePicker.getPlace(data, this);
            StringBuilder stringBuilder = new StringBuilder();
            String latitude = String.valueOf(place.getLatLng().latitude);
            String longitude = String.valueOf(place.getLatLng().longitude);
            et_product_name.setText(latitude);
            et_product_description.setText(longitude);
        }
    }
}

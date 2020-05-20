package com.example.finalsemprojectbuyer.AfterSale;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalsemprojectbuyer.MapActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;

import com.example.finalsemprojectbuyer.Chat.MessageActivity;
import com.example.finalsemprojectbuyer.Data.ProductDataAfterSale;
import com.example.finalsemprojectbuyer.DatePickerFragment;
import com.example.finalsemprojectbuyer.PymentActivity;
import com.example.finalsemprojectbuyer.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ProductViewAfterSale extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, LocationListener
{
    TextView tvDSPVproductName, tv_pick_up_date;
    Button btnDSPVcomfirmTransaction, btn_pay, btn_chat, btn_pick_update, btn_map, btn_product_details;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FusedLocationProviderClient client;
    public Double to_longitude;
    public Double to_latitude;
    boolean flag;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_sold_product_view);
        setUpViews();
//        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
//                &&
//                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
//        {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        Location location;
//        location = getLastKnownLocation();
//        onLocationChanged(location);
//        //
        client = LocationServices.getFusedLocationProviderClient(getApplicationContext());
        client.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>()
        {
            @Override
            public void onSuccess(Location location)
            {
                if(location!= null)
                {
                    to_longitude = location.getLongitude();
                    to_latitude = location.getLatitude();
                    Toast.makeText(ProductViewAfterSale.this, location.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        Bundle extras = getIntent().getExtras();
        final ProductDataAfterSale pd = (ProductDataAfterSale) extras.getSerializable("myClass");
        tvDSPVproductName.setText(pd.getProduct_pick_up_key());
        btn_product_details.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), Product_view_more.class);
                intent.putExtra("myClass", pd);
                startActivity(intent);
            }
        });
        btn_pay.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), PymentActivity.class);
                intent.putExtra("amount", pd.getProduct_price());
                intent.putExtra("note", "Thanks for buying my products");
                intent.putExtra("name", pd.getProduct_buyer_id());
                intent.putExtra("upi", pd.getProduct_upi_id());
                startActivityForResult(intent, 123);
            }
        });

        btn_chat.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), MessageActivity.class);
                intent.putExtra("User ID", pd.getProduct_seller_ID());
                startActivity(intent);
            }
        });
        tv_pick_up_date.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                DialogFragment date_picker = new DatePickerFragment();
                date_picker.show(getSupportFragmentManager(), "date picker");
            }
        });
        btn_pick_update.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                DocumentReference documentReference = db.collection("soldProducts").document(pd.getProduct_ID());
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("product_pick_up_date", tv_pick_up_date.getText().toString());
                documentReference.update(hashMap).addOnCompleteListener(new OnCompleteListener<Void>()
                {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        Toast.makeText(ProductViewAfterSale.this, "Pick Up date Updated Successfully", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        btn_map.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                Intent intent = new Intent(getApplicationContext(), MapActivity.class);
                intent.putExtra("from_long", pd.getProduct_location_longitude());
                intent.putExtra("from_lat", pd.getProduct_location_longitude());
                intent.putExtra("to_long",to_longitude.toString());
                intent.putExtra("to_lat", to_latitude.toString());
                startActivity(intent);
            }
        });
    }

    private void setUpViews()
    {
        btn_product_details = findViewById(R.id.btn_product_details);
        tvDSPVproductName = findViewById(R.id.tvDSPVproductName);
        btnDSPVcomfirmTransaction = findViewById(R.id.btnDSPVcomfirmTransaction);
        btn_pay = findViewById(R.id.btnPay);
        btn_chat = findViewById(R.id.btn_chat);
        btn_pick_update = findViewById(R.id.btn_change_pick_up_date);
        tv_pick_up_date = findViewById(R.id.tv_Pick_up_date);
        btn_map = findViewById(R.id.btn_map);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2)
    {
        flag = true;
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, i);
        c.set(Calendar.MONTH, i1);
        c.set(Calendar.DAY_OF_MONTH, i2);
        String product_original_date_of_purchase = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        tv_pick_up_date.setText(tv_pick_up_date.getText() + product_original_date_of_purchase);
    }

    @Override
    public void onLocationChanged(Location location)
    {
        LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = service.getBestProvider(criteria, false);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location loc = service.getLastKnownLocation(provider);
        LatLng latLng = new LatLng(loc.getLatitude(), loc.getLongitude());
        Toast.makeText(getApplicationContext(), loc.getLatitude()+"", Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(), loc.getLongitude()+"", Toast.LENGTH_SHORT).show();
        if(true)
       {
           return;
       }
        try
        {
            Double longitude = location.getLongitude();
            Double latitude = location.getLatitude();
            Toast.makeText(getApplicationContext(), longitude.toString(), Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), latitude.toString(), Toast.LENGTH_SHORT).show();
        } catch (Exception e)
        {
            Toast.makeText(getApplicationContext(), "SomeThing went wrong", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle)
    {

    }

    @Override
    public void onProviderEnabled(String s)
    {

    }

    @Override
    public void onProviderDisabled(String s)
    {

    }



    private Location getLastKnownLocation()
    {
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        List<String> lp = locationManager.getProviders(false);
        for(int i = 0; i<lp.size();i++)
        {
            Log.e("Location Activity", lp.get(i));
        }
        List<String> providers = Collections.singletonList(locationManager.getBestProvider(criteria, true));
        Location bestLocation = null;
        for (String provider : providers)
        {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return bestLocation;
            }
            Location l = locationManager.getLastKnownLocation(provider);
            if (l == null)
            {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy())
            {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }
}

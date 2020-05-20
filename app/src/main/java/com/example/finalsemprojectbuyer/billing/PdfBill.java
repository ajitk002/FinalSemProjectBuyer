package com.example.finalsemprojectbuyer.billing;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finalsemprojectbuyer.Data.BillData;
import com.example.finalsemprojectbuyer.Data.ProductDataAfterSale;
import com.example.finalsemprojectbuyer.Data.UserProfile;
import com.example.finalsemprojectbuyer.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PdfBill extends AppCompatActivity
{
    Button btnCreate;
    EditText editText;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    BillData billData;
    ProductDataAfterSale productDataAfterSale;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        btnCreate = (Button) findViewById(R.id.create);
        editText = (EditText) findViewById(R.id.edittext);
        Bundle extras = getIntent().getExtras();
        if (extras.getSerializable("myClass") != null)
        {
            productDataAfterSale = (ProductDataAfterSale) extras.getSerializable("myClass");
        }
        getBillData();
        btnCreate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                createPdf();
            }
        });

    }

    private void getBillData()
    {
        billData = new BillData();
        //Buyer and seller
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = firebaseDatabase.getInstance();
        Log.e("hello", firebaseAuth.getUid());

        //buyer details
        DatabaseReference databaseReference = firebaseDatabase.getReference("user").child(firebaseAuth.getUid());
        databaseReference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                billData.setSeller_email(userProfile.getUser_email_id());
                billData.setSeller_number(userProfile.getUser_phone_number());
                billData.setSeller_name(userProfile.getUser_name());
                billData.setSeller_id(productDataAfterSale.getProduct_buyer_id());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                Toast.makeText(getApplicationContext(), "Check Your Internet Connection", Toast.LENGTH_SHORT).show();

            }
        });

        //seller details
        databaseReference = firebaseDatabase.getReference("user").child(productDataAfterSale.getProduct_buyer_id().trim());
        databaseReference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                billData.setBuyer_email(userProfile.getUser_email_id());
                billData.setBuyer_number(userProfile.getUser_phone_number());
                billData.setBuyer_name(userProfile.getUser_name());
                billData.setBuyer_id(productDataAfterSale.getProduct_seller_ID());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                Toast.makeText(getApplicationContext(), "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });

        //product details
        billData.setProduct_name(productDataAfterSale.getProduct_name());
        billData.setProduct_price(productDataAfterSale.getProduct_price());
        billData.setProduct_gst("-");
        billData.setProduct_qty("1");


    }

    private void createPdf()
    {
        //document 1
        PdfDocument document = new PdfDocument();
        ProductDataAfterSale PDAS = new ProductDataAfterSale();
        //page 1
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(563, 342, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);
        Canvas canvas = page.getCanvas();
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);


        //draw borders
        canvas.drawRect(9, 7, 544 + 9, 326 + 7, paint);
        canvas.drawRect(29, 32, 254 + 29, 114 + 32, paint);
        canvas.drawRect(283, 32, 254 + 283, 114 + 32, paint);
        canvas.drawRect(29, 164, 508 + 29, 153 + 164, paint);
        canvas.drawRect(37, 174, 487 + 37, 22 + 174, paint);
        canvas.drawRect(37, 273, 487 + 37, 22 + 273, paint);

        canvas.drawLine(383.5f, 174.5f, 383f + 0f, 174.5f + 121f, paint);
        canvas.drawLine(432.5f, 174.5f, 432.5f + 0f, 121f + 174.5f, paint);
        canvas.drawLine(477.5f, 174.5f, 477.5f + 0f, 121f + 174.5f, paint);
        canvas.drawLine(523.5f, 174.5f, 523.5f + 0f, 174.5f + 121f, paint);

        //headings
        paint.setTextSize(10.0f);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.parseColor("#2962FF"));
        canvas.drawText("Invoice", 275 - 5, 10 + 11, paint);
        canvas.drawText("Seller", 49 - 5, 40 + 11, paint);
        canvas.drawText("Buyer", 295 - 5, 40 + 11, paint);
        canvas.drawText("Item Name", 103 - 5, 176 + 11, paint);
        canvas.drawText("QTY", 399 - 5, 176 + 11, paint);
        canvas.drawText("GST", 466 - 15, 176 + 11, paint);
        canvas.drawText("Price", 491 - 5, 175 + 11, paint);
        canvas.drawText("Total", 119 - 5, 277 + 11, paint);
        paint.setColor(Color.parseColor("#DD2C00"));
        canvas.drawText("E & O E", 465 - 5, 298 + 11, paint);
        paint.setColor(Color.BLACK);


        //Seller Details
        paint.setColor(Color.BLACK);
        canvas.drawText(billData.getSeller_name(), 49, 56+10, paint);
        canvas.drawText(billData.getSeller_number(), 49, 70+10, paint);
        canvas.drawText(billData.getSeller_email(), 49, 84+10, paint);
        canvas.drawText("\nSeller ID:"+billData.getSeller_id(), 49, 98+10, paint);


        //Buyer Details
        canvas.drawText(billData.getBuyer_name(), 295, 56+10, paint);
        canvas.drawText(billData.getBuyer_number(), 295, 70+10, paint);
        canvas.drawText(billData.getBuyer_email(), 295, 84+10, paint);
        canvas.drawText("\nBuyer ID:"+billData.getBuyer_id(), 295, 98+10, paint);


        //Product Details
        canvas.drawText(billData.getProduct_name(), 40+15, 206, paint);
        canvas.drawText("1", 404, 206+10, paint);
        canvas.drawText("1", 404, 273+10, paint);
        canvas.drawText("-", 451, 206+10, paint);
        canvas.drawText("-", 451, 273+10, paint);
        canvas.drawText(billData.getProduct_price(), 490, 206+10, paint);
        canvas.drawText(billData.getProduct_price(), 489, 273+10, paint);


        paint.setColor(Color.BLACK);
        document.finishPage(page);


        String directory_path = Environment.getExternalStorageDirectory().getPath() + "/mypdf/";
        File file = new File(directory_path);
        if (!file.exists())
        {
            file.mkdirs();
        }
        String targetPdf = directory_path + "test-2.pdf";
        try
        {
            File filePath = new File(targetPdf);
            filePath.createNewFile();
            document.writeTo(new FileOutputStream(filePath));
            Toast.makeText(this, "Done" + targetPdf, Toast.LENGTH_LONG).show();
        } catch (IOException e)
        {
            Log.e("main", "error " + e.toString());
            Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
        }
        document.close();
    }
}

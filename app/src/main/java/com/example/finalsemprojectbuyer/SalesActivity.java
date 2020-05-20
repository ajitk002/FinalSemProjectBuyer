package com.example.finalsemprojectbuyer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalsemprojectbuyer.AfterSale.SoldProducts;
import com.example.finalsemprojectbuyer.BeforeSale.AdapterProductViewBeforeSale;
import com.example.finalsemprojectbuyer.Data.ProductDataBeforeSale;
import com.example.finalsemprojectbuyer.Data.QueryStringData;
import com.example.finalsemprojectbuyer.sales.SortActionDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class SalesActivity extends AppCompatActivity implements SortActionDialog.SortAction
{
    final ArrayList<ProductDataBeforeSale> products = new ArrayList<>();
    Button btn_purchased_products, btn_sort;
    TextView tv_empty_list;
    RecyclerView rv;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);
        btn_purchased_products = findViewById(R.id.btn_purchased_products);//new product for sale
        btn_sort = findViewById(R.id.btn_sort);//
        tv_empty_list = findViewById(R.id.tv_empty_list);
        rv = findViewById(R.id.RVdashBoard);
        loadProductsDynamically();
        Log.e("hello", "ok4");
        tv_empty_list.setVisibility(View.VISIBLE);
        btn_purchased_products.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(getApplicationContext(), SoldProducts.class));
            }
        });
        btn_sort.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                openDialog();
            }
        });
        Log.e("hello", "ok5");
    }

    private void openDialog()
    {
        SortActionDialog sortActionDialog = new SortActionDialog();
        sortActionDialog.show(getSupportFragmentManager(), "Apply filters");
    }

    private void loadProductsDynamically()
    {
        firebaseAuth = FirebaseAuth.getInstance();
        CollectionReference db = FirebaseFirestore.getInstance().collection("products");
        db.addSnapshotListener(new EventListener<QuerySnapshot>()
        {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e)
            {
                if (queryDocumentSnapshots != null)
                {
                    tv_empty_list.setVisibility(View.INVISIBLE);
                    for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments())
                    {
                        ProductDataBeforeSale productData = snapshot.toObject(ProductDataBeforeSale.class);
                        products.add(productData);
                    }
                    rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    rv.setAdapter(new AdapterProductViewBeforeSale(products));
                }
            }
        });
    }

    @Override
    public void order(String action, Query.Direction order, String productCompany, String productCategories, String productUsageDuration, String productCondition, boolean isAdvanceOptionIsApplied)
    {
        if (isAdvanceOptionIsApplied)
        {
            loadProductsDynamicallyWithAdvanceQuery(action, order, productCompany, productCategories, productUsageDuration, productCondition);
        } else
        {
            loadProductsDynamicallyWithSimpleQuery(action, order);
        }

    }

    private void loadProductsDynamicallyWithSimpleQuery(String action, Query.Direction order)
    {
        tv_empty_list.setVisibility(View.INVISIBLE);
        products.clear();
        CollectionReference db = FirebaseFirestore.getInstance().collection("products");
        db
                .orderBy(action, order)
                .addSnapshotListener(new EventListener<QuerySnapshot>()
                {
                    @Override
                    public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e)
                    {
                        if (queryDocumentSnapshots != null)
                        {
                            for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments())
                            {
                                ProductDataBeforeSale productData = snapshot.toObject(ProductDataBeforeSale.class);
                                products.add(productData);
                            }
                            rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            rv.setAdapter(new AdapterProductViewBeforeSale(products));
                        }
                    }
                });
    }

    private void loadProductsDynamicallyWithAdvanceQuery(String action, Query.Direction order, final String productCompany, final String productCategories, final String productUsageDuration, final String productCondition)
    {
        products.clear();
        CollectionReference db = FirebaseFirestore.getInstance().collection("products");
        db
                .orderBy(action, order)
                .whereEqualTo(QueryStringData.product_company, productCompany)
                .whereEqualTo(QueryStringData.product_categories, productCategories)
                .whereEqualTo(QueryStringData.product_usage_duration, productUsageDuration)
                .whereEqualTo(QueryStringData.product_condition, productCondition)
                .addSnapshotListener(new EventListener<QuerySnapshot>()
                {
                    @Override
                    public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e)
                    {
                        if (queryDocumentSnapshots != null)
                        {
                            tv_empty_list.setVisibility(View.INVISIBLE);
                            for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments())
                            {
                                ProductDataBeforeSale productData = snapshot.toObject(ProductDataBeforeSale.class);
                                products.add(productData);
                            }
                            rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            rv.setAdapter(new AdapterProductViewBeforeSale(products));
                        } else
                        {
                            String msg = productCompany + " " + productCategories + " " + productCondition + " " + productUsageDuration;
                            Toast.makeText(SalesActivity.this, msg, Toast.LENGTH_SHORT).show();
                            tv_empty_list.setVisibility(View.VISIBLE);
                            rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            rv.setAdapter(new AdapterProductViewBeforeSale(products));
                        }
                    }
                });
    }

}

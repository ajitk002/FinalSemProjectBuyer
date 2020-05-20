package com.example.finalsemprojectbuyer.sales;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.finalsemprojectbuyer.Data.QueryStringData;
import com.example.finalsemprojectbuyer.R;
import com.google.firebase.firestore.Query;

public class SortActionDialog extends AppCompatDialogFragment
{
    private Query.Direction order;
    private Button btn_advance_option;
    private LinearLayout ll_advance;
    private RadioGroup radio_group_action, radio_group_order;
    private String action;
    private RadioButton radioButton;
    private SortAction sortAction;
    private Spinner spinner_product_company, spinner_product_categories, spinner_product_condition, spinner_product_usage_duration;
    private Context context;
    private String product_categories, product_company, product_condition, product_usage_duration;
    private boolean isAdvanceOptionIsApplied = false;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState)
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        final View view = layoutInflater.inflate(R.layout.layout_sort_action_dialog, null);
        setUpViews(view);
        btn_advance_option.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (isAdvanceOptionIsApplied)
                {
                    btn_advance_option.setText("Show advance option");
                    ll_advance.setVisibility(View.INVISIBLE);
                    isAdvanceOptionIsApplied = false;
                } else
                {
                    btn_advance_option.setText("Hide advance option");
                    isAdvanceOptionIsApplied = true;
                    ll_advance.setVisibility(View.VISIBLE);
                }
            }
        });
        spinner_product_company.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                product_company = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });
        spinner_product_categories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                product_categories = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });
        spinner_product_condition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                product_condition = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });
        spinner_product_usage_duration.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                product_usage_duration = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });

        radio_group_order.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i)
            {
                switch (i)
                {
                    case R.id.rb_order_action_acceding:
                        radioButton = view.findViewById(R.id.rb_order_action_acceding);
                        order = Query.Direction.ASCENDING;
                        break;
                    case R.id.rb_order_action_descending:
                        radioButton = view.findViewById(R.id.rb_order_action_descending);
                        order = Query.Direction.DESCENDING;
                        break;
                }
            }
        });

        radio_group_action.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i)
            {
                switch (i)
                {
                    case R.id.rb_order_by_name:
                        radioButton = view.findViewById(R.id.rb_order_by_name);
                        action = QueryStringData.product_name;
                        break;
                    case R.id.rb_order_by_date:
                        radioButton = view.findViewById(R.id.rb_order_by_date);
                        action = QueryStringData.product_date_of_sale;
                        break;
                    case R.id.rb_order_by_price:
                        radioButton = view.findViewById(R.id.rb_order_by_price);
                        action = QueryStringData.product_price;
                        break;
                    case R.id.rb_product_near_to_me:
                        radioButton = view.findViewById(R.id.rb_product_near_to_me);
                        action = radioButton.getText().toString();
                        break;
                }
            }
        });

        builder
                .setView(view).setTitle("Sort")

                .setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {

                    }
                })

                .setPositiveButton("Apply", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        if (isAdvanceOptionIsApplied)
                        {
                            sortAction.order(action, order, product_company, product_categories, product_usage_duration, product_condition, true);
                        }
                        else
                        {
                            sortAction.order(action, order, product_company, product_categories, product_usage_duration, product_condition, false);
                        }
                    }
                });
        return builder.create();
    }


    private void setUpViews(View view)
    {
        radio_group_action = view.findViewById(R.id.rg_sort_action);
        radio_group_order = view.findViewById(R.id.rg_sort_action_acc_dcc);

        btn_advance_option = view.findViewById(R.id.btn_advance_option);
        ll_advance = view.findViewById(R.id.ll_advance);

        spinner_product_categories = view.findViewById(R.id.categories_spinner);
        ArrayAdapter<CharSequence> adapter_categories = ArrayAdapter.createFromResource(context, R.array.product_categories, android.R.layout.simple_spinner_item);
        adapter_categories.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_product_categories.setAdapter(adapter_categories);

        spinner_product_company = view.findViewById(R.id.company_spinner);
        ArrayAdapter<CharSequence> adapter_company = ArrayAdapter.createFromResource(context, R.array.product_company, android.R.layout.simple_spinner_item);
        adapter_company.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_product_company.setAdapter(adapter_company);

        spinner_product_condition = view.findViewById(R.id.condition_spinner);
        ArrayAdapter<CharSequence> adapter_condition = ArrayAdapter.createFromResource(context, R.array.product_condition, android.R.layout.simple_spinner_item);
        adapter_condition.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_product_condition.setAdapter(adapter_condition);

        spinner_product_usage_duration = view.findViewById(R.id.duration_spinner);
        ArrayAdapter<CharSequence> adapter_useage_duration = ArrayAdapter.createFromResource(context, R.array.duration_of_useage, android.R.layout.simple_spinner_item);
        adapter_useage_duration.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_product_usage_duration.setAdapter(adapter_useage_duration);


    }

    @Override
    public void onAttach(@NonNull Context context)
    {
        super.onAttach(context);
        this.context = context;
        sortAction = (SortAction) context;
    }

    public interface SortAction
    {
        public void order(String action, Query.Direction order, String productCompany, String productCategories, String productUsageDuration, String productCondition, boolean isAdvanceOptionIsApplied);
    }
}

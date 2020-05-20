package com.example.finalsemprojectbuyer.others;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalsemprojectbuyer.Data.Address_data;
import com.example.finalsemprojectbuyer.R;

import java.util.regex.Pattern;

public class Address extends AppCompatActivity
{
    Intent address_intent;
    private EditText
            et_address_line_one,
            et_address_line_two,
            et_city_dist,
            et_state_province,
            et_postal_code;
    private TextView
            et_address_line_oneError,
            et_address_line_twoError,
            et_city_distError,
            et_state_provinceError,
            et_postal_codeError;
    private boolean
            et_address_line_oneErrorFlag,
            et_address_line_twoErrorFlag,
            et_city_distErrorFlag,
            et_state_provinceErrorFlag,
            et_postal_codeErrorFlag;
    private Address_data
            address_data = new Address_data();
    private Button
            btn_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.address);
        setUpViews();
        et_address_line_one.setText("");
        et_address_line_two.setText("");
        et_city_dist.setText("");
        et_state_province.setText("");
        et_postal_code.setText("");
        Bundle extras = getIntent().getExtras();

        et_address_line_one.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void afterTextChanged(Editable editable)
            {
                if (et_address_line_one.getText().toString().isEmpty())
                {
                    et_address_line_oneError.setTextColor(Color.parseColor("#DD2C00"));
                    et_address_line_oneError.setText("add some more details about address");
                    et_address_line_oneErrorFlag = false;
                } else
                {
                    if (et_address_line_one.getText().toString().length() >= 15)
                    {
                        et_address_line_oneError.setTextColor(Color.parseColor("#00C853"));
                        et_address_line_oneError.setText("looks good");
                        et_address_line_oneErrorFlag = true;
                    }
                    else
                    {
                        et_address_line_oneError.setTextColor(Color.parseColor("#DD2C00"));
                        et_address_line_oneError.setText("add some more details about your address");
                        et_address_line_oneErrorFlag = false;
                    }

//                    Pattern userNamePattern = Pattern.compile("^\\w+@\\w+$");
//                    if (!userNamePattern.matcher(et_upi_id.getText().toString().trim()).matches())
//                    {
//                        is_flag_error_et_product_description = false;
//                        error_et_product_description.setTextColor(Color.parseColor("#DD2C00"));
//                        error_et_product_description.setText("invalid name");
//                    }
                }
            }
        });
        et_address_line_two.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void afterTextChanged(Editable editable)
            {
                if (et_address_line_two.getText().toString().isEmpty())
                {
                    et_address_line_twoError.setTextColor(Color.parseColor("#DD2C00"));
                    et_address_line_twoError.setText("add some more details about your address");
                    et_address_line_twoErrorFlag = false;
                }
                else
                {
                    if (et_address_line_two.getText().toString().length() >= 15)
                    {
                        et_address_line_twoError.setTextColor(Color.parseColor("#00C853"));
                        et_address_line_twoError.setText("looks good");
                        et_address_line_twoErrorFlag = true;
                    } else
                    {
                        et_address_line_twoError.setTextColor(Color.parseColor("#DD2C00"));
                        et_address_line_twoError.setText("add some more details about your address");
                        et_address_line_twoErrorFlag = false;
                    }

//                    Pattern userNamePattern = Pattern.compile("^\\w+@\\w+$");
//                    if (!userNamePattern.matcher(et_upi_id.getText().toString().trim()).matches())
//                    {
//                        is_flag_error_et_product_description = false;
//                        error_et_product_description.setTextColor(Color.parseColor("#DD2C00"));
//                        error_et_product_description.setText("invalid name");
//                    }
                }
            }
        });
        et_postal_code.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void afterTextChanged(Editable editable)
            {
                if (et_postal_code.getText().toString().isEmpty())
                {
                    et_postal_codeError.setTextColor(Color.parseColor("#DD2C00"));
                    et_postal_codeError.setText("Enter your postal code");
                    et_postal_codeErrorFlag = false;
                } else
                {
                    if (et_postal_code.getText().toString().length() == 6)
                    {
                        et_postal_codeError.setTextColor(Color.parseColor("#00C853"));
                        et_postal_codeError.setText("looks good");
                        et_postal_codeErrorFlag = true;
                    }
                    Pattern userNamePattern = Pattern.compile("^\\d{6}");
                    if (!userNamePattern.matcher(et_postal_code.getText().toString().trim()).matches())
                    {
                        et_postal_codeErrorFlag = false;
                        et_postal_codeError.setTextColor(Color.parseColor("#DD2C00"));
                        et_postal_codeError.setText("invalid postal code");
                    }
                }
            }
        });
        et_state_province.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void afterTextChanged(Editable editable)
            {
                if (et_state_province.getText().toString().isEmpty())
                {
                    et_state_provinceError.setTextColor(Color.parseColor("#DD2C00"));
                    et_state_provinceError.setText("add some more details about your product");
                    et_state_provinceErrorFlag = false;
                } else
                {
                    if (et_state_province.getText().toString().length() >= 3)
                    {
                        et_state_provinceError.setTextColor(Color.parseColor("#00C853"));
                        et_state_provinceError.setText("looks good");
                        et_state_provinceErrorFlag = true;
                    } else
                    {
                        et_state_provinceError.setTextColor(Color.parseColor("#DD2C00"));
                        et_state_provinceError.setText("enter proper state name");
                        et_state_provinceErrorFlag = false;
                    }

//                    Pattern userNamePattern = Pattern.compile("^\\w+@\\w+$");
//                    if (!userNamePattern.matcher(et_upi_id.getText().toString().trim()).matches())
//                    {
//                        is_flag_error_et_product_description = false;
//                        error_et_product_description.setTextColor(Color.parseColor("#DD2C00"));
//                        error_et_product_description.setText("invalid name");
//                    }
                }
            }
        });
        et_city_dist.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                if (et_city_dist.getText().toString().isEmpty())
                {
                    et_city_distError.setTextColor(Color.parseColor("#DD2C00"));
                    et_city_distError.setText("add some more details about your product");
                    et_city_distErrorFlag = false;
                } else
                {
                    if (et_city_dist.getText().toString().length() >= 5)
                    {
                        et_city_distError.setTextColor(Color.parseColor("#00C853"));
                        et_city_distError.setText("looks good");
                        et_city_distErrorFlag = true;
                    }
                    else
                    {
                        et_city_distError.setTextColor(Color.parseColor("#DD2C00"));
                        et_city_distError.setText("Enter proper district name");
                        et_city_distErrorFlag = false;
                    }

//                    Pattern userNamePattern = Pattern.compile("^\\w+@\\w+$");
//                    if (!userNamePattern.matcher(et_upi_id.getText().toString().trim()).matches())
//                    {
//                        is_flag_error_et_product_description = false;
//                        error_et_product_description.setTextColor(Color.parseColor("#DD2C00"));
//                        error_et_product_description.setText("invalid name");
//                    }
                }
            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (validate())
                {
                    address_data.setAddress_line_one(et_address_line_one.getText().toString());
                    address_data.setAddress_line_two(et_address_line_two.getText().toString());
                    address_data.setCity_dist(et_city_dist.getText().toString());
                    address_data.setState_province(et_state_province.getText().toString());
                    address_data.setPostal_code(et_postal_code.getText().toString());
                    address_intent = new Intent();
                    address_intent.putExtra("address_data", address_data);
                    setResult(RESULT_OK, address_intent);
                    finish();
                }
            }
        });
        if (extras.getSerializable("address_data") != null)
        {
            address_data = (Address_data) extras.getSerializable("address_data");
            et_address_line_one.setText(address_data.getAddress_line_one());
            et_address_line_two.setText(address_data.getAddress_line_two());
            et_city_dist.setText(address_data.getCity_dist());
            et_state_province.setText(address_data.getState_province());
            et_postal_code.setText(address_data.getPostal_code());
        }
    }

    private boolean validate()
    {
        if (et_city_dist.getText().toString().isEmpty())
        {
            return false;
        }
        if (et_address_line_two.getText().toString().isEmpty())
        {
            return false;
        }
        if (et_address_line_two.getText().toString().isEmpty())
        {
            return false;
        }
        if (et_state_province.getText().toString().isEmpty())
        {
            return false;
        }
        if (et_postal_code.getText().toString().isEmpty())
        {
            return false;
        }
        if (!et_address_line_oneErrorFlag)
        {
            return false;
        }
        if (!et_address_line_twoErrorFlag)
        {
            return false;
        }
        if (!et_city_distErrorFlag)
        {
            return false;
        }
        if (!et_postal_codeErrorFlag)
        {
            return false;
        }
        if (!et_state_provinceErrorFlag)
        {
            return false;
        }
        return true;
    }

    private void setUpViews()
    {
        et_address_line_one = findViewById(R.id.etAddressLineOne);
        et_address_line_two = findViewById(R.id.etAddressLineTwo);
        et_city_dist = findViewById(R.id.etCityOrDistrict);
        et_state_province = findViewById(R.id.etStateOrProvince);
        et_postal_code = findViewById(R.id.etPostalCode);
        et_address_line_oneError = findViewById(R.id.etAddressLineOneError);
        et_address_line_twoError = findViewById(R.id.etAddressLineTwoError);
        et_city_distError = findViewById(R.id.etCityOrDistrictError);
        et_state_provinceError = findViewById(R.id.etStateOrProvinceError);
        et_postal_codeError = findViewById(R.id.etPostalCodeError);
        btn_submit = findViewById(R.id.btnSubmit);
    }
}

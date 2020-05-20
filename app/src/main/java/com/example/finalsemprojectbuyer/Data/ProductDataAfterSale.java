package com.example.finalsemprojectbuyer.Data;

import com.example.finalsemprojectbuyer.parameter.ParameterProductDataAfterSale;

import java.io.Serializable;

public class ProductDataAfterSale implements Serializable
{
    private String product_upi_id;
    private String product_ID;
    private String product_seller_ID;
    private String product_name;
    private String product_condition;
    private String product_usage_duration;
    private String product_company;
    private String product_categories;
    private String product_description;
    private String product_address;
    private String product_payment_mode;
    private String product_location_longitude;
    private String product_location_latitude;
    private String product_price;
    private String product_pick_up_key;
    private String product_picture_url;
    private String product_pick_up_date;
    private String product_date_of_purchase;
    private String product_buyer_id;
    private String product_date_of_sale;
    private String product_address_pin;

    public ProductDataAfterSale()
    {
    }

    public String getProduct_address_pin()
    {
        return product_address_pin;
    }

    public void setProduct_address_pin(String product_address_pin)
    {
        this.product_address_pin = product_address_pin;
    }

    public ProductDataAfterSale(ParameterProductDataAfterSale spdp)
    {
        product_ID = spdp.product_ID;
        product_seller_ID = spdp.Seller_ID;
        product_name = spdp.product_name;
        product_condition = spdp.product_condition;
        product_usage_duration = spdp.original_date_of_Purchase;
        product_company = spdp.product_company;
        product_categories = spdp.product_category;
        product_description = spdp.product_details;
        product_address = spdp.product_address;
        product_payment_mode = spdp.payment_type;
        product_location_longitude = spdp.longitude;
        product_location_latitude = spdp.latitude;
        product_price = spdp.product_price;
        product_pick_up_key = spdp.product_key;
        product_picture_url = spdp.product_picture_url;
        product_pick_up_date = spdp.pick_up_date;
        product_date_of_purchase = spdp.original_date_of_Purchase;
        product_buyer_id = spdp.buyer_ID;
        product_date_of_sale = spdp.date_of_sale;
        product_upi_id = spdp.product_upi_id;
        product_address_pin = spdp.product_address_pin;
    }

    public ProductDataAfterSale(String product_ID, String product_seller_ID, String product_name, String product_condition, String product_usage_duration, String product_company, String product_categories, String product_description, String product_address, String product_payment_mode, String product_location_longitude, String product_location_latitude, String product_price, String product_pick_up_key, String product_picture_url, String product_pick_up_date, String product_date_of_purchase, String product_buyer_id, String product_date_of_sale, String product_address_pin)
    {
        this.product_ID = product_ID;
        this.product_seller_ID = product_seller_ID;
        this.product_name = product_name;
        this.product_condition = product_condition;
        this.product_usage_duration = product_usage_duration;
        this.product_company = product_company;
        this.product_categories = product_categories;
        this.product_description = product_description;
        this.product_address = product_address;
        this.product_payment_mode = product_payment_mode;
        this.product_location_longitude = product_location_longitude;
        this.product_location_latitude = product_location_latitude;
        this.product_price = product_price;
        this.product_pick_up_key = product_pick_up_key;
        this.product_picture_url = product_picture_url;
        this.product_pick_up_date = product_pick_up_date;
        this.product_date_of_purchase = product_date_of_purchase;
        this.product_buyer_id = product_buyer_id;
        this.product_date_of_sale = product_date_of_sale;
        this.product_address_pin =product_address_pin;
    }

    public String getProduct_upi_id()
    {
        return product_upi_id;
    }

    public void setProduct_upi_id(String product_upi_id)
    {
        this.product_upi_id = product_upi_id;
    }

    public String getProduct_ID()
    {
        return product_ID;
    }

    public void setProduct_ID(String product_ID)
    {
        this.product_ID = product_ID;
    }

    public String getProduct_seller_ID()
    {
        return product_seller_ID;
    }

    public void setProduct_seller_ID(String product_seller_ID)
    {
        this.product_seller_ID = product_seller_ID;
    }

    public String getProduct_name()
    {
        return product_name;
    }

    public void setProduct_name(String product_name)
    {
        this.product_name = product_name;
    }

    public String getProduct_condition()
    {
        return product_condition;
    }

    public void setProduct_condition(String product_condition)
    {
        this.product_condition = product_condition;
    }

    public String getProduct_usage_duration()
    {
        return product_usage_duration;
    }

    public void setProduct_usage_duration(String product_usage_duration)
    {
        this.product_usage_duration = product_usage_duration;
    }

    public String getProduct_company()
    {
        return product_company;
    }

    public void setProduct_company(String product_company)
    {
        this.product_company = product_company;
    }

    public String getProduct_categories()
    {
        return product_categories;
    }

    public void setProduct_categories(String product_categories)
    {
        this.product_categories = product_categories;
    }

    public String getProduct_description()
    {
        return product_description;
    }

    public void setProduct_description(String product_description)
    {
        this.product_description = product_description;
    }

    public String getProduct_address()
    {
        return product_address;
    }

    public void setProduct_address(String product_address)
    {
        this.product_address = product_address;
    }

    public String getProduct_payment_mode()
    {
        return product_payment_mode;
    }

    public void setProduct_payment_mode(String product_payment_mode)
    {
        this.product_payment_mode = product_payment_mode;
    }

    public String getProduct_location_longitude()
    {
        return product_location_longitude;
    }

    public void setProduct_location_longitude(String product_location_longitude)
    {
        this.product_location_longitude = product_location_longitude;
    }

    public String getProduct_location_latitude()
    {
        return product_location_latitude;
    }

    public void setProduct_location_latitude(String product_location_latitude)
    {
        this.product_location_latitude = product_location_latitude;
    }

    public String getProduct_price()
    {
        return product_price;
    }

    public void setProduct_price(String product_price)
    {
        this.product_price = product_price;
    }

    public String getProduct_pick_up_key()
    {
        return product_pick_up_key;
    }

    public void setProduct_pick_up_key(String product_pick_up_key)
    {
        this.product_pick_up_key = product_pick_up_key;
    }

    public String getProduct_picture_url()
    {
        return product_picture_url;
    }

    public void setProduct_picture_url(String product_picture_url)
    {
        this.product_picture_url = product_picture_url;
    }

    public String getProduct_pick_up_date()
    {
        return product_pick_up_date;
    }

    public void setProduct_pick_up_date(String product_pick_up_date)
    {
        this.product_pick_up_date = product_pick_up_date;
    }

    public String getProduct_date_of_purchase()
    {
        return product_date_of_purchase;
    }

    public void setProduct_date_of_purchase(String product_date_of_purchase)
    {
        this.product_date_of_purchase = product_date_of_purchase;
    }

    public String getProduct_buyer_id()
    {
        return product_buyer_id;
    }

    public void setProduct_buyer_id(String product_buyer_id)
    {
        this.product_buyer_id = product_buyer_id;
    }

    public String getProduct_date_of_sale()
    {
        return product_date_of_sale;
    }

    public void setProduct_date_of_sale(String product_date_of_sale)
    {
        this.product_date_of_sale = product_date_of_sale;
    }
}

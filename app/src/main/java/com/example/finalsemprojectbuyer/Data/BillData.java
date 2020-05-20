package com.example.finalsemprojectbuyer.Data;

public class BillData
{
    public String seller_name;
    public String seller_number;
    public String seller_email;
    public String seller_id;

    public String buyer_name;
    public String buyer_number;
    public String buyer_email;
    public String buyer_id;

    public String product_name;
    public String product_qty;
    public String product_gst;
    public String product_price;
    public String total;

    public BillData()
    {
    }

    public BillData(String seller_name, String seller_number, String seller_email, String seller_id, String buyer_name, String buyer_number, String buyer_email, String buyer_id, String product_name, String product_qty, String product_gst, String product_price, String total)
    {
        this.seller_name = seller_name;
        this.seller_number = seller_number;
        this.seller_email = seller_email;
        this.seller_id = seller_id;
        this.buyer_name = buyer_name;
        this.buyer_number = buyer_number;
        this.buyer_email = buyer_email;
        this.buyer_id = buyer_id;
        this.product_name = product_name;
        this.product_qty = product_qty;
        this.product_gst = product_gst;
        this.product_price = product_price;
        this.total = total;
    }

    public String getSeller_name()
    {
        return seller_name;
    }

    public void setSeller_name(String seller_name)
    {
        this.seller_name = seller_name;
    }

    public String getSeller_number()
    {
        return seller_number;
    }

    public void setSeller_number(String seller_number)
    {
        this.seller_number = seller_number;
    }

    public String getSeller_email()
    {
        return seller_email;
    }

    public void setSeller_email(String seller_email)
    {
        this.seller_email = seller_email;
    }

    public String getSeller_id()
    {
        return seller_id;
    }

    public void setSeller_id(String seller_id)
    {
        this.seller_id = seller_id;
    }

    public String getBuyer_name()
    {
        return buyer_name;
    }

    public void setBuyer_name(String buyer_name)
    {
        this.buyer_name = buyer_name;
    }

    public String getBuyer_number()
    {
        return buyer_number;
    }

    public void setBuyer_number(String buyer_number)
    {
        this.buyer_number = buyer_number;
    }

    public String getBuyer_email()
    {
        return buyer_email;
    }

    public void setBuyer_email(String buyer_email)
    {
        this.buyer_email = buyer_email;
    }

    public String getBuyer_id()
    {
        return buyer_id;
    }

    public void setBuyer_id(String buyer_id)
    {
        this.buyer_id = buyer_id;
    }

    public String getProduct_name()
    {
        return product_name;
    }

    public void setProduct_name(String product_name)
    {
        this.product_name = product_name;
    }

    public String getProduct_qty()
    {
        return product_qty;
    }

    public void setProduct_qty(String product_qty)
    {
        this.product_qty = product_qty;
    }

    public String getProduct_gst()
    {
        return product_gst;
    }

    public void setProduct_gst(String product_gst)
    {
        this.product_gst = product_gst;
    }

    public String getProduct_price()
    {
        return product_price;
    }

    public void setProduct_price(String product_price)
    {
        this.product_price = product_price;
    }

    public String getTotal()
    {
        return total;
    }

    public void setTotal(String total)
    {
        this.total = total;
    }
}

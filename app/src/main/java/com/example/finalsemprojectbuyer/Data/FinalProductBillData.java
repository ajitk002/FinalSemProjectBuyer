package com.example.finalsemprojectbuyer.Data;

import java.io.Serializable;

public class FinalProductBillData implements Serializable
{
    private String ProductID;
    private String SellerID;
    private String ProductName;
    private String Description;
    private String Categories;
    private String Company;
    private String DateOfPurchase;
    private String DateOfSale;
    private String ProductCondition;
    private String url;
    private String ProductPrice;
    private String BuyerID;
    private String BoughtTime;
    private String pickUpDate;
    private String Key;

    public FinalProductBillData(
            String productID, String sellerID, String productName, String description,
            String categories, String company, String dateOfPurchase, String dateOfSale,
            String productCondition, String url, String productPrice, String buyerID,
            String boughtTime, String pickUpDate, String Key
    )
    {
        ProductID = productID;
        SellerID = sellerID;
        ProductName = productName;
        Description = description;
        Categories = categories;
        Company = company;
        DateOfPurchase = dateOfPurchase;
        DateOfSale = dateOfSale;
        ProductCondition = productCondition;
        this.url = url;
        ProductPrice = productPrice;
        BuyerID = buyerID;
        BoughtTime = boughtTime;
        this.pickUpDate = pickUpDate;
        this.Key = Key;
    }

    public FinalProductBillData()
    {

    }

    public String getKey()
    {
        return Key;
    }

    public void setKey(String key)
    {
        Key = key;
    }

    public String getProductID()
    {
        return ProductID;
    }

    public void setProductID(String productID)
    {
        ProductID = productID;
    }

    public String getSellerID()
    {
        return SellerID;
    }

    public void setSellerID(String sellerID)
    {
        SellerID = sellerID;
    }

    public String getProductName()
    {
        return ProductName;
    }

    public void setProductName(String productName)
    {
        ProductName = productName;
    }

    public String getDescription()
    {
        return Description;
    }

    public void setDescription(String description)
    {
        Description = description;
    }

    public String getCategories()
    {
        return Categories;
    }

    public void setCategories(String categories)
    {
        Categories = categories;
    }

    public String getCompany()
    {
        return Company;
    }

    public void setCompany(String company)
    {
        Company = company;
    }

    public String getDateOfPurchase()
    {
        return DateOfPurchase;
    }

    public void setDateOfPurchase(String dateOfPurchase)
    {
        DateOfPurchase = dateOfPurchase;
    }

    public String getDateOfSale()
    {
        return DateOfSale;
    }

    public void setDateOfSale(String dateOfSale)
    {
        DateOfSale = dateOfSale;
    }

    public String getProductCondition()
    {
        return ProductCondition;
    }

    public void setProductCondition(String productCondition)
    {
        ProductCondition = productCondition;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getProductPrice()
    {
        return ProductPrice;
    }

    public void setProductPrice(String productPrice)
    {
        ProductPrice = productPrice;
    }

    public String getBuyerID()
    {
        return BuyerID;
    }

    public void setBuyerID(String buyerID)
    {
        BuyerID = buyerID;
    }

    public String getBoughtTime()
    {
        return BoughtTime;
    }

    public void setBoughtTime(String boughtTime)
    {
        BoughtTime = boughtTime;
    }

    public String getPickUpDate()
    {
        return pickUpDate;
    }

    public void setPickUpDate(String pickUpDate)
    {
        this.pickUpDate = pickUpDate;
    }
}

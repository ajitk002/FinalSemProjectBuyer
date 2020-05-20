package com.example.finalsemprojectbuyer.Data;

import java.io.Serializable;

public class Address_data implements Serializable
{
    String address_line_one,
            address_line_two,
            city_dist,
            state_province,
            postal_code;

    public Address_data()
    {
    }

    public Address_data(String address_line_one, String address_line_two, String city_dist, String state_province, String postal_code)
    {
        this.address_line_one = address_line_one;
        this.address_line_two = address_line_two;
        this.city_dist = city_dist;
        this.state_province = state_province;
        this.postal_code = postal_code;
    }

    public String getAddress_line_one()
    {
        return address_line_one;
    }

    public void setAddress_line_one(String address_line_one)
    {
        this.address_line_one = address_line_one;
    }

    public String getAddress_line_two()
    {
        return address_line_two;
    }

    public void setAddress_line_two(String address_line_two)
    {
        this.address_line_two = address_line_two;
    }

    public String getCity_dist()
    {
        return city_dist;
    }

    public void setCity_dist(String city_dist)
    {
        this.city_dist = city_dist;
    }

    public String getState_province()
    {
        return state_province;
    }

    public void setState_province(String state_province)
    {
        this.state_province = state_province;
    }

    public String getPostal_code()
    {
        return postal_code;
    }

    public void setPostal_code(String postal_code)
    {
        this.postal_code = postal_code;
    }
}

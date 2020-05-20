package com.example.finalsemprojectbuyer.blogapp;

public class Blog_UserProfile
{
    public String user_name;
    public String user_id;
    public String user_phone_number;
    public String user_email_id;
    public String user_profile_pic_url;
    public String status;


    public Blog_UserProfile(Blog_ParameterUserProfile parameterUserProfile)
    {
        this.user_name = parameterUserProfile.user_name;
        this.user_id = parameterUserProfile.user_ID;
        this.user_phone_number = parameterUserProfile.user_phone_number;
        this.user_email_id = parameterUserProfile.user_email_id;
        this.user_profile_pic_url = parameterUserProfile.user_profile_Pic_Location;
    }

    public Blog_UserProfile()
    {

    }

    public Blog_UserProfile(String user_name, String user_id, String user_phone_number, String user_email_id, String user_profile_pic_url, String status)
    {
        this.user_name = user_name;
        this.user_id = user_id;
        this.user_phone_number = user_phone_number;
        this.user_email_id = user_email_id;
        this.user_profile_pic_url = user_profile_pic_url;
        this.status = status;
    }

    public String getUser_name()
    {
        return user_name;
    }

    public void setUser_name(String user_name)
    {
        this.user_name = user_name;
    }

    public String getUser_id()
    {
        return user_id;
    }

    public void setUser_id(String user_id)
    {
        this.user_id = user_id;
    }

    public String getUser_phone_number()
    {
        return user_phone_number;
    }

    public void setUser_phone_number(String user_phone_number)
    {
        this.user_phone_number = user_phone_number;
    }

    public String getUser_email_id()
    {
        return user_email_id;
    }

    public void setUser_email_id(String user_email_id)
    {
        this.user_email_id = user_email_id;
    }

    public String getUser_profile_pic_url()
    {
        return user_profile_pic_url;
    }

    public void setUser_profile_pic_url(String user_profile_pic_url)
    {
        this.user_profile_pic_url = user_profile_pic_url;
    }


    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }
}

package com.example.finalsemprojectbuyer.blogapp;

public class Blog_Comments
{

    private String message, user_id;
    private String  timestamp;

    public Blog_Comments()
    {

    }

    public Blog_Comments(String message, String user_id, String timestamp)
    {
        this.message = message;
        this.user_id = user_id;
        this.timestamp = timestamp;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public String getUser_id()
    {
        return user_id;
    }

    public void setUser_id(String user_id)
    {
        this.user_id = user_id;
    }

    public String getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp(String timestamp)
    {
        this.timestamp = timestamp;
    }
}

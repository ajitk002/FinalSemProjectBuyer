package com.example.finalsemprojectbuyer.blogapp;
public class Blog_BlogPost extends Blog_BlogPostId
{

    public String user_id;
    public String image_url;
    public String desc;
    public String post_id;
    public String timestamp;

    public Blog_BlogPost()
    {
    }

    public Blog_BlogPost(String user_id, String image_url, String desc,  String timestamp, String post_id)
    {
        this.user_id = user_id;
        this.image_url = image_url;
        this.desc = desc;
        this.timestamp = timestamp;
        this.post_id = post_id;
    }

    public String getUser_id()
    {
        return user_id;
    }

    public void setUser_id(String user_id)
    {
        this.user_id = user_id;
    }

    public String getImage_url()
    {
        return image_url;
    }

    public void setImage_url(String image_url)
    {
        this.image_url = image_url;
    }

    public String getDesc()
    {
        return desc;
    }

    public void setDesc(String desc)
    {
        this.desc = desc;
    }

    public String getPost_id()
    {
        return post_id;
    }

    public void setPost_id(String post_id)
    {
        this.post_id = post_id;
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

package com.example.finalsemprojectbuyer.blogapp;


import androidx.annotation.NonNull;

import com.google.firebase.firestore.Exclude;

public class Blog_BlogPostId
{

    @Exclude
    public String BlogPostId;

    public <T extends Blog_BlogPostId> T withId(@NonNull final String id)
    {
        this.BlogPostId = id;
        return (T) this;
    }

}

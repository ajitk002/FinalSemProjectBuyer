//package com.example.blogapp;
//
//
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.fragment.app.Fragment;
//
//
///**
// * A simple {@link Fragment} subclass.
// */
//public class NotificationFragment extends Fragment
//{
//
//
//    public NotificationFragment()
//    {
//        // Required empty public constructor
//    }
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
//    {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.blog_fragment_noti, container, false);
//    }
//
//}

package com.example.finalsemprojectbuyer.blogapp;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalsemprojectbuyer.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;


public class Blog_NotificationFragment extends Fragment
{

   public ref_listnere ref;
    private RecyclerView blog_list_view;
    private List<Blog_BlogPost> blog_list;

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private Blog_BlogRecyclerAdapter blogRecyclerAdapter;

    private DocumentSnapshot lastVisible;
    private Boolean isFirstPageFirstLoad = true;

    public Blog_NotificationFragment()
    {
        // Required empty public constructor
    }
    public Blog_NotificationFragment(ref_listnere ref)
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.blog_fragment_home, container, false);

        blog_list = new ArrayList<>();
        blog_list_view = view.findViewById(R.id.blog_list_view);

        firebaseAuth = FirebaseAuth.getInstance();
        ref = new ref_listnere()
        {
            @Override
            public void isOrderToRefresh(boolean isRefresh)
            {
                refresh();
            }
        };
        blogRecyclerAdapter = new Blog_BlogRecyclerAdapter(blog_list, true,ref);
        blog_list_view.setLayoutManager(new LinearLayoutManager(container.getContext()));
        blog_list_view.setAdapter(blogRecyclerAdapter);
        blog_list_view.setHasFixedSize(true);
        refresh();
        // Inflate the layout for this fragment
        return view;
    }

    private void refresh()
    {
        if (firebaseAuth.getCurrentUser() != null)
        {
            final ArrayList<Blog_BlogPost> blogs = new ArrayList<>();
            CollectionReference db = FirebaseFirestore.getInstance().collection("posts");
            db.addSnapshotListener(new EventListener<QuerySnapshot>()
            {

                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e)
                {
                    if (queryDocumentSnapshots != null)
                    {
                        for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments())
                        {
                            Blog_BlogPost blog = snapshot.toObject(Blog_BlogPost.class);
                            if (blog.getUser_id().trim().equals(FirebaseAuth.getInstance().getUid()))
                            {
                                blogs.add(blog);
                            }
                        }
                        blog_list_view.setAdapter(new Blog_BlogRecyclerAdapter(blogs, true, ref));
                    }
                }

            });
        }
    }

    public void loadMorePost()
    {

        if (firebaseAuth.getCurrentUser() != null)
        {
//                    .orderBy("timestamp", Query.Direction.DESCENDING)
            Query nextQuery = firebaseFirestore.collection("Posts")
                    .startAfter(lastVisible)
                    .limit(3);

            nextQuery.addSnapshotListener(getActivity(), new EventListener<QuerySnapshot>()
            {
                @Override
                public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e)
                {

                    if (!documentSnapshots.isEmpty())
                    {

                        lastVisible = documentSnapshots.getDocuments().get(documentSnapshots.size() - 1);
                        for (DocumentChange doc : documentSnapshots.getDocumentChanges())
                        {

                            if (doc.getType() == DocumentChange.Type.ADDED)
                            {

                                String blogPostId = doc.getDocument().getId();
                                Blog_BlogPost blogPost = doc.getDocument().toObject(Blog_BlogPost.class).withId(blogPostId);
                                blog_list.add(blogPost);
                                blogRecyclerAdapter.notifyDataSetChanged();
                            }

                        }
                    }

                }
            });

        }

    }




    public void upDateEditText(String msg)
    {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }
}

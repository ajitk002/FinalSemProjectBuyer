package com.example.finalsemprojectbuyer.blogapp;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalsemprojectbuyer.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class Blog_HomeFragment extends Fragment
{

    public ref_listnere ref;
    private RecyclerView blog_list_view;
    private List<Blog_BlogPost> blog_list;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private Blog_BlogRecyclerAdapter blogRecyclerAdapter;

    private DocumentSnapshot lastVisible;
    private Boolean isFirstPageFirstLoad = true;

    public Blog_HomeFragment()
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
//                refresh();
                loadPost();
            }
        };
        blog_list_view.setLayoutManager(new LinearLayoutManager(container.getContext()));
//        loadPost();


//        refresh();
        return view;
    }

    void loadPost()
    {
        blog_list.clear();
        final CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("posts");
        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>()
        {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e)
            {
                for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments())
                {
                    Toast.makeText(getContext(), "hello399", Toast.LENGTH_SHORT).show();
                    Blog_BlogPost blog_blogPost = snapshot.toObject(Blog_BlogPost.class);
                    blog_list.add(blog_blogPost);
                    load();
                }
            }
        });
    }

    private void load()
    {
        blogRecyclerAdapter = new Blog_BlogRecyclerAdapter(blog_list, false, ref);
        blog_list_view.setAdapter(blogRecyclerAdapter);
        blog_list_view.setHasFixedSize(true);
    }


//    public void refresh()
//    {
//        blogRecyclerAdapter= new Blog_BlogRecyclerAdapter(blog_list, false, ref);
//        blog_list_view.setAdapter(blogRecyclerAdapter);
//        blog_list_view.setHasFixedSize(true);
//        blog_list.clear();
//        if (firebaseAuth.getCurrentUser() != null)
//        {
//            firebaseFirestore = FirebaseFirestore.getInstance();
//            blog_list_view.addOnScrollListener(new RecyclerView.OnScrollListener()
//            {
//                @Override
//                public void onScrolled(RecyclerView recyclerView, int dx, int dy)
//                {
//                    super.onScrolled(recyclerView, dx, dy);
//                    Boolean reachedBottom = !recyclerView.canScrollVertically(1);
//                    if (reachedBottom)
//                    {
//                        loadMorePost();
//                    }
//                }
//            });
//
//            Query firstQuery = firebaseFirestore.collection("Posts").orderBy("timestamp", Query.Direction.DESCENDING).limit(3);
//            firstQuery.addSnapshotListener(new EventListener<QuerySnapshot>()
//            {
//                @Override
//                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e)
//                {
//                    if (!queryDocumentSnapshots.isEmpty())
//                    {
//
//                        if (isFirstPageFirstLoad)
//                        {
//                            lastVisible = queryDocumentSnapshots.getDocuments().get(queryDocumentSnapshots.size() - 1);
//                            blog_list.clear();
//                        }
//
//                        for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges())
//                        {
//                            if (doc.getType() == DocumentChange.Type.ADDED)
//                            {
//                                String blogPostId = doc.getDocument().getId();
//                                Blog_BlogPost blogPost = doc.getDocument().toObject(Blog_BlogPost.class).withId(blogPostId);
//                                if (isFirstPageFirstLoad)
//                                {
//                                    blog_list.add(blogPost);
//                                } else
//                                {
//                                    blog_list.add(0, blogPost);
//                                }
//                                blogRecyclerAdapter.notifyDataSetChanged();
//                            }
//                        }
//                        isFirstPageFirstLoad = false;
//                    }
//                }
//            });
//        }
//    }

//    public void loadMorePost()
//    {
//        if (firebaseAuth.getCurrentUser() != null)
//        {
//            Query nextQuery = firebaseFirestore.collection("Posts").startAfter(lastVisible).limit(3);
//            nextQuery.addSnapshotListener(getActivity(), new EventListener<QuerySnapshot>()
//            {
//                @Override
//                public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e)
//                {
//                    if (!documentSnapshots.isEmpty())
//                    {
//                        lastVisible = documentSnapshots.getDocuments().get(documentSnapshots.size() - 1);
//                        for (DocumentChange doc : documentSnapshots.getDocumentChanges())
//                        {
//                            if (doc.getType() == DocumentChange.Type.ADDED)
//                            {
//
//                                String blogPostId = doc.getDocument().getId();
//                                Blog_BlogPost blogPost = doc.getDocument().toObject(Blog_BlogPost.class).withId(blogPostId);
//                                blog_list.add(blogPost);
//
//                                blogRecyclerAdapter.notifyDataSetChanged();
//                            }
//                        }
//                    }
//                }
//            });
//        }
//    }

    @Override
    public void onResume()
    {
        Toast.makeText(getContext(), "onResume", Toast.LENGTH_SHORT).show();
        super.onResume();
        loadPost();
    }
}

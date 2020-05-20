package com.example.finalsemprojectbuyer.Chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalsemprojectbuyer.Data.ChatList;
import com.example.finalsemprojectbuyer.Data.UserProfile;
import com.example.finalsemprojectbuyer.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatFragment extends Fragment
{
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    List<ChatList> user_list;
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<UserProfile> recent_users;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        recyclerView = view.findViewById(R.id.rv2_recent_chat);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        user_list = new ArrayList<>();
//        databaseReference = FirebaseDatabase.getInstance().getReference().child("child").child("chats");
//        databaseReference.addValueEventListener(new ValueEventListener()
//        {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
//            {
//                user_list.clear();
//                for(DataSnapshot snapshot : dataSnapshot.getChildren())
//                {
//                    IndividualMessage chatClass = snapshot.getValue(IndividualMessage.class);
//                    if(chatClass.getSender().equals(firebaseUser.getUid()))
//                    {
//                        user_list.add(chatClass.getReceiver());
//
//                    }
//                    if(chatClass.getReceiver().equals(firebaseUser.getUid()))
//                    {
//                        user_list.add(chatClass.getSender());
//
//                    }
//                }
//                readChat();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError)
//            {
//
//            }
//        });
        databaseReference = FirebaseDatabase.getInstance().getReference().child("ChatList").child(firebaseUser.getUid());
        databaseReference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                user_list.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    ChatList chatList = snapshot.getValue(ChatList.class);
                    user_list.add(chatList);
                }
                chatlist();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });
        return view;
    }

    private void chatlist()
    {
        recent_users = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("user");
        databaseReference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                recent_users.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    UserProfile userProfile = snapshot.getValue(UserProfile.class);
                    for (ChatList chatList : user_list)
                    {
                        if (userProfile.getUser_id().equals(chatList.getId()))
                        {
                            recent_users.add(userProfile);
                        }
                    }
                }
                userAdapter = new UserAdapter(getContext(), recent_users, true);
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });

    }

//    private void readChat()
//    {
//        recent_users = new ArrayList<>();
//        final ArrayList muser2 = new ArrayList<>();
//        databaseReference = FirebaseDatabase.getInstance().getReference("user");
//        databaseReference.addValueEventListener(new ValueEventListener()
//        {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
//            {
//                recent_users.clear();
//                muser2.clear();
//                for(DataSnapshot snapshot: dataSnapshot.getChildren())
//                {
//                    UserProfile user = snapshot.getValue(UserProfile.class);
//                    for(String id :user_list)
//                    {
//                        if(user.getUser_id().equals(id))
//                        {
//                            if(recent_users.size()!=0)
//                            {
//                                for(UserProfile user1: recent_users)
//                                {
//                                    if(!user.getUser_id().equals(user1.getUser_id()))
//                                    {
//                                        muser2.add(user);
//                                    }
//                                }
//                            }
//                            else
//                            {
//                                muser2.add(user);
//                            }
//                        }
//                    }
//                    recent_users.clear();
//                    recent_users.addAll(muser2);
//                }
//                userAdapter = new UserAdapter(getContext(), recent_users,true);
//                recyclerView.setAdapter(userAdapter);
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError)
//            {
//
//            }
//        });
//    }

}

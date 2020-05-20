package com.example.finalsemprojectbuyer.Chat;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalsemprojectbuyer.Data.UserProfile;
import com.example.finalsemprojectbuyer.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserFragment extends Fragment
{
    EditText search;
    private List mUser;
    private RecyclerView rv;
    private UserAdapter userAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        rv = view.findViewById(R.id.UF_rv);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        mUser = new ArrayList<>();
        readUser();
        search = view.findViewById(R.id.search);
        search.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                searchUsers(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable)
            {

            }
        });
        return view;
    }

    private void searchUsers(String s)
    {
        final String d = s;
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        Query query = FirebaseDatabase.getInstance().getReference("user").orderByChild("user_name").startAt(s).endAt(s + "\uf0ff");
        query.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                mUser.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    UserProfile userProfile = snapshot.getValue(UserProfile.class);
                    assert userProfile != null;
                    assert firebaseUser != null;
                    if (!(userProfile.getUser_id().equals(firebaseUser.getUid())))
                    {
                        mUser.add(userProfile);
                    }
                }
                userAdapter = new UserAdapter(getContext(), mUser, false);
                rv.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });
    }

    private void readUser()
    {
        mUser.clear();
        final String firebaseUser;
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Log.e("8888", firebaseUser);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("user");
        databaseReference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (search.getText().toString().isEmpty())
                {
                    mUser.clear();
                    for (DataSnapshot Snapshot : dataSnapshot.getChildren())
                    {
                        UserProfile userProfile = Snapshot.getValue(UserProfile.class);
                        if (FirebaseAuth.getInstance().getCurrentUser().getUid().equals(userProfile.getUser_id()))
                        {

                        } else
                        {
                            mUser.add(Snapshot.getValue(UserProfile.class));
                        }
                    }
                    userAdapter = new UserAdapter(getContext(), mUser, false);
                    rv.setAdapter(userAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });
    }
}

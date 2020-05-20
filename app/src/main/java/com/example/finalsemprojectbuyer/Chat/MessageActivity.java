package com.example.finalsemprojectbuyer.Chat;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.finalsemprojectbuyer.Data.IndividualMessage;
import com.example.finalsemprojectbuyer.Data.SubscriptionData;
import com.example.finalsemprojectbuyer.Data.UserProfile;
import com.example.finalsemprojectbuyer.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageActivity extends AppCompatActivity
{
    CircleImageView profiileImageView;
    TextView userName, back;
    String userID;
    FirebaseUser firebaseUser;
    Intent intent;
    DatabaseReference databaseReference;
    EditText text_message;
    CircleImageView send_message;
    Boolean notify = false;
    ValueEventListener eventListener;
    List<IndividualMessage> mChatClass;
    RecyclerView recyclerView;
    String url = "https://fcm.googleapis.com/fcm/send";
    private RequestQueue mRequeue;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        setUpViews();
        back.setText("<");
        Toolbar toolbar = findViewById(R.id.AM_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        mRequeue = Volley.newRequestQueue(getApplicationContext());
        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(MessageActivity.this, ChatHomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });

        FirebaseMessaging.getInstance().subscribeToTopic("/topics/" + FirebaseAuth.getInstance().getUid());

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        intent = getIntent();
        userID = intent.getStringExtra("User ID");
        send_message.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                notify = true;
                String message = text_message.getText().toString();
                text_message.setText("");
                if (!message.equals(""))
                {
                    SendMessage(message, firebaseUser.getUid(), userID);
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                onBackPressed();
            }
        });
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//        databaseReference = FirebaseDatabase.getInstance().getReference("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        databaseReference = FirebaseDatabase.getInstance().getReference("user").child(userID);
        databaseReference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                Picasso.get().load(userProfile.getUser_profile_pic_url()).into(profiileImageView);
                userName.setText(userProfile.getUser_name());
                readMessage(firebaseUser.getUid(), userID, userProfile.user_profile_pic_url);

                //hello
                FirebaseStorage firebaseStorage;
                firebaseStorage = FirebaseStorage.getInstance();
                StorageReference storageReference = firebaseStorage.getReference();
                storageReference.child(userID).child("Images").child("Profile Pic").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>()
                {
                    @Override
                    public void onSuccess(Uri uri)
                    {
                        Picasso.get().load(uri).into(profiileImageView);
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });
        seenMessage(userID);
    }

    private void setUpViews()
    {
        profiileImageView = findViewById(R.id.AM_profile_image);
        userName = findViewById(R.id.AM_username);
        back = findViewById(R.id.back);
        send_message = findViewById(R.id.AM_btn_send);
        text_message = findViewById(R.id.AM_text_send);
        recyclerView = findViewById(R.id.AM_recycler_view);
    }

    private void seenMessage(final String userID)
    {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("child").child("chats");
        eventListener = databaseReference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    IndividualMessage chat = snapshot.getValue(IndividualMessage.class);
                    if (chat.getReceiver().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            && chat.getSender().equals(userID))
                    {
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("isSeen", true);
                        snapshot.getRef().updateChildren(hashMap);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });
    }

    private void SendMessage(String message, String sender, final String receiver)
    {
        DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("child");
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);
        hashMap.put("isSeen", false);
        databaseReference1.child("chats").push().setValue(hashMap);

        final DatabaseReference chatRef = FirebaseDatabase.getInstance().getReference("ChatList").child(firebaseUser.getUid()).child(userID);
        chatRef.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (!dataSnapshot.exists())
                {
                    chatRef.child("id").setValue(userID);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });
        final String msg = message;
        databaseReference = FirebaseDatabase.getInstance().getReference("user").child(firebaseUser.getUid());
        databaseReference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                if (notify)
//                    sendNotification(receiver);
                    sendNotification(receiver, userProfile, msg);
                notify = false;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
            }
        });
    }


    private void sendNotification(String receiver_id, UserProfile sender, String message)
    {
        JSONObject json = new JSONObject();
        try
        {
            json.put("to", "/topics/" + receiver_id);
            JSONObject notificationObj = new JSONObject();
            notificationObj.put("title", sender.getUser_name());
            notificationObj.put("body", message);

            JSONObject extraData = new JSONObject();
            extraData.put("user_id", sender.getUser_id());
            extraData.put("mType", "M");


            json.put("notification", notificationObj);
            json.put("data", extraData);

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url,
                    json,
                    new Response.Listener<JSONObject>()
                    {
                        @Override
                        public void onResponse(JSONObject response)
                        {
                            Toast.makeText(MessageActivity.this, "onResponse", Toast.LENGTH_SHORT).show();
                            Log.d("MUR", "onResponse: ");
                        }
                    }, new Response.ErrorListener()
            {
                @Override
                public void onErrorResponse(VolleyError error)
                {
                    Toast.makeText(MessageActivity.this, "onFailure", Toast.LENGTH_SHORT).show();
                    Log.d("MUR", "onError: " + error.networkResponse);
                }
            }
            )
            {

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError
                {
                    Map<String, String> header = new HashMap<>();
                    header.put("content-type", "application/json");
                    header.put("authorization", "key=AAAA1ZtthRU:APA91bHgP2WP6ebUnvwM9oVAayzyYEdf3m-cEyUUxmOb6-sYZdsB5c7sU3d6HVENadgwVUNEzD4ImAWhLUKkBxT12BIk7co88jeIvBa3t5rn_hipftdDfzuBlEbB8pDg380UwKSSosZX");
                    return header;
                }
            };
            mRequeue.add(request);
        } catch (JSONException e)

        {
            e.printStackTrace();
        }
    }
    private void readMessage(final String myId, final String userId, final String imageuri)
    {
        mChatClass = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("child").child("chats");
        databaseReference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                mChatClass.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    IndividualMessage chatClass = snapshot.getValue(IndividualMessage.class);
                    if (chatClass.getReceiver().equals(myId) && chatClass.getSender().equals(userId) ||
                            chatClass.getReceiver().equals(userId) && chatClass.getSender().equals(myId))
                    {
                        mChatClass.add(chatClass);
                    }
                    MessageAdapter messageAdapter = new MessageAdapter(MessageActivity.this, mChatClass, imageuri);
                    recyclerView.setAdapter(messageAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
            }
        });
    }

    public void status(String status)
    {
        databaseReference = FirebaseDatabase.getInstance().getReference("user").child(FirebaseAuth.getInstance().getUid());
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("status", status);
        databaseReference.updateChildren(hashMap);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        status("online");
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        databaseReference.removeEventListener(eventListener);
        status("offline");
    }
}

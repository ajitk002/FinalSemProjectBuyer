package com.example.finalsemprojectbuyer.Notification;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessagingService;

import java.io.IOException;

public class MyFirebaseService extends FirebaseMessagingService
{
    String refreshToken = null;
    @Override
    public void onNewToken(@NonNull String s)
    {
        super.onNewToken(s);
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        try
        {
            refreshToken = FirebaseInstanceId.getInstance().getToken(FirebaseAuth.getInstance().getCurrentUser().getUid(),"923856209368");
//            FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>()
//            {
//                @Override
//                public void onSuccess(InstanceIdResult instanceIdResult)
//                {
//                    Toast.makeText(MyFirebaseService.this, "bro", Toast.LENGTH_SHORT).show();
//                    refreshToken = instanceIdResult.getToken();
//                }
//            });
//            FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListenr(getApplicationContext(),new OnSuccessListener<InstanceIdResult>() {
//                @Override
//                public void onSuccess(InstanceIdResult instanceIdResult) {
//                    String newToken = instanceIdResult.getToken();
//                }
//            });
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        if(firebaseUser!= null)
        {
            updateToken(refreshToken);
        }
    }
    private void updateToken(String refreshToken)
    {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Token");
        Token token = new Token(refreshToken);
        databaseReference.child(firebaseUser.getUid()).setValue(token);
    }
}

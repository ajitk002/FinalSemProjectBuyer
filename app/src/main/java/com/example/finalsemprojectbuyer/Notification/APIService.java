package com.example.finalsemprojectbuyer.Notification;
import com.example.finalsemprojectbuyer.Notification.MyResponse;
import com.example.finalsemprojectbuyer.Notification.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService
{
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAA1xofBdg:APA91bH9YgbaJq6jlC1YPIKK-ove_lkA8Utj4pGci8qLOgnc5dlE4Z-TH69HIWFj5dQ5FfG9lYRmexVzcUcLhzNBdqHjAceYku4udgNkobmp-Y04oUQtGHY3AbhwOT7NY7Rvo2vm-Ed_"
//                    "Authorization:key=AAAA1xofBdg:APA91bFm8kmCok9rvPNDGfAkNgAR5wGrqE-eatJWe-3zWrSZ2NtC8v1FDMNvkKMIi97f_gw1YIBel0k5T23Y-F5kp7KmiKurxHf4nVQTCgvLsT-ZzW6dHMzztfZ30CHYXdlyvm-31ujF"
            }
    )
    @POST("fcm/send")
    Call<MyResponse>sendNotification(@Body Sender body);
}

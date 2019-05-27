package com.example.toshiba.myapplication.Remote;

import com.example.toshiba.myapplication.Model.DataMessage;
import com.example.toshiba.myapplication.Model.MyResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {


    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAJpzteCs:APA91bEJ_Nl0PMvsOtVSbd8QT65X99-8GVQAcmyPe6lPOOo4YALEXZYxV_tKDhCJQitM-flPGrbqmpe09E4FkKPE9R_wn6hgy425HWuKtFHzTQ-IMWutXxeVKWpi_OGKZRFy-E7Y1Dc9"
            }
    )
    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body DataMessage body);

}

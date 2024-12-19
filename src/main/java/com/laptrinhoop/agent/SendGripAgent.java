package com.laptrinhoop.agent;

import com.laptrinhoop.dto.SendGripDto;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

import javax.xml.ws.Response;

public interface SendGripAgent {

    @POST("mail/send")
    Call<ResponseBody> sendEmail(@Header("Authorization") String token, @Body SendGripDto.SendGripRequest request);




}

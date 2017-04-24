package edu.csumb.anna.rendevu.api;

import edu.csumb.anna.rendevu.data.TextMessageResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface TextMessageAPI {
     @FormUrlEncoded
     @POST("api/v1.0/send")
     Call<TextMessageResponse> makeRequest(@Field("userID") String userID, @Field("message") String message, @Field("number") String number);
}

package edu.csumb.anna.rendevu.api;
import edu.csumb.anna.rendevu.data.Location;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface HerokuService {
    @GET("/")
    Call<ResponseBody> hello();

    @POST("/post")
    Call<Location> sendLocationData(@Body Location location);
}
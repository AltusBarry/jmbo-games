package praekelt.visualradio.Rest;

import java.io.File;

import praekelt.visualradio.Rest.Models.Item;
import praekelt.visualradio.Rest.Models.ReceivedProfileData;
import praekelt.visualradio.Rest.Models.SentProfileData;
import praekelt.visualradio.Rest.Models.VerticalThumbnailListing;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Part;
import retrofit.http.Path;

/**
 * Created by altus on 2015/04/15.
 */
public class API {

    public interface VRApi {
        @GET("/profile/")
        void getProfile(@Header("Authorization") String authorizationKey, Callback<ReceivedProfileData> cb);

        @Headers("Content-Type: application/json")
        @POST("/profile/register/")
        void createProfile(@Body SentProfileData profile, Callback<ReceivedProfileData> cb);

        @PUT("/profile/{userID}/")
        void updateProfile(@Path("userID") int userId,
                           @Header("Authorization") String authorizationKey,
                           @Body SentProfileData profile,
                           Callback<ReceivedProfileData> cb);

        @Multipart
        @PUT("/profile/avatar")
        void getProfile(@Header("Authorization") String authorizationKey,
                        @Part("file") File avatar,
                        Callback<ReceivedProfileData> cb);

    }

    public interface JMBOApi {

        //@GET("/listing/game-index/?format=json")

        //@Headers("Content-Type: application/json")
        @GET("/listing/game-index/?format=json")
        void getVerticalThumbnailListing(Callback<VerticalThumbnailListing> callback);

        @GET("/jmbo/content/detail/{slug}/?format=json")
        void getItem(@Path("slug") Callback<Item> callback);
    }
}

package praekelt.weblistingapp.Rest;

import com.google.gson.JsonElement;

import praekelt.weblistingapp.Rest.Models.Item;
import praekelt.weblistingapp.Rest.Models.VerticalThumbnailListing;
import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by altus on 2015/04/15.
 */
public class API {

    public interface JMBOApi {

        //@Headers("Content-Type: application/json")
        @GET("/api/v1/listing/2/?format=json")
        void getVerticalThumbnailListing(Callback<VerticalThumbnailListing> callback);

        @GET("/{uri}?format=json")
        void getItem(@Path(value = "uri", encode=false) String uri, Callback<JsonElement> response);

        @GET("/post/post/?format=json")
        //public JsonElement getTestPost(String item);
        void getTestPost(Callback<JsonElement> response);
    }
}

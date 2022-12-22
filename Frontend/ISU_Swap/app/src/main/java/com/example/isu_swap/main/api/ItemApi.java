/**
 *
 * @author Jose Medina Mani
 *
 * API Class that makes a variety of requests to the backend to
 * retrieve/send/delete information regarding Item information.
 */
package com.example.isu_swap.main.api;

import com.example.isu_swap.main.model.Item;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ItemApi {
    /**
     *
     * @return Endpoint returning Items
     */
    @GET("listings")
    Call<List<Item>> getItems();

    /**
     *
     * @param item
     * @param username
     * @return Creates listing in DB
     */
    @POST("listings/users/{username}")
    Call<Item> createListing(@Body Item item, @Path("username") String username);

    /**
     *
     * @param id
     * @param item
     * @return Updates listing in DB
     */
    @PUT("listings/{id}")
    Call<Item> updateListing(@Path("id") int id, @Body Item item);

    /**
     *
     * @param username
     * @return Endpoint returning Listings by username
     */
    @GET("listings/users/{username}")
    Call<List<Item>> getListingsByLister(@Path("username") String username);

    /**
     *
     * @param id
     * @return Endpoint returning Listings by ID
     */
    @GET("listings/{id}")
    Call<Item> getListingsById(@Path("id") int id);

    /**
     *
     * @param id
     * @return Deletes listing by ID (DB)
     */
    @DELETE("listings/{id}")
    Call<ResponseBody> deleteListing(@Path("id") int id);
}

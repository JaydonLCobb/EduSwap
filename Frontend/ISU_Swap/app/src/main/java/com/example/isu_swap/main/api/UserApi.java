/**
 *
 * @author Jose Medina Mani
 *
 * API Class that makes a variety of requests to the backend
 * to retrieve/send/delete information regarding Users/User Information.
 */
package com.example.isu_swap.main.api;

import com.example.isu_swap.main.model.User;
import java.util.List;

import okhttp3.Response;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserApi {

    /**
     *
     * @return Endpoint returning Users
     */
    @GET("users")
    Call<List<User>> getAllUsers();

    /**
     *
     * @param uid
     * @return Endpoint returning User by ID
     */
    @GET("users/{id}")
    Call<User> getUserById(@Path("id") int uid);

    /**
     *
     * @param username
     * @return Endpoint returning User by Username
     */
    @GET("users/username/{username}")
    Call<User> getUserByUsername(@Path("username") String username);

    /**
     *
     * @param user
     * @return Creates User in DB
     */
    @POST("users")
    Call<User> createUser(@Body User user);

    /**
     *
     * @param uid
     * @param user
     * @return Updates User in DB
     */
    @PUT("users/{id}")
    Call<User> updateUser(@Path("id") int uid, @Body User user);

    /**
     *
     * @param uid
     * @return Deletes User in DB
     */
    @DELETE("users/{id}")
    Call<Response> deleteUser(@Path("id") int uid);
}
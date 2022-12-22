/**
 *
 *
 * @author Jose Medina Mani
 *
 * API Class that makes a variety of requests to the backend
 * to retrieve/send/delete information regarding account information.
 */
package com.example.isu_swap.main.api;

import com.example.isu_swap.main.model.AccountInfo;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AccountInfoApi {

    /**
     *
     * @return Endpoint returning AccountInfo
     */
    @GET("accountInfos")
    Call<List<AccountInfo>> getAllAccountInfo();

    /**
     *
     * @param uid
     * @return Endpoint returning AccountInfo by ID
     */
    @GET("accountInfos/{id}")
    Call<AccountInfo> getAccountInfoById(@Path("id") int uid);

    /**
     *
     * @param ai
     * @return Creates AccountInfo
     */
    @POST("AccountInfos")
    Call<AccountInfo> createAccountInfo(@Body AccountInfo ai);

    /**
     *
     * @param uid
     * @param ai
     * @return Updates AccountInfo
     */
    @PUT("accountInfos/{id}")
    Call<AccountInfo> updateAccountInfo(@Path("id") int uid, @Body AccountInfo ai);

    /**
     *
     * @param aiid
     * @param uid
     * @return Connects Account to Information
     */
    @PUT("AccountInfos/{aiid}/users/{uid}")
    Call<AccountInfo> assignUserToAccountInfo(@Path("aiid") int aiid, @Path("uid") int uid);

    /**
     *
     * @param id
     * @return Deletes an accounts information
     */
    @DELETE("accountInfos/{id}")
    Call<ResponseBody> deleteAccountInfo(@Path("id") int id);
}
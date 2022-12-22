/**
 *
 *
 * @author Jose Medina Mani
 *
 * API Factory/Builder class that sets the baseURL to be used by the rest of our API's and then
 * creates them.
 */
package com.example.isu_swap.main.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClientFactory {
    static Retrofit apiClientSeed = null;

    /**
     *
     * @return Sets API Source URL
     */
    static Retrofit getApiClientSeed()
    {
        if(apiClientSeed == null)
        {
            apiClientSeed = new Retrofit.Builder()
                    .baseUrl("http://coms-309-057.class.las.iastate.edu:8080/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return apiClientSeed;
    }

    /**
     *
     * @return Return UserAPI based on it's associated model class
     */
    public static UserApi GetUserApi() {return getApiClientSeed().create(UserApi.class);}

    /**
     *
     * @return Return AccountInfoAPI based on it's associated model class
     */
    public static AccountInfoApi GetAccountInfoApi() {return getApiClientSeed().create(AccountInfoApi.class);}

    /**
     *
     * @return Return ImageAPI based on it's associated model class
     */
    public static ImageApi GetImageApi() {return getApiClientSeed().create(ImageApi.class);}

    /**
     *
     * @return Return MessageAPI based on it's associated model class
     */
    public static MessageApi GetMessageApi() {return getApiClientSeed().create(MessageApi.class);}

    /**
     *
     * @return Return ItemAPI based on it's associated model class
     */
    public static ItemApi GetItemApi() {return getApiClientSeed().create(ItemApi.class);}
}

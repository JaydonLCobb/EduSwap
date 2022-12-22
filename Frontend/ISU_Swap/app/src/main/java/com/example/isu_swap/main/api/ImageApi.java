/**
 *
 * @author Jose Medina Mani
 *
 * API Class that makes a GET Request to the backend to retrieve images/image information.
 */
package com.example.isu_swap.main.api;

import com.example.isu_swap.main.model.Image;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ImageApi {
    /**
     *
     * @return Endpoint returning Images
     */
    @GET("images")
    Call<List<Image>> getAllImages();
}

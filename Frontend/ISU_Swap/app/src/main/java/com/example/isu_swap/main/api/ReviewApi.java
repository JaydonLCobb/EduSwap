package com.example.isu_swap.main.api;

import com.example.isu_swap.main.model.Review;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ReviewApi {
    @GET("reviews")
    Call<List<Review>> getReviews();

    @GET("reviews/{id}")
    Call<Review> getReviewById(@Path("id") int id);

    @GET("reviews/reviewee/{username}")
    Call<List<Review>> getReviewsBySender(@Path("username") String username);

    @GET("reviews/reviewer/{username}")
    Call<List<Review>> getReviewsByReceiver(@Path("username") String username);

    @POST("reviews/{revieweeId}/{reviewerId}")
    Call<String> createReview(@Body Review review, @Path("revieweeId") int revieweeId, @Path("reviewerId") int reviewerId);

    @PUT("reviews/{id}")
    Call<Review> updateReview(@Path("id") int id, @Body Review review);
}

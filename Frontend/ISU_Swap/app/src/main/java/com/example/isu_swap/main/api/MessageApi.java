/**
 *
 * @author Jose Medina Mani
 *
 * API Class that makes a variety of requests to the backend
 * to retrieve/send/delete information regarding Messages.
 */
package com.example.isu_swap.main.api;

import com.example.isu_swap.main.model.Message;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MessageApi {
    /**
     *
     * @return Endpoint returning Messages
     */
    @GET("messages")
    Call<List<Message>> getAllMessages();

    /**
     *
     * @param uid
     * @return Endpoint returning Messages by ID
     */
    @GET("messages/{id}")
    Call<List<Message>> getMessagesById(@Path("id") int uid);

    /**
     *
     * @param receiver
     * @return Endpoint returning messages by recipient
     */
    @GET("messages/receiver/{receiver}")
    Call<List<Message>> getMessagesByReceiver(@Path("receiver") String receiver);

    /**
     *
     * @param message
     * @return Creates a message (DB)
     */
    @POST("messages")
    Call<Message> createMessage(@Body Message message);
}

package com.example.isu_swap.main.screen;

import static com.example.isu_swap.main.api.ApiClientFactory.GetUserApi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.isu_swap.main.model.User;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {
    int userId;
    WebSocketClient webSocketClient;
    EditText sendText;
    Button connectButton;
    Button sendButton;
    TextView chatView;
    String web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        getSupportActionBar().setTitle("Chat");
        userId = getIntent().getIntExtra("userId", 0);

        sendText = findViewById(R.id.chat_edittext);
        connectButton = findViewById(R.id.connect_button);
        sendButton = findViewById(R.id.send_button);
        chatView = findViewById(R.id.chat_textview);

        //et1 and bt1 omitted
        //e1 and b1 omitted

        GetUserApi().getUserById(userId).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                web = "ws://coms-309-057.class.las.iastate.edu:8080/chat/" + response.body().getUsername();
                Log.d("API", "onResponse: connecting at " + web);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {}
        });

        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Draft[] drafts = {
                        new Draft_6455()
                };

                try {
                    webSocketClient = new WebSocketClient(new URI(web), drafts[0]) {
                        @Override
                        public void onOpen(ServerHandshake handshakedata) {
                            Log.d("OPEN", "run() returned: " + "is connecting");
                        }

                        @Override
                        public void onMessage(String message) {
                            Log.d("", "run() returned: " + message);
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    String s = chatView.getText().toString();
                                    chatView.setText(s + "\n" + message);
                                }
                            });
                        }

                        @Override
                        public void onClose(int code, String reason, boolean remote) {
                            Log.d("CLOSE", "onClose() returned: " + reason);
                        }

                        @Override
                        public void onError(Exception ex) {
                            Log.d("Exception:", ex.toString());
                        }
                    };
                } catch (URISyntaxException e) {
                    Log.d("Exception:", e.getMessage());
                    e.printStackTrace();
                }
                webSocketClient.connect();
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    webSocketClient.send(sendText.getText().toString());
                } catch (Exception e) {
                    Log.d("ExceptionSendMessage:", e.getMessage());
                }
            }
        });
    }
}
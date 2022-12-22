/**
 * Class representing the Compose Message screen
 *
 * @author Jose Medina Mani
 */
package com.example.isu_swap.main.screen;

import static com.example.isu_swap.main.api.ApiClientFactory.GetMessageApi;
import static com.example.isu_swap.main.api.ApiClientFactory.GetUserApi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.isu_swap.main.model.Message;
import com.example.isu_swap.main.model.User;

import java.sql.Timestamp;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComposeActivity extends AppCompatActivity {
    TextView toField;
    TextView subjectField;
    TextView bodyField;
    int userId;
    String username;

    /**
     * Sets XML elements and api calls necessary for base functionality of this screen
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        getSupportActionBar().setTitle("Compose Message");

        userId = getIntent().getIntExtra("userId", 0);
        toField = findViewById(R.id.to_text_field);
        subjectField = findViewById(R.id.subject_text_field);
        bodyField = findViewById(R.id.body_text_field);

        GetUserApi().getUserById(userId).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.body() != null) {
                    username = response.body().getUsername();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {}
        });
    }

    /**
     * Represents this screen's actionbar
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.compose_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.app_bar_send);
        Button sendBtn = (Button) menuItem.getActionView();

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message newMsgRequest = new Message();
                    newMsgRequest.setId(1); //how is this going to be kept track of...
                    newMsgRequest.setSender(username);
                    newMsgRequest.setReceiver(toField.getText().toString());
                    newMsgRequest.setTimestamp(new Timestamp(new Date().getTime()).toString());
                    newMsgRequest.setSubject(subjectField.getText().toString());
                    newMsgRequest.setMessage(bodyField.getText().toString());

                GetMessageApi().createMessage(newMsgRequest).enqueue(new Callback<Message>() {
                    @Override
                    public void onResponse(Call<Message> call, Response<Message> response) {
                        Toast.makeText(ComposeActivity.this, "Message sent!", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onFailure(Call<Message> call, Throwable t) {

                    }
                });
            }
        });

        return true;
    }
}
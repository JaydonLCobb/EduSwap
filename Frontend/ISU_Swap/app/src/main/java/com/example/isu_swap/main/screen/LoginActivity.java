/**
 * Class that handles User login(s)
 * @Author Jaydon Cobb
 */

package com.example.isu_swap.main.screen;

import static com.example.isu_swap.main.api.ApiClientFactory.GetUserApi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.isu_swap.main.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText userName;
    private EditText passWord;
    private TextView errorText;
    private Button login;
    private String currentUser;

    /**
     * Sets XML elements necessary for base functionality of this screen and validates Login information
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("Login");

        userName = findViewById(R.id.UsernameTextField);
        passWord = findViewById(R.id.PasswordTextField);
        errorText = findViewById(R.id.error_text);
        login = findViewById(R.id.LoginButton);

        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                GetUserApi().getUserByUsername(userName.getText().toString()).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (passWord.getText().toString().equals(response.body().getPassword()))
                        {
//                          setCurrentUser(String.valueOf(userName));
                            Intent homepageIntent = new Intent(LoginActivity.this, HomepageActivity.class);
                            homepageIntent.putExtra("userId", response.body().getId());
                            startActivity(homepageIntent);
                        }
                        else
                        {
                            errorText.setText("Password is incorrect.");
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        errorText.setText(t.getMessage());
                    }
                });
            }
        });
    }
//    private void setCurrentUser(String currentUser) {
//        this.currentUser = currentUser;
//    }
//    private String getCurrentUser() {
//        return currentUser;
//    }
}

/**
 * Class representing the Landing Page screen
 *
 * @author Jose Medina Mani
 */
package com.example.isu_swap.main.screen;

import static com.example.isu_swap.main.api.ApiClientFactory.GetAccountInfoApi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.isu_swap.main.api.AccountInfoApi;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LandingPageActivity extends AppCompatActivity {
    Button registerBtn;
    Button loginBtn;


    /**
     * Sets XML elements and api calls necessary for base functionality of this screen
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);
        getSupportActionBar().setTitle("Landing Page");

        registerBtn = findViewById(R.id.button_register);
        loginBtn = findViewById(R.id.button_login);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentRegister = new Intent(LandingPageActivity.this, RegisterActivity.class);
                startActivity(intentRegister);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLogin = new Intent(LandingPageActivity.this, LoginActivity.class);
                startActivity(intentLogin);
            }
        });
    }
}
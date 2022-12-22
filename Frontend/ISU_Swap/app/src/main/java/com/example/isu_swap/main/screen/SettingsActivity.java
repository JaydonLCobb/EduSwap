/**
 * Class representing the Account Settings screen
 *
 * @author Jose Medina Mani
 */
package com.example.isu_swap.main.screen;

import static com.example.isu_swap.main.api.ApiClientFactory.GetUserApi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.isu_swap.main.model.User;
import com.google.android.material.navigation.NavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsActivity extends AppCompatActivity {
    int userId;
    Button detailsPage;

    TextView navHeaderName;
    TextView navHeaderUser;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;

    /**
     * Helper method for the DrawerLayout
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Sets XML elements and api calls necessary for base functionality of this screen
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().setTitle("Account Settings");

        userId = getIntent().getIntExtra("userId", 0);
        detailsPage = findViewById(R.id.create_avatar_button);

        drawerLayout = findViewById(R.id.settings_drawer);
        navigationView = findViewById(R.id.settings_nav_view);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        navHeaderName = navigationView.getHeaderView(0).findViewById(R.id.nav_header_name);
        navHeaderUser = navigationView.getHeaderView(0).findViewById(R.id.nav_header_username);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        GetUserApi().getUserById(userId).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                navHeaderName.setText(response.body().getUsername());
                navHeaderUser.setText(response.body().getEmail());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_home) {
                    Intent intentHomepage = new Intent(SettingsActivity.this, HomepageActivity.class);
                    intentHomepage.putExtra("userId", userId);
                    startActivity(intentHomepage);
                } else if (item.getItemId() == R.id.nav_profile) {
                    Intent intentProfile = new Intent(SettingsActivity.this, UserPageActivity.class);
                    intentProfile.putExtra("userId", userId);
                    intentProfile.putExtra("seller", navHeaderName.getText());
                    startActivity(intentProfile);
                } else if (item.getItemId() == R.id.nav_inbox) {
                    Intent intentInbox = new Intent(SettingsActivity.this, InboxActivity.class);
                    intentInbox.putExtra("userId", userId);
                    startActivity(intentInbox);
                } else if (item.getItemId() == R.id.nav_settings) {
                    // DO NOTHING
                }
                return false;
            }
        });

        detailsPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentDetails = new Intent(SettingsActivity.this, DetailsActivity.class);
                intentDetails.putExtra("userId", userId);
                startActivity(intentDetails);
            }
        });
    }

    /**
     * Sets the back press functionality for the DrawerLayout of this screen
     */
    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
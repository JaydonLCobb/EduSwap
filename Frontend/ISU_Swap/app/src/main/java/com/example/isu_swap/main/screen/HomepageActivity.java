/**
 * Class representing the Homepage screen
 *
 * @author Jose Medina Mani
 */
package com.example.isu_swap.main.screen;

import static com.example.isu_swap.main.api.ApiClientFactory.GetItemApi;
import static com.example.isu_swap.main.api.ApiClientFactory.GetUserApi;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.isu_swap.main.model.Item;
import com.example.isu_swap.main.model.User;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomepageActivity extends AppCompatActivity implements HomepageRecyclerAdapter.ItemListener {
    int userId;
    ArrayList<Item> itemList;
    RecyclerView recyclerView;
    CardView createListingBtn;
    CardView tradingChatBtn;

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
        setContentView(R.layout.activity_homepage);
        getSupportActionBar().setTitle("Homepage");

        userId = getIntent().getIntExtra("userId", 0);
        itemList = new ArrayList<Item>();
        recyclerView = findViewById(R.id.homepage_recycler);
        createListingBtn = findViewById(R.id.homepage_create_listing_button);
        tradingChatBtn = findViewById(R.id.trading_chat_cardview);

        drawerLayout = findViewById(R.id.homepage_drawer);
        navigationView = findViewById(R.id.homepage_nav_view);
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
                    // Do nothing, stay on this screen
                } else if (item.getItemId() == R.id.nav_profile) {
                    Intent intentProfile = new Intent(HomepageActivity.this, UserPageActivity.class);
                    intentProfile.putExtra("userId", userId);
                    intentProfile.putExtra("seller", navHeaderName.getText());
                    startActivity(intentProfile);
                } else if (item.getItemId() == R.id.nav_inbox) {
                    Intent intentInbox = new Intent(HomepageActivity.this, InboxActivity.class);
                    intentInbox.putExtra("userId", userId);
                    startActivity(intentInbox);
                } else if (item.getItemId() == R.id.nav_settings) {
                    Intent intentSettings = new Intent(HomepageActivity.this, SettingsActivity.class);
                    intentSettings.putExtra("userId", userId);
                    startActivity(intentSettings);
                }
                return false;
            }
        });

        GetItemApi().getItems().enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                if (response.isSuccessful() && response != null) {
                    itemList.addAll(response.body());
                } else {
                    itemList.add(new Item(1, "Lamp", "14.99", 2, "https://upload.wikimedia.org/wikipedia/commons/2/2f/Lamp_with_a_lampshade_illuminated_by_sunlight.jpg"));
                    itemList.add(new Item(2, "Rug", "7.23", 1, "https://upload.wikimedia.org/wikipedia/commons/6/6a/Taspinar_rug_%28Yurdan%29.jpg"));
                }
                setAdapter();
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });

        createListingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentCreateListing = new Intent(HomepageActivity.this, CreateListingActivity.class);
                intentCreateListing.putExtra("userId", userId);
                startActivity(intentCreateListing);
            }
        });

        tradingChatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentChatActivity = new Intent(HomepageActivity.this, ChatActivity.class);
                intentChatActivity.putExtra("userId", userId);
                startActivity(intentChatActivity);
            }
        });
    }

    /**
     * Sets the RecyclerView adapter for this screen
     */
    private void setAdapter() {
        HomepageRecyclerAdapter adapter = new HomepageRecyclerAdapter(itemList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
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

    /**
     * Sets the intent for each item in the RecyclerView of this screen
     *
     * @param position position of the item in the RecyclerView
     */
    @Override
    public void onItemClick(int position) {
        Intent intentItemPage = new Intent(HomepageActivity.this, ItemPageActivity.class);
        intentItemPage.putExtra("userId", userId);
        intentItemPage.putExtra("itemId", itemList.get(position).getId());
        startActivity(intentItemPage);
    }
}


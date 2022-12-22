/**
 * Class representing the Profile screen
 *
 * @author Jose Medina Mani
 */
package com.example.isu_swap.main.screen;

import static com.example.isu_swap.main.api.ApiClientFactory.GetAccountInfoApi;
import static com.example.isu_swap.main.api.ApiClientFactory.GetItemApi;
import static com.example.isu_swap.main.api.ApiClientFactory.GetUserApi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.isu_swap.main.model.AccountInfo;
import com.example.isu_swap.main.model.Item;
import com.example.isu_swap.main.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class UserPageActivity extends AppCompatActivity implements HomepageRecyclerAdapter.ItemListener {
    final long ONE_MEGABYTE = 1024 * 1024;
    int userId;
    String seller;
    ArrayList<Item> itemList;
    RecyclerView recyclerView;
    TextView fullName;
    TextView username;
    CardView reviewBtn;
    ImageView userImg;
    TextView navHeaderName;
    TextView navHeaderUser;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;
    StorageReference storageRef;

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
        setContentView(R.layout.activity_user_page);
        getSupportActionBar().setTitle("Profile");

        userId = getIntent().getIntExtra("userId", 0);
        seller = getIntent().getStringExtra("seller");
        itemList = new ArrayList<Item>();
        recyclerView = findViewById(R.id.user_page_recycler);
        fullName = findViewById(R.id.user_page_fullname);
        username = findViewById(R.id.user_page_username);
        reviewBtn = findViewById(R.id.user_page_review_button);
        userImg = findViewById(R.id.user_page_profile_picture);
        username.setText(seller);
        drawerLayout = findViewById(R.id.profile_drawer);
        navigationView = findViewById(R.id.profile_nav_view);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        navHeaderName = navigationView.getHeaderView(0).findViewById(R.id.nav_header_name);
        navHeaderUser = navigationView.getHeaderView(0).findViewById(R.id.nav_header_username);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        storageRef = FirebaseStorage.getInstance().getReference();

        GetUserApi().getUserById(userId).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                navHeaderName.setText(response.body().getUsername());
                navHeaderUser.setText(response.body().getEmail());
                setUserImg(response.body().getId());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_home) {
                    Intent intentHome = new Intent(UserPageActivity.this, HomepageActivity.class);
                    intentHome.putExtra("userId", userId);
                    startActivity(intentHome);
                } else if (item.getItemId() == R.id.nav_profile) {
                    if (navHeaderName.getText().equals(seller)) {
                        // Do nothing
                    } else {
                        Intent intentProfile = new Intent(UserPageActivity.this, UserPageActivity.class);
                        intentProfile.putExtra("userId", userId);
                        intentProfile.putExtra("seller", navHeaderName.getText());
                        startActivity(intentProfile);
                    }
                } else if (item.getItemId() == R.id.nav_inbox) {
                    Intent intentInbox = new Intent(UserPageActivity.this, InboxActivity.class);
                    intentInbox.putExtra("userId", userId);
                    startActivity(intentInbox);
                } else if (item.getItemId() == R.id.nav_settings) {
                    Intent intentSettings = new Intent(UserPageActivity.this, SettingsActivity.class);
                    intentSettings.putExtra("userId", userId);
                    startActivity(intentSettings);
                }
                return false;
            }
        });

        GetItemApi().getListingsByLister(seller).enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                if (response.isSuccessful() && response.body().size() > 0) {
                    GetAccountInfoApi().getAccountInfoById(response.body().get(0).getLister().getId()).enqueue(new Callback<AccountInfo>() {
                        @Override
                        public void onResponse(Call<AccountInfo> call, Response<AccountInfo> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                fullName.setText(response.body().getFname() + " " + response.body().getLname());
                            }
                        }

                        @Override
                        public void onFailure(Call<AccountInfo> call, Throwable t) {
                            fullName.setText("");
                        }
                    });
                    itemList.addAll(response.body());
                    setAdapter();
                }
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                System.out.println(seller);
                System.out.println(t.getMessage());
            }
        });

        reviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (seller.equals(navHeaderName.getText())) {   // this is the logged-in user's page
                    // set onClick to remind them of this
                    Toast.makeText(getApplicationContext(), "You can't leave a review for yourself!", Toast.LENGTH_SHORT).show();
                } else {                                        // this is another user's page
                    // set onClick to intent towards the review form
                    Intent intentReview = new Intent(UserPageActivity.this, ReviewActivity.class);
                    intentReview.putExtra("userId", userId);
                    intentReview.putExtra("seller", seller);
                }
            }
        });
    }

    private void setUserImg(int id) {
        StorageReference pathRef = storageRef.child("images/" + id);

        pathRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                userImg.setImageBitmap(Bitmap.createScaledBitmap(bm, userImg.getWidth(), userImg.getHeight(), false));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Sets the RecyclerView adapter for this screen. The adapter is set differently depending on
     * whether or not the logged in user is viewing their own profile, or if they are viewing
     * someone else's profile.
     */
    private void setAdapter() {
        if (seller.equals(navHeaderName.getText())) {   // this is the logged-in user's page
            // set adapter to UserPageRecyclerAdapter
            UserPageRecyclerAdapter userPageRecyclerAdapter = new UserPageRecyclerAdapter(itemList, this::onItemClick);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(userPageRecyclerAdapter);
        } else {                                        // this is another user's page
            // set adapter to HomepageRecyclerAdapter
            HomepageRecyclerAdapter homepageRecyclerAdapter = new HomepageRecyclerAdapter(itemList, this::onItemClick);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(homepageRecyclerAdapter);
        }
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
     * Sets the intent for each item in the RecyclerView of this screen. The intent is set
     * differently depending on whether or not the logged in user is viewing their own profile, or
     * if they are viewing someone else's profile.
     *
     * @param position
     */
    @Override
    public void onItemClick(int position) {
        if (seller.equals(navHeaderName.getText())) {   // this is the logged-in user's page
            // set onItemClick to make edits
            Intent intentItemEdit = new Intent(UserPageActivity.this, ItemPageEditActivity.class);
            intentItemEdit.putExtra("userId", userId);
            intentItemEdit.putExtra("seller", seller);
            intentItemEdit.putExtra("itemId", itemList.get(position).getId());
            startActivity(intentItemEdit);
        } else {                                        // this is another user's page
            // set onItemClick to item page
            Intent intentItemPage = new Intent(UserPageActivity.this, ItemPageActivity.class);
            intentItemPage.putExtra("userId", userId);
            intentItemPage.putExtra("itemId", itemList.get(position).getId());
            startActivity(intentItemPage);
        }
    }
}
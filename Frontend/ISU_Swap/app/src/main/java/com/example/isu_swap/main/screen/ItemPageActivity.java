/**
 * Class representing the Item Details screen
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

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.isu_swap.main.model.AccountInfo;
import com.example.isu_swap.main.model.Item;
import com.example.isu_swap.main.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemPageActivity extends AppCompatActivity {
    final long ONE_MEGABYTE = 1024 * 1024;
    int userId;
    int itemId;
    ImageView itemPreview;
    TextView itemName;
    ImageView profilePicture;
    TextView sellerName;
    TextView sellerUsername;
    CardView messageBtn;
    TextView itemPrice;
    TextView itemDesc;
    CardView purchaseBtn;
    LinearLayout profileBtn;

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
        setContentView(R.layout.activity_item_page);
        getSupportActionBar().setTitle("Item Details");

        userId = getIntent().getIntExtra("userId", 0);
        itemId = getIntent().getIntExtra("itemId", 0);
        itemPreview = findViewById(R.id.item_page_image);
        itemName = findViewById(R.id.item_page_title);
        profilePicture = findViewById(R.id.item_page_profile_picture_image);
        sellerName = findViewById(R.id.item_page_seller_fullname);
        sellerUsername = findViewById(R.id.item_page_seller_username);
        messageBtn = findViewById(R.id.item_page_message_button);
        itemPrice = findViewById(R.id.item_page_price);
        itemDesc = findViewById(R.id.item_page_desc);
        purchaseBtn = findViewById(R.id.item_page_buy_button);
        profileBtn = findViewById(R.id.item_page_profile_button);
        storageRef = FirebaseStorage.getInstance().getReference();

        GetItemApi().getListingsById(itemId).enqueue(new Callback<Item>() {
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {
                itemName.setText(response.body().getTitle());
                sellerUsername.setText(response.body().getLister().getUsername());
                itemPrice.setText("$" + response.body().getPrice());
                itemDesc.setText(response.body().getDescription());
                setItemImg(response.body().getImage());

                GetAccountInfoApi().getAccountInfoById(response.body().getLister().getId()).enqueue(new Callback<AccountInfo>() {
                    @Override
                    public void onResponse(Call<AccountInfo> call, Response<AccountInfo> response) {
                        sellerName.setText(response.body().getFname() + " " + response.body().getLname());
                        setProfileImg(response.body().getId());
                    }

                    @Override
                    public void onFailure(Call<AccountInfo> call, Throwable t) {
                        sellerName.setText("");
                    }
                });

                messageBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intentCompose = new Intent(ItemPageActivity.this, ComposeActivity.class);
                        intentCompose.putExtra("userId", userId);
                        intentCompose.putExtra("sellerId", sellerUsername.getText()); //need to set this
                        startActivity(intentCompose);
                    }
                });

                profileBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intentProfile = new Intent(ItemPageActivity.this, UserPageActivity.class);
                        intentProfile.putExtra("userId", userId);
                        intentProfile.putExtra("seller", sellerUsername.getText());
                        startActivity(intentProfile);
                    }
                });
            }

            @Override
            public void onFailure(Call<Item> call, Throwable t) {

            }
        });

        purchaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentPurchase = new Intent(ItemPageActivity.this, PurchasePageActivity.class);
                intentPurchase.putExtra("userId", userId);
                intentPurchase.putExtra("itemId", itemId);
                startActivity(intentPurchase);
            }
        });

        drawerLayout = findViewById(R.id.item_page_drawer);
        navigationView = findViewById(R.id.item_page_nav_view);
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
                    Intent intentHome = new Intent(ItemPageActivity.this, HomepageActivity.class);
                    intentHome.putExtra("userId", userId);
                    startActivity(intentHome);
                } else if (item.getItemId() == R.id.nav_profile) {
                    Intent intentProfile = new Intent(ItemPageActivity.this, UserPageActivity.class);
                    intentProfile.putExtra("userId", userId);
                    intentProfile.putExtra("seller", navHeaderName.getText());
                    startActivity(intentProfile);
                } else if (item.getItemId() == R.id.nav_inbox) {
                    Intent intentInbox = new Intent(ItemPageActivity.this, InboxActivity.class);
                    intentInbox.putExtra("userId", userId);
                    startActivity(intentInbox);
                } else if (item.getItemId() == R.id.nav_settings) {
                    Intent intentSettings = new Intent(ItemPageActivity.this, SettingsActivity.class);
                    intentSettings.putExtra("userId", userId);
                    startActivity(intentSettings);                }
                return false;
            }
        });
    }

    private void setItemImg(String uuid) {
        StorageReference pathRef = storageRef.child("images/" + uuid);

        pathRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                itemPreview.setImageBitmap(Bitmap.createScaledBitmap(bm, itemPreview.getWidth(), itemPreview.getHeight(), false));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void setProfileImg(int id) {
        StorageReference pathRef = storageRef.child("images/" + id);

        pathRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                profilePicture.setImageBitmap(Bitmap.createScaledBitmap(bm, profilePicture.getWidth(), profilePicture.getHeight(), false));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
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
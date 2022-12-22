/**
 * Class representing the Inbox screen
 *
 * @author Jose Medina Mani
 */
package com.example.isu_swap.main.screen;

import static com.example.isu_swap.main.api.ApiClientFactory.GetMessageApi;
import static com.example.isu_swap.main.api.ApiClientFactory.GetUserApi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.isu_swap.main.model.Message;
import com.example.isu_swap.main.model.User;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InboxActivity extends AppCompatActivity {
    int userId;
    ArrayList<Message> messageList;
    RecyclerView recyclerView;
    Button composeBtn;
    TextView apiInboxStatus;
    String username;
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
        setContentView(R.layout.activity_inbox);
        getSupportActionBar().setTitle("Inbox");

        userId = getIntent().getIntExtra("userId", 0);
        messageList = new ArrayList<>();
        recyclerView = findViewById(R.id.inbox_recycler);
        apiInboxStatus = findViewById(R.id.inbox_status);
        composeBtn = findViewById(R.id.compose_button);
        drawerLayout = findViewById(R.id.inbox_drawer);
        navigationView = findViewById(R.id.inbox_nav_view);
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
                    Intent intentHome = new Intent(InboxActivity.this, HomepageActivity.class);
                    intentHome.putExtra("userId", userId);
                    startActivity(intentHome);
                } else if (item.getItemId() == R.id.nav_profile) {
                    Intent intentProfile = new Intent(InboxActivity.this, UserPageActivity.class);
                    intentProfile.putExtra("userId", userId);
                    intentProfile.putExtra("seller", navHeaderName.getText());
                    startActivity(intentProfile);
                } else if (item.getItemId() == R.id.nav_inbox) {
                    // Do nothing, stay on this screen
                } else if (item.getItemId() == R.id.nav_settings) {
                    Intent intentSettings = new Intent(InboxActivity.this, SettingsActivity.class);
                    intentSettings.putExtra("userId", userId);
                    startActivity(intentSettings);                }
                return false;
            }
        });

        composeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent composeIntent = new Intent(InboxActivity.this, ComposeActivity.class);
                composeIntent.putExtra("userId", userId);
                startActivity(composeIntent);
            }
        });

        GetUserApi().getUserById(userId).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful())
                {
                    username = response.body().getUsername();

                    GetMessageApi().getMessagesByReceiver(username).enqueue(new Callback<List<Message>>() {
                        @Override
                        public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                            if(response.isSuccessful() && response.body() != null)
                            {
                                apiInboxStatus.setText("");
                                messageList.addAll(response.body());
                                setAdapter();
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Message>> call, Throwable t) {
                            apiInboxStatus.setText(t.getMessage());
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
            }
        });
    }

    /**
     * Sets the RecyclerView adapter for this screen
     */
    private void setAdapter() {
        InboxRecyclerAdapter adapter = new InboxRecyclerAdapter(messageList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    /**
     * Represents this screen's actionbar
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.inbox_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.app_bar_search);
        menuItem.setTitle("Inbox");
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search inbox for...");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
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
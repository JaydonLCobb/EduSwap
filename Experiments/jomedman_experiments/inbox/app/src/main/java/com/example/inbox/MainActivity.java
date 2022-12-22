package com.example.inbox;

import static com.example.inbox.api.ApiClientFactory.GetPostApi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.inbox.model.Post;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    ArrayList<Post> postList;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        postList = new ArrayList<>();
        recyclerView = findViewById(R.id.inbox_recycler);
        TextView apiInboxStatus = findViewById(R.id.inbox_status);
        apiInboxStatus.setText("Loading mail...");

        GetPostApi().getFirstPost().enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if(response.isSuccessful() && response.body() != null)
                {
                    apiInboxStatus.setText("");
                    postList.addAll(response.body());
                    setAdapter();
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                apiInboxStatus.setText(t.getMessage());
            }
        });
    }

    private void setAdapter() {
        InboxRecyclerAdapter adapter = new InboxRecyclerAdapter(postList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.inbox_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.app_bar_search);
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
}
/**
 * Class representing the Edit Listing screen
 *
 * @author Jose Medina Mani
 */
package com.example.isu_swap.main.screen;

import static com.example.isu_swap.main.api.ApiClientFactory.GetItemApi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.isu_swap.main.model.Item;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemPageEditActivity extends AppCompatActivity {
    int userId;
    int itemId;
    String seller;
    private Uri path;
    private final int PICK_IMAGE_REQUEST = 1;
    private CardView selectImageBtn;
    private TextView pathText;
    private EditText titleInput;
    private EditText descInput;
    private EditText priceInput;
    private CardView cancelBtn;
    private CardView updateListingBtn;
    private StorageReference ref;
    private ProgressDialog progressDialog;

    /**
     * Sets XML elements and api calls necessary for base functionality of this screen
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_page_edit);
        getSupportActionBar().setTitle("Edit Listing");
        progressDialog = new ProgressDialog(this);

        userId = getIntent().getIntExtra("userId", 0);
        itemId = getIntent().getIntExtra("itemId", 0);
        seller = getIntent().getStringExtra("seller");
        selectImageBtn = findViewById(R.id.edit_listing_update_image_button);
        pathText = findViewById(R.id.edit_listing_path_text);
        titleInput = findViewById(R.id.edit_listing_title_update);
        descInput = findViewById(R.id.edit_listing_description_update);
        priceInput = findViewById(R.id.edit_listing_price_update);
        cancelBtn = findViewById(R.id.edit_listing_cancel_button);
        updateListingBtn = findViewById(R.id.edit_listing_update_button);

        selectImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentProfile = new Intent(ItemPageEditActivity.this, UserPageActivity.class);
                intentProfile.putExtra("userId", userId);
                intentProfile.putExtra("seller", seller);
                startActivity(intentProfile);
            }
        });

        updateListingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();
            }
        });
    }

    private void update() {
        if (path != null) {
            progressDialog.show();
            String imageName = UUID.randomUUID().toString();

            ref = FirebaseStorage.getInstance().getReference().child("images/" + imageName);
            ref.putFile(path).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    //on upload success
                    updateListing(imageName);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    //on upload fail
                    progressDialog.dismiss();
                    Toast.makeText(ItemPageEditActivity.this, e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    //on upload progress
                    double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                    progressDialog.setMessage("Uploading... " + + (int)progress + "%");
                }
            });
        }
    }

    /**
     * Instantiates image selection intent
     */
    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Choose an image"), PICK_IMAGE_REQUEST);
    }

    /**
     * Displays the path of the selected image
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            path = data.getData();
            pathText.setText(path.getPath());
        }
    }

    /**
     * Sends a listing object to the database. If successful, the screen finishes, if not, a toast
     * message displays what went wrong.
     */
    private void updateListing(String uuid) {
        GetItemApi().getListingsById(itemId).enqueue(new Callback<Item>() {
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {
                response.body().setImage(uuid);
                if (!titleInput.getText().toString().isEmpty()) {
                    response.body().setTitle(titleInput.getText().toString());
                }
                if (!descInput.getText().toString().isEmpty()) {
                    response.body().setDescription(descInput.getText().toString());
                }
                if (!priceInput.getText().toString().isEmpty()) {
                    response.body().setPrice(priceInput.getText().toString());
                }

                GetItemApi().updateListing(itemId, response.body()).enqueue(new Callback<Item>() {
                    @Override
                    public void onResponse(Call<Item> call, Response<Item> response) {
                        Intent intentProfile = new Intent(ItemPageEditActivity.this, UserPageActivity.class);
                        intentProfile.putExtra("userId", userId);
                        intentProfile.putExtra("seller", seller);
                        startActivity(intentProfile);
                    }

                    @Override
                    public void onFailure(Call<Item> call, Throwable t) {
                        Toast.makeText(ItemPageEditActivity.this, t.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onFailure(Call<Item> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }
}
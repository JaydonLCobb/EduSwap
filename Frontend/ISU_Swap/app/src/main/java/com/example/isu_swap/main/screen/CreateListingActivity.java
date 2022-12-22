/**
 * Class representing the Create Listing screen
 *
 * @author Jose Medina Mani
 */
package com.example.isu_swap.main.screen;

import static com.example.isu_swap.main.api.ApiClientFactory.GetItemApi;
import static com.example.isu_swap.main.api.ApiClientFactory.GetUserApi;
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
import android.app.ProgressDialog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.isu_swap.main.model.Item;
import com.example.isu_swap.main.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateListingActivity extends AppCompatActivity {
    private final int PICK_IMAGE_REQUEST = 1;
    int userId;
    private Uri path;
    private String downloadUrl;
    private CardView selectImageBtn;
    private TextView pathText;
    private EditText titleInput;
    private EditText descInput;
    private EditText priceInput;
    private CardView cancelBtn;
    private CardView createListingBtn;
    private StorageReference ref,strg;
    private DatabaseReference dbref;
    private ProgressDialog progressDialog;

    /**
     * Sets XML elements and api calls necessary for base functionality of this screen
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_listing);
        getSupportActionBar().setTitle("Create Listing");
        progressDialog = new ProgressDialog(this);

        userId = getIntent().getIntExtra("userId", 0);
        selectImageBtn = findViewById(R.id.create_listing_upload_button);
        pathText = findViewById(R.id.create_listing_path_text);
        titleInput = findViewById(R.id.create_listing_title_edit);
        descInput = findViewById(R.id.create_listing_description_edit);
        priceInput = findViewById(R.id.create_listing_price_edit);
        cancelBtn = findViewById(R.id.create_listing_cancel_button);
        createListingBtn = findViewById(R.id.create_listing_create_button);
        //firebase
        strg = FirebaseStorage.getInstance().getReference();
        dbref = FirebaseDatabase.getInstance().getReference("IMAGE");

        selectImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentHomepage = new Intent(CreateListingActivity.this, HomepageActivity.class);
                intentHomepage.putExtra("userId", userId);
                startActivity(intentHomepage);
            }
        });

        createListingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    uploadImage();
                } else {
                    Toast.makeText(CreateListingActivity.this, "invalid form",Toast.LENGTH_LONG).show();
                }
            }
        });
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
     *
     * @param imageName name of the image to be stored in the server
     */
    private void createListing(String imageName) {
        Item newRequestItem = new Item();
        newRequestItem.setTitle(titleInput.getText().toString());
        newRequestItem.setDescription(descInput.getText().toString());
        newRequestItem.setPrice(priceInput.getText().toString());
        newRequestItem.setImage(imageName);

        GetItemApi().getItems().enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                newRequestItem.setId(response.body().get(response.body().size() - 1).getId() + 1);
                GetUserApi().getUserById(userId).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        GetItemApi().createListing(newRequestItem, response.body().getUsername()).enqueue(new Callback<Item>() {
                            @Override
                            public void onResponse(Call<Item> call, Response<Item> response) {
                                Intent intentHomepage = new Intent(CreateListingActivity.this, HomepageActivity.class);
                                intentHomepage.putExtra("userId", userId);
                                startActivity(intentHomepage);
                            }

                            @Override
                            public void onFailure(Call<Item> call, Throwable t) {
                                System.out.println(t.getMessage());
                                Toast.makeText(CreateListingActivity.this, t.getMessage(),Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(CreateListingActivity.this, t.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                System.out.println(t.getMessage());
                Toast.makeText(CreateListingActivity.this, t.getMessage(),Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }

    private void uploadImage() {
        if(path != null) {
            progressDialog.show();
            String imageName = UUID.randomUUID().toString();

            ref = FirebaseStorage.getInstance().getReference().child("images/" + imageName);
            ref.putFile(path).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    //on upload success
                    createListing(imageName);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    //on upload fail
                    progressDialog.dismiss();
                    Toast.makeText(CreateListingActivity.this, e.getMessage(),Toast.LENGTH_LONG).show();

                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    //on upload progress
                    double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                    progressDialog.setMessage("Uploading... " + + (int)progress + "%");
                }
            });
        } else {
            Toast.makeText(CreateListingActivity.this, "Image not found",Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Form validation of an Item object before being sent to the database.
     *
     * @return true if valid, false otherwise
     */
    private boolean validate() {

        if (titleInput.getText().toString().isEmpty()
        || descInput.getText().toString().isEmpty()
        || priceInput.getText().toString().isEmpty()
        || path == null) {
            System.out.println("validation failed");
            return false;
        } else {
            return true;
        }
    }
}
/**
 * Class that handles image/avatar selection, uploading and assignment.
 * @Author: Jaydon Cobb
 */

package com.example.isu_swap.main.screen;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class DetailsActivity extends AppCompatActivity {

    int userId;

    private EditText imgTitle;
    private Button selectImg;
    private Button uploadImg;
    private ImageView imageView;
    private Uri path;
    private final int PICK_IMAGE_REQUEST = 1;
    FirebaseStorage storage;
    StorageReference storageRef;
    private int imageName;

    /**
     * Sets XML elements necessary for base functionality of this screen, enables user to
     * browse device.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        userId = getIntent().getIntExtra("userId", 0);
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#000000"));
        actionBar.setBackgroundDrawable(colorDrawable);
        selectImg = findViewById(R.id.selectImg);
        uploadImg = findViewById(R.id.uploadImg);
        imageView = findViewById(R.id.imageView);
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        selectImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        uploadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });
    }

    /**
     * Instantiates image selection intent
     */
    @Deprecated
    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Choose an image"), PICK_IMAGE_REQUEST);
    }

    /**
     *
     * Converts image to bitmap to be sent to FireBase
     * @param requestCode The integer request code originally supplied to
     *                    startActivityForResult(), allowing you to identify who this
     *                    result came from.
     * @param resultCode The integer result code returned by the child activity
     *                   through its setResult().
     * @param data An Intent, which can return result data to the caller
     *               (various data can be attached to Intent "extras").
     *
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            path = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Creates an image object and a reference to selected image in order to be sent to firebase storage,
     * will show success/failure upon upload request completion
     */
    private void uploadImage() {
        if (path != null) {
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Attempting Upload");
            progressDialog.show();
            imageName = getIntent().getIntExtra("userId", 0);
            StorageReference ref = storageRef.child("images/" + imageName);
            ref.putFile(path).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(
                                        UploadTask.TaskSnapshot taskSnapshot) {
                                    progressDialog.dismiss();
                                    Toast.makeText(DetailsActivity.this, "Upload Success ", Toast.LENGTH_SHORT).show();
                                }
                            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(DetailsActivity.this, "Upload failure " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage("Progress " + (int)progress + "%");
                                }
                            });
        }
    }
}


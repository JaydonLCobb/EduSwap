/**
 * Class representing the Purchase Portal screen
 *
 * @author Jose Medina Mani
 */
package com.example.isu_swap.main.screen;

import static com.example.isu_swap.main.api.ApiClientFactory.GetItemApi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.isu_swap.main.model.Item;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PurchasePageActivity extends AppCompatActivity {
    final long ONE_MEGABYTE = 1024 * 1024;
    int userId;
    int itemId;
    double subtotal;
    double shipping = 5.00;
    double tax = 0.06;
    ImageView itemPreview;
    TextView itemName;
    TextView itemPrice;
    TextView itemSeller;
    TextView itemSubtotal;
    TextView itemShipping;
    TextView itemTax;
    TextView itemTotal;
    RadioGroup shippingRadioGroup;
    CardView confirmBtn;
    AlertDialog.Builder confirmAlert;
    StorageReference storageRef;

    /**
     * Sets XML elements and api calls necessary for base functionality of this screen
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_page);
        getSupportActionBar().setTitle("Purchase Portal");

        userId = getIntent().getIntExtra("userId", 0);
        itemId = getIntent().getIntExtra("itemId", 0);
        System.out.println(itemId);
        itemPreview = findViewById(R.id.purchase_item_card_preview_image);
        itemName = findViewById(R.id.purchase_item_card_name);
        itemPrice = findViewById(R.id.purchase_item_card_price);
        itemSeller = findViewById(R.id.purchase_item_card_seller);
        itemSubtotal = findViewById(R.id.purchase_item_subtotal);
        itemShipping = findViewById(R.id.purchase_item_shipping);
        itemTax = findViewById(R.id.purchase_item_tax);
        itemTotal = findViewById(R.id.purchase_item_total);
        shippingRadioGroup = findViewById(R.id.radiogroup_shipping);
        confirmBtn = findViewById(R.id.purchase_item_confirm_purchase);
        confirmAlert = new AlertDialog.Builder(this);
        storageRef = FirebaseStorage.getInstance().getReference();

        GetItemApi().getListingsById(itemId).enqueue(new Callback<Item>() {
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {
                itemName.setText(response.body().getTitle());
                itemPrice.setText("$" + response.body().getPrice());
                itemSeller.setText(response.body().getLister().getUsername());
                setItemImg(response.body().getImage());
                subtotal = Double.parseDouble(response.body().getPrice());
                itemSubtotal.setText("$" + String.format("%.2f",subtotal));
                itemShipping.setText("$" + String.format("%.2f",shipping));
                itemTax.setText("$" + String.format("%.2f",(subtotal * tax)));
                itemTotal.setText("$" + String.format("%.2f",(subtotal + shipping + (subtotal * tax))));
            }

            @Override
            public void onFailure(Call<Item> call, Throwable t) {

            }
        });



        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmAlert.setTitle("Confirmation")
                        .setMessage("Are you sure you want to purchase this listing?")
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                /**
                                 * deleting for now but should be changed by demo 4
                                 * there should be a status attribute in every item
                                 * that this would change from "active" to "pending"
                                 * and once completed would change to "inactive" and
                                 * be logged in some purchase history table.
                                 */
                                GetItemApi().deleteListing(itemId).enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                        Intent intentHome = new Intent(PurchasePageActivity.this, HomepageActivity.class);
                                        intentHome.putExtra("userId", userId);
                                        startActivity(intentHome);
                                        finish();
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        })
                        .show();
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

    /**
     * Adjusts the grand total price based on what shipping is selected
     *
     * @param view
     */
    public void checkButton(View view) {
        int radioId = shippingRadioGroup.getCheckedRadioButtonId();

        if (radioId == R.id.radio_shipping_seller_delivery) {
            itemShipping.setText("$" + String.format("%.2f",shipping));
            itemTotal.setText("$" + String.format("%.2f",(subtotal + shipping + (subtotal * tax))));
        } else {
            itemShipping.setText("$0.00");
            itemTotal.setText("$" + String.format("%.2f",(subtotal + (subtotal * tax))));
        }
    }
}
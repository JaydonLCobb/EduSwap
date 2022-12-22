/**
 * Class representing the Review Form screen
 *
 * @author Jose Medina Mani
 */
package com.example.isu_swap.main.screen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ReviewActivity extends AppCompatActivity {

    /**
     * Sets XML elements and api calls necessary for base functionality of this screen
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        getSupportActionBar().setTitle("Review Form");
    }
}
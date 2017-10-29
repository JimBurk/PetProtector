package edu.orangecoastcollege.cs273.petprotector;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;

public class PetDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_detail);

        ImageView petDetailsImageView = (ImageView) findViewById(R.id.petDetailsImageView);
        TextView petDetailsNameTextView = (TextView) findViewById(R.id.petDetailsNameTextView);
        TextView petDetailsDetailsTextView = (TextView) findViewById(R.id.petDetailsDetailsTextView);
        TextView petDetailsPhoneTextView = (TextView) findViewById(R.id.petDetailsPhoneTextView);

        Intent detailsIntent = getIntent();

        String name = detailsIntent.getStringExtra("Name");
        String details = detailsIntent.getStringExtra("Details");
        String phone = detailsIntent.getStringExtra("Phone");
        Uri imageUri = Uri.parse(detailsIntent.getStringExtra("ImageURI"));

        petDetailsImageView.setImageURI(imageUri);
        petDetailsNameTextView.setText(name);
        petDetailsDetailsTextView.setText(details);
        petDetailsPhoneTextView.setText(phone);
    }
}

package edu.orangecoastcollege.cs273.petprotector;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class PetListActivity extends AppCompatActivity {
    private DBHelper db;
    private List<Pet> mPetList;
    private PetListAdapter mPetListAdapter;
    private ListView petListView;

    private ImageView petImageView;
    private Uri imageUri;

    // constants for permissions
    private static final int GRANTED = PackageManager.PERMISSION_GRANTED;
    private static final int DENIED = PackageManager.PERMISSION_DENIED;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_list);

        petImageView = (ImageView) findViewById(R.id.petImageView);

        petImageView.setImageURI(getUriFromResource(this, R.drawable.none));

        // this.deleteDatabase(DBHelper.DATABASE_NAME);
        db = new DBHelper(this);

        // Fill the copetList with all Pets from the database
        mPetList = db.getAllPets();

        // Connect the list adapter with the list
        mPetListAdapter = new PetListAdapter(this, R.layout.pet_list_item, mPetList);

        // Set the list view to use the list adapter
        petListView = (ListView) findViewById(R.id.petListView);
        petListView.setAdapter(mPetListAdapter);
    }

    public void selectPetImage(View v) {
        List<String> permsList = new ArrayList<>();

        // Check each permission individually
        int hasCameraPerm = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (hasCameraPerm == DENIED)
            permsList.add(Manifest.permission.CAMERA);

        int readStoragePerm = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (readStoragePerm == DENIED)
            permsList.add(Manifest.permission.READ_EXTERNAL_STORAGE);

        int writeStoragePerm = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (writeStoragePerm == DENIED)
            permsList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permsList.size() > 0) {
            // Convert perms list into array
            String [] permsArray = new String[permsList.size()];
            permsList.toArray(permsArray);

            // Ask user for them
            ActivityCompat.requestPermissions(this, permsArray, 1337);
        }

        // Lets make sure we have all permissions, then start Image Gallery
        if (hasCameraPerm == GRANTED && readStoragePerm == GRANTED && writeStoragePerm == GRANTED) {
            // Open the Image Gallery
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

            startActivityForResult(galleryIntent, 1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null)  {
            // data is from GalleryIntent
            imageUri = data.getData();
            petImageView.setImageURI(imageUri);
        }
    }

    public static Uri getUriFromResource(Context context, int resId) {
        Resources res = context.getResources();
        // Build a string in the form:
        // android.resources://"package"/dwawable/none;
        String uri = ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                + res.getResourcePackageName(resId) + "/"
                +res.getResourceTypeName(resId) + "/"
                +res.getResourceEntryName(resId);

        // Parse the String
        return Uri.parse(uri);
    }

    public void viewPetDetails(View view) {
        // Implement the view pet details using an Intent
        Intent detailsIntent = new Intent(this, PetDetailActivity.class);

        Pet selectedPet = (Pet) view.getTag();

        detailsIntent.putExtra("Name", selectedPet.getName());
        detailsIntent.putExtra("Details", selectedPet.getDetails());
        detailsIntent.putExtra("Phone", selectedPet.getPhone());
        detailsIntent.putExtra("ImageURI", selectedPet.getImageUri().toString());

        startActivity(detailsIntent);
    }

    public void addPet(View view) {
        EditText nameET = (EditText) findViewById(R.id.nameEditText);
        EditText detailsET = (EditText) findViewById(R.id.detailsEditText);
        EditText phoneET = (EditText) findViewById(R.id.phoneEditText);

        String nameString = nameET.getText().toString();
        String detailsString = detailsET.getText().toString();
        String phoneString = phoneET.getText().toString();

        Pet pet = new Pet(nameString, detailsString, phoneString, imageUri);

        db.addPet(pet);

        mPetList.add(pet);

        mPetListAdapter.notifyDataSetChanged();

        nameET.setText("");
        detailsET.setText("");
        phoneET.setText("");
    }

    public void clearAllPets(View view) {
        db = new DBHelper(this);
        db.deleteAllPets();
        mPetList = new ArrayList<>();
        mPetListAdapter.notifyDataSetChanged();
    }
}

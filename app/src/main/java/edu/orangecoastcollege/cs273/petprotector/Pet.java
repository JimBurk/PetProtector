package edu.orangecoastcollege.cs273.petprotector;

import android.net.Uri;

/**
 * Created by jimburk on 10/25/17.
 */

public class Pet {
    private int mId;
    private String mName;
    private String mDetails;
    private String mPhone;
    private Uri mImageUri;

    public Pet(int id, String name, String details, String phone, Uri imageUri) {
        mId = id;
        mName = name;
        mDetails = details;
        mPhone = phone;
        mImageUri = imageUri;
    }

    public Pet(String name, String details, String phone, Uri imageUri) {
        mName = name;
        mDetails = details;
        mPhone = phone;
        mImageUri = imageUri;
    }

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getDetails() {
        return mDetails;
    }

    public String getPhone() {
        return mPhone;
    }

    public Uri getImageUri() {
        return mImageUri;
    }

    public void setName(String name) {
        mName = name;
    }

    public void setDetails(String details) {
        mDetails = details;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }

    public void setImageURI(Uri imageUri) {
        mImageUri = imageUri;
    }

    public String toString() {
        return "Pet{" +
                "Name='" + mName + '\'' +
                ", Details='" + mDetails + '\'' +
                ", Phone='" + mPhone + '\'' +
                ", ImageURI='" + mImageUri + '\'' +
                '}';
    }
}
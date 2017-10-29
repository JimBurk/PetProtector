package edu.orangecoastcollege.cs273.petprotector;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jimburk on 10/26/17.
 */

public class PetListAdapter extends ArrayAdapter<Pet> {

    private Context mContext;
    private List<Pet> mPetList = new ArrayList<>();
    private int mResourceId;

    public PetListAdapter(Context c, int rId, List<Pet> pet) {
        super(c, rId, pet);

        mContext = c;
        mResourceId = rId;
        mPetList = pet;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(mResourceId, null);

        Pet selectedPet = mPetList.get(pos);

        view.setTag(selectedPet);

        ImageView listItemImageView = (ImageView) view.findViewById(R.id.petListImageView);
        TextView listItemNameTextView = (TextView) view.findViewById(R.id.petListNameTextView);
        TextView listItemDetailsTextView = (TextView) view.findViewById(R.id.petListDetailsTextView);

        listItemImageView.setImageURI(selectedPet.getImageUri());
        listItemNameTextView.setText(selectedPet.getName());
        listItemDetailsTextView.setText(selectedPet.getDetails());

        return view;
    }
}

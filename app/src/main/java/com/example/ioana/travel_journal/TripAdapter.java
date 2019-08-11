package com.example.ioana.travel_journal;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.DocumentSnapshot;

import java.io.IOException;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TripAdapter extends RecyclerView.Adapter<TripViewHolder> {
    private List<Trip> mList;
    private boolean mIsFavourite;
    Context context;

    public TripAdapter(List<Trip> lista, boolean isFavourite) {
        mList = lista;
        mIsFavourite = isFavourite;
    }

    @NonNull
    @Override
    public TripViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //cream item-ul respectiv
        View tripView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.trip_item, viewGroup, false);
        //returnam un viewHolder
        context = viewGroup.getContext();
        return new TripViewHolder(tripView);
    }

    @Override
    public void onBindViewHolder(@NonNull TripViewHolder tripViewHolder, int i) {
        Trip trip = mList.get(i);

        tripViewHolder.mTextViewDestination.setText(trip.getMDestination());
        tripViewHolder.mTextViewName.setText(trip.getMName());
        tripViewHolder.mTextViewRating.setText(trip.getMRating()+ "/5.0");

        //TODO get image
//        Bitmap tripImage = null;
//        try {
//            tripImage = MediaStore.Images.Media.getBitmap(context.getContentResolver(),Uri.parse(trip.getMPicture().toString()));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        tripViewHolder.mImageViewPicture.setImageBitmap(tripImage);

//
//        // TODO check favorite state
//        if( tripViewHolder.mCheckBoxFavourite.isChecked()){
//            mIsFavourite = true;
//        }
        //TODO set field is favorite in FIREBASE
//        if(mIsFavourite){
//            tripViewHolder.mCheckBoxFavourite.setVisibility(View.INVISIBLE);
//        }
//        else{
//    -----> getmFavorite();
//            tripViewHolder.mCheckBoxFavourite.setChecked((boolean)trip.get("isFavourite"));
//        }


        /*byte[] decodedBytes = Base64.decode(trip.getmPicture(), 0);
        Bitmap tripImage = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);*/

       /* tripViewHolder.mTextViewName.setText(trip.getMName());
        Bitmap tripImage = null;
        */


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public Trip getItemAtPosition(int position) {
        return this.mList.get(position);
    }
}

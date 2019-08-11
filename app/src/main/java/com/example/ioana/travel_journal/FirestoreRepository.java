package com.example.ioana.travel_journal;

import android.content.Context;
import android.content.Intent;
import android.graphics.Picture;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;

public class FirestoreRepository {
    public static final String TRIPS_COLLECTION = "Trips";
    private static final String DESTINATION = "destination";
    private static final String ID = "id";
    private static final String ENDDATE = "endDate";
    private static final String PRICE = "price";
    private static final String RATING = "rating";
    private static final String STARTDATE = "startDate";
    private static final String TRIPTYPE = "tripType";
    private static final String PICTURE = "picture";
    private static final String NAME = "name";
   // private static final String ISFAVORITE = "isFavorite";  //TODO GET ITS VALUE
    private FirebaseFirestore mFirebaseFirestore;
    private Context mContext;

    public FirestoreRepository(Context context) {
        mFirebaseFirestore = FirebaseFirestore.getInstance();
        mContext = context;
    }

    public void addTrip(Trip item) {
        Map<String, Object> theItem = new HashMap<>();
        theItem.put(DESTINATION, item.getMDestination());
        theItem.put(ID,item.getMDocumentId());
        theItem.put(ENDDATE,item.getMEndDate());
        theItem.put(PICTURE,item.getMPicture());
        theItem.put(PRICE,item.getMPrice());
        theItem.put(RATING,item.getMRating());
        theItem.put(STARTDATE,item.getMStartDate());
        theItem.put(TRIPTYPE,item.getMTripType());
        theItem.put(NAME, item.getMName());
        //..... put pt toate valorile

        //TODO put isFavorite
       // theItem.put(ISFAVORITE,item.getmIsFavourite());


        mFirebaseFirestore.collection(TRIPS_COLLECTION)
                .add(theItem)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        displayMessage(
                                "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        displayMessage("Error adding document");
                    }
                });
    }


    public void updateTrip(Trip item, String id) {     //TODO think we need a new method for update
        Map<String, Object> theItem = new HashMap<>();
        theItem.put(DESTINATION, item.getMDestination());
        //theItem.put(ID,item.getMDocumentId());
        theItem.put(ENDDATE,item.getMEndDate());
        theItem.put(PICTURE,item.getMPicture());
        theItem.put(PRICE,item.getMPrice());
        theItem.put(RATING,item.getMRating());
        theItem.put(STARTDATE,item.getMStartDate());
        theItem.put(TRIPTYPE,item.getMTripType());
        theItem.put(NAME, item.getMName());
        //..... put pt toate valorile

        //TODO put isFavorite
        // theItem.put(ISFAVORITE,item.getmIsFavourite());


        if (mFirebaseFirestore.collection(TRIPS_COLLECTION)
               .document().getId().equals(id)){
            mFirebaseFirestore.collection(TRIPS_COLLECTION)
                    .document()
                    .update( theItem);
        }
    }

    private void displayMessage(String s) {
        Toast.makeText(mContext, s, Toast.LENGTH_SHORT).show();
    }

    public List<Trip> getTrips() {
        final List<Trip> trips = new ArrayList<>();
        mFirebaseFirestore.collection(TRIPS_COLLECTION)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Trip trip = new Trip();
                                trip.setMDestination(document.getData().get("destination")
                                        .toString());
                               // trip.setMName(document.getData().get("name").toString());
                                //trip.setMPrice(document.getData().get("price"));

                                trips.add(trip);
                            }
                        } else {
                            displayMessage("Error getting documents.");
                        }
                    }
                });
        return trips;
    }
}

package com.example.ioana.travel_journal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

import static com.example.ioana.travel_journal.Trip.TripType.CITY_BREAK;
import static com.example.ioana.travel_journal.Trip.TripType.MOUNTAINS;
import static com.example.ioana.travel_journal.Trip.TripType.SEA_SIDE;
import static com.google.android.material.internal.ContextUtils.getActivity;

public class DetailsTripActivity extends AppCompatActivity {
    private static final String TAG = "ADDTRIP";
    private TextView mTextViewName;
    private TextView mTextViewDestination;
    private TextView mTextViewType;
    private RatingBar mRatingBar;
    private TextView mTextViewPrice;
    private TextView mTextViewStarDate;
    private TextView mTextViewEndDate;
    private ImageView mImageViewPicture;
    private Button mButtonBack;
    private Trip trip;
    Intent intent;
    Bundle bundle;
    String currentTripId = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_trip);

        initView();
        intent = getIntent();

        /// this is done when i press long on a trip from home to see its details
        bundle = intent.getExtras();
        if (bundle != null) {
            currentTripId = bundle.getString(HomeFragment.TRIP_ID);
            FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
            firebaseFirestore.collection("Trips").document(currentTripId)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                    trip = getTrip(document);
                                    updateUI();
                                } else {
                                    Log.d(TAG, "No such document");
                                }
                            } else {
                                Log.d(TAG, "get failed with ", task.getException());
                            }
                        }
                    });

            mTextViewName.setText(trip.getMName());
            mTextViewDestination.setText(trip.getMDestination());
            mTextViewType.setText(trip.getMTripType().toString());
            mRatingBar.setRating(trip.getMRating());
            mImageViewPicture.setImageURI(trip.getMPicture());
            mTextViewPrice.setText(getString(R.string.price) + trip.getMPrice() + getString(R.string.eur));
            Calendar startDate = trip.getMStartDate();
            mTextViewStarDate.setText(getString(R.string.start) + startDate.get(Calendar.DAY_OF_MONTH) + "/" + (1 + startDate.get(Calendar.MONTH)) + "/" + startDate.get(Calendar.YEAR));
            Calendar endDate = trip.getMEndDate();
            mTextViewEndDate.setText(getString(R.string.end)+ endDate.get(Calendar.DAY_OF_MONTH) + "/" + (1 + endDate.get(Calendar.MONTH)) + "/" + endDate.get(Calendar.YEAR));
            mButtonBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
            trip = new Trip();

        }








    }
    private void updateUI () {
        mTextViewDestination.setText(trip.getMDestination());
        mTextViewName.setText(trip.getMName());
        mTextViewType.setText(trip.getMTripType().toString());
//        imageViewTrip.setVisibility(View.VISIBLE);
//        try {
//            imagine = MediaStore.Images.Media.getBitmap(this.getContentResolver(),
//                    trip.getMPicture());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
 //       imageViewTrip.setImageBitmap(imagine);
        //TODO check here
      //  mTextViewStarDate = toString(trip.getMStartDate());
        // calendarEnd = trip.getMEndDate();
    }

    private Trip getTrip (DocumentSnapshot trip){
        Trip newTrip = new Trip();
        newTrip.setMName((String) trip.get("name"));
        newTrip.setMDestination((String) trip.get("destination"));
        newTrip.setMPrice(((Double) trip.get("price")).floatValue());
        newTrip.setMRating(((Double) trip.get("rating")).floatValue());
        newTrip.setMTripType(Trip.TripType.valueOf(trip.get("tripType").toString()));
        //TODO check here
        //newTrip.setMPicture(Uri.parse((String) trip.get("picture")));
        /*Calendar startDate = new GregorianCalendar();
        startDate.setTimeInMillis((long) trip.get("startDate"));
        newTrip.setMStartDate(startDate);
        Calendar endDate = new GregorianCalendar();
        startDate.setTimeInMillis((long) trip.get("endDate"));
        newTrip.setMEndDate(endDate);*/
        newTrip.setMDocumentId((String) trip.get("documentId"));
        return newTrip;
    }

    private void initView(){
        mTextViewName = findViewById(R.id.text_view_name);
        mTextViewDestination = findViewById(R.id.text_view_destination);
        mTextViewPrice = findViewById(R.id.textview_price);
        mRatingBar = findViewById(R.id.ratingbar_detail);
        mTextViewType = findViewById(R.id.textview_type);
        mTextViewStarDate = findViewById(R.id.textview_start_date);
        mTextViewEndDate = findViewById(R.id.textView_end_date);
        mButtonBack = findViewById(R.id.button_back);
        mImageViewPicture = findViewById(R.id.imageview_trip_detail);
    }
    public void setTrip(Trip trip){
        this.trip = trip;
    }
}

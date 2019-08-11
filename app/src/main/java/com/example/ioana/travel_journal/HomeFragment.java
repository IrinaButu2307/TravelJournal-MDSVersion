package com.example.ioana.travel_journal;


import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.example.ioana.travel_journal.FirestoreRepository.TRIPS_COLLECTION;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    public static final String TRIP_ID = "trip_id";
    private RecyclerView mRecyclerView;
    private List<Trip> mData;
    private String mDocumentID;
    private static final int REQUEST_CODE = 5123;
    TripAdapter adapter;
    FirebaseFirestore firebaseFirestore;
    private boolean check = false;
    private FirestoreRepository mFirebaseRepository;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mData = new ArrayList<>();

        mFirebaseRepository = new FirestoreRepository(getActivity());

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //appDatabase = AppDatabase.getAppDatabase(getActivity().getApplicationContext());

        mRecyclerView = view.findViewById(R.id.recyclerview_trips);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        //ca parametru primeste contextul activitatii
        mRecyclerView.setLayoutManager(layoutManager);

       getTrips();

        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(this.getActivity(),
                mRecyclerView, new TripClickListener() {

            @Override
            public void onLongClick(View view, final int position) {
                String tripId = mData.get(position).getMDocumentId();
                Intent intent = new Intent(getActivity(), DetailsTripActivity.class);
                intent.putExtra(TRIP_ID, tripId);
                startActivity(intent);

            }

            @Override
            public void onClick(View view, final int position) {

                String tripId = mData.get(position).getMDocumentId();
                Intent intent = new Intent(getActivity(), AddTripActivity.class);
                intent.putExtra(TRIP_ID, tripId);
                startActivity(intent);

                //TODO add favorite
                final CheckBox checkBoxFavourite = view.findViewById(R.id.checkbox_bookmark);
                checkBoxFavourite.requestFocus();
                checkBoxFavourite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        check = true;
                        /*DocumentSnapshot trip = ((TripAdapter)mRecyclerView.getAdapter()).getItemAtPosition(position);
                        final DocumentReference ref = db.collection("users").document(mDocumentID).collection("trips").document(trip.getId());
                        final Trip favourite = getTrip(trip);
                        if(checkBoxFavourite.isChecked() == true){

                            ref.update("isFavourite",true);
                            AsyncTask.execute(new Runnable() {
                                @Override
                                public void run() {
                                    // Insert Data
                                    appDatabase.tripDao().insertTrip(favourite);
                                    List<Trip> listOfTrips = appDatabase.tripDao().getAllTrips();
                                    Log.i("ListOfTrips",listOfTrips.toString());
                                }
                            });
                        }
                        else{
                            ref.update("isFavourite",false);
                            AsyncTask.execute(new Runnable() {
                                @Override
                                public void run() {
                                    // Delete Data
                                    appDatabase.tripDao().deleteTrip(favourite);
                                    List<Trip> listOfTrips = appDatabase.tripDao().getAllTrips();
                                    Log.i("ListOfTrips",listOfTrips.toString());
                                }
                            });
                        }*/
                        return;
                    }

                });
                if (check == false) {
                    /*
                    DocumentSnapshot trip = ((TripAdapter)mRecyclerView.getAdapter()).getItemAtPosition(position);
                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    TripDetailFragment fragment = new TripDetailFragment();
                    fragment.setTrip(getTrip(trip));
                    transaction.replace(R.id.fragment_container,fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();*/
                } else {
                    check = false;
                    return;
                }
            }

//            @Override
//            public void onLongClick(View view, int position) {
//                /*Intent intent = new Intent(getActivity(),AddTripActivity.class);
//                DocumentSnapshot trip = ((TripAdapter)mRecyclerView.getAdapter()).getItemAtPosition(position);
//                Trip newTrip = getTrip(trip);
//                intent.putExtra("trip",newTrip);
//                getActivity().startActivityForResult(intent,REQUEST_CODE);*/
//                //Toast.makeText(getActivity().getApplicationContext(),position + " " + newTrip,Toast.LENGTH_LONG).show();
//            }
        }));

        return view;
    }


    private List<Trip> getData() {
        return mFirebaseRepository.getTrips();
    }

    public void setmDocumentID(String documentID) {
        this.mDocumentID = documentID;
    }


    public List<Trip> getTrips() {
        final List<Trip> trips = new ArrayList<>();
        FirebaseFirestore.getInstance().collection(FirestoreRepository.TRIPS_COLLECTION)
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
                                trip.setMPrice(((Double)(document.getData().get("price")))
                                        .floatValue());
                                trip.setMDocumentId(document.getId());
                               // trip.setMTripType(Trip.TripType.valueOf(document.getData().get("tripType").toString()));
//                                trip.setMPicture(Uri.parse((String)document.getData()
//                                        .get("picture")));
                                trip.setMRating(((Double)(document.getData().get("rating")))
                                        .floatValue());
                                trips.add(trip);
                            }
                            mData = trips;
                            adapter = new TripAdapter(mData, false);
                            mRecyclerView.setAdapter(adapter);

                            adapter.notifyDataSetChanged();
                        }
                    }
                });
        return trips;
    }


}

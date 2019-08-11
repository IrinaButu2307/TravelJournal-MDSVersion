package com.example.ioana.travel_journal;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavouriteFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private List<Trip> mData = new ArrayList<>();
    private String mDocumentID;
    TripAdapter adapter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    public FavouriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favourite, container, false);
        mRecyclerView = view.findViewById(R.id.recyclerview_favourite);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getActivity()); //ca parametru primeste contextul activitatii
        mRecyclerView.setLayoutManager(layoutManager);
        Query trips = db.collection("users").document(mDocumentID).collection("trips").whereEqualTo("isFavourite",true);
        trips.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for(QueryDocumentSnapshot trip : task.getResult()){
                       // mData.add(trip);
                    }
                    adapter = new TripAdapter(mData,true);

                    mRecyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }
        });
        return view;
    }

    public void setmDocumentID(String documentID) {
        this.mDocumentID = documentID;
    }

}

package com.example.ioana.travel_journal;

import android.view.View;

public interface TripClickListener {
    void onClick(View view, int position);
    void onLongClick(View view, int position);
}

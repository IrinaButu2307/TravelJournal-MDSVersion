package com.example.ioana.travel_journal;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.Calendar;

import androidx.fragment.app.DialogFragment;

public class CustomDatePickerFragment extends DialogFragment {
        private Button button;

        public Button getButton() {
            return button;
        }

        public void setButton(Button button) {
            this.button = button;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), dateSetListener, year, month, day);
        }

        private DatePickerDialog.OnDateSetListener dateSetListener =
                new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        button.setText(view.getYear() +
                                " / " + (view.getMonth() + 1) +
                                " / " + view.getDayOfMonth());
                        Toast.makeText(getActivity(), "The selected date is " + view.getYear() +
                                " / " + (view.getMonth() + 1) +
                                " / " + view.getDayOfMonth(), Toast.LENGTH_SHORT).show();
                    }
                };
}


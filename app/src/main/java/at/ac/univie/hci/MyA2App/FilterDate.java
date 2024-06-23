package at.ac.univie.hci.MyA2App;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.SearchView;


import androidx.annotation.NonNull;

import java.util.Calendar;

public class FilterDate extends Dialog {

    private NumberPicker numberPickerYear;
    private Button buttonSelectYear;
    private OnYearSelectedListener listener;

    public FilterDate(@NonNull Context context, OnYearSelectedListener listener) {
        super(context);
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_layout_date);


        SearchView searchView = findViewById(R.id.SearchViewDateID);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //make the number-query react to submitting
                Intent intent = new Intent(getContext(), ResultsDate.class);
                String stringSelectedYear = String.valueOf(query);
                intent.putExtra("date_name", stringSelectedYear);
                getContext().startActivity(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {return false;}
        });

        numberPickerYear = findViewById(R.id.numberPickerYear);
        buttonSelectYear = findViewById(R.id.buttonSelectYear);

        // Configure NumberPicker to display years
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        numberPickerYear.setMinValue(0); // Min year
        numberPickerYear.setMaxValue(currentYear); // Max year
        numberPickerYear.setValue(currentYear); // Default to current year
        buttonSelectYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //react while clicking the select-button
                int selectedYear = numberPickerYear.getValue();
                if (listener != null) {
                    listener.onYearSelected(selectedYear);
                }
                dismiss();
            }
        });
    }
    public interface OnYearSelectedListener {
        void onYearSelected(int year);
    }
}

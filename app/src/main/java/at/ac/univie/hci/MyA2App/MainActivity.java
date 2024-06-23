package at.ac.univie.hci.MyA2App;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.SearchView;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_activity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        //Searchbar config.
        final SearchView searchView = findViewById(R.id.searchViewID);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getApplicationContext(),"Searching for:" + query, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, ResultsQuery.class);
                intent.putExtra("Suche",query);
                startActivity(intent);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }

    //Artist filter button config.
    public void artistFilterButton(View view){FilterArtist.showPopup(MainActivity.this,view);}
    //gallery filter button config.
    public void galleryFilterButton(View view){FilterGallery.showPopup(MainActivity.this,view);}
    //Year filter button config.
    public void showYearPickerDialog(View view) {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        FilterDate dialog = new FilterDate(MainActivity.this, selectedYear -> {
            Toast.makeText(MainActivity.this, "Selected year: " + selectedYear, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(view.getContext(), ResultsDate.class);
            String stringSelectedYear = String.valueOf(selectedYear);
            intent.putExtra("date_name", stringSelectedYear);
            view.getContext().startActivity(intent);
        });
        dialog.show();
    }



}


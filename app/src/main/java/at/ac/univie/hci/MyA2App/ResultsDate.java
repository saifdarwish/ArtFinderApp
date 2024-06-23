package at.ac.univie.hci.MyA2App;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ResultsDate extends AppCompatActivity {

    public static int page=1;
    public List<Artwork> artworksData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_results_date);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_activity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        //Get date-selected and search artworks related to the selected-date
        Intent intent = getIntent();
        String date = intent.getStringExtra("date_name");
        TextView textView = findViewById(R.id.DateFilterID);
        textView.setText("Results for year: \n\n" + date);
        artworksData = API_Calls.filterSelectedDate(date, page);
        if (artworksData.isEmpty()) {
            if(page>1){
                textView.setText("No more results for \n" + date);
                TextView currPage=findViewById(R.id.dateCurrentPageID);
                currPage.setText("Page: "+page);
            }else{
            textView.setText("No results for \n" + date + "\nTry again with \nanother input .");
            TextView currPage=findViewById(R.id.dateCurrentPageID);
            currPage.setText("Page: "+page);
            }
        }else {
            //put results in a recyclerview
            RecyclerView recyclerView = findViewById(R.id.DateRecyclerID);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(new ArtworkAdapter(getApplicationContext(), artworksData));
            TextView currPage=findViewById(R.id.dateCurrentPageID);
            currPage.setText("Page: "+page);
        }
    }


    //page control logic
    public void dateNextPageButton(View view){
        if(artworksData.isEmpty()){
            Toast.makeText(this, "Last page reached.", Toast.LENGTH_SHORT).show();
        }else {
            ++page;
            recreate();
            Toast.makeText(this, "Next to page " + page, Toast.LENGTH_SHORT).show();
            TextView currPage=findViewById(R.id.dateCurrentPageID);
            currPage.setText("Page: "+page);
        }
    }
    public void datePreviousPageButton(View view){
        if(page>1){
            --page;
            recreate();
            Toast.makeText(this, "Back to page "+page, Toast.LENGTH_SHORT).show();
            TextView currPage=findViewById(R.id.dateCurrentPageID);
            currPage.setText("Page: "+page);
        }
        else{
            Toast.makeText(this, "No previous page available.", Toast.LENGTH_SHORT).show();
        }
    }

    //home button
    public void navigateToHome(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        page=1;
        finish();
    }
}
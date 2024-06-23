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

public class ResultsQuery extends AppCompatActivity {
    public static int page=1;
    public List<Artwork> artworkList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_results_query);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_activity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //get query selected and search artworks related to the query
        Intent intent = getIntent();
        String display = intent.getStringExtra("Suche");
        TextView textView = findViewById(R.id.SearchFilterID);
             artworkList = API_Calls.fetchQueryData(display, page);
            textView.setText("Results for: \n\n" + display);
            if (artworkList.isEmpty()) {
                if(page>1){
                    textView.setText("No more results for \n" + display);
                    TextView currPage=findViewById(R.id.queryCurrentPageID);
                    currPage.setText("Page: "+page);
                }else{
                textView.setText("No results for \n" + display + "\nTry again with \nanother input .");
                TextView currPage=findViewById(R.id.queryCurrentPageID);
                currPage.setText("Page: "+page);
                }
            } else {
                //put results in a recyclerview
                RecyclerView recyclerView = findViewById(R.id.recyclerID);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                recyclerView.setAdapter(new ArtworkAdapter(getApplicationContext(), artworkList));
                TextView currPage=findViewById(R.id.queryCurrentPageID);
                currPage.setText("Page: "+page);
            }

    }

    //page control logic
    public void queryNextPage(View view){
        if(artworkList.isEmpty()){
            Toast.makeText(this, "Last page reached.", Toast.LENGTH_SHORT).show();
        }else {
            ++page;
            recreate();
            Toast.makeText(this, "Next to page" + page, Toast.LENGTH_SHORT).show();
            TextView currPage = findViewById(R.id.queryCurrentPageID);
            currPage.setText("Page: " + page);
        }
    }
    public void queryPreviousPage(View view){
        if(page>1){
            --page;
            recreate();
            Toast.makeText(this, "Back to page "+page, Toast.LENGTH_SHORT).show();
            TextView currPage=findViewById(R.id.queryCurrentPageID);
            currPage.setText("Page: " + page);
        }
        else{
            Toast.makeText(this, "This is the first page.", Toast.LENGTH_SHORT).show();
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
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

public class ResultsArtist extends AppCompatActivity {
    public static int page=1;
    public List<Artwork> artworksData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_results_artist);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_activity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
//get artist selected and search artworks related to
        Intent intent = getIntent();
        String artistSelected = intent.getStringExtra("artist_name");
        int artist_id = intent.getIntExtra("artist_id",0);
        TextView textView = findViewById(R.id.ArtistFilterID);
        ArtistOrGallery artist = new ArtistOrGallery(artistSelected,artist_id);
        textView.setText("Results for: \n\n" + artist.getName());
        artworksData = API_Calls.filterArtistsWorks(artist, page);
        if (artworksData.isEmpty()) {
            if (page > 1) {
                textView.setText("No more results for \n" + artist.getName());
                TextView currPage = findViewById(R.id.artistCurrentPageID);
                currPage.setText("Page: " + page);
            } else {
                textView.setText("No results for \n" + artist.getName() + "\nTry again with \nanother input .");
                TextView currPage = findViewById(R.id.artistCurrentPageID);
                currPage.setText("Page: " + page);
            }
        } else {
            //put results in a recyclerview
        RecyclerView recyclerView = findViewById(R.id.DateRecyclerID);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ArtworkAdapter(getApplicationContext(), artworksData));
        TextView currPage=findViewById(R.id.artistCurrentPageID);
        currPage.setText("Page: "+String.valueOf(page));
        }
    }


//page control logic
    public void nextArtistPage(View view){
        if(artworksData.isEmpty()){
            Toast.makeText(this, "Last page reached."+page, Toast.LENGTH_SHORT).show();
        }else {
            ++page;
            recreate();
            Toast.makeText(this, "Next to page " + page, Toast.LENGTH_SHORT).show();
            TextView currPage=findViewById(R.id.artistCurrentPageID);
            currPage.setText("Page: " + page);
        }
    }
    public void previousArtistPage(View view){
        if(page>1){
            --page;
            recreate();
            Toast.makeText(this, "Back to page "+page, Toast.LENGTH_SHORT).show();
            TextView currPage=findViewById(R.id.artistCurrentPageID);
            currPage.setText("Page: " + page);
        }
        else{
            Toast.makeText(this, "No previous page available", Toast.LENGTH_SHORT).show();
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
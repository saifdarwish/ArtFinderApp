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

public class ResultsGallery extends AppCompatActivity {
    public static int page=1;
    public List<Artwork> galleryData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_results_gallery);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_activity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
// get the gallery selected and search artworks related to it
        Intent intent = getIntent();
        String gallerySelected = intent.getStringExtra("gallery_name");
        int gallery_id = intent.getIntExtra("gallery_id",0);
        TextView textView = findViewById(R.id.GalleryFilterID);
        ArtistOrGallery gallery = new ArtistOrGallery(gallerySelected,gallery_id);
        textView.setText("Results for: \n\n" + gallery.getName());
        galleryData = API_Calls.filterGallerysWorks(gallery, page);
        if (galleryData.isEmpty()) {
            if(page>1){
                textView.setText("No more results for \n" + gallery.getName());
                TextView currPage=findViewById(R.id.galleryCurrentPageID);
                currPage.setText("Page: "+page);
            }else{
            textView.setText("No results for \n" + gallery.getName() + "\nTry again with \nanother input .");
            TextView currPage=findViewById(R.id.galleryCurrentPageID);
            currPage.setText("Page: "+page);
            }
        }
        else{
         //put results in a recyclerview
        RecyclerView recyclerView = findViewById(R.id.GalleryRecyclerID);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ArtworkAdapter(getApplicationContext(), galleryData));
        TextView currPage=findViewById(R.id.galleryCurrentPageID);
        currPage.setText("Page: "+page);
        }

        }


//page control logic
    public void galleryNextPage(View view){
        if(galleryData.isEmpty()){
            Toast.makeText(this, "Last page reached.", Toast.LENGTH_SHORT).show();
        }else{
        ++page;
        recreate();
        Toast.makeText(this, "Next to page "+page, Toast.LENGTH_SHORT).show();
        TextView currPage=findViewById(R.id.galleryCurrentPageID);
        currPage.setText("Page: "+page);
        }
    }
    public void galleryPreviousPage(View view){
        if(page>1){
            --page;
            recreate();
            Toast.makeText(this, "Back to page "+page, Toast.LENGTH_SHORT).show();
            TextView currPage=findViewById(R.id.galleryCurrentPageID);
            currPage.setText("Page: "+page);
        }
        else{
            Toast.makeText(this, "This is the first page", Toast.LENGTH_SHORT).show();
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
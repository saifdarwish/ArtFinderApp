package at.ac.univie.hci.MyA2App;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class DetailsOfArtwork extends AppCompatActivity {

    @SuppressLint({"ClickableViewAccessibility", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_details_of_artwork);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //catch input from a chosen artwork and use the attributes for details
        Intent intent = getIntent();
        String imageURl = intent.getStringExtra("image");
        String title = intent.getStringExtra("title");
        String artist = intent.getStringExtra("artist");
        String description = intent.getStringExtra("description");
        String year = intent.getStringExtra("year");
        String medium = intent.getStringExtra("medium");
        String dimensions = intent.getStringExtra("dimensions");
        String gallery = intent.getStringExtra("gallery");

        TextView OneTitle = findViewById(R.id.OneTitleID);
        TextView OneArtist = findViewById(R.id.OneArtistID);
        TextView OneDescription = findViewById(R.id.OneDescriptionID);
        ImageView OneImage = findViewById(R.id.OneImageID);
        TextView OneYear = findViewById(R.id.OneYearID);
        TextView OneMedium = findViewById(R.id.OneMediumID);
        TextView OneDimensions = findViewById(R.id.OneDimensionsID);
        TextView OneGallery = findViewById(R.id.OneGalleryID);

        OneTitle.setText("Title: "+title);
        OneArtist.setText("Artist: "+artist);
        OneDescription.setText("Description: "+description);
        API_Calls.loadImageAsync(imageURl,OneImage);
        OneYear.setText("Date display: "+year);
        OneMedium.setText("Medium: "+medium);
        OneDimensions.setText("Dimensions: "+dimensions);
        OneGallery.setText("Gallery: "+gallery);



        //Make zoomable pictures zoomable
        /*OneImage.setOnTouchListener(new View.OnTouchListener() {
            private float scale = 1f;
            private final ScaleGestureDetector detector = new ScaleGestureDetector(getApplicationContext(),
                    new ScaleGestureDetector.SimpleOnScaleGestureListener() {
                        @Override
                        public boolean onScale(@NonNull ScaleGestureDetector detector) {
                            scale *= detector.getScaleFactor();
                            scale = Math.max(0.1f, Math.min(scale, 5.0f));
                            OneImage.setScaleX(scale);
                            OneImage.setScaleY(scale);
                            return true;
                        }
                    });
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                detector.onTouchEvent(motionEvent);
                return true;
            }
        });*/

        ScaleGestureDetector scaleGestureDetector = new ScaleGestureDetector(this,
                new ScaleGestureDetector.SimpleOnScaleGestureListener() {
                    @Override
                    public boolean onScale(ScaleGestureDetector detector) {
                        float scaleFactor = detector.getScaleFactor();
                        OneImage.setScaleX(OneImage.getScaleX() * scaleFactor);
                        OneImage.setScaleY(OneImage.getScaleY() * scaleFactor);
                        return true;
                    }
                });

        OneImage.setOnTouchListener((view, event) -> {
            scaleGestureDetector.onTouchEvent(event);
            if (event.getPointerCount() == 1 && OneImage.getScaleX() > 1.0f) {
                float offsetX = event.getX() - view.getWidth() / 2f;
                float offsetY = event.getY() - view.getHeight() / 2f;
                OneImage.setTranslationX(offsetX);
                OneImage.setTranslationY(offsetY);
            }
            return true;
        });






    }


    //home button
    public void navigateToHome(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        ResultsQuery.page=1;
        ResultsDate.page=1;
        ResultsGallery.page=1;
        ResultsArtist.page=1;
        finish();

    }

}
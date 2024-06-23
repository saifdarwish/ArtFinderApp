package at.ac.univie.hci.MyA2App;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.List;

public class FilterGallery {
    public static PopupWindow popupWindow;
    public static ListView galleryListView;
    public static ArrayAdapter<ArtistOrGallery> galleryAdapter;
    public static List<ArtistOrGallery> galleryList;

    public static void showPopup(Context context, View anchorView) {

        View popupViewArtist = LayoutInflater.from(context).inflate(R.layout.item_layout_gallery, null);
        popupWindow = new PopupWindow(popupViewArtist,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.showAsDropDown(anchorView);
        TextView galleryCounter=popupViewArtist.findViewById(R.id.galleryCounterID);

        galleryList=API_Calls.searchForGalleries(1);
        galleryCounter.setText("Results:100/180");
        Button pageLoad=popupViewArtist.findViewById(R.id.loadMoreID);
        pageLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(galleryCounter.getText().equals("Results:100/180")){
                    pageLoad.setText("Previous results");
                    galleryCounter.setText("Results:180/180");
                    List<ArtistOrGallery>newGalleryList=API_Calls.searchForGalleries(2);
                    updatePopup(newGalleryList);
                } else if (galleryCounter.getText().equals("Results:180/180")) {
                    pageLoad.setText("Load more");
                    galleryCounter.setText("Results:100/180");
                    List<ArtistOrGallery>newGalleryList=API_Calls.searchForGalleries(1);
                    updatePopup(newGalleryList);
                }
            }
        });
        galleryListView = popupViewArtist.findViewById(R.id.galleryListViewID);
        galleryAdapter = new ArrayAdapter<>(anchorView.getContext(),
                android.R.layout.simple_list_item_1, galleryList);
        galleryListView.setAdapter(galleryAdapter);

        galleryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArtistOrGallery selectedItem = (ArtistOrGallery) parent.getItemAtPosition(position);
                Intent intent = new Intent(view.getContext(), ResultsGallery.class);
                intent.putExtra("gallery_name", selectedItem.getName());
                intent.putExtra("gallery_id", selectedItem.getId());
                view.getContext().startActivity(intent);
            }
        });



    }

    public static void updatePopup(List<ArtistOrGallery> updatedGalleryList) {
        galleryList.clear();
        galleryList.addAll(updatedGalleryList);
        galleryAdapter.notifyDataSetChanged();
    }


}
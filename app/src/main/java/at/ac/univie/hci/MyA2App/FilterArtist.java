package at.ac.univie.hci.MyA2App;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class FilterArtist {
    private static PopupWindow popupWindow;
    private static ListView artistListView;
    private static ArrayAdapter<ArtistOrGallery> artistAdapter;

    public static List<ArtistOrGallery> artistList;
    private static SearchView searchView;

    public static void showPopup(Context context, View anchorView) {

        View popupViewArtist = LayoutInflater.from(context).inflate(R.layout.item_layout_artist, null);
        popupWindow = new PopupWindow(popupViewArtist,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        popupWindow.showAsDropDown(anchorView);

        artistList=new ArrayList<>();
        searchView = popupViewArtist.findViewById(R.id.artistSearchViewID);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Load artists-suggestions asynchronous to keep the main UI fast and fluid
                CompletableFuture.supplyAsync(() -> API_Calls.searchForArtists(newText))
                        .thenAcceptAsync(updatedArtistList -> {
                            // Update UI on the main (UI) thread
                            Handler mainHandler = new Handler(Looper.getMainLooper());
                            mainHandler.post(() -> {
                                FilterArtist.updatePopup(updatedArtistList);
                            });
                        });
                return true;
            }
        });

        artistListView = popupViewArtist.findViewById(R.id.ArtistListViewID);
        artistAdapter = new ArrayAdapter<>(anchorView.getContext(),
                android.R.layout.simple_list_item_1, artistList);
        artistListView.setAdapter(artistAdapter);

        artistListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //make artists clickable
                ArtistOrGallery selectedItem = (ArtistOrGallery) parent.getItemAtPosition(position);
                Intent intent = new Intent(view.getContext(), ResultsArtist.class);
                intent.putExtra("artist_name", selectedItem.getName());
                intent.putExtra("artist_id", selectedItem.getId());
                view.getContext().startActivity(intent);
            }
        });



    }

    public static void updatePopup(List<ArtistOrGallery> updatedArtistList) {
        artistList.clear();
        artistList.addAll(updatedArtistList);
        artistAdapter.notifyDataSetChanged();
    }


}

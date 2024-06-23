package at.ac.univie.hci.MyA2App;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


//simple Adapter for the artwork-recyclerview
public class ArtworkAdapter extends RecyclerView.Adapter<ArtworkViewHolder> {
    Context context;
    public List<Artwork> dataList;

    public ArtworkAdapter(Context context, List<Artwork> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    public ArtworkAdapter(List<Artwork> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ArtworkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ArtworkViewHolder(LayoutInflater.from(context).inflate(R.layout.item_layout_query,
                parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ArtworkViewHolder holder, int position) {
        Artwork artwork = dataList.get(position);
        holder.titleID.setText(dataList.get(position).getTitle());
        API_Calls.loadImageAsync(artwork.getImageURL(),holder.imageID);
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
             //set artwork on click-listener and send the infos to the details-page
            Artwork artworksDataTmp = dataList.get(position);
            Intent intent = new Intent(context, DetailsOfArtwork.class);
            intent.putExtra("image", artworksDataTmp.getImageURL());
            intent.putExtra("title", artworksDataTmp.getTitle());
            intent.putExtra("artist", artworksDataTmp.getArtist());
            intent.putExtra("description", artworksDataTmp.getDescription());
            intent.putExtra("year", artworksDataTmp.getYear());
            intent.putExtra("medium",artworksDataTmp.getMedium());
            intent.putExtra("dimensions",artworksDataTmp.getDimensions());
            intent.putExtra("gallery",artworksDataTmp.getGallery());
            context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

}
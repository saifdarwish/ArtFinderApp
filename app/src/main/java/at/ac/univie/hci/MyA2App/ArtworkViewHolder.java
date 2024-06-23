package at.ac.univie.hci.MyA2App;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


//simple viewholder for the artwork-recyclerview
public class ArtworkViewHolder extends RecyclerView.ViewHolder {
    ImageView imageID;
    TextView titleID;


    ArtworkViewHolder(@NonNull View itemView) {
        super(itemView);
        imageID = itemView.findViewById(R.id.imageID);
        titleID = itemView.findViewById(R.id.titleID);
    }
}

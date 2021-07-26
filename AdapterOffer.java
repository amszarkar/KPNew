package velociter.kumar.property;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import velociter.kumar.property.data.Offer;

import java.util.ArrayList;

public class AdapterOffer extends RecyclerView.Adapter<AdapterOffer.ViewHolder> {

    ArrayList<Offer> offerList;
    Context context;

    public AdapterOffer(ArrayList<Offer> offerList, Context context) {
        this.offerList = offerList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.offer_view,viewGroup,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Offer offer=offerList.get(i);

        Picasso.get().load(offer.getOffer_image()).into(viewHolder.imageView);


    }

    @Override
    public int getItemCount() {
        return offerList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imgOffer);
        }
    }
}

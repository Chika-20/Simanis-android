package com.simanisandroid.simanis.Adapter;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.simanisandroid.simanis.DetailActivity;
import com.simanisandroid.simanis.Model.PasienModel;
import com.simanisandroid.simanis.R;

import java.util.List;

public class PasienAdapter extends FirebaseRecyclerAdapter<PasienModel, PasienAdapter.PasienHolder> implements Filterable {

    public PasienAdapter(@NonNull FirebaseRecyclerOptions<PasienModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final PasienHolder holder, int position, @NonNull final PasienModel model) {
        //color backgrond cardview pada recycler view
        if(model.getStatus().equals("Sembuh") && model.getVol_akhir()<= 400){
            holder.cardView.setCardBackgroundColor(Color.parseColor("#ebe8e8"));
            holder.imageView.setVisibility(View.GONE);
        }
        else if(model.getStatus().equals("Sembuh")){
            holder.cardView.setCardBackgroundColor(Color.parseColor("#ebe8e8"));
            holder.imageView.setVisibility(View.GONE);
        }
         else if (model.getVol_akhir()<=400){
            holder.cardView.setCardBackgroundColor(Color.parseColor("#c1f7f7"));
            holder.imageView.setVisibility(View.VISIBLE);
        } else {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#c1f7f7"));
            holder.imageView.setVisibility(View.GONE);
        }


        holder.text_nama.setText(model.getNama());
        holder.text_ruangan.setText(model.getRuangan());
        holder.text_bangsal.setText(model.getBangsal());
        holder.text_status.setText(model.getStatus());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataSnapshot dataSnapshot = getSnapshots().getSnapshot(holder.getAdapterPosition());
                view.getContext().startActivity(new Intent(view.getContext(), DetailActivity.class).putExtra("id_pasien", dataSnapshot.getKey().toString()));
            }
        });
    }

    @Override
    public PasienHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pasien, parent, false);
        return new PasienHolder(view);
    }

    @Override
    public Filter getFilter() {
        return pasienFilter;
    }

    private Filter pasienFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            return null;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

        }
    };

    class PasienHolder extends RecyclerView.ViewHolder {
        TextView text_nama, text_ruangan, text_bangsal, text_status;
        CardView cardView;
        ImageView imageView;

        public PasienHolder(View itemView) {
            super(itemView);
            text_nama = itemView.findViewById(R.id.text_nama);
            text_ruangan = itemView.findViewById(R.id.text_ruangan);
            text_bangsal = itemView.findViewById(R.id.text_bangsal);
            text_status = itemView.findViewById(R.id.text_stats);
            cardView = itemView.findViewById(R.id.card_item);
            imageView = itemView.findViewById(R.id.img_notif);

        }
    }
}

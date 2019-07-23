package com.simanisandroid.simanis.Adapter;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.simanisandroid.simanis.DetailActivity;
import com.simanisandroid.simanis.Model.PasienModel;
import com.simanisandroid.simanis.R;

public class PasienAdapter extends FirebaseRecyclerAdapter<PasienModel, PasienAdapter.PasienHolder> {

    public PasienAdapter(@NonNull FirebaseRecyclerOptions<PasienModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final PasienHolder holder, int position, @NonNull final PasienModel model) {
        //color backgrond cardview pada recycler view
        if (model.getVol_akhir()<=400){
            holder.cardView.setCardBackgroundColor(Color.parseColor("#ff9f05"));
            holder.imageView.setVisibility(View.VISIBLE);
        } else {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#ffffff"));
            holder.imageView.setVisibility(View.GONE);
        }


        holder.text_nama.setText("Nama : "+model.getNama());
        holder.text_ruangan.setText("Ruangan : "+model.getRuangan());
        holder.text_bangsal.setText("Bangsal : "+model.getBangsal());
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

    class PasienHolder extends RecyclerView.ViewHolder {
        TextView text_nama, text_ruangan, text_bangsal;
        CardView cardView;
        ImageView imageView;

        public PasienHolder(View itemView) {
            super(itemView);
            text_nama = itemView.findViewById(R.id.text_nama);
            text_ruangan = itemView.findViewById(R.id.text_ruangan);
            text_bangsal = itemView.findViewById(R.id.text_bangsal);
            cardView = itemView.findViewById(R.id.card_item);
            imageView = itemView.findViewById(R.id.img_notif);

        }
    }
}

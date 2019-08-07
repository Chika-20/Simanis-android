package com.simanisandroid.simanis.Adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.simanisandroid.simanis.Model.NotifikasiModel;
import com.simanisandroid.simanis.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NotifikasiAdapter extends FirebaseRecyclerAdapter<NotifikasiModel, NotifikasiAdapter.NotifikasiHolder> {

    public NotifikasiAdapter(@NonNull FirebaseRecyclerOptions<NotifikasiModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull NotifikasiAdapter.NotifikasiHolder holder, int position, @NonNull NotifikasiModel model) {
        Date wakt = new Date(model.getWaktu());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        holder.text_title.setText("Kondisi infus Pasien di " + model.getRuangan() + " " + model.getBangsal());
        holder.text_body.setText("Infus hampir habis. Segera ganti infus pasien");
        holder.text_date.setText(dateFormat.format(wakt));
        holder.cardView.setCardBackgroundColor(Color.parseColor("#c1f7f7"));
    }

    @Override
    public NotifikasiHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_riwayat, parent, false);
        return new NotifikasiHolder(view);
    }

    class NotifikasiHolder extends RecyclerView.ViewHolder {
        TextView text_title, text_body, text_date;
        CardView cardView;
        String dateStr;

        public NotifikasiHolder(View itemView) {
            super(itemView);
            text_title = itemView.findViewById(R.id.txt_title);
            text_body = itemView.findViewById(R.id.txt_body);
            text_date = itemView.findViewById(R.id.txt_date);
            cardView = itemView.findViewById(R.id.card_riwayat);
        }
    }
}

package com.simanisandroid.simanis;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationManagerCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import uk.co.barbuzz.beerprogressview.BeerProgressView;


public class InfusFragment extends Fragment {

    DatabaseReference infusRef;
    TextView string_id, string_vol, string_persen, string_tetesan;
    BeerProgressView infusProgress;
    String id;
    Integer vol_awal, tetesan, vol_akhir;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_infus, container, false);
        getActivity().setTitle("Infus");

        //get id pasien
        id = ((DetailActivity) getActivity()).id;
        infusProgress = v.findViewById(R.id.infus_progress);
        string_persen = v.findViewById(R.id.txt_persen);
        string_tetesan = v.findViewById(R.id.tetesan);
        string_vol = v.findViewById(R.id.vol_akhir);

        infusRef = FirebaseDatabase.getInstance().getReference().child("Pasien/"+id);
        infusRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                vol_awal = dataSnapshot.child("vol_awal").getValue(Integer.class);
                vol_akhir = dataSnapshot.child("vol_akhir").getValue(Integer.class);
                tetesan = dataSnapshot.child("tetesan").getValue(Integer.class);

                //set data ke progressbar
                infusProgress.setBeerProgress(vol_akhir);
                infusProgress.setMax(vol_awal);
                string_persen.setText(vol_akhir+" ML");
                string_tetesan.setText(tetesan+" tetes");
                string_vol.setText(vol_awal+" ML");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return v;
    }


}

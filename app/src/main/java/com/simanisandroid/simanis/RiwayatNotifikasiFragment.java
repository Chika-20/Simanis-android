package com.simanisandroid.simanis;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.simanisandroid.simanis.Adapter.NotifikasiAdapter;
import com.simanisandroid.simanis.Model.NotifikasiModel;

import java.util.Collections;


public class RiwayatNotifikasiFragment extends Fragment {
    NotifikasiAdapter notifikasiAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle("Riwayat Notifikasi");

        View v = inflater.inflate(R.layout.fragment_riwayatnotifikasi, container, false);

        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("Notifikasi").orderByChild("waktu");
        FirebaseRecyclerOptions<NotifikasiModel> options = new FirebaseRecyclerOptions.Builder<NotifikasiModel>()
                .setQuery(query, NotifikasiModel.class)
                .setLifecycleOwner(this)
                .build();

        notifikasiAdapter = new NotifikasiAdapter(options);
        RecyclerView recyclerView = v.findViewById(R.id.recycler_notifikasi);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(notifikasiAdapter);
        return v;
    }



    @Override
    public void onStart() {
        super.onStart();
        notifikasiAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        notifikasiAdapter.stopListening();
    }
}

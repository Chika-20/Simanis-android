package com.simanisandroid.simanis;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.simanisandroid.simanis.Adapter.PasienAdapter;
import com.simanisandroid.simanis.Model.PasienModel;


public class DataPasienSembuhFragment extends Fragment {
    private PasienAdapter pasienAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_data_pasien_sembuh, container, false);
        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("Pasien")
                .orderByChild("status")
                .equalTo("Sembuh");
        FirebaseRecyclerOptions<PasienModel> options = new FirebaseRecyclerOptions.Builder<PasienModel>()
                .setQuery(query, PasienModel.class)
                .setLifecycleOwner(this)
                .build();

        pasienAdapter = new PasienAdapter(options);
        RecyclerView recyclerView = v.findViewById(R.id.recycle_pasiensembuh);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(pasienAdapter);

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        pasienAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        pasienAdapter.stopListening();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}

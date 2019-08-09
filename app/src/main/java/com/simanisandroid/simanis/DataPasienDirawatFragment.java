package com.simanisandroid.simanis;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.simanisandroid.simanis.Adapter.PasienAdapter;
import com.simanisandroid.simanis.Model.PasienModel;


public class DataPasienDirawatFragment extends Fragment {
    private PasienAdapter pasienAdapter;
    EditText filter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Simanis Apps");
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_data_pasien_dirawat, container, false);
        filter = v.findViewById(R.id.filter);

        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("Pasien")
                .orderByChild("status")
                .equalTo("Dirawat");
        FirebaseRecyclerOptions<PasienModel> options = new FirebaseRecyclerOptions.Builder<PasienModel>()
                .setQuery(query, PasienModel.class)
                .setLifecycleOwner(this)
                .build();

        pasienAdapter = new PasienAdapter(options);
        RecyclerView recyclerView = v.findViewById(R.id.recycle_pasien);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(pasienAdapter);

        filter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                (DataPasienDirawatFragment.this).pasienAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

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

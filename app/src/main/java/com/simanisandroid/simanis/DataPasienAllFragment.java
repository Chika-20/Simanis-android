package com.simanisandroid.simanis;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
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


public class DataPasienAllFragment extends Fragment {
    private PasienAdapter pasienAdapter;
    EditText filter;
    SearchView searchView;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Simanis Apps");
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_data_pasien_all, container, false);
        searchView = v.findViewById(R.id.Id_search);


        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("Pasien")
                .orderByChild("status");
        FirebaseRecyclerOptions<PasienModel> options = new FirebaseRecyclerOptions.Builder<PasienModel>()
                .setQuery(query, PasienModel.class)
                .setLifecycleOwner(this)
                .build();

        pasienAdapter = new PasienAdapter(options);
        recyclerView = v.findViewById(R.id.recycle_pasien);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(pasienAdapter);

        //method untuk memanggil data sesuai query
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String text) {
                search(text);
                if (text.isEmpty()){
                    alldata();
                }
                return false;
            }
        });
        return v;
    }

    private void alldata() {
        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("Pasien")
                .orderByChild("status");
        FirebaseRecyclerOptions<PasienModel> options = new FirebaseRecyclerOptions.Builder<PasienModel>()
                .setQuery(query, PasienModel.class)
                .setLifecycleOwner(this)
                .build();

        pasienAdapter = new PasienAdapter(options);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(pasienAdapter);
    }


    //method untuk mencri data pasien sesuai namanya
    private void search(String text) {
        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("Pasien")
                .orderByChild("nama")
                .startAt(text)
                .endAt(text + "\uf8ff");
        FirebaseRecyclerOptions<PasienModel> options = new FirebaseRecyclerOptions.Builder<PasienModel>()
                .setQuery(query, PasienModel.class)
                .setLifecycleOwner(this)
                .build();

        pasienAdapter = new PasienAdapter(options);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(pasienAdapter);
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

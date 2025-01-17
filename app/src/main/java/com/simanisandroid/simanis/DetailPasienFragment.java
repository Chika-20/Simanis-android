package com.simanisandroid.simanis;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;

public class DetailPasienFragment extends Fragment {

    TextView txt_nama, txt_tgllahir, txt_usia, txt_alamat, txt_ruangan, txt_bangsal, txt_jenisinfus, txt_vol, txt_tetes;
    Button btnEdit, btn_sembuh;
    String id_pasien, ruangan, bangsal;
    Dialog dialog;

    //reference database
    DatabaseReference Pasien, bangsalRef, pasienRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_detail_pasien, container, false);
        getActivity().setTitle("Data Pasien");

        //get stringextra from parent activity
        id_pasien = ((DetailActivity) getActivity()).id;
        Pasien = FirebaseDatabase.getInstance().getReference().child("Pasien/" + id_pasien);

        txt_nama = v.findViewById(R.id.nama);
        txt_tgllahir = v.findViewById(R.id.tgl_lahir);
        txt_usia = v.findViewById(R.id.usia);
        txt_alamat = v.findViewById(R.id.alamat);
        txt_ruangan = v.findViewById(R.id.ruangan);
        txt_bangsal = v.findViewById(R.id.bangsal);
        txt_jenisinfus = v.findViewById(R.id.jenis_infus);
        txt_vol = v.findViewById(R.id.vol_infus);
        txt_tetes = v.findViewById(R.id.tetes);
        btnEdit = v.findViewById(R.id.btn_edit);
        btn_sembuh = v.findViewById(R.id.btn_sembuh);

        //intent button edit
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), EditPasienActivity.class).putExtra("id_pasien", id_pasien);
                startActivity(i);
            }
        });

        //button sembuf
        btn_sembuh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Konfirmasi Sembuh")
                        .setMessage("Apakah Pasien Telah Sembuh ?")
                        .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                final DatabaseReference bangsalRef = FirebaseDatabase.getInstance().getReference().child("Ruangan/" + ruangan + "/" + bangsal);
                                final DatabaseReference pasienRef = FirebaseDatabase.getInstance().getReference().child("Pasien/" + id_pasien);
                            pasienRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    Map<String, Object> sembuh = new HashMap<>();
                                    sembuh.put("status", "Sembuh");
                                    pasienRef.updateChildren(sembuh).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            startActivity(new Intent(getContext(), HomeActivity.class).putExtra("id_pasien", id_pasien));
                                        }
                                    });
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                            bangsalRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    Map<String, Object> sembuh = new HashMap<>();
                                    sembuh.put("kondisi", "kosong");
                                    bangsalRef.updateChildren(sembuh).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            startActivity(new Intent(getContext(), HomeActivity.class).putExtra("id_pasien", id_pasien));
                                        }
                                    });
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                            }
                        })
                        .setNegativeButton("TIDAK", null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });



        Pasien.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //get database
                String nama = dataSnapshot.child("nama").getValue(String.class);
                String tgl_lahir = dataSnapshot.child("tgl_lahir").getValue(String.class);
                String usia = dataSnapshot.child("usia").getValue(String.class);
                String alamat = dataSnapshot.child("alamat").getValue(String.class);
                ruangan = dataSnapshot.child("ruangan").getValue(String.class);
                bangsal = dataSnapshot.child("bangsal").getValue(String.class);
                String jenis = dataSnapshot.child("jenis_infus").getValue(String.class);
                Integer volume = dataSnapshot.child("vol_awal").getValue(Integer.class);
                Integer tetes = dataSnapshot.child("faktor_tetes").getValue(Integer.class);


                txt_nama.setText(nama);
                txt_tgllahir.setText(tgl_lahir);
                txt_usia.setText(usia);
                txt_alamat.setText(alamat);
                txt_ruangan.setText(ruangan);
                txt_bangsal.setText(bangsal);
                txt_jenisinfus.setText(jenis);
                txt_vol.setText(volume.toString());
                txt_tetes.setText(tetes.toString());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Pasien.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String status = dataSnapshot.child("status").getValue(String.class);
                if (status.equals("Sembuh")) {
                    btn_sembuh.setVisibility(View.GONE);
                    btnEdit.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return v;
    }
}

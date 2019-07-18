package com.simanisandroid.simanis;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class EditPasienActivity extends AppCompatActivity {

    EditText txt_nama, txt_tgllahir, txt_usia, txt_alamat, txt_ruangan, txt_bangsal, edt_status, edt_makro;
    RadioGroup rg_jenis, rg_vol, rg_tetesan;
    Button btn_apdet;
    String nama, tgl_lahir, usia, alamat, ruangan, bangsal, status;
    String jenis = "";
    int vol_awal = 0, faktor_tetes = 0;

    //Referensi Database
    DatabaseReference pasienRef;

    String id_pasien;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pasien);
        id_pasien = getIntent().getStringExtra("id_pasien");

        //deklarasi variabel
        pasienRef = FirebaseDatabase.getInstance().getReference().child("Pasien/" + id_pasien);
        txt_nama = findViewById(R.id.edt_nama);
        txt_tgllahir = findViewById(R.id.edt_tgllahir);
        txt_usia = findViewById(R.id.edt_usia);
        txt_alamat = findViewById(R.id.edt_alamat);
        txt_ruangan = findViewById(R.id.edt_ruangan);
        txt_bangsal = findViewById(R.id.edt_bangsal);
        edt_status = findViewById(R.id.edt_status);
        rg_jenis = findViewById(R.id.radio_jenis);
        rg_vol = findViewById(R.id.radio_vol);
        rg_tetesan = findViewById(R.id.radio_tetesan);
        btn_apdet = findViewById(R.id.btn_apdet);


        pasienRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String nama = dataSnapshot.child("nama").getValue(String.class);
                String tgl_lahir = dataSnapshot.child("tgl_lahir").getValue(String.class);
                String usia = dataSnapshot.child("usia").getValue(String.class);
                String alamat = dataSnapshot.child("alamat").getValue(String.class);
                String ruangan = dataSnapshot.child("ruangan").getValue(String.class);
                String bangsal = dataSnapshot.child("bangsal").getValue(String.class);
                String status = dataSnapshot.child("status").getValue(String.class);
                String jenisinfus = dataSnapshot.child("jenis_infus").getValue(String.class);
                Integer volawal = dataSnapshot.child("vol_awal").getValue(Integer.class);
                Integer faktortetes = dataSnapshot.child("faktor_tetes").getValue(Integer.class);

                // replace variabel
                jenis = jenis.replace(jenis, jenisinfus);
                vol_awal = volawal;
                faktor_tetes = faktortetes;

                //set jenis
                if (jenisinfus.equalsIgnoreCase("makro")) {
                    rg_jenis.check(R.id.makro);
                } else {
                    rg_jenis.check(R.id.mikro);
                }

//                //set vol awal
                if (String.valueOf(volawal).equals("1000")) {
                    rg_vol.check(R.id.Id_vol1);
                } else {
                    rg_vol.check(R.id.Id_vol2);
                }

//                //set faktor tetes
                if (String.valueOf(faktortetes).equals("20")) {
                    rg_tetesan.check(R.id.tetes1);
                } else {
                    rg_tetesan.check(R.id.tetes2);
                }

                //set database ke edit text
                txt_nama.setText(nama);
                txt_tgllahir.setText(tgl_lahir);
                txt_usia.setText(usia);
                txt_alamat.setText(alamat);
                txt_ruangan.setText(ruangan);
                txt_bangsal.setText(bangsal);
                edt_status.setText(status);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btn_apdet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nama = txt_nama.getText().toString();
                tgl_lahir = txt_tgllahir.getText().toString();
                usia = txt_usia.getText().toString();
                alamat = txt_alamat.getText().toString();
                ruangan = txt_ruangan.getText().toString();
                bangsal = txt_bangsal.getText().toString();
                status = edt_status.getText().toString();

                //update data
                Map<String, Object> update = new HashMap<>();
                update.put("nama", nama);
                update.put("tgl_lahir", tgl_lahir);
                update.put("usia", usia);
                update.put("alamat", alamat);
                update.put("ruangan", ruangan);
                update.put("bangsal", bangsal);
                update.put("status", status);
                update.put("jenis_infus", jenis);
                update.put("vol_awal", vol_awal);
                update.put("faktor_tetes", faktor_tetes);

                pasienRef.updateChildren(update)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(EditPasienActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), DetailActivity.class).putExtra("id_pasien", id_pasien));
                                finish();
                            }
                        });
            }
        });


//        btn_sembuh.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String ruangan = txt_ruangan.getText().toString();
//                String bangsal = txt_bangsal.getText().toString();
//                final DatabaseReference bangsalRef = FirebaseDatabase.getInstance().getReference().child("Ruangan/" + ruangan + "/" + bangsal);
//                bangsalRef.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        Map<String, Object> sembuh = new HashMap<>();
//                        sembuh.put("kondisi", "kosong");
//                        bangsalRef.updateChildren(sembuh).addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void aVoid) {
//                                startActivity(new Intent(getApplicationContext(), DetailActivity.class).putExtra("id_pasien", id_pasien));
//                                finish();
//                            }
//                        });
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                });
//            }
//        });
    }

    public void onVolumeSelected(View view) {
        RadioGroup radioGroup = findViewById(R.id.radio_vol);
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.Id_vol1:
                vol_awal = 1000;
                break;
            case R.id.Id_vol2:
                vol_awal = 500;
                break;
        }
    }

    //
    public void onJenisSelected(View view) {
        RadioGroup radioGroup = findViewById(R.id.radio_jenis);
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.makro:
                jenis = jenis.replace(jenis, "MAKRO");
                break;
            case R.id.mikro:
                jenis = jenis.replace(jenis, "MIKRO");
                break;
        }
    }

    //
    public void onTetesanSelected(View view) {
        RadioGroup radioGroup = findViewById(R.id.radio_tetesan);
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.tetes1:
                faktor_tetes = 20;
                break;
            case R.id.tetes2:
                faktor_tetes = 60;
                break;
        }
    }
}

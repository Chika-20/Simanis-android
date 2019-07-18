package com.simanisandroid.simanis;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.simanisandroid.simanis.Model.SetUserModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InputDataActivity extends AppCompatActivity {

    EditText edt_nama, edt_tgllahir, edt_usia, edt_alamat, edt_ruangan, edt_bangsal, edt_status;
    RadioGroup radio_jenis, radio_volinfus, radio_tetes;
    RadioButton makro, mikro, vol1, vol2, tetes1, tetes2;
    Button btn_simpan;
    Spinner spinnerRuangan, spinnerBangsal;
    String nama, tgl_lahir, usia, alamat, ruangan, bangsal, status, kondisi;
    String jenis = "";
    int vol_awal = 0, faktor_tetes = 0;

    DatabaseReference userRef, ruanganRef, bangsalRef, dbRef, kondisiRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_data);

        edt_nama = findViewById(R.id.edt_nama);
        edt_tgllahir = findViewById(R.id.edt_tgllahir);
        edt_usia = findViewById(R.id.edt_usia);
        edt_alamat = findViewById(R.id.edt_alamat);
        edt_ruangan = findViewById(R.id.edt_ruangan);
        spinnerRuangan = findViewById(R.id.spinner_ruangan);
        spinnerBangsal = findViewById(R.id.spinner_bangsal);
        radio_jenis = findViewById(R.id.radio_jenis);
        radio_volinfus = findViewById(R.id.radio_vol);
        radio_tetes = findViewById(R.id.radio_tetesan);
        makro = findViewById(R.id.makro);
        mikro = findViewById(R.id.mikro);
        vol1 = findViewById(R.id.Id_vol1);
        vol2 = findViewById(R.id.Id_vol2);
        tetes1 = findViewById(R.id.tetes1);
        tetes2 = findViewById(R.id.tetes2);
        btn_simpan = findViewById(R.id.btn_simpan);
        dbRef = FirebaseDatabase.getInstance().getReference();
        userRef = dbRef.child("Pasien");
        ruanganRef = dbRef.child("Ruangan");


        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nama = edt_nama.getText().toString();
                tgl_lahir = edt_tgllahir.getText().toString();
                usia = edt_usia.getText().toString();
                alamat = edt_alamat.getText().toString();
                ruangan = spinnerRuangan.getSelectedItem().toString();
                bangsal = spinnerBangsal.getSelectedItem().toString();
                status = "Dirawat";

                //cek kondisi bangsal
                bangsalRef = dbRef.child("Ruangan/" + ruangan + "/" + bangsal);
                bangsalRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        kondisi = dataSnapshot.child("kondisi").getValue(String.class);
                        if (kondisi.equals("kosong")) {
                            setUser(nama, tgl_lahir, usia, alamat, ruangan, bangsal, status, jenis, vol_awal, faktor_tetes);
                            Map<String, Object> bangsal = new HashMap<>();
                            bangsal.put("kondisi","dipakai" );
                            bangsalRef.updateChildren(bangsal);
                        } else {
                            Toast.makeText(InputDataActivity.this, "Bangsal Sudah Digunakan", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        //menampilkan list ruangan ke spinner ruangan
        ruanganRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<String> ruang = new ArrayList<>();

                for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                    String ruangName = areaSnapshot.getKey();
                    ruang.add(ruangName);
                }

                ArrayAdapter<String> ruangAdapter = new ArrayAdapter<>(InputDataActivity.this, android.R.layout.simple_spinner_item, ruang);
                ruangAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerRuangan.setAdapter(ruangAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //menampilkan list bangsal ke spinner bangsal sesuai ruangan
        spinnerRuangan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String ruangan = spinnerRuangan.getSelectedItem().toString();
                bangsalRef = dbRef.child("Ruangan/" + ruangan);
                bangsalRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final List<String> bangsal = new ArrayList<>();
//
                        for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                            String bangsalName = areaSnapshot.getKey();
                            bangsal.add(bangsalName);
                        }
                        ArrayAdapter<String> bangsalAdapter = new ArrayAdapter<>(InputDataActivity.this, android.R.layout.simple_spinner_item, bangsal);
                        bangsalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerBangsal.setAdapter(bangsalAdapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setUser(String nama, String tgl_lahir, String usia, String alamat, final String ruangan, final String bangsal, String status, String jenis, Integer vol_awal, Integer faktor_tetes) {
        SetUserModel userModel = new SetUserModel(nama, tgl_lahir, usia, alamat, ruangan, bangsal, status, jenis, vol_awal, faktor_tetes);
        final String key = userRef.push().getKey();
        ruanganRef.child(ruangan).child(bangsal).child("id_pasien").setValue(key);
        userRef.child(key).setValue(userModel)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Toast.makeText(InputDataActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(InputDataActivity.this, "Failed, " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
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

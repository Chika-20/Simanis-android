package com.simanisandroid.simanis.Model;

public class SetUserModel {

    public String nama;
    public String tgl_lahir;
    public String usia;
    public String alamat;
    public String ruangan;
    public String bangsal;
    public String status;
    public String jenis_infus;
    public Integer tetesan;
    public Integer vol_akhir;
    public Integer vol_awal;
    public Integer faktor_tetes;

    public SetUserModel(String nama, String tgl_lahir, String usia, String alamat, String ruangan, String bangsal, String status, String jenis_infus, Integer tetesan, Integer vol_akhir, Integer vol_awal, Integer faktor_tetes) {
        this.nama = nama;
        this.tgl_lahir = tgl_lahir;
        this.usia = usia;
        this.alamat = alamat;
        this.ruangan = ruangan;
        this.bangsal = bangsal;
        this.status = status;
        this.jenis_infus = jenis_infus;
        this.tetesan = tetesan;
        this.vol_akhir = vol_akhir;
        this.vol_awal = vol_awal;
        this.faktor_tetes = faktor_tetes;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setTgl_lahir(String tgl_lahir) {
        this.tgl_lahir = tgl_lahir;
    }

    public void setUsia(String usia) {
        this.usia = usia;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public void setRuangan(String ruangan) {
        this.ruangan = ruangan;
    }

    public void setBangsal(String bangsal) {
        this.bangsal = bangsal;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setJenis_infus(String jenis_infus) {
        this.jenis_infus = jenis_infus;
    }

    public void setTetesan(Integer tetesan) {
        this.tetesan = tetesan;
    }

    public void setVol_akhir(Integer vol_akhir) {
        this.vol_akhir = vol_akhir;
    }

    public void setVol_awal(Integer vol_awal) {
        this.vol_awal = vol_awal;
    }

    public void setFaktor_tetes(Integer faktor_tetes) {
        this.faktor_tetes = faktor_tetes;
    }
}

package com.simanisandroid.simanis.Model;

public class PasienModel {
    private String id_pasien;
    private String nama;
    private String ruangan;
    private String bangsal;
    private Integer vol_akhir;
    private String status;

    public PasienModel() {

    }

    public String getId_pasien() {
        return id_pasien;
    }

    public String getNama() {
        return nama;
    }

    public String getRuangan() {
        return ruangan;
    }

    public String getBangsal() {
        return bangsal;
    }

    public Integer getVol_akhir() {
        return vol_akhir;
    }

    public String getStatus() {
        return status;
    }
}

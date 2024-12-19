package com.grokmusic.gestiondehorarios.Clases;

public class Tarifas {
    private int idTarifa;
    private String tarifa;
    private double horaNormal;
    private double horaExtra;
    private  double horaFestiva;
    private double precioDescanso;
    private  double plusTransporte;
    private  double limite;

    public Tarifas() {    }

    public Tarifas(int idTarifa, String tarifa, double horaNormal, double horaExtra, double horaFestiva,  double precioDescanso,double plusTransporte,double limite) {
        this.idTarifa = idTarifa;
        this.tarifa = tarifa;
        this.horaNormal = horaNormal;
        this.horaExtra = horaExtra;
        this.horaFestiva = horaFestiva;
        this.precioDescanso = precioDescanso;
        this.plusTransporte = plusTransporte;
        this.limite = limite;
    }

    public int getIdTarifa() {
        return idTarifa;
    }

    public void setIdTarifa(int idTarifa) {
        this.idTarifa = idTarifa;
    }

    public String getTarifa() {
        return tarifa;
    }

    public void setTarifa(String tarifa) {
        this.tarifa = tarifa;
    }

    public double getHoraNormal() {
        return horaNormal;
    }

    public void setHoraNormal(double horaNormal) {
        this.horaNormal = horaNormal;
    }

    public double getHoraExtra() {
        return horaExtra;
    }

    public void setHoraExtra(double horaExtra) {
        this.horaExtra = horaExtra;
    }

    public double getHoraFestiva() {
        return horaFestiva;
    }

    public void setHoraFestiva(double horaFestiva) {
        this.horaFestiva = horaFestiva;
    }


    public double getPrecioDescanso() {
        return precioDescanso;
    }

    public void setPrecioDescanso(double precioDescanso) {
        this.precioDescanso = precioDescanso;
    }

    public double getPlusTransporte() {
        return plusTransporte;
    }

    public void setPlusTransporte(double plusTransporte) {
        this.plusTransporte = plusTransporte;
    }

    public double getLimite() {
        return limite;
    }

    public void setLimite(double limite) {
        this.limite = limite;
    }
}

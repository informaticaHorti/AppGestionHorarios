package com.grokmusic.gestiondehorarios.Clases;

public class NominaDiaria {
    private String fecha;
    private String horasN;
    private String horasE;
    private String horasF;
    private int paradas;
    private double subtotal,irpf,segSoc,total;

    public NominaDiaria() {
    }

    public NominaDiaria(String fecha, String horasN, String horasE, String horasF, int paradas, double subtotal, double irpf, double segSoc, double total) {
        this.fecha = fecha;
        this.horasN = horasN;
        this.horasE = horasE;
        this.horasF = horasF;
        this.paradas = paradas;
        this.subtotal = subtotal;
        this.irpf = irpf;
        this.segSoc = segSoc;
        this.total = total;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHorasN() {
        return horasN;
    }

    public void setHorasN(String horasN) {
        this.horasN = horasN;
    }

    public String getHorasE() {
        return horasE;
    }

    public void setHorasE(String horasE) {
        this.horasE = horasE;
    }

    public String getHorasF() {
        return horasF;
    }

    public void setHorasF(String horasF) {
        this.horasF = horasF;
    }

    public int getParadas() {
        return paradas;
    }

    public void setParadas(int paradas) {
        this.paradas = paradas;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getIrpf() {
        return irpf;
    }

    public void setIrpf(double irpf) {
        this.irpf = irpf;
    }

    public double getSegSoc() {
        return segSoc;
    }

    public void setSegSoc(double segSoc) {
        this.segSoc = segSoc;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}

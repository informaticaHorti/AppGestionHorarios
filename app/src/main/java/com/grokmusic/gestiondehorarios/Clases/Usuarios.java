package com.grokmusic.gestiondehorarios.Clases;

public class Usuarios {
    private  int id ;
    private  String nombre;
    private  int tarifa;
    private double antig;
    private  double irpf,segsoc;

    public Usuarios(int id, String nombre, int tarifa,double antig,double irpf,double segsoc) {
        this.id = id;
        this.nombre = nombre;
        this.tarifa = tarifa;
        this.antig = antig;
        this.irpf = irpf;
        this.segsoc = segsoc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTarifa() {
        return tarifa;
    }

    public void setTarifa(int tarifa) {
        this.tarifa = tarifa;
    }

    public double getAntig() {
        return antig;
    }

    public void setAntig(double antig) {
        this.antig = antig;
    }

    public double getIrpf() {
        return irpf;
    }

    public void setIrpf(double irpf) {
        this.irpf = irpf;
    }

    public double getSegsoc() {
        return segsoc;
    }

    public void setSegsoc(double segsoc) {
        this.segsoc = segsoc;
    }
}

package com.grokmusic.gestiondehorarios.Clases;

public class MesesNomina {
    private int year;
    private int mes;
    private String nameMes;
    private int diasTrabajados;
    private double importeMes;

    public MesesNomina() {
    }

    public MesesNomina(int year, int mes, String nameMes, int diasTrabajados, double importeMes) {
        this.year = year;
        this.mes = mes;
        this.nameMes = nameMes;
        this.diasTrabajados = diasTrabajados;
        this.importeMes = importeMes;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public String getNameMes() {
        return nameMes;
    }

    public void setNameMes(String nameMes) {
        this.nameMes = nameMes;
    }

    public int getDiasTrabajados() {
        return diasTrabajados;
    }

    public void setDiasTrabajados(int diasTrabajados) {
        this.diasTrabajados = diasTrabajados;
    }

    public double getImporteMes() {
        return importeMes;
    }

    public void setImporteMes(double importeMes) {
        this.importeMes = importeMes;
    }
}

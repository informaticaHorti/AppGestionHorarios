package com.grokmusic.gestiondehorarios.Clases;

public class Horario {
    private  int id, idUsuario,paraM,paraT,paradas;
    private String fecha,festivo,entraM,saleM,entraT,saleT,totNorm,totExtra,totFiesta,totHor;
    private double totHoras,totIrpf,totSegSoc,totDia,subTotal,totNm,totEX,totFI;
    private String valorHorNm,valorHorEX,valorHorFI,valorDesc,valorTransp;
    private  int mesHorario,yearHorario;

    public Horario() {
    }

    public Horario(int id, int idUsuario,String fecha, String festivo,  String entraM, String saleM, int paraM,  String entraT, String saleT,int paraT,
                   String totNorm, String totExtra, String totFiesta, String totHor, double totHoras, double totNm,double totEX,double totFI,int paradas,
                   double subTotal,double totIrpf, double totSegSoc, double totDia,
                   String valorHorNm, String valorHorEX, String valorHorFI, String valorDesc, String valorTransp,int mesHorario,int yearHorario) {

        this.id = id;
        this.idUsuario = idUsuario;
        this.paraM = paraM;
        this.paraT = paraT;
        this.paradas = paradas;
        this.fecha = fecha;
        this.festivo = festivo;
        this.entraM = entraM;
        this.saleM = saleM;
        this.entraT = entraT;
        this.saleT = saleT;
        this.totNorm = totNorm;
        this.totExtra = totExtra;
        this.totFiesta = totFiesta;
        this.totHor = totHor;
        this.totHoras = totHoras;
        this.totIrpf = totIrpf;
        this.totSegSoc = totSegSoc;
        this.totDia = totDia;
        this.subTotal = subTotal;
        this.valorHorNm = valorHorNm;
        this.valorHorEX = valorHorEX;
        this.valorHorFI = valorHorFI;
        this.valorDesc = valorDesc;
        this.valorTransp = valorTransp;
        this.totNm=totNm;
        this.totEX=totEX;
        this.totFI=totFI;
        this.mesHorario = mesHorario;
        this.yearHorario = yearHorario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getParaM() {
        return paraM;
    }

    public void setParaM(int paraM) {
        this.paraM = paraM;
    }

    public int getParaT() {
        return paraT;
    }

    public void setParaT(int paraT) {
        this.paraT = paraT;
    }

    public int getParadas() {
        return paradas;
    }

    public void setParadas(int paradas) {
        this.paradas = paradas;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getFestivo() {
        return festivo;
    }

    public void setFestivo(String festivo) {
        this.festivo = festivo;
    }

    public String getEntraM() {
        return entraM;
    }

    public void setEntraM(String entraM) {
        this.entraM = entraM;
    }

    public String getSaleM() {
        return saleM;
    }

    public void setSaleM(String saleM) {
        this.saleM = saleM;
    }

    public String getEntraT() {
        return entraT;
    }

    public void setEntraT(String entraT) {
        this.entraT = entraT;
    }

    public String getSaleT() {
        return saleT;
    }

    public void setSaleT(String saleT) {
        this.saleT = saleT;
    }

    public String getTotNorm() {
        return totNorm;
    }

    public void setTotNorm(String totNorm) {
        this.totNorm = totNorm;
    }

    public String getTotExtra() {
        return totExtra;
    }

    public void setTotExtra(String totExtra) {
        this.totExtra = totExtra;
    }

    public String getTotFiesta() {
        return totFiesta;
    }

    public void setTotFiesta(String totFiesta) {
        this.totFiesta = totFiesta;
    }

    public String getTotHor() {
        return totHor;
    }

    public void setTotHor(String totHor) {
        this.totHor = totHor;
    }

    public double getTotHoras() {
        return totHoras;
    }

    public void setTotHoras(double totHoras) {
        this.totHoras = totHoras;
    }

    public double getTotIrpf() {
        return totIrpf;
    }

    public void setTotIrpf(double totIrpf) {
        this.totIrpf = totIrpf;
    }

    public double getTotSegSoc() {
        return totSegSoc;
    }

    public void setTotSegSoc(double totSegSoc) {
        this.totSegSoc = totSegSoc;
    }

    public double getTotDia() {
        return totDia;
    }

    public void setTotDia(double totDia) {
        this.totDia = totDia;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public double getTotNm() {
        return totNm;
    }

    public void setTotNm(double totNm) {
        this.totNm = totNm;
    }

    public double getTotEX() {
        return totEX;
    }

    public void setTotEX(double totEX) {
        this.totEX = totEX;
    }

    public double getTotFI() {
        return totFI;
    }

    public void setTotFI(double totFI) {
        this.totFI = totFI;
    }

    public String getValorHorNm() {
        return valorHorNm;
    }

    public void setValorHorNm(String valorHorNm) {
        this.valorHorNm = valorHorNm;
    }

    public String getValorHorEX() {
        return valorHorEX;
    }

    public void setValorHorEX(String valorHorEX) {
        this.valorHorEX = valorHorEX;
    }

    public String getValorHorFI() {
        return valorHorFI;
    }

    public void setValorHorFI(String valorHorFI) {
        this.valorHorFI = valorHorFI;
    }

    public String getValorDesc() {
        return valorDesc;
    }

    public void setValorDesc(String valorDesc) {
        this.valorDesc = valorDesc;
    }

    public String getValorTransp() {
        return valorTransp;
    }

    public void setValorTransp(String valorTransp) {
        this.valorTransp = valorTransp;
    }

    public int getMesHorario() {
        return mesHorario;
    }

    public void setMesHorario(int mesHorario) {
        this.mesHorario = mesHorario;
    }

    public int getYearHorario() {
        return yearHorario;
    }

    public void setYearHorario(int yearHorario) {
        this.yearHorario = yearHorario;
    }
}

package com.grokmusic.gestiondehorarios.Clases;

public class HorarioDef {
    private int id;
    private int idUsuario;
    private int HentraM,MentraM;
    private int HsaleM, MsaleM;
    private int HentraT,MentraT;
    private int HsaleT,MsaleT;

    public HorarioDef() {    }

    public HorarioDef(int id, int idUsuario, int hentraM, int mentraM, int hsaleM, int msaleM, int hentraT, int mentraT, int hsaleT, int msaleT) {
        this.id = id;
        this.idUsuario = idUsuario;
        HentraM = hentraM;
        MentraM = mentraM;
        HsaleM = hsaleM;
        MsaleM = msaleM;
        HentraT = hentraT;
        MentraT = mentraT;
        HsaleT = hsaleT;
        MsaleT = msaleT;
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

    public int getHentraM() {
        return HentraM;
    }

    public void setHentraM(int hentraM) {
        HentraM = hentraM;
    }

    public int getMentraM() {
        return MentraM;
    }

    public void setMentraM(int mentraM) {
        MentraM = mentraM;
    }

    public int getHsaleM() {
        return HsaleM;
    }

    public void setHsaleM(int hsaleM) {
        HsaleM = hsaleM;
    }

    public int getMsaleM() {
        return MsaleM;
    }

    public void setMsaleM(int msaleM) {
        MsaleM = msaleM;
    }

    public int getHentraT() {
        return HentraT;
    }

    public void setHentraT(int hentraT) {
        HentraT = hentraT;
    }

    public int getMentraT() {
        return MentraT;
    }

    public void setMentraT(int mentraT) {
        MentraT = mentraT;
    }

    public int getHsaleT() {
        return HsaleT;
    }

    public void setHsaleT(int hsaleT) {
        HsaleT = hsaleT;
    }

    public int getMsaleT() {
        return MsaleT;
    }

    public void setMsaleT(int msaleT) {
        MsaleT = msaleT;
    }
}

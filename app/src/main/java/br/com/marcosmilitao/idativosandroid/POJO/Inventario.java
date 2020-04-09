package br.com.marcosmilitao.idativosandroid.POJO;

import android.nfc.Tag;

/**
 * Created by marcoswerneck on 03/09/19.
 */

public class Inventario {
    public int CadastroMateriaisId;
    public String Tagid;
    public String Modelo;
    public String PartNumber;
    public int PosicaoId;
    public String Posicao;
    public boolean IsFound;

    public Inventario(int CadastroMateriaisId, String Tagid, String Modelo, String PartNumber, int PosicaoId, String Posicao, boolean isFound)
    {
        this.CadastroMateriaisId = CadastroMateriaisId;
        this.Tagid = Tagid;
        this.Modelo = Modelo;
        this.PartNumber = PartNumber;
        this.PosicaoId = PosicaoId;
        this.Posicao = Posicao;
        this.IsFound = isFound;
    }

    public Boolean getFound() {
        return IsFound;
    }

    public void setFound(Boolean found) {
        IsFound = found;
    }

    public int getCadastroMateriaisId() {
        return CadastroMateriaisId;
    }

    public void setCadastroMateriaisId(int cadastroMateriaisId) {
        CadastroMateriaisId = cadastroMateriaisId;
    }

    public String getTagid() {
        return Tagid;
    }

    public void setTagid(String tagid) {
        Tagid = tagid;
    }

    public String getModelo() {
        return Modelo;
    }

    public void setModelo(String modelo) {
        Modelo = modelo;
    }

    public String getPartNumber() {
        return PartNumber;
    }

    public void setPartNumber(String partNumber) {
        PartNumber = partNumber;
    }

    public int getPosicaoId() {
        return PosicaoId;
    }

    public void setPosicaoId(int posicaoId) {
        PosicaoId = posicaoId;
    }

    public String getPosicao() {
        return Posicao;
    }

    public void setPosicao(String posicao) {
        Posicao = posicao;
    }
}

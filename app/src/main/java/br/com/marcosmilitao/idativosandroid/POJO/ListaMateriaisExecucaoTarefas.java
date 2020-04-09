package br.com.marcosmilitao.idativosandroid.POJO;

/**
 * Created by marcoswerneck on 03/10/19.
 */

public class ListaMateriaisExecucaoTarefas {
    private int IdOriginal;
    public int CadastroMateriaisId;
    public String Tagid;
    public String Modelo;
    public String PartNumber;
    public String NumSerie;
    public boolean IsFound;

    public int getIdOriginal() {
        return IdOriginal;
    }

    public void setIdOriginal(int idOriginal) {
        IdOriginal = idOriginal;
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

    public boolean isFound() {
        return IsFound;
    }

    public void setFound(boolean found) {
        IsFound = found;
    }

    public String getNumSerie() {
        return NumSerie;
    }

    public void setNumSerie(String numSerie) {
        NumSerie = numSerie;
    }
}

package br.com.marcosmilitao.idativosandroid.DBUtils.Models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class ParametrosPadrao {
    @PrimaryKey(autoGenerate = true)
    private int Id;

    private int IdOriginal;

    private String RowVersion;

    private String CodAlmoxarifado;

    private String SetorProprietario;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getIdOriginal() {
        return IdOriginal;
    }

    public void setIdOriginal(int idOriginal) {
        IdOriginal = idOriginal;
    }

    public String getCodAlmoxarifado() {
        return CodAlmoxarifado;
    }

    public void setCodAlmoxarifado(String codAlmoxarifado) {
        CodAlmoxarifado = codAlmoxarifado;
    }

    public String getSetorProprietario() {
        return SetorProprietario;
    }

    public void setSetorProprietario(String setorProprietario) {
        SetorProprietario = setorProprietario;
    }

    public String getRowVersion() {
        return RowVersion;
    }

    public void setRowVersion(String rowVersion) {
        RowVersion = rowVersion;
    }
}

package br.com.marcosmilitao.idativosandroid.DBUtils.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

/**
 * Created by marcoswerneck on 04/09/19.
 */

@Entity
public class ListaMateriaisInventarioPlanejado {
    @PrimaryKey(autoGenerate = true)
    private int Id;

    private int IdOriginal;

    private String RowVersion;

    private int CadastroMateriaisId;

    private int InventarioPlanejadoId;

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

    public String getRowVersion() {
        return RowVersion;
    }

    public void setRowVersion(String rowVersion) {
        RowVersion = rowVersion;
    }

    public int getCadastroMateriaisId() {
        return CadastroMateriaisId;
    }

    public void setCadastroMateriaisId(int cadastroMateriaisId) {
        CadastroMateriaisId = cadastroMateriaisId;
    }

    public int getInventarioPlanejadoId() {
        return InventarioPlanejadoId;
    }

    public void setInventarioPlanejadoId(int inventarioPlanejadoId) {
        InventarioPlanejadoId = inventarioPlanejadoId;
    }
}

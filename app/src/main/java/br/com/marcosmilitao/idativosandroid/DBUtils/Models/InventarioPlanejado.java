package br.com.marcosmilitao.idativosandroid.DBUtils.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity
public class InventarioPlanejado {
    @PrimaryKey(autoGenerate = true)
    private int Id;

    private int IdOriginal;

    private String RowVersion;

    private String Descricao;

    private String ApplicationUserItemIdOriginal;

    private boolean EmUso;

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

    public String getDescricao() {
        return Descricao;
    }

    public void setDescricao(String descricao) {
        Descricao = descricao;
    }

    public String getApplicationUserItemIdOriginal() {
        return ApplicationUserItemIdOriginal;
    }

    public void setApplicationUserItemIdOriginal(String applicationUserItemIdOriginal) {
        ApplicationUserItemIdOriginal = applicationUserItemIdOriginal;
    }

    public boolean isEmUso() {
        return EmUso;
    }

    public void setEmUso(boolean emUso) {
        EmUso = emUso;
    }
}

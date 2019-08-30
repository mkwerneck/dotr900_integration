package br.com.marcosmilitao.idativosandroid.DBUtils.Models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Grupos {
    @PrimaryKey(autoGenerate = true)
    private int Id;

    private String IdOriginal;

    private String RowVersion;

    private String Titulo;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getIdOriginal() {
        return IdOriginal;
    }

    public void setIdOriginal(String idOriginal) {
        IdOriginal = idOriginal;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    public String getRowVersion() {
        return RowVersion;
    }

    public void setRowVersion(String rowVersion) {
        RowVersion = rowVersion;
    }
}

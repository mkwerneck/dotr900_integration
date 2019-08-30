package br.com.marcosmilitao.idativosandroid.DBUtils.Models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by marcoswerneck on 20/08/19.
 */

@Entity
public class Funcoes {
    @PrimaryKey(autoGenerate = true)
    private int Id;

    private String IdOriginal;

    private String RowVersion;

    private String Role;

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

    public String getRowVersion() {
        return RowVersion;
    }

    public void setRowVersion(String rowVersion) {
        RowVersion = rowVersion;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }
}

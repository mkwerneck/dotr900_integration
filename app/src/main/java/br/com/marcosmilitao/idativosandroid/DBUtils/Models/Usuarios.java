package br.com.marcosmilitao.idativosandroid.DBUtils.Models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Usuarios {
    @PrimaryKey(autoGenerate = true)
    private int Id;

    private String IdOriginal;

    private String RowVersion;

    private String UserName;

    private String NomeCompleto;

    private String Email;

    private String Permissao;

    private String TAGID;

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

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getNomeCompleto() {
        return NomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        NomeCompleto = nomeCompleto;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPermissao() {
        return Permissao;
    }

    public void setPermissao(String permissao) {
        Permissao = permissao;
    }

    public String getTAGID() {
        return TAGID;
    }

    public void setTAGID(String TAGID) {
        this.TAGID = TAGID;
    }

    public String getRowVersion() {
        return RowVersion;
    }

    public void setRowVersion(String rowVersion) {
        RowVersion = rowVersion;
    }
}

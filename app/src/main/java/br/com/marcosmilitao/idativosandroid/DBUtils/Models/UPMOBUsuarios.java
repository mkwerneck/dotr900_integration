package br.com.marcosmilitao.idativosandroid.DBUtils.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity
public class UPMOBUsuarios {
    @PrimaryKey(autoGenerate = true)
    private int Id;

    private String IdOriginal;

    private String RoleIdOriginal;

    private String Username;

    private String Email;

    private String NomeCompleto;

    private String TAGID;

    private boolean EnviarSenhaEmail;

    private String DescricaoErro;

    private Boolean FlagErro;

    private Boolean FlagAtualizar;

    private Boolean FlagProcess;

    private String CodColetor;

    public String getCodColetor() {
        return CodColetor;
    }

    public void setCodColetor(String codColetor) {
        CodColetor = codColetor;
    }

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

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getNomeCompleto() {
        return NomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        NomeCompleto = nomeCompleto;
    }

    public String getTAGID() {
        return TAGID;
    }

    public void setTAGID(String TAGID) {
        this.TAGID = TAGID;
    }

    public String getDescricaoErro() {
        return DescricaoErro;
    }

    public void setDescricaoErro(String descricaoErro) {
        DescricaoErro = descricaoErro;
    }

    public Boolean getFlagErro() {
        return FlagErro;
    }

    public void setFlagErro(Boolean flagErro) {
        FlagErro = flagErro;
    }

    public Boolean getFlagAtualizar() {
        return FlagAtualizar;
    }

    public void setFlagAtualizar(Boolean flagAtualizar) {
        FlagAtualizar = flagAtualizar;
    }

    public Boolean getFlagProcess() {
        return FlagProcess;
    }

    public void setFlagProcess(Boolean flagProcess) {
        FlagProcess = flagProcess;
    }

    public String getRoleIdOriginal() {
        return RoleIdOriginal;
    }

    public void setRoleIdOriginal(String roleIdOriginal) {
        RoleIdOriginal = roleIdOriginal;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public boolean getEnviarSenhaEmail() {
        return EnviarSenhaEmail;
    }

    public void setEnviarSenhaEmail(boolean enviarSenhaEmail) {
        EnviarSenhaEmail = enviarSenhaEmail;
    }
}

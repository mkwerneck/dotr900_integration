package br.com.marcosmilitao.idativosandroid.DBUtils.Models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Tarefas {
    @PrimaryKey(autoGenerate = true)
    private int Id;

    private int IdOriginal;

    private String RowVersion;

    private String Codigo;

    private String Titulo;

    private String Descricao;

    private Boolean FlagDependenciaServico;

    private Boolean FlagDependenciaMaterial;

    private String CategoriaEquipamentos;

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

    public String getCodigo() {
        return Codigo;
    }

    public void setCodigo(String codigo) {
        Codigo = codigo;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    public String getDescricao() {
        return Descricao;
    }

    public void setDescricao(String descricao) {
        Descricao = descricao;
    }

    public Boolean getFlagDependenciaServico() {
        return FlagDependenciaServico;
    }

    public void setFlagDependenciaServico(Boolean flagDependenciaServico) {
        FlagDependenciaServico = flagDependenciaServico;
    }

    public Boolean getFlagDependenciaMaterial() {
        return FlagDependenciaMaterial;
    }

    public void setFlagDependenciaMaterial(Boolean flagDependenciaMaterial) {
        FlagDependenciaMaterial = flagDependenciaMaterial;
    }

    public String getCategoriaEquipamentos() {
        return CategoriaEquipamentos;
    }

    public void setCategoriaEquipamentos(String categoriaEquipamentos) {
        CategoriaEquipamentos = categoriaEquipamentos;
    }

    public String getRowVersion() {
        return RowVersion;
    }

    public void setRowVersion(String rowVersion) {
        RowVersion = rowVersion;
    }
}

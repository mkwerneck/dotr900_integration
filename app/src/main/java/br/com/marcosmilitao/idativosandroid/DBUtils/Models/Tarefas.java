package br.com.marcosmilitao.idativosandroid.DBUtils.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

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

    private Boolean FlagEntradaAlmoxarifado;

    private Boolean FlagSaidaAlmoxarifado;

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

    public Boolean getFlagEntradaAlmoxarifado() {
        return FlagEntradaAlmoxarifado;
    }

    public void setFlagEntradaAlmoxarifado(Boolean flagEntradaAlmoxarifado) {
        FlagEntradaAlmoxarifado = flagEntradaAlmoxarifado;
    }

    public Boolean getFlagSaidaAlmoxarifado() {
        return FlagSaidaAlmoxarifado;
    }

    public void setFlagSaidaAlmoxarifado(Boolean flagSaidaAlmoxarifado) {
        FlagSaidaAlmoxarifado = flagSaidaAlmoxarifado;
    }
}

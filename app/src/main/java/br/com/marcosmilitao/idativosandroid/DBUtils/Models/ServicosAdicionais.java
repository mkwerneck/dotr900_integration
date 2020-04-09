package br.com.marcosmilitao.idativosandroid.DBUtils.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity
public class ServicosAdicionais {
    @PrimaryKey(autoGenerate = true)
    private int Id;

    private int IdOriginal;

    private String RowVersion;

    private String Servico;

    private String Descricao;

    private String Modalidade;

    private Boolean FlagObrigatorio;

    private Boolean FlagAtivo;

    private int TarefaItemIdOriginal;

    public int getIdOriginal() {
        return IdOriginal;
    }

    public void setIdOriginal(int idOriginal) {
        IdOriginal = idOriginal;
    }

    public String getServico() {
        return Servico;
    }

    public void setServico(String servico) {
        Servico = servico;
    }

    public String getDescricao() {
        return Descricao;
    }

    public void setDescricao(String descricao) {
        Descricao = descricao;
    }

    public String getModalidade() {
        return Modalidade;
    }

    public void setModalidade(String modalidade) {
        Modalidade = modalidade;
    }

    public Boolean getFlagObrigatorio() {
        return FlagObrigatorio;
    }

    public void setFlagObrigatorio(Boolean flagObrigatorio) {
        FlagObrigatorio = flagObrigatorio;
    }

    public Boolean getFlagAtivo() {
        return FlagAtivo;
    }

    public void setFlagAtivo(Boolean flagAtivo) {
        FlagAtivo = flagAtivo;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getTarefaItemIdOriginal() {
        return TarefaItemIdOriginal;
    }

    public void setTarefaItemIdOriginal(int tarefaItemIdOriginal) {
        TarefaItemIdOriginal = tarefaItemIdOriginal;
    }

    public String getRowVersion() {
        return RowVersion;
    }

    public void setRowVersion(String rowVersion) {
        RowVersion = rowVersion;
    }
}

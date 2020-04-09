package br.com.marcosmilitao.idativosandroid.DBUtils.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

import br.com.marcosmilitao.idativosandroid.helper.TimeStampConverter;

@Entity
public class ListaMateriaisListaTarefas {
    @PrimaryKey(autoGenerate = true)
    private int Id;

    private int IdOriginal;

    private String RowVersion;

    @TypeConverters({TimeStampConverter.class})
    private Date DataInicio;

    @TypeConverters({TimeStampConverter.class})
    private Date DataConclusao;

    private String Observacao;

    private int ProcessoIdOriginal;

    private int CadastroMateriaisIdOriginal;

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

    public Date getDataInicio() {
        return DataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        DataInicio = dataInicio;
    }

    public Date getDataConclusao() {
        return DataConclusao;
    }

    public void setDataConclusao(Date dataConclusao) {
        DataConclusao = dataConclusao;
    }

    public String getObservacao() {
        return Observacao;
    }

    public void setObservacao(String observacao) {
        Observacao = observacao;
    }

    public int getProcessoIdOriginal() {
        return ProcessoIdOriginal;
    }

    public void setProcessoIdOriginal(int processoIdOriginal) {
        ProcessoIdOriginal = processoIdOriginal;
    }

    public int getCadastroMateriaisIdOriginal() {
        return CadastroMateriaisIdOriginal;
    }

    public void setCadastroMateriaisIdOriginal(int cadastroMateriaisIdOriginal) {
        CadastroMateriaisIdOriginal = cadastroMateriaisIdOriginal;
    }

}

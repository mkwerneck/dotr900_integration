package br.com.marcosmilitao.idativosandroid.DBUtils.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

import br.com.marcosmilitao.idativosandroid.helper.TimeStampConverter;

@Entity
public class ListaTarefas {
    @PrimaryKey(autoGenerate = true)
    private int Id;

    private int IdOriginal;

    private String RowVersion;

    private String Status;

    @TypeConverters({TimeStampConverter.class})
    private Date DataInicio;

    @TypeConverters({TimeStampConverter.class})
    private Date DataConclusao;

    @TypeConverters({TimeStampConverter.class})
    private Date DataCancelamento;

    private int ProcessoIdOriginal;

    private int TarefaIdOriginal;

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

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
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

    public Date getDataCancelamento() {
        return DataCancelamento;
    }

    public void setDataCancelamento(Date dataCancelamento) {
        DataCancelamento = dataCancelamento;
    }

    public int getProcessoIdOriginal() {
        return ProcessoIdOriginal;
    }

    public void setProcessoIdOriginal(int processoIdOriginal) {
        ProcessoIdOriginal = processoIdOriginal;
    }

    public int getTarefaIdOriginal() {
        return TarefaIdOriginal;
    }

    public void setTarefaIdOriginal(int tarefaIdOriginal) {
        TarefaIdOriginal = tarefaIdOriginal;
    }

}

package br.com.marcosmilitao.idativosandroid.DBUtils.Models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

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
    private Date DataFimPrevisao;

    @TypeConverters({TimeStampConverter.class})
    private Date DataFimReal;

    @TypeConverters({TimeStampConverter.class})
    private Date DataCancelamento;

    private String Dominio;

    private int Processo;

    private int TarefaItemIdOriginal;

    private int CadsatroEquipamentosItemIdOriginal;




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

    public Date getDataFimPrevisao() {
        return DataFimPrevisao;
    }

    public void setDataFimPrevisao(Date dataFimPrevisao) {
        DataFimPrevisao = dataFimPrevisao;
    }

    public Date getDataFimReal() {
        return DataFimReal;
    }

    public void setDataFimReal(Date dataFimReal) {
        DataFimReal = dataFimReal;
    }

    public Date getDataCancelamento() {
        return DataCancelamento;
    }

    public void setDataCancelamento(Date dataCancelamento) {
        DataCancelamento = dataCancelamento;
    }

    public String getDominio() {
        return Dominio;
    }

    public void setDominio(String dominio) {
        Dominio = dominio;
    }

    public int getProcesso() {
        return Processo;
    }

    public void setProcesso(int processo) {
        Processo = processo;
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

    public int getCadsatroEquipamentosItemIdOriginal() {
        return CadsatroEquipamentosItemIdOriginal;
    }

    public void setCadsatroEquipamentosItemIdOriginal(int cadsatroEquipamentosItemIdOriginal) {
        CadsatroEquipamentosItemIdOriginal = cadsatroEquipamentosItemIdOriginal;
    }
}

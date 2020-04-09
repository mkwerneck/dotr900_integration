package br.com.marcosmilitao.idativosandroid.DBUtils.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

import br.com.marcosmilitao.idativosandroid.helper.TimeStampConverter;

/**
 * Created by marcoswerneck on 24/09/19.
 */

@Entity
public class Processos {
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
    private Date DataCancelado;

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

    public Date getDataCancelado() {
        return DataCancelado;
    }

    public void setDataCancelado(Date dataCancelado) {
        DataCancelado = dataCancelado;
    }

    public int getCadsatroEquipamentosItemIdOriginal() {
        return CadsatroEquipamentosItemIdOriginal;
    }

    public void setCadsatroEquipamentosItemIdOriginal(int cadsatroEquipamentosItemIdOriginal) {
        CadsatroEquipamentosItemIdOriginal = cadsatroEquipamentosItemIdOriginal;
    }
}

package br.com.marcosmilitao.idativosandroid.DBUtils.Models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import java.util.Date;
import br.com.marcosmilitao.idativosandroid.helper.TimeStampConverter;

@Entity
public class ListaServicosListaTarefas {
    @PrimaryKey(autoGenerate = true)
    private int Id;

    private int IdOriginal;

    private String RowVersion;

    private String Status;

    @TypeConverters({TimeStampConverter.class})
    private Date DataInicio;

    @TypeConverters({TimeStampConverter.class})
    private Date DataConclusao;

    private String Resultado;

    private int ListaTarefasItemIdOriginal;

    private int ServicoAdicinalItemIdOriginal;

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

    public Date getDataConclusao() {
        return DataConclusao;
    }

    public void setDataConclusao(Date dataConclusao) {
        DataConclusao = dataConclusao;
    }

    public String getResultado() {
        return Resultado;
    }

    public void setResultado(String resultado) {
        Resultado = resultado;
    }

    public int getListaTarefasItemIdOriginal() {
        return ListaTarefasItemIdOriginal;
    }

    public void setListaTarefasItemIdOriginal(int listaTarefasItemIdOriginal) {
        ListaTarefasItemIdOriginal = listaTarefasItemIdOriginal;
    }

    public int getServicoAdicinalItemIdOriginal() {
        return ServicoAdicinalItemIdOriginal;
    }

    public void setServicoAdicinalItemIdOriginal(int servicoAdicinalItemIdOriginal) {
        ServicoAdicinalItemIdOriginal = servicoAdicinalItemIdOriginal;
    }

    public String getRowVersion() {
        return RowVersion;
    }

    public void setRowVersion(String rowVersion) {
        RowVersion = rowVersion;
    }
}

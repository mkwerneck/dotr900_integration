package br.com.marcosmilitao.idativosandroid.DBUtils.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

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
    private Date UltimaAtualizacao;

    private String Resultado;

    private int ListaTarefaIdOriginal;

    private int ServicoAdicionalIdOriginal;

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

    public Date getUltimaAtualizacao() {
        return UltimaAtualizacao;
    }

    public void setUltimaAtualizacao(Date ultimaAtualizacao) {
        UltimaAtualizacao = ultimaAtualizacao;
    }

    public String getResultado() {
        return Resultado;
    }

    public void setResultado(String resultado) {
        Resultado = resultado;
    }

    public int getListaTarefaIdOriginal() {
        return ListaTarefaIdOriginal;
    }

    public void setListaTarefaIdOriginal(int listaTarefaIdOriginal) {
        ListaTarefaIdOriginal = listaTarefaIdOriginal;
    }

    public int getServicoAdicionalIdOriginal() {
        return ServicoAdicionalIdOriginal;
    }

    public void setServicoAdicionalIdOriginal(int servicoAdicionalIdOriginal) {
        ServicoAdicionalIdOriginal = servicoAdicionalIdOriginal;
    }

}

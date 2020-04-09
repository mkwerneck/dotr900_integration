package br.com.marcosmilitao.idativosandroid.DBUtils.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;
import br.com.marcosmilitao.idativosandroid.helper.TimeStampConverter;

@Entity
public class UPMOBListaServicosListaTarefas {
    @PrimaryKey(autoGenerate = true)
    private int Id;

    private int IdOriginal;

    @TypeConverters({TimeStampConverter.class})
    private Date DataHoraEvento;

   private String Resultado;

    @TypeConverters({TimeStampConverter.class})
    private Date UltimaAtualizacao;

    private int ListaTarefaId;

    private int ServicoId;

    private String CodColetor;

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

    public Date getDataHoraEvento() {
        return DataHoraEvento;
    }

    public void setDataHoraEvento(Date dataHoraEvento) {
        DataHoraEvento = dataHoraEvento;
    }

    public String getResultado() {
        return Resultado;
    }

    public void setResultado(String resultado) {
        Resultado = resultado;
    }

    public Date getUltimaAtualizacao() {
        return UltimaAtualizacao;
    }

    public void setUltimaAtualizacao(Date ultimaAtualizacao) {
        UltimaAtualizacao = ultimaAtualizacao;
    }

    public int getListaTarefaId() {
        return ListaTarefaId;
    }

    public void setListaTarefaId(int listaTarefaId) {
        ListaTarefaId = listaTarefaId;
    }

    public int getServicoId() {
        return ServicoId;
    }

    public void setServicoId(int servicoId) {
        ServicoId = servicoId;
    }

    public String getCodColetor() {
        return CodColetor;
    }

    public void setCodColetor(String codColetor) {
        CodColetor = codColetor;
    }
}

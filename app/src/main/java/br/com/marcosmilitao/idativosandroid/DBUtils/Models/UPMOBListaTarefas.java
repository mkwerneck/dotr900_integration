package br.com.marcosmilitao.idativosandroid.DBUtils.Models;


import java.util.Date;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import br.com.marcosmilitao.idativosandroid.helper.TimeStampConverter;

@Entity
public class UPMOBListaTarefas {
    @PrimaryKey(autoGenerate = true)
    private int Id;

    private int IdOriginal;

    @TypeConverters({TimeStampConverter.class})
    private Date DataHoraEvento;

    private int ProcessoId;

    private int TarefaId;

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

    public int getProcessoId() {
        return ProcessoId;
    }

    public void setProcessoId(int processoId) {
        ProcessoId = processoId;
    }

    public int getTarefaId() {
        return TarefaId;
    }

    public void setTarefaId(int tarefaId) {
        TarefaId = tarefaId;
    }

    public String getCodColetor() {
        return CodColetor;
    }

    public void setCodColetor(String codColetor) {
        CodColetor = codColetor;
    }
}

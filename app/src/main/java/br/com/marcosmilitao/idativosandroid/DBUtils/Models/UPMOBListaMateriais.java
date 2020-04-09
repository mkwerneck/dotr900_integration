package br.com.marcosmilitao.idativosandroid.DBUtils.Models;



import java.util.Date;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import br.com.marcosmilitao.idativosandroid.helper.TimeStampConverter;

/**
 * Created by marcoswerneck on 03/10/19.
 */

@Entity
public class UPMOBListaMateriais {
    @PrimaryKey(autoGenerate = true)
    private int Id;

    private int IdOriginal;

    @TypeConverters({TimeStampConverter.class})
    private Date DataHoraEvento;

    private int CadastroMateriaisId;

    private int ProcessoId;

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

    public int getCadastroMateriaisId() {
        return CadastroMateriaisId;
    }

    public void setCadastroMateriaisId(int cadastroMateriaisId) {
        CadastroMateriaisId = cadastroMateriaisId;
    }

    public int getProcessoId() {
        return ProcessoId;
    }

    public void setProcessoId(int processoId) {
        ProcessoId = processoId;
    }

    public String getCodColetor() {
        return CodColetor;
    }

    public void setCodColetor(String codColetor) {
        CodColetor = codColetor;
    }
}

package br.com.marcosmilitao.idativosandroid.DBUtils.Models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.util.Date;

import br.com.marcosmilitao.idativosandroid.helper.TimeStampConverter;

/**
 * Created by marcoswerneck on 21/08/19.
 */

@Entity
public class UPMOBDescartes {
    @PrimaryKey(autoGenerate = true)
    private int Id;
    private int CadastromateriaisId;
    private String ApplicationUserId;
    private String Motivo;
    @TypeConverters({TimeStampConverter.class})
    private Date DataHoraEvento;
    private String CodColetor;
    private String DescricaoErro;
    private boolean FlagErro;
    private boolean FlagProcess;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getCadastromateriaisId() {
        return CadastromateriaisId;
    }

    public void setCadastromateriaisId(int cadastromateriaisId) {
        CadastromateriaisId = cadastromateriaisId;
    }

    public String getApplicationUserId() {
        return ApplicationUserId;
    }

    public void setApplicationUserId(String applicationUserId) {
        ApplicationUserId = applicationUserId;
    }

    public String getMotivo() {
        return Motivo;
    }

    public void setMotivo(String motivo) {
        Motivo = motivo;
    }

    public Date getDataHoraEvento() {
        return DataHoraEvento;
    }

    public void setDataHoraEvento(Date dataHoraEvento) {
        DataHoraEvento = dataHoraEvento;
    }

    public String getCodColetor() {
        return CodColetor;
    }

    public void setCodColetor(String codColetor) {
        CodColetor = codColetor;
    }

    public String getDescricaoErro() {
        return DescricaoErro;
    }

    public void setDescricaoErro(String descricaoErro) {
        DescricaoErro = descricaoErro;
    }

    public boolean isFlagErro() {
        return FlagErro;
    }

    public void setFlagErro(boolean flagErro) {
        FlagErro = flagErro;
    }

    public boolean isFlagProcess() {
        return FlagProcess;
    }

    public void setFlagProcess(boolean flagProcess) {
        FlagProcess = flagProcess;
    }


}

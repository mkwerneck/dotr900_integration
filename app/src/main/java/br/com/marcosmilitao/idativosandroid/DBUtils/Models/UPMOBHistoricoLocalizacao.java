package br.com.marcosmilitao.idativosandroid.DBUtils.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

import br.com.marcosmilitao.idativosandroid.helper.TimeStampConverter;

@Entity
public class UPMOBHistoricoLocalizacao {
    @PrimaryKey(autoGenerate = true)
    private int Id;

    private int CadastroMateriaisId;

    private int PosicaoId;

    @TypeConverters({TimeStampConverter.class})
    private Date DataHoraEvento;

    private String Processo;

    private String CodColetor;

    private String DescricaoErro;

    private Boolean FlagErro;

    private Boolean FlagProcess;

    public int getCadastroMateriaisId() {
        return CadastroMateriaisId;
    }

    public void setCadastroMateriaisId(int cadastroMateriaisId) {
        CadastroMateriaisId = cadastroMateriaisId;
    }

    public int getPosicaoId() {
        return PosicaoId;
    }

    public void setPosicaoId(int posicaoId) {
        PosicaoId = posicaoId;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public Date getDataHoraEvento() {
        return DataHoraEvento;
    }

    public void setDataHoraEvento(Date dataHoraEvento) {
        DataHoraEvento = dataHoraEvento;
    }

    public String getProcesso() {
        return Processo;
    }

    public void setProcesso(String processo) {
        Processo = processo;
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

    public Boolean getFlagErro() {
        return FlagErro;
    }

    public void setFlagErro(Boolean flagErro) {
        FlagErro = flagErro;
    }

    public Boolean getFlagProcess() {
        return FlagProcess;
    }

    public void setFlagProcess(Boolean flagProcess) {
        FlagProcess = flagProcess;
    }
}

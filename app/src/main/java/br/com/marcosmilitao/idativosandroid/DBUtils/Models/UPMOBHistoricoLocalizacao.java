package br.com.marcosmilitao.idativosandroid.DBUtils.Models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.util.Date;

import br.com.marcosmilitao.idativosandroid.helper.TimeStampConverter;

@Entity
public class UPMOBHistoricoLocalizacao {
    @PrimaryKey(autoGenerate = true)
    private int Id;

    private String TAGID;

    private String TAGIDPosicao;

    @TypeConverters({TimeStampConverter.class})
    private Date DataHoraEvento;

    private String Processo;

    private String Dominio;

    private int Quantidade;

    private String Modalidade;

    private String CodColetor;

    private String DescricaoErro;

    private Boolean FlagErro;

    private Boolean FlagAtualizar;

    private Boolean FlagProcess;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTAGID() {
        return TAGID;
    }

    public void setTAGID(String TAGID) {
        this.TAGID = TAGID;
    }

    public String getTAGIDPosicao() {
        return TAGIDPosicao;
    }

    public void setTAGIDPosicao(String TAGIDPosicao) {
        this.TAGIDPosicao = TAGIDPosicao;
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

    public String getDominio() {
        return Dominio;
    }

    public void setDominio(String dominio) {
        Dominio = dominio;
    }

    public int getQuantidade() {
        return Quantidade;
    }

    public void setQuantidade(int quantidade) {
        Quantidade = quantidade;
    }

    public String getModalidade() {
        return Modalidade;
    }

    public void setModalidade(String modalidade) {
        Modalidade = modalidade;
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

    public Boolean getFlagAtualizar() {
        return FlagAtualizar;
    }

    public void setFlagAtualizar(Boolean flagAtualizar) {
        FlagAtualizar = flagAtualizar;
    }

    public Boolean getFlagProcess() {
        return FlagProcess;
    }

    public void setFlagProcess(Boolean flagProcess) {
        FlagProcess = flagProcess;
    }
}

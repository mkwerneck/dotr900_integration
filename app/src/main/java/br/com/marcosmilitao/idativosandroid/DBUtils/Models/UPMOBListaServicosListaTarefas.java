package br.com.marcosmilitao.idativosandroid.DBUtils.Models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import java.util.Date;
import br.com.marcosmilitao.idativosandroid.helper.TimeStampConverter;

@Entity
public class UPMOBListaServicosListaTarefas {
    @PrimaryKey(autoGenerate = true)
    private int Id;

    private int IdOriginal;

    private String CodTarefa;

    private String Servico;

    private String Resultado;

    private String Status;

    @TypeConverters({TimeStampConverter.class})
    private Date DataHoraEvento;

    @TypeConverters({TimeStampConverter.class})
    private Date DataInicio;

    @TypeConverters({TimeStampConverter.class})
    private Date DataConclusao;

    @TypeConverters({TimeStampConverter.class})
    private Date DataCancelamento;

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

    public int getIdOriginal() {
        return IdOriginal;
    }

    public void setIdOriginal(int idOriginal) {
        IdOriginal = idOriginal;
    }

    public String getCodTarefa() {
        return CodTarefa;
    }

    public void setCodTarefa(String codTarefa) {
        CodTarefa = codTarefa;
    }

    public String getServico() {
        return Servico;
    }

    public void setServico(String servico) {
        Servico = servico;
    }

    public String getResultado() {
        return Resultado;
    }

    public void setResultado(String resultado) {
        Resultado = resultado;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public Date getDataHoraEvento() {
        return DataHoraEvento;
    }

    public void setDataHoraEvento(Date dataHoraEvento) {
        DataHoraEvento = dataHoraEvento;
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

    public Date getDataCancelamento() {
        return DataCancelamento;
    }

    public void setDataCancelamento(Date dataCancelamento) {
        DataCancelamento = dataCancelamento;
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

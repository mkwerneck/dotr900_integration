package br.com.marcosmilitao.idativosandroid.DBUtils.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;
import br.com.marcosmilitao.idativosandroid.helper.TimeStampConverter;

@Entity
public class UPMOBCadastroMateriais {
    @PrimaryKey(autoGenerate = true)
    private int Id;
    private int IdOriginal;
    private String Patrimonio;
    private String NumSerie;
    private int Quantidade;
    @TypeConverters({TimeStampConverter.class})
    private Date DataFabricacao;
    @TypeConverters({TimeStampConverter.class})
    private Date DataValidade;
    @TypeConverters({TimeStampConverter.class})
    private Date DataHoraEvento;
    private String DadosTecnicos;
    private String TAGID;
    private int PosicaoOriginalItemId;
    private String NotaFiscal;
    @TypeConverters({TimeStampConverter.class})
    private Date DataEntradaNotaFiscal;
    private double ValorUnitario;
    private String CodColetor;
    private String DescricaoErro;
    private Boolean FlagErro;
    private Boolean FlagAtualizar;
    private Boolean FlagProcess;
    private int ModeloMateriaisItemId;
    private String Status;

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

    public String getPatrimonio() {
        return Patrimonio;
    }

    public void setPatrimonio(String patrimonio) {
        Patrimonio = patrimonio;
    }

    public String getNumSerie() {
        return NumSerie;
    }

    public void setNumSerie(String numSerie) {
        NumSerie = numSerie;
    }

    public int getQuantidade() {
        return Quantidade;
    }

    public void setQuantidade(int quantidade) {
        Quantidade = quantidade;
    }

    public Date getDataFabricacao() {
        return DataFabricacao;
    }

    public void setDataFabricacao(Date dataFabricacao) {
        DataFabricacao = dataFabricacao;
    }

    public Date getDataValidade() {
        return DataValidade;
    }

    public void setDataValidade(Date dataValidade) {
        DataValidade = dataValidade;
    }

    public Date getDataHoraEvento() {
        return DataHoraEvento;
    }

    public void setDataHoraEvento(Date dataHoraEvento) {
        DataHoraEvento = dataHoraEvento;
    }

    public String getDadosTecnicos() {
        return DadosTecnicos;
    }

    public void setDadosTecnicos(String dadosTecnicos) {
        DadosTecnicos = dadosTecnicos;
    }

    public String getTAGID() {
        return TAGID;
    }

    public void setTAGID(String TAGID) {
        this.TAGID = TAGID;
    }

    public double getValorUnitario() {
        return ValorUnitario;
    }

    public void setValorUnitario(double valorUnitario) {
        ValorUnitario = valorUnitario;
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

    public int getPosicaoOriginalItemId() {
        return PosicaoOriginalItemId;
    }

    public void setPosicaoOriginalItemId(int posicaoOriginalItemId) {
        PosicaoOriginalItemId = posicaoOriginalItemId;
    }

    public String getNotaFiscal() {
        return NotaFiscal;
    }

    public void setNotaFiscal(String notaFiscal) {
        NotaFiscal = notaFiscal;
    }

    public Date getDataEntradaNotaFiscal() {
        return DataEntradaNotaFiscal;
    }

    public void setDataEntradaNotaFiscal(Date dataEntradaNotaFiscal) {
        DataEntradaNotaFiscal = dataEntradaNotaFiscal;
    }

    public int getModeloMateriaisItemId() {
        return ModeloMateriaisItemId;
    }

    public void setModeloMateriaisItemId(int modeloMateriaisItemId) {
        ModeloMateriaisItemId = modeloMateriaisItemId;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}

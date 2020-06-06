package br.com.marcosmilitao.idativosandroid.DBUtils.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

import br.com.marcosmilitao.idativosandroid.helper.TimeStampConverter;

@Entity
public class CadastroMateriais {
    @PrimaryKey(autoGenerate = true)
    private int Id;

    private int IdOriginal;

    private String RowVersion;

    private String NumSerie;

    private String Patrimonio;

    private int Quantidade;

    @TypeConverters({TimeStampConverter.class})
    private Date DataCadastro;

    @TypeConverters({TimeStampConverter.class})
    private Date DataValidadeCalibracao;

    @TypeConverters({TimeStampConverter.class})
    private Date DataValidadeInspecao;

    private double ValorUnitario;

    private String DadosTecnicos;

    private String NotaFiscal;

    @TypeConverters({TimeStampConverter.class})
    private Date DataEntradaNotaFiscal;

    private String Categoria;

    private int ModeloMateriaisItemIdOriginal;

    private int PosicaoOriginalItemIdoriginal;

    private String TAGID;

    private boolean EmUso;

    private String Status;

    public boolean isEmUso() {
        return EmUso;
    }

    public void setEmUso(boolean emUso) {
        EmUso = emUso;
    }

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

    public String getNumSerie() {
        return NumSerie;
    }

    public void setNumSerie(String numSerie) {
        NumSerie = numSerie;
    }

    public String getPatrimonio() {
        return Patrimonio;
    }

    public void setPatrimonio(String patrimonio) {
        Patrimonio = patrimonio;
    }

    public int getQuantidade() {
        return Quantidade;
    }

    public void setQuantidade(int quantidade) {
        Quantidade = quantidade;
    }

    public Date getDataCadastro() {
        return DataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        DataCadastro = dataCadastro;
    }

    public Date getDataValidadeCalibracao() {
        return DataValidadeCalibracao;
    }

    public void setDataValidadeCalibracao(Date dataValidadeCalibracao) {
        DataValidadeCalibracao = dataValidadeCalibracao;
    }

    public Date getDataValidadeInspecao() {
        return DataValidadeInspecao;
    }

    public void setDataValidadeInspecao(Date dataValidadeInspecao) {
        DataValidadeInspecao = dataValidadeInspecao;
    }

    public double getValorUnitario() {
        return ValorUnitario;
    }

    public void setValorUnitario(double valorUnitario) {
        ValorUnitario = valorUnitario;
    }

    public String getDadosTecnicos() {
        return DadosTecnicos;
    }

    public void setDadosTecnicos(String dadosTecnicos) {
        DadosTecnicos = dadosTecnicos;
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

    public String getCategoria() {
        return Categoria;
    }

    public void setCategoria(String categoria) {
        Categoria = categoria;
    }


    public int getModeloMateriaisItemIdOriginal() {
        return ModeloMateriaisItemIdOriginal;
    }

    public void setModeloMateriaisItemIdOriginal(int modeloMateriaisItemIdOriginal) {
        ModeloMateriaisItemIdOriginal = modeloMateriaisItemIdOriginal;
    }

    public int getPosicaoOriginalItemIdoriginal() {
        return PosicaoOriginalItemIdoriginal;
    }

    public void setPosicaoOriginalItemIdoriginal(int posicaoOriginalItemIdoriginal) {
        PosicaoOriginalItemIdoriginal = posicaoOriginalItemIdoriginal;
    }

    public String getTAGID() {
        return TAGID;
    }

    public void setTAGID(String TAGID) {
        this.TAGID = TAGID;
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
}

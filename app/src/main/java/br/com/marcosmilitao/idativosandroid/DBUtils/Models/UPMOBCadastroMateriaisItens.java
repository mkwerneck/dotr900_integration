package br.com.marcosmilitao.idativosandroid.DBUtils.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

import br.com.marcosmilitao.idativosandroid.helper.TimeStampConverter;

@Entity
public class UPMOBCadastroMateriaisItens {
    @PrimaryKey(autoGenerate = true)
    private int Id;

    private int IdOriginal;

    private String Patrimonio;

    private String NumSerie;

    private int Quantidade;

    @TypeConverters({TimeStampConverter.class})
    private Date DataHoraEvento;

    @TypeConverters({TimeStampConverter.class})
    private Date DataValidade;

    @TypeConverters({TimeStampConverter.class})
    private Date DataFabricacao;

    private int CadastroMateriaisItemIdOriginal;

    private int ModeloMateriaisItemIdOriginal;

    private String CodColetor;

    private String DescricaoErro;

    private Boolean FlagErro;

    private Boolean FlagAtualizar;

    private Boolean FlagProcess;

    public int getCadastroMateriaisItemIdOriginal() {
        return CadastroMateriaisItemIdOriginal;
    }

    public void setCadastroMateriaisItemIdOriginal(int cadastroMateriaisItemIdOriginal) {
        CadastroMateriaisItemIdOriginal = cadastroMateriaisItemIdOriginal;
    }

    public int getModeloMateriaisItemIdOriginal() {
        return ModeloMateriaisItemIdOriginal;
    }

    public void setModeloMateriaisItemIdOriginal(int modeloMateriaisItemIdOriginal) {
        ModeloMateriaisItemIdOriginal = modeloMateriaisItemIdOriginal;
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

    public Date getDataHoraEvento() {
        return DataHoraEvento;
    }

    public void setDataHoraEvento(Date dataHoraEvento) {
        DataHoraEvento = dataHoraEvento;
    }

    public Date getDataValidade() {
        return DataValidade;
    }

    public void setDataValidade(Date dataValidade) {
        DataValidade = dataValidade;
    }

    public Date getDataFabricacao() {
        return DataFabricacao;
    }

    public void setDataFabricacao(Date dataFabricacao) {
        DataFabricacao = dataFabricacao;
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

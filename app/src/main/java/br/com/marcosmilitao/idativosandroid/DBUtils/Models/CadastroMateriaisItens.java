package br.com.marcosmilitao.idativosandroid.DBUtils.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

import br.com.marcosmilitao.idativosandroid.helper.TimeStampConverter;

@Entity
public class CadastroMateriaisItens {
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
    private Date DataFabricacao;

    @TypeConverters({TimeStampConverter.class})
    private Date DataValidade;

    private int CadastroMateriaisItemIdOriginal;

    private int ModeloMateriaisItemIdOriginal;

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

    public String getRowVersion() {
        return RowVersion;
    }

    public void setRowVersion(String rowVersion) {
        RowVersion = rowVersion;
    }
}

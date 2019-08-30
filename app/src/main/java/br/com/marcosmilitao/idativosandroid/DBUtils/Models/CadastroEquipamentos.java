package br.com.marcosmilitao.idativosandroid.DBUtils.Models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import java.util.Date;
import br.com.marcosmilitao.idativosandroid.helper.TimeStampConverter;

@Entity
public class CadastroEquipamentos {
    @PrimaryKey(autoGenerate = true)
    private int Id;

    private int IdOriginal;

    private String RowVersion;

    private String TraceNumber;

    @TypeConverters({TimeStampConverter.class})
    private Date DataCadastro;

    @TypeConverters({TimeStampConverter.class})
    private Date DataFabricacao;

    private String Status;

    private int ModeloEquipamentoItemIdOriginal;

    private String Localizacao;

    private String TAGID;

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

    public String getTraceNumber() {
        return TraceNumber;
    }

    public void setTraceNumber(String traceNumber) {
        TraceNumber = traceNumber;
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

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public int getModeloEquipamentoItemIdOriginal() {
        return ModeloEquipamentoItemIdOriginal;
    }

    public void setModeloEquipamentoItemIdOriginal(int modeloEquipamentoItemIdOriginal) {
        ModeloEquipamentoItemIdOriginal = modeloEquipamentoItemIdOriginal;
    }

    public String getLocalizacao() {
        return Localizacao;
    }

    public void setLocalizacao(String localizacao) {
        Localizacao = localizacao;
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
}

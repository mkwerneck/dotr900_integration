package br.com.marcosmilitao.idativosandroid.DBUtils.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

import br.com.marcosmilitao.idativosandroid.helper.TimeStampConverter;

/**
 * Created by marcoswerneck on 03/10/19.
 */

@Entity
public class UPMOBProcesso {
    @PrimaryKey(autoGenerate = true)
    private int Id;

    private int IdOriginal;

    @TypeConverters({TimeStampConverter.class})
    private Date DataHoraEvento;

    private int CadastroEquipamentoId;

    private String DescricaoErro;

    private boolean FlagErro;

    private boolean FlagProcess;

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

    public int getCadastroEquipamentoId() {
        return CadastroEquipamentoId;
    }

    public void setCadastroEquipamentoId(int cadastroEquipamentoId) {
        CadastroEquipamentoId = cadastroEquipamentoId;
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

    public String getCodColetor() {
        return CodColetor;
    }

    public void setCodColetor(String codColetor) {
        CodColetor = codColetor;
    }
}

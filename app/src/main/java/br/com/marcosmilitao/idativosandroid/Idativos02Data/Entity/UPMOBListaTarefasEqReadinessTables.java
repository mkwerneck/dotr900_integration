package br.com.marcosmilitao.idativosandroid.Idativos02Data.Entity;

import java.util.Date;

/**
 * Created by Vinicius on 26/12/2016.
 */

public class UPMOBListaTarefasEqReadinessTables {

    private long Id;
    private String Cod_Tarefa;
    private String Titulo_Tarefa;
    private String Status;
    private Date Data_Inicio;
    private Date Data_Fim_Previsao;
    private Date Data_Fim_Real;
    private String Equipment_Type;
    private long WorksheetTable_FK;
    private Boolean FlagMobileUpdate;

    public UPMOBListaTarefasEqReadinessTables()

    {

    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getCod_Tarefa() {
        return Cod_Tarefa;
    }

    public void setCod_Tarefa(String cod_Tarefa) {
        Cod_Tarefa = cod_Tarefa;
    }

    public String getTitulo_Tarefa() {
        return Titulo_Tarefa;
    }

    public void setTitulo_Tarefa(String titulo_Tarefa) {
        Titulo_Tarefa = titulo_Tarefa;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public Date getData_Inicio() {
        return Data_Inicio;
    }

    public void setData_Inicio(Date data_Inicio) {
        Data_Inicio = data_Inicio;
    }

    public Date getData_Fim_Previsao() {
        return Data_Fim_Previsao;
    }

    public void setData_Fim_Previsao(Date data_Fim_Previsao) {
        Data_Fim_Previsao = data_Fim_Previsao;
    }

    public String getEquipment_Type() {
        return Equipment_Type;
    }

    public void setEquipment_Type(String equipment_Type) {
        Equipment_Type = equipment_Type;
    }

    public long getWorksheetTable_FK() {
        return WorksheetTable_FK;
    }

    public void setWorksheetTable_FK(long worksheetTable_FK) {
        WorksheetTable_FK = worksheetTable_FK;
    }

    public Boolean getFlagMobileUpdate() {
        return FlagMobileUpdate;
    }

    public void setFlagMobileUpdate(Boolean flagMobileUpdate) {
        FlagMobileUpdate = flagMobileUpdate;
    }

    public Date getData_Fim_Real() {
        return Data_Fim_Real;
    }

    public void setData_Fim_Real(Date data_Fim_Real) {
        Data_Fim_Real = data_Fim_Real;
    }
}

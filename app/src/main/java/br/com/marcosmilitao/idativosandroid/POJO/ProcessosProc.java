package br.com.marcosmilitao.idativosandroid.POJO;

/**
 * Created by marcoswerneck on 25/09/19.
 */

public class ProcessosProc {
    public int IdOriginal;
    public String Tarefa;
    public String Ativo;
    public String CodProcesso;
    public String Status;
    public String DataInicio;
    public boolean EntradaAlmoxarifado;

    public int getIdOriginal() {
        return IdOriginal;
    }

    public void setIdOriginal(int idOriginal) {
        IdOriginal = idOriginal;
    }

    public String getTarefa() {
        return Tarefa;
    }

    public void setTarefa(String tarefa) {
        Tarefa = tarefa;
    }

    public String getAtivo() {
        return Ativo;
    }

    public void setAtivo(String ativo) {
        Ativo = ativo;
    }

    public String getCodProcesso() {
        return CodProcesso;
    }

    public void setCodProcesso(String codProcesso) {
        CodProcesso = codProcesso;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getDataInicio() {
        return DataInicio;
    }

    public void setDataInicio(String dataInicio) {
        DataInicio = dataInicio;
    }

    public boolean isEntradaAlmoxarifado() {
        return EntradaAlmoxarifado;
    }

    public void setEntradaAlmoxarifado(boolean entradaAlmoxarifado) {
        EntradaAlmoxarifado = entradaAlmoxarifado;
    }
}

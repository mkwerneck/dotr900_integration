package br.com.marcosmilitao.idativosandroid.POJO;

import net.sourceforge.jtds.jdbc.DateTime;

import java.util.Date;

/**
 * Created by marcoswerneck on 03/10/19.
 */

public class ListaServicosExecucaoTarefas {
    private int IdOriginal;
    private int ServicoId;
    private String Servico;
    private String Resultado;
    private Date UltimaAtualizacao;
    private String Modalidade;
    private boolean Obrigatorio;

    public int getIdOriginal() {
        return IdOriginal;
    }

    public void setIdOriginal(int idOriginal) {
        IdOriginal = idOriginal;
    }

    public int getServicoId() {
        return ServicoId;
    }

    public void setServicoId(int servicoId) {
        ServicoId = servicoId;
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

    public Date getUltimaAtualizacao() {
        return UltimaAtualizacao;
    }

    public void setUltimaAtualizacao(Date ultimaAtualizacao) {
        UltimaAtualizacao = ultimaAtualizacao;
    }

    public String getModalidade() {
        return Modalidade;
    }

    public void setModalidade(String modalidade) {
        Modalidade = modalidade;
    }

    public boolean isObrigatorio() {
        return Obrigatorio;
    }

    public void setObrigatorio(boolean obrigatorio) {
        Obrigatorio = obrigatorio;
    }
}

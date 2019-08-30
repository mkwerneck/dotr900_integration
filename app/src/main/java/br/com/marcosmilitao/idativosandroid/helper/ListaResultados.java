package br.com.marcosmilitao.idativosandroid.helper;

/**
 * Created by marcoswerneck on 23/07/2018.
 */

public class ListaResultados {

    private String resultado;
    private String idServico;
    private boolean isChecked;

    public ListaResultados(String resultado, boolean isChecked, String idServico){
        this.resultado = resultado;
        this.idServico = idServico;
        this.isChecked = isChecked;
    }

    public String getResultado(){
        return resultado;
    }

    public void setResultado(String resultado){
        this.resultado = resultado;
    }

    public boolean getIsChecked(){
        return isChecked;
    }

    public void setIsChecked(boolean isChecked){
        this.isChecked = isChecked;
    }

    public String getIdServico(){
        return idServico;
    }

    public void setIdServico(String idServico){
        this.idServico = idServico;
    }
}

package br.com.marcosmilitao.idativosandroid.POJO;

import br.com.marcosmilitao.idativosandroid.DBUtils.Models.ServicosAdicionais;

/**
 * Created by marcoswerneck on 20/02/19.
 */

public class NovoProcesso_Servicos {

    public ServicosAdicionais ServicosAdicional;

    public String Resultado;

    public int IdOriginal;

    public ServicosAdicionais getServicosAdicional() {
        return ServicosAdicional;
    }

    public void setServicosAdicional(ServicosAdicionais servicosAdicional) {
        ServicosAdicional = servicosAdicional;
    }

    public String getResultado() {
        return Resultado;
    }

    public void setResultado(String resultado) {
        Resultado = resultado;
    }

    public int getIdOriginal() {
        return IdOriginal;
    }

    public void setIdOriginal(int idOriginal){
        this.IdOriginal = idOriginal;
    }

}

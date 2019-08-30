package br.com.marcosmilitao.idativosandroid.POJO;

/**
 * Created by marcoswerneck on 14/08/19.
 */

public class ModeloMateriaisCF {

    private int idOriginal;
    private String modelo;
    private String numProduto;

    public ModeloMateriaisCF(String modelo, String numProduto)
    {
        this.modelo = modelo;
        this.numProduto = numProduto;
    }

    public int getIdOriginal() {
        return idOriginal;
    }

    public void setIdOriginal(int idOriginal) {
        this.idOriginal = idOriginal;
    }

    public void setModelo(String modelo)
    {
        this.modelo = modelo;
    }

    public void setNumProduto(String numProduto)
    {
        this.numProduto = numProduto;
    }

    public String getModelo()
    {
        return modelo;
    }

    public String getNumProduto()
    {
        return numProduto;
    }
}

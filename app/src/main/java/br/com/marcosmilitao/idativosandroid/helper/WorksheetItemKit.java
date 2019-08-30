package br.com.marcosmilitao.idativosandroid.helper;

import android.arch.persistence.room.Relation;

import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.marcosmilitao.idativosandroid.DBUtils.Models.CadastroMateriais;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.CadastroMateriaisItens;
import br.com.marcosmilitao.idativosandroid.DBUtils.Models.ModeloMateriais;

/**
 * Created by marcoswerneck on 06/12/17.
 */

public class WorksheetItemKit {
    private int id_original;
    private String modeloMaterial;
    private String numSerie;
    private String patrimonio;
    private int quantidade;
    private Date data_cadastro;

    public WorksheetItemKit(int id_original, String modeloMaterial, String numSerie, String patrimonio, int quantidade, Date data_cadastro){
        this.id_original = id_original;
        this.modeloMaterial = modeloMaterial;
        this.numSerie = numSerie;
        this.patrimonio = patrimonio;
        this.quantidade = quantidade;
        this.data_cadastro = data_cadastro;
    }

    public String getModeloMaterial(){
        return modeloMaterial;
    }

    public String getNumSerie(){
        return numSerie;
    }

    public String getPatrimonio(){
        return patrimonio;
    }

    public int getQuantidade(){
        return quantidade;
    }

    public Date getDataCadastro(){
        return data_cadastro;
    }

    public int getId_original(){
        return id_original;
    }

    @Override
    public String toString(){
        return "N˚ Série: " + getNumSerie() + " | Patrimônio: " + getPatrimonio() + " | Quantidade: " + getQuantidade();
    }
}

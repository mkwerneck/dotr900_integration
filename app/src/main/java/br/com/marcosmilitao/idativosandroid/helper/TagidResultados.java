package br.com.marcosmilitao.idativosandroid.helper;

import java.util.ArrayList;

/**
 * Created by marcoswerneck on 23/07/2018.
 */

public class TagidResultados {

    private String label;
    private String tagid;
    private ArrayList<ListaResultados> resultados;
    public boolean isTaged;
    private int idOriginal;

    public TagidResultados(String label, String tagid, ArrayList<ListaResultados> resultados, boolean isTaged, int idOriginal){

        this.label = label;
        this.tagid = tagid;
        this.resultados = resultados;
        this.isTaged = isTaged;
        this.idOriginal = idOriginal;
    }

    public TagidResultados(){

    }

    public void setisTaged(boolean isTaged){
        this.isTaged = isTaged;
    }

    public void setTagid(String tagid){
        this.tagid = tagid;
    }

    public String getTagid(){
        return tagid;
    }

    public void setLabel(String label){
        this.label = label;
    }

    public String getLabel(){
        return label;
    }

    public void setRestulados(ArrayList<ListaResultados> resultados){
        this.resultados = resultados;
    }

    public ArrayList<ListaResultados> getResultados(){
        return resultados;
    }

    public void AddToResultados(ListaResultados resultado) {
        resultados.add(resultado);
    }

    public void ChangeResultado(int position, boolean value){
        resultados.get(position).setIsChecked(value);
    }

    public void RemoveResultado(ListaResultados resultado){
        resultados.remove(resultado);
    }

    public boolean ExistsResultado(String resultado){
        for (ListaResultados item : resultados){
            if (resultado.equals(item.getResultado())){
                return true;
            }
        }
        return false;
    }

    public boolean AnyResultTrue(){
        for (ListaResultados result : resultados){
            if (result.getIsChecked() == true){
                return true;
            }
        }
        return false;
    }

    public ListaResultados getResultadobyName(String name){
        for (ListaResultados resultado : resultados){
            if (resultado.getResultado().equals(name)){
                return resultado;
            }
        } return null;
    }

    public int getIdOriginal() {
        return idOriginal;
    }

    public void setIdOriginal(int idOriginal) {
        this.idOriginal = idOriginal;
    }
}

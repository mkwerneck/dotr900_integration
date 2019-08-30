package br.com.marcosmilitao.idativosandroid.helper;

/**
 * Created by marcoswerneck on 09/07/2018.
 */

public class Descarte {
    private String title;
    private String subtitle;
    private String comments;
    private int idOriginal;

    public static final Descarte[] descartes = {
            new Descarte("H000000000000000000001266", "FERRAMENTA DE TESTE1", 1),
            new Descarte("H000000000000000000001247", "TOOLBOX CHECKBOX MATRIBOX TESTE", 2),
            new Descarte("H000000000000000000001248", "FERRAMENTA DE EMPURRAR", 3),
            new Descarte("H000000000000000000001429", "FERRAMENTA DE TESTE1", 4),
            new Descarte("H000000000000000000001682", "TOOLBOX CHECKBOX MATRIBOX TESTE", 5),
            new Descarte("H000000000000000000001664", "FERRAMENTA DE EMPURRAR", 6),
            new Descarte("H000000000000000000001691", "FERRAMENTA DE TESTE1", 7)
    };

    public Descarte(String title, String subtitle, int idOriginal){
        this.title = title;
        this.subtitle = subtitle;
        this.idOriginal = idOriginal;
    }

    public Descarte (){

    }

    public int getIdOriginal() {
        return idOriginal;
    }

    public void setIdOriginal(int idOriginal) {
        idOriginal = idOriginal;
    }

    public String getTitle(){
        return title;
    }

    public String getSubtitle(){
        return subtitle;
    }

    public String getComments(){
        return comments;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setSubtitle(String subtitle){
        this.subtitle = subtitle;
    }

    public void setComments(String comments){
        this.comments = comments;
    }
}

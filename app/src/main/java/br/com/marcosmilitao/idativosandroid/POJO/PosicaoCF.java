package br.com.marcosmilitao.idativosandroid.POJO;

/**
 * Created by marcoswerneck on 15/08/19.
 */

public class PosicaoCF {

    private int idOriginal;
    private String codPosicao;
    private String descPosicao;

    public PosicaoCF(int idOriginal, String codPosicao, String descPosicao) {
        this.idOriginal = idOriginal;
        this.codPosicao = codPosicao;
        this.descPosicao = descPosicao;
    }

    public String getCodPosicao() {
        return codPosicao;
    }

    public void setCodPosicao(String codPosicao) {
        this.codPosicao = codPosicao;
    }

    public String getDescPosicao() {
        return descPosicao;
    }

    public void setDescPosicao(String descPosicao) {
        this.descPosicao = descPosicao;
    }

    public int getIdOriginal() {
        return idOriginal;
    }

    public void setIdOriginal(int idOriginal) {
        this.idOriginal = idOriginal;
    }

}

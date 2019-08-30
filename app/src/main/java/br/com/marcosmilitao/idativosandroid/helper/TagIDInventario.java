package br.com.marcosmilitao.idativosandroid.helper;

import java.util.ArrayList;

/**
 * Created by Marcos Paulo on 18/08/2017.
 */

public class TagIDInventario {
    public String tagid;
    public boolean isTaged;

    public TagIDInventario(String tagid, boolean isTaged){
        this.tagid = tagid;
        this.isTaged = isTaged;
    }

    public TagIDInventario(){

    }

    private void setisTaged(boolean isTaged){
        this.isTaged = isTaged;
    }

    public String getTagid()
    {
        return tagid;
    }
}

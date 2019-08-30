package br.com.marcosmilitao.idativosandroid.helper;

import android.content.SharedPreferences;

import net.sourceforge.jtds.jdbc.DateTime;

/**
 * Created by marcoswerneck on 18/12/17.
 */

public class XR400ReadTags {
    private String tagid, datetime, type;

    public XR400ReadTags(){

    }

    public void setTagid(String tagid){
        this.tagid = tagid;
    }

    public void setDatetime(String datetime){
        this.datetime = datetime;
    }

    public void setType(String type){
        this.type = type;
    }

    public String getTagid(){
        return tagid;
    }

    public String getDatetime(){
        return datetime;
    }

    public String getType(){
        return type;
    }

}

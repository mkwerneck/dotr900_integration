package br.com.marcosmilitao.idativosandroid.ViewModel;

/**
 * Created by marcoswerneck on 29/10/2018.
 */

public class TAGIDInventarioViewModel {

    public TAGIDInventarioViewModel(String TAGID, String Description)
    {
        this.TAGID = TAGID;
        this.Description = Description;
    }

    public String TAGID;
    public String Description;

    public String getTAGID()
    {
        return TAGID;
    }

    public void setTAGID(String TAGID)
    {
        this.TAGID = TAGID;
    }

    public String getDescription()
    {
        return Description;
    }

    public void setDescription(String Description)
    {
        this.Description = Description;
    }
}

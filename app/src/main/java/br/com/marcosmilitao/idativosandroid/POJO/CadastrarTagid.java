package br.com.marcosmilitao.idativosandroid.POJO;

public class CadastrarTagid {
    public String Tagid;
    public boolean isCadastrado;
    public boolean isSelected;

    public CadastrarTagid(String Tagid, boolean isCadastrado, boolean isSelected)
    {
        this.Tagid = Tagid;
        this.isCadastrado = isCadastrado;
        this.isSelected = isSelected;
    }

    public CadastrarTagid(){}


    public String getTagid() {
        return Tagid;
    }

    public void setTagid(String tagid) {
        Tagid = tagid;
    }

    public boolean getIsCadastrado() {
        return isCadastrado;
    }

    public void setCadastrado(boolean cadastrado) {
        isCadastrado = cadastrado;
    }

    public boolean getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected){
        this.isSelected = isSelected;
    }
}

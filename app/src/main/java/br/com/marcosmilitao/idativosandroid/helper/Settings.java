package br.com.marcosmilitao.idativosandroid.helper;

/**
 * Created by Marcos Paulo on 05/10/2017.
 */

public class Settings {
    private String title;
    private String subtitle;
    private boolean hasCheckbox;
    private boolean isChecked;
    private boolean isClickable;

    public static final Settings[] settings = {
            new Settings("Versão", "", false, false, false),
            new Settings("Número de Série", "", false, false, false),
            new Settings("FAQ", "Perguntas frequentes", false, false, true)
    };

    public static final Settings[] sobreSettings = {
            new Settings("Modelo Leitor RFID", "Digite o Modelo do Leitor", false, false, true),
            new Settings("Potência do leitor", "", false, false, true),
            new Settings("Bip do leitor", "Alarme sonoro ao realizar leitura", true, true, true),
            new Settings("IP do XR400", "", false, false, true)
    };

    public static final Settings[] maisSettings = {
            new Settings ("Limpar dados armazenados", null, false, false, true)
    };

    public Settings(String title, String subtitle, boolean hasCheckbox, boolean isChecked, boolean isClickable){
        this.title = title;
        this.subtitle = subtitle;
        this.hasCheckbox = hasCheckbox;
        this.isChecked = isChecked;
        this.isClickable = isClickable;
    }

    public Settings(){

    }

    public void setChecked(boolean isChecked){
        this.isChecked = isChecked;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setSubtitle(String subtitle){
        this.subtitle = subtitle;
    }

    public void setHasCheckbox(boolean hasCheckbox){
        this.hasCheckbox = hasCheckbox;
    }

    public void setIsClickable(boolean isChecked){
        this.isClickable = isClickable;
    }

    public boolean getChecked(){
        return isChecked;
    }

    public String getTitle(){
        return title;
    }

    public String getSubtitle(){
        return subtitle;
    }

    public boolean getHasCheckbox(){
        return hasCheckbox;
    }

    public boolean getIsClickable(){
        return isClickable;
    }

}

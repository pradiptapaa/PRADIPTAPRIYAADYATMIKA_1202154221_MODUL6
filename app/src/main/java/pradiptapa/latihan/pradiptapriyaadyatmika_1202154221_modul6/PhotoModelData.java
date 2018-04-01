package pradiptapa.latihan.pradiptapriyaadyatmika_1202154221_modul6;

import java.io.Serializable;

/**
 * Created by monoc on 4/1/2018.
 */

public class PhotoModelData implements Serializable {
    public PhotoModelData(String key, String url, String tittle, String description, String names, String email) {
        this.key = key;
        this.url = url;
        this.tittle = tittle;
        this.description = description;
        this.names = names;
        this.email = email;
    }
    public PhotoModelData() {
    }

    public String key;
    public String url;
    public String tittle;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String description;
    public String names;
    public String email;


}

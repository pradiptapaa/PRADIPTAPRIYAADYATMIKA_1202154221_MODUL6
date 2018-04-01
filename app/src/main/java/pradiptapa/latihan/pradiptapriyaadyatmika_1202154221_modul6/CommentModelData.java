package pradiptapa.latihan.pradiptapriyaadyatmika_1202154221_modul6;

import java.io.Serializable;

/**
 * Created by monoc on 4/1/2018.
 */

public class CommentModelData implements Serializable {
    public String name;
    public String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String comment;

    public CommentModelData(String name, String email, String comment) {
        this.name = name;
        this.email = email;
        this.comment = comment;
    }
    public CommentModelData() {
    }
}

package model;

import java.io.Serializable;

public class Question implements Serializable {

    private String foreignId;
    private String content;

    public Question() {
    }

    public Question(String foreignId, String content) {
        this.foreignId = foreignId;
        this.content = content;
    }

    public String getForeignId() {
        return foreignId;
    }

    public void setForeignId(String foreignId) {
        this.foreignId = foreignId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

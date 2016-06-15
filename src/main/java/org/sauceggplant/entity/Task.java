package org.sauceggplant.entity;

public class Task implements java.io.Serializable {

    private static final long serialVersionUID = 5837177313753624644L;

    private String id;

    private long index;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getNum() {
        return index;
    }

    public void setNum(long num) {
        this.index = num;
    }

}

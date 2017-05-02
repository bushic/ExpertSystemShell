package model;

import java.io.Serializable;

/**
 * Created by Дмитрий on 28.02.2017.
 */
public class Variable implements Serializable {

    private int id;
    private String name;
    private Domain domain;
    private boolean requested;
    private boolean withdrawn;
    private String question;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Domain getDomain() {
        return domain;
    }

    public void setDomain(Domain domain) {
        this.domain = domain;
    }

    public boolean isRequested() {
        return requested;
    }

    public boolean setRequested(boolean requested) {
        if (!withdrawn && !requested) {
            return false;
        } else{
            this.requested = requested;
            return true;
        }
    }

    public boolean isWithdrawn() {
        return withdrawn;
    }

    public boolean setWithdrawn(boolean withdrawn) {
        if (!withdrawn && !requested) {
            return false;
        } else {
            this.withdrawn = withdrawn;
            return true;
        }
    }

    public String toString(){
        return name;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}

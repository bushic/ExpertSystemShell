package model;

import java.io.Serializable;

public class Condition implements Serializable {

    private Variable variable;
    private String value;

    public Variable getVariable() {
        return variable;
    }

    public void setVariable(Variable variable) {
        this.variable = variable;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String toString(){
        return variable + " = " + value;
    }
}

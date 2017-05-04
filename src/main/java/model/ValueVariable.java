package model;

import java.io.Serializable;

public class ValueVariable implements Serializable {

    private Variable variable;
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Variable getVariable() {
        return variable;
    }

    public void setVariable(Variable variable) {
        this.variable = variable;
    }

    @Override
    public String toString() {
        return variable.getName() + " = " + value;
    }
}

package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Дмитрий on 28.02.2017.
 */
public class Domain implements Serializable {

    private int id;
    private String name;
    private ArrayList<String> values;
    private ArrayList<Variable> variables;

    public Domain(){
        values = new ArrayList<>();
        variables = new ArrayList<>();
    }

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

    public ArrayList<String> getValues() {
        return values;
    }

    public void setValues(ArrayList<String> values) {
        this.values = values;
    }

    public void addValue(String value){
        values.add(value);
    }

    public void removeValue(String value){
        values.remove(value);
    }

    public void addVariable(Variable variable){
        variables.add(variable);
    }

    public void removeVariable(Variable variable){
        variables.remove(variable);
    }

    public String toString(){
        return name;
    }
}

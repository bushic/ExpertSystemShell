package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Дмитрий on 28.02.2017.
 */
public class Rule implements Serializable {

    private String name;
    private ArrayList<Condition> conditions;
    private ArrayList<Condition> conclusions;
    private String Reason;

    public Rule(){
        conditions = new ArrayList<>();
        conclusions = new ArrayList<>();
    }

    public String getReason() {
        return Reason;
    }

    public void setReason(String reason) {
        Reason = reason;
    }

    public String toString(){
        String s = name + ": if ";
        for (int i=0; i<conditions.size(); i++){
            s += conditions.get(i);
            s += " and ";
        }
        s = s.substring(0,s.length()-4);
        s += "then ";
        for (int i=0; i<conclusions.size(); i++){
            s += conclusions.get(i);
            s += "; ";
        }

        return s.substring(0,s.length());
    }

    public ArrayList<Condition> getConditions(){
        return conditions;
    }

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addCondition(Condition condition){
        conditions.add(condition);
    }

    public void deleteCondition(Condition condition){
        conditions.remove(condition);
    }

    public void addConclusion(Condition conclusion){
        conclusions.add(conclusion);
    }

    public void deletetConclusion(Condition conclusion){
        conclusions.remove(conclusion);
    }

    public ArrayList<Condition> getConclusions() {
        return conclusions;
    }

    public void setConclusions(ArrayList<Condition> conclusions) {
        this.conclusions = conclusions;
    }
}

package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Дмитрий on 05.03.2017.
 */
public class KnowledgeBase implements Serializable {
    private ArrayList<Domain> domains;
    private ArrayList<Variable> variables;
    private ArrayList<Rule> rules;

    public ArrayList<Domain> getDomains() {
        return domains;
    }

    public void setDomains(ArrayList<Domain> domains) {
        this.domains = domains;
    }

    public ArrayList<Variable> getVariables() {
        return variables;
    }

    public void setVariables(ArrayList<Variable> variables) {
        this.variables = variables;
    }

    public ArrayList<Rule> getRules() {
        return rules;
    }

    public void setRules(ArrayList<Rule> rules) {
        this.rules = rules;
    }

    public void addDomain(Domain domain){
        domains.add(domain);
    }

    public void deleteDomain(Domain domain){
        domains.remove(domain);
    }

    public void clear(){
        if (domains != null)
            domains.clear();
        if (variables != null)
            variables.clear();
        if (rules != null)
            rules.clear();
    }
}

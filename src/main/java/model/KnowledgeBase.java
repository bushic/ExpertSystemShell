package model;

import controller.ChooseGoalViewController;
import controller.QuestionViewController;
import controller.ResultViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.MainApp;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Дмитрий on 05.03.2017.
 */
public class KnowledgeBase implements Serializable {
    private ArrayList<Domain> domains;
    private ArrayList<Variable> variables;
    private ArrayList<Rule> rules;
    private ArrayList<Rule> workedRules;
    private ArrayList<ValueVariable> variablesValue;


    public ArrayList<ValueVariable> getVariablesValue(){
        return variablesValue;
    }

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

    public void addVariablesValue(ValueVariable valueVariable){
        variablesValue.add(valueVariable);
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

    public void consult(Variable variable, ResultViewController resultViewController, MainApp mainApp){
        workedRules = new ArrayList<Rule>();
        variablesValue = new ArrayList<ValueVariable>();

        TreeItem<String> rootItemWork;
        TreeItem<String> rootItemFull;

        rootItemWork = new TreeItem<>("Начало");
        rootItemWork.setExpanded(true);
        //resultViewController.getTreeViewWork().setRoot(rootItemWork);

        rootItemFull = new TreeItem<>("Начало");
        rootItemFull.setExpanded(true);
        //resultViewController.getTreeViewFull().setRoot(rootItemFull);

        goal(variable,rootItemWork,rootItemFull,mainApp);

        ValueVariable lastValueVariable = variablesValue.get(variablesValue.size()-1);
        if (variable.equals(lastValueVariable.getVariable()))
            mainApp.setGoal(lastValueVariable);
        else{
            Variable helpVariable = new Variable();
            helpVariable.setName(variable.getName());
            lastValueVariable.setValue("");
            lastValueVariable.setVariable(helpVariable);
            mainApp.setGoal(lastValueVariable);
        }

        mainApp.rootItemFull = rootItemFull;
        mainApp.rootItemWork = rootItemWork;

        mainApp.showResultView();
    }

    private String goal(Variable variable, TreeItem<String> treeItemWork, TreeItem<String> treeItemFull, MainApp mainApp) {
        int indexRules = 0;
        String goal = "";
        while (goal.equals("") && indexRules<rules.size()){
            boolean concHasVar = false, good = true;
            ArrayList<Condition> conclusions = rules.get(indexRules).getConclusions();
            for (int i = 0; i < conclusions.size(); i++){
                if (conclusions.get(i).getVariable().equals(variable)
                        && !workedRules.contains(rules.get(indexRules)))
                    concHasVar = true;
            }
            if (concHasVar && !workedRules.contains(rules.get(indexRules))){
                TreeItem<String> newRule = new TreeItem<String>(rules.get(indexRules).toString());
                treeItemFull.getChildren().add(newRule);

                ArrayList<Condition> conditions = rules.get(indexRules).getConditions();

                for (int i = 0; i < conditions.size(); i++){
                    Condition condition = conditions.get(i);
                    if (good){
                        TreeItem<String> treeItem = treeItemFull.getChildren().get(treeItemFull.getChildren().size()-1);
                        treeItem.getChildren().add(new TreeItem<>("Выводим " + conditions.get(i).getVariable().getName()));

                        int indexValue = valueExist(condition.getVariable());

                        if (indexValue >= 0){
                            TreeItem<String> treeItem2 = treeItem.getChildren().get(treeItem.getChildren().size()-1);
                            ValueVariable valueVariable = variablesValue.get(indexValue);
                            if (!valueVariable.getValue().equals(condition.getValue()))
                                good = false;
                            treeItem2.getChildren().add(new TreeItem<String>("Уже означено: " + valueVariable.getVariable().getName()
                                    + " = " + valueVariable.getValue()));
                        } else{
                            Variable sendVariable = condition.getVariable();

                            if (sendVariable.isRequested() && ! sendVariable.isWithdrawn()){
                                TreeItem<String> treeItem2 = treeItem.getChildren().get(treeItem.getChildren().size()-1);
                                int count = variablesValue.size();

                                askVariable(sendVariable,mainApp);

                                indexValue = valueExist(condition.getVariable());
                                ValueVariable valueVariable = variablesValue.get(indexValue);
                                if (!valueVariable.getValue().equals(condition.getValue()))
                                    good = false;
                                treeItem2.getChildren().add(new TreeItem<String>("Пользователь ввел: " + valueVariable.getVariable().getName()
                                        + " = " + valueVariable.getValue()));
                            }else if (sendVariable.isRequested() && sendVariable.isWithdrawn()){
                                TreeItem<String> treeItem2 = treeItem.getChildren().get(treeItem.getChildren().size()-1);

                                if (goal(sendVariable,treeItemWork,treeItem2,mainApp).equals("")){
                                    askVariable(sendVariable,mainApp);

                                    indexValue = valueExist(condition.getVariable());
                                    ValueVariable valueVariable = variablesValue.get(indexValue);
                                    if (!valueVariable.getValue().equals(condition.getValue()))
                                        good = false;
                                    treeItem2.getChildren().add(new TreeItem<String>("Пользователь ввел: " + valueVariable.getVariable().getName()
                                            + " = " + valueVariable.getValue()));
                                }else{
                                    indexValue = valueExist(condition.getVariable());
                                    ValueVariable valueVariable = variablesValue.get(indexValue);
                                    if (!valueVariable.getValue().equals(condition.getValue()))
                                        good = false;
                                }
                            }else{
                                TreeItem<String> treeItem2 = treeItem.getChildren().get(treeItem.getChildren().size()-1);

                                if (goal(sendVariable,treeItemWork,treeItem2,mainApp).equals("")){
                                    good = false;
                                }else{
                                    indexValue = valueExist(condition.getVariable());
                                    ValueVariable valueVariable = variablesValue.get(indexValue);
                                    if (!valueVariable.getValue().equals(condition.getValue()))
                                        good = false;
                                }
                            }
                        }
                    }
                }
                if (good){
                    TreeItem<String> treeItem = treeItemFull.getChildren().get(treeItemFull.getChildren().size()-1);
                    treeItem.getChildren().add(new TreeItem<String>("Правило сработало"));

                    TreeItem<String> newRule2 = new TreeItem<String>(rules.get(indexRules).toString());
                    treeItemWork.getChildren().add(newRule);

                    String s1 = "";

                    for (int j = 0; j < conclusions.size(); j++){
                        ValueVariable valueVariable = new ValueVariable();
                        valueVariable.setVariable(conclusions.get(j).getVariable());
                        valueVariable.setValue(conclusions.get(j).getValue());
                        int indexValue = valueExist(conclusions.get(j).getVariable());
                        //treeItemWork.getChildren().add(new TreeItem<String>(rules.get(indexRules).toString()));
                        if (indexValue == -1) {
                            variablesValue.add(valueVariable);
                            treeItemFull.getChildren().add(new TreeItem<String>("Означили: " + valueVariable.getVariable().getName()+
                                " = " + valueVariable.getValue()));
                            treeItemWork.getChildren().add(new TreeItem<String>("Означили: " + valueVariable.getVariable().getName()+
                                    " = " + valueVariable.getValue()));
                            s1 = valueVariable.getValue();
                        }
                        //treeItemFull.getChildren().add(new TreeItem<String>(rules.get(indexRules).toString()));

                        indexValue = valueExist(variable);
                        goal = variablesValue.get(indexValue).getValue();
                        workedRules.add(rules.get(indexRules));
                    }
                }
            }
            indexRules++;
        }
        return goal;
    }

    private void askVariable(Variable sendVariable, MainApp mainApp) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/questionView.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.getIcons().add(new Image(String.valueOf(getClass().getResource("/logo.png"))));
            dialogStage.setTitle("Вопрос");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainApp.getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            QuestionViewController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.initForm(sendVariable);
            controller.setMainApp(mainApp);

            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int valueExist(Variable variable) {
        for (int i = 0; i < variablesValue.size(); i++){
            if (variablesValue.get(i).getVariable().equals(variable))
                return i;
        }
        return -1;
    }
}

package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import main.MainApp;
import model.Condition;
import model.Variable;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ConditionAddViewConroller implements Initializable {

    @FXML
    private Button buttonAdd;
    @FXML
    private Button buttonCancel;
    @FXML
    private Button variableEdit;
    @FXML
    private Button valueEdit;
    @FXML
    private ComboBox<Variable> variableComboBox;
    @FXML
    private ComboBox<String> valueComboBox;

    private Stage dialogStage;
    private RuleViewController ruleViewController;
    private MainApp mainApp;
    private String par;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buttonAdd.setOnAction(event -> {
            Condition condition = new Condition();
            condition.setVariable(variableComboBox.getSelectionModel().getSelectedItem());
            condition.setValue(valueComboBox.getSelectionModel().getSelectedItem());
            if (par.equals("condition"))
                ruleViewController.addCondition(condition);
            else ruleViewController.addConclusion(condition);
            dialogStage.close();
        });
        buttonCancel.setOnAction(event->dialogStage.close());
        variableComboBox.valueProperty().addListener(new ChangeListener<Variable>() {
            @Override
            public void changed(ObservableValue<? extends Variable> observable, Variable oldValue, Variable newValue) {
                if (newValue == null)
                    return;
                valueComboBox.setItems(FXCollections.observableArrayList(newValue.getDomain().getValues()));
                valueComboBox.getSelectionModel().selectFirst();
            }
        });
        variableEdit.setOnAction(event -> {
            mainApp.showVariableView(dialogStage);
            variableComboBox.setItems(FXCollections.observableArrayList(mainApp.variables));
            variableComboBox.getSelectionModel().selectLast();
        });
        valueEdit.setOnAction(event -> {
            mainApp.showDomainView(dialogStage);
            variableComboBox.setItems(FXCollections.observableArrayList(mainApp.variables));
            variableComboBox.getSelectionModel().selectLast();
        });
    }

    public RuleViewController getRuleViewController() {
        return ruleViewController;
    }

    public void setRuleViewController(RuleViewController ruleViewController) {
        this.ruleViewController = ruleViewController;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setPar(ArrayList<Variable> parVariables, String par){
        if (parVariables != null && parVariables.size() != 0){
            if (par.equals("condition")){
                variableComboBox.setItems(FXCollections.observableArrayList(parVariables));
            }else{
                for (int i = 0; i < parVariables.size(); i++){
                    if (!parVariables.get(i).isRequested()){
                        variableComboBox.getItems().add(parVariables.get(i));
                    }
                }
            }

            variableComboBox.getSelectionModel().selectFirst();
            valueComboBox.setItems(FXCollections.observableArrayList(parVariables.get(0).getDomain().getValues()));
            valueComboBox.getSelectionModel().selectFirst();
        }
        this.par = par;
    }

    public MainApp getMainApp() {
        return mainApp;
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}

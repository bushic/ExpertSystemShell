package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import main.MainApp;
import model.Domain;
import model.Variable;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by Дмитрий on 27.03.2017.
 */
public class VariableAddViewController implements Initializable {

    @FXML
    private Button buttonDomains;
    @FXML
    private Button buttonAdd;
    @FXML
    private Button buttonCancel;
    @FXML
    private CheckBox checkWithdrawn;
    @FXML
    private CheckBox checkRequested;
    @FXML
    private TextField textName;
    @FXML
    private ComboBox<Domain> comboBox;

    private VariableViewController variableViewController;
    private Stage dialogStage;
    private Variable parVariable;
    private MainApp mainApp;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        buttonDomains.setOnAction(event -> {
            mainApp.showDomainView(dialogStage);
            comboBox.setItems(FXCollections.observableList(mainApp.domains));
            comboBox.getSelectionModel().selectFirst();
        });

        buttonAdd.setOnAction(event -> {
            doAdd();
        });
        buttonCancel.setOnAction(event->dialogStage.close());
        textName.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER){
                doAdd();
            }
        });
    }

    private void doAdd() {

        if (!checkRequested.isSelected() && !checkWithdrawn.isSelected()) {
            new Alert(Alert.AlertType.INFORMATION, "Укажите тип переменной").showAndWait();
            return;
        }

        Variable variable = new Variable();
        variable.setDomain(comboBox.getSelectionModel().getSelectedItem());
        variable.setName(textName.getText().trim());
        variable.setRequested(checkRequested.isSelected());
        variable.setWithdrawn(checkWithdrawn.isSelected());

        if (parVariable == null)
            try {
                variableViewController.addVariable(variable);
            } catch (Exception e) {
                new Alert(Alert.AlertType.INFORMATION,e.getMessage()).showAndWait();
                textName.requestFocus();
                return;
            }
        else variableViewController.editVariable(variable);
        dialogStage.close();
    }


    public VariableViewController getVariableViewController() {
        return variableViewController;
    }

    public void setVariableViewController(VariableViewController variableViewController) {
        this.variableViewController = variableViewController;
        mainApp = variableViewController.getMainApp();
    }

    public void setPar(Variable parVariable, ArrayList<Domain> parDomains){
        this.parVariable = parVariable;

        comboBox.setItems(FXCollections.observableList(parDomains));
        comboBox.getSelectionModel().selectFirst();
        if (parVariable != null){
            buttonAdd.setText("Изменить");
            textName.setText(parVariable.getName());
            comboBox.getSelectionModel().select(parVariable.getDomain());
            checkRequested.setSelected(parVariable.isRequested());
            checkWithdrawn.setSelected(parVariable.isWithdrawn());
        }

    }

    public Stage getDialogStage() {
        return dialogStage;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
}

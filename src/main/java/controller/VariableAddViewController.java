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
    @FXML
    private TextArea textQuestion;

    private VariableViewController variableViewController;
    private Stage dialogStage;
    private Variable parVariable;
    private MainApp mainApp;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        buttonDomains.setOnAction(event -> {
            mainApp.showDomainView(dialogStage);
            comboBox.setItems(FXCollections.observableList(mainApp.domains));
            comboBox.getSelectionModel().selectLast();
        });

        buttonAdd.setOnAction(event -> {
            doAdd();
        });
        buttonAdd.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER){
                doAdd();
            }
        });
        buttonCancel.setOnAction(event->dialogStage.close());
        buttonCancel.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER){
                dialogStage.close();
            }
        });
        textName.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER){
                doAdd();
            }
        });
        checkRequested.setOnAction(event -> {
            if (checkRequested.isSelected()){
                textQuestion.setDisable(false);
            } else textQuestion.setDisable(true);
        });
    }

    private void doAdd() {

        Variable variable = new Variable();
        variable.setDomain(comboBox.getSelectionModel().getSelectedItem());
        variable.setName(textName.getText().trim());
        variable.setRequested(checkRequested.isSelected());
        variable.setWithdrawn(checkWithdrawn.isSelected());
        variable.setQuestion(textQuestion.getText().trim());

        if (variable.getQuestion().equals("")){
            variable.setQuestion(variable.getName() + "?");
        }

        if (variable.getName().equals("")){
            new Alert(Alert.AlertType.INFORMATION, "Введите непустое имя",ButtonType.OK).showAndWait();
            textName.requestFocus();
            return;
        }
        if (variable.getDomain() == null){
            new Alert(Alert.AlertType.INFORMATION, "Укажите домен",ButtonType.OK).showAndWait();
            comboBox.requestFocus();
            return;
        }
        if (!variable.isRequested() && !variable.isWithdrawn()) {
            new Alert(Alert.AlertType.INFORMATION, "Укажите тип переменной",ButtonType.OK).showAndWait();
            checkWithdrawn.requestFocus();
            return;
        }

        if (parVariable == null)
            try {
                variableViewController.addVariable(variable);
            } catch (Exception e) {
                new Alert(Alert.AlertType.INFORMATION,e.getMessage()).showAndWait();
                textName.requestFocus();
                textName.selectAll();
                return;
            }
        else try {
            variableViewController.editVariable(variable);
        } catch (Exception e) {
            new Alert(Alert.AlertType.INFORMATION,e.getMessage()).showAndWait();
            textName.requestFocus();
            textName.selectAll();
            return;
        }
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

package controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.MainApp;
import model.Variable;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by Дмитрий on 01.03.2017.
 */
public class VariableViewController implements Initializable {

    @FXML
    private ListView<Variable> variableListView;
    @FXML
    private Label labelName;
    @FXML
    private Label labelDomain;
    @FXML
    private Label labelType;
    @FXML
    private Label labelQuestion;
    @FXML
    private Button variableAdd;
    @FXML
    private Button variableEdit;
    @FXML
    private Button variableDelete;

    private MainApp mainApp;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        variableListView.setOnMouseClicked(event -> {
            Variable selectedVariable = variableListView.getSelectionModel().getSelectedItem();
            if (selectedVariable != null) {
                labelName.setText(selectedVariable.getName());
                labelDomain.setText(selectedVariable.getDomain().toString());
                if (selectedVariable.isRequested() && selectedVariable.isWithdrawn()){
                    labelType.setText("Выводимо-запрашиваемая");
                } else if (selectedVariable.isRequested()){
                    labelType.setText("Запрашиваемая");
                }else if (selectedVariable.isWithdrawn()){
                    labelType.setText("Выводимая");
                }
                labelQuestion.setText(selectedVariable.getQuestion());
            }
        });

        variableAdd.setOnAction(event->{
            openVariableAddView(null);
        });

        variableAdd.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER){
                openVariableAddView(null);
            }
        });

        variableEdit.setOnAction(event -> {
            Variable selectedVariable = variableListView.getSelectionModel().getSelectedItem();
            openVariableAddView(selectedVariable);
        });

        variableEdit.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER){
                Variable selectedVariable = variableListView.getSelectionModel().getSelectedItem();
                openVariableAddView(selectedVariable);
            }
        });

        variableDelete.setOnAction(event->{
            Variable selectedVariable = variableListView.getSelectionModel().getSelectedItem();
            if (selectedVariable != null) {
                variableListView.getItems().remove(selectedVariable);
                mainApp.deleteVariable(selectedVariable);
                Variable selectedItem = variableListView.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    labelName.setText(selectedItem.getName());
                    labelDomain.setText(selectedItem.getDomain().toString());
                    if (selectedItem.isRequested() && selectedItem.isWithdrawn()){
                        labelType.setText("Выводимо-запрашиваемая");
                    } else if (selectedItem.isRequested()){
                        labelType.setText("Запрашиваемая");
                    }else if (selectedItem.isWithdrawn()){
                        labelType.setText("Выводимая");
                    }
                }
            }
        });

        variableDelete.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER){
                Variable selectedVariable = variableListView.getSelectionModel().getSelectedItem();
                if (selectedVariable != null) {
                    variableListView.getItems().remove(selectedVariable);
                    mainApp.deleteVariable(selectedVariable);
                    Variable selectedItem = variableListView.getSelectionModel().getSelectedItem();
                    if (selectedItem != null) {
                        labelName.setText(selectedItem.getName());
                        labelDomain.setText(selectedItem.getDomain().toString());
                        if (selectedItem.isRequested() && selectedItem.isWithdrawn()){
                            labelType.setText("Выводимо-запрашиваемая");
                        } else if (selectedItem.isRequested()){
                            labelType.setText("Запрашиваемая");
                        }else if (selectedItem.isWithdrawn()){
                            labelType.setText("Выводимая");
                        }
                    }
                }
            }
        });

        if (mainApp != null)
            setItems(mainApp.variables);
        variableAdd.requestFocus();
    }

    private void openVariableAddView(Variable variable) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/variableAddView.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.getIcons().add(new Image(String.valueOf(getClass().getResource("/logo.png"))));
            dialogStage.setTitle("Редактирование переменной");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainApp.getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            VariableAddViewController controller = loader.getController();
            controller.setVariableViewController(this);
            controller.setDialogStage(dialogStage);
            controller.setPar(variable,mainApp.domains);

            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clear(){
        variableListView.getItems().clear();
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public MainApp getMainApp() {
        return mainApp;
    }

    public void setItems(ArrayList<Variable> variables) {
        variableListView.setItems(FXCollections.observableList(variables));
        mainApp.setBaseSaved(false);
    }

    public void addVariable(Variable variable) throws Exception {
        if (isVariableExist(variable,mainApp.variables)){
            throw new Exception("Переменная с таким именем уже существует");
        }
        variableListView.getItems().add(variable);
        variableListView.getSelectionModel().select(variable);
        Variable selectedVariable = variableListView.getSelectionModel().getSelectedItem();
        if (selectedVariable != null) {
            labelName.setText(selectedVariable.getName());
            labelDomain.setText(selectedVariable.getDomain().toString());
            if (selectedVariable.isRequested() && selectedVariable.isWithdrawn()){
                labelType.setText("Выводимо-запрашиваемая");
            } else if (selectedVariable.isRequested()){
                labelType.setText("Запрашиваемая");
            }else if (selectedVariable.isWithdrawn()){
                labelType.setText("Выводимая");
            }
            labelQuestion.setText(selectedVariable.getQuestion());
        }
        mainApp.setBaseSaved(false);
    }

    private boolean isVariableExist(Variable variable, ArrayList<Variable> variables) {
        for (int i = 0; i < variables.size(); i++){
            if (variables.get(i).getName().toLowerCase().equals(variable.getName().toLowerCase())){
                return true;
            }
        }
        return false;
    }

    public void editVariable(Variable variable) throws Exception {
        int selectedVariable = variableListView.getSelectionModel().getSelectedIndex();
        if (isVariableExist(variable,mainApp.variables) &&
                !mainApp.variables.get(selectedVariable).getName().equals(variable.getName())){
            throw new Exception("Переменная с таким именем уже существует");
        }

        if (variable != null) {
            mainApp.editVariable(selectedVariable, variable);
            variableListView.getItems().set(selectedVariable,variable);
            Variable selectedItem = variableListView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                labelName.setText(selectedItem.getName());
                labelDomain.setText(selectedItem.getDomain().toString());
                if (selectedItem.isRequested() && selectedItem.isWithdrawn()){
                    labelType.setText("Выводимо-запрашиваемая");
                } else if (selectedItem.isRequested()){
                    labelType.setText("Запрашиваемая");
                }else if (selectedItem.isWithdrawn()){
                    labelType.setText("Выводимая");
                }
                labelQuestion.setText(selectedItem.getQuestion());
            }
            mainApp.setBaseSaved(false);
        }
    }
}

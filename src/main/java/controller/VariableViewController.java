package controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
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
    private Button variableAdd;
    @FXML
    private Button variableEdit;
    @FXML
    private Button variableDelete;

    private MainApp mainApp;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        variableAdd.setOnAction(event->{
            openVariableAddView(null);
        });

        variableEdit.setOnAction(event -> {
            Variable selectedVariable = variableListView.getSelectionModel().getSelectedItem();
            openVariableAddView(selectedVariable);
        });

        variableDelete.setOnAction(event->{
            Variable selectedVariable = variableListView.getSelectionModel().getSelectedItem();
            if (selectedVariable != null) {
                variableListView.getItems().remove(selectedVariable);
                mainApp.deleteVariable(selectedVariable);
            }
        });

        if (mainApp != null)
            setItems(mainApp.variables);
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

    public void editVariable(Variable variable) {
        int selectedVariable = variableListView.getSelectionModel().getSelectedIndex();
        if (variable != null) {
            mainApp.editVariable(selectedVariable, variable);
            variableListView.getItems().set(selectedVariable,variable);
            mainApp.setBaseSaved(false);
        }
    }
}

package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import main.MainApp;
import model.Variable;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ChooseGoalViewController implements Initializable {

    @FXML
    private ComboBox<Variable> comboBox;
    @FXML
    private Button buttonChoose;
    @FXML
    private Button buttonCancel;

    private MainApp mainApp;
    private Stage dialogStage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buttonChoose.setOnAction(event -> {
            mainApp.getKnowledgeBase().consult(comboBox.getSelectionModel().getSelectedItem(),
                    new ResultViewController(),mainApp);
            dialogStage.close();
        });
        buttonCancel.setOnAction(event -> dialogStage.close());
    }

    public void setItems(ArrayList<Variable> variables){
        for (int i=0; i<variables.size(); i++){
            if (variables.get(i).isWithdrawn()){
                comboBox.getItems().add(variables.get(i));
            }
        }
        comboBox.getSelectionModel().selectFirst();
    }

    public MainApp getMainApp() {
        return mainApp;
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
}

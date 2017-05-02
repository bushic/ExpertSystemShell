package controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import main.MainApp;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class QuestionViewController implements Initializable {

    @FXML
    private Label labelQuestion;
    @FXML
    private ComboBox<String> comboBox;
    @FXML
    private TextArea textArea;
    @FXML
    private Button buttonNext;
    @FXML
    private Button buttonCancel;

    private Stage dialogStage;
    private MainApp mainApp;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buttonCancel.setOnAction(event -> dialogStage.close());
    }

    public void initForm(String question, ArrayList<String> domainValues){
        labelQuestion.setText(question);
        comboBox.setItems(FXCollections.observableArrayList(domainValues));
    }

    public Stage getDialogStage() {
        return dialogStage;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public MainApp getMainApp() {
        return mainApp;
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}

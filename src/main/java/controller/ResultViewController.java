package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;
import main.MainApp;

import java.net.URL;
import java.util.ResourceBundle;

public class ResultViewController implements Initializable{

    @FXML
    private Label labelResult;
    @FXML
    private Label labelReason;
    @FXML
    private ListView<String> listView;
    @FXML
    private TreeView treeViewWork;
    @FXML
    private TreeView treeViewFull;

    private Stage dialogStage;
    private MainApp mainApp;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

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

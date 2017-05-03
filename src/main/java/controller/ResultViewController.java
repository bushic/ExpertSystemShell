package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.MainApp;
import model.ValueVariable;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ResultViewController implements Initializable{

    @FXML
    private Label labelResult;
    @FXML
    private ListView<String> listView;
    @FXML
    private TreeView treeViewWork;
    @FXML
    private TreeView treeViewFull;
    @FXML
    private Button buttonExpand;
    @FXML
    private Button buttonTurn;
    @FXML
    private TabPane tabPane;

    private Stage dialogStage;
    private MainApp mainApp;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buttonExpand.setOnAction(event -> {
            if (tabPane.getSelectionModel().getSelectedIndex()==0){
                ObservableList<TreeItem> observableList = treeViewWork.getRoot().getChildren();
                for (int i = 0; i < observableList.size(); i++){
                    ObservableList<TreeItem> observableList2 = observableList.get(i).getChildren();
                    for (int j = 0; j < observableList2.size(); j++){
                        observableList2.get(j).setExpanded(true);
                    }
                    observableList.get(i).setExpanded(true);
                }
            }else{
                ObservableList<TreeItem> observableList = treeViewFull.getRoot().getChildren();
                for (int i = 0; i < observableList.size(); i++){
                    ObservableList<TreeItem> observableList2 = observableList.get(i).getChildren();
                    for (int j = 0; j < observableList2.size(); j++){
                        observableList2.get(j).setExpanded(true);
                    }
                    observableList.get(i).setExpanded(true);
                }
            }
        });
        buttonTurn.setOnAction(event -> {
            if (tabPane.getSelectionModel().getSelectedIndex()==0){
                ObservableList<TreeItem> observableList = treeViewWork.getRoot().getChildren();
                for (int i = 0; i < observableList.size(); i++){
                    ObservableList<TreeItem> observableList2 = observableList.get(i).getChildren();
                    for (int j = 0; j < observableList2.size(); j++){
                        observableList2.get(j).setExpanded(false);
                    }
                    observableList.get(i).setExpanded(false);
                }
            }else{
                ObservableList<TreeItem> observableList = treeViewFull.getRoot().getChildren();
                for (int i = 0; i < observableList.size(); i++){
                    ObservableList<TreeItem> observableList2 = observableList.get(i).getChildren();
                    for (int j = 0; j < observableList2.size(); j++){
                        observableList2.get(j).setExpanded(false);
                    }
                    observableList.get(i).setExpanded(false);
                }
            }
        });
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

    public void setTreeViewWork(TreeItem<String> treeViewWork) {
        this.treeViewWork.setRoot(treeViewWork);
    }

    public void setTreeViewFull(TreeItem<String> treeViewFull) {
        this.treeViewFull.setRoot(treeViewFull);
    }

    public void setVariable(ValueVariable variable) {
        labelResult.setText(variable.getVariable().getName() + " = " + variable.getValue());
        ArrayList<ValueVariable> valueVariables = mainApp.getKnowledgeBase().getVariablesValue();
        for( int i = 0; i < valueVariables.size(); i++){
            listView.getItems().add(valueVariables.get(i).toString());
        }
    }
}

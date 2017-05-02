package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import main.MainApp;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Дмитрий on 28.02.2017.
 */
public class RootLayoutController implements Initializable {

    @FXML
    private MenuItem menuFileExit;
    @FXML
    private MenuItem menuKPZDomain;
    @FXML
    private MenuItem menuKPZVariable;
    @FXML
    private MenuItem menuKPZRule;
    @FXML
    private MenuItem menuMLV;
    @FXML
    private MenuItem menuKO;
    @FXML
    private MenuItem menuFileSave;
    @FXML
    private MenuItem menuFileSaveAs;
    @FXML
    private MenuItem menuFileOpen;
    @FXML
    private MenuItem menuFileNew;

    private BorderPane rootLayout;
    private MainApp mainApp;

    public void initialize(URL location, ResourceBundle resources) {
        menuFileExit.setOnAction(event->{
            mainApp.saveQuestion();
            System.exit(0);
        });
        menuKPZDomain.setOnAction(event->{
            mainApp.showDomainView();
        });
        menuKPZVariable.setOnAction(event -> {
            mainApp.showVariableView();
        });
        menuKPZRule.setOnAction(event -> {
            mainApp.showRuleView();
        });
        menuFileSave.setOnAction(event -> {
            mainApp.saveKnowledgeBase();
        });
        menuFileSaveAs.setOnAction(event -> {
            mainApp.saveKnowledgeBaseAs();
        });
        menuFileOpen.setOnAction(event -> {
            mainApp.saveQuestion();
            mainApp.openKnowledgeBase();
        });
        menuFileNew.setOnAction(event -> {
            mainApp.saveQuestion();
            mainApp.newKnowledgeBase();
        });
        menuMLV.setOnAction(event -> {
            mainApp.showChooseGoalView();
        });
    }

    public BorderPane getRootLayout() {
        return rootLayout;
    }

    public void setRootLayout(BorderPane rootLayout) {
        this.rootLayout = rootLayout;
    }

    public MainApp getMainApp() {
        return mainApp;
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}

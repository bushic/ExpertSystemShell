package main;

import controller.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Domain;
import model.KnowledgeBase;
import model.Rule;
import model.Variable;

import java.io.*;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Created by Дмитрий on 28.02.2017.
 */
public class MainApp extends Application{

    private Stage primaryStage;
    private BorderPane rootLayout;
    private String pathToSavedBase = "";
    private RootLayoutController rootcontroller;
    private DomainViewController domainViewController;
    private VariableViewController variableViewController;
    private RuleViewController ruleViewController;

    public ArrayList<Variable> variables = new ArrayList<Variable>();
    public ArrayList<Domain> domains = new ArrayList<Domain>();
    public ArrayList<Rule> rules = new ArrayList<Rule>();
    private KnowledgeBase knowledgeBase = new KnowledgeBase();
    private FXMLLoader loaderVariableView;
    private FXMLLoader loaderDomainView;
    private FXMLLoader loaderRootLayout;
    private FXMLLoader loaderRuleView;
    private AnchorPane domainView;
    private AnchorPane variableView;
    private AnchorPane ruleView;
    private boolean isBaseSaved;

    public static void main(String[] args) {
        launch(args);
    }
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Оболочка");
        this.primaryStage.getIcons().add(new Image(String.valueOf(getClass().getResource("/logo.png"))));

        primaryStage.setOnCloseRequest(event -> saveQuestion());

        initRootLayout();

        showDomainView();
        isBaseSaved = true;
    }

    private void initRootLayout() {
        try {
            if (loaderRootLayout == null) {
                loaderRootLayout = new FXMLLoader(getClass().getResource("/view/rootLayout.fxml"));
                rootLayout = (BorderPane) loaderRootLayout.load();
            }

            rootcontroller = loaderRootLayout.getController();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            rootcontroller.setRootLayout(rootLayout);
            rootcontroller.setMainApp(this);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showDomainView() {
        try{
            if (loaderDomainView == null) {
                loaderDomainView = new FXMLLoader(getClass().getResource("/view/domainView.fxml"));
                domainView = (AnchorPane) loaderDomainView.load();
                domainViewController = loaderDomainView.getController();
                domainViewController.setMainApp(this);
                domainViewController.setItems(domains);
            }

            rootLayout.setCenter(domainView);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void showDomainView(Stage dialogStage) {
        try{
            if (loaderDomainView == null) {
                loaderDomainView = new FXMLLoader(getClass().getResource("/view/domainView.fxml"));
                domainView = (AnchorPane) loaderDomainView.load();
                domainViewController = loaderDomainView.getController();
                domainViewController.setMainApp(this);
                domainViewController.setItems(domains);
            }

            Stage newStage = new Stage();
            newStage.getIcons().add(new Image(String.valueOf(getClass().getResource("/logo.png"))));
            newStage.setTitle("Домены");
            newStage.initModality(Modality.WINDOW_MODAL);
            newStage.initOwner(dialogStage);
            Scene scene = new Scene(domainView);
            newStage.setScene(scene);
            newStage.showAndWait();

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void showVariableView(){
        try{
            if (loaderVariableView == null) {
                loaderVariableView = new FXMLLoader(getClass().getResource("/view/variableView.fxml"));
                variableView = (AnchorPane) loaderVariableView.load();
                variableViewController = loaderVariableView.getController();
                variableViewController.setMainApp(this);
                variableViewController.setItems(variables);
            }

            rootLayout.setCenter(variableView);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void showVariableView(Stage dialogStage) {
        try{
            if (loaderVariableView == null) {
                loaderVariableView = new FXMLLoader(getClass().getResource("/view/variableView.fxml"));
                variableView = (AnchorPane) loaderVariableView.load();
                variableViewController = loaderVariableView.getController();
                variableViewController.setMainApp(this);
                variableViewController.setItems(variables);
            }

            Stage newStage = new Stage();
            newStage.getIcons().add(new Image(String.valueOf(getClass().getResource("/logo.png"))));
            newStage.setTitle("Переменные");
            newStage.initModality(Modality.WINDOW_MODAL);
            newStage.initOwner(dialogStage);
            Scene scene = new Scene(variableView);
            newStage.setScene(scene);
            newStage.showAndWait();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void showRuleView(){
        try{
            if (loaderRuleView == null) {
                loaderRuleView = new FXMLLoader(getClass().getResource("/view/ruleView.fxml"));
                ruleView = (AnchorPane) loaderRuleView.load();
                ruleViewController = loaderRuleView.getController();
                ruleViewController.setMainApp(this);
                ruleViewController.setItems(rules);
            }

            rootLayout.setCenter(ruleView);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void showChooseGoalView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/chooseGoalView.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.getIcons().add(new Image(String.valueOf(getClass().getResource("/logo.png"))));
            dialogStage.setTitle("Выберите цель консультации");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            ChooseGoalViewController controller = loader.getController();
            controller.setMainApp(this);
            controller.setDialogStage(dialogStage);
            controller.setItems(variables);

            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Domain> getDomains() {
        return domains;
    }

    public void setDomains(ArrayList<Domain> domains) {
        this.domains = domains;
    }

    public void setVariables(ArrayList<Variable> variables) {
        this.variables = variables;
    }

    public void addDomain(Domain domain){
        domains.add(domain);
    }

    public void editDomain(int index, String name){
        domains.get(index).setName(name);
    }

    public void editDomainValue(int indexDomain, int indexValue, String value){
        domains.get(indexDomain).getValues().set(indexValue,value);
    }

    public void deleteDomain(Domain domain){
        domains.remove(domain);
    }

    public void deleteDomainValue(int domain, String value){
        domains.get(domain).getValues().remove(value);
    }

    public KnowledgeBase getKnowledgeBase() {
        return knowledgeBase;
    }

    public void setKnowledgeBase(KnowledgeBase knowledgeBase) {
        this.knowledgeBase = knowledgeBase;
    }

    public String showFileDialogForOpen(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Открыть базу знаний");
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("Knowledge bases files (*.base)", "*.base");//Расширение
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(primaryStage);

        if (file != null){
            return file.getPath();
        }
        else return "";
    }

    public void openKnowledgeBase(){
        String filename = showFileDialogForOpen();

        if (filename.equals("")){
            return;
        }

        pathToSavedBase = filename;

        try {
            FileInputStream fis = new FileInputStream(filename);
            ObjectInputStream oin = new ObjectInputStream(fis);
            knowledgeBase = (KnowledgeBase) oin.readObject();

            setDomains(knowledgeBase.getDomains());
            setVariables(knowledgeBase.getVariables());
            setRules(knowledgeBase.getRules());

            if (domainViewController != null){
                domainViewController.setItems(knowledgeBase.getDomains());
            }

            if (variableViewController != null){
                variableViewController.setItems(knowledgeBase.getVariables());
            }

            if (ruleViewController != null){
                ruleViewController.setItems(knowledgeBase.getRules());
            }
            primaryStage.setTitle("Оболочка (" + filename + ")");

            isBaseSaved = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setRules(ArrayList<Rule> rules) {
        this.rules = rules;
    }

    public void saveKnowledgeBase(){
        String filename = pathToSavedBase;
        if (filename.equals("")){
            saveKnowledgeBaseAs();
            isBaseSaved = true;
            return;
        }
        saveToFile(filename);
        primaryStage.setTitle("Оболочка (" + filename + ")");
        isBaseSaved = true;
    }

    public String showFileDialogForSave(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Сохранить базу знаний");

        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("Knowledge bases files (*.base)", "*.base");//Расширение
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showSaveDialog(primaryStage);

        if (file != null){
            return file.getPath();
        }
        else return "";
    }

    public void saveKnowledgeBaseAs(){
        String filename = showFileDialogForSave();
        saveToFile(filename);
        primaryStage.setTitle("Оболочка (" + filename + ")");
    }

    public void saveToFile(String filename){
        if (filename.equals("")){
            isBaseSaved = false;
            return;
        }

        knowledgeBase.setDomains(domains);
        knowledgeBase.setVariables(variables);
        knowledgeBase.setRules(rules);

        pathToSavedBase = filename;

        try {
            FileOutputStream fos = new FileOutputStream(filename);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(knowledgeBase);
            oos.flush();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void newKnowledgeBase(){
        if (domainViewController != null){
            domainViewController.clear();
        }

        if (variableViewController != null){
            variableViewController.clear();
        }

        if (ruleViewController != null){
            ruleViewController.clear();
        }
        knowledgeBase.clear();
        variables = new ArrayList<Variable>();
        domains = new ArrayList<Domain>();
        rules = new ArrayList<Rule>();

        pathToSavedBase = "";
        isBaseSaved = true;
    }

    public Stage getPrimaryStage(){
        return primaryStage;
    }

    public void editVariable(int selectedVariable, Variable variable) {
        Variable changeVariable = variables.get(selectedVariable);
        changeVariable.setName(variable.getName());
        changeVariable.setDomain(variable.getDomain());
        changeVariable.setRequested(variable.isRequested());
        changeVariable.setWithdrawn(variable.isWithdrawn());
    }

    public void deleteVariable(Variable selectedVariable) {
        variables.remove(selectedVariable);
    }

    public void deleteRule(Rule selectedRule) {
        rules.remove(selectedRule);
    }

    public boolean isBaseSaved() {
        return isBaseSaved;
    }

    public void setBaseSaved(boolean baseSaved) {
        isBaseSaved = baseSaved;
    }

    public void saveQuestion(){
        if (!isBaseSaved){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Текущая база знаний не сохранена. Сохранить?", ButtonType.OK,ButtonType.CANCEL);
            Optional<ButtonType> selectedButton = alert.showAndWait();
            String result = selectedButton.get().getText();
            if (result.equals("OK")){
                saveKnowledgeBase();
            }
        }
    }

}

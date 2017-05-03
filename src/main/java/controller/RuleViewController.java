package controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.MainApp;
import model.Condition;
import model.Rule;
import model.Variable;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class RuleViewController implements Initializable {

    @FXML
    private ListView<Rule> ruleListView;
    @FXML
    private Button buttonAdd;
    @FXML
    private Button buttonChange;
    @FXML
    private Button buttonDelete;
    @FXML
    private Button buttonSave;
    @FXML
    private TextField textName;
    @FXML
    private TableView<Condition> conclusionTableView;
    @FXML
    private TableColumn<Condition, Variable> variableConclusionTableColumn;
    @FXML
    private TableColumn<Condition,String> valueConclusionTableColumn;
    @FXML
    private TableView<Condition> conditionTableView;
    @FXML
    private TableColumn<Condition, Variable> variableTableColumn;
    @FXML
    private TableColumn<Condition,String> valueTableColumn;
    @FXML
    private Button buttonAddCondition;
    @FXML
    private Button buttonDeleteCondition;
    @FXML
    private Button buttonAddConclusion;
    @FXML
    private Button buttonDeleteConclusion;
    @FXML
    private TextArea textReason;

    private MainApp mainApp;
    private boolean isSaved = true;
    private boolean ruleEdit;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ruleListView.setOnMouseClicked(event -> {
            ruleEdit = true;
            Rule selectedRule = ruleListView.getSelectionModel().getSelectedItem();
            if (selectedRule == null){
                return;
            }
            saveQuestion();
            textName.setText(selectedRule.getName());
            textReason.setText(selectedRule.getReason());
            conditionTableView.setItems(FXCollections.observableArrayList(selectedRule.getConditions()));
            conclusionTableView.setItems(FXCollections.observableArrayList(selectedRule.getConclusions()));
            isSaved = true;
            setDisable(true);
        });

        buttonAddCondition.setOnAction(event -> {
            openConditionAddView("condition");
            isSaved = false;
            mainApp.setBaseSaved(false);
        });
        buttonDeleteCondition.setOnAction(event -> {
            Condition selectedCondition = conditionTableView.getSelectionModel().getSelectedItem();
            if (selectedCondition != null) {
                conditionTableView.getItems().remove(selectedCondition);
                isSaved = false;
                mainApp.setBaseSaved(false);
            }
        });
        buttonAddConclusion.setOnAction(event -> {
            openConditionAddView("conclusion");
            isSaved = false;
            mainApp.setBaseSaved(false);
        });
        buttonDeleteConclusion.setOnAction(event -> {
            Condition selectedConclusion = conclusionTableView.getSelectionModel().getSelectedItem();
            if (selectedConclusion != null) {
                conclusionTableView.getItems().remove(selectedConclusion);
                isSaved = false;
                mainApp.setBaseSaved(false);
            }
        });
        buttonAdd.setOnAction(event -> {
            saveQuestion();
            clearAll();
            ruleEdit = false;
            textName.requestFocus();
            setDisable(false);
        });
        buttonSave.setOnAction(event -> {
            buttonSaveClick();
            mainApp.setBaseSaved(false);

        });
        buttonDelete.setOnAction(event -> {
            Rule selectedRule = ruleListView.getSelectionModel().getSelectedItem();
            if (selectedRule != null) {
                ruleListView.getItems().remove(selectedRule);
                mainApp.deleteRule(selectedRule);
                ruleListView.getSelectionModel().clearSelection();
                isSaved = true;
                mainApp.setBaseSaved(false);
                setDisable(true);
            }

        });
        textName.setOnKeyPressed(event -> {
            isSaved = false;
        });
        textReason.setOnKeyPressed(event -> {
            isSaved = false;
        });
        variableTableColumn.setCellValueFactory(new PropertyValueFactory<Condition, Variable>("variable"));
        valueTableColumn.setCellValueFactory(new PropertyValueFactory<Condition, String>("value"));
        variableConclusionTableColumn.setCellValueFactory(new PropertyValueFactory<Condition, Variable>("variable"));
        valueConclusionTableColumn.setCellValueFactory(new PropertyValueFactory<Condition, String>("value"));

        buttonChange.setOnAction(event -> {
            setDisable(false);
        });

        //Drag and Drop

    }

    private void setDisable(boolean f){
        textName.setDisable(f);
        buttonAddConclusion.setDisable(f);
        buttonDeleteConclusion.setDisable(f);
        buttonAddCondition.setDisable(f);
        buttonDeleteCondition.setDisable(f);
        buttonSave.setDisable(f);
        textReason.setDisable(f);
    }

    private void saveQuestion() {
        if (!isSaved){

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Текущее правило не сохранено. Сохранить?",ButtonType.OK,ButtonType.CANCEL);
            Optional<ButtonType > selectedButton = alert.showAndWait();

            if (selectedButton != null) {
                String result = selectedButton.get().getText();
                if (result.equals("OK")) {
                    buttonSaveClick();
                    clearAll();
                }
                else
                    clearAll();
            }
        }
    }

    private void buttonSaveClick() {

        Rule rule = new Rule();
        rule.setName(textName.getText().trim());
        rule.setReason(textReason.getText().trim());
        if (rule.getConclusions().equals("")){
            new Alert(Alert.AlertType.CONFIRMATION,"Введите имя",ButtonType.OK).showAndWait();
            return;
        }

        int n = conditionTableView.getItems().size();
        for (int i = 0; i < n; i++){
            rule.addCondition(conditionTableView.getItems().get(i));
        }
        n = conclusionTableView.getItems().size();
        for (int i = 0; i < n; i++){
            rule.addConclusion(conclusionTableView.getItems().get(i));
        }

        Rule selectedRule = ruleListView.getSelectionModel().getSelectedItem();
        /*ruleEdit = false;
        if (selectedRule != null)
            ruleEdit = textName.getText().toLowerCase().trim().equals(selectedRule.getName().toLowerCase());*/
        int existIndex = isRuleExist(rule,mainApp.rules);
        if (existIndex == -1){
                int selectedIndex = ruleListView.getSelectionModel().getSelectedIndex();
                if (selectedIndex != ruleListView.getItems().size() && selectedIndex != -1)
                    ruleListView.getItems().add(selectedIndex + 1,rule);
                else ruleListView.getItems().add(rule);
                //ruleListView.getSelectionModel().select(rule);
                mainApp.setBaseSaved(false);
                isSaved = true;
            }
        else if (ruleEdit) {
            ruleListView.getItems().set(existIndex, rule);
            //ruleListView.getSelectionModel().select(rule);
            mainApp.setBaseSaved(false);
            isSaved = true;
        }else{
            new Alert(Alert.AlertType.INFORMATION,"Правило с таким именем уже существует").showAndWait();
        }
        setDisable(true);
    }

    private void clearAll() {
        textName.setText("");
        textReason.setText("");
        conditionTableView.getItems().clear();
        conclusionTableView.getItems().clear();
    }

    private int isRuleExist(Rule rule, ArrayList<Rule> rules) {
        for (int i = 0; i < rules.size(); i++){
            if (rules.get(i).getName().toLowerCase().equals(rule.getName().toLowerCase())){
                return i;
            }
        }
        return -1;
    }

    private void openConditionAddView(String par) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/conditionAddView.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.getIcons().add(new Image(String.valueOf(getClass().getResource("/logo.png"))));
            if (par.equals("condition"))
                dialogStage.setTitle("Редактирование посылки");
            else dialogStage.setTitle("Редактирование заключения");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainApp.getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            ConditionAddViewConroller controller = loader.getController();
            controller.setRuleViewController(this);
            controller.setDialogStage(dialogStage);
            controller.setPar(mainApp.variables, par);
            controller.setMainApp(mainApp);

            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MainApp getMainApp() {
        return mainApp;
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void setItems(ArrayList<Rule> rules) {
        ruleListView.setItems(FXCollections.observableList(rules));
    }

    public void clear() {
        ruleListView.getItems().clear();
    }

    public void addCondition(Condition condition) {
        conditionTableView.getItems().add(condition);
        isSaved = false;
    }

    public void addConclusion(Condition condition) {
        conclusionTableView.getItems().add(condition);
        isSaved = false;
    }
}

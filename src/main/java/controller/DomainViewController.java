package controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.MainApp;
import model.Domain;

import java.awt.*;
import java.awt.image.AreaAveragingScaleFilter;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by Дмитрий on 01.03.2017.
 */
public class DomainViewController implements Initializable {

    @FXML
    private Button domainAdd;
    @FXML
    private Button domainEdit;
    @FXML
    private Button domainDelete;
    @FXML
    private Button domainValueAdd;
    @FXML
    private Button domainValueEdit;
    @FXML
    private Button domainValueDelete;
    @FXML
    private ListView<Domain> domainListView;
    @FXML
    private ListView<String> valueListView;

    private MainApp mainApp;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        domainListView.setOnMouseClicked(event->{
            //valueListView.getItems().clear();
            Domain selectedDomain = domainListView.getSelectionModel().getSelectedItem();
            if (selectedDomain == null){
                return;
            }
            valueListView.setItems(FXCollections.observableList(selectedDomain.getValues()));
        });
        domainAdd.setOnAction(event->{
            openDomainAddView("","domain");
        });

        domainEdit.setOnAction(event -> {
            String par = "";
            Domain selectedDomain = domainListView.getSelectionModel().getSelectedItem();
            if (selectedDomain != null)
                par = selectedDomain.getName();
            openDomainAddView(par,"domain");
        });

        domainDelete.setOnAction(event->{
            Domain selectedDomain = domainListView.getSelectionModel().getSelectedItem();
            if (selectedDomain != null) {
                domainListView.getItems().remove(selectedDomain);
                mainApp.deleteDomain(selectedDomain);
            }
        });

        domainValueAdd.setOnAction(event->{
            if (domainListView.getSelectionModel().getSelectedItem() == null){
                new Alert(Alert.AlertType.INFORMATION,"Выберите домен").showAndWait();
                return;
            }
            openDomainAddView("","value");
        });

        domainValueEdit.setOnAction(event -> {
            String par = "";
            String selectedValue = valueListView.getSelectionModel().getSelectedItem();
            if (selectedValue != null)
                par = selectedValue;
            openDomainAddView(par,"value");
        });

        domainValueDelete.setOnAction(event->{
            String selectedValue = valueListView.getSelectionModel().getSelectedItem();
            int selectedDomain = domainListView.getSelectionModel().getSelectedIndex();
            if (selectedValue != null && selectedDomain > -1) {
                valueListView.getItems().remove(selectedValue);
                mainApp.deleteDomainValue(selectedDomain, selectedValue);
            }
        });

        if (mainApp != null)
            setItems(mainApp.domains);
    }

    public void openDomainAddView(String parName,String parType){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/domainAddView.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.getIcons().add(new Image(String.valueOf(getClass().getResource("/logo.png"))));
            dialogStage.setTitle("Редактирование");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainApp.getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            DomainAddViewController controller = loader.getController();
            controller.setDomainViewController(this);
            controller.setDialogStage(dialogStage);
            controller.setPar(parName, parType);

            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addDomain(Domain domain) throws Exception {
        if (isDomainExist(domain,mainApp.domains))
            throw new Exception("Домен с таким именем существует");
        domainListView.getItems().add(domain);
        domainListView.getSelectionModel().select(domain);
        mainApp.setBaseSaved(false);
        //mainApp.addDomain(domain);
    }

    private boolean isDomainExist(Domain domain, ArrayList<Domain> arrayList) {

        for (int i = 0; i < arrayList.size(); i++){
            if (arrayList.get(i).getName().toLowerCase().equals(domain.getName().toLowerCase()))
                return true;
        }
        return false;
    }

    public void editDomain(Domain domain){
        int selectedDomain = domainListView.getSelectionModel().getSelectedIndex();
        if (domain != null) {
            mainApp.editDomain(selectedDomain, domain.getName());
            domainListView.getItems().set(selectedDomain,domain);
            mainApp.setBaseSaved(false);
        }
    }

    public void addDomainValue(String value) throws Exception {
        if (isDomainValueExist(domainListView.getSelectionModel().getSelectedItem(),value))
            throw new Exception("Такое значение домена существует");
        valueListView.getItems().add(value);
        valueListView.getSelectionModel().select(value);
        mainApp.setBaseSaved(false);
        /*Domain selectedDomain = domainListView.getSelectionModel().getSelectedItem();
        if (selectedDomain != null){
            selectedDomain.getValues().add(value);
        }*/
    }

    private boolean isDomainValueExist(Domain domain, String value) {
        for (int i = 0; i < domain.getValues().size(); i++){
            if (domain.getValues().get(i).toLowerCase().equals(value.toLowerCase()))
                return true;
        }
        return false;
    }

    public void editDomainValue(String value){
        int selectedValue = domainListView.getSelectionModel().getSelectedIndex();
        int selectedDomain = domainListView.getSelectionModel().getSelectedIndex();
        if (value != null && selectedValue>-1 && selectedDomain>-1) {
            mainApp.editDomainValue(selectedDomain, selectedValue, value);
            valueListView.getItems().set(selectedValue,value);
            mainApp.setBaseSaved(false);
        }
    }

    public MainApp getMainApp() {
        return mainApp;
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void clear(){
        domainListView.getItems().clear();
        valueListView.getItems().clear();
    }

    public void setItems(ArrayList<Domain> domains){
        domainListView.setItems(FXCollections.observableList(domains));
        mainApp.setBaseSaved(false);
    }
}

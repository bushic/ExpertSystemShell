package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import model.Domain;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Дмитрий on 01.03.2017.
 */
public class DomainAddViewController implements Initializable {

    private DomainViewController domainViewController;

    @FXML
    private Button buttonAdd;
    @FXML
    private Button buttonCancel;
    @FXML
    private TextField textName;
    @FXML
    private Label labelText;

    private Stage dialogStage;
    private String parName;
    private String parType;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buttonAdd.setOnAction(event->{
            doAdd();
        });
        buttonCancel.setOnAction(event->dialogStage.close());
        textName.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER){
                doAdd();
            }
        });
    }

    private void doAdd() {
        Domain domain = new Domain();
        domain.setName(textName.getText().trim());
        if (parName.equals("") && parType.equals("domain"))
            try {
                domainViewController.addDomain(domain);
            } catch (Exception e) {
                new Alert(Alert.AlertType.INFORMATION,e.getMessage()).showAndWait();
                textName.requestFocus();
                return;
            }
        else if (!parName.equals("") && parType.equals("domain"))
            domainViewController.editDomain(domain);
        else if (parName.equals(""))
            try {
                domainViewController.addDomainValue(textName.getText().trim());
            } catch (Exception e) {
                new Alert(Alert.AlertType.INFORMATION,e.getMessage()).showAndWait();
                textName.requestFocus();
                return;
            }
        else if (!parName.equals(""))
            domainViewController.editDomainValue(textName.getText().trim());
        dialogStage.close();
    }

    public DomainViewController getDomainViewController() {
        return domainViewController;
    }

    public void setDomainViewController(DomainViewController domainViewController) {
        this.domainViewController = domainViewController;
    }

    public Stage getDialogStage() {
        return dialogStage;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setPar(String parName, String parType){
        this.parName = parName;
        this.parType = parType;
        textName.setText(parName);
        if (!parName.equals("")){
            buttonAdd.setText("Изменить");
        }
        if (parType.equals("domain")){
            labelText.setText("Введите имя домена");
        }else{
            labelText.setText("Введите значение домена");
        }
    }
}

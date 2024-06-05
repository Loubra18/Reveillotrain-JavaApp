package be.bralion.appreveillotrain.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class WifiSetupViewController {
    @FXML
    private TextField ssidField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private ChoiceBox<String> scrollingMenu;

    @FXML
    public void onSentButtonClicked() {
        setSSID();
        setPassword();
        setComPort();
        checkInput();
        listener.onSentButtonClicked();
    }

    private void checkInput() {
        if (ssid.isEmpty() || password.isEmpty() || selectedComPort.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Please fill in all fields");
            alert.showAndWait();
        }
    }

    private WifiSetupViewListener listener;
    private static Stage clientWindow;
    private String ssid;
    private String password;
    private String[] comPortList;
    private String selectedComPort;


    public void init(){
        displayComPortList();
    }

    public void setListener(WifiSetupViewListener listener) {
        this.listener = listener;
    }

    public static URL getFxmlRessource() {
        return WifiSetupViewController.class.getResource("/be/bralion/appreveillotrain/wifi-setup-view.fxml");
    }
    
    private void setSSID() {
        this.ssid = ssidField.getText();
    }
    
    private void setPassword() {
         this.password = passwordField.getText();
    }

    private void setComPort() {
        if(scrollingMenu.getValue() != null) {
            selectedComPort = scrollingMenu.getValue();
        } else {
            selectedComPort = "";
        }
    }

    public String getSSID() {
        return ssid;
    }

    public String getPassword() {
        return password;
    }

    public String getSelectedComPort() {
        return selectedComPort;
    }

    public void setComPortList(String[] comPortList) {
        this.comPortList = comPortList;
    }
    

    public static void setStageOf(FXMLLoader fxmlLoader) throws IOException {
        clientWindow = new Stage();
        Scene scene = new Scene(fxmlLoader.load(), 505, 500);
        clientWindow.setTitle("Client Interface");
        clientWindow.setScene(scene);
        clientWindow.show();
    }

    private void displayComPortList() {
        listener.fillScrollingMenu();
        for (String comPort : comPortList) {
            scrollingMenu.getItems().add(comPort);
        }
    }


    public interface WifiSetupViewListener {
        void onSentButtonClicked();
        void fillScrollingMenu();
    }
}
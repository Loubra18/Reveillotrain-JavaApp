package be.bralion.appreveillotrain.controller;

import be.bralion.appreveillotrain.view.WifiSetupViewController;
import com.fazecast.jSerialComm.SerialPort;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.OutputStream;

public class MainController extends Application implements WifiSetupViewController.WifiSetupViewListener {

    private WifiSetupViewController wifiSetupViewController;
    private SerialPort arduinoPort;
    private String[] comPortList;

    @Override
    public void start(Stage stage) throws IOException {
        //initializeSerial();
        FXMLLoader fxmlLoader = new FXMLLoader(wifiSetupViewController.getFxmlRessource());
        wifiSetupViewController = new WifiSetupViewController();
        fxmlLoader.setController(wifiSetupViewController);
        wifiSetupViewController.setListener(this);
        WifiSetupViewController.setStageOf(fxmlLoader);
        wifiSetupViewController.init();
        //j'établis la connexion avec l'arduino
    }

    public static void main(String[] args) {
        launch();
    }


    private void getComPortList() {
        SerialPort[] ports = SerialPort.getCommPorts();
        comPortList = new String[ports.length];
        for (int i = 0; i < ports.length; i++) {
            comPortList[i] = ports[i].getSystemPortName();
        }
    }

    private void initializeSerial() {
        try{
            arduinoPort.setComPortParameters(115200, 8, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY);
            arduinoPort.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 0);
            if (arduinoPort.openPort()) {
                System.out.println("Port opened: " + arduinoPort.getDescriptivePortName());
            } else {
                System.out.println("Failed to open port: " + arduinoPort.getDescriptivePortName());
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void sendWifiConfig(String ssid, String password) {
        if (arduinoPort != null && arduinoPort.isOpen()) {
            try {
                OutputStream outputStream = arduinoPort.getOutputStream();
                outputStream.write(("SSID:" + ssid + "\n").getBytes());
                outputStream.flush();
                Thread.sleep(500);  // Wait a bit before sending the next command
                outputStream.write(("PASS:" + password + "\n").getBytes());
                outputStream.flush();
                System.out.println("WiFi configuration sent.");
                //close the port
                arduinoPort.closePort();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Port is not open.");
        }
    }

    @Override
    public void onSentButtonClicked() {
        try{
            System.out.printf("SSID: %s, Password: %s, Port: %s\n", wifiSetupViewController.getSSID(), wifiSetupViewController.getPassword(), wifiSetupViewController.getSelectedComPort());
            setArduinoPort();
            initializeSerial();
            sendWifiConfig(wifiSetupViewController.getSSID(), wifiSetupViewController.getPassword());
        }catch(Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void fillScrollingMenu() {
        System.out.println("Scrolling menu clicked.");
        getComPortList();
        wifiSetupViewController.setComPortList(comPortList);
    }

    private void setArduinoPort() {
        arduinoPort = SerialPort.getCommPort(wifiSetupViewController.getSelectedComPort());
    }
}
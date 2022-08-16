package client;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.LoginUser;
import model.RegisterUser;

import java.awt.event.MouseEvent;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LoginFormController implements Initializable {
    @FXML
    private Pane pnSignUp;

    @FXML
    private TextField txtRegname;

    @FXML
    private TextField txtRegFirstname;

    @FXML
    private TextField txtRegPassword;

    @FXML
    private TextField txtRegEmail;

    @FXML
    private TextField txtRegPhoneNo;

    @FXML
    private RadioButton female;

    @FXML
    private ToggleGroup Gender;

    @FXML
    private RadioButton male;

    @FXML
    private Label controlRegLabel;

    @FXML
    private Label success;

    @FXML
    private Label goBack;

    @FXML
    private Button getStarted;

    @FXML
    private ImageView btnBack;

    @FXML
    private Pane pnSignIn;

    @FXML
    private Button btnSignUp;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUsername;

    @FXML
    private Label loginNotifier;

    public Label nameExists;
    public Label checkEmail;


    public static String username, password, gender;
    public static ArrayList<User> loggedInUser = new ArrayList<>();
    public static ArrayList<User> users = new ArrayList<User>();

    @FXML
    void handleButtonAction(ActionEvent event) {
        if (event.getSource().equals(btnSignUp)) {
            new FadeTransition().play();
            pnSignUp.toFront();
        }
        if (event.getSource().equals(getStarted)) {
            new FadeTransition().play();
            pnSignIn.toFront();
        }
        loginNotifier.setOpacity(0);
        txtUsername.setText("");
        txtPassword.setText("");

    }
    @FXML
    void handleMouseEvent(MouseEvent event) {
        if (event.getSource() == btnBack) {

            new FadeTransition().play();
            pnSignIn.toFront();
        }
        txtRegname.setText("");
        txtRegPassword.setText("");
        txtRegEmail.setText("");

    }
    private void makeDefault() {
        txtRegname.setText("");
        txtRegPassword.setText("");
        txtRegEmail.setText("");
        txtRegFirstname.setText("");
        txtRegPhoneNo.setText("");
        male.setSelected(true);

    }


    @FXML
    public void login(ActionEvent event) throws IOException {
            Socket socket= null;
            try {
                //localhost:8001
                socket = new Socket("0.tcp.in.ngrok.io",17097);

                System.out.println("Connected Chat server Login");
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                objectOutputStream.writeObject(new LoginUser(txtUsername.getText(),txtPassword.getText()));
                String response=reader.readLine();

                if (response.equalsIgnoreCase("null")) {
                    username=null;
                    loginNotifier.setOpacity(1);
                } else {
                    System.out.println(response);
                    username=response;
                    changeWindow();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    private void changeWindow() {
        try {
            Stage stage = (Stage) txtUsername.getScene().getWindow();
            URL resource = this.getClass().
                    getResource("ChatRoomBox.fxml");
            Parent load = FXMLLoader.load(resource);
            Scene scene= new Scene(load);

            stage.setScene(scene);
            stage.show();
            stage.setTitle(username + "");
            stage.setOnCloseRequest(event -> {
                System.exit(0);
            });
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void registration(ActionEvent event) throws IOException {
        if (!txtRegname.getText().equalsIgnoreCase("")
                && !txtRegPassword.getText().equalsIgnoreCase("")
                && !txtRegEmail.getText().equalsIgnoreCase("")
                && !txtRegFirstname.getText().equalsIgnoreCase("")
                && !txtRegPhoneNo.getText().equalsIgnoreCase("")
                && (male.isSelected() || female.isSelected())) {
            if (checkUser(txtRegname.getText())) {
                if (checkEmail(txtRegEmail.getText())) {

                    String uName = txtRegname.getText();
                    String password = txtRegPassword.getText();
                    String email = txtRegEmail.getText();
                    String fullName = txtRegFirstname.getText();
                    String phoneNo = txtRegPhoneNo.getText();
                    String gender;

                    if (male.isSelected()) {
                        gender = "Male";
                    } else {
                        gender = "Female";
                    }


                     new Thread(()->{
                         try {
                             //localhost:8000
                             Socket socket=new Socket("0.tcp.in.ngrok.io",14760);
                             ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                             objectOutputStream.writeObject(new RegisterUser(uName,password,fullName,email,gender,phoneNo));

                             String read=String.valueOf(reader.readLine());
                             System.out.println(read);
                             if (read.equalsIgnoreCase("registered")){
                                 System.out.println("ok");
                                 success.setOpacity(1);
                                 goBack.setOpacity(0);
                                 socket.close();
                             }else {
                                 System.out.println("no");
                                 goBack.setOpacity(1); success.setOpacity(0);}

                         } catch (IOException e) {
                             e.printStackTrace();
                         }

                     }).start();

                    makeDefault();
                }
            }
        }
    }

    private boolean checkUser(String username) {
        for(User user : users) {
            if(user.name.equalsIgnoreCase(username)) {
                return false;
            }
        }
        return true;
    }
    private boolean checkEmail(String email) {
        for(User user : users) {
            if(user.email.equalsIgnoreCase(email)) {
                return false;
            }
        }
        return true;
    }


    public void handleMouseEvent(javafx.scene.input.MouseEvent mouseEvent) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}

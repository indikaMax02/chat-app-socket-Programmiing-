package client;


import com.madeorsk.emojisfx.EmojisLabel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import server.model.ClientHandler;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ChatRoomBoxController extends Thread implements Initializable {

    public ScrollPane chatAppendPane;
    public VBox vBoxAppend;
    @FXML
    private Label clientName;


    @FXML
    private TextArea msgRoom;

    @FXML
    private TextField msgField;



    private FileChooser fileChooser;
    private File filePath;


    BufferedReader reader;
    InputStream imageSocketInputStream;
    PrintWriter writer;
    Socket socket;
    Socket imageSocket;


    private ArrayList<ClientHandler> clients;
    private BufferedImage image;


    public void connectSocket() {
        try {
            //localhost:8002 & 8005
            socket = new Socket("localhost", 8002);
            imageSocket=new Socket("localhost",8005);
            System.out.println("Socket is connected with server!");
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF8"));

            writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF8"),true);


            this.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {

        new Thread(()->{
            while (true){
                try {

                    InputStream inputStream = socket.getInputStream();
                    System.out.println("Reading client side: " + System.currentTimeMillis());

                    byte[] sizeAr = new byte[4];
                    inputStream.read(sizeAr);
                    int size = ByteBuffer.wrap(sizeAr).asIntBuffer().get();
                    byte[] imageAr = new byte[size];
                    inputStream.read(imageAr);
                    image = ImageIO.read(new ByteArrayInputStream(imageAr));
                    System.out.println("client paththe image load unaa");



                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        try {
            while (true) {
                System.out.println("text  ekak ewanakan balagena innawa");
                String msg = reader.readLine();
                String[] tokens = msg.split(" ");
                String cmd = tokens[0];
                System.out.println(cmd);
                StringBuilder fulmsg = new StringBuilder();
                for(int i = 1; i < tokens.length; i++) {
                    fulmsg.append(tokens[i]);
                }
              /*  System.out.println(fulmsg);*/
                if (cmd.equalsIgnoreCase(LoginFormController.username + ":")) {
                    continue;
                } else if(fulmsg.toString().equalsIgnoreCase("bye")) {
                    break;
                }



                /*msgRoom.appendText(msg + "\n");*/
////////////set Text message and Emoji///////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


                HBox hBox = new HBox();
                hBox.setSpacing(10);
                // hBox.getChildren().add(new Text("how are you fdvbjhfdbvjfdhjvhfdjvbfdjvnjfdvjfdvbjdfbvjfdbvjhfdhvjhdfjvhbfdjvbfdjhvjdfhvjdfhvjdfvjhfdjvhhdjh"));
                AnchorPane anchorPane = new AnchorPane();
                //anchorPane.setPrefWidth(300);
                // anchorPane.setBackground(new Background(new BackgroundFill(Color.RED,null,null)));
                anchorPane.setStyle("-fx-background-radius: 10px; -fx-background-color: #d3d9d3;");


                EmojisLabel label = new EmojisLabel();

                label.setText(msg);

                label.setMaxWidth(300);
                String mgsText=msg;
                Text text = new Text(mgsText);
                double width = text.getLayoutBounds().getWidth();
                System.out.println(width);
                label.setPrefWidth(width+15);
                label.setStyle("-fx-alignment: center ; -fx-font-size: 15 ; -fx-font-family: 'Agency FB';");
                label.setPadding(new Insets(5, 5, 5, 5));
                anchorPane.getChildren().add(label);



                anchorPane.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
                hBox.getChildren().add(anchorPane);
                System.out.println("wada wada");

                Platform.runLater(
                        () -> {
                            vBoxAppend.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
                            vBoxAppend.getChildren().add(hBox);
                        }
                );





////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



                //////////////////////////////////////set Image //////////////////////////////////////////





                ///////////////////////////////////////////////////////////////////////////////////////////



            }
            reader.close();
            writer.close();
            socket.close();
        } catch (Exception e) {
        e.printStackTrace();
        }
    }


    public void send() throws UnsupportedEncodingException {
        String msg = msgField.getText();
        System.out.println(msg);
       writer.println(LoginFormController.username + ": " + msg);
      //  msgRoom.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
      //  msgRoom.appendText("Me: " + msg + "\n");
        HBox hBox = new HBox();
        hBox.setSpacing(10);
       // hBox.getChildren().add(new Text("how are you fdvbjhfdbvjfdhjvhfdjvbfdjvnjfdvjfdvbjdfbvjfdbvjhfdhvjhdfjvhbfdjvbfdjhvjdfhvjdfhvjdfvjhfdjvhhdjh"));
        AnchorPane anchorPane = new AnchorPane();
        //anchorPane.setPrefWidth(300);
       // anchorPane.setBackground(new Background(new BackgroundFill(Color.RED,null,null)));
        anchorPane.setStyle("-fx-background-radius: 10px; -fx-background-color: #69c65c;");


        EmojisLabel label = new EmojisLabel();

        label.setText(msg);

        label.setMaxWidth(300);
        String mgsText=msgField.getText();
        final Text text = new Text(mgsText);
        final double width = text.getLayoutBounds().getWidth();
        System.out.println(width);
         label.setPrefWidth(width+15);
       label.setStyle("-fx-alignment: center ; -fx-font-size: 15 ; -fx-font-family: 'Agency FB';");
        label.setPadding(new Insets(5, 5, 5, 5));
        anchorPane.getChildren().add(label);
       // hBox.getChildren().add(new Text("Kamal :"));




        anchorPane.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
        hBox.setPrefWidth(453);


        Pane pane = new Pane();
        pane.setPrefSize(490,20);

        hBox.getChildren().addAll(pane,anchorPane);



        vBoxAppend.getChildren().add(hBox);





        /*msgField.setText("");
        if(msg.equalsIgnoreCase("BYE") || (msg.equalsIgnoreCase("logout"))) {
            System.exit(0);
        }*/
    }
    public boolean saveControl = false;

    public void sendMessageByKey(KeyEvent event) {
        if (event.getCode().toString().equals("ENTER")) {
            try {
                send();
            } catch (UnsupportedEncodingException e) {


            }
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        clientName.setText(LoginFormController.username);
        connectSocket();
    }

    public void handleSendEvent(javafx.scene.input.MouseEvent mouseEvent) {
        try {
            send();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        for(User user : LoginFormController.users) {
            System.out.println(user.name);
        }

    }


    public void selectImage(javafx.scene.input.MouseEvent mouseEvent) throws IOException {



    }


    public void viewImojiOnAction(MouseEvent mouseEvent) {
        System.out.println("imoji view ");
    }




    public void sendImageOnMouseClick(MouseEvent mouseEvent) throws IOException {
        HBox hBox = new HBox();
        //selectImage();
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(new Stage());
        System.out.println(file.getName());

        InputStream stream = new FileInputStream(file.getPath());
        Image image = new Image(stream);


        OutputStream outputStream = imageSocket.getOutputStream();
        BufferedImage im = ImageIO.read(file);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(im, "jpg", byteArrayOutputStream);

        byte[] size = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();
        outputStream.write(size);
        outputStream.write(byteArrayOutputStream.toByteArray());
        outputStream.flush();

        ImageView imageView = new ImageView(image);

        imageView.setFitWidth(280);
        imageView.setFitHeight(180);



        hBox.setSpacing(10);
        // hBox.getChildren().add(new Text("how are you fdvbjhfdbvjfdhjvhfdjvbfdjvnjfdvjfdvbjdfbvjfdbvjhfdhvjhdfjvhbfdjvbfdjhvjdfhvjdfhvjdfvjhfdjvhhdjh"));
        AnchorPane anchorPane = new AnchorPane();
        if (280>=image.getWidth()){imageView.setFitWidth(image.getWidth()); anchorPane.setPrefWidth(1000);}
        if(180>=image.getWidth()){imageView.setFitHeight(image.getHeight()); anchorPane.setPrefWidth(image.getHeight());}

        // anchorPane.setBackground(new Background(new BackgroundFill(Color.RED,null,null)));
        anchorPane.setStyle("-fx-background-radius: 10px; -fx-background-color: #69c65c;");
        AnchorPane pane = new AnchorPane();
        pane.setStyle("-fx-alignment: center;");
        pane.setTranslateX(10);
        pane.setTranslateY(10);
        pane.setPadding(new Insets(5, 15, 15, 5));
        pane.setMaxHeight(280);
        pane.setMaxWidth(180);
        pane.getChildren().add(imageView);
        anchorPane.getChildren().add(pane);

        EmojisLabel label = new EmojisLabel();
        label.setText(msgField.getText());

        hBox.getChildren().add(anchorPane);
        vBoxAppend.getChildren().add(hBox);




    }
}

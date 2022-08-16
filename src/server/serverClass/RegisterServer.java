package server.serverClass;

import model.RegisterUser;
import server.bo.BoFactory;
import server.bo.SuperBO;
import server.bo.custom.UserBO;
import server.dto.UserDTO;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

public class RegisterServer extends Thread {


    @Override
    public void run() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(8000);
            while (true) {
                System.out.println("Waiting for User...register Server");
                Socket socket = serverSocket.accept();
                System.out.println("User Connected");




                 new Thread(()->{

                             try {
                                 ObjectInputStream is = new ObjectInputStream(socket.getInputStream());

                                 RegisterUser details = (RegisterUser) is.readObject();

                                 UserBO userBO = (UserBO) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.USER);
                                 boolean isRegisted = userBO.registerUser(new UserDTO(details.getUserName(), details.getPassword(), details.getFullName(), details.getEmail(), details.getGender(), details.getPhoneNo()));
                                 if (isRegisted) {
                                     System.out.println("registred");
                                     PrintWriter writer = new PrintWriter(socket.getOutputStream());
                                     writer.println("registered");
                                     writer.flush();
                                 }

                             } catch (SQLException throwables) {
                                 PrintWriter writer = null;
                                 try {
                                     System.out.println("error");
                                     writer = new PrintWriter(socket.getOutputStream());
                                     writer.println("error");
                                     writer.flush();
                                 } catch (IOException e) {
                                     e.printStackTrace();
                                 }

                             } catch (ClassNotFoundException e) {
                                 e.printStackTrace();
                             } catch (IOException e) {
                                 e.printStackTrace();
                             }

                 }).start();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package server.serverClass;

import model.LoginUser;
import model.RegisterUser;
import server.bo.BoFactory;
import server.bo.custom.UserBO;
import server.dto.LoginUserDTO;
import server.dto.UserDTO;
import server.dto.UserDetailsDTO;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

public class LoginServer extends Thread{

    UserBO userBO = (UserBO) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.USER);

    @Override
    public void run(){
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(8001);
            while (true) {
                System.out.println("Waiting for User...login Server");
                Socket socket = serverSocket.accept();
                System.out.println("User Connected");


                new Thread(()->{
                    try {
                        ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
                        LoginUser userAccount =(LoginUser) is.readObject();
                        System.out.println("okokok");
                        UserDetailsDTO userDetailsDTO = userBO.loginAccount(new LoginUserDTO(userAccount.getUserName(), userAccount.getPassword()));
                        if(userDetailsDTO!=null){
                            PrintWriter writer = new PrintWriter(socket.getOutputStream());
                            writer.println(userDetailsDTO.getUserName());
                            System.out.println("login ok");
                            writer.flush();
                        }else{
                            PrintWriter writer = new PrintWriter(socket.getOutputStream());
                            writer.println("null");
                            System.out.println("user name or password incorrct");
                            writer.flush();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (SQLException throwables) {
                        PrintWriter writer = null;
                        try {
                            writer = new PrintWriter(socket.getOutputStream());
                            writer.println("null");
                            System.out.println("login notFound");
                            writer.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }).start();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

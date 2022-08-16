package server;

import model.LoginUser;
import server.serverClass.ChatServer;
import server.serverClass.LoginServer;
import server.serverClass.RegisterServer;

public class AppInitializer {

    public static void main(String[] args) {

        RegisterServer registerServer=new RegisterServer();
        registerServer.start();

        LoginServer loginServer=new LoginServer();
        loginServer.start();

        ChatServer chatServer=new ChatServer();
        chatServer.start();

    }


}

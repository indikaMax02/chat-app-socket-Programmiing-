package server.serverClass;

import server.model.ClientHandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChatServer extends Thread{
    private static ArrayList<ClientHandler> clients = new ArrayList<ClientHandler>();

    @Override
    public void run(){
        ServerSocket serverSocket= null;
        try {
            serverSocket = new ServerSocket(8002);
            while(true) {
                System.out.println("Waiting for clients...Chat Server");
                Socket socket = serverSocket.accept();
                System.out.println("Connected");

                PrintWriter writer=new PrintWriter(socket.getOutputStream());
                writer.println("loaded chat history");
                writer.flush();

                ClientHandler clientThread = new ClientHandler(socket, clients);
                clients.add(clientThread);
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

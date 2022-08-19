package server.serverClass;

import server.model.ClientHandler;
import server.model.ImageHandler;

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
        ServerSocket serverSocket1=null;
        try {
            serverSocket = new ServerSocket(8002);
            serverSocket1=new ServerSocket(8005);
            while(true) {
                System.out.println("Waiting for clients...Chat Server and Image tranfer proto");
                Socket chatSocket = serverSocket.accept();
                Socket imageTranferSocket = serverSocket1.accept();
                System.out.println("Connected");

                PrintWriter writer=new PrintWriter(chatSocket.getOutputStream());
                writer.println("loaded chat history");
                writer.flush();

                ClientHandler clientThread = new ClientHandler(chatSocket,imageTranferSocket, clients);
                clientThread.start();

                ImageHandler imageHandler = new ImageHandler(imageTranferSocket, clients);
                clients.add(clientThread);
                imageHandler.start();

                System.out.println("okkoma hari");


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

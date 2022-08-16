package server.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler extends Thread {



    private ArrayList<ClientHandler> clients;
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    public ClientHandler(Socket socket, ArrayList<ClientHandler> clients) {
        try {
            this.socket = socket;
            this.clients = clients;
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.writer = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String msg = null;
        try {
            while ((msg = reader.readLine()) != null) {
                System.out.println(msg);
                /*if (msg.equalsIgnoreCase( "bye")) {
                    break;
                }*/
                for (ClientHandler cl : clients) {
                    cl.writer.println(msg);
                }
            }
        } catch (Exception e) {
            /*e.printStackTrace();*/
            String[] split = msg.split(":");
            System.out.println("offline "+split[0]);
        }
        finally {
            try {
                reader.close();
                writer.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}

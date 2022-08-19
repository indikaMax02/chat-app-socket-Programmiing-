package server.model;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class ClientHandler extends Thread {



    private ArrayList<ClientHandler> clients;
    private Socket socket;
    private Socket imageSocket;
    private BufferedReader reader;
    private PrintWriter writer;


    public ClientHandler(Socket socket,Socket imageSocket, ArrayList<ClientHandler> clients) {
        try {
            this.socket = socket;
            this.imageSocket=imageSocket;
            this.clients = clients;

          this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF8"));
           this.writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF8"),true);

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
                    //cl.image = ImageIO.read(new ByteArrayInputStream(imageAr));
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

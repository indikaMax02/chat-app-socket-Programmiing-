package server.model;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class ImageHandler extends Thread{

    Socket socket;
    private ArrayList<ClientHandler> clients;
    private BufferedImage image;
    InputStreamReader inputStream;

    public ImageHandler(Socket socket, ArrayList<ClientHandler> clients) throws IOException {
            this.socket = socket;
            this.clients = clients;
            this.inputStream=new InputStreamReader(socket.getInputStream());
    }

    @Override
    public void run(){


    }

}

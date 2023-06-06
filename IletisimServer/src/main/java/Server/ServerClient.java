/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mertcankarabas
 */
public class ServerClient extends Thread {

    Socket socket;
    String name;
    OutputStream output;
    InputStream input;
    boolean isListening;
    Server server;

    public ServerClient(Socket socket, Server server) throws IOException {
        this.server = server;
        this.isListening = false;
        this.socket = socket;
        this.output = socket.getOutputStream();
        this.input = socket.getInputStream();
    }

    public void sendMessage(byte[] bytes) throws IOException {
        this.output.write(bytes);
    }

    public String clientNames() {
        return null;
    }

    public void StartClient(ServerClient srvClient) {
        this.isListening = true;
        this.start();
    }

    public void StopClient() {
        try {
            this.isListening = false;
            this.server.removeClient(this);
            this.input.close();
            this.output.close();
            this.socket.close();  
        } catch (IOException ex) {
            //System.out.println("Client düzgün kapanmadı.." + ex);
            Logger.getLogger(ServerClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {

        int byteSize;

        try {
            while (this.isListening) {

                byteSize = this.input.read();
                byte bytes[] = new byte[byteSize]; // blocking
                this.input.read(bytes);

                String recievedMessage = new String(bytes, StandardCharsets.UTF_8);
                System.out.println("--------------------");
                System.out.println("Gelen mesaj: " + recievedMessage);
                String[] recievedSplitted = recievedMessage.split(" ");
                String typeOfMessage = recievedSplitted[0];

                if (typeOfMessage.equals("FirstTime")) {
                    
                    System.out.println("First Time algılandı...");
                    this.name = recievedSplitted[1];
                    
                    for (ServerClient client : server.clients) {
                        recievedMessage += " " + client.name;
                        System.out.println("Clientler.. " + client.name);
                    }
                    
                    String sendingMessage = " " + recievedMessage;
                    System.out. println("Gönderilen Mesaj: " + sendingMessage);
                    System.out.println("---------------------------");
                    this.server.sendBroadcast(sendingMessage);

                } else if (typeOfMessage.equals("Name")) {
                    
                } else if (typeOfMessage.equals("Disconnect")) {
                    
                    System.out.println("Disconnect mesajı algılandı...");
                    String sendingMessage = " " + recievedMessage;
                    System.out.println("Gönderilen Mesag: " + sendingMessage);
                    this.server.sendBroadcast(sendingMessage);
                    this.StopClient();
                    
                } else if (typeOfMessage.equals("Text")) {
                    
                    System.out.println("Text algılandı...");
                    System.out.println("Broadcast mesaj yollanıyor...");
                    String broadcastMessage = " " + recievedMessage;
                    System.out.println("Yollanan mesaj: " + broadcastMessage);
                    System.out.println("-----------------------------");
                    this.server.sendBroadcast(broadcastMessage);
                
                } else if (typeOfMessage.equals("PrivateText")) {

                } else if (typeOfMessage.equals("GroupID")) {

                }

            }

        } catch (IOException ex) {
            this.StopClient();
            System.out.println("Client Stopped");
        }
    }
}

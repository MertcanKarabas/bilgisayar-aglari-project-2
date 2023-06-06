/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mertcankarabas
 */
public class Server extends Thread {

    int port;
    ServerSocket serverSocket;
    boolean isListening;
    ArrayList<ServerClient> clients;
    
    public Server (int port) throws IOException {
        this.port = port;
        this.serverSocket = new ServerSocket(port);
        this.clients = new ArrayList<>();
        System.out.println("Server bağlandı...");
    }
    
    public void addClient(ServerClient serverClient) {
        this.clients.add(serverClient);
    }
    
    public void removeClient(ServerClient serverClient) {
        this.clients.remove(serverClient);
    }
    
    public ServerClient getClientByIndex (int index) {
        return this.clients.get(index);
    } 
    
    public void sendBroadcast(String messages) throws IOException {
        byte[] bytes = messages.getBytes();
        for (ServerClient client : clients) {
            client.sendMessage(bytes);
        }
    }
    
    public void StartServer() {
        isListening = true;
        this.start();
    }
    
    public void StopServer() throws IOException {
        this.isListening = false;
        this.serverSocket.close();
    }
    
    @Override
    public void run() {
        while(!serverSocket.isClosed()) {
            try {
                System.out.println("Client Bekleniyor...");
                Socket clientSocket = serverSocket.accept();//Client bağlantısı bekleniyor..         
                ServerClient newClient = new ServerClient(clientSocket, this);       
                newClient.StartClient(newClient);
                this.addClient(newClient);
                System.out.println("Client bağlandı...");
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
}

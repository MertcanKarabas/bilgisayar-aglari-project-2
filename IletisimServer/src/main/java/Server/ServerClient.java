/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server;

import java.io.IOException;
import java.io.InputStream;
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

    public void sendMessage(byte[] bytes) {
        try {
            this.output.write(bytes);
        } catch (IOException ex) {
            Logger.getLogger(ServerClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void StartClient() {
        this.isListening = true;
        this.start();
    }
    
    public void StopClient() {
        try {
            this.isListening = false;
            this.input.close();
            this.output.close();
            this.socket.close();
            this.server.removeClient(this);
        } catch (IOException ex) {
            Logger.getLogger(ServerClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        try {
            while (this.isListening) {
                int byteSize = this.input.read();
                byte[] bytes = new byte[byteSize];
                this.input.read(bytes);
                String name = " "; //gelen mesaja boşluk atıyorum ki broadcast yaparken de bir tane silsin problem olmasın...
                name += new String(bytes, StandardCharsets.UTF_8);
                this.server.sendBroadcast(name);
                Server_Frame.lst_client_model.addElement(name);
            }
        } catch (IOException ex) {
            this.StopClient();
            System.out.println("Client Stopped");
        }
    }
}

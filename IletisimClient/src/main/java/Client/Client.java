/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Client;

import App.ClientMainFrame;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mertcankarabas
 */
public class Client extends Thread {

    int port;
    String ip;
    Socket socket;
    InputStream input;
    OutputStream output;
    boolean isListening;
    String name;
    ClientMainFrame mainFrame;
    
    public Client(String ip, int port, String name, ClientMainFrame mainFrame) {
        this.ip = ip;
        this.port = port;
        this.name = name;
        this.mainFrame = mainFrame;
        this.isListening = false;
    }

    public void sendMessage(byte[] bytes) throws IOException {

        this.output.write(bytes);

    }

    public void StartClient() throws IOException {
        this.socket = new Socket(ip, port);
        this.input = socket.getInputStream();
        this.output = socket.getOutputStream();
        this.sendMessage(this.name.getBytes());
        this.isListening = true;
        this.start();
    }

    public void StopClient() {
        try {
            this.isListening = false;
            this.input.close();
            this.output.close();
            this.socket.close();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        try {
            while (this.isListening) {
                int byteSize = this.input.read();
                byte[] bytes = new byte[byteSize];
                input.read(bytes);
                String name = new String(bytes, StandardCharsets.UTF_8);
                this.mainFrame.getTxta_messages().append(name);
                System.out.println(name);

            }
        } catch (IOException ex) {
            this.StopClient();
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

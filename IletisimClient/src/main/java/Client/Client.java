/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Client;

import App.ClientMainFrame;
import Forms.People;
import java.awt.Component;
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

/*
Mesajlar: FirstTime, Name, Disconnect, Text, PrivateText, GroupID
 */
public class Client extends Thread {

    int port;
    String ip;
    Socket socket;
    InputStream input;
    OutputStream output;
    boolean isListening, isFirstTime;
    String name;
    ClientMainFrame mainFrame;
    ArrayList<Client> clientList;
    ArrayList<String> clientNames;

    public Client(String ip, int port, String name, ClientMainFrame mainFrame) {
        this.ip = ip;
        this.port = port;
        this.name = name;
        this.mainFrame = mainFrame;
        this.isListening = false;
        this.clientList = new ArrayList<>();
        this.clientNames = new ArrayList<>();
    }

    public void sendMessage(byte[] message) throws IOException {
        this.output.write(message);

    }

    public void addClient(Client serverClient) {
        this.clientList.add(serverClient);
    }

    public void removeClient(Client serverClient) {
        this.clientList.remove(serverClient);
    }

    public Client getClientByIndex(int index) {
        return this.clientList.get(index);
    }

    public void StartClient() throws IOException {
        this.isFirstTime = true;
        this.socket = new Socket(ip, port);
        this.input = socket.getInputStream();
        this.output = socket.getOutputStream();
        System.out.println("----------------------------");
        System.out.println("Ben: " + this.name + " yeni açıldım... Mesaj yolluyorum..");
        String firstMessage = " FirstTime " + this.name + " " + this.socket.getPort();
        System.out.println("Yolladığım Mesaj: " + firstMessage);
        System.out.println("-----------------------------------");
        this.sendMessage(firstMessage.getBytes());
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

        int byteSize;
        try {
            while (this.isListening) {
                byteSize = this.input.read();
                byte bytes[] = new byte[byteSize]; // blocking
                this.input.read(bytes);

                String recievedMessage = new String(bytes, StandardCharsets.UTF_8);
                System.out.println("-------------------------");
                System.out.println("Serverdan gelen mesaj: " + recievedMessage);
                System.out.println("--------------------------");
                String[] recievedSplitted = recievedMessage.split(" ");
                String typeOfMessage = recievedSplitted[0];

                if (typeOfMessage.equals("FirstTime")) {
                    System.out.println("First Time mesajı algılandı...");
                    System.out.println("Mesaj içeriği: " + recievedMessage);

                    if (isFirstTime) { //burada ilk başladığında client dinleme durumuna geçip beklediği için bu değeri tutabiliyoruz. İlk işlem bittikten sonra bunu false'a çevirip bir daha girmemesini sağlıyoruz.
                        System.out.println("Bu client ilk defa bağlandı...");
                        clientNames.add(recievedSplitted[1]);
                        for (int i = 1; i < clientNames.size(); i++) {
                            System.out.println("Client");
                            mainFrame.showMessages(clientNames.get(i));
                        }
                    } else {
                        System.out.println("Bu client bağlıydı...");
                        mainFrame.showMessages(recievedSplitted[1]);
                    }
                } else if (typeOfMessage.equals("Name")) {

                } else if (typeOfMessage.equals("Disconnect")) {
                    
                    System.out.println("Disconnect mesajı algılandı...");
                    System.out.println("Kullanıcı çıkarılıyor..");
                    String disconnectedClientName = "";
                    
                    for (int i = 0; i < recievedSplitted[1].length(); i++) {
                        if (recievedSplitted[1].charAt(i) != ':') {
                            disconnectedClientName += recievedSplitted[1].charAt(i);
                        }
                    }
                    System.out.println("Çıkarılan kişinin ismi: " + disconnectedClientName);
                    
                    Component[] comp = mainFrame.getLayeredPane_lists().getComponents();
                    for (Component component : comp) {
                        People disconnected = (People) component;
                        System.out.println("Componentteki isim: " + disconnected.getName());
                        if(disconnected.getName().equals(disconnectedClientName)){
                            mainFrame.getLayeredPane_lists().remove(component);
                            mainFrame.refreshList();
                            break;
                        }
                    }
                    
                } else if (typeOfMessage.equals("Text")) {
                    
                    System.out.println("Text mesajı algılandı...");
                    System.out.println("ekrana basılıyor...");
                    System.out.println(recievedMessage);
                    for (int i = 1; i < recievedSplitted.length; i++) {
                        mainFrame.getTxta_messages().append(recievedSplitted[i] + " ");
                    }
                    mainFrame.getTxta_messages().append("\n");
                
                } else if (typeOfMessage.equals("PrivateText")) {

                } else if (typeOfMessage.equals("GroupID")) {

                }
                
                isFirstTime = false;

            }
        } catch (IOException ex) {
            this.StopClient();
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

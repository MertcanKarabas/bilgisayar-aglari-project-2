/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mertcankarabas
 */
public class ServerMain {
    public static void main(String[] args) {
        Server server;
        try {
            server = new Server(10000);
            server.StartServer();
        } catch (IOException ex) {
            Logger.getLogger(ServerMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

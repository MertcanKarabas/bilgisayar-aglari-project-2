/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

/**
 *
 * @author mertcankarabas
 */

public class Message implements java.io.Serializable {
    //mesaj tipleri enum 
    public static enum Message_Type {FirstTime, Name, Disconnect, Text, PrivateText, GroupID}
    //mesajın tipi
    public Message_Type type;
    //mesajın içeriği obje tipinde ki istenilen tip içerik yüklenebilsin
    public Object content;
    public Message(Message_Type t)
    {
        this.type=t;
    } 
    
}

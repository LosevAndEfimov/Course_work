package application;

import java.io.*;
import java.net.*;

public class Server {

  static BufferedReader in;
  static ServerSocket servers;
  static PrintWriter out;
  static Socket fromclient;

  public Server(){
    System.out.println("Welcome to Server side");
    try {
      servers = new ServerSocket(3333);
    } catch (IOException e) {
      System.out.println("Couldn't listen to port 3333");
      System.exit(-1);
    }
    try {
      System.out.print("Waiting for a client...");
      fromclient = servers.accept();
      System.out.println("Client connected");
    } catch (IOException e) {
      System.out.println("Can't accept");
      System.exit(-1);
    }
    try {
      out = new PrintWriter(fromclient.getOutputStream(), true);
    } catch (IOException e1) {
      e1.printStackTrace();
    }
  }

  void sendCoordinates(int x, int y) {
    String output;
 
    output = Integer.toString(x) + " " + Integer.toString(y);
    out.println(output);
    
   /* out.close();
    try {
      fromclient.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    try {
      servers.close();
    } catch (IOException e) {
      e.printStackTrace();
    }*/
  }
  
  void sendEmptyString(){
    String output = "";
    out.println(output);
  }
}

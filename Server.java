package application;

import java.io.*;
import java.net.*;

import javafx.application.Platform;

public class Server implements Runnable {

  static BufferedReader in;
  static ServerSocket servers;
  static PrintWriter out;
  static Socket fromclient;

  public Server() {
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
      System.out.println("Client connected ");
    } catch (IOException e) {
      System.out.println("Can't accept");
      System.exit(-1);
    }
    try {
      in = new BufferedReader(new InputStreamReader(fromclient.getInputStream()));
    } catch (IOException e) {
      e.printStackTrace();
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
  }

  void sendKillCreep(int currentSpawnIndex, int currentEnemyIndex) {
    String output;
    output =
        "kill " + Integer.toString(currentSpawnIndex) + " " + Integer.toString(currentEnemyIndex);
    out.println(output);
  }

  void sendReadyString() {
    String output = "serverIsReady";
    out.println(output);
  }

  void recieve() {
    String Body = null;
    String ArrayBody[] = new String[3];
    try {
      Body = in.readLine();
      if (Body.isEmpty())
        return;
      System.out.println(Body);
    } catch (IOException e) {
      e.printStackTrace();
    }
    if (!Body.isEmpty()) {
      if (Body.equals("clientIsReady")) {
        Main.menu.clientIsReady = true;
        return;
      }

      ArrayBody = Body.split(" ");

      if (ArrayBody[0].equals("kill")) {
        int killSpawnIndex = Integer.parseInt(ArrayBody[1]);
        int killEnemyIndex = Integer.parseInt(ArrayBody[2]);
        Enemy temp = Main.gameRoot.Spawn[killSpawnIndex].enemies.get(killEnemyIndex);
        temp.setVisible(false);
        temp.Health = -10;
        Platform.runLater(()->Main.gameRoot.getChildren().remove(temp));
        temp.animation.stop();
      } else {
        int coordX = Integer.parseInt(ArrayBody[0]);
        int coordY = Integer.parseInt(ArrayBody[1]);
        Platform.runLater(() -> Main.gameRoot.Towers.add(new Tower(coordX, coordY, 150)));
      }
    }
  }

  @Override
  public void run() {
    recieve();
  }
}

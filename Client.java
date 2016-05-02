package application;

import java.io.*;
import java.net.*;

import javafx.application.Platform;

public class Client implements Runnable{

  static Socket fromServer;
  static BufferedReader in;
  static PrintWriter out;
  static Socket fromclient;

  public Client() {
    fromServer = null;
    try {
      fromServer = new Socket("localhost", 3333);
    } catch (UnknownHostException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    try {
      in = new BufferedReader(new InputStreamReader(fromServer.getInputStream()));
    } catch (IOException e) {
      e.printStackTrace();
    }
    try {
      out = new PrintWriter(fromServer.getOutputStream(), true);
    } catch (IOException e1) {
      e1.printStackTrace();
    }
  }

  void sendReadyString() {
    String output = "clientIsReady";
    out.println(output);
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
      if (Body.equals("serverIsReady")) {
        Main.menu.serverIsReady = true;
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
        Platform.runLater(()->Main.gameRoot.Towers.add(new Tower(coordX, coordY, 150)));
      }
    }
  }

  @Override
  public void run() {
    recieve();
  }
}

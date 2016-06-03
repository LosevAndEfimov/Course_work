package com.gluonapplication;   

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;

import javax.net.ssl.HttpsURLConnection;

public class HttpURLConnectionExample {

    private final static String USER_AGENT = "Mozilla/5.0";

    // HTTP GET request
    static void sendIp() throws Exception {
        String address = null;
        try {
          address = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
          e.printStackTrace();
        }

        String url = "http://kuhnimog.by?cat=send_ip&ipAddress=" + address;
        
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
    }
    
    static String getIp() throws Exception{
      String url = "http://kuhnimog.by?cat=get_ip";
      
      URL obj = new URL(url);
      HttpURLConnection con = (HttpURLConnection) obj.openConnection();

      // optional default is GET
      con.setRequestMethod("GET");

      //add request header
      con.setRequestProperty("User-Agent", USER_AGENT);

      int responseCode = con.getResponseCode();
      
      BufferedReader in = new BufferedReader(
              new InputStreamReader(con.getInputStream()));
      String inputLine;
      StringBuffer response = new StringBuffer();

      while ((inputLine = in.readLine()) != null) {
          response.append(inputLine);
      }
      in.close();

      String result = response.toString();
      return result;
    }
    
    static void deleteIp() throws Exception {
      String address = null;
      try {
        address = InetAddress.getLocalHost().getHostAddress();
      } catch (UnknownHostException e) {
        e.printStackTrace();
      }

      String url = "http://kuhnimog.by?cat=delete_ip&ipAddress=" + address;
      
      URL obj = new URL(url);
      HttpURLConnection con = (HttpURLConnection) obj.openConnection();

      // optional default is GET
      con.setRequestMethod("GET");

      //add request header
      con.setRequestProperty("User-Agent", USER_AGENT);

      int responseCode = con.getResponseCode();

      BufferedReader in = new BufferedReader(
              new InputStreamReader(con.getInputStream()));
      String inputLine;
      StringBuffer response = new StringBuffer();

      while ((inputLine = in.readLine()) != null) {
          response.append(inputLine);
      }
      in.close();
  }
}

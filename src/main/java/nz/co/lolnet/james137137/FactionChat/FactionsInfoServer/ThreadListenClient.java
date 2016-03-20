/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.co.lolnet.james137137.FactionChat.FactionsInfoServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import nz.co.lolnet.james137137.FactionChat.FactionChat;

/**
 *
 * @author James
 */
public class ThreadListenClient implements Runnable {

    Thread t;
    Socket clientSocket;
    BufferedReader in;
    PrintWriter out;

    public ThreadListenClient(Socket clientSocket) {
        this.clientSocket = clientSocket;
        start();
    }

    private void start() {
        t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException ex) {
            Logger.getLogger(ThreadListenClient.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }

        String inputLine;
        try {
            while ((inputLine = in.readLine()) != null) {
                String output = "";
                if (inputLine.startsWith("getFactionName")) {
                    String[] split = inputLine.split("~~~~");
                    output = FactionChat.factionsAPI.getFactionName((String) split[1]);
                } else if (inputLine.startsWith("getFactionID")) {
                    String[] split = inputLine.split("~~~~");
                    output = FactionChat.factionsAPI.getFactionID((String) split[1]);
                } else if (inputLine.startsWith("getRelationship")) {
                    String[] split = inputLine.split("~~~~");
                    output = Integer.toString(FactionChat.factionsAPI.getRelationship((String) split[1],(String) split[2]).getValue());
                } else if (inputLine.startsWith("isFactionless")) {
                    String[] split = inputLine.split("~~~~");
                    output = Boolean.toString(FactionChat.factionsAPI.isFactionless((String) split[1]));
                } else if (inputLine.startsWith("getPlayerTitle")) {
                    String[] split = inputLine.split("~~~~");
                    output = FactionChat.factionsAPI.getPlayerTitle((String) split[1]);
                } else if (inputLine.startsWith("getPlayerRank")) {
                    String[] split = inputLine.split("~~~~");
                    output = Integer.toString(FactionChat.factionsAPI.getPlayerRank((String) split[1]).getValue());
                } else if (inputLine.startsWith("ping")) {
                    output = "pong";
                }
                out.println(output);

            }
        } catch (IOException ex) {
            Logger.getLogger(ThreadListenClient.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(ThreadListenClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

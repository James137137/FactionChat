/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.co.lolnet.james137137.FactionChat.FactionsInfoServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import nz.co.lolnet.james137137.FactionChat.FactionChat;

/**
 *
 * @author James
 */
public class FactionInfoServer {
    private int PORT;
    private static ServerSocket serverSocket;

    public FactionInfoServer(int PORT) {
        this.PORT = PORT;
         try {
            serverSocket = new ServerSocket(PORT);
            FactionChat.plugin.getLogger().info("listening on port: " + PORT+ " for server messages.");
            new ThreadListenForClients();
        } catch (IOException e) {
            System.err.println("Could not listen on port: " + PORT);
            return;
        }
    }
    
    private static class ThreadListenForClients implements Runnable {

        Thread t;

        public ThreadListenForClients() {
            start();
        }

        private void start() {
            t = new Thread(this);
            t.start();
        }

        @Override
        public void run() {
            while (FactionChat.plugin != null) {
                Socket clientSocket = null;

                try {
                    clientSocket = serverSocket.accept();
                    new ThreadListenClient(clientSocket);
                } catch (IOException e) {
                }
            }
        }

    }
    
}

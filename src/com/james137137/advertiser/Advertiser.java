/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.james137137.advertiser;

/**
 *
 * @author James
 */
import com.james137137.FactionChat.FactionChat;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.MessageDigest;
import java.util.Calendar;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class Advertiser {

    boolean bypassAds = false;
    static final Logger log = Logger.getLogger("Minecraft");
    static URL url;
    static Scanner myScanner;
    static BufferedReader reader;
    FactionChat plugin;
    Calendar mytime = Calendar.getInstance();
    public double lastRun = (double) mytime.get(Calendar.HOUR_OF_DAY) + (double) mytime.get(Calendar.MINUTE) / 60.0;
    int autoRunTimeMinutes = 10;

    public Advertiser(FactionChat aThis) {
        plugin = aThis;
        bypassAds = hasCorrectPassword(aThis);
        if (bypassAds) {
            log.info("[FactionChat] Advertiser is disabled");
            return;
        }
        try {
            url = new URL("http://lolnet.co.nz/factionchatads.txt");
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            log.info("[FactionChat] Advertiser is enabled");
            
        } catch (Exception e) {
            log.severe(e.toString());
        }
        runAdvertiser();
        
    }

    public void StopAdvertiser() {
        Bukkit.getScheduler().cancelTasks(Bukkit.getServer().getPluginManager().getPlugin("FactionChat"));
    }

    public void runAdvertiser() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            double now;

            @Override
            public void run() {
                mytime = Calendar.getInstance();
                now = (double) mytime.get(Calendar.HOUR_OF_DAY) + (double) mytime.get(Calendar.MINUTE) / 60.0;
                if (Math.abs(now - lastRun + 0.001) >= (double) autoRunTimeMinutes / 60) {
                    
                    String message1 = "[FactionChat]";
                    String message2 = "dev.bukkit.org/server-mods/factionchat/";
                    try {
                        message1 = reader.readLine();
                        message2 = reader.readLine();
                        if ((message1 == null) || (message2 == null))
                        {
                            reader.close();
                            reader = new BufferedReader(new InputStreamReader(url.openStream()));
                            message1 = reader.readLine();
                            message2 = reader.readLine();
                        }
                        message1 = message1.replaceAll("&", "§");
                        message2 = message2.replaceAll("&", "§");
                    } catch (IOException ex) {
                        Logger.getLogger(Advertiser.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    
                    
                    plugin.getServer().broadcastMessage(ChatColor.GOLD + "------------------=================------------------");
                    plugin.getServer().broadcastMessage(message1);
                    plugin.getServer().broadcastMessage(message2);
                    plugin.getServer().broadcastMessage(ChatColor.GOLD + "------------------=================------------------");
                    lastRun = (double) mytime.get(Calendar.HOUR_OF_DAY) + (double) mytime.get(Calendar.MINUTE) / 60.0;
                }

            }
        }, 20L, 20 * 30);

    }

    private boolean hasCorrectPassword(FactionChat aThis) {

        String password = aThis.getConfig().getString("AdvertiserBypass");
        if (sha256(password).equals("670a69836666cd39b0d46d0367685c6eba7c102b71cf163c6cd0fbb1a381493e")) {
            return true;
        }
        return false;
    }

    public static String sha256(String base) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public boolean check() {
        return true;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.co.lolnet.james137137.FactionChat;

/**
 *
 * @author James
 */
public class FactionChatMessage {

    String format;
    String message;
    boolean allowCostomColour;

    String playerPrefix;
    String playerName;
    String playerSuffix;
    String playerFactionTitle;
    String playerFactionRank;
    String FacitonName;
    String otherFactionName;

    public FactionChatMessage(String format, String message, boolean allowCostomColour, String playerPrefix, String playerName, String playerSuffix, String playerFactionTitle, String playerFactionRank, String FacitonName, String otherFactionName) {
        this.format = checkFormat(format);
        this.message = checkFormat(message);
        this.allowCostomColour = allowCostomColour;
        this.playerPrefix = checkFormat(playerPrefix);
        this.playerName = checkFormat(playerName);
        this.playerSuffix = checkFormat(playerSuffix);
        this.playerFactionTitle = checkFormat(playerFactionTitle);
        this.playerFactionRank = checkFormat(playerFactionRank);
        this.FacitonName = checkFormat(FacitonName);
        this.otherFactionName = checkFormat(otherFactionName);
    }

    public FactionChatMessage(String format, String message, boolean allowCostomColour) {
        this.format = checkFormat(format);
        this.message = checkFormat(message);
        this.allowCostomColour = allowCostomColour;
        this.playerPrefix = null;
        this.playerName = null;
        this.playerSuffix = null;
        this.playerFactionTitle = null;
        this.playerFactionRank = null;
        this.FacitonName = null;
        this.otherFactionName = null;
    }

    @Override
    public String toString() {
        String output;
        if (allowCostomColour) {
            message = message.replace("/&", "/and");
            message = message.replace("&", "" + (char) 167);
            message = message.replace("/and", "&");
        }

        output = message;
        if (format != null) {
            format = format.replace("/&", "/and");
            format = format.replace("&", "" + (char) 167);
            format = format.replace("/and", "&");
            if (playerName != null) {
                format = format.replace("PLAYER", playerName);
            }

            if (playerPrefix != null) {
                format = format.replace("PREFIX", playerPrefix);
            }
            if (playerSuffix != null) {
                format = format.replace("SUFFIX", playerSuffix);
            }
            if (playerFactionTitle != null) {
                format = format.replace("TITLE", playerFactionTitle);
            }
            if (playerFactionRank != null) {
                format = format.replace("FACTIONRANK", playerFactionRank);
            }
            if (FacitonName != null) {
                format = format.replace("FACTION", FacitonName);
            }
            if (otherFactionName != null) {
                format = format.replace("OTHERFACTION", otherFactionName);
            }

            format = format.replace("  ", " "); //removing double spaces
            format = format.replace("MESSAGE", message);

            output = format;
        }

        return output;
    }

    private static String checkFormat(String y) {
        return y;
    }

}

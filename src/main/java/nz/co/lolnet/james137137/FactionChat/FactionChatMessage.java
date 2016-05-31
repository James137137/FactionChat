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
        this.format = format;
        this.message = message;
        this.allowCostomColour = allowCostomColour;
        this.playerPrefix = playerPrefix;
        this.playerName = playerName;
        this.playerSuffix = playerSuffix;
        this.playerFactionTitle = playerFactionTitle;
        this.playerFactionRank = playerFactionRank;
        this.FacitonName = FacitonName;
        this.otherFactionName = otherFactionName;
    }

    public FactionChatMessage(String format, String message, boolean allowCostomColour) {
        this.format = format;
        this.message = message;
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
            message = message.replaceAll("/&", "/and");
            message = message.replaceAll("&", "" + (char) 167);
            message = message.replaceAll("/and", "&");
        }

        output = message;
        if (format != null) {
            if (playerName != null) {
                format = format.replaceAll("%PLAYER%", playerName);
            }

            if (playerPrefix != null) {
                format = format.replaceAll("%PREFIX%", playerPrefix);
            }
            if (playerSuffix != null) {
                format = format.replaceAll("%SUFFIX%", playerSuffix);
            }
            if (playerFactionTitle != null) {
                format = format.replaceAll("%TITLE%", playerFactionTitle);
            }
            if (playerFactionRank != null) {
                format = format.replaceAll("%FACTIONRANK%", playerFactionRank);
            }
            if (FacitonName != null) {
                format = format.replaceAll("%FACTION%", FacitonName);
            }
            if (otherFactionName != null) {
                format = format.replaceAll("%OTHERFACTION%", otherFactionName);
            }
            format = format.replaceAll("%MESSAGE%", message);

            output = format;
        }

        return output;
    }

}

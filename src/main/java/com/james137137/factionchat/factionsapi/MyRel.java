/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.james137137.factionchat.factionsapi;

import com.james137137.factionchat.FactionChat;

/**
 *
 * @author James
 */
public enum MyRel {

    LEADER(70, true, "your faction leader", "your faction leader", "", ""),
    OFFICER(60, true, "an officer in your faction", "officers in your faction", "", ""),
    MEMBER(50, true, "a member in your faction", "members in your faction", "your faction", "your factions"),
    RECRUIT(45, true, "a recruit in your faction", "recruits in your faction", "", ""),
    ALLY(40, true, "an ally", "allies", "an allied faction", "allied factions"),
    TRUCE(30, true, "someone in truce with you", "those in truce with you", "a faction in truce", "factions in truce"),
    NEUTRAL(20, false, "someone neutral to you", "those neutral to you", "a neutral faction", "neutral factions"),
    ENEMY(10, false, "an enemy", "enemies", "an enemy faction", "enemy factions"), // END OF LIST
    ;

    private MyRel(final int value, final boolean friend, final String descPlayerOne, final String descPlayerMany, final String descFactionOne, final String descFactionMany) {
        this.value = value;
        this.friend = friend;
        this.descPlayerOne = descPlayerOne;
        this.descPlayerMany = descPlayerMany;
        this.descFactionOne = descFactionOne;
        this.descFactionMany = descFactionMany;
    }

        // -------------------------------------------- //
    // CONSTRUCT
    // -------------------------------------------- //
    private final int value;

    public int getValue() {
        return this.value;
    }

    // Used for friendly fire.
    private final boolean friend;

    public boolean isFriend() {
        return this.friend;
    }

    private final String descPlayerOne;

    public String getDescPlayerOne() {
        return this.descPlayerOne;
    }

    private final String descPlayerMany;

    public String getDescPlayerMany() {
        return this.descPlayerMany;
    }

    private final String descFactionOne;

    public String getDescFactionOne() {
        return this.descFactionOne;
    }

    private final String descFactionMany;

    public String getDescFactionMany() {
        return this.descFactionMany;
    }

    public boolean isAtLeast(MyRel rel) {
        return this.value >= rel.value;
    }

    public boolean isAtMost(MyRel rel) {
        return this.value <= rel.value;
    }

    public boolean isLessThan(MyRel rel) {
        return this.value < rel.value;
    }

    public boolean isMoreThan(MyRel rel) {
        return this.value > rel.value;
    }

    @Override
    public String toString() {
        if (this.equals(MyRel.LEADER)) {
            return FactionChat.LeaderRank;
        } else if (this.equals(MyRel.OFFICER)) {
            return FactionChat.OfficerRank;
        } else if (this.equals(MyRel.MEMBER)) {
            return FactionChat.MemberRank;
        } else if (this.equals(MyRel.RECRUIT)) {
            return FactionChat.RecruitRank;
        } else {
            return "";
        }
    }

}

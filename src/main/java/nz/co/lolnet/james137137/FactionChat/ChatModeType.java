package nz.co.lolnet.james137137.FactionChat;

public enum ChatModeType {
    PUBLIC,
    FACTION,
    ALLY,
    TRUCE,
    ALLY_TRUCE,
    ENEMY,
    LEADER,
    OFFICER,
    VIP,
    USERASSISTANT,
    JRMOD,
    MOD,
    SRMOD,
    JRADMIN,
    ADMIN;

    public static ChatModeType fromInput(String input) {
        if (input == null) {
            return null;
        }
        String key = input.trim().toUpperCase();
        switch (key) {
            case "P":
            case "PUBLIC":
                return PUBLIC;
            case "F":
            case "FACTION":
                return FACTION;
            case "A":
            case "ALLY":
                return ALLY;
            case "T":
            case "TRUCE":
                return TRUCE;
            case "AT":
            case "ALLYTRUCE":
            case "ALLY_TRUCE":
            case "ALLY&TRUCE":
                return ALLY_TRUCE;
            case "E":
            case "ENEMY":
                return ENEMY;
            case "L":
            case "LEADER":
                return LEADER;
            case "O":
            case "OFFICER":
                return OFFICER;
            case "V":
            case "VIP":
                return VIP;
            case "UA":
            case "USERASSISTANT":
            case "USER_ASSISTANT":
                return USERASSISTANT;
            case "JRMOD":
                return JRMOD;
            case "MOD":
                return MOD;
            case "SRMOD":
                return SRMOD;
            case "JRADMIN":
                return JRADMIN;
            case "ADMIN":
                return ADMIN;
            default:
                key = key.replace("&", "_");
                try {
                    return ChatModeType.valueOf(key);
                } catch (IllegalArgumentException e) {
                    return null;
                }
        }
    }

    @Override
    public String toString() {
        switch (this) {
            case ALLY_TRUCE:
                return "ALLY&TRUCE";
            case USERASSISTANT:
                return "UserAssistant";
            case JRMOD:
                return "JrMOD";
            case SRMOD:
                return "SrMOD";
            case JRADMIN:
                return "JrADMIN";
            default:
                return name();
        }
    }
}

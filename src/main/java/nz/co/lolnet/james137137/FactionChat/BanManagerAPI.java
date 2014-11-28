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
public class BanManagerAPI {

    public static boolean isMuted(String PlayerName) {
        try {
            boolean result = me.confuser.banmanager.BmAPI.isMuted(PlayerName);
            return result;
        } catch (Exception e) {
            return false;
        }

    }
}

package xyz.lightsky.squarelottery.lottery.entities.manager;

import xyz.lightsky.squarelottery.SquareLottery;
import xyz.lightsky.squarelottery.lottery.entities.ache.OneArmBanditAche;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class OneArmBanditManager {

    /* identifier -> ache */
    public static final Map<String, OneArmBanditAche> aches = new HashMap<>();

    public static void init() {
        File path = new File(SquareLottery.getInstance().getDataFolder() + "/OneArmBandit/");
        if(path.mkdirs()) {
            SquareLottery.getInstance().getLogger().info("first init");
        }

    }

    public static void register(OneArmBanditAche ache) {
        aches.putIfAbsent(ache.getIdentifier(), ache);
    }



}

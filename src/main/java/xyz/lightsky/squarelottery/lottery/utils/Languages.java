package xyz.lightsky.squarelottery.lottery.utils;


import cn.nukkit.utils.Config;
import xyz.lightsky.squarelottery.SquareLottery;

import java.io.File;

public class Languages {

    private static Config lang;

    public static void init() {
        if(new File(SquareLottery.getInstance().getDataFolder(), "/Languages/").mkdirs()) {
            SquareLottery.getInstance().getLogger().info("first init");
        }
        Config config = SquareLottery.getInstance().getConfig();
        String langKey = config.getString("language");
        File langFile = null;
        if((langFile = new File(SquareLottery.getInstance().getDataFolder() + "/Languages/" + langKey + ".yml")).exists()) {
            lang = new Config(langFile);
        }else {
            SquareLottery.getInstance().getLogger().error("can find "+langKey+".yml, please confirm its existence");
        }
    }

    public static String translate(String key) {
        return lang.getString(key);
    }

}

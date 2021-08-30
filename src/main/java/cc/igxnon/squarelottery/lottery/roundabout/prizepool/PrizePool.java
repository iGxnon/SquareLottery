package cc.igxnon.squarelottery.lottery.roundabout.prizepool;

import cc.igxnon.squarelottery.SquareLottery;
import cn.nukkit.utils.Config;

/**
 * @author iGxnon
 * @date 2021/8/30
 */
public class PrizePool {

    private static Config config;

    private static double defaultRedRate = 0.25D;
    private static double defaultBlueRate = 0.25D;
    private static double defaultYellowRate = 0.25D;
    private static double defaultOrangeRate = 0.25D;


    public static void init() {
        SquareLottery.getInstance().saveResource("roundabout/config.yml");
        config = new Config(SquareLottery.getInstance().getDataFolder() + "/roundabout/prizepool.yml", Config.YAML);
        defaultRedRate = new Config(SquareLottery.getInstance().getDataFolder() + "/roundabout/config.yml", Config.YAML)
                .getDouble("defaultRedRate");
        defaultBlueRate = new Config(SquareLottery.getInstance().getDataFolder() + "/roundabout/config.yml", Config.YAML)
                .getDouble("defaultBlueRate");
        defaultYellowRate = new Config(SquareLottery.getInstance().getDataFolder() + "/roundabout/config.yml", Config.YAML)
                .getDouble("defaultYellowRate");
        defaultOrangeRate = new Config(SquareLottery.getInstance().getDataFolder() + "/roundabout/config.yml", Config.YAML)
                .getDouble("defaultOrangeRate");
    }

}

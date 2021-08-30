package cc.igxnon.squarelottery;

import cc.igxnon.squarelottery.languages.Languages;
import cc.igxnon.squarelottery.lottery.roundabout.prizepool.PrizePool;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.plugin.PluginBase;

/**
 * @author iGxnon
 * @date 2021/8/29
 */
public class SquareLottery extends PluginBase {

    private static SquareLottery instance;

    public static SquareLottery getInstance() {
        return instance;
    }

    @Override
    public void onLoad() {
        instance = this;
        saveDefaultConfig();
    }

    @Override
    public void onEnable() {
        Languages.init();
        PrizePool.init();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

}

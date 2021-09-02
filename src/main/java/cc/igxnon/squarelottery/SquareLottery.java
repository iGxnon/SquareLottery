package cc.igxnon.squarelottery;

import cc.igxnon.squarelottery.animations.AnimationUtils;
import cc.igxnon.squarelottery.commands.CommandBuilder;
import cc.igxnon.squarelottery.languages.Languages;
import cc.igxnon.squarelottery.lottery.roundabout.prizepool.PrizePool;
import cc.igxnon.squarelottery.utils.ClickEntityUtils;
import cn.nukkit.Server;
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
        CommandBuilder.init();
        if(AnimationUtils.buildResourcePack()) {
            getLogger().warning("Building resource packet...");
        }else {
            getLogger().warning("Resource packet built.");
        }
        if (!Server.getInstance().getForceResources()) {
            getLogger().warning("Detected that 'force-resources' is not true, we suggest you to toggle it to true");
        }
        getServer().getPluginManager().registerEvents(new ClickEntityUtils(), this);
    }

    @Override
    public void onDisable() {
        PrizePool.save();
    }

}

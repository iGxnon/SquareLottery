package xyz.lightsky.squarelottery;

import cn.nukkit.plugin.PluginBase;
import xyz.lightsky.squarelottery.lottery.entities.manager.OneArmBanditManager;
import xyz.lightsky.squarelottery.lottery.utils.Languages;

public class SquareLottery extends PluginBase {

    private static SquareLottery instance;

    public static SquareLottery getInstance() {
        return instance;
    }

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        Languages.init();
        OneArmBanditManager.init();
    }

    @Override
    public void onDisable() {

    }
}

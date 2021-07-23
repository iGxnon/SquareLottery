package xyz.lightsky.squarelottery;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.EntityHuman;
import cn.nukkit.entity.data.Skin;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.scheduler.Task;
import xyz.lightsky.squarelottery.lottery.entities.manager.OneArmBanditManager;
import xyz.lightsky.squarelottery.lottery.network.protocol.AnimateLotteryPacket;
import xyz.lightsky.squarelottery.lottery.utils.DataPool;
import xyz.lightsky.squarelottery.lottery.utils.Languages;

import java.util.Arrays;
import java.util.Collections;


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
        saveResource("Languages/chs.yml");
        saveDefaultConfig();
        DataPool.init();
        Languages.init();
        OneArmBanditManager.init();
    }

    @Override
    public void onDisable() {

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            /*test*/
            CompoundTag tag = Entity.getDefaultNBT(((Player) sender).getPosition());
            tag.putCompound("Skin", new CompoundTag());
            EntityHuman test = new EntityHuman(((Player) sender).chunk, tag);
            Skin skin = new Skin();
            skin.setSkinId("OneArmBandit");
            skin.setSkinData(DataPool.ONE_ARM_BANDIT_IMAG);
            skin.setGeometryName("geometry.onearmbandit");
            skin.setGeometryData(DataPool.ONE_ARM_BANDIT_JSON);
            test.setSkin(skin);
            test.setScale(1.5F);
            test.spawnToAll();
            Server.getInstance().getScheduler().scheduleRepeatingTask(new Task() {
                @Override
                public void onRun(int i) {
                    AnimateLotteryPacket pk = new AnimateLotteryPacket();
                    pk.setAnimation(DataPool.ONE_ARM_BANDIT_SETUP);
                    pk.setEntityRuntimeIds(Collections.singletonList(test.getId()));
                    ((Player) sender).dataPacket(pk);
                }
            }, 110);
        }
        return true;
    }
}

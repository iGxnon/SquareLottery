package xyz.lightsky.squarelottery.lottery.entities;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.entity.EntityHuman;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.scheduler.Task;
import xyz.lightsky.squarelottery.lottery.entities.ache.OneArmBanditAche;
import xyz.lightsky.squarelottery.lottery.entities.manager.OneArmBanditManager;
import xyz.lightsky.squarelottery.lottery.network.protocol.AnimateLotteryPacket;
import xyz.lightsky.squarelottery.lottery.utils.DataPool;

import java.net.ServerSocket;
import java.util.Collections;

public class OneArmBandit extends EntityHuman {

    private String identifier;

    private String nameTag;

    private double redRate;

    private double blueRate;

    private double greenRate;

    private double purpleRate;

    private Player onLottery = null;

    private OneArmBanditAche ache;

    public OneArmBandit(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
        identifier = nbt.getString("Identifier");
        ache = OneArmBanditManager.aches.get(identifier);
        setScale(ache.getScale());
        nameTag = ache.getNameTag();
        redRate = ache.getRedRate();
        purpleRate = ache.getPurpleRate();
        blueRate = ache.getBlueRate();
        greenRate = ache.getGreenRate();
    }

    @Override
    public boolean onUpdate(int currentTick) {
        if(onLottery == null) {
            // delay for 2 seconds
            if(currentTick % 160 == 0) {
                playAnimation(DataPool.ONE_ARM_BANDIT_SETUP);
                Server.getInstance().getScheduler().scheduleDelayedTask(new Task() {
                    @Override
                    public void onRun(int i) {
                        playAnimation(DataPool.ONE_ARM_BANDIT_GIFT);
                    }
                }, 120);
            }
        }
        return super.onUpdate(currentTick);
    }

    public void playAnimation(String animation) {
        AnimateLotteryPacket pk = new AnimateLotteryPacket();
        pk.setAnimation(animation);
        pk.setEntityRuntimeIds(Collections.singletonList(getId()));
        getServer().getOnlinePlayers().values().forEach(player -> player.dataPacket(pk));
    }

    public void playAnimation(String animation, float belendoutTime) {
        AnimateLotteryPacket pk = new AnimateLotteryPacket();
        pk.setAnimation(animation);
        pk.setEntityRuntimeIds(Collections.singletonList(getId()));
        pk.setBlendOutTime(belendoutTime);
        getServer().getOnlinePlayers().values().forEach(player -> player.dataPacket(pk));
    }

}

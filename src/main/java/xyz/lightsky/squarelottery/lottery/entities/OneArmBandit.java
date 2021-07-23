package xyz.lightsky.squarelottery.lottery.entities;

import cn.nukkit.Player;
import cn.nukkit.entity.EntityHuman;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.utils.Config;
import lombok.Getter;
import lombok.Setter;
import xyz.lightsky.squarelottery.lottery.entities.ache.OneArmBanditAche;
import xyz.lightsky.squarelottery.lottery.entities.manager.OneArmBanditManager;
import xyz.lightsky.squarelottery.lottery.utils.DataPool;
import xyz.lightsky.squarelottery.lottery.network.protocol.AnimateLotteryPacket;

import java.util.Collections;

@Getter
@Setter
public class OneArmBandit extends EntityHuman {

    private String identifier;

    private Config config;

    private String nameTag;

    private float redRate;

    private float blueRate;

    private float greenRate;

    private float purpleRate;

    private Player onLottery = null;

    private OneArmBanditAche ache;

    public OneArmBandit(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
        setNameTagVisible();
        setNameTagAlwaysVisible(true);
    }

    @Override
    protected void initEntity() {
        super.initEntity();
        identifier = namedTag.getString("Identifier");
        ache = OneArmBanditManager.aches.get(identifier);
        config = ache.getConfig();
        nameTag = ache.getNameTag();
        redRate = ache.getRedRate();
        purpleRate = ache.getPurpleRate();
        blueRate = ache.getBlueRate();
        greenRate = ache.getGreenRate();
    }

    @Override
    public boolean attack(float damage) {
        return super.attack(damage);
    }

    public void lottery(Player player) {

    }

    @Override
    public boolean onUpdate(int currentTick) {
        if(onLottery == null) {
            // delay for 3 seconds
            if(currentTick % 60 == 0) {
                playAnimation(DataPool.ONE_ARM_BANDIT_SETUP);
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

}

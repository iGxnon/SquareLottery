package cc.igxnon.squarelottery.lottery;

import cn.nukkit.Player;
import cn.nukkit.entity.EntityHuman;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;

/**
 * @author iGxnon
 * @date 2021/8/30
 */
public abstract class BaseLotteryEntity extends EntityHuman {

    public BaseLotteryEntity(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    public abstract void onClick(Player player);

    public abstract void save();

    public enum Type {
        ROUNDABOUT;

        public static Type[] all() {
            return new Type[]{Type.ROUNDABOUT};
        }
    }
}

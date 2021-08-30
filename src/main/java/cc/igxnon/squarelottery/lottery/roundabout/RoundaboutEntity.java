package cc.igxnon.squarelottery.lottery.roundabout;

import cc.igxnon.squarelottery.lottery.BaseLotteryEntity;
import cn.nukkit.level.Position;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;

/**
 * @author iGxnon
 * @date 2021/8/30
 */
public class RoundaboutEntity extends BaseLotteryEntity {

    public RoundaboutEntity(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    public BaseLotteryEntity createLottery(Position position) {
        return null;
    }
}

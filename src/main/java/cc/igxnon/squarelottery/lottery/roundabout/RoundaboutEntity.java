package cc.igxnon.squarelottery.lottery.roundabout;

import cc.igxnon.squarelottery.lottery.BaseLotteryEntity;
import cc.igxnon.squarelottery.lottery.roundabout.form.Menu;
import cc.igxnon.squarelottery.lottery.roundabout.prizepool.PrizePool;
import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.data.Skin;
import cn.nukkit.level.Position;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;

import java.util.Base64;
import java.util.Locale;
import java.util.UUID;

/**
 * @author iGxnon
 * @date 2021/8/30
 */
public class RoundaboutEntity extends BaseLotteryEntity {

    public static final String SKIN_BASE64 = "";
    public static final String JSON = "";
    public static final String IDENTIFIER = "";

    private static Menu MENU;

    public static final Skin SKIN = new Skin();

    static {
        SKIN.setSkinId(UUID.randomUUID().toString());
        SKIN.setSkinData(Base64.getDecoder().decode(SKIN_BASE64));
        SKIN.setGeometryName(IDENTIFIER);
        SKIN.setGeometryData(JSON);
        SKIN.setTrusted(true);
    }

    public RoundaboutEntity(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
        MENU = new Menu(this);
    }

    public void startLottery(Player player, PrizePool prizePool) {

    }

    public static RoundaboutEntity createLottery(Position position) {
        RoundaboutEntity lotteryEntity = new RoundaboutEntity(position.getChunk(), Entity.getDefaultNBT(position)
                .putCompound("Skin", new CompoundTag()));
        lotteryEntity.setSkin(SKIN);
        return lotteryEntity;
    }

    @Override
    public void onClick(Player player) {
        MENU.sendMenu(player);
    }

    public enum Color {
        RED,
        YELLOW,
        BLUE,
        ORANGE;

        public static Color of(String strKey) {
            strKey = strKey.toLowerCase(Locale.ROOT);
            switch (strKey) {
                case "red":
                    return Color.RED;
                case "yellow":
                    return Color.YELLOW;
                case "blue":
                    return Color.BLUE;
                case "orange":
                    return Color.ORANGE;
                default:
                    return null;
            }
        }
    }
}

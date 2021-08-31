package cc.igxnon.squarelottery.lottery.roundabout.prizepool;

import cc.igxnon.squarelottery.languages.Languages;
import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.item.Item;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author iGxnon
 * @date 2021/8/31
 */
@Getter
@Setter
@ToString
@SuppressWarnings("unused")
public class Prize {

    private String name;
    private String info;
    private boolean showRate;
    private double redRate;
    private double blueRate;
    private double yellowRate;
    private double orangeRate;
    private PrizeType prizeType;
    private String prizeValue;
    private int prizeCount;
    private int prizeSize;
    private boolean prizeArranged;
    private List<List<String>> prizeArrangements = new ArrayList<>();

    public boolean reduceCount() {
        if(prizeCount <= 0) {
            return false;
        }
        prizeCount --;
        save();
        return true;
    }

    public void save() {
        PrizePool.savePrize(this);
    }

    public void onPrize(Player player) {
        if(prizeCount == 0) {
            player.sendMessage(Languages.translate("%lottery_roundabout_prize_count_0%")
                    .replace("{prizeName}", getName()));
            return;
        }
        switch (prizeType) {
            case CMD:
                String cmd = prizeValue.replace("@p", player.getName());
                Server.getInstance().dispatchCommand(Server.getInstance().getConsoleSender(), cmd);
                player.sendMessage(Languages.translate("%lottery_roundabout_prizepool_onprize%")
                        .replace("{prizeName}", getName()));
                break;
            case ITEM:
                Item item = Item.get(Integer.parseInt(prizeValue.split(":")[0]),
                        Integer.parseInt(prizeValue.split(":")[0]),
                        Integer.parseInt(prizeValue.split(":")[0]));
                if(player.getInventory().canAddItem(item)) {
                    player.getInventory().addItem(item);
                }else {
                    player.sendMessage(Languages.translate("%lottery_roundabout_prizepool_onprize_item_cannotadd%")
                            .replace("{prizeName}", getName()));
                    PrizePool.waitPrizePlayers.put(player.getName().toLowerCase(Locale.ROOT), this);
                }
        }
        reduceCount();
    }

    public enum PrizeType {
        CMD,
        ITEM;

        public static PrizeType of(String strKey) {
            strKey = strKey.toLowerCase(Locale.ROOT);
            switch (strKey) {
                case "item":
                    return PrizeType.ITEM;
                case "cmd":
                default:
                    return PrizeType.CMD;
            }
        }
    }
}

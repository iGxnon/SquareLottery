package cc.igxnon.squarelottery.lottery.roundabout.prizepool;

import cc.igxnon.squarelottery.languages.Languages;
import cc.igxnon.squarelottery.lottery.roundabout.RoundaboutEntity;
import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.item.Item;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.*;
import java.util.stream.Collectors;

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
        //save();
        final Map<String, Object> config = (Map<String, Object>) PrizePool.prizePoolConfig.get(getName());
        config.put("prizeCount", prizeCount);
        PrizePool.prizePoolConfig.set(getName(), config);
        PrizePool.prizePoolConfig.save();
        return true;
    }

    @Deprecated
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
                    player.sendMessage(Languages.translate("%lottery_roundabout_prizepool_onprize%")
                            .replace("{prizeName}", getName()));
                }else {
                    player.sendMessage(Languages.translate("%lottery_roundabout_prizepool_onprize_item_cannotadd%")
                            .replace("{prizeName}", getName()));
                    PrizePool.waitPrizePlayers.put(player.getName().toLowerCase(Locale.ROOT), this);
                }
        }
        reduceCount();
    }

    public String getPrizeArrangementsList() {
        StringBuilder builder = new StringBuilder().append("\n");
        for(List<String> childList : prizeArrangements) {
            String child = childList.toString().toLowerCase(Locale.ROOT)
                    .replace("red", Languages.translate("%lottery_roundabout_color_red%") + "§r")
                    .replace("yellow", Languages.translate("%lottery_roundabout_color_yellow%") + "§r")
                    .replace("blue",Languages.translate("%lottery_roundabout_color_blue%") + "§r")
                    .replace("orange", Languages.translate("%lottery_roundabout_color_orange%") + "§r");
            builder.append("  - ").append(child).append("\n");
        }
        return builder.toString();
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

    public boolean hasArrangement(RoundaboutEntity.Color[] color) {
        if(color.length != prizeArrangements.get(0).size()) {
            return false;
        }
        for(List<String> arrangement : prizeArrangements) {
            arrangement = arrangement.stream().map(String::toLowerCase).collect(Collectors.toList());
            List<Boolean> accept = new ArrayList<>();
            if(prizeArranged) {
                for (int i = 0; i < prizeSize; i ++) {
                    accept.add(arrangement.get(i).equals(color[i].name().toLowerCase(Locale.ROOT)));
                }
            }else {
                for(RoundaboutEntity.Color colorInside : color) {
                    accept.add(arrangement.contains(colorInside.name().toLowerCase(Locale.ROOT)));
                }
            }
            if(!accept.contains(Boolean.FALSE)) {
                return true;
            }
        }
        return false;
    }

}

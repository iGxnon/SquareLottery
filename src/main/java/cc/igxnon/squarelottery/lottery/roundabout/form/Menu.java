package cc.igxnon.squarelottery.lottery.roundabout.form;

import cc.igxnon.squarelottery.languages.Languages;
import cc.igxnon.squarelottery.lottery.roundabout.RoundaboutEntity;
import cc.igxnon.squarelottery.lottery.roundabout.prizepool.Prize;
import cc.igxnon.squarelottery.lottery.roundabout.prizepool.PrizePool;
import cc.igxnon.squarelottery.utils.ClickEntityUtils;
import cc.igxnon.squarelottery.utils.StringUtils;
import cn.lanink.gamecore.form.windows.AdvancedFormWindowModal;
import cn.lanink.gamecore.form.windows.AdvancedFormWindowSimple;
import cn.nukkit.Player;

import java.util.Locale;

/**
 * @author iGxnon
 * @date 2021/8/31
 */
public class Menu {

    private RoundaboutEntity entity;

    public Menu(RoundaboutEntity baseLotteryEntity) {
        this.entity = baseLotteryEntity;
    }

    public void sendMenu(Player player) {
        if(ClickEntityUtils.clickList.containsKey(player)) return;
        AdvancedFormWindowSimple advancedFormWindowSimple = new AdvancedFormWindowSimple(Languages.translate("%lottery_roundabout_form_menu_title%"));
        PrizePool.allPrizePool.forEach((name, prizePool) -> {
            advancedFormWindowSimple.addButton(name, onClickPlayer -> {
                sendPrizePool(onClickPlayer, prizePool);
            });
        });
        if(PrizePool.waitPrizePlayers.containsKey(player.getName().toLowerCase(Locale.ROOT))) {
            advancedFormWindowSimple.addButton(Languages.translate("%lottery_roundabout_form_pickup%"), onClickPlayer -> {
                Prize prize = PrizePool.waitPrizePlayers.get(player.getName().toLowerCase(Locale.ROOT));
                prize.onPrize(player);
                PrizePool.waitPrizePlayers.remove(player.getName().toLowerCase(Locale.ROOT));
            });
        }
        player.showFormWindow(advancedFormWindowSimple);
    }

    public void sendPrizePool(Player player, PrizePool prizePool) {
        AdvancedFormWindowSimple advancedFormWindowSimple = new AdvancedFormWindowSimple(prizePool.name);
        advancedFormWindowSimple.setContent(Languages.translate("%lottery_roundabout_form_prizepool_content%"));
        prizePool.prizePool.forEach((name, prize) -> {
            advancedFormWindowSimple.addButton(name, onClickPlayer -> {
                sendPrize(onClickPlayer, prize, prizePool);
            });
        });
        advancedFormWindowSimple.addButton(Languages.translate("%lottery_roundabout_form_prizepool_start_button%"), onClickPlayer -> {
            entity.startLottery(onClickPlayer, prizePool);
        });
        advancedFormWindowSimple.addButton(Languages.translate("%lottery_roundabout_form_prizepool_back%"), this::sendMenu);
        player.showFormWindow(advancedFormWindowSimple);
    }

    public void sendPrize(Player player, Prize prize, PrizePool parentPool) {
        String raw = Languages.translate("%lottery_roundabout_form_prize_content%");
        String[] hideStrings = StringUtils.readContent(raw, "[", "]");
        for (String hide : hideStrings) {
            if (prize.isShowRate() && hide.contains("{redRate}")) {
                raw = raw.replace(hide, hide.substring(1, hide.length()-1));
            }else if(prize.getPrizeCount() != -1 && hide.contains("{prizeCount}")) {
                raw = raw.replace(hide, hide.substring(1, hide.length()-1));
            }else {
                raw = raw.replace(hide, "");
            }
        }
        String content = raw.replace("{n}", "\n")
                .replace("{info}", prize.getInfo())
                .replace("{redRate}", String.valueOf(prize.getRedRate()))
                .replace("{blueRate}", String.valueOf(prize.getBlueRate()))
                .replace("{yellowRate}", String.valueOf(prize.getYellowRate()))
                .replace("{orangeRate}", String.valueOf(prize.getOrangeRate()))
                .replace("{prizeCount}", String.valueOf(prize.getPrizeCount()))
                .replace("{prizeArranged}", prize.isPrizeArranged() ? Languages.translate("%lottery_roundabout_form_prize_prizearranged_true%") : Languages.translate("%lottery_roundabout_form_prize_prizearranged_false%"))
                .replace("{prizeArrangements}", prize.getPrizeArrangementsList());
        AdvancedFormWindowModal advancedFormWindowModal = new AdvancedFormWindowModal(
                prize.getName(),
                content,
                Languages.translate("%lottery_roundabout_form_prize_back%"),
                Languages.translate("%lottery_roundabout_form_prize_lottery_immediately%"));
        advancedFormWindowModal.onClickedTrue(onClickPlayer -> {
            sendPrizePool(onClickPlayer, parentPool);
        });

        advancedFormWindowModal.onClickedFalse(onClickPlayer -> {
            entity.startLottery(onClickPlayer, parentPool);
        });

        advancedFormWindowModal.onClosed(onClickPlayer -> {
            sendPrizePool(onClickPlayer, parentPool);
        });
        player.showFormWindow(advancedFormWindowModal);
    }

}

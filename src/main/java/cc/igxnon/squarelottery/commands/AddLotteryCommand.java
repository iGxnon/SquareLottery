package cc.igxnon.squarelottery.commands;

import cc.igxnon.squarelottery.lottery.roundabout.RoundaboutEntity;
import cc.igxnon.squarelottery.lottery.roundabout.form.Menu;
import cc.igxnon.squarelottery.lottery.roundabout.prizepool.PrizePool;
import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;

/**
 * @author iGxnon
 * @date 2021/8/29
 */
public class AddLotteryCommand extends Command  {

    public AddLotteryCommand() {
        super("addLottery");
        setPermission("addLottery.command.SquareLottery");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
//        if(commandSender instanceof Player) {
//            PrizePool testPool = PrizePool.createPrizePool("ABC");
//            testPool.addPrize(PrizePool.allPrize.get("prizeName1"));
//            testPool.addPrize(PrizePool.allPrize.get("prizeName2"));
//            Menu menu = new Menu();
//            menu.sendMenu((Player) commandSender);
//        }
        return true;
    }
}

package cc.igxnon.squarelottery.commands;

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
        return true;
    }
}

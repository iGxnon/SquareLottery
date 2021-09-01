package cc.igxnon.squarelottery.commands;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;

/**
 * @author iGxnon
 * @date 2021/8/30
 */
public class DelLotteryCommand extends Command {

    public DelLotteryCommand() {
        super("delLottery");
        setPermission("delLottery.command.SquareLottery");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if(commandSender instanceof Player) {
            commandSender.sendMessage(((Player) commandSender).getPosition().toString());
        }
        return true;
    }
}

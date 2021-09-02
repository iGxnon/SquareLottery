package cc.igxnon.squarelottery.commands;

import cc.igxnon.squarelottery.languages.Languages;
import cc.igxnon.squarelottery.lottery.BaseLotteryEntity;
import cc.igxnon.squarelottery.lottery.roundabout.RoundaboutEntity;
import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;

import java.util.Locale;

/**
 * @author iGxnon
 * @date 2021/8/29
 */
public class AddLotteryCommand extends Command  {

    public AddLotteryCommand() {
        super("addLottery");
        setPermission("squarelottery.command.op");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if(commandSender instanceof ConsoleCommandSender) {
            commandSender.sendMessage(Languages.translate("%lottery_command_console_add%"));
            return true;
        }
        if(strings.length != 1) {
            commandSender.sendMessage(Languages.translate("%lottery_command_add_help%")
                    .replace("{n}", "\n"));
            return true;
        }
        if(commandSender instanceof Player) {
            if(strings[0].toLowerCase(Locale.ROOT).equals(BaseLotteryEntity.Type.ROUNDABOUT.name().toLowerCase(Locale.ROOT))) {
                RoundaboutEntity roundaboutEntity = RoundaboutEntity.createLottery(((Player) commandSender).getLocation());
                roundaboutEntity.spawnToAll();
                roundaboutEntity.save();
            }
        }
        return true;
    }
}

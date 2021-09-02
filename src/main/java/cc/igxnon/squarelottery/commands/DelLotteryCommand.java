package cc.igxnon.squarelottery.commands;

import cc.igxnon.squarelottery.languages.Languages;
import cc.igxnon.squarelottery.lottery.BaseLotteryEntity;
import cc.igxnon.squarelottery.lottery.roundabout.RoundaboutEntity;
import cc.igxnon.squarelottery.lottery.roundabout.prizepool.PrizePool;
import cc.igxnon.squarelottery.utils.ClickEntityUtils;
import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;

import java.util.List;

/**
 * @author iGxnon
 * @date 2021/8/30
 */
public class DelLotteryCommand extends Command {

    public DelLotteryCommand() {
        super("delLottery");
        setPermission("squarelottery.command.op");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if(commandSender instanceof ConsoleCommandSender) {
            commandSender.sendMessage(Languages.translate("%lottery_command_console_add%"));
            return true;
        }
        if(commandSender instanceof Player) {
            commandSender.sendMessage(Languages.translate("%lottery_command_del_choose%"));
            ClickEntityUtils.clickList.put((Player) commandSender, lottery -> {
                if(lottery instanceof BaseLotteryEntity) {
                    if(lottery instanceof RoundaboutEntity) {
                        List<String> machines = PrizePool.config.getStringList("machines");
                        machines.removeIf(machineInfo -> (machineInfo.equals(((RoundaboutEntity) lottery).getInfo())));
                        PrizePool.config.set("machines", machines);
                        PrizePool.config.save();
                        lottery.close();
                    }
                }
            });
        }
        return true;
    }
}

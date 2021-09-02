package cc.igxnon.squarelottery.commands;

import cc.igxnon.squarelottery.lottery.BaseLotteryEntity;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;

/**
 * @author iGxnon
 * @date 2021/9/2
 */
public class LotteryTypesCommand extends Command {

    public LotteryTypesCommand() {
        super("lotteryTypes");
        setPermission("squarelottery.command.op");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        commandSender.sendMessage("======TypeList=======\n");
        for(BaseLotteryEntity.Type type : BaseLotteryEntity.Type.all()) {
            commandSender.sendMessage("  -" + type.name() + "\n");
        }
        commandSender.sendMessage("\b=====================");
        return true;
    }

}

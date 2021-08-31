package cc.igxnon.squarelottery.commands;

import cn.nukkit.Server;

/**
 * @author iGxnon
 * @date 2021/8/29
 */
public class CommandBuilder {

    public static final String PREFIX = "SquarePet";

    public static void init() {
        Server.getInstance().getCommandMap().register(PREFIX, new AddLotteryCommand());
        Server.getInstance().getCommandMap().register(PREFIX, new DelLotteryCommand());
    }

}

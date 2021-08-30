package cc.igxnon.squarelottery.commands;

import cn.nukkit.Server;

/**
 * @author iGxnon
 * @date 2021/8/29
 */
public class CommandBuilder {

    public static void init() {
        Server.getInstance().getCommandMap().register("SquarePet", new AddLotteryCommand());
    }

}

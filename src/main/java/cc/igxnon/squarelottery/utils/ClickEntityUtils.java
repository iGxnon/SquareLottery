package cc.igxnon.squarelottery.utils;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.entity.EntityDamageEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author iGxnon
 * @date 2021/9/2
 */
@SuppressWarnings("unused")
public class ClickEntityUtils implements Listener {

    public static Map<Player, Consumer<Entity>> clickList = new HashMap<>();

    /**
     * 事件需要被取消
     */
    @EventHandler(priority = EventPriority.MONITOR)
    public void onClick(EntityDamageEvent event) {
        if(!event.isCancelled()) return;
        if(!(event instanceof EntityDamageByEntityEvent)) return;
        if(!(((EntityDamageByEntityEvent) event).getDamager() instanceof Player)) return;
        if(!clickList.containsKey((Player) ((EntityDamageByEntityEvent) event).getDamager())) return;
        clickList.get((Player) ((EntityDamageByEntityEvent) event).getDamager()).accept(event.getEntity());
        clickList.remove((Player) ((EntityDamageByEntityEvent) event).getDamager());
    }

}

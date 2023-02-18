package de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator.events;

import de.tomstahlberg.advancedcobblegenerator.advancedcobblegenerator.Main;
import me.jet315.minions.events.MinerBlockBreakEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BlockBreakByMinion implements Listener {
    @EventHandler
    public void onMinerBlockBreakEvent(MinerBlockBreakEvent event){
        Main.cobblerBlocksBroken.put(event.getBlock().getLocation(), Bukkit.getPlayer(event.getMinion().getPlayerUUID()));
    }

}

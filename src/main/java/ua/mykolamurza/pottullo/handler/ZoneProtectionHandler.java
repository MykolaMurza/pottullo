package ua.mykolamurza.pottullo.handler;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import ua.mykolamurza.pottullo.Pottullo;
import ua.mykolamurza.pottullo.model.PrivatizationZone;

import static ua.mykolamurza.pottullo.handler.util.PrivatizationBlockUtil.isItPrivatizationBlock;

public class ZoneProtectionHandler implements Listener {
    private final Pottullo plugin;

    public ZoneProtectionHandler(Pottullo plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        Location blockLocation = block.getLocation();

        PrivatizationZone zone = plugin.getRegionConfig().getPrivatizationZoneAt(blockLocation);

        if (zone != null) {
            if (isItPrivatizationBlock(zone, block) && !zone.getOwner().equals(player.getName())) {
                event.setCancelled(true);
                player.sendMessage("Only private zone owner able to break privatization block.");
            }

            if (!(zone.getOwner().equals(player.getName())
                    || zone.getResidents().contains(player.getUniqueId().toString()))) {
                event.setCancelled(true);
                player.sendMessage("You don't have permission to break blocks in this private zone.");
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        Location blockLocation = block.getLocation();

        PrivatizationZone zone = plugin.getRegionConfig().getPrivatizationZoneAt(blockLocation);

        if (zone != null && !(zone.getOwner().equals(player.getName())
                || zone.getResidents().contains(player.getUniqueId().toString()))) {
            event.setCancelled(true);
            player.sendMessage("You can't build here.");
        }
    }
}
package ua.mykolamurza.pottullo.handler;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import ua.mykolamurza.pottullo.Pottullo;
import ua.mykolamurza.pottullo.model.PrivatizationZone;

import static ua.mykolamurza.pottullo.handler.util.PrivatizationBlockUtil.checkPlayerPermissionsAndSendMessage;
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

        PrivatizationZone zone = plugin.getPrivateZoneConfig().getPrivatizationZoneAt(block.getLocation());

        if (zone != null) {
            if (isItPrivatizationBlock(zone, block) && !zone.getOwner().equals(player.getName())) {
                event.setCancelled(true);
                player.sendMessage("Only private zone owner able to break privatization block.");
            }

            checkPlayerPermissionsAndSendMessage(event, player, zone,
                    "You can't build here.");
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        PrivatizationZone zone = plugin.getPrivateZoneConfig()
                .getPrivatizationZoneAt(event.getBlock().getLocation());
        checkPlayerPermissionsAndSendMessage(event, event.getPlayer(), zone,
                "You can't build here.");
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getHand() != EquipmentSlot.HAND) {
            return;
        }

        Block block = event.getClickedBlock();

        if (block == null) {
            return;
        }

        PrivatizationZone zone = plugin.getPrivateZoneConfig().getPrivatizationZoneAt(block.getLocation());
        checkPlayerPermissionsAndSendMessage(event, event.getPlayer(), zone,
                "You can't interact with items in this private zone.");
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        PrivatizationZone zone = plugin.getPrivateZoneConfig()
                .getPrivatizationZoneAt(event.getRightClicked().getLocation());
        checkPlayerPermissionsAndSendMessage(event, event.getPlayer(), zone,
                "You can't interact with entities in this private zone.");
    }
}
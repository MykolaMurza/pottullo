package ua.mykolamurza.pottullo.handler.util;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import ua.mykolamurza.pottullo.Pottullo;
import ua.mykolamurza.pottullo.model.PrivatizationZone;

import java.util.List;
import java.util.Set;

import static ua.mykolamurza.pottullo.config.Vars.getSystemPD;

/**
 * @author Mykola Murza
 */
public class PrivatizationBlockUtil {
    public static boolean isItPlayersPrivatizationBlock(Player player, Block block, Pottullo plugin) {
        PrivatizationZone zone = plugin.getPrivateZoneConfig().getPrivatizationZone(player);

        if (zone == null) {
            return false;
        }

        return zone.getOwner().equals(player.getName()) && isItPrivatizationBlock(zone, block);
    }

    public static boolean isItPrivatizationBlock(PrivatizationZone zone, Block block) {
        if (zone == null) {
            return false;
        }

        int radius = getSystemPD();

        return zone.getWorld().equals(block.getWorld().getName())
                && block.getX() == zone.getFromX() + radius
                && block.getY() == zone.getFromY() + radius
                && block.getZ() == zone.getFromZ() + radius;
    }

    public static void checkPlayerHasPermissionsAndSendMessageIfNot(Cancellable event, Player player,
                                                                    PrivatizationZone zone, String message) {
        if (zone != null && !(zone.getOwner().equals(player.getName())
                || zone.getResidents().contains(player.getUniqueId().toString()))) {
            event.setCancelled(true);
            player.sendMessage(message);
        }
    }

    public static void cancelAffectedByPistonOrExplosionBlocksInZone(Cancellable event, List<Block> blocks,
                                                                     Pottullo plugin) {
        for (Block block : blocks) {
            PrivatizationZone zone = plugin.getPrivateZoneConfig().getPrivatizationZoneAt(block.getLocation());
            if (zone != null) {
                event.setCancelled(true);
                break;
            }
        }
    }

    public static void cancelAffectedByPistonOrExplosionBlocksInZone(Cancellable event, List<Block> blocks,
                                                                     Pottullo plugin, BlockFace direction) {
        for (Block block : blocks) {
            PrivatizationZone zoneCurrent = plugin.getPrivateZoneConfig().getPrivatizationZoneAt(block.getLocation());
            PrivatizationZone zoneNext = plugin.getPrivateZoneConfig()
                    .getPrivatizationZoneAt(block.getRelative(direction).getLocation());
            if (zoneCurrent != null || zoneNext != null) {
                event.setCancelled(true);
                break;
            }
        }
    }

    public static void cancelIfZoneExists(Cancellable event, Entity entity, Pottullo plugin) {
        PrivatizationZone zone = plugin.getPrivateZoneConfig().getPrivatizationZoneAt(entity.getLocation());
        if (zone != null) {
            event.setCancelled(true);
        }
    }

    public static boolean isInstanceOfAny(Object obj, Set<Class<? extends Entity>> types) {
        for (Class<?> type : types) {
            if (type.isInstance(obj)) {
                return true;
            }
        }

        return false;
    }
}

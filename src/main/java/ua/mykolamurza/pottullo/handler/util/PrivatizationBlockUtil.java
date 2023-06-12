package ua.mykolamurza.pottullo.handler.util;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import ua.mykolamurza.pottullo.Pottullo;
import ua.mykolamurza.pottullo.model.PrivatizationZone;

import static ua.mykolamurza.pottullo.config.Vars.PRIVATIZATION_DISTANCE;

public class PrivatizationBlockUtil {
    public static boolean isItPlayersPrivatizationBlock(Player player, Block block, Pottullo plugin) {
        PrivatizationZone zone = plugin.getRegionConfig().getPrivatizationZone(player);

        if (zone == null) {
            return false;
        }

        return zone.getOwner().equals(player.getName()) && isItPrivatizationBlock(zone, block);
    }

    public static boolean isItPrivatizationBlock(PrivatizationZone zone, Block block) {
        if (zone == null) {
            return false;
        }

        return zone.getWorld().equals(block.getWorld().getName())
                && block.getX() == zone.getFromX() + PRIVATIZATION_DISTANCE
                && block.getY() == zone.getFromY() + PRIVATIZATION_DISTANCE
                && block.getZ() == zone.getFromZ() + PRIVATIZATION_DISTANCE;
    }
}

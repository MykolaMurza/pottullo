package ua.mykolamurza.pottullo.handler;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import ua.mykolamurza.pottullo.Pottullo;
import ua.mykolamurza.pottullo.PrivatizationZone;
import ua.mykolamurza.pottullo.Storage;

import java.util.ArrayList;
import java.util.List;

import static ua.mykolamurza.pottullo.config.Vars.COORDS_DELIMITER;
import static ua.mykolamurza.pottullo.config.Vars.PRIVATIZATION_DISTANCE;

/**
 * @author MykolaMurza
 */
public class LapisLazuliHandler implements Listener {
    private final Pottullo plugin;
    private final List<Material> blocks;

    public LapisLazuliHandler(List<?> blocks, Pottullo plugin) {
        this.plugin = plugin;
        if (blocks.isEmpty()) {
            this.blocks = List.of(Material.LAPIS_ORE, Material.DEEPSLATE_LAPIS_ORE);
        } else {
            this.blocks = convert(blocks).stream().map(Material::getMaterial).toList();
            Bukkit.getLogger().info("Pottullo set as target blocks next list: " + this.blocks);
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onBlockPlace(BlockPlaceEvent event) {
        Block block = event.getBlock();
        Player player = event.getPlayer();

        if (!blocks.contains(block.getType())) {
            return;
        }

        PrivatizationZone zone = new PrivatizationZone(
                block.getWorld().getName(), player.getName(), block.getX(), block.getY(), block.getZ());
        Bukkit.getLogger().info(String.format("%s's private zone is from %s to %s", player.getName(),
                zone.getFromX() + COORDS_DELIMITER + zone.getFromY() + COORDS_DELIMITER + zone.getFromZ(),
                zone.getToX() + COORDS_DELIMITER + zone.getToY() + COORDS_DELIMITER + zone.getToZ()
        ));
        Storage.add(player, zone);
        player.sendMessage("Privatization is in process.");
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        Player player = event.getPlayer();

        if (blocks.contains(block.getType()) && isItPlayersPrivatizationBlock(player, block)) {
            plugin.getRegionConfig().removePrivatizationZone(player);
        }
    }

    private List<String> convert(List<?> blocks) {
        List<String> result = new ArrayList<>();

        for (Object item : blocks) {
            if (item instanceof String) {
                result.add((String) item);
            }
        }

        return result;
    }

    private boolean isItPlayersPrivatizationBlock(Player player, Block block) {
        PrivatizationZone zone = plugin.getRegionConfig().getPrivatizationZone(player);

        if (zone == null) {
            return false;
        }

        return zone.getWorld().equals(block.getWorld().getName())
                && zone.getOwner().equals(player.getName())
                && block.getX() == zone.getFromX() + PRIVATIZATION_DISTANCE
                && block.getY() == zone.getFromY() + PRIVATIZATION_DISTANCE
                && block.getZ() == zone.getFromZ() + PRIVATIZATION_DISTANCE;
    }
}

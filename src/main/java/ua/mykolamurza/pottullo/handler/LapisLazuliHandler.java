package ua.mykolamurza.pottullo.handler;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import ua.mykolamurza.pottullo.PrivatizationZone;

import java.util.ArrayList;
import java.util.List;

public class LapisLazuliHandler implements Listener {
    public static final String COORDS_DELIMITER = "/";
    private final List<Material> blocks;

    public LapisLazuliHandler(List<?> blocks) {
        if (blocks.isEmpty()) {
            this.blocks = List.of(Material.LAPIS_ORE, Material.DEEPSLATE_LAPIS_ORE);
        } else {
            this.blocks = convert(blocks).stream().map(Material::getMaterial).toList();
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onBlockPlace(BlockPlaceEvent event) {
        Block block = event.getBlock();

        if (!blocks.contains(block.getType())) {
            return;
        }

        PrivatizationZone zone = new PrivatizationZone(block.getX(), block.getY(), block.getZ());
        Player player = event.getPlayer();
        Bukkit.getLogger().info(String.format("%s's private zone is from %s to %s", player.getName(),
                zone.getFromX() + COORDS_DELIMITER + zone.getFromY() + COORDS_DELIMITER + zone.getFromZ(),
                zone.getToX() + COORDS_DELIMITER + zone.getToY() + COORDS_DELIMITER + zone.getToZ()
        ));
        Bukkit.getLogger().info("Privatization is in process.");
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
}

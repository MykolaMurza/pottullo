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
import ua.mykolamurza.pottullo.model.PrivatizationZone;
import ua.mykolamurza.pottullo.model.Storage;

import java.util.ArrayList;
import java.util.List;

import static ua.mykolamurza.pottullo.config.LocalizationConfig.getBundledText;
import static ua.mykolamurza.pottullo.handler.util.PrivatizationBlockUtil.isItPlayersPrivatizationBlock;

/**
 * @author Mykola Murza
 */
public class LapisLazuliHandler implements Listener {
    private final Pottullo plugin;
    private final List<Material> blocks;
    private final List<Material> microBlocks;

    public LapisLazuliHandler(List<?> blocks, List<?> microBlocks, Pottullo plugin) {
        this.plugin = plugin;

        if (blocks.isEmpty()) {
            this.blocks = List.of(Material.LAPIS_ORE, Material.DEEPSLATE_LAPIS_ORE);
        } else {
            this.blocks = convert(blocks).stream().map(Material::getMaterial).toList();
            Bukkit.getLogger().info("Pottullo set as target blocks next list: " + this.blocks);
        }

        if (microBlocks.isEmpty()) {
            this.microBlocks = new ArrayList<>(); // No default blocks for this list
        } else {
            this.microBlocks = convert(microBlocks).stream().map(Material::getMaterial).toList();
            Bukkit.getLogger().info("Pottullo set as target micro-blocks next list: " + this.microBlocks);
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onBlockPlace(BlockPlaceEvent event) {
        Block block = event.getBlock();
        Player player = event.getPlayer();

        boolean isCommon = blocks.contains(block.getType());
        boolean isMicro = microBlocks.contains(block.getType());
        if (!isCommon && !isMicro) {
            return;
        }

        PrivatizationZone zone = new PrivatizationZone(
                block.getWorld().getName(), player.getName(), block.getX(), block.getY(), block.getZ(), isMicro);
        Storage.add(player, zone);
        player.sendMessage(getBundledText("privatization-started"));
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        Player player = event.getPlayer();

        if (blocks.contains(block.getType()) && isItPlayersPrivatizationBlock(player, block, plugin)) {
            player.sendMessage(
                    plugin.getPrivateZoneConfig().removePrivatizationZone(player)
                            ? getBundledText("command.remove.removed")
                            : getBundledText("command.common.dont-own-zone")
            );
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
}

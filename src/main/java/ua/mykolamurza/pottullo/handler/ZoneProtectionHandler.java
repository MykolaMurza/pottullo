package ua.mykolamurza.pottullo.handler;

import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.projectiles.ProjectileSource;
import ua.mykolamurza.pottullo.Pottullo;
import ua.mykolamurza.pottullo.model.PrivatizationZone;

import java.util.List;

import static ua.mykolamurza.pottullo.handler.util.PrivatizationBlockUtil.*;
import static ua.mykolamurza.pottullo.handler.util.Vars.RARE_PASSIVE_MOBS;

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
            } else {
                checkPlayerHasPermissionsAndSendMessageIfNot(event, player, zone, "You can't build here.");
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        PrivatizationZone zone = plugin.getPrivateZoneConfig()
                .getPrivatizationZoneAt(event.getBlock().getLocation());
        checkPlayerHasPermissionsAndSendMessageIfNot(event, event.getPlayer(), zone, "You can't build here.");
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
        checkPlayerHasPermissionsAndSendMessageIfNot(event, event.getPlayer(), zone,
                "You can't interact with items in this private zone.");
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        PrivatizationZone zone = plugin.getPrivateZoneConfig()
                .getPrivatizationZoneAt(event.getRightClicked().getLocation());
        checkPlayerHasPermissionsAndSendMessageIfNot(event, event.getPlayer(), zone,
                "You can't interact with entities in this private zone.");

        if (event.getRightClicked() instanceof Hanging || event.getRightClicked() instanceof ArmorStand) {
            checkPlayerHasPermissionsAndSendMessageIfNot(event, event.getPlayer(), zone,
                    "You can't interact with items in this private zone.");
        }
    }

    @EventHandler
    public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent event) {
        EntityType entityType = event.getRightClicked().getType();
        if (entityType == EntityType.ARMOR_STAND || entityType == EntityType.CHEST_BOAT
                || entityType == EntityType.ITEM_FRAME || entityType == EntityType.GLOW_ITEM_FRAME) {
            PrivatizationZone zone = plugin.getPrivateZoneConfig()
                    .getPrivatizationZoneAt(event.getRightClicked().getLocation());
            checkPlayerHasPermissionsAndSendMessageIfNot(event, event.getPlayer(), zone,
                    "You can't interact with items in this private zone.");
        }
    }

    @EventHandler
    public void onPistonExtend(BlockPistonExtendEvent event) {
        List<Block> blocks = event.getBlocks();
        cancelAffectedByPistonOrExplosionBlocksInZone(event, blocks, plugin, event.getDirection());
    }

    @EventHandler
    public void onPistonRetract(BlockPistonRetractEvent event) {
        List<Block> blocks = event.getBlocks();
        cancelAffectedByPistonOrExplosionBlocksInZone(event, blocks, plugin, event.getDirection());
    }

    @EventHandler
    public void onExplosion(EntityExplodeEvent event) {
        List<Block> blocks = event.blockList();
        cancelAffectedByPistonOrExplosionBlocksInZone(event, blocks, plugin);
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();
        Entity damager = event.getDamager();

        if (damager instanceof Player player) {
            PrivatizationZone zone = plugin.getPrivateZoneConfig().getPrivatizationZoneAt(entity.getLocation());

            if (zone != null && (zone.getOwner().equals(player.getName())
                    || zone.getResidents().contains(player.getName()))) {
                return;
            }

            if (entity instanceof Player) {
                return; // PVP is ON
            }
        }

        if (isInstanceOfAny(entity, RARE_PASSIVE_MOBS) || !entity.getScoreboardTags().isEmpty()) {
            cancelIfZoneExists(event, entity, plugin);
        }
    }

    @EventHandler
    public void onHangingBreakByEntity(HangingBreakByEntityEvent event) {
        Entity remover = event.getRemover();
        Hanging hanging = event.getEntity();
        PrivatizationZone zone = plugin.getPrivateZoneConfig().getPrivatizationZoneAt(hanging.getLocation());

        // On player break
        if (remover instanceof Player player && zone != null
                && (zone.getOwner().equals(player.getName()) || zone.getResidents().contains(player.getName()))) {
            return;
        }

        // On projectile break (arrow, fireball, snowball etc.)
        if (remover instanceof Projectile projectile) {
            ProjectileSource shooter = projectile.getShooter();
            if (shooter instanceof Player player) {
                checkPlayerHasPermissionsAndSendMessageIfNot(event, player, zone, "Don't even try to brake this!");
            }
        }

        cancelIfZoneExists(event, hanging, plugin);
    }

    @EventHandler
    public void onHangingBreak(HangingBreakEvent event) {
        Hanging hanging = event.getEntity();
        HangingBreakEvent.RemoveCause cause = event.getCause();

        if (cause == HangingBreakEvent.RemoveCause.EXPLOSION) {
            cancelIfZoneExists(event, hanging, plugin);
        }
    }
}
package ua.mykolamurza.pottullo.handler.util;

import org.bukkit.entity.*;

import java.util.Set;

public class Vars {
    public static final Set<Class<? extends Entity>> RARE_PASSIVE_MOBS = Set.of(
            ArmorStand.class, // lol, look at this dude
            Ocelot.class,
            Axolotl.class,
            Sniffer.class,
            MushroomCow.class
    );

    public static final Set<EntityType> TYPES_TO_HANDLE_ON_INTERACT = Set.of(
            EntityType.ARMOR_STAND,
            EntityType.ITEM_FRAME,
            EntityType.GLOW_ITEM_FRAME
    );
}

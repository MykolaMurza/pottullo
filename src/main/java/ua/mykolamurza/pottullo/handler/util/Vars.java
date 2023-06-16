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
}

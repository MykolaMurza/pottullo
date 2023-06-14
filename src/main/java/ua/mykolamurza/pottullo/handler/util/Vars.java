package ua.mykolamurza.pottullo.handler.util;

import org.bukkit.entity.*;

import java.util.Set;

public class Vars {
    public static final Set<Class<? extends Entity>> COMMON_PASSIVE_MOBS = Set.of(
            Chicken.class,
            Cow.class,
            Pig.class,
            Sheep.class,
            Horse.class,
            Mule.class,
            Parrot.class
    );

    public static final Set<Class<? extends Entity>> RARE_PASSIVE_MOBS = Set.of(
            ArmorStand.class, // lol, look at this dude
            Cat.class,
            Donkey.class,
            Fox.class,
            Squid.class,
            Bat.class,
            Cod.class,
            Ocelot.class,
            Salmon.class,
            Rabbit.class,
            Bee.class,
            Axolotl.class,
            GlowSquid.class,
            Turtle.class,
            WanderingTrader.class,
            Villager.class,
            Sniffer.class,
            MushroomCow.class,
            Strider.class,
            Frog.class
    );
}

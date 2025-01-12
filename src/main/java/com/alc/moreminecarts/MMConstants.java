package com.alc.moreminecarts;

import io.github.fabricators_of_create.porting_lib.config.ModConfigSpec;
import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.function.Predicate;

@SuppressWarnings("UnstableApiUsage")
public class MMConstants {
    public static final String modid = "moreminecarts";

    public static float WOODEN_MAX_SPEED = 0.2f;
    public static float MAGLEV_MAX_SPEED = 1f;
    public static float LIGHTSPEED_MAX_SPEED = 2.5f;

    public static double POWERED_LIGHTSPEED_BOOST = 0.2; // default powered rail: 0.06f


    public static final double POWERED_LOCKING_RAIL_SPEED = 0.05;

    public static final float HS_SLOWDOWN = 0.995f;
    public static final float HS_AIR_DRAG = 0.995f;

    public static final float PISTON_PUSHCART_VERTICAL_SPEED = 0.2f;
    public static final float PISTON_PUSHCART_AERODYNAMIC_VERTICAL_SPEED = 0.4f;
    public static final float PISTON_PUSHCART_MAX_HEIGHT = 5f;

    public static ModConfigSpec.IntValue CONFIG_GLASS_CACTUS_SPAWNS;
    public static ModConfigSpec.BooleanValue CONFIG_GLASS_CACTUS_DESERT_ONLY;
    public static ModConfigSpec.DoubleValue CONFIG_WOOD_RAILS_MAX_SPEED;
    public static ModConfigSpec.DoubleValue CONFIG_MAGLEV_RAILS_MAX_SPEED;
    public static ModConfigSpec.DoubleValue CONFIG_LIGHTSPEED_RAILS_MAX_SPEED;
    public static ModConfigSpec.DoubleValue CONFIG_TURBO_BOOST;
    public static ModConfigSpec.ConfigValue<List<? extends String>> CONFIG_CHUNK_LOADER_FUEL_IDS;
    public static ModConfigSpec.ConfigValue<List<? extends Integer>> CONFIG_CHUNK_LOADER_FUEL_TICKS;
    public static ModConfigSpec.ConfigValue<List<? extends String>> CONFIG_CHUNK_LOADER_MESSAGE;
    public static ModConfigSpec.DoubleValue CONFIG_CHUNK_LOADER_MULTIPLIER;
    public static ModConfigSpec.IntValue CONFIG_CHUNK_LOADER_CHUNKRODITE;

    public static List<Predicate<net.minecraft.world.item.ItemStack>> CHUNK_LOADER_FUEL_PREDICATES_BAKED = null;

    public static ResourceLocation id(String path) {
        return new ResourceLocation(modid, path);
    }
}

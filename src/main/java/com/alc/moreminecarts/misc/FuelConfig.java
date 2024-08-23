package com.alc.moreminecarts.misc;

import com.alc.moreminecarts.MMConstants;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.arguments.item.ItemPredicateArgument;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeConfig;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class FuelConfig {

    // or -1 if nothing found
    public static void bakePredicatesIfNeeded(Level level) {
        if (MMConstants.CHUNK_LOADER_FUEL_PREDICATES_BAKED == null
                || MMConstants.CHUNK_LOADER_FUEL_PREDICATES_BAKED.size() < MMConstants.CONFIG_CHUNK_LOADER_FUEL_IDS.get().size()) {

            MMConstants.CHUNK_LOADER_FUEL_PREDICATES_BAKED = new ArrayList<Predicate<ItemStack>>();
            for (String s : MMConstants.CONFIG_CHUNK_LOADER_FUEL_IDS.get()) {

                try {

                    CommandBuildContext commandContext = CommandBuildContext.simple(level.registryAccess(), level.enabledFeatures());

                    Predicate<ItemStack> bakedItemPredicate =
                            new ItemPredicateArgument(commandContext).parse(new StringReader(s));

                    MMConstants.CHUNK_LOADER_FUEL_PREDICATES_BAKED.add(bakedItemPredicate);

                } catch(CommandSyntaxException e) {
                    // should never hit here!
                }

            }
        }
    }

    public static boolean ValidateID(String id) {

        // this ran into issues with new command requirements, so we're just not going to validate

        return true;
    }

    public static boolean ValidateTicks(int ticks) {
        return ticks >= 0;
    }

    public static String[] DEFAULT_FUEL_IDS = new String[] {
        "minecraft:quartz",
        "minecraft:emerald",
        "minecraft:emerald_block",
        "minecraft:diamond",
        "minecraft:diamond_block",
        "minecraft:nether_star",
        "moreminecarts:chunkrodite",
        "moreminecarts:chunkrodite_block"
    };

    public static Integer[] DEFAULT_FUEL_TICKS = new Integer[] {
        600,
        6000,
        54000,
        72000,
        648000,
        720000,
        18000,
        162000
    };
}

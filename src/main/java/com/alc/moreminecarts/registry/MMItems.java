package com.alc.moreminecarts.registry;

import com.alc.moreminecarts.MMConstants;
import com.alc.moreminecarts.MoreMinecartsMod;
import com.alc.moreminecarts.items.*;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Collection;

public class MMItems {
    public static final Collection<ItemStack> ITEMS = new ArrayList<>();

    public static Item.Properties modItem() {
        return new Item.Properties();
    }

    // Rail Items
    public static final Item RAIL_TURN_ITEM = register("rail_turn", new BlockItem(MMBlocks.RAIL_TURN, modItem()));
    public static final Item PARALLEL_RAIL_ITEM = register("parallel_rail", new BlockItem(MMBlocks.PARALLEL_RAIL_BLOCK, modItem()));
    public static final Item CROSS_RAIL_ITEM = register("cross_rail", new BlockItem(MMBlocks.CROSS_RAIL_BLOCK, modItem()));
    public static final Item PROJECTOR_RAIL_ITEM = register("projector_rail", new BlockItem(MMBlocks.PROJECTOR_RAIL, modItem()));
    public static final Item WOODEN_RAIL_ITEM = register("wooden_rail", new BlockItem(MMBlocks.WOODEN_RAIL_BLOCK, modItem()));
    public static final Item WOODEN_RAIL_TURN_ITEM = register("wooden_rail_turn", new BlockItem(MMBlocks.WOODEN_RAIL_TURN, modItem()));
    public static final Item WOODEN_PARALLEL_RAIL_ITEM = register("wooden_parallel_rail", new BlockItem(MMBlocks.WOODEN_PARALLEL_RAIL_BLOCK, modItem()));
    public static final Item WOODEN_CROSS_RAIL_ITEM = register("wooden_cross_rail", new BlockItem(MMBlocks.WOODEN_CROSS_RAIL_BLOCK, modItem()));
    public static final Item WOODEN_PROJECTOR_RAIL_ITEM = register("wooden_projector_rail", new BlockItem(MMBlocks.WOODEN_PROJECTOR_RAIL, modItem()));
    public static final Item MAGLEV_RAIL_ITEM = register("maglev_rail", new BlockItem(MMBlocks.MAGLEV_RAIL_BLOCK, modItem()));
    public static final Item MAGLEV_RAIL_TURN_ITEM = register("maglev_rail_turn", new BlockItem(MMBlocks.MAGLEV_RAIL_TURN, modItem()));
    public static final Item MAGLEV_PARALLEL_RAIL_ITEM = register("maglev_parallel_rail", new BlockItem(MMBlocks.MAGLEV_PARALLEL_RAIL_BLOCK, modItem()));
    public static final Item MAGLEV_CROSS_RAIL = register("maglev_cross_rail", new BlockItem(MMBlocks.MAGLEV_CROSS_RAIL_BLOCK, modItem()));
    public static final Item MAGLEV_PROJECTOR_RAIL_ITEM = register("maglev_projector_rail", new BlockItem(MMBlocks.MAGLEV_PROJECTOR_RAIL, modItem()));
    public static final Item MAGLEV_POWERED_RAIL_ITEM = register("maglev_powered_rail", new BlockItem(MMBlocks.MAGLEV_POWERED_RAIL_BLOCK, modItem()));
    public static final Item LIGHTSPEED_RAIL_ITEM = register("lightspeed_rail", new BlockItem(MMBlocks.LIGHTSPEED_RAIL_BLOCK, modItem()));
    public static final Item LIGHTSPEED_CROSS_RAIL_ITEM = register("lightspeed_cross_rail", new BlockItem(MMBlocks.LIGHTSPEED_CROSS_RAIL_BLOCK, modItem()));
    public static final Item LIGHTSPEED_POWERED_RAIL_ITEM = register("lightspeed_powered_rail", new BlockItem(MMBlocks.LIGHTSPEED_POWERED_RAIL_BLOCK, modItem()));
    public static final Item BIOLUMINESCENT_RAIL_ITEM = register("bioluminescent_rail", new BlockItem(MMBlocks.BIOLUMINESCENT_RAIL_BLOCK, modItem()));
    public static final Item LOCKING_RAIL_ITEM = register("locking_rail", new BlockItem(MMBlocks.LOCKING_RAIL_BLOCK, modItem()));
    public static final Item POWERED_LOCKING_RAIL_ITEM = register("powered_locking_rail", new BlockItem(MMBlocks.POWERED_LOCKING_RAIL_BLOCK, modItem()));
    public static final Item PISTON_LIFTER_RAIL_ITEM = register("piston_lifter_rail", new BlockItem(MMBlocks.PISTON_LIFTER_RAIL, modItem()));
    public static final Item ARITHMETIC_RAIL_ITEM = register("arithmetic_rail", new BlockItem(MMBlocks.ARITHMETIC_RAIL, modItem()));

    // Minecart Items
    public static final Item MINECART_WITH_NET_ITEM = register("minecart_with_net", new MinecartWithNetItem(new Item.Properties().stacksTo(1)));
    public static final Item MINECART_WITH_CHUNK_LOADER_ITEM = register("minecart_with_chunk_loader", new ChunkLoaderCartItem(new Item.Properties().stacksTo(1)));
    public static final Item MINECART_WITH_STASIS_ITEM = register("pearl_stasis_minecart", new OrbStasisCartItem(new Item.Properties().stacksTo(1)));
    public static final Item FLAG_CART_ITEM = register("flag_cart", new FlagCartItem(new Item.Properties().stacksTo(1)));
    public static final Item CAMPFIRE_CART_ITEM = register("campfire_cart", new CampfireCartItem(new Item.Properties().stacksTo(1)));
    public static final Item SOULFIRE_CART_ITEM = register("soulfire_cart", new SoulfireCartItem(new Item.Properties().stacksTo(1)));
    private static Item ENDFIRE_CART_ITEM;
    public static final Item WOODEN_PUSHCART_ITEM = register("wooden_pushcart", new WoodenPushcartItem(new Item.Properties().stacksTo(1)));
    public static final Item IRON_PUSHCART_ITEM = register("iron_pushcart", new IronPushcartItem(new Item.Properties().stacksTo(1)));
    public static final Item PISTON_PUSHCART_ITEM = register("piston_pushcart", new PistonPushcartItem(new Item.Properties().stacksTo(1)));
    public static final Item STICKY_PISTON_PUSHCART_ITEM = register("sticky_piston_pushcart", new StickyPistonPushcartItem(new Item.Properties().stacksTo(1)));
    public static final Item TANK_CART_ITEM = register("tank_cart", new TankCartItem(new Item.Properties().stacksTo(1)));
    public static final Item BATTERY_CART_ITEM = register("battery_cart", new BatteryCartItem(new Item.Properties().stacksTo(1)));

    // Block Items
    public static final Item SILICA_STEEL_BLOCK_ITEM = register("silica_steel_block", new BlockItem(MMBlocks.SILICA_STEEL_BLOCK, new Item.Properties()));
    public static final Item CHUNKRODITE_BLOCK_ITEM = register("chunkrodite_block", new BlockItem(MMBlocks.CHUNKRODITE_BLOCK, new Item.Properties()));
    public static final Item CORRUGATED_SILICA_STEEL_ITEM = register("corrugated_silica_steel", new BlockItem(MMBlocks.CORRUGATED_SILICA_STEEL, new Item.Properties()));
    public static final Item SILICA_STEEL_PILLAR_ITEM = register("silica_steel_pillar", new BlockItem(MMBlocks.SILICA_STEEL_PILLAR, new Item.Properties()));
    public static final Item ORGANIC_GLASS_ITEM = register("organic_glass", new BlockItem(MMBlocks.ORGANIC_GLASS, new Item.Properties()));
    public static final Item ORGANIC_GLASS_PANE_ITEM = register("organic_glass_pane", new BlockItem(MMBlocks.ORGANIC_GLASS_PANE, new Item.Properties()));
    public static final Item CHISELED_ORGANIC_GLASS_ITEM = register("chiseled_organic_glass", new BlockItem(MMBlocks.CHISELED_ORGANIC_GLASS, new Item.Properties()));
    public static final Item CHISELED_ORGANIC_GLASS_PANE_ITEM = register("chiseled_organic_glass_pane", new BlockItem(MMBlocks.CHISELED_ORGANIC_GLASS_PANE, new Item.Properties()));
    public static final Item GLASS_CACTUS_ITEM = register("glass_cactus", new GlassCactusItem(MMBlocks.GLASS_CACTUS, new Item.Properties()));
    public static final Item HOLO_SCAFFOLD_GENERATOR_ITEM = register("holo_scaffold_generator", new BlockItem(MMBlocks.HOLO_SCAFFOLD_GENERATOR, new Item.Properties()));
    public static final Item CHUNK_LOADER_ITEM = register("chunk_loader", new BlockItem(MMBlocks.CHUNK_LOADER_BLOCK, new Item.Properties()));
    public static final Item MINECART_LOADER_ITEM = register("minecart_loader", new BlockItem(MMBlocks.MINECART_LOADER_BLOCK, modItem()));
    public static final Item MINECART_UNLOADER_ITEM = register("minecart_unloader", new BlockItem(MMBlocks.MINECART_UNLOADER_BLOCK, modItem()));
    public static final Item FILTER_UNLOADER_ITEM = register("filter_unloader", new BlockItem(MMBlocks.FILTER_UNLOADER_BLOCK, modItem()));
    public static final Item PEARL_STASIS_CHAMBER_ITEM = register("pearl_stasis_chamber", new BlockItem(MMBlocks.PEARL_STASIS_CHAMBER, new Item.Properties()));

    // Misc Items
    public static final Item COUPLER_ITEM = register("coupler", new CouplerItem(new Item.Properties().stacksTo(1)));
    public static final Item HIGH_SPEED_UPGRADE_ITEM = register("high_speed_upgrade", new Item(modItem()));
    public static final HoloRemoteItem HOLO_REMOTE_ITEM = register("holo_remote", new HoloRemoteItem(HoloRemoteItem.HoloRemoteType.regular, new Item.Properties()));
    public static final HoloRemoteItem BACKWARDS_HOLO_REMOTE_ITEM = register("backwards_holo_remote", new HoloRemoteItem(HoloRemoteItem.HoloRemoteType.backwards, new Item.Properties()));
    public static final HoloRemoteItem SIMPLE_HOLO_REMOTE_ITEM = register("simple_holo_remote", new HoloRemoteItem(HoloRemoteItem.HoloRemoteType.simple, new Item.Properties()));
    public static final HoloRemoteItem BROKEN_HOLO_REMOTE_ITEM = register("broken_holo_remote", new HoloRemoteItem(HoloRemoteItem.HoloRemoteType.broken, new Item.Properties()));

    // Rail Signal Items
    public static final BiMap<DyeColor, Item> RAIL_SIGNALS = HashBiMap.create();
    static {
        for (DyeColor color : MoreMinecartsMod.dyeColorsByRainbow) {
            Item entry = register("rail_signal_" + color.getName(), new Item(new Item.Properties().stacksTo(1)));
            RAIL_SIGNALS.put(color, entry);
        }
    }

    // Color Detector Rail Items
    static {
        for (DyeColor color : MoreMinecartsMod.dyeColorsByRainbow) {
            register("color_detector_rail_" + color.getName(), new BlockItem(MMBlocks.COLOR_DETECTOR_RAILS.get(color), modItem()));
        }
    }

    // Material Items
    public static final Item LEVITATION_POWDER = register("levitation_powder", new Item(new Item.Properties().stacksTo(64)));
    public static final Item SILICA_STEEL_MIX = register("silica_steel_mix", new Item(new Item.Properties().stacksTo(64)));
    public static final Item SILICA_STEEL = register("silica_steel", new Item(new Item.Properties().stacksTo(64)));
    public static final Item CHUNKRODITE = register("chunkrodite", new Item(new Item.Properties().stacksTo(64)));
    public static final Item HARD_LIGHT_LENS = register("hard_light_lens", new Item(new Item.Properties().stacksTo(64)));
    public static final Item GLASS_SPINES = register("glass_spines", new Item(new Item.Properties().stacksTo(64)));
    public static final Item TRANSPORT_TANK = register("transport_tank", new Item(new Item.Properties().stacksTo(1)));
    public static final Item TRANSPORT_BATTERY = register("transport_battery", new Item(new Item.Properties().stacksTo(1)));

    public static void register() {
    }

    private static <T extends Item> T register(String path, T item) {
        ITEMS.add(item.getDefaultInstance());
        return Registry.register(BuiltInRegistries.ITEM, MMConstants.id(path), item);
    }
}

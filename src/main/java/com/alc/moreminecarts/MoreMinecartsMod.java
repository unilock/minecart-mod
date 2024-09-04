package com.alc.moreminecarts;

import com.alc.moreminecarts.misc.FuelConfig;
import com.alc.moreminecarts.misc.MMCreativeTabs;
import com.alc.moreminecarts.proxy.IProxy;
import com.alc.moreminecarts.proxy.MoreMinecartsPacketHandler;
import com.alc.moreminecarts.proxy.ServerProxy;
import com.alc.moreminecarts.registry.*;
import io.github.fabricators_of_create.porting_lib.config.ConfigRegistry;
import io.github.fabricators_of_create.porting_lib.config.ConfigType;
import io.github.fabricators_of_create.porting_lib.config.ModConfigSpec;
import net.fabricmc.api.ModInitializer;
import net.minecraft.world.item.DyeColor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;

import static com.alc.moreminecarts.misc.FuelConfig.DEFAULT_FUEL_IDS;
import static com.alc.moreminecarts.misc.FuelConfig.DEFAULT_FUEL_TICKS;

@SuppressWarnings("UnstableApiUsage")
public class MoreMinecartsMod implements ModInitializer {
    // Directly reference a log4j logger.
    public static Logger LOGGER = LogManager.getLogger();
    public static IProxy PROXY = new ServerProxy();

    @Override
    public void onInitialize() {

        /*
        if (EndergeticCompat.endergeticInstalled()) {
            ENDFIRE_CART_ENTITY = ENTITIES.register("endfire_cart", () -> EntityType.Builder.<EndfireCartEntity>of(EndfireCartEntity::new, MobCategory.MISC ).sized(0.98F, 0.7F).build("endfire_cart"));
            ENDFIRE_CART_ITEM = ITEMS.register("endfire_cart", () -> new EndfireCartItem(new Item.Properties().stacksTo(1).tab(CreativeModeTab.TAB_TRANSPORTATION)));
            HS_ENDFIRE_CART_ENTITY = ENTITIES.register("high_speed_endfire_minecart", () -> EntityType.Builder.<HSEndfireMinecart>of(HSEndfireMinecart::new, MobCategory.MISC ).sized(0.98F, 0.7F).build("high_speed_endfire_minecart"));
        }*/

        MoreMinecartsPacketHandler.Init();

        // Register the setup method for modloading
        this.setup();

        MMEntities.register();
        MMBlocks.register();
        MMItems.register();
        MMTileEntities.register();
        MMContainers.register();
        MMCreativeTabs.register();

        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        builder.comment("Changes the spawn rate of vitric cactus. Default cactus is 10, set to zero to disable.");
        MMConstants.CONFIG_GLASS_CACTUS_SPAWNS = builder.defineInRange("vitric_cactus_spawns", ()->2, 0, 100);
        builder.comment("Requires that vitric cactus be grown only in desert and mesa biomes.");
        MMConstants.CONFIG_GLASS_CACTUS_DESERT_ONLY = builder.define("vitric_cactus_desert_only", true);
        builder.comment("Sets the max speed of various rail types. Default rails are 0.4.");
        MMConstants.CONFIG_WOOD_RAILS_MAX_SPEED = builder.defineInRange("wood_rails_max_speed", () -> 0.2D, 0.1, 10);
        MMConstants.CONFIG_MAGLEV_RAILS_MAX_SPEED = builder.defineInRange("maglev_rails_max_speed", () -> 1.0D, 0.1, 10);
        MMConstants.CONFIG_LIGHTSPEED_RAILS_MAX_SPEED = builder.defineInRange("lightspeed_rails_max_speed", () -> 2.5D, 0.1, 10);
        builder.comment("Sets the extra speed boost given by turbo rails. 0.06 is the default for regular powered rails.");
        MMConstants.CONFIG_TURBO_BOOST = builder.defineInRange("turbo_rails_boost", () -> 0.2D, 0, 1);

        builder.comment("Defines what fuels are allowed in the chunk loader. Any item predicate works here.");
        MMConstants.CONFIG_CHUNK_LOADER_FUEL_IDS = builder.defineList("chunk_loader_fuel_ids", Arrays.asList(DEFAULT_FUEL_IDS), (c) -> FuelConfig.ValidateID((String) c));
        MMConstants.CONFIG_CHUNK_LOADER_FUEL_TICKS = builder.defineList("chunk_loader_fuel_ticks", Arrays.asList(DEFAULT_FUEL_TICKS), (c) -> FuelConfig.ValidateTicks((Integer) c));
        builder.comment("Message lines to show in the chunk loader info. Uses the lang file if empty.");
        MMConstants.CONFIG_CHUNK_LOADER_MESSAGE = builder.defineList("chunk_loader_message", new ArrayList<String>(), (c) -> true);
        builder.comment("Multiplies all the costs above to buff or nerf. Set to zero to prevent chunk loading completely.");
        MMConstants.CONFIG_CHUNK_LOADER_MULTIPLIER = builder.defineInRange("chunk_loader_multiplier", ()->1.0D, 0, 9999);
        builder.comment("Refund 1 chunkrodite per n leftoever ticks, considering the multiplier. Set to zero to disable chunkrodite drops.");
        MMConstants.CONFIG_CHUNK_LOADER_CHUNKRODITE = builder.defineInRange("chunk_loader_chunkrodite", ()->24000, 0, 999999999);

        ConfigRegistry.registerConfig(MMConstants.modid, ConfigType.COMMON, builder.build(), "moreminecartsconfig.toml");

    }

    private void setup() {
//        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(new ResourceLocation("moreminecarts:chunkrodite_block"), MMBlocks.POTTED_BEET);
//        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(new ResourceLocation("moreminecarts:glass_cactus"), MMBlocks.POTTED_GLASS_CACTUS);

        MMConstants.WOODEN_MAX_SPEED = MMConstants.CONFIG_WOOD_RAILS_MAX_SPEED.get().floatValue();
        MMConstants.MAGLEV_MAX_SPEED = MMConstants.CONFIG_MAGLEV_RAILS_MAX_SPEED.get().floatValue();
        MMConstants.LIGHTSPEED_MAX_SPEED = MMConstants.CONFIG_LIGHTSPEED_RAILS_MAX_SPEED.get().floatValue();
        MMConstants.POWERED_LIGHTSPEED_BOOST = MMConstants.CONFIG_TURBO_BOOST.get().floatValue();
    }

    public static DyeColor[] dyeColorsByRainbow = new DyeColor[] {
        DyeColor.WHITE,
        DyeColor.LIGHT_GRAY,
        DyeColor.GRAY,
        DyeColor.BLACK,
        DyeColor.BROWN,
        DyeColor.RED,
        DyeColor.ORANGE,
        DyeColor.YELLOW,
        DyeColor.LIME,
        DyeColor.GREEN,
        DyeColor.CYAN,
        DyeColor.LIGHT_BLUE,
        DyeColor.BLUE,
        DyeColor.PURPLE,
        DyeColor.MAGENTA,
        DyeColor.PINK
    };

}

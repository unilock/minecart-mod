package com.alc.moreminecarts;

import com.alc.moreminecarts.client.*;
import com.alc.moreminecarts.proxy.ClientProxy;
import com.alc.moreminecarts.proxy.MoreMinecartsPacketHandler;
import com.alc.moreminecarts.registry.MMBlocks;
import com.alc.moreminecarts.registry.MMContainers;
import com.alc.moreminecarts.registry.MMEntities;
import com.alc.moreminecarts.renderers.*;
import com.alc.moreminecarts.renderers.highspeed.HSMinecartRenderer;
import com.alc.moreminecarts.renderers.highspeed.HSPistonPushcartRenderer;
import com.alc.moreminecarts.renderers.highspeed.HSPushcartRenderer;
import com.alc.moreminecarts.renderers.highspeed.HSStickyPistonPushcartRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;

import java.util.Map;

@Environment(EnvType.CLIENT)
public class MoreMinecartsModClient implements ClientModInitializer {
    static {
        MoreMinecartsMod.PROXY = new ClientProxy();
    }

    @Override
    public void onInitializeClient() {
        // Register the doClientStuff method for modloading
        this.doClientStuff();
        this.registerEntityRenderers();

        MoreMinecartsPacketHandler.initClient();
        ModKeyMappings.setupKeybindings();
    }

    private void doClientStuff() {
        // do something that can only be done on the client
        //LOGGER.info("Got game settings {}", event.getMinecraftSupplier().gameSettings);

        MenuScreens.register(MMContainers.CHUNK_LOADER_CONTAINER, ChunkLoaderScreen::new);
        MenuScreens.register(MMContainers.MINECART_LOADER_CONTAINER, MinecartUnLoaderScreen::new);
        MenuScreens.register(MMContainers.FILTER_UNLOADER_CONTAINER, FilterUnloaderScreen::new);
        MenuScreens.register(MMContainers.TANK_CART_CONTAINER, TankCartScreen::new);
        MenuScreens.register(MMContainers.BATTERY_CART_CONTAINER, BatteryCartScreen::new);
        MenuScreens.register(MMContainers.FLAG_CART_CONTAINER, FlagCartScreen::new);

        BlockRenderLayerMap.INSTANCE.putBlocks(
                RenderType.cutout(),
                MMBlocks.RAIL_TURN,
                MMBlocks.PARALLEL_RAIL_BLOCK,
                MMBlocks.CROSS_RAIL_BLOCK,
                MMBlocks.PROJECTOR_RAIL,
                MMBlocks.HOLOGRAM_RAIL,
                MMBlocks.WOODEN_RAIL_BLOCK,
                MMBlocks.WOODEN_RAIL_TURN,
                MMBlocks.WOODEN_PARALLEL_RAIL_BLOCK,
                MMBlocks.WOODEN_CROSS_RAIL_BLOCK,
                MMBlocks.WOODEN_PROJECTOR_RAIL,
                MMBlocks.WOODEN_HOLOGRAM_RAIL,
                MMBlocks.MAGLEV_RAIL_BLOCK,
                MMBlocks.MAGLEV_RAIL_TURN,
                MMBlocks.MAGLEV_PARALLEL_RAIL_BLOCK,
                MMBlocks.MAGLEV_CROSS_RAIL_BLOCK,
                MMBlocks.MAGLEV_PROJECTOR_RAIL,
                MMBlocks.MAGLEV_HOLOGRAM_RAIL,
                MMBlocks.MAGLEV_POWERED_RAIL_BLOCK,
                MMBlocks.LIGHTSPEED_RAIL_BLOCK,
                MMBlocks.LIGHTSPEED_CROSS_RAIL_BLOCK,
                MMBlocks.LIGHTSPEED_POWERED_RAIL_BLOCK,
                MMBlocks.BIOLUMINESCENT_RAIL_BLOCK,
                MMBlocks.LOCKING_RAIL_BLOCK,
                MMBlocks.POWERED_LOCKING_RAIL_BLOCK,
                MMBlocks.PISTON_LIFTER_RAIL,
                MMBlocks.ARITHMETIC_RAIL,
                MMBlocks.CHUNK_LOADER_BLOCK,
                MMBlocks.PEARL_STASIS_CHAMBER,
                MMBlocks.HOLO_SCAFFOLD,
                MMBlocks.CHAOTIC_HOLO_SCAFFOLD,
                MMBlocks.GLASS_CACTUS,
                MMBlocks.POTTED_BEET,
                MMBlocks.POTTED_GLASS_CACTUS
        );

        for (Map.Entry<DyeColor, Block> entry : MMBlocks.COLOR_DETECTOR_RAILS.entrySet()) {
            BlockRenderLayerMap.INSTANCE.putBlock(entry.getValue(), RenderType.cutout());
        }

        BlockRenderLayerMap.INSTANCE.putBlocks(
                RenderType.translucent(),
                MMBlocks.ORGANIC_GLASS,
                MMBlocks.ORGANIC_GLASS_PANE,
                MMBlocks.CHISELED_ORGANIC_GLASS,
                MMBlocks.CHISELED_ORGANIC_GLASS_PANE
        );

    }

    private void registerEntityRenderers() {

        EntityRendererRegistry.register(MMEntities.MINECART_WITH_NET_ENTITY, VanillaMinecartRenderer::new);
        EntityRendererRegistry.register(MMEntities.CHUNK_LOADER_CART, VanillaMinecartRenderer::new);
        EntityRendererRegistry.register(MMEntities.ORB_STASIS_CART, VanillaMinecartRenderer::new);
        EntityRendererRegistry.register(MMEntities.FLAG_CART, VanillaMinecartRenderer::new);
        EntityRendererRegistry.register(MMEntities.CAMPFIRE_CART_ENTITY, CampfireCartRenderer::new);
        EntityRendererRegistry.register(MMEntities.SOULFIRE_CART_ENTITY, SoulfireCartRenderer::new);
        EntityRendererRegistry.register(MMEntities.WOODEN_PUSHCART_ENTITY, WoodenPushcartRenderer::new);
        EntityRendererRegistry.register(MMEntities.IRON_PUSHCART_ENTITY, IronPushcartRenderer::new);
        EntityRendererRegistry.register(MMEntities.PISTON_PUSHCART_ENTITY, PistonPushcartRenderer::new);
        EntityRendererRegistry.register(MMEntities.STICKY_PISTON_PUSHCART_ENTITY, StickyPistonPushcartRenderer::new);
        EntityRendererRegistry.register(MMEntities.TANK_CART_ENTITY, VanillaMinecartRenderer::new);
        EntityRendererRegistry.register(MMEntities.BATTERY_CART_ENTITY, VanillaMinecartRenderer::new);
        EntityRendererRegistry.register(MMEntities.COUPLER_ENTITY, CouplerRenderer::new);

        EntityRendererRegistry.register(MMEntities.HS_CART_ENTITY, HSMinecartRenderer::new);
        EntityRendererRegistry.register(MMEntities.HS_CHEST_CART_ENTITY, HSMinecartRenderer::new);
        EntityRendererRegistry.register(MMEntities.HS_TNT_CART_ENTITY, HSMinecartRenderer::new);
        EntityRendererRegistry.register(MMEntities.HS_COMMAND_BLOCK_CART_ENTITY, HSMinecartRenderer::new);
        EntityRendererRegistry.register(MMEntities.HS_HOPPER_CART_ENTITY, HSMinecartRenderer::new);
        EntityRendererRegistry.register(MMEntities.HS_SPAWNER_CART_ENTITY, HSMinecartRenderer::new);
        EntityRendererRegistry.register(MMEntities.HS_FURNACE_CART_ENTITY, HSMinecartRenderer::new);
        EntityRendererRegistry.register(MMEntities.HS_NET_CART_ENTITY, HSMinecartRenderer::new);
        EntityRendererRegistry.register(MMEntities.HS_CHUNK_LOADER_CART_ENTITY, HSMinecartRenderer::new);
        EntityRendererRegistry.register(MMEntities.HS_STASIS_CART_ENTITY, HSMinecartRenderer::new);
        EntityRendererRegistry.register(MMEntities.HS_FLAG_CART_ENTITY, HSMinecartRenderer::new);
        EntityRendererRegistry.register(MMEntities.HS_TANK_CART_ENTITY, HSMinecartRenderer::new);
        EntityRendererRegistry.register(MMEntities.HS_BATTERY_CART_ENTITY, HSMinecartRenderer::new);
        EntityRendererRegistry.register(MMEntities.HS_CAMPFIRE_CART_ENTITY, HSPushcartRenderer::new);
        EntityRendererRegistry.register(MMEntities.HS_SOULFIRE_CART_ENTITY, HSPushcartRenderer::new);
        EntityRendererRegistry.register(MMEntities.HS_PUSHCART_ENTITY, HSPushcartRenderer::new);
        EntityRendererRegistry.register(MMEntities.HS_PISTON_PUSHCART_ENTITY, HSPistonPushcartRenderer::new);
        EntityRendererRegistry.register(MMEntities.HS_STICKY_PISTON_PUSHCART_ENTITY, HSStickyPistonPushcartRenderer::new);

    }
}

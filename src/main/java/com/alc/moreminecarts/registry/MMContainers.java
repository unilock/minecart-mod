package com.alc.moreminecarts.registry;

import com.alc.moreminecarts.MMConstants;
import com.alc.moreminecarts.containers.*;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.AbstractContainerMenu;

import static com.alc.moreminecarts.MoreMinecartsMod.PROXY;

public class MMContainers {
    // Containers
    public static final ExtendedScreenHandlerType<ChunkLoaderContainer> CHUNK_LOADER_CONTAINER = register("chunk_loader_c", new ExtendedScreenHandlerType<>(
            (windowId, inv, data) -> {
                if (data != null) return new ChunkLoaderContainer(windowId, PROXY.getWorld(), data.readBlockPos(), inv, PROXY.getPlayer());
                else return new ChunkLoaderContainer(windowId, PROXY.getWorld(), inv, PROXY.getPlayer());
            }));
    public static final ExtendedScreenHandlerType<MinecartUnLoaderContainer> MINECART_LOADER_CONTAINER = register("minecart_loader_c", new ExtendedScreenHandlerType<>(
            (windowId, inv, data) -> {
                if (data != null) return new MinecartUnLoaderContainer(windowId, PROXY.getWorld(), data.readBlockPos(), inv, PROXY.getPlayer());
                else return new MinecartUnLoaderContainer(windowId, PROXY.getWorld(), inv, PROXY.getPlayer());
            }));
    public static final ExtendedScreenHandlerType<FilterUnloaderContainer> FILTER_UNLOADER_CONTAINER = register("filter_loader_c", new ExtendedScreenHandlerType<>(
            (windowId, inv, data) -> {
                if (data != null) return new FilterUnloaderContainer(windowId, PROXY.getWorld(), data.readBlockPos(), inv, PROXY.getPlayer());
                else return new FilterUnloaderContainer(windowId, PROXY.getWorld(), inv, PROXY.getPlayer());
            }));
    public static final ExtendedScreenHandlerType<TankCartContainer> TANK_CART_CONTAINER = register("tank_cart_c", new ExtendedScreenHandlerType<>(
            (windowId, inv, data) -> {
                return new TankCartContainer(windowId, PROXY.getWorld(), inv, PROXY.getPlayer());
            }));
    public static final ExtendedScreenHandlerType<BatteryCartContainer> BATTERY_CART_CONTAINER = register("battery_cart_c", new ExtendedScreenHandlerType<>(
            (windowId, inv, data) -> {
                return new BatteryCartContainer(windowId, PROXY.getWorld(), inv, PROXY.getPlayer());
            }));
    public static final ExtendedScreenHandlerType<FlagCartContainer> FLAG_CART_CONTAINER = register("flag_cart_c", new ExtendedScreenHandlerType<>(
            (windowId, inv, data) -> {
                return new FlagCartContainer(windowId, PROXY.getWorld(), inv, PROXY.getPlayer());
            }));

    public static void register() {
    }

    private static <T extends AbstractContainerMenu> ExtendedScreenHandlerType<T> register(String path, ExtendedScreenHandlerType<T> type) {
        return Registry.register(BuiltInRegistries.MENU, MMConstants.id(path), type);
    }
}

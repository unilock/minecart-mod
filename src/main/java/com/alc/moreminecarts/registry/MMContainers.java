package com.alc.moreminecarts.registry;

import com.alc.moreminecarts.MMConstants;
import com.alc.moreminecarts.containers.*;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

import static com.alc.moreminecarts.MoreMinecartsMod.PROXY;

public class MMContainers {
    // Containers
    public static final ExtendedScreenHandlerType<ChunkLoaderContainer> CHUNK_LOADER_CONTAINER = registerExtended("chunk_loader_c", new ExtendedScreenHandlerType<>(
            (windowId, inv, data) -> {
                if (data != null) return new ChunkLoaderContainer(windowId, PROXY.getWorld(), data.readBlockPos(), inv, PROXY.getPlayer());
                else return new ChunkLoaderContainer(windowId, PROXY.getWorld(), inv, PROXY.getPlayer());
            }));
    public static final ExtendedScreenHandlerType<MinecartUnLoaderContainer> MINECART_LOADER_CONTAINER = registerExtended("minecart_loader_c", new ExtendedScreenHandlerType<>(
            (windowId, inv, data) -> {
                if (data != null) return new MinecartUnLoaderContainer(windowId, PROXY.getWorld(), data.readBlockPos(), inv, PROXY.getPlayer());
                else return new MinecartUnLoaderContainer(windowId, PROXY.getWorld(), inv, PROXY.getPlayer());
            }));
    public static final ExtendedScreenHandlerType<FilterUnloaderContainer> FILTER_UNLOADER_CONTAINER = registerExtended("filter_loader_c", new ExtendedScreenHandlerType<>(
            (windowId, inv, data) -> {
                if (data != null) return new FilterUnloaderContainer(windowId, PROXY.getWorld(), data.readBlockPos(), inv, PROXY.getPlayer());
                else return new FilterUnloaderContainer(windowId, PROXY.getWorld(), inv, PROXY.getPlayer());
            }));
    public static final MenuType<TankCartContainer> TANK_CART_CONTAINER = register("tank_cart_c", new MenuType<>(
            (windowId, inv) -> {
                return new TankCartContainer(windowId, PROXY.getWorld(), inv, PROXY.getPlayer());
            }, FeatureFlagSet.of()));
    public static final MenuType<BatteryCartContainer> BATTERY_CART_CONTAINER = register("battery_cart_c", new MenuType<>(
            (windowId, inv) -> {
                return new BatteryCartContainer(windowId, PROXY.getWorld(), inv, PROXY.getPlayer());
            }, FeatureFlagSet.of()));
    public static final MenuType<FlagCartContainer> FLAG_CART_CONTAINER = register("flag_cart_c", new MenuType<>(
            (windowId, inv) -> {
                return new FlagCartContainer(windowId, PROXY.getWorld(), inv, PROXY.getPlayer());
            }, FeatureFlagSet.of()));

    public static void register() {
    }

    private static <T extends AbstractContainerMenu> ExtendedScreenHandlerType<T> registerExtended(String path, ExtendedScreenHandlerType<T> type) {
        return Registry.register(BuiltInRegistries.MENU, MMConstants.id(path), type);
    }

    private static <T extends AbstractContainerMenu> MenuType<T> register(String path, MenuType<T> type) {
        return Registry.register(BuiltInRegistries.MENU, MMConstants.id(path), type);
    }
}

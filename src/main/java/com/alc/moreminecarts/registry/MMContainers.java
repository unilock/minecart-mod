package com.alc.moreminecarts.registry;

import com.alc.moreminecarts.MMConstants;
import com.alc.moreminecarts.containers.*;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.alc.moreminecarts.MoreMinecartsMod.PROXY;

public class MMContainers {
    private static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, MMConstants.modid);

    // Containers
    public static final RegistryObject<MenuType<ChunkLoaderContainer>> CHUNK_LOADER_CONTAINER = CONTAINERS.register("chunk_loader_c", () -> IForgeMenuType.create(
            (windowId, inv, data) -> {
                if (data != null) return new ChunkLoaderContainer(windowId, PROXY.getWorld(), data.readBlockPos(), inv, PROXY.getPlayer());
                else return new ChunkLoaderContainer(windowId, PROXY.getWorld(), inv, PROXY.getPlayer());
            }));
    public static final RegistryObject<MenuType<MinecartUnLoaderContainer>> MINECART_LOADER_CONTAINER = CONTAINERS.register("minecart_loader_c", () -> IForgeMenuType.create(
            (windowId, inv, data) -> {
                if (data != null) return new MinecartUnLoaderContainer(windowId, PROXY.getWorld(), data.readBlockPos(), inv, PROXY.getPlayer());
                else return new MinecartUnLoaderContainer(windowId, PROXY.getWorld(), inv, PROXY.getPlayer());
            }));
    public static final RegistryObject<MenuType<TankCartContainer>> TANK_CART_CONTAINER = CONTAINERS.register("tank_cart_c", () -> IForgeMenuType.create(
            (windowId, inv, data) -> {
                return new TankCartContainer(windowId, PROXY.getWorld(), inv, PROXY.getPlayer());
            }));
    public static final RegistryObject<MenuType<BatteryCartContainer>> BATTERY_CART_CONTAINER = CONTAINERS.register("battery_cart_c", () -> IForgeMenuType.create(
            (windowId, inv, data) -> {
                return new BatteryCartContainer(windowId, PROXY.getWorld(), inv, PROXY.getPlayer());
            }));
    public static final RegistryObject<MenuType<FlagCartContainer>> FLAG_CART_CONTAINER = CONTAINERS.register("flag_cart_c", () -> IForgeMenuType.create(
            (windowId, inv, data) -> {
                return new FlagCartContainer(windowId, PROXY.getWorld(), inv, PROXY.getPlayer());
            }));

    public static void register(IEventBus bus) {
        CONTAINERS.register(bus);
    }
}
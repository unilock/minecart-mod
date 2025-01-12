package com.alc.moreminecarts.registry;

import com.alc.moreminecarts.MMConstants;
import com.alc.moreminecarts.entities.*;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class MMEntities {
    // Entities
    public static final EntityType<NetMinecartEntity> MINECART_WITH_NET_ENTITY = register("minecart_with_net", EntityType.Builder.<NetMinecartEntity>of(NetMinecartEntity::new, MobCategory.MISC ).sized(0.98F, 0.7F).clientTrackingRange(8).build("minecart_with_net"));
    public static final EntityType<ChunkLoaderCartEntity> CHUNK_LOADER_CART = register("minecart_with_chunk_loader", EntityType.Builder.<ChunkLoaderCartEntity>of(ChunkLoaderCartEntity::new, MobCategory.MISC ).sized(0.98F, 0.7F).clientTrackingRange(8).build("minecart_with_chunk_loader"));
    public static final EntityType<OrbStasisCart> ORB_STASIS_CART = register("minecart_with_stasis", EntityType.Builder.<OrbStasisCart>of(OrbStasisCart::new, MobCategory.MISC ).sized(0.98F, 0.7F).clientTrackingRange(8).build("minecart_with_stasis"));
    public static final EntityType<FlagCartEntity> FLAG_CART = register("flag_cart", EntityType.Builder.<FlagCartEntity>of(FlagCartEntity::new, MobCategory.MISC ).sized(0.98F, 0.7F).clientTrackingRange(8).build("flag_cart"));
    public static final EntityType<CampfireCartEntity> CAMPFIRE_CART_ENTITY = register("campfire_cart", EntityType.Builder.<CampfireCartEntity>of(CampfireCartEntity::new, MobCategory.MISC ).sized(0.98F, 0.7F).clientTrackingRange(8).build("campfire_cart"));
    public static final EntityType<SoulfireCartEntity> SOULFIRE_CART_ENTITY = register("soulfire_cart", EntityType.Builder.<SoulfireCartEntity>of(SoulfireCartEntity::new, MobCategory.MISC ).sized(0.98F, 0.7F).clientTrackingRange(8).build("soulfire_cart"));
    public static EntityType<EndfireCartEntity> ENDFIRE_CART_ENTITY;
    public static final EntityType<WoodenPushcartEntity> WOODEN_PUSHCART_ENTITY = register("wooden_pushcart", EntityType.Builder.<WoodenPushcartEntity>of(WoodenPushcartEntity::new, MobCategory.MISC ).sized(0.98F, 0.7F).clientTrackingRange(8).build("wooden_pushcart"));
    public static final EntityType<IronPushcartEntity> IRON_PUSHCART_ENTITY = register("iron_pushcart", EntityType.Builder.<IronPushcartEntity>of(IronPushcartEntity::new, MobCategory.MISC ).sized(0.98F, 0.7F).clientTrackingRange(8).build("iron_pushcart"));
    public static final EntityType<PistonPushcartEntity> PISTON_PUSHCART_ENTITY = register("piston_pushcart", EntityType.Builder.<PistonPushcartEntity>of(PistonPushcartEntity::new, MobCategory.MISC ).sized(0.98F, 0.7F).clientTrackingRange(8).build("piston_pushcart"));
    public static final EntityType<StickyPistonPushcartEntity> STICKY_PISTON_PUSHCART_ENTITY = register("sticky_piston_pushcart", EntityType.Builder.<StickyPistonPushcartEntity>of(StickyPistonPushcartEntity::new, MobCategory.MISC ).sized(0.98F, 0.7F).clientTrackingRange(8).build("sticky_piston_pushcart"));
    public static final EntityType<TankCartEntity> TANK_CART_ENTITY = register("tank_cart", EntityType.Builder.<TankCartEntity>of(TankCartEntity::new, MobCategory.MISC ).sized(0.98F, 0.7F).clientTrackingRange(8).build("tank_cart"));
    public static final EntityType<BatteryCartEntity> BATTERY_CART_ENTITY = register("battery_cart", EntityType.Builder.<BatteryCartEntity>of(BatteryCartEntity::new, MobCategory.MISC ).sized(0.98F, 0.7F).clientTrackingRange(8).build("battery_cart"));
    public static final EntityType<CouplerEntity> COUPLER_ENTITY = register("coupler", EntityType.Builder.<CouplerEntity>of(CouplerEntity::new, MobCategory.MISC ).sized(0.3F, 0.3F).noSummon().clientTrackingRange(8).build("coupler")); //.setCustomClientFactory(CouplerClientFactory.get()

    // High Speed Cart Entities
    public static final EntityType<HSMinecartEntities.HSMinecart> HS_CART_ENTITY = register("high_speed_minecart", EntityType.Builder.<HSMinecartEntities.HSMinecart>of(HSMinecartEntities.HSMinecart::new, MobCategory.MISC ).sized(0.98F, 0.7F).clientTrackingRange(8).build("high_speed_minecart"));
    public static final EntityType<HSMinecartEntities.HSChestMinecart> HS_CHEST_CART_ENTITY = register("high_speed_chest_minecart", EntityType.Builder.<HSMinecartEntities.HSChestMinecart>of(HSMinecartEntities.HSChestMinecart::new, MobCategory.MISC ).sized(0.98F, 0.7F).clientTrackingRange(8).build("high_speed_chest_minecart"));
    public static final EntityType<HSMinecartEntities.HSTNTMinecart> HS_TNT_CART_ENTITY = register("high_speed_tnt_minecart", EntityType.Builder.<HSMinecartEntities.HSTNTMinecart>of(HSMinecartEntities.HSTNTMinecart::new, MobCategory.MISC ).sized(0.98F, 0.7F).clientTrackingRange(8).build("high_speed_tnt_minecart"));
    public static final EntityType<HSMinecartEntities.HSCommandBlockMinecart> HS_COMMAND_BLOCK_CART_ENTITY = register("high_speed_command_block_minecart", EntityType.Builder.<HSMinecartEntities.HSCommandBlockMinecart>of(HSMinecartEntities.HSCommandBlockMinecart::new, MobCategory.MISC ).sized(0.98F, 0.7F).clientTrackingRange(8).build("high_speed_command_block_minecart"));
    public static final EntityType<HSMinecartEntities.HSHopperMinecart> HS_HOPPER_CART_ENTITY = register("high_speed_hopper_minecart", EntityType.Builder.<HSMinecartEntities.HSHopperMinecart>of(HSMinecartEntities.HSHopperMinecart::new, MobCategory.MISC ).sized(0.98F, 0.7F).clientTrackingRange(8).build("high_speed_hopper_minecart"));
    public static final EntityType<HSMinecartEntities.HSSpawnerMinecart> HS_SPAWNER_CART_ENTITY = register("high_speed_spawner_minecart", EntityType.Builder.<HSMinecartEntities.HSSpawnerMinecart>of(HSMinecartEntities.HSSpawnerMinecart::new, MobCategory.MISC ).sized(0.98F, 0.7F).clientTrackingRange(8).build("high_speed_spawner_minecart"));
    public static final EntityType<HSMinecartEntities.HSFurnaceMinecart> HS_FURNACE_CART_ENTITY = register("high_speed_furnace_minecart", EntityType.Builder.<HSMinecartEntities.HSFurnaceMinecart>of(HSMinecartEntities.HSFurnaceMinecart::new, MobCategory.MISC ).sized(0.98F, 0.7F).clientTrackingRange(8).build("high_speed_furnace_minecart"));
    public static final EntityType<HSMinecartEntities.HSNetMinecart> HS_NET_CART_ENTITY = register("high_speed_net_minecart", EntityType.Builder.<HSMinecartEntities.HSNetMinecart>of(HSMinecartEntities.HSNetMinecart::new, MobCategory.MISC ).sized(0.98F, 0.7F).clientTrackingRange(8).build("high_speed_net_minecart"));
    public static final EntityType<HSMinecartEntities.HSChunkLoaderMinecart> HS_CHUNK_LOADER_CART_ENTITY = register("high_speed_chunk_loader_minecart", EntityType.Builder.<HSMinecartEntities.HSChunkLoaderMinecart>of(HSMinecartEntities.HSChunkLoaderMinecart::new, MobCategory.MISC ).sized(0.98F, 0.7F).clientTrackingRange(8).build("high_speed_chunk_loader_minecart"));
    public static final EntityType<HSMinecartEntities.HSStasisMinecart> HS_STASIS_CART_ENTITY = register("high_speed_stasis_minecart", EntityType.Builder.<HSMinecartEntities.HSStasisMinecart>of(HSMinecartEntities.HSStasisMinecart::new, MobCategory.MISC ).sized(0.98F, 0.7F).clientTrackingRange(8).build("high_speed_stasis_minecart"));
    public static final EntityType<HSMinecartEntities.HSFlagMinecart> HS_FLAG_CART_ENTITY = register("high_speed_flag_minecart", EntityType.Builder.<HSMinecartEntities.HSFlagMinecart>of(HSMinecartEntities.HSFlagMinecart::new, MobCategory.MISC ).sized(0.98F, 0.7F).clientTrackingRange(8).build("high_speed_flag_minecart"));
    public static final EntityType<HSMinecartEntities.HSTankMinecart> HS_TANK_CART_ENTITY = register("high_speed_tank_minecart", EntityType.Builder.<HSMinecartEntities.HSTankMinecart>of(HSMinecartEntities.HSTankMinecart::new, MobCategory.MISC ).sized(0.98F, 0.7F).clientTrackingRange(8).build("high_speed_tank_minecart"));
    public static final EntityType<HSMinecartEntities.HSBatteryMinecart> HS_BATTERY_CART_ENTITY = register("high_speed_battery_minecart", EntityType.Builder.<HSMinecartEntities.HSBatteryMinecart>of(HSMinecartEntities.HSBatteryMinecart::new, MobCategory.MISC ).sized(0.98F, 0.7F).clientTrackingRange(8).build("high_speed_battery_minecart"));
    public static final EntityType<HSMinecartEntities.HSCampfireMinecart> HS_CAMPFIRE_CART_ENTITY = register("high_speed_campfire_minecart", EntityType.Builder.<HSMinecartEntities.HSCampfireMinecart>of(HSMinecartEntities.HSCampfireMinecart::new, MobCategory.MISC ).sized(0.98F, 0.7F).clientTrackingRange(8).build("high_speed_campfire_minecart"));
    public static final EntityType<HSMinecartEntities.HSSoulfireMinecart> HS_SOULFIRE_CART_ENTITY = register("high_speed_soulfire_minecart", EntityType.Builder.<HSMinecartEntities.HSSoulfireMinecart>of(HSMinecartEntities.HSSoulfireMinecart::new, MobCategory.MISC ).sized(0.98F, 0.7F).clientTrackingRange(8).build("high_speed_soulfire_minecart"));
    public static EntityType<HSMinecartEntities.HSEndfireMinecart> HS_ENDFIRE_CART_ENTITY;
    public static final EntityType<HSMinecartEntities.HSPushcart> HS_PUSHCART_ENTITY = register("high_speed_pushcart", EntityType.Builder.<HSMinecartEntities.HSPushcart>of(HSMinecartEntities.HSPushcart::new, MobCategory.MISC ).sized(0.98F, 0.7F).clientTrackingRange(8).build("high_speed_pushcart"));
    public static final EntityType<HSMinecartEntities.HSPistonPushcart> HS_PISTON_PUSHCART_ENTITY = register("high_speed_piston_pushcart", EntityType.Builder.<HSMinecartEntities.HSPistonPushcart>of(HSMinecartEntities.HSPistonPushcart::new, MobCategory.MISC ).sized(0.98F, 0.7F).clientTrackingRange(8).build("high_speed_piston_pushcart"));
    public static final EntityType<HSMinecartEntities.HSStickyPistonPushcart> HS_STICKY_PISTON_PUSHCART_ENTITY = register("high_speed_sticky_piston_pushcart", EntityType.Builder.<HSMinecartEntities.HSStickyPistonPushcart>of(HSMinecartEntities.HSStickyPistonPushcart::new, MobCategory.MISC ).sized(0.98F, 0.7F).clientTrackingRange(8).build("high_speed_sticky_piston_pushcart"));

    public static void register() {
    }

    private static <T extends Entity> EntityType<T> register(String path, EntityType<T> type) {
        return Registry.register(BuiltInRegistries.ENTITY_TYPE, MMConstants.id(path), type);
    }
}

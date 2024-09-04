package com.alc.moreminecarts.registry;

import com.alc.moreminecarts.MMConstants;
import com.alc.moreminecarts.tile_entities.*;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class MMTileEntities {
    // Tile Entities
    public static final BlockEntityType<ChunkLoaderTile> CHUNK_LOADER_TILE_ENTITY = register("chunk_loader_te", BlockEntityType.Builder.of(ChunkLoaderTile::new, MMBlocks.CHUNK_LOADER_BLOCK).build(null));
    public static final BlockEntityType<LockingRailTile> LOCKING_RAIL_TILE_ENTITY = register("locking_rail_te", BlockEntityType.Builder.of(LockingRailTile::new, MMBlocks.LOCKING_RAIL_BLOCK).build(null));
    public static final BlockEntityType<PoweredLockingRailTile> POWERED_LOCKING_RAIL_TILE_ENTITY = register("powered_locking_rail_te", BlockEntityType.Builder.of(PoweredLockingRailTile::new, MMBlocks.POWERED_LOCKING_RAIL_BLOCK).build(null));
    public static final BlockEntityType<MinecartLoaderTile> MINECART_LOADER_TILE_ENTITY = register("minecart_loader_te", BlockEntityType.Builder.of(MinecartLoaderTile::new, MMBlocks.MINECART_LOADER_BLOCK).build(null));
    public static final BlockEntityType<MinecartUnloaderTile> MINECART_UNLOADER_TILE_ENTITY = register("minecart_unloader_te", BlockEntityType.Builder.of(MinecartUnloaderTile::new, MMBlocks.MINECART_UNLOADER_BLOCK).build(null));
    public static final BlockEntityType<FilterUnloaderTile> FILTER_UNLOADER_TILE_ENTITY = register("filter_unloader_te", BlockEntityType.Builder.of(FilterUnloaderTile::new, MMBlocks.FILTER_UNLOADER_BLOCK).build(null));
    public static final BlockEntityType<OrbStasisTile> PEARL_STASIS_CHAMBER_TILE_ENTITY = register("pearl_stasis_chamber_te", BlockEntityType.Builder.<OrbStasisTile>of(OrbStasisTile::new, MMBlocks.PEARL_STASIS_CHAMBER).build(null));

    public static void register() {
    }

    private static <T extends BlockEntity> BlockEntityType<T> register(String path, BlockEntityType<T> type) {
        return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, MMConstants.id(path), type);
    }
}

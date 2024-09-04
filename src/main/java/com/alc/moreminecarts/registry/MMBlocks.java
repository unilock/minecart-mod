package com.alc.moreminecarts.registry;

import com.alc.moreminecarts.MMConstants;
import com.alc.moreminecarts.MoreMinecartsMod;
import com.alc.moreminecarts.blocks.GlassCactusBlock;
import com.alc.moreminecarts.blocks.OrbStasisBlock;
import com.alc.moreminecarts.blocks.PistonDisplayBlock;
import com.alc.moreminecarts.blocks.containers.ChunkLoaderBlock;
import com.alc.moreminecarts.blocks.containers.FilterUnloaderBlock;
import com.alc.moreminecarts.blocks.containers.MinecartLoaderBlock;
import com.alc.moreminecarts.blocks.containers.MinecartUnloaderBlock;
import com.alc.moreminecarts.blocks.holo_scaffolds.ChaoticHoloScaffold;
import com.alc.moreminecarts.blocks.holo_scaffolds.HoloScaffold;
import com.alc.moreminecarts.blocks.holographic_rails.*;
import com.alc.moreminecarts.blocks.parallel_rails.MaglevParallelRail;
import com.alc.moreminecarts.blocks.parallel_rails.ParallelRail;
import com.alc.moreminecarts.blocks.parallel_rails.WoodenParallelRail;
import com.alc.moreminecarts.blocks.powered_rails.PoweredLightspeedRail;
import com.alc.moreminecarts.blocks.powered_rails.PoweredMaglevRail;
import com.alc.moreminecarts.blocks.rail_crossings.LightspeedRailCrossing;
import com.alc.moreminecarts.blocks.rail_crossings.MaglevRailCrossing;
import com.alc.moreminecarts.blocks.rail_crossings.RailCrossing;
import com.alc.moreminecarts.blocks.rail_crossings.WoodenRailCrossing;
import com.alc.moreminecarts.blocks.rail_turns.MaglevRailTurn;
import com.alc.moreminecarts.blocks.rail_turns.RailTurn;
import com.alc.moreminecarts.blocks.rail_turns.WoodenRailTurn;
import com.alc.moreminecarts.blocks.rails.LightspeedRail;
import com.alc.moreminecarts.blocks.rails.MaglevRail;
import com.alc.moreminecarts.blocks.rails.WoodenRail;
import com.alc.moreminecarts.blocks.utility_rails.*;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.MapColor;

import java.util.HashMap;
import java.util.Map;
import java.util.function.ToIntFunction;

public class MMBlocks {
    // Rail Blocks
    public static final Block RAIL_TURN = register("rail_turn", new RailTurn(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).noCollission().strength(0.7F).sound(SoundType.METAL)));
    public static final Block PARALLEL_RAIL_BLOCK = register("parallel_rail", new ParallelRail(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).noCollission().strength(0.7F).sound(SoundType.METAL)));
    public static final Block CROSS_RAIL_BLOCK = register("cross_rail", new RailCrossing(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).noCollission().strength(0.7F).sound(SoundType.METAL)));
    public static final Block PROJECTOR_RAIL = register("projector_rail", new ProjectorRail(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).noCollission().strength(0.7F).sound(SoundType.METAL)));
    public static final Block HOLOGRAM_RAIL = register("hologram_rail", new HolographicRail(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).noCollission().strength(0.2F).sound(SoundType.GLASS)));
    public static final Block WOODEN_RAIL_BLOCK = register("wooden_rail", new WoodenRail(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).ignitedByLava().noCollission().strength(0.7F).sound(SoundType.BAMBOO)));
    public static final Block WOODEN_RAIL_TURN = register("wooden_rail_turn", new WoodenRailTurn(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).ignitedByLava().noCollission().strength(0.7F).sound(SoundType.BAMBOO)));
    public static final Block WOODEN_PARALLEL_RAIL_BLOCK = register("wooden_parallel_rail", new WoodenParallelRail(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).ignitedByLava().noCollission().strength(0.7F).sound(SoundType.BAMBOO)));
    public static final Block WOODEN_CROSS_RAIL_BLOCK = register("wooden_cross_rail", new WoodenRailCrossing(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).ignitedByLava().noCollission().strength(0.7F).sound(SoundType.BAMBOO)));
    public static final Block WOODEN_PROJECTOR_RAIL = register("wooden_projector_rail", new WoodenProjectorRail(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).noCollission().strength(0.7F).sound(SoundType.BAMBOO)));
    public static final Block WOODEN_HOLOGRAM_RAIL = register("wooden_hologram_rail", new WoodenHolographicRail(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).noCollission().strength(0.2F).sound(SoundType.GLASS)));
    public static final Block MAGLEV_RAIL_BLOCK = register("maglev_rail", new MaglevRail(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLUE).noCollission().strength(0.7F).sound(SoundType.METAL)));
    public static final Block MAGLEV_RAIL_TURN = register("maglev_rail_turn", new MaglevRailTurn(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLUE).noCollission().strength(0.7F).sound(SoundType.METAL)));
    public static final Block MAGLEV_PARALLEL_RAIL_BLOCK = register("maglev_parallel_rail", new MaglevParallelRail(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLUE).noCollission().strength(0.7F).sound(SoundType.METAL)));
    public static final Block MAGLEV_CROSS_RAIL_BLOCK = register("maglev_cross_rail", new MaglevRailCrossing(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLUE).noCollission().strength(0.7F).sound(SoundType.METAL)));
    public static final Block MAGLEV_PROJECTOR_RAIL = register("maglev_projector_rail", new MaglevProjectorRail(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLUE).noCollission().strength(0.7F).sound(SoundType.METAL)));
    public static final Block MAGLEV_HOLOGRAM_RAIL = register("maglev_hologram_rail", new MaglevHolographicRail(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLUE).noCollission().strength(0.2F).sound(SoundType.GLASS)));
    public static final Block MAGLEV_POWERED_RAIL_BLOCK = register("maglev_powered_rail", new PoweredMaglevRail(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLUE).noCollission().strength(0.7F).sound(SoundType.METAL)));
    public static final Block LIGHTSPEED_RAIL_BLOCK = register("lightspeed_rail", new LightspeedRail(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLUE).noCollission().strength(1F).sound(SoundType.METAL)));
    public static final Block LIGHTSPEED_CROSS_RAIL_BLOCK = register("lightspeed_cross_rail", new LightspeedRailCrossing(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLUE).noCollission().strength(1F).sound(SoundType.METAL)));
    public static final Block LIGHTSPEED_POWERED_RAIL_BLOCK = register("lightspeed_powered_rail", new PoweredLightspeedRail(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLUE).noCollission().strength(1F).sound(SoundType.METAL)));
    public static final Block BIOLUMINESCENT_RAIL_BLOCK = register("bioluminescent_rail", new WoodenRail(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_YELLOW).noCollission().strength(0.7F).sound(SoundType.BAMBOO).lightLevel((state)->10)));
    public static final Block LOCKING_RAIL_BLOCK = register("locking_rail", new LockingRailBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLUE).noCollission().strength(0.7F).sound(SoundType.METAL)));
    public static final Block POWERED_LOCKING_RAIL_BLOCK = register("powered_locking_rail", new PoweredLockingRailBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).noCollission().strength(0.7F).sound(SoundType.METAL)));
    public static final Block PISTON_LIFTER_RAIL = register("piston_lifter_rail", new PistonLifterRailBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).noCollission().strength(0.7F).sound(SoundType.METAL)));
    public static final Block ARITHMETIC_RAIL = register("arithmetic_rail", new ArithmeticRailBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).noCollission().strength(0.7F).sound(SoundType.METAL)));

    // Container Blocks
    public static final Block CHUNK_LOADER_BLOCK = register("chunk_loader", new ChunkLoaderBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).strength(5f).noOcclusion().lightLevel(poweredBlockEmission(13))));
    public static final Block MINECART_LOADER_BLOCK = register("minecart_loader", new MinecartLoaderBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY).strength(1f)));
    public static final Block MINECART_UNLOADER_BLOCK = register("minecart_unloader", new MinecartUnloaderBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY).strength(1f)));
    public static final Block FILTER_UNLOADER_BLOCK = register("filter_unloader", new FilterUnloaderBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY).strength(1f)));
    public static final Block PEARL_STASIS_CHAMBER = register("pearl_stasis_chamber", new OrbStasisBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).strength(5f).noOcclusion()));

    // Other Blocks
    public static final Block SILICA_STEEL_BLOCK = register("silica_steel_block", new Block( BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY).strength(3f,3f).requiresCorrectToolForDrops().sound(SoundType.METAL)));
    public static final Block CHUNKRODITE_BLOCK = register("chunkrodite_block", new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).strength(2f, 2f)));
    public static final Block CORRUGATED_SILICA_STEEL = register("corrugated_silica_steel", new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY).requiresCorrectToolForDrops().sound(SoundType.METAL).strength(2f, 2f)));
    public static final Block SILICA_STEEL_PILLAR = register("silica_steel_pillar", new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY).requiresCorrectToolForDrops().sound(SoundType.METAL).strength(2f, 2f)));
    public static final Block ORGANIC_GLASS = register("organic_glass", new GlassBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).strength(0.3F).sound(SoundType.GLASS).noOcclusion().isValidSpawn((a, b, c, d)->false).isRedstoneConductor((a, b, c)->false).isSuffocating((a, b, c)->false).isViewBlocking((a, b, c)->false)));
    public static final Block ORGANIC_GLASS_PANE = register("organic_glass_pane", new IronBarsBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).strength(0.3F).sound(SoundType.GLASS).noOcclusion().isValidSpawn((a, b, c, d)->false).isRedstoneConductor((a, b, c)->false).isSuffocating((a, b, c)->false).isViewBlocking((a, b, c)->false)));
    public static final Block CHISELED_ORGANIC_GLASS = register("chiseled_organic_glass", new GlassBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).strength(0.3F).sound(SoundType.GLASS).noOcclusion().isValidSpawn((a,b,c,d)->false).isRedstoneConductor((a,b,c)->false).isSuffocating((a,b,c)->false).isViewBlocking((a,b,c)->false)));
    public static final Block CHISELED_ORGANIC_GLASS_PANE = register("chiseled_organic_glass_pane", new IronBarsBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).strength(0.3F).sound(SoundType.GLASS).noOcclusion().isValidSpawn((a,b,c,d)->false).isRedstoneConductor((a,b,c)->false).isSuffocating((a,b,c)->false).isViewBlocking((a,b,c)->false)));
    public static final Block GLASS_CACTUS = register("glass_cactus", new GlassCactusBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GREEN).randomTicks().strength(2F).sound(SoundType.WOOL).noOcclusion()));
    public static final Block HOLO_SCAFFOLD_GENERATOR = register("holo_scaffold_generator", new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY).strength(3f,3f).lightLevel((state) -> 13)));
    public static final Block HOLO_SCAFFOLD = register("holo_scaffold", new HoloScaffold(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).strength(0.05F).noOcclusion().dynamicShape()));
    public static final Block CHAOTIC_HOLO_SCAFFOLD = register("chaotic_holo_scaffold", new ChaoticHoloScaffold(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).strength(0.05F).noOcclusion().dynamicShape()));
    public static final Block PISTON_DISPLAY_BLOCK = register("piston_display_block", new PistonDisplayBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN)));

    // Potted Plants
    public static final Block POTTED_GLASS_CACTUS = register("potted_glass_cactus", new FlowerPotBlock(MMBlocks.GLASS_CACTUS, BlockBehaviour.Properties.of().instabreak().noOcclusion()));
    public static final Block POTTED_BEET = register("potted_beet", new FlowerPotBlock(MMBlocks.CHUNKRODITE_BLOCK, BlockBehaviour.Properties.of().instabreak().noOcclusion()));


    // Color Detector Rail Blocks
    public static final Map<DyeColor, Block> COLOR_DETECTOR_RAILS = new HashMap<>();
    static {
        for (DyeColor color : MoreMinecartsMod.dyeColorsByRainbow) {
            Block entry = register("color_detector_rail_" + color.getName(), new ColorDetectorRailBlock(BlockBehaviour.Properties.of().mapColor(color).noCollission().strength(0.7F).sound(SoundType.METAL), () -> MMItems.RAIL_SIGNALS.get(color)));
            COLOR_DETECTOR_RAILS.put(color, entry);
        }
    }

    // Taken from Blocks
    private static ToIntFunction<BlockState> poweredBlockEmission(int p_235420_0_) {
        return (p_235421_1_) -> {
            return p_235421_1_.getValue(BlockStateProperties.POWERED) ? p_235420_0_ : 0;
        };
    }

    public static void register() {
    }

    private static <T extends Block> T register(String path, T block) {
        return Registry.register(BuiltInRegistries.BLOCK, MMConstants.id(path), block);
    }
}

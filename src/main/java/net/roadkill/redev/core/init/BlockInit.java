package net.roadkill.redev.core.init;

import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.roadkill.redev.ReDev;
import net.roadkill.redev.common.block.*;
import net.roadkill.redev.common.world.tree.ShadeTree;
import net.roadkill.redev.util.registries.ModBlocks;
import net.roadkill.redev.util.registries.ModSoundType;

import java.util.Optional;
import java.util.function.Supplier;

public class BlockInit
{
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ReDev.MOD_ID);

    private static final Supplier<BlockBehaviour.Properties> SCRAPWOOD_PROPERTIES = () -> BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.NETHER_WOOD).instrument(NoteBlockInstrument.BASS).mapColor(MapColor.CRIMSON_HYPHAE);
    private static final Supplier<BlockBehaviour.Properties> WHISPUR_PROPERTIES = () -> BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.NETHER_WOOD).instrument(NoteBlockInstrument.BASS).mapColor(MapColor.SAND);
    private static final Supplier<BlockBehaviour.Properties> PETRIFIED_PROPERTIES = () -> BlockBehaviour.Properties.of().strength(3.0F, 8.0F).sound(ModSoundType.PETRIFIED_PLANKS).instrument(NoteBlockInstrument.BASS).mapColor(MapColor.TERRACOTTA_PURPLE);
    private static final Supplier<BlockBehaviour.Properties> SHADE_PROPERTIES = () -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).mapColor(MapColor.TERRACOTTA_ORANGE);
    private static final Supplier<BlockBehaviour.Properties> DOOR_PROPERTIES = () -> BlockBehaviour.Properties.of().noOcclusion();

    public static final DeferredHolder<Block, Block> SCRAPWOOD_PLANKS = BLOCKS.registerBlock("scrapwood_planks", Block::new, SCRAPWOOD_PROPERTIES.get());
    public static final DeferredHolder<Block, Block> WHISPUR_PLANKS = BLOCKS.registerBlock("whispur_planks", Block::new, WHISPUR_PROPERTIES.get());
    public static final DeferredHolder<Block, Block> PETRIFIED_PLANKS = BLOCKS.registerBlock("petrified_planks", Block::new, PETRIFIED_PROPERTIES.get());
    public static final DeferredHolder<Block, Block> SHADE_PLANKS = BLOCKS.registerBlock("shade_planks", Block::new, SHADE_PROPERTIES.get());

    public static final DeferredHolder<Block, RotatedPillarBlock> STRIPPED_PETRIFIED_LOG = BLOCKS.registerBlock("stripped_petrified_log", RotatedPillarBlock::new, Block.Properties.of().strength(2.0F).sound(ModSoundType.PETRIFIED_LOG).mapColor(MapColor.TERRACOTTA_ORANGE));
    public static final DeferredHolder<Block, RotatedPillarBlock> STRIPPED_SHADE_LOG = BLOCKS.registerBlock("stripped_shade_log", RotatedPillarBlock::new, Block.Properties.of().strength(2.0F).sound(SoundType.WOOD).mapColor(MapColor.TERRACOTTA_ORANGE));

    public static final DeferredHolder<Block, RotatedPillarBlock> STRIPPED_PETRIFIED_WOOD = BLOCKS.registerBlock("stripped_petrified_wood", RotatedPillarBlock::new, Block.Properties.of().strength(2.0F).sound(ModSoundType.PETRIFIED_LOG).mapColor(MapColor.TERRACOTTA_PURPLE));
    public static final DeferredHolder<Block, RotatedPillarBlock> STRIPPED_SHADE_WOOD = BLOCKS.registerBlock("stripped_shade_wood", RotatedPillarBlock::new, Block.Properties.of().strength(2.0F).sound(SoundType.WOOD).mapColor(MapColor.TERRACOTTA_ORANGE));

    public static final DeferredHolder<Block, RotatedPillarBlock> SCRAPWOOD_HEAP = BLOCKS.registerBlock("scrapwood_heap", RotatedPillarBlock::new, Block.Properties.ofFullCopy(Blocks.CRIMSON_STEM).strength(2.0F, 3.0F).sound(SoundType.NETHER_WOOD));
    public static final DeferredHolder<Block, StrippableLogBlock> PETRIFIED_LOG = BLOCKS.registerBlock("petrified_log", props -> new StrippableLogBlock(props, STRIPPED_PETRIFIED_LOG.value()), Block.Properties.ofFullCopy(Blocks.OAK_LOG).strength(3.0F, 8.0F).sound(ModSoundType.PETRIFIED_LOG));
    public static final DeferredHolder<Block, RotatedPillarBlock> SHADE_LOG = BLOCKS.registerBlock("shade_log", props -> new StrippableLogBlock(props, STRIPPED_SHADE_LOG.value()), Block.Properties.of().strength(2.0F).sound(SoundType.WOOD).mapColor(MapColor.TERRACOTTA_BROWN));

    public static final DeferredHolder<Block, RotatedPillarBlock> PETRIFIED_WOOD = BLOCKS.registerBlock("petrified_wood", props -> new StrippableLogBlock(props, STRIPPED_PETRIFIED_WOOD.value()), Block.Properties.ofFullCopy(Blocks.OAK_WOOD).strength(3.0F, 8.0F).sound(ModSoundType.PETRIFIED_LOG).mapColor(MapColor.TERRACOTTA_PURPLE));
    public static final DeferredHolder<Block, RotatedPillarBlock> SHADE_WOOD = BLOCKS.registerBlock("shade_wood", props -> new StrippableLogBlock(props, STRIPPED_SHADE_WOOD.value()), Block.Properties.of().strength(2.0F).sound(SoundType.WOOD).mapColor(MapColor.TERRACOTTA_ORANGE));

    public static final DeferredHolder<Block, SlabBlock> SCRAPWOOD_SLAB = BLOCKS.registerBlock("scrapwood_slab", SlabBlock::new, SCRAPWOOD_PROPERTIES.get());
    public static final DeferredHolder<Block, SlabBlock> WHISPUR_SLAB = BLOCKS.registerBlock("whispur_slab", SlabBlock::new, WHISPUR_PROPERTIES.get());
    public static final DeferredHolder<Block, SlabBlock> PETRIFIED_SLAB = BLOCKS.registerBlock("petrified_slab", SlabBlock::new, PETRIFIED_PROPERTIES.get());
    public static final DeferredHolder<Block, SlabBlock> SHADE_SLAB = BLOCKS.registerBlock("shade_slab", SlabBlock::new, SHADE_PROPERTIES.get());

    public static final DeferredHolder<Block, StairBlock> SCRAPWOOD_STAIRS = BLOCKS.registerBlock("scrapwood_stairs", props -> new StairBlock(SCRAPWOOD_PLANKS.value().defaultBlockState(), props), SCRAPWOOD_PROPERTIES.get());
    public static final DeferredHolder<Block, StairBlock> WHISPUR_STAIRS = BLOCKS.registerBlock("whispur_stairs", props -> new StairBlock(WHISPUR_PLANKS.value().defaultBlockState(), props), WHISPUR_PROPERTIES.get());
    public static final DeferredHolder<Block, StairBlock> PETRIFIED_STAIRS = BLOCKS.registerBlock("petrified_stairs", props -> new StairBlock(PETRIFIED_PLANKS.value().defaultBlockState(), props), PETRIFIED_PROPERTIES.get());
    public static final DeferredHolder<Block, StairBlock> SHADE_STAIRS = BLOCKS.registerBlock("shade_stairs", props -> new StairBlock(SHADE_PLANKS.value().defaultBlockState(), props), SHADE_PROPERTIES.get());

    public static final DeferredHolder<Block, FenceBlock> SCRAPWOOD_FENCE = BLOCKS.registerBlock("scrapwood_fence", FenceBlock::new, SCRAPWOOD_PROPERTIES.get().forceSolidOn());
    public static final DeferredHolder<Block, FenceBlock> WHISPUR_FENCE = BLOCKS.registerBlock("whispur_fence", FenceBlock::new, WHISPUR_PROPERTIES.get().forceSolidOn());
    public static final DeferredHolder<Block, FenceBlock> PETRIFIED_FENCE = BLOCKS.registerBlock("petrified_fence", FenceBlock::new, PETRIFIED_PROPERTIES.get().forceSolidOn());
    public static final DeferredHolder<Block, FenceBlock> SHADE_FENCE = BLOCKS.registerBlock("shade_fence", FenceBlock::new, SHADE_PROPERTIES.get().forceSolidOn());

    public static final DeferredHolder<Block, FenceGateBlock> SCRAPWOOD_FENCE_GATE = BLOCKS.registerBlock("scrapwood_fence_gate", props -> new FenceGateBlock(WoodType.CRIMSON, props), SCRAPWOOD_PROPERTIES.get().forceSolidOn());
    public static final DeferredHolder<Block, FenceGateBlock> WHISPUR_FENCE_GATE = BLOCKS.registerBlock("whispur_fence_gate", props -> new FenceGateBlock(WoodType.WARPED, props), WHISPUR_PROPERTIES.get().forceSolidOn());
    public static final DeferredHolder<Block, FenceGateBlock> PETRIFIED_FENCE_GATE = BLOCKS.registerBlock("petrified_fence_gate", props -> new FenceGateBlock(WoodType.OAK, props), PETRIFIED_PROPERTIES.get().forceSolidOn());
    public static final DeferredHolder<Block, FenceGateBlock> SHADE_FENCE_GATE = BLOCKS.registerBlock("shade_fence_gate", props -> new FenceGateBlock(WoodType.OAK, props), SHADE_PROPERTIES.get().forceSolidOn());

    public static final DeferredHolder<Block, DoorBlock> SCRAPWOOD_DOOR = BLOCKS.registerBlock("scrapwood_door", props -> new DoorBlock(ModBlocks.BlockSets.SCRAPWOOD, props), SCRAPWOOD_PROPERTIES.get().noOcclusion());
    public static final DeferredHolder<Block, DoorBlock> WHISPUR_DOOR = BLOCKS.registerBlock("whispur_door", props -> new DoorBlock(ModBlocks.BlockSets.WHISPUR, props), WHISPUR_PROPERTIES.get().noOcclusion());
    public static final DeferredHolder<Block, DoorBlock> PETRIFIED_DOOR = BLOCKS.registerBlock("petrified_door", props -> new DoorBlock(ModBlocks.BlockSets.PETRIFIED, props), PETRIFIED_PROPERTIES.get().noOcclusion());
    public static final DeferredHolder<Block, DoorBlock> SHADE_DOOR = BLOCKS.registerBlock("shade_door", props -> new DoorBlock(ModBlocks.BlockSets.SHADE, props), SHADE_PROPERTIES.get().noOcclusion());

    public static final DeferredHolder<Block, TrapDoorBlock> SCRAPWOOD_TRAPDOOR = BLOCKS.registerBlock("scrapwood_trapdoor", props -> new TrapDoorBlock(ModBlocks.BlockSets.SCRAPWOOD, props), SCRAPWOOD_PROPERTIES.get().noOcclusion());
    public static final DeferredHolder<Block, TrapDoorBlock> WHISPUR_TRAPDOOR = BLOCKS.registerBlock("whispur_trapdoor", props -> new TrapDoorBlock(ModBlocks.BlockSets.WHISPUR, props), WHISPUR_PROPERTIES.get().noOcclusion());
    public static final DeferredHolder<Block, TrapDoorBlock> PETRIFIED_TRAPDOOR = BLOCKS.registerBlock("petrified_trapdoor", props -> new TrapDoorBlock(ModBlocks.BlockSets.PETRIFIED, props), PETRIFIED_PROPERTIES.get().noOcclusion());
    public static final DeferredHolder<Block, TrapDoorBlock> SHADE_TRAPDOOR = BLOCKS.registerBlock("shade_trapdoor", props -> new TrapDoorBlock(ModBlocks.BlockSets.SHADE, props), SHADE_PROPERTIES.get().noOcclusion());

    public static final DeferredHolder<Block, ButtonBlock> SCRAPWOOD_BUTTON = BLOCKS.registerBlock("scrapwood_button", props -> new ButtonBlock(ModBlocks.BlockSets.SCRAPWOOD, 30, props), SCRAPWOOD_PROPERTIES.get().strength(0.5f).noCollission());
    public static final DeferredHolder<Block, ButtonBlock> WHISPUR_BUTTON = BLOCKS.registerBlock("whispur_button", props -> new ButtonBlock(ModBlocks.BlockSets.WHISPUR, 30, props), WHISPUR_PROPERTIES.get().strength(0.5f).noCollission());
    public static final DeferredHolder<Block, ButtonBlock> PETRIFIED_BUTTON = BLOCKS.registerBlock("petrified_button", props -> new ButtonBlock(ModBlocks.BlockSets.PETRIFIED, 30, props), PETRIFIED_PROPERTIES.get().strength(0.5f).noCollission());
    public static final DeferredHolder<Block, ButtonBlock> SHADE_BUTTON = BLOCKS.registerBlock("shade_button", props -> new ButtonBlock(ModBlocks.BlockSets.SHADE, 30, props), SHADE_PROPERTIES.get().strength(0.5f).noCollission());

    public static final DeferredHolder<Block, PressurePlateBlock> SCRAPWOOD_PRESSURE_PLATE = BLOCKS.registerBlock("scrapwood_pressure_plate", props -> new PressurePlateBlock(ModBlocks.BlockSets.SCRAPWOOD, props), SCRAPWOOD_PROPERTIES.get().destroyTime(1.2f).noCollission());
    public static final DeferredHolder<Block, PressurePlateBlock> WHISPUR_PRESSURE_PLATE = BLOCKS.registerBlock("whispur_pressure_plate", props -> new PressurePlateBlock(ModBlocks.BlockSets.WHISPUR, props), WHISPUR_PROPERTIES.get().destroyTime(1.2f).noCollission());
    public static final DeferredHolder<Block, PressurePlateBlock> PETRIFIED_PRESSURE_PLATE = BLOCKS.registerBlock("petrified_pressure_plate", props -> new PressurePlateBlock(ModBlocks.BlockSets.PETRIFIED, props), PETRIFIED_PROPERTIES.get().destroyTime(1.2f).noCollission());
    public static final DeferredHolder<Block, PressurePlateBlock> SHADE_PRESSURE_PLATE = BLOCKS.registerBlock("shade_pressure_plate", props -> new PressurePlateBlock(ModBlocks.BlockSets.SHADE, props), SHADE_PROPERTIES.get().destroyTime(1.2f).noCollission());

    public static final DeferredHolder<Block, StandingSignBlock> SCRAPWOOD_SIGN = BLOCKS.registerBlock("scrapwood_sign", props -> new StandingSignBlock(ModBlocks.WoodTypes.SCRAPWOOD, props), SCRAPWOOD_PROPERTIES.get());
    public static final DeferredHolder<Block, StandingSignBlock> WHISPUR_SIGN = BLOCKS.registerBlock("whispur_sign", props -> new StandingSignBlock(ModBlocks.WoodTypes.WHISPUR, props), WHISPUR_PROPERTIES.get());
    public static final DeferredHolder<Block, StandingSignBlock> PETRIFIED_SIGN = BLOCKS.registerBlock("petrified_sign", props -> new StandingSignBlock(ModBlocks.WoodTypes.PETRIFIED, props), Block.Properties.ofFullCopy(Blocks.OAK_SIGN).strength(3.0F, 8.0F).sound(ModSoundType.PETRIFIED_PLANKS).mapColor(MapColor.TERRACOTTA_PURPLE));
    public static final DeferredHolder<Block, StandingSignBlock> SHADE_SIGN = BLOCKS.registerBlock("shade_sign", props -> new StandingSignBlock(ModBlocks.WoodTypes.SHADE, props), Block.Properties.of().strength(2.0F).sound(SoundType.WOOD));

    public static final DeferredHolder<Block, WallSignBlock> SCRAPWOOD_WALL_SIGN = BLOCKS.registerBlock("scrapwood_wall_sign", props -> new WallSignBlock(ModBlocks.WoodTypes.SCRAPWOOD, props), SCRAPWOOD_PROPERTIES.get());
    public static final DeferredHolder<Block, WallSignBlock> WHISPUR_WALL_SIGN = BLOCKS.registerBlock("whispur_wall_sign", props -> new WallSignBlock(ModBlocks.WoodTypes.WHISPUR, props), WHISPUR_PROPERTIES.get());
    public static final DeferredHolder<Block, WallSignBlock> PETRIFIED_WALL_SIGN = BLOCKS.registerBlock("petrified_wall_sign", props -> new WallSignBlock(ModBlocks.WoodTypes.PETRIFIED, props), Block.Properties.ofFullCopy(Blocks.OAK_WALL_SIGN).strength(3.0F, 8.0F).sound(ModSoundType.PETRIFIED_PLANKS).mapColor(MapColor.TERRACOTTA_PURPLE));
    public static final DeferredHolder<Block, WallSignBlock> SHADE_WALL_SIGN = BLOCKS.registerBlock("shade_wall_sign", props -> new WallSignBlock(ModBlocks.WoodTypes.SHADE, props), Block.Properties.of().strength(2.0F).sound(SoundType.WOOD).mapColor(MapColor.TERRACOTTA_ORANGE));

    public static final DeferredHolder<Block, Block> ACACIA_BOOKSHELF = BLOCKS.registerBlock("acacia_bookshelf", Block::new, Block.Properties.ofFullCopy(Blocks.BOOKSHELF));
    public static final DeferredHolder<Block, Block> BIRCH_BOOKSHELF = BLOCKS.registerBlock("birch_bookshelf", Block::new, Block.Properties.ofFullCopy(Blocks.BOOKSHELF));
    public static final DeferredHolder<Block, Block> CRIMSON_BOOKSHELF = BLOCKS.registerBlock("crimson_bookshelf", Block::new, Block.Properties.ofFullCopy(Blocks.BOOKSHELF));
    public static final DeferredHolder<Block, Block> DARK_OAK_BOOKSHELF = BLOCKS.registerBlock("dark_oak_bookshelf", Block::new, Block.Properties.ofFullCopy(Blocks.BOOKSHELF));
    public static final DeferredHolder<Block, Block> JUNGLE_BOOKSHELF = BLOCKS.registerBlock("jungle_bookshelf", Block::new, Block.Properties.ofFullCopy(Blocks.BOOKSHELF));
    public static final DeferredHolder<Block, Block> MANGROVE_BOOKSHELF = BLOCKS.registerBlock("mangrove_bookshelf", Block::new, Block.Properties.ofFullCopy(Blocks.BOOKSHELF));
    public static final DeferredHolder<Block, Block> SPRUCE_BOOKSHELF = BLOCKS.registerBlock("spruce_bookshelf", Block::new, Block.Properties.ofFullCopy(Blocks.BOOKSHELF));
    public static final DeferredHolder<Block, Block> WARPED_BOOKSHELF = BLOCKS.registerBlock("warped_bookshelf", Block::new, Block.Properties.ofFullCopy(Blocks.BOOKSHELF));
    public static final DeferredHolder<Block, Block> SCRAPWOOD_BOOKSHELF = BLOCKS.registerBlock("scrapwood_bookshelf", Block::new, Block.Properties.ofFullCopy(Blocks.BOOKSHELF));
    public static final DeferredHolder<Block, Block> WHISPUR_BOOKSHELF = BLOCKS.registerBlock("whispur_bookshelf", Block::new, Block.Properties.ofFullCopy(Blocks.BOOKSHELF));
    public static final DeferredHolder<Block, Block> PETRIFIED_BOOKSHELF = BLOCKS.registerBlock("petrified_bookshelf", Block::new, Block.Properties.ofFullCopy(Blocks.BOOKSHELF).strength(3.0F, 8.0F).sound(ModSoundType.PETRIFIED_PLANKS));
    public static final DeferredHolder<Block, Block> SHADE_BOOKSHELF = BLOCKS.registerBlock("shade_bookshelf", Block::new, Block.Properties.ofFullCopy(Blocks.BOOKSHELF));

    public static final DeferredHolder<Block, LadderBlock> ACACIA_LADDER = BLOCKS.registerBlock("acacia_ladder", LadderBlock::new, Block.Properties.ofFullCopy(Blocks.LADDER));
    public static final DeferredHolder<Block, LadderBlock> BIRCH_LADDER = BLOCKS.registerBlock("birch_ladder", LadderBlock::new, Block.Properties.ofFullCopy(Blocks.LADDER));
    public static final DeferredHolder<Block, LadderBlock> CRIMSON_LADDER = BLOCKS.registerBlock("crimson_ladder", LadderBlock::new, Block.Properties.ofFullCopy(Blocks.LADDER));
    public static final DeferredHolder<Block, LadderBlock> DARK_OAK_LADDER = BLOCKS.registerBlock("dark_oak_ladder", LadderBlock::new, Block.Properties.ofFullCopy(Blocks.LADDER));
    public static final DeferredHolder<Block, LadderBlock> JUNGLE_LADDER = BLOCKS.registerBlock("jungle_ladder", LadderBlock::new, Block.Properties.ofFullCopy(Blocks.LADDER));
    public static final DeferredHolder<Block, LadderBlock> MANGROVE_LADDER = BLOCKS.registerBlock("mangrove_ladder", LadderBlock::new, Block.Properties.ofFullCopy(Blocks.LADDER));
    public static final DeferredHolder<Block, LadderBlock> SPRUCE_LADDER = BLOCKS.registerBlock("spruce_ladder", LadderBlock::new, Block.Properties.ofFullCopy(Blocks.LADDER));
    public static final DeferredHolder<Block, LadderBlock> WARPED_LADDER = BLOCKS.registerBlock("warped_ladder", LadderBlock::new, Block.Properties.ofFullCopy(Blocks.LADDER));
    public static final DeferredHolder<Block, LadderBlock> SCRAPWOOD_LADDER = BLOCKS.registerBlock("scrapwood_ladder", LadderBlock::new, Block.Properties.ofFullCopy(Blocks.LADDER));
    public static final DeferredHolder<Block, LadderBlock> WHISPUR_LADDER = BLOCKS.registerBlock("whispur_ladder", LadderBlock::new, Block.Properties.ofFullCopy(Blocks.LADDER));
    public static final DeferredHolder<Block, LadderBlock> PETRIFIED_LADDER = BLOCKS.registerBlock("petrified_ladder", LadderBlock::new, Block.Properties.ofFullCopy(Blocks.LADDER).strength(3.0F, 8.0F).sound(ModSoundType.PETRIFIED_PLANKS));
    public static final DeferredHolder<Block, LadderBlock> SHADE_LADDER = BLOCKS.registerBlock("shade_ladder", LadderBlock::new, Block.Properties.ofFullCopy(Blocks.LADDER));
    public static final DeferredHolder<Block, HangingLadderBlock> CHAIN_LADDER = BLOCKS.registerBlock("chain_ladder", HangingLadderBlock::new, Block.Properties.ofFullCopy(Blocks.LADDER).strength(3F, 8F).sound(SoundType.CHAIN));
    public static final DeferredHolder<Block, HangingLadderBlock> BAMBOO_LADDER = BLOCKS.registerBlock("bamboo_ladder", HangingLadderBlock::new, Block.Properties.ofFullCopy(Blocks.LADDER).sound(SoundType.BAMBOO_WOOD));

    public static final DeferredHolder<Block, SmithingTableBlock> ACACIA_SMITHING_TABLE = BLOCKS.registerBlock("acacia_smithing_table", SmithingTableBlock::new, Block.Properties.ofFullCopy(Blocks.SMITHING_TABLE));
    public static final DeferredHolder<Block, SmithingTableBlock> BIRCH_SMITHING_TABLE = BLOCKS.registerBlock("birch_smithing_table", SmithingTableBlock::new, Block.Properties.ofFullCopy(Blocks.SMITHING_TABLE));
    public static final DeferredHolder<Block, SmithingTableBlock> CRIMSON_SMITHING_TABLE = BLOCKS.registerBlock("crimson_smithing_table", SmithingTableBlock::new, Block.Properties.ofFullCopy(Blocks.SMITHING_TABLE));
    public static final DeferredHolder<Block, SmithingTableBlock> DARK_OAK_SMITHING_TABLE = BLOCKS.registerBlock("dark_oak_smithing_table", SmithingTableBlock::new, Block.Properties.ofFullCopy(Blocks.SMITHING_TABLE));
    public static final DeferredHolder<Block, SmithingTableBlock> JUNGLE_SMITHING_TABLE = BLOCKS.registerBlock("jungle_smithing_table", SmithingTableBlock::new, Block.Properties.ofFullCopy(Blocks.SMITHING_TABLE));
    public static final DeferredHolder<Block, SmithingTableBlock> MANGROVE_SMITHING_TABLE = BLOCKS.registerBlock("mangrove_smithing_table", SmithingTableBlock::new, Block.Properties.ofFullCopy(Blocks.SMITHING_TABLE));
    public static final DeferredHolder<Block, SmithingTableBlock> SPRUCE_SMITHING_TABLE = BLOCKS.registerBlock("spruce_smithing_table", SmithingTableBlock::new, Block.Properties.ofFullCopy(Blocks.SMITHING_TABLE));
    public static final DeferredHolder<Block, SmithingTableBlock> WARPED_SMITHING_TABLE = BLOCKS.registerBlock("warped_smithing_table", SmithingTableBlock::new, Block.Properties.ofFullCopy(Blocks.SMITHING_TABLE));
    public static final DeferredHolder<Block, SmithingTableBlock> SCRAPWOOD_SMITHING_TABLE = BLOCKS.registerBlock("scrapwood_smithing_table", SmithingTableBlock::new, Block.Properties.ofFullCopy(Blocks.SMITHING_TABLE));
    public static final DeferredHolder<Block, SmithingTableBlock> WHISPUR_SMITHING_TABLE = BLOCKS.registerBlock("whispur_smithing_table", SmithingTableBlock::new, Block.Properties.ofFullCopy(Blocks.SMITHING_TABLE));
    public static final DeferredHolder<Block, SmithingTableBlock> PETRIFIED_SMITHING_TABLE = BLOCKS.registerBlock("petrified_smithing_table", SmithingTableBlock::new, Block.Properties.ofFullCopy(Blocks.SMITHING_TABLE));
    public static final DeferredHolder<Block, SmithingTableBlock> SHADE_SMITHING_TABLE = BLOCKS.registerBlock("shade_smithing_table", SmithingTableBlock::new, Block.Properties.ofFullCopy(Blocks.SMITHING_TABLE));

    public static final DeferredHolder<Block, CampfireBlock> ACACIA_CAMPFIRE = BLOCKS.registerBlock("acacia_campfire", props -> new ModCampfireBlock(true, 1, props), Block.Properties.ofFullCopy(Blocks.CAMPFIRE));
    public static final DeferredHolder<Block, CampfireBlock> ACACIA_SOUL_CAMPFIRE = BLOCKS.registerBlock("acacia_soul_campfire", props -> new ModCampfireBlock(true, 1, props), Block.Properties.ofFullCopy(Blocks.SOUL_CAMPFIRE));
    public static final DeferredHolder<Block, CampfireBlock> BIRCH_CAMPFIRE = BLOCKS.registerBlock("birch_campfire", props -> new ModCampfireBlock(true, 1, props), Block.Properties.ofFullCopy(Blocks.CAMPFIRE));
    public static final DeferredHolder<Block, CampfireBlock> BIRCH_SOUL_CAMPFIRE = BLOCKS.registerBlock("birch_soul_campfire", props -> new ModCampfireBlock(true, 1, props), Block.Properties.ofFullCopy(Blocks.SOUL_CAMPFIRE));
    public static final DeferredHolder<Block, CampfireBlock> CRIMSON_CAMPFIRE = BLOCKS.registerBlock("crimson_campfire", props -> new ModCampfireBlock(true, 1, props), Block.Properties.ofFullCopy(Blocks.CAMPFIRE));
    public static final DeferredHolder<Block, CampfireBlock> CRIMSON_SOUL_CAMPFIRE = BLOCKS.registerBlock("crimson_soul_campfire", props -> new ModCampfireBlock(true, 1, props), Block.Properties.ofFullCopy(Blocks.SOUL_CAMPFIRE));
    public static final DeferredHolder<Block, CampfireBlock> DARK_OAK_CAMPFIRE = BLOCKS.registerBlock("dark_oak_campfire", props -> new ModCampfireBlock(true, 1, props), Block.Properties.ofFullCopy(Blocks.CAMPFIRE));
    public static final DeferredHolder<Block, CampfireBlock> DARK_OAK_SOUL_CAMPFIRE = BLOCKS.registerBlock("dark_oak_soul_campfire", props -> new ModCampfireBlock(true, 1, props), Block.Properties.ofFullCopy(Blocks.SOUL_CAMPFIRE));
    public static final DeferredHolder<Block, CampfireBlock> JUNGLE_CAMPFIRE = BLOCKS.registerBlock("jungle_campfire", props -> new ModCampfireBlock(true, 1, props), Block.Properties.ofFullCopy(Blocks.CAMPFIRE));
    public static final DeferredHolder<Block, CampfireBlock> JUNGLE_SOUL_CAMPFIRE = BLOCKS.registerBlock("jungle_soul_campfire", props -> new ModCampfireBlock(true, 1, props), Block.Properties.ofFullCopy(Blocks.SOUL_CAMPFIRE));
    public static final DeferredHolder<Block, CampfireBlock> MANGROVE_CAMPFIRE = BLOCKS.registerBlock("mangrove_campfire", props -> new ModCampfireBlock(true, 1, props), Block.Properties.ofFullCopy(Blocks.CAMPFIRE));
    public static final DeferredHolder<Block, CampfireBlock> OAK_CAMPFIRE = BLOCKS.registerBlock("oak_campfire", props -> new ModCampfireBlock(true, 1, props), Block.Properties.ofFullCopy(Blocks.CAMPFIRE));
    public static final DeferredHolder<Block, CampfireBlock> OAK_SOUL_CAMPFIRE = BLOCKS.registerBlock("oak_soul_campfire", props -> new ModCampfireBlock(true, 1, props), Block.Properties.ofFullCopy(Blocks.SOUL_CAMPFIRE));
    public static final DeferredHolder<Block, CampfireBlock> MANGROVE_SOUL_CAMPFIRE = BLOCKS.registerBlock("mangrove_soul_campfire", props -> new ModCampfireBlock(true, 1, props), Block.Properties.ofFullCopy(Blocks.SOUL_CAMPFIRE));
    public static final DeferredHolder<Block, CampfireBlock> SPRUCE_CAMPFIRE = BLOCKS.registerBlock("spruce_campfire", props -> new ModCampfireBlock(true, 1, props), Block.Properties.ofFullCopy(Blocks.CAMPFIRE));
    public static final DeferredHolder<Block, CampfireBlock> SPRUCE_SOUL_CAMPFIRE = BLOCKS.registerBlock("spruce_soul_campfire", props -> new ModCampfireBlock(true, 1, props), Block.Properties.ofFullCopy(Blocks.SOUL_CAMPFIRE));
    public static final DeferredHolder<Block, CampfireBlock> WARPED_CAMPFIRE = BLOCKS.registerBlock("warped_campfire", props -> new ModCampfireBlock(true, 1, props), Block.Properties.ofFullCopy(Blocks.CAMPFIRE));
    public static final DeferredHolder<Block, CampfireBlock> WARPED_SOUL_CAMPFIRE = BLOCKS.registerBlock("warped_soul_campfire", props -> new ModCampfireBlock(true, 1, props), Block.Properties.ofFullCopy(Blocks.SOUL_CAMPFIRE));
    public static final DeferredHolder<Block, CampfireBlock> PETRIFIED_CAMPFIRE = BLOCKS.registerBlock("petrified_campfire", props -> new ModCampfireBlock(true, 1, props), Block.Properties.ofFullCopy(Blocks.CAMPFIRE));
    public static final DeferredHolder<Block, CampfireBlock> PETRIFIED_SOUL_CAMPFIRE = BLOCKS.registerBlock("petrified_soul_campfire", props -> new ModCampfireBlock(true, 1, props), Block.Properties.ofFullCopy(Blocks.SOUL_CAMPFIRE));
    public static final DeferredHolder<Block, CampfireBlock> SCRAPWOOD_CAMPFIRE = BLOCKS.registerBlock("scrapwood_campfire", props -> new ModCampfireBlock(true, 1, props), Block.Properties.ofFullCopy(Blocks.CAMPFIRE));
    public static final DeferredHolder<Block, CampfireBlock> SCRAPWOOD_SOUL_CAMPFIRE = BLOCKS.registerBlock("scrapwood_soul_campfire", props -> new ModCampfireBlock(true, 1, props), Block.Properties.ofFullCopy(Blocks.SOUL_CAMPFIRE));
    public static final DeferredHolder<Block, CampfireBlock> BONE_CAMPFIRE = BLOCKS.registerBlock("bone_campfire", props -> new ModCampfireBlock(true, 1, props), Block.Properties.ofFullCopy(Blocks.CAMPFIRE));
    public static final DeferredHolder<Block, CampfireBlock> BONE_SOUL_CAMPFIRE = BLOCKS.registerBlock("bone_soul_campfire", props -> new ModCampfireBlock(true, 1, props), Block.Properties.ofFullCopy(Blocks.SOUL_CAMPFIRE));
    public static final DeferredHolder<Block, CampfireBlock> SHADE_CAMPFIRE = BLOCKS.registerBlock("shade_campfire", props -> new ModCampfireBlock(true, 1, props), Block.Properties.ofFullCopy(Blocks.CAMPFIRE));
    public static final DeferredHolder<Block, CampfireBlock> SHADE_SOUL_CAMPFIRE = BLOCKS.registerBlock("shade_soul_campfire", props -> new ModCampfireBlock(true, 1, props), Block.Properties.ofFullCopy(Blocks.SOUL_CAMPFIRE));

    public static final DeferredHolder<Block, RailBlock> ACACIA_RAIL = BLOCKS.registerBlock("acacia_rail", RailBlock::new, Block.Properties.ofFullCopy(Blocks.RAIL));
    public static final DeferredHolder<Block, RailBlock> BIRCH_RAIL = BLOCKS.registerBlock("birch_rail", RailBlock::new, Block.Properties.ofFullCopy(Blocks.RAIL));
    public static final DeferredHolder<Block, RailBlock> CRIMSON_RAIL = BLOCKS.registerBlock("crimson_rail", RailBlock::new, Block.Properties.ofFullCopy(Blocks.RAIL));
    public static final DeferredHolder<Block, RailBlock> DARK_OAK_RAIL = BLOCKS.registerBlock("dark_oak_rail", RailBlock::new, Block.Properties.ofFullCopy(Blocks.RAIL));
    public static final DeferredHolder<Block, RailBlock> JUNGLE_RAIL = BLOCKS.registerBlock("jungle_rail", RailBlock::new, Block.Properties.ofFullCopy(Blocks.RAIL));
    public static final DeferredHolder<Block, RailBlock> MANGROVE_RAIL = BLOCKS.registerBlock("mangrove_rail", RailBlock::new, Block.Properties.ofFullCopy(Blocks.RAIL));
    public static final DeferredHolder<Block, RailBlock> SPRUCE_RAIL = BLOCKS.registerBlock("spruce_rail", RailBlock::new, Block.Properties.ofFullCopy(Blocks.RAIL));
    public static final DeferredHolder<Block, RailBlock> WARPED_RAIL = BLOCKS.registerBlock("warped_rail", RailBlock::new, Block.Properties.ofFullCopy(Blocks.RAIL));
    public static final DeferredHolder<Block, RailBlock> SCRAPWOOD_RAIL = BLOCKS.registerBlock("scrapwood_rail", RailBlock::new, Block.Properties.ofFullCopy(Blocks.RAIL));
    public static final DeferredHolder<Block, RailBlock> WHISPUR_RAIL = BLOCKS.registerBlock("whispur_rail", RailBlock::new, Block.Properties.ofFullCopy(Blocks.RAIL));
    public static final DeferredHolder<Block, RailBlock> PETRIFIED_RAIL = BLOCKS.registerBlock("petrified_rail", RailBlock::new, Block.Properties.ofFullCopy(Blocks.RAIL));
    public static final DeferredHolder<Block, RailBlock> SHADE_RAIL = BLOCKS.registerBlock("shade_rail", RailBlock::new, Block.Properties.ofFullCopy(Blocks.RAIL));

    public static final DeferredHolder<Block, DetectorRailBlock> ACACIA_DETECTOR_RAIL = BLOCKS.registerBlock("acacia_detector_rail", DetectorRailBlock::new, Block.Properties.ofFullCopy(Blocks.DETECTOR_RAIL));
    public static final DeferredHolder<Block, DetectorRailBlock> BIRCH_DETECTOR_RAIL = BLOCKS.registerBlock("birch_detector_rail", DetectorRailBlock::new, Block.Properties.ofFullCopy(Blocks.DETECTOR_RAIL));
    public static final DeferredHolder<Block, DetectorRailBlock> CRIMSON_DETECTOR_RAIL = BLOCKS.registerBlock("crimson_detector_rail", DetectorRailBlock::new, Block.Properties.ofFullCopy(Blocks.DETECTOR_RAIL));
    public static final DeferredHolder<Block, DetectorRailBlock> DARK_OAK_DETECTOR_RAIL = BLOCKS.registerBlock("dark_oak_detector_rail", DetectorRailBlock::new, Block.Properties.ofFullCopy(Blocks.DETECTOR_RAIL));
    public static final DeferredHolder<Block, DetectorRailBlock> JUNGLE_DETECTOR_RAIL = BLOCKS.registerBlock("jungle_detector_rail", DetectorRailBlock::new, Block.Properties.ofFullCopy(Blocks.DETECTOR_RAIL));
    public static final DeferredHolder<Block, DetectorRailBlock> MANGROVE_DETECTOR_RAIL = BLOCKS.registerBlock("mangrove_detector_rail", DetectorRailBlock::new, Block.Properties.ofFullCopy(Blocks.DETECTOR_RAIL));
    public static final DeferredHolder<Block, DetectorRailBlock> SPRUCE_DETECTOR_RAIL = BLOCKS.registerBlock("spruce_detector_rail", DetectorRailBlock::new, Block.Properties.ofFullCopy(Blocks.DETECTOR_RAIL));
    public static final DeferredHolder<Block, DetectorRailBlock> WARPED_DETECTOR_RAIL = BLOCKS.registerBlock("warped_detector_rail", DetectorRailBlock::new, Block.Properties.ofFullCopy(Blocks.DETECTOR_RAIL));
    public static final DeferredHolder<Block, DetectorRailBlock> SCRAPWOOD_DETECTOR_RAIL = BLOCKS.registerBlock("scrapwood_detector_rail", DetectorRailBlock::new, Block.Properties.ofFullCopy(Blocks.DETECTOR_RAIL));
    public static final DeferredHolder<Block, DetectorRailBlock> WHISPUR_DETECTOR_RAIL = BLOCKS.registerBlock("whispur_detector_rail", DetectorRailBlock::new, Block.Properties.ofFullCopy(Blocks.DETECTOR_RAIL));
    public static final DeferredHolder<Block, DetectorRailBlock> PETRIFIED_DETECTOR_RAIL = BLOCKS.registerBlock("petrified_detector_rail", DetectorRailBlock::new, Block.Properties.ofFullCopy(Blocks.DETECTOR_RAIL));
    public static final DeferredHolder<Block, DetectorRailBlock> SHADE_DETECTOR_RAIL = BLOCKS.registerBlock("shade_detector_rail", DetectorRailBlock::new, Block.Properties.ofFullCopy(Blocks.DETECTOR_RAIL));

    public static final DeferredHolder<Block, PoweredRailBlock> ACACIA_POWERED_RAIL = BLOCKS.registerBlock("acacia_powered_rail", props -> new PoweredRailBlock(props, true), Block.Properties.ofFullCopy(Blocks.POWERED_RAIL));
    public static final DeferredHolder<Block, PoweredRailBlock> BIRCH_POWERED_RAIL = BLOCKS.registerBlock("birch_powered_rail", props -> new PoweredRailBlock(props, true), Block.Properties.ofFullCopy(Blocks.POWERED_RAIL));
    public static final DeferredHolder<Block, PoweredRailBlock> CRIMSON_POWERED_RAIL = BLOCKS.registerBlock("crimson_powered_rail", props -> new PoweredRailBlock(props, true), Block.Properties.ofFullCopy(Blocks.POWERED_RAIL));
    public static final DeferredHolder<Block, PoweredRailBlock> DARK_OAK_POWERED_RAIL = BLOCKS.registerBlock("dark_oak_powered_rail", props -> new PoweredRailBlock(props, true), Block.Properties.ofFullCopy(Blocks.POWERED_RAIL));
    public static final DeferredHolder<Block, PoweredRailBlock> JUNGLE_POWERED_RAIL = BLOCKS.registerBlock("jungle_powered_rail", props -> new PoweredRailBlock(props, true), Block.Properties.ofFullCopy(Blocks.POWERED_RAIL));
    public static final DeferredHolder<Block, PoweredRailBlock> MANGROVE_POWERED_RAIL = BLOCKS.registerBlock("mangrove_powered_rail", props -> new PoweredRailBlock(props, true), Block.Properties.ofFullCopy(Blocks.POWERED_RAIL));
    public static final DeferredHolder<Block, PoweredRailBlock> SPRUCE_POWERED_RAIL = BLOCKS.registerBlock("spruce_powered_rail", props -> new PoweredRailBlock(props, true), Block.Properties.ofFullCopy(Blocks.POWERED_RAIL));
    public static final DeferredHolder<Block, PoweredRailBlock> WARPED_POWERED_RAIL = BLOCKS.registerBlock("warped_powered_rail", props -> new PoweredRailBlock(props, true), Block.Properties.ofFullCopy(Blocks.POWERED_RAIL));
    public static final DeferredHolder<Block, PoweredRailBlock> SCRAPWOOD_POWERED_RAIL = BLOCKS.registerBlock("scrapwood_powered_rail", props -> new PoweredRailBlock(props, true), Block.Properties.ofFullCopy(Blocks.POWERED_RAIL));
    public static final DeferredHolder<Block, PoweredRailBlock> WHISPUR_POWERED_RAIL = BLOCKS.registerBlock("whispur_powered_rail", props -> new PoweredRailBlock(props, true), Block.Properties.ofFullCopy(Blocks.POWERED_RAIL));
    public static final DeferredHolder<Block, PoweredRailBlock> PETRIFIED_POWERED_RAIL = BLOCKS.registerBlock("petrified_powered_rail", props -> new PoweredRailBlock(props, true), Block.Properties.ofFullCopy(Blocks.POWERED_RAIL));
    public static final DeferredHolder<Block, PoweredRailBlock> SHADE_POWERED_RAIL = BLOCKS.registerBlock("shade_powered_rail", props -> new PoweredRailBlock(props, true), Block.Properties.ofFullCopy(Blocks.POWERED_RAIL));

    public static final DeferredHolder<Block, PoweredRailBlock> ACACIA_ACTIVATOR_RAIL = BLOCKS.registerBlock("acacia_activator_rail", PoweredRailBlock::new, Block.Properties.ofFullCopy(Blocks.ACTIVATOR_RAIL));
    public static final DeferredHolder<Block, PoweredRailBlock> BIRCH_ACTIVATOR_RAIL = BLOCKS.registerBlock("birch_activator_rail", PoweredRailBlock::new, Block.Properties.ofFullCopy(Blocks.ACTIVATOR_RAIL));
    public static final DeferredHolder<Block, PoweredRailBlock> CRIMSON_ACTIVATOR_RAIL = BLOCKS.registerBlock("crimson_activator_rail", PoweredRailBlock::new, Block.Properties.ofFullCopy(Blocks.ACTIVATOR_RAIL));
    public static final DeferredHolder<Block, PoweredRailBlock> DARK_OAK_ACTIVATOR_RAIL = BLOCKS.registerBlock("dark_oak_activator_rail", PoweredRailBlock::new, Block.Properties.ofFullCopy(Blocks.ACTIVATOR_RAIL));
    public static final DeferredHolder<Block, PoweredRailBlock> JUNGLE_ACTIVATOR_RAIL = BLOCKS.registerBlock("jungle_activator_rail", PoweredRailBlock::new, Block.Properties.ofFullCopy(Blocks.ACTIVATOR_RAIL));
    public static final DeferredHolder<Block, PoweredRailBlock> MANGROVE_ACTIVATOR_RAIL = BLOCKS.registerBlock("mangrove_activator_rail", PoweredRailBlock::new, Block.Properties.ofFullCopy(Blocks.ACTIVATOR_RAIL));
    public static final DeferredHolder<Block, PoweredRailBlock> SPRUCE_ACTIVATOR_RAIL = BLOCKS.registerBlock("spruce_activator_rail", PoweredRailBlock::new, Block.Properties.ofFullCopy(Blocks.ACTIVATOR_RAIL));
    public static final DeferredHolder<Block, PoweredRailBlock> WARPED_ACTIVATOR_RAIL = BLOCKS.registerBlock("warped_activator_rail", PoweredRailBlock::new, Block.Properties.ofFullCopy(Blocks.ACTIVATOR_RAIL));
    public static final DeferredHolder<Block, PoweredRailBlock> SCRAPWOOD_ACTIVATOR_RAIL = BLOCKS.registerBlock("scrapwood_activator_rail", PoweredRailBlock::new, Block.Properties.ofFullCopy(Blocks.ACTIVATOR_RAIL));
    public static final DeferredHolder<Block, PoweredRailBlock> WHISPUR_ACTIVATOR_RAIL = BLOCKS.registerBlock("whispur_activator_rail", PoweredRailBlock::new, Block.Properties.ofFullCopy(Blocks.ACTIVATOR_RAIL));
    public static final DeferredHolder<Block, PoweredRailBlock> PETRIFIED_ACTIVATOR_RAIL = BLOCKS.registerBlock("petrified_activator_rail", PoweredRailBlock::new, Block.Properties.ofFullCopy(Blocks.ACTIVATOR_RAIL));
    public static final DeferredHolder<Block, PoweredRailBlock> SHADE_ACTIVATOR_RAIL = BLOCKS.registerBlock("shade_activator_rail", PoweredRailBlock::new, Block.Properties.ofFullCopy(Blocks.ACTIVATOR_RAIL));

    public static final DeferredHolder<Block, HedgeBlock> ACACIA_HEDGE = BLOCKS.registerBlock("acacia_hedge", HedgeBlock::new, Block.Properties.ofFullCopy(Blocks.ACACIA_LEAVES));
    public static final DeferredHolder<Block, HedgeBlock> AZALEA_HEDGE = BLOCKS.registerBlock("azalea_hedge", HedgeBlock::new, Block.Properties.ofFullCopy(Blocks.AZALEA_LEAVES));
    public static final DeferredHolder<Block, HedgeBlock> FLOWERING_AZALEA_HEDGE = BLOCKS.registerBlock("flowering_azalea_hedge", HedgeBlock::new, Block.Properties.ofFullCopy(Blocks.FLOWERING_AZALEA_LEAVES));
    public static final DeferredHolder<Block, HedgeBlock> BIRCH_HEDGE = BLOCKS.registerBlock("birch_hedge", HedgeBlock::new, Block.Properties.ofFullCopy(Blocks.BIRCH_LEAVES));
    public static final DeferredHolder<Block, HedgeBlock> DARK_OAK_HEDGE = BLOCKS.registerBlock("dark_oak_hedge", HedgeBlock::new, Block.Properties.ofFullCopy(Blocks.DARK_OAK_LEAVES));
    public static final DeferredHolder<Block, HedgeBlock> JUNGLE_HEDGE = BLOCKS.registerBlock("jungle_hedge", HedgeBlock::new, Block.Properties.ofFullCopy(Blocks.JUNGLE_LEAVES));
    public static final DeferredHolder<Block, HedgeBlock> MANGROVE_HEDGE = BLOCKS.registerBlock("mangrove_hedge", HedgeBlock::new, Block.Properties.ofFullCopy(Blocks.MANGROVE_LEAVES));
    public static final DeferredHolder<Block, HedgeBlock> OAK_HEDGE = BLOCKS.registerBlock("oak_hedge", HedgeBlock::new, Block.Properties.ofFullCopy(Blocks.OAK_LEAVES));
    public static final DeferredHolder<Block, HedgeBlock> SPRUCE_HEDGE = BLOCKS.registerBlock("spruce_hedge", HedgeBlock::new, Block.Properties.ofFullCopy(Blocks.SPRUCE_LEAVES));
    public static final DeferredHolder<Block, HedgeBlock> SHADE_HEDGE = BLOCKS.registerBlock("shade_hedge", HedgeBlock::new, Block.Properties.ofFullCopy(Blocks.OAK_LEAVES));
    public static final DeferredHolder<Block, HedgeBlock> TEAL_SHADE_HEDGE = BLOCKS.registerBlock("teal_shade_hedge", HedgeBlock::new, Block.Properties.ofFullCopy(Blocks.OAK_LEAVES));
    public static final DeferredHolder<Block, HedgeBlock> RED_SHADE_HEDGE = BLOCKS.registerBlock("red_shade_hedge", HedgeBlock::new, Block.Properties.ofFullCopy(Blocks.OAK_LEAVES));
    public static final DeferredHolder<Block, HedgeBlock> PURPLE_SHADE_HEDGE = BLOCKS.registerBlock("purple_shade_hedge", HedgeBlock::new, Block.Properties.ofFullCopy(Blocks.OAK_LEAVES));

    public static final DeferredHolder<Block, LeafyWallBlock> ACACIA_HEDGE_WALL = BLOCKS.registerBlock("acacia_hedge_wall", LeafyWallBlock::new, Block.Properties.ofFullCopy(Blocks.ACACIA_LEAVES));
    public static final DeferredHolder<Block, LeafyWallBlock> AZALEA_HEDGE_WALL = BLOCKS.registerBlock("azalea_hedge_wall", LeafyWallBlock::new, Block.Properties.ofFullCopy(Blocks.AZALEA_LEAVES));
    public static final DeferredHolder<Block, LeafyWallBlock> FLOWERING_AZALEA_HEDGE_WALL = BLOCKS.registerBlock("flowering_azalea_hedge_wall", LeafyWallBlock::new, Block.Properties.ofFullCopy(Blocks.FLOWERING_AZALEA_LEAVES));
    public static final DeferredHolder<Block, LeafyWallBlock> BIRCH_HEDGE_WALL = BLOCKS.registerBlock("birch_hedge_wall", LeafyWallBlock::new, Block.Properties.ofFullCopy(Blocks.BIRCH_LEAVES));
    public static final DeferredHolder<Block, LeafyWallBlock> DARK_OAK_HEDGE_WALL = BLOCKS.registerBlock("dark_oak_hedge_wall", LeafyWallBlock::new, Block.Properties.ofFullCopy(Blocks.DARK_OAK_LEAVES));
    public static final DeferredHolder<Block, LeafyWallBlock> JUNGLE_HEDGE_WALL = BLOCKS.registerBlock("jungle_hedge_wall", LeafyWallBlock::new, Block.Properties.ofFullCopy(Blocks.JUNGLE_LEAVES));
    public static final DeferredHolder<Block, LeafyWallBlock> MANGROVE_HEDGE_WALL = BLOCKS.registerBlock("mangrove_hedge_wall", LeafyWallBlock::new, Block.Properties.ofFullCopy(Blocks.MANGROVE_LEAVES));
    public static final DeferredHolder<Block, LeafyWallBlock> OAK_HEDGE_WALL = BLOCKS.registerBlock("oak_hedge_wall", LeafyWallBlock::new, Block.Properties.ofFullCopy(Blocks.OAK_LEAVES));
    public static final DeferredHolder<Block, LeafyWallBlock> SPRUCE_HEDGE_WALL = BLOCKS.registerBlock("spruce_hedge_wall", LeafyWallBlock::new, Block.Properties.ofFullCopy(Blocks.SPRUCE_LEAVES));
    public static final DeferredHolder<Block, LeafyWallBlock> SHADE_HEDGE_WALL = BLOCKS.registerBlock("shade_hedge_wall", LeafyWallBlock::new, Block.Properties.ofFullCopy(Blocks.OAK_LEAVES));
    public static final DeferredHolder<Block, LeafyWallBlock> TEAL_SHADE_HEDGE_WALL = BLOCKS.registerBlock("teal_shade_hedge_wall", LeafyWallBlock::new, Block.Properties.ofFullCopy(Blocks.OAK_LEAVES));
    public static final DeferredHolder<Block, LeafyWallBlock> RED_SHADE_HEDGE_WALL = BLOCKS.registerBlock("red_shade_hedge_wall", LeafyWallBlock::new, Block.Properties.ofFullCopy(Blocks.OAK_LEAVES));
    public static final DeferredHolder<Block, LeafyWallBlock> PURPLE_SHADE_HEDGE_WALL = BLOCKS.registerBlock("purple_shade_hedge_wall", LeafyWallBlock::new, Block.Properties.ofFullCopy(Blocks.OAK_LEAVES));

    public static final DeferredHolder<Block, FurnaceBlock> BLACKSTONE_FURNACE = BLOCKS.registerBlock("blackstone_furnace", FurnaceBlock::new, Block.Properties.ofFullCopy(Blocks.FURNACE));
    public static final DeferredHolder<Block, FurnaceBlock> DEEPSLATE_FURNACE = BLOCKS.registerBlock("deepslate_furnace", FurnaceBlock::new, Block.Properties.ofFullCopy(Blocks.FURNACE));
    public static final DeferredHolder<Block, FurnaceBlock> ANDESITE_FURNACE = BLOCKS.registerBlock("andesite_furnace", FurnaceBlock::new, Block.Properties.ofFullCopy(Blocks.FURNACE));
    public static final DeferredHolder<Block, FurnaceBlock> DIORITE_FURNACE = BLOCKS.registerBlock("diorite_furnace", FurnaceBlock::new, Block.Properties.ofFullCopy(Blocks.FURNACE));
    public static final DeferredHolder<Block, FurnaceBlock> GRANITE_FURNACE = BLOCKS.registerBlock("granite_furnace", FurnaceBlock::new, Block.Properties.ofFullCopy(Blocks.FURNACE));

    public static final DeferredHolder<Block, DispenserBlock> BLACKSTONE_DISPENSER = BLOCKS.registerBlock("blackstone_dispenser", DispenserBlock::new, Block.Properties.ofFullCopy(Blocks.DISPENSER));
    public static final DeferredHolder<Block, DispenserBlock> DEEPSLATE_DISPENSER = BLOCKS.registerBlock("deepslate_dispenser", DispenserBlock::new, Block.Properties.ofFullCopy(Blocks.DISPENSER));
    public static final DeferredHolder<Block, DispenserBlock> ANDESITE_DISPENSER = BLOCKS.registerBlock("andesite_dispenser", DispenserBlock::new, Block.Properties.ofFullCopy(Blocks.DISPENSER));
    public static final DeferredHolder<Block, DispenserBlock> DIORITE_DISPENSER = BLOCKS.registerBlock("diorite_dispenser", DispenserBlock::new, Block.Properties.ofFullCopy(Blocks.DISPENSER));
    public static final DeferredHolder<Block, DispenserBlock> GRANITE_DISPENSER = BLOCKS.registerBlock("granite_dispenser", DispenserBlock::new, Block.Properties.ofFullCopy(Blocks.DISPENSER));

    public static final DeferredHolder<Block, DropperBlock> BLACKSTONE_DROPPER = BLOCKS.registerBlock("blackstone_dropper", DropperBlock::new, Block.Properties.ofFullCopy(Blocks.DROPPER));
    public static final DeferredHolder<Block, DropperBlock> DEEPSLATE_DROPPER = BLOCKS.registerBlock("deepslate_dropper", DropperBlock::new, Block.Properties.ofFullCopy(Blocks.DROPPER));
    public static final DeferredHolder<Block, DropperBlock> ANDESITE_DROPPER = BLOCKS.registerBlock("andesite_dropper", DropperBlock::new, Block.Properties.ofFullCopy(Blocks.DROPPER));
    public static final DeferredHolder<Block, DropperBlock> DIORITE_DROPPER = BLOCKS.registerBlock("diorite_dropper", DropperBlock::new, Block.Properties.ofFullCopy(Blocks.DROPPER));
    public static final DeferredHolder<Block, DropperBlock> GRANITE_DROPPER = BLOCKS.registerBlock("granite_dropper", DropperBlock::new, Block.Properties.ofFullCopy(Blocks.DROPPER));

    public static final DeferredHolder<Block, Block> AURUM_BLOCK = BLOCKS.registerBlock("aurum_block", Block::new, Block.Properties.ofFullCopy(Blocks.GOLD_BLOCK));
    public static final DeferredHolder<Block, DropExperienceBlock> AURUM_ORE = BLOCKS.registerBlock("aurum_ore", props -> new DropExperienceBlock(UniformInt.of(0, 1), props), BlockBehaviour.Properties.of().strength(0.5F, 3.0F).sound(SoundType.NETHER_GOLD_ORE).mapColor(MapColor.NETHER));
    public static final DeferredHolder<Block, DropExperienceBlock> NETHER_DIAMOND_ORE = BLOCKS.registerBlock("nether_diamond_ore", props -> new DropExperienceBlock(UniformInt.of(0, 6), props),  BlockBehaviour.Properties.of().strength(2.5F, 6.0F).sound(SoundType.NETHER_GOLD_ORE).mapColor(MapColor.NETHER).requiresCorrectToolForDrops());
    public static final DeferredHolder<Block, DropExperienceBlock> BASALT_DIAMOND_ORE = BLOCKS.registerBlock("basalt_diamond_ore", props -> new DropExperienceBlock(UniformInt.of(0, 6), props),  BlockBehaviour.Properties.of().strength(2.0F, 6.0F).sound(SoundType.BASALT).mapColor(MapColor.NETHER).requiresCorrectToolForDrops());

    public static final DeferredHolder<Block, Block> WITHERED_BLOCK = BLOCKS.registerBlock("withered_block", Block::new, Block.Properties.of().strength(2.0F, 20.0F).sound(SoundType.NETHERITE_BLOCK).mapColor(MapColor.COLOR_BLACK));

    public static final DeferredHolder<Block, NetherBristleBlock> NETHER_BRISTLE = BLOCKS.registerBlock("nether_bristle", NetherBristleBlock::new, BlockBehaviour.Properties.of().noCollission().instabreak().sound(SoundType.AZALEA_LEAVES).offsetType(BlockBehaviour.OffsetType.XZ).mapColor(MapColor.CRIMSON_HYPHAE));
    public static final DeferredHolder<Block, WhispurRootBlock> WHISPUR_ROOT = BLOCKS.registerBlock("whispur_root", WhispurRootBlock::new, BlockBehaviour.Properties.of().strength(0.4F).sound(SoundType.BONE_BLOCK).mapColor(MapColor.SAND).noOcclusion());

    public static final DeferredHolder<Block, CaramineRyeBlock> CARAMINE_RYE = BLOCKS.registerBlock("caramine_rye", CaramineRyeBlock::new, Block.Properties.of().noCollission().randomTicks().instabreak().sound(SoundType.CROP).mapColor(MapColor.COLOR_RED));

    public static final DeferredHolder<Block, Block> TWISTING_VINES_PLANT = BLOCKS.registerBlock("twisting_vines_plant", ModTwistingVinesPlantBlock::new, Block.Properties.ofFullCopy(Blocks.TWISTING_VINES_PLANT));
    public static final DeferredHolder<Block, Block> WARPED_ROOTS = BLOCKS.registerBlock("warped_roots", ModWarpedRootsBlock::new, Block.Properties.ofFullCopy(Blocks.WARPED_ROOTS));

    public static final DeferredHolder<Block, ModCarvedPumpkinBlock> CARVED_PUMPKIN = BLOCKS.registerBlock("carved_pumpkin", ModCarvedPumpkinBlock::new, (Block.Properties.ofFullCopy(Blocks.CARVED_PUMPKIN).lightLevel(value -> value.getValue(ModCarvedPumpkinBlock.LIT) ? 15 : 0)));

    private static final BlockBehaviour.Properties SHADE_LEAVES_PROPERTIES = Block.Properties.of().mapColor(MapColor.COLOR_GREEN).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion();
    public static final DeferredHolder<Block, LeavesBlock> SHADE_LEAVES = BLOCKS.registerBlock("shade_leaves", LeavesBlock::new, SHADE_LEAVES_PROPERTIES);
    public static final DeferredHolder<Block, LeavesBlock> TEAL_SHADE_LEAVES = BLOCKS.registerBlock("teal_shade_leaves", LeavesBlock::new, SHADE_LEAVES_PROPERTIES.mapColor(MapColor.COLOR_CYAN));
    public static final DeferredHolder<Block, LeavesBlock> RED_SHADE_LEAVES = BLOCKS.registerBlock("red_shade_leaves", LeavesBlock::new, SHADE_LEAVES_PROPERTIES.mapColor(MapColor.COLOR_RED));
    public static final DeferredHolder<Block, LeavesBlock> PURPLE_SHADE_LEAVES = BLOCKS.registerBlock("purple_shade_leaves", LeavesBlock::new, SHADE_LEAVES_PROPERTIES.mapColor(MapColor.COLOR_PURPLE));

    private static final BlockBehaviour.Properties SHADE_SAPLING_PROPERTIES = Block.Properties.of().mapColor(MapColor.COLOR_GREEN).noCollission().randomTicks().instabreak().sound(SoundType.GRASS);
    public static final DeferredHolder<Block, ShadeSaplingBlock> SHADE_SAPLING = BLOCKS.registerBlock("shade_sapling", props -> new ShadeSaplingBlock(ShadeTree.Color.NORMAL, props), SHADE_SAPLING_PROPERTIES);
    public static final DeferredHolder<Block, ShadeSaplingBlock> TEAL_SHADE_SAPLING = BLOCKS.registerBlock("teal_shade_sapling", props -> new ShadeSaplingBlock(ShadeTree.Color.TEAL, props), SHADE_SAPLING_PROPERTIES.mapColor(MapColor.COLOR_CYAN));
    public static final DeferredHolder<Block, ShadeSaplingBlock> RED_SHADE_SAPLING = BLOCKS.registerBlock("red_shade_sapling", props -> new ShadeSaplingBlock(ShadeTree.Color.RED, props), SHADE_SAPLING_PROPERTIES.mapColor(MapColor.COLOR_RED));
    public static final DeferredHolder<Block, ShadeSaplingBlock> PURPLE_SHADE_SAPLING = BLOCKS.registerBlock("purple_shade_sapling", props -> new ShadeSaplingBlock(ShadeTree.Color.PURPLE, props), SHADE_SAPLING_PROPERTIES.mapColor(MapColor.COLOR_PURPLE));

    public static final DeferredHolder<Block, DurianBlock> DURIAN = BLOCKS.registerBlock("durian", DurianBlock::new, Block.Properties.of().mapColor(MapColor.COLOR_BROWN).strength(0.2F).sound(SoundType.WOOD).noOcclusion());

    public static final DeferredHolder<Block, HadaliteBlock> HADALITE = BLOCKS.registerBlock("hadalite", HadaliteBlock::new, Block.Properties.ofFullCopy(Blocks.BEDROCK));

    public static final DeferredHolder<Block, SlabBlock> BASALT_SLAB = BLOCKS.registerBlock("basalt_slab", SlabBlock::new, Block.Properties.ofFullCopy(Blocks.BASALT));
    public static final DeferredHolder<Block, SlabBlock> SMOOTH_BASALT_SLAB = BLOCKS.registerBlock("smooth_basalt_slab", SlabBlock::new, Block.Properties.ofFullCopy(Blocks.SMOOTH_BASALT));
    public static final DeferredHolder<Block, SlabBlock> POLISHED_BASALT_SLAB = BLOCKS.registerBlock("polished_basalt_slab", SlabBlock::new, Block.Properties.ofFullCopy(Blocks.POLISHED_BASALT));

    public static final DeferredHolder<Block, StairBlock> BASALT_STAIRS = BLOCKS.registerBlock("basalt_stairs", props -> new StairBlock(Blocks.BASALT.defaultBlockState(), props), Block.Properties.ofFullCopy(Blocks.BASALT));
    public static final DeferredHolder<Block, StairBlock> SMOOTH_BASALT_STAIRS = BLOCKS.registerBlock("smooth_basalt_stairs", props -> new StairBlock(Blocks.SMOOTH_BASALT.defaultBlockState(), props), Block.Properties.ofFullCopy(Blocks.SMOOTH_BASALT));
    public static final DeferredHolder<Block, StairBlock> POLISHED_BASALT_STAIRS = BLOCKS.registerBlock("polished_basalt_stairs", props -> new StairBlock(Blocks.POLISHED_BASALT.defaultBlockState(), props), Block.Properties.ofFullCopy(Blocks.POLISHED_BASALT));

    public static final DeferredHolder<Block, WallBlock> BASALT_WALL = BLOCKS.registerBlock("basalt_wall", WallBlock::new, Block.Properties.ofFullCopy(Blocks.BASALT));
    public static final DeferredHolder<Block, WallBlock> SMOOTH_BASALT_WALL = BLOCKS.registerBlock("smooth_basalt_wall", WallBlock::new, Block.Properties.ofFullCopy(Blocks.SMOOTH_BASALT));
    public static final DeferredHolder<Block, WallBlock> POLISHED_BASALT_WALL = BLOCKS.registerBlock("polished_basalt_wall", WallBlock::new, Block.Properties.ofFullCopy(Blocks.POLISHED_BASALT));

    public static final DeferredHolder<Block, Block> PATINA_GOLD_BLOCK = BLOCKS.registerBlock("patina_gold_block", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GOLD_BLOCK));
    public static final DeferredHolder<Block, Block> PATINA_AURUM_BLOCK = BLOCKS.registerBlock("patina_aurum_block", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GOLD_BLOCK));

    public static final DeferredHolder<Block, Block> BLUE_ICE_BRICKS = BLOCKS.registerBlock("blue_ice_bricks", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BLUE_ICE));

    public static final TreeGrower SHADE_TREE_GROWER = new TreeGrower("shade_tree", Optional.empty(), Optional.of(FeatureInit.CONFIGURED_SHADE_TREE), Optional.empty());

    public static <T> T useSuppliers(Supplier<T>[] Suppliers) {
        T[] result = (T[]) new Object[Suppliers.length];
        for (int i = 0; i < Suppliers.length; i++) {
            result[i] = Suppliers[i].get();
        }


        return (T) result; 
    }
}

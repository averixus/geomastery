/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.main;

import java.util.Set;
import com.google.common.collect.Sets;
import jayavery.geomastery.blocks.BlockAntler;
import jayavery.geomastery.blocks.BlockBeam;
import jayavery.geomastery.blocks.BlockBed;
import jayavery.geomastery.blocks.BlockBeehive;
import jayavery.geomastery.blocks.BlockBuildingAbstract;
import jayavery.geomastery.blocks.BlockCarcass;
import jayavery.geomastery.blocks.BlockContainerFacing;
import jayavery.geomastery.blocks.BlockContainerMulti;
import jayavery.geomastery.blocks.BlockContainerSingle;
import jayavery.geomastery.blocks.BlockCrop;
import jayavery.geomastery.blocks.BlockCropBlockfruit;
import jayavery.geomastery.blocks.BlockCropHarvestable;
import jayavery.geomastery.blocks.BlockDoor;
import jayavery.geomastery.blocks.BlockFlatroof;
import jayavery.geomastery.blocks.BlockFruit;
import jayavery.geomastery.blocks.BlockHarvestableLeaves;
import jayavery.geomastery.blocks.BlockLadder;
import jayavery.geomastery.blocks.BlockLeaves;
import jayavery.geomastery.blocks.BlockLight;
import jayavery.geomastery.blocks.BlockMushroombaby;
import jayavery.geomastery.blocks.BlockPitchroof;
import jayavery.geomastery.blocks.BlockRiceBase;
import jayavery.geomastery.blocks.BlockRiceTop;
import jayavery.geomastery.blocks.BlockRubble;
import jayavery.geomastery.blocks.BlockSawpit;
import jayavery.geomastery.blocks.BlockSeedling;
import jayavery.geomastery.blocks.BlockSlab;
import jayavery.geomastery.blocks.BlockSolid;
import jayavery.geomastery.blocks.BlockStairsComplex;
import jayavery.geomastery.blocks.BlockStairsStraight;
import jayavery.geomastery.blocks.BlockTar;
import jayavery.geomastery.blocks.BlockTrunk;
import jayavery.geomastery.blocks.BlockVault;
import jayavery.geomastery.blocks.BlockVaultDoubling;
import jayavery.geomastery.blocks.BlockWall;
import jayavery.geomastery.blocks.BlockWallComplex;
import jayavery.geomastery.blocks.BlockWallFence;
import jayavery.geomastery.blocks.BlockWallHeaping;
import jayavery.geomastery.blocks.BlockWallLog;
import jayavery.geomastery.blocks.BlockWallThin;
import jayavery.geomastery.blocks.BlockWindow;
import jayavery.geomastery.blocks.BlockWood;
import jayavery.geomastery.items.ItemPlacing;
import jayavery.geomastery.utilities.BlockMaterial;
import jayavery.geomastery.utilities.EBlockWeight;
import jayavery.geomastery.utilities.EToolType;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

/** Stores and registers all new Geomastery blocks. */
public class GeoBlocks {
    
    /** All new blocks. */
    public static final Set<Block> BLOCKS = Sets.newHashSet();
    
    /** Items which are restricted to the offhand slot. */
    public static final Set<Item> OFFHAND_ONLY = Sets.newHashSet();
    
    /** Leaves which need to set transparency. */
    public static final Set<BlockHarvestableLeaves> LEAVES = Sets.newHashSet();
    
    /** Blockfruit crops which need custom state mapper. */
    public static final Set<BlockCropBlockfruit> CROP_BLOCKFRUIT = Sets.newHashSet();
    
    /** All blocks which have delayed baking models. */
    public static final Set<Block> DELAYED_BAKE = Sets.newHashSet();
    
    /** Straight walls which need delayed baking models. */
    public static final Set<BlockWall> RENDER_STRAIGHT = Sets.newHashSet();
    
    /** Complex walls which need delayed baking models. */
    public static final Set<BlockWall> RENDER_COMPLEX = Sets.newHashSet();
    
    /** Single walls which need delayed baking models. */
    public static final Set<BlockWall> RENDER_SINGLE = Sets.newHashSet();
    
    /** Heaping walls which need delayed baking mdoels. */
    public static final Set<BlockWall> RENDER_HEAPING = Sets.newHashSet();
    
    // Fluids
    public static Fluid tarFluid = new Fluid("fluid_tar", new ResourceLocation(Geomastery.MODID, "blocks/liquids/tar_still"), new ResourceLocation(Geomastery.MODID, "blocks/liquids/tar_flowing")).setViscosity(10000);
    public static BlockFluidBase tar;
    
    // TEST
    public static final BlockTrunk TRUNK = makeItemBuilding(new BlockTrunk());
    public static final BlockLeaves LEAF = makeItemBlock(new BlockLeaves("leaves"));
    
    // Beds
    public static final BlockBed BED_LEAF =   makeItemBuilding(new BlockBed.Leaf());
    public static final BlockBed BED_COTTON = makeItemBuilding(new BlockBed.Cotton());
    public static final BlockBed BED_WOOL =   makeItemBuilding(new BlockBed.Wool());
    public static final BlockBed BED_SIMPLE = makeItemBuilding(new BlockBed.Simple());
    
    // Antler
    public static final Block ANTLER = makeItemless(new BlockAntler());
    
    // Beehive
    public static final BlockBeehive BEEHIVE = makeItemBlock(new BlockBeehive());
    
    // Carcasses
    public static final BlockCarcass CARCASS_CHICKEN =    makeItemBuilding(new BlockCarcass.Chicken());
    public static final BlockCarcass CARCASS_COWPART =    makeItemBuilding(new BlockCarcass.Cowpart());
    public static final BlockCarcass CARCASS_PIG =        makeItemBuilding(new BlockCarcass.Pig());
    public static final BlockCarcass CARCASS_SHEEP =      makeItemBuilding(new BlockCarcass.Sheep());
    public static final BlockCarcass CARCASS_RABBIT =     makeItemBuilding(new BlockCarcass.Rabbit());

    // Multiblock complex devices
    public static final BlockContainerMulti.Candlemaker CRAFTING_CANDLEMAKER =      makeItemBuilding(new BlockContainerMulti.Candlemaker());
    public static final BlockContainerMulti.Forge CRAFTING_FORGE =                  makeItemBuilding(new BlockContainerMulti.Forge());
    public static final BlockContainerMulti.Mason CRAFTING_MASON =                  makeItemBuilding(new BlockContainerMulti.Mason());
    public static final BlockContainerMulti.Textiles CRAFTING_TEXTILES =            makeItemBuilding(new BlockContainerMulti.Textiles());
    public static final BlockContainerMulti.Woodworking CRAFTING_WOODWORKING =      makeItemBuilding(new BlockContainerMulti.Woodworking());
    public static final BlockContainerMulti.Armourer CRAFTING_ARMOURER =            makeItemBuilding(new BlockContainerMulti.Armourer());
    public static final BlockSawpit CRAFTING_SAWPIT =                               makeItemBuilding(new BlockSawpit());
    public static final BlockContainerMulti.Clay FURNACE_CLAY =                     makeItemBuilding(new BlockContainerMulti.Clay());
    public static final BlockContainerMulti.Stone FURNACE_STONE =                   makeItemBuilding(new BlockContainerMulti.Stone());

    // Single complex blocks
    public static final BlockBuildingAbstract<?> CRAFTING_KNAPPING =  makeItemBuilding(new BlockContainerSingle.Knapping());
    public static final BlockBuildingAbstract<?> DRYING =             makeItemBuilding(new BlockContainerSingle.Drying());
    public static final BlockBuildingAbstract<?> COMPOSTHEAP =        makeItemBuilding(new BlockContainerFacing.Compostheap(), true);
    public static final BlockBuildingAbstract<?> FURNACE_CAMPFIRE =   makeItemBuilding(new BlockContainerSingle.Campfire());
    public static final BlockBuildingAbstract<?> FURNACE_POTFIRE =    makeItemBuilding(new BlockContainerSingle.Potfire());
    public static final BlockBuildingAbstract<?> CHEST =              makeItemBuilding(new BlockContainerFacing.Chest());
    public static final BlockBuildingAbstract<?> BASKET =             makeItemBuilding(new BlockContainerSingle.Basket());
    public static final BlockBuildingAbstract<?> BOX =                makeItemBuilding(new BlockContainerSingle.Box());
    
    // Lights
    public static final BlockBuildingAbstract<?> CANDLE_BEESWAX = makeItemBuilding(new BlockLight.Candle("candle_beeswax", 0.005F, 15));
    public static final BlockBuildingAbstract<?> CANDLE_TALLOW =  makeItemBuilding(new BlockLight.Candle("candle_tallow", 0.02F, 15));
    public static final BlockBuildingAbstract<?> TORCH_TALLOW =   makeItemBuilding(new BlockLight.Torch("torch_tallow", 0.005F, 4));
    public static final BlockBuildingAbstract<?> TORCH_TAR =      makeItemBuilding(new BlockLight.Torch("torch_tar", 0.02F, 4));
    public static final BlockBuildingAbstract<?> LAMP_CLAY =      makeItemBuilding(new BlockLight.Lamp("lamp_clay", 4));
    
    // Single crops
    public static final BlockCrop CHICKPEA =  makeItemless(new BlockCrop.Chickpea());
    public static final BlockCrop COTTON =    makeItemless(new BlockCrop.Cotton());
    public static final BlockCrop HEMP =      makeItemless(new BlockCrop.Hemp());
    public static final BlockCrop PEPPER =    makeItemless(new BlockCrop.Pepper());
    public static final BlockCrop WHEAT =     makeItemless(new BlockCrop.Wheat());
    public static final BlockCrop CARROT =    makeItemless(new BlockCrop.Carrot());
    public static final BlockCrop POTATO =    makeItemless(new BlockCrop.Potato());
    public static final BlockCrop BEETROOT =  makeItemless(new BlockCrop.Beetroot());
    
    // Harvestable crops
    public static final BlockCropHarvestable BERRY =  makeItemless(new BlockCropHarvestable.Berry());
    public static final BlockCropHarvestable BEAN =   makeItemless(new BlockCropHarvestable.Bean());
    public static final BlockCropHarvestable TOMATO = makeItemless(new BlockCropHarvestable.Tomato());
    
    // Blockfruit crops
    public static final BlockCropBlockfruit MELON_CROP =   makeItemless(new BlockCropBlockfruit.Melon(), CROP_BLOCKFRUIT);
    public static final BlockCropBlockfruit PUMPKIN_CROP = makeItemless(new BlockCropBlockfruit.Pumpkin(), CROP_BLOCKFRUIT);
    
    // Block fruits
    public static final BlockFruit MELON_FRUIT = makeItemBlock(new BlockFruit("melon_fruit", () -> GeoItems.MELON, () -> GeoItems.SEED_MELON));
    public static final BlockFruit PUMPKIN_FRUIT =   makeItemBlock(new BlockFruit("pumpkin_fruit", () -> GeoItems.PUMPKIN, () -> GeoItems.SEED_PUMPKIN));
    
    // Rice
    public static final Block RICE_BASE = makeItemless(new BlockRiceBase());
    public static final Block RICE_TOP =  makeItemless(new BlockRiceTop());
    
    // Baby mushrooms
    public static final Block MUSHROOMBABY_BROWN =  makeItemless(new BlockMushroombaby("mushroombaby_brown", () -> Blocks.BROWN_MUSHROOM));
    public static final Block MUSHROOMBABY_RED =    makeItemless(new BlockMushroombaby("mushroombaby_red", () -> Blocks.RED_MUSHROOM));
    
    // Seedlings
    public static final BlockSeedling SEEDLING_APPLE =  makeItemBlock(new BlockSeedling.Apple());
    public static final BlockSeedling SEEDLING_PEAR =   makeItemBlock(new BlockSeedling.Pear());
    public static final BlockSeedling SEEDLING_ORANGE = makeItemBlock(new BlockSeedling.Orange());
    public static final BlockSeedling SEEDLING_BANANA = makeItemBlock(new BlockSeedling.Banana());
    
    // Leaves
    public static final BlockHarvestableLeaves LEAF_APPLE =  makeItemBlock(new BlockHarvestableLeaves("leaf_apple", () -> GeoItems.APPLE, () -> SEEDLING_APPLE, 0.05F), LEAVES);
    public static final BlockHarvestableLeaves LEAF_BANANA = makeItemBlock(new BlockHarvestableLeaves("leaf_banana", () -> GeoItems.BANANA, () -> SEEDLING_BANANA, 0.15F), LEAVES);
    public static final BlockHarvestableLeaves LEAF_PEAR =   makeItemBlock(new BlockHarvestableLeaves("leaf_pear", () -> GeoItems.PEAR, () -> SEEDLING_PEAR, 0.05F), LEAVES);
    public static final BlockHarvestableLeaves LEAF_ORANGE = makeItemBlock(new BlockHarvestableLeaves("leaf_orange", () -> GeoItems.ORANGE, () -> SEEDLING_ORANGE, 0.1F), LEAVES);
    
    // Logs
    public static final Block WOOD_APPLE =  makeItemBlock(new BlockWood("wood_apple", 2F));
    public static final Block WOOD_BANANA = makeItemBlock(new BlockWood("wood_banana", 1F));
    public static final Block WOOD_PEAR =   makeItemBlock(new BlockWood("wood_pear", 2F));
    public static final Block WOOD_ORANGE = makeItemBlock(new BlockWood("wood_orange", 2F));

    // Ores
    public static final Block LODE_AMETHYST =  makeItemBlock(new BlockSolid(Material.ROCK, "lode_amethyst", 4F, () -> GeoItems.AMETHYST, 2, EToolType.PICKAXE));
    public static final Block LODE_FIREOPAL =  makeItemBlock(new BlockSolid(Material.ROCK, "lode_fireopal", 4F, () -> GeoItems.FIREOPAL, 5, EToolType.PICKAXE));
    public static final Block LODE_RUBY =      makeItemBlock(new BlockSolid(Material.ROCK, "lode_ruby", 4F, () -> GeoItems.RUBY, 3, EToolType.PICKAXE));
    public static final Block LODE_SAPPHIRE =  makeItemBlock(new BlockSolid(Material.ROCK, "lode_sapphire", 4F, () -> GeoItems.SAPPHIRE, 8, EToolType.PICKAXE));
    public static final Block ORE_COPPER =     makeItemBlock(new BlockSolid(Material.ROCK, "ore_copper", 3F, () -> GeoItems.ORE_COPPER, 1, EToolType.PICKAXE));
    public static final Block ORE_SILVER =     makeItemBlock(new BlockSolid(Material.ROCK, "ore_silver", 3F, () -> GeoItems.ORE_SILVER, 1, EToolType.PICKAXE));
    public static final Block ORE_TIN =        makeItemBlock(new BlockSolid(Material.ROCK, "ore_tin", 3F, () -> GeoItems.ORE_TIN, 1, EToolType.PICKAXE));
    
    // Rocks
    public static final Block SALT =  makeItemBlock(new BlockSolid(Material.ROCK, "salt", 1F, () -> GeoItems.SALT, 1, EToolType.PICKAXE));
    public static final Block CHALK = makeItemBlock(new BlockSolid(Material.ROCK, "chalk", 3F, () -> GeoItems.CHALK, 1, EToolType.PICKAXE));
    public static final Block PEAT =  makeItemBlock(new BlockSolid(BlockMaterial.SOIL, "peat", 0.5F, () -> GeoItems.PEAT_WET, 1, EToolType.SHOVEL));
    
    // Rubble
    public static final BlockBuildingAbstract<?> RUBBLE = makeItemBuilding(new BlockRubble());
    
    // Walls (in rendering priority order)
    public static final BlockBuildingAbstract<?> WALL_LOG =     makeItemBuilding(new BlockWallLog(), RENDER_STRAIGHT, DELAYED_BAKE); 
    public static final BlockBuildingAbstract<?> FENCE =        makeItemBuilding(new BlockWallFence(), RENDER_SINGLE, DELAYED_BAKE);
    public static final BlockBuildingAbstract<?> WALL_POLE =    makeItemBuilding(new BlockWallThin(BlockMaterial.WOOD_FURNITURE, "wall_pole", 2F, 180, 4), RENDER_SINGLE, DELAYED_BAKE);
    public static final BlockBuildingAbstract<?> FRAME =        makeItemBuilding(new BlockWallThin(BlockMaterial.WOOD_FURNITURE, "frame", 2F, 180, 6), RENDER_SINGLE, DELAYED_BAKE);
    public static final BlockBuildingAbstract<?> WALL_MUD =     makeItemBuilding(new BlockWallHeaping(BlockMaterial.STONE_FURNITURE, "wall_mud", 1F, 0), RENDER_HEAPING, DELAYED_BAKE);
    public static final BlockBuildingAbstract<?> WALL_ROUGH =   makeItemBuilding(new BlockWallHeaping(BlockMaterial.STONE_FURNITURE, "wall_rough", 1.5F, 0), RENDER_HEAPING, DELAYED_BAKE);
    public static final BlockBuildingAbstract<?> WALL_BRICK =   makeItemBuilding(new BlockWallComplex(BlockMaterial.STONE_FURNITURE, "wall_brick", 2F), RENDER_COMPLEX, DELAYED_BAKE); 
    public static final BlockBuildingAbstract<?> WALL_STONE =   makeItemBuilding(new BlockWallComplex(BlockMaterial.STONE_FURNITURE, "wall_stone", 2F), RENDER_COMPLEX, DELAYED_BAKE);
    
    // Complex stairs
    public static final BlockBuildingAbstract<?> STAIRS_BRICK = makeItemBuilding(new BlockStairsComplex(BlockMaterial.STONE_FURNITURE, "stairs_brick", 3F, 2));
    public static final BlockBuildingAbstract<?> STAIRS_STONE = makeItemBuilding(new BlockStairsComplex(BlockMaterial.STONE_FURNITURE, "stairs_stone", 3F, 2));
    
    // Double joining stairs
    public static final BlockBuildingAbstract<?> STAIRS_WOOD = makeItemBuilding(new BlockStairsStraight.Joining("stairs_wood", 2F, 4));
    
    // Single stairs
    public static final BlockBuildingAbstract<?> STAIRS_POLE = makeItemBuilding(new BlockStairsStraight.Single("stairs_pole", 2F, 4));
    
    // Vaults
    public static final BlockVault VAULT_BRICK =        makeItemBuilding(new BlockVaultDoubling("vault_brick", EBlockWeight.HEAVY, 2));
    public static final BlockVault VAULT_STONE =        makeItemBuilding(new BlockVaultDoubling("vault_stone", EBlockWeight.HEAVY, 2));
    public static final BlockVault VAULT_FRAME =        makeItemBuilding(new BlockVault("vault_frame", BlockMaterial.WOOD_FURNITURE, EBlockWeight.LIGHT, 6));
        
    // Doors
    public static final BlockDoor DOOR_POLE = makeItemBuilding(new BlockDoor("door_pole"));
    public static final BlockDoor DOOR_WOOD = makeItemBuilding(new BlockDoor("door_wood"));
    
    // Window
    public static final BlockBuildingAbstract<?> WINDOW = makeItemBuilding(new BlockWindow());
    
    // Ladder
    public static final BlockLadder LADDER = makeItemBuilding(new BlockLadder());
    
    // Beams
    public static final BlockBeam BEAM_THIN =  makeItemBuilding(new BlockBeam("beam_thick", 4, 8), DELAYED_BAKE);
    public static final BlockBeam BEAM_THICK = makeItemBuilding(new BlockBeam("beam_thin", 2, 4), DELAYED_BAKE);
        
    // Slabs
    public static final BlockSlab SLAB_STONE = makeItemBuilding(new BlockSlab("slab_stone"));
    public static final BlockSlab SLAB_BRICK = makeItemBuilding(new BlockSlab("slab_brick"));
    
    // Flat roofs
    public static final BlockBuildingAbstract<?> FLATROOF_POLE = makeItemBuilding(new BlockFlatroof("flatroof_pole", 1F));
    
    // Pitched roofs
    public static final BlockBuildingAbstract<?> PITCHROOF_CLAY = makeItemBuilding(new BlockPitchroof(BlockMaterial.WOOD_FURNITURE, "pitchroof_clay", 2F, 4));
    
    /** Adjusts vanilla blocks, register fluids. */
    public static void preInit() {
        
        Geomastery.LOG.info("Registering fluids");
        FluidRegistry.registerFluid(tarFluid);
        GameRegistry.register(tar = makeItemless(new BlockTar()));
                
        Geomastery.LOG.info("Altering vanilla block properties");
        Blocks.LOG.setHarvestLevel("axe", 1);
        Blocks.LOG2.setHarvestLevel("axe", 1);
        Blocks.DIRT.setHarvestLevel("shovel", 1);
        Blocks.GRASS.setHarvestLevel("shovel", 1);
        Blocks.SAND.setHarvestLevel("shovel", 1);
        Blocks.CLAY.setHardness(1.8F).setHarvestLevel("shovel", 1);
        Blocks.GRAVEL.setHarvestLevel("shovel", 1);
        Blocks.COBBLESTONE.setHarvestLevel("pickaxe", 1);
        Blocks.STONE.setHarvestLevel("pickaxe", 1);
        Blocks.COAL_ORE.setHarvestLevel("pickaxe", 1);
        Blocks.DIAMOND_ORE.setHardness(4F).setHarvestLevel("pickaxe", 1);
        Blocks.EMERALD_ORE.setHardness(4F).setHarvestLevel("pickaxe", 1);
        Blocks.GOLD_ORE.setHardness(3F).setHarvestLevel("pickaxe", 1);
        Blocks.IRON_ORE.setHardness(3F).setHarvestLevel("pickaxe", 1);
        Blocks.LAPIS_ORE.setHardness(4F).setHarvestLevel("pickaxe", 1);
        Blocks.LIT_REDSTONE_ORE.setHardness(4F).setHarvestLevel("pickaxe", 1);
        Blocks.REDSTONE_ORE.setHardness(4F).setHarvestLevel("pickaxe", 1);
        Blocks.QUARTZ_ORE.setHarvestLevel("pickaxe", 1);
        Blocks.LEAVES.setHardness(0.2F)
                .setHarvestLevel(EToolType.MACHETE.name(), 1);
        Blocks.REEDS.setHardness(0.2F)
                .setHarvestLevel(EToolType.SICKLE.name(), 1);
    }

    /** Creates an ItemBlock, adds to maps and sets as needed. */ 
    private static <B extends Block> B makeItemBlock(B block,
            int stackSize, boolean isOffhandOnly, Set<? super B>... sets) {

        Item item = new ItemBlock(block).setMaxStackSize(stackSize)
                .setRegistryName(block.getRegistryName());
        
        if (isOffhandOnly) {
            
            OFFHAND_ONLY.add(item);
        }
        
        for (Set<? super B> set : sets) {
            
            set.add(block);
        }
        
        GeoItems.ITEMS.add(item);
        BLOCKS.add(block);
        return block;
    }
    
    /** Gets ItemBuilding, adds to maps and sets as needed. */
    private static <B extends BlockBuildingAbstract<?>> B makeItemBuilding(
            B block, boolean isOffhandOnly, Set<? super B>...sets) {
        
        ItemPlacing item = block.getItem();
        
        if (isOffhandOnly) {
            
            OFFHAND_ONLY.add(item);
        }
        
        for (Set<? super B> set : sets) {
            
            set.add(block);
        }
        
        GeoItems.ITEMS.add(item);
        BLOCKS.add(block);
        return block;
    }
    
    /** Adds to maps and sets as needed without any item. */
    private static <B extends Block> B makeItemless(B block,
            Set<? super B>... sets) {
        
        BLOCKS.add(block);
        
        for (Set<? super B> set : sets) {
            
            set.add(block);
        }
        
        return block;
    }
    
    private static <B extends BlockBuildingAbstract<?>> B makeItemBuilding(
            B block, Set<? super B>...sets) {
        
        return makeItemBuilding(block, false, sets);
    }
    
    private static <B extends Block> B makeItemBlock(B block,
            Set<? super B>... sets) {
        
        return makeItemBlock(block, 1, false, sets);
    }
}

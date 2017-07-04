/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.main;

import java.util.Map;
import java.util.Set;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import jayavery.geomastery.blocks.BlockAntler;
import jayavery.geomastery.blocks.BlockBasket;
import jayavery.geomastery.blocks.BlockBeam;
import jayavery.geomastery.blocks.BlockBed;
import jayavery.geomastery.blocks.BlockBeehive;
import jayavery.geomastery.blocks.BlockBox;
import jayavery.geomastery.blocks.BlockCarcass;
import jayavery.geomastery.blocks.BlockChest;
import jayavery.geomastery.blocks.BlockCompostheap;
import jayavery.geomastery.blocks.BlockCraftingKnapping;
import jayavery.geomastery.blocks.BlockCrop;
import jayavery.geomastery.blocks.BlockCropBlockfruit;
import jayavery.geomastery.blocks.BlockCropHarvestable;
import jayavery.geomastery.blocks.BlockDoor;
import jayavery.geomastery.blocks.BlockDrying;
import jayavery.geomastery.blocks.BlockFlatroof;
import jayavery.geomastery.blocks.BlockFruit;
import jayavery.geomastery.blocks.BlockFurnaceCampfire;
import jayavery.geomastery.blocks.BlockFurnacePotfire;
import jayavery.geomastery.blocks.BlockHarvestableLeaves;
import jayavery.geomastery.blocks.BlockLight;
import jayavery.geomastery.blocks.BlockMultiCrafting;
import jayavery.geomastery.blocks.BlockMushroombaby;
import jayavery.geomastery.blocks.BlockPitchroof;
import jayavery.geomastery.blocks.BlockRiceBase;
import jayavery.geomastery.blocks.BlockRiceTop;
import jayavery.geomastery.blocks.BlockRubble;
import jayavery.geomastery.blocks.BlockSeedling;
import jayavery.geomastery.blocks.BlockSlab;
import jayavery.geomastery.blocks.BlockSolid;
import jayavery.geomastery.blocks.BlockStairsComplex;
import jayavery.geomastery.blocks.BlockStairsStraight;
import jayavery.geomastery.blocks.BlockTar;
import jayavery.geomastery.blocks.BlockVault;
import jayavery.geomastery.blocks.BlockWall;
import jayavery.geomastery.blocks.BlockWallComplex;
import jayavery.geomastery.blocks.BlockWallFence;
import jayavery.geomastery.blocks.BlockWallHeaping;
import jayavery.geomastery.blocks.BlockWallLog;
import jayavery.geomastery.blocks.BlockWallThin;
import jayavery.geomastery.blocks.BlockWindow;
import jayavery.geomastery.blocks.BlockWood;
import jayavery.geomastery.utilities.BlockMaterial;
import jayavery.geomastery.utilities.BlockWeight;
import jayavery.geomastery.utilities.ToolType;
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
    
    // Fluids
    public static Fluid tarFluid = new Fluid("fluid_tar", new ResourceLocation(Geomastery.MODID, "blocks/liquids/tar_still"), new ResourceLocation(Geomastery.MODID, "blocks/liquids/tar_flowing")).setViscosity(10000);
    public static BlockFluidBase tar;
    
    // Beds
    public static final BlockBed BED_LEAF =   makeItemless(new BlockBed.Leaf());
    public static final BlockBed BED_COTTON = makeItemless(new BlockBed.Cotton());
    public static final BlockBed BED_WOOL =   makeItemless(new BlockBed.Wool());
    public static final BlockBed BED_SIMPLE = makeItemless(new BlockBed.Simple());
    
    // Antler
    public static final Block ANTLER = makeItemless(new BlockAntler());
    
    // Carcasses
    public static final BlockCarcass CARCASS_CHICKEN =    makeItemless(new BlockCarcass.Chicken());
    public static final BlockCarcass CARCASS_COWPART =    makeItemless(new BlockCarcass.Cowpart());
    public static final BlockCarcass CARCASS_PIG =        makeItemless(new BlockCarcass.Pig());
    public static final BlockCarcass CARCASS_SHEEP =      makeItemless(new BlockCarcass.Sheep());
    public static final BlockCarcass CARCASS_RABBIT =     makeItemless(new BlockCarcass.Rabbit());

    // Multiblock crafting devices
    public static final BlockMultiCrafting.Candlemaker CRAFTING_CANDLEMAKER =      makeItemless(new BlockMultiCrafting.Candlemaker());
    public static final BlockMultiCrafting.Forge CRAFTING_FORGE =                  makeItemless(new BlockMultiCrafting.Forge());
    public static final BlockMultiCrafting.Mason CRAFTING_MASON =                  makeItemless(new BlockMultiCrafting.Mason());
    public static final BlockMultiCrafting.Textiles CRAFTING_TEXTILES =            makeItemless(new BlockMultiCrafting.Textiles());
    public static final BlockMultiCrafting.Woodworking CRAFTING_WOODWORKING =      makeItemless(new BlockMultiCrafting.Woodworking());
    public static final BlockMultiCrafting.Armourer CRAFTING_ARMOURER =            makeItemless(new BlockMultiCrafting.Armourer());
    public static final BlockMultiCrafting.Sawpit CRAFTING_SAWPIT =                makeItemless(new BlockMultiCrafting.Sawpit());
    
    // Multiblock furnaces
    public static final BlockMultiCrafting.Clay FURNACE_CLAY =   makeItemless(new BlockMultiCrafting.Clay());
    public static final BlockMultiCrafting.Stone FURNACE_STONE = makeItemless(new BlockMultiCrafting.Stone());
    
    // Beehive
    public static final BlockBeehive BEEHIVE = make(new BlockBeehive());

    // Lights
    public static final Block CANDLE_BEESWAX = make(new BlockLight.Candle("candle_beeswax", 0.005F), 15);
    public static final Block CANDLE_TALLOW =  make(new BlockLight.Candle("candle_tallow", 0.02F), 15);
    public static final Block TORCH_TALLOW =   make(new BlockLight.Torch("torch_tallow", 0.005F), 4);
    public static final Block TORCH_TAR =      make(new BlockLight.Torch("torch_tar", 0.02F), 4);
    public static final Block LAMP_CLAY =      make(new BlockLight.Lamp("lamp_clay"), 4);
    
    // Single complex blocks
    public static final Block CRAFTING_KNAPPING =  make(new BlockCraftingKnapping());
    public static final Block DRYING =             make(new BlockDrying());
    public static final Block COMPOSTHEAP =        make(new BlockCompostheap(), true);
    public static final Block FURNACE_CAMPFIRE =   make(new BlockFurnaceCampfire());
    public static final Block FURNACE_POTFIRE =    make(new BlockFurnacePotfire());
    public static final Block CHEST =              make(new BlockChest());
    public static final Block BASKET =             make(new BlockBasket());
    public static final Block BOX =                make(new BlockBox());
    
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
    public static final BlockFruit PUMPKIN_FRUIT = make(new BlockFruit("melon_fruit", () -> GeoItems.MELON, () -> GeoItems.SEED_MELON));
    public static final BlockFruit MELON_FRUIT =   make(new BlockFruit("pumpkin_fruit", () -> GeoItems.PUMPKIN, () -> GeoItems.SEED_PUMPKIN));
    
    // Rice
    public static final Block RICE_BASE = makeItemless(new BlockRiceBase());
    public static final Block RICE_TOP =  makeItemless(new BlockRiceTop());
    
    // Baby mushrooms
    public static final Block MUSHROOMBABY_BROWN =   makeItemless(new BlockMushroombaby("mushroombaby_brown", Blocks.BROWN_MUSHROOM));
    public static final Block MUSHROOMBABY_RED = makeItemless(new BlockMushroombaby("mushroombaby_red", Blocks.RED_MUSHROOM));
    
    // Seedlings
    public static final BlockSeedling SEEDLING_APPLE =  make(new BlockSeedling.Apple());
    public static final BlockSeedling SEEDLING_PEAR =   make(new BlockSeedling.Pear());
    public static final BlockSeedling SEEDLING_ORANGE = make(new BlockSeedling.Orange());
    public static final BlockSeedling SEEDLING_BANANA = make(new BlockSeedling.Banana());
    
    // Leaves
    public static final BlockHarvestableLeaves LEAF_APPLE =  make(new BlockHarvestableLeaves("leaf_apple", () -> GeoItems.APPLE, () -> SEEDLING_APPLE, 0.05F), LEAVES);
    public static final BlockHarvestableLeaves LEAF_BANANA = make(new BlockHarvestableLeaves("leaf_banana", () -> GeoItems.BANANA, () -> SEEDLING_BANANA, 0.15F), LEAVES);
    public static final BlockHarvestableLeaves LEAF_PEAR =   make(new BlockHarvestableLeaves("leaf_pear", () -> GeoItems.PEAR, () -> SEEDLING_PEAR, 0.05F), LEAVES);
    public static final BlockHarvestableLeaves LEAF_ORANGE = make(new BlockHarvestableLeaves("leaf_orange", () -> GeoItems.ORANGE, () -> SEEDLING_ORANGE, 0.1F), LEAVES);
    
    // Logs
    public static final Block WOOD_APPLE =  make(new BlockWood("wood_apple", 2F));
    public static final Block WOOD_BANANA = make(new BlockWood("wood_banana", 1F));
    public static final Block WOOD_PEAR =   make(new BlockWood("wood_pear", 2F));
    public static final Block WOOD_ORANGE = make(new BlockWood("wood_orange", 2F));

    // Ores
    public static final Block LODE_AMETHYST =  make(new BlockSolid(Material.ROCK, "lode_amethyst", 4F, BlockWeight.HEAVY, () -> GeoItems.AMETHYST, 2, ToolType.PICKAXE));
    public static final Block LODE_FIREOPAL =  make(new BlockSolid(Material.ROCK, "lode_fireopal", 4F, BlockWeight.HEAVY, () -> GeoItems.FIREOPAL, 5, ToolType.PICKAXE));
    public static final Block LODE_RUBY =      make(new BlockSolid(Material.ROCK, "lode_ruby", 4F, BlockWeight.HEAVY, () -> GeoItems.RUBY, 3, ToolType.PICKAXE));
    public static final Block LODE_SAPPHIRE =  make(new BlockSolid(Material.ROCK, "lode_sapphire", 4F, BlockWeight.HEAVY, () -> GeoItems.SAPPHIRE, 8, ToolType.PICKAXE));
    public static final Block ORE_COPPER =     make(new BlockSolid(Material.ROCK, "ore_copper", 3F, BlockWeight.HEAVY, () -> GeoItems.ORE_COPPER, 1, ToolType.PICKAXE));
    public static final Block ORE_SILVER =     make(new BlockSolid(Material.ROCK, "ore_silver", 3F, BlockWeight.HEAVY, () -> GeoItems.ORE_SILVER, 1, ToolType.PICKAXE));
    public static final Block ORE_TIN =        make(new BlockSolid(Material.ROCK, "ore_tin", 3F, BlockWeight.HEAVY, () -> GeoItems.ORE_TIN, 1, ToolType.PICKAXE));
    
    // Rocks
    public static final Block SALT =  make(new BlockSolid(Material.ROCK, "salt", 1F, BlockWeight.MEDIUM, () -> GeoItems.SALT, 1, ToolType.PICKAXE));
    public static final Block CHALK = make(new BlockSolid(Material.ROCK, "chalk", 3F, BlockWeight.MEDIUM, () -> GeoItems.CHALK, 1, ToolType.PICKAXE));
    public static final Block PEAT =  make(new BlockSolid(BlockMaterial.SOIL, "peat", 0.5F, BlockWeight.MEDIUM, () -> GeoItems.PEAT_WET, 1, ToolType.SHOVEL));
    
    // Rubble
    public static final Block RUBBLE = makeItemless(new BlockRubble());
    
    // Walls (in rendering priority order)
    public static final Block WALL_LOG =                       make(new BlockWallLog(), RENDER_STRAIGHT, DELAYED_BAKE); 
    public static final Block FENCE =                          make(new BlockWallFence(BlockMaterial.WOOD_FURNITURE, "fence", 2F, ToolType.AXE, 90), RENDER_SINGLE, DELAYED_BAKE);
    public static final Block WALL_POLE =                      make(new BlockWallThin(BlockMaterial.WOOD_FURNITURE, "wall_pole", 2F, ToolType.AXE, 180), 4, RENDER_SINGLE, DELAYED_BAKE);
    public static final Block FRAME =                          make(new BlockWallThin(BlockMaterial.WOOD_FURNITURE, "frame", 2F, ToolType.AXE, 180), 6, RENDER_SINGLE, DELAYED_BAKE);
    public static final BlockWallHeaping WALL_MUD_SINGLE =     makeItemless(new BlockWallHeaping(BlockMaterial.STONE_FURNITURE, "wall_mud_single", 1F, ToolType.PICKAXE, false, 0, () -> GeoItems.WALL_MUD), RENDER_SINGLE, DELAYED_BAKE);
    public static final BlockWallHeaping WALL_ROUGH_SINGLE =   makeItemless(new BlockWallHeaping(BlockMaterial.STONE_FURNITURE, "wall_rough_single", 1.5F, ToolType.PICKAXE, false, 0, () -> GeoItems.WALL_ROUGH), RENDER_SINGLE, DELAYED_BAKE);
    public static final BlockWallComplex WALL_BRICK_SINGLE =   makeItemless(new BlockWallComplex(BlockMaterial.STONE_FURNITURE, "wall_brick_single", 2F, ToolType.PICKAXE, false, () -> GeoItems.WALL_BRICK), RENDER_COMPLEX, DELAYED_BAKE); 
    public static final BlockWallComplex WALL_STONE_SINGLE =   makeItemless(new BlockWallComplex(BlockMaterial.STONE_FURNITURE, "wall_stone_single", 2F, ToolType.PICKAXE, false, () -> GeoItems.WALL_STONE), RENDER_COMPLEX, DELAYED_BAKE);
    public static final BlockWallHeaping WALL_MUD_DOUBLE =     makeItemless(new BlockWallHeaping(BlockMaterial.STONE_FURNITURE, "wall_mud_double", 3F, ToolType.PICKAXE, true, 0, () -> GeoItems.WALL_MUD), RENDER_SINGLE, DELAYED_BAKE); 
    public static final BlockWallHeaping WALL_ROUGH_DOUBLE =   makeItemless(new BlockWallHeaping(BlockMaterial.STONE_FURNITURE, "wall_rough_double", 3F, ToolType.PICKAXE, true, 0, () -> GeoItems.WALL_ROUGH), RENDER_SINGLE, DELAYED_BAKE); 
    public static final BlockWallComplex WALL_BRICK_DOUBLE =   makeItemless(new BlockWallComplex(BlockMaterial.STONE_FURNITURE, "wall_brick_double", 3F, ToolType.PICKAXE, true,  () -> GeoItems.WALL_BRICK), RENDER_COMPLEX, DELAYED_BAKE); 
    public static final BlockWallComplex WALL_STONE_DOUBLE =   makeItemless(new BlockWallComplex(BlockMaterial.STONE_FURNITURE, "wall_stone_double", 3F, ToolType.PICKAXE, true, () -> GeoItems.WALL_STONE), RENDER_COMPLEX, DELAYED_BAKE);
    
    // Complex stairs
    public static final Block STAIRS_BRICK = make(new BlockStairsComplex(BlockMaterial.STONE_FURNITURE, "stairs_brick", 3F, ToolType.PICKAXE), 2);
    public static final Block STAIRS_STONE = make(new BlockStairsComplex(BlockMaterial.STONE_FURNITURE, "stairs_stone", 3F, ToolType.PICKAXE), 2);
    
    // Double joining stairs
    public static final Block STAIRS_WOOD = make(new BlockStairsStraight.Joining("stairs_wood", 2F), 4);
    
    // Single stairs
    public static final Block STAIRS_POLE = make(new BlockStairsStraight.Single("stairs_pole", 2F), 4);
    
    // Vaults
    public static final BlockVault VAULT_STONE_SINGLE = makeItemless(new BlockVault("vault_stone_single", () -> GeoItems.VAULT_STONE, false, BlockWeight.HEAVY));
    public static final BlockVault VAULT_STONE_DOUBLE = makeItemless(new BlockVault("vault_stone_double", () -> GeoItems.VAULT_STONE, true, BlockWeight.HEAVY));
    public static final BlockVault VAULT_BRICK_SINGLE = makeItemless(new BlockVault("vault_brick_single", () -> GeoItems.VAULT_BRICK, false, BlockWeight.HEAVY));
    public static final BlockVault VAULT_BRICK_DOUBLE = makeItemless(new BlockVault("vault_brick_double", () -> GeoItems.VAULT_BRICK, true, BlockWeight.HEAVY));
    public static final BlockVault VAULT_FRAME =        make(new BlockVault("vault_frame", () -> null, false, BlockWeight.LIGHT), 6);
        
    // Doors
    public static final BlockDoor DOOR_POLE = makeItemless(new BlockDoor("door_pole", () -> GeoItems.DOOR_POLE));
    public static final BlockDoor DOOR_WOOD = makeItemless(new BlockDoor("door_wood", () -> GeoItems.DOOR_WOOD));
    
    // Window
    public static final Block WINDOW = make(new BlockWindow(), 4);
    
    // Beams
    public static final BlockBeam BEAM_THIN =  makeItemless(new BlockBeam("beam_thick"), DELAYED_BAKE);
    public static final BlockBeam BEAM_THICK = makeItemless(new BlockBeam("beam_thin"), DELAYED_BAKE);
        
    // Slabs
    public static final BlockSlab SLAB_STONE_SINGLE = makeItemless(new BlockSlab("slab_stone_single", false, () -> GeoItems.SLAB_STONE));
    public static final BlockSlab SLAB_STONE_DOUBLE = makeItemless(new BlockSlab("slab_stone_double", true, () -> GeoItems.SLAB_STONE));
    public static final BlockSlab SLAB_BRICK_SINGLE = makeItemless(new BlockSlab("slab_brick_single", false, () -> GeoItems.SLAB_BRICK));
    public static final BlockSlab SLAB_BRICK_DOUBLE = makeItemless(new BlockSlab("slab_brick_double", true, () -> GeoItems.SLAB_BRICK));
    
    // Flat roofs
    public static final Block FLATROOF_POLE = make(new BlockFlatroof("flatroof_pole", 1F, ToolType.AXE), 4);
    
    // Pitched roofs
    public static final Block PITCHROOF_CLAY = make(new BlockPitchroof(BlockMaterial.WOOD_FURNITURE, "pitchroof_clay", 2F), 4);
    
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
                .setHarvestLevel(ToolType.MACHETE.name(), 1);
        Blocks.REEDS.setHardness(0.2F)
                .setHarvestLevel(ToolType.SICKLE.name(), 1);
        Blocks.PUMPKIN.setHardness(0.2F);
        Blocks.MELON_BLOCK.setHardness(0.2F);
        GeoBlocks.OFFHAND_ONLY.add(Item.getItemFromBlock(Blocks.CHEST)
                .setMaxStackSize(1));
        Item.getItemFromBlock(Blocks.LADDER).setMaxStackSize(8);
    }

    /** Creates an ItemBlock, adds to maps and sets as needed. */ 
    private static <B extends Block> B make(B block,
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
    
    /** Adds to maps and sets as needed without an ItemBlock. */
    private static <B extends Block> B makeItemless(B block,
            Set<? super B>... sets) {
        
        BLOCKS.add(block);
        
        for (Set<? super B> set : sets) {
            
            set.add(block);
        }
        
        return block;
    }
    
    private static <B extends Block> B make(B block, boolean isOffhandOnly,
            Set<? super B>... sets) {
        
        return make(block, 1, isOffhandOnly, sets);
    }
    
    private static <B extends Block> B make(B block, Set<? super B>... sets) {
        
        return make(block, 1, false, sets);
    }
        
    private static <B extends Block> B make(B block, int stackSize,
            Set<? super B>... sets) {
        
        return make(block, stackSize, false, sets);
    }
}

package com.jj.jjmod.init;

import java.util.Set;
import com.google.common.collect.Sets;
import com.jj.jjmod.blocks.BlockAntler;
import com.jj.jjmod.blocks.BlockBeam;
import com.jj.jjmod.blocks.BlockBedBreakable;
import com.jj.jjmod.blocks.BlockBedPlain;
import com.jj.jjmod.blocks.BlockBeehive;
import com.jj.jjmod.blocks.BlockBox;
import com.jj.jjmod.blocks.BlockCarcass;
import com.jj.jjmod.blocks.BlockCarcassChicken;
import com.jj.jjmod.blocks.BlockCarcassCowpart;
import com.jj.jjmod.blocks.BlockCarcassPig;
import com.jj.jjmod.blocks.BlockCarcassRabbit;
import com.jj.jjmod.blocks.BlockCarcassSheep;
import com.jj.jjmod.blocks.BlockCraftingSawpit;
import com.jj.jjmod.blocks.BlockCraftingCandlemaker;
import com.jj.jjmod.blocks.BlockCraftingClayworks;
import com.jj.jjmod.blocks.BlockCraftingForge;
import com.jj.jjmod.blocks.BlockCraftingKnapping;
import com.jj.jjmod.blocks.BlockCraftingMason;
import com.jj.jjmod.blocks.BlockCraftingTextiles;
import com.jj.jjmod.blocks.BlockCraftingWoodworking;
import com.jj.jjmod.blocks.BlockCrop;
import com.jj.jjmod.blocks.BlockCropBeetroot;
import com.jj.jjmod.blocks.BlockCropBlockfruit;
import com.jj.jjmod.blocks.BlockCropBlockfruitMelon;
import com.jj.jjmod.blocks.BlockCropBlockfruitPumpkin;
import com.jj.jjmod.blocks.BlockCropCarrot;
import com.jj.jjmod.blocks.BlockCropChickpea;
import com.jj.jjmod.blocks.BlockCropCotton;
import com.jj.jjmod.blocks.BlockCropHarvestable;
import com.jj.jjmod.blocks.BlockCropHarvestableBean;
import com.jj.jjmod.blocks.BlockCropHarvestableBerry;
import com.jj.jjmod.blocks.BlockCropHarvestableTomato;
import com.jj.jjmod.blocks.BlockCropHemp;
import com.jj.jjmod.blocks.BlockCropPepper;
import com.jj.jjmod.blocks.BlockCropPotato;
import com.jj.jjmod.blocks.BlockCropWheat;
import com.jj.jjmod.blocks.BlockDoor;
import com.jj.jjmod.blocks.BlockDrying;
import com.jj.jjmod.blocks.BlockFence;
import com.jj.jjmod.blocks.BlockFoundation;
import com.jj.jjmod.blocks.BlockFurnaceCampfire;
import com.jj.jjmod.blocks.BlockFurnaceClay;
import com.jj.jjmod.blocks.BlockFurnacePotfire;
import com.jj.jjmod.blocks.BlockFurnaceStone;
import com.jj.jjmod.blocks.BlockHarvestableLeaves;
import com.jj.jjmod.blocks.BlockLight;
import com.jj.jjmod.blocks.BlockLode;
import com.jj.jjmod.blocks.BlockPeat;
import com.jj.jjmod.blocks.BlockRiceBase;
import com.jj.jjmod.blocks.BlockRiceTop;
import com.jj.jjmod.blocks.BlockRock;
import com.jj.jjmod.blocks.BlockSeedling;
import com.jj.jjmod.blocks.BlockSeedlingApple;
import com.jj.jjmod.blocks.BlockSeedlingBanana;
import com.jj.jjmod.blocks.BlockSeedlingOrange;
import com.jj.jjmod.blocks.BlockSeedlingPear;
import com.jj.jjmod.blocks.BlockSlabDouble;
import com.jj.jjmod.blocks.BlockSlabSingle;
import com.jj.jjmod.blocks.BlockStairs;
import com.jj.jjmod.blocks.BlockStairsSimple;
import com.jj.jjmod.blocks.BlockStairsStraight;
import com.jj.jjmod.blocks.BlockVault;
import com.jj.jjmod.blocks.BlockWall;
import com.jj.jjmod.blocks.BlockWallSimple;
import com.jj.jjmod.blocks.BlockWallSingle;
import com.jj.jjmod.blocks.BlockWallStraight;
import com.jj.jjmod.blocks.BlockWood;
import com.jj.jjmod.utilities.BlockMaterial;
import com.jj.jjmod.utilities.ToolType;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModBlocks {
    
    public static final Set<Item> OFFHAND_ONLY = Sets.newHashSet();
    
    public static final Set<Block> HEAVY = Sets.newHashSet();
    public static final Set<Block> LIGHT = Sets.newHashSet();
    
    public static BlockBedPlain bedLeaf;
    public static BlockBedBreakable bedCotton;
    public static BlockBedBreakable bedWool;
    public static BlockBedPlain bedSimple;
    
    public static BlockAntler antler;
    
    public static BlockBeehive beehive;

    public static BlockLight candleBeeswax;
    public static BlockLight candleTallow;
    public static BlockLight torchTallow;
    public static BlockLight torchTar;

    public static BlockCarcass carcassChicken;
    public static BlockCarcass carcassCowpart;
    public static BlockCarcass carcassPig;
    public static BlockCarcass carcassSheep;
    public static BlockCarcass carcassRabbit;

    public static BlockCraftingCandlemaker craftingCandlemaker;
    public static BlockCraftingClayworks craftingClayworks;
    public static BlockCraftingForge craftingForge;
    public static BlockCraftingKnapping craftingKnapping;
    public static BlockCraftingMason craftingMason;
    public static BlockCraftingTextiles craftingTextiles;
    public static BlockCraftingWoodworking craftingWoodworking;
    //ublic static BlockCraftingArmourer craftingArmourer;
    public static BlockCraftingSawpit craftingSawpit;

    public static BlockDrying drying;

    public static BlockFurnaceCampfire furnaceCampfire;
    public static BlockFurnacePotfire furnacePotfire;
    public static BlockFurnaceClay furnaceClay;
    public static BlockFurnaceStone furnaceStone;
    
    public static BlockBox box;

    public static BlockCrop chickpea;
    public static BlockCrop cotton;
    public static BlockCrop hemp;
    public static BlockCrop pepper;
    
    public static BlockCrop wheat;
    public static BlockCrop carrot;
    public static BlockCrop potato;
    public static BlockCrop beetroot;
    
    public static BlockRiceBase riceBase;
    public static BlockRiceTop riceTop;
    
    public static BlockHarvestableLeaves leafApple;
    public static BlockHarvestableLeaves leafBanana;
    public static BlockHarvestableLeaves leafPear;
    public static BlockHarvestableLeaves leafOrange;
    
    public static BlockWood woodApple;
    public static BlockWood woodBanana;
    public static BlockWood woodPear;
    public static BlockWood woodOrange;

    public static BlockLode lodeAmethyst;
    public static BlockLode lodeFireopal;
    public static BlockLode lodeRuby;
    public static BlockLode lodeSapphire;

    public static BlockRock oreCopper;
    public static BlockRock oreSilver;
    public static BlockRock oreTin;
    
    public static BlockRock salt;
    public static BlockRock chalk;
    
    public static BlockPeat peat;
    
    public static BlockCropHarvestable berry;
    public static BlockCropHarvestable bean;
    public static BlockCropHarvestable tomato;
    
    public static BlockCropBlockfruit melon;
    public static BlockCropBlockfruit pumpkin;
    
    public static BlockSeedling seedlingApple;
    public static BlockSeedling seedlingPear;
    public static BlockSeedling seedlingOrange;
    public static BlockSeedling seedlingBanana;
    
    public static BlockWall wallBrickSingle;
    public static BlockWall wallBrickDouble;
    public static BlockWall wallMudSingle;
    public static BlockWall wallMudDouble;
    public static BlockWall wallRoughSingle;
    public static BlockWall wallRoughDouble;
    public static BlockWall wallStoneSingle;
    public static BlockWall wallStoneDouble;
    public static BlockWall wallLogSingle;
    public static BlockWall wallLogDouble;
    public static BlockWallSingle wallPole;
    
    public static BlockStairs stairsBrick;
    public static BlockStairs stairsStone;
    public static BlockStairsStraight stairsWood;
    public static BlockStairsSimple stairsPole;
    
    public static BlockVault vault;
    
    public static BlockFoundation foundation;
    
    public static BlockDoor doorPole;
    public static BlockDoor doorWood;
    
    public static BlockBeam beam;
    
    public static BlockFence fence;
    
    public static BlockSlabSingle slabStoneSingle;
    public static BlockSlabDouble slabStoneDouble;
    public static BlockSlabSingle slabBrickSingle;
    public static BlockSlabDouble slabBrickDouble;
    
    public static void preInit() {
        
        buildSets();
        
        register(bedLeaf = new BlockBedPlain("bed_leaf", 0.2F, 
                () -> null, true, null));
        register(bedCotton = new BlockBedBreakable("bed_cotton", 2.0F,
                () -> ModItems.bedCotton, true, ToolType.NONE));
        register(bedWool = new BlockBedBreakable("bed_wool", 2.0F,
                () -> ModItems.bedWool, true, ToolType.NONE));
        register(bedSimple = new BlockBedPlain("bed_simple", 2.0F,
                () -> ModItems.bedSimple, false, ToolType.NONE));
        
        register(antler = new BlockAntler());
        
        register(beehive = new BlockBeehive());

        register(candleBeeswax = new BlockLight("candle_beeswax",
                10, 0.005F), 15);
        register(candleTallow = new BlockLight("candle_tallow", 10, 0.02F), 15);
        register(torchTallow = new BlockLight("torch_tallow", 13, 0.005F), 4);
        register(torchTar = new BlockLight("torch_tar", 13, 0.02F), 4);

        register(carcassChicken = new BlockCarcassChicken());
        register(carcassCowpart = new BlockCarcassCowpart(), true);
        register(carcassPig = new BlockCarcassPig(), true);
        register(carcassSheep = new BlockCarcassSheep(), true);
        register(carcassRabbit = new BlockCarcassRabbit());

        register(craftingCandlemaker = new BlockCraftingCandlemaker());
        register(craftingClayworks = new BlockCraftingClayworks());
        register(craftingForge = new BlockCraftingForge());
        register(craftingKnapping = new BlockCraftingKnapping());
        register(craftingMason = new BlockCraftingMason());
        register(craftingTextiles = new BlockCraftingTextiles());
        register(craftingWoodworking = new BlockCraftingWoodworking());
        //register(craftingArmourer = new BlockCraftingSawpit());
        register(craftingSawpit = new BlockCraftingSawpit());
        
        register(drying = new BlockDrying());

        register(furnaceCampfire = new BlockFurnaceCampfire());
        register(furnacePotfire = new BlockFurnacePotfire());
        register(furnaceClay = new BlockFurnaceClay());
        register(furnaceStone = new BlockFurnaceStone());
        
        register(box = new BlockBox());

        register(chickpea = new BlockCropChickpea());
        register(cotton = new BlockCropCotton());
        register(hemp = new BlockCropHemp());
        register(pepper = new BlockCropPepper());
        
        register(wheat = new BlockCropWheat());
        register(carrot = new BlockCropCarrot());
        register(potato = new BlockCropPotato());
        register(beetroot = new BlockCropBeetroot());
        
        register(riceBase = new BlockRiceBase());
        register(riceTop = new BlockRiceTop());

        register(leafApple = new BlockHarvestableLeaves("leaf_apple",
                () -> Items.APPLE, 0.2F));
        register(leafBanana = new BlockHarvestableLeaves("leaf_banana",
                () -> ModItems.banana, 0.4F));
        register(leafPear = new BlockHarvestableLeaves("leaf_pear",
                () -> ModItems.pear, 0.2F));
        register(leafOrange = new BlockHarvestableLeaves("leaf_orange",
                () -> ModItems.orange, 0.3F));
        
        register(woodApple = new BlockWood("wood_apple", 2F));
        register(woodBanana = new BlockWood("wood_banana", 1F));
        register(woodPear = new BlockWood("wood_pear", 2F));
        register(woodOrange = new BlockWood("wood_orange", 2F));
        
        register(lodeAmethyst = new BlockLode("lode_amethyst",
                () -> ModItems.amethyst, 2));
        register(lodeFireopal = new BlockLode("lode_fireopal",
                () -> ModItems.fireopal, 5));
        register(lodeRuby = new BlockLode("lode_ruby",
                () -> ModItems.ruby, 3));
        register(lodeSapphire = new BlockLode("lode_sapphire",
                () -> ModItems.sapphire, 8));

        register(oreCopper = new BlockRock("ore_copper", 3F));
        register(oreSilver = new BlockRock("ore_silver", 3F));
        register(oreTin = new BlockRock("ore_tin", 3F));
        
        register(salt = new BlockRock("salt", 1F));
        register(chalk = new BlockRock("chalk", 3F));
        
        register(peat = new BlockPeat());

        register(berry = new BlockCropHarvestableBerry());
        register(bean = new BlockCropHarvestableBean());
        register(tomato = new BlockCropHarvestableTomato());
        
        register(melon = new BlockCropBlockfruitMelon());
        register(pumpkin = new BlockCropBlockfruitPumpkin());
        
        register(seedlingApple = new BlockSeedlingApple());
        register(seedlingPear = new BlockSeedlingPear());
        register(seedlingOrange = new BlockSeedlingOrange());
        register(seedlingBanana = new BlockSeedlingBanana());
        
        register(wallBrickSingle = new BlockWall(BlockMaterial.STONE_FURNITURE,
                "wall_brick_single", 2F, ToolType.PICKAXE, false,
                () -> ModItems.wallBrick, true, 6));
        register(wallBrickDouble = new BlockWall(BlockMaterial.STONE_FURNITURE,
                "wall_brick_double", 3F, ToolType.PICKAXE, true,
                () -> ModItems.wallBrick, true, 6));
        register(wallMudSingle = new BlockWallSimple(BlockMaterial.STONE_FURNITURE,
                "wall_mud_single", 1F, ToolType.PICKAXE, false,
                () -> ModItems.wallMud, false, 1));
        register(wallMudDouble = new BlockWallSimple(BlockMaterial.STONE_FURNITURE,
                "wall_mud_double", 3F, ToolType.PICKAXE, true,
                () -> ModItems.wallMud, false, 1));
        register(wallRoughSingle = new BlockWallSimple(BlockMaterial.STONE_FURNITURE,
                "wall_rough_single", 1.5F, ToolType.PICKAXE, false,
                () -> ModItems.wallRough, true, 1));
        register(wallRoughDouble = new BlockWallSimple(BlockMaterial.STONE_FURNITURE,
                "wall_rough_double", 3F, ToolType.PICKAXE, true,
                () -> ModItems.wallRough, true, 1));
        register(wallStoneSingle = new BlockWall(BlockMaterial.STONE_FURNITURE,
                "wall_stone_single", 2F, ToolType.PICKAXE, false,
                () -> ModItems.wallStone, true, 6));
        register(wallStoneDouble = new BlockWall(BlockMaterial.STONE_FURNITURE,
                "wall_stone_double", 3F, ToolType.PICKAXE, true,
                () -> ModItems.wallStone, true, 6));
        register(wallLogSingle = new BlockWallStraight(BlockMaterial.WOOD_FURNITURE,
                "wall_log_single", 1F, ToolType.AXE, false,
                () -> ModItems.wallLog, false, 4));
        register(wallLogDouble = new BlockWallStraight(BlockMaterial.WOOD_FURNITURE,
                "wall_log_double", 3F, ToolType.AXE, true,
                () -> ModItems.wallLog, false, 4));
        register(wallPole = new BlockWallSingle(BlockMaterial.WOOD_FURNITURE,
                "wall_pole", 2F, ToolType.AXE, false, 4));
        
        register(stairsBrick = new BlockStairs("stairs_brick",
                wallBrickDouble.getDefaultState(), ToolType.PICKAXE));
        register(stairsStone = new BlockStairs("stairs_stone",
                wallStoneDouble.getDefaultState(), ToolType.PICKAXE));
        register(stairsWood = new BlockStairsStraight("stairs_wood",
                2F, ToolType.AXE));
        register(stairsPole = new BlockStairsSimple("stairs_pole",
                2F, ToolType.AXE));
        
        register(vault = new BlockVault());
        
        register(foundation = new BlockFoundation());
        
        register(doorPole = new BlockDoor("door_pole", () -> ModItems.doorPole));
        register(doorWood = new BlockDoor("door_wood", () -> ModItems.doorWood));
        
        register(beam = new BlockBeam());
        
        register(fence = new BlockFence());
        
        register(slabStoneSingle = new BlockSlabSingle(BlockMaterial
                .STONE_FURNITURE, "slab_stone_single", 2F, ToolType.PICKAXE,
                () -> ModItems.slabStone));
        register(slabStoneDouble = new BlockSlabDouble(BlockMaterial
                .STONE_FURNITURE, "slab_stone_double", 2F, ToolType.PICKAXE,
                () -> ModItems.slabStone));
        register(slabBrickSingle = new BlockSlabSingle(BlockMaterial
                .STONE_FURNITURE, "slab_brick_single", 2F, ToolType.PICKAXE,
                () -> ModItems.slabBrick));
        register(slabBrickDouble = new BlockSlabDouble(BlockMaterial
                .STONE_FURNITURE, "slab_brick_double", 2F, ToolType.PICKAXE,
                () -> ModItems.slabBrick));
        
        Blocks.LOG.setHarvestLevel("axe", 1);
        Blocks.LOG2.setHarvestLevel("axe", 1);
        Blocks.DIRT.setHarvestLevel("shovel", 1);
        Blocks.GRASS.setHarvestLevel("shovel", 1);
        Blocks.SAND.setHarvestLevel("shovel", 1);
        Blocks.CLAY.setHarvestLevel("shovel", 1);
        Blocks.GRAVEL.setHarvestLevel("shovel", 1);
        Blocks.COBBLESTONE.setHarvestLevel("pickaxe", 1);
        Blocks.STONE.setHarvestLevel("pickaxe", 1);
        Blocks.COAL_ORE.setHarvestLevel("pickaxe", 1);
        Blocks.DIAMOND_ORE.setHarvestLevel("pickaxe", 1);
        Blocks.EMERALD_ORE.setHarvestLevel("pickaxe", 1);
        Blocks.GOLD_ORE.setHarvestLevel("pickaxe", 1);
        Blocks.IRON_ORE.setHarvestLevel("pickaxe", 1);
        Blocks.LAPIS_ORE.setHarvestLevel("pickaxe", 1);
        Blocks.LIT_REDSTONE_ORE.setHarvestLevel("pickaxe", 1);
        Blocks.REDSTONE_ORE.setHarvestLevel("pickaxe", 1);
        Blocks.QUARTZ_ORE.setHarvestLevel("pickaxe", 1);
    }
    
    private static void register(Block block, boolean isOffhandOnly) {
        
        register(block, 1, isOffhandOnly);
    }
    
    private static void register(Block block) {
        
        register(block, 1, false);
    }
        
    private static void register(Block block, int stackSize) {
        
        register(block, stackSize, false);
    }

    private static void register(Block block,
            int stackSize, boolean isOffhandOnly) {

        Item item = new ItemBlock(block).setMaxStackSize(stackSize);
        
        if (isOffhandOnly) {
            
            OFFHAND_ONLY.add(item);
        }

        GameRegistry.register(block);
        GameRegistry.register(item
                .setRegistryName(block.getRegistryName()));

        ModelLoader.setCustomModelResourceLocation(item, 0,
                new ModelResourceLocation(block.getRegistryName(),
                "inventory"));
    }
    
    private static void buildSets() {
        
        Block[] heavy = {Blocks.BEDROCK, Blocks.COBBLESTONE, Blocks.MOSSY_COBBLESTONE, Blocks.SANDSTONE, Blocks.RED_SANDSTONE, Blocks.OBSIDIAN, Blocks.STONE, Blocks.STONEBRICK, Blocks.PRISMARINE, Blocks.QUARTZ_BLOCK, Blocks.BRICK_BLOCK, Blocks.COAL_ORE, Blocks.DIAMOND_ORE, Blocks.EMERALD_ORE, Blocks.GOLD_ORE, Blocks.IRON_ORE, Blocks.LAPIS_ORE, Blocks.REDSTONE_ORE};
        
        for (Block block : heavy) {
            
            HEAVY.add(block);
        }
        
        Block[] light = {Blocks.CLAY, Blocks.DIRT, Blocks.GRASS, Blocks.GRAVEL, Blocks.ICE, Blocks.PACKED_ICE, Blocks.SAND, Blocks.HARDENED_CLAY, Blocks.STAINED_HARDENED_CLAY};
        
        for (Block block : light) {
            
            LIGHT.add(block);
        }
    }
}

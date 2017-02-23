package com.jj.jjmod.init;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.jj.jjmod.blocks.BlockAntler;
import com.jj.jjmod.blocks.BlockBeam;
import com.jj.jjmod.blocks.BlockBedBreakableAbstract;
import com.jj.jjmod.blocks.BlockBedCotton;
import com.jj.jjmod.blocks.BlockBedLeaf;
import com.jj.jjmod.blocks.BlockBedPlainAbstract;
import com.jj.jjmod.blocks.BlockBedSimple;
import com.jj.jjmod.blocks.BlockBedWool;
import com.jj.jjmod.blocks.BlockBeehive;
import com.jj.jjmod.blocks.BlockBox;
import com.jj.jjmod.blocks.BlockCarcass;
import com.jj.jjmod.blocks.BlockCarcassChicken;
import com.jj.jjmod.blocks.BlockCarcassCowpart;
import com.jj.jjmod.blocks.BlockCarcassPig;
import com.jj.jjmod.blocks.BlockCarcassRabbit;
import com.jj.jjmod.blocks.BlockCarcassSheep;
import com.jj.jjmod.blocks.BlockCraftingArmourer;
import com.jj.jjmod.blocks.BlockCraftingCandlemaker;
import com.jj.jjmod.blocks.BlockCraftingClayworks;
import com.jj.jjmod.blocks.BlockCraftingForge;
import com.jj.jjmod.blocks.BlockCraftingKnapping;
import com.jj.jjmod.blocks.BlockCraftingMason;
import com.jj.jjmod.blocks.BlockCraftingSawpit;
import com.jj.jjmod.blocks.BlockCraftingTextiles;
import com.jj.jjmod.blocks.BlockCraftingWoodworking;
import com.jj.jjmod.blocks.BlockCropAbstract;
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
import com.jj.jjmod.blocks.BlockFruit;
import com.jj.jjmod.blocks.BlockFurnaceCampfire;
import com.jj.jjmod.blocks.BlockFurnaceClay;
import com.jj.jjmod.blocks.BlockFurnaceCookfire;
import com.jj.jjmod.blocks.BlockFurnaceStone;
import com.jj.jjmod.blocks.BlockHarvestableLeaves;
import com.jj.jjmod.blocks.BlockLight;
import com.jj.jjmod.blocks.BlockLightCandle;
import com.jj.jjmod.blocks.BlockLightTorch;
import com.jj.jjmod.blocks.BlockLode;
import com.jj.jjmod.blocks.BlockPeat;
import com.jj.jjmod.blocks.BlockRiceBase;
import com.jj.jjmod.blocks.BlockRiceTop;
import com.jj.jjmod.blocks.BlockRock;
import com.jj.jjmod.blocks.BlockRubble;
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
import com.jj.jjmod.blocks.BlockTar;
import com.jj.jjmod.blocks.BlockVault;
import com.jj.jjmod.blocks.BlockWall;
import com.jj.jjmod.blocks.BlockWallHeightless;
import com.jj.jjmod.blocks.BlockWallStraight;
import com.jj.jjmod.blocks.BlockWallThin;
import com.jj.jjmod.blocks.BlockWood;
import com.jj.jjmod.utilities.BlockMaterial;
import com.jj.jjmod.utilities.ToolType;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModBlocks {
    
    /** Items which are restricted to the offhand slot. */
    public static final Set<Item> OFFHAND_ONLY = Sets.newHashSet();
    
    /** All new blocks, for ease of modelling. */
    private static final Map<Block, Item> MOD_BLOCKS = Maps.newHashMap();

    /** Vanilla blocks classed as Heavy. */
    public static final Set<Block> HEAVY = Sets.newHashSet(Blocks.BEDROCK,
            Blocks.COBBLESTONE, Blocks.MOSSY_COBBLESTONE, Blocks.SANDSTONE,
            Blocks.RED_SANDSTONE, Blocks.OBSIDIAN, Blocks.STONE,
            Blocks.STONEBRICK, Blocks.PRISMARINE, Blocks.QUARTZ_BLOCK,
            Blocks.BRICK_BLOCK, Blocks.COAL_ORE, Blocks.DIAMOND_ORE,
            Blocks.EMERALD_ORE, Blocks.GOLD_ORE, Blocks.IRON_ORE,
            Blocks.LAPIS_ORE, Blocks.REDSTONE_ORE);
    /** Vanilla blocks classed as Light. */
    public static final Set<Block> LIGHT = Sets.newHashSet(Blocks.CLAY,
            Blocks.DIRT, Blocks.GRASS, Blocks.GRAVEL,
            Blocks.ICE, Blocks.PACKED_ICE, Blocks.SAND,
            Blocks.HARDENED_CLAY, Blocks.STAINED_HARDENED_CLAY);
    
    public static BlockBedPlainAbstract bedLeaf;
    public static BlockBedBreakableAbstract bedCotton;
    public static BlockBedBreakableAbstract bedWool;
    public static BlockBedPlainAbstract bedSimple;
    
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
    public static BlockCraftingArmourer craftingArmourer;
    public static BlockCraftingSawpit craftingSawpit;

    public static BlockDrying drying;

    public static BlockFurnaceCampfire furnaceCampfire;
    public static BlockFurnaceCookfire furnaceCookfire;
    public static BlockFurnaceClay furnaceClay;
    public static BlockFurnaceStone furnaceStone;
    
    public static BlockBox box;

    public static BlockCropAbstract chickpea;
    public static BlockCropAbstract cotton;
    public static BlockCropAbstract hemp;
    public static BlockCropAbstract pepper;
    
    public static BlockCropAbstract wheat;
    public static BlockCropAbstract carrot;
    public static BlockCropAbstract potato;
    public static BlockCropAbstract beetroot;
    
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
    
    public static BlockCropBlockfruit melonCrop;
    public static BlockCropBlockfruit pumpkinCrop;
    
    public static BlockFruit pumpkinFruit;
    public static BlockFruit melonFruit;
    
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
    public static BlockWallThin wallPole;
    
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
    
    public static BlockRubble rubble;
    
    public static BlockTar tar;
    
    public static void preInit() {
                
        registerItemless(bedLeaf = new BlockBedLeaf());
        registerItemless(bedCotton = new BlockBedCotton());
        registerItemless(bedWool = new BlockBedWool());
        registerItemless(bedSimple = new BlockBedSimple());
        
        registerItemless(antler = new BlockAntler());
        
        register(beehive = new BlockBeehive());

        register(candleBeeswax = new BlockLightCandle("candle_beeswax",
                0.005F), 15);
        register(candleTallow = new BlockLightCandle("candle_tallow",
                0.02F), 15);
        register(torchTallow = new BlockLightTorch("torch_tallow", 0.005F), 4);
        register(torchTar = new BlockLightTorch("torch_tar", 0.02F), 4);

        register(carcassChicken = new BlockCarcassChicken());
        register(carcassCowpart = new BlockCarcassCowpart(), true);
        register(carcassPig = new BlockCarcassPig(), true);
        register(carcassSheep = new BlockCarcassSheep(), true);
        register(carcassRabbit = new BlockCarcassRabbit());

        registerItemless(craftingCandlemaker = new BlockCraftingCandlemaker());
        registerItemless(craftingClayworks = new BlockCraftingClayworks());
        registerItemless(craftingForge = new BlockCraftingForge());
        register(craftingKnapping = new BlockCraftingKnapping());
        registerItemless(craftingMason = new BlockCraftingMason());
        registerItemless(craftingTextiles = new BlockCraftingTextiles());
        registerItemless(craftingWoodworking = new BlockCraftingWoodworking());
        registerItemless(craftingArmourer = new BlockCraftingArmourer());
        registerItemless(craftingSawpit = new BlockCraftingSawpit());
        
        register(drying = new BlockDrying());

        register(furnaceCampfire = new BlockFurnaceCampfire());
        register(furnaceCookfire = new BlockFurnaceCookfire());
        registerItemless(furnaceClay = new BlockFurnaceClay());
        registerItemless(furnaceStone = new BlockFurnaceStone());
        
        registerModelless(box = new BlockBox(), 1, false);

        registerItemless(chickpea = new BlockCropChickpea());
        registerItemless(cotton = new BlockCropCotton());
        registerItemless(hemp = new BlockCropHemp());
        registerItemless(pepper = new BlockCropPepper());
        
        registerItemless(wheat = new BlockCropWheat());
        registerItemless(carrot = new BlockCropCarrot());
        registerItemless(potato = new BlockCropPotato());
        registerItemless(beetroot = new BlockCropBeetroot());
        
        registerItemless(riceBase = new BlockRiceBase());
        registerItemless(riceTop = new BlockRiceTop());

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
                4F, () -> ModItems.amethyst, 2));
        register(lodeFireopal = new BlockLode("lode_fireopal",
                4F, () -> ModItems.fireopal, 5));
        register(lodeRuby = new BlockLode("lode_ruby",
                4F, () -> ModItems.ruby, 3));
        register(lodeSapphire = new BlockLode("lode_sapphire",
                4F, () -> ModItems.sapphire, 8));

        register(oreCopper = new BlockLode("ore_copper",
                3F, () -> ModItems.oreCopper, 1));
        register(oreSilver = new BlockLode("ore_silver",
                3F, () -> ModItems.oreSilver, 1));
        register(oreTin = new BlockLode("ore_tin",
                3F, () -> ModItems.oreTin, 1));
        
        register(salt = new BlockRock("salt", 1F));
        register(chalk = new BlockRock("chalk", 3F));
        
        register(peat = new BlockPeat());

        registerItemless(berry = new BlockCropHarvestableBerry());
        registerItemless(bean = new BlockCropHarvestableBean());
        registerItemless(tomato = new BlockCropHarvestableTomato());
        
        registerItemless(melonCrop = new BlockCropBlockfruitMelon());
        registerItemless(pumpkinCrop = new BlockCropBlockfruitPumpkin());
        
        register(melonFruit = new BlockFruit("melon_fruit",
                () -> ModItems.melon));
        register(pumpkinFruit = new BlockFruit("pumpkin_fruit",
                () -> ModItems.pumpkin));
        
        register(seedlingApple = new BlockSeedlingApple());
        register(seedlingPear = new BlockSeedlingPear());
        register(seedlingOrange = new BlockSeedlingOrange());
        register(seedlingBanana = new BlockSeedlingBanana());
        
        registerItemless(wallBrickSingle =
                new BlockWall(BlockMaterial.STONE_FURNITURE,
                "wall_brick_single", 2F, ToolType.PICKAXE, false,
                () -> ModItems.wallBrick, true, 6, true));
        registerItemless(wallBrickDouble =
                new BlockWall(BlockMaterial.STONE_FURNITURE,
                "wall_brick_double", 3F, ToolType.PICKAXE, true,
                () -> ModItems.wallBrick, true, 6, true));
        registerItemless(wallMudSingle =
                new BlockWallHeightless(BlockMaterial.STONE_FURNITURE,
                "wall_mud_single", 1F, ToolType.PICKAXE, false,
                () -> ModItems.wallMud, false, 1, false));
        registerItemless(wallMudDouble =
                new BlockWallHeightless(BlockMaterial.STONE_FURNITURE,
                "wall_mud_double", 3F, ToolType.PICKAXE, true,
                () -> ModItems.wallMud, false, 1, false));
        registerItemless(wallRoughSingle =
                new BlockWallHeightless(BlockMaterial.STONE_FURNITURE,
                "wall_rough_single", 1.5F, ToolType.PICKAXE, false,
                () -> ModItems.wallRough, false, 1, false));
        registerItemless(wallRoughDouble =
                new BlockWallHeightless(BlockMaterial.STONE_FURNITURE,
                "wall_rough_double", 3F, ToolType.PICKAXE, true,
                () -> ModItems.wallRough, false, 1, false));
        registerItemless(wallStoneSingle =
                new BlockWall(BlockMaterial.STONE_FURNITURE,
                "wall_stone_single", 2F, ToolType.PICKAXE, false,
                () -> ModItems.wallStone, true, 6, true));
        registerItemless(wallStoneDouble =
                new BlockWall(BlockMaterial.STONE_FURNITURE,
                "wall_stone_double", 3F, ToolType.PICKAXE, true,
                () -> ModItems.wallStone, true, 6, true));
        registerItemless(wallLogSingle =
                new BlockWallStraight(BlockMaterial.WOOD_FURNITURE,
                "wall_log_single", 1F, ToolType.AXE, false,
                () -> ModItems.wallLog, false, 4, true));
        registerItemless(wallLogDouble =
                new BlockWallStraight(BlockMaterial.WOOD_FURNITURE,
                "wall_log_double", 3F, ToolType.AXE, true,
                () -> ModItems.wallLog, false, 4, true));
        register(wallPole = new BlockWallThin(BlockMaterial.WOOD_FURNITURE,
                "wall_pole", 2F, ToolType.AXE, false, 4, false));
        
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
        
        registerItemless(doorPole = new BlockDoor("door_pole",
                () -> ModItems.doorPole));
        registerItemless(doorWood = new BlockDoor("door_wood",
                () -> ModItems.doorWood));
        
        registerItemless(beam = new BlockBeam());
        
        register(fence = new BlockFence());
        
        registerItemless(slabStoneSingle = new BlockSlabSingle(BlockMaterial
                .STONE_FURNITURE, "slab_stone_single", 2F, ToolType.PICKAXE,
                () -> ModItems.slabStone));
        registerItemless(slabStoneDouble = new BlockSlabDouble(BlockMaterial
                .STONE_FURNITURE, "slab_stone_double", 2F, ToolType.PICKAXE,
                () -> ModItems.slabStone));
        registerItemless(slabBrickSingle = new BlockSlabSingle(BlockMaterial
                .STONE_FURNITURE, "slab_brick_single", 2F, ToolType.PICKAXE,
                () -> ModItems.slabBrick));
        registerItemless(slabBrickDouble = new BlockSlabDouble(BlockMaterial
                .STONE_FURNITURE, "slab_brick_double", 2F, ToolType.PICKAXE,
                () -> ModItems.slabBrick));
        
        register(rubble = new BlockRubble());
        
        registerFluid(tar = new BlockTar());
        
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
        OFFHAND_ONLY.add(Item.getItemFromBlock(Blocks.CHEST)
                .setMaxStackSize(1));
        
    
    }
    
    public static void preInitClient() {
        
        for(Entry<Block, Item> entry : MOD_BLOCKS.entrySet()) {
            
            model(entry.getKey(), entry.getValue());
        }
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
        MOD_BLOCKS.put(block, item);
    }
    
    private static void registerFluid(Block block) {
        
        Item item = Item.getItemFromBlock(block);
        ModelBakery.registerItemVariants(item);
        ModelResourceLocation loc =
                new ModelResourceLocation(block.getRegistryName(), "normal");
   
        ModelLoader.setCustomMeshDefinition(item, stack -> loc);
        
        ModelLoader.setCustomStateMapper(block, new StateMapperBase() {
            
            @Override
            protected ModelResourceLocation getModelResourceLocation(
                    IBlockState state) {
                
                return loc;
            }
        });
    }
    
    private static void registerModelless(Block block,
            int stackSize, boolean isOffhandOnly) {
        
        Item item = new ItemBlock(block).setMaxStackSize(stackSize);
        
        if (isOffhandOnly) {
            
            OFFHAND_ONLY.add(item);
        }

        GameRegistry.register(block);
        GameRegistry.register(item
                .setRegistryName(block.getRegistryName()));
    }
    
    private static void registerItemless(Block block) {
        
        GameRegistry.register(block);
        MOD_BLOCKS.put(block, Item.getItemFromBlock(block));
    }
    
    private static void model(Block block, Item item) {

        ModelLoader.setCustomModelResourceLocation(item, 0,
                new ModelResourceLocation(block.getRegistryName(),
                "inventory"));
    }
}

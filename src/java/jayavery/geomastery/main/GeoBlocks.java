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
import jayavery.geomastery.blocks.BlockInvisibleLight;
import jayavery.geomastery.blocks.BlockLight;
import jayavery.geomastery.blocks.BlockMultiCrafting;
import jayavery.geomastery.blocks.BlockMushroombaby;
import jayavery.geomastery.blocks.BlockPitchroof;
import jayavery.geomastery.blocks.BlockRiceBase;
import jayavery.geomastery.blocks.BlockRiceTop;
import jayavery.geomastery.blocks.BlockSeedling;
import jayavery.geomastery.blocks.BlockSlab;
import jayavery.geomastery.blocks.BlockSolid;
import jayavery.geomastery.blocks.BlockStairsComplex;
import jayavery.geomastery.blocks.BlockStairsStraight;
import jayavery.geomastery.blocks.BlockTar;
import jayavery.geomastery.blocks.BlockVault;
import jayavery.geomastery.blocks.BlockWall;
import jayavery.geomastery.blocks.BlockWallComplex;
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

public class GeoBlocks {
    
    /** All new blocks. */
    public static final Set<Block> BLOCKS = Sets.newHashSet();
    
    /** Items which are restricted to the offhand slot. */
    public static final Set<Item> OFFHAND_ONLY = Sets.newHashSet();
    
    /** All which have ItemBlocks. */
    public static final Map<Block, Item> ITEM_MAP = Maps.newHashMap();
    
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
    public static BlockBed bedLeaf =   makeItemless(new BlockBed.Leaf());
    public static BlockBed bedCotton = makeItemless(new BlockBed.Cotton());
    public static BlockBed bedWool =   makeItemless(new BlockBed.Wool());
    public static BlockBed bedSimple = makeItemless(new BlockBed.Simple());
    
    // Antler
    public static Block antler = makeItemless(new BlockAntler());
    
    // Beehive
    public static BlockBeehive beehive = make(new BlockBeehive());

    // Lights
    public static Block candleBeeswax = make(new BlockLight.Candle("candle_beeswax", 0.005F), 15);
    public static Block candleTallow =  make(new BlockLight.Candle("candle_tallow", 0.02F), 15);
    public static Block torchTallow =   make(new BlockLight.Torch("torch_tallow", 0.005F), 4);
    public static Block torchTar =      make(new BlockLight.Torch("torch_tar", 0.02F), 4);
    public static Block lampClay =      make(new BlockLight.Lamp("lamp_clay"), 4);

    // Carcasses
    public static BlockCarcass carcassChicken =    make(new BlockCarcass.Chicken());
    public static BlockCarcass carcassCowpart =    make(new BlockCarcass.Cowpart(), true);
    public static BlockCarcass carcassPig =        make(new BlockCarcass.Pig(), true);
    public static BlockCarcass carcassSheep =      make(new BlockCarcass.Sheep(), true);
    public static BlockCarcass carcassRabbit =     make(new BlockCarcass.Rabbit());

    // Multiblock crafting devices
    public static BlockMultiCrafting.Candlemaker craftingCandlemaker =      makeItemless(new BlockMultiCrafting.Candlemaker());
    public static BlockMultiCrafting.Forge craftingForge =                  makeItemless(new BlockMultiCrafting.Forge());
    public static BlockMultiCrafting.Mason craftingMason =                  makeItemless(new BlockMultiCrafting.Mason());
    public static BlockMultiCrafting.Textiles craftingTextiles =            makeItemless(new BlockMultiCrafting.Textiles());
    public static BlockMultiCrafting.Woodworking craftingWoodworking =      makeItemless(new BlockMultiCrafting.Woodworking());
    public static BlockMultiCrafting.Armourer craftingArmourer =            makeItemless(new BlockMultiCrafting.Armourer());
    public static BlockMultiCrafting.Sawpit craftingSawpit =                makeItemless(new BlockMultiCrafting.Sawpit());
    
    // Multiblock furnaces
    public static BlockMultiCrafting.Clay furnaceClay =   makeItemless(new BlockMultiCrafting.Clay());
    public static BlockMultiCrafting.Stone furnaceStone = makeItemless(new BlockMultiCrafting.Stone());
    
    // Single complex blocks
    public static Block craftingKnapping =  make(new BlockCraftingKnapping());
    public static Block drying =            make(new BlockDrying());
    public static Block compostheap =       make(new BlockCompostheap(), true);
    public static Block furnaceCampfire =   make(new BlockFurnaceCampfire());
    public static Block furnacePotfire =    make(new BlockFurnacePotfire());
    public static Block basket =            make(new BlockBasket());
    public static Block box =               make(new BlockBox());
    
    // Single crops
    public static BlockCrop chickpea =  makeItemless(new BlockCrop.Chickpea());
    public static BlockCrop cotton =    makeItemless(new BlockCrop.Cotton());
    public static BlockCrop hemp =      makeItemless(new BlockCrop.Hemp());
    public static BlockCrop pepper =    makeItemless(new BlockCrop.Pepper());
    public static BlockCrop wheat =     makeItemless(new BlockCrop.Wheat());
    public static BlockCrop carrot =    makeItemless(new BlockCrop.Carrot());
    public static BlockCrop potato =    makeItemless(new BlockCrop.Potato());
    public static BlockCrop beetroot =  makeItemless(new BlockCrop.Beetroot());
    
    // Harvestable crops
    public static BlockCropHarvestable berry =  makeItemless(new BlockCropHarvestable.Berry());
    public static BlockCropHarvestable bean =   makeItemless(new BlockCropHarvestable.Bean());
    public static BlockCropHarvestable tomato = makeItemless(new BlockCropHarvestable.Tomato());
    
    // Blockfruit crops
    public static BlockCropBlockfruit melonCrop =   makeItemless(new BlockCropBlockfruit.Melon(), CROP_BLOCKFRUIT);
    public static BlockCropBlockfruit pumpkinCrop = makeItemless(new BlockCropBlockfruit.Pumpkin(), CROP_BLOCKFRUIT);
    
    // Block fruits
    public static BlockFruit pumpkinFruit = make(new BlockFruit("melon_fruit", () -> GeoItems.melon, () -> GeoItems.seedMelon));
    public static BlockFruit melonFruit =   make(new BlockFruit("pumpkin_fruit", () -> GeoItems.pumpkin, () -> GeoItems.seedPumpkin));
    
    // Rice
    public static Block riceBase = makeItemless(new BlockRiceBase());
    public static Block riceTop =  makeItemless(new BlockRiceTop());
    
    // Baby mushrooms
    public static Block mushroombabyRed =   makeItemless(new BlockMushroombaby("mushroombaby_brown", Blocks.BROWN_MUSHROOM));
    public static Block mushroombabyBrown = makeItemless(new BlockMushroombaby("mushroombaby_red", Blocks.RED_MUSHROOM));
    
    // Seedlings
    public static BlockSeedling seedlingApple =  make(new BlockSeedling.Apple());
    public static BlockSeedling seedlingPear =   make(new BlockSeedling.Pear());
    public static BlockSeedling seedlingOrange = make(new BlockSeedling.Orange());
    public static BlockSeedling seedlingBanana = make(new BlockSeedling.Banana());
    
    // Leaves
    public static BlockHarvestableLeaves leafApple =  make(new BlockHarvestableLeaves("leaf_apple", () -> GeoItems.apple, () -> seedlingApple, 0.05F), LEAVES);
    public static BlockHarvestableLeaves leafBanana = make(new BlockHarvestableLeaves("leaf_banana", () -> GeoItems.banana, () -> seedlingBanana, 0.15F), LEAVES);
    public static BlockHarvestableLeaves leafPear =   make(new BlockHarvestableLeaves("leaf_pear", () -> GeoItems.pear, () -> seedlingPear, 0.05F), LEAVES);
    public static BlockHarvestableLeaves leafOrange = make(new BlockHarvestableLeaves("leaf_orange", () -> GeoItems.orange, () -> seedlingOrange, 0.1F), LEAVES);
    
    // Logs
    public static Block woodApple =  make(new BlockWood("wood_apple", 2F));
    public static Block woodBanana = make(new BlockWood("wood_banana", 1F));
    public static Block woodPear =   make(new BlockWood("wood_pear", 2F));
    public static Block woodOrange = make(new BlockWood("wood_orange", 2F));

    // Ores
    public static Block lodeAmethyst =  make(new BlockSolid(Material.ROCK, "lode_amethyst", 4F, BlockWeight.HEAVY, () -> GeoItems.amethyst, 2, ToolType.PICKAXE));
    public static Block lodeFireopal =  make(new BlockSolid(Material.ROCK, "lode_fireopal", 4F, BlockWeight.HEAVY, () -> GeoItems.fireopal, 5, ToolType.PICKAXE));
    public static Block lodeRuby =      make(new BlockSolid(Material.ROCK, "lode_ruby", 4F, BlockWeight.HEAVY, () -> GeoItems.ruby, 3, ToolType.PICKAXE));
    public static Block lodeSapphire =  make(new BlockSolid(Material.ROCK, "lode_sapphire", 4F, BlockWeight.HEAVY, () -> GeoItems.sapphire, 8, ToolType.PICKAXE));
    public static Block oreCopper =     make(new BlockSolid(Material.ROCK, "ore_copper", 3F, BlockWeight.HEAVY, () -> GeoItems.oreCopper, 1, ToolType.PICKAXE));
    public static Block oreSilver =     make(new BlockSolid(Material.ROCK, "ore_silver", 3F, BlockWeight.HEAVY, () -> GeoItems.oreSilver, 1, ToolType.PICKAXE));
    public static Block oreTin =        make(new BlockSolid(Material.ROCK, "ore_tin", 3F, BlockWeight.HEAVY, () -> GeoItems.oreTin, 1, ToolType.PICKAXE));
    
    // Rocks
    public static Block salt =  make(new BlockSolid(Material.ROCK, "salt", 1F, BlockWeight.MEDIUM, () -> GeoItems.salt, 1, ToolType.PICKAXE));
    public static Block chalk = make(new BlockSolid(Material.ROCK, "chalk", 3F, BlockWeight.MEDIUM, () -> GeoItems.chalk, 1, ToolType.PICKAXE));
    public static Block peat =  make(new BlockSolid(BlockMaterial.SOIL, "peat", 0.5F, BlockWeight.MEDIUM, () -> GeoItems.peatWet, 1, ToolType.SHOVEL));
    
    // Rubble
    public static Block rubble = makeItemless(new BlockSolid(Material.ROCK, "rubble", 1F, BlockWeight.HEAVY, () -> GeoItems.rubble, 1, ToolType.SHOVEL));
    
    // Walls (in rendering priority order)
    public static Block wallLog =                              make(new BlockWallLog(), RENDER_STRAIGHT, DELAYED_BAKE); 
    public static Block fence =                                make(new BlockWallThin(BlockMaterial.WOOD_FURNITURE, "fence", 2F, ToolType.AXE, 90), RENDER_SINGLE, DELAYED_BAKE);
    public static Block wallPole =                             make(new BlockWallThin(BlockMaterial.WOOD_FURNITURE, "wall_pole", 2F, ToolType.AXE, 180), 4, RENDER_SINGLE, DELAYED_BAKE);
    public static Block frame =                                make(new BlockWallThin(BlockMaterial.WOOD_FURNITURE, "frame", 2F, ToolType.AXE, 180), 6, RENDER_SINGLE, DELAYED_BAKE);
    public static BlockWallHeaping wallMudSingle =     makeItemless(new BlockWallHeaping(BlockMaterial.STONE_FURNITURE, "wall_mud_single", 1F, ToolType.PICKAXE, false, 0, () -> GeoItems.wallMud), RENDER_SINGLE, DELAYED_BAKE);
    public static BlockWallHeaping wallRoughSingle =   makeItemless(new BlockWallHeaping(BlockMaterial.STONE_FURNITURE, "wall_rough_single", 1.5F, ToolType.PICKAXE, false, 0, () -> GeoItems.wallRough), RENDER_SINGLE, DELAYED_BAKE);
    public static BlockWallComplex wallBrickSingle =   makeItemless(new BlockWallComplex(BlockMaterial.STONE_FURNITURE, "wall_brick_single", 2F, ToolType.PICKAXE, false, () -> GeoItems.wallBrick), RENDER_COMPLEX, DELAYED_BAKE); 
    public static BlockWallComplex wallStoneSingle =   makeItemless(new BlockWallComplex(BlockMaterial.STONE_FURNITURE, "wall_stone_single", 2F, ToolType.PICKAXE, false, () -> GeoItems.wallStone), RENDER_COMPLEX, DELAYED_BAKE);
    public static BlockWallHeaping wallMudDouble =     makeItemless(new BlockWallHeaping(BlockMaterial.STONE_FURNITURE, "wall_mud_double", 3F, ToolType.PICKAXE, true, 0, () -> GeoItems.wallMud), RENDER_SINGLE, DELAYED_BAKE); 
    public static BlockWallHeaping wallRoughDouble =   makeItemless(new BlockWallHeaping(BlockMaterial.STONE_FURNITURE, "wall_rough_double", 3F, ToolType.PICKAXE, true, 0, () -> GeoItems.wallRough), RENDER_SINGLE, DELAYED_BAKE); 
    public static BlockWallComplex wallBrickDouble =   makeItemless(new BlockWallComplex(BlockMaterial.STONE_FURNITURE, "wall_brick_double", 3F, ToolType.PICKAXE, true,  () -> GeoItems.wallBrick), RENDER_COMPLEX, DELAYED_BAKE); 
    public static BlockWallComplex wallStoneDouble =   makeItemless(new BlockWallComplex(BlockMaterial.STONE_FURNITURE, "wall_stone_double", 3F, ToolType.PICKAXE, true, () -> GeoItems.wallStone), RENDER_COMPLEX, DELAYED_BAKE);
    
    // Complex stairs
    public static Block stairsBrick = make(new BlockStairsComplex(BlockMaterial.STONE_FURNITURE, "stairs_brick", 3F, ToolType.PICKAXE), 2);
    public static Block stairsStone = make(new BlockStairsComplex(BlockMaterial.STONE_FURNITURE, "stairs_stone", 3F, ToolType.PICKAXE), 2);
    
    // Double joining stairs
    public static Block stairsWood = make(new BlockStairsStraight.Joining("stairs_wood", 2F), 4);
    
    // Single stairs
    public static Block stairsPole = make(new BlockStairsStraight.Single("stairs_pole", 2F), 4);
    
    // Vaults
    public static BlockVault vaultStoneSingle = makeItemless(new BlockVault("vault_stone_single", () -> GeoItems.vaultStone, false, BlockWeight.HEAVY));
    public static BlockVault vaultStoneDouble = makeItemless(new BlockVault("vault_stone_double", () -> GeoItems.vaultStone, true, BlockWeight.HEAVY));
    public static BlockVault vaultBrickSingle = makeItemless(new BlockVault("vault_brick_single", () -> GeoItems.vaultBrick, false, BlockWeight.HEAVY));
    public static BlockVault vaultBrickDouble = makeItemless(new BlockVault("vault_brick_double", () -> GeoItems.vaultBrick, true, BlockWeight.HEAVY));
    public static BlockVault vaultFrame =               make(new BlockVault("vault_frame", () -> null, false, BlockWeight.LIGHT), 6);
        
    // Doors
    public static BlockDoor doorPole = makeItemless(new BlockDoor("door_pole", () -> GeoItems.doorPole));
    public static BlockDoor doorWood = makeItemless(new BlockDoor("door_wood", () -> GeoItems.doorWood));
    
    // Window
    public static Block window = make(new BlockWindow(), 4);
    
    // Beams
    public static BlockBeam beamThin =  makeItemless(new BlockBeam("beam_thick"), DELAYED_BAKE);
    public static BlockBeam beamThick = makeItemless(new BlockBeam("beam_thin"), DELAYED_BAKE);
        
    // Slabs
    public static BlockSlab slabStoneSingle = makeItemless(new BlockSlab("slab_stone_single", false, () -> GeoItems.slabStone));
    public static BlockSlab slabStoneDouble = makeItemless(new BlockSlab("slab_stone_double", true, () -> GeoItems.slabStone));
    public static BlockSlab slabBrickSingle = makeItemless(new BlockSlab("slab_brick_single", false, () -> GeoItems.slabBrick));
    public static BlockSlab slabBrickDouble = makeItemless(new BlockSlab("slab_brick_double", true, () -> GeoItems.slabBrick));
    
    // Invisible light
    public static Block invisibleLight = makeItemless(new BlockInvisibleLight());
    
    // Flat roofs
    public static Block flatroofPole = make(new BlockFlatroof("flatroof_pole", 1F, ToolType.AXE), 4);
    
    // Pitched roofs
    public static Block pitchroofClay = make(new BlockPitchroof(BlockMaterial.WOOD_FURNITURE, "pitchroof_clay", 2F), 4);
    
    /** Adjusts vanilla blocks, register fluids. */
    public static void preInit() {
        
        FluidRegistry.registerFluid(tarFluid);
        GameRegistry.register(tar = makeItemless(new BlockTar()));
                
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
        
        ITEM_MAP.put(block, item);
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

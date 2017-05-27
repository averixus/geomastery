package com.jayavery.jjmod.init;

import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.jayavery.jjmod.blocks.BlockAntler;
import com.jayavery.jjmod.blocks.BlockBasket;
import com.jayavery.jjmod.blocks.BlockBeam;
import com.jayavery.jjmod.blocks.BlockBed;
import com.jayavery.jjmod.blocks.BlockBeehive;
import com.jayavery.jjmod.blocks.BlockBox;
import com.jayavery.jjmod.blocks.BlockCarcass;
import com.jayavery.jjmod.blocks.BlockCompostheap;
import com.jayavery.jjmod.blocks.BlockCraftingKnapping;
import com.jayavery.jjmod.blocks.BlockCrop;
import com.jayavery.jjmod.blocks.BlockCropBlockfruit;
import com.jayavery.jjmod.blocks.BlockCropHarvestable;
import com.jayavery.jjmod.blocks.BlockDoor;
import com.jayavery.jjmod.blocks.BlockDrying;
import com.jayavery.jjmod.blocks.BlockFlatroof;
import com.jayavery.jjmod.blocks.BlockFruit;
import com.jayavery.jjmod.blocks.BlockFurnaceCampfire;
import com.jayavery.jjmod.blocks.BlockFurnacePotfire;
import com.jayavery.jjmod.blocks.BlockHarvestableLeaves;
import com.jayavery.jjmod.blocks.BlockInvisibleLight;
import com.jayavery.jjmod.blocks.BlockLight;
import com.jayavery.jjmod.blocks.BlockMultiCrafting;
import com.jayavery.jjmod.blocks.BlockMushroombaby;
import com.jayavery.jjmod.blocks.BlockPitchroof;
import com.jayavery.jjmod.blocks.BlockRiceBase;
import com.jayavery.jjmod.blocks.BlockRiceTop;
import com.jayavery.jjmod.blocks.BlockSeedling;
import com.jayavery.jjmod.blocks.BlockSlab;
import com.jayavery.jjmod.blocks.BlockSolid;
import com.jayavery.jjmod.blocks.BlockStairsComplex;
import com.jayavery.jjmod.blocks.BlockStairsStraight;
import com.jayavery.jjmod.blocks.BlockTar;
import com.jayavery.jjmod.blocks.BlockVault;
import com.jayavery.jjmod.blocks.BlockWallComplex;
import com.jayavery.jjmod.blocks.BlockWallLog;
import com.jayavery.jjmod.blocks.BlockWallRough;
import com.jayavery.jjmod.blocks.BlockWallThin;
import com.jayavery.jjmod.blocks.BlockWindow;
import com.jayavery.jjmod.blocks.BlockWood;
import com.jayavery.jjmod.render.block.BeamThick;
import com.jayavery.jjmod.render.block.BeamThin;
import com.jayavery.jjmod.utilities.BlockMaterial;
import com.jayavery.jjmod.utilities.BlockWeight;
import com.jayavery.jjmod.utilities.IDelayedMultipart;
import com.jayavery.jjmod.utilities.ToolType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockStem;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModBlocks {
    
    /** Items which are restricted to the offhand slot. */
    public static final Set<Item> OFFHAND_ONLY = Sets.newHashSet();
    
    /** All new blocks, for ease of modelling. */
    private static final Map<Block, Item> MOD_BLOCKS = Maps.newHashMap();
    
    public static BlockBed.Leaf bedLeaf;
    public static BlockBed.Cotton bedCotton;
    public static BlockBed.Wool bedWool;
    public static BlockBed.Simple bedSimple;
    
    public static BlockAntler antler;
    
    public static BlockBeehive beehive;

    public static BlockLight candleBeeswax;
    public static BlockLight candleTallow;
    public static BlockLight torchTallow;
    public static BlockLight torchTar;
    public static BlockLight lampClay;

    public static BlockCarcass carcassChicken;
    public static BlockCarcass carcassCowpart;
    public static BlockCarcass carcassPig;
    public static BlockCarcass carcassSheep;
    public static BlockCarcass carcassRabbit;

    public static BlockMultiCrafting craftingCandlemaker;
    public static BlockMultiCrafting craftingForge;
    public static BlockCraftingKnapping craftingKnapping;
    public static BlockMultiCrafting craftingMason;
    public static BlockMultiCrafting craftingTextiles;
    public static BlockMultiCrafting craftingWoodworking;
    public static BlockMultiCrafting craftingArmourer;
    public static BlockMultiCrafting craftingSawpit;

    public static BlockDrying drying;
    
    public static BlockCompostheap compostheap;

    public static BlockFurnaceCampfire furnaceCampfire;
    public static BlockFurnacePotfire furnacePotfire;
    public static BlockMultiCrafting furnaceClay;
    public static BlockMultiCrafting furnaceStone;
    
    public static BlockBasket basket;
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
    
    public static BlockMushroombaby mushroombabyRed;
    public static BlockMushroombaby mushroombabyBrown;
    
    public static BlockHarvestableLeaves leafApple;
    public static BlockHarvestableLeaves leafBanana;
    public static BlockHarvestableLeaves leafPear;
    public static BlockHarvestableLeaves leafOrange;
    
    public static BlockWood woodApple;
    public static BlockWood woodBanana;
    public static BlockWood woodPear;
    public static BlockWood woodOrange;

    public static BlockSolid lodeAmethyst;
    public static BlockSolid lodeFireopal;
    public static BlockSolid lodeRuby;
    public static BlockSolid lodeSapphire;

    public static BlockSolid oreCopper;
    public static BlockSolid oreSilver;
    public static BlockSolid oreTin;
    
    public static BlockSolid salt;
    public static BlockSolid chalk;
    public static BlockSolid peat;
    
    public static BlockSolid rubble;
    
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
    
    public static BlockWallComplex wallBrickSingle; 
    public static BlockWallComplex wallBrickDouble; 
    public static BlockWallRough wallMudSingle;
    public static BlockWallRough wallMudDouble; 
    public static BlockWallRough wallRoughSingle;
    public static BlockWallRough wallRoughDouble; 
    public static BlockWallComplex wallStoneSingle;
    public static BlockWallComplex wallStoneDouble;
    public static BlockWallLog wallLog; 
    public static BlockWallThin wallPole;
    public static BlockWallThin fence;
    public static BlockWallThin frame;
    
    public static BlockStairsComplex stairsBrick;
    public static BlockStairsComplex stairsStone;
    public static BlockStairsStraight.Joining stairsWood;
    public static BlockStairsStraight.Single stairsPole;
    
    public static BlockVault vaultStoneSingle;
    public static BlockVault vaultStoneDouble;
    public static BlockVault vaultBrickSingle;
    public static BlockVault vaultBrickDouble;
    public static BlockVault vaultFrame;
        
    public static BlockDoor doorPole;
    public static BlockDoor doorWood;
    
    public static BlockWindow window;
    
    public static BlockBeam beamThin;
    public static BlockBeam beamThick;
        
    public static BlockSlab slabStoneSingle;
    public static BlockSlab slabStoneDouble;
    public static BlockSlab slabBrickSingle;
    public static BlockSlab slabBrickDouble;
        
    public static BlockTar tar;
    
    public static BlockInvisibleLight invisibleLight;
    
    public static BlockFlatroof flatroofPole;
    
    public static BlockPitchroof pitchroofClay;
    
    public static void preInit() {
                
        registerItemless(bedLeaf = new BlockBed.Leaf());
        registerItemless(bedCotton = new BlockBed.Cotton());
        registerItemless(bedWool = new BlockBed.Wool());
        registerItemless(bedSimple = new BlockBed.Simple());
        
        registerItemless(antler = new BlockAntler());
        
        register(beehive = new BlockBeehive());

        register(candleBeeswax = new BlockLight.Candle("candle_beeswax",
                0.005F), 15);
        register(candleTallow = new BlockLight.Candle("candle_tallow",
                0.02F), 15);
        register(torchTallow = new BlockLight.Torch("torch_tallow", 0.005F), 4);
        register(torchTar = new BlockLight.Torch("torch_tar", 0.02F), 4);
        register(lampClay = new BlockLight.Lamp("lamp_clay"), 4);

        register(carcassChicken = new BlockCarcass.Chicken());
        register(carcassCowpart = new BlockCarcass.Cowpart(), true);
        register(carcassPig = new BlockCarcass.Pig(), true);
        register(carcassSheep = new BlockCarcass.Sheep(), true);
        register(carcassRabbit = new BlockCarcass.Rabbit());

        registerItemless(craftingCandlemaker = new BlockMultiCrafting.Candlemaker());
        registerItemless(craftingForge = new BlockMultiCrafting.Forge());
        register(craftingKnapping = new BlockCraftingKnapping());
        registerItemless(craftingMason = new BlockMultiCrafting.Mason());
        registerItemless(craftingTextiles = new BlockMultiCrafting.Textiles());
        registerItemless(craftingWoodworking = new BlockMultiCrafting.Woodworking());
        registerItemless(craftingArmourer = new BlockMultiCrafting.Armourer());
        registerItemless(craftingSawpit = new BlockMultiCrafting.Sawpit());
        
        register(drying = new BlockDrying());
        
        register(compostheap = new BlockCompostheap(), true);

        register(furnaceCampfire = new BlockFurnaceCampfire());
        register(furnacePotfire = new BlockFurnacePotfire());
        registerItemless(furnaceClay = new BlockMultiCrafting.Clay());
        registerItemless(furnaceStone = new BlockMultiCrafting.Stone());
        
        register(basket = new BlockBasket());
        register(box = new BlockBox());

        registerItemless(chickpea = new BlockCrop.Chickpea());
        registerItemless(cotton = new BlockCrop.Cotton());
        registerItemless(hemp = new BlockCrop.Hemp());
        registerItemless(pepper = new BlockCrop.Pepper());
        
        registerItemless(wheat = new BlockCrop.Wheat());
        registerItemless(carrot = new BlockCrop.Carrot());
        registerItemless(potato = new BlockCrop.Potato());
        registerItemless(beetroot = new BlockCrop.Beetroot());
        
        registerItemless(riceBase = new BlockRiceBase());
        registerItemless(riceTop = new BlockRiceTop());
        
        registerItemless(mushroombabyBrown = new BlockMushroombaby(
                "mushroombaby_brown", Blocks.BROWN_MUSHROOM));
        registerItemless(mushroombabyRed = new BlockMushroombaby(
                "mushroombaby_red", Blocks.RED_MUSHROOM));
        
        register(leafApple = new BlockHarvestableLeaves("leaf_apple",
                () -> ModItems.apple, () -> seedlingApple, 0.05F));
        register(leafBanana = new BlockHarvestableLeaves("leaf_banana",
                () -> ModItems.banana, () -> seedlingBanana, 0.15F));
        register(leafPear = new BlockHarvestableLeaves("leaf_pear",
                () -> ModItems.pear, () -> seedlingPear, 0.05F));
        register(leafOrange = new BlockHarvestableLeaves("leaf_orange",
                () -> ModItems.orange, () -> seedlingOrange, 0.1F));
        
        register(woodApple = new BlockWood("wood_apple", 2F));
        register(woodBanana = new BlockWood("wood_banana", 1F));
        register(woodPear = new BlockWood("wood_pear", 2F));
        register(woodOrange = new BlockWood("wood_orange", 2F));
        
        register(lodeAmethyst = new BlockSolid(Material.ROCK, "lode_amethyst",
                4F, BlockWeight.HEAVY, () -> ModItems.amethyst,
                2, ToolType.PICKAXE));
        register(lodeFireopal = new BlockSolid(Material.ROCK, "lode_fireopal",
                4F, BlockWeight.HEAVY, () -> ModItems.fireopal,
                5, ToolType.PICKAXE));
        register(lodeRuby = new BlockSolid(Material.ROCK, "lode_ruby",
                4F, BlockWeight.HEAVY, () -> ModItems.ruby,
                3, ToolType.PICKAXE));
        register(lodeSapphire = new BlockSolid(Material.ROCK, "lode_sapphire",
                4F, BlockWeight.HEAVY, () -> ModItems.sapphire,
                8, ToolType.PICKAXE));

        register(oreCopper = new BlockSolid(Material.ROCK, "ore_copper",
                3F, BlockWeight.HEAVY, () -> ModItems.oreCopper,
                1, ToolType.PICKAXE));
        register(oreSilver = new BlockSolid(Material.ROCK, "ore_silver",
                3F, BlockWeight.HEAVY, () -> ModItems.oreSilver,
                1, ToolType.PICKAXE));
        register(oreTin = new BlockSolid(Material.ROCK, "ore_tin",
                3F, BlockWeight.HEAVY, () -> ModItems.oreTin,
                1, ToolType.PICKAXE));
        
        register(salt = new BlockSolid(Material.ROCK, "salt", 1F,
                BlockWeight.MEDIUM, () -> ModItems.salt, 1, ToolType.PICKAXE));
        register(chalk = new BlockSolid(Material.ROCK, "chalk", 3F,
                BlockWeight.MEDIUM, () -> ModItems.chalk, 1, ToolType.PICKAXE));
        register(peat = new BlockSolid(BlockMaterial.SOIL, "peat", 0.5F,
                BlockWeight.MEDIUM, () -> ModItems.peatWet,
                1, ToolType.SHOVEL));
        
        registerItemless(rubble = new BlockSolid(Material.ROCK, "rubble", 1F,
                BlockWeight.HEAVY, () -> ModItems.rubble, 1, ToolType.SHOVEL));

        registerItemless(berry = new BlockCropHarvestable.Berry());
        registerItemless(bean = new BlockCropHarvestable.Bean());
        registerItemless(tomato = new BlockCropHarvestable.Tomato());
        
        registerItemless(melonCrop = new BlockCropBlockfruit.Melon());
        registerItemless(pumpkinCrop = new BlockCropBlockfruit.Pumpkin());
        
        register(melonFruit = new BlockFruit("melon_fruit",
                () -> ModItems.melon, () -> ModItems.seedMelon));
        register(pumpkinFruit = new BlockFruit("pumpkin_fruit",
                () -> ModItems.pumpkin, () -> ModItems.seedPumpkin));
        
        register(seedlingApple = new BlockSeedling.Apple());
        register(seedlingPear = new BlockSeedling.Pear());
        register(seedlingOrange = new BlockSeedling.Orange());
        register(seedlingBanana = new BlockSeedling.Banana());
        
        // Register walls in priority order for correct rendering
        register(wallLog = new BlockWallLog());
        register(fence = new BlockWallThin(BlockMaterial.WOOD_FURNITURE,
                "fence", 2F, ToolType.AXE, 90));
        register(wallPole = new BlockWallThin(BlockMaterial.WOOD_FURNITURE,
                "wall_pole", 2F, ToolType.AXE), 4);
        register(frame = new BlockWallThin(BlockMaterial.WOOD_FURNITURE,
                "frame", 2F, ToolType.AXE), 6);
        registerItemless(wallMudSingle =
                new BlockWallRough(BlockMaterial.STONE_FURNITURE,
                "wall_mud_single", 1F, ToolType.PICKAXE, false,
                () -> ModItems.wallMud));
        registerItemless(wallRoughSingle =
                new BlockWallRough(BlockMaterial.STONE_FURNITURE,
                "wall_rough_single", 1.5F, ToolType.PICKAXE,
                false, () -> ModItems.wallRough));
        registerItemless(wallBrickSingle =
                new BlockWallComplex(BlockMaterial.STONE_FURNITURE,
                "wall_brick_single", 2F, ToolType.PICKAXE,
                false, () -> ModItems.wallBrick));
        registerItemless(wallStoneSingle =
                new BlockWallComplex(BlockMaterial.STONE_FURNITURE,
                "wall_stone_single", 2F, ToolType.PICKAXE,
                false, () -> ModItems.wallStone));
        registerItemless(wallMudDouble =
                new BlockWallRough(BlockMaterial.STONE_FURNITURE,
                "wall_mud_double", 3F, ToolType.PICKAXE,
                true, () -> ModItems.wallMud));
        registerItemless(wallRoughDouble =
                new BlockWallRough(BlockMaterial.STONE_FURNITURE,
                "wall_rough_double", 3F, ToolType.PICKAXE,
                true, () -> ModItems.wallRough));
        registerItemless(wallBrickDouble =
                new BlockWallComplex(BlockMaterial.STONE_FURNITURE,
                "wall_brick_double", 3F, ToolType.PICKAXE,
                true,  () -> ModItems.wallBrick)); 
        registerItemless(wallStoneDouble =
                new BlockWallComplex(BlockMaterial.STONE_FURNITURE,
                "wall_stone_double", 3F, ToolType.PICKAXE,
                true, () -> ModItems.wallStone));
        
        register(stairsBrick = new BlockStairsComplex(BlockMaterial
                .STONE_FURNITURE, "stairs_brick", 3F, ToolType.PICKAXE), 2);
        register(stairsStone = new BlockStairsComplex(BlockMaterial
                .STONE_FURNITURE, "stairs_stone", 3F, ToolType.PICKAXE), 2);
        register(stairsWood = new BlockStairsStraight.Joining("stairs_wood",
                2F), 4);
        register(stairsPole = new BlockStairsStraight.Single("stairs_pole",
                2F), 4);
        
        registerItemless(vaultStoneSingle = new BlockVault("vault_stone_single",
                () -> ModItems.vaultStone, false, BlockWeight.HEAVY));
        registerItemless(vaultStoneDouble = new BlockVault("vault_stone_double",
                () -> ModItems.vaultStone, true, BlockWeight.HEAVY));
        registerItemless(vaultBrickSingle = new BlockVault("vault_brick_single",
                () -> ModItems.vaultBrick, false, BlockWeight.HEAVY));
        registerItemless(vaultBrickDouble = new BlockVault("vault_brick_double",
                () -> ModItems.vaultBrick, true, BlockWeight.HEAVY));
        register(vaultFrame = new BlockVault("vault_frame",
                () -> Item.getItemFromBlock(vaultFrame),
                false, BlockWeight.LIGHT), 6);
        
        registerItemless(doorPole = new BlockDoor("door_pole",
                () -> ModItems.doorPole));
        registerItemless(doorWood = new BlockDoor("door_wood",
                () -> ModItems.doorWood));
        
        register(window = new BlockWindow(), 4);
        
        registerItemless(beamThick = new BlockBeam("beam_thick",
                BeamThick::new));
        registerItemless(beamThin = new BlockBeam("beam_thin",
                BeamThin::new));
                
        registerItemless(slabStoneSingle = new BlockSlab("slab_stone_single",
                false, () -> ModItems.slabStone));
        registerItemless(slabStoneDouble = new BlockSlab("slab_stone_double",
                true, () -> ModItems.slabStone));
        registerItemless(slabBrickSingle = new BlockSlab("slab_brick_single",
                false, () -> ModItems.slabBrick));
        registerItemless(slabBrickDouble = new BlockSlab("slab_brick_double",
                true, () -> ModItems.slabBrick));
                
        registerItemless(tar = new BlockTar());
        
        registerItemless(invisibleLight = new BlockInvisibleLight());
        
        register(flatroofPole = new BlockFlatroof("flatroof_pole",
                1F, ToolType.AXE), 4);
        
        register(pitchroofClay = new BlockPitchroof(
                BlockMaterial.WOOD_FURNITURE, "pitchroof_clay", 2F), 4);
        
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
        OFFHAND_ONLY.add(Item.getItemFromBlock(Blocks.CHEST)
                .setMaxStackSize(1));
        Item.getItemFromBlock(Blocks.LADDER).setMaxStackSize(8);
    }
    
    @SideOnly(Side.CLIENT)
    public static void preInitClient() {
                
        for(Entry<Block, Item> entry : MOD_BLOCKS.entrySet()) {
            
            model(entry.getKey(), entry.getValue());
        }
    }
    
    private static Block register(Block block, boolean isOffhandOnly) {
        
        return register(block, 1, isOffhandOnly);
    }
    
    private static Block register(Block block) {
        
        return register(block, 1, false);
    }
        
    private static Block register(Block block, int stackSize) {
        
        return register(block, stackSize, false);
    }

    private static Block register(Block block,
            int stackSize, boolean isOffhandOnly) {

        Item item = new ItemBlock(block).setMaxStackSize(stackSize);
        
        if (isOffhandOnly) {
            
            OFFHAND_ONLY.add(item);
        }

        GameRegistry.register(block);
        GameRegistry.register(item
                .setRegistryName(block.getRegistryName()));
        MOD_BLOCKS.put(block, item);
        return block;
    }
    
    private static Block registerItemless(Block block) {
        
        MOD_BLOCKS.put(block, Item.getItemFromBlock(block));
        GameRegistry.register(block);
        return block;
    }
    
    @SideOnly(Side.CLIENT)
    private static void model(Block block, Item item) {
        
        if (block instanceof IFluidBlock) {
            

            ResourceLocation registry = block.getRegistryName();
            ModelResourceLocation loc = new ModelResourceLocation(
                    registry.getResourceDomain() + ":fluid#" +
                    registry.getResourcePath());
            
            ModelBakery.registerItemVariants(item);
            ModelLoader.setCustomMeshDefinition(item, stack -> loc);
            ModelLoader.setCustomStateMapper(block, new StateMapperBase() {
                
                @Override
                protected ModelResourceLocation getModelResourceLocation(
                        IBlockState state) {
                    
                    return loc;
                }
            });
            
        } else if (block instanceof BlockInvisibleLight) {
            
            ModelLoader.setCustomStateMapper(block,
                    (b) -> Collections.emptyMap());
            
        } else if (block instanceof IDelayedMultipart) {

            ModelLoaderRegistry.registerLoader(((IDelayedMultipart)
                    block).getLoader());
            
            ModelResourceLocation loc = new ModelResourceLocation(block
                    .getRegistryName(), "delayedbake");

            ModelLoader.setCustomStateMapper(block, new StateMapperBase() {
                
                @Override
                protected ModelResourceLocation getModelResourceLocation(
                        IBlockState state) {
                    
                    return loc;
                }
            });
            
            if (item instanceof ItemBlock) {
                
                ModelLoader.setCustomModelResourceLocation(item, 0,
                        new ModelResourceLocation(block.getRegistryName(),
                        "inventory"));
                
            }
            
        } else if (block instanceof BlockCropBlockfruit) {
            
            ModelLoader.setCustomStateMapper(block, new StateMapperBase() {
                
                @Override
                protected ModelResourceLocation getModelResourceLocation(
                        IBlockState state) {
                    
                    Map<IProperty<?>, Comparable<?>> map =
                            Maps.newLinkedHashMap(state.getProperties());

                    if (state.getValue(BlockStem.FACING) != EnumFacing.UP) {
                        
                        map.remove(BlockStem.AGE);
                    }

                    return new ModelResourceLocation(block.getRegistryName(),
                            this.getPropertyString(map));
                }
            });
            
        } else {

            ModelLoader.setCustomModelResourceLocation(item, 0,
                    new ModelResourceLocation(block.getRegistryName(),
                    "inventory"));
        }
        
        if (block instanceof BlockHarvestableLeaves) {
            
            ((BlockLeaves) block).setGraphicsLevel(Minecraft.getMinecraft()
                    .gameSettings.fancyGraphics);
        }
    }
}

package com.jayavery.jjmod.init;

import java.util.Set;
import com.google.common.collect.Sets;
import com.jayavery.jjmod.blocks.BlockSlab;
import com.jayavery.jjmod.blocks.BlockVault;
import com.jayavery.jjmod.blocks.BlockWallComplex;
import com.jayavery.jjmod.blocks.BlockWallLog;
import com.jayavery.jjmod.blocks.BlockWallRough;
import com.jayavery.jjmod.entities.projectile.EntityProjectile;
import com.jayavery.jjmod.entities.projectile.EntitySpearBronze;
import com.jayavery.jjmod.entities.projectile.EntitySpearCopper;
import com.jayavery.jjmod.entities.projectile.EntitySpearFlint;
import com.jayavery.jjmod.entities.projectile.EntitySpearSteel;
import com.jayavery.jjmod.entities.projectile.EntitySpearWood;
import com.jayavery.jjmod.items.ItemApparel;
import com.jayavery.jjmod.items.ItemArrow;
import com.jayavery.jjmod.items.ItemAxe;
import com.jayavery.jjmod.items.ItemBed;
import com.jayavery.jjmod.items.ItemBlockplacer;
import com.jayavery.jjmod.items.ItemBow;
import com.jayavery.jjmod.items.ItemBucket;
import com.jayavery.jjmod.items.ItemCarcassDecayable;
import com.jayavery.jjmod.items.ItemCompost;
import com.jayavery.jjmod.items.ItemEdible;
import com.jayavery.jjmod.items.ItemEdibleDecayable;
import com.jayavery.jjmod.items.ItemEdibleSeed;
import com.jayavery.jjmod.items.ItemEdibleSeedDecayable;
import com.jayavery.jjmod.items.ItemHoe;
import com.jayavery.jjmod.items.ItemHuntingknife;
import com.jayavery.jjmod.items.ItemJj;
import com.jayavery.jjmod.items.ItemMachete;
import com.jayavery.jjmod.items.ItemPickaxe;
import com.jayavery.jjmod.items.ItemRice;
import com.jayavery.jjmod.items.ItemSeed;
import com.jayavery.jjmod.items.ItemShears;
import com.jayavery.jjmod.items.ItemShield;
import com.jayavery.jjmod.items.ItemShovel;
import com.jayavery.jjmod.items.ItemSickle;
import com.jayavery.jjmod.items.ItemSpear;
import com.jayavery.jjmod.items.ItemSword;
import com.jayavery.jjmod.tileentities.TEBeam.EnumFloor;
import com.jayavery.jjmod.tileentities.TECraftingArmourer.EnumPartArmourer;
import com.jayavery.jjmod.tileentities.TECraftingCandlemaker.EnumPartCandlemaker;
import com.jayavery.jjmod.tileentities.TECraftingForge.EnumPartForge;
import com.jayavery.jjmod.tileentities.TECraftingMason.EnumPartMason;
import com.jayavery.jjmod.tileentities.TECraftingSawpit.EnumPartSawpit;
import com.jayavery.jjmod.tileentities.TECraftingTextiles.EnumPartTextiles;
import com.jayavery.jjmod.tileentities.TECraftingWoodworking.EnumPartWoodworking;
import com.jayavery.jjmod.tileentities.TEFurnaceClay.EnumPartClay;
import com.jayavery.jjmod.tileentities.TEFurnaceStone.EnumPartStone;
import com.jayavery.jjmod.utilities.EquipMaterial;
import com.jayavery.jjmod.utilities.FoodType;
import net.minecraft.block.SoundType;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModItems {
    
    /** All new items, for ease of modelling. */
    private static final Set<Item> MOD_ITEMS = Sets.newHashSet();
    
    public static ItemEdibleDecayable banana;
    public static ItemEdibleDecayable pear;
    public static ItemEdibleDecayable orange;
    public static ItemEdible honey;
    public static ItemEdibleDecayable wheatBoiled;
    public static ItemEdibleDecayable riceBoiled;
    public static ItemEdibleDecayable chickpeasBoiled;
    public static ItemEdibleDecayable pumpkin;
    public static ItemEdibleDecayable melon;
    public static ItemEdible bread;
    
    public static ItemEdibleDecayable apple;
    public static ItemEdible sugar;
    public static ItemEdibleDecayable potatoCooked;
    public static ItemEdibleDecayable egg;
    public static ItemEdibleSeedDecayable mushroomRed;
    public static ItemEdibleSeedDecayable mushroomBrown;
    public static ItemEdibleDecayable beefRaw;
    public static ItemEdibleDecayable porkRaw;
    public static ItemEdibleDecayable chickenRaw;
    public static ItemEdibleDecayable muttonRaw;
    public static ItemEdibleDecayable rabbitRaw;
    public static ItemEdibleDecayable fishRaw;
    
    public static ItemCarcassDecayable carcassCowpart;
    public static ItemCarcassDecayable carcassPig;
    public static ItemCarcassDecayable carcassChicken;
    public static ItemCarcassDecayable carcassSheep;
    public static ItemCarcassDecayable carcassRabbit;
    
    public static ItemEdibleDecayable beefCooked;
    public static ItemEdibleDecayable porkCooked;
    public static ItemEdibleDecayable muttonCooked;
    public static ItemEdibleDecayable rabbitCooked;
    public static ItemEdibleDecayable chickenCooked;
    public static ItemEdibleDecayable fishCooked;
    
    public static ItemEdibleSeedDecayable bean;
    public static ItemEdibleSeedDecayable pepper;
    public static ItemEdibleSeedDecayable tomato;
    public static ItemEdibleSeedDecayable berry;
    
    public static ItemEdibleSeedDecayable carrot;
    public static ItemEdibleSeedDecayable potato;
    public static ItemEdibleSeedDecayable beetroot;
    public static ItemEdibleSeed seedPumpkin;
        
    public static ItemRice rice;

    public static ItemJj cotton;
    public static ItemJj twineHemp;
    public static ItemJj wool;
    
    public static ItemSeed chickpeas;
    public static ItemSeed cuttingCotton;
    public static ItemSeed cuttingHemp;
    
    public static ItemSeed wheat;
    public static ItemSeed seedMelon;

    public static ItemBed bedLeaf;
    public static ItemBed bedCotton;
    public static ItemBed bedWool;
    public static ItemBed bedSimple;

    public static ItemBucket bucketEmpty;
    public static ItemBucket bucketWater;
    public static ItemBucket bucketTar;
    public static ItemJj bucketMilk;

    public static ItemJj backpack;
    public static ItemJj yoke;

    public static ItemJj amethyst;
    public static ItemJj fireopal;
    public static ItemJj ruby;
    public static ItemJj sapphire;

    public static ItemJj beeswax;
    public static ItemJj skinBear;
    public static ItemJj skinCow;
    public static ItemJj skinPig;
    public static ItemJj skinSheep;
    public static ItemJj skinWolf;
    public static ItemJj tallow;
    public static ItemJj honeycomb;
    
    public static ItemJj ingotCopper;
    public static ItemJj ingotSilver;
    public static ItemJj ingotSteel;
    public static ItemJj ingotTin;

    public static ItemJj leaves;
    public static ItemJj pole;
    public static ItemJj log;
    public static ItemJj thicklog;
    public static ItemJj timber;

    public static ItemBlockplacer.Heaping looseDirt;
    public static ItemBlockplacer.Heaping looseGravel;
    public static ItemBlockplacer.Heaping looseSand;
    public static ItemBlockplacer.Heaping looseClay;
    
    public static ItemJj glass;
    
    public static ItemCompost compost;
    
    public static ItemJj clay;

    public static ItemJj mudbricks;
    public static ItemJj peatDry;
    public static ItemJj peatWet;
    
    public static ItemJj salt;
    public static ItemJj chalk;
    
    public static ItemJj oreTin;
    public static ItemJj oreCopper;
    public static ItemJj oreIron;
    public static ItemJj oreSilver;
    public static ItemJj oreGold;

    public static ItemBlockplacer.Heaping rubble;
    public static ItemJj stoneDressed;

    public static ItemJj potClay;
    public static ItemJj potMetal;

    public static ItemBlockplacer.Multipart<EnumPartCandlemaker>
            craftingCandlemaker;
    public static ItemBlockplacer.Multipart<EnumPartForge> craftingForge;
    public static ItemBlockplacer.Multipart<EnumPartMason> craftingMason;
    public static ItemBlockplacer.Multipart<EnumPartTextiles> craftingTextiles;
    public static ItemBlockplacer.Multipart<EnumPartWoodworking>
            craftingWoodworking;
    public static ItemBlockplacer.Multipart<EnumPartSawpit> craftingSawpit;
    public static ItemBlockplacer.Multipart<EnumPartArmourer> craftingArmourer;
    
    public static ItemBlockplacer.Multipart<EnumPartClay> furnaceClay;
    public static ItemBlockplacer.Multipart<EnumPartStone> furnaceStone;

    public static ItemJj arrowheadBronze;
    public static ItemJj arrowheadCopper;
    public static ItemJj arrowheadFlint;
    public static ItemJj arrowheadSteel;

    public static ItemJj axeheadBronze;
    public static ItemJj axeheadCopper;
    public static ItemJj axeheadFlint;
    public static ItemJj axeheadSteel;

    public static ItemJj hoeheadBronze;
    public static ItemJj hoeheadCopper;
    public static ItemJj hoeheadSteel;

    public static ItemJj knifebladeBronze;
    public static ItemJj knifebladeCopper;
    public static ItemJj knifebladeSteel;

    public static ItemJj machetebladeBronze;
    public static ItemJj machetebladeCopper;
    public static ItemJj machetebladeSteel;

    public static ItemJj pickheadBronze;
    public static ItemJj pickheadCopper;
    public static ItemJj pickheadFlint;
    public static ItemJj pickheadSteel;

    public static ItemJj sicklebladeBronze;
    public static ItemJj sicklebladeCopper;
    public static ItemJj sicklebladeSteel;

    public static ItemJj shovelheadBronze;
    public static ItemJj shovelheadCopper;
    public static ItemJj shovelheadSteel;

    public static ItemJj spearheadBronze;
    public static ItemJj spearheadCopper;
    public static ItemJj spearheadFlint;
    public static ItemJj spearheadSteel;

    public static ItemJj swordbladeBronze;
    public static ItemJj swordbladeCopper;
    public static ItemJj swordbladeSteel;

    public static ItemArrow.Bronze arrowBronze;
    public static ItemArrow.Copper arrowCopper;
    public static ItemArrow.Flint arrowFlint;
    public static ItemArrow.Steel arrowSteel;
    public static ItemArrow.Wood arrowWood;

    public static ItemHoe hoeBronze;
    public static ItemHoe hoeCopper;
    public static ItemHoe hoeSteel;
    public static ItemHoe hoeAntler;

    public static ItemSickle sickleBronze;
    public static ItemSickle sickleCopper;
    public static ItemSickle sickleSteel;

    public static ItemShovel shovelAntler;
    public static ItemShovel shovelBronze;
    public static ItemShovel shovelCopper;
    public static ItemShovel shovelSteel;
    public static ItemShovel shovelWood;

    public static ItemAxe axeBronze;
    public static ItemAxe axeCopper;
    public static ItemAxe axeFlint;
    public static ItemAxe axeSteel;

    public static ItemHuntingknife huntingknifeBronze;
    public static ItemHuntingknife huntingknifeCopper;
    public static ItemHuntingknife huntingknifeFlint;
    public static ItemHuntingknife huntingknifeSteel;

    public static ItemMachete macheteBronze;
    public static ItemMachete macheteCopper;
    public static ItemMachete macheteSteel;

    public static ItemPickaxe pickaxeBronze;
    public static ItemPickaxe pickaxeCopper;
    public static ItemPickaxe pickaxeFlint;
    public static ItemPickaxe pickaxeSteel;

    public static ItemShears shearsFlint;
    public static ItemShears shearsBronze;
    public static ItemShears shearsCopper;
    public static ItemShears shearsSteel;

    public static ItemSpear spearBronze;
    public static ItemSpear spearCopper;
    public static ItemSpear spearFlint;
    public static ItemSpear spearSteel;
    public static ItemSpear spearWood;

    public static ItemSword swordBronze;
    public static ItemSword swordCopper;
    public static ItemSword swordSteel;
    
    public static ItemShield shieldWood;
    public static ItemShield shieldSteel;
    
    public static ItemApparel cottonHead;
    public static ItemApparel cottonChest;
    public static ItemApparel cottonLegs;
    public static ItemApparel cottonFeet;
    
    public static ItemApparel woolHead;
    public static ItemApparel woolChest;
    public static ItemApparel woolLegs;
    public static ItemApparel woolFeet;
    
    public static ItemApparel furHead;
    public static ItemApparel furChest;
    public static ItemApparel furLegs;
    public static ItemApparel furFeet;
    
    public static ItemApparel leatherHead;
    public static ItemApparel leatherChest;
    public static ItemApparel leatherLegs;
    public static ItemApparel leatherFeet;
    
    public static ItemApparel steelmailHead;
    public static ItemApparel steelmailChest;
    public static ItemApparel steelmailLegs;
    public static ItemApparel steelmailFeet;
    
    public static ItemApparel steelplateHead;
    public static ItemApparel steelplateChest;
    public static ItemApparel steelplateLegs;
    public static ItemApparel steelplateFeet;

    public static ItemBow bowCrude;
    public static ItemBow bowWar;
    
    public static ItemBlockplacer.Doubling<BlockWallComplex> wallBrick; 
    public static ItemBlockplacer.Doubling<BlockWallRough> wallMud;
    public static ItemBlockplacer.Doubling<BlockWallRough> wallRough;
    public static ItemBlockplacer.Doubling<BlockWallComplex> wallStone;
    public static ItemBlockplacer.Doubling<BlockWallLog> wallLog;
    
    public static ItemBlockplacer.Door doorPole;
    public static ItemBlockplacer.Door doorWood;
    
    public static ItemBlockplacer.Beam beamLong;
    public static ItemBlockplacer.Beam beamShort;
    
    public static ItemBlockplacer.Floor floorPole;
    public static ItemBlockplacer.Floor floorWood;
    
    public static ItemBlockplacer.Doubling<BlockSlab> slabStone;
    public static ItemBlockplacer.Doubling<BlockSlab> slabBrick;
    
    public static ItemBlockplacer.Doubling<BlockVault> vaultStone;
    public static ItemBlockplacer.Doubling<BlockVault> vaultBrick;
    
    public static void preInit() {

        register(banana = new ItemEdibleDecayable("banana",
                4, 6F, 5, FoodType.FRUITVEG, 2));
        register(pear = new ItemEdibleDecayable("pear",
                4, 4F, 5, FoodType.FRUITVEG, 2));
        register(orange = new ItemEdibleDecayable("orange",
                3, 3F, 6, FoodType.FRUITVEG, 4));
        register(honey = new ItemEdible("honey",
                4, 1F, 8, FoodType.CARBS));
        register(wheatBoiled = new ItemEdibleDecayable("wheat_boiled",
                4, 2F, 10, FoodType.CARBS, 1));
        register(riceBoiled = new ItemEdibleDecayable("rice_boiled",
                4, 2, 12, FoodType.CARBS, 1));
        register(chickpeasBoiled = new ItemEdibleDecayable("chickpeas_boiled",
                3, 3, 10, FoodType.PROTEIN, 1));
        register(pumpkin = new ItemEdibleDecayable("pumpkin",
                10, 10, 2, FoodType.FRUITVEG, 4));
        register(melon = new ItemEdibleDecayable("melon",
                12, 12, 2, FoodType.FRUITVEG, 2));
        register(bread = new ItemEdible("bread", 4, 2, 10, FoodType.CARBS));
        
        register(apple = new ItemEdibleDecayable("apple",
                4, 4, 5, FoodType.FRUITVEG, 2));
        register(sugar = new ItemEdible("sugar",
                5, 0, 10, FoodType.CARBS));
        register(potatoCooked = new ItemEdibleDecayable("potato_cooked",
                8, 6, 5, FoodType.CARBS, 2));
        register(egg = new ItemEdibleDecayable("egg",
                5, 5, 6, FoodType.PROTEIN, 4));
        register(mushroomRed = new ItemEdibleSeedDecayable("mushroom_red",
                4, 4, 4, ModBlocks.mushroombabyRed,
                EnumPlantType.Cave, FoodType.FRUITVEG, 2));
        register(mushroomBrown = new ItemEdibleSeedDecayable("mushroom_brown", 
                2, 2, 9, ModBlocks.mushroombabyBrown,
                EnumPlantType.Cave, FoodType.FRUITVEG, 2));
        register(beefRaw = new ItemEdibleDecayable("beef_raw",
                3, 6, 5, FoodType.PROTEIN, 1));
        register(porkRaw = new ItemEdibleDecayable("pork_raw",
                2, 5, 6, FoodType.PROTEIN, 1));
        register(chickenRaw = new ItemEdibleDecayable("chicken_raw",
                1, 3, 10, FoodType.PROTEIN, 1));
        register(muttonRaw = new ItemEdibleDecayable("mutton_raw",
                2, 4, 7, FoodType.PROTEIN, 1));
        register(rabbitRaw = new ItemEdibleDecayable("rabbit_raw",
                1, 2, 15, FoodType.PROTEIN, 1));
        register(fishRaw = new ItemEdibleDecayable("fish_raw",
                1, 3, 10, FoodType.PROTEIN, 1));
        
        register(carcassCowpart = new ItemCarcassDecayable("carcass_cowpart",
                ModBlocks.carcassCowpart), true);
        register(carcassPig = new ItemCarcassDecayable("carcass_pig",
                ModBlocks.carcassPig));
        register(carcassChicken = new ItemCarcassDecayable("carcass_chicken",
                ModBlocks.carcassChicken));
        register(carcassSheep = new ItemCarcassDecayable("carcass_sheep",
                ModBlocks.carcassSheep));
        register(carcassRabbit = new ItemCarcassDecayable("carcass_rabbit",
                ModBlocks.carcassRabbit));
        
        register(beefCooked = new ItemEdibleDecayable("beef_cooked",
                6, 6, 5, FoodType.PROTEIN, 2));
        register(porkCooked = new ItemEdibleDecayable("pork_cooked",
                5, 5, 6, FoodType.PROTEIN, 2));
        register(muttonCooked = new ItemEdibleDecayable("mutton_cooked",
                4, 4, 7, FoodType.PROTEIN, 2));
        register(rabbitCooked = new ItemEdibleDecayable("rabbit_cooked",
                2, 2, 15, FoodType.PROTEIN, 2));
        register(chickenCooked = new ItemEdibleDecayable("chicken_cooked",
                3, 3, 10, FoodType.PROTEIN, 2));
        register(fishCooked = new ItemEdibleDecayable("fish_cooked",
                3, 3, 10, FoodType.PROTEIN, 2));
        
        register(bean = new ItemEdibleSeedDecayable("bean", 2, 2F, 10,
                ModBlocks.bean, EnumPlantType.Crop, FoodType.FRUITVEG, 2));
        register(pepper = new ItemEdibleSeedDecayable("pepper", 3, 3F, 6,
                ModBlocks.pepper, EnumPlantType.Crop, FoodType.FRUITVEG, 2));
        register(tomato = new ItemEdibleSeedDecayable("tomato", 3, 3F, 7,
                ModBlocks.tomato, EnumPlantType.Crop, FoodType.FRUITVEG, 2));
        register(berry = new ItemEdibleSeedDecayable("berry", 1, 1F, 20,
                ModBlocks.berry, EnumPlantType.Crop, FoodType.FRUITVEG, 2));
        
        register(potato = new ItemEdibleSeedDecayable("potato", 7, 7F, 5,
                ModBlocks.potato, EnumPlantType.Crop, FoodType.CARBS, 4));
        register(carrot = new ItemEdibleSeedDecayable("carrot", 3, 3F, 7,
                ModBlocks.carrot, EnumPlantType.Crop, FoodType.FRUITVEG, 4,
                EntityPig.class, EntityRabbit.class));
        register(beetroot = new ItemEdibleSeedDecayable("beetroot", 3, 3F, 6,
                ModBlocks.beetroot, EnumPlantType.Crop, FoodType.FRUITVEG, 4));
        register(seedPumpkin = new ItemEdibleSeed("seeds_pumpkin",
                1, 1, 15, ModBlocks.pumpkinCrop, FoodType.PROTEIN));
        
        register(rice = new ItemRice());
        
        register(cotton = new ItemJj("cotton", 6));
        register(twineHemp = new ItemJj("twine_hemp", 6));
        register(wool = new ItemJj("wool", 5));
        
        register(chickpeas = new ItemSeed("chickpeas",
                10, ModBlocks.chickpea));
        register(cuttingCotton = new ItemSeed("cutting_cotton",
                1, ModBlocks.cotton));
        register(cuttingHemp = new ItemSeed("cutting_hemp",
                1, ModBlocks.hemp));

        register(wheat = new ItemSeed("wheat", 10, ModBlocks.wheat,
                EntityCow.class, EntitySheep.class, EntityChicken.class));
        register(seedMelon = new ItemSeed("seeds_melon",
                15, ModBlocks.melonCrop));
        
        register(bedLeaf = new ItemBed("bed_leaf",
                ModBlocks.bedLeaf), true);
        register(bedCotton = new ItemBed("bed_cotton",
                ModBlocks.bedCotton));
        register(bedWool = new ItemBed("bed_wool",
                ModBlocks.bedWool));
        register(bedSimple = new ItemBed("bed_simple",
                ModBlocks.bedSimple), true);

        register(bucketEmpty = new ItemBucket("bucket_empty",
                Blocks.AIR, () -> bucketEmpty,
                () -> bucketWater, () -> bucketTar));
        register(bucketWater = new ItemBucket("bucket_water",
                Blocks.FLOWING_WATER, () -> bucketEmpty,
                () -> bucketWater, () -> bucketTar), true);
        register(bucketTar = new ItemBucket("bucket_tar",
                ModBlocks.tar, () -> bucketEmpty,
                () -> bucketWater, () -> bucketTar), true);
        register(bucketMilk = new ItemJj("bucket_milk",
                1, CreativeTabs.MISC));
        
        register(backpack = new ItemJj("backpack",
                1, CreativeTabs.TRANSPORTATION));
        register(yoke = new ItemJj("yoke",
                1, CreativeTabs.TRANSPORTATION));

        register(amethyst = new ItemJj("amethyst", 53));
        register(fireopal = new ItemJj("fireopal", 47));
        register(ruby = new ItemJj("ruby", 61));
        register(sapphire = new ItemJj("sapphire", 59));

        register(beeswax = new ItemJj("beeswax", 12));
        register(skinBear = new ItemJj("skin_bear", 4));
        register(skinCow = new ItemJj("skin_cow", 4));
        register(skinPig = new ItemJj("skin_pig", 4));
        register(skinSheep = new ItemJj("skin_sheep", 4));
        register(skinWolf = new ItemJj("skin_wolf", 4));
        register(tallow = new ItemJj("tallow", 2));
        register(honeycomb = new ItemJj("honeycomb"));

        register(ingotCopper = new ItemJj("ingot_copper", 2));
        register(ingotSilver = new ItemJj("ingot_silver", 2));
        register(ingotSteel = new ItemJj("ingot_steel", 2));
        register(ingotTin = new ItemJj("ingot_tin", 2));

        register(leaves = new ItemJj("leaves", 3));
        register(pole = new ItemJj("pole", 6));
        register(log = new ItemJj("log", 3));
        register(thicklog = new ItemJj("thicklog"));
        register(timber = new ItemJj("timber", 6));

        register(looseDirt = new ItemBlockplacer.Heaping("loose_dirt",
                SoundType.GROUND, Blocks.DIRT));
        register(looseClay = new ItemBlockplacer.Heaping("loose_clay",
                SoundType.GROUND, Blocks.CLAY));
        register(looseSand = new ItemBlockplacer.Heaping("loose_sand",
                SoundType.SAND, Blocks.SAND));
        register(looseGravel = new ItemBlockplacer.Heaping("loose_gravel",
                SoundType.GROUND, Blocks.GRAVEL));
        
        register(mudbricks = new ItemJj("mudbricks"));
        register(peatDry = new ItemJj("peat_dry"));
        register(peatWet = new ItemJj("peat_wet"));
        
        register(salt = new ItemJj("salt"));
        register(chalk = new ItemJj("chalk"));
        register(clay = new ItemJj("clay", 9));
        
        register(glass = new ItemJj("glass", 6));
        
        register(compost = new ItemCompost());

        register(oreTin = new ItemJj("ore_tin"));
        register(oreCopper = new ItemJj("ore_copper"));
        register(oreIron = new ItemJj("ore_iron"));
        register(oreSilver = new ItemJj("ore_silver"));
        register(oreGold = new ItemJj("ore_gold"));
        
        register(rubble = new ItemBlockplacer.Heaping("rubble",
                SoundType.GROUND, ModBlocks.rubble));
        
        register(stoneDressed = new ItemJj("stone_dressed", 1));

        register(potClay = new ItemJj("pot_clay"));
        register(potMetal = new ItemJj("pot_metal"));

        register(craftingCandlemaker = new ItemBlockplacer.Multipart<EnumPartCandlemaker>("crafting_candlemaker",
                EnumPartCandlemaker.FRONT, SoundType.WOOD), true);
        register(craftingForge = new ItemBlockplacer.Multipart<EnumPartForge>("crafting_forge",
                EnumPartForge.FM, SoundType.METAL), true);
        register(craftingMason = new ItemBlockplacer.Multipart<EnumPartMason>("crafting_mason",
                EnumPartMason.FM, SoundType.STONE), true);
        register(craftingTextiles = new ItemBlockplacer.Multipart<EnumPartTextiles>("crafting_textiles",
                EnumPartTextiles.FRONT, SoundType.WOOD), true);
        register(craftingWoodworking = new ItemBlockplacer.Multipart<EnumPartWoodworking>("crafting_woodworking",
                EnumPartWoodworking.FM, SoundType.WOOD), true);
        register(craftingSawpit = new ItemBlockplacer.Multipart<EnumPartSawpit>("crafting_sawpit",
                EnumPartSawpit.F, SoundType.WOOD), true);
        register(craftingArmourer = new ItemBlockplacer.Multipart<EnumPartArmourer>("crafting_armourer",
                EnumPartArmourer.M, SoundType.METAL), true);
        
        register(furnaceClay = new ItemBlockplacer.Multipart<EnumPartClay>("furnace_clay",
                EnumPartClay.BL, SoundType.STONE), true);
        register(furnaceStone = new ItemBlockplacer.Multipart<EnumPartStone>("furnace_stone",
                EnumPartStone.BM, SoundType.STONE), true);

        register(arrowheadBronze = new ItemJj("arrowhead_bronze", 20));
        register(arrowheadCopper = new ItemJj("arrowhead_copper", 20));
        register(arrowheadFlint = new ItemJj("arrowhead_flint", 20));
        register(arrowheadSteel = new ItemJj("arrowhead_steel", 20));

        register(axeheadBronze = new ItemJj("axehead_bronze", 2));
        register(axeheadCopper = new ItemJj("axehead_copper", 2));
        register(axeheadFlint = new ItemJj("axehead_flint", 2));
        register(axeheadSteel = new ItemJj("axehead_steel", 2));

        register(hoeheadBronze = new ItemJj("hoehead_bronze", 2));
        register(hoeheadCopper = new ItemJj("hoehead_copper", 2));
        register(hoeheadSteel = new ItemJj("hoehead_steel", 2));

        register(knifebladeBronze = new ItemJj("knifeblade_bronze", 2));
        register(knifebladeCopper = new ItemJj("knifeblade_copper", 2));
        register(knifebladeSteel = new ItemJj("knifeblade_steel", 2));

        register(machetebladeBronze = new ItemJj("macheteblade_bronze", 2));
        register(machetebladeCopper = new ItemJj("macheteblade_copper", 2));
        register(machetebladeSteel = new ItemJj("macheteblade_steel", 2));

        register(pickheadBronze = new ItemJj("pickhead_bronze", 2));
        register(pickheadCopper = new ItemJj("pickhead_copper", 2));
        register(pickheadFlint = new ItemJj("pickhead_flint", 2));
        register(pickheadSteel = new ItemJj("pickhead_steel", 2));

        register(sicklebladeBronze = new ItemJj("sickleblade_bronze", 2));
        register(sicklebladeCopper = new ItemJj("sickleblade_copper", 2));
        register(sicklebladeSteel = new ItemJj("sickleblade_steel", 2));

        register(shovelheadBronze = new ItemJj("shovelhead_bronze", 2));
        register(shovelheadCopper = new ItemJj("shovelhead_copper", 2));
        register(shovelheadSteel = new ItemJj("shovelhead_steel", 2));

        register(spearheadBronze = new ItemJj("spearhead_bronze", 2));
        register(spearheadCopper = new ItemJj("spearhead_copper", 2));
        register(spearheadFlint = new ItemJj("spearhead_flint", 2));
        register(spearheadSteel = new ItemJj("spearhead_steel", 2));

        register(swordbladeBronze = new ItemJj("swordblade_bronze"));
        register(swordbladeCopper = new ItemJj("swordblade_copper"));
        register(swordbladeSteel = new ItemJj("swordblade_steel"));

        register(arrowBronze = new ItemArrow.Bronze());
        register(arrowCopper = new ItemArrow.Copper());
        register(arrowFlint = new ItemArrow.Flint());
        register(arrowSteel = new ItemArrow.Steel());
        register(arrowWood = new ItemArrow.Wood());

        register(hoeBronze = new ItemHoe("hoe_bronze",
                EquipMaterial.BRONZE_TOOL));
        register(hoeCopper = new ItemHoe("hoe_copper",
                EquipMaterial.COPPER_TOOL));
        register(hoeSteel = new ItemHoe("hoe_steel",
                EquipMaterial.STEEL_TOOL));
        register(hoeAntler = new ItemHoe("hoe_antler",
                EquipMaterial.ANTLER_TOOL));

        register(sickleBronze = new ItemSickle("sickle_bronze",
                EquipMaterial.BRONZE_TOOL));
        register(sickleCopper = new ItemSickle("sickle_copper",
                EquipMaterial.COPPER_TOOL));
        register(sickleSteel = new ItemSickle("sickle_steel",
                EquipMaterial.STEEL_TOOL));

        register(shovelAntler = new ItemShovel("shovel_antler",
                EquipMaterial.ANTLER_TOOL));
        register(shovelBronze = new ItemShovel("shovel_bronze",
                EquipMaterial.BRONZE_TOOL));
        register(shovelCopper = new ItemShovel("shovel_copper",
                EquipMaterial.COPPER_TOOL));
        register(shovelSteel = new ItemShovel("shovel_steel",
                EquipMaterial.STEEL_TOOL));
        register(shovelWood = new ItemShovel("shovel_wood",
                EquipMaterial.WOOD_TOOL));

        register(axeBronze = new ItemAxe("axe_bronze",
                EquipMaterial.BRONZE_TOOL));
        register(axeCopper = new ItemAxe("axe_copper",
                EquipMaterial.COPPER_TOOL));
        register(axeFlint = new ItemAxe("axe_flint",
                EquipMaterial.FLINT_TOOL));
        register(axeSteel = new ItemAxe("axe_steel",
                EquipMaterial.STEEL_TOOL));

        register(huntingknifeBronze = new
                ItemHuntingknife("huntingknife_bronze",
                EquipMaterial.BRONZE_TOOL));
        register(huntingknifeCopper =
                new ItemHuntingknife("huntingknife_copper",
                EquipMaterial.COPPER_TOOL));
        register(huntingknifeFlint = new ItemHuntingknife("huntingknife_flint",
                EquipMaterial.FLINT_TOOL));
        register(huntingknifeSteel = new ItemHuntingknife("huntingknife_steel",
                EquipMaterial.STEEL_TOOL));

        register(macheteBronze = new ItemMachete("machete_bronze",
                EquipMaterial.BRONZE_TOOL));
        register(macheteCopper = new ItemMachete("machete_copper",
                EquipMaterial.COPPER_TOOL));
        register(macheteSteel = new ItemMachete("machete_steel",
                EquipMaterial.STEEL_TOOL));

        register(pickaxeBronze = new ItemPickaxe("pickaxe_bronze",
                EquipMaterial.BRONZE_TOOL));
        register(pickaxeCopper = new ItemPickaxe("pickaxe_copper",
                EquipMaterial.COPPER_TOOL));
        register(pickaxeFlint = new ItemPickaxe("pickaxe_flint",
                EquipMaterial.FLINT_TOOL));
        register(pickaxeSteel = new ItemPickaxe("pickaxe_steel",
                EquipMaterial.STEEL_TOOL));

        register(shearsFlint = new ItemShears("shears_flint",
                EquipMaterial.FLINT_TOOL, (r) -> 3 + r.nextInt(3)));
        register(shearsBronze = new ItemShears("shears_bronze",
                EquipMaterial.BRONZE_TOOL, (r) -> 6 + r.nextInt(3)));
        register(shearsCopper = new ItemShears("shears_copper",
                EquipMaterial.COPPER_TOOL, (r) -> 5 + r.nextInt(3)));
        register(shearsSteel = new ItemShears("shears_steel",
                EquipMaterial.STEEL_TOOL, (r) -> 7 + r.nextInt(3)));

        register(spearBronze = new ItemSpear("spear_bronze",
                EquipMaterial.BRONZE_TOOL, EntitySpearBronze::new));
        register(spearCopper = new ItemSpear("spear_copper",
                EquipMaterial.COPPER_TOOL, EntitySpearCopper::new));
        register(spearFlint = new ItemSpear("spear_flint",
                EquipMaterial.FLINT_TOOL, EntitySpearFlint::new));
        register(spearSteel = new ItemSpear("spear_steel",
                EquipMaterial.STEEL_TOOL, EntitySpearSteel::new));
        register(spearWood = new ItemSpear("spear_wood",
                EquipMaterial.WOOD_TOOL, EntitySpearWood::new));

        register(swordBronze = new ItemSword("sword_bronze",
                EquipMaterial.BRONZE_TOOL));
        register(swordCopper = new ItemSword("sword_copper",
                EquipMaterial.COPPER_TOOL));
        register(swordSteel = new ItemSword("sword_steel",
                EquipMaterial.STEEL_TOOL));
        
        register(shieldWood = new ItemShield("shield_wood", 100));
        register(shieldSteel = new ItemShield("shield_steel", 200));
        
        register(cottonHead = new ItemApparel("cotton_head",
                EquipMaterial.COTTON_APPAREL,
                EntityEquipmentSlot.HEAD, CreativeTabs.MISC));
        register(cottonChest = new ItemApparel("cotton_chest",
                EquipMaterial.COTTON_APPAREL,
                EntityEquipmentSlot.CHEST, CreativeTabs.MISC));
        register(cottonLegs = new ItemApparel("cotton_legs",
                EquipMaterial.COTTON_APPAREL,
                EntityEquipmentSlot.LEGS, CreativeTabs.MISC));
        register(cottonFeet = new ItemApparel("cotton_feet",
                EquipMaterial.COTTON_APPAREL,
                EntityEquipmentSlot.FEET, CreativeTabs.MISC));
        
        register(woolHead = new ItemApparel("wool_head",
                EquipMaterial.WOOL_APPAREL,
                EntityEquipmentSlot.HEAD, CreativeTabs.MISC));
        register(woolChest = new ItemApparel("wool_chest",
                EquipMaterial.WOOL_APPAREL,
                EntityEquipmentSlot.CHEST, CreativeTabs.MISC));
        register(woolLegs = new ItemApparel("wool_legs",
                EquipMaterial.WOOL_APPAREL,
                EntityEquipmentSlot.LEGS, CreativeTabs.MISC));
        register(woolFeet = new ItemApparel("wool_feet",
                EquipMaterial.WOOL_APPAREL,
                EntityEquipmentSlot.FEET, CreativeTabs.MISC));
        
        
        
        register(furHead = new ItemApparel("fur_head",
                EquipMaterial.FUR_APPAREL, EntityEquipmentSlot.HEAD,
                CreativeTabs.MISC));
        register(furChest = new ItemApparel("fur_chest",
                EquipMaterial.FUR_APPAREL, EntityEquipmentSlot.CHEST,
                CreativeTabs.MISC));
        register(furLegs = new ItemApparel("fur_legs",
                EquipMaterial.FUR_APPAREL, EntityEquipmentSlot.LEGS,
                CreativeTabs.MISC));
        register(furFeet = new ItemApparel("fur_feet",
                EquipMaterial.FUR_APPAREL, EntityEquipmentSlot.FEET,
                CreativeTabs.MISC));
        
        register(leatherHead = new ItemApparel("leather_head",
                EquipMaterial.LEATHER_APPAREL, EntityEquipmentSlot.HEAD,
                CreativeTabs.COMBAT));
        register(leatherChest = new ItemApparel("leather_chest",
                EquipMaterial.LEATHER_APPAREL, EntityEquipmentSlot.CHEST,
                CreativeTabs.COMBAT));
        register(leatherLegs = new ItemApparel("leather_legs",
                EquipMaterial.LEATHER_APPAREL, EntityEquipmentSlot.LEGS,
                CreativeTabs.COMBAT));
        register(leatherFeet = new ItemApparel("leather_feet",
                EquipMaterial.LEATHER_APPAREL, EntityEquipmentSlot.FEET,
                CreativeTabs.COMBAT));
        
        register(steelmailHead = new ItemApparel("steelmail_head",
                EquipMaterial.STEELMAIL_APPAREL, EntityEquipmentSlot.HEAD,
                CreativeTabs.COMBAT));
        register(steelmailChest = new ItemApparel("steelmail_chest",
                EquipMaterial.STEELMAIL_APPAREL, EntityEquipmentSlot.CHEST,
                CreativeTabs.COMBAT));
        register(steelmailLegs = new ItemApparel("steelmail_legs",
                EquipMaterial.STEELMAIL_APPAREL, EntityEquipmentSlot.LEGS,
                CreativeTabs.COMBAT));
        register(steelmailFeet = new ItemApparel("steelmail_feet",
                EquipMaterial.STEELMAIL_APPAREL, EntityEquipmentSlot.FEET,
                CreativeTabs.COMBAT));
        
        register(steelplateHead = new ItemApparel("steelplate_head",
                EquipMaterial.STEELPLATE_APPAREL, EntityEquipmentSlot.HEAD,
                CreativeTabs.COMBAT));
        register(steelplateChest = new ItemApparel("steelplate_chest",
                EquipMaterial.STEELPLATE_APPAREL, EntityEquipmentSlot.CHEST,
                CreativeTabs.COMBAT));
        register(steelplateLegs = new ItemApparel("steelplate_legs",
                EquipMaterial.STEELPLATE_APPAREL, EntityEquipmentSlot.LEGS,
                CreativeTabs.COMBAT));
        register(steelplateFeet = new ItemApparel("steelplate_feet",
                EquipMaterial.STEELPLATE_APPAREL, EntityEquipmentSlot.FEET,
                CreativeTabs.COMBAT));

        register(bowCrude = new ItemBow("bow_crude",
                200, EntityProjectile.CRUDE_MOD));
        register(bowWar = new ItemBow("bow_war",
                384, EntityProjectile.BOW_MOD));

        register(wallBrick = new ItemBlockplacer.Doubling<BlockWallComplex>(
                "wall_brick", 2, SoundType.STONE, ModBlocks.wallBrickSingle,
                ModBlocks.wallBrickDouble));
        register(wallMud = new ItemBlockplacer.Doubling<BlockWallRough>(
                "wall_mud", 2, SoundType.GROUND, ModBlocks.wallMudSingle,
                ModBlocks.wallMudDouble));
        register(wallRough = new ItemBlockplacer.Doubling<BlockWallRough>(
                "wall_rough", 2, SoundType.STONE, ModBlocks.wallRoughSingle,
                ModBlocks.wallRoughDouble));
        register(wallStone = new ItemBlockplacer.Doubling<BlockWallComplex>(
                "wall_stone", 2, SoundType.STONE, ModBlocks.wallStoneSingle,
                ModBlocks.wallStoneDouble));
        register(wallLog = new ItemBlockplacer.Doubling<BlockWallLog>(
                "wall_log", 2, SoundType.WOOD,ModBlocks.wallLogSingle,
                ModBlocks.wallLogDouble));
        
        register(doorPole = new ItemBlockplacer.Door(ModBlocks.doorPole,
                "door_pole"), true);
        register(doorWood = new ItemBlockplacer.Door(ModBlocks.doorWood,
                "door_wood"), true);
        
        register(beamLong = new ItemBlockplacer.Beam("beam_long",
                ModBlocks.beamThick, 4, 8));
        register(beamShort = new ItemBlockplacer.Beam("beam_short",
                ModBlocks.beamThin, 2, 4));
        
        register(floorPole = new ItemBlockplacer.Floor(6, EnumFloor.POLE));
        register(floorWood = new ItemBlockplacer.Floor(6, EnumFloor.WOOD));
        
        register(slabStone = new ItemBlockplacer.Doubling<BlockSlab>(
                "slab_stone", 2, SoundType.STONE, ModBlocks.slabStoneSingle,
                ModBlocks.slabStoneDouble));
        register(slabBrick = new ItemBlockplacer.Doubling<BlockSlab>(
                "slab_brick", 2, SoundType.STONE, ModBlocks.slabBrickSingle,
                ModBlocks.slabBrickDouble));
        
        register(vaultStone = new ItemBlockplacer.Doubling<BlockVault>(
                "vault_stone", 2, SoundType.STONE, ModBlocks.vaultStoneSingle,
                ModBlocks.vaultStoneDouble));
        register(vaultBrick = new ItemBlockplacer.Doubling<BlockVault>(
                "vault_brick", 2, SoundType.STONE, ModBlocks.vaultBrickSingle,
                ModBlocks.vaultBrickDouble));
                
        Items.STICK.setMaxStackSize(12);
        Items.BONE.setMaxStackSize(6);
        Items.FLINT.setMaxStackSize(6);
        Items.LEATHER.setMaxStackSize(3);
        Items.COAL.setMaxStackSize(1);
        Items.RABBIT_HIDE.setMaxStackSize(4);
        Items.REEDS.setMaxStackSize(5);
        Items.BRICK.setMaxStackSize(1);
    }
    
    public static void preInitClient() {
        
        for (Item item : MOD_ITEMS) {
            
            model(item);
        }
    }
    
    private static void register(Item item) {
        
        register(item, false);
    }

    private static void register(Item item, boolean isOffhandOnly) {

        GameRegistry.register(item);
        
        if (isOffhandOnly) {
            
            ModBlocks.OFFHAND_ONLY.add(item);
        }

        MOD_ITEMS.add(item);
    }
    
    private static void model(Item item) {
        
        if (item == compost) {
            
            for (int i = 1; i < 6; i++) {
            
                ModelLoader.setCustomModelResourceLocation(item, i,
                        new ModelResourceLocation(item.getRegistryName(),
                        String.valueOf(i)));
            }
            
        } else {
        
            ModelLoader.setCustomModelResourceLocation(item, 0,
                    new ModelResourceLocation(item.getRegistryName(),
                    "inventory"));
        }
    }
}

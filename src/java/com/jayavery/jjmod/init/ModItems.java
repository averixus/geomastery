package com.jayavery.jjmod.init;

import java.util.Set;
import com.google.common.collect.Sets;
import com.jayavery.jjmod.entities.projectile.EntityProjectile;
import com.jayavery.jjmod.items.ItemApparel;
import com.jayavery.jjmod.items.ItemArrowBronze;
import com.jayavery.jjmod.items.ItemArrowCopper;
import com.jayavery.jjmod.items.ItemArrowFlint;
import com.jayavery.jjmod.items.ItemArrowSteel;
import com.jayavery.jjmod.items.ItemArrowWood;
import com.jayavery.jjmod.items.ItemAxe;
import com.jayavery.jjmod.items.ItemBeam;
import com.jayavery.jjmod.items.ItemBedBreakable;
import com.jayavery.jjmod.items.ItemBedPlain;
import com.jayavery.jjmod.items.ItemBow;
import com.jayavery.jjmod.items.ItemBucket;
import com.jayavery.jjmod.items.ItemCarcassDecayable;
import com.jayavery.jjmod.items.ItemCraftingArmourer;
import com.jayavery.jjmod.items.ItemCraftingCandlemaker;
import com.jayavery.jjmod.items.ItemCraftingForge;
import com.jayavery.jjmod.items.ItemCraftingMason;
import com.jayavery.jjmod.items.ItemCraftingSawpit;
import com.jayavery.jjmod.items.ItemCraftingTextiles;
import com.jayavery.jjmod.items.ItemCraftingWoodworking;
import com.jayavery.jjmod.items.ItemDoor;
import com.jayavery.jjmod.items.ItemEdible;
import com.jayavery.jjmod.items.ItemEdibleDecayable;
import com.jayavery.jjmod.items.ItemEdibleDecayablePoison;
import com.jayavery.jjmod.items.ItemEdibleDecayableSeed;
import com.jayavery.jjmod.items.ItemEdibleSeed;
import com.jayavery.jjmod.items.ItemFloor;
import com.jayavery.jjmod.items.ItemFurnaceClay;
import com.jayavery.jjmod.items.ItemFurnaceStone;
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
import com.jayavery.jjmod.items.ItemSlab;
import com.jayavery.jjmod.items.ItemSpearBronze;
import com.jayavery.jjmod.items.ItemSpearCopper;
import com.jayavery.jjmod.items.ItemSpearFlint;
import com.jayavery.jjmod.items.ItemSpearSteel;
import com.jayavery.jjmod.items.ItemSpearWood;
import com.jayavery.jjmod.items.ItemSword;
import com.jayavery.jjmod.items.ItemWall;
import com.jayavery.jjmod.tileentities.TEBeam.EnumFloor;
import com.jayavery.jjmod.utilities.EquipMaterial;
import com.jayavery.jjmod.utilities.FoodType;
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
    
    public static ItemEdibleDecayablePoison beefRaw;
    public static ItemEdibleDecayablePoison porkRaw;
    public static ItemEdibleDecayablePoison chickenRaw;
    public static ItemEdibleDecayablePoison muttonRaw;
    public static ItemEdibleDecayablePoison rabbitRaw;
    public static ItemEdibleDecayablePoison fishRaw;
    
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
    
    public static ItemEdibleDecayableSeed bean;
    public static ItemEdibleDecayableSeed pepper;
    public static ItemEdibleDecayableSeed tomato;
    public static ItemEdibleDecayableSeed berry;
    
    public static ItemEdibleDecayableSeed carrot;
    public static ItemEdibleDecayableSeed potato;
    public static ItemEdibleDecayableSeed beetroot;
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

    public static ItemBedPlain bedLeaf;
    public static ItemBedBreakable bedCotton;
    public static ItemBedBreakable bedWool;
    public static ItemBedPlain bedSimple;

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

    public static ItemJj dirt;
    public static ItemJj mudbricks;
    public static ItemJj peatDry;
    public static ItemJj peatWet;
    
    public static ItemJj salt;
    public static ItemJj chalk;
    public static ItemJj clay;
    
    public static ItemJj oreTin;
    public static ItemJj oreCopper;
    public static ItemJj oreIron;
    public static ItemJj oreSilver;
    public static ItemJj oreGold;

    public static ItemJj stoneRough;
    public static ItemJj stoneDressed;

    public static ItemJj potClay;
    public static ItemJj potMetal;

    public static ItemCraftingCandlemaker craftingCandlemaker;
    public static ItemCraftingForge craftingForge;
    public static ItemCraftingMason craftingMason;
    public static ItemCraftingTextiles craftingTextiles;
    public static ItemCraftingWoodworking craftingWoodworking;
    public static ItemCraftingSawpit craftingSawpit;
    public static ItemCraftingArmourer craftingArmourer;
    
    public static ItemFurnaceClay furnaceClay;
    public static ItemFurnaceStone furnaceStone;

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

    public static ItemArrowBronze arrowBronze;
    public static ItemArrowCopper arrowCopper;
    public static ItemArrowFlint arrowFlint;
    public static ItemArrowSteel arrowSteel;
    public static ItemArrowWood arrowWood;

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

    public static ItemSpearBronze spearBronze;
    public static ItemSpearCopper spearCopper;
    public static ItemSpearFlint spearFlint;
    public static ItemSpearSteel spearSteel;
    public static ItemSpearWood spearWood;

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
    
    public static ItemWall wallBrick;
    public static ItemWall wallMud;
    public static ItemWall wallRough;
    public static ItemWall wallStone;
    public static ItemWall wallLog;
    
    public static ItemDoor doorPole;
    public static ItemDoor doorWood;
    
    public static ItemBeam beamLong;
    public static ItemBeam beamShort;
    
    public static ItemFloor floorPole;
    public static ItemFloor floorWood;
    
    public static ItemSlab slabStone;
    public static ItemSlab slabBrick;
    
    public static ItemJj sand;

    public static void preInit() {

        register(banana = new ItemEdibleDecayable("banana",
                4, 6F, 5, FoodType.FRUITVEG, 4));
        register(pear = new ItemEdibleDecayable("pear",
                4, 4F, 5, FoodType.FRUITVEG, 4));
        register(orange = new ItemEdibleDecayable("orange",
                3, 3F, 6, FoodType.FRUITVEG, 8));
        register(honey = new ItemEdible("honey",
                4, 1F, 8, FoodType.CARBS));
        register(wheatBoiled = new ItemEdibleDecayable("wheat_boiled",
                4, 2F, 10, FoodType.CARBS, 2));
        register(riceBoiled = new ItemEdibleDecayable("rice_boiled",
                4, 2, 12, FoodType.CARBS, 1));
        register(chickpeasBoiled = new ItemEdibleDecayable("chickpeas_boiled",
                3, 3, 10, FoodType.PROTEIN, 1));
        register(pumpkin = new ItemEdibleDecayable("pumpkin",
                10, 10, 2, FoodType.FRUITVEG, 8));
        register(melon = new ItemEdibleDecayable("melon",
                12, 12, 2, FoodType.FRUITVEG, 4));
        register(bread = new ItemEdible("bread", 4, 2, 10, FoodType.CARBS));
        
        register(apple = new ItemEdibleDecayable("apple",
                4, 4, 5, FoodType.FRUITVEG, 8));
        register(sugar = new ItemEdible("sugar",
                5, 0, 10, FoodType.CARBS));
        register(potatoCooked = new ItemEdibleDecayable("potato_cooked",
                8, 6, 5, FoodType.CARBS, 4));
        register(egg = new ItemEdibleDecayable("egg",
                5, 5, 6, FoodType.PROTEIN, 8));
        
        register(beefRaw = new ItemEdibleDecayablePoison("beef_raw",
                3, 6, 5, FoodType.PROTEIN, 2));
        register(porkRaw = new ItemEdibleDecayablePoison("pork_raw",
                2, 5, 6, FoodType.PROTEIN, 2));
        register(chickenRaw = new ItemEdibleDecayablePoison("chicken_raw",
                1, 3, 10, FoodType.PROTEIN, 1));
        register(muttonRaw = new ItemEdibleDecayablePoison("mutton_raw",
                2, 4, 7, FoodType.PROTEIN, 2));
        register(rabbitRaw = new ItemEdibleDecayablePoison("rabbit_raw",
                1, 2, 15, FoodType.PROTEIN, 2));
        register(fishRaw = new ItemEdibleDecayablePoison("fish_raw",
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
                3, 3, 10, FoodType.PROTEIN, 1));
        register(fishCooked = new ItemEdibleDecayable("fish_cooked",
                3, 3, 10, FoodType.PROTEIN, 1));
        
        register(bean = new ItemEdibleDecayableSeed("bean",
                2, 2F, 10, ModBlocks.bean, FoodType.FRUITVEG, 4));
        register(pepper = new ItemEdibleDecayableSeed("pepper",
                3, 3F, 6, ModBlocks.pepper, FoodType.FRUITVEG, 4));
        register(tomato = new ItemEdibleDecayableSeed("tomato",
                3, 3F, 7, ModBlocks.tomato, FoodType.FRUITVEG, 4));
        register(berry = new ItemEdibleDecayableSeed("berry",
                1, 1F, 20, ModBlocks.berry, FoodType.FRUITVEG, 4));
        
        register(potato = new ItemEdibleDecayableSeed("potato",
                7, 7F, 5, ModBlocks.potato, FoodType.CARBS, 8));
        register(carrot = new ItemEdibleDecayableSeed("carrot",
                3, 3F, 7, ModBlocks.carrot, FoodType.FRUITVEG, 8,
                EntityPig.class, EntityRabbit.class));
        register(beetroot = new ItemEdibleDecayableSeed("beetroot",
                3, 3F, 6, ModBlocks.beetroot, FoodType.FRUITVEG, 8));
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
        
        register(bedLeaf = new ItemBedPlain("bed_leaf",
                ModBlocks.bedLeaf), true);
        register(bedCotton = new ItemBedBreakable("bed_cotton",
                ModBlocks.bedCotton, 20));
        register(bedWool = new ItemBedBreakable("bed_wool",
                ModBlocks.bedWool, 20));
        register(bedSimple = new ItemBedPlain("bed_simple",
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

        register(amethyst = new ItemJj("amethyst", 64));
        register(fireopal = new ItemJj("fireopal", 64));
        register(ruby = new ItemJj("ruby", 61));
        register(sapphire = new ItemJj("sapphire", 64));

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

        register(dirt = new ItemJj("dirt", 1));
        register(mudbricks = new ItemJj("mudbricks"));
        register(peatDry = new ItemJj("peat_dry"));
        register(peatWet = new ItemJj("peat_wet"));
        
        register(salt = new ItemJj("salt"));
        register(chalk = new ItemJj("chalk"));
        register(clay = new ItemJj("clay", 9));

        register(oreTin = new ItemJj("ore_tin"));
        register(oreCopper = new ItemJj("ore_copper"));
        register(oreIron = new ItemJj("ore_iron"));
        register(oreSilver = new ItemJj("ore_silver"));
        register(oreGold = new ItemJj("ore_gold"));
        
        register(stoneRough = new ItemJj("stone_rough", 1));
        register(stoneDressed = new ItemJj("stone_dressed", 1));

        register(potClay = new ItemJj("pot_clay"));
        register(potMetal = new ItemJj("pot_metal"));

        register(craftingCandlemaker = new ItemCraftingCandlemaker(), true);
        register(craftingForge = new ItemCraftingForge(), true);
        register(craftingMason = new ItemCraftingMason(), true);
        register(craftingTextiles = new ItemCraftingTextiles(), true);
        register(craftingWoodworking = new ItemCraftingWoodworking(), true);
        register(craftingSawpit = new ItemCraftingSawpit(), true);
        register(craftingArmourer = new ItemCraftingArmourer(), true);
        
        register(furnaceClay = new ItemFurnaceClay(), true);
        register(furnaceStone = new ItemFurnaceStone(), true);

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

        register(arrowBronze = new ItemArrowBronze());
        register(arrowCopper = new ItemArrowCopper());
        register(arrowFlint = new ItemArrowFlint());
        register(arrowSteel = new ItemArrowSteel());
        register(arrowWood = new ItemArrowWood());

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

        register(spearBronze = new ItemSpearBronze());
        register(spearCopper = new ItemSpearCopper());
        register(spearFlint = new ItemSpearFlint());
        register(spearSteel = new ItemSpearSteel());
        register(spearWood = new ItemSpearWood());

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
        
        register(wallBrick = new ItemWall("wall_brick", 2,
                ModBlocks.wallBrickSingle, ModBlocks.wallBrickDouble));
        register(wallMud = new ItemWall("wall_mud", 2,
                ModBlocks.wallMudSingle, ModBlocks.wallMudDouble));
        register(wallRough = new ItemWall("wall_rough", 2,
                ModBlocks.wallRoughSingle, ModBlocks.wallRoughDouble));
        register(wallStone = new ItemWall("wall_stone", 2,
                ModBlocks.wallStoneSingle, ModBlocks.wallStoneDouble));
        register(wallLog = new ItemWall("wall_log", 2,
                ModBlocks.wallLogSingle, ModBlocks.wallLogDouble));
        
        register(doorPole = new ItemDoor(ModBlocks.doorPole, "door_pole"),
                true);
        register(doorWood = new ItemDoor(ModBlocks.doorWood, "door_wood"),
                true);
        
        register(beamLong = new ItemBeam("beam_long", 4, 8));
        register(beamShort = new ItemBeam("beam_short", 2, 4));
        
        register(floorPole = new ItemFloor(6, EnumFloor.POLE));
        register(floorWood = new ItemFloor(6, EnumFloor.WOOD));
        
        register(slabStone = new ItemSlab("slab_stone", 2,
                ModBlocks.slabStoneSingle, ModBlocks.slabStoneDouble));
        register(slabBrick = new ItemSlab("slab_brick", 2,
                ModBlocks.slabBrickSingle, ModBlocks.slabBrickDouble));
        
        register(sand = new ItemJj("sand"));
        
        Items.STICK.setMaxStackSize(12);
        Items.BONE.setMaxStackSize(6);
        Items.FLINT.setMaxStackSize(6);
        Items.LEATHER.setMaxStackSize(3);
        Items.COAL.setMaxStackSize(1);
        Items.RABBIT_HIDE.setMaxStackSize(4);
        Items.REEDS.setMaxStackSize(5);
        Items.CLAY_BALL.setMaxStackSize(1);
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
        
        ModelLoader.setCustomModelResourceLocation(item, 0,
                new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }
}

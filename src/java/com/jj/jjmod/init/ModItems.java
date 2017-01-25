package com.jj.jjmod.init;

import java.util.Set;
import com.google.common.collect.Sets;
import com.jj.jjmod.items.ItemApparel;
import com.jj.jjmod.items.ItemArrowBronze;
import com.jj.jjmod.items.ItemArrowCopper;
import com.jj.jjmod.items.ItemArrowFlint;
import com.jj.jjmod.items.ItemArrowSteel;
import com.jj.jjmod.items.ItemArrowWood;
import com.jj.jjmod.items.ItemAxe;
import com.jj.jjmod.items.ItemBeam;
import com.jj.jjmod.items.ItemBedBreakableAbstract;
import com.jj.jjmod.items.ItemBedPlainAbstract;
import com.jj.jjmod.items.ItemBowCrude;
import com.jj.jjmod.items.ItemBucket;
import com.jj.jjmod.items.ItemCraftingCandlemaker;
import com.jj.jjmod.items.ItemCraftingClayworks;
import com.jj.jjmod.items.ItemCraftingForge;
import com.jj.jjmod.items.ItemCraftingMason;
import com.jj.jjmod.items.ItemCraftingSawpit;
import com.jj.jjmod.items.ItemCraftingTextiles;
import com.jj.jjmod.items.ItemCraftingWoodworking;
import com.jj.jjmod.items.ItemDoor;
import com.jj.jjmod.items.ItemEdible;
import com.jj.jjmod.items.ItemEdibleSeed;
import com.jj.jjmod.items.ItemFloor;
import com.jj.jjmod.items.ItemFurnaceClay;
import com.jj.jjmod.items.ItemFurnaceStone;
import com.jj.jjmod.items.ItemHoe;
import com.jj.jjmod.items.ItemHuntingknife;
import com.jj.jjmod.items.ItemMachete;
import com.jj.jjmod.items.ItemNew;
import com.jj.jjmod.items.ItemPickaxe;
import com.jj.jjmod.items.ItemRice;
import com.jj.jjmod.items.ItemSeed;
import com.jj.jjmod.items.ItemShears;
import com.jj.jjmod.items.ItemShield;
import com.jj.jjmod.items.ItemShovel;
import com.jj.jjmod.items.ItemSickle;
import com.jj.jjmod.items.ItemSlab;
import com.jj.jjmod.items.ItemSpearBronze;
import com.jj.jjmod.items.ItemSpearCopper;
import com.jj.jjmod.items.ItemSpearFlint;
import com.jj.jjmod.items.ItemSpearSteel;
import com.jj.jjmod.items.ItemSpearWood;
import com.jj.jjmod.items.ItemSword;
import com.jj.jjmod.items.ItemWall;
import com.jj.jjmod.items.ItemWoolknife;
import com.jj.jjmod.tileentities.TEBeam.EnumFloor;
import com.jj.jjmod.utilities.EquipMaterial;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModItems {
    
    public static final Set<Item> MOD_ITEMS = Sets.newHashSet();
    
    public static final Set<Item> FRUITVEG = Sets.newHashSet();
    public static final Set<Item> PROTEIN = Sets.newHashSet();
    public static final Set<Item> CARBS = Sets.newHashSet();
    
    public static ItemEdible banana;
    public static ItemEdible pear;
    public static ItemEdible orange;
    public static ItemEdible honey;
    public static ItemEdible wheatBoiled;
    public static ItemEdible riceBoiled;
    public static ItemEdible chickpeasBoiled;
    public static ItemEdible pumpkin;
    public static ItemEdible melon;
    
    public static ItemEdibleSeed bean;
    public static ItemEdibleSeed onion;
    public static ItemEdibleSeed pepper;
    public static ItemEdibleSeed tomato;
    public static ItemEdibleSeed berry;
    
    public static ItemEdibleSeed carrot;
    public static ItemEdibleSeed potato;
    public static ItemEdibleSeed beetroot;
    public static ItemEdibleSeed seedPumpkin;
        
    public static ItemRice rice;

    public static ItemNew cotton;
    public static ItemNew twineHemp;
    
    public static ItemSeed chickpeas;
    public static ItemSeed cuttingCotton;
    public static ItemSeed cuttingHemp;
    
    public static ItemSeed wheat;
    public static ItemSeed seedMelon;

    public static ItemBedPlainAbstract bedLeaf;
    public static ItemBedBreakableAbstract bedCotton;
    public static ItemBedBreakableAbstract bedWool;
    public static ItemBedPlainAbstract bedSimple;

    public static ItemBucket bucketEmpty;
    public static ItemBucket bucketWater;
    public static ItemBucket bucketTar;
    public static ItemNew bucketMilk;

    public static ItemNew backpack;
    public static ItemNew yoke;

    public static ItemNew amethyst;
    public static ItemNew fireopal;
    public static ItemNew ruby;
    public static ItemNew sapphire;

    public static ItemNew beeswax;
    public static ItemNew fur;
    public static ItemNew skinBear;
    public static ItemNew skinCow;
    public static ItemNew skinPig;
    public static ItemNew skinSheep;
    public static ItemNew skinWolf;
    public static ItemNew tallow;
    public static ItemNew honeycomb;
    
    public static ItemNew rot;

    public static ItemNew ingotCopper;
    public static ItemNew ingotSilver;
    public static ItemNew ingotSteel;
    public static ItemNew ingotTin;

    public static ItemNew leafpile;
    public static ItemNew pole;
    public static ItemNew log;
    public static ItemNew thicklog;

    public static ItemNew claybricksDry;
    public static ItemNew claybricksWet;
    public static ItemNew dirt;
    public static ItemNew mudbricksDry;
    public static ItemNew mudbricksWet;
    public static ItemNew peatDry;
    public static ItemNew peatWet;
    
    public static ItemNew salt;
    public static ItemNew chalk;
    
    public static ItemNew oreTin;
    public static ItemNew oreCopper;
    public static ItemNew oreIron;
    public static ItemNew oreSilver;
    public static ItemNew oreGold;

    public static ItemNew stoneLoose;
    public static ItemNew stoneRough;
    public static ItemNew stoneDressed;

    public static ItemNew potClay;
    public static ItemNew potMetal;

    public static ItemCraftingCandlemaker craftingCandlemaker;
    public static ItemCraftingClayworks craftingClayworks;
    public static ItemCraftingForge craftingForge;
    public static ItemCraftingMason craftingMason;
    public static ItemCraftingTextiles craftingTextiles;
    public static ItemCraftingWoodworking craftingWoodworking;
    public static ItemCraftingSawpit craftingSawpit;
    //public static ItemCraftingArmourer craftingArmourer;
    
    public static ItemFurnaceClay furnaceClay;
    public static ItemFurnaceStone furnaceStone;

    public static ItemNew arrowheadBronze;
    public static ItemNew arrowheadCopper;
    public static ItemNew arrowheadFlint;
    public static ItemNew arrowheadSteel;

    public static ItemNew axeheadBronze;
    public static ItemNew axeheadCopper;
    public static ItemNew axeheadFlint;
    public static ItemNew axeheadSteel;

    public static ItemNew hoeheadBronze;
    public static ItemNew hoeheadCopper;
    public static ItemNew hoeheadSteel;

    public static ItemNew knifebladeBronze;
    public static ItemNew knifebladeCopper;
    public static ItemNew knifebladeSteel;

    public static ItemNew machetebladeBronze;
    public static ItemNew machetebladeCopper;
    public static ItemNew machetebladeSteel;

    public static ItemNew pickheadBronze;
    public static ItemNew pickheadCopper;
    public static ItemNew pickheadFlint;
    public static ItemNew pickheadSteel;

    public static ItemNew sicklebladeBronze;
    public static ItemNew sicklebladeCopper;
    public static ItemNew sicklebladeSteel;

    public static ItemNew shovelheadBronze;
    public static ItemNew shovelheadCopper;
    public static ItemNew shovelheadSteel;

    public static ItemNew spearheadBronze;
    public static ItemNew spearheadCopper;
    public static ItemNew spearheadFlint;
    public static ItemNew spearheadSteel;

    public static ItemNew swordbladeBronze;
    public static ItemNew swordbladeCopper;
    public static ItemNew swordbladeSteel;

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

    public static ItemShears shearsBronze;
    public static ItemShears shearsCopper;
    public static ItemShears shearsSteel;

    public static ItemWoolknife woolknifeFlint;

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
    
    public static ItemApparel steelmailHead;
    public static ItemApparel steelmailChest;
    public static ItemApparel steelmailLegs;
    public static ItemApparel steelmailFeet;
    
    public static ItemApparel steelplateHead;
    public static ItemApparel steelplateChest;
    public static ItemApparel steelplateLegs;
    public static ItemApparel steelplateFeet;

    public static ItemBowCrude bowCrude;
    
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

    public static void preInit() {

        register(banana = new ItemEdible("banana", 4, 6F, 5), FoodType.FOOD_FRUITVEG);
        register(pear = new ItemEdible("pear", 4, 4F, 5), FoodType.FOOD_FRUITVEG);
        register(orange = new ItemEdible("orange", 3, 3F, 6), FoodType.FOOD_FRUITVEG);
        register(honey = new ItemEdible("honey", 4, 1F, 8), FoodType.FOOD_CARBS);
        register(wheatBoiled = new ItemEdible("wheat_boiled", 4, 2F, 10), FoodType.FOOD_CARBS);
        register(riceBoiled = new ItemEdible("rice_boiled", 4, 2, 12), FoodType.FOOD_CARBS);
        register(chickpeasBoiled = new ItemEdible("chickpeas_boiled", 3, 3, 10), FoodType.FOOD_PROTEIN);
        register(pumpkin = new ItemEdible("pumpkin", 10, 10, 2), FoodType.FOOD_FRUITVEG);
        register(melon = new ItemEdible("melon", 12, 12, 2), FoodType.FOOD_FRUITVEG);
        
        register(bean = new ItemEdibleSeed("bean", 2, 2F, 10, ModBlocks.bean), FoodType.FOOD_FRUITVEG);
        register(pepper = new ItemEdibleSeed("pepper", 3, 3F, 6, ModBlocks.pepper), FoodType.FOOD_FRUITVEG);
        register(tomato = new ItemEdibleSeed("tomato", 3, 3F, 7, ModBlocks.tomato), FoodType.FOOD_FRUITVEG);
        register(berry = new ItemEdibleSeed("berry", 1, 1F, 20, ModBlocks.berry), FoodType.FOOD_FRUITVEG);
        
        register(potato = new ItemEdibleSeed("potato", 1, 1F, 1, ModBlocks.potato), FoodType.FOOD_CARBS);
        register(carrot = new ItemEdibleSeed("carrot", 3, 3F, 7, ModBlocks.carrot), FoodType.FOOD_FRUITVEG);
        register(beetroot = new ItemEdibleSeed("beetroot", 3, 3F, 6, ModBlocks.beetroot), FoodType.FOOD_FRUITVEG);
        register(seedPumpkin = new ItemEdibleSeed("seed_pumpkin", 1, 1, 15, ModBlocks.pumpkin), FoodType.FOOD_PROTEIN);
        
        register(rice = new ItemRice());
        
        register(cotton = new ItemNew("cotton", 6));
        register(twineHemp = new ItemNew("twine_hemp", 3));
        
        register(chickpeas = new ItemSeed("chickpeas", 1, ModBlocks.chickpea));
        register(cuttingCotton = new ItemSeed("cutting_cotton", 1, ModBlocks.cotton));
        register(cuttingHemp = new ItemSeed("cutting_hemp", 1, ModBlocks.hemp));

        register(wheat = new ItemSeed("wheat", 10, ModBlocks.wheat));
        register(seedMelon = new ItemSeed("seed_melon", 15, ModBlocks.melon));
        
        register(bedLeaf = new ItemBedPlainAbstract("bed_leaf",
                ModBlocks.bedLeaf), true);
        register(bedCotton = new ItemBedBreakableAbstract("bed_cotton",
                ModBlocks.bedCotton, 20), true);
        register(bedWool = new ItemBedBreakableAbstract("bed_wool",
                ModBlocks.bedWool, 20), true);
        register(bedSimple = new ItemBedPlainAbstract("bed_simple",
                ModBlocks.bedSimple), true);

        register(bucketEmpty = new ItemBucket("bucket_empty",
                Blocks.AIR, () -> bucketEmpty,
                () -> bucketWater, () -> bucketTar), true);
        register(bucketWater = new ItemBucket("bucket_water",
                Blocks.FLOWING_WATER, () -> bucketEmpty,
                () -> bucketWater, () -> bucketTar), true);
        register(bucketTar = new ItemBucket("bucket_tar",
                ModLiquids.tarBlock, () -> bucketEmpty,
                () -> bucketWater, () -> bucketTar), true);
        register(bucketMilk = new ItemNew("bucket_milk"));
        
        register(backpack = new ItemNew("backpack"));
        register(yoke = new ItemNew("yoke"));

        register(amethyst = new ItemNew("amethyst", 64));
        register(fireopal = new ItemNew("fireopal", 64));
        register(ruby = new ItemNew("ruby", 64));
        register(sapphire = new ItemNew("sapphire", 64));

        register(beeswax = new ItemNew("beeswax"));
        register(skinBear = new ItemNew("skin_bear", 4));
        register(skinCow = new ItemNew("skin_cow", 4));
        register(skinPig = new ItemNew("skin_pig", 4));
        register(skinSheep = new ItemNew("skin_sheep", 4));
        register(skinWolf = new ItemNew("skin_wolf", 4));
        register(tallow = new ItemNew("tallow"));
        register(honeycomb = new ItemNew("honeycomb"));
        
        register(rot = new ItemNew("rot", 10));

        register(ingotCopper = new ItemNew("ingot_copper", 2));
        register(ingotSilver = new ItemNew("ingot_silver", 2));
        register(ingotSteel = new ItemNew("ingot_steel", 2));
        register(ingotTin = new ItemNew("ingot_tin", 2));

        register(leafpile = new ItemNew("leafpile", 2));
        register(pole = new ItemNew("pole", 4));
        register(log = new ItemNew("log"));
        register(thicklog = new ItemNew("thicklog"));

        register(claybricksDry = new ItemNew("claybricks_dry"));
        register(claybricksWet = new ItemNew("claybricks_wet"));
        register(dirt = new ItemNew("dirt", 4));
        register(mudbricksDry = new ItemNew("mudbricks_dry"));
        register(mudbricksWet = new ItemNew("mudbricks_wet"));
        register(peatDry = new ItemNew("peat_dry"));
        register(peatWet = new ItemNew("peat_wet"));
        
        register(salt = new ItemNew("salt"));
        register(chalk = new ItemNew("chalk"));        

        register(oreTin = new ItemNew("ore_tin"));
        register(oreCopper = new ItemNew("ore_copper"));
        register(oreIron = new ItemNew("ore_iron"));
        register(oreSilver = new ItemNew("ore_silver"));
        register(oreGold = new ItemNew("ore_gold"));
        
        register(stoneLoose = new ItemNew("stone_loose", 4));
        register(stoneRough = new ItemNew("stone_rough", 4));
        register(stoneDressed = new ItemNew("stone_dressed", 4));

        register(potClay = new ItemNew("pot_clay"));
        register(potMetal = new ItemNew("pot_metal"));

        register(craftingCandlemaker = new ItemCraftingCandlemaker());
        register(craftingClayworks = new ItemCraftingClayworks(), true);
        register(craftingForge = new ItemCraftingForge(), true);
        register(craftingMason = new ItemCraftingMason(), true);
        register(craftingTextiles = new ItemCraftingTextiles(), true);
        register(craftingWoodworking = new ItemCraftingWoodworking(), true);
        register(craftingSawpit = new ItemCraftingSawpit(), true);
        //register(craftingArmourer = new ItemCraftingArmourer(), true);
        
        register(furnaceClay = new ItemFurnaceClay(), true);
        register(furnaceStone = new ItemFurnaceStone(), true);

        register(arrowheadBronze = new ItemNew("arrowhead_bronze", 12));
        register(arrowheadCopper = new ItemNew("arrowhead_copper", 12));
        register(arrowheadFlint = new ItemNew("arrowhead_flint", 12));
        register(arrowheadSteel = new ItemNew("arrowhead_steel", 12));

        register(axeheadBronze = new ItemNew("axehead_bronze", 4));
        register(axeheadCopper = new ItemNew("axehead_copper", 4));
        register(axeheadFlint = new ItemNew("axehead_flint", 4));
        register(axeheadSteel = new ItemNew("axehead_steel", 4));

        register(hoeheadBronze = new ItemNew("hoehead_bronze"));
        register(hoeheadCopper = new ItemNew("hoehead_copper"));
        register(hoeheadSteel = new ItemNew("hoehead_steel"));

        register(knifebladeBronze = new ItemNew("knifeblade_bronze", 2));
        register(knifebladeCopper = new ItemNew("knifeblade_copper", 2));
        register(knifebladeSteel = new ItemNew("knifeblade_steel", 2));

        register(machetebladeBronze = new ItemNew("macheteblade_bronze"));
        register(machetebladeCopper = new ItemNew("macheteblade_copper"));
        register(machetebladeSteel = new ItemNew("macheteblade_steel"));

        register(pickheadBronze = new ItemNew("pickhead_bronze"));
        register(pickheadCopper = new ItemNew("pickhead_copper"));
        register(pickheadFlint = new ItemNew("pickhead_flint"));
        register(pickheadSteel = new ItemNew("pickhead_steel"));

        register(sicklebladeBronze = new ItemNew("sickleblade_bronze"));
        register(sicklebladeCopper = new ItemNew("sickleblade_copper"));
        register(sicklebladeSteel = new ItemNew("sickleblade_steel"));

        register(shovelheadBronze = new ItemNew("shovelhead_bronze"));
        register(shovelheadCopper = new ItemNew("shovelhead_copper"));
        register(shovelheadSteel = new ItemNew("shovelhead_steel"));

        register(spearheadBronze = new ItemNew("spearhead_bronze", 2));
        register(spearheadCopper = new ItemNew("spearhead_copper", 2));
        register(spearheadFlint = new ItemNew("spearhead_flint", 2));
        register(spearheadSteel = new ItemNew("spearhead_steel", 2));

        register(swordbladeBronze = new ItemNew("swordblade_bronze"));
        register(swordbladeCopper = new ItemNew("swordblade_copper"));
        register(swordbladeSteel = new ItemNew("swordblade_steel"));

        register(arrowBronze = new ItemArrowBronze());
        register(arrowCopper = new ItemArrowCopper());
        register(arrowFlint = new ItemArrowFlint());
        register(arrowSteel = new ItemArrowSteel());
        register(arrowWood = new ItemArrowWood());

        register(hoeBronze = new ItemHoe("hoe_bronze", EquipMaterial.BRONZE_TOOL));
        register(hoeCopper = new ItemHoe("hoe_copper", EquipMaterial.COPPER_TOOL));
        register(hoeSteel = new ItemHoe("hoe_steel", EquipMaterial.STEEL_TOOL));
        register(hoeAntler = new ItemHoe("hoe_antler", EquipMaterial.ANTLER_TOOL));

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
        register(shovelWood = new ItemShovel("shovel_wood", EquipMaterial.WOOD_TOOL));

        register(axeBronze = new ItemAxe("axe_bronze", EquipMaterial.BRONZE_TOOL));
        register(axeCopper = new ItemAxe("axe_copper", EquipMaterial.COPPER_TOOL));
        register(axeFlint = new ItemAxe("axe_flint", EquipMaterial.FLINT_TOOL));
        register(axeSteel = new ItemAxe("axe_steel", EquipMaterial.STEEL_TOOL));

        register(huntingknifeBronze = new ItemHuntingknife("huntingknife_bronze",
                EquipMaterial.BRONZE_TOOL));
        register(huntingknifeCopper = new ItemHuntingknife("huntingknife_copper",
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

        register(shearsBronze = new ItemShears("shears_bronze",
                EquipMaterial.BRONZE_TOOL));
        register(shearsCopper = new ItemShears("shears_copper",
                EquipMaterial.COPPER_TOOL));
        register(shearsSteel = new ItemShears("shears_steel",
                EquipMaterial.STEEL_TOOL));

        register(woolknifeFlint = new ItemWoolknife("woolknife_flint",
                EquipMaterial.FLINT_TOOL));

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
                EquipMaterial.COTTON_APPAREL, EntityEquipmentSlot.HEAD));
        register(cottonChest = new ItemApparel("cotton_chest",
                EquipMaterial.COTTON_APPAREL, EntityEquipmentSlot.CHEST));
        register(cottonLegs = new ItemApparel("cotton_legs",
                EquipMaterial.COTTON_APPAREL, EntityEquipmentSlot.LEGS));
        register(cottonFeet = new ItemApparel("cotton_feet",
                EquipMaterial.COTTON_APPAREL, EntityEquipmentSlot.FEET));
        
        register(woolHead = new ItemApparel("wool_head",
                EquipMaterial.WOOL_APPAREL, EntityEquipmentSlot.HEAD));
        register(woolChest = new ItemApparel("wool_chest",
                EquipMaterial.WOOL_APPAREL, EntityEquipmentSlot.CHEST));
        register(woolLegs = new ItemApparel("wool_legs",
                EquipMaterial.WOOL_APPAREL, EntityEquipmentSlot.LEGS));
        register(woolFeet = new ItemApparel("wool_feet",
                EquipMaterial.WOOL_APPAREL, EntityEquipmentSlot.FEET));
        
        register(steelmailHead = new ItemApparel("steelmail_head",
                EquipMaterial.STEELMAIL_APPAREL, EntityEquipmentSlot.HEAD));
        register(steelmailChest = new ItemApparel("steelmail_chest",
                EquipMaterial.STEELMAIL_APPAREL, EntityEquipmentSlot.CHEST));
        register(steelmailLegs = new ItemApparel("steelmail_legs",
                EquipMaterial.STEELMAIL_APPAREL, EntityEquipmentSlot.LEGS));
        register(steelmailFeet = new ItemApparel("steelmail_feet",
                EquipMaterial.STEELMAIL_APPAREL, EntityEquipmentSlot.FEET));
        
        register(steelplateHead = new ItemApparel("steelplate_head",
                EquipMaterial.STEELPLATE_APPAREL, EntityEquipmentSlot.HEAD));
        register(steelplateChest = new ItemApparel("steelplate_chest",
                EquipMaterial.STEELPLATE_APPAREL, EntityEquipmentSlot.CHEST));
        register(steelplateLegs = new ItemApparel("steelplate_legs",
                EquipMaterial.STEELPLATE_APPAREL, EntityEquipmentSlot.LEGS));
        register(steelplateFeet = new ItemApparel("steelplate_feet",
                EquipMaterial.STEELPLATE_APPAREL, EntityEquipmentSlot.FEET));

        register(bowCrude = new ItemBowCrude());
        
        register(wallBrick = new ItemWall("wall_brick", 1,
                ModBlocks.wallBrickSingle, ModBlocks.wallBrickDouble));
        register(wallMud = new ItemWall("wall_mud", 1,
                ModBlocks.wallMudSingle, ModBlocks.wallMudDouble));
        register(wallRough = new ItemWall("wall_rough", 1,
                ModBlocks.wallRoughSingle, ModBlocks.wallRoughDouble));
        register(wallStone = new ItemWall("wall_stone", 1,
                ModBlocks.wallStoneSingle, ModBlocks.wallStoneDouble));
        register(wallLog = new ItemWall("wall_log", 1,
                ModBlocks.wallLogSingle, ModBlocks.wallLogDouble));
        
        register(doorPole = new ItemDoor(ModBlocks.doorPole, "door_pole"), true);
        register(doorWood = new ItemDoor(ModBlocks.doorWood, "door_wood"), true);
        
        register(beamLong = new ItemBeam("beam_long", 4, 8));
        register(beamShort = new ItemBeam("beam_short", 2, 4));
        
        register(floorPole = new ItemFloor(3, EnumFloor.POLE));
        register(floorWood = new ItemFloor(4, EnumFloor.WOOD));
        
        register(slabStone = new ItemSlab("slab_stone", 1,
                ModBlocks.slabStoneSingle, ModBlocks.slabStoneDouble));
        register(slabBrick = new ItemSlab("slab_brick", 1,
                ModBlocks.slabBrickSingle, ModBlocks.slabBrickDouble));
        
    }
    
    public static void preInitClient() {
        
        for (Item item : MOD_ITEMS) {
            
            model(item);
        }
    }
    
    private static void register(Item item) {
        
        register(item, false);
    }
    
    private static void register(Item item, FoodType foodType) {
        
        register(item, false);
        
        switch (foodType) {
            
            case FOOD_FRUITVEG: {
                
                FRUITVEG.add(item);
                break;
            }
            
            case FOOD_PROTEIN: {
                
                PROTEIN.add(item);
                break;
            }
            
            case FOOD_CARBS: {
                
                CARBS.add(item);
                break;
            }
        }
    }

    private static void register(Item item, boolean isOffhandOnly) {

        GameRegistry.register(item);
        
        if (isOffhandOnly) {
            
            ModBlocks.OFFHAND_ONLY.add(item);
        }

        MOD_ITEMS.add(item);
        
       // ModelLoader.setCustomModelResourceLocation(item, 0,
       //         new ModelResourceLocation(item.getRegistryName(),
       //         "inventory"));
    }
    
    private static void model(Item item) {
        
        ModelLoader.setCustomModelResourceLocation(item, 0,
                new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }
    
    public enum FoodType {
        
        FOOD_CARBS, FOOD_PROTEIN, FOOD_FRUITVEG;
    }
}

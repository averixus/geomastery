package jayavery.geomastery.main;

import java.util.Set;
import com.google.common.collect.Sets;
import jayavery.geomastery.blocks.BlockSlab;
import jayavery.geomastery.blocks.BlockVault;
import jayavery.geomastery.blocks.BlockWallComplex;
import jayavery.geomastery.blocks.BlockWallHeaping;
import jayavery.geomastery.entities.projectile.EntityProjectile;
import jayavery.geomastery.entities.projectile.EntitySpearBronze;
import jayavery.geomastery.entities.projectile.EntitySpearCopper;
import jayavery.geomastery.entities.projectile.EntitySpearFlint;
import jayavery.geomastery.entities.projectile.EntitySpearSteel;
import jayavery.geomastery.entities.projectile.EntitySpearWood;
import jayavery.geomastery.items.ItemApparel;
import jayavery.geomastery.items.ItemArrow;
import jayavery.geomastery.items.ItemAxe;
import jayavery.geomastery.items.ItemBed;
import jayavery.geomastery.items.ItemBlockplacer;
import jayavery.geomastery.items.ItemBow;
import jayavery.geomastery.items.ItemCarcassDecayable;
import jayavery.geomastery.items.ItemCompost;
import jayavery.geomastery.items.ItemEdible;
import jayavery.geomastery.items.ItemEdibleDecayable;
import jayavery.geomastery.items.ItemEdibleSeed;
import jayavery.geomastery.items.ItemEdibleSeedDecayable;
import jayavery.geomastery.items.ItemHoe;
import jayavery.geomastery.items.ItemHuntingknife;
import jayavery.geomastery.items.ItemMachete;
import jayavery.geomastery.items.ItemPickaxe;
import jayavery.geomastery.items.ItemRice;
import jayavery.geomastery.items.ItemSeed;
import jayavery.geomastery.items.ItemShears;
import jayavery.geomastery.items.ItemShield;
import jayavery.geomastery.items.ItemShovel;
import jayavery.geomastery.items.ItemSickle;
import jayavery.geomastery.items.ItemSimple;
import jayavery.geomastery.items.ItemSpear;
import jayavery.geomastery.items.ItemSword;
import jayavery.geomastery.tileentities.TEBeam.EnumFloor;
import jayavery.geomastery.tileentities.TECraftingArmourer.EnumPartArmourer;
import jayavery.geomastery.tileentities.TECraftingCandlemaker.EnumPartCandlemaker;
import jayavery.geomastery.tileentities.TECraftingForge.EnumPartForge;
import jayavery.geomastery.tileentities.TECraftingMason.EnumPartMason;
import jayavery.geomastery.tileentities.TECraftingSawpit.EnumPartSawpit;
import jayavery.geomastery.tileentities.TECraftingTextiles.EnumPartTextiles;
import jayavery.geomastery.tileentities.TECraftingWoodworking.EnumPartWoodworking;
import jayavery.geomastery.tileentities.TEFurnaceClay.EnumPartClay;
import jayavery.geomastery.tileentities.TEFurnaceStone.EnumPartStone;
import jayavery.geomastery.utilities.EquipMaterial;
import jayavery.geomastery.utilities.FoodType;
import net.minecraft.block.SoundType;
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
import net.minecraftforge.common.EnumPlantType;

public class GeoItems {
    
    /** All new items. */
    public static final Set<Item> ITEMS = Sets.newHashSet();
    
    // Non-decaying normal foods
    public static Item honey = make(new ItemEdible("honey", 4, 1F, 8, FoodType.CARBS));
    public static Item bread = make(new ItemEdible("bread", 4, 2, 10, FoodType.CARBS));
    public static Item sugar = make(new ItemEdible("sugar", 5, 0, 10, FoodType.CARBS));

    // Decaying foods
    public static Item banana =             make(new ItemEdibleDecayable("banana", 4, 6F, 5, FoodType.FRUITVEG, 2));
    public static Item pear =               make(new ItemEdibleDecayable("pear", 4, 4F, 5, FoodType.FRUITVEG, 2));
    public static Item orange =             make(new ItemEdibleDecayable("orange", 3, 3F, 6, FoodType.FRUITVEG, 4));
    public static Item wheatBoiled =        make(new ItemEdibleDecayable("wheat_boiled", 4, 2F, 10, FoodType.CARBS, 1));
    public static Item riceBoiled =         make(new ItemEdibleDecayable("rice_boiled", 4, 2, 12, FoodType.CARBS, 1));
    public static Item chickpeasBoiled =    make(new ItemEdibleDecayable("chickpeas_boiled", 3, 3, 10, FoodType.PROTEIN, 1));
    public static Item pumpkin =            make(new ItemEdibleDecayable("pumpkin", 10, 10, 2, FoodType.FRUITVEG, 4));
    public static Item melon =              make(new ItemEdibleDecayable("melon", 12, 12, 2, FoodType.FRUITVEG, 2));
    public static Item apple =              make(new ItemEdibleDecayable("apple", 4, 4, 5, FoodType.FRUITVEG, 2));
    public static Item potatoCooked =       make(new ItemEdibleDecayable("potato_cooked", 8, 6, 5, FoodType.CARBS, 2));
    public static Item egg =                make(new ItemEdibleDecayable("egg", 5, 5, 6, FoodType.PROTEIN, 4));
    public static Item beefRaw =            make(new ItemEdibleDecayable("beef_raw", 3, 6, 5, FoodType.PROTEIN, 1));
    public static Item porkRaw =            make(new ItemEdibleDecayable("pork_raw", 2, 5, 6, FoodType.PROTEIN, 1));
    public static Item chickenRaw =         make(new ItemEdibleDecayable("chicken_raw", 1, 3, 10, FoodType.PROTEIN, 1));
    public static Item muttonRaw =          make(new ItemEdibleDecayable("mutton_raw", 2, 4, 7, FoodType.PROTEIN, 1));
    public static Item rabbitRaw =          make(new ItemEdibleDecayable("rabbit_raw", 1, 2, 15, FoodType.PROTEIN, 1));
    public static Item fishRaw =            make(new ItemEdibleDecayable("fish_raw", 1, 3, 10, FoodType.PROTEIN, 1));
    public static Item beefCooked =         make(new ItemEdibleDecayable("beef_cooked", 6, 6, 5, FoodType.PROTEIN, 2));
    public static Item porkCooked =         make(new ItemEdibleDecayable("pork_cooked", 5, 5, 6, FoodType.PROTEIN, 2));
    public static Item muttonCooked =       make(new ItemEdibleDecayable("mutton_cooked", 4, 4, 7, FoodType.PROTEIN, 2));
    public static Item rabbitCooked =       make(new ItemEdibleDecayable("rabbit_cooked", 2, 2, 15, FoodType.PROTEIN, 2));
    public static Item chickenCooked =      make(new ItemEdibleDecayable("chicken_cooked", 3, 3, 10, FoodType.PROTEIN, 2));
    public static Item fishCooked =         make(new ItemEdibleDecayable("fish_cooked", 3, 3, 10, FoodType.PROTEIN, 2));
    
    // Decaying carcasses
    public static ItemCarcassDecayable carcassCowpart = make(new ItemCarcassDecayable("carcass_cowpart", GeoBlocks.carcassCowpart), true);
    public static ItemCarcassDecayable carcassPig =     make(new ItemCarcassDecayable("carcass_pig", GeoBlocks.carcassPig));
    public static ItemCarcassDecayable carcassChicken = make(new ItemCarcassDecayable("carcass_chicken", GeoBlocks.carcassChicken));
    public static ItemCarcassDecayable carcassSheep =   make(new ItemCarcassDecayable("carcass_sheep", GeoBlocks.carcassSheep));
    public static ItemCarcassDecayable carcassRabbit =  make(new ItemCarcassDecayable("carcass_rabbit", GeoBlocks.carcassRabbit));
    
    // Non-decaying edible seeds
    public static Item seedPumpkin = make(new ItemEdibleSeed("seeds_pumpkin", 1, 1, 15, GeoBlocks.pumpkinCrop, FoodType.PROTEIN));
    
    // Decaying edible seeds
    public static Item mushroomRed =    make(new ItemEdibleSeedDecayable("mushroom_red", 4, 4, 4, GeoBlocks.mushroombabyRed, EnumPlantType.Cave, FoodType.FRUITVEG, 2));
    public static Item mushroomBrown =  make(new ItemEdibleSeedDecayable("mushroom_brown", 2, 2, 9, GeoBlocks.mushroombabyBrown, EnumPlantType.Cave, FoodType.FRUITVEG, 2));
    public static Item bean =           make(new ItemEdibleSeedDecayable("bean", 2, 2F, 10, GeoBlocks.bean, EnumPlantType.Crop, FoodType.FRUITVEG, 2));
    public static Item pepper =         make(new ItemEdibleSeedDecayable("pepper", 3, 3F, 6, GeoBlocks.pepper, EnumPlantType.Crop, FoodType.FRUITVEG, 2));
    public static Item tomato =         make(new ItemEdibleSeedDecayable("tomato", 3, 3F, 7, GeoBlocks.tomato, EnumPlantType.Crop, FoodType.FRUITVEG, 2));
    public static Item berry =          make(new ItemEdibleSeedDecayable("berry", 1, 1F, 20, GeoBlocks.berry, EnumPlantType.Crop, FoodType.FRUITVEG, 2));
    public static Item carrot =         make(new ItemEdibleSeedDecayable("potato", 7, 7F, 5, GeoBlocks.potato, EnumPlantType.Crop, FoodType.CARBS, 4));
    public static Item potato =         make(new ItemEdibleSeedDecayable("carrot", 3, 3F, 7, GeoBlocks.carrot, EnumPlantType.Crop, FoodType.FRUITVEG, 4, EntityPig.class, EntityRabbit.class));
    public static Item beetroot =       make(new ItemEdibleSeedDecayable("beetroot", 3, 3F, 6, GeoBlocks.beetroot, EnumPlantType.Crop, FoodType.FRUITVEG, 4));
        
    // Non-decaying inedible seeds
    public static Item chickpeas =      make(new ItemSeed("chickpeas", 10, GeoBlocks.chickpea));
    public static Item cuttingCotton =  make(new ItemSeed("cutting_cotton", 1, GeoBlocks.cotton));
    public static Item cuttingHemp =    make(new ItemSeed("cutting_hemp", 1, GeoBlocks.hemp));
    public static Item wheat =          make(new ItemSeed("wheat", 10, GeoBlocks.wheat, EntityCow.class, EntitySheep.class, EntityChicken.class));
    public static Item seedMelon =      make(new ItemSeed("seeds_melon", 15, GeoBlocks.melonCrop));
    
    // Rice
    public static Item rice = make(new ItemRice());

    // Beds
    public static Item bedLeaf =    make(new ItemBed("bed_leaf", GeoBlocks.bedLeaf), true);
    public static Item bedCotton =  make(new ItemBed("bed_cotton", GeoBlocks.bedCotton));
    public static Item bedWool =    make(new ItemBed("bed_wool", GeoBlocks.bedWool));
    public static Item bedSimple =  make(new ItemBed("bed_simple", GeoBlocks.bedSimple), true);

    // Carry items
    public static Item backpack =   make(new ItemSimple("backpack", 1, CreativeTabs.TRANSPORTATION));
    public static Item yoke =       make(new ItemSimple("yoke", 1, CreativeTabs.TRANSPORTATION));

    // Loose heaping blockplacers
    public static Item looseDirt =      make(new ItemBlockplacer.Heaping("loose_dirt", SoundType.GROUND, Blocks.DIRT));
    public static Item looseGravel =    make(new ItemBlockplacer.Heaping("loose_gravel", SoundType.GROUND, Blocks.GRAVEL));
    public static Item looseSand =      make(new ItemBlockplacer.Heaping("loose_sand", SoundType.SAND, Blocks.SAND));
    public static Item looseClay =      make(new ItemBlockplacer.Heaping("loose_clay", SoundType.GROUND, Blocks.CLAY));
    public static Item rubble =         make(new ItemBlockplacer.Heaping("rubble", SoundType.GROUND, GeoBlocks.rubble));
    
    // Multipart blockplacers
    public static Item craftingCandlemaker =    make(new ItemBlockplacer.Multipart<EnumPartCandlemaker>("crafting_candlemaker", EnumPartCandlemaker.FRONT, SoundType.WOOD), true);
    public static Item craftingForge =          make(new ItemBlockplacer.Multipart<EnumPartForge>("crafting_forge", EnumPartForge.FM, SoundType.METAL), true);
    public static Item craftingMason =          make(new ItemBlockplacer.Multipart<EnumPartMason>("crafting_mason", EnumPartMason.FM, SoundType.STONE), true);
    public static Item craftingTextiles =       make(new ItemBlockplacer.Multipart<EnumPartTextiles>("crafting_textiles", EnumPartTextiles.FRONT, SoundType.WOOD), true);
    public static Item craftingWoodworking =    make(new ItemBlockplacer.Multipart<EnumPartWoodworking>("crafting_woodworking", EnumPartWoodworking.FM, SoundType.WOOD), true);
    public static Item craftingSawpit =         make(new ItemBlockplacer.Multipart<EnumPartSawpit>("crafting_sawpit", EnumPartSawpit.F, SoundType.WOOD), true);
    public static Item craftingArmourer =       make(new ItemBlockplacer.Multipart<EnumPartArmourer>("crafting_armourer", EnumPartArmourer.M, SoundType.METAL), true);
    public static Item furnaceClay =            make(new ItemBlockplacer.Multipart<EnumPartClay>("furnace_clay", EnumPartClay.BL, SoundType.STONE), true);
    public static Item furnaceStone =           make(new ItemBlockplacer.Multipart<EnumPartStone>("furnace_stone", EnumPartStone.BM, SoundType.STONE), true);

    // Doubling blockplacers
    public static ItemBlockplacer.Doubling<BlockWallComplex> wallBrick = make(new ItemBlockplacer.Doubling<BlockWallComplex>("wall_brick", 2, SoundType.STONE, GeoBlocks.wallBrickSingle, GeoBlocks.wallBrickDouble)); 
    public static ItemBlockplacer.Doubling<BlockWallHeaping> wallMud =   make(new ItemBlockplacer.Doubling<BlockWallHeaping>("wall_mud", 2, SoundType.GROUND, GeoBlocks.wallMudSingle, GeoBlocks.wallMudDouble));
    public static ItemBlockplacer.Doubling<BlockWallHeaping> wallRough = make(new ItemBlockplacer.Doubling<BlockWallHeaping>("wall_rough", 2, SoundType.STONE, GeoBlocks.wallRoughSingle, GeoBlocks.wallRoughDouble));
    public static ItemBlockplacer.Doubling<BlockWallComplex> wallStone = make(new ItemBlockplacer.Doubling<BlockWallComplex>("wall_stone", 2, SoundType.STONE, GeoBlocks.wallStoneSingle, GeoBlocks.wallStoneDouble));
    
    public static ItemBlockplacer.Doubling<BlockSlab> slabStone = make(new ItemBlockplacer.Doubling<BlockSlab>("slab_stone", 2, SoundType.STONE, GeoBlocks.slabStoneSingle, GeoBlocks.slabStoneDouble));
    public static ItemBlockplacer.Doubling<BlockSlab> slabBrick = make(new ItemBlockplacer.Doubling<BlockSlab>("slab_brick", 2, SoundType.STONE, GeoBlocks.slabBrickSingle, GeoBlocks.slabBrickDouble));
    
    public static ItemBlockplacer.Doubling<BlockVault> vaultStone = make(new ItemBlockplacer.Doubling<BlockVault>("vault_stone", 2, SoundType.STONE, GeoBlocks.vaultStoneSingle, GeoBlocks.vaultStoneDouble));
    public static ItemBlockplacer.Doubling<BlockVault> vaultBrick = make(new ItemBlockplacer.Doubling<BlockVault>("vault_brick", 2, SoundType.STONE, GeoBlocks.vaultBrickSingle, GeoBlocks.vaultBrickDouble));
    
    // Door blockplacers
    public static Item doorPole = make(new ItemBlockplacer.Door(GeoBlocks.doorPole, "door_pole"), true);
    public static Item doorWood = make(new ItemBlockplacer.Door(GeoBlocks.doorWood, "door_wood"), true);
    
    // Beam blockplacers
    public static Item beamLong =  make(new ItemBlockplacer.Beam("beam_long", GeoBlocks.beamThick, 4, 8));
    public static Item beamShort = make(new ItemBlockplacer.Beam("beam_short", GeoBlocks.beamThin, 2, 4));
    
    // Floor blockplacers
    public static Item floorPole = make(new ItemBlockplacer.Floor(6, EnumFloor.POLE));
    public static Item floorWood = make(new ItemBlockplacer.Floor(6, EnumFloor.WOOD));
    
    // Compost
    public static Item compost = make(new ItemCompost());

    // Inert materials
    public static Item twineHemp =  make(new ItemSimple("twine_hemp", 6));
    public static Item cotton =     make(new ItemSimple("cotton", 6));
    public static Item wool =       make(new ItemSimple("wool", 5));
    
    public static Item amethyst = make(new ItemSimple("amethyst", 53));
    public static Item fireopal = make(new ItemSimple("fireopal", 47));
    public static Item ruby =     make(new ItemSimple("ruby", 61));
    public static Item sapphire = make(new ItemSimple("sapphire", 59));
    
    public static Item skinBear =   make(new ItemSimple("skin_bear", 4));
    public static Item skinCow =    make(new ItemSimple("skin_cow", 4));
    public static Item skinPig =    make(new ItemSimple("skin_pig", 4));
    public static Item skinSheep =  make(new ItemSimple("skin_sheep", 4));
    public static Item skinWolf =   make(new ItemSimple("skin_wolf", 4));
    
    public static Item tallow = make(new ItemSimple("tallow", 2));
    public static Item beeswax= make(new ItemSimple("beeswax", 12));

    public static Item honeycomb = make(new ItemSimple("honeycomb"));
    
    public static Item ingotCopper = make(new ItemSimple("ingot_copper", 2));
    public static Item ingotSilver = make(new ItemSimple("ingot_silver", 2));
    public static Item ingotSteel =  make(new ItemSimple("ingot_steel", 2));
    public static Item ingotTin =    make(new ItemSimple("ingot_tin", 2));
    
    public static Item leaves = make(new ItemSimple("leaves", 3));
    
    public static Item pole =       make(new ItemSimple("pole", 6));
    public static Item log =        make(new ItemSimple("log", 3));
    public static Item thicklog =   make(new ItemSimple("thicklog"));
    public static Item timber =     make(new ItemSimple("timber", 6));
    
    public static Item glass = make(new ItemSimple("glass", 6));
    
    public static Item clay = make(new ItemSimple("clay", 9));
    
    public static Item mudbricks =    make(new ItemSimple("mudbricks"));
    public static Item stoneDressed = make(new ItemSimple("stone_dressed", 1));

    public static Item peatDry = make(new ItemSimple("peat_dry"));
    public static Item peatWet = make(new ItemSimple("peat_wet"));
    
    public static Item salt =  make(new ItemSimple("salt"));
    public static Item chalk = make(new ItemSimple("chalk"));
    
    public static Item oreTin =    make(new ItemSimple("ore_tin"));
    public static Item oreCopper = make(new ItemSimple("ore_copper"));
    public static Item oreIron =   make(new ItemSimple("ore_iron"));
    public static Item oreSilver = make(new ItemSimple("ore_silver"));
    public static Item oreGold =   make(new ItemSimple("ore_gold"));
    
    public static Item potClay =  make(new ItemSimple("pot_clay"));
    public static Item potMetal = make(new ItemSimple("pot_metal"));
    
    public static Item arrowheadBronze = make(new ItemSimple("arrowhead_bronze", 20));
    public static Item arrowheadCopper = make(new ItemSimple("arrowhead_copper", 20));
    public static Item arrowheadFlint =  make(new ItemSimple("arrowhead_flint", 20));
    public static Item arrowheadSteel =  make(new ItemSimple("arrowhead_steel", 20));
    
    public static Item axeheadBronze = make(new ItemSimple("axehead_bronze", 2));
    public static Item axeheadCopper = make(new ItemSimple("axehead_copper", 2));
    public static Item axeheadFlint =  make(new ItemSimple("axehead_flint", 2));
    public static Item axeheadSteel =  make(new ItemSimple("axehead_steel", 2));
    
    public static Item hoeheadBronze = make(new ItemSimple("hoehead_bronze", 2));
    public static Item hoeheadCopper = make(new ItemSimple("hoehead_copper", 2));
    public static Item hoeheadSteel =  make(new ItemSimple("hoehead_steel", 2));
    
    public static Item knifebladeBronze = make(new ItemSimple("knifeblade_bronze", 2));
    public static Item knifebladeCopper = make(new ItemSimple("knifeblade_copper", 2));
    public static Item knifebladeSteel =  make(new ItemSimple("knifeblade_steel", 2));
    
    public static Item machetebladeBronze = make(new ItemSimple("macheteblade_bronze", 2));
    public static Item machetebladeCopper = make(new ItemSimple("macheteblade_copper", 2));
    public static Item machetebladeSteel =  make(new ItemSimple("macheteblade_steel", 2));
    
    public static Item pickheadBronze = make(new ItemSimple("pickhead_bronze", 2));
    public static Item pickheadCopper = make(new ItemSimple("pickhead_copper", 2));
    public static Item pickheadFlint =  make(new ItemSimple("pickhead_flint", 2));
    public static Item pickheadSteel =  make(new ItemSimple("pickhead_steel", 2));
    
    public static Item sicklebladeBronze = make(new ItemSimple("sickleblade_bronze", 2));
    public static Item sicklebladeCopper = make(new ItemSimple("sickleblade_copper", 2));
    public static Item sicklebladeSteel =  make(new ItemSimple("sickleblade_steel", 2));
    
    public static Item shovelheadBronze = make(new ItemSimple("shovelhead_bronze", 2));
    public static Item shovelheadCopper = make(new ItemSimple("shovelhead_copper", 2));
    public static Item shovelheadSteel =  make(new ItemSimple("shovelhead_steel", 2));
    
    public static Item spearheadBronze = make(new ItemSimple("spearhead_bronze", 2));
    public static Item spearheadCopper = make(new ItemSimple("spearhead_copper", 2));
    public static Item spearheadFlint =  make(new ItemSimple("spearhead_flint", 2));
    public static Item spearheadSteel =  make(new ItemSimple("spearhead_steel", 2));
    
    public static Item swordbladeBronze = make(new ItemSimple("swordblade_bronze"));
    public static Item swordbladeCopper = make(new ItemSimple("swordblade_copper"));
    public static Item swordbladeSteel =  make(new ItemSimple("swordblade_steel"));

    // Arrows
    public static Item arrowBronze = make(new ItemArrow.Bronze());
    public static Item arrowCopper = make(new ItemArrow.Copper());
    public static Item arrowFlint =  make(new ItemArrow.Flint());
    public static Item arrowSteel =  make(new ItemArrow.Steel());
    public static Item arrowWood =   make(new ItemArrow.Wood());

    // Hoes
    public static Item hoeBronze = make(new ItemHoe("hoe_bronze", EquipMaterial.BRONZE_TOOL));
    public static Item hoeCopper = make(new ItemHoe("hoe_copper", EquipMaterial.COPPER_TOOL));
    public static Item hoeSteel =  make(new ItemHoe("hoe_steel", EquipMaterial.STEEL_TOOL));
    public static Item hoeAntler = make(new ItemHoe("hoe_antler", EquipMaterial.ANTLER_TOOL));

    // Sickles
    public static Item sickleBronze = make(new ItemSickle("sickle_bronze", EquipMaterial.BRONZE_TOOL));
    public static Item sickleCopper = make(new ItemSickle("sickle_copper", EquipMaterial.COPPER_TOOL));
    public static Item sickleSteel =  make(new ItemSickle("sickle_steel", EquipMaterial.STEEL_TOOL));

    // Shovels
    public static Item shovelAntler = make(new ItemShovel("shovel_antler", EquipMaterial.ANTLER_TOOL));
    public static Item shovelBronze = make(new ItemShovel("shovel_bronze", EquipMaterial.BRONZE_TOOL));
    public static Item shovelCopper = make(new ItemShovel("shovel_copper", EquipMaterial.COPPER_TOOL));
    public static Item shovelSteel =  make(new ItemShovel("shovel_steel", EquipMaterial.STEEL_TOOL));
    public static Item shovelWood =   make(new ItemShovel("shovel_wood", EquipMaterial.WOOD_TOOL));

    // Axes
    public static Item axeBronze = make(new ItemAxe("axe_bronze", EquipMaterial.BRONZE_TOOL));
    public static Item axeCopper = make(new ItemAxe("axe_copper", EquipMaterial.COPPER_TOOL));
    public static Item axeFlint =  make(new ItemAxe("axe_flint", EquipMaterial.FLINT_TOOL));
    public static Item axeSteel =  make(new ItemAxe("axe_steel", EquipMaterial.STEEL_TOOL));

    // Hunting knives
    public static Item huntingknifeBronze = make(new ItemHuntingknife("huntingknife_bronze", EquipMaterial.BRONZE_TOOL));
    public static Item huntingknifeCopper = make(new ItemHuntingknife("huntingknife_copper", EquipMaterial.COPPER_TOOL));
    public static Item huntingknifeFlint =  make(new ItemHuntingknife("huntingknife_flint", EquipMaterial.FLINT_TOOL));
    public static Item huntingknifeSteel =  make(new ItemHuntingknife("huntingknife_steel", EquipMaterial.STEEL_TOOL));

    // Machetes
    public static Item macheteBronze = make(new ItemMachete("machete_bronze", EquipMaterial.BRONZE_TOOL));
    public static Item macheteCopper = make(new ItemMachete("machete_copper", EquipMaterial.COPPER_TOOL));
    public static Item macheteSteel =  make(new ItemMachete("machete_steel", EquipMaterial.STEEL_TOOL));

    // Pickaxes
    public static Item pickaxeBronze = make(new ItemPickaxe("pickaxe_bronze", EquipMaterial.BRONZE_TOOL));
    public static Item pickaxeCopper = make(new ItemPickaxe("pickaxe_copper", EquipMaterial.COPPER_TOOL));
    public static Item pickaxeFlint =  make(new ItemPickaxe("pickaxe_flint", EquipMaterial.FLINT_TOOL));
    public static Item pickaxeSteel =  make(new ItemPickaxe("pickaxe_steel", EquipMaterial.STEEL_TOOL));

    // Shears
    public static Item shearsFlint =  make(new ItemShears("shears_flint", EquipMaterial.FLINT_TOOL, (r) -> 3 + r.nextInt(3)));
    public static Item shearsBronze = make(new ItemShears("shears_bronze", EquipMaterial.BRONZE_TOOL, (r) -> 6 + r.nextInt(3)));
    public static Item shearsCopper = make(new ItemShears("shears_copper", EquipMaterial.COPPER_TOOL, (r) -> 5 + r.nextInt(3)));
    public static Item shearsSteel =  make(new ItemShears("shears_steel", EquipMaterial.STEEL_TOOL, (r) -> 7 + r.nextInt(3)));

    // Spears
    public static Item spearBronze = make(new ItemSpear("spear_bronze", EquipMaterial.BRONZE_TOOL, EntitySpearBronze::new));
    public static Item spearCopper = make(new ItemSpear("spear_copper", EquipMaterial.COPPER_TOOL, EntitySpearCopper::new));
    public static Item spearFlint =  make(new ItemSpear("spear_flint", EquipMaterial.FLINT_TOOL, EntitySpearFlint::new));
    public static Item spearSteel =  make(new ItemSpear("spear_steel", EquipMaterial.STEEL_TOOL, EntitySpearSteel::new));
    public static Item spearWood =   make(new ItemSpear("spear_wood", EquipMaterial.WOOD_TOOL, EntitySpearWood::new));

    // Swords
    public static Item swordBronze = make(new ItemSword("sword_bronze", EquipMaterial.BRONZE_TOOL));
    public static Item swordCopper = make(new ItemSword("sword_copper", EquipMaterial.COPPER_TOOL));
    public static Item swordSteel =  make(new ItemSword("sword_steel", EquipMaterial.STEEL_TOOL));
    
    // Shields
    public static Item shieldWood =  make(new ItemShield("shield_wood", 100));
    public static Item shieldSteel = make(new ItemShield("shield_steel", 200));
    
    // Apparel
    public static Item cottonHead =         make(new ItemApparel("cotton_head", EquipMaterial.COTTON_APPAREL, EntityEquipmentSlot.HEAD, CreativeTabs.MISC));
    public static Item cottonChest =        make(new ItemApparel("cotton_chest", EquipMaterial.COTTON_APPAREL, EntityEquipmentSlot.CHEST, CreativeTabs.MISC));
    public static Item cottonLegs =         make(new ItemApparel("cotton_legs", EquipMaterial.COTTON_APPAREL, EntityEquipmentSlot.LEGS, CreativeTabs.MISC));
    public static Item cottonFeet =         make(new ItemApparel("cotton_feet", EquipMaterial.COTTON_APPAREL, EntityEquipmentSlot.FEET, CreativeTabs.MISC));
    
    public static Item woolHead =           make(new ItemApparel("wool_head", EquipMaterial.WOOL_APPAREL, EntityEquipmentSlot.HEAD, CreativeTabs.MISC));
    public static Item woolChest =          make(new ItemApparel("wool_chest", EquipMaterial.WOOL_APPAREL, EntityEquipmentSlot.CHEST, CreativeTabs.MISC));
    public static Item woolLegs =           make(new ItemApparel("wool_legs", EquipMaterial.WOOL_APPAREL, EntityEquipmentSlot.LEGS, CreativeTabs.MISC));
    public static Item woolFeet =           make(new ItemApparel("wool_feet", EquipMaterial.WOOL_APPAREL, EntityEquipmentSlot.FEET, CreativeTabs.MISC));
    
    public static Item furHead =            make(new ItemApparel("fur_head", EquipMaterial.FUR_APPAREL, EntityEquipmentSlot.HEAD, CreativeTabs.MISC));
    public static Item furChest =           make(new ItemApparel("fur_chest", EquipMaterial.FUR_APPAREL, EntityEquipmentSlot.CHEST, CreativeTabs.MISC));
    public static Item furLegs =            make(new ItemApparel("fur_legs", EquipMaterial.FUR_APPAREL, EntityEquipmentSlot.LEGS, CreativeTabs.MISC));
    public static Item furFeet =            make(new ItemApparel("fur_feet", EquipMaterial.FUR_APPAREL, EntityEquipmentSlot.FEET, CreativeTabs.MISC));
    
    public static Item leatherHead =        make(new ItemApparel("leather_head", EquipMaterial.LEATHER_APPAREL, EntityEquipmentSlot.HEAD, CreativeTabs.COMBAT));
    public static Item leatherChest =       make(new ItemApparel("leather_chest", EquipMaterial.LEATHER_APPAREL, EntityEquipmentSlot.CHEST, CreativeTabs.COMBAT));
    public static Item leatherLegs =        make(new ItemApparel("leather_legs", EquipMaterial.LEATHER_APPAREL, EntityEquipmentSlot.LEGS, CreativeTabs.COMBAT));
    public static Item leatherFeet =        make(new ItemApparel("leather_feet", EquipMaterial.LEATHER_APPAREL, EntityEquipmentSlot.FEET, CreativeTabs.COMBAT));
    
    public static Item steelmailHead =      make(new ItemApparel("steelmail_head", EquipMaterial.STEELMAIL_APPAREL, EntityEquipmentSlot.HEAD, CreativeTabs.COMBAT));
    public static Item steelmailChest =     make(new ItemApparel("steelmail_chest", EquipMaterial.STEELMAIL_APPAREL, EntityEquipmentSlot.CHEST, CreativeTabs.COMBAT));
    public static Item steelmailLegs =      make(new ItemApparel("steelmail_legs", EquipMaterial.STEELMAIL_APPAREL, EntityEquipmentSlot.LEGS, CreativeTabs.COMBAT));
    public static Item steelmailFeet =      make(new ItemApparel("steelmail_feet", EquipMaterial.STEELMAIL_APPAREL, EntityEquipmentSlot.FEET, CreativeTabs.COMBAT));
    
    public static Item steelplateHead =     make(new ItemApparel("steelplate_head", EquipMaterial.STEELPLATE_APPAREL, EntityEquipmentSlot.HEAD, CreativeTabs.COMBAT));
    public static Item steelplateChest =    make(new ItemApparel("steelplate_chest", EquipMaterial.STEELPLATE_APPAREL, EntityEquipmentSlot.CHEST, CreativeTabs.COMBAT));
    public static Item steelplateLegs =     make(new ItemApparel("steelplate_legs", EquipMaterial.STEELPLATE_APPAREL, EntityEquipmentSlot.LEGS, CreativeTabs.COMBAT));
    public static Item steelplateFeet =     make(new ItemApparel("steelplate_feet", EquipMaterial.STEELPLATE_APPAREL, EntityEquipmentSlot.FEET, CreativeTabs.COMBAT));

    // Bows
    public static Item bowCrude = make(new ItemBow("bow_crude", 200, EntityProjectile.CRUDE_MOD));
    public static Item bowWar =   make(new ItemBow("bow_war", 384, EntityProjectile.BOW_MOD));
    
    /** Adjust vanilla items. */
    public static void preInit() {
        
        Items.STICK.setMaxStackSize(12);
        Items.BONE.setMaxStackSize(6);
        Items.FLINT.setMaxStackSize(6);
        Items.LEATHER.setMaxStackSize(3);
        Items.COAL.setMaxStackSize(1);
        Items.RABBIT_HIDE.setMaxStackSize(4);
        Items.REEDS.setMaxStackSize(5);
        Items.BRICK.setMaxStackSize(1);
    }
    
    /** Put the constructed item into the modelling set
     * and the offhand-only set if applicable. */
    private static <I extends Item> I make(I item, boolean isOffhandOnly) {

      //  GameRegistry.register(item);
        
        if (isOffhandOnly) {
            
            GeoBlocks.OFFHAND_ONLY.add(item);
        }

        ITEMS.add(item);
        return item;
    }
    
    private static <I extends Item> I make(I item) {
        
        return make(item, false);
    }
}

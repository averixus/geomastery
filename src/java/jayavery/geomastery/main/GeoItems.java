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
    public static final Item HONEY = make(new ItemEdible("honey", 4, 1F, 8, FoodType.CARBS));
    public static final Item BREAD = make(new ItemEdible("bread", 4, 2, 10, FoodType.CARBS));
    public static final Item SUGAR = make(new ItemEdible("sugar", 5, 0, 10, FoodType.CARBS));

    // Decaying foods
    public static final Item BANANA =              make(new ItemEdibleDecayable("banana", 4, 6F, 5, FoodType.FRUITVEG, 2));
    public static final Item PEAR =                make(new ItemEdibleDecayable("pear", 4, 4F, 5, FoodType.FRUITVEG, 2));
    public static final Item ORANGE =              make(new ItemEdibleDecayable("orange", 3, 3F, 6, FoodType.FRUITVEG, 4));
    public static final Item WHEAT_BOILED =        make(new ItemEdibleDecayable("wheat_boiled", 4, 2F, 10, FoodType.CARBS, 1));
    public static final Item RICE_BOILED =         make(new ItemEdibleDecayable("rice_boiled", 4, 2, 12, FoodType.CARBS, 1));
    public static final Item CHICKPEAS_BOILED =    make(new ItemEdibleDecayable("chickpeas_boiled", 3, 3, 10, FoodType.PROTEIN, 1));
    public static final Item PUMPKIN =             make(new ItemEdibleDecayable("pumpkin", 10, 10, 2, FoodType.FRUITVEG, 4));
    public static final Item MELON =               make(new ItemEdibleDecayable("melon", 12, 12, 2, FoodType.FRUITVEG, 2));
    public static final Item APPLE =               make(new ItemEdibleDecayable("apple", 4, 4, 5, FoodType.FRUITVEG, 2));
    public static final Item POTATO_COOKED =       make(new ItemEdibleDecayable("potato_cooked", 8, 6, 5, FoodType.CARBS, 2));
    public static final Item EGG =                 make(new ItemEdibleDecayable("egg", 5, 5, 6, FoodType.PROTEIN, 4));
    public static final Item BEEF_RAW =            make(new ItemEdibleDecayable("beef_raw", 3, 6, 5, FoodType.PROTEIN, 1));
    public static final Item PORK_RAW =            make(new ItemEdibleDecayable("pork_raw", 2, 5, 6, FoodType.PROTEIN, 1));
    public static final Item CHICKEN_RAW =         make(new ItemEdibleDecayable("chicken_raw", 1, 3, 10, FoodType.PROTEIN, 1));
    public static final Item MUTTON_RAW =          make(new ItemEdibleDecayable("mutton_raw", 2, 4, 7, FoodType.PROTEIN, 1));
    public static final Item RABBIT_RAW =          make(new ItemEdibleDecayable("rabbit_raw", 1, 2, 15, FoodType.PROTEIN, 1));
    public static final Item FISH_RAW =            make(new ItemEdibleDecayable("fish_raw", 1, 3, 10, FoodType.PROTEIN, 1));
    public static final Item BEEF_COOKED =         make(new ItemEdibleDecayable("beef_cooked", 6, 6, 5, FoodType.PROTEIN, 2));
    public static final Item PORK_COOKED =         make(new ItemEdibleDecayable("pork_cooked", 5, 5, 6, FoodType.PROTEIN, 2));
    public static final Item MUTTON_COOKED =       make(new ItemEdibleDecayable("mutton_cooked", 4, 4, 7, FoodType.PROTEIN, 2));
    public static final Item RABBIT_COOKED =       make(new ItemEdibleDecayable("rabbit_cooked", 2, 2, 15, FoodType.PROTEIN, 2));
    public static final Item CHICKEN_COOKED =      make(new ItemEdibleDecayable("chicken_cooked", 3, 3, 10, FoodType.PROTEIN, 2));
    public static final Item FISH_COOKED =         make(new ItemEdibleDecayable("fish_cooked", 3, 3, 10, FoodType.PROTEIN, 2));
    
    // Decaying carcasses
    public static final ItemCarcassDecayable CARCASS_COWPART = make(new ItemCarcassDecayable("carcass_cowpart", GeoBlocks.CARCASS_COWPART), true);
    public static final ItemCarcassDecayable CARCASS_PIG =     make(new ItemCarcassDecayable("carcass_pig", GeoBlocks.CARCASS_PIG));
    public static final ItemCarcassDecayable CARCASS_CHICKEN = make(new ItemCarcassDecayable("carcass_chicken", GeoBlocks.CARCASS_CHICKEN));
    public static final ItemCarcassDecayable CARCASS_SHEEP =   make(new ItemCarcassDecayable("carcass_sheep", GeoBlocks.CARCASS_SHEEP));
    public static final ItemCarcassDecayable CARCASS_RABBIT =  make(new ItemCarcassDecayable("carcass_rabbit", GeoBlocks.CARCASS_RABBIT));
    
    // Non-decaying edible seeds
    public static final Item SEED_PUMPKIN = make(new ItemEdibleSeed("seeds_pumpkin", 1, 1, 15, GeoBlocks.PUMPKIN_CROP, FoodType.PROTEIN));
    
    // Decaying edible seeds
    public static final Item MUSHROOM_RED =   make(new ItemEdibleSeedDecayable("mushroom_red", 4, 4, 4, GeoBlocks.MUSHROOMBABY_RED, EnumPlantType.Cave, FoodType.FRUITVEG, 2));
    public static final Item MUSHROOM_BROWN = make(new ItemEdibleSeedDecayable("mushroom_brown", 2, 2, 9, GeoBlocks.MUSHROOMBABY_BROWN, EnumPlantType.Cave, FoodType.FRUITVEG, 2));
    public static final Item BEAN =           make(new ItemEdibleSeedDecayable("bean", 2, 2F, 10, GeoBlocks.BEAN, EnumPlantType.Crop, FoodType.FRUITVEG, 2));
    public static final Item PEPPER =         make(new ItemEdibleSeedDecayable("pepper", 3, 3F, 6, GeoBlocks.PEPPER, EnumPlantType.Crop, FoodType.FRUITVEG, 2));
    public static final Item TOMATO =         make(new ItemEdibleSeedDecayable("tomato", 3, 3F, 7, GeoBlocks.TOMATO, EnumPlantType.Crop, FoodType.FRUITVEG, 2));
    public static final Item BERRY =          make(new ItemEdibleSeedDecayable("berry", 1, 1F, 20, GeoBlocks.BERRY, EnumPlantType.Crop, FoodType.FRUITVEG, 2));
    public static final Item CARROT =         make(new ItemEdibleSeedDecayable("potato", 7, 7F, 5, GeoBlocks.POTATO, EnumPlantType.Crop, FoodType.CARBS, 4));
    public static final Item POTATO =         make(new ItemEdibleSeedDecayable("carrot", 3, 3F, 7, GeoBlocks.CARROT, EnumPlantType.Crop, FoodType.FRUITVEG, 4, EntityPig.class, EntityRabbit.class));
    public static final Item BEETROOT =       make(new ItemEdibleSeedDecayable("beetroot", 3, 3F, 6, GeoBlocks.BEETROOT, EnumPlantType.Crop, FoodType.FRUITVEG, 4));
        
    // Non-decaying inedible seeds
    public static final Item CHICKPEAS =      make(new ItemSeed("chickpeas", 10, GeoBlocks.CHICKPEA));
    public static final Item CUTTING_COTTON = make(new ItemSeed("cutting_cotton", 1, GeoBlocks.COTTON));
    public static final Item CUTTING_HEMP =   make(new ItemSeed("cutting_hemp", 1, GeoBlocks.HEMP));
    public static final Item WHEAT =          make(new ItemSeed("wheat", 10, GeoBlocks.WHEAT, EntityCow.class, EntitySheep.class, EntityChicken.class));
    public static final Item SEED_MELON =     make(new ItemSeed("seeds_melon", 15, GeoBlocks.MELON_CROP));
    
    // Rice
    public static final Item RICE = make(new ItemRice());

    // Beds
    public static final Item BED_LEAF =    make(new ItemBed("bed_leaf", GeoBlocks.BED_LEAF), true);
    public static final Item BED_COTTON =  make(new ItemBed("bed_cotton", GeoBlocks.BED_COTTON));
    public static final Item BED_WOOL =    make(new ItemBed("bed_wool", GeoBlocks.BED_WOOL));
    public static final Item BED_SIMPLE =  make(new ItemBed("bed_simple", GeoBlocks.BED_SIMPLE), true);

    // Carry items
    public static final Item BACKPACK =   make(new ItemSimple("backpack", 1, CreativeTabs.TRANSPORTATION));
    public static final Item YOKE =       make(new ItemSimple("yoke", 1, CreativeTabs.TRANSPORTATION));

    // Loose heaping blockplacers
    public static final Item LOOSE_DIRT =     make(new ItemBlockplacer.Heaping("loose_dirt", SoundType.GROUND, Blocks.DIRT));
    public static final Item LOOSE_GRAVEL =   make(new ItemBlockplacer.Heaping("loose_gravel", SoundType.GROUND, Blocks.GRAVEL));
    public static final Item LOOSE_SAND =     make(new ItemBlockplacer.Heaping("loose_sand", SoundType.SAND, Blocks.SAND));
    public static final Item LOOSE_CLAY =     make(new ItemBlockplacer.Heaping("loose_clay", SoundType.GROUND, Blocks.CLAY));
    public static final Item RUBBLE =         make(new ItemBlockplacer.Heaping("rubble", SoundType.GROUND, GeoBlocks.RUBBLE));
    
    // Multipart blockplacers
    public static final Item CRAFTING_CANDLEMAKER =    make(new ItemBlockplacer.Multipart<EnumPartCandlemaker>("crafting_candlemaker", EnumPartCandlemaker.FRONT, SoundType.WOOD), true);
    public static final Item CRAFTING_FORGE =          make(new ItemBlockplacer.Multipart<EnumPartForge>("crafting_forge", EnumPartForge.FM, SoundType.METAL), true);
    public static final Item CRAFTING_MASON =          make(new ItemBlockplacer.Multipart<EnumPartMason>("crafting_mason", EnumPartMason.FM, SoundType.STONE), true);
    public static final Item CRAFTING_TEXTILES =       make(new ItemBlockplacer.Multipart<EnumPartTextiles>("crafting_textiles", EnumPartTextiles.FRONT, SoundType.WOOD), true);
    public static final Item CRAFTING_WOODWORKING =    make(new ItemBlockplacer.Multipart<EnumPartWoodworking>("crafting_woodworking", EnumPartWoodworking.FM, SoundType.WOOD), true);
    public static final Item CRAFTING_SAWPIT =         make(new ItemBlockplacer.Multipart<EnumPartSawpit>("crafting_sawpit", EnumPartSawpit.F, SoundType.WOOD), true);
    public static final Item CRAFTING_ARMOURER =       make(new ItemBlockplacer.Multipart<EnumPartArmourer>("crafting_armourer", EnumPartArmourer.M, SoundType.METAL), true);
    public static final Item FURNACE_CLAY =            make(new ItemBlockplacer.Multipart<EnumPartClay>("furnace_clay", EnumPartClay.BL, SoundType.STONE), true);
    public static final Item FURNACE_STONE =           make(new ItemBlockplacer.Multipart<EnumPartStone>("furnace_stone", EnumPartStone.BM, SoundType.STONE), true);

    // Doubling blockplacers
    public static final ItemBlockplacer.Doubling<BlockWallComplex> WALL_BRICK = make(new ItemBlockplacer.Doubling<BlockWallComplex>("wall_brick", 2, SoundType.STONE, GeoBlocks.WALL_BRICK_SINGLE, GeoBlocks.WALL_BRICK_DOUBLE)); 
    public static final ItemBlockplacer.Doubling<BlockWallHeaping> WALL_MUD =   make(new ItemBlockplacer.Doubling<BlockWallHeaping>("wall_mud", 2, SoundType.GROUND, GeoBlocks.WALL_MUD_SINGLE, GeoBlocks.WALL_MUD_DOUBLE));
    public static final ItemBlockplacer.Doubling<BlockWallHeaping> WALL_ROUGH = make(new ItemBlockplacer.Doubling<BlockWallHeaping>("wall_rough", 2, SoundType.STONE, GeoBlocks.WALL_ROUGH_SINGLE, GeoBlocks.WALL_ROUGH_DOUBLE));
    public static final ItemBlockplacer.Doubling<BlockWallComplex> WALL_STONE = make(new ItemBlockplacer.Doubling<BlockWallComplex>("wall_stone", 2, SoundType.STONE, GeoBlocks.WALL_STONE_SINGLE, GeoBlocks.WALL_STONE_DOUBLE));
    
    public static final ItemBlockplacer.Doubling<BlockSlab> SLAB_STONE = make(new ItemBlockplacer.Doubling<BlockSlab>("slab_stone", 2, SoundType.STONE, GeoBlocks.SLAB_STONE_SINGLE, GeoBlocks.SLAB_STONE_DOUBLE));
    public static final ItemBlockplacer.Doubling<BlockSlab> SLAB_BRICK = make(new ItemBlockplacer.Doubling<BlockSlab>("slab_brick", 2, SoundType.STONE, GeoBlocks.SLAB_BRICK_SINGLE, GeoBlocks.SLAB_BRICK_DOUBLE));
    
    public static final ItemBlockplacer.Doubling<BlockVault> VAULT_STONE = make(new ItemBlockplacer.Doubling<BlockVault>("vault_stone", 2, SoundType.STONE, GeoBlocks.VAULT_STONE_SINGLE, GeoBlocks.VAULT_STONE_DOUBLE));
    public static final ItemBlockplacer.Doubling<BlockVault> VAULT_BRICK = make(new ItemBlockplacer.Doubling<BlockVault>("vault_brick", 2, SoundType.STONE, GeoBlocks.VAULT_BRICK_SINGLE, GeoBlocks.VAULT_BRICK_DOUBLE));
    
    // Door blockplacers
    public static final Item DOOR_POLE = make(new ItemBlockplacer.Door(GeoBlocks.DOOR_POLE, "door_pole"), true);
    public static final Item DOOR_WOOD = make(new ItemBlockplacer.Door(GeoBlocks.DOOR_WOOD, "door_wood"), true);
    
    // Beam blockplacers
    public static final Item BEAM_LONG =  make(new ItemBlockplacer.Beam("beam_long", GeoBlocks.BEAM_THICK, 4, 8));
    public static final Item BEAM_SHORT = make(new ItemBlockplacer.Beam("beam_short", GeoBlocks.BEAM_THIN, 2, 4));
    
    // Floor blockplacers
    public static final Item FLOOR_POLE = make(new ItemBlockplacer.Floor(6, EnumFloor.POLE));
    public static final Item FLOOR_WOOD = make(new ItemBlockplacer.Floor(6, EnumFloor.WOOD));
    
    // Compost
    public static final Item COMPOST = make(new ItemCompost());

    // Inert materials
    public static final Item TWINE_HEMP = make(new ItemSimple("twine_hemp", 6));
    public static final Item COTTON =     make(new ItemSimple("cotton", 6));
    public static final Item WOOL =       make(new ItemSimple("wool", 5));
    
    public static final Item AMETHYST = make(new ItemSimple("amethyst", 53));
    public static final Item FIREOPAL = make(new ItemSimple("fireopal", 47));
    public static final Item RUBY =     make(new ItemSimple("ruby", 61));
    public static final Item SAPPHIRE = make(new ItemSimple("sapphire", 59));
    
    public static final Item SKIN_BEAR =   make(new ItemSimple("skin_bear", 4));
    public static final Item SKIN_COW =    make(new ItemSimple("skin_cow", 4));
    public static final Item SKIN_PIG =    make(new ItemSimple("skin_pig", 4));
    public static final Item SKIN_SHEEP =  make(new ItemSimple("skin_sheep", 4));
    public static final Item SKIN_WOLF =   make(new ItemSimple("skin_wolf", 4));
    
    public static final Item TALLOW =  make(new ItemSimple("tallow", 2));
    public static final Item BEESWAX = make(new ItemSimple("beeswax", 12));

    public static final Item HONEYCOMB = make(new ItemSimple("honeycomb"));
    
    public static final Item INGOT_COPPER = make(new ItemSimple("ingot_copper", 2));
    public static final Item INGOT_SILVER = make(new ItemSimple("ingot_silver", 2));
    public static final Item INGOT_STEEL =  make(new ItemSimple("ingot_steel", 2));
    public static final Item INGOT_TIN =    make(new ItemSimple("ingot_tin", 2));
    
    public static final Item LEAVES = make(new ItemSimple("leaves", 3));
    
    public static final Item POLE =       make(new ItemSimple("pole", 6));
    public static final Item LOG =        make(new ItemSimple("log", 3));
    public static final Item THICKLOG =   make(new ItemSimple("thicklog"));
    public static final Item TIMBER =     make(new ItemSimple("timber", 6));
    
    public static final Item GLASS = make(new ItemSimple("glass", 6));
    
    public static final Item CLAY = make(new ItemSimple("clay", 9));
    
    public static final Item MUDBRICKS =    make(new ItemSimple("mudbricks"));
    public static final Item DRESSEDSTONE = make(new ItemSimple("dressedstone", 1));

    public static final Item PEAT_DRY = make(new ItemSimple("peat_dry"));
    public static final Item PEAT_WET = make(new ItemSimple("peat_wet"));
    
    public static final Item SALT =  make(new ItemSimple("salt"));
    public static final Item CHALK = make(new ItemSimple("chalk"));
    
    public static final Item ORE_TIN =    make(new ItemSimple("ore_tin"));
    public static final Item ORE_COPPER = make(new ItemSimple("ore_copper"));
    public static final Item ORE_IRON =   make(new ItemSimple("ore_iron"));
    public static final Item ORE_SILVER = make(new ItemSimple("ore_silver"));
    public static final Item ORE_GOLD =   make(new ItemSimple("ore_gold"));
    
    public static final Item POT_CLAY =  make(new ItemSimple("pot_clay"));
    public static final Item POT_METAL = make(new ItemSimple("pot_metal"));
    
    public static final Item ARROWHEAD_BRONZE = make(new ItemSimple("arrowhead_bronze", 20));
    public static final Item ARROWHEAD_COPPER = make(new ItemSimple("arrowhead_copper", 20));
    public static final Item ARROWHEAT_FLINT =  make(new ItemSimple("arrowhead_flint", 20));
    public static final Item ARROWHEAD_STEEL =  make(new ItemSimple("arrowhead_steel", 20));
    
    public static final Item AXEHEAD_BRONZE = make(new ItemSimple("axehead_bronze", 2));
    public static final Item AXEHEAD_COPPER = make(new ItemSimple("axehead_copper", 2));
    public static final Item AXEHEAD_FLINT =  make(new ItemSimple("axehead_flint", 2));
    public static final Item AXEHEAD_STEEL =  make(new ItemSimple("axehead_steel", 2));
    
    public static final Item HOEHEAD_BRONZE = make(new ItemSimple("hoehead_bronze", 2));
    public static final Item HOEHEAD_COPPER = make(new ItemSimple("hoehead_copper", 2));
    public static final Item HOEHEAD_STEEL =  make(new ItemSimple("hoehead_steel", 2));
    
    public static final Item KNIFEBLADE_BRONZE = make(new ItemSimple("knifeblade_bronze", 2));
    public static final Item KNIFEBLADE_COPPER = make(new ItemSimple("knifeblade_copper", 2));
    public static final Item KNIFEBLADE_STEEL =  make(new ItemSimple("knifeblade_steel", 2));
    
    public static final Item MACHETEBLADE_BRONZE = make(new ItemSimple("macheteblade_bronze", 2));
    public static final Item MACHETEBLADE_COPPER = make(new ItemSimple("macheteblade_copper", 2));
    public static final Item MACHETEBLADE_STEEL =  make(new ItemSimple("macheteblade_steel", 2));
    
    public static final Item PICKHEAD_BRONZE = make(new ItemSimple("pickhead_bronze", 2));
    public static final Item PICKHEAD_COPPER = make(new ItemSimple("pickhead_copper", 2));
    public static final Item PICKHEAD_FLINT =  make(new ItemSimple("pickhead_flint", 2));
    public static final Item PICKHEAD_STEEL =  make(new ItemSimple("pickhead_steel", 2));
    
    public static final Item SICKLEBLADE_BRONZE = make(new ItemSimple("sickleblade_bronze", 2));
    public static final Item SICKLEBLADE_COPPER = make(new ItemSimple("sickleblade_copper", 2));
    public static final Item SICKLEBLADE_STEEL =  make(new ItemSimple("sickleblade_steel", 2));
    
    public static final Item SHOVELHEAD_BRONZE = make(new ItemSimple("shovelhead_bronze", 2));
    public static final Item SHOVELHEAD_COPPER = make(new ItemSimple("shovelhead_copper", 2));
    public static final Item SHOVELHEAD_STEEL =  make(new ItemSimple("shovelhead_steel", 2));
    
    public static final Item SPEARHEAD_BRONZE = make(new ItemSimple("spearhead_bronze", 2));
    public static final Item SPEARHEAD_COPPER = make(new ItemSimple("spearhead_copper", 2));
    public static final Item SPEARHEAD_FLINT =  make(new ItemSimple("spearhead_flint", 2));
    public static final Item SPEARHEAD_STEEL =  make(new ItemSimple("spearhead_steel", 2));
    
    public static final Item SWORDBLADE_BRONZE = make(new ItemSimple("swordblade_bronze"));
    public static final Item SWORDBLADE_COPPER = make(new ItemSimple("swordblade_copper"));
    public static final Item SWORDBLADE_STEEL =  make(new ItemSimple("swordblade_steel"));

    // Arrows
    public static final Item ARROW_BRONZE = make(new ItemArrow.Bronze());
    public static final Item ARROW_COPPER = make(new ItemArrow.Copper());
    public static final Item ARROW_FLINT =  make(new ItemArrow.Flint());
    public static final Item ARROW_STEEL =  make(new ItemArrow.Steel());
    public static final Item ARROW_WOOD =   make(new ItemArrow.Wood());

    // Hoes
    public static final Item HOE_BRONZE = make(new ItemHoe("hoe_bronze", EquipMaterial.BRONZE_TOOL));
    public static final Item HOE_COPPER = make(new ItemHoe("hoe_copper", EquipMaterial.COPPER_TOOL));
    public static final Item HOE_STEEL =  make(new ItemHoe("hoe_steel", EquipMaterial.STEEL_TOOL));
    public static final Item HOE_ANTLER = make(new ItemHoe("hoe_antler", EquipMaterial.ANTLER_TOOL));

    // Sickles
    public static final Item SICKLE_BRONZE = make(new ItemSickle("sickle_bronze", EquipMaterial.BRONZE_TOOL));
    public static final Item SICKLE_COPPER = make(new ItemSickle("sickle_copper", EquipMaterial.COPPER_TOOL));
    public static final Item SICKLE_STEEL =  make(new ItemSickle("sickle_steel", EquipMaterial.STEEL_TOOL));

    // Shovels
    public static final Item SHOVEL_ANTLER = make(new ItemShovel("shovel_antler", EquipMaterial.ANTLER_TOOL));
    public static final Item SHOVEL_BRONZE = make(new ItemShovel("shovel_bronze", EquipMaterial.BRONZE_TOOL));
    public static final Item SHOVEL_COPPER = make(new ItemShovel("shovel_copper", EquipMaterial.COPPER_TOOL));
    public static final Item SHOVEL_STEEL =  make(new ItemShovel("shovel_steel", EquipMaterial.STEEL_TOOL));
    public static final Item SHOVEL_WOOD =   make(new ItemShovel("shovel_wood", EquipMaterial.WOOD_TOOL));

    // Axes
    public static final Item AXE_BRONZE = make(new ItemAxe("axe_bronze", EquipMaterial.BRONZE_TOOL));
    public static final Item AXE_COPPER = make(new ItemAxe("axe_copper", EquipMaterial.COPPER_TOOL));
    public static final Item AXE_FLINT =  make(new ItemAxe("axe_flint", EquipMaterial.FLINT_TOOL));
    public static final Item AXE_STEEL =  make(new ItemAxe("axe_steel", EquipMaterial.STEEL_TOOL));

    // Hunting knives
    public static final Item HUNTINGKNIFE_BRONZE = make(new ItemHuntingknife("huntingknife_bronze", EquipMaterial.BRONZE_TOOL));
    public static final Item HUNTINGKNIFE_COPPER = make(new ItemHuntingknife("huntingknife_copper", EquipMaterial.COPPER_TOOL));
    public static final Item HUNTINGKNIFE_FLINT =  make(new ItemHuntingknife("huntingknife_flint", EquipMaterial.FLINT_TOOL));
    public static final Item HUNTINGKNIFE_STEEL =  make(new ItemHuntingknife("huntingknife_steel", EquipMaterial.STEEL_TOOL));

    // Machetes
    public static final Item MACHETE_BRONZE = make(new ItemMachete("machete_bronze", EquipMaterial.BRONZE_TOOL));
    public static final Item MACHETE_COPPER = make(new ItemMachete("machete_copper", EquipMaterial.COPPER_TOOL));
    public static final Item MACHETE_STEEL =  make(new ItemMachete("machete_steel", EquipMaterial.STEEL_TOOL));

    // Pickaxes
    public static final Item PICKAXE_BRONZE = make(new ItemPickaxe("pickaxe_bronze", EquipMaterial.BRONZE_TOOL));
    public static final Item PICKAXE_COPPER = make(new ItemPickaxe("pickaxe_copper", EquipMaterial.COPPER_TOOL));
    public static final Item PICKAXE_FLINT =  make(new ItemPickaxe("pickaxe_flint", EquipMaterial.FLINT_TOOL));
    public static final Item PICKAXE_STEEL =  make(new ItemPickaxe("pickaxe_steel", EquipMaterial.STEEL_TOOL));

    // Shears
    public static final Item SHEARS_FLINT =  make(new ItemShears("shears_flint", EquipMaterial.FLINT_TOOL, (r) -> 3 + r.nextInt(3)));
    public static final Item SHEARS_BRONZE = make(new ItemShears("shears_bronze", EquipMaterial.BRONZE_TOOL, (r) -> 6 + r.nextInt(3)));
    public static final Item SHEARS_COPPER = make(new ItemShears("shears_copper", EquipMaterial.COPPER_TOOL, (r) -> 5 + r.nextInt(3)));
    public static final Item SHEARS_STEEL =  make(new ItemShears("shears_steel", EquipMaterial.STEEL_TOOL, (r) -> 7 + r.nextInt(3)));

    // Spears
    public static final Item SPEAR_BRONZE = make(new ItemSpear("spear_bronze", EquipMaterial.BRONZE_TOOL, EntitySpearBronze::new));
    public static final Item SPEAR_COPPER = make(new ItemSpear("spear_copper", EquipMaterial.COPPER_TOOL, EntitySpearCopper::new));
    public static final Item SPEAR_FLINT =  make(new ItemSpear("spear_flint", EquipMaterial.FLINT_TOOL, EntitySpearFlint::new));
    public static final Item SPEAR_STEEL =  make(new ItemSpear("spear_steel", EquipMaterial.STEEL_TOOL, EntitySpearSteel::new));
    public static final Item SPEAR_WOOD =   make(new ItemSpear("spear_wood", EquipMaterial.WOOD_TOOL, EntitySpearWood::new));

    // Swords
    public static final Item SWORD_BRONZE = make(new ItemSword("sword_bronze", EquipMaterial.BRONZE_TOOL));
    public static final Item SWORD_COPPER = make(new ItemSword("sword_copper", EquipMaterial.COPPER_TOOL));
    public static final Item SWORD_STEEL =  make(new ItemSword("sword_steel", EquipMaterial.STEEL_TOOL));
    
    // Shields
    public static final Item SHIELD_WOOD =  make(new ItemShield("shield_wood", 100));
    public static final Item SHIELD_STEEL = make(new ItemShield("shield_steel", 200));
    
    // Apparel
    public static final Item COTTON_HEAD =         make(new ItemApparel("cotton_head", EquipMaterial.COTTON_APPAREL, EntityEquipmentSlot.HEAD, CreativeTabs.MISC));
    public static final Item COTTON_CHEST =        make(new ItemApparel("cotton_chest", EquipMaterial.COTTON_APPAREL, EntityEquipmentSlot.CHEST, CreativeTabs.MISC));
    public static final Item COTTON_LEGS =         make(new ItemApparel("cotton_legs", EquipMaterial.COTTON_APPAREL, EntityEquipmentSlot.LEGS, CreativeTabs.MISC));
    public static final Item COTTON_FEET =         make(new ItemApparel("cotton_feet", EquipMaterial.COTTON_APPAREL, EntityEquipmentSlot.FEET, CreativeTabs.MISC));
    
    public static final Item WOOL_HEAD =           make(new ItemApparel("wool_head", EquipMaterial.WOOL_APPAREL, EntityEquipmentSlot.HEAD, CreativeTabs.MISC));
    public static final Item WOOL_CHEST =          make(new ItemApparel("wool_chest", EquipMaterial.WOOL_APPAREL, EntityEquipmentSlot.CHEST, CreativeTabs.MISC));
    public static final Item WOOL_LEGS =           make(new ItemApparel("wool_legs", EquipMaterial.WOOL_APPAREL, EntityEquipmentSlot.LEGS, CreativeTabs.MISC));
    public static final Item WOOL_FEET =           make(new ItemApparel("wool_feet", EquipMaterial.WOOL_APPAREL, EntityEquipmentSlot.FEET, CreativeTabs.MISC));
    
    public static final Item FUR_HEAD =            make(new ItemApparel("fur_head", EquipMaterial.FUR_APPAREL, EntityEquipmentSlot.HEAD, CreativeTabs.MISC));
    public static final Item FUR_CHEST =           make(new ItemApparel("fur_chest", EquipMaterial.FUR_APPAREL, EntityEquipmentSlot.CHEST, CreativeTabs.MISC));
    public static final Item FUR_LEGS =            make(new ItemApparel("fur_legs", EquipMaterial.FUR_APPAREL, EntityEquipmentSlot.LEGS, CreativeTabs.MISC));
    public static final Item FUR_FEET =            make(new ItemApparel("fur_feet", EquipMaterial.FUR_APPAREL, EntityEquipmentSlot.FEET, CreativeTabs.MISC));
    
    public static final Item LEATHER_HEAD =        make(new ItemApparel("leather_head", EquipMaterial.LEATHER_APPAREL, EntityEquipmentSlot.HEAD, CreativeTabs.COMBAT));
    public static final Item LEATHER_CHEST =       make(new ItemApparel("leather_chest", EquipMaterial.LEATHER_APPAREL, EntityEquipmentSlot.CHEST, CreativeTabs.COMBAT));
    public static final Item LEATHER_LEGS =        make(new ItemApparel("leather_legs", EquipMaterial.LEATHER_APPAREL, EntityEquipmentSlot.LEGS, CreativeTabs.COMBAT));
    public static final Item LEATHER_FEET =        make(new ItemApparel("leather_feet", EquipMaterial.LEATHER_APPAREL, EntityEquipmentSlot.FEET, CreativeTabs.COMBAT));
    
    public static final Item STEELMAIL_HEAD =      make(new ItemApparel("steelmail_head", EquipMaterial.STEELMAIL_APPAREL, EntityEquipmentSlot.HEAD, CreativeTabs.COMBAT));
    public static final Item STEELMAIL_CHEST =     make(new ItemApparel("steelmail_chest", EquipMaterial.STEELMAIL_APPAREL, EntityEquipmentSlot.CHEST, CreativeTabs.COMBAT));
    public static final Item STEELMAIL_LEGS =      make(new ItemApparel("steelmail_legs", EquipMaterial.STEELMAIL_APPAREL, EntityEquipmentSlot.LEGS, CreativeTabs.COMBAT));
    public static final Item STEELMAIL_FEET =      make(new ItemApparel("steelmail_feet", EquipMaterial.STEELMAIL_APPAREL, EntityEquipmentSlot.FEET, CreativeTabs.COMBAT));
    
    public static final Item STEELPLATE_HEAD =     make(new ItemApparel("steelplate_head", EquipMaterial.STEELPLATE_APPAREL, EntityEquipmentSlot.HEAD, CreativeTabs.COMBAT));
    public static final Item STEELPLATE_CHEST =    make(new ItemApparel("steelplate_chest", EquipMaterial.STEELPLATE_APPAREL, EntityEquipmentSlot.CHEST, CreativeTabs.COMBAT));
    public static final Item STEELPLATE_LEGS =     make(new ItemApparel("steelplate_legs", EquipMaterial.STEELPLATE_APPAREL, EntityEquipmentSlot.LEGS, CreativeTabs.COMBAT));
    public static final Item STEELPLATE_FEET =     make(new ItemApparel("steelplate_feet", EquipMaterial.STEELPLATE_APPAREL, EntityEquipmentSlot.FEET, CreativeTabs.COMBAT));

    // Bows
    public static final Item BOW_CRUDE = make(new ItemBow("bow_crude", 200, EntityProjectile.CRUDE_MOD));
    public static final Item BOW_WAR =   make(new ItemBow("bow_war", 384, EntityProjectile.BOW_MOD));
    
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

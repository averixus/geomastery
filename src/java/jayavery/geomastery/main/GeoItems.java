/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.main;

import java.util.List;
import com.google.common.collect.Lists;
import jayavery.geomastery.entities.projectile.EntityProjectile;
import jayavery.geomastery.entities.projectile.EntitySpearBronze;
import jayavery.geomastery.entities.projectile.EntitySpearCopper;
import jayavery.geomastery.entities.projectile.EntitySpearFlint;
import jayavery.geomastery.entities.projectile.EntitySpearSteel;
import jayavery.geomastery.entities.projectile.EntitySpearWood;
import jayavery.geomastery.items.ItemApparel;
import jayavery.geomastery.items.ItemArrow;
import jayavery.geomastery.items.ItemAxe;
import jayavery.geomastery.items.ItemBow;
import jayavery.geomastery.items.ItemCompost;
import jayavery.geomastery.items.ItemEdible;
import jayavery.geomastery.items.ItemEdibleDecayable;
import jayavery.geomastery.items.ItemEdibleSeed;
import jayavery.geomastery.items.ItemEdibleSeedDecayable;
import jayavery.geomastery.items.ItemFloor;
import jayavery.geomastery.items.ItemHoe;
import jayavery.geomastery.items.ItemHuntingknife;
import jayavery.geomastery.items.ItemLooseplacing;
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
import jayavery.geomastery.tileentities.TEBeam.ETypeFloor;
import jayavery.geomastery.utilities.EFoodType;
import jayavery.geomastery.utilities.EquipMaterial;
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
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/** Stores and registers all new Geomastery items. */
public class GeoItems {
    
    /** All new items. */
    public static final List<Item> ITEMS = Lists.newArrayList();
    
    // Non-decaying normal foods
    public static final Item HONEY = make(new ItemEdible("honey", 4, 1F, 8, EFoodType.CARBS));
    public static final Item BREAD = make(new ItemEdible("bread", 4, 2, 10, EFoodType.CARBS));
    public static final Item SUGAR = make(new ItemEdible("sugar", 5, 0, 10, EFoodType.CARBS));

    // Decaying foods
    public static final Item BANANA =              make(new ItemEdibleDecayable("banana", 4, 6F, 5, EFoodType.FRUITVEG, 2));
    public static final Item PEAR =                make(new ItemEdibleDecayable("pear", 4, 4F, 5, EFoodType.FRUITVEG, 2));
    public static final Item ORANGE =              make(new ItemEdibleDecayable("orange", 3, 3F, 6, EFoodType.FRUITVEG, 4));
    public static final Item WHEAT_BOILED =        make(new ItemEdibleDecayable("wheat_boiled", 4, 2F, 10, EFoodType.CARBS, 1));
    public static final Item RICE_BOILED =         make(new ItemEdibleDecayable("rice_boiled", 4, 2, 12, EFoodType.CARBS, 1));
    public static final Item CHICKPEAS_BOILED =    make(new ItemEdibleDecayable("chickpeas_boiled", 3, 3, 10, EFoodType.PROTEIN, 1));
    public static final Item PUMPKIN =             make(new ItemEdibleDecayable("pumpkin", 10, 10, 2, EFoodType.FRUITVEG, 4));
    public static final Item MELON =               make(new ItemEdibleDecayable("melon", 12, 12, 2, EFoodType.FRUITVEG, 2));
    public static final Item APPLE =               make(new ItemEdibleDecayable("apple", 4, 4, 5, EFoodType.FRUITVEG, 2));
    public static final Item POTATO_COOKED =       make(new ItemEdibleDecayable("potato_cooked", 8, 6, 5, EFoodType.CARBS, 2));
    public static final Item EGG =                 make(new ItemEdibleDecayable("egg", 5, 5, 6, EFoodType.PROTEIN, 4));
    public static final Item BEEF_RAW =            make(new ItemEdibleDecayable("beef_raw", 3, 6, 5, EFoodType.PROTEIN, 1));
    public static final Item PORK_RAW =            make(new ItemEdibleDecayable("pork_raw", 2, 5, 6, EFoodType.PROTEIN, 1));
    public static final Item CHICKEN_RAW =         make(new ItemEdibleDecayable("chicken_raw", 1, 3, 10, EFoodType.PROTEIN, 1));
    public static final Item MUTTON_RAW =          make(new ItemEdibleDecayable("mutton_raw", 2, 4, 7, EFoodType.PROTEIN, 1));
    public static final Item RABBIT_RAW =          make(new ItemEdibleDecayable("rabbit_raw", 1, 2, 15, EFoodType.PROTEIN, 1));
    public static final Item FISH_RAW =            make(new ItemEdibleDecayable("fish_raw", 1, 3, 10, EFoodType.PROTEIN, 1));
    public static final Item BEEF_COOKED =         make(new ItemEdibleDecayable("beef_cooked", 6, 6, 5, EFoodType.PROTEIN, 2));
    public static final Item PORK_COOKED =         make(new ItemEdibleDecayable("pork_cooked", 5, 5, 6, EFoodType.PROTEIN, 2));
    public static final Item MUTTON_COOKED =       make(new ItemEdibleDecayable("mutton_cooked", 4, 4, 7, EFoodType.PROTEIN, 2));
    public static final Item RABBIT_COOKED =       make(new ItemEdibleDecayable("rabbit_cooked", 2, 2, 15, EFoodType.PROTEIN, 2));
    public static final Item CHICKEN_COOKED =      make(new ItemEdibleDecayable("chicken_cooked", 3, 3, 10, EFoodType.PROTEIN, 2));
    public static final Item FISH_COOKED =         make(new ItemEdibleDecayable("fish_cooked", 3, 3, 10, EFoodType.PROTEIN, 2));

    // Non-decaying edible seeds
    public static final Item SEED_PUMPKIN = make(new ItemEdibleSeed("seeds_pumpkin", 1, 1, 15, () -> GeoBlocks.PUMPKIN_CROP, EFoodType.PROTEIN));
    
    // Non-decaying inedible seeds
    public static final Item CHICKPEAS =      make(new ItemSeed("chickpeas", 10, () -> GeoBlocks.CHICKPEA));
    public static final Item CUTTING_COTTON = make(new ItemSeed("cutting_cotton", 1, () -> GeoBlocks.COTTON));
    public static final Item CUTTING_HEMP =   make(new ItemSeed("cutting_hemp", 1, () -> GeoBlocks.HEMP));
    public static final Item WHEAT =          make(new ItemSeed("wheat", 10, () -> GeoBlocks.WHEAT, EntityCow.class, EntitySheep.class, EntityChicken.class));
    public static final Item SEED_MELON =     make(new ItemSeed("seeds_melon", 15, () -> GeoBlocks.MELON_CROP));

    // Decaying edible seeds
    public static final Item MUSHROOM_RED =   make(new ItemEdibleSeedDecayable("mushroom_red", 4, 4, 4, () -> GeoBlocks.MUSHROOMBABY_RED, EnumPlantType.Cave, EFoodType.FRUITVEG, 2));
    public static final Item MUSHROOM_BROWN = make(new ItemEdibleSeedDecayable("mushroom_brown", 2, 2, 9, () -> GeoBlocks.MUSHROOMBABY_BROWN, EnumPlantType.Cave, EFoodType.FRUITVEG, 2));
    public static final Item BEAN =           make(new ItemEdibleSeedDecayable("bean", 2, 2F, 10, () -> GeoBlocks.BEAN, EnumPlantType.Crop, EFoodType.FRUITVEG, 2));
    public static final Item PEPPER =         make(new ItemEdibleSeedDecayable("pepper", 3, 3F, 6, () -> GeoBlocks.PEPPER, EnumPlantType.Crop, EFoodType.FRUITVEG, 2));
    public static final Item TOMATO =         make(new ItemEdibleSeedDecayable("tomato", 3, 3F, 7, () -> GeoBlocks.TOMATO, EnumPlantType.Crop, EFoodType.FRUITVEG, 2));
    public static final Item BERRY =          make(new ItemEdibleSeedDecayable("berry", 1, 1F, 20, () -> GeoBlocks.BERRY, EnumPlantType.Crop, EFoodType.FRUITVEG, 2));
    public static final Item POTATO =         make(new ItemEdibleSeedDecayable("potato", 7, 7F, 5, () -> GeoBlocks.POTATO, EnumPlantType.Crop, EFoodType.CARBS, 4));
    public static final Item CARROT =         make(new ItemEdibleSeedDecayable("carrot", 3, 3F, 7, () -> GeoBlocks.CARROT, EnumPlantType.Crop, EFoodType.FRUITVEG, 4, EntityPig.class, EntityRabbit.class));
    public static final Item BEETROOT =       make(new ItemEdibleSeedDecayable("beetroot", 3, 3F, 6, () -> GeoBlocks.BEETROOT, EnumPlantType.Crop, EFoodType.FRUITVEG, 4));
    
    // Rice
    public static final Item RICE = make(new ItemRice());

    // Carry items
    public static final Item BACKPACK =   make(new ItemSimple("backpack", 1, CreativeTabs.TRANSPORTATION));
    public static final Item YOKE =       make(new ItemSimple("yoke", 1, CreativeTabs.TRANSPORTATION));

    // Loose heaping blockplacers
    public static final Item LOOSE_DIRT =     make(new ItemLooseplacing("loose_dirt", () -> Blocks.DIRT));
    public static final Item LOOSE_GRAVEL =   make(new ItemLooseplacing("loose_gravel", () -> Blocks.GRAVEL));
    public static final Item LOOSE_SAND =     make(new ItemLooseplacing("loose_sand", () -> Blocks.SAND));
    public static final Item LOOSE_CLAY =     make(new ItemLooseplacing("loose_clay", () -> Blocks.CLAY));
    
    // Floors
    public static final Item FLOOR_WOOD = make(new ItemFloor(6, ETypeFloor.WOOD));
    
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
    
    public static final Item MUDBRICKS =    make(new ItemSimple("mudbricks", 2));
    public static final Item DRESSEDSTONE = make(new ItemSimple("dressedstone", 2));

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
    
    public static final Item ARROWHEAD_BRONZE = make(new ItemSimple("arrowhead_bronze", 20));
    public static final Item ARROWHEAD_COPPER = make(new ItemSimple("arrowhead_copper", 20));
    public static final Item ARROWHEAD_FLINT =  make(new ItemSimple("arrowhead_flint", 20));
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
    
    public static final CreativeTabs TAB = new CreativeTabs("geomastery") {
        
        @Override
        public ItemStack getTabIconItem() {
            
            return new ItemStack(Items.FLINT);
        }
        
        @Override @SideOnly(Side.CLIENT)
        public void displayAllRelevantItems(NonNullList<ItemStack> list) {
            
            GeoItems.ITEMS.forEach((i) -> i.getSubItems(i, this, list));
        }
    };
    
    /** Adjusts vanilla items. */
    public static void preInit() {
        
        Geomastery.LOG.info("Adjusting vanilla item properties");
        Items.STICK.setMaxStackSize(12);
        Items.BONE.setMaxStackSize(6);
        Items.FLINT.setMaxStackSize(6);
        Items.LEATHER.setMaxStackSize(3);
        Items.COAL.setMaxStackSize(1);
        Items.RABBIT_HIDE.setMaxStackSize(4);
        Items.REEDS.setMaxStackSize(5);
        Items.BRICK.setMaxStackSize(1);
    }
    
    /** Puts the constructed item into the modelling set
     * and the offhand-only set if applicable. */
    private static <I extends Item> I make(I item, boolean isOffhandOnly) {
        
        if (isOffhandOnly) {
            
            GeoBlocks.addOffhandOnly(item);
        }

        ITEMS.add(item);
        return item;
    }
    
    private static <I extends Item> I make(I item) {
        
        return make(item, false);
    }
}

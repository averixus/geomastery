/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.main;

import java.util.stream.Collectors;
import jayavery.geomastery.crafting.CompostManager;
import jayavery.geomastery.crafting.CookingManager;
import jayavery.geomastery.crafting.CraftingManager;
import jayavery.geomastery.items.ItemEdibleDecayable;
import jayavery.geomastery.items.ItemSimple;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/** Stores and registers all Geomastery recipes and crafting/cooking managers. */
public class GeoRecipes {

    public static final CraftingManager INVENTORY = new CraftingManager();
    public static final CraftingManager KNAPPING = new CraftingManager();
    public static final CraftingManager WOODWORKING = new CraftingManager();
    public static final CraftingManager TEXTILES = new CraftingManager();
    public static final CraftingManager CANDLEMAKER = new CraftingManager();
    public static final CraftingManager FORGE = new CraftingManager();
    public static final CraftingManager MASON = new CraftingManager();
    public static final CraftingManager ARMOURER = new CraftingManager();
    public static final CraftingManager SAWPIT = new CraftingManager();
    
    // To use for recipe checks
    public static final CookingManager CAMPFIRE_ALL = new CookingManager(3);
    public static final CookingManager POTFIRE_ALL = new CookingManager(3);
    public static final CookingManager CLAY_ALL = new CookingManager(2);
    public static final CookingManager STONE_ALL = new CookingManager(1);
    
    // To define minimum recipe levels, e.g. for JEI
    public static final CookingManager CAMPFIRE_ONLY = new CookingManager(3);
    public static final CookingManager POTFIRE_ONLY = new CookingManager(3);
    public static final CookingManager CLAY_ONLY = new CookingManager(2);
    public static final CookingManager STONE_ONLY = new CookingManager(1);

    public static final CookingManager DRYING = new CookingManager(1);
    
    public static final CompostManager COMPOST = new CompostManager();

    /** All metals usable in crafting for reference in multiple recipes. */
    private static final Item[] METALS = {GeoItems.INGOT_COPPER, GeoItems.INGOT_TIN, GeoItems.INGOT_STEEL};
    /** All skins for reference in multiple recipes. */
    private static final Item[] SKINS = {GeoItems.SKIN_BEAR, GeoItems.SKIN_COW, GeoItems.SKIN_PIG, GeoItems.SKIN_SHEEP, GeoItems.SKIN_WOLF};
    /** All items that can be rot. */
    private static final Item[] ROTTABLES = GeoItems.ITEMS.stream().filter((i) -> i instanceof ItemEdibleDecayable).collect(Collectors.toSet()).toArray(new Item[0]);
    
    /** Cooking managers for campfire and higher furnace levels. */
    private static final CookingManager[] CAMPFIRE_PLUS = {CAMPFIRE_ONLY, CAMPFIRE_ALL, POTFIRE_ALL, CLAY_ALL, STONE_ALL};
    /** Cooking managers for cookfire and higher furnace levels. */
    private static final CookingManager[] POTFIRE_PLUS = {POTFIRE_ONLY, POTFIRE_ALL, CLAY_ALL, STONE_ALL};
    /** Cooking managers for clay furnace and higher furnace levels. */
    private static final CookingManager[] CLAY_PLUS = {CLAY_ONLY, CLAY_ALL, STONE_ALL};
    /** Cooking managers for stone furnace only. */
    private static final CookingManager[] STONE_PLUS = {STONE_ONLY, STONE_ALL};
    
    public static void init() {

        Geomastery.LOG.info("Setting up crafting and cooking recipes");
        setupInventory();
        setupKnapping();
        setupWoodworking();
        setupTextiles();
        setupCandlemaker();
        setupForge();
        setupMason();
        setupArmourer();
        setupSawpit();
        setupCampfire();
        setupPotfire();
        setupClay();
        setupStone();
        setupDrying();
        setupCompost();
    }
    
    /** Adds all recipes to inventory. */
    private static void setupInventory() {
        
        INVENTORY.addShapedRecipe(new ItemStack(GeoItems.HOE_ANTLER), "A", 'A', GeoItems.SHOVEL_ANTLER);
        INVENTORY.addShapedRecipe(new ItemStack(Items.FISHING_ROD), "SS ", " TT", 'S', Items.STICK, 'T', GeoItems.TWINE_HEMP);
        INVENTORY.addShapedRecipe(new ItemStack(GeoItems.SPEAR_WOOD), "S", "S", 'S', Items.STICK);
        INVENTORY.addShapedRecipe(new ItemStack(GeoItems.BOW_CRUDE), "S ", "ST", 'S', Items.STICK, 'T', GeoItems.TWINE_HEMP);
        INVENTORY.addShapedRecipe(new ItemStack(GeoItems.ARROW_WOOD, 1), "S", "F", 'S', Items.STICK, 'F', Items.FEATHER);
        INVENTORY.addShapedRecipe(new ItemStack(GeoItems.POT_CLAY), "C C", " C ", 'C', GeoItems.CLAY);
        INVENTORY.addShapedRecipe(new ItemStack(GeoBlocks.CRAFTING_KNAPPING.getItem()), "FFF", 'F', Items.FLINT);
        INVENTORY.addShapedRecipe(new ItemStack(GeoBlocks.CRAFTING_CANDLEMAKER.getItem()), "PPP", "PPP", 'P', GeoItems.POLE);
        INVENTORY.addShapedRecipe(new ItemStack(GeoBlocks.CRAFTING_TEXTILES.getItem()), "BPP", "PPP",'B', Items.BONE, 'P', GeoItems.POLE);
        INVENTORY.addShapedRecipe(new ItemStack(GeoBlocks.CRAFTING_SAWPIT.getItem()), "PSP", " S ", 'P', GeoItems.POLE, 'S', GeoItems.INGOT_STEEL);
        INVENTORY.addShapedRecipe(new ItemStack(GeoBlocks.FURNACE_CAMPFIRE.getItem()), "S S", "SSS", 'S', Items.STICK);
        INVENTORY.addShapedRecipe(new ItemStack(GeoBlocks.FURNACE_CLAY.getItem()), "C C", "MMM", 'C', GeoItems.LOOSE_CLAY, 'M', GeoItems.MUDBRICKS);
        INVENTORY.addShapedRecipe(new ItemStack(GeoBlocks.DRYING.getItem()), "PPP", 'P', GeoItems.POLE);
        INVENTORY.addShapedRecipe(new ItemStack(GeoBlocks.BED_LEAF.getItem()), "LLL", 'L', GeoItems.LEAVES);
        INVENTORY.addShapedRecipe(new ItemStack(GeoBlocks.WALL_MUD.getItem(), 2), "M", "M", 'M', GeoItems.MUDBRICKS);
        INVENTORY.addShapedRecipe(new ItemStack(GeoItems.CLAY, 6), "C", 'C', GeoItems.LOOSE_CLAY);
        INVENTORY.addShapedRecipe(new ItemStack(GeoItems.LOOSE_CLAY), "CCC", "CCC", 'C', GeoItems.CLAY);
        INVENTORY.addShapedRecipe(new ItemStack(GeoBlocks.FURNACE_POTFIRE.getItem()), "SPS", "SSS", 'S', Items.STICK, 'P', GeoItems.POT_CLAY);
        
        for (Item rottable : ROTTABLES) {
            
            INVENTORY.addShapedRecipe(new ItemStack(GeoBlocks.COMPOSTHEAP.getItem()), "PRP", "PPP", 'P', GeoItems.POLE, 'R', ItemSimple.rottenStack(rottable, 1));
        }

        for (Item skin : SKINS) {
            
            INVENTORY.addShapedRecipe(new ItemStack(GeoBlocks.BASKET.getItem()), "S S", "HSH", 'S', Items.STICK, 'H', skin);
        }
        
        for (Item metal : METALS) {
            
            INVENTORY.addShapedRecipe(new ItemStack(GeoBlocks.FURNACE_STONE.getItem()), "S S", "SMS", 'S', GeoBlocks.RUBBLE.getItem(), 'M', metal);
            INVENTORY.addShapedRecipe(new ItemStack(GeoBlocks.CRAFTING_TEXTILES.getItem()), "MPP", "PPP", 'M', metal, 'P', GeoItems.POLE);
            INVENTORY.addShapedRecipe(new ItemStack(GeoBlocks.CRAFTING_WOODWORKING.getItem()), "PMM", "PPP", 'P', GeoItems.POLE, 'M', metal);
            INVENTORY.addShapedRecipe(new ItemStack(GeoBlocks.CRAFTING_MASON.getItem()), "MSS", "PPP", 'M', metal, 'S', GeoBlocks.RUBBLE.getItem(), 'P', GeoItems.POLE);
            INVENTORY.addShapedRecipe(new ItemStack(GeoBlocks.CRAFTING_ARMOURER.getItem()), "MSP", "LBP", 'M', metal, 'S', GeoBlocks.RUBBLE.getItem(), 'P', GeoItems.POLE, 'L', Items.LEATHER, 'B', GeoItems.BEESWAX);
            INVENTORY.addShapedRecipe(new ItemStack(GeoBlocks.CRAFTING_ARMOURER.getItem()), "MSP", "LBP", 'M', metal, 'S', GeoBlocks.RUBBLE.getItem(), 'P', GeoItems.POLE, 'L', Items.LEATHER, 'B', GeoItems.TALLOW);

            for (Item metal2 : METALS) {
                
                INVENTORY.addShapedRecipe(new ItemStack(GeoBlocks.CRAFTING_FORGE.getItem()), "MNS", "PPP", 'M', metal, 'N', metal2, 'S', GeoBlocks.RUBBLE.getItem(), 'P', GeoItems.POLE);
            }
        }
    }

    /** Adds all recipes to knapping block. */
    private static void setupKnapping() {

        KNAPPING.addShapedRecipe(new ItemStack(GeoItems.HUNTINGKNIFE_FLINT), "F", "F", 'F', Items.FLINT);
        KNAPPING.addShapedRecipe(new ItemStack(GeoItems.AXE_FLINT), "F", "S", "S", 'F', GeoItems.AXEHEAD_FLINT, 'S', Items.STICK);
        KNAPPING.addShapedRecipe(new ItemStack(GeoItems.PICKAXE_FLINT),  "F", "S", "S", 'F', GeoItems.PICKHEAD_FLINT, 'S', Items.STICK);
        KNAPPING.addShapedRecipe(new ItemStack(GeoItems.SHEARS_FLINT), "F ", "FF", 'F', Items.FLINT);
        KNAPPING.addShapedRecipe(new ItemStack(GeoItems.SPEAR_FLINT), "F", "S", "S", 'F', GeoItems.SPEARHEAD_FLINT, 'S', Items.STICK);
        KNAPPING.addShapedRecipe(new ItemStack(GeoItems.ARROW_FLINT, 1), "F", "S", "E", 'F', GeoItems.ARROWHEAD_FLINT, 'S', Items.STICK, 'E', Items.FEATHER);
        KNAPPING.addShapedRecipe(new ItemStack(GeoBlocks.WALL_ROUGH.getItem(), 4), "R", "R", 'R', GeoBlocks.RUBBLE.getItem());
        KNAPPING.addShapedRecipe(new ItemStack(GeoItems.ARROWHEAD_FLINT), "F", 'F', Items.FLINT);
        KNAPPING.addShapedRecipe(new ItemStack(GeoItems.AXEHEAD_FLINT), "F ", "FF", "F ", 'F', Items.FLINT);
        KNAPPING.addShapedRecipe(new ItemStack(GeoItems.PICKHEAD_FLINT), " F ", "FFF", " F ", 'F', Items.FLINT);
        KNAPPING.addShapedRecipe(new ItemStack(GeoItems.SPEARHEAD_FLINT), " F ", " F ", "F F", 'F', Items.FLINT);
    }

    /** Adds all recipes to woodworking bench. */
    private static void setupWoodworking() {

        WOODWORKING.addShapedRecipe(new ItemStack(GeoItems.SHOVEL_WOOD), "P", "P", "P", 'P', GeoItems.POLE);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoItems.BOW_WAR), "P ", "TP", "P ", 'P', GeoItems.POLE, 'T', GeoItems.TWINE_HEMP);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoItems.HUNTINGKNIFE_COPPER), "B", "P", 'B', GeoItems.KNIFEBLADE_COPPER, 'P', GeoItems.POLE);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoItems.MACHETE_COPPER), "B", "P", 'B', GeoItems.MACHETEBLADE_COPPER, 'P', GeoItems.POLE);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoItems.PICKAXE_COPPER), "H", "P", "P", 'H', GeoItems.PICKHEAD_COPPER, 'P', GeoItems.POLE);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoItems.SPEAR_COPPER), "H", "P", "P", 'H', GeoItems.SPEARHEAD_COPPER, 'P', GeoItems.POLE);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoItems.AXE_COPPER), "H", "P", "P", 'H', GeoItems.AXEHEAD_COPPER, 'P', GeoItems.POLE);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoItems.HOE_COPPER), "H", "P", "P", 'H', GeoItems.HOEHEAD_COPPER, 'P', GeoItems.POLE);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoItems.SICKLE_COPPER), "B", "P", 'B', GeoItems.SICKLEBLADE_COPPER, 'P', GeoItems.POLE);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoItems.SHOVEL_COPPER), "H", "P", "P", 'H', GeoItems.SHOVELHEAD_COPPER, 'P', GeoItems.POLE);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoItems.ARROW_COPPER, 1), "A", "S", "F", 'A', GeoItems.ARROWHEAD_COPPER, 'S', Items.STICK, 'F', Items.FEATHER);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoItems.HUNTINGKNIFE_BRONZE), "B", "P", 'B', GeoItems.KNIFEBLADE_BRONZE, 'P', GeoItems.POLE);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoItems.MACHETE_BRONZE), "B", "P", 'B', GeoItems.MACHETEBLADE_BRONZE, 'P', GeoItems.POLE);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoItems.PICKAXE_BRONZE), "H", "P", "P", 'H', GeoItems.PICKHEAD_BRONZE, 'P', GeoItems.POLE);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoItems.SPEAR_BRONZE), "H", "P", "P", 'H', GeoItems.SPEARHEAD_BRONZE, 'P', GeoItems.POLE);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoItems.AXE_BRONZE), "H", "P", "P", 'H', GeoItems.AXEHEAD_BRONZE, 'P', GeoItems.POLE);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoItems.HOE_BRONZE), "H", "P", "P", 'H', GeoItems.HOEHEAD_BRONZE, 'P', GeoItems.POLE);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoItems.SICKLE_BRONZE), "B", "P", 'B', GeoItems.SICKLEBLADE_BRONZE, 'P', GeoItems.POLE);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoItems.SHOVEL_BRONZE), "H", "P", "P", 'H', GeoItems.SHOVELHEAD_BRONZE, 'P', GeoItems.POLE);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoItems.ARROW_BRONZE, 1), "A", "S", "F", 'A', GeoItems.ARROWHEAD_BRONZE, 'S', Items.STICK, 'F', Items.FEATHER);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoItems.HUNTINGKNIFE_STEEL), "B", "P", 'B', GeoItems.KNIFEBLADE_STEEL, 'P', GeoItems.POLE);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoItems.MACHETE_STEEL), "B", "P", 'B', GeoItems.MACHETEBLADE_STEEL, 'P', GeoItems.POLE);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoItems.PICKAXE_STEEL), "H", "P", "P", 'H', GeoItems.PICKHEAD_STEEL, 'P', GeoItems.POLE);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoItems.SPEAR_STEEL), "H", "P", "P", 'H', GeoItems.SPEARHEAD_STEEL, 'P', GeoItems.POLE);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoItems.AXE_STEEL), "H", "P", "P", 'H', GeoItems.AXEHEAD_STEEL, 'P', GeoItems.POLE);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoItems.HOE_STEEL), "H", "P", "P", 'H', GeoItems.HOEHEAD_STEEL, 'P', GeoItems.POLE);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoItems.SICKLE_STEEL), "B", "P", 'B', GeoItems.SICKLEBLADE_STEEL, 'P', GeoItems.POLE);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoItems.SHOVEL_STEEL), "H", "P", "P", 'H', GeoItems.SHOVELHEAD_STEEL, 'P', GeoItems.POLE);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoItems.ARROW_STEEL, 1), "A", "S", "F", 'A', GeoItems.ARROWHEAD_STEEL, 'S', Items.STICK, 'F', Items.FEATHER);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoItems.SHIELD_WOOD), " P ", "PPP", " P ", 'P', GeoItems.POLE);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoBlocks.WALL_POLE.getItem(), 4), "P", "P", 'P', GeoItems.POLE);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoBlocks.STAIRS_POLE.getItem(), 4), " P", "PP", 'P', GeoItems.POLE);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoBlocks.STAIRS_WOOD.getItem(), 4), " T", "TT", 'T', GeoItems.TIMBER);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoBlocks.DOOR_POLE.getItem()), "P", "P", "P", 'P', GeoItems.POLE);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoBlocks.DOOR_WOOD.getItem()), "S", "S", "S", 'S', GeoItems.TIMBER);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoBlocks.BOX.getItem()), "PTP", "PPP", 'P', GeoItems.POLE, 'T', GeoItems.TALLOW);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoBlocks.LADDER, 10), "P P", "PPP", "P P", 'P', GeoItems.POLE);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoBlocks.BED_SIMPLE.getItem()), "CCC", "WWW", "PPP", 'C', GeoItems.COTTON, 'W', GeoItems.WOOL, 'P', GeoItems.POLE);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoBlocks.PITCHROOF_CLAY.getItem(), 6), "S C", " S ", "  S", 'S', GeoItems.TIMBER, 'C', GeoItems.LOOSE_CLAY);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoBlocks.FRAME.getItem(), 10), "SSS", " S ", 'S', GeoItems.TIMBER);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoBlocks.VAULT_FRAME.getItem(), 12), "SS", "S ", 'S', GeoItems.TIMBER);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoBlocks.WINDOW.getItem(), 6), "GSG", "SSS", "GSG", 'S', GeoItems.TIMBER, 'G', GeoItems.GLASS);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoItems.FLOOR_WOOD, 6), "TTT", 'T', GeoItems.TIMBER);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoBlocks.FLATROOF_POLE.getItem(), 12), "LLL", "TTT", 'T', GeoItems.POLE, 'L', GeoItems.LEAVES);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoBlocks.WALL_LOG.getItem(), 4), "L", "L", 'L', GeoItems.LOG);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoBlocks.FENCE.getItem(), 10), "PPP", " P ", 'P', GeoItems.POLE);
        
        if (GeoConfig.compatibility.addCrafting) {
            
            WOODWORKING.addShapedRecipe(new ItemStack(Blocks.CRAFTING_TABLE), "SSS", "SSS", "SSS", 'S', GeoItems.TIMBER);
        }
        
        for (Item metal : METALS) {
            
            WOODWORKING.addShapedRecipe(new ItemStack(GeoBlocks.CHEST.getItem()), "SMS", "SSS", 'S', GeoItems.TIMBER, 'M', metal);
        }
    }

    /** Adds all recipes to textiles table. */
    private static void setupTextiles() {

        TEXTILES.addShapedRecipe(new ItemStack(GeoBlocks.BED_COTTON.getItem()), "CCC", "CCC", 'C', GeoItems.COTTON);
        TEXTILES.addShapedRecipe(new ItemStack(GeoBlocks.BED_WOOL.getItem()), "WWW", "WWW", 'W', GeoItems.WOOL);
        TEXTILES.addShapedRecipe(new ItemStack(GeoItems.COTTON_CHEST), "C C", "CCC", "CCC", 'C', GeoItems.COTTON);
        TEXTILES.addShapedRecipe(new ItemStack(GeoItems.COTTON_LEGS), "CCC", "C C", "C C", 'C', GeoItems.COTTON);
        TEXTILES.addShapedRecipe(new ItemStack(GeoItems.COTTON_HEAD), "CCC", "C C", 'C', GeoItems.COTTON);
        TEXTILES.addShapedRecipe(new ItemStack(GeoItems.COTTON_FEET), "C C", "C C", 'C', GeoItems.COTTON);
        TEXTILES.addShapedRecipe(new ItemStack(GeoItems.WOOL_CHEST), "W W", "WWW", "WWW", 'W', GeoItems.WOOL);
        TEXTILES.addShapedRecipe(new ItemStack(GeoItems.WOOL_LEGS), "WWW", "W W", "W W", 'W', GeoItems.WOOL);
        TEXTILES.addShapedRecipe(new ItemStack(GeoItems.WOOL_HEAD), "WWW", "W W", 'W', GeoItems.WOOL);
        TEXTILES.addShapedRecipe(new ItemStack(GeoItems.WOOL_FEET), "W W", "W W", 'W', GeoItems.WOOL);
        TEXTILES.addShapedRecipe(new ItemStack(GeoItems.LEATHER_CHEST), "L L", "LLL", "LLL", 'L', Items.LEATHER);
        TEXTILES.addShapedRecipe(new ItemStack(GeoItems.LEATHER_LEGS), "LLL", "L L", "L L", 'L', Items.LEATHER);
        TEXTILES.addShapedRecipe(new ItemStack(GeoItems.LEATHER_HEAD), "LLL", "L L", 'L', Items.LEATHER);
        TEXTILES.addShapedRecipe(new ItemStack(GeoItems.LEATHER_FEET), "L L", "L L", 'L', Items.LEATHER);
        TEXTILES.addShapedRecipe(new ItemStack(GeoItems.BACKPACK), "L L", "LLL", 'L', Items.LEATHER);
        TEXTILES.addShapedRecipe(new ItemStack(GeoItems.YOKE), "LPL", "LLL", 'L', Items.LEATHER, 'P', GeoItems.POLE);
        
        for (Item skin : new Item[] {GeoItems.SKIN_BEAR, GeoItems.SKIN_WOLF, GeoItems.SKIN_SHEEP}) {
            
            TEXTILES.addShapedRecipe(new ItemStack(GeoItems.FUR_HEAD), "SSS", "S S", 'S', skin);
            TEXTILES.addShapedRecipe(new ItemStack(GeoItems.FUR_CHEST), "S S", "SSS", "SSS", 'S', skin);
            TEXTILES.addShapedRecipe(new ItemStack(GeoItems.FUR_LEGS), "SSS", "S S", "S S", 'S', skin);
            TEXTILES.addShapedRecipe(new ItemStack(GeoItems.FUR_FEET), "S S", "S S", 'S', skin);
        }
    }

    /** Adds all recipes to candlemaker's bench. */
    private static void setupCandlemaker() {

        CANDLEMAKER.addShapedRecipe(new ItemStack(GeoBlocks.CANDLE_TALLOW.getItem(), 12), "T", "T", 'T', GeoItems.TALLOW);
        CANDLEMAKER.addShapedRecipe(new ItemStack(GeoBlocks.CANDLE_BEESWAX.getItem(), 12), "B", "B", 'B', GeoItems.BEESWAX);
        CANDLEMAKER.addShapedRecipe(new ItemStack(GeoBlocks.TORCH_TALLOW.getItem(), 3), "T", "S", 'T', GeoItems.TALLOW, 'S', Items.STICK);
        CANDLEMAKER.addShapedRecipe(new ItemStack(GeoBlocks.LAMP_CLAY.getItem(), 2), "H", "T", "C", 'H', GeoItems.TWINE_HEMP, 'T', GeoItems.TALLOW, 'C', GeoItems.CLAY);
    }

    /** Adds all recipes to forge. */
    private static void setupForge() {

        FORGE.addShapedRecipe(new ItemStack(GeoItems.SHEARS_COPPER), "C ", "CC", 'C', GeoItems.INGOT_COPPER);
        FORGE.addShapedRecipe(new ItemStack(GeoItems.SHEARS_BRONZE), "T ", "CC", 'T', GeoItems.INGOT_TIN, 'C', GeoItems.INGOT_COPPER);
        FORGE.addShapedRecipe(new ItemStack(GeoItems.SHEARS_STEEL), "S ", "SS", 'S', GeoItems.INGOT_STEEL);
        FORGE.addShapedRecipe(new ItemStack(GeoItems.KNIFEBLADE_COPPER, 2), "M", "M", 'M', GeoItems.INGOT_COPPER);
        FORGE.addShapedRecipe(new ItemStack(GeoItems.MACHETEBLADE_COPPER), "MMM", 'M', GeoItems.INGOT_COPPER);
        FORGE.addShapedRecipe(new ItemStack(GeoItems.PICKHEAD_COPPER), " M ", "MMM", " M ", 'M', GeoItems.INGOT_COPPER);
        FORGE.addShapedRecipe(new ItemStack(GeoItems.ARROWHEAD_COPPER, 24), "M ", " M", 'M', GeoItems.INGOT_COPPER);
        FORGE.addShapedRecipe(new ItemStack(GeoItems.SPEARHEAD_COPPER, 1), " M ", " M ", "M M", 'M', GeoItems.INGOT_COPPER);
        FORGE.addShapedRecipe(new ItemStack(GeoItems.AXEHEAD_COPPER), "M ", "MM", "M ", 'M', GeoItems.INGOT_COPPER);
        FORGE.addShapedRecipe(new ItemStack(GeoItems.HOEHEAD_COPPER), " M ", "MMM", 'M', GeoItems.INGOT_COPPER);
        FORGE.addShapedRecipe(new ItemStack(GeoItems.SICKLEBLADE_COPPER), "MM ", "  M", "  M", 'M', GeoItems.INGOT_COPPER);
        FORGE.addShapedRecipe(new ItemStack(GeoItems.SHOVELHEAD_COPPER), "MM", "MM", 'M', GeoItems.INGOT_COPPER);
        FORGE.addShapedRecipe(new ItemStack(GeoItems.SWORDBLADE_COPPER), "M", "M", "M", 'M', GeoItems.INGOT_COPPER);
        FORGE.addShapedRecipe(new ItemStack(GeoItems.KNIFEBLADE_BRONZE, 2), "T", "M", 'M', GeoItems.INGOT_COPPER, 'T', GeoItems.INGOT_TIN);
        FORGE.addShapedRecipe(new ItemStack(GeoItems.MACHETEBLADE_BRONZE), "TMM", 'M', GeoItems.INGOT_COPPER, 'T', GeoItems.INGOT_TIN);
        FORGE.addShapedRecipe(new ItemStack(GeoItems.PICKHEAD_BRONZE), " T ", "MMM", " M ", 'M', GeoItems.INGOT_COPPER, 'T', GeoItems.INGOT_TIN);
        FORGE.addShapedRecipe(new ItemStack(GeoItems.ARROWHEAD_BRONZE, 24), "T ", " M", 'M', GeoItems.INGOT_COPPER, 'T', GeoItems.INGOT_TIN);
        FORGE.addShapedRecipe(new ItemStack(GeoItems.SPEARHEAD_BRONZE, 1), " T ", " M ", "M M", 'M', GeoItems.INGOT_COPPER, 'T', GeoItems.INGOT_TIN);
        FORGE.addShapedRecipe(new ItemStack(GeoItems.AXEHEAD_BRONZE), "T ", "MM", "M ", 'M', GeoItems.INGOT_COPPER, 'T', GeoItems.INGOT_TIN);
        FORGE.addShapedRecipe(new ItemStack(GeoItems.HOEHEAD_BRONZE), " T ", "MMM", 'M', GeoItems.INGOT_COPPER, 'T', GeoItems.INGOT_TIN);
        FORGE.addShapedRecipe(new ItemStack(GeoItems.SICKLEBLADE_BRONZE), "TM ", "  M", "  M", 'M', GeoItems.INGOT_COPPER, 'T', GeoItems.INGOT_TIN);
        FORGE.addShapedRecipe(new ItemStack(GeoItems.SHOVELHEAD_BRONZE), "TM", "MM", 'M', GeoItems.INGOT_COPPER, 'T', GeoItems.INGOT_TIN);
        FORGE.addShapedRecipe(new ItemStack(GeoItems.SWORDBLADE_BRONZE), "T", "M", "M", 'M', GeoItems.INGOT_COPPER, 'T', GeoItems.INGOT_TIN);
        FORGE.addShapedRecipe(new ItemStack(GeoItems.KNIFEBLADE_STEEL, 2), "M", "M", 'M', GeoItems.INGOT_STEEL);
        FORGE.addShapedRecipe(new ItemStack(GeoItems.MACHETEBLADE_STEEL), "MMM", 'M', GeoItems.INGOT_STEEL);
        FORGE.addShapedRecipe(new ItemStack(GeoItems.PICKHEAD_STEEL), " M ", "MMM", " M ", 'M', GeoItems.INGOT_STEEL);
        FORGE.addShapedRecipe(new ItemStack(GeoItems.ARROWHEAD_STEEL, 24), "M ", " M", 'M', GeoItems.INGOT_STEEL);
        FORGE.addShapedRecipe(new ItemStack(GeoItems.SPEARHEAD_STEEL, 1),  " M ", " M ", "M M", 'M', GeoItems.INGOT_STEEL);
        FORGE.addShapedRecipe(new ItemStack(GeoItems.AXEHEAD_STEEL), "M ", "MM", "M ", 'M', GeoItems.INGOT_STEEL);
        FORGE.addShapedRecipe(new ItemStack(GeoItems.HOEHEAD_STEEL), " M ", "MMM", 'M', GeoItems.INGOT_STEEL);
        FORGE.addShapedRecipe(new ItemStack(GeoItems.SICKLEBLADE_STEEL), "MM ", "  M", "  M", 'M', GeoItems.INGOT_STEEL);
        FORGE.addShapedRecipe(new ItemStack(GeoItems.SHOVELHEAD_STEEL), "MM", "MM", 'M', GeoItems.INGOT_STEEL);
        FORGE.addShapedRecipe(new ItemStack(GeoItems.SWORDBLADE_STEEL), "M", "M", "M", 'M', GeoItems.INGOT_STEEL);

    }

    /** Adds all recipes to mason. */
    private static void setupMason() {
        
        MASON.addShapedRecipe(new ItemStack(GeoBlocks.WALL_BRICK.getItem(), 4), "B", "B", 'B', Items.BRICK);
        MASON.addShapedRecipe(new ItemStack(GeoBlocks.WALL_STONE.getItem(), 4), "S", "S", 'S', GeoItems.DRESSEDSTONE);
        MASON.addShapedRecipe(new ItemStack(GeoBlocks.STAIRS_STONE.getItem(), 4), " S", "SS", 'S', GeoItems.DRESSEDSTONE);
        MASON.addShapedRecipe(new ItemStack(GeoBlocks.STAIRS_BRICK.getItem(), 4), " B", "BB", 'B', Items.BRICK);
        MASON.addShapedRecipe(new ItemStack(GeoBlocks.VAULT_STONE.getItem(), 4), "SS", "S ", 'S', GeoItems.DRESSEDSTONE);
        MASON.addShapedRecipe(new ItemStack(GeoBlocks.VAULT_BRICK.getItem(), 4), "BB", "B ", 'B', Items.BRICK);
        MASON.addShapedRecipe(new ItemStack(GeoBlocks.SLAB_STONE.getItem(), 4), "SSS", 'S', GeoItems.DRESSEDSTONE);
        MASON.addShapedRecipe(new ItemStack(GeoBlocks.SLAB_BRICK.getItem(), 4), "BBB", 'B', Items.BRICK);
        MASON.addShapedRecipe(new ItemStack(GeoItems.DRESSEDSTONE, 4), "RRR", "RRR", "RRR", 'R', GeoBlocks.RUBBLE.getItem());
    }

    /** Adds all recipes to armourer. */
    private static void setupArmourer() {

        ARMOURER.addShapedRecipe(new ItemStack(GeoItems.SWORD_COPPER), "B", "L", "P", 'B', GeoItems.SWORDBLADE_COPPER, 'L', Items.LEATHER, 'P', GeoItems.POLE);
        ARMOURER.addShapedRecipe(new ItemStack(GeoItems.SWORD_BRONZE), "B", "L", "P", 'B', GeoItems.SWORDBLADE_BRONZE, 'L', Items.LEATHER, 'P', GeoItems.POLE);
        ARMOURER.addShapedRecipe(new ItemStack(GeoItems.SWORD_STEEL), "B", "L", "P", 'B', GeoItems.SWORDBLADE_STEEL, 'L', Items.LEATHER, 'P', GeoItems.POLE);
        ARMOURER.addShapedRecipe(new ItemStack(GeoItems.SHIELD_STEEL), "SP ", "PPP", " PS", 'S', GeoItems.INGOT_STEEL, 'P', GeoItems.POLE);
        ARMOURER.addShapedRecipe(new ItemStack(GeoItems.STEELMAIL_CHEST), "S S", "SSS", "SSS", 'S', GeoItems.INGOT_STEEL);
        ARMOURER.addShapedRecipe(new ItemStack(GeoItems.STEELMAIL_LEGS), "SSS", "S S", "S S", 'S', GeoItems.INGOT_STEEL);
        ARMOURER.addShapedRecipe(new ItemStack(GeoItems.STEELMAIL_FEET), "S S", "S S", 'S', GeoItems.INGOT_STEEL);
        ARMOURER.addShapedRecipe(new ItemStack(GeoItems.STEELMAIL_HEAD), "SSS", "S S", 'S', GeoItems.INGOT_STEEL);
        ARMOURER.addShapedRecipe(new ItemStack(GeoItems.STEELPLATE_CHEST), "SLS", "SSS", "SSS", 'S', GeoItems.INGOT_STEEL, 'L', Items.LEATHER);
        ARMOURER.addShapedRecipe(new ItemStack(GeoItems.STEELPLATE_LEGS), "SSS", "SLS", "S S", 'S', GeoItems.INGOT_STEEL, 'L', Items.LEATHER);
        ARMOURER.addShapedRecipe(new ItemStack(GeoItems.STEELPLATE_FEET), "S S", "SLS", 'S', GeoItems.INGOT_STEEL, 'L', Items.LEATHER);
        ARMOURER.addShapedRecipe(new ItemStack(GeoItems.STEELPLATE_HEAD), "SSS", "SLS", 'S', GeoItems.INGOT_STEEL, 'L', Items.LEATHER);
    }
    
    /** Adds all recipes to sawpit. */
    private static void setupSawpit() {
        
        SAWPIT.addShapedRecipe(new ItemStack(GeoItems.TIMBER, 6), "T", 'T', GeoItems.THICKLOG);
        SAWPIT.addShapedRecipe(new ItemStack(GeoItems.TIMBER, 3), "L", 'L', GeoItems.LOG);
        SAWPIT.addShapedRecipe(new ItemStack(GeoBlocks.BEAM_THIN.getItem()), "LL", 'L', GeoItems.LOG);
        SAWPIT.addShapedRecipe(new ItemStack(GeoBlocks.BEAM_THICK.getItem()), "TT", 'T', GeoItems.THICKLOG);
    }

    /** Adds all recipes to campfire and higher levels. */
    private static void setupCampfire() {

        for (CookingManager recipes : CAMPFIRE_PLUS) {
            
            recipes.addCookingRecipe(new ItemStack(GeoItems.BEEF_RAW), new ItemStack(GeoItems.BEEF_COOKED), 120);
            recipes.addCookingRecipe(new ItemStack(GeoItems.PORK_RAW), new ItemStack(GeoItems.PORK_COOKED), 100);
            recipes.addCookingRecipe(new ItemStack(GeoItems.MUTTON_RAW), new ItemStack(GeoItems.MUTTON_COOKED), 80);
            recipes.addCookingRecipe(new ItemStack(GeoItems.RABBIT_RAW), new ItemStack(GeoItems.RABBIT_COOKED), 40);
            recipes.addCookingRecipe(new ItemStack(GeoItems.CHICKEN_RAW), new ItemStack(GeoItems.CHICKEN_COOKED), 60);
            recipes.addCookingRecipe(new ItemStack(GeoItems.FISH_RAW), new ItemStack(GeoItems.FISH_COOKED), 40);
            recipes.addCookingRecipe(new ItemStack(GeoItems.POTATO), new ItemStack(GeoItems.POTATO_COOKED), 120);
            recipes.addFuel(new ItemStack(Items.STICK), 200);
            recipes.addFuel(new ItemStack(GeoItems.POLE), 500);
            recipes.addFuel(new ItemStack(GeoItems.LOG), 1000);
            recipes.addFuel(new ItemStack(GeoItems.THICKLOG), 2000);
        }
    }

    /** Adds all recipes to cookfire and higher levels. */
    private static void setupPotfire() {

        for (CookingManager recipes : POTFIRE_PLUS) {
            
            recipes.addCookingRecipe(new ItemStack(Items.REEDS), new ItemStack(GeoItems.SUGAR), 60);
            recipes.addCookingRecipe(new ItemStack(GeoItems.CHICKPEAS), new ItemStack(GeoItems.CHICKPEAS_BOILED), 60);
            recipes.addCookingRecipe(new ItemStack(GeoItems.RICE), new ItemStack(GeoItems.RICE_BOILED), 60);
        }
        
        POTFIRE_ALL.addCookingRecipe(new ItemStack(GeoItems.WHEAT), new ItemStack(GeoItems.WHEAT_BOILED), 60);
    }

    /** Adds all recipes to clay furnace and higher levels. */
    private static void setupClay() {

        for (CookingManager recipes : CLAY_PLUS) {
            
            recipes.addCookingRecipe(new ItemStack(GeoItems.WHEAT), new ItemStack(GeoItems.BREAD), 60);
            recipes.addCookingRecipe(new ItemStack(GeoItems.ORE_COPPER), new ItemStack(GeoItems.INGOT_COPPER), 300);
            recipes.addCookingRecipe(new ItemStack(GeoItems.ORE_TIN), new ItemStack(GeoItems.INGOT_TIN), 300);
            recipes.addCookingRecipe(new ItemStack(GeoItems.POLE), new ItemStack(Items.COAL, 1, 1),60);
            recipes.addCookingRecipe(new ItemStack(GeoItems.LOG), new ItemStack(Items.COAL, 3, 1), 180);
            recipes.addCookingRecipe(new ItemStack(GeoItems.THICKLOG), new ItemStack(Items.COAL, 6, 1), 360);
            recipes.addFuel(new ItemStack(GeoItems.PEAT_DRY), 3000);
            recipes.addFuel(new ItemStack(Items.COAL, 1, 1), 4000);
        }
    }

    /** Adds all recipes to stone furnace and higher levels. */
    private static void setupStone() {
        
        for (CookingManager recipes : STONE_PLUS) {

            recipes.addCookingRecipe(new ItemStack(GeoItems.ORE_IRON), new ItemStack(GeoItems.INGOT_STEEL), 400);
            recipes.addCookingRecipe(new ItemStack(GeoItems.ORE_SILVER), new ItemStack(GeoItems.INGOT_SILVER), 300);
            recipes.addCookingRecipe(new ItemStack(GeoItems.ORE_GOLD), new ItemStack(Items.GOLD_INGOT), 200);
            recipes.addCookingRecipe(new ItemStack(GeoItems.LOOSE_SAND), new ItemStack(GeoItems.GLASS), 200);
            recipes.addCookingRecipe(new ItemStack(GeoItems.CLAY), new ItemStack(Items.BRICK, 4), 200);
            recipes.addFuel(new ItemStack(Items.COAL, 1, 0), 5000);
        }
    }

    /** Adds all recipes to drying rack. */
    private static void setupDrying() {

        DRYING.addCookingRecipe(new ItemStack(GeoItems.LOOSE_DIRT), new ItemStack(GeoItems.MUDBRICKS), 4000);
        DRYING.addCookingRecipe(new ItemStack(GeoItems.PEAT_WET), new ItemStack(GeoItems.PEAT_DRY), 4000);
        
        for (Item skin : SKINS) {
            
            DRYING.addCookingRecipe(new ItemStack(skin), new ItemStack(Items.LEATHER), 4000);
        }
    }
    
    /** Adds all recipes to compost heap. */
    private static void setupCompost() {
        
        COMPOST.addWet(GeoItems.ITEMS.stream().filter((i) -> i instanceof ItemEdibleDecayable).map(ItemStack::new).collect(Collectors.toSet()).toArray(new ItemStack[0]));
        COMPOST.addWet(new ItemStack(GeoItems.WOOL), new ItemStack(Items.BONE));
        COMPOST.addDry(new ItemStack(GeoItems.LEAVES), new ItemStack(Items.STICK), new ItemStack(GeoItems.LOG), new ItemStack(GeoItems.POLE), new ItemStack(GeoItems.THICKLOG), new ItemStack(Items.REEDS), new ItemStack(Blocks.RED_FLOWER), new ItemStack(Blocks.YELLOW_FLOWER));
    }
}

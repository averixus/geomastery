package jayavery.geomastery.main;

import jayavery.geomastery.crafting.CookingManager;
import jayavery.geomastery.crafting.CraftingManager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

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
    
    public static final CookingManager CAMPFIRE = new CookingManager(3);
    public static final CookingManager COOKFIRE = new CookingManager(3);
    public static final CookingManager CLAY = new CookingManager(2);
    public static final CookingManager STONE = new CookingManager(1);

    public static final CookingManager DRYING = new CookingManager(1);

    /** All metals usable in crafting for reference in multiple recipes. */
    private static final Item[] METALS = {GeoItems.ingotCopper, GeoItems.ingotTin, GeoItems.ingotSteel};
    /** All skins for reference in multiple recipes. */
    private static final Item[] SKINS = {GeoItems.skinBear, GeoItems.skinCow, GeoItems.skinPig, GeoItems.skinSheep, GeoItems.skinWolf};
    
    /** Cooking managers for campfire and higher furnace levels. */
    private static final CookingManager[] CAMPFIRE_PLUS = {CAMPFIRE, COOKFIRE, CLAY, STONE};
    /** Cooking managers for cookfire and higher furnace levels. */
    private static final CookingManager[] COOKFIRE_PLUS = {COOKFIRE, CLAY, STONE};
    /** Cooking managers for clay furnace and higher furnace levels. */
    private static final CookingManager[] CLAY_PLUS = {CLAY, STONE};
    
    /** Placeholder item to define when recipes need any rotten item. */
    public static final Item rot = new Item();
    
    public static void init() {

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
        setupCookfire();
        setupClay();
        setupStone();
        setupDrying();
    }
    
    /** Adds all recipes to inventory. */
    private static void setupInventory() {
        
        INVENTORY.addShapedRecipe(new ItemStack(GeoItems.hoeAntler), "A", 'A', GeoItems.shovelAntler);
        INVENTORY.addShapedRecipe(new ItemStack(Items.FISHING_ROD), "SS ", " TT", 'S', Items.STICK, 'T', GeoItems.twineHemp);
        INVENTORY.addShapedRecipe(new ItemStack(GeoItems.spearWood), "S", "S", 'S', Items.STICK);
        INVENTORY.addShapedRecipe(new ItemStack(GeoItems.bowCrude), "S ", "ST", 'S', Items.STICK, 'T', GeoItems.twineHemp);
        INVENTORY.addShapedRecipe(new ItemStack(GeoItems.arrowWood, 5), "S", "F", 'S', Items.STICK, 'F', Items.FEATHER);
        INVENTORY.addShapedRecipe(new ItemStack(GeoItems.potClay), "C C", " C ", 'C', GeoItems.clay);
        INVENTORY.addShapedRecipe(new ItemStack(GeoBlocks.craftingKnapping), "FFF", 'F', Items.FLINT);
        INVENTORY.addShapedRecipe(new ItemStack(GeoItems.craftingCandlemaker), "PPP", "PPP", 'P', GeoItems.pole);
        INVENTORY.addShapedRecipe(new ItemStack(GeoItems.craftingTextiles), "BPP", "PPP",'B', Items.BONE, 'P', GeoItems.pole);
        INVENTORY.addShapedRecipe(new ItemStack(GeoItems.craftingSawpit), "PSP", " S ", 'P', GeoItems.pole, 'S', GeoItems.ingotSteel);
        INVENTORY.addShapedRecipe(new ItemStack(GeoBlocks.furnaceCampfire), "S S", "SSS", 'S', Items.STICK);
        INVENTORY.addShapedRecipe(new ItemStack(GeoItems.furnaceClay), "C C", "MMM", 'C', GeoItems.looseClay, 'M', GeoItems.mudbricks);
        INVENTORY.addShapedRecipe(new ItemStack(GeoBlocks.drying), "PPP", 'P', GeoItems.pole);
        INVENTORY.addShapedRecipe(new ItemStack(GeoItems.bedLeaf), "LLL", 'L', GeoItems.leaves);
        INVENTORY.addShapedRecipe(new ItemStack(GeoItems.wallMud, 2), "M", "M", 'M', GeoItems.mudbricks);
        INVENTORY.addShapedRecipe(new ItemStack(GeoItems.clay, 6), "C", 'C', GeoItems.looseClay);
        INVENTORY.addShapedRecipe(new ItemStack(GeoItems.looseClay), "CCC", "CCC", 'C', GeoItems.clay);
        INVENTORY.addShapedRecipe(new ItemStack(GeoBlocks.compostheap), "PRP", "PPP", 'P', GeoItems.pole, 'R', rot);
        
        for (Item skin : SKINS) {
            
            INVENTORY.addShapedRecipe(new ItemStack(GeoBlocks.basket), "SHS", " S ", 'S', Items.STICK, 'H', skin);
        }
        
        for (Item metal : METALS) {
            
            INVENTORY.addShapedRecipe(new ItemStack(GeoItems.furnaceStone), "S S", "SMS", 'S', GeoItems.rubble, 'M', metal);
            INVENTORY.addShapedRecipe(new ItemStack(GeoItems.craftingTextiles), "MPP", "PPP", 'M', metal, 'P', GeoItems.pole);
            INVENTORY.addShapedRecipe(new ItemStack(GeoItems.craftingWoodworking), "PMM", "PPP", 'P', GeoItems.pole, 'M', metal);
            INVENTORY.addShapedRecipe(new ItemStack(GeoItems.craftingMason), "MSS", "PPP", 'M', metal, 'S', GeoItems.rubble, 'P', GeoItems.pole);
            INVENTORY.addShapedRecipe(new ItemStack(GeoItems.craftingArmourer), "MSP", "LBP", 'M', metal, 'S', GeoItems.rubble, 'P', GeoItems.pole, 'L', Items.LEATHER, 'B', GeoItems.beeswax);
            INVENTORY.addShapedRecipe(new ItemStack(GeoItems.craftingArmourer), "MSP", "LBP", 'M', metal, 'S', GeoItems.rubble, 'P', GeoItems.pole, 'L', Items.LEATHER, 'B', GeoItems.tallow);

            for (Item metal2 : METALS) {
                
                INVENTORY.addShapedRecipe(new ItemStack(GeoItems.craftingForge), "MNS", "PPP", 'M', metal, 'N', metal2, 'S', GeoItems.rubble, 'P', GeoItems.pole);
            }
        }
        
        for (Item pot : new Item[] {GeoItems.potClay, GeoItems.potMetal}) {
            
            INVENTORY.addShapedRecipe(new ItemStack(GeoBlocks.furnacePotfire), "SPS", "SSS", 'S', Items.STICK, 'P', pot);
        }
    }

    /** Adds all recipes to knapping block. */
    private static void setupKnapping() {

        KNAPPING.addShapedRecipe(new ItemStack(GeoItems.huntingknifeFlint), "F", "F", 'F', Items.FLINT);
        KNAPPING.addShapedRecipe(new ItemStack(GeoItems.axeFlint), "F", "S", "S", 'F', GeoItems.axeheadFlint, 'S', Items.STICK);
        KNAPPING.addShapedRecipe(new ItemStack(GeoItems.pickaxeFlint),  "F", "S", "S", 'F', GeoItems.pickheadFlint, 'S', Items.STICK);
        KNAPPING.addShapedRecipe(new ItemStack(GeoItems.shearsFlint), "F ", "FF", 'F', Items.FLINT);
        KNAPPING.addShapedRecipe(new ItemStack(GeoItems.spearFlint), "F", "S", "S", 'F', GeoItems.spearheadFlint, 'S', Items.STICK);
        KNAPPING.addShapedRecipe(new ItemStack(GeoItems.arrowFlint, 5), "F", "S", "E", 'F', GeoItems.arrowheadFlint, 'S', Items.STICK, 'E', Items.FEATHER);
        KNAPPING.addShapedRecipe(new ItemStack(GeoItems.wallRough, 2), "R", "R", 'R', GeoItems.rubble);
        KNAPPING.addShapedRecipe(new ItemStack(GeoItems.arrowheadFlint), "F", 'F', Items.FLINT);
        KNAPPING.addShapedRecipe(new ItemStack(GeoItems.axeheadFlint), "F ", "FF", "F ", 'F', Items.FLINT);
        KNAPPING.addShapedRecipe(new ItemStack(GeoItems.pickheadFlint), " F ", "FFF", " F ", 'F', Items.FLINT);
        KNAPPING.addShapedRecipe(new ItemStack(GeoItems.spearheadFlint), " F ", " F ", "F F", 'F', Items.FLINT);
    }

    /** Adds all recipes to woodworking bench. */
    private static void setupWoodworking() {

        WOODWORKING.addShapedRecipe(new ItemStack(GeoItems.shovelWood), "P", "P", "P", 'P', GeoItems.pole);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoItems.bowWar), "P ", "TP", "P ", 'P', GeoItems.pole, 'T', GeoItems.twineHemp);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoItems.huntingknifeCopper), "B", "P", 'B', GeoItems.knifebladeCopper, 'P', GeoItems.pole);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoItems.macheteCopper), "B", "P", 'B', GeoItems.machetebladeCopper, 'P', GeoItems.pole);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoItems.pickaxeCopper), "H", "P", "P", 'H', GeoItems.pickheadCopper, 'P', GeoItems.pole);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoItems.spearCopper), "H", "P", "P", 'H', GeoItems.spearheadCopper, 'P', GeoItems.pole);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoItems.axeCopper), "H", "P", "P", 'H', GeoItems.axeheadCopper, 'P', GeoItems.pole);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoItems.hoeCopper), "H", "P", "P", 'H', GeoItems.hoeheadCopper, 'P', GeoItems.pole);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoItems.sickleCopper), "B", "P", 'B', GeoItems.sicklebladeCopper, 'P', GeoItems.pole);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoItems.shovelCopper), "HPP", 'H', GeoItems.shovelheadCopper, 'P', GeoItems.pole);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoItems.arrowCopper, 5), "A", "S", "F", 'A', GeoItems.arrowheadCopper, 'S', Items.STICK, 'F', Items.FEATHER);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoItems.huntingknifeBronze), "B", "P", 'B', GeoItems.knifebladeBronze, 'P', GeoItems.pole);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoItems.macheteBronze), "B", "P", 'B', GeoItems.machetebladeBronze, 'P', GeoItems.pole);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoItems.pickaxeBronze), "H", "P", "P", 'H', GeoItems.pickheadBronze, 'P', GeoItems.pole);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoItems.spearBronze), "H", "P", "P", 'H', GeoItems.spearheadBronze, 'P', GeoItems.pole);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoItems.axeBronze), "H", "P", "P", 'H', GeoItems.axeheadBronze, 'P', GeoItems.pole);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoItems.hoeBronze), "H", "P", "P", 'H', GeoItems.hoeheadBronze, 'P', GeoItems.pole);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoItems.sickleBronze), "B", "P", 'B', GeoItems.sicklebladeBronze, 'P', GeoItems.pole);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoItems.shovelBronze), "H", "P", "P", 'H', GeoItems.shovelheadBronze, 'P', GeoItems.pole);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoItems.arrowBronze, 5), "A", "S", "F", 'A', GeoItems.arrowheadBronze, 'S', Items.STICK, 'F', Items.FEATHER);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoItems.huntingknifeSteel), "B", "P", 'B', GeoItems.knifebladeSteel, 'P', GeoItems.pole);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoItems.macheteSteel), "B", "P", 'B', GeoItems.machetebladeSteel, 'P', GeoItems.pole);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoItems.pickaxeSteel), "H", "P", "P", 'H', GeoItems.pickheadSteel, 'P', GeoItems.pole);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoItems.spearSteel), "H", "P", "P", 'H', GeoItems.spearheadSteel, 'P', GeoItems.pole);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoItems.axeSteel), "H", "P", "P", 'H', GeoItems.axeheadSteel, 'P', GeoItems.pole);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoItems.hoeSteel), "H", "P", "P", 'H', GeoItems.hoeheadSteel, 'P', GeoItems.pole);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoItems.sickleSteel), "B", "P", 'B', GeoItems.sicklebladeSteel, 'P', GeoItems.pole);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoItems.shovelSteel), "H", "P", "P", 'H', GeoItems.shovelheadSteel, 'P', GeoItems.pole);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoItems.arrowSteel, 5), "A", "S", "F", 'A', GeoItems.arrowheadSteel, 'S', Items.STICK, 'F', Items.FEATHER);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoItems.shieldWood), " P ", "PPP", " P ", 'P', GeoItems.pole);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoBlocks.wallPole, 2), "PPP", "PPP", 'P', GeoItems.pole);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoBlocks.stairsPole, 2), "  P", " PP", "PPP", 'P', GeoItems.pole);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoBlocks.stairsWood, 2), "  T", " TT", "TTT", 'T', GeoItems.timber);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoItems.doorPole), "PP", "PP", "PP", 'P', GeoItems.pole);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoItems.doorWood), "SS", "SS", "SS", 'S', GeoItems.timber);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoBlocks.box), "PTP", "PPP", 'P', GeoItems.pole, 'T', GeoItems.tallow);
        WOODWORKING.addShapedRecipe(new ItemStack(Blocks.LADDER, 8), "P P", "PPP", "P P", 'P', GeoItems.pole);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoItems.bedSimple), "WWW", "PPP", 'W', GeoItems.wool, 'P', GeoItems.pole);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoBlocks.pitchroofClay, 2), "SC ", " SC", "  S", 'S', GeoItems.timber, 'C', GeoItems.clay);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoBlocks.frame, 3), "SSS", " S ", 'S', GeoItems.timber);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoBlocks.vaultFrame, 3), "S  ", " S ", "  S", 'S', GeoItems.timber);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoBlocks.window, 4), "S S", " G ", "S S", 'S', GeoItems.timber, 'G', GeoItems.glass);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoItems.floorPole, 4), "PPP", 'P', GeoItems.pole);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoItems.floorWood, 4), "TTT", 'T', GeoItems.timber);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoBlocks.flatroofPole, 2), "TTT", "T T", 'T', GeoItems.pole);
        WOODWORKING.addShapedRecipe(new ItemStack(GeoBlocks.wallLog, 4), "LLL", "LLL", 'L', GeoItems.log);
        
        for (Item metal : METALS) {
            
            WOODWORKING.addShapedRecipe(new ItemStack(Blocks.CHEST), "SSS", "SMS", "SSS", 'S', GeoItems.timber, 'M', metal);
        }
    }

    /** Adds all recipes to textiles table. */
    private static void setupTextiles() {

        TEXTILES.addShapedRecipe(new ItemStack(GeoItems.bedCotton), "CCC", "CCC", 'C', GeoItems.cotton);
        TEXTILES.addShapedRecipe(new ItemStack(GeoItems.bedWool), "WWW", "WWW", 'W', GeoItems.wool);
        TEXTILES.addShapedRecipe(new ItemStack(GeoItems.cottonChest), "C C", "CCC", "CCC", 'C', GeoItems.cotton);
        TEXTILES.addShapedRecipe(new ItemStack(GeoItems.cottonLegs), "CCC", "C C", "C C", 'C', GeoItems.cotton);
        TEXTILES.addShapedRecipe(new ItemStack(GeoItems.cottonHead), "CCC", "C C", 'C', GeoItems.cotton);
        TEXTILES.addShapedRecipe(new ItemStack(GeoItems.cottonFeet), "C C", "C C", 'C', GeoItems.cotton);
        TEXTILES.addShapedRecipe(new ItemStack(GeoItems.woolChest), "W W", "WWW", "WWW", 'W', GeoItems.wool);
        TEXTILES.addShapedRecipe(new ItemStack(GeoItems.woolLegs), "WWW", "W W", "W W", 'W', GeoItems.wool);
        TEXTILES.addShapedRecipe(new ItemStack(GeoItems.woolHead), "WWW", "W W", 'W', GeoItems.wool);
        TEXTILES.addShapedRecipe(new ItemStack(GeoItems.woolFeet), "W W", "W W", 'W', GeoItems.wool);
        TEXTILES.addShapedRecipe(new ItemStack(GeoItems.leatherChest), "L L", "LLL", "LLL", 'L', Items.LEATHER);
        TEXTILES.addShapedRecipe(new ItemStack(GeoItems.leatherLegs), "LLL", "L L", "L L", 'L', Items.LEATHER);
        TEXTILES.addShapedRecipe(new ItemStack(GeoItems.leatherHead), "LLL", "L L", 'L', Items.LEATHER);
        TEXTILES.addShapedRecipe(new ItemStack(GeoItems.leatherFeet), "L L", "L L", 'L', Items.LEATHER);
        TEXTILES.addShapedRecipe(new ItemStack(GeoItems.backpack), "L L", "LLL", 'L', Items.LEATHER);
        TEXTILES.addShapedRecipe(new ItemStack(GeoItems.yoke), "LPL", "LLL", 'L', Items.LEATHER, 'P', GeoItems.pole);
        
        for (Item skin : new Item[] {GeoItems.skinBear, GeoItems.skinWolf, GeoItems.skinSheep}) {
            
            TEXTILES.addShapedRecipe(new ItemStack(GeoItems.furHead), "SSS", "S S", 'S', skin);
            TEXTILES.addShapedRecipe(new ItemStack(GeoItems.furChest), "S S", "SSS", "SSS", 'S', skin);
            TEXTILES.addShapedRecipe(new ItemStack(GeoItems.furLegs), "SSS", "S S", "S S", 'S', skin);
            TEXTILES.addShapedRecipe(new ItemStack(GeoItems.furFeet), "S S", "S S", 'S', skin);
        }
    }

    /** Adds all recipes to candlemaker's bench. */
    private static void setupCandlemaker() {

        CANDLEMAKER.addShapedRecipe(new ItemStack(GeoBlocks.candleTallow, 12), "T", "T", 'T', GeoItems.tallow);
        CANDLEMAKER.addShapedRecipe(new ItemStack(GeoBlocks.candleBeeswax, 12), "B", "B", 'B', GeoItems.beeswax);
        CANDLEMAKER.addShapedRecipe(new ItemStack(GeoBlocks.torchTallow, 4), "T", "S", 'T', GeoItems.tallow, 'S', Items.STICK);
        CANDLEMAKER.addShapedRecipe(new ItemStack(GeoBlocks.lampClay), "H", "T", "C", 'H', GeoItems.twineHemp, 'T', GeoItems.tallow, 'C', GeoItems.clay);
    }

    /** Adds all recipes to forge. */
    private static void setupForge() {

        FORGE.addShapedRecipe(new ItemStack(GeoItems.shearsCopper), "C ", "CC", 'C', GeoItems.ingotCopper);
        FORGE.addShapedRecipe(new ItemStack(GeoItems.shearsBronze), "T ", "CC", 'T', GeoItems.ingotTin, 'C', GeoItems.ingotCopper);
        FORGE.addShapedRecipe(new ItemStack(GeoItems.shearsSteel), "S ", "SS", 'S', GeoItems.ingotSteel);
        FORGE.addShapedRecipe(new ItemStack(GeoItems.potMetal), "M M", " M ", 'M', GeoItems.ingotCopper);
        FORGE.addShapedRecipe(new ItemStack(GeoItems.potMetal), "M M", " M ", 'M', GeoItems.ingotSteel);
        FORGE.addShapedRecipe(new ItemStack(GeoItems.knifebladeCopper, 2), "M", "M", 'M', GeoItems.ingotCopper);
        FORGE.addShapedRecipe(new ItemStack(GeoItems.machetebladeCopper), "MMM", 'M', GeoItems.ingotCopper);
        FORGE.addShapedRecipe(new ItemStack(GeoItems.pickheadCopper), " M ", "MMM", " M ", 'M', GeoItems.ingotCopper);
        FORGE.addShapedRecipe(new ItemStack(GeoItems.arrowheadCopper, 24), "M ", " M", 'M', GeoItems.ingotCopper);
        FORGE.addShapedRecipe(new ItemStack(GeoItems.spearheadCopper, 1), " M ", " M ", "M M", 'M', GeoItems.ingotCopper);
        FORGE.addShapedRecipe(new ItemStack(GeoItems.axeheadCopper), "M ", "MM", "M ", 'M', GeoItems.ingotCopper);
        FORGE.addShapedRecipe(new ItemStack(GeoItems.hoeheadCopper), " M ", "MMM", 'M', GeoItems.ingotCopper);
        FORGE.addShapedRecipe(new ItemStack(GeoItems.sicklebladeCopper), "MM ", "  M", "  M", 'M', GeoItems.ingotCopper);
        FORGE.addShapedRecipe(new ItemStack(GeoItems.shovelheadCopper), "MM", "MM", 'M', GeoItems.ingotCopper);
        FORGE.addShapedRecipe(new ItemStack(GeoItems.swordbladeCopper), "M", "M", "M", 'M', GeoItems.ingotCopper);
        FORGE.addShapedRecipe(new ItemStack(GeoItems.knifebladeBronze, 2), "T", "M", 'M', GeoItems.ingotCopper, 'T', GeoItems.ingotTin);
        FORGE.addShapedRecipe(new ItemStack(GeoItems.machetebladeBronze), "TMM", 'M', GeoItems.ingotCopper, 'T', GeoItems.ingotTin);
        FORGE.addShapedRecipe(new ItemStack(GeoItems.pickheadBronze), " T ", "MMM", " M ", 'M', GeoItems.ingotCopper, 'T', GeoItems.ingotTin);
        FORGE.addShapedRecipe(new ItemStack(GeoItems.arrowheadBronze, 24), "T ", " M", 'M', GeoItems.ingotCopper, 'T', GeoItems.ingotTin);
        FORGE.addShapedRecipe(new ItemStack(GeoItems.spearheadBronze, 1), " T ", " M ", "M M", 'M', GeoItems.ingotCopper, 'T', GeoItems.ingotTin);
        FORGE.addShapedRecipe(new ItemStack(GeoItems.axeheadBronze), "T ", "MM", "M ", 'M', GeoItems.ingotCopper, 'T', GeoItems.ingotTin);
        FORGE.addShapedRecipe(new ItemStack(GeoItems.hoeheadBronze), " T ", "MMM", 'M', GeoItems.ingotCopper, 'T', GeoItems.ingotTin);
        FORGE.addShapedRecipe(new ItemStack(GeoItems.sicklebladeBronze), "TM ", "  M", "  M", 'M', GeoItems.ingotCopper, 'T', GeoItems.ingotTin);
        FORGE.addShapedRecipe(new ItemStack(GeoItems.shovelheadBronze), "TM", "MM", 'M', GeoItems.ingotCopper, 'T', GeoItems.ingotTin);
        FORGE.addShapedRecipe(new ItemStack(GeoItems.swordbladeBronze), "T", "M", "M", 'M', GeoItems.ingotCopper, 'T', GeoItems.ingotTin);
        FORGE.addShapedRecipe(new ItemStack(GeoItems.knifebladeSteel, 2), "M", "M", 'M', GeoItems.ingotSteel);
        FORGE.addShapedRecipe(new ItemStack(GeoItems.machetebladeSteel), "MMM", 'M', GeoItems.ingotSteel);
        FORGE.addShapedRecipe(new ItemStack(GeoItems.pickheadSteel), " M ", "MMM", " M ", 'M', GeoItems.ingotSteel);
        FORGE.addShapedRecipe(new ItemStack(GeoItems.arrowheadSteel, 24), "M ", " M", 'M', GeoItems.ingotSteel);
        FORGE.addShapedRecipe(new ItemStack(GeoItems.spearheadSteel, 1),  " M ", " M ", "M M", 'M', GeoItems.ingotSteel);
        FORGE.addShapedRecipe(new ItemStack(GeoItems.axeheadSteel), "M ", "MM", "M ", 'M', GeoItems.ingotSteel);
        FORGE.addShapedRecipe(new ItemStack(GeoItems.hoeheadSteel), " M ", "MMM", 'M', GeoItems.ingotSteel);
        FORGE.addShapedRecipe(new ItemStack(GeoItems.sicklebladeSteel), "MM ", "  M", "  M", 'M', GeoItems.ingotSteel);
        FORGE.addShapedRecipe(new ItemStack(GeoItems.shovelheadSteel), "MM", "MM", 'M', GeoItems.ingotSteel);
        FORGE.addShapedRecipe(new ItemStack(GeoItems.swordbladeSteel), "M", "M", "M", 'M', GeoItems.ingotSteel);

    }

    /** Adds all recipes to mason. */
    private static void setupMason() {
        
        MASON.addShapedRecipe(new ItemStack(GeoItems.wallBrick, 2), "B", "B", 'B', Items.BRICK);
        MASON.addShapedRecipe(new ItemStack(GeoItems.wallStone, 2), "S", "S", 'S', GeoItems.stoneDressed);
        MASON.addShapedRecipe(new ItemStack(GeoBlocks.stairsStone, 2), " S", "SS", 'S', GeoItems.stoneDressed);
        MASON.addShapedRecipe(new ItemStack(GeoBlocks.stairsBrick, 2), " B", "BB", 'B', Items.BRICK);
        MASON.addShapedRecipe(new ItemStack(GeoItems.vaultStone, 2), "SS", "S ", 'S', GeoItems.stoneDressed);
        MASON.addShapedRecipe(new ItemStack(GeoItems.vaultBrick, 2), "BB", "B ", 'B', Items.BRICK);
        MASON.addShapedRecipe(new ItemStack(GeoItems.slabStone, 2), "SSS", 'S', GeoItems.stoneDressed);
        MASON.addShapedRecipe(new ItemStack(GeoItems.slabBrick, 2), "BBB", 'B', Items.BRICK);
        MASON.addShapelessRecipe(new ItemStack(GeoItems.stoneDressed, 2), GeoItems.rubble);
        MASON.addShapedRecipe(new ItemStack(GeoItems.stoneDressed, 2), "RR", "RR", 'R', GeoItems.rubble);
    }

    /** Adds all recipes to armourer. */
    private static void setupArmourer() {

        ARMOURER.addShapedRecipe(new ItemStack(GeoItems.swordCopper), "B", "L", "P", 'B', GeoItems.swordbladeCopper, 'L', Items.LEATHER, 'P', GeoItems.pole);
        ARMOURER.addShapedRecipe(new ItemStack(GeoItems.swordBronze), "B", "L", "P", 'B', GeoItems.swordbladeBronze, 'L', Items.LEATHER, 'P', GeoItems.pole);
        ARMOURER.addShapedRecipe(new ItemStack(GeoItems.swordSteel), "B", "L", "P", 'B', GeoItems.swordbladeSteel, 'L', Items.LEATHER, 'P', GeoItems.pole);
        ARMOURER.addShapedRecipe(new ItemStack(GeoItems.shieldSteel), "SP ", "PPP", " PS", 'S', GeoItems.ingotSteel, 'P', GeoItems.pole);
        ARMOURER.addShapedRecipe(new ItemStack(GeoItems.steelmailChest), "S S", "SSS", "SSS", 'S', GeoItems.ingotSteel);
        ARMOURER.addShapedRecipe(new ItemStack(GeoItems.steelmailLegs), "SSS", "S S", "S S", 'S', GeoItems.ingotSteel);
        ARMOURER.addShapedRecipe(new ItemStack(GeoItems.steelmailFeet), "S S", "S S", 'S', GeoItems.ingotSteel);
        ARMOURER.addShapedRecipe(new ItemStack(GeoItems.steelmailHead), "SSS", "S S", 'S', GeoItems.ingotSteel);
        ARMOURER.addShapedRecipe(new ItemStack(GeoItems.steelplateChest), "SLS", "SSS", "SSS", 'S', GeoItems.ingotSteel, 'L', Items.LEATHER);
        ARMOURER.addShapedRecipe(new ItemStack(GeoItems.steelplateLegs), "SSS", "SLS", "S S", 'S', GeoItems.ingotSteel, 'L', Items.LEATHER);
        ARMOURER.addShapedRecipe(new ItemStack(GeoItems.steelplateFeet), "S S", "SLS", 'S', GeoItems.ingotSteel, 'L', Items.LEATHER);
        ARMOURER.addShapedRecipe(new ItemStack(GeoItems.steelplateHead), "SSS", "SLS", 'S', GeoItems.ingotSteel, 'L', Items.LEATHER);
    }
    
    /** Adds all recipes to sawpit. */
    private static void setupSawpit() {
        
        SAWPIT.addShapelessRecipe(new ItemStack(GeoItems.timber, 3), GeoItems.thicklog);
        SAWPIT.addShapedRecipe(new ItemStack(GeoItems.timber, 3), "LLL", 'L', GeoItems.log);
        SAWPIT.addShapedRecipe(new ItemStack(GeoItems.beamShort), "LLL", "L  ", 'L', GeoItems.log);
        SAWPIT.addShapedRecipe(new ItemStack(GeoItems.beamLong), "TTT", "TTT", "TT ", 'T', GeoItems.thicklog);
    }

    /** Adds all recipes to campfire and higher levels. */
    private static void setupCampfire() {

        for (CookingManager recipes : CAMPFIRE_PLUS) {
            
            recipes.addCookingRecipe(new ItemStack(GeoItems.beefRaw), new ItemStack(GeoItems.beefCooked), 120);
            recipes.addCookingRecipe(new ItemStack(GeoItems.porkRaw), new ItemStack(GeoItems.porkCooked), 100);
            recipes.addCookingRecipe(new ItemStack(GeoItems.muttonRaw), new ItemStack(GeoItems.muttonCooked), 80);
            recipes.addCookingRecipe(new ItemStack(GeoItems.rabbitRaw), new ItemStack(GeoItems.rabbitCooked), 40);
            recipes.addCookingRecipe(new ItemStack(GeoItems.chickenRaw), new ItemStack(GeoItems.chickenCooked), 60);
            recipes.addCookingRecipe(new ItemStack(GeoItems.fishRaw), new ItemStack(GeoItems.fishCooked), 40);
            recipes.addCookingRecipe(new ItemStack(GeoItems.potato), new ItemStack(GeoItems.potatoCooked), 120);
            recipes.addFuel(new ItemStack(Items.STICK), 200);
            recipes.addFuel(new ItemStack(GeoItems.pole), 500);
            recipes.addFuel(new ItemStack(GeoItems.log), 1000);
            recipes.addFuel(new ItemStack(GeoItems.thicklog), 2000);
        }
    }

    /** Adds all recipes to cookfire and higher levels. */
    private static void setupCookfire() {

        for (CookingManager recipes : COOKFIRE_PLUS) {
            
            recipes.addCookingRecipe(new ItemStack(Items.REEDS), new ItemStack(GeoItems.sugar), 60);
            recipes.addCookingRecipe(new ItemStack(GeoItems.chickpeas), new ItemStack(GeoItems.chickpeasBoiled), 60);
            recipes.addCookingRecipe(new ItemStack(GeoItems.wheat), new ItemStack(GeoItems.wheatBoiled), 60);
            recipes.addCookingRecipe(new ItemStack(GeoItems.rice), new ItemStack(GeoItems.riceBoiled), 60);
        }
    }

    /** Adds all recipes to clay furnace and higher levels. */
    private static void setupClay() {

        for (CookingManager recipes : CLAY_PLUS) {
            
            recipes.addCookingRecipe(new ItemStack(GeoItems.wheat), new ItemStack(Items.BREAD), 60);
            recipes.addCookingRecipe(new ItemStack(GeoItems.oreCopper), new ItemStack(GeoItems.ingotCopper), 300);
            recipes.addCookingRecipe(new ItemStack(GeoItems.oreTin), new ItemStack(GeoItems.ingotTin), 300);
            recipes.addCookingRecipe(new ItemStack(GeoItems.pole), new ItemStack(Items.COAL, 1, 1),60);
            recipes.addCookingRecipe(new ItemStack(GeoItems.log), new ItemStack(Items.COAL, 3, 1), 180);
            recipes.addCookingRecipe(new ItemStack(GeoItems.thicklog), new ItemStack(Items.COAL, 6, 1), 360);
            recipes.addCookingRecipe(new ItemStack(GeoItems.looseClay), new ItemStack(Items.BRICK), 200);
            recipes.addFuel(new ItemStack(GeoItems.peatDry), 2400);
            recipes.addFuel(new ItemStack(Items.COAL, 1, 1), 3000);
        }
    }

    /** Adds all recipes to stone furnace and higher levels. */
    private static void setupStone() {

        STONE.addCookingRecipe(new ItemStack(GeoItems.oreIron), new ItemStack(GeoItems.ingotSteel), 400);
        STONE.addCookingRecipe(new ItemStack(GeoItems.oreSilver), new ItemStack(GeoItems.ingotSilver), 300);
        STONE.addCookingRecipe(new ItemStack(GeoItems.oreGold), new ItemStack(Items.GOLD_INGOT), 200);
        STONE.addCookingRecipe(new ItemStack(GeoItems.looseSand), new ItemStack(GeoItems.glass), 200);
        STONE.addFuel(new ItemStack(Items.COAL, 1, 0), 4000);
    }

    /** Adds all recipes to drying rack. */
    private static void setupDrying() {

        DRYING.addCookingRecipe(new ItemStack(GeoItems.looseDirt), new ItemStack(GeoItems.mudbricks), 4000);
        DRYING.addCookingRecipe(new ItemStack(GeoItems.peatWet), new ItemStack(GeoItems.peatDry), 4000);
        
        for (Item skin : SKINS) {
            
            DRYING.addCookingRecipe(new ItemStack(skin), new ItemStack(Items.LEATHER), 4000);
        }
    }
}

package com.jj.jjmod.init;

import com.jj.jjmod.crafting.CookingManager;
import com.jj.jjmod.crafting.CraftingManager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ModRecipes {

    /** Crafting recipes for player inventory. */
    public static final CraftingManager INVENTORY = new CraftingManager();
    /** Crafting recipes for knapping block. */
    public static final CraftingManager KNAPPING = new CraftingManager();
    /** Crafting recipes for woodworking bench. */
    public static final CraftingManager WOODWORKING
            = new CraftingManager();
    /** Crafting recipes for textiles table. */
    public static final CraftingManager TEXTILES = new CraftingManager();
    /** Crafting recipes for clayworks. */
    public static final CraftingManager CLAYWORKS
            = new CraftingManager();
    /** Crafting recipes for candlemaker's bench. */
    public static final CraftingManager CANDLEMAKER
            = new CraftingManager();
    /** Crafting recipes for forge. */
    public static final CraftingManager FORGE = new CraftingManager();
    /** Crafting recipes for mason. */
    public static final CraftingManager MASON = new CraftingManager();
    /** Crafting recipes for armourer. */
    public static final CraftingManager ARMOURER = new CraftingManager();
    /** Crafting recipes for sawpit. */
    public static final CraftingManager SAWPIT = new CraftingManager();
    
    /** Cooking recipes for campfire. */
    public static final CookingManager CAMPFIRE = new CookingManager(3);
    /** Cooking recipes for cookfire. */
    public static final CookingManager COOKFIRE = new CookingManager(3);
    /** Cooking recipes for clay furnace. */
    public static final CookingManager CLAY = new CookingManager(2);
    /** Cooking recipes for stone furnace. */
    public static final CookingManager STONE = new CookingManager(1);
    /** Cooking recipes for drying rack. */
    public static final CookingManager DRYING = new CookingManager(1);

    /** All metals usable in crafting for reference. */
    private static final Item[] METALS =
            {ModItems.ingotCopper, ModItems.ingotTin, ModItems.ingotSteel};
    /** All pots usable in crafting for reference. */
    private static final Item[] POTS = {ModItems.potClay, ModItems.potMetal};
    /** All skins usable in crafitng furs for reference. */
    private static final Item[] SKINS_FUR = {ModItems.skinBear,
            ModItems.skinWolf, ModItems.skinSheep};
    /** All skins for reference. */
    private static final Item[] SKINS_ALL = {ModItems.skinBear,
            ModItems.skinCow, ModItems.skinPig,
            ModItems.skinSheep, ModItems.skinWolf};
    
    /** Cooking managers for campfire and higher furnace levels. */
    private static final CookingManager[] CAMPFIRE_PLUS =
            {CAMPFIRE, COOKFIRE, CLAY, STONE};
    /** Cooking managers for cookfire and higher furnace levels. */
    private static final CookingManager[] COOKFIRE_PLUS =
            {COOKFIRE, CLAY, STONE};
    /** Cooking managers for clay furnace and higher furnace levels. */
    private static final CookingManager[] CLAY_PLUS = {CLAY, STONE};
    
    public static void init() {

        setupInventory();
        setupKnapping();
        setupWoodworking();
        setupTextiles();
        setupClayworks();
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
        
        INVENTORY.addShapedRecipe(new ItemStack(ModItems.hoeAntler),
                "A", 'A', ModItems.shovelAntler);
        INVENTORY.addShapedRecipe(new ItemStack(Items.FISHING_ROD),
                "SS ", " TT", 'S', Items.STICK, 'T', ModItems.twineHemp);
        INVENTORY.addShapedRecipe(new ItemStack(ModItems.spearWood),
                "SSS", 'S', Items.STICK);
        INVENTORY.addShapedRecipe(new ItemStack(ModItems.bowCrude),
                "STS", " S ", 'S', Items.STICK, 'T', ModItems.twineHemp);
        INVENTORY.addShapedRecipe(new ItemStack(ModItems.arrowWood, 5),
                "SF", 'S', Items.STICK, 'F', Items.FEATHER);
        INVENTORY.addShapedRecipe(new ItemStack(ModItems.backpack),
                "L L", "LLL", 'L', Items.LEATHER);
        INVENTORY.addShapedRecipe(new ItemStack(ModItems.yoke),
                "LPL", "LLL", 'L', Items.LEATHER, 'P', ModItems.pole);
        INVENTORY.addShapedRecipe(new ItemStack(ModItems.potClay),
                "C C", " C ", 'C', Items.CLAY_BALL);
        INVENTORY.addShapedRecipe(new ItemStack(ModBlocks.craftingKnapping),
                "FFF", 'F', Items.FLINT);
        INVENTORY.addShapedRecipe(new ItemStack(ModItems.craftingCandlemaker),
                "PPP", "PPP", 'P', ModItems.pole);
        INVENTORY.addShapedRecipe(new ItemStack(ModItems.craftingTextiles),
                "BPP", "PPP",'B', Items.BONE, 'P', ModItems.pole);
        INVENTORY.addShapedRecipe(new ItemStack(ModItems.craftingClayworks),
                "PCP", "PPP", 'P', ModItems.pole, 'C', Items.CLAY_BALL);
        INVENTORY.addShapedRecipe(new ItemStack(ModItems.craftingArmourer),
                "SRP", "LBP", 'S', ModItems.ingotSteel, 'R',
                ModItems.stoneRough, 'P', ModItems.pole, 'L',
                Items.LEATHER, 'B', ModItems.beeswax);
        INVENTORY.addShapedRecipe(new ItemStack(ModItems.craftingSawpit),
                "PSP", " S ", 'P', ModItems.pole, 'S', ModItems.ingotSteel);
        INVENTORY.addShapedRecipe(new ItemStack(ModBlocks.furnaceCampfire),
                "S S", "SSS", 'S', Items.STICK);
        INVENTORY.addShapedRecipe(new ItemStack(ModItems.furnaceClay),
                "C C", "CCC", 'C', Items.CLAY_BALL);
        INVENTORY.addShapedRecipe(new ItemStack(ModItems.furnaceStone),
                "S S", "SSS", 'S', ModItems.stoneRough);
        INVENTORY.addShapedRecipe(new ItemStack(ModBlocks.drying),
                "PPP", 'P', ModItems.pole);
        INVENTORY.addShapedRecipe(new ItemStack(ModItems.bedLeaf),
                "LLL", 'L', ModItems.leaves);
        INVENTORY.addShapedRecipe(new ItemStack(ModItems.wallMud),
                "MMM", "MMM", 'M', ModItems.mudbricks);
        
        for (Item metal : METALS) {
        
            INVENTORY.addShapedRecipe(new
                    ItemStack(ModItems.craftingTextiles), "MPP", "PPP",
                    'M', metal, 'P', ModItems.pole);
            INVENTORY.addShapedRecipe(new
                    ItemStack(ModItems.craftingWoodworking), "PMM", "PPP",
                    'P', ModItems.pole, 'M', metal);
            INVENTORY.addShapedRecipe(new
                    ItemStack(ModItems.craftingMason), "MSS", "PPP",
                    'M', metal, 'S', ModItems.stoneRough, 'P', ModItems.pole);

            for (Item metal2 : METALS) {
                
                INVENTORY.addShapedRecipe(new ItemStack(ModItems.craftingForge),
                        "MNS", "PPP", 'M', metal, 'N', metal2,
                        'S', ModItems.stoneRough, 'P', ModItems.pole);
            }
        }
        
        for (Item pot : POTS) {
            
            INVENTORY.addShapedRecipe(new ItemStack(ModBlocks.furnaceCookfire),
                    "SPS", "SSS", 'S', Items.STICK, 'P', pot);
        }
    }

    /** Adds all recipes to knapping block. */
    private static void setupKnapping() {

        KNAPPING.addShapedRecipe(new ItemStack(ModItems.huntingknifeFlint),
                "FF", 'F', Items.FLINT);
        KNAPPING.addShapedRecipe(new ItemStack(ModItems.axeFlint),
                "FSS", 'F', ModItems.axeheadFlint, 'S', Items.STICK);
        KNAPPING.addShapedRecipe(new ItemStack(ModItems.pickaxeFlint),
                "FSS", 'F', ModItems.pickheadFlint, 'S', Items.STICK);
        KNAPPING.addShapedRecipe(new ItemStack(ModItems.shearsFlint),
                "F ", "FF", 'F', Items.FLINT);
        KNAPPING.addShapedRecipe(new ItemStack(ModItems.spearFlint),
                "FSS", 'F', ModItems.spearheadFlint, 'S', Items.STICK);
        KNAPPING.addShapedRecipe(new ItemStack(ModItems.arrowFlint, 5),
                "FSE", 'F', ModItems.arrowheadFlint, 'S', Items.STICK,
                'E', Items.FEATHER);
        KNAPPING.addShapedRecipe(new ItemStack(ModItems.wallRough, 4),
                "RRR", "RRR", 'R', ModItems.stoneRough);
        KNAPPING.addShapedRecipe(new ItemStack(ModItems.arrowheadFlint),
                "F", 'F', Items.FLINT);
        KNAPPING.addShapedRecipe(new ItemStack(ModItems.axeheadFlint),
                "F ", "FF", "F ", 'F', Items.FLINT);
        KNAPPING.addShapedRecipe(new ItemStack(ModItems.pickheadFlint),
                " F ", "FFF", " F ", 'F', Items.FLINT);
        KNAPPING.addShapedRecipe(new ItemStack(ModItems.spearheadFlint),
                " F ", " F ", "F F", 'F', Items.FLINT);
    }

    /** Adds all recipes to woodworking bench. */
    private static void setupWoodworking() {

        WOODWORKING.addShapedRecipe(new ItemStack(ModItems.shovelWood),
                "PPP", 'P', ModItems.pole);
        WOODWORKING.addShapedRecipe(new ItemStack(Items.BOW), "PTP",
                " P ", 'P', ModItems.pole, 'T', ModItems.twineHemp);

        WOODWORKING.addShapedRecipe(new ItemStack(ModItems.huntingknifeCopper),
                "BP", 'B', ModItems.knifebladeCopper, 'P', ModItems.pole);
        WOODWORKING.addShapedRecipe(new ItemStack(ModItems.macheteCopper),
                "BP", 'B', ModItems.machetebladeCopper, 'P',
                ModItems.pole);
        WOODWORKING.addShapedRecipe(new ItemStack(ModItems.pickaxeCopper),
                "HPP", 'H', ModItems.pickheadCopper, 'P', ModItems.pole);
        WOODWORKING.addShapedRecipe(new ItemStack(ModItems.spearCopper),
                "HPP", 'H', ModItems.spearheadCopper, 'P', ModItems.pole);
        WOODWORKING.addShapedRecipe(new ItemStack(ModItems.axeCopper),
                "HPP", 'H', ModItems.axeheadCopper, 'P', ModItems.pole);
        WOODWORKING.addShapedRecipe(new ItemStack(ModItems.hoeCopper),
                "HPP", 'H', ModItems.hoeheadCopper, 'P', ModItems.pole);
        WOODWORKING.addShapedRecipe(new ItemStack(ModItems.sickleCopper),
                "BP", 'B', ModItems.sicklebladeCopper, 'P',
                ModItems.pole);
        WOODWORKING.addShapedRecipe(new ItemStack(ModItems.shovelCopper),
                "HPP", 'H', ModItems.shovelheadCopper, 'P', ModItems.pole);
        WOODWORKING.addShapedRecipe(new ItemStack(ModItems.arrowCopper),
                "ASF", 'A', ModItems.arrowheadCopper, 'S', Items.STICK,
                'F', Items.FEATHER);

        WOODWORKING.addShapedRecipe(new ItemStack(ModItems.huntingknifeBronze),
                "BP", 'B', ModItems.knifebladeBronze, 'P', ModItems.pole);
        WOODWORKING.addShapedRecipe(new ItemStack(ModItems.macheteBronze),
                "BP", 'B', ModItems.machetebladeBronze, 'P',
                ModItems.pole);
        WOODWORKING.addShapedRecipe(new ItemStack(ModItems.pickaxeBronze),
                "HPP", 'H', ModItems.pickheadBronze, 'P', ModItems.pole);
        WOODWORKING.addShapedRecipe(new ItemStack(ModItems.spearBronze),
                "HPP", 'H', ModItems.spearheadBronze, 'P', ModItems.pole);
        WOODWORKING.addShapedRecipe(new ItemStack(ModItems.axeBronze),
                "HPP", 'H', ModItems.axeheadBronze, 'P', ModItems.pole);
        WOODWORKING.addShapedRecipe(new ItemStack(ModItems.hoeBronze),
                "HPP", 'H', ModItems.hoeheadBronze, 'P', ModItems.pole);
        WOODWORKING.addShapedRecipe(new ItemStack(ModItems.sickleBronze),
                "BP", 'B', ModItems.sicklebladeBronze, 'P',
                ModItems.pole);
        WOODWORKING.addShapedRecipe(new ItemStack(ModItems.shovelBronze),
                "HPP", 'H', ModItems.shovelheadBronze, 'P', ModItems.pole);
        WOODWORKING.addShapedRecipe(new ItemStack(ModItems.arrowBronze),
                "ASF", 'A', ModItems.arrowheadBronze, 'S', Items.STICK,
                'F', Items.FEATHER);

        WOODWORKING.addShapedRecipe(new ItemStack(ModItems.huntingknifeSteel),
                "BP", 'B', ModItems.knifebladeSteel, 'P', ModItems.pole);
        WOODWORKING.addShapedRecipe(new ItemStack(ModItems.macheteSteel),
                "BP", 'B', ModItems.machetebladeSteel, 'P',
                ModItems.pole);
        WOODWORKING.addShapedRecipe(new ItemStack(ModItems.pickaxeSteel),
                "HPP", 'H', ModItems.pickheadSteel, 'P', ModItems.pole);
        WOODWORKING.addShapedRecipe(new ItemStack(ModItems.spearSteel),
                "HPP", 'H', ModItems.spearheadSteel, 'P', ModItems.pole);
        WOODWORKING.addShapedRecipe(new ItemStack(ModItems.axeSteel),
                "HPP", 'H', ModItems.axeheadSteel, 'P', ModItems.pole);
        WOODWORKING.addShapedRecipe(new ItemStack(ModItems.hoeSteel),
                "HPP", 'H', ModItems.hoeheadSteel, 'P', ModItems.pole);
        WOODWORKING.addShapedRecipe(new ItemStack(ModItems.sickleSteel),
                "BP", 'B', ModItems.sicklebladeSteel, 'P', ModItems.pole);
        WOODWORKING.addShapedRecipe(new ItemStack(ModItems.shovelSteel),
                "HPP", 'H', ModItems.shovelheadSteel, 'P', ModItems.pole);
        WOODWORKING.addShapedRecipe(new ItemStack(ModItems.arrowSteel),
                "ASF", 'A', ModItems.arrowheadSteel, 'S', Items.STICK,
                'F', Items.FEATHER);
        
        WOODWORKING.addShapedRecipe(new ItemStack(ModItems.shieldWood),
                " P ", "PPP", " P ", 'P', ModItems.pole);
        WOODWORKING.addShapedRecipe(new ItemStack(ModBlocks.wallPole, 4),
                "PPP", "PPP", 'P', ModItems.pole);
        WOODWORKING.addShapedRecipe(new ItemStack(ModItems.wallLog, 4),
                "LLL", "LLL", 'L', ModItems.log);
        WOODWORKING.addShapedRecipe(new ItemStack(ModBlocks.stairsPole, 4),
                "  P", " PP", "PPP", 'P', ModItems.pole);
        WOODWORKING.addShapedRecipe(new ItemStack(ModBlocks.stairsWood, 4),
                "  T", " TT", "TTT", 'T', ModItems.timber);
        WOODWORKING.addShapedRecipe(new ItemStack(ModBlocks.fence, 4),
                "PPP", " P ", 'P', ModItems.pole);
        WOODWORKING.addShapedRecipe(new ItemStack(ModItems.doorPole),
                "PP", "PP", "PP", 'P', ModItems.pole);
        WOODWORKING.addShapedRecipe(new ItemStack(ModItems.doorWood),
                "SS", "SS", "SS", 'S', ModItems.timber);
        WOODWORKING.addShapedRecipe(new ItemStack(Blocks.CHEST),
                "SSS", "S S", "SSS", 'S', ModItems.timber);
        WOODWORKING.addShapedRecipe(new ItemStack(ModBlocks.box),
                "PP", "PP", 'P', ModItems.pole);
        WOODWORKING.addShapedRecipe(new ItemStack(Blocks.LADDER, 6),
                "P P", "PPP", "P P", 'P', ModItems.pole);
        WOODWORKING.addShapedRecipe(new ItemStack(ModItems.bedSimple), "WWW",
                "PPP", 'W', Blocks.WOOL, 'P', ModItems.pole);
    }

    /** Adds all recipes to textiles table. */
    private static void setupTextiles() {

        TEXTILES.addShapedRecipe(new ItemStack(ModItems.bedCotton),
                "CCC", "CCC", 'C', ModItems.cotton);
        TEXTILES.addShapedRecipe(new ItemStack(ModItems.bedWool),
                "WWW", "WWW", 'W', Blocks.WOOL);
        TEXTILES.addShapedRecipe(new ItemStack(ModItems.cottonChest),
                "C C", "CCC", "CCC", 'C', ModItems.cotton);
        TEXTILES.addShapedRecipe(new ItemStack(ModItems.cottonLegs),
                "CCC", "C C", "C C", 'C', ModItems.cotton);
        TEXTILES.addShapedRecipe(new ItemStack(ModItems.cottonHead),
                "CCC", "C C", 'C', ModItems.cotton);
        TEXTILES.addShapedRecipe(new ItemStack(ModItems.cottonFeet),
                "C C", "C C", 'C', ModItems.cotton);
        TEXTILES.addShapedRecipe(new ItemStack(ModItems.woolChest),
                "W W", "WWW", "WWW", 'W', ModItems.wool);
        TEXTILES.addShapedRecipe(new ItemStack(ModItems.woolLegs),
                "WWW", "W W", "W W", 'W', ModItems.wool);
        TEXTILES.addShapedRecipe(new ItemStack(ModItems.woolHead),
                "WWW", "W W", 'W', ModItems.wool);
        TEXTILES.addShapedRecipe(new ItemStack(ModItems.woolFeet),
                "W W", "W W", 'W', ModItems.wool);
        
        for (Item skin : SKINS_FUR) {
            
            TEXTILES.addShapedRecipe(new ItemStack(ModItems.furHead),
                    "SSS", "S S", 'S', skin);
            TEXTILES.addShapedRecipe(new ItemStack(ModItems.furChest),
                    "S S", "SSS", "SSS", 'S', skin);
            TEXTILES.addShapedRecipe(new ItemStack(ModItems.furLegs),
                    "SSS", "S S", "S S", 'S', skin);
            TEXTILES.addShapedRecipe(new ItemStack(ModItems.furFeet),
                    "S S", "S S", 'S', skin);
        }
    }

    /** Adds all recipes to clayworks. */
    private static void setupClayworks() {

    }

    /** Adds all recipes to candlemaker's bench. */
    private static void setupCandlemaker() {

        CANDLEMAKER.addShapedRecipe(new ItemStack(ModBlocks.candleTallow, 15),
                "H", "T", "T", 'H', ModItems.twineHemp, 'T', ModItems.tallow);
        CANDLEMAKER.addShapedRecipe(new ItemStack(ModBlocks.candleBeeswax, 15),
                "H", "B", "B", 'H', ModItems.twineHemp, 'B', ModItems.beeswax);
        CANDLEMAKER.addShapedRecipe(new ItemStack(ModBlocks.torchTallow, 4),
                "T", "C", "S", 'T', ModItems.tallow, 'C',
                ModItems.cotton, 'S', Items.STICK);
    }

    /** Adds all recipes to forge. */
    private static void setupForge() {

        FORGE.addShapedRecipe(new ItemStack(ModItems.shearsCopper), "C ",
                "CC", 'C', ModItems.ingotCopper);
        FORGE.addShapedRecipe(new ItemStack(ModItems.shearsBronze), "T ",
                "CC", 'T', ModItems.ingotTin, 'C', ModItems.ingotCopper);
        FORGE.addShapedRecipe(new ItemStack(ModItems.shearsSteel), "S ",
                "SS", 'S', ModItems.ingotSteel);

        FORGE.addShapedRecipe(new ItemStack(ModItems.potMetal), "M M",
                " M ", 'M', ModItems.ingotCopper);
        FORGE.addShapedRecipe(new ItemStack(ModItems.potMetal), "M M",
                " M ", 'M', ModItems.ingotSteel);

        FORGE.addShapedRecipe(new ItemStack(ModItems.knifebladeCopper, 2),
                "M", "M", 'M', ModItems.ingotCopper);
        FORGE.addShapedRecipe(new ItemStack(ModItems.machetebladeCopper),
                "MMM", 'M', ModItems.ingotCopper);
        FORGE.addShapedRecipe(new ItemStack(ModItems.pickheadCopper),
                " M ", "MMM", " M ", 'M', ModItems.ingotCopper);
        FORGE.addShapedRecipe(new ItemStack(ModItems.arrowheadCopper, 24),
                "M ", " M", 'M', ModItems.ingotCopper);
        FORGE.addShapedRecipe(new ItemStack(ModItems.spearheadCopper, 2),
                " M ", " M ", "M M", 'M', ModItems.ingotCopper);
        FORGE.addShapedRecipe(new ItemStack(ModItems.axeheadCopper), "M ",
                "MM", "M ", 'M', ModItems.ingotCopper);
        FORGE.addShapedRecipe(new ItemStack(ModItems.hoeheadCopper), " M ",
                "MMM", 'M', ModItems.ingotCopper);
        FORGE.addShapedRecipe(new ItemStack(ModItems.sicklebladeCopper),
                "MM ", "  M", "  M", 'M', ModItems.ingotCopper);
        FORGE.addShapedRecipe(new ItemStack(ModItems.shovelheadCopper),
                "MM", "MM", "MM", 'M', ModItems.ingotCopper);
        FORGE.addShapedRecipe(new ItemStack(ModItems.swordbladeCopper),
                "M", "M", "M", 'M', ModItems.ingotCopper);

        FORGE.addShapedRecipe(new ItemStack(ModItems.knifebladeBronze, 2),
                "T", "M", 'M', ModItems.ingotCopper, 'T',
                ModItems.ingotTin);
        FORGE.addShapedRecipe(new ItemStack(ModItems.machetebladeBronze),
                "TMM", 'M', ModItems.ingotCopper, 'T', ModItems.ingotTin);
        FORGE.addShapedRecipe(new ItemStack(ModItems.pickheadBronze),
                " T ", "MMM", " M ", 'M', ModItems.ingotCopper, 'T',
                ModItems.ingotTin);
        FORGE.addShapedRecipe(new ItemStack(ModItems.arrowheadBronze, 24),
                "T ", " M", 'M', ModItems.ingotCopper, 'T',
                ModItems.ingotTin);
        FORGE.addShapedRecipe(new ItemStack(ModItems.spearheadBronze, 2),
                " T ", " M ", "M M", 'M', ModItems.ingotCopper, 'T',
                ModItems.ingotTin);
        FORGE.addShapedRecipe(new ItemStack(ModItems.axeheadBronze), "T ",
                "MM", "M ", 'M', ModItems.ingotCopper, 'T',
                ModItems.ingotTin);
        FORGE.addShapedRecipe(new ItemStack(ModItems.hoeheadBronze), " T ",
                "MMM", 'M', ModItems.ingotCopper, 'T', ModItems.ingotTin);
        FORGE.addShapedRecipe(new ItemStack(ModItems.sicklebladeBronze),
                "TM ", "  M", "  M", 'M', ModItems.ingotCopper, 'T',
                ModItems.ingotTin);
        FORGE.addShapedRecipe(new ItemStack(ModItems.shovelheadBronze),
                "TM", "MM", "MM", 'M', ModItems.ingotCopper, 'T',
                ModItems.ingotTin);
        FORGE.addShapedRecipe(new ItemStack(ModItems.swordbladeBronze),
                "T", "M", "M", 'M', ModItems.ingotCopper, 'T',
                ModItems.ingotTin);

        FORGE.addShapedRecipe(new ItemStack(ModItems.knifebladeSteel, 2),
                "M", "M", 'M', ModItems.ingotSteel);
        FORGE.addShapedRecipe(new ItemStack(ModItems.machetebladeSteel),
                "MMM", 'M', ModItems.ingotSteel);
        FORGE.addShapedRecipe(new ItemStack(ModItems.pickheadSteel), " M ",
                "MMM", " M ", 'M', ModItems.ingotSteel);
        FORGE.addShapedRecipe(new ItemStack(ModItems.arrowheadSteel, 24),
                "M ", " M", 'M', ModItems.ingotSteel);
        FORGE.addShapedRecipe(new ItemStack(ModItems.spearheadSteel, 2),
                " M ", " M ", "M M", 'M', ModItems.ingotSteel);
        FORGE.addShapedRecipe(new ItemStack(ModItems.axeheadSteel), "M ",
                "MM", "M ", 'M', ModItems.ingotSteel);
        FORGE.addShapedRecipe(new ItemStack(ModItems.hoeheadSteel), " M ",
                "MMM", 'M', ModItems.ingotSteel);
        FORGE.addShapedRecipe(new ItemStack(ModItems.sicklebladeSteel),
                "MM ", "  M", "  M", 'M', ModItems.ingotSteel);
        FORGE.addShapedRecipe(new ItemStack(ModItems.shovelheadSteel), "MM",
                "MM", "MM", 'M', ModItems.ingotSteel);
        FORGE.addShapedRecipe(new ItemStack(ModItems.swordbladeSteel),
                "M", "M", "M", 'M', ModItems.ingotSteel);
        FORGE.addShapedRecipe(new ItemStack(ModItems.bucketEmpty),
                "P P", " P ", 'P', ModItems.pole);
    }

    /** Adds all recipes to mason. */
    private static void setupMason() {
        
        MASON.addShapedRecipe(new ItemStack(ModItems.wallBrick, 4),
                "BBB", "BBB", 'B', Items.BRICK);
        MASON.addShapedRecipe(new ItemStack(ModItems.wallStone, 4),
                "SSS", "SSS", 'S', ModItems.stoneDressed);
        MASON.addShapedRecipe(new ItemStack(ModBlocks.stairsStone, 4),
                "  S", " SS", "SSS", 'S', ModItems.stoneDressed);
        MASON.addShapedRecipe(new ItemStack(ModItems.slabStone, 2),
                "SSS", 'S', ModItems.stoneDressed);
        MASON.addShapelessRecipe(new ItemStack(ModItems.stoneDressed, 2),
                ModItems.stoneRough);

    }

    /** Adds all recipes to armourer. */
    private static void setupArmourer() {

        ARMOURER.addShapedRecipe(new ItemStack(ModItems.swordCopper), "B",
                "L", "P", 'B', ModItems.swordbladeCopper, 'L',
                Items.LEATHER, 'P', ModItems.pole);
        ARMOURER.addShapedRecipe(new ItemStack(ModItems.swordBronze), "B",
                "L", "P", 'B', ModItems.swordbladeBronze, 'L',
                Items.LEATHER, 'P', ModItems.pole);
        ARMOURER.addShapedRecipe(new ItemStack(ModItems.swordSteel), "B",
                "L", "P", 'B', ModItems.swordbladeSteel, 'L',
                Items.LEATHER, 'P', ModItems.pole);
        ARMOURER.addShapedRecipe(new ItemStack(ModItems.shieldSteel), "SP ",
                "PPP", " PS", 'S', ModItems.ingotSteel, 'P',
                ModItems.pole);
        ARMOURER.addShapedRecipe(new ItemStack(ModItems.steelmailChest),
                "S S", "SSS", "SSS", 'S', ModItems.ingotSteel);
        ARMOURER.addShapedRecipe(new ItemStack(ModItems.steelmailLegs),
                "SSS", "S S", "S S", 'S', ModItems.ingotSteel);
        ARMOURER.addShapedRecipe(new ItemStack(ModItems.steelmailFeet),
                "S S", "S S", 'S', ModItems.ingotSteel);
        ARMOURER.addShapedRecipe(new ItemStack(ModItems.steelmailHead),
                "SSS", "S S", 'S', ModItems.ingotSteel);
        ARMOURER.addShapedRecipe(new ItemStack(ModItems.steelplateChest),
                "SLS", "SSS", "SSS", 'S', ModItems.ingotSteel, 'L', Items.LEATHER);
        ARMOURER.addShapedRecipe(new ItemStack(ModItems.steelplateLegs),
                "SSS", "SLS", "S S", 'S', ModItems.ingotSteel, 'L', Items.LEATHER);
        ARMOURER.addShapedRecipe(new ItemStack(ModItems.steelplateFeet),
                "S S", "SLS", 'S', ModItems.ingotSteel, 'L', Items.LEATHER);
        ARMOURER.addShapedRecipe(new ItemStack(ModItems.steelplateHead),
                "SSS", "SLS", 'S', ModItems.ingotSteel, 'L', Items.LEATHER);
    }
    
    /** Adds all recipes to sawpit. */
    private static void setupSawpit() {
        
        SAWPIT.addShapelessRecipe(new ItemStack(ModItems.timber, 3),
                ModItems.thicklog);
        SAWPIT.addShapedRecipe(new ItemStack(ModItems.timber, 3),
                "LLL", 'L', ModItems.log);
    }

    /** Adds all recipes to campfire and higher levels. */
    private static void setupCampfire() {

        for (CookingManager recipes : CAMPFIRE_PLUS) {
            
            recipes.addCookingRecipe(new ItemStack(ModItems.beefRaw),
                    new ItemStack(ModItems.beefCooked), 120);
            recipes.addCookingRecipe(new ItemStack(ModItems.porkRaw),
                    new ItemStack(ModItems.porkCooked), 100);
            recipes.addCookingRecipe(new ItemStack(ModItems.muttonRaw),
                    new ItemStack(ModItems.muttonCooked), 80);
            recipes.addCookingRecipe(new ItemStack(ModItems.rabbitRaw),
                    new ItemStack(ModItems.rabbitCooked), 40);
            recipes.addCookingRecipe(new ItemStack(ModItems.chickenRaw),
                    new ItemStack(ModItems.chickenCooked), 60);
            recipes.addCookingRecipe(new ItemStack(ModItems.fishRaw),
                    new ItemStack(ModItems.fishCooked), 40);
            recipes.addCookingRecipe(new ItemStack(ModItems.potato),
                    new ItemStack(ModItems.potatoCooked), 120);
            recipes.addFuel(new ItemStack(Items.STICK), 200);
            recipes.addFuel(new ItemStack(ModItems.pole), 500);
            recipes.addFuel(new ItemStack(ModItems.log), 1000);
            recipes.addFuel(new ItemStack(ModItems.thicklog), 2000);
        }
    }

    /** Adds all recipes to cookfire and higher levels. */
    private static void setupCookfire() {

        for (CookingManager recipes : COOKFIRE_PLUS) {
            
            recipes.addCookingRecipe(new ItemStack(Items.REEDS),
                    new ItemStack(ModItems.sugar), 60);
            recipes.addCookingRecipe(new ItemStack(ModItems.chickpeas),
                    new ItemStack(ModItems.chickpeasBoiled), 60);
            recipes.addCookingRecipe(new ItemStack(ModItems.wheat),
                    new ItemStack(ModItems.wheatBoiled), 60);
            recipes.addCookingRecipe(new ItemStack(ModItems.rice),
                    new ItemStack(ModItems.riceBoiled), 60);
        }
    }

    /** Adds all recipes to clay furnace and higher levels. */
    private static void setupClay() {

        for (CookingManager recipes : CLAY_PLUS) {
            
            recipes.addCookingRecipe(new ItemStack(ModItems.wheat),
                    new ItemStack(Items.BREAD), 60);
            recipes.addCookingRecipe(new ItemStack(ModItems.oreCopper),
                    new ItemStack(ModItems.ingotCopper), 300);
            recipes.addCookingRecipe(new ItemStack(ModItems.oreTin),
                    new ItemStack(ModItems.ingotTin), 300);
            recipes.addFuel(new ItemStack(ModItems.peatDry), 2400);
            recipes.addFuel(new ItemStack(Items.COAL, 1, 1), 3000);
        }
    }

    /** Adds all recipes to stone furnace and higher levels. */
    private static void setupStone() {

        STONE.addCookingRecipe(new ItemStack(ModItems.oreIron),
                new ItemStack(ModItems.ingotSteel), 400);
        STONE.addCookingRecipe(new ItemStack(ModItems.oreSilver),
                new ItemStack(ModItems.ingotSilver), 300);
        STONE.addCookingRecipe(new ItemStack(ModItems.oreGold),
                new ItemStack(Items.GOLD_INGOT), 200);
        STONE.addCookingRecipe(new ItemStack(Items.CLAY_BALL),
                new ItemStack(Items.BRICK), 200);
        STONE.addFuel(new ItemStack(Items.COAL, 1, 0), 4000);
    }

    /** Adds all recipes to drying rack. */
    private static void setupDrying() {

        DRYING.addCookingRecipe(new ItemStack(ModItems.dirt),
                new ItemStack(ModItems.mudbricks), 4000);
        DRYING.addCookingRecipe(new ItemStack(ModItems.peatWet),
                new ItemStack(ModItems.peatDry), 4000);
        
        for (Item skin : SKINS_ALL) {
            
            DRYING.addCookingRecipe(new ItemStack(skin),
                    new ItemStack(Items.LEATHER), 4000);
        }
    }
}

package com.jj.jjmod.init;

import com.jj.jjmod.crafting.CookingManager;
import com.jj.jjmod.crafting.CraftingManager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ModRecipes {

    public static final CraftingManager INVENTORY = new CraftingManager();
    public static final CraftingManager KNAPPING = new CraftingManager();
    public static final CraftingManager WOODWORKING
            = new CraftingManager();
    public static final CraftingManager TEXTILES = new CraftingManager();
    public static final CraftingManager CLAYWORKS
            = new CraftingManager();
    public static final CraftingManager CANDLEMAKER
            = new CraftingManager();
    public static final CraftingManager FORGE = new CraftingManager();
    public static final CraftingManager MASON = new CraftingManager();
    public static final CraftingManager ARMOURER = new CraftingManager();
    public static final CraftingManager SAWPIT = new CraftingManager();
    public static final CookingManager CAMPFIRE = new CookingManager();
    public static final CookingManager POTFIRE = new CookingManager();
    public static final CookingManager CLAY = new CookingManager();
    public static final CookingManager STONE = new CookingManager();
    public static final CookingManager DRYING = new CookingManager();

    private static final Item[] METALS =
            {ModItems.ingotCopper, ModItems.ingotTin, ModItems.ingotSteel};

    public static void init() {

        setupInventory();
        setupKnapping();
        setupWoodworking();
        setupTextiles();
        setupBricklayer();
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
    }

    // CONFIG crafting recipes

    public static void setupInventory() {
        
        //TEST
        INVENTORY.addShapelessRecipe(new ItemStack(Items.BEEF), new ItemStack(Items.APPLE, 3), Items.STICK);

        INVENTORY.addShapedRecipe(new ItemStack(ModItems.bedLeaf), "LLL",
                'L', ModItems.leaves);

        INVENTORY.addShapelessRecipe(
                new ItemStack(ModItems.hoeAntler),
                ModItems.shovelAntler);
        INVENTORY.addShapedRecipe(new ItemStack(Items.FISHING_ROD),
                "SS ", " TT", 'S', Items.STICK, 'T', ModItems.twineHemp);

        INVENTORY.addShapedRecipe(
                new ItemStack(ModBlocks.craftingKnapping), "FF", "FF",
                'F', Items.FLINT);
        INVENTORY.addShapedRecipe(
                new ItemStack(ModBlocks.craftingCandlemaker), "PPP", "P P",
                'P', ModItems.pole);
        INVENTORY.addShapedRecipe(
                new ItemStack(ModItems.craftingTextiles), "BPP", "P P",
                'B', Items.BONE, 'P', ModItems.pole);
        INVENTORY.addShapedRecipe(
                new ItemStack(ModBlocks.craftingClayworks), "CPP", "P P",
                'C', Items.CLAY_BALL, 'P', ModItems.pole);

        INVENTORY.addShapedRecipe(
                new ItemStack(ModBlocks.furnaceCampfire), "SSS", 'S',
                Items.STICK);
        INVENTORY.addShapedRecipe(new ItemStack(ModItems.furnaceClay),
                "CCC", "C C", 'C', Items.CLAY_BALL);

        INVENTORY.addShapedRecipe(new ItemStack(ModItems.spearWood),
                "SSS", 'S', Items.STICK);
        INVENTORY.addShapedRecipe(new ItemStack(ModItems.bowCrude),
                "STS", " S ", 'S', Items.STICK, 'T', ModItems.twineHemp);
        INVENTORY.addShapedRecipe(new ItemStack(ModItems.arrowWood, 5),
                "SF", 'S', Items.STICK, 'F', Items.FEATHER);

        // Recipes with one metal
        for (Item metal: METALS) {

            INVENTORY.addShapedRecipe(
                    new ItemStack(ModItems.craftingTextiles), "MPP", "P P",
                    'M', metal, 'P', ModItems.pole);
            INVENTORY.addShapedRecipe(
                    new ItemStack(ModItems.craftingWoodworking), "PPM",
                    "P P", 'P', ModItems.pole, 'M', metal);
            INVENTORY.addShapedRecipe(
                    new ItemStack(ModItems.craftingMason), "MPP", "S S",
                    'M', metal, 'P', ModItems.pole, 'S', ModItems.stoneRough);
        }
    }

    public static void setupKnapping() {

        KNAPPING.addShapelessRecipe(
                new ItemStack(ModItems.arrowheadFlint, 4), Items.FLINT);
        KNAPPING.addShapedRecipe(new ItemStack(ModItems.huntingknifeFlint),
                "F", "F", 'F', Items.FLINT);
        KNAPPING.addShapedRecipe(new ItemStack(ModItems.woolknifeFlint),
                "F ", "FF", 'F', Items.FLINT);
        KNAPPING.addShapedRecipe(new ItemStack(ModItems.axeheadFlint),
                "F ", "FF", "F ", 'F', Items.FLINT);
        KNAPPING.addShapedRecipe(new ItemStack(ModItems.pickheadFlint),
                " F ", "FFF", " F ", 'F', Items.FLINT);
        KNAPPING.addShapedRecipe(new ItemStack(ModItems.spearheadFlint),
                " F ", " F ", "F F", 'F', Items.FLINT);

        KNAPPING.addShapedRecipe(new ItemStack(ModItems.axeFlint), "ASS",
                'A', ModItems.axeheadFlint, 'S', Items.STICK);
        KNAPPING.addShapedRecipe(new ItemStack(ModItems.pickaxeFlint), "PSS",
                'P', ModItems.pickheadFlint, 'S', Items.STICK);
        KNAPPING.addShapedRecipe(new ItemStack(ModItems.spearFlint), "HSS",
                'H', ModItems.spearheadFlint, 'S', Items.STICK);
        KNAPPING.addShapedRecipe(new ItemStack(ModItems.arrowFlint, 5),
                "ASF", 'A', ModItems.arrowheadFlint, 'S', Items.STICK,
                'F', Items.FEATHER);
    }

    public static void setupWoodworking() {

        WOODWORKING.addShapedRecipe(new ItemStack(ModItems.bedSimple), "WWW",
                "PPP", 'W', Blocks.WOOL, 'P', ModItems.pole);

        WOODWORKING.addShapedRecipe(new ItemStack(ModItems.shovelWood),
                "PPP", 'P', ModItems.pole);
        WOODWORKING.addShapedRecipe(new ItemStack(Blocks.LADDER, 6),
                "P P", "PPP", "P P", 'P', ModItems.pole);
        WOODWORKING.addShapedRecipe(new ItemStack(Items.ITEM_FRAME, 4),
                "PPP", "PLP", "PPP", 'P', ModItems.pole, 'L',
                Items.LEATHER);
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
        WOODWORKING.addShapedRecipe(new ItemStack(ModItems.arrowCopper, 5),
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
        WOODWORKING.addShapedRecipe(new ItemStack(ModItems.arrowBronze, 5),
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
        WOODWORKING.addShapedRecipe(new ItemStack(ModItems.arrowSteel, 5),
                "ASF", 'A', ModItems.arrowheadSteel, 'S', Items.STICK,
                'F', Items.FEATHER);
    }

    public static void setupTextiles() {

        TEXTILES.addShapedRecipe(new ItemStack(ModItems.bedCotton), "CCC", 'C',
                ModItems.cotton);
        TEXTILES.addShapedRecipe(new ItemStack(ModItems.bedWool), "WWW", 'W',
                Blocks.WOOL);
    }

    public static void setupBricklayer() {

    }

    public static void setupCandlemaker() {

    }

    public static void setupForge() {

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
                " M ", "MMM", " M ", 'M', ModItems.ingotCopper);

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
                " T ", "MMM", " M ", 'M', ModItems.ingotCopper, 'T',
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
                " M ", "MMM", " M ", 'M', ModItems.ingotSteel);
    }

    public static void setupMason() {

    }

    public static void setupArmourer() {

        ARMOURER.addShapedRecipe(new ItemStack(ModItems.swordCopper), "B",
                "L", "P", 'B', ModItems.swordbladeCopper, 'L',
                Items.LEATHER, 'P', ModItems.pole);
        ARMOURER.addShapedRecipe(new ItemStack(ModItems.swordBronze), "B",
                "L", "P", 'B', ModItems.swordbladeBronze, 'L',
                Items.LEATHER, 'P', ModItems.pole);
        ARMOURER.addShapedRecipe(new ItemStack(ModItems.swordSteel), "B",
                "L", "P", 'B', ModItems.swordbladeSteel, 'L',
                Items.LEATHER, 'P', ModItems.pole);

        ARMOURER.addShapedRecipe(new ItemStack(Items.SHIELD), "SP ",
                "PPP", " PS", 'S', ModItems.ingotSteel, 'P',
                ModItems.pole);
    }
    
    public static void setupSawpit() {
        
        
    }

    // CONFIG furnace recipes

    public static void setupCampfire() {

        CAMPFIRE.addSmeltingRecipe(new ItemStack(Items.BEEF), new ItemStack(Items.COOKED_BEEF));
    }

    public static void setupPotfire() {

    }

    public static void setupClay() {

    }

    public static void setupStone() {

    }

    public static void setupDrying() {

    }
}

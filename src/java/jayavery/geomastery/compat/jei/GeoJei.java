/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.compat.jei;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import jayavery.geomastery.container.ContainerCrafting;
import jayavery.geomastery.container.ContainerFurnaceClay;
import jayavery.geomastery.container.ContainerFurnaceSingle;
import jayavery.geomastery.container.ContainerFurnaceStone;
import jayavery.geomastery.crafting.CompostManager.CompostType;
import jayavery.geomastery.crafting.CraftingManager;
import jayavery.geomastery.crafting.ShapedRecipe;
import jayavery.geomastery.items.ItemCarcassDecayable;
import jayavery.geomastery.items.ItemEdibleDecayable;
import jayavery.geomastery.main.GeoBlocks;
import jayavery.geomastery.main.GeoCaps;
import jayavery.geomastery.main.GeoConfig;
import jayavery.geomastery.main.GeoItems;
import jayavery.geomastery.main.GeoRecipes;
import jayavery.geomastery.main.Geomastery;
import jayavery.geomastery.main.GuiHandler.GuiList;
import mezz.jei.api.BlankModPlugin;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.ISubtypeRegistry;
import mezz.jei.api.ISubtypeRegistry.ISubtypeInterpreter;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.IIngredientBlacklist;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.IStackHelper;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandlerHelper;
import mezz.jei.api.recipe.transfer.IRecipeTransferRegistry;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

/** JEI plugin for Geomastery. */
@JEIPlugin
public class GeoJei extends BlankModPlugin {
    
    // Helpers stored for convenience 
    static IGuiHelper guiHelper;
    static IRecipeTransferHandlerHelper transferHelper;
    static IStackHelper stackHelper;
    
    // All categories for access
    static GeoCompostCategory compost;
    static GeoCookingCategory cooking;
    static GeoFuelCategory fuel;
    static GeoDryingCategory drying;
    static GeoInventoryCategory inventory;
    static GeoCraftingCategory armourer;
    static GeoCraftingCategory candlemaker;
    static GeoCraftingCategory forge;
    static GeoCraftingCategory knapping;
    static GeoCraftingCategory mason;
    static GeoCraftingCategory sawpit;
    static GeoCraftingCategory textiles;
    static GeoCraftingCategory woodworking;
    
    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        
        guiHelper = registry.getJeiHelpers().getGuiHelper();
        
        Geomastery.LOG.info("JEI: Registering recipe categories");
        compost = new GeoCompostCategory();
        cooking = new GeoCookingCategory();
        fuel = new GeoFuelCategory();
        drying = new GeoDryingCategory();
        inventory = new GeoInventoryCategory();
        armourer = new GeoCraftingCategory(GuiList.ARMOURER.title());
        candlemaker = new GeoCraftingCategory(GuiList.CANDLEMAKER.title());
        forge = new GeoCraftingCategory(GuiList.FORGE.title());
        knapping = new GeoCraftingCategory(GuiList.KNAPPING.title());
        mason = new GeoCraftingCategory(GuiList.MASON.title());
        sawpit = new GeoCraftingCategory(GuiList.SAWPIT.title());
        textiles = new GeoCraftingCategory(GuiList.TEXTILES.title());
        woodworking = new GeoCraftingCategory(GuiList.WOODWORKING.title());
        registry.addRecipeCategories(compost, cooking, fuel, drying, inventory, armourer, candlemaker, forge, knapping, mason, sawpit, textiles, woodworking);
    }
    
    @Override
    public void registerItemSubtypes(ISubtypeRegistry registry) {
        
        Geomastery.LOG.info("JEI: Registering decayable item subtypes");
        ISubtypeInterpreter typer = (s) ->
                s.getCapability(GeoCaps.CAP_DECAY, null)
                .isRot(Geomastery.proxy.getClientWorld()) ? "rotten" : "fresh";
        GeoItems.ITEMS.stream().filter((i) -> i instanceof ItemEdibleDecayable
                || i instanceof ItemCarcassDecayable)
                .forEach((i) -> registry.registerSubtypeInterpreter(i, typer));
    }
    
    @Override
    public void register(IModRegistry registry) {
        
        transferHelper = registry.getJeiHelpers().recipeTransferHandlerHelper();
        stackHelper = registry.getJeiHelpers().getStackHelper();
        IRecipeTransferRegistry transfers = registry.getRecipeTransferRegistry();
        IIngredientBlacklist blacklist = registry.getJeiHelpers().getIngredientBlacklist();
        
        Geomastery.LOG.info("JEI: Registering compost recipes");
        registry.handleRecipes(GeoCompostCategory.Recipe.class, GeoCompostCategory.Wrapper::new, compost.getUid());
        registry.addRecipeCatalyst(new ItemStack(GeoBlocks.COMPOSTHEAP.getItem()), compost.getUid());
        transfers.addRecipeTransferHandler(GeoTransferInfo.comp());
        registry.addRecipes(Lists.newArrayList(new GeoCompostCategory.Recipe(GeoRecipes.COMPOST.wet, CompostType.WET), new GeoCompostCategory.Recipe(GeoRecipes.COMPOST.dry, CompostType.DRY)), compost.getUid());

        Geomastery.LOG.info("JEI: Registering cooking recipes");
        registry.handleRecipes(GeoCookingCategory.Recipe.class, GeoCookingCategory.Wrapper::new, cooking.getUid());
        registry.addRecipeCatalyst(new ItemStack(GeoBlocks.FURNACE_CAMPFIRE.getItem()), cooking.getUid());
        registry.addRecipeCatalyst(new ItemStack(GeoBlocks.FURNACE_POTFIRE.getItem()), cooking.getUid());
        registry.addRecipeCatalyst(new ItemStack(GeoBlocks.FURNACE_CLAY.getItem()), cooking.getUid());
        registry.addRecipeCatalyst(new ItemStack(GeoBlocks.FURNACE_STONE.getItem()), cooking.getUid());

        transfers.addRecipeTransferHandler(GeoFillTransfer.cook(ContainerFurnaceSingle.Camp.class), cooking.getUid());
        transfers.addRecipeTransferHandler(GeoFillTransfer.cook(ContainerFurnaceSingle.Pot.class), cooking.getUid());
        transfers.addRecipeTransferHandler(GeoFillTransfer.cook(ContainerFurnaceClay.class), cooking.getUid());
        transfers.addRecipeTransferHandler(GeoFillTransfer.cook(ContainerFurnaceStone.class), cooking.getUid());
        
        Set<GeoCookingCategory.Recipe> cookings = Sets.newHashSet();
        GeoRecipes.CAMPFIRE_ONLY.recipes.entrySet().forEach((e) -> cookings.add(new GeoCookingCategory.Recipe(e, GuiList.CAMPFIRE.title())));
        GeoRecipes.POTFIRE_ONLY.recipes.entrySet().forEach((e) -> cookings.add(new GeoCookingCategory.Recipe(e, GuiList.POTFIRE.title())));
        GeoRecipes.CLAY_ONLY.recipes.entrySet().forEach((e) -> cookings.add(new GeoCookingCategory.Recipe(e, GuiList.CLAY.title())));
        GeoRecipes.STONE_ONLY.recipes.entrySet().forEach((e) -> cookings.add(new GeoCookingCategory.Recipe(e, GuiList.STONE.title())));
        registry.addRecipes(cookings, cooking.getUid());
        
        Geomastery.LOG.info("JEI: Registering fuel recipes");
        registry.handleRecipes(GeoFuelCategory.Recipe.class, GeoFuelCategory.Wrapper::new, fuel.getUid());
        registry.addRecipeCatalyst(new ItemStack(GeoBlocks.FURNACE_CAMPFIRE.getItem()), fuel.getUid());
        registry.addRecipeCatalyst(new ItemStack(GeoBlocks.FURNACE_POTFIRE.getItem()), fuel.getUid());
        registry.addRecipeCatalyst(new ItemStack(GeoBlocks.FURNACE_CLAY.getItem()), fuel.getUid());
        registry.addRecipeCatalyst(new ItemStack(GeoBlocks.FURNACE_STONE.getItem()), fuel.getUid());
        
        transfers.addRecipeTransferHandler(GeoFillTransfer.fuel(ContainerFurnaceSingle.Camp.class), fuel.getUid());
        transfers.addRecipeTransferHandler(GeoFillTransfer.fuel(ContainerFurnaceSingle.Pot.class), fuel.getUid());
        transfers.addRecipeTransferHandler(GeoFillTransfer.fuel(ContainerFurnaceClay.class), fuel.getUid());
        transfers.addRecipeTransferHandler(GeoFillTransfer.fuel(ContainerFurnaceStone.class), fuel.getUid());

        Set<GeoFuelCategory.Recipe> fuels = Sets.newHashSet();
        GeoRecipes.CAMPFIRE_ONLY.fuels.keySet().forEach((i) -> fuels.add(new GeoFuelCategory.Recipe(i, GuiList.CAMPFIRE.title())));
        GeoRecipes.POTFIRE_ONLY.fuels.keySet().forEach((i) -> fuels.add(new GeoFuelCategory.Recipe(i, GuiList.POTFIRE.title())));
        GeoRecipes.CLAY_ONLY.fuels.keySet().forEach((i) -> fuels.add(new GeoFuelCategory.Recipe(i, GuiList.CLAY.title())));
        GeoRecipes.STONE_ONLY.fuels.keySet().forEach((i) -> fuels.add(new GeoFuelCategory.Recipe(i, GuiList.STONE.title())));
        registry.addRecipes(fuels, fuel.getUid());
        
        Geomastery.LOG.info("JEI: Registering drying recipes");
        registry.handleRecipes(GeoDryingCategory.Recipe.class, GeoDryingCategory.Wrapper::new, drying.getUid());
        registry.addRecipeCatalyst(new ItemStack(GeoBlocks.DRYING.getItem()), drying.getUid());
        transfers.addRecipeTransferHandler(GeoFillTransfer.dry(), drying.getUid());
        registry.addRecipes(GeoRecipes.DRYING.recipes.entrySet().stream().map(GeoDryingCategory.Recipe::new).collect(Collectors.toSet()), drying.getUid());
        
        Geomastery.LOG.info("JEI: Registering inventory recipes");
        registry.handleRecipes(GeoCraftingCategory.Recipe.class, GeoCraftingCategory.Wrapper::new, inventory.getUid());
        transfers.addRecipeTransferHandler(GeoTransferInfo.inv());
        registry.addRecipes(getRecipes(GeoRecipes.INVENTORY), inventory.getUid());
        
        Geomastery.LOG.info("JEI: Registering crafting recipes");
        registry.handleRecipes(GeoCraftingCategory.Recipe.class, GeoCraftingCategory.Wrapper::new, armourer.getUid());
        registry.handleRecipes(GeoCraftingCategory.Recipe.class, GeoCraftingCategory.Wrapper::new, candlemaker.getUid());
        registry.handleRecipes(GeoCraftingCategory.Recipe.class, GeoCraftingCategory.Wrapper::new, forge.getUid());
        registry.handleRecipes(GeoCraftingCategory.Recipe.class, GeoCraftingCategory.Wrapper::new, knapping.getUid());
        registry.handleRecipes(GeoCraftingCategory.Recipe.class, GeoCraftingCategory.Wrapper::new, mason.getUid());
        registry.handleRecipes(GeoCraftingCategory.Recipe.class, GeoCraftingCategory.Wrapper::new, sawpit.getUid());
        registry.handleRecipes(GeoCraftingCategory.Recipe.class, GeoCraftingCategory.Wrapper::new, textiles.getUid());
        registry.handleRecipes(GeoCraftingCategory.Recipe.class, GeoCraftingCategory.Wrapper::new, woodworking.getUid());

        registry.addRecipeCatalyst(new ItemStack(GeoBlocks.CRAFTING_ARMOURER.getItem()), armourer.getUid());
        registry.addRecipeCatalyst(new ItemStack(GeoBlocks.CRAFTING_CANDLEMAKER.getItem()), candlemaker.getUid());
        registry.addRecipeCatalyst(new ItemStack(GeoBlocks.CRAFTING_FORGE.getItem()), forge.getUid());
        registry.addRecipeCatalyst(new ItemStack(GeoBlocks.CRAFTING_KNAPPING.getItem()), knapping.getUid());
        registry.addRecipeCatalyst(new ItemStack(GeoBlocks.CRAFTING_MASON.getItem()), mason.getUid());
        registry.addRecipeCatalyst(new ItemStack(GeoBlocks.CRAFTING_SAWPIT.getItem()), sawpit.getUid());
        registry.addRecipeCatalyst(new ItemStack(GeoBlocks.CRAFTING_TEXTILES.getItem()), textiles.getUid());
        registry.addRecipeCatalyst(new ItemStack(GeoBlocks.CRAFTING_WOODWORKING.getItem()), woodworking.getUid());

        transfers.addRecipeTransferHandler(GeoTransferInfo.craft(ContainerCrafting.Armourer.class, armourer.getUid()));
        transfers.addRecipeTransferHandler(GeoTransferInfo.craft(ContainerCrafting.Candlemaker.class, candlemaker.getUid()));
        transfers.addRecipeTransferHandler(GeoTransferInfo.craft(ContainerCrafting.Forge.class, forge.getUid()));
        transfers.addRecipeTransferHandler(GeoTransferInfo.craft(ContainerCrafting.Knapping.class, knapping.getUid()));
        transfers.addRecipeTransferHandler(GeoTransferInfo.craft(ContainerCrafting.Knapping.class, knapping.getUid()));
        transfers.addRecipeTransferHandler(GeoTransferInfo.craft(ContainerCrafting.Mason.class, mason.getUid()));
        transfers.addRecipeTransferHandler(GeoTransferInfo.craft(ContainerCrafting.Sawpit.class, sawpit.getUid()));
        transfers.addRecipeTransferHandler(GeoTransferInfo.craft(ContainerCrafting.Textiles.class, textiles.getUid()));
        transfers.addRecipeTransferHandler(GeoTransferInfo.craft(ContainerCrafting.Woodworking.class, woodworking.getUid()));
        
        registry.addRecipes(getRecipes(GeoRecipes.ARMOURER), armourer.getUid());
        registry.addRecipes(getRecipes(GeoRecipes.CANDLEMAKER), candlemaker.getUid());
        registry.addRecipes(getRecipes(GeoRecipes.FORGE), forge.getUid());
        registry.addRecipes(getRecipes(GeoRecipes.KNAPPING), knapping.getUid());
        registry.addRecipes(getRecipes(GeoRecipes.MASON), mason.getUid());
        registry.addRecipes(getRecipes(GeoRecipes.SAWPIT), sawpit.getUid());
        registry.addRecipes(getRecipes(GeoRecipes.TEXTILES), textiles.getUid());
        registry.addRecipes(getRecipes(GeoRecipes.WOODWORKING), woodworking.getUid());
        
        if (GeoConfig.compatibility.hideVanilla) {
            
            Geomastery.LOG.info("JEI: Blacklisting vanilla items");
            Set<Item> vanillaItems = Sets.newHashSet(Items.PAINTING, Items.BED, Items.ITEM_FRAME, Items.FLOWER_POT, Items.SKULL, Items.ARMOR_STAND, Items.BANNER, Items.END_CRYSTAL, Items.ACACIA_DOOR, Items.BIRCH_DOOR, Items.DARK_OAK_DOOR, Items.IRON_DOOR, Items.JUNGLE_DOOR, Items.OAK_DOOR, Items.SPRUCE_DOOR, Items.MINECART, Items.CHEST_MINECART, Items.COMMAND_BLOCK_MINECART, Items.FURNACE_MINECART, Items.HOPPER_MINECART, Items.TNT_MINECART, Items.CARROT_ON_A_STICK, Items.ELYTRA, Items.BUCKET, Items.LAVA_BUCKET, Items.MILK_BUCKET, Items.WATER_BUCKET, Items.SNOWBALL, Items.PAPER, Items.BOOK, Items.SLIME_BALL, Items.ENDER_EYE, Items.ENDER_PEARL, Items.SPAWN_EGG, Items.EXPERIENCE_BOTTLE, Items.FIRE_CHARGE, Items.WRITABLE_BOOK, Items.WRITTEN_BOOK, Items.ENCHANTED_BOOK, Items.FIREWORK_CHARGE, Items.MAP, Items.DIAMOND_HORSE_ARMOR, Items.GOLDEN_HORSE_ARMOR, Items.IRON_HORSE_ARMOR, Items.RECORD_11, Items.RECORD_13, Items.RECORD_BLOCKS, Items.RECORD_CAT, Items.RECORD_CHIRP, Items.RECORD_FAR, Items.RECORD_MALL, Items.RECORD_MELLOHI, Items.RECORD_STAL, Items.RECORD_STRAD, Items.RECORD_WAIT, Items.RECORD_WARD, Items.APPLE, Items.MUSHROOM_STEW, Items.BREAD, Items.PORKCHOP, Items.COOKED_PORKCHOP, Items.GOLDEN_APPLE, Items.FISH, Items.COOKED_FISH, Items.CAKE, Items.COOKIE, Items.COMPARATOR, Items.REPEATER, Items.MELON, Items.BEEF, Items.COOKED_BEEF, Items.CHICKEN, Items.COOKED_CHICKEN, Items.ROTTEN_FLESH, Items.SPIDER_EYE, Items.CARROT, Items.POTATO, Items.BAKED_POTATO, Items.POISONOUS_POTATO, Items.PUMPKIN_PIE, Items.RABBIT, Items.COOKED_RABBIT, Items.RABBIT_STEW, Items.MUTTON, Items.COOKED_MUTTON, Items.BEETROOT_SOUP, Items.DIAMOND_SHOVEL, Items.GOLDEN_SHOVEL, Items.IRON_SHOVEL, Items.STONE_SHOVEL, Items.WOODEN_SHOVEL, Items.DIAMOND_PICKAXE, Items.BEETROOT, Items.GOLDEN_PICKAXE, Items.IRON_PICKAXE, Items.STONE_PICKAXE, Items.WOODEN_PICKAXE, Items.DIAMOND_AXE, Items.GOLDEN_AXE, Items.IRON_AXE, Items.STONE_AXE, Items.WOODEN_AXE, Items.FLINT_AND_STEEL, Items.COMPASS, Items.DIAMOND_HOE, Items.GOLDEN_HOE, Items.IRON_HOE, Items.STONE_HOE, Items.WOODEN_HOE, Items.CLOCK, Items.SHEARS, Items.LEAD, Items.NAME_TAG, Items.BOW, Items.ARROW, Items.DIAMOND_SWORD, Items.GOLDEN_SWORD, Items.IRON_SWORD, Items.STONE_SWORD, Items.WOODEN_SWORD, Items.CHAINMAIL_HELMET, Items.DIAMOND_HELMET, Items.GOLDEN_HELMET, Items.IRON_HELMET, Items.LEATHER_HELMET, Items.CHAINMAIL_CHESTPLATE, Items.DIAMOND_CHESTPLATE, Items.GOLDEN_CHESTPLATE, Items.IRON_CHESTPLATE, Items.LEATHER_CHESTPLATE, Items.CHAINMAIL_LEGGINGS, Items.DIAMOND_LEGGINGS, Items.GOLDEN_LEGGINGS, Items.IRON_LEGGINGS, Items.LEATHER_LEGGINGS, Items.CHAINMAIL_BOOTS, Items.DIAMOND_BOOTS, Items.GOLDEN_BOOTS, Items.IRON_BOOTS, Items.LEATHER_BOOTS, Items.SPECTRAL_ARROW, Items.TIPPED_ARROW, Items.SHIELD, Items.TOTEM, Items.GHAST_TEAR, Items.GLASS_BOTTLE, Items.POTIONITEM, Items.LINGERING_POTION, Items.SPLASH_POTION, Items.FERMENTED_SPIDER_EYE, Items.BLAZE_POWDER, Items.MAGMA_CREAM, Items.BREWING_STAND, Items.CAULDRON, Items.SPECKLED_MELON, Items.GOLDEN_CARROT, Items.RABBIT_FOOT, Items.DRAGON_BREATH, Items.IRON_INGOT, Items.BOWL, Items.STRING, Items.GUNPOWDER, Items.WHEAT_SEEDS, Items.WHEAT, Items.CLAY_BALL, Items.GLOWSTONE_DUST, Items.DYE, Items.BEETROOT_SEEDS, Items.MELON_SEEDS, Items.PUMPKIN_SEEDS, Items.BLAZE_ROD, Items.GOLD_NUGGET, Items.NETHER_WART, Items.NETHER_STAR, Items.NETHERBRICK, Items.QUARTZ, Items.PRISMARINE_CRYSTALS, Items.PRISMARINE_SHARD, Items.CHORUS_FRUIT, Items.CHORUS_FRUIT_POPPED, Items.SHULKER_SHELL, /* Iron nugget */ Items.field_191525_da);
            Set<Block> vanillaBlocks = Sets.newHashSet(Blocks.PLANKS, Blocks.SPONGE, Blocks.GLASS, Blocks.WOOL, Blocks.LAPIS_BLOCK, Blocks.GOLD_BLOCK, Blocks.IRON_BLOCK, Blocks.STONE_SLAB, Blocks.STONE_SLAB2, Blocks.BRICK_BLOCK, Blocks.BOOKSHELF, Blocks.ACACIA_STAIRS, Blocks.BIRCH_STAIRS, Blocks.BRICK_STAIRS, Blocks.DARK_OAK_STAIRS, Blocks.JUNGLE_STAIRS, Blocks.NETHER_BRICK_STAIRS, Blocks.OAK_STAIRS, Blocks.PURPUR_STAIRS, Blocks.QUARTZ_STAIRS, Blocks.RED_SANDSTONE_STAIRS, Blocks.SANDSTONE_STAIRS, Blocks.SPRUCE_STAIRS, Blocks.STONE_BRICK_STAIRS, Blocks.STONE_STAIRS, Blocks.DIAMOND_BLOCK, Blocks.PUMPKIN, Blocks.LIT_PUMPKIN, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK_FENCE, Blocks.NETHER_BRICK_STAIRS, Blocks.NETHER_WART_BLOCK, Blocks.NETHERRACK, Blocks.RED_NETHER_BRICK, Blocks.SOUL_SAND, Blocks.GLOWSTONE, Blocks.STAINED_GLASS, Blocks.STONEBRICK, Blocks.MELON_BLOCK, Blocks.END_BRICKS, Blocks.END_STONE, Blocks.PURPUR_SLAB, Blocks.WOODEN_SLAB, Blocks.EMERALD_BLOCK, Blocks.QUARTZ_BLOCK, Blocks.QUARTZ_ORE, Blocks.QUARTZ_STAIRS, Blocks.COAL_BLOCK, Blocks.PURPUR_BLOCK, Blocks.PURPUR_PILLAR, Blocks.MAGMA, Blocks.CHEST, Blocks.FURNACE, Blocks.JUKEBOX, Blocks.ACACIA_FENCE, Blocks.BIRCH_FENCE, Blocks.DARK_OAK_FENCE, Blocks.JUNGLE_FENCE, Blocks.NETHER_BRICK_FENCE, Blocks.OAK_FENCE, Blocks.SPRUCE_FENCE, Blocks.MONSTER_EGG, Blocks.IRON_BARS, Blocks.GLASS_PANE, Blocks.ENCHANTING_TABLE, Blocks.END_PORTAL_FRAME, Blocks.ENDER_CHEST, Blocks.COBBLESTONE_WALL, Blocks.ANVIL, Blocks.STAINED_GLASS_PANE, Blocks.CARPET, Blocks.SLIME_BLOCK, Blocks.END_ROD, Blocks.CHORUS_FLOWER, Blocks.CHORUS_PLANT, Blocks.BLACK_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.SILVER_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX, Blocks.DISPENSER, Blocks.DROPPER, Blocks.NOTEBLOCK, Blocks.PISTON, Blocks.STICKY_PISTON, Blocks.TNT, Blocks.LEVER, Blocks.ACACIA_FENCE_GATE, Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE, Blocks.STONE_PRESSURE_PLATE, Blocks.WOODEN_PRESSURE_PLATE, Blocks.REDSTONE_TORCH, Blocks.STONE_BUTTON, Blocks.WOODEN_BUTTON, Blocks.TRAPDOOR, Blocks.BIRCH_FENCE_GATE, Blocks.DARK_OAK_FENCE_GATE, Blocks.JUNGLE_FENCE_GATE, Blocks.OAK_FENCE_GATE, Blocks.SPRUCE_FENCE_GATE, Blocks.REDSTONE_LAMP, Blocks.TRIPWIRE_HOOK, Blocks.TRAPPED_CHEST, Blocks.DAYLIGHT_DETECTOR, Blocks.REDSTONE_BLOCK, Blocks.HOPPER, Blocks.IRON_TRAPDOOR, Blocks.RAIL, Blocks.ACTIVATOR_RAIL, Blocks.DETECTOR_RAIL, Blocks.GOLDEN_RAIL, Blocks.BEACON, Blocks.OBSERVER, Blocks.WHITE_SHULKER_BOX, Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE);

            for (Item item : vanillaItems) {
                
                if (!item.getHasSubtypes()) {
                    
                    blacklist.addIngredientToBlacklist(new ItemStack(item));
                    
                } else {
                    
                    for (CreativeTabs tab : CreativeTabs.CREATIVE_TAB_ARRAY) {
                        
                        NonNullList<ItemStack> list = NonNullList.create();
                        item.getSubItems(item, tab, list);
                        
                        for (ItemStack stack : list) {
                            
                            blacklist.addIngredientToBlacklist(stack);
                        }
                    }
                }
            }

            for (Block block : vanillaBlocks) {
                
                if (!Item.getItemFromBlock(block).getHasSubtypes()) {
                    
                    blacklist.addIngredientToBlacklist(new ItemStack(block));
                    
                } else {
                    
                    for (CreativeTabs tab : CreativeTabs.CREATIVE_TAB_ARRAY) {
                        
                        NonNullList<ItemStack> list = NonNullList.create();
                        block.getSubBlocks(Item.getItemFromBlock(block),
                                tab, list);
                        
                        for (ItemStack stack : list) {
                            
                            blacklist.addIngredientToBlacklist(stack);
                        }
                    }
                }
            }
            
            if (!GeoConfig.compatibility.addCrafting) {
                
                blacklist.addIngredientToBlacklist(
                        new ItemStack(Blocks.CRAFTING_TABLE));
            }
        }
    }
    
    /** @return A collection of recipes, combining duplicated outputs. */
    private static Collection<GeoCraftingCategory.Recipe> getRecipes(
            CraftingManager recipes) {

        Map<Item, GeoCraftingCategory.Recipe> map = Maps.newHashMap();
        
        for (ShapedRecipe recipe : recipes.getRecipeList()) {
            
            Item key = recipe.getRecipeOutput().getItem();
                
            if (map.containsKey(key)) {
                
                map.get(key).addRecipe(recipe);
                
            } else {
                
                map.put(key, new GeoCraftingCategory.Recipe(recipe));
            }
        }
        
        return map.values();
    }
}

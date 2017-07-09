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
import mezz.jei.api.gui.ICraftingGridHelper;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.IStackHelper;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandlerHelper;
import mezz.jei.api.recipe.transfer.IRecipeTransferRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

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
        armourer = new GeoCraftingCategory(GuiList.ARMOURER.name, Geomastery.MODID + ":armourer");
        candlemaker = new GeoCraftingCategory(GuiList.CANDLEMAKER.name, Geomastery.MODID + ":candlemaker");
        forge = new GeoCraftingCategory(GuiList.FORGE.name, Geomastery.MODID + ":forge");
        knapping = new GeoCraftingCategory(GuiList.KNAPPING.name, Geomastery.MODID + ":knapping");
        mason = new GeoCraftingCategory(GuiList.MASON.name, Geomastery.MODID + ":mason");
        sawpit = new GeoCraftingCategory(GuiList.SAWPIT.name, Geomastery.MODID + ":sawpit");
        textiles = new GeoCraftingCategory(GuiList.TEXTILES.name, Geomastery.MODID + ":textiles");
        woodworking = new GeoCraftingCategory(GuiList.WOODWORKING.name, Geomastery.MODID + ":woodworking");
        registry.addRecipeCategories(compost, cooking, fuel, drying, inventory, armourer, candlemaker, forge, knapping, mason, sawpit, textiles, woodworking);
    }
    
    @Override
    public void registerItemSubtypes(ISubtypeRegistry registry) {
        
        Geomastery.LOG.info("JEI: Registering decayable item subtypes");
        ISubtypeInterpreter typer = (s) -> s.getCapability(GeoCaps.CAP_DECAY, null).isRot(Geomastery.proxy.getClientWorld()) ? "rotten" : "fresh";
        GeoItems.ITEMS.stream().filter((i) -> i instanceof ItemEdibleDecayable || i instanceof ItemCarcassDecayable).forEach((i) -> registry.registerSubtypeInterpreter(i, typer));
    }
    
    @Override
    public void register(IModRegistry registry) {
        
        transferHelper = registry.getJeiHelpers().recipeTransferHandlerHelper();
        stackHelper = registry.getJeiHelpers().getStackHelper();
        IRecipeTransferRegistry transfers = registry.getRecipeTransferRegistry();
        
        Geomastery.LOG.info("JEI: Registering compost recipes");
        registry.handleRecipes(GeoCompostCategory.Recipe.class, GeoCompostCategory.Wrapper::new, compost.getUid());
        registry.addRecipeCatalyst(new ItemStack(GeoBlocks.COMPOSTHEAP), compost.getUid());
        transfers.addRecipeTransferHandler(GeoTransferInfo.comp());
        registry.addRecipes(Lists.newArrayList(new GeoCompostCategory.Recipe(GeoRecipes.COMPOST.wet, CompostType.WET), new GeoCompostCategory.Recipe(GeoRecipes.COMPOST.dry, CompostType.DRY)), compost.getUid());

        Geomastery.LOG.info("JEI: Registering cooking recipes");
        registry.handleRecipes(GeoCookingCategory.Recipe.class, GeoCookingCategory.Wrapper::new, cooking.getUid());
        registry.addRecipeCatalyst(new ItemStack(GeoBlocks.FURNACE_CAMPFIRE), cooking.getUid());
        registry.addRecipeCatalyst(new ItemStack(GeoBlocks.FURNACE_POTFIRE), cooking.getUid());
        registry.addRecipeCatalyst(new ItemStack(GeoItems.FURNACE_CLAY), cooking.getUid());
        registry.addRecipeCatalyst(new ItemStack(GeoItems.FURNACE_STONE), cooking.getUid());

        transfers.addRecipeTransferHandler(GeoFillTransfer.cook(ContainerFurnaceSingle.Camp.class), cooking.getUid());
        transfers.addRecipeTransferHandler(GeoFillTransfer.cook(ContainerFurnaceSingle.Pot.class), cooking.getUid());
        transfers.addRecipeTransferHandler(GeoFillTransfer.cook(ContainerFurnaceClay.class), cooking.getUid());
        transfers.addRecipeTransferHandler(GeoFillTransfer.cook(ContainerFurnaceStone.class), cooking.getUid());
        
        Set<GeoCookingCategory.Recipe> cookings = Sets.newHashSet();
        GeoRecipes.CAMPFIRE_ONLY.recipes.entrySet().forEach((e) -> cookings.add(new GeoCookingCategory.Recipe(e, GuiList.CAMPFIRE.name)));
        GeoRecipes.POTFIRE_ONLY.recipes.entrySet().forEach((e) -> cookings.add(new GeoCookingCategory.Recipe(e, GuiList.POTFIRE.name)));
        GeoRecipes.CLAY_ONLY.recipes.entrySet().forEach((e) -> cookings.add(new GeoCookingCategory.Recipe(e, GuiList.CLAY.name)));
        GeoRecipes.STONE_ONLY.recipes.entrySet().forEach((e) -> cookings.add(new GeoCookingCategory.Recipe(e, GuiList.STONE.name)));
        registry.addRecipes(cookings, cooking.getUid());
        
        Geomastery.LOG.info("JEI: Registering fuel recipes");
        registry.handleRecipes(GeoFuelCategory.Recipe.class, GeoFuelCategory.Wrapper::new, fuel.getUid());
        registry.addRecipeCatalyst(new ItemStack(GeoBlocks.FURNACE_CAMPFIRE), fuel.getUid());
        registry.addRecipeCatalyst(new ItemStack(GeoBlocks.FURNACE_POTFIRE), fuel.getUid());
        registry.addRecipeCatalyst(new ItemStack(GeoItems.FURNACE_CLAY), fuel.getUid());
        registry.addRecipeCatalyst(new ItemStack(GeoItems.FURNACE_STONE), fuel.getUid());
        
        transfers.addRecipeTransferHandler(GeoFillTransfer.fuel(ContainerFurnaceSingle.Camp.class), fuel.getUid());
        transfers.addRecipeTransferHandler(GeoFillTransfer.fuel(ContainerFurnaceSingle.Pot.class), fuel.getUid());
        transfers.addRecipeTransferHandler(GeoFillTransfer.fuel(ContainerFurnaceClay.class), fuel.getUid());
        transfers.addRecipeTransferHandler(GeoFillTransfer.fuel(ContainerFurnaceStone.class), fuel.getUid());

        Set<GeoFuelCategory.Recipe> fuels = Sets.newHashSet();
        GeoRecipes.CAMPFIRE_ONLY.fuels.keySet().forEach((k) -> fuels.add(new GeoFuelCategory.Recipe(k, GuiList.CAMPFIRE.name)));
        GeoRecipes.POTFIRE_ONLY.fuels.keySet().forEach((k) -> fuels.add(new GeoFuelCategory.Recipe(k, GuiList.POTFIRE.name)));
        GeoRecipes.CLAY_ONLY.fuels.keySet().forEach((k) -> fuels.add(new GeoFuelCategory.Recipe(k, GuiList.CLAY.name)));
        GeoRecipes.STONE_ONLY.fuels.keySet().forEach((k) -> fuels.add(new GeoFuelCategory.Recipe(k, GuiList.STONE.name)));
        registry.addRecipes(fuels, fuel.getUid());
        
        Geomastery.LOG.info("JEI: Registering drying recipes");
        registry.handleRecipes(GeoDryingCategory.Recipe.class, GeoDryingCategory.Wrapper::new, drying.getUid());
        registry.addRecipeCatalyst(new ItemStack(GeoBlocks.DRYING), drying.getUid());
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

        registry.addRecipeCatalyst(new ItemStack(GeoItems.CRAFTING_ARMOURER), armourer.getUid());
        registry.addRecipeCatalyst(new ItemStack(GeoItems.CRAFTING_CANDLEMAKER), candlemaker.getUid());
        registry.addRecipeCatalyst(new ItemStack(GeoItems.CRAFTING_FORGE), forge.getUid());
        registry.addRecipeCatalyst(new ItemStack(GeoBlocks.CRAFTING_KNAPPING), knapping.getUid());
        registry.addRecipeCatalyst(new ItemStack(GeoItems.CRAFTING_MASON), mason.getUid());
        registry.addRecipeCatalyst(new ItemStack(GeoItems.CRAFTING_SAWPIT), sawpit.getUid());
        registry.addRecipeCatalyst(new ItemStack(GeoItems.CRAFTING_TEXTILES), textiles.getUid());
        registry.addRecipeCatalyst(new ItemStack(GeoItems.CRAFTING_WOODWORKING), woodworking.getUid());

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

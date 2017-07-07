/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.compat;

import java.util.Collection;
import java.util.stream.Collectors;
import com.google.common.collect.Sets;
import jayavery.geomastery.container.ContainerCrafting;
import jayavery.geomastery.container.ContainerCrafting.Armourer;
import jayavery.geomastery.container.ContainerCrafting.Candlemaker;
import jayavery.geomastery.container.ContainerCrafting.Forge;
import jayavery.geomastery.container.ContainerCrafting.Knapping;
import jayavery.geomastery.container.ContainerCrafting.Mason;
import jayavery.geomastery.container.ContainerCrafting.Sawpit;
import jayavery.geomastery.container.ContainerCrafting.Textiles;
import jayavery.geomastery.container.ContainerCrafting.Woodworking;
import jayavery.geomastery.container.ContainerFurnaceAbstract;
import jayavery.geomastery.container.ContainerFurnaceClay;
import jayavery.geomastery.container.ContainerFurnaceSingle;
import jayavery.geomastery.container.ContainerFurnaceStone;
import jayavery.geomastery.crafting.CookingManager;
import jayavery.geomastery.crafting.CraftingManager;
import jayavery.geomastery.crafting.ShapedRecipe;
import jayavery.geomastery.items.ItemCarcassDecayable;
import jayavery.geomastery.items.ItemEdibleDecayable;
import jayavery.geomastery.main.GeoBlocks;
import jayavery.geomastery.main.GeoCaps;
import jayavery.geomastery.main.GeoItems;
import jayavery.geomastery.main.GeoRecipes;
import jayavery.geomastery.main.Geomastery;
import mezz.jei.api.BlankModPlugin;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.ISubtypeRegistry;
import mezz.jei.api.ISubtypeRegistry.ISubtypeInterpreter;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandlerHelper;
import mezz.jei.api.recipe.transfer.IRecipeTransferRegistry;
import mezz.jei.plugins.vanilla.crafting.ShapedRecipesWrapper;
import mezz.jei.startup.StackHelper;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;

@JEIPlugin
public class GeoJei extends BlankModPlugin {
    
    static IRecipeTransferHandlerHelper transferHelper;
    static StackHelper stackHelper;
    static IGuiHelper guiHelper;
    
    private static IRecipeTransferRegistry transfers;
    private static IModRegistry registry;
    
    private static Collection<FuelRecipe> fuels = Sets.newHashSet();
    private static Collection<CookingRecipe> cookings = Sets.newHashSet();
    
    @Override
    public void register(IModRegistry registryIn) {
        
        transferHelper = registryIn.getJeiHelpers().recipeTransferHandlerHelper();
        stackHelper = (StackHelper) registryIn.getJeiHelpers().getStackHelper();
        guiHelper = registryIn.getJeiHelpers().getGuiHelper();
        transfers = registryIn.getRecipeTransferRegistry();
        registry = registryIn;
        
        Geomastery.LOG.info("JEI: Registering crafting recipes, handlers, and transfers");
        
        crafting("Armourer", "armourer", GeoItems.CRAFTING_ARMOURER, Armourer.class, GeoRecipes.ARMOURER);
        crafting("Candlemaker's Bench", "candlemaker", GeoItems.CRAFTING_CANDLEMAKER, Candlemaker.class, GeoRecipes.CANDLEMAKER);
        crafting("Forge", "forge", GeoItems.CRAFTING_FORGE, Forge.class, GeoRecipes.FORGE);
        crafting("Knapping Block", "knapping", Item.getItemFromBlock(GeoBlocks.CRAFTING_KNAPPING), Knapping.class, GeoRecipes.KNAPPING);
        crafting("Mason's workshop", "mason", GeoItems.CRAFTING_MASON, Mason.class, GeoRecipes.MASON);
        crafting("Sawpit", "sawpit", GeoItems.CRAFTING_SAWPIT, Sawpit.class, GeoRecipes.SAWPIT);
        crafting("Textiles Table", "textiles", GeoItems.CRAFTING_TEXTILES, Textiles.class, GeoRecipes.TEXTILES);
        crafting("Woodworking Bench", "woodworking", GeoItems.CRAFTING_WOODWORKING, Woodworking.class, GeoRecipes.WOODWORKING);
        
        String invUid = "geomastery:inventory";
        transfers.addRecipeTransferHandler(GridTransferHandler.inv(), invUid);
        registry.handleRecipes(ShapedRecipe.class, ShapedRecipesWrapper::new, invUid);
        registry.addRecipes(GeoRecipes.INVENTORY.getRecipeList(), invUid);
        registry.addRecipeCategories(new GeoInventoryCategory());
        
        String dryUid = "geomastery:drying";
        transfers.addRecipeTransferHandler(FillTransferHandler.dry(), dryUid);
        registry.handleRecipes(DryingRecipe.class, DryingRecipe.Wrapper::new, dryUid);
        registry.addRecipes(GeoRecipes.DRYING.recipes.entrySet().stream().map(DryingRecipe::new).collect(Collectors.toSet()), dryUid);
        registry.addRecipeCategories(new GeoDryingCategory());
        registry.addRecipeCatalyst(new ItemStack(GeoBlocks.DRYING), dryUid);
        
        String compUid = "geomastery:compost";
        transfers.addRecipeTransferHandler(GridTransferHandler.comp(), compUid);
        registry.handleRecipes(CompostRecipe.class, CompostRecipe.Wrapper::new, compUid);
        registry.addRecipes(GeoRecipes.COMPOST.recipes.entrySet().stream().map(CompostRecipe::new).collect(Collectors.toSet()), compUid);
        registry.addRecipeCategories(new GeoCompostCategory());
        registry.addRecipeCatalyst(new ItemStack(GeoBlocks.COMPOSTHEAP), compUid);
        
        String cookUid = "geomastery:cooking";
        String fuelUid = "geomastery:fuel";

        registry.handleRecipes(CookingRecipe.class, CookingRecipe.Wrapper::new, cookUid);
        registry.handleRecipes(FuelRecipe.class, FuelRecipe.Wrapper::new, fuelUid);
        registry.addRecipeCategories(new GeoCookingCategory("Cooking", cookUid), new GeoFuelCategory("Fuel", fuelUid));
        registry.addRecipeCatalyst(new ItemStack(GeoBlocks.FURNACE_CAMPFIRE), cookUid);
        
        furnace("Campfire", ContainerFurnaceSingle.Camp.class, GeoRecipes.CAMPFIRE_ONLY);
        furnace("Pot Fire", ContainerFurnaceSingle.Pot.class, GeoRecipes.POTFIRE_ONLY);
        furnace("Clay Oven", ContainerFurnaceClay.class, GeoRecipes.CLAY_ONLY);
        furnace("Stone Furnace", ContainerFurnaceStone.class, GeoRecipes.STONE_ONLY);
        
        registry.addRecipes(cookings, cookUid);
        registry.addRecipes(fuels, fuelUid);
    }
    
    @Override
    public void registerItemSubtypes(ISubtypeRegistry registry) {
        
        ISubtypeInterpreter subtyper = (s) -> s.getCapability(GeoCaps.CAP_DECAY, null).isRot(Geomastery.proxy.getClientWorld()) ? "rotten" : "fresh";
        GeoItems.ITEMS.stream().filter((i) -> i instanceof ItemEdibleDecayable || i instanceof ItemCarcassDecayable).forEach((i) -> registry.registerSubtypeInterpreter(i, subtyper));
    }
    
    private static void crafting(String name, String id, Item item, Class<? extends ContainerCrafting> clas, CraftingManager recipes) {
        
        String uid = "geomastery:crafting." + id;
        transfers.addRecipeTransferHandler(GridTransferHandler.craft(clas), uid);
        registry.handleRecipes(ShapedRecipes.class, ShapedRecipesWrapper::new, uid);
        registry.addRecipes(recipes.getRecipeList(), uid);
        registry.addRecipeCatalyst(new ItemStack(item), uid);
        registry.addRecipeCategories(new GeoCraftingCategory(guiHelper, uid, name));
    }
    

    
    private static void furnace(String name, Class<? extends ContainerFurnaceAbstract> clas, CookingManager recipes) {
        
        String cookUid = "geomastery:cooking";
        String fuelUid = "geomastery:fuel";
        transfers.addRecipeTransferHandler(FillTransferHandler.fuel(clas), fuelUid);
        transfers.addRecipeTransferHandler(FillTransferHandler.cook(clas), cookUid);
        recipes.recipes.entrySet().forEach((e) -> cookings.add(new CookingRecipe(e, name)));
        recipes.fuels.keySet().forEach((k) -> fuels.add(new FuelRecipe(k, name)));
    }
}

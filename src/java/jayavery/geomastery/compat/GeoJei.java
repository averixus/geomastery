/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.compat;

import java.util.Collection;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import org.apache.commons.lang3.tuple.Pair;
import com.google.common.collect.Lists;
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
import jayavery.geomastery.container.ContainerFurnaceSingle;
import jayavery.geomastery.crafting.CookingManager;
import jayavery.geomastery.crafting.CraftingManager;
import jayavery.geomastery.main.GeoBlocks;
import jayavery.geomastery.main.GeoItems;
import jayavery.geomastery.main.GeoRecipes;
import jayavery.geomastery.main.Geomastery;
import mezz.jei.api.BlankModPlugin;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandlerHelper;
import mezz.jei.api.recipe.transfer.IRecipeTransferRegistry;
import mezz.jei.plugins.vanilla.crafting.ShapedRecipesWrapper;
import mezz.jei.startup.StackHelper;
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
    
        furnace("Campfire", "campfire", Item.getItemFromBlock(GeoBlocks.FURNACE_CAMPFIRE), ContainerFurnaceSingle.class, GeoRecipes.CAMPFIRE);
    
    }
    
    private static void crafting(String name, String id, Item item, Class<? extends ContainerCrafting> clas, CraftingManager recipes) {
        
        String uid = "geomastery:crafting." + id;
        transfers.addRecipeTransferHandler(ContainerTransferHandler.craft(clas), uid);
        registry.handleRecipes(ShapedRecipes.class, ShapedRecipesWrapper::new, uid);
        registry.addRecipes(recipes.getRecipeList(), uid);
        registry.addRecipeCatalyst(new ItemStack(item), uid);
        registry.addRecipeCategories(new GeoCraftingRecipeCategory(guiHelper, uid, name));
    }
    
    private static void furnace(String name, String id, Item item, Class<? extends ContainerFurnaceAbstract> clas, CookingManager recipes) {
        
        String cookUid = "geomastery:cooking." + id;
        String fuelUid = "geomastery:fuel." + id;
        transfers.addRecipeTransferHandler(ContainerTransferHandler.fuel(clas), fuelUid);
        transfers.addRecipeTransferHandler(ContainerTransferHandler.cook(clas), cookUid);
        registry.handleRecipes(Entry.class, CookingRecipeWrapper::new, cookUid);
        registry.handleRecipes(Pair.class, FuelRecipeWrapper::new, fuelUid);
        registry.addRecipes(recipes.recipes.entrySet(), cookUid);
        Collection<Pair<Entry<ItemStack, Integer>, String>> fuels = recipes.fuels.entrySet().stream().map((e) -> Pair.of(e, name)).collect(Collectors.toSet());
        registry.addRecipes(fuels, fuelUid);
        registry.addRecipeCatalyst(new ItemStack(item), cookUid);
        registry.addRecipeCatalyst(new ItemStack(item), fuelUid);
        registry.addRecipeCategories(new GeoCampfireRecipeCategory(name, cookUid), new GeoCampfireFuelRecipeCategory(name, fuelUid));
    }
}

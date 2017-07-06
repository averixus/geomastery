/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.compat;

import jayavery.geomastery.container.ContainerCrafting;
import jayavery.geomastery.container.ContainerCrafting.Armourer;
import jayavery.geomastery.container.ContainerCrafting.Candlemaker;
import jayavery.geomastery.container.ContainerCrafting.Forge;
import jayavery.geomastery.container.ContainerCrafting.Knapping;
import jayavery.geomastery.container.ContainerCrafting.Mason;
import jayavery.geomastery.container.ContainerCrafting.Sawpit;
import jayavery.geomastery.container.ContainerCrafting.Textiles;
import jayavery.geomastery.container.ContainerCrafting.Woodworking;
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
import net.minecraft.block.Block;
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
    }
    
    private static void crafting(String name, String id, Item item, Class<? extends ContainerCrafting> clas, CraftingManager recipes) {
        
        String uid = "geomastery:crafting." + id;
        transfers.addRecipeTransferHandler(CraftingTransferHandler.create(clas), uid);
        registry.handleRecipes(ShapedRecipes.class, ShapedRecipesWrapper::new, uid);
        registry.addRecipes(recipes.getRecipeList(), uid);
        registry.addRecipeCatalyst(new ItemStack(item), uid);
        registry.addRecipeCategories(new GeoCraftingRecipeCategory(guiHelper, uid, name));
    }
}

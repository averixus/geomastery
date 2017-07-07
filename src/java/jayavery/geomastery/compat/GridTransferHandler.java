/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.compat;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import jayavery.geomastery.container.ContainerAbstract;
import jayavery.geomastery.container.ContainerCompost;
import jayavery.geomastery.container.ContainerCrafting;
import jayavery.geomastery.container.ContainerDrying;
import jayavery.geomastery.container.ContainerFurnaceAbstract;
import jayavery.geomastery.container.ContainerInventory;
import mezz.jei.JustEnoughItems;
import mezz.jei.api.gui.IGuiIngredient;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.recipe.transfer.IRecipeTransferError;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandler;
import mezz.jei.network.packets.PacketRecipeTransfer;
import mezz.jei.startup.StackHelper.MatchingItemsResult;
import mezz.jei.util.Translator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public abstract class GridTransferHandler<C extends ContainerAbstract> implements IRecipeTransferHandler<C> {

    private final Class<C> clas;
    
    public GridTransferHandler(Class<C> clas) {
        
        this.clas = clas;
    }
    
    public static <T extends ContainerCrafting> GridTransferHandler.Crafting<T> craft(Class<T> clas) {
        
        return new GridTransferHandler.Crafting<T>(clas);
    }
    
    public static GridTransferHandler.Inventory inv() {
        
        return new GridTransferHandler.Inventory();
    }
    
    public static GridTransferHandler.Compost comp() {
        
        return new GridTransferHandler.Compost();
    }

    @Override
    public Class<C> getContainerClass() {
        
        return this.clas;
    }
    
    protected abstract int getInvStart(C container);
    protected abstract int getInvEnd(C container);
    protected abstract int getCraftStart(C container);
    protected abstract int getCraftEnd(C container);
    
    @Override
    public IRecipeTransferError transferRecipe(C container, IRecipeLayout recipeLayout, EntityPlayer player, boolean maxTransfer, boolean doTransfer) {
        
        Map<Integer, Slot> inventory = Maps.newHashMap();
        
        for (int i = this.getInvStart(container);
                i <= this.getInvEnd(container); i++) {
            
            inventory.put(i, container.getSlot(i));
        }
        
        Map<Integer, Slot> crafting = Maps.newHashMap();
        
        for (int i = this.getCraftStart(container);
                i <= this.getCraftEnd(container); i++) {
            
            crafting.put(i, container.getSlot(i));
        }
        
        int inputs = 0;
        IGuiItemStackGroup recipeStacks = recipeLayout.getItemStacks();
        
        for (IGuiIngredient<ItemStack> ingredient : recipeStacks.getGuiIngredients().values()) {
            
            if (ingredient.isInput() && !ingredient.getAllIngredients().isEmpty()) {
                
                inputs++;
            }
        }
        
        Map<Integer, ItemStack> availableStacks = Maps.newHashMap();
        int filledSlots = 0;
        int emptySlots = 0;
        
        for (Slot slot : crafting.values()) {
            
            ItemStack stack = slot.getStack();
            
            if (!stack.isEmpty()) {
                
                filledSlots++;
                availableStacks.put(slot.slotNumber, stack.copy());
            }
        }
        
        for (Slot slot : inventory.values()) {
            
            ItemStack stack = slot.getStack();
            
            if (!stack.isEmpty()) {
                
                availableStacks.put(slot.slotNumber, stack.copy());
                
            } else {
                
                emptySlots++;
            }
        }
        
        if (filledSlots - inputs > emptySlots) {
            
            String message = Translator.translateToLocal("jei.tooltip.error.recipe.transfer.inventory.full");
            return GeoJei.transferHelper.createUserErrorWithTooltip(message);
        }
        
        MatchingItemsResult matchResult = GeoJei.stackHelper.getMatchingItems(availableStacks, recipeStacks.getGuiIngredients());
        
        if (matchResult.missingItems.size() > 0) {
            
            String message = Translator.translateToLocal("jei.tooltip.error.recipe.transfer.missing");
            return GeoJei.transferHelper.createUserErrorForSlots(message, matchResult.missingItems);
        }
        
        List<Integer> craftSlots = Lists.newArrayList(crafting.keySet());
        Collections.sort(craftSlots);
        List<Integer> invSlots = Lists.newArrayList(inventory.keySet());
        Collections.sort(invSlots);
        
        if (doTransfer) {
            
            PacketRecipeTransfer packet = new PacketRecipeTransfer(matchResult.matchingItems, craftSlots, invSlots, maxTransfer);
            JustEnoughItems.getProxy().sendPacketToServer(packet);
        }
        
        return null;
    }
    
    public static class Inventory extends GridTransferHandler<ContainerInventory> {
        
        public Inventory() {
            
            super(ContainerInventory.class);
        }
        
        @Override
        protected int getInvStart(ContainerInventory container) {
            
            return container.hotStart;
        }
        
        @Override
        protected int getInvEnd(ContainerInventory container) {
            
            return container.invEnd;
        }
        
        @Override
        protected int getCraftStart(ContainerInventory container) {
            
            return ContainerInventory.CRAFT_START;
        }
        
        @Override
        protected int getCraftEnd(ContainerInventory container) {
            
            return container.craftEnd;
        }
    }
    
    public static class Crafting<C extends ContainerCrafting> extends GridTransferHandler<C> {

        public Crafting(Class<C> clas) {
            
            super(clas);
        }

        @Override
        protected int getInvStart(C container) {

            return ContainerCrafting.HOT_START;
        }

        @Override
        protected int getInvEnd(C container) {

            return container.invEnd;
        }

        @Override
        protected int getCraftStart(C container) {

            return container.craftStart;
        }

        @Override
        protected int getCraftEnd(C container) {

            return container.craftEnd;
        }
    }
    
    public static class Compost extends GridTransferHandler<ContainerCompost> {
        
        public Compost() {
            
            super(ContainerCompost.class);
        }
        
        @Override
        protected int getInvStart(ContainerCompost container) {
            
            return ContainerCompost.HOT_START;
        }
        
        @Override
        protected int getInvEnd(ContainerCompost container) {
            
            return container.invEnd;
        }
        
        @Override
        protected int getCraftStart(ContainerCompost container) {
            
            return ContainerCompost.INPUT_I;
        }
        
        @Override
        protected int getCraftEnd(ContainerCompost container) {
            
            return ContainerCompost.INPUT_I;
        }
    }
}

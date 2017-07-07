/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.compat;

import jayavery.geomastery.container.ContainerAbstract;
import jayavery.geomastery.container.ContainerDrying;
import jayavery.geomastery.container.ContainerFurnaceAbstract;
import jayavery.geomastery.main.Geomastery;
import jayavery.geomastery.packets.SPacketJei;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.recipe.transfer.IRecipeTransferError;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandler;
import mezz.jei.util.Translator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;

public abstract class FillTransferHandler<C extends ContainerAbstract> implements IRecipeTransferHandler<C> {

    private final Class<C> clas;
    
    public FillTransferHandler(Class<C> clas) {
        
        this.clas = clas;
    }
    
    public static <T extends ContainerFurnaceAbstract> FillTransferHandler.Fuel<T> fuel(Class<T> clas) {
        
        return new FillTransferHandler.Fuel<T>(clas);
    }
    
    public static <T extends ContainerFurnaceAbstract> FillTransferHandler.Cooking<T> cook(Class<T> clas) {
        
        return new FillTransferHandler.Cooking<T>(clas);
    }
    
    public static FillTransferHandler.Drying dry() {
        
        return new FillTransferHandler.Drying();
    }
    
    @Override
    public Class<C> getContainerClass() {
        
        return this.clas;
    }
    
    protected abstract int getInvStart(C container);
    protected abstract int getInvEnd(C container);
    protected abstract int getInputStart(C container);
    protected abstract int getInputEnd(C container);
    
    @Override
    public IRecipeTransferError transferRecipe(C container, IRecipeLayout layout, EntityPlayer player, boolean maxTransfer, boolean doTransfer) {
        
        ItemStack input = layout.getItemStacks().getGuiIngredients().values().stream().filter((i) -> i.isInput()).findAny().get().getDisplayedIngredient();
        
        int foundInput = -1;
        
        for (int i = this.getInvStart(container);
                i <= this.getInvEnd(container); i++) {
            
            if (GeoJei.stackHelper.isEquivalent(container.getSlot(i).getStack(), input)) {
                
                foundInput = i;
                break;
            }
        }
        
        if (foundInput == -1) {
            
            String message = Translator.translateToLocal("jei.tooltip.error.recipe.transfer.missing");
            return GeoJei.transferHelper.createUserErrorWithTooltip(message);
        }
        
        int foundSpace = -1;
        
        for (int i = this.getInputStart(container);
                i <= this.getInputEnd(container); i++) {
            
            
            if (container.getSlot(i).getStack().isEmpty()) {
                
                foundSpace = i;
                break;
            }
        }
        
        if (foundSpace == -1) {
            
            String message = Translator.translateToLocal("jei.tooltip.error.recipe.transfer.inventory.full");
            return GeoJei.transferHelper.createUserErrorWithTooltip(message);
        }

        if (doTransfer) {
            
            Geomastery.NETWORK.sendToServer(new SPacketJei(foundSpace, foundInput, maxTransfer));
        }
        
        return null;
    }
    
    public static class Fuel<C extends ContainerFurnaceAbstract> extends FillTransferHandler<C> {

        public Fuel(Class<C> clas) {
            
            super(clas);
        }

        @Override
        protected int getInvStart(C container) {

            return container.hotStart;
        }

        @Override
        protected int getInvEnd(C container) {

            return container.invEnd;
        }

        @Override
        protected int getInputStart(C container) {

            return container.fuelStart;
        }

        @Override
        protected int getInputEnd(C container) {

            return container.fuelEnd;
        }
    }
    
    public static class Cooking<C extends ContainerFurnaceAbstract> extends FillTransferHandler<C> {

        public Cooking(Class<C> clas) {
            
            super(clas);
        }

        @Override
        protected int getInvStart(C container) {

            return container.hotStart;
        }

        @Override
        protected int getInvEnd(C container) {

            return container.invEnd;
        }

        @Override
        protected int getInputStart(C container) {

            return ContainerFurnaceAbstract.INPUT_START;
        }

        @Override
        protected int getInputEnd(C container) {

            return container.inputEnd;
        }
    }
    
    public static class Drying extends FillTransferHandler<ContainerDrying> {
        
        public Drying() {
            
            super(ContainerDrying.class);
        }
        
        @Override
        protected int getInvStart(ContainerDrying container) {
            
            return ContainerDrying.HOT_START;
        }
        
        @Override
        protected int getInvEnd(ContainerDrying container) {
            
            return container.invEnd;
        }
        
        @Override
        protected int getInputStart(ContainerDrying container) {
            
            return ContainerDrying.INPUT_START;
        }
        
        @Override
        protected int getInputEnd(ContainerDrying container) {
            
            return ContainerDrying.INPUT_END;
        }
    }
}

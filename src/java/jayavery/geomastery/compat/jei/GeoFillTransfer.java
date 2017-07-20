/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.compat.jei;

import jayavery.geomastery.container.ContainerAbstract;
import jayavery.geomastery.container.ContainerDrying;
import jayavery.geomastery.container.ContainerFurnaceAbstract;
import jayavery.geomastery.main.Geomastery;
import jayavery.geomastery.utilities.Lang;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.recipe.transfer.IRecipeTransferError;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandler;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/** Recipe transfer handler for Geomastery filling containers. */
public abstract class GeoFillTransfer<C extends ContainerAbstract>
        implements IRecipeTransferHandler<C> {

    /** The class this transfer applies to. */
    private final Class<C> clas;
    
    public GeoFillTransfer(Class<C> clas) {
        
        this.clas = clas;
    }   
    
    /** @return Start index of the inventory to draw from. */
    protected abstract int getInvStart(C container);
    /** @return End index of the inventory to draw from. */
    protected abstract int getInvEnd(C container);
    /** @return Start index of the inventory to fill. */
    protected abstract int getInputStart(C container);
    /** @return End index of the inventory to fill. */
    protected abstract int getInputEnd(C container);
    
    @Override
    public Class<C> getContainerClass() {
        
        return this.clas;
    }
    
    @Override
    public IRecipeTransferError transferRecipe(C container,
            IRecipeLayout layout, EntityPlayer player,
            boolean maxTransfer, boolean doTransfer) {
        
        ItemStack input = layout.getItemStacks().getGuiIngredients().values()
                .stream().filter((i) -> i.isInput()).findAny()
                .get().getDisplayedIngredient();
        
        // Search for a valid input
        int foundInput = -1;
        
        for (int i = this.getInvStart(container);
                i <= this.getInvEnd(container); i++) {
            
            if (GeoJei.stackHelper.isEquivalent(container
                    .getSlot(i).getStack(), input)) {
                
                foundInput = i;
                break;
            }
        }
        
        if (foundInput == -1) {
            
            return GeoJei.transferHelper.createUserErrorWithTooltip(I18n
                    .format(Lang.JEIERR_MISSING));
        }
        
        // Search for an empty space to fill
        int foundSpace = -1;
        
        for (int i = this.getInputStart(container);
                i <= this.getInputEnd(container); i++) {
            
            
            if (container.getSlot(i).getStack().isEmpty()) {
                
                foundSpace = i;
                break;
            }
        }
        
        if (foundSpace == -1) {
            
            return GeoJei.transferHelper.createUserErrorWithTooltip(I18n
                    .format(Lang.JEIERR_SPACE));
        }

        if (doTransfer) {
            
            Geomastery.NETWORK.sendToServer(new SGeoPacketSingle(foundSpace,
                    foundInput, maxTransfer));
        }
        
        return null;
    }
    
    /** Fuel transfer object factory. */
    public static <T extends ContainerFurnaceAbstract> GeoFillTransfer.Fuel<T>
            fuel(Class<T> clas) {
        
        return new GeoFillTransfer.Fuel<T>(clas);
    }
    
    /** Cooking transfer object factory. */
    public static <T extends ContainerFurnaceAbstract> GeoFillTransfer.Cooking<T>
            cook(Class<T> clas) {
        
        return new GeoFillTransfer.Cooking<T>(clas);
    }
    
    /** Drying transfer object factory. */
    public static GeoFillTransfer.Drying dry() {
        
        return new GeoFillTransfer.Drying();
    }
    
    /** Furnace container transfer handler for fuels. */
    public static class Fuel<C extends ContainerFurnaceAbstract>
            extends GeoFillTransfer<C> {

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
    
    /** Furnace container transfer handler for cooking inputs. */
    public static class Cooking<C extends ContainerFurnaceAbstract>
            extends GeoFillTransfer<C> {

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
    
    /** Drying container transfer handler. */
    public static class Drying extends GeoFillTransfer<ContainerDrying> {
        
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

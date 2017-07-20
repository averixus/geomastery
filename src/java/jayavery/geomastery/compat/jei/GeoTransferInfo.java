/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.compat.jei;

import java.util.ArrayList;
import java.util.List;
import jayavery.geomastery.container.ContainerAbstract;
import jayavery.geomastery.container.ContainerCompost;
import jayavery.geomastery.container.ContainerCrafting;
import jayavery.geomastery.container.ContainerInventory;
import mezz.jei.api.recipe.transfer.IRecipeTransferInfo;
import net.minecraft.inventory.Slot;

/** Recipe transfer info for Geomastery crafting grid containers. */
public abstract class GeoTransferInfo<C extends ContainerAbstract>
        implements IRecipeTransferInfo<C> {

    /** The class this info applies to. */
    private final Class<C> clas;
    /** Intenal unique id of this handler's recipe category. */
    private final String uid;
    
    public GeoTransferInfo(Class<C> clas, String uid) {
        
        this.clas = clas;
        this.uid = uid;
    }
    
    /** @return Start index of the inventory to draw from. */
    protected abstract int getInvStart(C container);
    /** @return End index of the inventory to draw from. */
    protected abstract int getInvEnd(C container);
    /** @return Start index of the crafting grid to fill. */
    protected abstract int getCraftStart(C container);
    /** @return End index of the crafting grid to fill. */
    protected abstract int getCraftEnd(C container);
    
    @Override
    public Class<C> getContainerClass() {

        return this.clas;
    }

    @Override
    public String getRecipeCategoryUid() {

        return this.uid;
    }

    @Override
    public boolean canHandle(C container) {

        return container.getClass() == this.clas;
    }

    @Override
    public List<Slot> getRecipeSlots(C container) {

        List<Slot> slots = new ArrayList<Slot>();
        
        for (int i = this.getCraftStart(container);
                i <= this.getCraftEnd(container); i++) {
            
            Slot slot = container.getSlot(i);
            slots.add(slot);
        }
        
        return slots;
    }

    @Override
    public List<Slot> getInventorySlots(C container) {

        List<Slot> slots = new ArrayList<Slot>();
        
        for (int i = this.getInvStart(container);
                i <= this.getInvEnd(container); i++) {
            
            Slot slot = container.getSlot(i);
            slots.add(slot);
        }
        
        return slots;
    }
    
    /** Crafting transfer info object factory. */
    public static <T extends ContainerCrafting> Crafting<T>
            craft(Class<T> clas, String uid) {
        
        return new Crafting<T>(clas, uid);
    }
    
    /** Inventory transfer info object factory. */
    public static Inventory inv() {
        
        return new Inventory();
    }
    
    /** Compost transfer info object factory. */
    public static Compost comp() {
        
        return new Compost();
    }
    
    /** Inventory container transfer info. */
    public static class Inventory extends GeoTransferInfo<ContainerInventory> {
       
        public Inventory() {
            
            super(ContainerInventory.class, GeoJei.inventory.getUid());
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

    /** Crafting container transfer info. */
    public static class Crafting<C extends ContainerCrafting>
            extends GeoTransferInfo<C> {

        public Crafting(Class<C> clas, String uid) {
            
            super(clas, uid);
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
    
    /** Compost heap container transfer info. */
    public static class Compost extends GeoTransferInfo<ContainerCompost> {
        
        public Compost() {
            
            super(ContainerCompost.class, GeoJei.compost.getUid());
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

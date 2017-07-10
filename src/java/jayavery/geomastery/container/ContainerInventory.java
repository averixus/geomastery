/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.container;

import jayavery.geomastery.container.slots.SlotArmour;
import jayavery.geomastery.container.slots.SlotBackpack;
import jayavery.geomastery.container.slots.SlotCrafting;
import jayavery.geomastery.container.slots.SlotYoke;
import jayavery.geomastery.main.GeoBlocks;
import jayavery.geomastery.main.GeoItems;
import jayavery.geomastery.main.GeoRecipes;
import jayavery.geomastery.main.Geomastery;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

/** Container for player inventory. */
public class ContainerInventory extends ContainerAbstract {

    /** Columns of the craft grid. */
    private static final int CRAFT_COLS = 3;
    /** Rows of the craft grid. */
    private static final int CRAFT_ROWS = 2;

    /** X-position of the output slot. */
    private static final int OUTPUT_X = 154;
    /** Y-position of the output slot. */
    private static final int OUTPUT_Y = 28;
    /** X-position of the start of craft grid. */
    private static final int CRAFT_X = 80;
    /** Y-position of the end of craft grid. */
    private static final int CRAFT_Y = 18;
    /** X-position of the apparel slots. */
    private static final int APPAREL_X = 8;
    /** Y-position of the head apparel slot. */
    private static final int HEAD_Y = 8;
    /** Y-position of the chest apparel slot. */
    private static final int CHEST_Y = 26;
    /** Y-position of the legs apparel slot. */
    private static final int LEGS_Y = 44;
    /** Y-position of the feet apparel slot. */
    private static final int FEET_Y = 62;
    /** Y-position of the equipment slots. */
    private static final int EQUIP_Y = 62;
    /** X-position of the offhand slot. */
    private static final int OFFHAND_X = 77;
    /** X-position of the backpack slot. */
    private static final int BACKPACK_X = 95;
    /** X-position of the yoke slot. */
    private static final int YOKE_X = 113;

    /** Index of the feet apparel slot. */
    private static final int FEET_I = 0;
    /** Index of the legs apparel slot. */
    private static final int LEGS_I = 1;
    /** Index of the chest apparel slot. */
    private static final int CHEST_I = 2;
    /** Index of the head apparel slot. */
    private static final int HEAD_I = 3;
    /** Index of the offhand slot. */
    private static final int OFFHAND_I = 4;
    /** Index of the backpack slot. */
    private static final int BACKPACK_I = 5;
    /** Index of the yoke slot. */
    private static final int YOKE_I = 6;
    /** Index of the first craft grid slot. */
    public static final int CRAFT_START = 7;
    
    /** Index of the last craft grid slot. */
    public final int craftEnd;
    /** Index of the output slot. */
    private final int outputI;
    /** Index of the first hotbar slot. */
    public final int hotStart;
    /** Index of the last hotbar slot. */
    private final int hotEnd;
    /** Index of the first inventory slot. */
    private final int invStart;
    /** Index of the last inventory slot. */
    public int invEnd;

    /** Texture for the current background of this container. */
    private ResourceLocation background;
    /** Inventory for the craft grid. */
    public final InventoryCrafting craftMatrix;
    /** Inventory for the craft result. */
    public final IInventory craftResult = new InventoryCraftResult();

    public ContainerInventory(EntityPlayer player) {

        super(player);
        
        // Equipment slots
        this.addSlotToContainer(new SlotArmour(this.playerInv,
                APPAREL_X, FEET_Y, EntityEquipmentSlot.FEET));
        this.addSlotToContainer(new SlotArmour(this.playerInv,
                APPAREL_X, LEGS_Y, EntityEquipmentSlot.LEGS));
        this.addSlotToContainer(new SlotArmour(this.playerInv,
                APPAREL_X, CHEST_Y, EntityEquipmentSlot.CHEST));
        this.addSlotToContainer(new SlotArmour(this.playerInv,
                APPAREL_X, HEAD_Y, EntityEquipmentSlot.HEAD));
        this.addSlotToContainer(new SlotArmour(this.playerInv,
                OFFHAND_X, EQUIP_Y, EntityEquipmentSlot.OFFHAND));
        this.addSlotToContainer(new SlotBackpack(this.player,
                BACKPACK_X, EQUIP_Y));
        this.addSlotToContainer(new SlotYoke(this.player,
                YOKE_X, EQUIP_Y));

        // Craft grid 
        this.craftMatrix = this.buildCraftMatrix(CRAFT_COLS, CRAFT_ROWS,
                CRAFT_X, CRAFT_Y);
        
        // Craft output
        this.addSlotToContainer(new SlotCrafting(player,
                this.craftMatrix, this.craftResult, OUTPUT_X,
                OUTPUT_Y, GeoRecipes.INVENTORY));

        // Hotbar and inventory slots
        this.buildHotbar();
        int invIndex = this.buildInvgrid();
        
        // Container indices
        this.craftEnd = YOKE_I + this.craftMatrix.getSizeInventory();
        this.outputI = this.craftEnd + 1;
        this.hotStart = this.outputI + 1;
        this.hotEnd = this.outputI + ROW_LENGTH;
        this.invStart = this.hotEnd + 1;
        this.invEnd = this.invStart + invIndex;
        
        this.refresh();
        this.onCraftMatrixChanged(this.craftMatrix);
        this.detectAndSendChanges();
    }
    
    /** Rebuilds the correct number of main inventory slots. */
    private void refresh() {
        
        // Remove old slots
        int j = this.inventorySlots.size() - 1;
        
        while (j >= this.invStart) {

            this.inventorySlots.remove(j);
            this.inventoryItemStacks.remove(j);
            j--;
        }
        
        // Build new slots
        this.invEnd = this.invStart + this.buildInvgrid();

        // Move or drop excess items
        for (int i = this.capability.getInventorySize();
                i < this.playerInv.mainInventory.size(); i++) {

            ItemStack drop = this.playerInv.removeStackFromSlot(i);
            
            if (!drop.isEmpty()) {
            
                if (!this.mergeItemStack(drop, this.hotStart,
                        this.invEnd + 1, true)) {
                    
                    this.player.dropItem(drop, false);
                }
            }
        }
        
        this.updateBackground();
    }
    
    /** Sets the ResourceLocation for the GUI with current inventory size. */
    private void updateBackground() {
        
        this.background = new ResourceLocation(Geomastery.MODID,
                "textures/gui/inventory_"
                + this.capability.getInventoryRows() + ".png");
    }
    
    /** @return The ResourceLocation for the GUI backgound. */
    public ResourceLocation getBackground() {
        
        return this.background;
    }
    
    /** Attempts to put the ItemStack into this container.
     * @return The ItemStack left over. */
    private ItemStack add(ItemStack stack) {

        if (!stack.isEmpty()) {
            
            stack = this.addToOffhand(stack);
        }
        
        if (GeoBlocks.OFFHAND_ONLY.contains(stack.getItem())) {
            
            return stack;
        }
        
        if (!stack.isEmpty()) {
            
            stack = this.putInMatchingSlot(stack);
        }
        
        if (!stack.isEmpty()) {
            
            stack = this.putInEmptySlot(stack);
        }
        
        return stack;
    }
    
    /** Attempts to put the ItemStack into a non-full
     * slot containing the same item.
     * @return The ItemStack left over. */
    private ItemStack putInMatchingSlot(ItemStack stack) {
        
        NonNullList<ItemStack> inv = this.player.inventory.mainInventory;
        ItemStack remaining = stack;
        
        for (int slot = 0; slot < this.capability.getInventorySize() &&
                !remaining.isEmpty(); slot++) {

            if (ItemStack.areItemsEqual(remaining, inv.get(slot)) &&
                    inv.get(slot).areCapsCompatible(remaining)) {

                remaining = this.addToSlot(slot, remaining);
            }
        }

        return remaining;
    }
    
    /** Attempts to put the stack into an empty slot.
     * @return The ItemStack left over. */
    private ItemStack putInEmptySlot(ItemStack stack) {
        
        NonNullList<ItemStack> inv = this.player.inventory.mainInventory;
        ItemStack remaining = stack;

        for (int slot = 0; slot < this.capability.getInventorySize() &&
                !remaining.isEmpty(); slot++) {

            if (inv.get(slot).isEmpty()) {

                remaining = this.addToSlot(slot, remaining);
            }
        }

        return remaining;
    }
    
    /** Attempts to put the ItemStack into the offhand slot.
     * @return The ItemStack left over. */
    private ItemStack addToOffhand(ItemStack stack) {
        
        NonNullList<ItemStack> inv = this.player.inventory.offHandInventory;
        ItemStack added = stack.copy();
        ItemStack inSlot = inv.get(0);
        int max = stack.getMaxStackSize();
        int total = inSlot.getCount() + stack.getCount();
        
        if ((inSlot.isEmpty() &&
                GeoBlocks.OFFHAND_ONLY.contains(stack.getItem())) ||
                (ItemStack.areItemsEqual(stack, inSlot) &&
                stack.areCapsCompatible(inSlot))) {
            
            if (max >= total) {
                
                added.setCount(total);
                stack = ItemStack.EMPTY;
                
            } else {
                
                added.setCount(max);
                stack.setCount(total - max);
            }
            
            inv.set(0, added);
        }
        
        return stack;
    }
    
    /** Attempts to put the ItemStack into the inventory index slot.
     * @return The ItemStack left over. */
    private ItemStack addToSlot(int slot, ItemStack stack) {
        
        NonNullList<ItemStack> inv = this.player.inventory.mainInventory;
        ItemStack added = stack.copy();
        ItemStack inSlot = inv.get(slot);
        int max = stack.getMaxStackSize();
        int total = inSlot.getCount() + stack.getCount();
        
        if (inSlot.isEmpty() || (ItemStack.areItemsEqual(stack, inSlot) &&
                stack.areCapsCompatible(inSlot))) {

            if (max >= total) {
                
                added.setCount(total);
                stack = ItemStack.EMPTY;
                
            } else {
                
                added.setCount(max);
                stack.setCount(total - max);
            }
            
            inv.set(slot, added);
        }
        
        return stack;
    }

    @Override
    public void onCraftMatrixChanged(IInventory craftMatrix) {

        super.onCraftMatrixChanged(this.craftMatrix);
        ItemStack stack = GeoRecipes.INVENTORY
                .findMatchingRecipe(this.craftMatrix, this.world);
        this.craftResult.setInventorySlotContents(0, stack);
    }
    
    @Override
    public ItemStack slotClick(int slot, int dragType,
            ClickType clickType, EntityPlayer player) {
        
        ItemStack result = super.slotClick(slot, dragType, clickType, player);
        this.onCraftMatrixChanged(this.craftMatrix);
        return result;
    }

    @Override
    public void onContainerClosed(EntityPlayer player) {

        super.onContainerClosed(player);

        if (!this.world.isRemote) {

            for (int i = 0; i < this.craftMatrix.getSizeInventory(); i++) {

                ItemStack stack = this.craftMatrix.removeStackFromSlot(i);

                if (!stack.isEmpty()) {

                    player.dropItem(stack, false);
                }
            }
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {

        return true;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {

        ItemStack result = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot == null || !slot.getHasStack()) {

            return ItemStack.EMPTY;
        }

        ItemStack slotStack = slot.getStack();
        Item slotItem = slotStack.getItem();
        result = slotStack.copy();

        if (slotItem instanceof ItemArmor) {

            EntityEquipmentSlot armourType = ((ItemArmor) slotItem).armorType;

            if (armourType == EntityEquipmentSlot.HEAD && !this.inventorySlots
                    .get(HEAD_I).getHasStack()) {

                if (!this.mergeItemStack(slotStack,
                        HEAD_I, HEAD_I + 1, false)) {

                    result = ItemStack.EMPTY;
                }

            } else if (armourType == EntityEquipmentSlot.CHEST &&
                    !this.inventorySlots.get(CHEST_I).getHasStack()) {

                if (!this.mergeItemStack(slotStack, CHEST_I,
                        CHEST_I + 1, false)) {

                    result = ItemStack.EMPTY;
                }

            } else if (armourType == EntityEquipmentSlot.LEGS &&
                    !this.inventorySlots.get(LEGS_I).getHasStack()) {

                if (!this.mergeItemStack(slotStack,
                        LEGS_I, LEGS_I + 1, false)) {

                    result = ItemStack.EMPTY;
                }

            } else if (armourType == EntityEquipmentSlot.FEET &&
                    !this.inventorySlots.get(FEET_I).getHasStack()) {

                if (!this.mergeItemStack(slotStack,
                        FEET_I, FEET_I + 1, false)) {

                    result = ItemStack.EMPTY;
                }
            }
        }

        if (slotItem instanceof ItemShield &&
                !this.inventorySlots.get(OFFHAND_I).getHasStack()) {

            if (!this.mergeItemStack(slotStack,
                    OFFHAND_I, OFFHAND_I + 1, false)) {

                result = ItemStack.EMPTY;
            }
        }

        if (slotItem == GeoItems.BACKPACK && !this.inventorySlots
                .get(BACKPACK_I).getHasStack()) {

            if (!this.mergeItemStack(slotStack, BACKPACK_I,
                    BACKPACK_I + 1, false)) {
                
                result = ItemStack.EMPTY;
            }
        }

        if (slotItem == GeoItems.YOKE && !this.inventorySlots.get(YOKE_I)
                .getHasStack()) {

            if (!this.mergeItemStack(slotStack, YOKE_I,
                    YOKE_I + 1, false)) {

                result = ItemStack.EMPTY;
            }
        }

        if (index == this.outputI) {
            
            boolean fail = false;
            
            if (GeoBlocks.OFFHAND_ONLY.contains(slotItem) &&
                    !this.mergeItemStack(slotStack, OFFHAND_I,
                    OFFHAND_I + 1, false)) {
                
                fail = true;
                
            } else if (!GeoBlocks.OFFHAND_ONLY.contains(slotItem) &&
                    !this.mergeItemStack(slotStack, this.hotStart,
                    this.invEnd + 1, false)) {

                fail = true;
                
            }
            
            if (fail) {
                
                result = ItemStack.EMPTY;
                
            } else {
                
                slot.onTake(player, slotStack);
            }

            slot.onSlotChange(slotStack, result);
                

        } else if ((index >= CRAFT_START && index <= this.craftEnd) ||
                (index >= FEET_I && index <= YOKE_I)) {

            if (!this.mergeItemStack(slotStack, this.hotStart,
                    this.invEnd + 1, false)) {

                result = ItemStack.EMPTY;
            }

        } else if (index >= this.hotStart && index <= this.hotEnd) {

            if (!this.mergeItemStack(slotStack, this.invStart,
                    this.invEnd + 1, false)) {

                result = ItemStack.EMPTY;
            }

        } else if (index >= this.invStart && index <= this.invEnd) {

            if (!this.mergeItemStack(slotStack, this.hotStart,
                    this.hotEnd + 1, false)) {

                result = ItemStack.EMPTY;
            }
        }

        slot.onSlotChanged();
        return result;
    }
    
    /** Attempts to add the stack to the given player if their
     * inventory container is an instance of this class.
     * @return The ItemStack left over. */
    public static ItemStack add(EntityPlayer player, ItemStack stack) {
        
        if (!(player.inventoryContainer instanceof ContainerInventory)) {
            
            return ItemStack.EMPTY;
        }
        
        ContainerInventory inv = (ContainerInventory) player.inventoryContainer;
        return inv.add(stack);
    }
    
    /** Refreshes the player's inventory container size
     * if it is an instance of this class. */
    public static void refresh(EntityPlayer player) {
        
        if (!(player.inventoryContainer instanceof ContainerInventory)) {
            
            return;
        }
        
        ContainerInventory inv = (ContainerInventory) player.inventoryContainer;
        inv.refresh();
    }
}

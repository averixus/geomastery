package com.jj.jjmod.container;

import javax.annotation.Nullable;
import com.jj.jjmod.container.slots.SlotArmour;
import com.jj.jjmod.container.slots.SlotBackpack;
import com.jj.jjmod.container.slots.SlotCrafting;
import com.jj.jjmod.container.slots.SlotYoke;
import com.jj.jjmod.init.ModBlocks;
import com.jj.jjmod.init.ModItems;
import com.jj.jjmod.init.ModPackets;
import com.jj.jjmod.init.ModRecipes;
import com.jj.jjmod.packets.ContainerPacketClient;
import com.jj.jjmod.packets.ContainerPacketServer;
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
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

/** Container for player inventory. */
public class ContainerInventory extends ContainerAbstract {

    private static final int CRAFT_COLS = 3;
    private static final int CRAFT_ROWS = 2;

    private static final int OUTPUT_X = 154;
    private static final int OUTPUT_Y = 28;
    private static final int CRAFT_X = 80;
    private static final int CRAFT_Y = 18;
    private static final int ARMOUR_X = 8;
    private static final int HEAD_Y = 8;
    private static final int CHEST_Y = 26;
    private static final int LEGS_Y = 44;
    private static final int FEET_Y = 62;
    private static final int EQUIP_Y = 62;
    private static final int SHIELD_X = 77;
    private static final int BACKPACK_X = 95;
    private static final int YOKE_X = 113;

    private static final int FEET_I = 0;
    private static final int LEGS_I = 1;
    private static final int CHEST_I = 2;
    private static final int HEAD_I = 3;
    private static final int OFFHAND_I = 4;
    private static final int BACKPACK_I = 5;
    private static final int YOKE_I = 6;
    
    private final int craftStart = 7;
    private final int craftEnd;
    private final int outputI;
    private final int hotStart;
    private final int hotEnd;
    private final int invStart;
    private int invEnd;

    private ResourceLocation background;
    public InventoryCrafting craftMatrix;
    public IInventory craftResult = new InventoryCraftResult();

    public ContainerInventory(EntityPlayer player, World world) {

        super(player, world);
        this.player.openContainer = this;
        
        // Equipment slots
        this.addSlotToContainer(new SlotArmour(this.playerInv, this.player,
                ARMOUR_X, FEET_Y, EntityEquipmentSlot.FEET));
        this.addSlotToContainer(new SlotArmour(this.playerInv, this.player,
                ARMOUR_X, LEGS_Y, EntityEquipmentSlot.LEGS));
        this.addSlotToContainer(new SlotArmour(this.playerInv, this.player,
                ARMOUR_X, CHEST_Y, EntityEquipmentSlot.CHEST));
        this.addSlotToContainer(new SlotArmour(this.playerInv, this.player,
                ARMOUR_X, HEAD_Y, EntityEquipmentSlot.HEAD));
        this.addSlotToContainer(new SlotArmour(this.playerInv, this.player,
                SHIELD_X, EQUIP_Y, EntityEquipmentSlot.OFFHAND));
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
                OUTPUT_Y, ModRecipes.INVENTORY));

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
        
        this.setBackground();
        this.onCraftMatrixChanged(this.craftMatrix);
    }
    
    /** Rebuilds the correct number of main inventory slots. */
    public void refresh() {
        
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
            
                if (this.mergeItemStack(drop, this.hotStart,
                        this.invEnd + 1, true)) {
                    
                    this.player.dropItem(drop, false);
                }
            }
        }
        
        this.setBackground();
    }
    
    /** Sets the ResourceLocation for the GUI with current inventory size. */
    private void setBackground() {
        
        this.background = new ResourceLocation("jjmod:textures/gui/inventory_"
                + this.capability.getInventoryRows() + ".png");
    }
    
    /** @return The ResourceLocation for the GUI backgound. */
    public ResourceLocation getBackground() {
        
        return this.background;
    }
    
    /** Attempts to put the ItemStack into this container.
     * @return The ItemStack left over. */
    public ItemStack add(ItemStack stack) {
        
        ItemStack remaining = stack;
        
        if (ModBlocks.OFFHAND_ONLY.contains(stack.getItem())) {
            
            remaining = this.addToOffhand(remaining);
        }
        
        if (!remaining.isEmpty()) {
            
            remaining = this.putInMatchingSlot(stack);
        }
        
        if (!remaining.isEmpty()) {
            
            remaining = this.putInEmptySlot(remaining);
        }
        
        return remaining;
    }
    
    /** Attempts to put the ItemStack into a non-full
     * slot containing the same item.
     * @return The ItemStack left over. */
    private ItemStack putInMatchingSlot(ItemStack stack) {
        
        NonNullList<ItemStack> inv = this.player.inventory.mainInventory;
        ItemStack remaining = stack.copy();

        for (int slot = 0; slot < this.capability.getInventorySize() &&
                !remaining.isEmpty(); slot++) {

            if (ItemStack.areItemsEqual(remaining, inv.get(slot))) {
            
                remaining = this.addToSlot(slot, remaining);
            }
        }

        return remaining;
    }
    
    /** Attempts to put the stack into an empty slot.
     * @return The ItemStack left over. */
    private ItemStack putInEmptySlot(ItemStack stack) {
        
        NonNullList<ItemStack> inv = this.player.inventory.mainInventory;
        ItemStack remaining = stack.copy();

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
        
        if (this.playerInv.offHandInventory.get(0).isEmpty()) {
            
            this.playerInv.offHandInventory.set(0, stack);
            this.sendUpdateOffhand();
            return ItemStack.EMPTY;
        }
        
        return stack;
    }
    
    /** Attempts to put the ItemStack into the index slot.
     * @return The ItemStack left over. */
    private ItemStack addToSlot(int slot, ItemStack stack) {
        
        NonNullList<ItemStack> inv = this.player.inventory.mainInventory;
        ItemStack result = stack;
        ItemStack inSlot = inv.get(slot);
        
        if (inSlot.isEmpty()) {
            
            inv.set(slot, stack);
            result = ItemStack.EMPTY;
            
        } else if (ItemStack.areItemsEqual(stack, inSlot)) {
            
            ItemStack added = inSlot.copy();
            int total = inSlot.getCount() + stack.getCount();
            int max = stack.getMaxStackSize();

            if (max >= total) {
                
                added.setCount(total);
                inv.set(slot, added);
                result = ItemStack.EMPTY;
                
            } else {
                
                added.setCount(max);
                inv.set(slot, added);
                result.setCount(total - max);
            }
        }
        
        this.sendUpdateInventory(slot, inv.get(slot));
        return result;
    }

    @Override
    public void onCraftMatrixChanged(IInventory craftMatrix) {

        ItemStack stack = ModRecipes.INVENTORY
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

                if (stack != ItemStack.EMPTY) {

                    player.dropItem(stack, false);
                    this.sendUpdateInventory(this.craftStart + i,
                            ItemStack.EMPTY);
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
                        HEAD_I, HEAD_I + 1, true)) {

                    result = ItemStack.EMPTY;
                }

            } else if (armourType == EntityEquipmentSlot.CHEST &&
                    !this.inventorySlots.get(CHEST_I).getHasStack()) {

                if (!this.mergeItemStack(slotStack, CHEST_I,
                        CHEST_I + 1, true)) {

                    result = ItemStack.EMPTY;
                }

            } else if (armourType == EntityEquipmentSlot.LEGS &&
                    !this.inventorySlots.get(LEGS_I).getHasStack()) {

                if (!this.mergeItemStack(slotStack,
                        LEGS_I, LEGS_I + 1, true)) {

                    result = ItemStack.EMPTY;
                }

            } else if (armourType == EntityEquipmentSlot.FEET &&
                    !this.inventorySlots.get(FEET_I).getHasStack()) {

                if (!this.mergeItemStack(slotStack,
                        FEET_I, FEET_I + 1, true)) {

                    result = ItemStack.EMPTY;
                }
            }
        }

        if (slotItem instanceof ItemShield &&
                !this.inventorySlots.get(OFFHAND_I).getHasStack()) {

            if (!this.mergeItemStack(slotStack,
                    OFFHAND_I, OFFHAND_I + 1, true)) {

                result = ItemStack.EMPTY;
            }
        }

        if (slotItem == ModItems.backpack && !this.inventorySlots
                .get(BACKPACK_I).getHasStack()) {

            if (!this.mergeItemStack(slotStack, BACKPACK_I,
                    BACKPACK_I + 1, true)) {
                
                result = ItemStack.EMPTY;
            }
        }

        if (slotItem == ModItems.yoke && !this.inventorySlots.get(YOKE_I)
                .getHasStack()) {

            if (!this.mergeItemStack(slotStack, YOKE_I,
                    YOKE_I + 1, true)) {

                result = ItemStack.EMPTY;
            }
        }

        if (index == this.outputI) {

            if (!this.mergeItemStack(slotStack, this.hotStart,
                    this.invEnd + 1, true)) {

                result = ItemStack.EMPTY;
            }

            slot.onSlotChange(slotStack, result);
            slot.onTake(player, slotStack);

        } else if ((index >= this.craftStart && index <= this.craftEnd) ||
                (index >= FEET_I && index <= YOKE_I)) {

            if (!this.mergeItemStack(slotStack, this.hotStart,
                    this.invEnd + 1, false)) {

                result = ItemStack.EMPTY;
            }

        } else if (index >= this.hotStart && index <= this.hotEnd) {

            if (!this.mergeItemStack(slotStack, this.invStart,
                    this.invEnd + 1, true)) {

                result = ItemStack.EMPTY;
            }

        } else if (index >= this.invStart && index <= this.invEnd) {

            if (!this.mergeItemStack(slotStack, this.hotStart,
                    this.hotEnd + 1, true)) {

                result = ItemStack.EMPTY;
            }
        }

        slot.onSlotChanged();
        return result;
    }

    /** Swaps the mainhand and offhand items (as pressing F). */
    public void swapHands() {

        ItemStack toMove =
                this.playerInv.mainInventory.get(this.playerInv.currentItem);
        this.playerInv.mainInventory.set(this.playerInv.currentItem,
                this.playerInv.offHandInventory.get(0));
        this.playerInv.offHandInventory.set(0, toMove);
    }

    /** Sends a packet to update the offhand slot. */
    public void sendUpdateOffhand() {

        sendUpdateInventory(OFFHAND_I,
                this.playerInv.offHandInventory.get(0));
    }

    /** Sends a packet to update the highlighted slot. */
    public void sendUpdateHighlight() {

        sendUpdateInventory(this.playerInv.currentItem,
                this.playerInv.mainInventory.get(this.playerInv.currentItem));
    }

    /** Sends a packet to update the index slot. */
    public void sendUpdateInventory(int slot, ItemStack stack) {

        slot += this.hotStart;

        if (this.player instanceof EntityPlayerMP) {

            ModPackets.NETWORK
                    .sendTo(new ContainerPacketClient(slot, stack),
                    (EntityPlayerMP) this.player);

        } else {

            ModPackets.NETWORK
                    .sendToServer(new ContainerPacketServer(slot, stack));
        }
    }
}

package com.jj.jjmod.container;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import com.jj.jjmod.container.slots.SlotArmour;
import com.jj.jjmod.container.slots.SlotCarry;
import com.jj.jjmod.container.slots.SlotCarry.CarryType;
import com.jj.jjmod.container.slots.SlotCrafting;
import com.jj.jjmod.init.ModBlocks;
import com.jj.jjmod.init.ModItems;
import com.jj.jjmod.init.ModPackets;
import com.jj.jjmod.init.ModRecipes;
import com.jj.jjmod.packets.InventoryUpdateClient;
import com.jj.jjmod.packets.InventoryUpdateServer;
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

public class ContainerInventory extends ContainerAbstract {

    public static final int CRAFT_COLS = 3;
    public static final int CRAFT_ROWS = 2;

    public static final int OUTPUT_X = 154;
    public static final int OUTPUT_Y = 28;
    public static final int CRAFT_X = 80;
    public static final int CRAFT_Y = 18;
    public static final int ARMOUR_X = 8;
    public static final int HEAD_Y = 8;
    public static final int CHEST_Y = 26;
    public static final int LEGS_Y = 44;
    public static final int FEET_Y = 62;
    public static final int EQUIP_Y = 62;
    public static final int SHIELD_X = 77;
    public static final int BACKPACK_X = 95;
    public static final int YOKE_X = 113;

    public static final int FEET_I = 0;
    public static final int LEGS_I = 1;
    public static final int CHEST_I = 2;
    public static final int HEAD_I = 3;
    public static final int SHIELD_I = 4;
    public static final int BACKPACK_I = 5;
    public static final int YOKE_I = 6;
    public final int CRAFT_START = 7;
    
    public final int CRAFT_END;
    public final int OUTPUT_I;
    public final int HOT_START;
    public final int HOT_END;
    public final int INV_START;
    public int INV_END;

    protected ResourceLocation background;

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
        this.addSlotToContainer(new SlotCarry(this.player,
                BACKPACK_X, EQUIP_Y, CarryType.BACKPACK));
        this.addSlotToContainer(new SlotCarry(this.player,
                YOKE_X, EQUIP_Y, CarryType.YOKE));

        // Inventory and craft grid slots
        this.craftMatrix = this.buildCraftMatrix(CRAFT_COLS, CRAFT_ROWS,
                CRAFT_X, CRAFT_Y);
        
        this.CRAFT_END = YOKE_I + this.craftMatrix.getSizeInventory();
        this.OUTPUT_I = this.CRAFT_END + 1;
        
        this.addSlotToContainer(new SlotCrafting(player,
                this.craftMatrix, this.craftResult, this.OUTPUT_I, OUTPUT_X,
                OUTPUT_Y, ModRecipes.INVENTORY));
        
        this.buildHotbar();
        this.HOT_START = this.OUTPUT_I + 1;
        this.HOT_END = this.OUTPUT_I + ROW_LENGTH;
        this.INV_START = this.HOT_END + 1;
        this.INV_END = this.INV_START + this.buildInvgrid();
        
        System.out.println("indices: \n  craftend: " + this.CRAFT_END + "\n output: " + this.OUTPUT_I + "\n hotstart: " + this.HOT_START + "\n hotend: " + this.HOT_END + "\n invstart: " + this.INV_START + "\n invend: " + this.INV_END);

        this.setBackground();
        this.onCraftMatrixChanged(this.craftMatrix);
    }
    
    public void refresh() {
        
        //set up
      //  List<ItemStack> saved = NonNullList.create();
        
        // save old
      /*  for (int i = this.INV_START; i <= this.INV_END; i++) {
            System.out.println("saving " + this.inventorySlots.get(i).getStack() + " from slot " + i);
            saved.add(this.inventorySlots.get(i).getStack());
        }*/
        
        // kill old
        int j = this.inventorySlots.size() - 1;
        while (j >= this.INV_START) {
            System.out.println("killing index " + j);
            this.inventorySlots.remove(j);
            this.inventoryItemStacks.remove(j);
            j --;
        }
        
      /*  // save and kill old
        for (int i = this.INV_END, j = saved.size() - 1; i >= this.INV_START && j >= 0; i--, j--) {
            System.out.println("saving and killing " + this.inventorySlots.get(i).getStack() + " at index " + i);
            saved.set(j, this.inventorySlots.get(i).getStack());
            this.inventorySlots.remove(i);
            this.inventoryItemStacks.remove(i);
        }*/
        
        //create and fill new
        this.INV_END = this.INV_START + this.buildInvgrid();
        System.out.println("grown, new invend: " + this.INV_END + " inventorysots size: " + this.inventorySlots.size());
        
        for (int i = this.capInv.getInventorySize(); i < this.playerInv.mainInventory.size(); i++) {
            System.out.println("checking inventory index " + i);
            ItemStack drop = this.playerInv.removeStackFromSlot(i);
            if (!drop.isEmpty()) {
            
                if (this.mergeItemStack(drop, this.HOT_START, this.INV_END + 1, true)) {
                    
                    this.player.dropItem(drop, false);
                }
            }
        }
        
       /* for (int i = 0; i < saved.size(); i++) {
            
            if (i + this.INV_START > this.INV_END) {
                
                System.out.println("dropping " + saved.get(i) + " from index " + i);
                this.playerInv.removeStackFromSlot(this.inventorySlots.get(i + this.INV_START).getSlotIndex());
                this.player.dropItem(saved.get(i), false);
            }
        }
        
        
        for (int i = this.INV_END, k = saved.size() - 1; i >= this.INV_START && k >= 0; i--, k--) {
            System.out.println("filling index " + i + " with " + saved.get(k));
            this.inventorySlots.get(i).putStack(saved.get(k));
        }
        
        // try again
        System.out.println("refreshing inv");
        List<Slot> range = this.inventorySlots.subList(this.INV_START, this.INV_END + 1);
        int newSize = this.capInv.getInventorySize() - ROW_LENGTH;
        System.out.println("new inv size " + newSize);
        int rangeSize = range.size();
        System.out.println("existing size " + rangeSize);
        
        if (newSize == rangeSize) {
            System.out.println("no change, returing");
            return;
        }
        
        if (newSize > rangeSize) {
            System.out.println("new > old");
            List<ItemStack> stacks = NonNullList.withSize(newSize, ItemStack.EMPTY);
            Collections.copy(stacks, this.inventoryItemStacks.subList(this.INV_START, this.INV_END + 1));
            System.out.println("removing old");
            this.inventorySlots.removeAll(range);
            System.out.println("adding new");
            this.INV_END = this.INV_START + this.buildInvgrid();
            
            System.out.println("copying old stacks to new");
            for (int i = 0; i < stacks.size(); i++) {
                
                ItemStack stack = stacks.get(i);
                this.inventoryItemStacks.set(i + this.INV_START, stack);
            }
            return;
        }
        
        if (newSize < rangeSize) {
            System.out.println("new < old");
            System.out.println("dropping excess and removing old");
            for (int i = range.size() - 1; i > newSize; i--) {
                
                this.player.dropItem(range.get(i).getStack(), false);
                range.remove(i);
            }
            return;
        }*/
        
        this.setBackground();
    }
    
    private void setBackground() {
        
        this.background = new ResourceLocation("jjmod:textures/gui/inventory_"
                + this.capInv.getInventoryRows() + ".png");
    }
    
    public ResourceLocation getBackground() {
        
        return this.background;
    }
    
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
    
    private ItemStack putInMatchingSlot(ItemStack stack) {
        
        NonNullList<ItemStack> inv = this.player.inventory.mainInventory;
        ItemStack remaining = stack.copy();

        for (int slot = 0; slot < this.capInv.getInventorySize() && !remaining.isEmpty(); slot++) {

            if (ItemStack.areItemsEqual(remaining, inv.get(slot))) {
            
                remaining = this.addToSlot(slot, remaining);
            }
        }

        return remaining;
    }
    
    private ItemStack putInEmptySlot(ItemStack stack) {
        
        NonNullList<ItemStack> inv = this.player.inventory.mainInventory;
        ItemStack remaining = stack.copy();

        for (int slot = 0; slot < this.capInv.getInventorySize() && !remaining.isEmpty(); slot++) {

            if (inv.get(slot).isEmpty()) {
            
                remaining = this.addToSlot(slot, remaining);
            }
        }

        return remaining;
    }
    
    private ItemStack addToOffhand(ItemStack stack) {
        
        if (this.playerInv.offHandInventory.get(0).isEmpty()) {
            
            this.playerInv.offHandInventory.set(0, stack);
            this.sendUpdateOffhand();
            return ItemStack.EMPTY;
        }
        
        return stack;
    }
    
    private ItemStack addToSlot(int slot, ItemStack stack) {
        
        NonNullList<ItemStack> inv = this.player.inventory.mainInventory;
        ItemStack result = stack;
        ItemStack inSlot = inv.get(slot);
        
        if (inSlot.isEmpty()) {
            
            inv.set(slot, stack);
            result = ItemStack.EMPTY;
            
        } else if (ItemStack.areItemStacksEqual(stack, inSlot)) {
            
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
        
        this.sendUpdateInventory(InvType.INVENTORY, slot, inv.get(slot));
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
                    this.sendUpdateInventory(InvType.CRAFTGRID, i, ItemStack.EMPTY);
                }
            }
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {

        return true;
    }

    @Override
    @Nullable
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {

        System.out.println("transferring index " + index);
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

                if (!this.mergeItemStack(slotStack, CHEST_I, CHEST_I + 1,
                        true)) {

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
                !this.inventorySlots.get(SHIELD_I).getHasStack()) {

            if (!this.mergeItemStack(slotStack,
                    SHIELD_I, SHIELD_I + 1, true)) {

                result = ItemStack.EMPTY;
            }
        }

        if (slotItem == ModItems.backpack && !this.inventorySlots
                .get(BACKPACK_I).getHasStack()) {

            if (!this.mergeItemStack(slotStack, BACKPACK_I, BACKPACK_I + 1,
                    true)) {
                
                result = ItemStack.EMPTY;
            }
        }

        if (slotItem == ModItems.yoke && !this.inventorySlots.get(YOKE_I)
                .getHasStack()) {

            if (!this.mergeItemStack(slotStack, YOKE_I, YOKE_I + 1, true)) {

                result = ItemStack.EMPTY;
            }
        }

        if (index == this.OUTPUT_I) {

            if (!this.mergeItemStack(slotStack, this.HOT_START, this.INV_END + 1,
                    true)) {

                result = ItemStack.EMPTY;
            }

            slot.onSlotChange(slotStack, result);
            slot.onTake(player, slotStack);

        } else if ((index >= this.CRAFT_START && index <= this.CRAFT_END) ||
                (index >= FEET_I && index <= YOKE_I)) {
            System.out.println("transferring from craft grid index " + index + " to inventory between " + this.HOT_START + " and " + this.INV_END);
            if (!this.mergeItemStack(slotStack, this.HOT_START, this.INV_END + 1,
                    false)) {

                result = ItemStack.EMPTY;
            }

        } else if (index >= this.HOT_START && index <= this.HOT_END) {

            if (!this.mergeItemStack(slotStack, this.INV_START, this.INV_END + 1,
                    true)) {

                result = ItemStack.EMPTY;
            }

        } else if (index >= this.INV_START && index <= this.INV_END) {

            if (!this.mergeItemStack(slotStack, this.HOT_START, this.HOT_END + 1, true)) {

                result = ItemStack.EMPTY;
            }
        }

        slot.onSlotChanged();
        
        return result;
    }

    public void setStack(InvType type, int slot, ItemStack stack) {

        ItemStack replace = (stack == ItemStack.EMPTY ||
                stack.getCount() == 0) ?
                ItemStack.EMPTY : stack;

        switch (type) {

            case INVENTORY: {

                this.playerInv.mainInventory.set(slot, replace);
                break;
            }

            case OFFHAND: {

                this.playerInv.offHandInventory.set(slot, replace);
                break;
            }

            case CRAFTGRID: {

                this.craftMatrix.setInventorySlotContents(slot, replace);
                break;
            }
            
            case CRAFTOUT : {
                
                this.craftResult.setInventorySlotContents(slot, replace);
                break;
            }
        }
    }

    public void swapHands() {

        ItemStack toMove =
                this.playerInv.mainInventory.get(this.playerInv.currentItem);
        this.playerInv.mainInventory.set(this.playerInv.currentItem,
                this.playerInv.offHandInventory.get(0));
        this.playerInv.offHandInventory.set(0, toMove);
    }

    public void sendUpdateOffhand() {

        sendUpdateInventory(InvType.INVENTORY, this.playerInv.currentItem,
                this.playerInv.mainInventory.get(this.playerInv.currentItem));
        sendUpdateInventory(InvType.OFFHAND, 0,
                this.playerInv.offHandInventory.get(0));
    }

    public void sendUpdateHighlight() {

        sendUpdateInventory(InvType.INVENTORY, this.playerInv.currentItem,
                this.playerInv.mainInventory.get(this.playerInv.currentItem));
    }

    public void sendUpdateInventory(InvType type, int slot, ItemStack stack) {

        if (this.player instanceof EntityPlayerMP) {

            ModPackets.INSTANCE
                    .sendTo(new InventoryUpdateClient(type, slot, stack),
                    (EntityPlayerMP) this.player);

        } else {

            ModPackets.INSTANCE.sendToServer(new InventoryUpdateServer(type,
                    slot, stack));
        }
    }

    public enum InvType {

        INVENTORY,
        OFFHAND,
        CRAFTGRID,
        CRAFTOUT;
    }
}
